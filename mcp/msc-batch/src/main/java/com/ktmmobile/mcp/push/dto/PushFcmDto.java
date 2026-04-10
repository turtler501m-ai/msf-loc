package com.ktmmobile.mcp.push.dto;

import java.io.Serializable;

public class PushFcmDto implements Serializable{

	private static final long serialVersionUID = 4212601479589882460L;

	private String pushSendData;


	public String getPushSendData() {
		return pushSendData;
	}
	public void setPushSendData(String pushSendData) {
		this.pushSendData = pushSendData;
	}

}