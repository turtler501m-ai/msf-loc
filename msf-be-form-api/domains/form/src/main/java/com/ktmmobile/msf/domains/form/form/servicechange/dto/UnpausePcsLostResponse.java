package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

/**
 * 분실신고 해지신청
 */
@Getter
@Setter
@NoArgsConstructor
public class UnpausePcsLostResponse extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;  // 고객번호

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        // 결과코드 가능여부 (Y: 정상, 그 외 불가)
        @JacksonXmlProperty(localName = "rsltCd")
        private String rsltCd;
        // 결과메시지
        @JacksonXmlProperty(localName = "rsltMsg")
        private String rsltMsg;

        // 분실신고 신청여부 (I: 분실신고 미신청, U: 분실신고 신청한 상태)
        @JacksonXmlProperty(localName = "runMode")
        private String runMode;

        /*
            Y: 고장접수 처리 중
         -> 고장접수 처리 중 기기는 분실신고 불가"
         */
        // 고장처리 접수 여부
        @JacksonXmlProperty(localName = "asfdYn")
        private String asfdYn;

        /*
            SUS: 일시정지 상태
         -> 일시정지 상태인 경우 분실신고 신청 불가
         */
        // 일시정지 상태
        @JacksonXmlProperty(localName = "subStatusLastAct")
        private String subStatusLastAct;

        /*
            N: 정상고객, D: 미납고객
         -> 미납상태 필수. 미납상태인 경우 분실신고 불가
         */
        // 미납상태
        @JacksonXmlProperty(localName = "coldeLinqStatus")
        private String coldeLinqStatus;

        // 접수일 YYYYMMDDhhmmss
        @JacksonXmlProperty(localName = "rcpDt")
        private String rcpDt;

        /*
            동일고객 ID로 듀얼심 IMEI1, IMEI2가 사용이력이 존재할 경우: Y  (분실처리시 IMEI1,2 모두 분실 처리됨)
            일반 단말 및 듀얼단말이여도 1회선만 사용 또는 타 명의로 사용중일 경우 : N(분실처리시 접수되는 IMEI만 분실접수됨)
         */
        // 듀얼심 분실 처리 여부
        @JacksonXmlProperty(localName = "dualSimTrtYn")
        private String dualSimTrtYn;

    }

}
