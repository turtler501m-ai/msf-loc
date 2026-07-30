package com.ktmmobile.msf.commons.auditing.utils;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingEntity;
import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingModifierResolver;
import com.ktmmobile.msf.commons.common.context.LoginContextHolder;
import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class AuditingUtils {

    public static String getAuditModifier() {
        return getAuthenticatedUserId()
            .or(LoginContextHolder::getUserId)
            .orElseGet(AuditingUtils::emptyModifier);
    }

    public static boolean hasAuditModifier() {
        try {
            return StringUtils.hasText(getAuditModifier());
        } catch (Exception _) {
            return false;
        }
    }

    public static void setAudit(AuditingEntity auditingEntity) {
        setAudit(auditingEntity, getAuditModifier());
    }

    public static void setAudit(AuditingEntity auditingEntity, String modifier) {
        setAudit(auditingEntity, modifier, false);
    }

    public static void setAudit(AuditingEntity auditingEntity, String modifier, boolean fallbackClientIp) {
        if (auditingEntity == null || auditingEntity.isAlreadySet()) {
            return;
        }
        String resolvedModifier = StringUtils.hasText(modifier) ? modifier : getAuditModifier();
        auditingEntity.setAudit(resolvedModifier, AuditingModifierResolver.resolveClientIp(fallbackClientIp));
    }

    private static Optional<String> getAuthenticatedUserId() {
        try {
            MsfUser user = AuthenticationUtils.getUser();
            return Optional.ofNullable(user.getUserId())
                .filter(StringUtils::hasText);
        } catch (Exception _) {
            return Optional.empty();
        }
    }

    private static String emptyModifier() {
        log.warn("Audit modifier is empty. authenticatedUser=false, loginContext=false");
        return "";
    }
}
