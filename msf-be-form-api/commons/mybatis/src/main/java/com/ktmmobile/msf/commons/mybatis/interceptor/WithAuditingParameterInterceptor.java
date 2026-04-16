package com.ktmmobile.msf.commons.mybatis.interceptor;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingEntity;
import com.ktmmobile.msf.commons.auditing.aspect.annotation.DefaultAuditingEntity;
import com.ktmmobile.msf.commons.auditing.utils.AuditingUtils;

@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class WithAuditingParameterInterceptor implements Interceptor {

    private static final String AUDIT_MODIFIER_KEY = "auditModifier";
    private static final String AUDIT_IP_KEY = "auditIp";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            Object[] args = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            if (isInsertOrUpdate(mappedStatement)) {
                args[1] = appendAuditingParameters(args[1]);
            }
        } catch (Exception e) {
            log.warn("Auditing 파라미터 자동 주입에 실패했습니다. 무시하고 계속 진행합니다.", e);
        }
        return invocation.proceed();
    }

    private boolean isInsertOrUpdate(MappedStatement mappedStatement) {
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        return sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.UPDATE;
    }

    private Object appendAuditingParameters(Object parameterObject) {
        AuditingEntity auditingEntity = DefaultAuditingEntity.create();
        AuditingUtils.setAudit(auditingEntity);

        if (parameterObject instanceof Map<?, ?> parameterMap) {
            @SuppressWarnings("unchecked")
            Map<String, Object> mutableMap = (Map<String, Object>) parameterMap;
            return appendAuditValues(mutableMap, auditingEntity);
        }

        if (parameterObject == null) {
            return appendAuditValues(new HashMap<>(), auditingEntity);
        }

        Map<String, Object> parameterMap = new HashMap<>(toPropertyMap(parameterObject));
        parameterMap.putIfAbsent("value", parameterObject);
        parameterMap.putIfAbsent("param1", parameterObject);
        return appendAuditValues(parameterMap, auditingEntity);
    }

    private Map<String, Object> appendAuditValues(Map<String, Object> parameterMap, AuditingEntity auditingEntity) {
        parameterMap.putIfAbsent(AUDIT_MODIFIER_KEY, auditingEntity.getAuditModifier());
        parameterMap.putIfAbsent(AUDIT_IP_KEY, auditingEntity.getAuditIp());
        return parameterMap;
    }

    private Map<String, Object> toPropertyMap(Object parameterObject) {
        Map<String, Object> propertyMap = new HashMap<>();
        if (parameterObject == null) {
            return propertyMap;
        }

        try {
            for (var propertyDescriptor: Introspector.getBeanInfo(parameterObject.getClass(), Object.class).getPropertyDescriptors()) {
                Optional.ofNullable(propertyDescriptor.getReadMethod())
                    .ifPresent(readMethod -> {
                        try {
                            propertyMap.put(propertyDescriptor.getName(), readMethod.invoke(parameterObject));
                        } catch (ReflectiveOperationException e) {
                            log.warn("프로퍼티 [{}] 조회에 실패했습니다.", propertyDescriptor.getName(), e);
                        }
                    });
            }
        } catch (IntrospectionException e) {
            log.warn("파라미터 객체 [{}]의 프로퍼티 추출에 실패했습니다.", parameterObject.getClass().getName(), e);
        }
        return propertyMap;
    }
}
