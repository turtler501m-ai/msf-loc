package com.ktis.msp.base;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BaseVo {

	/** login usr_id */
	private String sessionUserId = "";

	/** login usr_name */
	private String sessionUserName = "";

	/** user maskingt */
	private String sessionUserMasking = "";

	/** user orgn_id */
	private String sessionUserOrgnId = "";

	/** user orgn_nm */
	private String sessionUserOrgnNm = "";

	/** MENU ID */
	private String sessionMenuId = "";

	/** MNGR_RULE */
	private String sessionUserMngrRule = "";

	/** MNGR_RULE */
	private String sessionTypeDtlCd1 = "";

	/** user maskingt */
	private String sessionUserMskAuthYn = "";
	
	/** user orgn_type_cd */
	private String sessionUserOrgnTypeCd = "";
	
	/** user orgn_type_cd */
	private String sessionUserLogisCnterYn = "";
	
	/** user orgn_type_cd */
	private String sessionUserOrgnLvlCd = "";
	
	
	/**
	 * @return the sessionTypeDtlCd1
	 */
	public String getSessionTypeDtlCd1() {
		return sessionTypeDtlCd1;
	}

	/**
	 * @param sessionTypeDtlCd1
	 *            the sessionTypeDtlCd1 to set
	 */
	public void setSessionTypeDtlCd1(String sessionTypeDtlCd1) {
		this.sessionTypeDtlCd1 = sessionTypeDtlCd1;
	}

	public String getSessionUserMngrRule() {
		return sessionUserMngrRule;
	}

	public void setSessionUserMngrRule(String sessionUserMngrRule) {
		this.sessionUserMngrRule = sessionUserMngrRule;
	}

	public String getSessionMenuId() {
		return sessionMenuId;
	}

	public void setSessionMenuId(String sessionMenuId) {
		this.sessionMenuId = sessionMenuId;
	}

	public String getSessionUserOrgnId() {
		return sessionUserOrgnId;
	}

	public void setSessionUserOrgnId(String sessionUserOrgnId) {
		this.sessionUserOrgnId = sessionUserOrgnId;
	}

	public String getSessionUserOrgnNm() {
		return sessionUserOrgnNm;
	}

	public void setSessionUserOrgnNm(String sessionUserOrgnNm) {
		this.sessionUserOrgnNm = sessionUserOrgnNm;
	}

	public String getSessionUserOrgnTypeCd() {
		return sessionUserOrgnTypeCd;
	}

	public void setSessionUserOrgnTypeCd(String sessionUserOrgnTypeCd) {
		this.sessionUserOrgnTypeCd = sessionUserOrgnTypeCd;
	}

	public String getSessionUserOrgnLvlCd() {
		return sessionUserOrgnLvlCd;
	}

	public void setSessionUserOrgnLvlCd(String sessionUserOrgnLvlCd) {
		this.sessionUserOrgnLvlCd = sessionUserOrgnLvlCd;
	}

	public String getSessionUserLogisCnterYn() {
		return sessionUserLogisCnterYn;
	}

	public void setSessionUserLogisCnterYn(String sessionUserLogisCnterYn) {
		this.sessionUserLogisCnterYn = sessionUserLogisCnterYn;
	}

	public String getSessionUserMasking() {
		return sessionUserMasking;
	}

	public void setSessionUserMasking(String sessionUserMasking) {
		this.sessionUserMasking = sessionUserMasking;
	}

	public String getSessionUserMskAuthYn() {
		return sessionUserMskAuthYn;
	}

	public void setSessionUserMskAuthYn(String sessionUserMskAuthYn) {
		this.sessionUserMskAuthYn = sessionUserMskAuthYn;
	}

	public String getSessionUserId() {
		return sessionUserId;
	}

	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getSessionUserName() {
		return sessionUserName;
	}

	public void setSessionUserName(String sessionUserName) {
		this.sessionUserName = sessionUserName;
	}

}
