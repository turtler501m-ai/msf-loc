package com.ktmmobile.msf.commons.auditing.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingHandler;
import com.ktmmobile.msf.commons.auditing.aspect.processor.AuditingEntityProcessor;
import com.ktmmobile.msf.commons.auditing.aspect.processor.DefaultAuditingEntityProcessor;

@RequiredArgsConstructor
@Aspect
@Component
public class AuditingEntityAspect {

    private static final ThreadLocal<Boolean> AUDITING_IN_PROGRESS = ThreadLocal.withInitial(() -> false);

    private final List<AuditingEntityProcessor> auditingEntityProcessors;

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    private void repositoryAnnotation() { }

    /**
     * MyBatis 매퍼는 MyBatis 인터셉터를 이용해서 Auditing 파라미터를 자동 전달하도록 구현했으므로
     * AuditingEntityAspect가 동작할 필요가 없습니다.
     * @see com.ktmmobile.msf.commons.mybatis.interceptor.WithAuditingParameterInterceptor
     */
    @Pointcut("@within(org.apache.ibatis.annotations.Mapper)")
    private void myBatisMapperAnnotation() { }

    //@Before("repositoryAnnotation() || myBatisMapperAnnotation()")
    @Before("repositoryAnnotation()")
    public void doBefore(JoinPoint joinPoint) {
        if (Boolean.TRUE.equals(AUDITING_IN_PROGRESS.get())) {
            return;
        }
        if (isNotTransactionWrite()) {
            return;
        }

        AUDITING_IN_PROGRESS.set(true);
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AuditingHandler auditingHandler = new AuditingHandler(signature);
            if (auditingHandler.isAuditingDisabled()) {
                return;
            }

            Arrays.stream(joinPoint.getArgs())
                .filter(Objects::nonNull)
                .forEach(argument -> auditingEntityProcessors.stream()
                    .filter(processor -> processor.supports(argument))
                    .findAny()
                    .orElse(DefaultAuditingEntityProcessor.INSTANCE)
                    .tryToProcessAuditing(argument, auditingHandler)
                );
        } finally {
            AUDITING_IN_PROGRESS.remove();
        }
    }

    private static boolean isNotTransactionWrite() {
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        return !isActive || readOnly;
    }
}
