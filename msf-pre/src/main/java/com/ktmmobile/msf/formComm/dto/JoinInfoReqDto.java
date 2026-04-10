package com.ktmmobile.msf.formComm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 가입자정보조회 요청 DTO. ChangeTypeCust.vue → POST /api/v1/join-info.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JoinInfoReqDto {

    /** 고객명 */
    private String name;
    /** 휴대폰번호 (11자리) */
    private String ctn;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }
}
