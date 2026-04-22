package com.ktmmobile.msf.commons.mybatis.interceptor;

import java.util.Arrays;
import java.util.Optional;
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

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingEntity;
import com.ktmmobile.msf.commons.auditing.aspect.annotation.DefaultAuditingEntity;
import com.ktmmobile.msf.commons.auditing.utils.AuditingUtils;
import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;

/**
 * INSERT/UPDATE/MERGE 시 Audit 컬럼을 동적으로 자동 삽입하는 인터셉터입니다.
 * SQL 파싱을 통해 cret_dt, cret_id, cret_ip, amd_dt, amd_id, amd_ip 컬럼을 자동으로 주입합니다.
 */
@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AutoAuditingInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            Object[] args = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            if (isAutoAuditingEnabled(mappedStatement.getId())) {
                args[0] = createNewMappedStatement(mappedStatement);
            }
        } catch (Exception e) {
            log.warn("Auto auditing SQL injection failed. Proceeding without modification.", e);
        }
        return invocation.proceed();
    }

    private boolean isAutoAuditingEnabled(String mappedStatementId) {
        try {
            int lastDotIdx = mappedStatementId.lastIndexOf('.');
            if (lastDotIdx == -1) {
                return false;
            }
            String className = mappedStatementId.substring(0, lastDotIdx);
            String methodName = mappedStatementId.substring(lastDotIdx + 1);
            Class<?> clazz = Class.forName(className);

            // Method level annotation check
            Optional<AutoAuditing> methodAnnotation = Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .map(m -> m.getAnnotation(AutoAuditing.class));

            if (methodAnnotation.isPresent()) {
                return methodAnnotation.get().value();
            }

            // Class level annotation check
            AutoAuditing classAnnotation = clazz.getAnnotation(AutoAuditing.class);
            if (classAnnotation != null) {
                return classAnnotation.value();
            }
        } catch (Exception e) {
            log.trace("Failed to check @AutoAuditing annotation for: {}", mappedStatementId, e);
        }
        return false;
    }

    private MappedStatement createNewMappedStatement(MappedStatement ms) {
        return new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), new AuditSqlSourceWrapper(ms), ms.getSqlCommandType())
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

    static class AuditSqlSourceWrapper implements SqlSource {

        private final MappedStatement mappedStatement;

        public AuditSqlSourceWrapper(MappedStatement mappedStatement) {
            this.mappedStatement = mappedStatement;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(parameterObject);
            AuditBoundSql newBoundSql = new AuditBoundSql(mappedStatement.getConfiguration(), boundSql);
            boundSql.getAdditionalParameters().forEach(newBoundSql::setAdditionalParameter);
            return newBoundSql;
        }
    }

    static class AuditBoundSql extends BoundSql {

        private final BoundSql boundSql;

        public AuditBoundSql(Configuration config, BoundSql boundSql) {
            super(config, boundSql.getSql(), boundSql.getParameterMappings(), boundSql.getParameterObject());
            this.boundSql = boundSql;
        }

        @Override
        public String getSql() {
            String sql = boundSql.getSql();
            try {
                return rewriteSql(sql);
            } catch (Exception e) {
                log.warn("Error rewriting SQL for auditing. Original SQL used.", e);
                return sql;
            }
        }

        private String rewriteSql(String sql) {
            String cleanSql = sql.replaceAll("/\\*.*?\\*/", "").trim().toUpperCase();
            if (cleanSql.startsWith("INSERT")) {
                return rewriteInsertSql(sql);
            } else if (cleanSql.startsWith("UPDATE")) {
                return rewriteUpdateSql(sql);
            } else if (cleanSql.startsWith("MERGE")) {
                return rewriteMergeSql(sql);
            }
            return sql;
        }

        private String rewriteInsertSql(String sql) {
            AuditingEntity audit = getAuditEntity();
            String modifier = escapeSql(audit.getAuditModifier());
            String ip = escapeSql(audit.getAuditIp());

            String auditCols = "cret_dt, cret_id, cret_ip, amd_dt, amd_id, amd_ip";
            String auditVals = String.format("NOW(), '%s', '%s', NOW(), '%s', '%s'", modifier, ip, modifier, ip);

            // Case 1: INSERT INTO ... (cols) VALUES (vals)
            Pattern valuesPattern = Pattern.compile("\\)\\s*VALUES\\s*\\(", Pattern.CASE_INSENSITIVE);
            Matcher valuesMatcher = valuesPattern.matcher(sql);
            if (valuesMatcher.find()) {
                int insertColsEnd = valuesMatcher.start();
                int valuesStart = valuesMatcher.end();

                String beforeValues = sql.substring(0, insertColsEnd);
                String afterValues = sql.substring(valuesStart);

                int lastParenIdx = afterValues.lastIndexOf(")");
                if (lastParenIdx != -1) {
                    return beforeValues + ", " + auditCols + ") VALUES (" + afterValues.substring(0,
                        lastParenIdx) + ", " + auditVals + afterValues.substring(lastParenIdx);
                }
            }

            // Case 2: INSERT INTO ... (cols) SELECT ...
            Pattern selectPattern = Pattern.compile("\\)\\s*SELECT\\s+", Pattern.CASE_INSENSITIVE);
            Matcher selectMatcher = selectPattern.matcher(sql);
            if (selectMatcher.find()) {
                int insertColsEnd = selectMatcher.start();
                int selectStart = selectMatcher.end();

                String beforeSelect = sql.substring(0, insertColsEnd);
                String afterSelect = sql.substring(selectStart);

                int fromIdx = findTopLevelKeyword(afterSelect, "FROM");
                if (fromIdx != -1) {
                    return beforeSelect + ", " + auditCols + ") SELECT " + afterSelect.substring(0,
                        fromIdx) + ", " + auditVals + " " + afterSelect.substring(fromIdx);
                }
            }

            return sql;
        }

        private String rewriteUpdateSql(String sql) {
            AuditingEntity audit = getAuditEntity();
            String modifier = escapeSql(audit.getAuditModifier());
            String ip = escapeSql(audit.getAuditIp());
            String auditSet = String.format("amd_dt = NOW(), amd_id = '%s', amd_ip = '%s'", modifier, ip);

            int whereIdx = findTopLevelKeyword(sql, "WHERE");
            if (whereIdx != -1) {
                return sql.substring(0, whereIdx) + ", " + auditSet + " " + sql.substring(whereIdx);
            } else {
                return sql + ", " + auditSet;
            }
        }

        private String rewriteMergeSql(String sql) {
            AuditingEntity audit = getAuditEntity();
            String modifier = escapeSql(audit.getAuditModifier());
            String ip = escapeSql(audit.getAuditIp());

            String updateAuditSet = String.format("amd_dt = NOW(), amd_id = '%s', amd_ip = '%s'", modifier, ip);
            String insertAuditCols = "cret_dt, cret_id, cret_ip, amd_dt, amd_id, amd_ip";
            String insertAuditVals = String.format("NOW(), '%s', '%s', NOW(), '%s', '%s'", modifier, ip, modifier, ip);

            String resultSql = sql;

            // Handle WHEN MATCHED THEN UPDATE SET ...
            Pattern updateSetPattern = Pattern.compile("UPDATE\\s+SET\\s+", Pattern.CASE_INSENSITIVE);
            Matcher updateSetMatcher = updateSetPattern.matcher(resultSql);
            if (updateSetMatcher.find()) {
                int setStart = updateSetMatcher.end();
                // Find next top-level WHEN after UPDATE SET
                int nextWhenIdx = findTopLevelKeyword(resultSql.substring(setStart), "WHEN");
                if (nextWhenIdx != -1) {
                    nextWhenIdx += setStart;
                    resultSql = resultSql.substring(0, nextWhenIdx) + ", " + updateAuditSet + " " + resultSql.substring(nextWhenIdx);
                } else {
                    resultSql = resultSql + ", " + updateAuditSet;
                }
            }

            // Handle WHEN NOT MATCHED THEN INSERT (...) VALUES (...)
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
                    resultSql = beforeCols + ", " + insertAuditCols + betweenColsAndVals + afterVals.substring(0,
                        lastParenIdx) + ", " + insertAuditVals + afterVals.substring(lastParenIdx);
                }
            }

            return resultSql;
        }

        private int findTopLevelKeyword(String sql, String keyword) {
            int depth = 0;
            String upperSql = sql.toUpperCase();
            int keywordLen = keyword.length();
            for (int i = 0; i < sql.length(); i++) {
                char c = sql.charAt(i);
                if (c == '(') {
                    depth++;
                } else if (c == ')') {
                    depth--;
                } else if (depth == 0 && i <= sql.length() - (keywordLen + 2)) {
                    // Check if current position starts with keyword surrounded by whitespace or at start/end
                    if (upperSql.substring(i).startsWith(keyword)) {
                        boolean beforeOk = (i == 0 || Character.isWhitespace(sql.charAt(i - 1)));
                        boolean afterOk = (i + keywordLen == sql.length() || Character.isWhitespace(sql.charAt(i + keywordLen)));
                        if (beforeOk && afterOk) {
                            return i;
                        }
                    }
                }
            }
            return -1;
        }

        private AuditingEntity getAuditEntity() {
            AuditingEntity auditingEntity = DefaultAuditingEntity.create();
            AuditingUtils.setAudit(auditingEntity);
            return auditingEntity;
        }

        private String escapeSql(String value) {
            return value == null ? "" : value.replace("'", "''");
        }
    }
}
