package com.ktmmobile.msf.system.common.exception;


/**
 * @Class Name : McpCommonException
 * @Description :
 *   MCP 서비스 공통 예외 클래스
 * @author : ant
 * @Create Date : 2015. 12. 30.
 */
public class McpCommonJsonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /** 에러메세지 */
    private String errorMsg;


    private String rtnCode;



    public McpCommonJsonException() {

    }
    public McpCommonJsonException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public McpCommonJsonException(String rtnCode,String errorMsg) {
        this.errorMsg = errorMsg;
        this.rtnCode = rtnCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getRtnCode() {
        return this.rtnCode;
    }


}
