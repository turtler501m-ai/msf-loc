package com.ktis.msp.org.prmtmgmt.vo;

import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class RecommenIdStateVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3234747999341497150L;
	
	private String searchFromDt;
	private String searchToDt;
	private String searchCd;
	private String searchVal;
	private String appType;
	private String deSearchCd;
	private String deSearchVal;
	
	
	private String commendId;     /*추천인ID*/
	private String contractNum;   /*추천인 계약번호*/
	private String subLinkName;   /*추천인 고객명*/
	private String subscriberNo;  /*추천인 휴대폰번호*/ 
	private String sysRdate;		 /*발급일자*/
	

	private String rateCd;
	private String rateNm;
	private String dLstRateCd;
	private String dLstRateNm;
	private String dFstRateCd;
	private String dFstRateNm;
	private String subStatus;
	private String applyYn;
	private String applyDate;
	private String dContractNum;
	
	private String uploadContractNum;
	private String deSubLinkName;
	private String deSubscriberNo;
	private String dSubStatus;
	private String dLstComActvDate;
	private String dSysRdate;
	
	private String fileName;
	
	private String birthDt;
	private String dBirthDt;
	
	private String dOperTypeNm;		/*피추천인 업무구분*/
	private String dBfCommCmpnNm;	/*피추천인 이동전 통신사*/
	
	private String linkTypeCd;	/*링크유형코드*/
	private String linkTypeNm;	/*링크유형*/
	private String bfCommCmpnNm; /*이동전 통신사 */
	
	private String orgnNm;
	private String evntCdPrmt;
	private String dSvcCntrNo;
	private String dOpenAgntNm;
	private String dReqBuyTypeNm;
	private String dReqInDay;
	private String dSubStatusDate;
	private String dOrngNm;
	private String dGender;
	private String dAge;
	private String dEvntCdPrmt;
	private String dInetStatus;
	private String commendSocCode01;
	private String commendSocCode02;
	private String commendSocCode03;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	List<RecommenIdStateVO> items;
	
	public String getSearchFromDt() {
		return searchFromDt;
	}
	public void setSearchFromDt(String searchFromDt) {
		this.searchFromDt = searchFromDt;
	}
	public String getSearchToDt() {
		return searchToDt;
	}
	public void setSearchToDt(String searchToDt) {
		this.searchToDt = searchToDt;
	}
	public String getSearchCd() {
		return searchCd;
	}
	public void setSearchCd(String searchCd) {
		this.searchCd = searchCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getCommendId() {
		return commendId;
	}
	public void setCommendId(String commendId) {
		this.commendId = commendId;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSubLinkName() {
		return subLinkName;
	}
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
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
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getApplyYn() {
		return applyYn;
	}
	public void setApplyYn(String applyYn) {
		this.applyYn = applyYn;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getdContractNum() {
		return dContractNum;
	}
	public void setdContractNum(String dContractNum) {
		this.dContractNum = dContractNum;
	}


	public String getdLstRateNm() {
		return dLstRateNm;
	}
	public void setdLstRateNm(String dLstRateNm) {
		this.dLstRateNm = dLstRateNm;
	}
	public String getdSubStatus() {
		return dSubStatus;
	}
	public void setdSubStatus(String dSubStatus) {
		this.dSubStatus = dSubStatus;
	}
	public String getdLstComActvDate() {
		return dLstComActvDate;
	}
	public void setdLstComActvDate(String dLstComActvDate) {
		this.dLstComActvDate = dLstComActvDate;
	}
	public String getdSysRdate() {
		return dSysRdate;
	}
	public void setdSysRdate(String dSysRdate) {
		this.dSysRdate = dSysRdate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<RecommenIdStateVO> getItems() {
		return items;
	}
	public void setItems(List<RecommenIdStateVO> items) {
		this.items = items;
	}
	public String getUploadContractNum() {
		return uploadContractNum;
	}
	public void setUploadContractNum(String uploadContractNum) {
		this.uploadContractNum = uploadContractNum;
	}
	public String getDeSubLinkName() {
		return deSubLinkName;
	}
	public void setDeSubLinkName(String deSubLinkName) {
		this.deSubLinkName = deSubLinkName;
	}
	public String getDeSubscriberNo() {
		return deSubscriberNo;
	}
	public void setDeSubscriberNo(String deSubscriberNo) {
		this.deSubscriberNo = deSubscriberNo;
	}
	public String getDeSearchCd() {
		return deSearchCd;
	}
	public void setDeSearchCd(String deSearchCd) {
		this.deSearchCd = deSearchCd;
	}
	public String getDeSearchVal() {
		return deSearchVal;
	}
	public void setDeSearchVal(String deSearchVal) {
		this.deSearchVal = deSearchVal;
	}
	public String getBirthDt() {
		return birthDt;
	}
	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}
	public String getdBirthDt() {
		return dBirthDt;
	}
	public void setdBirthDt(String dBirthDt) {
		this.dBirthDt = dBirthDt;
	}
	public String getdOperTypeNm() {
		return dOperTypeNm;
	}
	public void setdOperTypeNm(String dOperTypeNm) {
		this.dOperTypeNm = dOperTypeNm;
	}
	public String getdBfCommCmpnNm() {
		return dBfCommCmpnNm;
	}
	public void setdBfCommCmpnNm(String dBfCommCmpnNm) {
		this.dBfCommCmpnNm = dBfCommCmpnNm;
	}
	public String getLinkTypeCd() {
		return linkTypeCd;
	}
	public void setLinkTypeCd(String linkTypeCd) {
		this.linkTypeCd = linkTypeCd;
	}
	public String getLinkTypeNm() {
		return linkTypeNm;
	}
	public void setLinkTypeNm(String linkTypeNm) {
		this.linkTypeNm = linkTypeNm;
	}
	public String getBfCommCmpnNm() {
		return bfCommCmpnNm;
	}
	public void setBfCommCmpnNm(String bfCommCmpnNm) {
		this.bfCommCmpnNm = bfCommCmpnNm;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getdLstRateCd() {
		return dLstRateCd;
	}
	public void setdLstRateCd(String dLstRateCd) {
		this.dLstRateCd = dLstRateCd;
	}
	public String getdFstRateCd() {
		return dFstRateCd;
	}
	public void setdFstRateCd(String dFstRateCd) {
		this.dFstRateCd = dFstRateCd;
	}
	public String getdFstRateNm() {
		return dFstRateNm;
	}
	public void setdFstRateNm(String dFstRateNm) {
		this.dFstRateNm = dFstRateNm;
	}
	public String getOrgnNm() {
		return orgnNm;
	}
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
	public String getEvntCdPrmt() {
		return evntCdPrmt;
	}
	public void setEvntCdPrmt(String evntCdPrmt) {
		this.evntCdPrmt = evntCdPrmt;
	}
	public String getdSvcCntrNo() {
		return dSvcCntrNo;
	}
	public void setdSvcCntrNo(String dSvcCntrNo) {
		this.dSvcCntrNo = dSvcCntrNo;
	}
	public String getdOpenAgntNm() {
		return dOpenAgntNm;
	}
	public void setdOpenAgntNm(String dOpenAgntNm) {
		this.dOpenAgntNm = dOpenAgntNm;
	}
	public String getdReqBuyTypeNm() {
		return dReqBuyTypeNm;
	}
	public void setdReqBuyTypeNm(String dReqBuyTypeNm) {
		this.dReqBuyTypeNm = dReqBuyTypeNm;
	}
	public String getdReqInDay() {
		return dReqInDay;
	}
	public void setdReqInDay(String dReqInDay) {
		this.dReqInDay = dReqInDay;
	}
	public String getdSubStatusDate() {
		return dSubStatusDate;
	}
	public void setdSubStatusDate(String dSubStatusDate) {
		this.dSubStatusDate = dSubStatusDate;
	}
	public String getdOrngNm() {
		return dOrngNm;
	}
	public void setdOrngNm(String dOrngNm) {
		this.dOrngNm = dOrngNm;
	}
	public String getdGender() {
		return dGender;
	}
	public void setdGender(String dGender) {
		this.dGender = dGender;
	}
	public String getdAge() {
		return dAge;
	}
	public void setdAge(String dAge) {
		this.dAge = dAge;
	}
	public String getdEvntCdPrmt() {
		return dEvntCdPrmt;
	}
	public void setdEvntCdPrmt(String dEvntCdPrmt) {
		this.dEvntCdPrmt = dEvntCdPrmt;
	}
	public String getdInetStatus() {
		return dInetStatus;
	}
	public void setdInetStatus(String dInetStatus) {
		this.dInetStatus = dInetStatus;
	}
	public String getCommendSocCode01() {
		return commendSocCode01;
	}
	public void setCommendSocCode01(String commendSocCode01) {
		this.commendSocCode01 = commendSocCode01;
	}
	public String getCommendSocCode02() {
		return commendSocCode02;
	}
	public void setCommendSocCode02(String commendSocCode02) {
		this.commendSocCode02 = commendSocCode02;
	}
	public String getCommendSocCode03() {
		return commendSocCode03;
	}
	public void setCommendSocCode03(String commendSocCode03) {
		this.commendSocCode03 = commendSocCode03;
	}
	
	
}
