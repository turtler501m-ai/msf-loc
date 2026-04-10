package com.ktmmobile.mcp.personal.dto;

import java.io.Serializable;

public class PersonalBillDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String billTypeCd;
	private String billAdInfo;
	private String ncn;
	private String zip;
	private String addr1;
	private String addr2;

	public String getBillTypeCd() {
		return billTypeCd;
	}

	public void setBillTypeCd(String billTypeCd) {
		this.billTypeCd = billTypeCd;
	}

	public String getBillAdInfo() {
		return billAdInfo;
	}

	public void setBillAdInfo(String billAdInfo) {
		this.billAdInfo = billAdInfo;
	}

	public String getNcn() {
		return ncn;
	}

	public void setNcn(String ncn) {
		this.ncn = ncn;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

}
