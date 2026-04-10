package com.ktmmobile.mcp.personal.dto;

import java.io.Serializable;

public class PersonalAddDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ncn;
	private String soc;
	private String ftrNewParam;
	private String couponPrice;

	public String getNcn() {
		return ncn;
	}

	public void setNcn(String ncn) {
		this.ncn = ncn;
	}

	public String getSoc() {
		return soc;
	}

	public void setSoc(String soc) {
		this.soc = soc;
	}

	public String getFtrNewParam() {
		return ftrNewParam;
	}

	public void setFtrNewParam(String ftrNewParam) {
		this.ftrNewParam = ftrNewParam;
	}

	public String getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}

}
