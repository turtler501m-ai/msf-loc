package com.ktmmobile.mcp.common.exception;

@SuppressWarnings("serial")
public class NullSessionException extends RuntimeException {

	public NullSessionException() {
	}

	public NullSessionException(String s) {
		super(s);
	}
	
}