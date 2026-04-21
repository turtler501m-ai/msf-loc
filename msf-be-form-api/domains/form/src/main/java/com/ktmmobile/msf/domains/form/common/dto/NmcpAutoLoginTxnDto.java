package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class NmcpAutoLoginTxnDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int autoLoginSeq;
    private String platformCd;
    private String userId;
    private String loginDivCd;
    private String token;
    private String tokenRegDt;
    private String tokenValidPeriod;
//    private String cretDt;
//    private String amdDt;


	/**
	 * @return the platformCd
	 */
	public String getPlatformCd() {
		return platformCd;
	}
	/**
	 * @param platformCd the platformCd to set
	 */
	public void setPlatformCd(String platformCd) {
		this.platformCd = platformCd;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the loginDivCd
	 */
	public String getLoginDivCd() {
		return loginDivCd;
	}
	/**
	 * @param loginDivCd the loginDivCd to set
	 */
	public void setLoginDivCd(String loginDivCd) {
		this.loginDivCd = loginDivCd;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the tokenRegDt
	 */
	public String getTokenRegDt() {
		return tokenRegDt;
	}
	/**
	 * @param tokenRegDt the tokenRegDt to set
	 */
	public void setTokenRegDt(String tokenRegDt) {
		this.tokenRegDt = tokenRegDt;
	}
	/**
	 * @return the tokenValidPeriod
	 */
	public String getTokenValidPeriod() {
		return tokenValidPeriod;
	}
	/**
	 * @param tokenValidPeriod the tokenValidPeriod to set
	 */
	public void setTokenValidPeriod(String tokenValidPeriod) {
		this.tokenValidPeriod = tokenValidPeriod;
	}
	public int getAutoLoginSeq() {
		return autoLoginSeq;
	}
	public void setAutoLoginSeq(int autoLoginSeq) {
		this.autoLoginSeq = autoLoginSeq;
	}

}