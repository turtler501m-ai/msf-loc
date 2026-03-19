package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 희망번호 예약/취소 요청 DTO (NU2).
 * ASIS AppformController.setNumberAjax.do / cancelNumberAjax.do 파라미터.
 */
public class NumberReserveReqDto extends SvcChgInfoDto {

    /** 예약할 전화번호 */
    private String tlphNo;

    public String getTlphNo() { return tlphNo; }
    public void setTlphNo(String tlphNo) { this.tlphNo = tlphNo; }
}
