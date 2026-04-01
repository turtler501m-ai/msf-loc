package com.ktmmobile.msf.formSvcChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 데이터쉐어링 요청 DTO.
 * ASIS MyShareDataReqDto 와 동일 구조 (stateless 기준 필드만).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SvcChgShareDataReqDto extends SvcChgInfoDto {

    /** 데이터쉐어링 대상 전화번호 (가입/해지 대상 서비스번호). ASIS opmdSvcNo */
    private String opmdSvcNo;
    /** 처리구분코드: A=가입(결합), C=해지. ASIS opmdWorkDivCd */
    private String opmdWorkDivCd;
    /** 셀프개통 사전체크 여부 (Y/N). ASIS selfShareYn */
    private String selfShareYn;
    /** X69 사전체크 대상 전화번호. ASIS crprCtn */
    private String crprCtn;

    public String getOpmdSvcNo() { return opmdSvcNo; }
    public void setOpmdSvcNo(String opmdSvcNo) { this.opmdSvcNo = opmdSvcNo; }
    public String getOpmdWorkDivCd() { return opmdWorkDivCd; }
    public void setOpmdWorkDivCd(String opmdWorkDivCd) { this.opmdWorkDivCd = opmdWorkDivCd; }
    public String getSelfShareYn() { return selfShareYn; }
    public void setSelfShareYn(String selfShareYn) { this.selfShareYn = selfShareYn; }
    public String getCrprCtn() { return crprCtn; }
    public void setCrprCtn(String crprCtn) { this.crprCtn = crprCtn; }
}
