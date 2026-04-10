package com.ktis.mcpif.mPlatform.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktis.mcpif.mPlatform.event.Y24Y25.MoscPrdcTrtmRequest;
import com.ktis.mcpif.mPlatform.event.Y51.MoscContCustInfoAgreeChgInVO;

import java.io.IOException;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.ktis.mcpif.mPlatform.event.MplatformNamespace.SEL;

public enum EventCode {
    Y24(MoscPrdcTrtmRequest.class, "MoscPrdcTrtmService", "moscPrdcTrtmPreChk", "MoscPrdcTrtmPreChkInVO", SEL)
    ,Y25(MoscPrdcTrtmRequest.class, "MoscPrdcTrtmService", "moscPrdcTrtm", "MoscPrdcTrtmInVO", SEL)
    ,Y51(MoscContCustInfoAgreeChgInVO.class, "MoscContCustInfoAgreeSyncService", "moscContCustInfoAgreeChg", "MoscContCustInfoAgreeChgInVO", SEL)
    ;

    private Class<? extends EventRequest> clazz;
    private String service;
    private String info;
    private String vo;
    private MplatformNamespace namespace;

    EventCode(Class<? extends EventRequest> clazz, String service, String info, String vo, MplatformNamespace namespace) {
        this.clazz = clazz;
        this.service = service;
        this.info = info;
        this.vo = vo;
        this.namespace = namespace;
    }

    public String getService() {
        return service;
    }

    public String getInfo() {
        return info;
    }

    public String getVo() {
        return vo;
    }

    public MplatformNamespace getNamespace() {
        return namespace;
    }

    public EventRequest createEventRequest(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        EventRequest eventRequest = mapper.readValue(json, clazz);
        eventRequest.setEventCode(this);
        return eventRequest;
    }
}
