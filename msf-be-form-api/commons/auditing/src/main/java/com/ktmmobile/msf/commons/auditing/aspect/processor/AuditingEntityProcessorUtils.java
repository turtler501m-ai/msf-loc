package com.ktmmobile.msf.commons.auditing.aspect.processor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ClassUtils;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingEntity;
import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingHandler;
import com.ktmmobile.msf.commons.auditing.utils.AuditingUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class AuditingEntityProcessorUtils {

    static void processAuditingIfAuditingEntity(Object argument, AuditingHandler auditingHandler) {
        if (!isAuditingEntity(argument)) {
            return;
        }
        AuditingEntity auditingEntity = (AuditingEntity) argument;
        if (auditingEntity.isAlreadySet()) {
            return;
        }
        doProcessAuditing(auditingEntity, auditingHandler);
    }

    private static boolean isAuditingEntity(Object argument) {
        return argument != null && ClassUtils.isAssignable(AuditingEntity.class, argument.getClass());
    }

    private static void doProcessAuditing(AuditingEntity auditingEntity, AuditingHandler auditingHandler) {
        AuditingUtils.setAudit(auditingEntity, auditingHandler.getAuditModifier());
        logResult(auditingHandler);
    }

    private static void logResult(AuditingHandler auditingHandler) {
        AuditingEntityProcessor.log.trace("Auditing has been processed.: {}", auditingHandler.getMethodSignatureName());
    }
}
