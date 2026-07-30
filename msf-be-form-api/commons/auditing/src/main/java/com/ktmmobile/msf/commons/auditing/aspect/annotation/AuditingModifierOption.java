package com.ktmmobile.msf.commons.auditing.aspect.annotation;

import com.ktmmobile.msf.commons.auditing.data.code.PredefinedAuditModifier;

/**
 * Auditing Modifier 옵션
 */
public record AuditingModifierOption(
    boolean present,
    boolean enabled,
    boolean forceApply,
    PredefinedAuditModifier predefinedModifier,
    String modifier,
    boolean fallbackClientIp
) {

    public static AuditingModifierOption empty() {
        return new AuditingModifierOption(false, true, false, PredefinedAuditModifier.NULL, "", false);
    }

    public static AuditingModifierOption of(AuditingModifierProxy auditing) {
        if (auditing == null || auditing.isEmpty()) {
            return empty();
        }
        return new AuditingModifierOption(
            true,
            auditing.enabled(),
            auditing.forceApply(),
            auditing.predefinedModifier(),
            auditing.modifier(),
            auditing.fallbackClientIp()
        );
    }
}
