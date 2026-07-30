package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

/**
 * 일시정지 해지신청
 */
@Getter
@Setter
@NoArgsConstructor
public class UnpauseResponse extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;  // 고객번호

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        // 일시정지 가능여부 (Y: 가능, N: 불가)
        @JacksonXmlProperty(localName = "rsltInd")
        private String rsltInd;
        // 일시정지메시지
        @JacksonXmlProperty(localName = "rsltMsg")
        private String rsltMsg;

        /*
            AR02 : 군입대-발착신정지
            AR06 : 군입대-장기발착신
            CR01 : 고객요청-발신정지
            CR02 : 고객요청-발착신정지
            CS01(고객요청M2M,MVNO)
            FS06 : 해외체류-장기발착신
            SNP1: 미납에 의한 일시정지
            DBL3: 이상고액정지
            MP02 : 불법사용이용정지
            TLCN: 해지전 일시정지
         */
        // 전화번호 상태사유코드
        @JacksonXmlProperty(localName = "subStatusRsnCode")
        private String subStatusRsnCode;

        // 발/착신구분코드 (01: 발신정지, 02: 착신정지, 03: 발착신정지)
        @JacksonXmlProperty(localName = "sndarvStatCd")
        private String sndarvStatCd;

        // 사유코드설명 (전화번호상태사유코드+"-"+발착신상태코드)
        @JacksonXmlProperty(localName = "rsnDesc")
        private String rsnDesc;

        // 일시정지일자
        @JacksonXmlProperty(localName = "subStatusDate")
        private String subStatusDate;

        // 전화번호상태 (A: 개통, S: 일시정지, C: 해지, R: 예약)
        @JacksonXmlProperty(localName = "ctnStatus")
        private String ctnStatus;
    }

}
