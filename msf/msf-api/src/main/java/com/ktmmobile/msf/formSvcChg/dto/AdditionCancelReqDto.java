package com.ktmmobile.msf.formSvcChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 부가서비스 해지 요청 DTO. POST /api/v1/addition/cancel.
 * SvcChgInfoDto(name, ncn, ctn, custId) 상속 + soc 추가.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionCancelReqDto extends SvcChgInfoDto {

    /** 부가서비스 코드 */
    private String soc;

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }
}
