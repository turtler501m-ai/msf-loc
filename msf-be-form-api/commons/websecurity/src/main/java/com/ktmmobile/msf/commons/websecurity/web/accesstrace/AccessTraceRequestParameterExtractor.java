package com.ktmmobile.msf.commons.websecurity.web.accesstrace;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.ktmmobile.msf.commons.websecurity.web.util.ContentTypeUtils;

/**
 * access trace PARAMETER 컬럼에 저장할 요청 파라미터 추출 유틸
 */
public final class AccessTraceRequestParameterExtractor {

    private static final String TRUNCATED_SUFFIX = "...<truncated>";

    private AccessTraceRequestParameterExtractor() {
    }

    /**
     * 현재 요청의 body/query 문자열 생성
     */
    public static String extract(HttpServletRequest request) {
        return extract(request, null);
    }

    /**
     * 현재 요청의 body/query 문자열 생성 및 마스킹/축약
     */
    public static String extract(HttpServletRequest request, AccessTraceParameterSanitizer parameterSanitizer) {
        List<String> parameters = new ArrayList<>();
        String body = requestBody(request, parameterSanitizer);
        if (StringUtils.hasText(body)) {
            parameters.add("body=" + body);
        }
        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            queryString = parameterSanitizer == null
                ? queryString
                : parameterSanitizer.sanitizeQueryString(queryString, requestCharset(request));
            parameters.add("query=" + queryString);
        }
        String parameter = String.join(", ", parameters);
        return parameterSanitizer == null ? parameter : parameterSanitizer.limitParameter(parameter);
    }

    /**
     * 텍스트 요청 본문 반환
     */
    private static String requestBody(HttpServletRequest request, AccessTraceParameterSanitizer parameterSanitizer) {
        if (!isTextBodyContentType(request.getContentType()) || !(request instanceof ContentCachingRequestWrapper wrapper)) {
            return "";
        }

        byte[] body = wrapper.getContentAsByteArray();
        if (body.length == 0 || containsBinaryByte(body)) {
            return "";
        }

        Charset charset = requestCharset(request);
        String bodyText = new String(body, charset);
        if (parameterSanitizer != null) {
            bodyText = parameterSanitizer.sanitizeBody(request.getContentType(), charset, bodyText);
        }
        if (wrapper instanceof LimitedRequestBodyCache limitedWrapper && limitedWrapper.isOverflowed()) {
            return bodyText + TRUNCATED_SUFFIX;
        }
        return bodyText;
    }

    /**
     * 요청 Content-Type에서 charset 확인
     */
    private static Charset requestCharset(HttpServletRequest request) {
        try {
            return StringUtils.hasText(request.getCharacterEncoding())
                ? Charset.forName(request.getCharacterEncoding())
                : ContentTypeUtils.contentCharset(request.getContentType());
        } catch (RuntimeException _) {
            return StandardCharsets.UTF_8;
        }
    }

    /**
     * 텍스트 요청 본문 Content-Type 여부
     */
    private static boolean isTextBodyContentType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return false;
        }
        String normalizedContentType = normalizeContentType(contentType);
        return normalizedContentType.startsWith("text/")
            || "application/json".equals(normalizedContentType)
            || normalizedContentType.endsWith("+json")
            || "application/xml".equals(normalizedContentType)
            || normalizedContentType.endsWith("+xml")
            || "application/x-www-form-urlencoded".equals(normalizedContentType);
    }

    /**
     * Content-Type 정규화
     */
    private static String normalizeContentType(String contentType) {
        int parameterIndex = contentType.indexOf(';');
        String mediaType = parameterIndex >= 0 ? contentType.substring(0, parameterIndex) : contentType;
        return mediaType.trim().toLowerCase();
    }

    /**
     * 바이너리 바이트 포함 여부
     */
    private static boolean containsBinaryByte(byte[] body) {
        for (byte value: body) {
            if (value == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 요청 본문 캐싱 초과 여부 제공 타입
     */
    public interface LimitedRequestBodyCache {

        /**
         * 캐싱 제한 초과 여부
         */
        boolean isOverflowed();
    }
}
