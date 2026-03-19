package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 단말보험 가입 요청 DTO.
 * DB 저장 대상: MSF_CUST_REQUEST_INSR (가입신청보험정보).
 * ncn/ctn/custId/soc 는 M플랫폼(Y24, X21) 호출에 사용.
 */
public class InsrApplyReqDto extends SvcChgInfoDto {

    /** 신청키 (MSF_REQUEST.REQUEST_KEY) */
    private Long requestKey;

    /** 보험코드 */
    private String insrCd;

    /** 단말보험상품코드 */
    private String insrProdCd;

    /** 보험 SOC 코드 (M플랫폼 Y24/X21 호출용) */
    private String soc;

    /** 단말보험동의여부 (Y/N) */
    private String clauseInsuranceYn;

    /** 단말보험가입동의여부 (Y/N) */
    private String clauseInsrProdYn;

    /** 단말보험인증정보 */
    private String insrAuthInfo;

    public Long getRequestKey() { return requestKey; }
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }

    public String getInsrCd() { return insrCd; }
    public void setInsrCd(String insrCd) { this.insrCd = insrCd; }

    public String getInsrProdCd() { return insrProdCd; }
    public void setInsrProdCd(String insrProdCd) { this.insrProdCd = insrProdCd; }

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getClauseInsuranceYn() { return clauseInsuranceYn; }
    public void setClauseInsuranceYn(String clauseInsuranceYn) { this.clauseInsuranceYn = clauseInsuranceYn; }

    public String getClauseInsrProdYn() { return clauseInsrProdYn; }
    public void setClauseInsrProdYn(String clauseInsrProdYn) { this.clauseInsrProdYn = clauseInsrProdYn; }

    public String getInsrAuthInfo() { return insrAuthInfo; }
    public void setInsrAuthInfo(String insrAuthInfo) { this.insrAuthInfo = insrAuthInfo; }
}
