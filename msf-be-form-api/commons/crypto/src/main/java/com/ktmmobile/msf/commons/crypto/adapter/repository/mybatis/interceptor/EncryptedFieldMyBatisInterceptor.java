package com.ktmmobile.msf.commons.crypto.adapter.repository.mybatis.interceptor;

import java.sql.Statement;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;
import com.ktmmobile.msf.commons.crypto.support.processor.FieldCryptoProcessor;

/**
 * MyBatis SQL 실행 전후 {@code @Encrypted} 필드 처리 인터셉터
 *
 * <p>INSERT/UPDATE 직전 파라미터 암호화
 * SELECT 결과 매핑 직후 복호화
 * SELECT 조건의 검색 가능 알고리즘 한정 암호화</p>
 */
@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
    @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class EncryptedFieldMyBatisInterceptor implements Interceptor {

    private final FieldCryptoProcessor fieldCryptoProcessor;

    public EncryptedFieldMyBatisInterceptor(FieldCryptoProcessor fieldCryptoProcessor) {
        this.fieldCryptoProcessor = fieldCryptoProcessor;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof Executor) {
            // SQL 바인딩 전 암호화 파라미터 복사본 교체
            replaceWithEncryptedParameterCopy(invocation);
        }
        Object result = invocation.proceed();
        if (target instanceof ResultSetHandler) {
            // ResultSet 매핑 완료 후 응답 DTO 암호문 복호화
            decryptResult(result);
        }
        return result;
    }

    /** MyBatis parameterObject 암호화 복사본 교체 */
    private void replaceWithEncryptedParameterCopy(Invocation invocation) {
        Object[] args = invocation.getArgs();
        Object originalParameterObject = args[1];
        try {
            args[1] = encryptedParameterCopy((MappedStatement) args[0], originalParameterObject);
        } catch (CryptoException e) {
            throw e;
        } catch (Exception e) {
            throw new CryptoException("Encrypted field parameter processing failed.", e);
        }
    }

    private Object encryptedParameterCopy(MappedStatement mappedStatement, Object parameterObject) {
        if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
            if (!fieldCryptoProcessor.hasSearchableEncryptedValue(parameterObject)) {
                return parameterObject;
            }
            // SELECT 조건의 동등 검색 가능 필드 암호화
            return fieldCryptoProcessor.encryptSearchableCopy(parameterObject);
        }
        if (!fieldCryptoProcessor.hasEncryptedValue(parameterObject)) {
            return parameterObject;
        }
        // INSERT/UPDATE/DELETE 계열 전체 @Encrypted 필드 암호화
        return fieldCryptoProcessor.encryptCopy(parameterObject);
    }

    /** 조회 결과 객체 그래프 암호화 필드 복호화 */
    private void decryptResult(Object result) {
        try {
            fieldCryptoProcessor.decrypt(result);
        } catch (CryptoException e) {
            throw e;
        } catch (Exception e) {
            log.warn("Encrypted field decryption failed.", e);
            throw new CryptoException("Encrypted field decryption failed.", e);
        }
    }
}
