package com.ktmmobile.msf.commons.auditing.aspect.annotation;

import java.net.UnknownHostException;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.auditing.utils.AuditingUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

/**
 * Auditing Modifier 애너테이션/옵션 Resolver
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditingModifierResolver {

    public static String resolve(AuditingModifierOption methodOption, AuditingModifierOption typeOption) {
        String modifier = resolve(methodOption);
        if (StringUtils.hasText(modifier)) {
            return modifier;
        }
        return resolve(typeOption);
    }

    public static String resolve(AuditingModifierOption option) {
        if (option == null || !option.present() || !option.enabled()) {
            return null;
        }

        String customModifier = customModifierOf(option);
        if (option.forceApply()) {
            if (StringUtils.hasText(customModifier)) {
                return customModifier;
            }
            throw new IllegalArgumentException("Auditing Modifier Option에 Custom Modifier가 지정되지 않았습니다.");
        }

        if (AuditingUtils.hasAuditModifier()) {
            return AuditingUtils.getAuditModifier();
        }
        return customModifier;
    }

    public static boolean resolveFallbackClientIp(AuditingModifierOption methodOption, AuditingModifierOption typeOption) {
        if (methodOption != null && methodOption.present()) {
            return methodOption.fallbackClientIp();
        }
        if (typeOption != null && typeOption.present()) {
            return typeOption.fallbackClientIp();
        }
        return false;
    }

    public static String resolveClientIp(boolean fallbackClientIp) {
        if (!fallbackClientIp) {
            return RequestUtils.getClientIp();
        }

        HttpServletRequest request = RequestUtils.getRequestIfNoRequest();
        if (request == null) {
            return serverIpFallback("Request 정보 없음");
        }

        String clientIp = RequestUtils.getClientIp(request);
        if (StringUtils.hasText(clientIp)) {
            return clientIp;
        }
        return serverIpFallback("Client IP 없음");
    }

    private static String customModifierOf(AuditingModifierOption option) {
        if (option.predefinedModifier().isValid()) {
            return option.predefinedModifier().getCode();
        }
        if (StringUtils.hasText(option.modifier())) {
            return option.modifier();
        }
        return null;
    }

    private static String serverIpFallback(String reason) {
        try {
            String serverIp = RequestUtils.getServerIp();
            log.warn("Client IP를 조회할 수 없어 Server IP를 사용합니다. reason={}, serverIp={}", reason, serverIp);
            return serverIp;
        } catch (UnknownHostException e) {
            log.warn("Client IP와 Server IP를 모두 조회할 수 없습니다. reason={}, error={}", reason, e.getMessage());
            return "";
        }
    }

}
