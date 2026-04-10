package com.ktmmobile.mcp.common.vo;

import java.io.Serializable;

public class KakaoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uri;
	private String authorizeCode;
	private String clientId;
	private String accessToken;

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getAuthorizeCode() {
		return authorizeCode;
	}
	public void setAuthorizeCode(String authorizeCode) {
		this.authorizeCode = authorizeCode;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "KakaoVo [uri=" + uri + ", authorizeCode=" + authorizeCode + ", clientId=" + clientId
				+ ", accessTokn=" + accessToken + "]";
	}

}
