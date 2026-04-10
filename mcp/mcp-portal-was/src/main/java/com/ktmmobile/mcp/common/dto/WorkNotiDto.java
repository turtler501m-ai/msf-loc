package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;

public class WorkNotiDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String urlSeq;
	private String urlNm;
	private String url;
	private String metaDesc;
	private String metaKywrd;
	private String cntpntCd;
	private String platformCd;
	private String urlDivCd;
	private String loginMustYn;
	private String statLdupYn;
	private String aiRecoShowYn;
	private String floatingShowYn;
	private String floatingTipSbst;
	private String chatbotTipSbst;
	private String sysWorkNotiRegYn;
	private String sysWorkNotiRegDt;
	private String menuSeq;

	//NMCP_MENU_URL_ATRIB_DTL
	private String urlAtribValCd;
	private String atribUseYn;


	/**
	 * @return the urlSeq
	 */
	public String getUrlSeq() {
		return urlSeq;
	}
	/**
	 * @param urlSeq the urlSeq to set
	 */
	public void setUrlSeq(String urlSeq) {
		this.urlSeq = urlSeq;
	}
	/**
	 * @return the urlNm
	 */
	public String getUrlNm() {
		return urlNm;
	}
	/**
	 * @param urlNm the urlNm to set
	 */
	public void setUrlNm(String urlNm) {
		this.urlNm = urlNm;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the metaDesc
	 */
	public String getMetaDesc() {
		return metaDesc;
	}
	/**
	 * @param metaDesc the metaDesc to set
	 */
	public void setMetaDesc(String metaDesc) {
		this.metaDesc = metaDesc;
	}
	/**
	 * @return the metaKywrd
	 */
	public String getMetaKywrd() {
		return metaKywrd;
	}
	/**
	 * @param metaKywrd the metaKywrd to set
	 */
	public void setMetaKywrd(String metaKywrd) {
		this.metaKywrd = metaKywrd;
	}
	/**
	 * @return the cntpntCd
	 */
	public String getCntpntCd() {
		return cntpntCd;
	}
	/**
	 * @param cntpntCd the cntpntCd to set
	 */
	public void setCntpntCd(String cntpntCd) {
		this.cntpntCd = cntpntCd;
	}
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
	 * @return the urlDivCd
	 */
	public String getUrlDivCd() {
		return urlDivCd;
	}
	/**
	 * @param urlDivCd the urlDivCd to set
	 */
	public void setUrlDivCd(String urlDivCd) {
		this.urlDivCd = urlDivCd;
	}
	/**
	 * @return the loginMustYn
	 */
	public String getLoginMustYn() {
		return loginMustYn;
	}
	/**
	 * @param loginMustYn the loginMustYn to set
	 */
	public void setLoginMustYn(String loginMustYn) {
		this.loginMustYn = loginMustYn;
	}
	/**
	 * @return the statLdupYn
	 */
	public String getStatLdupYn() {
		return statLdupYn;
	}
	/**
	 * @param statLdupYn the statLdupYn to set
	 */
	public void setStatLdupYn(String statLdupYn) {
		this.statLdupYn = statLdupYn;
	}

	public String getAiRecoShowYn() {
		return aiRecoShowYn;
	}

	public void setAiRecoShowYn(String aiRecoShowYn) {
		this.aiRecoShowYn = aiRecoShowYn;
	}

	/**
	 * @return the floatingShowYn
	 */
	public String getFloatingShowYn() {
		return floatingShowYn;
	}
	/**
	 * @param floatingShowYn the floatingShowYn to set
	 */
	public void setFloatingShowYn(String floatingShowYn) {
		this.floatingShowYn = floatingShowYn;
	}
	/**
	 * @return the floatingTipSbst
	 */
	public String getFloatingTipSbst() {
		return floatingTipSbst;
	}
	/**
	 * @param floatingTipSbst the floatingTipSbst to set
	 */
	public void setFloatingTipSbst(String floatingTipSbst) {
		this.floatingTipSbst = floatingTipSbst;
	}
	/**
	 * @return the chatbotTipSbst
	 */
	public String getChatbotTipSbst() {
		return chatbotTipSbst;
	}
	/**
	 * @param chatbotTipSbst the chatbotTipSbst to set
	 */
	public void setChatbotTipSbst(String chatbotTipSbst) {
		this.chatbotTipSbst = chatbotTipSbst;
	}
	/**
	 * @return the sysWorkNotiRegYn
	 */
	public String getSysWorkNotiRegYn() {
		return sysWorkNotiRegYn;
	}
	/**
	 * @param sysWorkNotiRegYn the sysWorkNotiRegYn to set
	 */
	public void setSysWorkNotiRegYn(String sysWorkNotiRegYn) {
		this.sysWorkNotiRegYn = sysWorkNotiRegYn;
	}
	/**
	 * @return the sysWorkNotiRegDt
	 */
	public String getSysWorkNotiRegDt() {
		return sysWorkNotiRegDt;
	}
	/**
	 * @param sysWorkNotiRegDt the sysWorkNotiRegDt to set
	 */
	public void setSysWorkNotiRegDt(String sysWorkNotiRegDt) {
		this.sysWorkNotiRegDt = sysWorkNotiRegDt;
	}
	public String getMenuSeq() {
		return menuSeq;
	}
	public void setMenuSeq(String menuSeq) {
		this.menuSeq = menuSeq;
	}
	public String getUrlAtribValCd() {
		return urlAtribValCd;
	}
	public void setUrlAtribValCd(String urlAtribValCd) {
		this.urlAtribValCd = urlAtribValCd;
	}
	public String getAtribUseYn() {
		return atribUseYn;
	}
	public void setAtribUseYn(String atribUseYn) {
		this.atribUseYn = atribUseYn;
	}

}
