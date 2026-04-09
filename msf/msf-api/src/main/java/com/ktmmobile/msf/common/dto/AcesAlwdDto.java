package com.ktmmobile.msf.common.dto;

import java.io.Serializable;

public class AcesAlwdDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String acesAlwdIp;
	private String nm;
	private String mobileNo;

	public String getAcesAlwdIp() {
		return acesAlwdIp;
	}
	public void setAcesAlwdIp(String acesAlwdIp) {
		this.acesAlwdIp = acesAlwdIp;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

}
