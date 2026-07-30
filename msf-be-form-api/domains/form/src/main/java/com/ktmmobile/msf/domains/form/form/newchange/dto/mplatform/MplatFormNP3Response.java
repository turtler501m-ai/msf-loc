package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;


@Data
@EqualsAndHashCode(callSuper = false)
public class MplatFormNP3Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private MplatFormNP3Response.OutDto outDto;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        private String rsltCd; //처리결과코드
        private String rsltMsg; //처리결과메세지
    }
}
