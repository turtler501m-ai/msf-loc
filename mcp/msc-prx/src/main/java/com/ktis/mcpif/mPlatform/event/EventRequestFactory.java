package com.ktis.mcpif.mPlatform.event;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktis.mcpif.mPlatform.service.InternalApiService;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class EventRequestFactory {
    @Resource
    private EgovPropertyService egovPropertyService;

    @Autowired
    private InternalApiService internalApiService;

    public EventRequest createFromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        String appEventCd = node.get("appEventCd").asText();

        EventCode eventCode = EventCode.valueOf(appEventCd);
        EventRequest eventRequest = eventCode.createEventRequest(json);

        // 요청 값 외 세팅
        eventRequest.setEncryptYn(internalApiService.getCryptionYn(appEventCd));
        eventRequest.setClntIp(egovPropertyService.getString("clntIp"));
        eventRequest.setClntUsrId(egovPropertyService.getString("clntUsrId"));

        return eventRequest;
    }
}
