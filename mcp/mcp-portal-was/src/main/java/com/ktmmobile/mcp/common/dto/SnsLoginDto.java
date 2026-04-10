package com.ktmmobile.mcp.common.dto;

public class SnsLoginDto {

	private long snsLoginSeq;
	private String userId;
	private String snsCd;
	private String snsId;
	private String loginDt;
	private int loginCnt;
	private int loginFailCnt;
	private String cretDt;
	private String amdDt;
	
	
	public long getSnsLoginSeq() {
		return snsLoginSeq;
	}
	public void setSnsLoginSeq(long snsLoginSeq) {
		this.snsLoginSeq = snsLoginSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSnsCd() {
		return snsCd;
	}
	public void setSnsCd(String snsCd) {
		this.snsCd = snsCd;
	}
	public String getSnsId() {
		return snsId;
	}
	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}
	public String getLoginDt() {
		return loginDt;
	}
	public void setLoginDt(String loginDt) {
		this.loginDt = loginDt;
	}
	public int getLoginCnt() {
		return loginCnt;
	}
	public void setLoginCnt(int loginCnt) {
		this.loginCnt = loginCnt;
	}
	public int getLoginFailCnt() {
		return loginFailCnt;
	}
	public void setLoginFailCnt(int loginFailCnt) {
		this.loginFailCnt = loginFailCnt;
	}
	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	public String getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}
	
	
}
