package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * USIM 변경 요청 DTO (UC0).
 * ASIS usimChangeUC0() 호출 파라미터.
 */
public class UsimChangeReqDto extends SvcChgInfoDto {

    /** 새 USIM 번호 (ICCID) */
    private String newUsimNo;

    /** 현재 USIM 번호 */
    private String currentUsimNo;

    public String getNewUsimNo() { return newUsimNo; }
    public void setNewUsimNo(String newUsimNo) { this.newUsimNo = newUsimNo; }

    public String getCurrentUsimNo() { return currentUsimNo; }
    public void setCurrentUsimNo(String currentUsimNo) { this.currentUsimNo = currentUsimNo; }
}
