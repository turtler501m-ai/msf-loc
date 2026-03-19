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
 * M플랫폼 연동 어댑터. 기존 MCP MplatFormServerAdapter 와 동일 구조.
 * juice.url 로 getURL 파라미터 POST 후 응답 XML 을 vo 에 세팅·파싱.
 */
@Service
public class MplatFormServerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MplatFormServerAdapter.class);
    private static final int DEFAULT_TIMEOUT_MS = 30000;

    /** 기존 MCP 와 동일 설정키: M플랫폼 프록시(msc-prx) URL */
    @Value("${juice.url:}")
    private String juiceUrl;

    @Value("${mplatform.log.ip:}")
    private String logIp;

    @Value("${mplatform.log.url:}")
    private String logUrl;

    @Value("${mplatform.log.mdlInd:}")
    private String logMdlInd;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo) {
        return callService(param, vo, DEFAULT_TIMEOUT_MS);
    }

    /**
     * 기존 MCP MplatFormServerAdapter.callService() 와 동일.
     * saveMplateSvcLog → getURL → POST juice.url → vo.setResponseXml / toResponseParse()
     */
    public boolean callService(HashMap<String, String> param, CommonXmlVO vo, int timeoutMs) {
        if (!StringUtils.hasText(juiceUrl)) {
            logger.warn("juice.url 미설정");
            return false;
        }
        boolean result = true;
        try {
            HashMap<String, String> pMplatform = saveMplateSvcLog(param);
            String getURL = getURL(pMplatform);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("getURL", getURL);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            logger.info("*** M-PlatForm Connect Start ***");
            ResponseEntity<String> response = restTemplate.exchange(
                juiceUrl,
                HttpMethod.POST,
                request,
                String.class
            );
            String responseXml = response.getBody() != null ? response.getBody() : "";
            logger.info("responseXml : {}", responseXml.length() > 200 ? responseXml.substring(0, 200) + "..." : responseXml);

            if (!StringUtils.hasText(responseXml)) {
                result = false;
            } else if (vo != null) {
                vo.setResponseXml(responseXml);
                vo.toResponseParse();
            }
        } catch (Exception e) {
            logger.warn("M플랫폼 연동 오류: {}", e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 기존 MCP getURL() 동일: key=value&... 를 UTF-8 인코딩.
     */
    private String getURL(HashMap<String, String> param) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        Set<String> keySet = param.keySet();
        for (String key : keySet) {
            if (result.length() > 0) result.append("&");
            result.append(key).append("=").append(param.get(key) != null ? param.get(key) : "");
        }
        return URLEncoder.encode(result.toString(), StandardCharsets.UTF_8.name());
    }

    /**
     * 엠플렛폼 서비스 연동 로그용 파라미터 보강. MCP saveMplateSvcLog() 동일 역할.
     * ASIS: ip(클라이언트IP), url, mdlInd 를 param 에 추가. 서비스에서 이미 ip 를 넣은 경우(Y04 등) 덮어쓰지 않음.
     */
    private HashMap<String, String> saveMplateSvcLog(HashMap<String, String> param) {
        HashMap<String, String> tmp = new HashMap<>(param);
        if (!StringUtils.hasText(tmp.get("ip")) && StringUtils.hasText(logIp)) {
            tmp.put("ip", logIp);
        }
        if (StringUtils.hasText(logUrl)) tmp.put("url", logUrl);
        if (StringUtils.hasText(logMdlInd)) tmp.put("mdlInd", logMdlInd);
        if (tmp.get("userid") != null) {
            logger.info("userid:{}", tmp.get("userid"));
        }
        return tmp;
    }
}
