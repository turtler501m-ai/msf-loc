package com.ktmmobile.msf.formComm.dto;

/**
 * 서식지 SCAN 전송 응답 VO.
 */
public class FormSendResVO {

    private boolean success;
    private String scanId;
    private String message;

    public static FormSendResVO ok(String scanId) {
        FormSendResVO vo = new FormSendResVO();
        vo.success = true;
        vo.scanId  = scanId;
        vo.message = "";
        return vo;
    }

    public static FormSendResVO fail(String message) {
        FormSendResVO vo = new FormSendResVO();
        vo.success = false;
        vo.scanId  = null;
        vo.message = message;
        return vo;
    }

    public boolean isSuccess() { return success; }
    public String getScanId()  { return scanId; }
    public String getMessage() { return message; }
}
