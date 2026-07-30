package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnerChangeWireUseTimeResponse extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        @JacksonXmlProperty(localName = "svcContSbscDt")
        private String svcContSbscDt; // 서비스계약가입일시
        @JacksonXmlProperty(localName = "longUseAdjDayNum")
        private String longUseAdjDayNum; // 장기조정기간일수
        @JacksonXmlProperty(localName = "realUseDayNum")
        private Integer realUseDayNum; // 실사용기
        @JacksonXmlProperty(localName = "totStopDayNum")
        private Integer totStopDayNum;
        @JacksonXmlProperty(localName = "totUseDayNum")
        private Integer totUseDayNum;
    }
}
