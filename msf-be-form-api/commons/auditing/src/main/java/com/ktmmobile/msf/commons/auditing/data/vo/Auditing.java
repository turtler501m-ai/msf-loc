package com.ktmmobile.msf.commons.auditing.data.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.auditing.data.code.AuditType;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

@ToString
@Getter
@NoArgsConstructor
public class Auditing extends SimpleAuditing {

    private AuditTrail registerer;
    private AuditTrail modifier;

    public Auditing(
        LocalDateTime registeredDate,
        String registeredIp,
        LocalDateTime modifiedDate,
        String modifiedIp,
        AuditTrail registerer,
        AuditTrail modifier
    ) {
        super(registeredDate, registeredIp, modifiedDate, modifiedIp);
        this.registerer = registerer;
        this.modifier = modifier;
    }

    public Auditing(
        LocalDateTime registeredDate,
        String registeredIp,
        LocalDateTime modifiedDate,
        String modifiedIp
    ) {
        super(registeredDate, registeredIp, modifiedDate, modifiedIp);
        this.registerer = null;
        this.modifier = null;
    }

    public static Auditing empty() {
        return new Auditing(LocalDateTime.now(), RequestUtils.getClientIp(),
            LocalDateTime.now(), RequestUtils.getClientIp(),
            AuditTrail.empty(), AuditTrail.empty());
    }

    @ToString
    @Getter
    public static class AuditTrail {

        private final AuditType type;
        private final String id;
        private final String name;

        public AuditTrail(String id, String name, String rawId) {
            this.type = resolveAuditType(rawId);
            this.id = getRequireValueElse(id, rawId);
            this.name = getRequireValueElse(name, "");
        }

        public AuditTrail(
            AuditTrailAdmin admin,
            AuditTrailUser user,
            String rawId
        ) {
            this(resolveAuditId(admin, user),
                resolveAuditName(admin, user),
                rawId);
        }

        private static String getRequireValueElse(String value, String defaultValue) {
            if (StringUtils.hasText(value)) {
                return value;
            }
            return defaultValue;
        }

        private static AuditType resolveAuditType(String rawId) {
            if (!StringUtils.hasText(rawId) || !rawId.contains("|")) {
                return AuditType.getDefaultValue();
            }

            String simpleCode = rawId.substring(0, rawId.indexOf('|'));
            return AuditType.valueOfSimpleCode(simpleCode);
        }

        public String getType() {
            return type.getCode();
        }

        public AuditTrail(
            String adminId,
            String adminName,
            String userId,
            String userName,
            String rawId
        ) {
            this(new AuditTrailAdmin(adminId, adminName),
                new AuditTrailUser(userId, userName),
                rawId);
        }

        private static String resolveAuditId(AuditTrailAdmin admin, AuditTrailUser user) {
            if (StringUtils.hasText(admin.id())) {
                return admin.id();
            }
            if (StringUtils.hasText(user.id())) {
                return user.id();
            }
            return "";
        }

        private static String resolveAuditName(AuditTrailAdmin admin, AuditTrailUser user) {
            if (StringUtils.hasText(admin.id())) {
                return admin.name();
            }
            if (StringUtils.hasText(user.id())) {
                return user.name();
            }
            return "";
        }


        public static AuditTrail empty() {
            return new AuditTrail("", "", "");
        }


        public record AuditTrailAdmin(String id, String name) { }

        public record AuditTrailUser(String id, String name) { }
    }
}
