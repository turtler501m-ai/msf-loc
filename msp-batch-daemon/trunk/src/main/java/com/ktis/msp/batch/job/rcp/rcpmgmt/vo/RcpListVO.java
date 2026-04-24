package com.ktis.msp.batch.job.rcp.rcpmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class RcpListVO extends BaseVo {

	private String prodTypeNm            ;    /* 상품구분                 */ 
	private String serviceName           ;    /* 서비스구분               */ 
	private String resNo                 ;    /* 예약번호                 */ 
	private String cstmrName             ;    /* 고객명                   */ 
	private String birthDt               ;    /* 생년월일                 */ 
	private String cstmrGenderStr        ;    /* 성별                     */ 
	private String reqBuyType            ;    /* 구매유형                 */ 
	private String moveCompany           ;    /* 이동전 통신사            */ 
	private String prdtNm                ;    /* 제품명                   */ 
	private String prdtColrNm            ;    /* 제품색상                 */ 
	private String cstmrMobile           ;    /* 휴대폰번호               */ 
	private String agentName             ;    /* 대리점                   */ 
	private String socName               ;    /* 요금제                   */ 
	private String prmtNm				 ;    /* 프로모션명                   */
	private String operName              ;    /* 업무구분                 */
	private String cstmrAddr             ;    /* 고객주소                 */ 
	private String dlvryAddr             ;    /* 배송주소                 */ 
	private String dlvryPost             ;    /* 배송우편번호             */ 
	private String dlvryMobile           ;    /* 배송휴대폰번호	          */ 
	private String cstmrTelNo            ;    /* 유선연락처               */ 
	private String reqInDay              ;    /* 신청일자                 */ 
	private String requestStateName      ;    /* 진행상태                 */ 
	private String pstateName            ;    /* 신청서상태               */ 
	private String onOffName             ;    /* 모집경로                 */ 
	private String openMarketReferer     ;    /* 유입경로                 */ 
	private String recommendFlagNm       ;    /* 추천인구분               */ 
	private String recommendInfo         ;    /* 추천인정보               */ 
	private String recYn                 ;    /* 녹취여부                 */ 
	private String sprtTpNm              ;    /* 할인유형                 */ 
	private String clauseRentalService   ;    /* 렌탈서비스동의           */ 
	private String clauseRentalModelCp   ;    /* 단말배상금동의           */ 
	private String clauseRentalModelCpPr ;    /* 단말배상금(부분파손)동의 */ 
	private String rentalBaseAmt         ;    /* 렌탈기본료               */ 
	private String rentalBaseDcAmt       ;    /* 렌탈할인금액             */ 
	private String rentalModelCpAmt      ;    /* 단말배상금액             */ 

	private String userId;
	private String cstmrForeignerRrn;
	private String cstmrNativeRrn;
	private String ssn;
	private String onOffType;
	private String shopNm;
	private String usrNm;
	private String requestKey;
	private String prodType;
	private String cntpntShopId;
	
	// 검색조건
	private String searchStartDt;
	private String searchEndDt;
	private String pServiceType;
	private String pOperType;
	private String typeCd;
	private String pAgentCode;
	private String pRequestStateCode;
	private String pSearchGbn;
	private String pSearchName;
	private String pPstate;
	private String payRstCd;
	private String operType;
//	private String reqBuyType;
	private String sprtTp;
//	private String prodType;
	
	// 엑셀다운로드 로그
	private String dwnldRsn    ;	/*다운로드 사유*/
	private String exclDnldId  ;	
	private String ipAddr      ;	/*ip정보*/
	private String menuId      ;	/*메뉴ID*/
	
	private String strtDt;
	private String endDt;
	private String searchCd;
	private String searchVal;
	private String agntCd;
	private String requestStateCode;
	private String serviceType;
	private String usimKindsCd;
	private String esimYn;

	private String acenYn;
	private String selfYn;

	private String pPstateReason;
	
	
	
	//2025-07-01 생년월일 추가
	private String pBirthDayVal;
	
	public String getDwnldRsn() {
		return dwnldRsn;
	}

	public void setDwnldRsn(String dwnldRsn) {
		this.dwnldRsn = dwnldRsn;
	}

	public String getExclDnldId() {
		return exclDnldId;
	}

	public void setExclDnldId(String exclDnldId) {
		this.exclDnldId = exclDnldId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getUserId() {
		return userId;
	}

	public String getBirthDt() {
		return birthDt;
	}

	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}

	public String getCstmrGenderStr() {
		return cstmrGenderStr;
	}

	public void setCstmrGenderStr(String cstmrGenderStr) {
		this.cstmrGenderStr = cstmrGenderStr;
	}

	public String getCntpntShopId() {
		return cntpntShopId;
	}

	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}

	public String getSearchStartDt() {
		return searchStartDt;
	}

	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}

	public String getSearchEndDt() {
		return searchEndDt;
	}

	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
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

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
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

	public String getpSearchGbn() {
		return pSearchGbn;
	}

	public void setpSearchGbn(String pSearchGbn) {
		this.pSearchGbn = pSearchGbn;
	}

	public String getpSearchName() {
		return pSearchName;
	}

	public void setpSearchName(String pSearchName) {
		this.pSearchName = pSearchName;
	}

	public String getpPstate() {
		return pPstate;
	}

	public void setpPstate(String pPstate) {
		this.pPstate = pPstate;
	}

	public String getSprtTp() {
		return sprtTp;
	}

	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getCstmrForeignerRrn() {
		return cstmrForeignerRrn;
	}

	public void setCstmrForeignerRrn(String cstmrForeignerRrn) {
		this.cstmrForeignerRrn = cstmrForeignerRrn;
	}

	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}

	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getReqBuyType() {
		return reqBuyType;
	}

	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}

	public String getMoveCompany() {
		return moveCompany;
	}

	public void setMoveCompany(String moveCompany) {
		this.moveCompany = moveCompany;
	}

	public String getPrdtNm() {
		return prdtNm;
	}

	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}

	public String getPrdtColrNm() {
		return prdtColrNm;
	}

	public void setPrdtColrNm(String prdtColrNm) {
		this.prdtColrNm = prdtColrNm;
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

	public String getPstateName() {
		return pstateName;
	}

	public void setPstateName(String pstateName) {
		this.pstateName = pstateName;
	}

	public String getRequestStateName() {
		return requestStateName;
	}

	public void setRequestStateName(String requestStateName) {
		this.requestStateName = requestStateName;
	}

	public String getOnOffName() {
		return onOffName;
	}

	public void setOnOffName(String onOffName) {
		this.onOffName = onOffName;
	}

	public String getOnOffType() {
		return onOffType;
	}

	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}

	public String getOpenMarketReferer() {
		return openMarketReferer;
	}

	public void setOpenMarketReferer(String openMarketReferer) {
		this.openMarketReferer = openMarketReferer;
	}

	public String getRecYn() {
		return recYn;
	}

	public void setRecYn(String recYn) {
		this.recYn = recYn;
	}

	public String getShopNm() {
		return shopNm;
	}

	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}

	public String getUsrNm() {
		return usrNm;
	}

	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getCstmrAddr() {
		return cstmrAddr;
	}

	public void setCstmrAddr(String cstmrAddr) {
		this.cstmrAddr = cstmrAddr;
	}

	public String getDlvryAddr() {
		return dlvryAddr;
	}

	public void setDlvryAddr(String dlvryAddr) {
		this.dlvryAddr = dlvryAddr;
	}

	public String getDlvryPost() {
		return dlvryPost;
	}

	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
	}

	public String getDlvryMobile() {
		return dlvryMobile;
	}

	public void setDlvryMobile(String dlvryMobile) {
		this.dlvryMobile = dlvryMobile;
	}

	public String getCstmrTelNo() {
		return cstmrTelNo;
	}

	public void setCstmrTelNo(String cstmrTelNo) {
		this.cstmrTelNo = cstmrTelNo;
	}

	public String getSprtTpNm() {
		return sprtTpNm;
	}

	public void setSprtTpNm(String sprtTpNm) {
		this.sprtTpNm = sprtTpNm;
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

	public String getProdTypeNm() {
		return prodTypeNm;
	}

	public void setProdTypeNm(String prodTypeNm) {
		this.prodTypeNm = prodTypeNm;
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

	public String getClauseRentalService() {
		return clauseRentalService;
	}

	public void setClauseRentalService(String clauseRentalService) {
		this.clauseRentalService = clauseRentalService;
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

	public String getRentalModelCpAmt() {
		return rentalModelCpAmt;
	}

	public void setRentalModelCpAmt(String rentalModelCpAmt) {
		this.rentalModelCpAmt = rentalModelCpAmt;
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

	public String getAgntCd() {
		return agntCd;
	}

	public void setAgntCd(String agntCd) {
		this.agntCd = agntCd;
	}

	public String getRequestStateCode() {
		return requestStateCode;
	}

	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getUsimKindsCd() {
		return usimKindsCd;
	}

	public void setUsimKindsCd(String usimKindsCd) {
		this.usimKindsCd = usimKindsCd;
	}

	public String getEsimYn() {
		return esimYn;
	}

	public void setEsimYn(String esimYn) {
		this.esimYn = esimYn;
	}
	
	public String getPrmtNm() {
		return prmtNm;
	}

	public void setPrmtNm(String prmtNm) {
		this.prmtNm = prmtNm;
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

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getSelfYn() {
		return selfYn;
	}

	public void setSelfYn(String selfYn) {
		this.selfYn = selfYn;
	}

	public String getpBirthDayVal() {
		return pBirthDayVal;
	}

	public void setpBirthDayVal(String pBirthDayVal) {
		this.pBirthDayVal = pBirthDayVal;
	}

	public String getpPstateReason() {
		return pPstateReason;
	}

	public void setpPstateReason(String pPstateReason) {
		this.pPstateReason = pPstateReason;
	}

}
