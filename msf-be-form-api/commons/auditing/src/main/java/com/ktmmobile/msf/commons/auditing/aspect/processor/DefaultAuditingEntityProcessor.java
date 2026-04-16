package com.ktmmobile.msf.commons.auditing.aspect.processor;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingHandler;

public class DefaultAuditingEntityProcessor implements AuditingEntityProcessor {

    public static final DefaultAuditingEntityProcessor INSTANCE = new DefaultAuditingEntityProcessor();

    @Override
    public boolean supports(Object argument) {
        return true;
    }

    @Override
    public void tryToProcessAuditing(Object argument, AuditingHandler auditingHandler) {
        AuditingEntityProcessorUtils.processAuditingIfAuditingEntity(argument, auditingHandler);
    }
}
