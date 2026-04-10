package com.ktis.msp.sale.subsdMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class SubsdMgmtVO extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String rateCd;
	private String prdtId;
	private String oldYn;
	private String agrmTrm;
	private String operType;
	private String applStrtDt;
	private String applEndDt;
	private int subsdAmt;
	private String userId;
	private String operTypeNm;
	private String agrmTrmNm;
	private String prdtIndCd;
	
	// 조회조건
	private String flag; 
	private String stdrDt;
	private String rateGrpYn;
	private String rateGrpCd;
	
	public String getOperTypeNm() {
		return operTypeNm;
	}
	public void setOperTypeNm(String operTypeNm) {
		this.operTypeNm = operTypeNm;
	}
	public String getAgrmTrmNm() {
		return agrmTrmNm;
	}
	public void setAgrmTrmNm(String agrmTrmNm) {
		this.agrmTrmNm = agrmTrmNm;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getOldYn() {
		return oldYn;
	}
	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}
	public String getAgrmTrm() {
		return agrmTrm;
	}
	public void setAgrmTrm(String agrmTrm) {
		this.agrmTrm = agrmTrm;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getApplStrtDt() {
		return applStrtDt;
	}
	public void setApplStrtDt(String applStrtDt) {
		this.applStrtDt = applStrtDt;
	}
	public String getApplEndDt() {
		return applEndDt;
	}
	public void setApplEndDt(String applEndDt) {
		this.applEndDt = applEndDt;
	}
	public int getSubsdAmt() {
		return subsdAmt;
	}
	public void setSubsdAmt(int subsdAmt) {
		this.subsdAmt = subsdAmt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public String getRateGrpYn() {
		return rateGrpYn;
	}
	public void setRateGrpYn(String rateGrpYn) {
		this.rateGrpYn = rateGrpYn;
	}
	public String getRateGrpCd() {
		return rateGrpCd;
	}
	public void setRateGrpCd(String rateGrpCd) {
		this.rateGrpCd = rateGrpCd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the prdtIndCd
	 */
	public String getPrdtIndCd() {
		return prdtIndCd;
	}
	/**
	 * @param prdtIndCd the prdtIndCd to set
	 */
	public void setPrdtIndCd(String prdtIndCd) {
		this.prdtIndCd = prdtIndCd;
	}
	
	
	
	
	
}
