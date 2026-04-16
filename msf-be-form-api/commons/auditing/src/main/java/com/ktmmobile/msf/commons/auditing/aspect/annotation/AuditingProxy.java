package com.ktmmobile.msf.commons.auditing.aspect.annotation;

import com.ktmmobile.msf.commons.auditing.data.code.PredefinedAuditModifier;

/**
 * @see Auditing
 */
public class AuditingProxy {

    private final Auditing auditing;

    public AuditingProxy(Auditing auditing) {
        this.auditing = auditing;
    }

    public boolean isPresent() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return auditing == null;
    }

    public boolean enabled() {
        if (auditing == null) {
            return true;
        }
        return auditing.enabled();
    }

    public boolean forceApply() {
        if (auditing == null) {
            return false;
        }
        return auditing.forceApply();
    }

    public PredefinedAuditModifier predefinedModifier() {
        if (auditing == null) {
            return PredefinedAuditModifier.NULL;
        }
        return auditing.predefinedModifier();
    }

    public String modifier() {
        if (auditing == null) {
            return null;
        }
        return auditing.modifier();
    }
}
