package com.ktmmobile.msf.commons.auditing.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.auditing.aspect.annotation.AuditingEntity;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditingUtils {

    public static String getAuditModifier() {
        // MsfUser user = AuthenticationUtils.getUser();
        // return String.format("%s|%s", user.getUserRole().getSimpleCode(), user.getId());
        return String.format("%s|%s",
            UserType.ADMIN_USER.getSimpleCode(),
            "82312000"); // FIXME: 임시
    }

    public static boolean hasAuditModifier() {
        try {
            return StringUtils.hasText(getAuditModifier());
        } catch (Exception e) {
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
