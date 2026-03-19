package com.ktmmobile.msf.formOwnChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 양도인 신청가능여부 체크 요청 (AS-IS grantorReqChk 대응).
 * 계약번호(또는 ncn) 기준으로 M전산 연동 예정.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnChgGrantorReqChkReqDto {

    /** 계약번호 (ncn) */
    private String contractNum;

    public String getContractNum() { return contractNum; }
    public void setContractNum(String contractNum) { this.contractNum = contractNum; }
}
