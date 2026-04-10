package com.ktmmobile.mcp.common.exception;

/**
 * @Description : MCP 서비스 공통 예외 클래스
 */
public class McpCommonJsonException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	// 추가, 2021.09.27
	private String statusCode;
	private String message;
	private long timestamp;
	
	public McpCommonJsonException() {
    }

    public McpCommonJsonException(String message) {
        this.message = message;
    }

    public McpCommonJsonException(String statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }
    
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}	
}
