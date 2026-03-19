package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * USIM 유효성 체크 요청 DTO (X85).
 * ASIS AppformController.moscIntmMgmt() 파라미터.
 */
public class UsimCheckReqDto extends SvcChgInfoDto {

    /** USIM 번호 (ICCID) */
    private String usimNo;

    public String getUsimNo() { return usimNo; }
    public void setUsimNo(String usimNo) { this.usimNo = usimNo; }
}
