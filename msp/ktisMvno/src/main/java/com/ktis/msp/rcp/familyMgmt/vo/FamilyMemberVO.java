package com.ktis.msp.rcp.familyMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class FamilyMemberVO extends BaseVo {

	private static final long serialVersionUID = 1000479942183581913L;

	private String famSeq;
	private String seq;
	private String svcCntrNo;
	private String memType;
	private String childType;
	private String useYn;
	private String strtDttm;
	private String endDttm;
	private String endCode;
	private String endMsg;

	public String getFamSeq() {
		return famSeq;
	}

	public void setFamSeq(String famSeq) {
		this.famSeq = famSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}

	public String getMemType() {
		return memType;
	}

	public void setMemType(String memType) {
		this.memType = memType;
	}

	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getStrtDttm() {
		return strtDttm;
	}

	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}

	public String getEndDttm() {
		return endDttm;
	}

	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getEndMsg() {
		return endMsg;
	}

	public void setEndMsg(String endMsg) {
		this.endMsg = endMsg;
	}
}
