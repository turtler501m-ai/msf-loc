package com.ktmmobile.msf.commons.auditing.aspect.processor;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingHandler;

public interface AuditingEntityProcessor {

    boolean supports(Object argument);

    void tryToProcessAuditing(Object argument, AuditingHandler auditingHandler);
}
