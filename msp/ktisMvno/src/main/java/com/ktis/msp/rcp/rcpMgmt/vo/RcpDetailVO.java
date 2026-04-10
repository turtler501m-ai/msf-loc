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

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 * @Description : SampleDefaultVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class RcpDetailVO extends BaseVo implements Serializable {
	
	//serialVersionUID
	private static final long serialVersionUID = 6760215222094556206L;
	
	private String requestKey 												;
	private String managerCode                       ;
	private String agentCode                         ;
	private String serviceType                       ;
	private String operType                          ;
	private String cstmrType                         ;
	private String resCode                           ;
	private String resMsg                            ;
	private String resNo                             ;
	private String clausePriCollectFlag = "N"              ;
	private String clausePriOfferFlag   = "N"              ;
	private String clauseEssCollectFlag  = "N"             ;
	private String clausePriTrustFlag  = "N"               ;
	private String clausePriAdFlag  = "N"                  ;
	private String clauseConfidenceFlag   = "N"            ;
	private String onlineAuthType                    ;
	private String onlineAuthInfo                    ;
	private String pstate                            ;
	private String requestStateCode                  ;
	private String openNo                            ;
	private String file01                            ;
	private String file01Mask                        ;
	private String faxyn                             ;
	private String faxnum                            ;
	private String scanId                            ;
	private String rip                               ;
	private String reqWantNumber                     ;
	private String reqWantNumber2                    ;
	private String reqWantNumber3                    ;
	private String reqBuyType                        ;
	private String reqModelName                      ;
	private String reqUsimName                      ;
	private String reqModelColor                     ;
	private String reqPhoneSn                        ;
	private String reqUsimSn                         ;
	private String reqPayType                        ;
	private String additionName                      ;
	private String shopCd                            ;
	private String phonePayment                      ;
	private String additionPrice                     ;
	private String spcCode                           ;
	private String disPrmtId                         ;
	private String cntpntShopId                      ;
	private String cstmrForeignerNation              ;
	private String cstmrForeignerPn                  ;
	private String cstmrForeignerRrn                 ;
	private String cstmrForeignerSdate               ;
	private String cstmrForeignerEdate               ;
	private String cstmrPrivateCname                 ;
	private String cstmrPrivateNumber                ;
	private String cstmrJuridicalCname               ;
	private String cstmrJuridicalRrn                 ;
	private String cstmrJuridicalNumber              ;
	private String cstmrTel                        ;
	private String cstmrTelFn                        ;
	private String cstmrTelMn                        ;
	private String cstmrTelRn                        ;
	private String cstmrMobile                     ;
	private String cstmrMobileFn                     ;
	private String cstmrMobileMn                     ;
	private String cstmrMobileRn                     ;
	private String cstmrPost                         ;
	private String cstmrAddr                         ;
	private String cstmrAddrDtl                      ;
	private String cstmrAddrBjd                      ;
	private String cstmrBillSendCode                 ;
	private String cstmrMail                         ;
	private String cstmrMailReceiveFlag              ;
	private String cstmrVisitType                    ;
	private String cstmrReceiveTelFn                 ;
	private String cstmrReceiveTelNm                 ;
	private String cstmrReceiveTelRn                 ;
	private String othersPaymentAg = "N";
	private String othersPaymentNm                   ;
	private String othersPaymentRrn                  ;
	private String othersPaymentRelation             ;
	private String othersPaymentRnm                  ;
	private String cstmrName                         ;
	private String cstmrNativeRrn                    ;
	private String cstmrForeignerDod                 ;
	private String cstmrForeignerBirth               ;
	private String modelId                           ;
	private String instNom                           ;
	private String modelInstallment                  ;
	private String salePlcyCd                        ;
	private String recycleYn                         ;
	private String modelDiscount1                    ;
	private String modelDiscount2                    ;
	private String modelDiscount3                    ;
	private String socCode                           ;
	private String usimPriceType                     ;
	private String joinPriceType                     ;
	private String joinPrice                         ;
	private String usimPrice                         ;
	private String modelPrice                        ;
	private String addtionService                    ;
	private String addtionServiceSum                 ;
	private String enggMnthCnt                       ;
	private String modelPriceVat                     ;
	private String dlvryName                         ;
	private String dlvryTel                        ;
	private String dlvryTelFn                        ;
	private String dlvryTelMn                        ;
	private String dlvryTelRn                        ;
	private String dlvryMobile                     ;
	private String dlvryMobileFn                     ;
	private String dlvryMobileMn                     ;
	private String dlvryMobileRn                     ;
	private String dlvryPost                         ;
	private String dlvryAddr                         ;
	private String dlvryAddrDtl                      ;
	private String dlvryAddrBjd                      ;
	private String dlvryMemo                         ;
	private String dlvryType                         ;
	private String tbCd                              ;
	private String reqBank                           ;
	private String reqAccountName                    ;
	private String reqAccountRrn                     ;
	private String reqAccountRelation                ;
	private String reqAccountNumber                  ;
	private String reqCardName                       ;
	private String reqCardRrn                        ;
	private String reqCardCompany                    ;
	private String reqCardNo                         ;
	private String reqCardYy                         ;
	private String reqCardMm                         ;
	private String reqWireType                       ;
	private String sysRdate                          ;
	private String reqPayOtherFlag = "N"             ;
	private String reqPayOtherTelFn                  ;
	private String reqPayOtherTelMn                  ;
	private String reqPayOtherTelRn                  ;
	private String additionKey                       ;
	private String moveCompany                       ;
	private String moveMobile                      ;
	private String moveMobileFn                      ;
	private String moveMobileMn                      ;
	private String moveMobileRn                      ;
	private String moveAuthType                      ;
	private String moveAuthNumber                    ;
	private String moveThismonthPayType              ;
	private String moveAllotmentStat                 ;
	private String moveRefundAgreeFlag               ;
	private String movePenalty						 ;
	private String sysRdaterequestKey                ;
	private String reqGuide                        ;
	private String reqGuideMn                        ;
	private String minorAgentRrn                     ;
	private String jrdclAgentRrn                     ;
	private String entrustReqNm                      ;
	private String entrustResNm                      ;
	private String entrustReqRelation                ;
	private String entrustResRrn                     ;
	private String entrustResTelFn                   ;
	private String entrustResTelMn                   ;
	private String entrustResTelRn                   ;
	private String requestStateKey                   ;
	private String dlvryNo                           ;
	private String memo                              ;
	private String rid                               ;
	private String viewFlag                          ;
	private String reqAc01Amount                     ;
	private String reqAc02Amount                     ;
	
	
	private String mnfctId			                  ;
	private String prdtId			                  ;
	private String salePlcyNm	                  ;
	private String prdtSctnCd			              ;
	private String cstmrNativeRrn1	              ;
	private String cstmrNativeRrn2		            ;
	private String cstmrForeignerRrn1	          ;
	private String cstmrForeignerRrn2		        ;
	private String cstmrJuridicalRrn1	          ;
	private String cstmrJuridicalRrn2		        ;
	private String cstmrJuridicalNumber1	        ;
	private String cstmrJuridicalNumber2	        ;
	private String cstmrJuridicalNumber3	        ;
	private String cstmrPrivateNumber1	          ;
	private String cstmrPrivateNumber2	          ;
	private String cstmrPrivateNumber3	          ;
	private String cstmrMail1	                  ;
	private String cstmrMail2	                  ;
	private String cstmrMail3	                  ;
	private String reqInDay			                ;
	private String onOffType			                ;
	private String faxYn	                        ;
	private String faxnum1	                      ;
	private String faxnum2	                      ;
	private String faxnum3	                      ;
	private String reqCardNo1	                  ;
	private String reqCardNo2	                  ;
	private String reqCardNo3	                  ;
	private String reqCardNo4                    ;
	private String reqAccountRrn1	              ;
	private String reqAccountRrn2		            ;
	private String othersPaymentAgClone = "N"			    ;
	private String reqCardRrn1	                  ;
	private String reqCardRrn2		                ;
	private String reqAcType			                ;
	private String reqAc01Balance			          ;
	private String reqAc02Day			              ;
	private String reqAcAmount			              ;
	private String reqModelCd		                ;
	private String reqModelColorCd		            ;
	private String reqGuideFlag = "N"	                ;
	private String reqGuideFn	                  ;
	private String reqGuideRn	                  ;
	private String reqWireType1	                ;
	private String reqWireType2                  ;
	private String reqWireType3			            ;
	private String btnRateFind			              ;
	private String agrmTrm			                  ;
	private String trmnlInfo			                ;
	private String minorAgentName			          ;
	private String minorAgentRelation			      ;
	private String minorAgentRrn1	              ;
	private String minorAgentRrn2		            ;
	private String minorAgentTel	              ;
	private String minorAgentTelFn	              ;
	private String minorAgentTelMn	              ;
	private String minorAgentTelRn	              ;
	private String jrdclAgentName			          ;
	private String jrdclAgentRrn1	              ;
	private String jrdclAgentRrn2		            ;
	private String jrdclAgentTel	              ;
	private String jrdclAgentTelFn	              ;
	private String jrdclAgentTelMn	              ;
	private String jrdclAgentTelRn	              ;
	private String nameChangeNm;
	private String nameChangeTelFn;
	private String nameChangeTelMn;
	private String nameChangeTelRn;
	private String nameChangeRrn;
	private String nameChangePinstallment;
	private String shopUsmId;
	
	private String btnType;
	private String scanIdBySearch;
	private String shopCdBySearch;
	
	private String requestMemo;
	
	//녹취파일ID
	private String fileId;
	private String recYn;
	
	private String soCd;
	private String nwBlckAgrmYn;
	private String appBlckAgrmYn;
	private String appCd;
	
	// 2015-04-20 본인인증추가
	private String selfInqryAgrmYn;		// 본인조회동의
	private String selfCertType;		// 인증유형
	private String selfIssuExprDt;		// 발급/만료일자
	private String selfIssuNum;			// 발급번호
	private String minorSelfInqryAgrmYn;	// 대리인본인조회동의
	private String minorSelfCertType;		// 대리인인증유형
	private String minorSelfIssuExprDt;		// 대리인발급/만료일자
	private String minorSelfIssuNum;		// 대리인발급번호
	
	//2015-08-12 기변정보
	private String dvcChgType; 
	private String dvcChgRsnCd;
	private String dvcChgRsnDtlCd;
	
	private String usimPayMthdCd;
	private String joinPayMthdCd;
	private String realMdlInstamt;
	
	private int statusKey;
	
	//2015-12-04 제주항공요금제관련정보
	private String cstmrJejuId;
	private String clauseJehuFlag;
	
	// 2016-01-11 신청정보 판매점, 지원금유형, 할인요금 추가
	private String shopNm;
	private String sprtTp;
	private String hndstAmt;
	private String maxDiscount3;
	private String dcAmt;
	private String addDcAmt;

	private String prodType;
	
	// 2016-09-27 추가
	private String contractNum;
	
	// v2017.02 신청관리 간소화 신청서 복사 대상 RequestKey
	private String trgtRequestKey;
	
	//v2017.03 신청관리 간소화 금용제휴 약관동의 추가 (동부 관련)
	private String clauseFinanceFlag;
	
	//v2017.05 신청관리 간소화 SMS인증번호 추가
	private String otpCheckYN;
	private String smsAuthInfo;
	
	//v2017.05 무료렌탈 오프라인 신청
	private String clauseRentalService;
	private String clauseRentalModelCp;
	private String clauseRentalModelCpPr;
	private String rentalModelCpAmt;
	private String rentalBaseAmt;
	private String rentalBaseDcAmt;
	private String rntlPrdtId;
	private String rntlPrdtCode;
	
	//v2017.08 프모로션 추가
	private String promotionCd;
	
	// 2017-11-02 실판매점 추가
	private String realShopNm;

	// 2017-12-05 유심종류 추가
	private String usimKindsCd;
	
	//v2018.02 고객혜택 추가
	private String bnftName;
	private String bnftKey;
	
	//개통간소화 이벤트코드
	private String appEventCd;
	
	// SRM18072675707, 단체보험
	private String insrCd;
	private String clauseInsuranceFlag;
	
	// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
	private String subStatus;
	private String lostYn;
	
	// 단말보험
	private String insrProdCd;
	private String clauseInsrProdFlag;
	
	// 추천인 정보
	private String recommendFlagCd; 
	private String recommendInfo; 

	
	//2020-12-14 visa cd 추가
	private String visaCd;
	
	//20220809 추가
	private String eid;
	private String imei1;
	private String imei2;
	
	//20220914 추가
	private String intmMdlId;
	private String intmSrlNo;
	
	//20230216 추가(eSIM Watch)
	private String prntsCtn;
	private String prntsCtnFn;
	private String prntsCtnMn;
	private String prntsCtnRn;
	private String billAcntNo;
	private String prntsContractNo;
	private String prntsBillNo;

	private String personalInfoCollectAgree;
	private String othersTrnsAgree;

	//20250730 추가
	private String othersTrnsKtAgree;
	private String othersAdReceiveAgree;
	
	//20231016 kt 인터넷 ID
	private String ktInterSvcNo;
	
	//APD 기적용
	private String apdSeq;				// apd SEQ
	private String evntCd;				// 업무 구분

	//20240306 K-Note
	private String frmpapId;			// 서식지ID

	//20240430 본인확인 CI 정보
	private String selfCstmrCi;			// 본인확인 CI 정보

	private String hubOrderSeq;			// 알뜰폰 허브 주문 정보 시퀀스

	private String clauseSensiCollectFlag;	/* 민감정보 수집동의*/
	private String clauseSensiOfferFlag;	/* 민감정보 제공동의*/

	private String jehuProdType;						// 요금제 제휴처
	private String clausePartnerOfferFlag; 	// 제휴사약관
	private String fathTrgYn; 				// 안면인증 대상여부
	private String clauseFathFlag; 			// 안면인증 동의여부
	private String fathTransacId; 			// 안면인증 트랜잭션ID
	private String fathCmpltNtfyDt; 		// 안면인증 완료일
    
    private String mcnResNo;                // 명의변경 예약번호

	private String fathMobile;
	private String fathMobileFn;			// 안면인증정보_휴대폰번호_앞자리
	private String fathMobileMn;			// 안면인증정보_휴대폰번호_중간자리
	private String fathMobileRn;			// 안면인증정보_휴대폰번호_뒷자리
	private String cpntId;					// 접점 아이디
	private String mngmAgncId;				// 관리대리점코드

	private String combineSoloType;			// 아무나 SOLO 여부
	private String combineSoloFlag;			// 아무나 SOLO 동의여부
	
	private String linkTypeCd;				//추천인 링크유형코드
	private String openMthdCd;				//개통방법코드 
	private String commendSocCode01;	//추천요금제 코드1
	private String commendSocCode02;	//추천요금제 코드2
	private String commendSocCode03;	//추천요금제 코드3

	public String getRecommendFlagCd() {
		return recommendFlagCd;
	}
	public void setRecommendFlagCd(String recommendFlagCd) {
		this.recommendFlagCd = recommendFlagCd;
	}
	public String getRecommendInfo() {
		return recommendInfo;
	}
	public void setRecommendInfo(String recommendInfo) {
		this.recommendInfo = recommendInfo;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getCstmrType() {
		return cstmrType;
	}
	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
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
	public String getOpenNo() {
		return openNo;
	}
	public void setOpenNo(String openNo) {
		this.openNo = openNo;
	}
	public String getFile01() {
		return file01;
	}
	public void setFile01(String file01) {
		this.file01 = file01;
	}
	public String getFile01Mask() {
		return file01Mask;
	}
	public void setFile01Mask(String file01Mask) {
		this.file01Mask = file01Mask;
	}
	public String getFaxyn() {
		return faxyn;
	}
	public void setFaxyn(String faxyn) {
		this.faxyn = faxyn;
	}
	public String getFaxnum() {
		return faxnum;
	}
	public void setFaxnum(String faxnum) {
		this.faxnum = faxnum;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getRip() {
		return rip;
	}
	public void setRip(String rip) {
		this.rip = rip;
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
	public String getReqUsimName() {
		return reqUsimName;
	}
	public void setReqUsimName(String reqUsimName) {
		this.reqUsimName = reqUsimName;
	}
	public String getReqModelColor() {
		return reqModelColor;
	}
	public void setReqModelColor(String reqModelColor) {
		this.reqModelColor = reqModelColor;
	}
	public String getReqPhoneSn() {
		return reqPhoneSn;
	}
	public void setReqPhoneSn(String reqPhoneSn) {
		this.reqPhoneSn = reqPhoneSn;
	}
	public String getReqUsimSn() {
		return reqUsimSn;
	}
	public void setReqUsimSn(String reqUsimSn) {
		this.reqUsimSn = reqUsimSn;
	}
	public String getReqPayType() {
		return reqPayType;
	}
	public void setReqPayType(String reqPayType) {
		this.reqPayType = reqPayType;
	}
	public String getAdditionName() {
		return additionName;
	}
	public void setAdditionName(String additionName) {
		this.additionName = additionName;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getPhonePayment() {
		return phonePayment;
	}
	public void setPhonePayment(String phonePayment) {
		this.phonePayment = phonePayment;
	}
	public String getAdditionPrice() {
		return additionPrice;
	}
	public void setAdditionPrice(String additionPrice) {
		this.additionPrice = additionPrice;
	}
	public String getSpcCode() {
		return spcCode;
	}
	public void setSpcCode(String spcCode) {
		this.spcCode = spcCode;
	}
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	public String getCstmrForeignerNation() {
		return cstmrForeignerNation;
	}
	public void setCstmrForeignerNation(String cstmrForeignerNation) {
		this.cstmrForeignerNation = cstmrForeignerNation;
	}
	public String getCstmrForeignerPn() {
		return cstmrForeignerPn;
	}
	public void setCstmrForeignerPn(String cstmrForeignerPn) {
		this.cstmrForeignerPn = cstmrForeignerPn;
	}
	public String getCstmrForeignerRrn() {
		return cstmrForeignerRrn;
	}
	public void setCstmrForeignerRrn(String cstmrForeignerRrn) {
		this.cstmrForeignerRrn = cstmrForeignerRrn;
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
	public String getCstmrPrivateCname() {
		return cstmrPrivateCname;
	}
	public void setCstmrPrivateCname(String cstmrPrivateCname) {
		this.cstmrPrivateCname = cstmrPrivateCname;
	}
	public String getCstmrPrivateNumber() {
		return cstmrPrivateNumber;
	}
	public void setCstmrPrivateNumber(String cstmrPrivateNumber) {
		this.cstmrPrivateNumber = cstmrPrivateNumber;
	}
	public String getCstmrJuridicalCname() {
		return cstmrJuridicalCname;
	}
	public void setCstmrJuridicalCname(String cstmrJuridicalCname) {
		this.cstmrJuridicalCname = cstmrJuridicalCname;
	}
	public String getCstmrJuridicalRrn() {
		return cstmrJuridicalRrn;
	}
	public void setCstmrJuridicalRrn(String cstmrJuridicalRrn) {
		this.cstmrJuridicalRrn = cstmrJuridicalRrn;
	}
	public String getCstmrJuridicalNumber() {
		return cstmrJuridicalNumber;
	}
	public void setCstmrJuridicalNumber(String cstmrJuridicalNumber) {
		this.cstmrJuridicalNumber = cstmrJuridicalNumber;
	}
	public String getCstmrTel() {
		return cstmrTel;
	}
	public void setCstmrTel(String cstmrTel) {
		this.cstmrTel = cstmrTel;
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
	public String getCstmrMobile() {
		return cstmrMobile;
	}
	public void setCstmrMobile(String cstmrMobile) {
		this.cstmrMobile = cstmrMobile;
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
	public String getCstmrPost() {
		return cstmrPost;
	}
	public void setCstmrPost(String cstmrPost) {
		this.cstmrPost = cstmrPost;
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
	public String getCstmrAddrBjd() {
		return cstmrAddrBjd;
	}
	public void setCstmrAddrBjd(String cstmrAddrBjd) {
		this.cstmrAddrBjd = cstmrAddrBjd;
	}
	public String getCstmrBillSendCode() {
		return cstmrBillSendCode;
	}
	public void setCstmrBillSendCode(String cstmrBillSendCode) {
		this.cstmrBillSendCode = cstmrBillSendCode;
	}
	public String getCstmrMail() {
		return cstmrMail;
	}
	public void setCstmrMail(String cstmrMail) {
		this.cstmrMail = cstmrMail;
	}
	public String getCstmrMailReceiveFlag() {
		return cstmrMailReceiveFlag;
	}
	public void setCstmrMailReceiveFlag(String cstmrMailReceiveFlag) {
		this.cstmrMailReceiveFlag = cstmrMailReceiveFlag;
	}
	public String getCstmrVisitType() {
		return cstmrVisitType;
	}
	public void setCstmrVisitType(String cstmrVisitType) {
		this.cstmrVisitType = cstmrVisitType;
	}
	public String getCstmrReceiveTelFn() {
		return cstmrReceiveTelFn;
	}
	public void setCstmrReceiveTelFn(String cstmrReceiveTelFn) {
		this.cstmrReceiveTelFn = cstmrReceiveTelFn;
	}
	public String getCstmrReceiveTelNm() {
		return cstmrReceiveTelNm;
	}
	public void setCstmrReceiveTelNm(String cstmrReceiveTelNm) {
		this.cstmrReceiveTelNm = cstmrReceiveTelNm;
	}
	public String getCstmrReceiveTelRn() {
		return cstmrReceiveTelRn;
	}
	public void setCstmrReceiveTelRn(String cstmrReceiveTelRn) {
		this.cstmrReceiveTelRn = cstmrReceiveTelRn;
	}
	public String getOthersPaymentAg() {
		return othersPaymentAg;
	}
	public void setOthersPaymentAg(String othersPaymentAg) {
		this.othersPaymentAg = othersPaymentAg;
	}
	public String getOthersPaymentNm() {
		return othersPaymentNm;
	}
	public void setOthersPaymentNm(String othersPaymentNm) {
		this.othersPaymentNm = othersPaymentNm;
	}
	public String getOthersPaymentRrn() {
		return othersPaymentRrn;
	}
	public void setOthersPaymentRrn(String othersPaymentRrn) {
		this.othersPaymentRrn = othersPaymentRrn;
	}
	public String getOthersPaymentRelation() {
		return othersPaymentRelation;
	}
	public void setOthersPaymentRelation(String othersPaymentRelation) {
		this.othersPaymentRelation = othersPaymentRelation;
	}
	public String getOthersPaymentRnm() {
		return othersPaymentRnm;
	}
	public void setOthersPaymentRnm(String othersPaymentRnm) {
		this.othersPaymentRnm = othersPaymentRnm;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}
	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}
	public String getCstmrForeignerDod() {
		return cstmrForeignerDod;
	}
	public void setCstmrForeignerDod(String cstmrForeignerDod) {
		this.cstmrForeignerDod = cstmrForeignerDod;
	}
	public String getCstmrForeignerBirth() {
		return cstmrForeignerBirth;
	}
	public void setCstmrForeignerBirth(String cstmrForeignerBirth) {
		this.cstmrForeignerBirth = cstmrForeignerBirth;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getInstNom() {
		return instNom;
	}
	public void setInstNom(String instNom) {
		this.instNom = instNom;
	}
	public String getModelInstallment() {
		return modelInstallment;
	}
	public void setModelInstallment(String modelInstallment) {
		this.modelInstallment = modelInstallment;
	}
	public String getSalePlcyCd() {
		return salePlcyCd;
	}
	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}
	public String getRecycleYn() {
		return recycleYn;
	}
	public void setRecycleYn(String recycleYn) {
		this.recycleYn = recycleYn;
	}
	public String getModelDiscount1() {
		return modelDiscount1;
	}
	public void setModelDiscount1(String modelDiscount1) {
		this.modelDiscount1 = modelDiscount1;
	}
	public String getModelDiscount2() {
		return modelDiscount2;
	}
	public void setModelDiscount2(String modelDiscount2) {
		this.modelDiscount2 = modelDiscount2;
	}
	public String getModelDiscount3() {
		return modelDiscount3;
	}
	public void setModelDiscount3(String modelDiscount3) {
		this.modelDiscount3 = modelDiscount3;
	}
	public String getSocCode() {
		return socCode;
	}
	public void setSocCode(String socCode) {
		this.socCode = socCode;
	}
	public String getUsimPriceType() {
		return usimPriceType;
	}
	public void setUsimPriceType(String usimPriceType) {
		this.usimPriceType = usimPriceType;
	}
	public String getJoinPriceType() {
		return joinPriceType;
	}
	public void setJoinPriceType(String joinPriceType) {
		this.joinPriceType = joinPriceType;
	}
	public String getJoinPrice() {
		return joinPrice;
	}
	public void setJoinPrice(String joinPrice) {
		this.joinPrice = joinPrice;
	}
	public String getUsimPrice() {
		return usimPrice;
	}
	public void setUsimPrice(String usimPrice) {
		this.usimPrice = usimPrice;
	}
	public String getModelPrice() {
		return modelPrice;
	}
	public void setModelPrice(String modelPrice) {
		this.modelPrice = modelPrice;
	}
	public String getAddtionService() {
		return addtionService;
	}
	public void setAddtionService(String addtionService) {
		this.addtionService = addtionService;
	}
	public String getAddtionServiceSum() {
		return addtionServiceSum;
	}
	public void setAddtionServiceSum(String addtionServiceSum) {
		this.addtionServiceSum = addtionServiceSum;
	}
	public String getEnggMnthCnt() {
		return enggMnthCnt;
	}
	public void setEnggMnthCnt(String enggMnthCnt) {
		this.enggMnthCnt = enggMnthCnt;
	}
	public String getModelPriceVat() {
		return modelPriceVat;
	}
	public void setModelPriceVat(String modelPriceVat) {
		this.modelPriceVat = modelPriceVat;
	}
	public String getDlvryName() {
		return dlvryName;
	}
	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}
	public String getDlvryTel() {
		return dlvryTel;
	}
	public void setDlvryTel(String dlvryTel) {
		this.dlvryTel = dlvryTel;
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
	public String getDlvryMobile() {
		return dlvryMobile;
	}
	public void setDlvryMobile(String dlvryMobile) {
		this.dlvryMobile = dlvryMobile;
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
	public String getDlvryPost() {
		return dlvryPost;
	}
	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
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
	public String getDlvryAddrBjd() {
		return dlvryAddrBjd;
	}
	public void setDlvryAddrBjd(String dlvryAddrBjd) {
		this.dlvryAddrBjd = dlvryAddrBjd;
	}
	public String getDlvryMemo() {
		return dlvryMemo;
	}
	public void setDlvryMemo(String dlvryMemo) {
		this.dlvryMemo = dlvryMemo;
	}
	public String getTbCd() {
		return tbCd;
	}
	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}
	public String getReqBank() {
		return reqBank;
	}
	public void setReqBank(String reqBank) {
		this.reqBank = reqBank;
	}
	public String getReqAccountName() {
		return reqAccountName;
	}
	public void setReqAccountName(String reqAccountName) {
		this.reqAccountName = reqAccountName;
	}
	public String getReqAccountRrn() {
		return reqAccountRrn;
	}
	public void setReqAccountRrn(String reqAccountRrn) {
		this.reqAccountRrn = reqAccountRrn;
	}
	public String getReqAccountRelation() {
		return reqAccountRelation;
	}
	public void setReqAccountRelation(String reqAccountRelation) {
		this.reqAccountRelation = reqAccountRelation;
	}
	public String getReqAccountNumber() {
		return reqAccountNumber;
	}
	public void setReqAccountNumber(String reqAccountNumber) {
		this.reqAccountNumber = reqAccountNumber;
	}
	public String getReqCardName() {
		return reqCardName;
	}
	public void setReqCardName(String reqCardName) {
		this.reqCardName = reqCardName;
	}
	public String getReqCardRrn() {
		return reqCardRrn;
	}
	public void setReqCardRrn(String reqCardRrn) {
		this.reqCardRrn = reqCardRrn;
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
	public String getReqCardYy() {
		return reqCardYy;
	}
	public void setReqCardYy(String reqCardYy) {
		this.reqCardYy = reqCardYy;
	}
	public String getReqCardMm() {
		return reqCardMm;
	}
	public void setReqCardMm(String reqCardMm) {
		this.reqCardMm = reqCardMm;
	}
	public String getReqWireType() {
		return reqWireType;
	}
	public void setReqWireType(String reqWireType) {
		this.reqWireType = reqWireType;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
	public String getReqPayOtherFlag() {
		return reqPayOtherFlag;
	}
	public void setReqPayOtherFlag(String reqPayOtherFlag) {
		this.reqPayOtherFlag = reqPayOtherFlag;
	}
	public String getReqPayOtherTelFn() {
		return reqPayOtherTelFn;
	}
	public void setReqPayOtherTelFn(String reqPayOtherTelFn) {
		this.reqPayOtherTelFn = reqPayOtherTelFn;
	}
	public String getReqPayOtherTelMn() {
		return reqPayOtherTelMn;
	}
	public void setReqPayOtherTelMn(String reqPayOtherTelMn) {
		this.reqPayOtherTelMn = reqPayOtherTelMn;
	}
	public String getReqPayOtherTelRn() {
		return reqPayOtherTelRn;
	}
	public void setReqPayOtherTelRn(String reqPayOtherTelRn) {
		this.reqPayOtherTelRn = reqPayOtherTelRn;
	}
	public String getAdditionKey() {
		return additionKey;
	}
	public void setAdditionKey(String additionKey) {
		this.additionKey = additionKey;
	}
	public String getMoveCompany() {
		return moveCompany;
	}
	public void setMoveCompany(String moveCompany) {
		this.moveCompany = moveCompany;
	}
	public String getMoveMobile() {
		return moveMobile;
	}
	public void setMoveMobile(String moveMobile) {
		this.moveMobile = moveMobile;
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
	public String getMoveAuthType() {
		return moveAuthType;
	}
	public void setMoveAuthType(String moveAuthType) {
		this.moveAuthType = moveAuthType;
	}
	public String getMoveAuthNumber() {
		return moveAuthNumber;
	}
	public void setMoveAuthNumber(String moveAuthNumber) {
		this.moveAuthNumber = moveAuthNumber;
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
	public String getMovePenalty() {
		return movePenalty;
	}
	public void setMovePenalty(String movePenalty) {
		this.movePenalty = movePenalty;
	}
	public String getSysRdaterequestKey() {
		return sysRdaterequestKey;
	}
	public void setSysRdaterequestKey(String sysRdaterequestKey) {
		this.sysRdaterequestKey = sysRdaterequestKey;
	}
	public String getReqGuide() {
		return reqGuide;
	}
	public void setReqGuide(String reqGuide) {
		this.reqGuide = reqGuide;
	}
	public String getReqGuideMn() {
		return reqGuideMn;
	}
	public void setReqGuideMn(String reqGuideMn) {
		this.reqGuideMn = reqGuideMn;
	}
	public String getMinorAgentRrn() {
		return minorAgentRrn;
	}
	public void setMinorAgentRrn(String minorAgentRrn) {
		this.minorAgentRrn = minorAgentRrn;
	}
	public String getJrdclAgentRrn() {
		return jrdclAgentRrn;
	}
	public void setJrdclAgentRrn(String jrdclAgentRrn) {
		this.jrdclAgentRrn = jrdclAgentRrn;
	}
	public String getEntrustReqNm() {
		return entrustReqNm;
	}
	public void setEntrustReqNm(String entrustReqNm) {
		this.entrustReqNm = entrustReqNm;
	}
	public String getEntrustResNm() {
		return entrustResNm;
	}
	public void setEntrustResNm(String entrustResNm) {
		this.entrustResNm = entrustResNm;
	}
	public String getEntrustReqRelation() {
		return entrustReqRelation;
	}
	public void setEntrustReqRelation(String entrustReqRelation) {
		this.entrustReqRelation = entrustReqRelation;
	}
	public String getEntrustResRrn() {
		return entrustResRrn;
	}
	public void setEntrustResRrn(String entrustResRrn) {
		this.entrustResRrn = entrustResRrn;
	}
	public String getEntrustResTelFn() {
		return entrustResTelFn;
	}
	public void setEntrustResTelFn(String entrustResTelFn) {
		this.entrustResTelFn = entrustResTelFn;
	}
	public String getEntrustResTelMn() {
		return entrustResTelMn;
	}
	public void setEntrustResTelMn(String entrustResTelMn) {
		this.entrustResTelMn = entrustResTelMn;
	}
	public String getEntrustResTelRn() {
		return entrustResTelRn;
	}
	public void setEntrustResTelRn(String entrustResTelRn) {
		this.entrustResTelRn = entrustResTelRn;
	}
	public String getRequestStateKey() {
		return requestStateKey;
	}
	public void setRequestStateKey(String requestStateKey) {
		this.requestStateKey = requestStateKey;
	}
	public String getDlvryNo() {
		return dlvryNo;
	}
	public void setDlvryNo(String dlvryNo) {
		this.dlvryNo = dlvryNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getViewFlag() {
		return viewFlag;
	}
	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}
	public String getReqAc01Amount() {
		return reqAc01Amount;
	}
	public void setReqAc01Amount(String reqAc01Amount) {
		this.reqAc01Amount = reqAc01Amount;
	}
	public String getReqAc02Amount() {
		return reqAc02Amount;
	}
	public void setReqAc02Amount(String reqAc02Amount) {
		this.reqAc02Amount = reqAc02Amount;
	}
	public String getMnfctId() {
		return mnfctId;
	}
	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getSalePlcyNm() {
		return salePlcyNm;
	}
	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}
	public String getPrdtSctnCd() {
		return prdtSctnCd;
	}
	public void setPrdtSctnCd(String prdtSctnCd) {
		this.prdtSctnCd = prdtSctnCd;
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
	public String getCstmrForeignerRrn1() {
		return cstmrForeignerRrn1;
	}
	public void setCstmrForeignerRrn1(String cstmrForeignerRrn1) {
		this.cstmrForeignerRrn1 = cstmrForeignerRrn1;
	}
	public String getCstmrForeignerRrn2() {
		return cstmrForeignerRrn2;
	}
	public void setCstmrForeignerRrn2(String cstmrForeignerRrn2) {
		this.cstmrForeignerRrn2 = cstmrForeignerRrn2;
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
	public String getReqInDay() {
		return reqInDay;
	}
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getFaxYn() {
		return faxYn;
	}
	public void setFaxYn(String faxYn) {
		this.faxYn = faxYn;
	}
	public String getFaxnum1() {
		return faxnum1;
	}
	public void setFaxnum1(String faxnum1) {
		this.faxnum1 = faxnum1;
	}
	public String getFaxnum2() {
		return faxnum2;
	}
	public void setFaxnum2(String faxnum2) {
		this.faxnum2 = faxnum2;
	}
	public String getFaxnum3() {
		return faxnum3;
	}
	public void setFaxnum3(String faxnum3) {
		this.faxnum3 = faxnum3;
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
	public String getOthersPaymentAgClone() {
		return othersPaymentAgClone;
	}
	public void setOthersPaymentAgClone(String othersPaymentAgClone) {
		this.othersPaymentAgClone = othersPaymentAgClone;
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
	public String getReqAcType() {
		return reqAcType;
	}
	public void setReqAcType(String reqAcType) {
		this.reqAcType = reqAcType;
	}
	public String getReqAc01Balance() {
		return reqAc01Balance;
	}
	public void setReqAc01Balance(String reqAc01Balance) {
		this.reqAc01Balance = reqAc01Balance;
	}
	public String getReqAc02Day() {
		return reqAc02Day;
	}
	public void setReqAc02Day(String reqAc02Day) {
		this.reqAc02Day = reqAc02Day;
	}
	public String getReqAcAmount() {
		return reqAcAmount;
	}
	public void setReqAcAmount(String reqAcAmount) {
		this.reqAcAmount = reqAcAmount;
	}
	public String getReqModelCd() {
		return reqModelCd;
	}
	public void setReqModelCd(String reqModelCd) {
		this.reqModelCd = reqModelCd;
	}
	public String getReqModelColorCd() {
		return reqModelColorCd;
	}
	public void setReqModelColorCd(String reqModelColorCd) {
		this.reqModelColorCd = reqModelColorCd;
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
	public String getReqWireType1() {
		return reqWireType1;
	}
	public void setReqWireType1(String reqWireType1) {
		this.reqWireType1 = reqWireType1;
	}
	public String getReqWireType2() {
		return reqWireType2;
	}
	public void setReqWireType2(String reqWireType2) {
		this.reqWireType2 = reqWireType2;
	}
	public String getReqWireType3() {
		return reqWireType3;
	}
	public void setReqWireType3(String reqWireType3) {
		this.reqWireType3 = reqWireType3;
	}
	public String getBtnRateFind() {
		return btnRateFind;
	}
	public void setBtnRateFind(String btnRateFind) {
		this.btnRateFind = btnRateFind;
	}
	public String getAgrmTrm() {
		return agrmTrm;
	}
	public void setAgrmTrm(String agrmTrm) {
		this.agrmTrm = agrmTrm;
	}
	public String getTrmnlInfo() {
		return trmnlInfo;
	}
	public void setTrmnlInfo(String trmnlInfo) {
		this.trmnlInfo = trmnlInfo;
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
	public String getMinorAgentTel() {
		return minorAgentTel;
	}
	public void setMinorAgentTel(String minorAgentTel) {
		this.minorAgentTel = minorAgentTel;
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
	public String getJrdclAgentName() {
		return jrdclAgentName;
	}
	public void setJrdclAgentName(String jrdclAgentName) {
		this.jrdclAgentName = jrdclAgentName;
	}
	public String getJrdclAgentRrn1() {
		return jrdclAgentRrn1;
	}
	public void setJrdclAgentRrn1(String jrdclAgentRrn1) {
		this.jrdclAgentRrn1 = jrdclAgentRrn1;
	}
	public String getJrdclAgentRrn2() {
		return jrdclAgentRrn2;
	}
	public void setJrdclAgentRrn2(String jrdclAgentRrn2) {
		this.jrdclAgentRrn2 = jrdclAgentRrn2;
	}
	public String getJrdclAgentTel() {
		return jrdclAgentTel;
	}
	public void setJrdclAgentTel(String jrdclAgentTel) {
		this.jrdclAgentTel = jrdclAgentTel;
	}
	public String getJrdclAgentTelFn() {
		return jrdclAgentTelFn;
	}
	public void setJrdclAgentTelFn(String jrdclAgentTelFn) {
		this.jrdclAgentTelFn = jrdclAgentTelFn;
	}
	public String getJrdclAgentTelMn() {
		return jrdclAgentTelMn;
	}
	public void setJrdclAgentTelMn(String jrdclAgentTelMn) {
		this.jrdclAgentTelMn = jrdclAgentTelMn;
	}
	public String getJrdclAgentTelRn() {
		return jrdclAgentTelRn;
	}
	public void setJrdclAgentTelRn(String jrdclAgentTelRn) {
		this.jrdclAgentTelRn = jrdclAgentTelRn;
	}
	public String getNameChangeNm() {
		return nameChangeNm;
	}
	public void setNameChangeNm(String nameChangeNm) {
		this.nameChangeNm = nameChangeNm;
	}
	public String getNameChangeTelFn() {
		return nameChangeTelFn;
	}
	public void setNameChangeTelFn(String nameChangeTelFn) {
		this.nameChangeTelFn = nameChangeTelFn;
	}
	public String getNameChangeTelMn() {
		return nameChangeTelMn;
	}
	public void setNameChangeTelMn(String nameChangeTelMn) {
		this.nameChangeTelMn = nameChangeTelMn;
	}
	public String getNameChangeTelRn() {
		return nameChangeTelRn;
	}
	public void setNameChangeTelRn(String nameChangeTelRn) {
		this.nameChangeTelRn = nameChangeTelRn;
	}
	public String getNameChangeRrn() {
		return nameChangeRrn;
	}
	public void setNameChangeRrn(String nameChangeRrn) {
		this.nameChangeRrn = nameChangeRrn;
	}
	public String getNameChangePinstallment() {
		return nameChangePinstallment;
	}
	public void setNameChangePinstallment(String nameChangePinstallment) {
		this.nameChangePinstallment = nameChangePinstallment;
	}
	public String getShopUsmId() {
		return shopUsmId;
	}
	public void setShopUsmId(String shopUsmId) {
		this.shopUsmId = shopUsmId;
	}
	public String getBtnType() {
		return btnType;
	}
	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}
	public String getScanIdBySearch() {
		return scanIdBySearch;
	}
	public void setScanIdBySearch(String scanIdBySearch) {
		this.scanIdBySearch = scanIdBySearch;
	}
	public String getShopCdBySearch() {
		return shopCdBySearch;
	}
	public void setShopCdBySearch(String shopCdBySearch) {
		this.shopCdBySearch = shopCdBySearch;
	}
	public String getRequestMemo() {
		return requestMemo;
	}
	public void setRequestMemo(String requestMemo) {
		this.requestMemo = requestMemo;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getRecYn() {
		return recYn;
	}
	public void setRecYn(String recYn) {
		this.recYn = recYn;
	}
	public String getSoCd() {
		return soCd;
	}
	public void setSoCd(String soCd) {
		this.soCd = soCd;
	}
	public String getNwBlckAgrmYn() {
		return nwBlckAgrmYn;
	}
	public void setNwBlckAgrmYn(String nwBlckAgrmYn) {
		this.nwBlckAgrmYn = nwBlckAgrmYn;
	}
	public String getAppBlckAgrmYn() {
		return appBlckAgrmYn;
	}
	public void setAppBlckAgrmYn(String appBlckAgrmYn) {
		this.appBlckAgrmYn = appBlckAgrmYn;
	}
	public String getAppCd() {
		return appCd;
	}
	public void setAppCd(String appCd) {
		this.appCd = appCd;
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
	public String getDvcChgType() {
		return dvcChgType;
	}
	public void setDvcChgType(String dvcChgType) {
		this.dvcChgType = dvcChgType;
	}
	public String getDvcChgRsnCd() {
		return dvcChgRsnCd;
	}
	public void setDvcChgRsnCd(String dvcChgRsnCd) {
		this.dvcChgRsnCd = dvcChgRsnCd;
	}
	public String getDvcChgRsnDtlCd() {
		return dvcChgRsnDtlCd;
	}
	public void setDvcChgRsnDtlCd(String dvcChgRsnDtlCd) {
		this.dvcChgRsnDtlCd = dvcChgRsnDtlCd;
	}
	public String getUsimPayMthdCd() {
		return usimPayMthdCd;
	}
	public void setUsimPayMthdCd(String usimPayMthdCd) {
		this.usimPayMthdCd = usimPayMthdCd;
	}
	public String getJoinPayMthdCd() {
		return joinPayMthdCd;
	}
	public void setJoinPayMthdCd(String joinPayMthdCd) {
		this.joinPayMthdCd = joinPayMthdCd;
	}
	public String getRealMdlInstamt() {
		return realMdlInstamt;
	}
	public void setRealMdlInstamt(String realMdlInstamt) {
		this.realMdlInstamt = realMdlInstamt;
	}
	public int getStatusKey() {
		return statusKey;
	}
	public void setStatusKey(int statusKey) {
		this.statusKey = statusKey;
	}
	public String getCstmrJejuId() {
		return cstmrJejuId;
	}
	public void setCstmrJejuId(String cstmrJejuId) {
		this.cstmrJejuId = cstmrJejuId;
	}
	public String getClauseJehuFlag() {
		return clauseJehuFlag;
	}
	public void setClauseJehuFlag(String clauseJehuFlag) {
		this.clauseJehuFlag = clauseJehuFlag;
	}
	public String getShopNm() {
		return shopNm;
	}
	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}
	public String getSprtTp() {
		return sprtTp;
	}
	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}
	public String getHndstAmt() {
		return hndstAmt;
	}
	public void setHndstAmt(String hndstAmt) {
		this.hndstAmt = hndstAmt;
	}
	public String getMaxDiscount3() {
		return maxDiscount3;
	}
	public void setMaxDiscount3(String maxDiscount3) {
		this.maxDiscount3 = maxDiscount3;
	}
	public String getDcAmt() {
		return dcAmt;
	}
	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}
	public String getAddDcAmt() {
		return addDcAmt;
	}
	public void setAddDcAmt(String addDcAmt) {
		this.addDcAmt = addDcAmt;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getTrgtRequestKey() {
		return trgtRequestKey;
	}
	public void setTrgtRequestKey(String trgtRequestKey) {
		this.trgtRequestKey = trgtRequestKey;
	}
	public String getClauseFinanceFlag() {
		return clauseFinanceFlag;
	}
	public void setClauseFinanceFlag(String clauseFinanceFlag) {
		this.clauseFinanceFlag = clauseFinanceFlag;
	}
	public String getOtpCheckYN() {
		return otpCheckYN;
	}
	public void setOtpCheckYN(String otpCheckYN) {
		this.otpCheckYN = otpCheckYN;
	}
	public String getSmsAuthInfo() {
		return smsAuthInfo;
	}
	public void setSmsAuthInfo(String smsAuthInfo) {
		this.smsAuthInfo = smsAuthInfo;
	}
	public String getClauseRentalService() {
		return clauseRentalService;
	}
	public void setClauseRentalService(String clauseRentalService) {
		this.clauseRentalService = clauseRentalService;
	}
	public String getClauseRentalModelCp() {
		return clauseRentalModelCp;
	}
	public void setClauseRentalModelCp(String clauseRentalModelCp) {
		this.clauseRentalModelCp = clauseRentalModelCp;
	}
	public String getClauseRentalModelCpPr() {
		return clauseRentalModelCpPr;
	}
	public void setClauseRentalModelCpPr(String clauseRentalModelCpPr) {
		this.clauseRentalModelCpPr = clauseRentalModelCpPr;
	}
	public String getRentalModelCpAmt() {
		return rentalModelCpAmt;
	}
	public void setRentalModelCpAmt(String rentalModelCpAmt) {
		this.rentalModelCpAmt = rentalModelCpAmt;
	}
	public String getRentalBaseAmt() {
		return rentalBaseAmt;
	}
	public void setRentalBaseAmt(String rentalBaseAmt) {
		this.rentalBaseAmt = rentalBaseAmt;
	}
	public String getRentalBaseDcAmt() {
		return rentalBaseDcAmt;
	}
	public void setRentalBaseDcAmt(String rentalBaseDcAmt) {
		this.rentalBaseDcAmt = rentalBaseDcAmt;
	}
	public String getRntlPrdtId() {
		return rntlPrdtId;
	}
	public void setRntlPrdtId(String rntlPrdtId) {
		this.rntlPrdtId = rntlPrdtId;
	}
	public String getRntlPrdtCode() {
		return rntlPrdtCode;
	}
	public void setRntlPrdtCode(String rntlPrdtCode) {
		this.rntlPrdtCode = rntlPrdtCode;
	}
	public String getPromotionCd() {
		return promotionCd;
	}
	public void setPromotionCd(String promotionCd) {
		this.promotionCd = promotionCd;
	}
	public String getRealShopNm() {
		return realShopNm;
	}
	public void setRealShopNm(String realShopNm) {
		this.realShopNm = realShopNm;
	}
	public String getUsimKindsCd() {
		return usimKindsCd;
	}
	public void setUsimKindsCd(String usimKindsCd) {
		this.usimKindsCd = usimKindsCd;
	}
	public String getBnftName() {
		return bnftName;
	}
	public void setBnftName(String bnftName) {
		this.bnftName = bnftName;
	}
	public String getBnftKey() {
		return bnftKey;
	}
	public void setBnftKey(String bnftKey) {
		this.bnftKey = bnftKey;
	}
	public String getAppEventCd() {
		return appEventCd;
	}
	public void setAppEventCd(String appEventCd) {
		this.appEventCd = appEventCd;
	}
	public String getInsrCd() {
		return insrCd;
	}
	public void setInsrCd(String insrCd) {
		this.insrCd = insrCd;
	}
	public String getClauseInsuranceFlag() {
		return clauseInsuranceFlag;
	}
	public void setClauseInsuranceFlag(String clauseInsuranceFlag) {
		this.clauseInsuranceFlag = clauseInsuranceFlag;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getLostYn() {
		return lostYn;
	}
	public void setLostYn(String lostYn) {
		this.lostYn = lostYn;
	}
	public String getInsrProdCd() {
		return insrProdCd;
	}
	public void setInsrProdCd(String insrProdCd) {
		this.insrProdCd = insrProdCd;
	}
	public String getClauseInsrProdFlag() {
		return clauseInsrProdFlag;
	}
	public void setClauseInsrProdFlag(String clauseInsrProdFlag) {
		this.clauseInsrProdFlag = clauseInsrProdFlag;
	}
	public String getVisaCd() {
		return visaCd;
	}
	public void setVisaCd(String visaCd) {
		this.visaCd = visaCd;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getImei1() {
		return imei1;
	}
	public void setImei1(String imei1) {
		this.imei1 = imei1;
	}
	public String getImei2() {
		return imei2;
	}
	public void setImei2(String imei2) {
		this.imei2 = imei2;
	}
	public String getIntmMdlId() {
		return intmMdlId;
	}
	public void setIntmMdlId(String intmMdlId) {
		this.intmMdlId = intmMdlId;
	}
	public String getIntmSrlNo() {
		return intmSrlNo;
	}
	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}
	public String getPrntsCtn() {
		return prntsCtn;
	}
	public void setPrntsCtn(String prntsCtn) {
		this.prntsCtn = prntsCtn;
	}
	public String getPrntsCtnFn() {
		return prntsCtnFn;
	}
	public void setPrntsCtnFn(String prntsCtnFn) {
		this.prntsCtnFn = prntsCtnFn;
	}
	public String getPrntsCtnMn() {
		return prntsCtnMn;
	}
	public void setPrntsCtnMn(String prntsCtnMn) {
		this.prntsCtnMn = prntsCtnMn;
	}
	public String getPrntsCtnRn() {
		return prntsCtnRn;
	}
	public void setPrntsCtnRn(String prntsCtnRn) {
		this.prntsCtnRn = prntsCtnRn;
	}
	public String getBillAcntNo() {
		return billAcntNo;
	}
	public void setBillAcntNo(String billAcntNo) {
		this.billAcntNo = billAcntNo;
	}
	public String getPrntsContractNo() {
		return prntsContractNo;
	}
	public void setPrntsContractNo(String prntsContractNo) {
		this.prntsContractNo = prntsContractNo;
	}

	public String getPrntsBillNo() {
		return prntsBillNo;
	}

	public void setPrntsBillNo(String prntsBillNo) {
		this.prntsBillNo = prntsBillNo;
	}

	public String getPersonalInfoCollectAgree() {
		return personalInfoCollectAgree;
	}
	public void setPersonalInfoCollectAgree(String personalInfoCollectAgree) {
		this.personalInfoCollectAgree = personalInfoCollectAgree;
	}
	public String getOthersTrnsAgree() {
		return othersTrnsAgree;
	}
	public void setOthersTrnsAgree(String othersTrnsAgree) {
		this.othersTrnsAgree = othersTrnsAgree;
	}

	public String getOthersTrnsKtAgree() {
		return othersTrnsKtAgree;
	}

	public void setOthersTrnsKtAgree(String othersTrnsKtAgree) {
		this.othersTrnsKtAgree = othersTrnsKtAgree;
	}

	public String getOthersAdReceiveAgree() {
		return othersAdReceiveAgree;
	}

	public void setOthersAdReceiveAgree(String othersAdReceiveAgree) {
		this.othersAdReceiveAgree = othersAdReceiveAgree;
	}

	public String getKtInterSvcNo() {
		return ktInterSvcNo;
	}
	public void setKtInterSvcNo(String ktInterSvcNo) {
		this.ktInterSvcNo = ktInterSvcNo;
	}
	public String getDisPrmtId() {
		return disPrmtId;
	}
	public void setDisPrmtId(String disPrmtId) {
		this.disPrmtId = disPrmtId;
	}
	public String getApdSeq() {
		return apdSeq;
	}
	public void setApdSeq(String apdSeq) {
		this.apdSeq = apdSeq;
	}
	public String getEvntCd() {
		return evntCd;
	}
	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}
	public String getFrmpapId() {
		return frmpapId;
	}
	public void setFrmpapId(String frmpapId) {
		this.frmpapId = frmpapId;
	}
	public String getSelfCstmrCi() {
		return selfCstmrCi;
	}
	public void setSelfCstmrCi(String selfCstmrCi) {
		this.selfCstmrCi = selfCstmrCi;
	}
	public String getHubOrderSeq() {
		return hubOrderSeq;
	}

	public void setHubOrderSeq(String hubOrderSeq) {
		this.hubOrderSeq = hubOrderSeq;
	}

	public String getClauseSensiCollectFlag() {
		return clauseSensiCollectFlag;
	}

	public void setClauseSensiCollectFlag(String clauseSensiCollectFlag) {
		this.clauseSensiCollectFlag = clauseSensiCollectFlag;
	}

	public String getClauseSensiOfferFlag() {
		return clauseSensiOfferFlag;
	}

	public void setClauseSensiOfferFlag(String clauseSensiOfferFlag) {
		this.clauseSensiOfferFlag = clauseSensiOfferFlag;
	}

	public String getJehuProdType() {
		return jehuProdType;
	}

	public void setJehuProdType(String jehuProdType) {
		this.jehuProdType = jehuProdType;
	}

	public String getClausePartnerOfferFlag() {
		return clausePartnerOfferFlag;
	}

	public void setClausePartnerOfferFlag(String clausePartnerOfferFlag) {
		this.clausePartnerOfferFlag = clausePartnerOfferFlag;
	}

	public String getDlvryType() {
		return dlvryType;
	}

	public void setDlvryType(String dlvryType) {
		this.dlvryType = dlvryType;
	}

	public String getFathTrgYn() {
		return fathTrgYn;
	}

	public void setFathTrgYn(String fathTrgYn) {
		this.fathTrgYn = fathTrgYn;
	}

	public String getClauseFathFlag() {
		return clauseFathFlag;
	}

	public void setClauseFathFlag(String clauseFathFlag) {
		this.clauseFathFlag = clauseFathFlag;
	}

	public String getFathTransacId() {
		return fathTransacId;
	}

	public void setFathTransacId(String fathTransacId) {
		this.fathTransacId = fathTransacId;
	}

	public String getFathCmpltNtfyDt() {
		return fathCmpltNtfyDt;
	}

	public void setFathCmpltNtfyDt(String fathCmpltNtfyDt) {
		this.fathCmpltNtfyDt = fathCmpltNtfyDt;
	}

    public String getMcnResNo() {
        return mcnResNo;
    }

    public void setMcnResNo(String mcnResNo) {
        this.mcnResNo = mcnResNo;
    }

	public String getFathMobile() {
		return fathMobile;
	}

	public void setFathMobile(String fathMobile) {
		this.fathMobile = fathMobile;
	}

	public String getFathMobileFn() {
		return fathMobileFn;
	}

	public void setFathMobileFn(String fathMobileFn) {
		this.fathMobileFn = fathMobileFn;
	}

	public String getFathMobileMn() {
		return fathMobileMn;
	}

	public void setFathMobileMn(String fathMobileMn) {
		this.fathMobileMn = fathMobileMn;
	}

	public String getFathMobileRn() {
		return fathMobileRn;
	}

	public void setFathMobileRn(String fathMobileRn) {
		this.fathMobileRn = fathMobileRn;
	}

	public String getCpntId() {
		return cpntId;
	}

	public void setCpntId(String cpntId) {
		this.cpntId = cpntId;
	}

	public String getMngmAgncId() {
		return mngmAgncId;
	}

	public void setMngmAgncId(String mngmAgncId) {
		this.mngmAgncId = mngmAgncId;
	}

	public String getCombineSoloType() {
		return combineSoloType;
	}

	public void setCombineSoloType(String combineSoloType) {
		this.combineSoloType = combineSoloType;
	}

	public String getCombineSoloFlag() {
		return combineSoloFlag;
	}

	public void setCombineSoloFlag(String combineSoloFlag) {
		this.combineSoloFlag = combineSoloFlag;
	}
	public String getLinkTypeCd() {
		return linkTypeCd;
	}
	public void setLinkTypeCd(String linkTypeCd) {
		this.linkTypeCd = linkTypeCd;
	}
	public String getOpenMthdCd() {
		return openMthdCd;
	}
	public void setOpenMthdCd(String openMthdCd) {
		this.openMthdCd = openMthdCd;
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
