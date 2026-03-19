package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 번호변경 신청 요청 DTO (X32).
 * ASIS MplatFormService.numChgeChg() 호출 파라미터.
 */
public class NumberChangeReqDto extends SvcChgInfoDto {

    /** 변경할 새 전화번호 */
    private String newTlphNo;

    /** 예약된 번호 (NU2로 예약한 번호) */
    private String reservedTlphNo;

    public String getNewTlphNo() { return newTlphNo; }
    public void setNewTlphNo(String newTlphNo) { this.newTlphNo = newTlphNo; }

    public String getReservedTlphNo() { return reservedTlphNo; }
    public void setReservedTlphNo(String reservedTlphNo) { this.reservedTlphNo = reservedTlphNo; }
}
