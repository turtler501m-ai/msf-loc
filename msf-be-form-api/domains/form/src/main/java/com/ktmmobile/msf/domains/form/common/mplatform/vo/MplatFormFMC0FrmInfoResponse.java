package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import lombok.Data;

@Data
public class MplatFormFMC0FrmInfoResponse {

    // =========================
    // inFrmpapDto
    // =========================
    private String mngmAgncId;            // 접점코드
    private String cntpntCd;            // 접점코드
    private String frmpapId;            // 서식지아이디
    // =========================

    private String mcnResNo;

    // 법인 정보
    private String cstmrJuridicalRrn;          // 법인 번호
    private String cstmrJuridicalBizNo;        // 법인 사업자 번호
    private String cstmrJuridicalUserNm;       // 실사용자 이름
    private String cstmrJuridicalBirth;        // 실사용자 생년월일
    private String cstmrJuridicalRepNm;        // 법인 대표자명
    private String cstmrJuridicalBizNoIssuDate;        // 법인 교부일자
    private String cstmrTelNo;                 // 고객 전화번호
    private String upjnCd;                     // 업종
    private String bcuSbst;                    // 업태
    private String cstmrZipcd;                 // 우편번호
    private String cstmrAdr;                   // 기본주소
    private String cstmrAdrDtl;                // 상세주소
    private String jrdclAgentNm;               // 법인 대리인 이름
    private String jrdclAgentRrn;              // 법인 대리인 생년월일/식별번호
    private String jrdclAgentRelTypeCd;        // 법인 대리인 관계코드
    private String jrdclAgentTelNo;            // 법인 대리인 전화번호
}
