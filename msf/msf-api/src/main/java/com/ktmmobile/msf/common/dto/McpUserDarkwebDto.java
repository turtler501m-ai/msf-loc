package com.ktmmobile.msf.common.dto;

import java.io.Serializable;

public class McpUserDarkwebDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String regDate;
	private String chgDate;
	private String pwUpdateYn; // PW변경여부 DEFAULT N
	private String regDt;
	private String chgDt;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getChgDate() {
		return chgDate;
	}
	public void setChgDate(String chgDate) {
		this.chgDate = chgDate;
	}
	public String getPwUpdateYn() {
		return pwUpdateYn;
	}
	public void setPwUpdateYn(String pwUpdateYn) {
		this.pwUpdateYn = pwUpdateYn;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getChgDt() {
		return chgDt;
	}
	public void setChgDt(String chgDt) {
		this.chgDt = chgDt;
	}




}
