package com.ktmmobile.mcp.msp.dto;

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
	private int updateQnty;
	/***20230203 wooki 추가 start****/
	private String startDate;
	private String endDate;
	private String inputSearch;
	private String searchDiv;
	private String linkMo;
	private String coupnCtgNm;
	private String coupnDivCd;
	/***20230203 wooki 추가 end****/
	private String lstComActvDate; // 최초개통날자

	private String fullGiveDate;

	private String cretDt;

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
	public int getUpdateQnty() {
		return updateQnty;
	}
	public void setUpdateQnty(int updateQnty) {
		this.updateQnty = updateQnty;
	}

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getInputSearch() {
		return inputSearch;
	}
	public void setInputSearch(String inputSearch) {
		this.inputSearch = inputSearch;
	}
	public String getSearchDiv() {
		return searchDiv;
	}
	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}
	public String getCoupnCtgNm() {
		return coupnCtgNm;
	}
	public void setCoupnCtgNm(String coupnCtgNm) {
		this.coupnCtgNm = coupnCtgNm;
	}
	public String getLinkMo() {
		return linkMo;
	}
	public void setLinkMo(String linkMo) {
		this.linkMo = linkMo;
	}
	public String getCoupnDivCd() {
		return coupnDivCd;
	}
	public void setCoupnDivCd(String coupnDivCd) {
		this.coupnDivCd = coupnDivCd;
	}

	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}

	public String getLstComActvDate() {
		return lstComActvDate;
	}

	public void setFullGiveDate(String fullGiveDate) {
		this.fullGiveDate = fullGiveDate;
	}

	public String getFullGiveDate() {
		return fullGiveDate;
	}

	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}

	public String getCretDt() {
		return cretDt;
	}
}
