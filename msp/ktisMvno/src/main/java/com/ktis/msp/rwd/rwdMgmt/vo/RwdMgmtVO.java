package com.ktis.msp.rwd.rwdMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class RwdMgmtVO extends BaseVo {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 636605795630344783L;
	
	private String trtmDt;
	private String pagingYn;
	
	private String rwdProdCd;
	private String rwdProdNm;
	private String rwdPrd;
	private String useYn;
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
	public String getPagingYn() {
		return pagingYn;
	}
	public void setPagingYn(String pagingYn) {
		this.pagingYn = pagingYn;
	}
	public String getRwdProdCd() {
		return rwdProdCd;
	}
	public void setRwdProdCd(String rwdProdCd) {
		this.rwdProdCd = rwdProdCd;
	}
	public String getRwdProdNm() {
		return rwdProdNm;
	}
	public void setRwdProdNm(String rwdProdNm) {
		this.rwdProdNm = rwdProdNm;
	}
	public String getRwdPrd() {
		return rwdPrd;
	}
	public void setRwdPrd(String rwdPrd) {
		this.rwdPrd = rwdPrd;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	public String getSubLmtYn() {
		return subLmtYn;
	}
	public void setSubLmtYn(String subLmtYn) {
		this.subLmtYn = subLmtYn;
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
}
