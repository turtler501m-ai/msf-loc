package com.ktmmobile.mcp.common.exception;


/**
 * @Class Name : McpCommonException
 * @Description :
 *   MCP 서비스 공통 예외 클래스
 * @author : ant
 * @Create Date : 2015. 12. 30.
 */
public class McpErropPageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /** 에러메세지 */
    private String errorMsg;

    /** redirect 할 URL */
    private String redirectUrl;

    public McpErropPageException() {

    }
    public McpErropPageException(Throwable cause) {
        super(cause);
    }

    public McpErropPageException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public McpErropPageException(String errorMsg,String redirectUrl) {
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
