package com.ktmmobile.mcp.push.dto;

import java.io.Serializable;


public class PushSendDataDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pushSeq;
    private String pushToken;
    private String pushTitle;
    private String pushMsg;
    private String pushImage;
    private String pushLink;

    private String pushSendResult;



	public String getPushSeq() {
		return pushSeq;
	}
	public void setPushSeq(String pushSeq) {
		this.pushSeq = pushSeq;
	}
	public String getPushToken() {
		return pushToken;
	}
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}
	public String getPushTitle() {
		return pushTitle;
	}
	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}
	public String getPushMsg() {
		return pushMsg;
	}
	public void setPushMsg(String pushMsg) {
		this.pushMsg = pushMsg;
	}
	public String getPushImage() {
		return pushImage;
	}
	public void setPushImage(String pushImage) {
		this.pushImage = pushImage;
	}
	public String getPushLink() {
		return pushLink;
	}
	public void setPushLink(String pushLink) {
		this.pushLink = pushLink;
	}
	public String getPushSendResult() {
		return pushSendResult;
	}
	public void setPushSendResult(String pushSendResult) {
		this.pushSendResult = pushSendResult;
	}

}
