package com.ktmmobile.mcp.appform.dto;

import java.io.Serializable;

//@Data
public class AppFormDto implements Serializable {

    private String requestKey;
    private String resNo;
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
    
}
