package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "outDto")
public class ChargePlanChangeProcessResponse {

    private String rsltYn; // 최종변경여부 (Y/N)
    @JacksonXmlProperty(localName = "message")
    private Message message;

    @Data
    private static class Message {

        private String rsltCd; // 결콰코드 (Y:안내, N:제약)
        private String ruleId; // 룰아이디
        private String ruleMsgSbst; // 룰메세지 내용
    }
}
