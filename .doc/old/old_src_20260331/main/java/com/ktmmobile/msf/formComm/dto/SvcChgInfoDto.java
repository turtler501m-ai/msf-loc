package com.ktmmobile.msf.formComm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 서비스변경 업무 API 공통 요청 바디. 각 업무 ReqDto 의 베이스 클래스로 사용.
 * name, ncn, ctn, custId 를 공통 필드로 가짐.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SvcChgInfoDto {

    /** 고객명 */
    private String name;
    /** 서비스계약번호 (9자리) */
    private String ncn;
    /** 전화번호 (11자리) */
    private String ctn;
    /** 고객ID */
    private String custId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNcn() { return ncn; }
    public void setNcn(String ncn) { this.ncn = ncn; }

    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
}
