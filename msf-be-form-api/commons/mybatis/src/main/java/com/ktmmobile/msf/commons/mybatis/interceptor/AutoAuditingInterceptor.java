package com.ktmmobile.msf.commons.mybatis.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingEntity;
import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingModifierOption;
import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingModifierResolver;
import com.ktmmobile.msf.commons.auditing.aspect.annotation.DefaultAuditingEntity;
import com.ktmmobile.msf.commons.auditing.data.code.PredefinedAuditModifier;
import com.ktmmobile.msf.commons.auditing.utils.AuditingUtils;
import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;

/**
 * INSERT/UPDATE/MERGE 시 Audit 컬럼 동적 자동 삽입 인터셉터
 * <p>SQL 파싱을 통한 cret_dt, cret_id, cret_ip, amd_dt, amd_id, amd_ip 컬럼 자동 주입
 */
@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AutoAuditingInterceptor implements Interceptor {

    /**
     * MyBatis update 계열 실행 전 <code>@AutoAuditing</code> 설정 확인 및
     * SQL Source Auditing 컬럼 주입용 교체
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            Object[] args = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            AutoAuditingOptions options = autoAuditingOptions(mappedStatement.getId());
            if (options.enabled()) {
                // 원본 MappedStatement 대신 SqlSource만 감싼 새 인스턴스로 교체
                args[0] = createNewMappedStatement(mappedStatement, options);
            }
        } catch (Exception e) {
            log.warn("Auto auditing SQL injection failed. Proceeding without modification.: {}", e.getMessage());
        }
        return invocation.proceed();
    }

    /**
     * MappedStatement ID 기반 mapper class/method 조회 및
     * <code>@AutoAuditing</code> 옵션 해석
     */
    private AutoAuditingOptions autoAuditingOptions(String mappedStatementId) {
        try {
            int lastDotIdx = mappedStatementId.lastIndexOf('.');
            if (lastDotIdx == -1) {
                return AutoAuditingOptions.DISABLED;
            }
            String className = mappedStatementId.substring(0, lastDotIdx);
            String methodName = mappedStatementId.substring(lastDotIdx + 1);
            Class<?> clazz = Class.forName(className);

            Method method = findMapperMethod(clazz, methodName).orElse(null);
            Optional<AutoAuditing> autoAuditing = findMethodAutoAuditing(method)
                .or(() -> findClassAutoAuditing(clazz));
            if (autoAuditing.isEmpty()) {
                return AutoAuditingOptions.DISABLED;
            }

            AutoAuditing auditing = autoAuditing.get();
            String auditModifier = auditing.value() ? resolveAuditModifier(method, clazz) : null;
            return AutoAuditingOptions.from(auditing, auditModifier);

        } catch (Exception e) {
            log.warn("Failed to check @AutoAuditing annotation for: {}: {}", mappedStatementId, e.getMessage());
        }
        return AutoAuditingOptions.DISABLED;
    }

    /**
     * Mapper Method 조회
     */
    private Optional<Method> findMapperMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> method.getName().equals(methodName))
            .findFirst();
    }

    /**
     * Method 레벨 <code>@AutoAuditing</code> 애너테이션 조회
     */
    private Optional<AutoAuditing> findMethodAutoAuditing(Method method) {
        // Method annotation 우선 적용
        return Optional.ofNullable(method)
            .map(value -> value.getAnnotation(AutoAuditing.class));
    }

    /**
     * Class 레벨 <code>@AutoAuditing</code> 애너테이션 조회
     */
    private Optional<AutoAuditing> findClassAutoAuditing(Class<?> clazz) {
        // Class annotation mapper method 기본값 적용
        return Optional.ofNullable(clazz.getAnnotation(AutoAuditing.class));
    }

    private String resolveAuditModifier(Method method, Class<?> clazz) {
        AuditingModifierOption methodOption = method == null
            ? AuditingModifierOption.empty()
            : findMethodAutoAuditing(method)
                .map(this::toModifierOption)
                .orElse(AuditingModifierOption.empty());
        AuditingModifierOption typeOption = findClassAutoAuditing(clazz)
            .map(this::toModifierOption)
            .orElse(AuditingModifierOption.empty());
        return AuditingModifierResolver.resolve(methodOption, typeOption);
    }

    private AuditingModifierOption toModifierOption(AutoAuditing autoAuditing) {
        return new AuditingModifierOption(
            true,
            autoAuditing.value(),
            autoAuditing.forceApply(),
            PredefinedAuditModifier.NULL,
            autoAuditing.modifier(),
            autoAuditing.fallbackClientIp()
        );
    }

    /**
     * 기존 MappedStatement 설정 유지 및 SqlSource Auditing 컬럼 주입용 Wrapper 교체
     */
    private MappedStatement createNewMappedStatement(MappedStatement ms, AutoAuditingOptions options) {
        return new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), new AuditSqlSourceWrapper(ms, options), ms.getSqlCommandType())
            .resource(ms.getResource())
            .fetchSize(ms.getFetchSize())
            .timeout(ms.getTimeout())
            .statementType(ms.getStatementType())
            .keyGenerator(ms.getKeyGenerator())
            .keyProperty(ms.getKeyProperties() != null ? String.join(",", ms.getKeyProperties()) : null)
            .keyColumn(ms.getKeyColumns() != null ? String.join(",", ms.getKeyColumns()) : null)
            .databaseId(ms.getDatabaseId())
            .lang(ms.getLang())
            .resultOrdered(ms.isResultOrdered())
            .resultSets(ms.getResultSets() != null ? String.join(",", ms.getResultSets()) : null)
            .resultMaps(ms.getResultMaps())
            .resultSetType(ms.getResultSetType())
            .flushCacheRequired(ms.isFlushCacheRequired())
            .useCache(ms.isUseCache())
            .cache(ms.getCache())
            .parameterMap(ms.getParameterMap())
            .build();
    }


    private record AutoAuditingOptions(
        boolean enabled,
        boolean includeAmendColumns,
        String auditModifier,
        boolean fallbackClientIp
    ) {

        private static final AutoAuditingOptions DISABLED = new AutoAuditingOptions(false, true, null, false);

        /**
         * <code>@AutoAuditing</code> 애너테이션 값의 SQL 재작성 옵션 변환
         */
        private static AutoAuditingOptions from(AutoAuditing autoAuditing, String auditModifier) {
            return new AutoAuditingOptions(
                autoAuditing.value(),
                autoAuditing.includeAmendColumns(),
                auditModifier,
                autoAuditing.fallbackClientIp()
            );
        }
    }


    private record AuditSqlSourceWrapper(MappedStatement mappedStatement, AutoAuditingOptions options) implements SqlSource {

        /**
         * 원본 BoundSql 조회 및 SQL 문자열 재작성 BoundSql wrapper 적용
         */
        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(parameterObject);
            AuditBoundSql newBoundSql = new AuditBoundSql(mappedStatement.getConfiguration(), boundSql, options);
            // MyBatis 동적 SQL 추가 파라미터 전달
            boundSql.getAdditionalParameters().forEach(newBoundSql::setAdditionalParameter);
            return newBoundSql;
        }
    }


    private record InsertAuditClause(String columns, String values) {

        /**
         * SQL 재작성 StringBuilder 초기 용량 보정값 계산
         */
        private int lengthHint() {
            return columns.length() + values.length();
        }
    }


    private static class AuditBoundSql extends BoundSql {

        private final BoundSql boundSql;
        private final AutoAuditingOptions options;

        public AuditBoundSql(Configuration config, BoundSql boundSql, AutoAuditingOptions options) {
            super(config, boundSql.getSql(), boundSql.getParameterMappings(), boundSql.getParameterObject());
            this.boundSql = boundSql;
            this.options = options;
        }

        /**
         * MyBatis 실제 사용 SQL의 Auditing 컬럼 포함 SQL 재작성
         */
        @Override
        public String getSql() {
            String sql = boundSql.getSql();
            try {
                return rewriteSql(sql);
            } catch (Exception e) {
                log.warn("Error rewriting SQL for auditing. Original SQL used.: {}", e.getMessage());
                return sql;
            }
        }

        /**
         * 최상위 SQL 명령어 종류별 INSERT/UPDATE/MERGE 재작성 로직 선택
         */
        private String rewriteSql(String sql) {
            String cleanSql = sql.replaceAll("/\\*.*?\\*/", "").trim().toUpperCase();
            // 명령어 판별 전 선행 주석 제거 및 원본 SQL 재작성 유지
            if (cleanSql.startsWith("INSERT")) {
                return rewriteInsertSql(sql);
            } else if (cleanSql.startsWith("UPDATE")) {
                return rewriteUpdateSql(sql);
            } else if (cleanSql.startsWith("MERGE")) {
                return rewriteMergeSql(sql);
            }
            return sql;
        }

        /**
         * INSERT 컬럼 목록과 VALUES/SELECT 목록에 생성 및 선택적 수정 Auditing 값 추가
         */
        private String rewriteInsertSql(String sql) {
            InsertAuditClause auditClause = insertAuditClause();
            return rewriteInsertValuesSql(sql, auditClause)
                .orElseGet(() -> rewriteInsertSelectSql(sql, auditClause).orElse(sql));
        }

        /**
         * INSERT VALUES 구문의 컬럼 목록과 각 VALUES tuple에 Auditing 컬럼/값 추가
         */
        private Optional<String> rewriteInsertValuesSql(String sql, InsertAuditClause auditClause) {
            Pattern valuesPattern = Pattern.compile("\\)\\s*VALUES\\s*\\(", Pattern.CASE_INSENSITIVE);
            Matcher valuesMatcher = valuesPattern.matcher(sql);
            if (!valuesMatcher.find()) {
                return Optional.empty();
            }

            int insertColsEnd = valuesMatcher.start();
            int tupleValueStart = valuesMatcher.end();
            StringBuilder rewrittenSql = new StringBuilder(sql.length() + auditClause.lengthHint());

            // INSERT 컬럼 목록 끝과 첫 번째 VALUES tuple 시작부까지 원본 공백/대소문자 유지
            rewrittenSql.append(sql, 0, insertColsEnd)
                .append(", ")
                .append(auditClause.columns())
                .append(sql, insertColsEnd, tupleValueStart);

            int currentTupleValueStart = tupleValueStart;
            while (currentTupleValueStart < sql.length()) {
                OptionalInt tupleEnd = findClosingParen(sql, currentTupleValueStart);
                if (tupleEnd.isEmpty()) {
                    return Optional.empty();
                }

                int tupleEndIdx = tupleEnd.getAsInt();
                // 현재 tuple 값 끝에 Auditing 값 추가
                rewrittenSql.append(sql, currentTupleValueStart, tupleEndIdx)
                    .append(", ")
                    .append(auditClause.values())
                    .append(")");

                OptionalInt nextTupleStart = findNextValuesTupleStart(sql, tupleEndIdx + 1);
                if (nextTupleStart.isEmpty()) {
                    rewrittenSql.append(sql.substring(tupleEndIdx + 1));
                    break;
                }

                int nextTupleStartIdx = nextTupleStart.getAsInt();
                // tuple 사이의 콤마/공백과 다음 tuple 여는 괄호 원본 유지
                rewrittenSql.append(sql, tupleEndIdx + 1, nextTupleStartIdx + 1);
                currentTupleValueStart = nextTupleStartIdx + 1;
            }

            return Optional.of(rewrittenSql.toString());
        }

        /**
         * INSERT SELECT 구문의 컬럼 목록과 SELECT list에 Auditing 컬럼/값 추가
         */
        private Optional<String> rewriteInsertSelectSql(String sql, InsertAuditClause auditClause) {
            Pattern selectPattern = Pattern.compile("\\)\\s*SELECT\\s+", Pattern.CASE_INSENSITIVE);
            Matcher selectMatcher = selectPattern.matcher(sql);
            if (!selectMatcher.find()) {
                return Optional.empty();
            }

            int insertColsEnd = selectMatcher.start();
            int selectListStart = selectMatcher.end();
            int fromIdx = findTopLevelKeyword(sql.substring(selectListStart), "FROM");
            if (fromIdx == -1) {
                return Optional.empty();
            }

            int fromStart = selectListStart + fromIdx;
            // SELECT list 최상위 FROM 직전에 Auditing 값 추가
            return Optional.of(sql.substring(0, insertColsEnd)
                + ", "
                + auditClause.columns()
                + sql.substring(insertColsEnd, selectListStart)
                + sql.substring(selectListStart, fromStart)
                + ", "
                + auditClause.values()
                + " "
                + sql.substring(fromStart));
        }

        /**
         * INSERT Auditing 컬럼/값 묶음 구성
         */
        private InsertAuditClause insertAuditClause() {
            AuditingEntity audit = getAuditEntity();
            String modifier = escapeSql(audit.getAuditModifier());
            String ip = escapeSql(audit.getAuditIp());

            return new InsertAuditClause(insertAuditColumns(), insertAuditValues(modifier, ip));
        }

        /**
         * VALUES tuple 닫는 괄호 인덱스 조회
         */
        private OptionalInt findClosingParen(String sql, int start) {
            int depth = 1;
            int i = start;

            while (i < sql.length()) {
                char c = sql.charAt(i);
                if (c == '\'') {
                    i = skipStringLiteral(sql, i + 1);
                    continue;
                }

                if (c == '(') {
                    depth++;
                } else if (c == ')') {
                    depth--;
                    if (depth == 0) {
                        return OptionalInt.of(i);
                    }
                }

                i++;
            }

            return OptionalInt.empty();
        }

        /**
         * SQL 문자열 리터럴 종료 직후 인덱스 조회
         */
        private int skipStringLiteral(String sql, int start) {
            int idx = start;
            while (idx < sql.length()) {
                if (isEscapedQuote(sql, idx)) {
                    idx += 2;
                } else if (sql.charAt(idx) == '\'') {
                    return idx + 1;
                } else {
                    idx++;
                }
            }
            return idx;
        }

        /**
         * SQL 문자열 리터럴 내부 escape quote 여부 확인
         */
        private boolean isEscapedQuote(String sql, int index) {
            return sql.charAt(index) == '\''
                && index + 1 < sql.length()
                && sql.charAt(index + 1) == '\'';
        }

        /**
         * Multi-row INSERT의 다음 VALUES tuple 시작 괄호 인덱스 조회
         */
        private OptionalInt findNextValuesTupleStart(String sql, int start) {
            int commaIdx = skipWhitespace(sql, start);
            if (commaIdx >= sql.length() || sql.charAt(commaIdx) != ',') {
                return OptionalInt.empty();
            }

            int nextTupleStart = skipWhitespace(sql, commaIdx + 1);
            if (nextTupleStart >= sql.length() || sql.charAt(nextTupleStart) != '(') {
                return OptionalInt.empty();
            }

            return OptionalInt.of(nextTupleStart);
        }

        /**
         * 공백이 아닌 첫 번째 문자 인덱스 조회
         */
        private int skipWhitespace(String sql, int start) {
            int idx = start;
            while (idx < sql.length() && Character.isWhitespace(sql.charAt(idx))) {
                idx++;
            }
            return idx;
        }

        /**
         * UPDATE SET 절에 수정 Auditing 컬럼 값 추가
         */
        private String rewriteUpdateSql(String sql) {
            if (!options.includeAmendColumns()) {
                return sql;
            }

            AuditingEntity audit = getAuditEntity();
            String modifier = escapeSql(audit.getAuditModifier());
            String ip = escapeSql(audit.getAuditIp());
            String auditSet = String.format("amd_dt = NOW(), amd_id = '%s', amd_ip = '%s'", modifier, ip);

            int whereIdx = findTopLevelKeyword(sql, "WHERE");
            if (whereIdx != -1) {
                // 최상위 WHERE 앞 Auditing 대입식 추가로 predicate 유지
                return sql.substring(0, whereIdx) + ", " + auditSet + " " + sql.substring(whereIdx);
            } else {
                return sql + ", " + auditSet;
            }
        }

        /**
         * MERGE UPDATE 분기와 INSERT 분기의 Auditing 컬럼 값 추가
         */
        private String rewriteMergeSql(String sql) {
            AuditingEntity audit = getAuditEntity();
            String modifier = escapeSql(audit.getAuditModifier());
            String ip = escapeSql(audit.getAuditIp());

            String updateAuditSet = String.format("amd_dt = NOW(), amd_id = '%s', amd_ip = '%s'", modifier, ip);
            String insertAuditCols = insertAuditColumns();
            String insertAuditVals = insertAuditValues(modifier, ip);

            String resultSql = sql;

            // WHEN MATCHED THEN UPDATE SET 처리
            if (options.includeAmendColumns()) {
                Pattern updateSetPattern = Pattern.compile("UPDATE\\s+SET\\s+", Pattern.CASE_INSENSITIVE);
                Matcher updateSetMatcher = updateSetPattern.matcher(resultSql);
                if (updateSetMatcher.find()) {
                    int setStart = updateSetMatcher.end();
                    // UPDATE SET 이후 다음 최상위 WHEN 탐색
                    int nextWhenIdx = findTopLevelKeyword(resultSql.substring(setStart), "WHEN");
                    if (nextWhenIdx != -1) {
                        nextWhenIdx += setStart;
                        // 다음 WHEN 분기 직전 Auditing 대입식 추가
                        resultSql = resultSql.substring(0, nextWhenIdx) + ", " + updateAuditSet + " " + resultSql.substring(nextWhenIdx);
                    } else {
                        resultSql = appendAuditSet(resultSql, updateAuditSet);
                    }
                } else {
                    resultSql = appendAuditSet(resultSql, updateAuditSet);
                }
            }

            // WHEN NOT MATCHED THEN INSERT 구문 처리
            Pattern insertPattern = Pattern.compile("INSERT\\s*\\(([^)]+)\\)\\s*VALUES\\s*\\(", Pattern.CASE_INSENSITIVE);
            Matcher insertMatcher = insertPattern.matcher(resultSql);
            if (insertMatcher.find()) {
                int colsEnd = insertMatcher.end(1);
                int valsStart = insertMatcher.end();

                String beforeCols = resultSql.substring(0, colsEnd);
                String betweenColsAndVals = resultSql.substring(colsEnd, valsStart);
                String afterVals = resultSql.substring(valsStart);

                int lastParenIdx = afterVals.lastIndexOf(")");
                if (lastParenIdx != -1) {
                    // MERGE INSERT 분기 컬럼/값 목록에 Auditing 필드 추가
                    resultSql = beforeCols + ", " + insertAuditCols + betweenColsAndVals + afterVals.substring(0,
                        lastParenIdx) + ", " + insertAuditVals + afterVals.substring(lastParenIdx);
                }
            }

            return resultSql;
        }

        /**
         * SQL 뒤쪽에 Auditing SET 절 추가
         */
        private String appendAuditSet(String sql, String auditSet) {
            return sql + ", " + auditSet;
        }

        /**
         * INSERT 계열 SQL 주입 Auditing 컬럼 목록 반환
         */
        private String insertAuditColumns() {
            if (options.includeAmendColumns()) {
                return "cret_dt, cret_id, cret_ip, amd_dt, amd_id, amd_ip";
            }
            return "cret_dt, cret_id, cret_ip";
        }

        /**
         * INSERT 계열 SQL 주입 Auditing 값 목록 반환
         */
        private String insertAuditValues(String modifier, String ip) {
            if (options.includeAmendColumns()) {
                return String.format("NOW(), '%s', '%s', NOW(), '%s', '%s'", modifier, ip, modifier, ip);
            }
            return String.format("NOW(), '%s', '%s'", modifier, ip);
        }

        /**
         * 괄호 중첩을 고려한 최상위 SQL 키워드 인덱스 조회
         */
        private int findTopLevelKeyword(String sql, String keyword) {
            int depth = 0;
            int keywordLen = keyword.length();
            int lastKeywordStart = sql.length() - keywordLen;

            for (int i = 0; i < sql.length(); i++) {
                char c = sql.charAt(i);
                if (c == '(') {
                    depth++;
                } else if (c == ')') {
                    depth--;
                }

                boolean topLevelKeywordStart = depth == 0 && i <= lastKeywordStart;
                if (topLevelKeywordStart && sql.regionMatches(true, i, keyword, 0, keywordLen) && isKeywordBoundary(sql, i, keywordLen)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 검색 문자열의 독립 SQL 키워드 여부 확인
         */
        private boolean isKeywordBoundary(String sql, int start, int keywordLen) {
            int end = start + keywordLen;
            boolean beforeOk = start == 0 || Character.isWhitespace(sql.charAt(start - 1));
            boolean afterOk = end == sql.length() || Character.isWhitespace(sql.charAt(end));
            return beforeOk && afterOk;
        }

        /**
         * 현재 요청 Auditing Modifier 정보 구성
         */
        private AuditingEntity getAuditEntity() {
            AuditingEntity auditingEntity = DefaultAuditingEntity.create();
            if (StringUtils.hasText(options.auditModifier())) {
                AuditingUtils.setAudit(auditingEntity, options.auditModifier(), options.fallbackClientIp());
                return auditingEntity;
            }
            AuditingUtils.setAudit(auditingEntity, null, options.fallbackClientIp());
            return auditingEntity;
        }

        /**
         * Auditing 값 문자열의 SQL literal 포함용 작은따옴표 escape
         */
        private String escapeSql(String value) {
            return value == null ? "" : value.replace("'", "''");
        }
    }
}
