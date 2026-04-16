package com.ktmmobile.msf.commons.auditing.aspect.processor;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingHandler;

@Component
class CollectionTypeAuditingEntityProcessor implements AuditingEntityProcessor {

    @Override
    public boolean supports(Object argument) {
        return argument instanceof Collection;
    }

    @Override
    public void tryToProcessAuditing(Object argument, AuditingHandler auditingHandler) {
        Collection<?> collection = (Collection<?>) argument;
        collection.forEach(element ->
            AuditingEntityProcessorUtils.processAuditingIfAuditingEntity(element, auditingHandler));
    }
}
