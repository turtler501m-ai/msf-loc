package com.ktmmobile.msf.commons.mybatis.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Slf4j
@RequiredArgsConstructor
@Intercepts({
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class QueryIdGenerationInterceptor implements Interceptor {

    private final String defaultQueryIdPrefix;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            Object[] args = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            args[0] = createNewMappedStatement(mappedStatement);
            log.trace(">>> 쿼리ID 주석 자동 적용: {}", mappedStatement.getId());
        } catch (Exception e) {
            log.warn("쿼리ID 주석 자동 적용에 실패했습니다. 무시하고 계속 진행합니다.", e);
        }
        return invocation.proceed();
    }

    private MappedStatement createNewMappedStatement(MappedStatement mappedStatement) {
        String[] keyProperties = mappedStatement.getKeyProperties();
        String[] keyColumns = mappedStatement.getKeyColumns();
        String[] resultSets = mappedStatement.getResultSets();
        return new MappedStatement.Builder(
            mappedStatement.getConfiguration(),
            mappedStatement.getId(),
            new SqlSourceWrapper(mappedStatement, defaultQueryIdPrefix),
            mappedStatement.getSqlCommandType())
            .resource(mappedStatement.getResource())
            .fetchSize(mappedStatement.getFetchSize())
            .timeout(mappedStatement.getTimeout())
            .statementType(mappedStatement.getStatementType())
            .resultSetType(mappedStatement.getResultSetType())
            .cache(mappedStatement.getCache())
            .parameterMap(mappedStatement.getParameterMap())
            .resultMaps(mappedStatement.getResultMaps())
            .flushCacheRequired(mappedStatement.isFlushCacheRequired())
            .useCache(mappedStatement.isUseCache())
            .resultOrdered(mappedStatement.isResultOrdered())
            .keyProperty(keyProperties != null ? String.join(",", keyProperties) : null)
            .keyColumn(keyColumns != null ? String.join(",", keyColumns) : null)
            // 이 필드는? boolean hasNestedResultMaps
            .databaseId(mappedStatement.getDatabaseId())
            // 이 필드는? Log statementLog
            .lang(mappedStatement.getLang())
            .resultSets(resultSets != null ? String.join(",", resultSets) : null)
            .dirtySelect((mappedStatement.isDirtySelect()))
            .keyGenerator(mappedStatement.getKeyGenerator())
            .build();
    }


    @RequiredArgsConstructor
    static class SqlSourceWrapper implements SqlSource {

        private final MappedStatement mappedStatement;
        private final String defaultQueryIdPrefix;

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(parameterObject);
            QueryIdGenerationBoundSql newBoundSql = new QueryIdGenerationBoundSql(mappedStatement.getConfiguration(),
                boundSql,
                mappedStatement.getId(),
                defaultQueryIdPrefix);
            boundSql.getAdditionalParameters()
                .forEach(newBoundSql::setAdditionalParameter);
            return newBoundSql;
        }
    }
}
