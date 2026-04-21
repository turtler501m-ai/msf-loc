package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class BannAccessTxnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bannAccessSeq;

	private long bannAccessSeqLong;

    private int bannSeq;
    private String accessDate;
    private String accessTime;
    private String platformCd;
    private String bannCtg;
    private int menuSeq;
    private int urlSeq;
    private String reqTrtCd;
    private String userId;
    private String accessIp;

	public long getBannAccessSeqLong() {
		return bannAccessSeqLong;
	}

	public void setBannAccessSeqLong(long bannAccessSeqLong) {
		this.bannAccessSeqLong = bannAccessSeqLong;
	}

	/**
	 * @return the bannAccessSeq
	 */
	public int getBannAccessSeq() {
		return bannAccessSeq;
	}
	/**
	 * @param bannAccessSeq the bannAccessSeq to set
	 */
	public void setBannAccessSeq(int bannAccessSeq) {
		this.bannAccessSeq = bannAccessSeq;
	}
	/**
	 * @return the bannSeq
	 */
	public int getBannSeq() {
		return bannSeq;
	}
	/**
	 * @param bannSeq the bannSeq to set
	 */
	public void setBannSeq(int bannSeq) {
		this.bannSeq = bannSeq;
	}
	/**
	 * @return the accessDate
	 */
	public String getAccessDate() {
		return accessDate;
	}
	/**
	 * @param accessDate the accessDate to set
	 */
	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}
	/**
	 * @return the accessTime
	 */
	public String getAccessTime() {
		return accessTime;
	}
	/**
	 * @param accessTime the accessTime to set
	 */
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
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
	 * @return the menuSeq
	 */
	public int getMenuSeq() {
		return menuSeq;
	}
	/**
	 * @param menuSeq the menuSeq to set
	 */
	public void setMenuSeq(int menuSeq) {
		this.menuSeq = menuSeq;
	}
	/**
	 * @return the urlSeq
	 */
	public int getUrlSeq() {
		return urlSeq;
	}
	/**
	 * @param urlSeq the urlSeq to set
	 */
	public void setUrlSeq(int urlSeq) {
		this.urlSeq = urlSeq;
	}
	/**
	 * @return the reqTrtCd
	 */
	public String getReqTrtCd() {
		return reqTrtCd;
	}
	/**
	 * @param reqTrtCd the reqTrtCd to set
	 */
	public void setReqTrtCd(String reqTrtCd) {
		this.reqTrtCd = reqTrtCd;
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
	 * @return the accessIp
	 */
	public String getAccessIp() {
		return accessIp;
	}
	/**
	 * @param accessIp the accessIp to set
	 */
	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}
	public String getBannCtg() {
		return bannCtg;
	}
	public void setBannCtg(String bannCtg) {
		this.bannCtg = bannCtg;
	}

}
