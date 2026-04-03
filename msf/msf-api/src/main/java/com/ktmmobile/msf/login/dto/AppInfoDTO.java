package com.ktmmobile.msf.login.dto;

import java.io.Serializable;


public class AppInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private String userId;
	private String udid;
	private String os;
	private String version;
	private String sendYn;
	private String appOsVer;
	private String appNm;
	private String lastAccessDt;
	private int badgeCnt = 0;
	private String simpleLoginYn; //AUTO_LOGIN_YN
	private String bioLoginYn;
	private String wdgtUseQntyCallCyclCd;
	private String cretId;
	private String upId;

	private String ncn;

	private String token;
	private String tokenRegDt;
	private String tokenValidPeriod;
	private String updateFlag = "Y";
	private String sendYnReferer; 	// 푸쉬팝업 업데이트 경로
	private String sendYnUpDt;		// 푸쉬팝업 업데이트 날짜
	private String appHstSeq;		// 앱정보이력시퀀스

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSendYn() {
		return sendYn;
	}
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}
	public String getAppOsVer() {
		return appOsVer;
	}
	public void setAppOsVer(String appOsVer) {
		this.appOsVer = appOsVer;
	}
	public String getAppNm() {
		return appNm;
	}
	public void setAppNm(String appNm) {
		this.appNm = appNm;
	}
	public String getLastAccessDt() {
		return lastAccessDt;
	}
	public void setLastAccessDt(String lastAccessDt) {
		this.lastAccessDt = lastAccessDt;
	}
	public int getBadgeCnt() {
		return badgeCnt;
	}
	public void setBadgeCnt(int badgeCnt) {
		this.badgeCnt = badgeCnt;
	}
	public String getBioLoginYn() {
		return bioLoginYn;
	}
	public void setBioLoginYn(String bioLoginYn) {
		this.bioLoginYn = bioLoginYn;
	}
	public String getWdgtUseQntyCallCyclCd() {
		return wdgtUseQntyCallCyclCd;
	}
	public void setWdgtUseQntyCallCyclCd(String wdgtUseQntyCallCyclCd) {
		this.wdgtUseQntyCallCyclCd = wdgtUseQntyCallCyclCd;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getUpId() {
		return upId;
	}
	public void setUpId(String upId) {
		this.upId = upId;
	}
	public String getNcn() {
		return ncn;
	}
	public void setNcn(String ncn) {
		this.ncn = ncn;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenRegDt() {
		return tokenRegDt;
	}
	public void setTokenRegDt(String tokenRegDt) {
		this.tokenRegDt = tokenRegDt;
	}
	public String getTokenValidPeriod() {
		return tokenValidPeriod;
	}
	public void setTokenValidPeriod(String tokenValidPeriod) {
		this.tokenValidPeriod = tokenValidPeriod;
	}
	public String getSimpleLoginYn() {
		return simpleLoginYn;
	}
	public void setSimpleLoginYn(String simpleLoginYn) {
		this.simpleLoginYn = simpleLoginYn;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getSendYnReferer() {
		return sendYnReferer;
	}

	public void setSendYnReferer(String sendYnReferer) {
		this.sendYnReferer = sendYnReferer;
	}

	public String getSendYnUpDt() {
		return sendYnUpDt;
	}

	public void setSendYnUpDt(String sendYnUpDt) {
		this.sendYnUpDt = sendYnUpDt;
	}

	public String getAppHstSeq() {
		return appHstSeq;
	}

	public void setAppHstSeq(String appHstSeq) {
		this.appHstSeq = appHstSeq;
	}
}
