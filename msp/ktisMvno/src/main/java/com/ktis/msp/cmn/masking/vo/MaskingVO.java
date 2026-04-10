package com.ktis.msp.cmn.masking.vo;

import java.io.Serializable;

public class MaskingVO implements Serializable {
	private static final long serialVersionUID = 8674344436438968020L;

	private String cnt;
	private String mskAuthYn;
	private String mskId;
	private String mskGrpId;
	private String mskLnth;
	private String mskLoc;
	private String mskStrtLoc;
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getMskAuthYn() {
		return mskAuthYn;
	}
	public void setMskAuthYn(String mskAuthYn) {
		this.mskAuthYn = mskAuthYn;
	}
	public String getMskId() {
		return mskId;
	}
	public void setMskId(String mskId) {
		this.mskId = mskId;
	}
	public String getMskGrpId() {
		return mskGrpId;
	}
	public void setMskGrpId(String mskGrpId) {
		this.mskGrpId = mskGrpId;
	}
	public String getMskLnth() {
		return mskLnth;
	}
	public void setMskLnth(String mskLnth) {
		this.mskLnth = mskLnth;
	}
	public String getMskLoc() {
		return mskLoc;
	}
	public void setMskLoc(String mskLoc) {
		this.mskLoc = mskLoc;
	}
	public String getMskStrtLoc() {
		return mskStrtLoc;
	}
	public void setMskStrtLoc(String mskStrtLoc) {
		this.mskStrtLoc = mskStrtLoc;
	}
}
