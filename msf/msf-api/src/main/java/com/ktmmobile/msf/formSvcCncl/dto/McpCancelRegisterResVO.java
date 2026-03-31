package com.ktmmobile.msf.formSvcCncl.dto;

/**
 * M포탈 서비스해지 적재 API 응답 VO.
 * 성공 시 custReqSeq를 MSF_REQUEST_CANCEL.RES_NO에 저장.
 */
public class McpCancelRegisterResVO {

    private boolean success;
    /** M포탈 고객요청 시퀀스 — MSF_REQUEST_CANCEL.RES_NO 저장용 */
    private String custReqSeq;
    private String message;

    public static McpCancelRegisterResVO ok(String custReqSeq) {
        McpCancelRegisterResVO v = new McpCancelRegisterResVO();
        v.success    = true;
        v.custReqSeq = custReqSeq;
        v.message    = "";
        return v;
    }

    public static McpCancelRegisterResVO fail(String message) {
        McpCancelRegisterResVO v = new McpCancelRegisterResVO();
        v.success    = false;
        v.custReqSeq = null;
        v.message    = message;
        return v;
    }

    public boolean isSuccess() { return success; }
    public String getCustReqSeq() { return custReqSeq; }
    public String getMessage() { return message; }
}
