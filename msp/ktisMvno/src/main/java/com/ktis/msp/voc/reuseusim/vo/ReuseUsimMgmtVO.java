package com.ktis.msp.voc.reuseusim.vo;
import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class ReuseUsimMgmtVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 검색어
	private String strtDt;		// 조회시작일자
	private String endDt;		// 조회종료일자
	
	private String searchClCd;	// 검색구분
	private String searchVal;	// 검색어
	
	//
	private String contractNum;		// 계약번호
	private String subscriberNo;	// 전화번호
	
	
	private String subStatus;		// 계약상태
	private String openAgntCd;			// 개통대리점코드
	private String subStatusNm;			// 계약상태
	private String searchGb;
	private String searchGbVal;
	
	private String iccId;
	private String usimStatus;
	private String regRsn;
	
	private String lstRateCd;
	private String sub_status;
	
	private String pContractNum;
	private String pSubLinkName;
	private String pCtn;
	private String pUsimNo;
	private String pUsimStatus;
	
	private String searchName;
	private String realUsim;
	
	private String reuseSeq;
		
	
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
	public String getSearchClCd() {
		return searchClCd;
	}
	public void setSearchClCd(String searchClCd) {
		this.searchClCd = searchClCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	public String getSearchGb() {
		return searchGb;
	}
	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}
	public String getSearchGbVal() {
		return searchGbVal;
	}
	public void setSearchGbVal(String searchGbVal) {
		this.searchGbVal = searchGbVal;
	}
	public String getIccId() {
		return iccId;
	}
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}
	public String getUsimStatus() {
		return usimStatus;
	}
	public void setUsimStatus(String usimStatus) {
		this.usimStatus = usimStatus;
	}
	public String getRegRsn() {
		return regRsn;
	}
	public void setRegRsn(String regRsn) {
		this.regRsn = regRsn;
	}
	public String getLstRateCd() {
		return lstRateCd;
	}
	public void setLstRateCd(String lstRateCd) {
		this.lstRateCd = lstRateCd;
	}
	public String getSub_status() {
		return sub_status;
	}
	public void setSub_status(String sub_status) {
		this.sub_status = sub_status;
	}
	public String getpContractNum() {
		return pContractNum;
	}
	public void setpContractNum(String pContractNum) {
		this.pContractNum = pContractNum;
	}
	public String getpSubLinkName() {
		return pSubLinkName;
	}
	public void setpSubLinkName(String pSubLinkName) {
		this.pSubLinkName = pSubLinkName;
	}
	public String getpCtn() {
		return pCtn;
	}
	public void setpCtn(String pCtn) {
		this.pCtn = pCtn;
	}
	public String getpUsimNo() {
		return pUsimNo;
	}
	public void setpUsimNo(String pUsimNo) {
		this.pUsimNo = pUsimNo;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getpUsimStatus() {
		return pUsimStatus;
	}
	public void setpUsimStatus(String pUsimStatus) {
		this.pUsimStatus = pUsimStatus;
	}
	public String getReuseSeq() {
		return reuseSeq;
	}
	public void setReuseSeq(String reuseSeq) {
		this.reuseSeq = reuseSeq;
	}
	public String getRealUsim() {
		return realUsim;
	}
	public void setRealUsim(String realUsim) {
		this.realUsim = realUsim;
	}
	
}
