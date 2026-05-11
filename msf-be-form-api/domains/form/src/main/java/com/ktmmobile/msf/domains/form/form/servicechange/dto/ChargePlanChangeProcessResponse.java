package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
@JacksonXmlRootElement(localName = "outDto")
@Getter
@Setter
@NoArgsConstructor
public class ChargePlanChangeProcessResponse {

    private String rsltYn; // 최종변경여부 (Y/N)
    @JacksonXmlProperty(localName = "message")
    private Message message;
    private static class Message {

        private String rsltCd; // 결콰코드 (Y:안내, N:제약)
        private String ruleId; // 룰아이디
        private String ruleMsgSbst; // 룰메세지 내용
    }
}
