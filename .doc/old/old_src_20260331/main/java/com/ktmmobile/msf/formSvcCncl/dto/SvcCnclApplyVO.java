package com.ktmmobile.msf.formSvcCncl.dto;

/**
 * 서비스해지 신청서 등록 응답 VO.
 */
public class SvcCnclApplyVO {

    private boolean success;
    private String applicationNo;
    private String message;

    public static SvcCnclApplyVO ok(String applicationNo) {
        SvcCnclApplyVO vo = new SvcCnclApplyVO();
        vo.success = true;
        vo.applicationNo = applicationNo;
        vo.message = "";
        return vo;
    }

    public static SvcCnclApplyVO fail(String message) {
        SvcCnclApplyVO vo = new SvcCnclApplyVO();
        vo.success = false;
        vo.message = message;
        return vo;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getApplicationNo() { return applicationNo; }
    public void setApplicationNo(String applicationNo) { this.applicationNo = applicationNo; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
