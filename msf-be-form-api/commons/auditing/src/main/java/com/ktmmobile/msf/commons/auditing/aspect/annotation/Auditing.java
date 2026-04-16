package com.ktmmobile.msf.commons.auditing.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ktmmobile.msf.commons.auditing.aspect.AuditingEntityAspect;
import com.ktmmobile.msf.commons.auditing.data.AuditModifier;
import com.ktmmobile.msf.commons.auditing.data.code.PredefinedAuditModifier;
import com.ktmmobile.msf.commons.auditing.utils.AuditingUtils;

/**
 * <pre>
 * Auditing Modifier는 <code>AuditModifier.modifiedBy</code> 필드를 의미합니다.
 * Auditing Modifier는 기본적으로 AOP를 통해 인증(Authentication) 객체 정보를 통해 적용됩니다.
 * 인증 객체 정보는 <code>AuditingUtils.getAuditModifier()</code> 메서드를 통해 조회합니다.
 * 인증 객체가 없는 경우, Auditing 처리 중에 오류가 발생합니다.
 *
 * 인증 객체가 없는 경우, Auditing Modifier에 적용할 값을
 * <code>Auditing</code> 애너테이션의 <code>predefinedModifier</code>와 <code>modifier</code> 속성을 통해 지정할 수 있습니다.
 * 위 2개 속성이 동시에 지정되는 경우 <code>predefinedModifier</code>가 <code>modifier</code>보다 우선 적용됩니다.
 * </pre>
 * @see AuditModifier
 * @see AuditingEntityAspect
 * @see AuditingUtils
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditing {

    /**
     * Auditing Modifier 자동 적용을 활성화할지 여부
     */
    boolean enabled() default true;

    /**
     * <pre>
     * Auditing Modifier에 Custom Modifier를 강제로 적용할지 여부
     * <code>false</code>인 경우, 인증 객체 정보를 우선 적용하고, 없으면 Custom Modifier 값을 적용합니다.
     * <code>true</code>인 경우, Custom Modifier를 강제로 적용합니다.
     * </pre>
     */
    boolean forceApply() default false;

    /**
     * <pre>
     * 사전 정의된 Enum을 통해 Custom Modifier 지정
     * (<code>modifier</code>보다 적용 우선순위 높음)
     * </pre>
     * @see PredefinedAuditModifier
     */
    PredefinedAuditModifier predefinedModifier() default PredefinedAuditModifier.NULL;

    /**
     * <pre>
     * 문자열을 통해 Custom Modifier 지정
     * (<code>predefinedModifier</code>보다 적용 우선순위 낮음)
     * </pre>
     */
    String modifier() default "";
}
