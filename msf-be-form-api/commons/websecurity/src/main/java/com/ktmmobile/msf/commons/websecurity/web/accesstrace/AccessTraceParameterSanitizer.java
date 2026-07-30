package com.ktmmobile.msf.commons.websecurity.web.accesstrace;

import java.nio.charset.Charset;

/**
 * API 요청 이력 PARAMETER 값 마스킹 및 축약 처리기
 */
public interface AccessTraceParameterSanitizer {

    /**
     * 요청 본문 마스킹 및 축약
     *
     * @param contentType 요청 본문 Content-Type
     * @param charset 요청 본문 문자셋
     * @param bodyText 요청 본문 문자열
     * @return 마스킹 및 축약된 요청 본문
     */
    String sanitizeBody(String contentType, Charset charset, String bodyText);

    /**
     * QueryString 마스킹 및 축약
     *
     * @param queryString 요청 QueryString
     * @param charset QueryString 문자셋
     * @return 마스킹 및 축약된 QueryString
     */
    String sanitizeQueryString(String queryString, Charset charset);

    /**
     * 최종 PARAMETER 값 축약
     *
     * @param parameter PARAMETER 컬럼 저장 문자열
     * @return 축약된 PARAMETER 컬럼 저장 문자열
     */
    String limitParameter(String parameter);
}
