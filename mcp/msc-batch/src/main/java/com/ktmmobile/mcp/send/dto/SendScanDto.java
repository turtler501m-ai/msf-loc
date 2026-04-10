package com.ktmmobile.mcp.send.dto;

import java.io.Serializable;



/**
 * @author key
 *
 */
public class SendScanDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String requestKey;

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

}
