package com.ktmmobile.msf.formSvcCncl.dto;

/**
 * [배치] 해지 완료 동기화 대상 항목 DTO.
 * SvcCnclMapper.selectPendingCancelList() 결과 매핑.
 */
public class SvcCnclSyncItemDto {

    /** 가입신청키 (PK) */
    private Long requestKey;
    /** 해지 휴대폰번호 (숫자만) */
    private String cancelMobileNo;
    /** 계약번호 (NCN) */
    private String contractNum;
    /** 현재 처리코드 (RC=접수) */
    private String procCd;
    /** M포탈 custReqSeq (RES_NO) — null 가능 */
    private String mcpCustReqSeq;

    public Long getRequestKey() { return requestKey; }
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }

    public String getCancelMobileNo() { return cancelMobileNo; }
    public void setCancelMobileNo(String cancelMobileNo) { this.cancelMobileNo = cancelMobileNo; }

    public String getContractNum() { return contractNum; }
    public void setContractNum(String contractNum) { this.contractNum = contractNum; }

    public String getProcCd() { return procCd; }
    public void setProcCd(String procCd) { this.procCd = procCd; }

    public String getMcpCustReqSeq() { return mcpCustReqSeq; }
    public void setMcpCustReqSeq(String mcpCustReqSeq) { this.mcpCustReqSeq = mcpCustReqSeq; }
}
