/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;
import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 
 *  Copyright (C) by MOPAS All right reserved.
 */
public class RcpListVO extends BaseVo implements Serializable {
	
	
	private static final long serialVersionUID = 3044976242494530140L;

	//신규 장익준 추가
    private String searchStartDt;
    private String searchEndDt;
    
    
    private String pReqInDayF = "";
    private String pReqInDayT = "";
    private String pServiceType = "";
    private String pOperType = "";
    private String pAgentCode = "";
    private String pRequestStateCode = "";
    private String pOpenNoExist = "";
    private String pSearchName = "";
    private String pSearchGbn = "";
    private String pPstate = "";
    
    private String cdGbn = "";
    private String prdtTypeCd = "";
	private String cdId = "";
	private String pFax = "";
	private String setEntrustResRrn ="";
	private String reqCardRrn = "";
	private String cstmrForeignerPn = "";

	private String serviceName = "";
	private String resNo = "";
	private String cstmrName = "";
	private String birthDay = "";
	private String cstmrMobile = "";
	private String agentName = "";
	private String socName = "";
	private String operName = "";
	private String reqInDay = "";
	private String requestStateName = "";
	private String openReqDate = "";
	private String pstateName = "";
	private String cstmrType = "";
	private String serviceType = "";
	private String socCode = "";
	private String operType = "";
	private String cstmrNativeRrn1 = "";
	private String cstmrNativeRrn2 = "";
	private String cstmrForeignerPn1 = "";
	private String cstmrForeignerPn2 = "";
	private String cstmrForeignerSdate = "";
	private String cstmrForeignerEdate = "";
	private String cstmrJuridicalCname = "";
	private String cstmrJuridicalRrn1 = "";
	private String cstmrJuridicalRrn2 = "";
	private String cstmrJuridicalNumber1 = "";
	private String cstmrJuridicalNumber2 = "";
	private String cstmrJuridicalNumber3 = "";
	private String cstmrPrivateCname = "";
	private String cstmrPrivateNumber1 = "";
	private String cstmrPrivateNumber2 = "";
	private String cstmrPrivateNumber3 = "";
	
	private String orgnId = "";
/*	private String operType = "";*/
	private String prdtId = "";
	private String prdtSctnCdNm = "";
	private String chrgPrdtId = "";
	private String agrmTrm = "";
	private String prdtColrNm = "";
	private String oldYn = "";
	private String instNom	 = "";
	private String cstmrNativeRrn = "";
	private String reqUsimSn	= "";
	private String reqPhoneSn = "";

	private String cstmrMail1 = "";
	private String cstmrMail2 = "";
	private String cstmrMail3 = "";
	
	private String cstmrMailReceiveFlag= "";
	
	private String cstmrAddr = "";
	private String cstmrAddrDtl = "";
	private String cstmrBillSendCode = "";
	private String cstmrTelFn = "";
	private String cstmrTelMn = "";
	private String cstmrTelRn = "";
	private String cstmrMobileFn = "";
	private String cstmrMobileMn = "";
	private String cstmrMobileRn = "";
	private String onlineAuthType = "";
	private String onlineAuthInfo = "";
	private String pstate = "";
	private String requestStateCode = "";
	private String onOffType = "";
	private String onOffName = "";
	private String paperInfo = "";
	private String dlvryName = "";
	private String dlvryTelFn = "";
	private String dlvryTelMn = "";
	private String dlvryTelRn = "";
	private String dlvryMobileFn = "";
	private String dlvryMobileMn = "";
	private String dlvryMobileRn = "";
	private String dlvryAddr = "";
	private String dlvryAddrDtl = "";
	private String dlvryMemo = "";
	private String reqPayType = "";
	private String reqBank = "";
	private String reqAccountNumber = "";
	private String reqAccountName = "";
	private String reqAccountRrn1 = "";
	private String reqAccountRrn2 = "";
	private String reqCardCompany = "";
	private String reqCardNo = "";
	private String reqCardNo1 = "";
	private String reqCardNo2 = "";
	private String reqCardNo3 = "";
	private String reqCardNo4 = "";
	private String reqCardMm = "";
	private String reqCardYy = "";
	
	private String clausePriCollectFlag = "";
	private String clausePriOfferFlag = "";
	private String clauseEssCollectFlag = "";
	private String clausePriTrustFlag = "";
	private String clausePriAdFlag = "";
	private String clauseConfidenceFlag = "";

	private String reqCardName = "";
	private String reqCardRrn1 = "";
	private String reqCardRrn2 = "";
	private String reqBuyType = "";
	private String reqModelName = "";
	private String reqWantNumber = "";
	private String reqWantNumber2 = "";
	private String reqWantNumber3 = "";
	private String reqGuideFlag = "";
	private String reqGuideFn = "";
	private String reqGuideRn = "";
	private String reqGuideMn = "";
	private String reqWireType = "";
	private String moveMobileFn = "";
	private String moveMobileMn = "";
	private String moveMobileRn = "";
	private String moveCompany = "";
	private String moveThismonthPayType = "";
	private String moveAllotmentStat = "";
	private String moveRefundAgreeFlag = "";
	private String moveAuthType = "";
	
	
	private String minorAgentName = "";
	private String minorAgentRelation = "";
	private String minorAgentRrn1 = "";
	private String minorAgentRrn2 = "";
	private String minorAgentTelFn = "";
	private String minorAgentTelMn = "";
	private String minorAgentTelRn = "";
	
	private String requestKey = "";
	private String additionKey = "";
	private String additionName = "";
	
	private String cstmrPost1 = "";
	private String cstmrPost2 = "";
	private String minorAgentRrn = "";
	
	private String reqAccountRrn = "";

	private String orgnNm;
	private String typeCd;
	private String hghrOrgnCd;
	private String cntpntShopId;
	
	private String requestMemo;
	private String salePlcyCd;
	
	
	// 2015-04-20 본인인증추가
	private String selfInqryAgrmYn;		// 본인조회동의
	private String selfCertType;		// 인증유형
	private String selfIssuExprDt;		// 발급/만료일자
	private String selfIssuNum;			// 발급번호
	private String minorSelfInqryAgrmYn;	// 대리인본인조회동의
	private String minorSelfCertType;		// 대리인인증유형
	private String minorSelfIssuExprDt;		// 대리인발급/만료일자
	private String minorSelfIssuNum;		// 대리인발급번호
	
	
	// 지원금유형 추가
	private String sprtTp;
	
	// 추천인 추가
	private String recommendFlagNm;			// 추천인 구분명(RCP9011)
	private String recommendInfo;			// 추천인 정보
	
	// 2016-06-01, 상품구분추가
	private String prodType;
	
	private String strtDt;
	private String endDt;
	private String searchCd;
	private String searchVal;
	private String agntCd;
	
	// 2017-11-02 실 판매점 추가
	private String realShopNm;

	// 2017-12-05 유심종류 추가
	private String usimKindsCd;
	
	// 단말보험
	private String insrProdCd;
	
	//2020.12.10 엑셀업로드,저장 시 필요 변수 추가(filename,items)
	private String fileName;
	
	List<RcpListVO> items;
	
	
	private String sessionUserId;
	
	//2022-08-10 esim 여부 추가
	private String esimYn;

	// 2024-05-30 acen 여부 추가
	private String acenYn;
	
	// 2024-10-15 신청관리(일시납) 변수 추가
	private String payRstCd;
	private String ipAddr;
	
	// 2025-05-21 셀프개통 제외 체크박스 추가
	private String selfYn;
	
	// 2025-07-01 생년월일 검색 추가
	private String pBirthDay;
	private String pBirthDayVal;
	
	private String grpId;

	public String getUsimKindsCd() {
		return usimKindsCd;
	}

	public void setUsimKindsCd(String usimKindsCd) {
		this.usimKindsCd = usimKindsCd;
	}

	public String getRealShopNm() {
		return realShopNm;
	}

	public void setRealShopNm(String realShopNm) {
		this.realShopNm = realShopNm;
	}

	public String getAgntCd() {
		return agntCd;
	}

	public void setAgntCd(String agntCd) {
		this.agntCd = agntCd;
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

	/**
	 * @return the searchStartDt
	 */
	public String getSearchStartDt() {
		return searchStartDt;
	}

	/**
	 * @param searchStartDt the searchStartDt to set
	 */
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}

	/**
	 * @return the searchEndDt
	 */
	public String getSearchEndDt() {
		return searchEndDt;
	}

	/**
	 * @param searchEndDt the searchEndDt to set
	 */
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}

	public String getRequestMemo() {
		return requestMemo;
	}

	public void setRequestMemo(String requestMemo) {
		this.requestMemo = requestMemo;
	}

	public String getpReqInDayF() {
		return pReqInDayF;
	}

	public void setpReqInDayF(String pReqInDayF) {
		this.pReqInDayF = pReqInDayF;
	}

	public String getpReqInDayT() {
		return pReqInDayT;
	}

	public void setpReqInDayT(String pReqInDayT) {
		this.pReqInDayT = pReqInDayT;
	}

	public String getpServiceType() {
		return pServiceType;
	}

	public void setpServiceType(String pServiceType) {
		this.pServiceType = pServiceType;
	}

	public String getpOperType() {
		return pOperType;
	}

	public void setpOperType(String pOperType) {
		this.pOperType = pOperType;
	}

	public String getpAgentCode() {
		return pAgentCode;
	}

	public void setpAgentCode(String pAgentCode) {
		this.pAgentCode = pAgentCode;
	}

	public String getpRequestStateCode() {
		return pRequestStateCode;
	}

	public void setpRequestStateCode(String pRequestStateCode) {
		this.pRequestStateCode = pRequestStateCode;
	}

	public String getpOpenNoExist() {
		return pOpenNoExist;
	}

	public void setpOpenNoExist(String pOpenNoExist) {
		this.pOpenNoExist = pOpenNoExist;
	}

	public String getpSearchName() {
		return pSearchName;
	}

	public void setpSearchName(String pSearchName) {
		this.pSearchName = pSearchName;
	}

	public String getpSearchGbn() {
		return pSearchGbn;
	}

	public void setpSearchGbn(String pSearchGbn) {
		this.pSearchGbn = pSearchGbn;
	}

	public String getpPstate() {
		return pPstate;
	}

	public void setpPstate(String pPstate) {
		this.pPstate = pPstate;
	}

	public String getCdGbn() {
		return cdGbn;
	}

	public void setCdGbn(String cdGbn) {
		this.cdGbn = cdGbn;
	}

	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}

	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}

	public String getCdId() {
		return cdId;
	}

	public void setCdId(String cdId) {
		this.cdId = cdId;
	}

	public String getpFax() {
		return pFax;
	}

	public void setpFax(String pFax) {
		this.pFax = pFax;
	}

	public String getSetEntrustResRrn() {
		return setEntrustResRrn;
	}

	public void setSetEntrustResRrn(String setEntrustResRrn) {
		this.setEntrustResRrn = setEntrustResRrn;
	}

	public String getReqCardRrn() {
		return reqCardRrn;
	}

	public void setReqCardRrn(String reqCardRrn) {
		this.reqCardRrn = reqCardRrn;
	}

	public String getCstmrForeignerPn() {
		return cstmrForeignerPn;
	}

	public void setCstmrForeignerPn(String cstmrForeignerPn) {
		this.cstmrForeignerPn = cstmrForeignerPn;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getCstmrName() {
		return cstmrName;
	}

	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getCstmrMobile() {
		return cstmrMobile;
	}

	public void setCstmrMobile(String cstmrMobile) {
		this.cstmrMobile = cstmrMobile;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getSocName() {
		return socName;
	}

	public void setSocName(String socName) {
		this.socName = socName;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getReqInDay() {
		return reqInDay;
	}

	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}

	public String getRequestStateName() {
		return requestStateName;
	}

	public void setRequestStateName(String requestStateName) {
		this.requestStateName = requestStateName;
	}

	public String getOpenReqDate() {
		return openReqDate;
	}

	public void setOpenReqDate(String openReqDate) {
		this.openReqDate = openReqDate;
	}

	public String getPstateName() {
		return pstateName;
	}

	public void setPstateName(String pstateName) {
		this.pstateName = pstateName;
	}

	public String getCstmrType() {
		return cstmrType;
	}

	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSocCode() {
		return socCode;
	}

	public void setSocCode(String socCode) {
		this.socCode = socCode;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getCstmrNativeRrn1() {
		return cstmrNativeRrn1;
	}

	public void setCstmrNativeRrn1(String cstmrNativeRrn1) {
		this.cstmrNativeRrn1 = cstmrNativeRrn1;
	}

	public String getCstmrNativeRrn2() {
		return cstmrNativeRrn2;
	}

	public void setCstmrNativeRrn2(String cstmrNativeRrn2) {
		this.cstmrNativeRrn2 = cstmrNativeRrn2;
	}

	public String getCstmrForeignerPn1() {
		return cstmrForeignerPn1;
	}

	public void setCstmrForeignerPn1(String cstmrForeignerPn1) {
		this.cstmrForeignerPn1 = cstmrForeignerPn1;
	}

	public String getCstmrForeignerPn2() {
		return cstmrForeignerPn2;
	}

	public void setCstmrForeignerPn2(String cstmrForeignerPn2) {
		this.cstmrForeignerPn2 = cstmrForeignerPn2;
	}

	public String getCstmrForeignerSdate() {
		return cstmrForeignerSdate;
	}

	public void setCstmrForeignerSdate(String cstmrForeignerSdate) {
		this.cstmrForeignerSdate = cstmrForeignerSdate;
	}

	public String getCstmrForeignerEdate() {
		return cstmrForeignerEdate;
	}

	public void setCstmrForeignerEdate(String cstmrForeignerEdate) {
		this.cstmrForeignerEdate = cstmrForeignerEdate;
	}

	public String getCstmrJuridicalCname() {
		return cstmrJuridicalCname;
	}

	public void setCstmrJuridicalCname(String cstmrJuridicalCname) {
		this.cstmrJuridicalCname = cstmrJuridicalCname;
	}

	public String getCstmrJuridicalRrn1() {
		return cstmrJuridicalRrn1;
	}

	public void setCstmrJuridicalRrn1(String cstmrJuridicalRrn1) {
		this.cstmrJuridicalRrn1 = cstmrJuridicalRrn1;
	}

	public String getCstmrJuridicalRrn2() {
		return cstmrJuridicalRrn2;
	}

	public void setCstmrJuridicalRrn2(String cstmrJuridicalRrn2) {
		this.cstmrJuridicalRrn2 = cstmrJuridicalRrn2;
	}

	public String getCstmrJuridicalNumber1() {
		return cstmrJuridicalNumber1;
	}

	public void setCstmrJuridicalNumber1(String cstmrJuridicalNumber1) {
		this.cstmrJuridicalNumber1 = cstmrJuridicalNumber1;
	}

	public String getCstmrJuridicalNumber2() {
		return cstmrJuridicalNumber2;
	}

	public void setCstmrJuridicalNumber2(String cstmrJuridicalNumber2) {
		this.cstmrJuridicalNumber2 = cstmrJuridicalNumber2;
	}

	public String getCstmrJuridicalNumber3() {
		return cstmrJuridicalNumber3;
	}

	public void setCstmrJuridicalNumber3(String cstmrJuridicalNumber3) {
		this.cstmrJuridicalNumber3 = cstmrJuridicalNumber3;
	}

	public String getCstmrPrivateCname() {
		return cstmrPrivateCname;
	}

	public void setCstmrPrivateCname(String cstmrPrivateCname) {
		this.cstmrPrivateCname = cstmrPrivateCname;
	}

	public String getCstmrPrivateNumber1() {
		return cstmrPrivateNumber1;
	}

	public void setCstmrPrivateNumber1(String cstmrPrivateNumber1) {
		this.cstmrPrivateNumber1 = cstmrPrivateNumber1;
	}

	public String getCstmrPrivateNumber2() {
		return cstmrPrivateNumber2;
	}

	public void setCstmrPrivateNumber2(String cstmrPrivateNumber2) {
		this.cstmrPrivateNumber2 = cstmrPrivateNumber2;
	}

	public String getCstmrPrivateNumber3() {
		return cstmrPrivateNumber3;
	}

	public void setCstmrPrivateNumber3(String cstmrPrivateNumber3) {
		this.cstmrPrivateNumber3 = cstmrPrivateNumber3;
	}

	public String getOrgnId() {
		return orgnId;
	}

	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

	public String getPrdtId() {
		return prdtId;
	}

	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}

	public String getPrdtSctnCdNm() {
		return prdtSctnCdNm;
	}

	public void setPrdtSctnCdNm(String prdtSctnCdNm) {
		this.prdtSctnCdNm = prdtSctnCdNm;
	}

	public String getChrgPrdtId() {
		return chrgPrdtId;
	}

	public void setChrgPrdtId(String chrgPrdtId) {
		this.chrgPrdtId = chrgPrdtId;
	}

	public String getAgrmTrm() {
		return agrmTrm;
	}

	public void setAgrmTrm(String agrmTrm) {
		this.agrmTrm = agrmTrm;
	}

	public String getPrdtColrNm() {
		return prdtColrNm;
	}

	public void setPrdtColrNm(String prdtColrNm) {
		this.prdtColrNm = prdtColrNm;
	}

	public String getOldYn() {
		return oldYn;
	}

	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}

	public String getInstNom() {
		return instNom;
	}

	public void setInstNom(String instNom) {
		this.instNom = instNom;
	}

	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}

	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}

	public String getReqUsimSn() {
		return reqUsimSn;
	}

	public void setReqUsimSn(String reqUsimSn) {
		this.reqUsimSn = reqUsimSn;
	}

	public String getReqPhoneSn() {
		return reqPhoneSn;
	}

	public void setReqPhoneSn(String reqPhoneSn) {
		this.reqPhoneSn = reqPhoneSn;
	}

	public String getCstmrMail1() {
		return cstmrMail1;
	}

	public void setCstmrMail1(String cstmrMail1) {
		this.cstmrMail1 = cstmrMail1;
	}

	public String getCstmrMail2() {
		return cstmrMail2;
	}

	public void setCstmrMail2(String cstmrMail2) {
		this.cstmrMail2 = cstmrMail2;
	}

	public String getCstmrMail3() {
		return cstmrMail3;
	}

	public void setCstmrMail3(String cstmrMail3) {
		this.cstmrMail3 = cstmrMail3;
	}

	public String getCstmrMailReceiveFlag() {
		return cstmrMailReceiveFlag;
	}

	public void setCstmrMailReceiveFlag(String cstmrMailReceiveFlag) {
		this.cstmrMailReceiveFlag = cstmrMailReceiveFlag;
	}

	public String getCstmrAddr() {
		return cstmrAddr;
	}

	public void setCstmrAddr(String cstmrAddr) {
		this.cstmrAddr = cstmrAddr;
	}

	public String getCstmrAddrDtl() {
		return cstmrAddrDtl;
	}

	public void setCstmrAddrDtl(String cstmrAddrDtl) {
		this.cstmrAddrDtl = cstmrAddrDtl;
	}

	public String getCstmrBillSendCode() {
		return cstmrBillSendCode;
	}

	public void setCstmrBillSendCode(String cstmrBillSendCode) {
		this.cstmrBillSendCode = cstmrBillSendCode;
	}

	public String getCstmrTelFn() {
		return cstmrTelFn;
	}

	public void setCstmrTelFn(String cstmrTelFn) {
		this.cstmrTelFn = cstmrTelFn;
	}

	public String getCstmrTelMn() {
		return cstmrTelMn;
	}

	public void setCstmrTelMn(String cstmrTelMn) {
		this.cstmrTelMn = cstmrTelMn;
	}

	public String getCstmrTelRn() {
		return cstmrTelRn;
	}

	public void setCstmrTelRn(String cstmrTelRn) {
		this.cstmrTelRn = cstmrTelRn;
	}

	public String getCstmrMobileFn() {
		return cstmrMobileFn;
	}

	public void setCstmrMobileFn(String cstmrMobileFn) {
		this.cstmrMobileFn = cstmrMobileFn;
	}

	public String getCstmrMobileMn() {
		return cstmrMobileMn;
	}

	public void setCstmrMobileMn(String cstmrMobileMn) {
		this.cstmrMobileMn = cstmrMobileMn;
	}

	public String getCstmrMobileRn() {
		return cstmrMobileRn;
	}

	public void setCstmrMobileRn(String cstmrMobileRn) {
		this.cstmrMobileRn = cstmrMobileRn;
	}

	public String getOnlineAuthType() {
		return onlineAuthType;
	}

	public void setOnlineAuthType(String onlineAuthType) {
		this.onlineAuthType = onlineAuthType;
	}

	public String getOnlineAuthInfo() {
		return onlineAuthInfo;
	}

	public void setOnlineAuthInfo(String onlineAuthInfo) {
		this.onlineAuthInfo = onlineAuthInfo;
	}

	public String getPstate() {
		return pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public String getRequestStateCode() {
		return requestStateCode;
	}

	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}

	public String getOnOffType() {
		return onOffType;
	}

	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}

	public String getPaperInfo() {
		return paperInfo;
	}

	public void setPaperInfo(String paperInfo) {
		this.paperInfo = paperInfo;
	}

	public String getDlvryName() {
		return dlvryName;
	}

	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}

	public String getDlvryTelFn() {
		return dlvryTelFn;
	}

	public void setDlvryTelFn(String dlvryTelFn) {
		this.dlvryTelFn = dlvryTelFn;
	}

	public String getDlvryTelMn() {
		return dlvryTelMn;
	}

	public void setDlvryTelMn(String dlvryTelMn) {
		this.dlvryTelMn = dlvryTelMn;
	}

	public String getDlvryTelRn() {
		return dlvryTelRn;
	}

	public void setDlvryTelRn(String dlvryTelRn) {
		this.dlvryTelRn = dlvryTelRn;
	}

	public String getDlvryMobileFn() {
		return dlvryMobileFn;
	}

	public void setDlvryMobileFn(String dlvryMobileFn) {
		this.dlvryMobileFn = dlvryMobileFn;
	}

	public String getDlvryMobileMn() {
		return dlvryMobileMn;
	}

	public void setDlvryMobileMn(String dlvryMobileMn) {
		this.dlvryMobileMn = dlvryMobileMn;
	}

	public String getDlvryMobileRn() {
		return dlvryMobileRn;
	}

	public void setDlvryMobileRn(String dlvryMobileRn) {
		this.dlvryMobileRn = dlvryMobileRn;
	}

	public String getDlvryAddr() {
		return dlvryAddr;
	}

	public void setDlvryAddr(String dlvryAddr) {
		this.dlvryAddr = dlvryAddr;
	}

	public String getDlvryAddrDtl() {
		return dlvryAddrDtl;
	}

	public void setDlvryAddrDtl(String dlvryAddrDtl) {
		this.dlvryAddrDtl = dlvryAddrDtl;
	}

	public String getDlvryMemo() {
		return dlvryMemo;
	}

	public void setDlvryMemo(String dlvryMemo) {
		this.dlvryMemo = dlvryMemo;
	}

	public String getReqPayType() {
		return reqPayType;
	}

	public void setReqPayType(String reqPayType) {
		this.reqPayType = reqPayType;
	}

	public String getReqBank() {
		return reqBank;
	}

	public void setReqBank(String reqBank) {
		this.reqBank = reqBank;
	}

	public String getReqAccountNumber() {
		return reqAccountNumber;
	}

	public void setReqAccountNumber(String reqAccountNumber) {
		this.reqAccountNumber = reqAccountNumber;
	}

	public String getReqAccountName() {
		return reqAccountName;
	}

	public void setReqAccountName(String reqAccountName) {
		this.reqAccountName = reqAccountName;
	}

	public String getReqAccountRrn1() {
		return reqAccountRrn1;
	}

	public void setReqAccountRrn1(String reqAccountRrn1) {
		this.reqAccountRrn1 = reqAccountRrn1;
	}

	public String getReqAccountRrn2() {
		return reqAccountRrn2;
	}

	public void setReqAccountRrn2(String reqAccountRrn2) {
		this.reqAccountRrn2 = reqAccountRrn2;
	}

	public String getReqCardCompany() {
		return reqCardCompany;
	}

	public void setReqCardCompany(String reqCardCompany) {
		this.reqCardCompany = reqCardCompany;
	}

	public String getReqCardNo() {
		return reqCardNo;
	}

	public void setReqCardNo(String reqCardNo) {
		this.reqCardNo = reqCardNo;
	}

	public String getReqCardNo1() {
		return reqCardNo1;
	}

	public void setReqCardNo1(String reqCardNo1) {
		this.reqCardNo1 = reqCardNo1;
	}

	public String getReqCardNo2() {
		return reqCardNo2;
	}

	public void setReqCardNo2(String reqCardNo2) {
		this.reqCardNo2 = reqCardNo2;
	}

	public String getReqCardNo3() {
		return reqCardNo3;
	}

	public void setReqCardNo3(String reqCardNo3) {
		this.reqCardNo3 = reqCardNo3;
	}

	public String getReqCardNo4() {
		return reqCardNo4;
	}

	public void setReqCardNo4(String reqCardNo4) {
		this.reqCardNo4 = reqCardNo4;
	}

	public String getReqCardMm() {
		return reqCardMm;
	}

	public void setReqCardMm(String reqCardMm) {
		this.reqCardMm = reqCardMm;
	}

	public String getReqCardYy() {
		return reqCardYy;
	}

	public void setReqCardYy(String reqCardYy) {
		this.reqCardYy = reqCardYy;
	}

	public String getClausePriCollectFlag() {
		return clausePriCollectFlag;
	}

	public void setClausePriCollectFlag(String clausePriCollectFlag) {
		this.clausePriCollectFlag = clausePriCollectFlag;
	}

	public String getClausePriOfferFlag() {
		return clausePriOfferFlag;
	}

	public void setClausePriOfferFlag(String clausePriOfferFlag) {
		this.clausePriOfferFlag = clausePriOfferFlag;
	}

	public String getClauseEssCollectFlag() {
		return clauseEssCollectFlag;
	}

	public void setClauseEssCollectFlag(String clauseEssCollectFlag) {
		this.clauseEssCollectFlag = clauseEssCollectFlag;
	}

	public String getClausePriTrustFlag() {
		return clausePriTrustFlag;
	}

	public void setClausePriTrustFlag(String clausePriTrustFlag) {
		this.clausePriTrustFlag = clausePriTrustFlag;
	}

	public String getClausePriAdFlag() {
		return clausePriAdFlag;
	}

	public void setClausePriAdFlag(String clausePriAdFlag) {
		this.clausePriAdFlag = clausePriAdFlag;
	}

	public String getClauseConfidenceFlag() {
		return clauseConfidenceFlag;
	}

	public void setClauseConfidenceFlag(String clauseConfidenceFlag) {
		this.clauseConfidenceFlag = clauseConfidenceFlag;
	}

	public String getReqCardName() {
		return reqCardName;
	}

	public void setReqCardName(String reqCardName) {
		this.reqCardName = reqCardName;
	}

	public String getReqCardRrn1() {
		return reqCardRrn1;
	}

	public void setReqCardRrn1(String reqCardRrn1) {
		this.reqCardRrn1 = reqCardRrn1;
	}

	public String getReqCardRrn2() {
		return reqCardRrn2;
	}

	public void setReqCardRrn2(String reqCardRrn2) {
		this.reqCardRrn2 = reqCardRrn2;
	}

	public String getReqBuyType() {
		return reqBuyType;
	}

	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}

	public String getReqModelName() {
		return reqModelName;
	}

	public void setReqModelName(String reqModelName) {
		this.reqModelName = reqModelName;
	}

	public String getReqWantNumber() {
		return reqWantNumber;
	}

	public void setReqWantNumber(String reqWantNumber) {
		this.reqWantNumber = reqWantNumber;
	}

	public String getReqWantNumber2() {
		return reqWantNumber2;
	}

	public void setReqWantNumber2(String reqWantNumber2) {
		this.reqWantNumber2 = reqWantNumber2;
	}

	public String getReqWantNumber3() {
		return reqWantNumber3;
	}

	public void setReqWantNumber3(String reqWantNumber3) {
		this.reqWantNumber3 = reqWantNumber3;
	}

	public String getReqGuideFlag() {
		return reqGuideFlag;
	}

	public void setReqGuideFlag(String reqGuideFlag) {
		this.reqGuideFlag = reqGuideFlag;
	}

	public String getReqGuideFn() {
		return reqGuideFn;
	}

	public void setReqGuideFn(String reqGuideFn) {
		this.reqGuideFn = reqGuideFn;
	}

	public String getReqGuideRn() {
		return reqGuideRn;
	}

	public void setReqGuideRn(String reqGuideRn) {
		this.reqGuideRn = reqGuideRn;
	}

	public String getReqGuideMn() {
		return reqGuideMn;
	}

	public void setReqGuideMn(String reqGuideMn) {
		this.reqGuideMn = reqGuideMn;
	}

	public String getReqWireType() {
		return reqWireType;
	}

	public void setReqWireType(String reqWireType) {
		this.reqWireType = reqWireType;
	}

	public String getMoveMobileFn() {
		return moveMobileFn;
	}

	public void setMoveMobileFn(String moveMobileFn) {
		this.moveMobileFn = moveMobileFn;
	}

	public String getMoveMobileMn() {
		return moveMobileMn;
	}

	public void setMoveMobileMn(String moveMobileMn) {
		this.moveMobileMn = moveMobileMn;
	}

	public String getMoveMobileRn() {
		return moveMobileRn;
	}

	public void setMoveMobileRn(String moveMobileRn) {
		this.moveMobileRn = moveMobileRn;
	}

	public String getMoveCompany() {
		return moveCompany;
	}

	public void setMoveCompany(String moveCompany) {
		this.moveCompany = moveCompany;
	}

	public String getMoveThismonthPayType() {
		return moveThismonthPayType;
	}

	public void setMoveThismonthPayType(String moveThismonthPayType) {
		this.moveThismonthPayType = moveThismonthPayType;
	}

	public String getMoveAllotmentStat() {
		return moveAllotmentStat;
	}

	public void setMoveAllotmentStat(String moveAllotmentStat) {
		this.moveAllotmentStat = moveAllotmentStat;
	}

	public String getMoveRefundAgreeFlag() {
		return moveRefundAgreeFlag;
	}

	public void setMoveRefundAgreeFlag(String moveRefundAgreeFlag) {
		this.moveRefundAgreeFlag = moveRefundAgreeFlag;
	}

	public String getMoveAuthType() {
		return moveAuthType;
	}

	public void setMoveAuthType(String moveAuthType) {
		this.moveAuthType = moveAuthType;
	}

	public String getMinorAgentName() {
		return minorAgentName;
	}

	public void setMinorAgentName(String minorAgentName) {
		this.minorAgentName = minorAgentName;
	}

	public String getMinorAgentRelation() {
		return minorAgentRelation;
	}

	public void setMinorAgentRelation(String minorAgentRelation) {
		this.minorAgentRelation = minorAgentRelation;
	}

	public String getMinorAgentRrn1() {
		return minorAgentRrn1;
	}

	public void setMinorAgentRrn1(String minorAgentRrn1) {
		this.minorAgentRrn1 = minorAgentRrn1;
	}

	public String getMinorAgentRrn2() {
		return minorAgentRrn2;
	}

	public void setMinorAgentRrn2(String minorAgentRrn2) {
		this.minorAgentRrn2 = minorAgentRrn2;
	}

	public String getMinorAgentTelFn() {
		return minorAgentTelFn;
	}

	public void setMinorAgentTelFn(String minorAgentTelFn) {
		this.minorAgentTelFn = minorAgentTelFn;
	}

	public String getMinorAgentTelMn() {
		return minorAgentTelMn;
	}

	public void setMinorAgentTelMn(String minorAgentTelMn) {
		this.minorAgentTelMn = minorAgentTelMn;
	}

	public String getMinorAgentTelRn() {
		return minorAgentTelRn;
	}

	public void setMinorAgentTelRn(String minorAgentTelRn) {
		this.minorAgentTelRn = minorAgentTelRn;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getAdditionKey() {
		return additionKey;
	}

	public void setAdditionKey(String additionKey) {
		this.additionKey = additionKey;
	}

	public String getAdditionName() {
		return additionName;
	}

	public void setAdditionName(String additionName) {
		this.additionName = additionName;
	}

	public String getCstmrPost1() {
		return cstmrPost1;
	}

	public void setCstmrPost1(String cstmrPost1) {
		this.cstmrPost1 = cstmrPost1;
	}

	public String getCstmrPost2() {
		return cstmrPost2;
	}

	public void setCstmrPost2(String cstmrPost2) {
		this.cstmrPost2 = cstmrPost2;
	}

	public String getMinorAgentRrn() {
		return minorAgentRrn;
	}

	public void setMinorAgentRrn(String minorAgentRrn) {
		this.minorAgentRrn = minorAgentRrn;
	}

	public String getReqAccountRrn() {
		return reqAccountRrn;
	}

	public void setReqAccountRrn(String reqAccountRrn) {
		this.reqAccountRrn = reqAccountRrn;
	}

	public String getOrgnNm() {
		return orgnNm;
	}

	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getHghrOrgnCd() {
		return hghrOrgnCd;
	}

	public void setHghrOrgnCd(String hghrOrgnCd) {
		this.hghrOrgnCd = hghrOrgnCd;
	}

	public String getCntpntShopId() {
		return cntpntShopId;
	}

	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}

	public String getSalePlcyCd() {
		return salePlcyCd;
	}

	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}

	public String getSelfInqryAgrmYn() {
		return selfInqryAgrmYn;
	}

	public void setSelfInqryAgrmYn(String selfInqryAgrmYn) {
		this.selfInqryAgrmYn = selfInqryAgrmYn;
	}

	public String getSelfCertType() {
		return selfCertType;
	}

	public void setSelfCertType(String selfCertType) {
		this.selfCertType = selfCertType;
	}

	public String getSelfIssuExprDt() {
		return selfIssuExprDt;
	}

	public void setSelfIssuExprDt(String selfIssuExprDt) {
		this.selfIssuExprDt = selfIssuExprDt;
	}

	public String getSelfIssuNum() {
		return selfIssuNum;
	}

	public void setSelfIssuNum(String selfIssuNum) {
		this.selfIssuNum = selfIssuNum;
	}

	public String getMinorSelfInqryAgrmYn() {
		return minorSelfInqryAgrmYn;
	}

	public void setMinorSelfInqryAgrmYn(String minorSelfInqryAgrmYn) {
		this.minorSelfInqryAgrmYn = minorSelfInqryAgrmYn;
	}

	public String getMinorSelfCertType() {
		return minorSelfCertType;
	}

	public void setMinorSelfCertType(String minorSelfCertType) {
		this.minorSelfCertType = minorSelfCertType;
	}

	public String getMinorSelfIssuExprDt() {
		return minorSelfIssuExprDt;
	}

	public void setMinorSelfIssuExprDt(String minorSelfIssuExprDt) {
		this.minorSelfIssuExprDt = minorSelfIssuExprDt;
	}

	public String getMinorSelfIssuNum() {
		return minorSelfIssuNum;
	}

	public void setMinorSelfIssuNum(String minorSelfIssuNum) {
		this.minorSelfIssuNum = minorSelfIssuNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOnOffName() {
		return onOffName;
	}

	public void setOnOffName(String onOffName) {
		this.onOffName = onOffName;
	}

	public String getSprtTp() {
		return sprtTp;
	}

	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}

	public String getRecommendFlagNm() {
		return recommendFlagNm;
	}

	public void setRecommendFlagNm(String recommendFlagNm) {
		this.recommendFlagNm = recommendFlagNm;
	}

	public String getRecommendInfo() {
		return recommendInfo;
	}

	public void setRecommendInfo(String recommendInfo) {
		this.recommendInfo = recommendInfo;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String getInsrProdCd() {
		return insrProdCd;
	}

	public void setInsrProdCd(String insrProdCd) {
		this.insrProdCd = insrProdCd;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public List<RcpListVO> getItems() {
		return items;
	}

	public void setItems(List<RcpListVO> items) {
		this.items = items;
	}

	public String getSessionUserId() {
		return sessionUserId;
	}

	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}

	public String getEsimYn() {
		return esimYn;
	}

	public void setEsimYn(String esimYn) {
		this.esimYn = esimYn;
	}

	public String getAcenYn() {
		return acenYn;
	}

	public void setAcenYn(String acenYn) {
		this.acenYn = acenYn;
	}

	public String getPayRstCd() {
		return payRstCd;
	}

	public void setPayRstCd(String payRstCd) {
		this.payRstCd = payRstCd;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getSelfYn() {
		return selfYn;
	}

	public void setSelfYn(String selfYn) {
		this.selfYn = selfYn;
	}

	public String getpBirthDay() {
		return pBirthDay;
	}

	public void setpBirthDay(String pBirthDay) {
		this.pBirthDay = pBirthDay;
	}

	public String getpBirthDayVal() {
		return pBirthDayVal;
	}

	public void setpBirthDayVal(String pBirthDayVal) {
		this.pBirthDayVal = pBirthDayVal;
	}

	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	
}
