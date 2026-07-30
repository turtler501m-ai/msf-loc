package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

/**
 * 변경 가능한 번호 목록 조회
 */
@Getter
@Setter
@NoArgsConstructor
public class NumChgeResponse extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private List<OutDto> outDtoList;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        // 디스플레이할 전화번호 리스트
        @JacksonXmlProperty(localName = "ctn")
        private String ctn;
        // 디스플레이할 암호화번호 리스트
        @JacksonXmlProperty(localName = "sctn")
        private String sctn;
        // 디스플레이할 marketGubun (KT 또는 KTF)
        @JacksonXmlProperty(localName = "marketGubun")
        private String marketGubun;
        // 전화번호상태변경일 (형식 : yyyyMMdd)
        @JacksonXmlProperty(localName = "ctnStatChgDt")
        private String ctnStatChgDt;

    }

}