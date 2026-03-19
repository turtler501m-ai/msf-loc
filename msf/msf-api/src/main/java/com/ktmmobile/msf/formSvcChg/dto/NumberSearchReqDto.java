package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 번호변경 희망번호 조회 요청 DTO (NU1).
 * ASIS AppformController.searchNumberAjax.do 파라미터와 동일.
 */
public class NumberSearchReqDto extends SvcChgInfoDto {

    /** 희망 번호 패턴 (끝 4자리 등) */
    private String wantNo;

    public String getWantNo() { return wantNo; }
    public void setWantNo(String wantNo) { this.wantNo = wantNo; }
}
