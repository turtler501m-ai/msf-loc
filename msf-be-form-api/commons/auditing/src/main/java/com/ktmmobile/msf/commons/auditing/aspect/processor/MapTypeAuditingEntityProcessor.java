package com.ktmmobile.msf.commons.auditing.aspect.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingHandler;

@Component
class MapTypeAuditingEntityProcessor implements AuditingEntityProcessor {

    @Override
    public boolean supports(Object argument) {
        return argument instanceof Map;
    }

    @Override
    public void tryToProcessAuditing(Object argument, AuditingHandler auditingHandler) {
        Map<?, ?> map = (Map<?, ?>) argument;
        map.forEach((key, element) ->
            AuditingEntityProcessorUtils.processAuditingIfAuditingEntity(element, auditingHandler));
    }
}
