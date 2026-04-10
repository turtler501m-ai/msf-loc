package com.ktis.msp.insr.insrMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class InsrMgmtVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6523254085944586545L;
	
	private String trtmDt;
	private String pagingYn;
	
	private String insrProdCd;
	private String insrProdNm;
	private String cmpnLmtAmt;
	private String insrEnggCnt;
	private String insrTypeCd;
	private String usgYn;
	private String baseAmt;
	private String regstId;
	private String regstDttm;
	private String rvisnId;
	private String rvisnDttm;
	private String ordrNo;
	private String subLmtYn;
	
	private String rprsPrdtId;
	private String prdtNm;
	private String prdtCode;
	private String mnfctNm;
	private String applYn;
	private String applYnNm;
	
	private String reqBuyType;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	public String getTrtmDt() {
		return trtmDt;
	}
	public void setTrtmDt(String trtmDt) {
		this.trtmDt = trtmDt;
	}
	public String getInsrProdCd() {
		return insrProdCd;
	}
	public void setInsrProdCd(String insrProdCd) {
		this.insrProdCd = insrProdCd;
	}
	public String getInsrProdNm() {
		return insrProdNm;
	}
	public void setInsrProdNm(String insrProdNm) {
		this.insrProdNm = insrProdNm;
	}
	public String getCmpnLmtAmt() {
		return cmpnLmtAmt;
	}
	public void setCmpnLmtAmt(String cmpnLmtAmt) {
		this.cmpnLmtAmt = cmpnLmtAmt;
	}
	public String getInsrEnggCnt() {
		return insrEnggCnt;
	}
	public void setInsrEnggCnt(String insrEnggCnt) {
		this.insrEnggCnt = insrEnggCnt;
	}
	public String getInsrTypeCd() {
		return insrTypeCd;
	}
	public void setInsrTypeCd(String insrTypeCd) {
		this.insrTypeCd = insrTypeCd;
	}
	public String getUsgYn() {
		return usgYn;
	}
	public void setUsgYn(String usgYn) {
		this.usgYn = usgYn;
	}
	public String getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(String baseAmt) {
		this.baseAmt = baseAmt;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public String getOrdrNo() {
		return ordrNo;
	}
	public void setOrdrNo(String ordrNo) {
		this.ordrNo = ordrNo;
	}
	public String getRprsPrdtId() {
		return rprsPrdtId;
	}
	public void setRprsPrdtId(String rprsPrdtId) {
		this.rprsPrdtId = rprsPrdtId;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getPrdtCode() {
		return prdtCode;
	}
	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}
	public String getMnfctNm() {
		return mnfctNm;
	}
	public void setMnfctNm(String mnfctNm) {
		this.mnfctNm = mnfctNm;
	}
	public String getApplYn() {
		return applYn;
	}
	public void setApplYn(String applYn) {
		this.applYn = applYn;
	}
	public String getApplYnNm() {
		return applYnNm;
	}
	public void setApplYnNm(String applYnNm) {
		this.applYnNm = applYnNm;
	}
	public String getPagingYn() {
		return pagingYn;
	}
	public void setPagingYn(String pagingYn) {
		this.pagingYn = pagingYn;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
	public String getSubLmtYn() {
		return subLmtYn;
	}
	public void setSubLmtYn(String subLmtYn) {
		this.subLmtYn = subLmtYn;
	}
	
}
