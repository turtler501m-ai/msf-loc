package com.ktmmobile.msf.common.exception;


/**
 * @Class Name : McpCommonException
 * @Description :
 *   MCP Maplat Form 예외 클래스
 * @author : ant
 * @Create Date : 2015. 12. 30.
 */
public class McpMplatFormException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /** 에러메세지 */
    private String errorMsg;

    /** redirect 할 URL */
    private String redirectUrl;

    public McpMplatFormException() {

    }
    public McpMplatFormException(Throwable cause) {
        super(cause);
    }

    public McpMplatFormException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public McpMplatFormException(String errorMsg,String redirectUrl) {
        this.errorMsg = errorMsg;
        this.redirectUrl = redirectUrl;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getRedirectUrl() {
        return this.redirectUrl;
    }
}
