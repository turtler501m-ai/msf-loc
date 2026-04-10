package com.ktmmobile.mcp.mcash.service;

import com.ktmmobile.mcp.mcash.dto.McashApiReqDto;
import com.ktmmobile.mcp.mcash.dto.McashApiResDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.ktmmobile.mcp.common.constants.Constants.MCASH_EVENT_CHECK;
import static com.ktmmobile.mcp.common.constants.Constants.MCASH_EVENT_CHECK_CASH;

@Service
public class McashApiServiceImpl implements McashApiService {
    private static Logger logger = LoggerFactory.getLogger(McashApiServiceImpl.class);

    @Value("${ext.url}")
    private String extUrl;

    public McashApiResDto syncUserInfo(McashApiReqDto requestDto) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);
        McashApiResDto mcashApiResDto = null;
        try {
            mcashApiResDto = restTemplate.postForObject(extUrl + "/mcash/syncUserInfo.do", requestDto, McashApiResDto.class);
        } catch (Exception e) {
            logger.error("syncUserInfo error ::: " + e.getMessage());
        }

        return mcashApiResDto;
    }

    public McashApiResDto getRemainCash(McashApiReqDto requestDto) {
        requestDto.setEvntType(MCASH_EVENT_CHECK);
        requestDto.setEvntTypeDtl(MCASH_EVENT_CHECK_CASH);

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);
        McashApiResDto mcashApiResDto = null;
        try {
            mcashApiResDto = restTemplate.postForObject(extUrl + "/mcash/getRemainCash.do", requestDto, McashApiResDto.class);
        } catch (Exception e) {
            logger.error("getRemainCash error ::: " + e.getMessage());
        }
        return mcashApiResDto;
    }
}