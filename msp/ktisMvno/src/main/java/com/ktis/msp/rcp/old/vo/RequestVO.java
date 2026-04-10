package com.ktis.msp.rcp.old.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @project : default
 * @file 	: RequestVO.java
 * @author : ParkDongHee
 * @date : 2014. 5. 25.
 * @history :
 * 
 * @comment : 
 *  
 */

public class RequestVO extends BaseVo implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 188013984807913913L;
	
	private String dlvryMobile;
	private String dlvryTel;
	private String jrdclAgentTel;
	private String minorAgentTel;
	private String moveMobile;
	private String reqGuide;
	private String cstmrMobile;
	private String cstmrTel;
	private String othersPaymentRrn;
	private String entrustResRrn;
	private String nameChangeRrn;
	private Integer requestKey;
	private Integer rateKey;
	private String  serviceType;
	private String  rateCategoryCode;
	private String  rateCategoryName;
	private String  rateCode;
	private String  rateName;
	private String  operType;
	private String  cstmrType;
	private String  cstmrName;
	private String  cstmrNativeRrn;
	private String  cstmrForeignerNation;
	private String  cstmrForeignerRrn;
	private String  cstmrForeignerPn;
	private String  cstmrForeignerSdate;
	private String  cstmrForeignerEdate;
	private String  cstmrPrivateCname;
	private String  cstmrPrivateNumber;
	private String  cstmrJuridicalCname;
	private String  cstmrJuridicalRrn;
	private String  cstmrJuridicalNumber;
	private String  cstmrTelFn;
	private String  cstmrTelMn;
	private String  cstmrTelRn;
	private String  cstmrMobileFn;
	private String  cstmrMobileMn;
	private String  cstmrMobileRn;
	private String  cstmrPost;
	private String  cstmrAddr;
	private String  cstmrAddrDtl;
	private String  cstmrBillSendCode;
	private String  cstmrMail;
	private String  cstmrMailReceiveFlag;
	private String  reqWantNumber;
	private String  reqPayType;
	private String  reqBank;
	private String  reqAccountName;
	private String  reqAccountRrn;
	private String  reqAccountRelation;
	private String  reqAccountNumber;
	private String  reqCardName;
	private String  reqCardRrn;
	private String  reqCardCompany;
	private String  reqCardNo;
	private String  reqCardYy;
	private String  reqCardMm;
	private String  reqAcType;
	private String  reqAc01Balance;
	private String  reqAc01Amount;
	private String  reqAc02Day;
	private String  reqAc02Amount;
	private String  reqGuideFlag;
	private String  reqGuideFn;
	private String  reqGuideMn;
	private String  reqGuideRn;
	private String  reqWireType;
	private String  reqBuyType;
	private String  reqModelName;
	private String  reqAddition;
	private String  moveCompany;
	private String  moveMobileFn;
	private String  moveMobileMn;
	private String  moveMobileRn;
	private String  moveAuthType;
	private String  moveAuthNumber;
	private String  moveThismonthPayType;
	private String  moveAllotmentStat;
	private String  moveRefundAgreeFlag;
	private String  minorAgentName;
	private String  minorAgentRrn;
	private String  minorAgentRelation;
	private String  minorAgentTelFn;
	private String  minorAgentTelMn;
	private String  minorAgentTelRn;
	private String  jrdclAgentName;
	private String  jrdclAgentRrn;
	//private String  minorMandateRelation;
	private String  jrdclAgentTelFn;
	private String  jrdclAgentTelMn;
	private String  jrdclAgentTelRn;
	private String  dlvryName = "";
	private String  dlvryTelFn = "";
	private String  dlvryTelMn = "";
	private String  dlvryTelRn = "";
	private String  dlvryMobileFn = "";
	private String  dlvryMobileMn = "";
	private String  dlvryMobileRn = "";
	private String  dlvryPost = "";
	private String  dlvryAddr = "";
	private String  dlvryAddrDtl = "";
	private String  dlvryMemo = "";
	private String  clausePriCollectFlag;
	private String  clausePriOfferFlag;
	private String  clauseEssCollectFlag;
	private String  clausePriTrustFlag;
	private String  clausePriAdFlag;
	private String  clauseConfidenceFlag;
	//private String  agencyWelfareCode;
	private String  onlineAuthType;
	private String  onlineAuthInfo;
	private String  resCode;
	private String  resMsg;
	private String  resNo;
	private String  pstate;
	private String  requestStateCode;
	private String  openNo;
	private String  rip;
	private String  rdate;
	
	//20140723 add
	private String  agentCode;
	private String  managerCode;
	private String  file01;
	private String  file01Mask;
	
	//20140829 add
	private String wareSeq;
	private String dataTypeCode;
	private String ktRateCode;
	private BigDecimal chgJoin;
	private BigDecimal chgUsim;
	private String reqAdditionPrice;
	
	//20140829 erd 추가건
	private String faxyn;
	private String faxnum;
	private String scanId;
	private String onOffType;
	private String sysRdate;
	private String reqWantNumber2;
	private String reqWantNumber3;
	private String reqModelColor;
	private String reqPhoneSn;
	private String reqUsimSn;
	private String shopCd;
	private String appFormYn;
	private String reqInDay;
	private String contractNum;
	private String etcSpecial;
	private String reqUsimName;
	private String phonePayment;
	private String wareTypeCd;
	private String cstmrAddrBjd;
	private String modelMonthly;
	private String joinPriceType;
	private String usimPriceType;
	private String reqAdditionKey;
	private String reqAdditionRantal;
	private String wareImgName;
	private String reqPayOtherFlag;
	private String reqPayOtherTelFn;
	private String reqPayOtherTelMn;
	private String reqPayOtherTelRn;
	private String joinPrice;
	private String usimPrice;
	private String cntpntShopId;
	private String cntpntShopGb;
	private String wareName;
	
	private String appFormXmlYn;
	private String modelSalePolicyCode;
	private String modelPrice;
	private String modelPriceVat;
	private String modelId;
	private String modelInstallment;
	private String modelDiscount1;
	private String modelDiscount2;
	private String socCode;
	private String enggMnthCnt;
	private String recycleYn;

	public RequestVO(){
		requestKey = 0;
		rateKey = 0;
		serviceType = "";
		rateCategoryCode = "";
		rateCategoryName = "";
		rateCode = "";
		rateName = "";
		operType = "";
		cstmrType = "";
		cstmrName = "";
		cstmrNativeRrn = "";
		cstmrForeignerNation = "";
		cstmrForeignerRrn = "";
		cstmrForeignerPn = "";
		cstmrForeignerSdate = "";
		cstmrForeignerEdate = "";
		cstmrPrivateCname = "";
		cstmrPrivateNumber = "";
		cstmrJuridicalCname = "";
		cstmrJuridicalRrn = "";
		cstmrJuridicalNumber = "";
		cstmrTelFn = "";
		cstmrTelMn = "";
		cstmrTelRn = "";
		cstmrMobileFn = "";
		cstmrMobileMn = "";
		cstmrMobileRn = "";
		cstmrPost = "";
		cstmrAddr = "";
		cstmrAddrDtl = "";
		cstmrBillSendCode = "";
		cstmrMail = "";
		cstmrMailReceiveFlag = "";
		reqWantNumber = "";
		reqPayType = "";
		reqBank = "";
		reqAccountName = "";
		reqAccountRrn = "";
		reqAccountRelation = "";
		reqAccountNumber = "";
		reqCardName = "";
		reqCardRrn = "";
		reqCardCompany = "";
		reqCardNo = "";
		reqAcType = "";
		reqAc01Balance = "";
		reqAc01Amount = "";
		reqAc02Day = "";
		reqAc02Amount = "";
		reqGuideFlag = "";
		reqGuideFn = "";
		reqGuideMn = "";
		reqGuideRn = "";
		reqWireType = "";
		reqBuyType = "";
		reqModelName = "";
		reqAddition = "";
		moveCompany = "";
		moveMobileFn = "";
		moveMobileMn = "";
		moveMobileRn = "";
		moveAuthType = "";
		moveAuthNumber = "";
		moveThismonthPayType = "";
		moveAllotmentStat = "";
		moveRefundAgreeFlag = "";
		minorAgentName = "";
		minorAgentRrn = "";
		minorAgentRelation = "";
		minorAgentTelFn = "";
		minorAgentTelMn = "";
		minorAgentTelRn = "";
		jrdclAgentName = "";
		jrdclAgentRrn = "";
		//jrdclAgentRelation = "";
		jrdclAgentTelFn = "";
		jrdclAgentTelMn = "";
		jrdclAgentTelRn = "";
		dlvryName = "";
		dlvryTelFn = "";
		dlvryTelMn = "";
		dlvryTelRn = "";
		dlvryMobileFn = "";
		dlvryMobileMn = "";
		dlvryMobileRn = "";
		dlvryPost = "";
		dlvryAddr = "";
		dlvryAddrDtl = "";
		dlvryMemo = "";
		clausePriCollectFlag = "";
		clausePriOfferFlag = "";
		clauseEssCollectFlag = "";
		clausePriTrustFlag = "";
		clausePriAdFlag = "";
		clauseConfidenceFlag = "";
		//agencyWelfareCode = "";
		onlineAuthType = "";
		resCode = "";
		resMsg = "";
		resNo = "";
		pstate = "";
		requestStateCode = "";
		openNo = "";
		rip = "";
		rdate = "";
		
		agentCode = "";
		managerCode = "";
		file01 = "";
		file01Mask = "";
		
		//20140829 add
		wareSeq = "";
		dataTypeCode = "";
		ktRateCode = "";
		chgJoin = BigDecimal.ZERO;
		chgUsim = BigDecimal.ZERO;
		faxyn = "";
		faxnum = "";
		scanId = "";
		onOffType = "";
		sysRdate = "";
		reqWantNumber2 = "";
		reqWantNumber3 = "";
		reqModelColor = "";
		reqPhoneSn = "";
		reqUsimSn = "";
		shopCd = "";
		appFormYn = "";
		reqInDay = "";
		contractNum = "";
		etcSpecial = "";
		reqUsimName = "";
		phonePayment = "";
		reqAdditionPrice = "";
		wareTypeCd = "";
		cstmrAddrBjd = "";
		modelMonthly = "";
		joinPriceType = "";
		usimPriceType = "";
		reqAdditionKey = "";
		reqAdditionRantal = "";
		wareImgName = "";
		reqPayOtherFlag = "";
		reqPayOtherTelFn = "";
		reqPayOtherTelMn = "";
		reqPayOtherTelRn = "";
		joinPrice = "";
		usimPrice = "";
		cntpntShopId= "";
		cntpntShopGb = "";
		wareName = "";
		appFormXmlYn = "";
		
		modelSalePolicyCode = "";
		modelPrice = "";
		modelPriceVat = "";
		modelId = "";
		modelInstallment = "";
		modelDiscount1 = "";
		modelDiscount2 = "";
		socCode = "";
		enggMnthCnt = "";
		recycleYn = "";
		
	}
	
	/**
	 * @return the modelSalePolicyCode
	 */
	public String getModelSalePolicyCode() {
		return modelSalePolicyCode;
	}

	/**
	 * @param modelSalePolicyCode the modelSalePolicyCode to set
	 */
	public void setModelSalePolicyCode(String modelSalePolicyCode) {
		this.modelSalePolicyCode = modelSalePolicyCode;
	}

	/**
	 * @return the modelPrice
	 */
	public String getModelPrice() {
		return modelPrice;
	}

	/**
	 * @param modelPrice the modelPrice to set
	 */
	public void setModelPrice(String modelPrice) {
		this.modelPrice = modelPrice;
	}

	/**
	 * @return the modelPriceVat
	 */
	public String getModelPriceVat() {
		return modelPriceVat;
	}

	/**
	 * @param modelPriceVat the modelPriceVat to set
	 */
	public void setModelPriceVat(String modelPriceVat) {
		this.modelPriceVat = modelPriceVat;
	}

	/**
	 * @return the modelId
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * @param modelId the modelId to set
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/**
	 * @return the modelInstallment
	 */
	public String getModelInstallment() {
		return modelInstallment;
	}

	/**
	 * @param modelInstallment the modelInstallment to set
	 */
	public void setModelInstallment(String modelInstallment) {
		this.modelInstallment = modelInstallment;
	}

	/**
	 * @return the modelDiscount1
	 */
	public String getModelDiscount1() {
		return modelDiscount1;
	}

	/**
	 * @param modelDiscount1 the modelDiscount1 to set
	 */
	public void setModelDiscount1(String modelDiscount1) {
		this.modelDiscount1 = modelDiscount1;
	}

	/**
	 * @return the modelDiscount2
	 */
	public String getModelDiscount2() {
		return modelDiscount2;
	}

	/**
	 * @param modelDiscount2 the modelDiscount2 to set
	 */
	public void setModelDiscount2(String modelDiscount2) {
		this.modelDiscount2 = modelDiscount2;
	}

	/**
	 * @return the socCode
	 */
	public String getSocCode() {
		return socCode;
	}

	/**
	 * @param socCode the socCode to set
	 */
	public void setSocCode(String socCode) {
		this.socCode = socCode;
	}

	/**
	 * @return the enggMnthCnt
	 */
	public String getEnggMnthCnt() {
		return enggMnthCnt;
	}

	/**
	 * @param enggMnthCnt the enggMnthCnt to set
	 */
	public void setEnggMnthCnt(String enggMnthCnt) {
		this.enggMnthCnt = enggMnthCnt;
	}

	/**
	 * @return the recycleYn
	 */
	public String getRecycleYn() {
		return recycleYn;
	}

	/**
	 * @param recycleYn the recycleYn to set
	 */
	public void setRecycleYn(String recycleYn) {
		this.recycleYn = recycleYn;
	}

	/**
	 * @return the appFormXmlYn
	 */
	public String getAppFormXmlYn() {
		return appFormXmlYn;
	}

	/**
	 * @param appFormXmlYn the appFormXmlYn to set
	 */
	public void setAppFormXmlYn(String appFormXmlYn) {
		this.appFormXmlYn = appFormXmlYn;
	}

	/**
	 * @return the wareName
	 */
	public String getWareName() {
		return wareName;
	}

	/**
	 * @param wareName the wareName to set
	 */
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	/**
	 * @return the cntpntShopGb
	 */
	public String getCntpntShopGb() {
		return cntpntShopGb;
	}

	/**
	 * @param cntpntShopGb the cntpntShopGb to set
	 */
	public void setCntpntShopGb(String cntpntShopGb) {
		this.cntpntShopGb = cntpntShopGb;
	}

	/**
	 * @return the cntpntShopId
	 */
	public String getCntpntShopId() {
		return cntpntShopId;
	}

	/**
	 * @param cntpntShopId the cntpntShopId to set
	 */
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}

	/**
	 * @return the joinPrice
	 */
	public String getJoinPrice() {
		return joinPrice;
	}

	/**
	 * @param joinPrice the joinPrice to set
	 */
	public void setJoinPrice(String joinPrice) {
		this.joinPrice = joinPrice;
	}

	/**
	 * @return the usimPrice
	 */
	public String getUsimPrice() {
		return usimPrice;
	}

	/**
	 * @param usimPrice the usimPrice to set
	 */
	public void setUsimPrice(String usimPrice) {
		this.usimPrice = usimPrice;
	}

	/**
	 * @return the reqPayOtherFlag
	 */
	public String getReqPayOtherFlag() {
		return reqPayOtherFlag;
	}

	/**
	 * @param reqPayOtherFlag the reqPayOtherFlag to set
	 */
	public void setReqPayOtherFlag(String reqPayOtherFlag) {
		this.reqPayOtherFlag = reqPayOtherFlag;
	}

	/**
	 * @return the reqPayOtherTelFn
	 */
	public String getReqPayOtherTelFn() {
		return reqPayOtherTelFn;
	}

	/**
	 * @param reqPayOtherTelFn the reqPayOtherTelFn to set
	 */
	public void setReqPayOtherTelFn(String reqPayOtherTelFn) {
		this.reqPayOtherTelFn = reqPayOtherTelFn;
	}

	/**
	 * @return the reqPayOtherTelMn
	 */
	public String getReqPayOtherTelMn() {
		return reqPayOtherTelMn;
	}

	/**
	 * @param reqPayOtherTelMn the reqPayOtherTelMn to set
	 */
	public void setReqPayOtherTelMn(String reqPayOtherTelMn) {
		this.reqPayOtherTelMn = reqPayOtherTelMn;
	}

	/**
	 * @return the reqPayOtherTelRn
	 */
	public String getReqPayOtherTelRn() {
		return reqPayOtherTelRn;
	}

	/**
	 * @param reqPayOtherTelRn the reqPayOtherTelRn to set
	 */
	public void setReqPayOtherTelRn(String reqPayOtherTelRn) {
		this.reqPayOtherTelRn = reqPayOtherTelRn;
	}

	/**
	 * @return the wareImgName
	 */
	public String getWareImgName() {
		return wareImgName;
	}

	/**
	 * @param wareImgName the wareImgName to set
	 */
	public void setWareImgName(String wareImgName) {
		this.wareImgName = wareImgName;
	}

	/**
	 * @return the reqAdditionRantal
	 */
	public String getReqAdditionRantal() {
		return reqAdditionRantal;
	}

	/**
	 * @param reqAdditionRantal the reqAdditionRantal to set
	 */
	public void setReqAdditionRantal(String reqAdditionRantal) {
		this.reqAdditionRantal = reqAdditionRantal;
	}

	/**
	 * @return the reqAdditionKey
	 */
	public String getReqAdditionKey() {
		return reqAdditionKey;
	}

	/**
	 * @param reqAdditionKey the reqAdditionKey to set
	 */
	public void setReqAdditionKey(String reqAdditionKey) {
		this.reqAdditionKey = reqAdditionKey;
	}


	/**
	 * @return the joinPriceType
	 */
	public String getJoinPriceType() {
		return joinPriceType;
	}


	/**
	 * @param joinPriceType the joinPriceType to set
	 */
	public void setJoinPriceType(String joinPriceType) {
		this.joinPriceType = joinPriceType;
	}


	/**
	 * @return the usimPriceType
	 */
	public String getUsimPriceType() {
		return usimPriceType;
	}


	/**
	 * @param usimPriceType the usimPriceType to set
	 */
	public void setUsimPriceType(String usimPriceType) {
		this.usimPriceType = usimPriceType;
	}


	/**
	 * @return the modelMonthly
	 */
	public String getModelMonthly() {
		return modelMonthly;
	}


	/**
	 * @param modelMonthly the modelMonthly to set
	 */
	public void setModelMonthly(String modelMonthly) {
		this.modelMonthly = modelMonthly;
	}


	/**
	 * @return the cstmrAddrBjd
	 */
	public String getCstmrAddrBjd() {
		return cstmrAddrBjd;
	}


	/**
	 * @param cstmrAddrBjd the cstmrAddrBjd to set
	 */
	public void setCstmrAddrBjd(String cstmrAddrBjd) {
		this.cstmrAddrBjd = cstmrAddrBjd;
	}


	/**
	 * @return the wareTypeCd
	 */
	public String getWareTypeCd() {
		return wareTypeCd;
	}


	/**
	 * @param wareTypeCd the wareTypeCd to set
	 */
	public void setWareTypeCd(String wareTypeCd) {
		this.wareTypeCd = wareTypeCd;
	}


	/**
	 * @return the reqAdditionPrice
	 */
	public String getReqAdditionPrice() {
		return reqAdditionPrice;
	}


	/**
	 * @param reqAdditionPrice the reqAdditionPrice to set
	 */
	public void setReqAdditionPrice(String reqAdditionPrice) {
		this.reqAdditionPrice = reqAdditionPrice;
	}


	/**
	 * @return the faxyn
	 */
	public String getFaxyn() {
		return faxyn;
	}


	/**
	 * @param faxyn the faxyn to set
	 */
	public void setFaxyn(String faxyn) {
		this.faxyn = faxyn;
	}


	/**
	 * @return the faxnum
	 */
	public String getFaxnum() {
		return faxnum;
	}


	/**
	 * @param faxnum the faxnum to set
	 */
	public void setFaxnum(String faxnum) {
		this.faxnum = faxnum;
	}


	/**
	 * @return the scanId
	 */
	public String getScanId() {
		return scanId;
	}


	/**
	 * @param scanId the scanId to set
	 */
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}


	/**
	 * @return the onOffType
	 */
	public String getOnOffType() {
		return onOffType;
	}


	/**
	 * @param onOffType the onOffType to set
	 */
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}


	/**
	 * @return the sysRdate
	 */
	public String getSysRdate() {
		return sysRdate;
	}


	/**
	 * @param sysRdate the sysRdate to set
	 */
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}


	/**
	 * @return the reqWantNumber2
	 */
	public String getReqWantNumber2() {
		return reqWantNumber2;
	}


	/**
	 * @param reqWantNumber2 the reqWantNumber2 to set
	 */
	public void setReqWantNumber2(String reqWantNumber2) {
		this.reqWantNumber2 = reqWantNumber2;
	}


	/**
	 * @return the reqWantNumber3
	 */
	public String getReqWantNumber3() {
		return reqWantNumber3;
	}


	/**
	 * @param reqWantNumber3 the reqWantNumber3 to set
	 */
	public void setReqWantNumber3(String reqWantNumber3) {
		this.reqWantNumber3 = reqWantNumber3;
	}


	/**
	 * @return the reqModelColor
	 */
	public String getReqModelColor() {
		return reqModelColor;
	}


	/**
	 * @param reqModelColor the reqModelColor to set
	 */
	public void setReqModelColor(String reqModelColor) {
		this.reqModelColor = reqModelColor;
	}


	/**
	 * @return the reqPhoneSn
	 */
	public String getReqPhoneSn() {
		return reqPhoneSn;
	}


	/**
	 * @param reqPhoneSn the reqPhoneSn to set
	 */
	public void setReqPhoneSn(String reqPhoneSn) {
		this.reqPhoneSn = reqPhoneSn;
	}


	/**
	 * @return the reqUsimSn
	 */
	public String getReqUsimSn() {
		return reqUsimSn;
	}


	/**
	 * @param reqUsimSn the reqUsimSn to set
	 */
	public void setReqUsimSn(String reqUsimSn) {
		this.reqUsimSn = reqUsimSn;
	}


	/**
	 * @return the shopCd
	 */
	public String getShopCd() {
		return shopCd;
	}


	/**
	 * @param shopCd the shopCd to set
	 */
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}


	/**
	 * @return the appFormYn
	 */
	public String getAppFormYn() {
		return appFormYn;
	}


	/**
	 * @param appFormYn the appFormYn to set
	 */
	public void setAppFormYn(String appFormYn) {
		this.appFormYn = appFormYn;
	}


	/**
	 * @return the reqInDay
	 */
	public String getReqInDay() {
		return reqInDay;
	}


	/**
	 * @param reqInDay the reqInDay to set
	 */
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}


	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}


	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}


	/**
	 * @return the etcSpecial
	 */
	public String getEtcSpecial() {
		return etcSpecial;
	}


	/**
	 * @param etcSpecial the etcSpecial to set
	 */
	public void setEtcSpecial(String etcSpecial) {
		this.etcSpecial = etcSpecial;
	}


	/**
	 * @return the reqUsimName
	 */
	public String getReqUsimName() {
		return reqUsimName;
	}


	/**
	 * @param reqUsimName the reqUsimName to set
	 */
	public void setReqUsimName(String reqUsimName) {
		this.reqUsimName = reqUsimName;
	}


	/**
	 * @return the phonePayment
	 */
	public String getPhonePayment() {
		return phonePayment;
	}


	/**
	 * @param phonePayment the phonePayment to set
	 */
	public void setPhonePayment(String phonePayment) {
		this.phonePayment = phonePayment;
	}


	/**
	 * @return the chgJoin
	 */
	public BigDecimal getChgJoin() {
		return chgJoin;
	}


	/**
	 * @param chgJoin the chgJoin to set
	 */
	public void setChgJoin(BigDecimal chgJoin) {
		this.chgJoin = chgJoin;
	}


	/**
	 * @return the chgUsim
	 */
	public BigDecimal getChgUsim() {
		return chgUsim;
	}


	/**
	 * @param chgUsim the chgUsim to set
	 */
	public void setChgUsim(BigDecimal chgUsim) {
		this.chgUsim = chgUsim;
	}
	
	
	/**
	 * @return the dataTypeCode
	 */
	public String getDataTypeCode() {
		return dataTypeCode;
	}


	/**
	 * @param dataTypeCode the dataTypeCode to set
	 */
	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}


	/**
	 * @return the ktRateCode
	 */
	public String getKtRateCode() {
		return ktRateCode;
	}


	/**
	 * @param ktRateCode the ktRateCode to set
	 */
	public void setKtRateCode(String ktRateCode) {
		this.ktRateCode = ktRateCode;
	}


	/**
	 * @return the wareSeq
	 */
	public String getWareSeq() {
		return wareSeq;
	}


	/**
	 * @param wareSeq the wareSeq to set
	 */
	public void setWareSeq(String wareSeq) {
		this.wareSeq = wareSeq;
	}
	
	
	/**
	 * @return the requestKey
	 */
	public Integer getRequestKey() {
		return requestKey;
	}

	/**
	 * @param requestKey the requestKey to set
	 */
	public void setRequestKey(Integer requestKey) {
		this.requestKey = requestKey;
	}

	/**
	 * @return the rateKey
	 */
	public Integer getRateKey() {
		return rateKey;
	}

	/**
	 * @param rateKey the rateKey to set
	 */
	public void setRateKey(Integer rateKey) {
		this.rateKey = rateKey;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the rateCategoryCode
	 */
	public String getRateCategoryCode() {
		return rateCategoryCode;
	}

	/**
	 * @param rateCategoryCode the rateCategoryCode to set
	 */
	public void setRateCategoryCode(String rateCategoryCode) {
		this.rateCategoryCode = rateCategoryCode;
	}

	/**
	 * @return the rateCategoryName
	 */
	public String getRateCategoryName() {
		return rateCategoryName;
	}

	/**
	 * @param rateCategoryName the rateCategoryName to set
	 */
	public void setRateCategoryName(String rateCategoryName) {
		this.rateCategoryName = rateCategoryName;
	}

	/**
	 * @return the rateCode
	 */
	public String getRateCode() {
		return rateCode;
	}

	/**
	 * @param rateCode the rateCode to set
	 */
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	/**
	 * @return the rateName
	 */
	public String getRateName() {
		return rateName;
	}

	/**
	 * @param rateName the rateName to set
	 */
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	/**
	 * @return the operType
	 */
	public String getOperType() {
		return operType;
	}

	/**
	 * @param operType the operType to set
	 */
	public void setOperType(String operType) {
		this.operType = operType;
	}

	/**
	 * @return the cstmrType
	 */
	public String getCstmrType() {
		return cstmrType;
	}

	/**
	 * @param cstmrType the cstmrType to set
	 */
	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}

	/**
	 * @return the cstmrName
	 */
	public String getCstmrName() {
		return cstmrName;
	}

	/**
	 * @param cstmrName the cstmrName to set
	 */
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}

	/**
	 * @return the cstmrNativeRrn
	 */
	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}

	/**
	 * @param cstmrNativeRrn the cstmrNativeRrn to set
	 */
	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}

	/**
	 * @return the cstmrForeignerNation
	 */
	public String getCstmrForeignerNation() {
		return cstmrForeignerNation;
	}

	/**
	 * @param cstmrForeignerNation the cstmrForeignerNation to set
	 */
	public void setCstmrForeignerNation(String cstmrForeignerNation) {
		this.cstmrForeignerNation = cstmrForeignerNation;
	}

	/**
	 * @return the cstmrForeignerRrn
	 */
	public String getCstmrForeignerRrn() {
		return cstmrForeignerRrn;
	}

	/**
	 * @param cstmrForeignerRrn the cstmrForeignerRrn to set
	 */
	public void setCstmrForeignerRrn(String cstmrForeignerRrn) {
		this.cstmrForeignerRrn = cstmrForeignerRrn;
	}

	/**
	 * @return the cstmrForeignerPn
	 */
	public String getCstmrForeignerPn() {
		return cstmrForeignerPn;
	}

	/**
	 * @param cstmrForeignerPn the cstmrForeignerPn to set
	 */
	public void setCstmrForeignerPn(String cstmrForeignerPn) {
		this.cstmrForeignerPn = cstmrForeignerPn;
	}

	/**
	 * @return the cstmrForeignerSdate
	 */
	public String getCstmrForeignerSdate() {
		return cstmrForeignerSdate;
	}

	/**
	 * @param cstmrForeignerSdate the cstmrForeignerSdate to set
	 */
	public void setCstmrForeignerSdate(String cstmrForeignerSdate) {
		this.cstmrForeignerSdate = cstmrForeignerSdate;
	}

	/**
	 * @return the cstmrForeignerEdate
	 */
	public String getCstmrForeignerEdate() {
		return cstmrForeignerEdate;
	}

	/**
	 * @param cstmrForeignerEdate the cstmrForeignerEdate to set
	 */
	public void setCstmrForeignerEdate(String cstmrForeignerEdate) {
		this.cstmrForeignerEdate = cstmrForeignerEdate;
	}

	/**
	 * @return the cstmrPrivateCname
	 */
	public String getCstmrPrivateCname() {
		return cstmrPrivateCname;
	}

	/**
	 * @param cstmrPrivateCname the cstmrPrivateCname to set
	 */
	public void setCstmrPrivateCname(String cstmrPrivateCname) {
		this.cstmrPrivateCname = cstmrPrivateCname;
	}

	/**
	 * @return the cstmrPrivateNumber
	 */
	public String getCstmrPrivateNumber() {
		return cstmrPrivateNumber;
	}

	/**
	 * @param cstmrPrivateNumber the cstmrPrivateNumber to set
	 */
	public void setCstmrPrivateNumber(String cstmrPrivateNumber) {
		this.cstmrPrivateNumber = cstmrPrivateNumber;
	}

	/**
	 * @return the cstmrJuridicalCname
	 */
	public String getCstmrJuridicalCname() {
		return cstmrJuridicalCname;
	}

	/**
	 * @param cstmrJuridicalCname the cstmrJuridicalCname to set
	 */
	public void setCstmrJuridicalCname(String cstmrJuridicalCname) {
		this.cstmrJuridicalCname = cstmrJuridicalCname;
	}

	/**
	 * @return the cstmrJuridicalRrn
	 */
	public String getCstmrJuridicalRrn() {
		return cstmrJuridicalRrn;
	}

	/**
	 * @param cstmrJuridicalRrn the cstmrJuridicalRrn to set
	 */
	public void setCstmrJuridicalRrn(String cstmrJuridicalRrn) {
		this.cstmrJuridicalRrn = cstmrJuridicalRrn;
	}

	/**
	 * @return the cstmrJuridicalNumber
	 */
	public String getCstmrJuridicalNumber() {
		return cstmrJuridicalNumber;
	}

	/**
	 * @param cstmrJuridicalNumber the cstmrJuridicalNumber to set
	 */
	public void setCstmrJuridicalNumber(String cstmrJuridicalNumber) {
		this.cstmrJuridicalNumber = cstmrJuridicalNumber;
	}

	/**
	 * @return the cstmrTelFn
	 */
	public String getCstmrTelFn() {
		return cstmrTelFn;
	}

	/**
	 * @param cstmrTelFn the cstmrTelFn to set
	 */
	public void setCstmrTelFn(String cstmrTelFn) {
		this.cstmrTelFn = cstmrTelFn;
	}

	/**
	 * @return the cstmrTelMn
	 */
	public String getCstmrTelMn() {
		return cstmrTelMn;
	}

	/**
	 * @param cstmrTelMn the cstmrTelMn to set
	 */
	public void setCstmrTelMn(String cstmrTelMn) {
		this.cstmrTelMn = cstmrTelMn;
	}

	/**
	 * @return the cstmrTelRn
	 */
	public String getCstmrTelRn() {
		return cstmrTelRn;
	}

	/**
	 * @param cstmrTelRn the cstmrTelRn to set
	 */
	public void setCstmrTelRn(String cstmrTelRn) {
		this.cstmrTelRn = cstmrTelRn;
	}

	/**
	 * @return the cstmrMobileFn
	 */
	public String getCstmrMobileFn() {
		return cstmrMobileFn;
	}

	/**
	 * @param cstmrMobileFn the cstmrMobileFn to set
	 */
	public void setCstmrMobileFn(String cstmrMobileFn) {
		this.cstmrMobileFn = cstmrMobileFn;
	}

	/**
	 * @return the cstmrMobileMn
	 */
	public String getCstmrMobileMn() {
		return cstmrMobileMn;
	}

	/**
	 * @param cstmrMobileMn the cstmrMobileMn to set
	 */
	public void setCstmrMobileMn(String cstmrMobileMn) {
		this.cstmrMobileMn = cstmrMobileMn;
	}

	/**
	 * @return the cstmrMobileRn
	 */
	public String getCstmrMobileRn() {
		return cstmrMobileRn;
	}

	/**
	 * @param cstmrMobileRn the cstmrMobileRn to set
	 */
	public void setCstmrMobileRn(String cstmrMobileRn) {
		this.cstmrMobileRn = cstmrMobileRn;
	}

	/**
	 * @return the cstmrPost
	 */
	public String getCstmrPost() {
		return cstmrPost;
	}

	/**
	 * @param cstmrPost the cstmrPost to set
	 */
	public void setCstmrPost(String cstmrPost) {
		this.cstmrPost = cstmrPost;
	}

	/**
	 * @return the cstmrAddr
	 */
	public String getCstmrAddr() {
		return cstmrAddr;
	}

	/**
	 * @param cstmrAddr the cstmrAddr to set
	 */
	public void setCstmrAddr(String cstmrAddr) {
		this.cstmrAddr = cstmrAddr;
	}

	/**
	 * @return the cstmrAddrDtl
	 */
	public String getCstmrAddrDtl() {
		return cstmrAddrDtl;
	}

	/**
	 * @param cstmrAddrDtl the cstmrAddrDtl to set
	 */
	public void setCstmrAddrDtl(String cstmrAddrDtl) {
		this.cstmrAddrDtl = cstmrAddrDtl;
	}

	/**
	 * @return the cstmrBillSendCode
	 */
	public String getCstmrBillSendCode() {
		return cstmrBillSendCode;
	}

	/**
	 * @param cstmrBillSendCode the cstmrBillSendCode to set
	 */
	public void setCstmrBillSendCode(String cstmrBillSendCode) {
		this.cstmrBillSendCode = cstmrBillSendCode;
	}

	/**
	 * @return the cstmrMail
	 */
	public String getCstmrMail() {
		return cstmrMail;
	}

	/**
	 * @param cstmrMail the cstmrMail to set
	 */
	public void setCstmrMail(String cstmrMail) {
		this.cstmrMail = cstmrMail;
	}

	/**
	 * @return the cstmrMailReceiveFlag
	 */
	public String getCstmrMailReceiveFlag() {
		return cstmrMailReceiveFlag;
	}

	/**
	 * @param cstmrMailReceiveFlag the cstmrMailReceiveFlag to set
	 */
	public void setCstmrMailReceiveFlag(String cstmrMailReceiveFlag) {
		this.cstmrMailReceiveFlag = cstmrMailReceiveFlag;
	}

	/**
	 * @return the reqWantNumber
	 */
	public String getReqWantNumber() {
		return reqWantNumber;
	}

	/**
	 * @param reqWantNumber the reqWantNumber to set
	 */
	public void setReqWantNumber(String reqWantNumber) {
		this.reqWantNumber = reqWantNumber;
	}

	/**
	 * @return the reqPayType
	 */
	public String getReqPayType() {
		return reqPayType;
	}

	/**
	 * @param reqPayType the reqPayType to set
	 */
	public void setReqPayType(String reqPayType) {
		this.reqPayType = reqPayType;
	}

	/**
	 * @return the reqBank
	 */
	public String getReqBank() {
		return reqBank;
	}

	/**
	 * @param reqBank the reqBank to set
	 */
	public void setReqBank(String reqBank) {
		this.reqBank = reqBank;
	}

	/**
	 * @return the reqAccountName
	 */
	public String getReqAccountName() {
		return reqAccountName;
	}

	/**
	 * @param reqAccountName the reqAccountName to set
	 */
	public void setReqAccountName(String reqAccountName) {
		this.reqAccountName = reqAccountName;
	}

	/**
	 * @return the reqAccountRrn
	 */
	public String getReqAccountRrn() {
		return reqAccountRrn;
	}

	/**
	 * @param reqAccountRrn the reqAccountRrn to set
	 */
	public void setReqAccountRrn(String reqAccountRrn) {
		this.reqAccountRrn = reqAccountRrn;
	}

	/**
	 * @return the reqAccountRelation
	 */
	public String getReqAccountRelation() {
		return reqAccountRelation;
	}

	/**
	 * @param reqAccountRelation the reqAccountRelation to set
	 */
	public void setReqAccountRelation(String reqAccountRelation) {
		this.reqAccountRelation = reqAccountRelation;
	}

	/**
	 * @return the reqAccountNumber
	 */
	public String getReqAccountNumber() {
		return reqAccountNumber;
	}

	/**
	 * @param reqAccountNumber the reqAccountNumber to set
	 */
	public void setReqAccountNumber(String reqAccountNumber) {
		this.reqAccountNumber = reqAccountNumber;
	}

	/**
	 * @return the reqCardName
	 */
	public String getReqCardName() {
		return reqCardName;
	}

	/**
	 * @param reqCardName the reqCardName to set
	 */
	public void setReqCardName(String reqCardName) {
		this.reqCardName = reqCardName;
	}

	/**
	 * @return the reqCardRrn
	 */
	public String getReqCardRrn() {
		return reqCardRrn;
	}

	/**
	 * @param reqCardRrn the reqCardRrn to set
	 */
	public void setReqCardRrn(String reqCardRrn) {
		this.reqCardRrn = reqCardRrn;
	}

	/**
	 * @return the reqCardCompany
	 */
	public String getReqCardCompany() {
		return reqCardCompany;
	}

	/**
	 * @param reqCardCompany the reqCardCompany to set
	 */
	public void setReqCardCompany(String reqCardCompany) {
		this.reqCardCompany = reqCardCompany;
	}

	/**
	 * @return the reqCardNo
	 */
	public String getReqCardNo() {
		return reqCardNo;
	}

	/**
	 * @param reqCardNo the reqCardNo to set
	 */
	public void setReqCardNo(String reqCardNo) {
		this.reqCardNo = reqCardNo;
	}

	/**
	 * @return the reqCardYy
	 */
	public String getReqCardYy() {
		return reqCardYy;
	}

	/**
	 * @param reqCardYy the reqCardYy to set
	 */
	public void setReqCardYy(String reqCardYy) {
		this.reqCardYy = reqCardYy;
	}

	/**
	 * @return the reqCardMm
	 */
	public String getReqCardMm() {
		return reqCardMm;
	}

	/**
	 * @param reqCardMm the reqCardMm to set
	 */
	public void setReqCardMm(String reqCardMm) {
		this.reqCardMm = reqCardMm;
	}

	/**
	 * @return the reqAcType
	 */
	public String getReqAcType() {
		return reqAcType;
	}

	/**
	 * @param reqAcType the reqAcType to set
	 */
	public void setReqAcType(String reqAcType) {
		this.reqAcType = reqAcType;
	}

	/**
	 * @return the reqAc01Balance
	 */
	public String getReqAc01Balance() {
		return reqAc01Balance;
	}

	/**
	 * @param reqAc01Balance the reqAc01Balance to set
	 */
	public void setReqAc01Balance(String reqAc01Balance) {
		this.reqAc01Balance = reqAc01Balance;
	}

	/**
	 * @return the reqAc01Amount
	 */
	public String getReqAc01Amount() {
		return reqAc01Amount;
	}

	/**
	 * @param reqAc01Amount the reqAc01Amount to set
	 */
	public void setReqAc01Amount(String reqAc01Amount) {
		this.reqAc01Amount = reqAc01Amount;
	}

	/**
	 * @return the reqAc02Day
	 */
	public String getReqAc02Day() {
		return reqAc02Day;
	}

	/**
	 * @param reqAc02Day the reqAc02Day to set
	 */
	public void setReqAc02Day(String reqAc02Day) {
		this.reqAc02Day = reqAc02Day;
	}

	/**
	 * @return the reqAc02Amount
	 */
	public String getReqAc02Amount() {
		return reqAc02Amount;
	}

	/**
	 * @param reqAc02Amount the reqAc02Amount to set
	 */
	public void setReqAc02Amount(String reqAc02Amount) {
		this.reqAc02Amount = reqAc02Amount;
	}

	/**
	 * @return the reqGuideFlag
	 */
	public String getReqGuideFlag() {
		return reqGuideFlag;
	}

	/**
	 * @param reqGuideFlag the reqGuideFlag to set
	 */
	public void setReqGuideFlag(String reqGuideFlag) {
		this.reqGuideFlag = reqGuideFlag;
	}

	/**
	 * @return the reqGuideFn
	 */
	public String getReqGuideFn() {
		return reqGuideFn;
	}

	/**
	 * @param reqGuideFn the reqGuideFn to set
	 */
	public void setReqGuideFn(String reqGuideFn) {
		this.reqGuideFn = reqGuideFn;
	}

	/**
	 * @return the reqGuideMn
	 */
	public String getReqGuideMn() {
		return reqGuideMn;
	}

	/**
	 * @param reqGuideMn the reqGuideMn to set
	 */
	public void setReqGuideMn(String reqGuideMn) {
		this.reqGuideMn = reqGuideMn;
	}

	/**
	 * @return the reqGuideRn
	 */
	public String getReqGuideRn() {
		return reqGuideRn;
	}

	/**
	 * @param reqGuideRn the reqGuideRn to set
	 */
	public void setReqGuideRn(String reqGuideRn) {
		this.reqGuideRn = reqGuideRn;
	}

	/**
	 * @return the reqWireType
	 */
	public String getReqWireType() {
		return reqWireType;
	}

	/**
	 * @param reqWireType the reqWireType to set
	 */
	public void setReqWireType(String reqWireType) {
		this.reqWireType = reqWireType;
	}

	/**
	 * @return the reqBuyType
	 */
	public String getReqBuyType() {
		return reqBuyType;
	}

	/**
	 * @param reqBuyType the reqBuyType to set
	 */
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}

	/**
	 * @return the reqModelName
	 */
	public String getReqModelName() {
		return reqModelName;
	}

	/**
	 * @param reqModelName the reqModelName to set
	 */
	public void setReqModelName(String reqModelName) {
		this.reqModelName = reqModelName;
	}

	/**
	 * @return the reqAddition
	 */
	public String getReqAddition() {
		return reqAddition;
	}

	/**
	 * @param reqAddition the reqAddition to set
	 */
	public void setReqAddition(String reqAddition) {
		this.reqAddition = reqAddition;
	}

	/**
	 * @return the moveCompany
	 */
	public String getMoveCompany() {
		return moveCompany;
	}

	/**
	 * @param moveCompany the moveCompany to set
	 */
	public void setMoveCompany(String moveCompany) {
		this.moveCompany = moveCompany;
	}

	/**
	 * @return the moveMobileFn
	 */
	public String getMoveMobileFn() {
		return moveMobileFn;
	}

	/**
	 * @param moveMobileFn the moveMobileFn to set
	 */
	public void setMoveMobileFn(String moveMobileFn) {
		this.moveMobileFn = moveMobileFn;
	}

	/**
	 * @return the moveMobileMn
	 */
	public String getMoveMobileMn() {
		return moveMobileMn;
	}

	/**
	 * @param moveMobileMn the moveMobileMn to set
	 */
	public void setMoveMobileMn(String moveMobileMn) {
		this.moveMobileMn = moveMobileMn;
	}

	/**
	 * @return the moveMobileRn
	 */
	public String getMoveMobileRn() {
		return moveMobileRn;
	}

	/**
	 * @param moveMobileRn the moveMobileRn to set
	 */
	public void setMoveMobileRn(String moveMobileRn) {
		this.moveMobileRn = moveMobileRn;
	}

	/**
	 * @return the moveAuthType
	 */
	public String getMoveAuthType() {
		return moveAuthType;
	}

	/**
	 * @param moveAuthType the moveAuthType to set
	 */
	public void setMoveAuthType(String moveAuthType) {
		this.moveAuthType = moveAuthType;
	}

	/**
	 * @return the moveAuthNumber
	 */
	public String getMoveAuthNumber() {
		return moveAuthNumber;
	}

	/**
	 * @param moveAuthNumber the moveAuthNumber to set
	 */
	public void setMoveAuthNumber(String moveAuthNumber) {
		this.moveAuthNumber = moveAuthNumber;
	}

	/**
	 * @return the moveThismonthPayType
	 */
	public String getMoveThismonthPayType() {
		return moveThismonthPayType;
	}

	/**
	 * @param moveThismonthPayType the moveThismonthPayType to set
	 */
	public void setMoveThismonthPayType(String moveThismonthPayType) {
		this.moveThismonthPayType = moveThismonthPayType;
	}

	/**
	 * @return the moveAllotmentStat
	 */
	public String getMoveAllotmentStat() {
		return moveAllotmentStat;
	}

	/**
	 * @param moveAllotmentStat the moveAllotmentStat to set
	 */
	public void setMoveAllotmentStat(String moveAllotmentStat) {
		this.moveAllotmentStat = moveAllotmentStat;
	}

	/**
	 * @return the moveRefundAgreeFlag
	 */
	public String getMoveRefundAgreeFlag() {
		return moveRefundAgreeFlag;
	}

	/**
	 * @param moveRefundAgreeFlag the moveRefundAgreeFlag to set
	 */
	public void setMoveRefundAgreeFlag(String moveRefundAgreeFlag) {
		this.moveRefundAgreeFlag = moveRefundAgreeFlag;
	}

	/**
	 * @return the minorAgentName
	 */
	public String getMinorAgentName() {
		return minorAgentName;
	}

	/**
	 * @param minorAgentName the minorAgentName to set
	 */
	public void setMinorAgentName(String minorAgentName) {
		this.minorAgentName = minorAgentName;
	}

	/**
	 * @return the minorAgentRrn
	 */
	public String getMinorAgentRrn() {
		return minorAgentRrn;
	}

	/**
	 * @param minorAgentRrn the minorAgentRrn to set
	 */
	public void setMinorAgentRrn(String minorAgentRrn) {
		this.minorAgentRrn = minorAgentRrn;
	}

	/**
	 * @return the minorAgentTelFn
	 */
	public String getMinorAgentTelFn() {
		return minorAgentTelFn;
	}

	/**
	 * @param minorAgentTelFn the minorAgentTelFn to set
	 */
	public void setMinorAgentTelFn(String minorAgentTelFn) {
		this.minorAgentTelFn = minorAgentTelFn;
	}

	/**
	 * @return the minorAgentTelMn
	 */
	public String getMinorAgentTelMn() {
		return minorAgentTelMn;
	}

	/**
	 * @param minorAgentTelMn the minorAgentTelMn to set
	 */
	public void setMinorAgentTelMn(String minorAgentTelMn) {
		this.minorAgentTelMn = minorAgentTelMn;
	}

	/**
	 * @return the minorAgentTelRn
	 */
	public String getMinorAgentTelRn() {
		return minorAgentTelRn;
	}

	/**
	 * @param minorAgentTelRn the minorAgentTelRn to set
	 */
	public void setMinorAgentTelRn(String minorAgentTelRn) {
		this.minorAgentTelRn = minorAgentTelRn;
	}

	/**
	 * @return the jrdclAgentName
	 */
	public String getJrdclAgentName() {
		return jrdclAgentName;
	}

	/**
	 * @param jrdclAgentName the jrdclAgentName to set
	 */
	public void setJrdclAgentName(String jrdclAgentName) {
		this.jrdclAgentName = jrdclAgentName;
	}

	/**
	 * @return the jrdclAgentRrn
	 */
	public String getJrdclAgentRrn() {
		return jrdclAgentRrn;
	}

	/**
	 * @param jrdclAgentRrn the jrdclAgentRrn to set
	 */
	public void setJrdclAgentRrn(String jrdclAgentRrn) {
		this.jrdclAgentRrn = jrdclAgentRrn;
	}

	/**
	 * @return the jrdclAgentTelFn
	 */
	public String getJrdclAgentTelFn() {
		return jrdclAgentTelFn;
	}

	/**
	 * @param jrdclAgentTelFn the jrdclAgentTelFn to set
	 */
	public void setJrdclAgentTelFn(String jrdclAgentTelFn) {
		this.jrdclAgentTelFn = jrdclAgentTelFn;
	}

	/**
	 * @return the jrdclAgentTelMn
	 */
	public String getJrdclAgentTelMn() {
		return jrdclAgentTelMn;
	}

	/**
	 * @param jrdclAgentTelMn the jrdclAgentTelMn to set
	 */
	public void setJrdclAgentTelMn(String jrdclAgentTelMn) {
		this.jrdclAgentTelMn = jrdclAgentTelMn;
	}

	/**
	 * @return the jrdclAgentTelRn
	 */
	public String getJrdclAgentTelRn() {
		return jrdclAgentTelRn;
	}

	/**
	 * @param jrdclAgentTelRn the jrdclAgentTelRn to set
	 */
	public void setJrdclAgentTelRn(String jrdclAgentTelRn) {
		this.jrdclAgentTelRn = jrdclAgentTelRn;
	}

	/**
	 * @return the dlvryName
	 */
	public String getDlvryName() {
		return dlvryName;
	}

	/**
	 * @param dlvryName the dlvryName to set
	 */
	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}

	/**
	 * @return the dlvryTelFn
	 */
	public String getDlvryTelFn() {
		return dlvryTelFn;
	}

	/**
	 * @param dlvryTelFn the dlvryTelFn to set
	 */
	public void setDlvryTelFn(String dlvryTelFn) {
		this.dlvryTelFn = dlvryTelFn;
	}

	/**
	 * @return the dlvryTelMn
	 */
	public String getDlvryTelMn() {
		return dlvryTelMn;
	}

	/**
	 * @param dlvryTelMn the dlvryTelMn to set
	 */
	public void setDlvryTelMn(String dlvryTelMn) {
		this.dlvryTelMn = dlvryTelMn;
	}

	/**
	 * @return the dlvryTelRn
	 */
	public String getDlvryTelRn() {
		return dlvryTelRn;
	}

	/**
	 * @param dlvryTelRn the dlvryTelRn to set
	 */
	public void setDlvryTelRn(String dlvryTelRn) {
		this.dlvryTelRn = dlvryTelRn;
	}

	/**
	 * @return the dlvryMobileFn
	 */
	public String getDlvryMobileFn() {
		return dlvryMobileFn;
	}

	/**
	 * @param dlvryMobileFn the dlvryMobileFn to set
	 */
	public void setDlvryMobileFn(String dlvryMobileFn) {
		this.dlvryMobileFn = dlvryMobileFn;
	}

	/**
	 * @return the dlvryMobileMn
	 */
	public String getDlvryMobileMn() {
		return dlvryMobileMn;
	}

	/**
	 * @param dlvryMobileMn the dlvryMobileMn to set
	 */
	public void setDlvryMobileMn(String dlvryMobileMn) {
		this.dlvryMobileMn = dlvryMobileMn;
	}

	/**
	 * @return the dlvryMobileRn
	 */
	public String getDlvryMobileRn() {
		return dlvryMobileRn;
	}

	/**
	 * @param dlvryMobileRn the dlvryMobileRn to set
	 */
	public void setDlvryMobileRn(String dlvryMobileRn) {
		this.dlvryMobileRn = dlvryMobileRn;
	}

	/**
	 * @return the dlvryPost
	 */
	public String getDlvryPost() {
		return dlvryPost;
	}

	/**
	 * @param dlvryPost the dlvryPost to set
	 */
	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
	}

	/**
	 * @return the dlvryAddr
	 */
	public String getDlvryAddr() {
		return dlvryAddr;
	}

	/**
	 * @param dlvryAddr the dlvryAddr to set
	 */
	public void setDlvryAddr(String dlvryAddr) {
		this.dlvryAddr = dlvryAddr;
	}

	/**
	 * @return the dlvryAddrDtl
	 */
	public String getDlvryAddrDtl() {
		return dlvryAddrDtl;
	}

	/**
	 * @param dlvryAddrDtl the dlvryAddrDtl to set
	 */
	public void setDlvryAddrDtl(String dlvryAddrDtl) {
		this.dlvryAddrDtl = dlvryAddrDtl;
	}

	/**
	 * @return the dlvryMemo
	 */
	public String getDlvryMemo() {
		return dlvryMemo;
	}

	/**
	 * @param dlvryMemo the dlvryMemo to set
	 */
	public void setDlvryMemo(String dlvryMemo) {
		this.dlvryMemo = dlvryMemo;
	}

	/**
	 * @return the clausePriCollectFlag
	 */
	public String getClausePriCollectFlag() {
		return clausePriCollectFlag;
	}

	/**
	 * @param clausePriCollectFlag the clausePriCollectFlag to set
	 */
	public void setClausePriCollectFlag(String clausePriCollectFlag) {
		this.clausePriCollectFlag = clausePriCollectFlag;
	}

	/**
	 * @return the clausePriOfferFlag
	 */
	public String getClausePriOfferFlag() {
		return clausePriOfferFlag;
	}

	/**
	 * @param clausePriOfferFlag the clausePriOfferFlag to set
	 */
	public void setClausePriOfferFlag(String clausePriOfferFlag) {
		this.clausePriOfferFlag = clausePriOfferFlag;
	}

	/**
	 * @return the clauseEssCollectFlag
	 */
	public String getClauseEssCollectFlag() {
		return clauseEssCollectFlag;
	}

	/**
	 * @param clauseEssCollectFlag the clauseEssCollectFlag to set
	 */
	public void setClauseEssCollectFlag(String clauseEssCollectFlag) {
		this.clauseEssCollectFlag = clauseEssCollectFlag;
	}

	/**
	 * @return the clausePriTrustFlag
	 */
	public String getClausePriTrustFlag() {
		return clausePriTrustFlag;
	}

	/**
	 * @param clausePriTrustFlag the clausePriTrustFlag to set
	 */
	public void setClausePriTrustFlag(String clausePriTrustFlag) {
		this.clausePriTrustFlag = clausePriTrustFlag;
	}

	/**
	 * @return the clausePriAdFlag
	 */
	public String getClausePriAdFlag() {
		return clausePriAdFlag;
	}

	/**
	 * @param clausePriAdFlag the clausePriAdFlag to set
	 */
	public void setClausePriAdFlag(String clausePriAdFlag) {
		this.clausePriAdFlag = clausePriAdFlag;
	}

	/**
	 * @return the clauseConfidenceFlag
	 */
	public String getClauseConfidenceFlag() {
		return clauseConfidenceFlag;
	}

	/**
	 * @param clauseConfidenceFlag the clauseConfidenceFlag to set
	 */
	public void setClauseConfidenceFlag(String clauseConfidenceFlag) {
		this.clauseConfidenceFlag = clauseConfidenceFlag;
	}

	/**
	 * @return the onlineAuthType
	 */
	public String getOnlineAuthType() {
		return onlineAuthType;
	}

	/**
	 * @param onlineAuthType the onlineAuthType to set
	 */
	public void setOnlineAuthType(String onlineAuthType) {
		this.onlineAuthType = onlineAuthType;
	}

	/**
	 * @return the onlineAuthInfo
	 */
	public String getOnlineAuthInfo() {
		return onlineAuthInfo;
	}

	/**
	 * @param onlineAuthInfo the onlineAuthInfo to set
	 */
	public void setOnlineAuthInfo(String onlineAuthInfo) {
		this.onlineAuthInfo = onlineAuthInfo;
	}

	/**
	 * @return the resCode
	 */
	public String getResCode() {
		return resCode;
	}

	/**
	 * @param resCode the resCode to set
	 */
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	/**
	 * @return the resMsg
	 */
	public String getResMsg() {
		return resMsg;
	}

	/**
	 * @param resMsg the resMsg to set
	 */
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	/**
	 * @return the resNo
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * @param resNo the resNo to set
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * @return the requestStateCode
	 */
	public String getRequestStateCode() {
		return requestStateCode;
	}

	/**
	 * @param requestStateCode the requestStateCode to set
	 */
	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}

	/**
	 * @return the openNo
	 */
	public String getOpenNo() {
		return openNo;
	}

	/**
	 * @param openNo the openNo to set
	 */
	public void setOpenNo(String openNo) {
		this.openNo = openNo;
	}

	/**
	 * @return the rip
	 */
	public String getRip() {
		return rip;
	}

	/**
	 * @param rip the rip to set
	 */
	public void setRip(String rip) {
		this.rip = rip;
	}

	/**
	 * @return the rdate
	 */
	public String getRdate() {
		return rdate;
	}

	/**
	 * @param rdate the rdate to set
	 */
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	/**
	 * @return the minorAgentRelation
	 */
	public String getMinorAgentRelation() {
		return minorAgentRelation;
	}


	/**
	 * @param minorAgentRelation the minorAgentRelation to set
	 */
	public void setMinorAgentRelation(String minorAgentRelation) {
		this.minorAgentRelation = minorAgentRelation;
	}

	/**
	 * @return the pstate
	 */
	public String getPstate() {
		return pstate;
	}


	/**
	 * @param pstate the pstate to set
	 */
	public void setPstate(String pstate) {
		this.pstate = pstate;
	}


	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}


	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}


	/**
	 * @return the managerCode
	 */
	public String getManagerCode() {
		return managerCode;
	}


	/**
	 * @param managerCode the managerCode to set
	 */
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}


	/**
	 * @return the file01
	 */
	public String getFile01() {
		return file01;
	}


	/**
	 * @param file01 the file01 to set
	 */
	public void setFile01(String file01) {
		this.file01 = file01;
	}


	/**
	 * @return the file01Mask
	 */
	public String getFile01Mask() {
		return file01Mask;
	}


	/**
	 * @param file01Mask the file01Mask to set
	 */
	public void setFile01Mask(String file01Mask) {
		this.file01Mask = file01Mask;
	}
	
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getEntrustResRrn() {
		return entrustResRrn;
	}

	public void setEntrustResRrn(String entrustResRrn) {
		this.entrustResRrn = entrustResRrn;
	}

	public String getNameChangeRrn() {
		return nameChangeRrn;
	}

	public void setNameChangeRrn(String nameChangeRrn) {
		this.nameChangeRrn = nameChangeRrn;
	}

	public String getOthersPaymentRrn() {
		return othersPaymentRrn;
	}

	public void setOthersPaymentRrn(String othersPaymentRrn) {
		this.othersPaymentRrn = othersPaymentRrn;
	}

	/**
	 * @return the cstmrTel
	 */
	public String getCstmrTel() {
		return cstmrTel;
	}

	/**
	 * @param cstmrTel the cstmrTel to set
	 */
	public void setCstmrTel(String cstmrTel) {
		this.cstmrTel = cstmrTel;
	}

	/**
	 * @return the cstmrMobile
	 */
	public String getCstmrMobile() {
		return cstmrMobile;
	}

	/**
	 * @param cstmrMobile the cstmrMobile to set
	 */
	public void setCstmrMobile(String cstmrMobile) {
		this.cstmrMobile = cstmrMobile;
	}

	/**
	 * @return the reqGuide
	 */
	public String getReqGuide() {
		return reqGuide;
	}

	/**
	 * @param reqGuide the reqGuide to set
	 */
	public void setReqGuide(String reqGuide) {
		this.reqGuide = reqGuide;
	}

	/**
	 * @return the moveMobile
	 */
	public String getMoveMobile() {
		return moveMobile;
	}

	/**
	 * @param moveMobile the moveMobile to set
	 */
	public void setMoveMobile(String moveMobile) {
		this.moveMobile = moveMobile;
	}

	/**
	 * @return the minorAgentTel
	 */
	public String getMinorAgentTel() {
		return minorAgentTel;
	}

	/**
	 * @param minorAgentTel the minorAgentTel to set
	 */
	public void setMinorAgentTel(String minorAgentTel) {
		this.minorAgentTel = minorAgentTel;
	}

	/**
	 * @return the jrdclAgentTel
	 */
	public String getJrdclAgentTel() {
		return jrdclAgentTel;
	}

	/**
	 * @param jrdclAgentTel the jrdclAgentTel to set
	 */
	public void setJrdclAgentTel(String jrdclAgentTel) {
		this.jrdclAgentTel = jrdclAgentTel;
	}

	/**
	 * @return the dlvryTel
	 */
	public String getDlvryTel() {
		return dlvryTel;
	}

	/**
	 * @param dlvryTel the dlvryTel to set
	 */
	public void setDlvryTel(String dlvryTel) {
		this.dlvryTel = dlvryTel;
	}

	/**
	 * @return the dlvryMobile
	 */
	public String getDlvryMobile() {
		return dlvryMobile;
	}

	/**
	 * @param dlvryMobile the dlvryMobile to set
	 */
	public void setDlvryMobile(String dlvryMobile) {
		this.dlvryMobile = dlvryMobile;
	}
	
	
}