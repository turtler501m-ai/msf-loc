package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
public class MplatFormX01OutDtoResponse {

    @JacksonXmlProperty(localName = "outDto")
    private MplatFormX01OutDtoResponse.OutDto outDto;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String email; //이메일주소
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String addr; //주소
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String homeTel; //전화번호
        private String initActivationDate; //가입일
    }
}
