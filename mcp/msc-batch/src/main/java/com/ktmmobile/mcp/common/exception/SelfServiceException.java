package com.ktmmobile.mcp.common.exception;

@SuppressWarnings("serial")
public class SelfServiceException extends RuntimeException {

    private String message; // 21년 4월 이전 사용
    private String resultCode; // 21년 4월 추가
    private String messageNe;   // 21년 4월 추가
    private String globalNo ;

    public SelfServiceException() {
    }

    public SelfServiceException(String s) {
        super(s);
        this.message = s;
    }


    public SelfServiceException(String strCode , String strMessage) {
        super(strCode+";;;"+strMessage);
        this.message = strCode+";;;"+strMessage;
        this.messageNe = strMessage;
        this.resultCode = strCode;
    }

    public SelfServiceException(String strCode , String strMessage , String globalNo) {
        super(strCode+";;;"+strMessage);
        this.message = strCode+";;;"+strMessage;
        this.messageNe = strMessage;
        this.resultCode = strCode;
        this.globalNo = globalNo;
    }

    public String getMessage() {
        return message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getMessageNe() {
        return messageNe;
    }

    public String getGlobalNo() {
        return globalNo;
    }

}
