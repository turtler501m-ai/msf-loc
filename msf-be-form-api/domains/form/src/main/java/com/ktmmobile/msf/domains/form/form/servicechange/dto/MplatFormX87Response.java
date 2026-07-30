package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class MplatFormX87Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    public static class OutDto {

        private String combTypeNm;  // 결합유형
        private String combProdNm;  // 결합상품
        private String engtPerdMonsNum;  // 약정기간
        private String combDcTypeNm;  // 결합할인 유형명칭
        private String combDcTypeDtl;  // 결합할인 유형 상세
        private String combStDt;  // 결합시작일
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "moscCombDtlListOutDTO")
        private List<MoscCombDtlListOutDTO> moscCombDtlListOutDTO;

        @Data
        public static class MoscCombDtlListOutDTO {

            private String svcContDivNm; //	상품구분
            private String prodNm; //	상품명
            private String svcNo; //	서비스번호
            private String combEngtPerdMonsNum; //	(결합)약정기간
            private String combEngtExpirDt; //	(결합)만료예정일
            private String combEngtStDt; //	회선결합가입일

        }
    }
}
