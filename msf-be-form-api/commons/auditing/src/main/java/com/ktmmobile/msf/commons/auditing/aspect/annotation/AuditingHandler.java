package com.ktmmobile.msf.commons.auditing.aspect.annotation;

import lombok.Getter;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.auditing.aspect.AuditingEntityAspect;
import com.ktmmobile.msf.commons.auditing.utils.AuditingUtils;

/**
 * @see AuditingEntityAspect
 * @see AuditingModifier
 */
public class AuditingHandler {

    private final MethodSignature signature;
    private final AuditingModifierProxy methodAuditing;
    private final AuditingModifierProxy typeAuditing;
    private final AuditingModifierOption methodOption;
    private final AuditingModifierOption typeOption;

    @Getter
    private final boolean auditingDisabled;

    private String cachedModifier;
    private Boolean cachedFallbackClientIp;


    public AuditingHandler(MethodSignature signature) {
        this.signature = signature;
        this.methodAuditing = cacheMethodAuditingAnnotation();
        this.typeAuditing = cacheTypeAuditingAnnotation();
        this.methodOption = AuditingModifierOption.of(methodAuditing);
        this.typeOption = AuditingModifierOption.of(typeAuditing);
        this.auditingDisabled = cacheAuditingDisabled();
    }

    private AuditingModifierProxy cacheMethodAuditingAnnotation() {
        AuditingModifier auditingModifier = signature.getMethod().getAnnotation(AuditingModifier.class);
        return new AuditingModifierProxy(auditingModifier);
    }

    private AuditingModifierProxy cacheTypeAuditingAnnotation() {
        Class<?> declaringClass = signature.getMethod().getDeclaringClass();
        AuditingModifier auditingModifier = declaringClass.getAnnotation(AuditingModifier.class);
        return new AuditingModifierProxy(auditingModifier);
    }

    /**
     * <pre>
     * 타입의 설정보다 메서드의 <code>@Auditing</code> 설정이 우선 적용됩니다.
     * 타입에 <code>@Auditing(enabled = false)</code>로 설정되어 있어도
     * 메서드에 <code>@Auditing(enabled = true)</code>로 설정되어 있다면 Auditing이 활성화됩니다.</pre>
     */
    private boolean cacheAuditingDisabled() {
        if (methodAuditing.isPresent()) {
            return !methodAuditing.enabled();
        }
        return !typeAuditing.enabled();
    }


    /**
     * <pre>
     * 타입의 설정보다 메서드의 <code>@Auditing</code> 설정이 우선 적용됩니다.</pre>
     * @see AuditingUtils#getAuditModifier()
     */
    public String getAuditModifier() {
        if (isModifierCached()) {
            return cachedModifier;
        }

        String modifier = AuditingModifierResolver.resolve(methodOption, typeOption);
        if (StringUtils.hasText(modifier)) {
            cacheModifier(modifier);
            return modifier;
        }
        return AuditingUtils.getAuditModifier();
    }

    public boolean fallbackClientIp() {
        if (cachedFallbackClientIp != null) {
            return cachedFallbackClientIp;
        }
        cachedFallbackClientIp = AuditingModifierResolver.resolveFallbackClientIp(methodOption, typeOption);
        return cachedFallbackClientIp;
    }

    private boolean isModifierCached() {
        return StringUtils.hasText(cachedModifier);
    }

    private void cacheModifier(String modifier) {
        this.cachedModifier = modifier;
    }

    public String getMethodSignatureName() {
        return signature.toShortString();
    }
}
