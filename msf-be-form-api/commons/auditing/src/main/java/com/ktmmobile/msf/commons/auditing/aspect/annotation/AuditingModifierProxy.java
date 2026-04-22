package com.ktmmobile.msf.commons.auditing.aspect.annotation;

import com.ktmmobile.msf.commons.auditing.data.code.PredefinedAuditModifier;

/**
 * @see AuditingModifier
 */
public class AuditingModifierProxy {

    private final AuditingModifier auditingModifier;

    public AuditingModifierProxy(AuditingModifier auditingModifier) {
        this.auditingModifier = auditingModifier;
    }

    public boolean isPresent() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return auditingModifier == null;
    }

    public boolean enabled() {
        if (auditingModifier == null) {
            return true;
        }
        return auditingModifier.enabled();
    }

    public boolean forceApply() {
        if (auditingModifier == null) {
            return false;
        }
        return auditingModifier.forceApply();
    }

    public PredefinedAuditModifier predefinedModifier() {
        if (auditingModifier == null) {
            return PredefinedAuditModifier.NULL;
        }
        return auditingModifier.predefinedModifier();
    }

    public String modifier() {
        if (auditingModifier == null) {
            return null;
        }
        return auditingModifier.modifier();
    }
}
