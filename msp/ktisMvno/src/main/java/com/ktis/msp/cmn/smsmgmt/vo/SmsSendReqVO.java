package com.ktis.msp.cmn.smsmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SmsSendReqVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7198086784678733662L;
	
	private String searchStartDate;		// 발송요청시작일자
	private String searchEndDate;		// 발송요청종료일자
	private String searchWorkType;		// 업무구분
	private String searchCustType;		// 고객구분
	private String searchTemplateNm;	// 템플릿명
	private String searchCode;			// 검색구분코드
	private String searchValue;			// 검색내용
	private String searchSendYn;		// 발송여부
	private String searchResult;		// 발송결과
	
	// 수신자선택발송
	private String searchFromDt;
	private String searchToDt;
	private String searchCustDiv;		// 고객유형
	private String searchRateCd;		// 요금제
	private String searchBuyType;		// 구매유형
	private String searchMarketing;		// 마케팅동의
	private String searchOrgnId;		// 조직ID
	
	private String msgId;		// 문자발송 KEY 값
	
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public String getSearchWorkType() {
		return searchWorkType;
	}
	public void setSearchWorkType(String searchWorkType) {
		this.searchWorkType = searchWorkType;
	}
	public String getSearchCustType() {
		return searchCustType;
	}
	public void setSearchCustType(String searchCustType) {
		this.searchCustType = searchCustType;
	}
	public String getSearchTemplateNm() {
		return searchTemplateNm;
	}
	public void setSearchTemplateNm(String searchTemplateNm) {
		this.searchTemplateNm = searchTemplateNm;
	}
	public String getSearchCode() {
		return searchCode;
	}
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getSearchSendYn() {
		return searchSendYn;
	}
	public void setSearchSendYn(String searchSendYn) {
		this.searchSendYn = searchSendYn;
	}
	public String getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
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
	public String getSearchCustDiv() {
		return searchCustDiv;
	}
	public void setSearchCustDiv(String searchCustDiv) {
		this.searchCustDiv = searchCustDiv;
	}
	public String getSearchRateCd() {
		return searchRateCd;
	}
	public void setSearchRateCd(String searchRateCd) {
		this.searchRateCd = searchRateCd;
	}
	public String getSearchBuyType() {
		return searchBuyType;
	}
	public void setSearchBuyType(String searchBuyType) {
		this.searchBuyType = searchBuyType;
	}
	public String getSearchMarketing() {
		return searchMarketing;
	}
	public void setSearchMarketing(String searchMarketing) {
		this.searchMarketing = searchMarketing;
	}
	public String getSearchOrgnId() {
		return searchOrgnId;
	}
	public void setSearchOrgnId(String searchOrgnId) {
		this.searchOrgnId = searchOrgnId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
