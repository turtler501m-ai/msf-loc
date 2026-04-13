package com.ktmmobile.msf.domains.form.form.termination.dto;

public class TerminationApplyResVO {

    private boolean success;
    private String applicationNo;
    private String message;

    public static TerminationApplyResVO ok(String applicationNo) {
        TerminationApplyResVO vo = new TerminationApplyResVO();
        vo.success = true;
        vo.applicationNo = applicationNo;
        vo.message = "";
        return vo;
    }

    public static TerminationApplyResVO fail(String message) {
        TerminationApplyResVO vo = new TerminationApplyResVO();
        vo.success = false;
        vo.message = message;
        return vo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
