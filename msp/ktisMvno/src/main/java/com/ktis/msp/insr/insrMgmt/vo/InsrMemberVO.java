package com.ktis.msp.insr.insrMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class InsrMemberVO extends BaseVo {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String subscriberNo;
	private String subLinkName;
	private String contractNum;
	private String insrProdCd;
	private String insrProdNm;
	private String strtDttm;
	private String endDttm;
	private String insrStatCd;
	private String insrStatNm;
	private String modelNm;
	private String intmSrlNo;
	private String modelId;
	private String chnCd;
	private String chnNm;
	private String reqBuyType;
	private String reqBuyTypeNm;
	private String scanId;
	private String resNo;
	private String openAgntCd;
	private String openAgntNm;
	private String insrMngmNo;
	private String rmnCmpnAmt;
	private String canRsltCd;
	private String canRsltNm;
	private String cmpnLmtAmt;
	private String insrEnggCnt;
	private String reqinday;
	private String searchGb;
	
	private String searchFromDt;
	private String searchToDt;
	private String searchCd;
	private String searchVal;
	private String agntId;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getSubLinkName() {
		return subLinkName;
	}
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
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
	public String getInsrStatCd() {
		return insrStatCd;
	}
	public void setInsrStatCd(String insrStatCd) {
		this.insrStatCd = insrStatCd;
	}
	public String getInsrStatNm() {
		return insrStatNm;
	}
	public void setInsrStatNm(String insrStatNm) {
		this.insrStatNm = insrStatNm;
	}
	public String getModelNm() {
		return modelNm;
	}
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}
	public String getIntmSrlNo() {
		return intmSrlNo;
	}
	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getChnCd() {
		return chnCd;
	}
	public void setChnCd(String chnCd) {
		this.chnCd = chnCd;
	}
	public String getChnNm() {
		return chnNm;
	}
	public void setChnNm(String chnNm) {
		this.chnNm = chnNm;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getReqBuyTypeNm() {
		return reqBuyTypeNm;
	}
	public void setReqBuyTypeNm(String reqBuyTypeNm) {
		this.reqBuyTypeNm = reqBuyTypeNm;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	public String getOpenAgntNm() {
		return openAgntNm;
	}
	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}
	public String getInsrMngmNo() {
		return insrMngmNo;
	}
	public void setInsrMngmNo(String insrMngmNo) {
		this.insrMngmNo = insrMngmNo;
	}
	public String getRmnCmpnAmt() {
		return rmnCmpnAmt;
	}
	public void setRmnCmpnAmt(String rmnCmpnAmt) {
		this.rmnCmpnAmt = rmnCmpnAmt;
	}
	public String getCanRsltCd() {
		return canRsltCd;
	}
	public void setCanRsltCd(String canRsltCd) {
		this.canRsltCd = canRsltCd;
	}
	public String getCanRsltNm() {
		return canRsltNm;
	}
	public void setCanRsltNm(String canRsltNm) {
		this.canRsltNm = canRsltNm;
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
	public String getAgntId() {
		return agntId;
	}
	public void setAgntId(String agntId) {
		this.agntId = agntId;
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
	public String getReqinday() {
		return reqinday;
	}
	public void setReqinday(String reqinday) {
		this.reqinday = reqinday;
	}
	public String getSearchGb() {
		return searchGb;
	}
	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}
	
}
