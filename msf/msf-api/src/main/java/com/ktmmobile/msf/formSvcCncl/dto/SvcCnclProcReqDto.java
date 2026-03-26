package com.ktmmobile.msf.formSvcCncl.dto;

/**
 * MSP 처리완료 통보 요청 DTO.
 * POST /api/v1/cancel/proc
 * MSP가 해지 처리 완료 후 MSF_REQUEST_CANCEL.PROC_CD 갱신용으로 호출.
 */
public class SvcCnclProcReqDto {

    /** M포탈 custReqSeq (= MSF_REQUEST_CANCEL.RES_NO) */
    private String custReqSeq;

    /** 처리코드 — CP(처리완료), BK(취소) */
    private String procCd;

    public String getCustReqSeq() { return custReqSeq; }
    public void setCustReqSeq(String custReqSeq) { this.custReqSeq = custReqSeq; }
    public String getProcCd() { return procCd; }
    public void setProcCd(String procCd) { this.procCd = procCd; }
}
