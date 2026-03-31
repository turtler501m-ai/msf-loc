package com.ktmmobile.msf.common.mplatform;

import com.ktmmobile.msf.common.mplatform.vo.CommonXmlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

/**
 * OSST 서버 연동 어댑터.
 * ASIS MplatFormOsstServerAdapter 와 동일 구조.
 * osst.url 로 POST 후 응답 XML 을 vo 에 세팅·파싱.
 */
@Service
public class OsstServerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(OsstServerAdapter.class);
    private static final int DEFAULT_TIMEOUT_MS = 100000;

    /** OSST 서버 URL (미설정 시 LOCAL Mock 동작) */
    @Value("${osst.url:}")
    private String osstUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo) {
        return callService(param, vo, DEFAULT_TIMEOUT_MS);
    }

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo, int timeoutMs) {
        if (!StringUtils.hasText(osstUrl)) {
            logger.warn("osst.url 미설정 — OSST 연동 불가");
            return false;
        }
        boolean result = true;
        try {
            String getURL = getURL(param);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("getURL", getURL);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            logger.info("*** OSST Connect Start *** eventCd={}", param.get("appEventCd"));
            ResponseEntity<String> response = restTemplate.exchange(
                osstUrl,
                HttpMethod.POST,
                request,
                String.class
            );
            String responseXml = response.getBody() != null ? response.getBody() : "";
            logger.info("OSST responseXml: {}", responseXml.length() > 200 ? responseXml.substring(0, 200) + "..." : responseXml);

            if (!StringUtils.hasText(responseXml)) {
                result = false;
            } else if (vo != null) {
                vo.setResponseXml(responseXml);
                vo.toResponseParse();
            }
        } catch (Exception e) {
            logger.warn("OSST 연동 오류: {}", e.getMessage());
            result = false;
        }
        return result;
    }

    private String getURL(HashMap<String, String> param) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        Set<String> keySet = param.keySet();
        for (String key : keySet) {
            if (sb.length() > 0) sb.append("&");
            sb.append(key).append("=").append(param.get(key) != null ? param.get(key) : "");
        }
        return URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8.name());
    }
}
