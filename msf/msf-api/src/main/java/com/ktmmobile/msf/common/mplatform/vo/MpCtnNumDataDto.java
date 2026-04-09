package com.ktmmobile.msf.common.mplatform.vo;


public class MpCtnNumDataDto {
	private String tel;//전화번호        ? 암호화
	private String linkName;//고객명    ? 암호화
	private String socDesc;//요금상품명
	private String currCharge;//당월 요금계
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getSocDesc() {
		return socDesc;
	}
	public void setSocDesc(String socDesc) {
		this.socDesc = socDesc;
	}
	public String getCurrCharge() {
		return currCharge;
	}
	public void setCurrCharge(String currCharge) {
		this.currCharge = currCharge;
	}
}


