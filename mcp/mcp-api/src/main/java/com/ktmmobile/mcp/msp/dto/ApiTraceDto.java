package com.ktmmobile.mcp.msp.dto;

import java.io.Serializable;

public class ApiTraceDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private String trSeq;
	private String apiType;
	private String apiDtl;
	private String reqUrl;
	private String reqParam;
	private String sessionUserId;
	private String resCd;
	private String resMsg;
	private String resStr;

	public String getTrSeq() {
		return trSeq;
	}

	public void setTrSeq(String trSeq) {
		this.trSeq = trSeq;
	}

	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	public String getApiDtl() {
		return apiDtl;
	}

	public void setApiDtl(String apiDtl) {
		this.apiDtl = apiDtl;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getReqParam() {
		return reqParam;
	}

	public void setReqParam(String reqParam) {
		this.reqParam = reqParam;
	}

	public String getSessionUserId() {
		return sessionUserId;
	}

	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}

	public String getResCd() {
		return resCd;
	}

	public void setResCd(String resCd) {
		this.resCd = resCd;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getResStr() {
		return resStr;
	}

	public void setResStr(String resStr) {
		this.resStr = resStr;
	}
}
