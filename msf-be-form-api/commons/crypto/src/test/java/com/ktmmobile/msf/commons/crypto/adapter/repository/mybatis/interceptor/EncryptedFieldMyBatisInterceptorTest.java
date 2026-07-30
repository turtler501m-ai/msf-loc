package com.ktmmobile.msf.commons.crypto.adapter.repository.mybatis.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;
import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;
import com.ktmmobile.msf.commons.crypto.support.processor.AesGcmTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.FieldCryptoProcessor;
import com.ktmmobile.msf.commons.crypto.support.processor.SearchableAesGcmTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.TextEncryptorRegistry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EncryptedFieldMyBatisInterceptorTest {

    private final EncryptedFieldMyBatisInterceptor interceptor = new EncryptedFieldMyBatisInterceptor(
        new FieldCryptoProcessor(new TextEncryptorRegistry(Map.of(
            FieldCryptoAlgorithm.AES_GCM,
            new AesGcmTextEncryptor("12345678901234567890123456789012", "ENC:", "aes-gcm-v1"),
            FieldCryptoAlgorithm.AES_GCM_SEARCHABLE,
            new SearchableAesGcmTextEncryptor("12345678901234567890123456789012", "ENC:", "aes-gcm-v1")
        )))
    );

    @Test
    @DisplayName("INSERT/UPDATE 파라미터는 원본을 변경하지 않고 암호화된 복사본으로 전달한다")
    void encryptUpdateParameterCopyWithoutChangingOriginal() throws Throwable {
        TestRequest request = new TestRequest("plain");
        RecordingExecutor executor = new RecordingExecutor();

        interceptor.intercept(new Invocation(
            executor,
            updateMethod(),
            new Object[] {mappedStatement(SqlCommandType.INSERT), request}
        ));

        assertThat(executor.updatedParameter).isInstanceOf(TestRequest.class);
        assertThat(((TestRequest) executor.updatedParameter).secret).startsWith("ENC:gcm1:aes-gcm-v1:");
        assertThat(request.secret).isEqualTo("plain");
    }

    @Test
    @DisplayName("SELECT 조건은 검색 가능 암호화 필드만 암호화된 복사본으로 전달한다")
    void encryptOnlySearchableRecordCopyForSelectCondition() throws Throwable {
        SearchCondition condition = new SearchCondition("01012345678", "plain-memo");
        RecordingExecutor executor = new RecordingExecutor();

        interceptor.intercept(new Invocation(
            executor,
            queryMethod(),
            new Object[] {mappedStatement(SqlCommandType.SELECT), condition, RowBounds.DEFAULT, null}
        ));

        assertThat(executor.queriedParameter).isInstanceOf(SearchCondition.class);
        SearchCondition encryptedCondition = (SearchCondition) executor.queriedParameter;
        assertThat(encryptedCondition.phoneNumber()).startsWith("ENC:sgcm1:aes-gcm-v1:");
        assertThat(encryptedCondition.memo()).isEqualTo("plain-memo");
        assertThat(condition.phoneNumber()).isEqualTo("01012345678");
        assertThat(condition.memo()).isEqualTo("plain-memo");
    }

    @Test
    @DisplayName("암호화 대상이 없는 파라미터는 복사하지 않고 원본 그대로 전달한다")
    void keepNoEncryptedParameterWithoutCopying() throws Throwable {
        PlainCondition condition = new PlainCondition("device-uuid");
        RecordingExecutor executor = new RecordingExecutor();

        interceptor.intercept(new Invocation(
            executor,
            queryMethod(),
            new Object[] {mappedStatement(SqlCommandType.SELECT), condition, RowBounds.DEFAULT, null}
        ));

        assertThat(executor.queriedParameter).isSameAs(condition);
    }

    @Test
    @DisplayName("SELECT 결과의 암호화 필드는 조회 직후 복호화한다")
    void decryptResultAfterSelect() throws Throwable {
        FieldCryptoProcessor processor =
            new FieldCryptoProcessor(new AesGcmTextEncryptor("12345678901234567890123456789012", "ENC:", "aes-gcm-v1"));
        TestRequest request = new TestRequest("plain");
        processor.encrypt(request);
        RecordingResultSetHandler resultSetHandler = new RecordingResultSetHandler(List.of(request));

        interceptor.intercept(new Invocation(resultSetHandler, resultSetHandlingMethod(), new Object[] {null}));

        assertThat(request.secret).isEqualTo("plain");
    }

    @Test
    @DisplayName("MyBatis 쿼리 실행 중 발생한 예외는 CryptoException으로 변환하지 않는다")
    void keepInvocationExceptionWithoutCryptoWrapping() {
        FailingExecutor executor = new FailingExecutor();

        assertThatThrownBy(() -> interceptor.intercept(new Invocation(
            executor,
            updateMethod(),
            new Object[] {mappedStatement(SqlCommandType.INSERT), new TestRequest("plain")}
        )))
            .isInstanceOf(InvocationTargetException.class)
            .hasCauseInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("암호화 파라미터 전처리 중 발생한 예외만 CryptoException으로 변환한다")
    void convertCryptoPreprocessingExceptionToCryptoException() {
        RecordingExecutor executor = new RecordingExecutor();

        assertThatThrownBy(() -> interceptor.intercept(new Invocation(
            executor,
            updateMethod(),
            new Object[] {mappedStatement(SqlCommandType.INSERT), new InvalidRequest()}
        )))
            .isInstanceOf(CryptoException.class)
            .hasMessageContaining("@Encrypted supports String fields only");
    }

    private static Method updateMethod() {
        return methodOf(Executor.class, "update", MappedStatement.class, Object.class);
    }

    private static Method queryMethod() {
        return methodOf(Executor.class, "query", MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class);
    }

    private static Method resultSetHandlingMethod() {
        return methodOf(ResultSetHandler.class, "handleResultSets", java.sql.Statement.class);
    }

    private static Method methodOf(Class<?> type, String name, Class<?>... parameterTypes) {
        try {
            return type.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    private static MappedStatement mappedStatement(SqlCommandType sqlCommandType) {
        Configuration configuration = new Configuration();
        return new MappedStatement.Builder(
            configuration,
            "test." + sqlCommandType.name().toLowerCase(),
            new StaticSqlSource(configuration, ""),
            sqlCommandType
        ).build();
    }


    private static class RecordingExecutor extends NoopExecutor {

        private Object updatedParameter;
        private Object queriedParameter;

        @Override
        public int update(MappedStatement ms, Object parameter) {
            this.updatedParameter = parameter;
            return 1;
        }

        @Override
        public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) {
            this.queriedParameter = parameter;
            return List.of();
        }
    }


    private static class FailingExecutor extends NoopExecutor {

        @Override
        public int update(MappedStatement ms, Object parameter) {
            throw new IllegalStateException("update failed");
        }
    }


    private abstract static class NoopExecutor implements Executor {

        @Override
        public int update(MappedStatement ms, Object parameter) {
            return 0;
        }

        @Override
        public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) {
            return List.of();
        }

        @Override
        public <E> List<E> query(
            MappedStatement ms,
            Object parameter,
            RowBounds rowBounds,
            ResultHandler resultHandler,
            CacheKey cacheKey,
            BoundSql boundSql
        ) {
            return List.of();
        }

        @Override
        public <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) {
            return null;
        }

        @Override
        public List<BatchResult> flushStatements() {
            return List.of();
        }

        @Override
        public void commit(boolean required) {
        }

        @Override
        public void rollback(boolean required) {
        }

        @Override
        public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
            return new CacheKey();
        }

        @Override
        public boolean isCached(MappedStatement ms, CacheKey key) {
            return false;
        }

        @Override
        public void clearLocalCache() {
        }

        @Override
        public void deferLoad(MappedStatement ms, org.apache.ibatis.reflection.MetaObject resultObject, String property, CacheKey key, Class<?> targetType) {
        }

        @Override
        public Transaction getTransaction() {
            return null;
        }

        @Override
        public void close(boolean forceRollback) {
        }

        @Override
        public boolean isClosed() {
            return false;
        }

        @Override
        public void setExecutorWrapper(Executor executor) {
        }
    }


    private static class RecordingResultSetHandler implements ResultSetHandler {

        private final List<TestRequest> result;

        private RecordingResultSetHandler(List<TestRequest> result) {
            this.result = result;
        }

        @Override
        public List<TestRequest> handleResultSets(Statement stmt) {
            return result;
        }

        @Override
        public <E> Cursor<E> handleCursorResultSets(Statement stmt) {
            return null;
        }

        @Override
        public void handleOutputParameters(CallableStatement cs) throws SQLException {
        }
    }


    private static class TestRequest {

        @Encrypted
        private String secret;

        private TestRequest() {
        }

        private TestRequest(String secret) {
            this.secret = secret;
        }
    }


    private static class InvalidRequest {

        @Encrypted
        private Integer secret = 1;
    }


    private record SearchCondition(
        @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
        String phoneNumber,
        @Encrypted
        String memo
    ) {
    }


    private record PlainCondition(String uuid) {
    }
}
