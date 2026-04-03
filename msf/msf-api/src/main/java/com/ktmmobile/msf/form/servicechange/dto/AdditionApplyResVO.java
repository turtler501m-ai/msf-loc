package com.ktmmobile.msf.form.servicechange.dto;

public class AdditionApplyResVO {

    private boolean success;
    private String message;

    public AdditionApplyResVO() {}

    public AdditionApplyResVO(boolean success) {
        this.success = success;
    }

    public AdditionApplyResVO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
