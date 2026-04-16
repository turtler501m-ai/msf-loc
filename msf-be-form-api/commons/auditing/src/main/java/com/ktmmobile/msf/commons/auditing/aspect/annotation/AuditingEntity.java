package com.ktmmobile.msf.commons.auditing.aspect.annotation;

import java.util.Optional;

import com.ktmmobile.msf.commons.auditing.data.AuditModifier;
import com.ktmmobile.msf.commons.auditing.data.code.PredefinedAuditModifier;

public interface AuditingEntity {

    AuditModifier getAudit();

    default String getAuditModifier() {
        return Optional.ofNullable(getAudit().getModifiedBy())
            .orElse(PredefinedAuditModifier.getNullCode());
    }

    default String getAuditIp() {
        return Optional.ofNullable(getAudit().getModifiedIp())
            .orElse(PredefinedAuditModifier.getNullCode());
    }

    default boolean isAlreadySet() {
        return getAudit().isAlreadySet();
    }

    default void setAudit(String modifier, String ip) {
        getAudit().setModifier(modifier, ip);
    }
}
