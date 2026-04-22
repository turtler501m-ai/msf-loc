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

    @Getter
    private final boolean auditingDisabled;

    private String cachedModifier;


    public AuditingHandler(MethodSignature signature) {
        this.signature = signature;
        this.methodAuditing = cacheMethodAuditingAnnotation();
        this.typeAuditing = cacheTypeAuditingAnnotation();
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

        String modifier = getModifierOf(methodAuditing);
        if (StringUtils.hasText(modifier)) {
            cacheModifier(modifier);
            return modifier;
        }
        modifier = getModifierOf(typeAuditing);
        if (StringUtils.hasText(modifier)) {
            cacheModifier(modifier);
            return modifier;
        }
        return AuditingUtils.getAuditModifier();
    }

    private boolean isModifierCached() {
        return StringUtils.hasText(cachedModifier);
    }

    private void cacheModifier(String modifier) {
        this.cachedModifier = modifier;
    }

    /**
     * <pre>
     * <code>@Auditing(forceApply = true)</code>인 경우, Custom Modifier가 강제 적용됩니다.
     * 이때 Custom Modifier를 지정하지 않으면 <code>IllegalArgumentException</code>이 발생합니다.
     * <code>@Auditing(forceApply = false)</code>인 경우, Custom Modifier보다 인증 객체 값이 우선 적용됩니다.</pre>
     */
    private String getModifierOf(AuditingModifierProxy auditing) {
        if (auditing.isEmpty() || !auditing.enabled()) {
            return null;
        }

        String customModifier = getCustomModifierOf(auditing);
        if (auditing.forceApply()) {
            if (StringUtils.hasText(customModifier)) {
                return customModifier;
            }
            throw new IllegalArgumentException("@Auditing 애너테이션에 Custom Modifier가 지정되지 않았습니다.");
        }

        if (AuditingUtils.hasAuditModifier()) {
            return AuditingUtils.getAuditModifier();
        }
        return customModifier;
    }

    /**
     * <pre>
     * <code>@Auditing</code>의 <code>modifier()</code>보다 <code>predefinedModifier()</code> 속성이 우선 적용됩니다.</pre>
     */
    private String getCustomModifierOf(AuditingModifierProxy auditing) {
        if (auditing.predefinedModifier().isValid()) {
            return auditing.predefinedModifier().getCode();
        }
        if (StringUtils.hasText(auditing.modifier())) {
            return auditing.modifier();
        }
        return null;
    }

    public String getMethodSignatureName() {
        return signature.toShortString();
    }
}
