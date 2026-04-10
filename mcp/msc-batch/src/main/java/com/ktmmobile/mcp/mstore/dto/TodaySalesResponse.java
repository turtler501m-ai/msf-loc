package com.ktmmobile.mcp.mstore.dto;

public class TodaySalesResponse {
    private String resultCode;
    private String failMessage;
    private TodaySalesMessage message;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public TodaySalesMessage getMessage() {
        return message;
    }

    public void setMessage(TodaySalesMessage message) {
        this.message = message;
    }
}
