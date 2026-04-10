package com.ktis.msp.insr.insrMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class InsrRegVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -345918188767125889L;
	private String subscriberNo;
	private String contractNum;
	private String custNm;
	private String subStatus;
	private String subStatusNm;
	private String openAgntCd;
	private String openAgntNm;
	private String openDt;
	private String dvcChgDt;
	private String insrProdCd;
	private String insrProdNm;
	private String strtDt;
	private String endDt;
	private String reqinday;/** 신청일자 */
	private String reqBuyType;
	private String reqBuyTypeNm;
	private String intmInsrStatNm;
	private String sbscInsrProdCd;
	private String sbscInsrProdNm;
	private String sbscInsrStrtDt;
	private String sbscInsrEndDt;
	private String insrModelNm;
	private String insrModelId;
	private String insrIntmSrlNo;
	private String trgtModelNm;
	private String trgtModelId;
	private String trgtIntmSrlNo;
	
	private String searchCntrNo;
	
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
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
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getDvcChgDt() {
		return dvcChgDt;
	}
	public void setDvcChgDt(String dvcChgDt) {
		this.dvcChgDt = dvcChgDt;
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
	public String getStrtDt() {
		return strtDt;
	}
	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getReqinday() {
		return reqinday;
	}
	public void setReqinday(String reqinday) {
		this.reqinday = reqinday;
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
	public String getIntmInsrStatNm() {
		return intmInsrStatNm;
	}
	public void setIntmInsrStatNm(String intmInsrStatNm) {
		this.intmInsrStatNm = intmInsrStatNm;
	}
	public String getSbscInsrProdCd() {
		return sbscInsrProdCd;
	}
	public void setSbscInsrProdCd(String sbscInsrProdCd) {
		this.sbscInsrProdCd = sbscInsrProdCd;
	}
	public String getSbscInsrProdNm() {
		return sbscInsrProdNm;
	}
	public void setSbscInsrProdNm(String sbscInsrProdNm) {
		this.sbscInsrProdNm = sbscInsrProdNm;
	}
	public String getSbscInsrStrtDt() {
		return sbscInsrStrtDt;
	}
	public void setSbscInsrStrtDt(String sbscInsrStrtDt) {
		this.sbscInsrStrtDt = sbscInsrStrtDt;
	}
	public String getSbscInsrEndDt() {
		return sbscInsrEndDt;
	}
	public void setSbscInsrEndDt(String sbscInsrEndDt) {
		this.sbscInsrEndDt = sbscInsrEndDt;
	}
	public String getInsrModelNm() {
		return insrModelNm;
	}
	public void setInsrModelNm(String insrModelNm) {
		this.insrModelNm = insrModelNm;
	}
	public String getInsrModelId() {
		return insrModelId;
	}
	public void setInsrModelId(String insrModelId) {
		this.insrModelId = insrModelId;
	}
	public String getInsrIntmSrlNo() {
		return insrIntmSrlNo;
	}
	public void setInsrIntmSrlNo(String insrIntmSrlNo) {
		this.insrIntmSrlNo = insrIntmSrlNo;
	}
	public String getTrgtModelNm() {
		return trgtModelNm;
	}
	public void setTrgtModelNm(String trgtModelNm) {
		this.trgtModelNm = trgtModelNm;
	}
	public String getTrgtModelId() {
		return trgtModelId;
	}
	public void setTrgtModelId(String trgtModelId) {
		this.trgtModelId = trgtModelId;
	}
	public String getTrgtIntmSrlNo() {
		return trgtIntmSrlNo;
	}
	public void setTrgtIntmSrlNo(String trgtIntmSrlNo) {
		this.trgtIntmSrlNo = trgtIntmSrlNo;
	}
	public String getSearchCntrNo() {
		return searchCntrNo;
	}
	public void setSearchCntrNo(String searchCntrNo) {
		this.searchCntrNo = searchCntrNo;
	}
	
}
