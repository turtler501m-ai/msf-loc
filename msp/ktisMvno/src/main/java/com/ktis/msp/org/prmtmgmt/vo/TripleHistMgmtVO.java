package com.ktis.msp.org.prmtmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class TripleHistMgmtVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9045258754875949571L;
	
	private String srchStrtDt;
	private String srchEndDt;
	private String regDt;
	private String regId;
	private String rvisnId;
	private String rvisnDt;
	private String searchGbn;
	private String searchName;
	private String appRoute;
	private String succYn;
	private String contractNum;
	private String rateCd;
	private String rateNm;
	private String additionCd;
	private String additionNm;
	private String rip;
	private String ktInternetId;
	private String installDd;
	private String procYn;
	private String nKtInternetId;
	private String viewType;
	private String lstRateCd;
	
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	public String getSrchStrtDt() {
		return srchStrtDt;
	}
	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnDt() {
		return rvisnDt;
	}
	public void setRvisnDt(String rvisnDt) {
		this.rvisnDt = rvisnDt;
	}
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getAppRoute() {
		return appRoute;
	}
	public void setAppRoute(String appRoute) {
		this.appRoute = appRoute;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getAdditionCd() {
		return additionCd;
	}
	public void setAdditionCd(String additionCd) {
		this.additionCd = additionCd;
	}
	public String getAdditionNm() {
		return additionNm;
	}
	public void setAdditionNm(String additionNm) {
		this.additionNm = additionNm;
	}
	public String getRip() {
		return rip;
	}
	public void setRip(String rip) {
		this.rip = rip;
	}
	public String getKtInternetId() {
		return ktInternetId;
	}
	public void setKtInternetId(String ktInternetId) {
		this.ktInternetId = ktInternetId;
	}
	public String getInstallDd() {
		return installDd;
	}
	public void setInstallDd(String installDd) {
		this.installDd = installDd;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProcYn() {
		return procYn;
	}
	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}
	public String getnKtInternetId() {
		return nKtInternetId;
	}
	public void setnKtInternetId(String nKtInternetId) {
		this.nKtInternetId = nKtInternetId;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getSuccYn() {
		return succYn;
	}
	public void setSuccYn(String succYn) {
		this.succYn = succYn;
	}
	public String getLstRateCd() {
		return lstRateCd;
	}
	public void setLstRateCd(String lstRateCd) {
		this.lstRateCd = lstRateCd;
	}
	
	
}
