package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class OwnerChangeJoinInfoResponse extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        @JacksonXmlProperty(localName = "addr")
        private String addr; // 주소
        @JacksonXmlProperty(localName = "email")
        private String email; // 이메일 주소
        @JacksonXmlProperty(localName = "homeTel")
        private String homeTel; // 전화번호
        @JacksonXmlProperty(localName = "initActivationDate")
        private String initActivationDate; // 가입일
        private String ctn;
        private String custId;
        private String ncn;
        private String userId;
        private String esimYn;
        private String banAdrZip;
        private String banAdrPrimaryLn;
        private String banAdrSecondaryLn;
        private String blBillingMethod;
    }
}
