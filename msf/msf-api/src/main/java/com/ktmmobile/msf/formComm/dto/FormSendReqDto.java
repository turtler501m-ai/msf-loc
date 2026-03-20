package com.ktmmobile.msf.formComm.dto;

/**
 * 서식지 SCAN 전송 요청 DTO.
 * ASIS: CustRequestScanServiceImpl.prodSendScan(custReqKey, cretId, reqType)
 */
public class FormSendReqDto {

    /** 고객요청 시퀀스 (MSF_CUST_REQUEST_MST.CUST_REQ_SEQ) */
    private Long custReqSeq;

    /**
     * 요청 유형.
     * NC: 명의변경, CC: 해지상담, IS: 안심보험
     */
    private String reqType;

    /** 처리자 ID */
    private String userId;

    public Long getCustReqSeq() { return custReqSeq; }
    public void setCustReqSeq(Long custReqSeq) { this.custReqSeq = custReqSeq; }

    public String getReqType() { return reqType; }
    public void setReqType(String reqType) { this.reqType = reqType; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
