package com.ktmmobile.msf.domains.form.common.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * mcp-api 호출 공통 클라이언트.
 * application-common-xxx.yaml 설정으로 호출 방식을 전환한다.
 * api.interface.use-mcp : true  — mcp-api REST 호출. 연결 실패 시 MCP 직접 조회로 자동 전환(TEST)
 *                         false — mcp-api 호출 없이 MCP 직접 조회만 사용 (정책 변경 시)
 */

@Component
public class McpApiClient {

    private static final Logger logger = LoggerFactory.getLogger(McpApiClient.class);

    @Value("${api.interface.server}")
    private String baseUrl;

    @Value("${api.interface.use-mcp:true}")
    private boolean useMcp;

    private final McpApiDirectRepository mspApiDirectRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public McpApiClient(McpApiDirectRepository mspApiDirectRepository) {
        this.mspApiDirectRepository = mspApiDirectRepository;
    }

    public boolean isLocal() {
        return baseUrl != null && baseUrl.startsWith("LOCAL");
    }

    /**
     * mcp-api POST 호출.
     */

    public <T> T post(String path, Object request, Class<T> responseType) {
        if (!useMcp) {
            logger.debug("[McpApiClient] use-mcp=false, MSP 직접 조회: {}", path);
            return mspApiDirectRepository.query(path, request, responseType);
        }

        String url = baseUrl + path;
        logger.debug("[McpApiClient] POST {}", url);
        try {
            T response = restTemplate.postForObject(url, request, responseType);
            logger.debug("[McpApiClient] POST {} OK", url);
            return response;
        } catch (ResourceAccessException e) {
            logger.warn("[McpApiClient] POST {} 연결 실패", url);
            logger.warn("[McpApiClient] MSP 직접 조회 실행: {}",e.getMessage());
            return mspApiDirectRepository.query(path, request, responseType);
        }
    }
}