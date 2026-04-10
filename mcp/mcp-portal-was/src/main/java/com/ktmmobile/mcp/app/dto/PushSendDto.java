package com.ktmmobile.mcp.app.dto;

import java.io.Serializable;


public class PushSendDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pushSndSetSno;
    private String userId;
    private String[] msgArr;


	public String getPushSndSetSno() {
		return pushSndSetSno;
	}

	public void setPushSndSetSno(String pushSndSetSno) {
		this.pushSndSetSno = pushSndSetSno;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getMsgArr() {
		return msgArr;
	}

	public void setMsgArr(String[] msgArr) {
		this.msgArr = msgArr;
	}

}
