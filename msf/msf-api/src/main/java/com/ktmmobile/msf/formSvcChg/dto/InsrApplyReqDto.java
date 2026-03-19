package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 단말보험 가입 요청 DTO.
 * DB 저장 대상: MSF_CUST_REQUEST_INSR.
 * ncn/ctn/custId 는 X21 호출에 사용 (SvcChgInfoDto 상속).
 * ASIS: CustRequestDto (insrProdCd → X21 soc 파라미터로 직접 사용).
 */
public class InsrApplyReqDto extends SvcChgInfoDto {

    /** 신청키 (MSF_REQUEST.REQUEST_KEY) */
    private Long requestKey;

    /** 보험코드 (MSF_CUST_REQUEST_INSR.INSR_CD) */
    private String insrCd;

    /** 단말보험상품코드 — X21 soc 파라미터로 직접 사용 (ASIS insrProdCd) */
    private String insrProdCd;

    /** 단말보험동의여부 (Y/N) */
    private String clauseInsuranceYn;

    /** 단말보험가입동의여부 (Y/N) */
    private String clauseInsrProdYn;

    /** 단말보험인증정보 (NICE) — ONLINE_AUTH_INFO 에 저장 */
    private String insrAuthInfo;

    /** 주민등록번호 암호화 — CSTMR_NATIVE_RRN (NICE 인증 후 수신) */
    private String cstmrNativeRrn;

    /** 고객종류 (NA/NM/FN) — CSTMR_TYPE */
    private String cstmrType;

    /** 온라인인증타입 (NICE 등) — ONLINE_AUTH_TYPE */
    private String onlineAuthType;

    /** D/E/F 타입 고객 사진전송 연락처 — ETC_MEMO */
    private String etcMobile;

    public Long getRequestKey() { return requestKey; }
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }

    public String getInsrCd() { return insrCd; }
    public void setInsrCd(String insrCd) { this.insrCd = insrCd; }

    public String getInsrProdCd() { return insrProdCd; }
    public void setInsrProdCd(String insrProdCd) { this.insrProdCd = insrProdCd; }

    public String getClauseInsuranceYn() { return clauseInsuranceYn; }
    public void setClauseInsuranceYn(String clauseInsuranceYn) { this.clauseInsuranceYn = clauseInsuranceYn; }

    public String getClauseInsrProdYn() { return clauseInsrProdYn; }
    public void setClauseInsrProdYn(String clauseInsrProdYn) { this.clauseInsrProdYn = clauseInsrProdYn; }

    public String getInsrAuthInfo() { return insrAuthInfo; }
    public void setInsrAuthInfo(String insrAuthInfo) { this.insrAuthInfo = insrAuthInfo; }

    public String getCstmrNativeRrn() { return cstmrNativeRrn; }
    public void setCstmrNativeRrn(String cstmrNativeRrn) { this.cstmrNativeRrn = cstmrNativeRrn; }

    public String getCstmrType() { return cstmrType; }
    public void setCstmrType(String cstmrType) { this.cstmrType = cstmrType; }

    public String getOnlineAuthType() { return onlineAuthType; }
    public void setOnlineAuthType(String onlineAuthType) { this.onlineAuthType = onlineAuthType; }

    public String getEtcMobile() { return etcMobile; }
    public void setEtcMobile(String etcMobile) { this.etcMobile = etcMobile; }
}
