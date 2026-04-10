package com.ktmmobile.mcp.coupon.dto;

import java.io.Serializable;

public class CouponOutsideDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private String coupnNo;
	private String coupnCtgCd;
	private String coupnAmt;
	private String useYn;
	private String pstngStartDate;
	private String pstngEndDate;
	private String userId;
	private String userNm;
	private String svcCntrNo;
	private String contractNum;
	private String smsRcvNo;
	private String smsSndYn;
	private String smsSndDt;
	private String smsSndSuccessYn;
	private String cretIp; // 등록아이피
	private String cretId; // 생성자
	private String searchValue; // 검색어
	private String searchType; // 검색 유형
	private int tmpSeq;
	private String rsltCd;
	private String failRsnDesc;
	private String realCoupnNo;
	private String tmpWorkSeq;
	private String coupnGiveDate;
	private String amdIp; // 수정자아이피
	private String amdId; // 수정자
	private String coupnHstSeq; // 실패이력시퀀스

	public String getCoupnNo() {
		return coupnNo;
	}
	public void setCoupnNo(String coupnNo) {
		this.coupnNo = coupnNo;
	}
	public String getCoupnCtgCd() {
		return coupnCtgCd;
	}
	public void setCoupnCtgCd(String coupnCtgCd) {
		this.coupnCtgCd = coupnCtgCd;
	}
	public String getCoupnAmt() {
		return coupnAmt;
	}
	public void setCoupnAmt(String coupnAmt) {
		this.coupnAmt = coupnAmt;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getPstngStartDate() {
		return pstngStartDate;
	}
	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}
	public String getPstngEndDate() {
		return pstngEndDate;
	}
	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getSmsRcvNo() {
		return smsRcvNo;
	}
	public void setSmsRcvNo(String smsRcvNo) {
		this.smsRcvNo = smsRcvNo;
	}
	public String getSmsSndYn() {
		return smsSndYn;
	}
	public void setSmsSndYn(String smsSndYn) {
		this.smsSndYn = smsSndYn;
	}
	public String getSmsSndDt() {
		return smsSndDt;
	}
	public void setSmsSndDt(String smsSndDt) {
		this.smsSndDt = smsSndDt;
	}
	public String getSmsSndSuccessYn() {
		return smsSndSuccessYn;
	}
	public void setSmsSndSuccessYn(String smsSndSuccessYn) {
		this.smsSndSuccessYn = smsSndSuccessYn;
	}
	public String getCretIp() {
		return cretIp;
	}
	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getTmpSeq() {
		return tmpSeq;
	}
	public void setTmpSeq(int tmpSeq) {
		this.tmpSeq = tmpSeq;
	}
	public String getFailRsnDesc() {
		return failRsnDesc;
	}
	public void setFailRsnDesc(String failRsnDesc) {
		this.failRsnDesc = failRsnDesc;
	}
	public String getRsltCd() {
		return rsltCd;
	}
	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}
	public String getRealCoupnNo() {
		return realCoupnNo;
	}
	public void setRealCoupnNo(String realCoupnNo) {
		this.realCoupnNo = realCoupnNo;
	}
	public String getTmpWorkSeq() {
		return tmpWorkSeq;
	}
	public void setTmpWorkSeq(String tmpWorkSeq) {
		this.tmpWorkSeq = tmpWorkSeq;
	}
	public String getCoupnGiveDate() {
		return coupnGiveDate;
	}
	public void setCoupnGiveDate(String coupnGiveDate) {
		this.coupnGiveDate = coupnGiveDate;
	}
	public String getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(String amdIp) {
		this.amdIp = amdIp;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}

	public String getCoupnHstSeq() {
		return coupnHstSeq;
	}

	public void setCoupnHstSeq(String coupnHstSeq) {
		this.coupnHstSeq = coupnHstSeq;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
}
