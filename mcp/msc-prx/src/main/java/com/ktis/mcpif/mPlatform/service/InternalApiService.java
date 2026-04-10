package com.ktis.mcpif.mPlatform.service;

import com.ktis.mcpif.mPlatform.vo.SelfCareLogVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class InternalApiService {
    @Resource(name = "propertiesService")
    private EgovPropertyService propertiesService;

    @Autowired
    private RestTemplate restTemplate;

    private String apiInterfaceServer;

    @PostConstruct
    public void init() {
        apiInterfaceServer = propertiesService.getString("api.interface.server");
    }

    public void logSelfCare(SelfCareLogVO selfCareLogVO) {
        restTemplate.postForObject(apiInterfaceServer + "/mPlatform/insertSelfCareLog", selfCareLogVO, SelfCareLogVO.class);
    }

    public String getCryptionYn(String appEventCd) {
        return restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getMplatformCryptionYn", appEventCd, String.class);
    }
}
