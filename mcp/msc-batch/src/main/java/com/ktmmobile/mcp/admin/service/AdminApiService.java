package com.ktmmobile.mcp.admin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AdminApiService {
    @Value("${admin.url}")
    private String adminUrl;

    public boolean makeXmlFromAllRateAdsvc() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(600000);
        RestTemplate restTemplate = new RestTemplate(factory);
        String resultCode = restTemplate.postForObject(adminUrl + "/rate/makeXmlAllData2.do", null, String.class);

        return "0000".equals(resultCode);
    }
}
