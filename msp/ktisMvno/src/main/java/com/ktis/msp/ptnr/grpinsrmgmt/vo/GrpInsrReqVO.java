package com.ktis.msp.ptnr.grpinsrmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class GrpInsrReqVO extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3196136939433192295L;
	
	private String seq;
	private String stdrDt;
	private String grpInsrCd;
	private String usgYn;
	// 신청등록 조회시 사용
	private String reqInDay;
	private String etc2;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public String getGrpInsrCd() {
		return grpInsrCd;
	}
	public void setGrpInsrCd(String grpInsrCd) {
		this.grpInsrCd = grpInsrCd;
	}
	public String getUsgYn() {
		return usgYn;
	}
	public void setUsgYn(String usgYn) {
		this.usgYn = usgYn;
	}
	public String getReqInDay() {
		return reqInDay;
	}
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}
	public String getEtc2() {
		return etc2;
	}
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}
	
	
	
}
