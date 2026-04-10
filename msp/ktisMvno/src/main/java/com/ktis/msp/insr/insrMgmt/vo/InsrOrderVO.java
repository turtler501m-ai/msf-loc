package com.ktis.msp.insr.insrMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class InsrOrderVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8128459243789712034L;
	
	private String searchFromDt;
	private String searchToDt;
	private String searchCd;
	private String searchVal;
	private String subscriberNo;
	private String subLinkName;
	private String contractNum;
	private String insrProdCd;
	private String insrProdNm;
	private String strtDttm;
	private String endDttm;
	private String ifTrgtCd;
	private String ifTrgtNm;
	private String insrStatCd;
	private String insrStatNm;
	private String chnCd;
	private String chnNm;
	private String imgChkYn;
	private String modelNm;
	private String intmSrlNo;
	private String modelId;
	private String reqBuyType;
	private String reqBuyTypeNm;
	private String vrfyRsltCd;
	private String vrfyRsltNm;
	private String fstVrfyCd;
	private String fstVrfyId;
	private String fstVrfyDttm;
	private String sndVrfyCd;
	private String sndVrfyId;
	private String sndVrfyDttm;
	private String rmk;
	private String scanId;
	private String resNo;
	private String scanMdyYn;
	private String openAgntCd;
	private String openAgntNm;
	private String agntId;
	private String insrTypeCd;
	private String prdtNm;
	
	//안심보험가입현황 조회용 
	private String onOffTypeNm;
	private String usimOrgNm;
	private String genderType;
	private String custAge;
	private String lstComActvDate;
	private String smsPhone;
	private String reqinday;
	private String searchGb;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	private String operTypeNm;
	private String operType;
	private String prodTypeNm;
	private String prodType;
	private String subStatusNm;

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
	public String getIfTrgtCd() {
		return ifTrgtCd;
	}
	public void setIfTrgtCd(String ifTrgtCd) {
		this.ifTrgtCd = ifTrgtCd;
	}
	public String getIfTrgtNm() {
		return ifTrgtNm;
	}
	public void setIfTrgtNm(String ifTrgtNm) {
		this.ifTrgtNm = ifTrgtNm;
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
	public String getImgChkYn() {
		return imgChkYn;
	}
	public void setImgChkYn(String imgChkYn) {
		this.imgChkYn = imgChkYn;
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
	public String getVrfyRsltCd() {
		return vrfyRsltCd;
	}
	public void setVrfyRsltCd(String vrfyRsltCd) {
		this.vrfyRsltCd = vrfyRsltCd;
	}
	public String getVrfyRsltNm() {
		return vrfyRsltNm;
	}
	public void setVrfyRsltNm(String vrfyRsltNm) {
		this.vrfyRsltNm = vrfyRsltNm;
	}
	public String getFstVrfyCd() {
		return fstVrfyCd;
	}
	public void setFstVrfyCd(String fstVrfyCd) {
		this.fstVrfyCd = fstVrfyCd;
	}
	public String getFstVrfyId() {
		return fstVrfyId;
	}
	public void setFstVrfyId(String fstVrfyId) {
		this.fstVrfyId = fstVrfyId;
	}
	public String getFstVrfyDttm() {
		return fstVrfyDttm;
	}
	public void setFstVrfyDttm(String fstVrfyDttm) {
		this.fstVrfyDttm = fstVrfyDttm;
	}
	public String getSndVrfyCd() {
		return sndVrfyCd;
	}
	public void setSndVrfyCd(String sndVrfyCd) {
		this.sndVrfyCd = sndVrfyCd;
	}
	public String getSndVrfyId() {
		return sndVrfyId;
	}
	public void setSndVrfyId(String sndVrfyId) {
		this.sndVrfyId = sndVrfyId;
	}
	public String getSndVrfyDttm() {
		return sndVrfyDttm;
	}
	public void setSndVrfyDttm(String sndVrfyDttm) {
		this.sndVrfyDttm = sndVrfyDttm;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
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
	public String getScanMdyYn() {
		return scanMdyYn;
	}
	public void setScanMdyYn(String scanMdyYn) {
		this.scanMdyYn = scanMdyYn;
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
	public String getAgntId() {
		return agntId;
	}
	public void setAgntId(String agntId) {
		this.agntId = agntId;
	}
	public String getInsrTypeCd() {
		return insrTypeCd;
	}
	public void setInsrTypeCd(String insrTypeCd) {
		this.insrTypeCd = insrTypeCd;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
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
	public String getOnOffTypeNm() {
		return onOffTypeNm;
	}
	public void setOnOffTypeNm(String onOffTypeNm) {
		this.onOffTypeNm = onOffTypeNm;
	}
	public String getUsimOrgNm() {
		return usimOrgNm;
	}
	public void setUsimOrgNm(String usimOrgNm) {
		this.usimOrgNm = usimOrgNm;
	}
	public String getGenderType() {
		return genderType;
	}
	public void setGenderType(String genderType) {
		this.genderType = genderType;
	}
	public String getCustAge() {
		return custAge;
	}
	public void setCustAge(String custAge) {
		this.custAge = custAge;
	}
	public String getLstComActvDate() {
		return lstComActvDate;
	}
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}
	public String getSmsPhone() {
		return smsPhone;
	}
	public void setSmsPhone(String smsPhone) {
		this.smsPhone = smsPhone;
	}
	public String getOperTypeNm() {
		return operTypeNm;
	}
	public void setOperTypeNm(String operTypeNm) {
		this.operTypeNm = operTypeNm;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getProdTypeNm() {
		return prodTypeNm;
	}
	public void setProdTypeNm(String prodTypeNm) {
		this.prodTypeNm = prodTypeNm;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
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
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	
}
