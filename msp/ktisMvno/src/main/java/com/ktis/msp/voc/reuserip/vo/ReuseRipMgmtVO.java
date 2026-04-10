package com.ktis.msp.voc.reuserip.vo;
import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class ReuseRipMgmtVO extends BaseVo implements Serializable {

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
	
	private String regRsn;
	
	private String lstRateCd;
	private String sub_status;
	
	private String pContractNum;
	private String pSubLinkName;
	private String pCtn;
	private String pRip;
	private String pRipStatus;
	
	private String searchName;
	
	private String reuseIpSeq;
	private String requestKey;
	
	private String rip;
	private String ripStatus;
	
		
	
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
	public String getpRip() {
		return pRip;
	}
	public void setpRip(String pRip) {
		this.pRip = pRip;
	}
	public String getpRipStatus() {
		return pRipStatus;
	}
	public void setpRipStatus(String pRipStatus) {
		this.pRipStatus = pRipStatus;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getReuseIpSeq() {
		return reuseIpSeq;
	}
	public void setReuseIpSeq(String reuseIpSeq) {
		this.reuseIpSeq = reuseIpSeq;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getRip() {
		return rip;
	}
	public void setRip(String rip) {
		this.rip = rip;
	}
	public String getRipStatus() {
		return ripStatus;
	}
	public void setRipStatus(String ripStatus) {
		this.ripStatus = ripStatus;
	}
	
}
