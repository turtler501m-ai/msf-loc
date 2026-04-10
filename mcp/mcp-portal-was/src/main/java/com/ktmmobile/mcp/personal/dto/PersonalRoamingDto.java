package com.ktmmobile.mcp.personal.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonalRoamingDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ncn;
	private String soc;
	private String strtDt;
	private String strtTm;
	private String endDt;
	private String[] addPhone;
	private String flag;
	private String prodHstSeq;

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

	public String getStrtDt() {
		return strtDt;
	}

	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}

	public String getStrtTm() {
		return strtTm;
	}

	public void setStrtTm(String strtTm) {
		this.strtTm = strtTm;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public String[] getAddPhone() {
		return addPhone;
	}

	public void setAddPhone(String[] addPhone) {
		this.addPhone = addPhone;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getProdHstSeq() {
		return prodHstSeq;
	}

	public void setProdHstSeq(String prodHstSeq) {
		this.prodHstSeq = prodHstSeq;
	}

}
