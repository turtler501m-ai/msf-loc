package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

/**
 * 실시간요금조회(X18)
 */
@Getter
@Setter
@NoArgsConstructor
//@JsonRootName("outDto")
public class RealTimeInfoResponse extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        // 조회 날짜(현재 날짜) 일자
        @JacksonXmlProperty(localName = "searchDay")
        private String searchDay;

        // 조회 날짜기간(현재 월 1일~현재 월일) 예)1201~1219
        @JacksonXmlProperty(localName = "searchTime")
        private String searchTime;

        @JacksonXmlProperty(localName = "amntDto")
        private List<AmntDto> amntDtoList;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AmntDto {

        // 요금항목명 예)단말기대금
        @JacksonXmlProperty(localName = "gubun")
        private String gubun;

        // 요금금액 예)12080
        @JacksonXmlProperty(localName = "payMent")
        private Long payment;

    }

}