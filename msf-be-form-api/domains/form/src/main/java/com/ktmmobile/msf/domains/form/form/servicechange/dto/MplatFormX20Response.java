package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class MplatFormX20Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    public static class OutDto {

        private List<X20ResultDto> outDto;

        @Data
        public static class X20ResultDto {

            private String effectiveDate; // 신청일자
            private String prodHstSeq; // 일련번호
            private String soc; // 부가서비스코드
            private String socDescription; // 부가서비스명
            private String socRateValue; // 이용요금
        }
    }
}
