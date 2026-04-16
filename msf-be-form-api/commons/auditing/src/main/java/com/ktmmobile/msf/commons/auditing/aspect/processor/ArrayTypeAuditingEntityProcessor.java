package com.ktmmobile.msf.commons.auditing.aspect.processor;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingHandler;

@Component
class ArrayTypeAuditingEntityProcessor implements AuditingEntityProcessor {

    @Override
    public boolean supports(Object argument) {
        return argument.getClass().isArray();
    }

    @Override
    public void tryToProcessAuditing(Object argument, AuditingHandler auditingHandler) {
        Object[] array = (Object[]) argument;
        Arrays.stream(array).forEach(element ->
            AuditingEntityProcessorUtils.processAuditingIfAuditingEntity(element, auditingHandler));
    }
}
