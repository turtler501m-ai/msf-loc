package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class OpenInfoVO extends BaseVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 253146537240514649L;
	
	private String serviceName;
	private String resNo;
	private String scanId;
	private String requestMemo;
	private String cstmrName;
	private String cstmrForeignerRrn;
	private String cstmrForeignerNation;
	private String cstmrMobile;
	private String agentName;
	private String socName;
	private String prdtSctnCd;
	private String prdtSctnCdName;
	private String operName;
	private String reqInDay;
	private String requestStateName;
	private String openReqDate;
	private String pstateName;
	private String cstmrType;
	private String serviceType;
	private String socCode;
	private String operType;
	private String cstmrForeignerPn;
	private String cstmrForeignerSdate;
	private String cstmrForeignerEdate;
	private String cstmrForeignerBirth;
	private String cstmrForeignerBirth2;
	private String cstmrForeignerDod;
	private String cstmrJuridicalCname;
	private String cstmrJuridicalRrn;
	private String cstmrJuridicalNumber1;
	private String cstmrJuridicalNumber2;
	private String cstmrJuridicalNumber3;
	private String cstmrPrivateCname;
	private String cstmrPrivateNumber1;
	private String cstmrPrivateNumber2;
	private String cstmrPrivateNumber3;
	private String cstmrMail;
	private String cstmrMailReceiveFlag;
	private String cstmrAddr;
	private String cstmrAddrDtl;
	private String cstmrBillSendCode;
	private String cstmrTel;
	private String onlineAuthType;
	private String onlineAuthInfo;
	private String pstate;
	private String requestStateCode;
	private String onOffType;
	private String onOffName;
	private String paperInfo;
	private String phonePayment;
	private String dlvryName;
	private String dlvryTel;
	private String dlvryMobile;
	private String dlvryAddr;
	private String dlvryAddrDtl;
	private String dlvryMemo;
	private String reqPayType;
	private String reqBank;
	private String reqAccountNumber;
	private String reqAccountName;
	private String reqAccountRrn;
	private String reqCardCompany;
	private String reqCardNo;
	private String reqCardMm;
	private String reqCardYy;
	private String reqCardName;
	private String reqCardRrn;
	private String reqBuyType;
	private String prdtCode;
	private String prdtId;
	private String reqWantNumber;
	private String reqWantNumber2;
	private String reqWantNumber3;
	private String reqGuideFlag;
	private String reqGuide;
	private String reqWireType1;
	private String reqWireType2;
	private String reqWireType3;
	private String moveMobile;
	private String moveCompany;
	private String moveThismonthPayType;
	private String moveAllotmentStat;
	private String moveRefundAgreeFlag;
	private String moveAuthType;
	private String minorAgentName;
	private String minorAgentRelation;
	private String minorAgentRrn;
	private String minorAgentTel;
	private String requestKey;
	private String additionKey;
	private String additionName;
	private String agentCode;
	private String cntpntShopId;
	private String cstmrPost;
	private String clausePriCollectFlag;
	private String clausePriOfferFlag;
	private String clauseEssCollectFlag;
	private String clausePriTrustFlag;
	private String clausePriAdFlag;
	private String clauseConfidenceFlag;
	private String reqUsimSn;
	private String reqPhoneSn;
	private String othersPaymentAg;
	private String spcCode;
	private String dlvryPost;
	private String moveAuthNumber;
	private String cstmrNativeRrn;
	private String trmnlInfo;
	private String reqPayOtherFlag;
	private String reqAcType;
	private String modelInstallment;
	private String realMdlInstamt;
	private String recycleYn;
	private String modelDiscount1;
	private String modelDiscount2;
	private String modelDiscount3;
	private String joinPrice;
	private String usimPrice;
	private String modelPrice;
	private String modelPriceVat;
	private String hndstAmt;
	private String maxDiscount3;
	private String dcAmt;
	private String addDcAmt;
	private String reqAdditionPrice;
	private String instNom;
	private String shopNm;
	private String usrNm;
	private String reqAc01Balance;
	private String reqAc02Day;
	private String reqAcAmount;
	private String reqModelColor;
	private String tbCd;
	private String dlvryNo;
	private String managerCode;
	private String salePlcyCd;
	private String salePlcyNm;
	private String faxyn;
	private String faxnum;
	private String faxBySearch;
	private String modelId;
	private String agrmTrm;
	private String mnfctId;
	private String reqModelName;
	private String reqUsimName;
	private String recYn;
	private String openMarketReferer;
	private String nwBlckAgrmYn;
	private String appBlckAgrmYn;
	private String appCd;
	private String dvcChgType;
	private String dvcChgRsnCd;
	private String dvcChgRsnDtlCd;
	private String usimPayMthdCd;
	private String joinPayMthdCd;
	private String clauseJehuFlag;
	private String cstmrJejuId;
	private String sprtTp;
	private String prodType;
	private String clauseRentalModelCp;
	private String clauseRentalModelCpPr;
	private String clauseRentalService;
	private String rentalBaseAmt;
	private String rentalBaseDcAmt;
	private String rentalModelCpAmt;
	
	private String cstmrForeignerRrn1;
	private String cstmrForeignerRrn2;
	private String cstmrMobileFn;
	private String cstmrMobileMn;
	private String cstmrMobileRn;
	private String cstmrJuridicalRrn1;
	private String cstmrJuridicalRrn2;
	private String cstmrMail1;
	private String cstmrMail2;
	private String cstmrTelFn;
	private String cstmrTelMn;
	private String cstmrTelRn;
	private String dlvryTelFn;
	private String dlvryTelMn;
	private String dlvryTelRn;
	private String dlvryMobileFn;
	private String dlvryMobileMn;
	private String dlvryMobileRn;
	private String reqAccountRrn1;
	private String reqAccountRrn2;
	private String reqCardNo1;
	private String reqCardNo2;
	private String reqCardNo3;
	private String reqCardNo4;
	private String reqCardRrn1;
	private String reqCardRrn2;
	private String reqGuideFn;
	private String reqGuideMn;
	private String reqGuideRn;
	private String moveMobileFn;
	private String moveMobileMn;
	private String moveMobileRn;
	private String minorAgentRrn1;
	private String minorAgentRrn2;
	private String minorAgentTelFn;
	private String minorAgentTelMn;
	private String minorAgentTelRn;
	private String cstmrNativeRrn1;
	private String cstmrNativeRrn2;
	private String faxnum1;
	private String faxnum2;
	private String faxnum3;
	private String cstmrGenderStr;
	private String birthDay;
	private String clauseFinanceFlag;
	
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
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getRequestMemo() {
		return requestMemo;
	}
	public void setRequestMemo(String requestMemo) {
		this.requestMemo = requestMemo;
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
	public String getCstmrForeignerNation() {
		return cstmrForeignerNation;
	}
	public void setCstmrForeignerNation(String cstmrForeignerNation) {
		this.cstmrForeignerNation = cstmrForeignerNation;
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
	public String getPrdtSctnCd() {
		return prdtSctnCd;
	}
	public void setPrdtSctnCd(String prdtSctnCd) {
		this.prdtSctnCd = prdtSctnCd;
	}
	public String getPrdtSctnCdName() {
		return prdtSctnCdName;
	}
	public void setPrdtSctnCdName(String prdtSctnCdName) {
		this.prdtSctnCdName = prdtSctnCdName;
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
	public String getCstmrForeignerPn() {
		return cstmrForeignerPn;
	}
	public void setCstmrForeignerPn(String cstmrForeignerPn) {
		this.cstmrForeignerPn = cstmrForeignerPn;
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
	public String getCstmrForeignerBirth() {
		return cstmrForeignerBirth;
	}
	public void setCstmrForeignerBirth(String cstmrForeignerBirth) {
		this.cstmrForeignerBirth = cstmrForeignerBirth;
	}
	public String getCstmrForeignerBirth2() {
		return cstmrForeignerBirth2;
	}
	public void setCstmrForeignerBirth2(String cstmrForeignerBirth2) {
		this.cstmrForeignerBirth2 = cstmrForeignerBirth2;
	}
	public String getCstmrForeignerDod() {
		return cstmrForeignerDod;
	}
	public void setCstmrForeignerDod(String cstmrForeignerDod) {
		this.cstmrForeignerDod = cstmrForeignerDod;
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
	public String getCstmrTel() {
		return cstmrTel;
	}
	public void setCstmrTel(String cstmrTel) {
		this.cstmrTel = cstmrTel;
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
	public String getOnOffName() {
		return onOffName;
	}
	public void setOnOffName(String onOffName) {
		this.onOffName = onOffName;
	}
	public String getPaperInfo() {
		return paperInfo;
	}
	public void setPaperInfo(String paperInfo) {
		this.paperInfo = paperInfo;
	}
	public String getPhonePayment() {
		return phonePayment;
	}
	public void setPhonePayment(String phonePayment) {
		this.phonePayment = phonePayment;
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
	public String getDlvryMobile() {
		return dlvryMobile;
	}
	public void setDlvryMobile(String dlvryMobile) {
		this.dlvryMobile = dlvryMobile;
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
	public String getReqAccountRrn() {
		return reqAccountRrn;
	}
	public void setReqAccountRrn(String reqAccountRrn) {
		this.reqAccountRrn = reqAccountRrn;
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
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getPrdtCode() {
		return prdtCode;
	}
	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
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
	public String getReqGuide() {
		return reqGuide;
	}
	public void setReqGuide(String reqGuide) {
		this.reqGuide = reqGuide;
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
	public String getMoveMobile() {
		return moveMobile;
	}
	public void setMoveMobile(String moveMobile) {
		this.moveMobile = moveMobile;
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
	public String getMinorAgentRrn() {
		return minorAgentRrn;
	}
	public void setMinorAgentRrn(String minorAgentRrn) {
		this.minorAgentRrn = minorAgentRrn;
	}
	public String getMinorAgentTel() {
		return minorAgentTel;
	}
	public void setMinorAgentTel(String minorAgentTel) {
		this.minorAgentTel = minorAgentTel;
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
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	public String getCstmrPost() {
		return cstmrPost;
	}
	public void setCstmrPost(String cstmrPost) {
		this.cstmrPost = cstmrPost;
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
	public String getOthersPaymentAg() {
		return othersPaymentAg;
	}
	public void setOthersPaymentAg(String othersPaymentAg) {
		this.othersPaymentAg = othersPaymentAg;
	}
	public String getSpcCode() {
		return spcCode;
	}
	public void setSpcCode(String spcCode) {
		this.spcCode = spcCode;
	}
	public String getDlvryPost() {
		return dlvryPost;
	}
	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
	}
	public String getMoveAuthNumber() {
		return moveAuthNumber;
	}
	public void setMoveAuthNumber(String moveAuthNumber) {
		this.moveAuthNumber = moveAuthNumber;
	}
	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}
	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}
	public String getTrmnlInfo() {
		return trmnlInfo;
	}
	public void setTrmnlInfo(String trmnlInfo) {
		this.trmnlInfo = trmnlInfo;
	}
	public String getReqPayOtherFlag() {
		return reqPayOtherFlag;
	}
	public void setReqPayOtherFlag(String reqPayOtherFlag) {
		this.reqPayOtherFlag = reqPayOtherFlag;
	}
	public String getReqAcType() {
		return reqAcType;
	}
	public void setReqAcType(String reqAcType) {
		this.reqAcType = reqAcType;
	}
	public String getModelInstallment() {
		return modelInstallment;
	}
	public void setModelInstallment(String modelInstallment) {
		this.modelInstallment = modelInstallment;
	}
	public String getRealMdlInstamt() {
		return realMdlInstamt;
	}
	public void setRealMdlInstamt(String realMdlInstamt) {
		this.realMdlInstamt = realMdlInstamt;
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
	public String getModelPriceVat() {
		return modelPriceVat;
	}
	public void setModelPriceVat(String modelPriceVat) {
		this.modelPriceVat = modelPriceVat;
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
	public String getReqAdditionPrice() {
		return reqAdditionPrice;
	}
	public void setReqAdditionPrice(String reqAdditionPrice) {
		this.reqAdditionPrice = reqAdditionPrice;
	}
	public String getInstNom() {
		return instNom;
	}
	public void setInstNom(String instNom) {
		this.instNom = instNom;
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
	public String getReqModelColor() {
		return reqModelColor;
	}
	public void setReqModelColor(String reqModelColor) {
		this.reqModelColor = reqModelColor;
	}
	public String getTbCd() {
		return tbCd;
	}
	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}
	public String getDlvryNo() {
		return dlvryNo;
	}
	public void setDlvryNo(String dlvryNo) {
		this.dlvryNo = dlvryNo;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getSalePlcyCd() {
		return salePlcyCd;
	}
	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}
	public String getSalePlcyNm() {
		return salePlcyNm;
	}
	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
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
	public String getFaxBySearch() {
		return faxBySearch;
	}
	public void setFaxBySearch(String faxBySearch) {
		this.faxBySearch = faxBySearch;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getAgrmTrm() {
		return agrmTrm;
	}
	public void setAgrmTrm(String agrmTrm) {
		this.agrmTrm = agrmTrm;
	}
	public String getMnfctId() {
		return mnfctId;
	}
	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
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
	public String getRecYn() {
		return recYn;
	}
	public void setRecYn(String recYn) {
		this.recYn = recYn;
	}
	public String getOpenMarketReferer() {
		return openMarketReferer;
	}
	public void setOpenMarketReferer(String openMarketReferer) {
		this.openMarketReferer = openMarketReferer;
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
	public String getClauseJehuFlag() {
		return clauseJehuFlag;
	}
	public void setClauseJehuFlag(String clauseJehuFlag) {
		this.clauseJehuFlag = clauseJehuFlag;
	}
	public String getCstmrJejuId() {
		return cstmrJejuId;
	}
	public void setCstmrJejuId(String cstmrJejuId) {
		this.cstmrJejuId = cstmrJejuId;
	}
	public String getSprtTp() {
		return sprtTp;
	}
	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
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
	public String getReqGuideFn() {
		return reqGuideFn;
	}
	public void setReqGuideFn(String reqGuideFn) {
		this.reqGuideFn = reqGuideFn;
	}
	public String getReqGuideMn() {
		return reqGuideMn;
	}
	public void setReqGuideMn(String reqGuideMn) {
		this.reqGuideMn = reqGuideMn;
	}
	public String getReqGuideRn() {
		return reqGuideRn;
	}
	public void setReqGuideRn(String reqGuideRn) {
		this.reqGuideRn = reqGuideRn;
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
	public String getCstmrGenderStr() {
		return cstmrGenderStr;
	}
	public void setCstmrGenderStr(String cstmrGenderStr) {
		this.cstmrGenderStr = cstmrGenderStr;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getClauseFinanceFlag() {
		return clauseFinanceFlag;
	}
	public void setClauseFinanceFlag(String clauseFinanceFlag) {
		this.clauseFinanceFlag = clauseFinanceFlag;
	}
}
