package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 데이터쉐어링 요청 DTO (X69/X70/X71 공용).
 * join/cancel 시 opmdSvcNo 필수.
 */
public class SvcChgShareDataReqDto extends SvcChgInfoDto {

    /** 데이터쉐어링 서비스번호 (X70 가입/해지 시 필수). */
    private String opmdSvcNo;

    /** 처리 구분: A(가입), C(해지) — X70 opmdWorkDivCd. */
    private String workDivCd;

    public String getOpmdSvcNo() { return opmdSvcNo; }
    public void setOpmdSvcNo(String opmdSvcNo) { this.opmdSvcNo = opmdSvcNo; }

    public String getWorkDivCd() { return workDivCd; }
    public void setWorkDivCd(String workDivCd) { this.workDivCd = workDivCd; }
}
