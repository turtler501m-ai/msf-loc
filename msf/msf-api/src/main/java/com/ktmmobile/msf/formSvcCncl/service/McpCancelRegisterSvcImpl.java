package com.ktmmobile.msf.formSvcCncl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktmmobile.msf.formSvcCncl.dto.McpCancelRegisterReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.McpCancelRegisterResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * M포탈 서비스해지 적재 서비스 구현.
 *
 * 처리 흐름:
 *  1. mcp.cancel.register.url 미설정(LOCAL) → Mock custReqSeq 반환
 *  2. 설정 시 → POST {url} JSON 전송 → resultCode/custReqSeq 파싱
 *
 * M포탈 응답 규격:
 *  { "resultCode": "00000", "custReqSeq": "2026032500001", "message": "" }
 */
@Service
public class McpCancelRegisterSvcImpl implements McpCancelRegisterSvc {

    private static final Logger logger = LoggerFactory.getLogger(McpCancelRegisterSvcImpl.class);
    private static final String REGISTER_PATH = "/mcp/api/cancel/msf-register";
    private static final String SUCCESS_CODE  = "00000";

    /**
     * M포탈 서비스해지 적재 전용 URL.
     * 미설정 시 mcp.portal.url 기반으로 폴백 (단, LOCAL Mock 우선).
     */
    @Value("${mcp.cancel.register.url:}")
    private String mcpCancelRegisterUrl;

    /** M포탈 기본 URL (mcp.portal.url) — fallback */
    @Value("${mcp.portal.url:}")
    private String mcpPortalUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public McpCancelRegisterResVO register(McpCancelRegisterReqDto req) {
        String targetUrl = resolveUrl();

        if (targetUrl == null || targetUrl.isEmpty()) {
            // LOCAL Mock
            String mockSeq = "MSF_MOCK_" + System.currentTimeMillis();
            logger.info("[McpCancelRegister] LOCAL Mock — custReqSeq={}, sourceKey={}", mockSeq, req.getSourceKey());
            return McpCancelRegisterResVO.ok(mockSeq);
        }

        try {
            String body = objectMapper.writeValueAsString(req);
            String response = httpPost(targetUrl + REGISTER_PATH, body);

            @SuppressWarnings("unchecked")
            Map<String, Object> map = objectMapper.readValue(response, Map.class);
            String resultCode = (String) map.get("resultCode");
            String custReqSeq = (String) map.get("custReqSeq");
            String message    = map.containsKey("message") ? (String) map.get("message") : "";

            if (SUCCESS_CODE.equals(resultCode) && custReqSeq != null) {
                logger.info("[McpCancelRegister] 적재 완료: sourceKey={}, custReqSeq={}", req.getSourceKey(), custReqSeq);
                return McpCancelRegisterResVO.ok(custReqSeq);
            } else {
                logger.warn("[McpCancelRegister] 적재 실패: resultCode={}, message={}", resultCode, message);
                return McpCancelRegisterResVO.fail("M포탈 적재 실패: " + message);
            }

        } catch (Exception e) {
            logger.error("[McpCancelRegister] HTTP 오류: sourceKey={}, {}", req.getSourceKey(), e.getMessage());
            return McpCancelRegisterResVO.fail("M포탈 적재 중 오류: " + e.getMessage());
        }
    }

    private String resolveUrl() {
        if (mcpCancelRegisterUrl != null && !mcpCancelRegisterUrl.isEmpty()) {
            return mcpCancelRegisterUrl;
        }
        // mcp.portal.url이 localhost 등 명시적이지 않으면 LOCAL 처리
        if (mcpPortalUrl != null && !mcpPortalUrl.isEmpty()
                && !mcpPortalUrl.contains("localhost") && !mcpPortalUrl.contains("127.0.0.1")) {
            return mcpPortalUrl;
        }
        return null; // LOCAL Mock
    }

    private String httpPost(String urlStr, String jsonBody) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }

        int code = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(),
                        StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        }
        if (code < 200 || code >= 300) {
            throw new Exception("HTTP " + code + ": " + sb);
        }
        return sb.toString();
    }
}
