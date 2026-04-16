package com.ktmmobile.msf.commons.auditing.aspect.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingHandler;

public interface AuditingEntityProcessor {

    Logger log = LoggerFactory.getLogger(AuditingEntityProcessor.class);

    boolean supports(Object argument);

    void tryToProcessAuditing(Object argument, AuditingHandler auditingHandler);
}
