package com.ktmmobile.msf.commons.auditing.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingEntity;
import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditingUtils {

    public static String getAuditModifier() {
        MsfUser user = AuthenticationUtils.getUser();
        return user.getUserId();
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
        if (auditingEntity == null || auditingEntity.isAlreadySet()) {
            return;
        }
        String resolvedModifier = StringUtils.hasText(modifier) ? modifier : getAuditModifier();
        auditingEntity.setAudit(resolvedModifier, RequestUtils.getClientIp());
    }
}
