package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

/**
 * 번호 변경
 */
@Getter
@Setter
@NoArgsConstructor
public class NumberChgeProcessResponse extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDtoList;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        // OTA 선발송여부 (Y: LTE ONLY 단말 이므로 OTA 선발송 먼저 처리)
        @JacksonXmlProperty(localName = "otaPrefSendYn")
        private String otaPrefSendYn;
    }
}
