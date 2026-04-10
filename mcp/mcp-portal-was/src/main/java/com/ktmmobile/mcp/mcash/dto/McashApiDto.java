package com.ktmmobile.mcp.mcash.dto;

import java.io.Serializable;

public class McashApiDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userid;          // 포탈ID
    private String customerId;      // 고객ID
    private String contractNum;     // 계약번호
    private String svcCntrNo;       // 서비스계약번호
    private String evntType;        // 연동 유형
    private String evntTypeDtl;     // 연동 유형 상세

    public McashApiDto() {
    }

    public McashApiDto(McashUserDto mcashUserDto) {
        this.userid = mcashUserDto.getUserid();
        this.customerId = mcashUserDto.getCustomerId();
        this.contractNum = mcashUserDto.getContractNum();
        this.svcCntrNo = mcashUserDto.getSvcCntrNo();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSvcCntrNo() {
        return svcCntrNo;
    }

    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }

    public String getEvntType() {
        return evntType;
    }

    public void setEvntType(String evntType) {
        this.evntType = evntType;
    }

    public String getEvntTypeDtl() {
        return evntTypeDtl;
    }

    public void setEvntTypeDtl(String evntTypeDtl) {
        this.evntTypeDtl = evntTypeDtl;
    }
}




