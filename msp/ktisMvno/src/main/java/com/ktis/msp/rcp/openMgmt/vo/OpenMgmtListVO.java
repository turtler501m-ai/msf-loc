package com.ktis.msp.rcp.openMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : OpenMgmtListVO
 * @since 2016. 11. 16
 * @version 1.0
 */
// 개통관리 조회(가입자관리) 그리드
public class OpenMgmtListVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String requestKey;        /**/
	private String prodTypeNm;        /*상품구분    */
	private String contractNum;       /*계약번호    */
	private String cstmrName;         /*고객명      */
	private String ssn;               /*주민번호    */
	private String birth;             /*생년월일    */
	private String age;               /*나이(만)    */
	private String subscriberNo;      /*휴대폰번호  */
	private String lstRateNm;         /*요금제      */
	private String sprtTpNm;          /*할인유형    */
	private String salePlcyNm;        /*판매정책명  */
	private String enggMnthCnt;       /*약정개월수  */
	private String instMnthCnt;       /*할부개월수  */
	private String lstModelNm;        /*단말모델    */
	private String lstModelSrlNum;    /*단말일련번호*/
	private String subStatusNm;       /*상태        */
	private String canRsn;            /*해지사유    */
	private String onOffTypeNm;       /*모집경로    */
	private String openMarketReferer; /*유입경로    */
	private String recommendFlagNm;   /*추천인구분  */
	private String recommendInfo;     /*추천인정보  */
	private String recYn;             /*녹취여부    */
	private String agentNm;           /*대리점      */
	private String shopNm;            /*판매점명    */
	private String shopUsrId;         /*판매자Id    */
	private String shopUsrNm;         /*판매자명    */
	private String dvcChgCnt;         /*기변횟수    */
	private String ban;				  /*청구번호	*/
	
	// 검색조건
	private String lstComActvDateF; /*개통일      */
	private String lstComActvDateT; /*            */
	private String subStatus;       /*가입자상태  */
	private String cntpntShopId;    /*개통대리점  */
	private String cntpntShopNm;    /*            */
	private String pAgentCode;      /*판매점      */
	private String pAgentName;      /*            */
	private String reqBuyTypeS;     /*구매유형    */
	private String onOffType;       /*모집경로코드*/
	private String minorYn;         /*미성년자여부*/
	private String appBlckAgrmYn;   /*APP 설치동의*/
	private String appInstYn;       /*APP 설치확인*/
	private String searchName;      /*검색내용    */
	private String searchGbn;       /*검색조건    */
	private String sprtTp;          /*할인유형코드*/
	private String operType;        /*업무구분코드*/
	private String prodType;        /*상품구분코드*/
	
	private String cstmrType;       /*고객구분코드*/
	private String cstmrTypeNm;     /*고객구분    */
	private String usimOrgCd;		/*유심접점  */
	private int total;
	

	// 페이징
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;	
	

	// 복호화
	private String ssn1;				/**/
	private String cstmrForeignerRrn;	/*외국인주민번호    */
	private String minorAgentRrn;		/*법정대리인주민번호*/
	private String reqCardNo;			/*카드번호          */
	private String reqCardRrn;			/*카드주주민번호    */	
	private String reqAccountNumber;	/*계좌번호          */	
	private String reqAccountRrn;		/*예금주주민번호    */
	
	
	// 스캔
	private String scanId;				/*스캔ID            */
	private String resNo;				/*예약번호          */
	
	private String ctnVoc;
	private String custNmVoc;
	
	private String reqPayType;			/*요금납부방법      */
	private String requestStateCode;	/*개통진행상태      */
	
	// 기변정보추가
	private String dvcChgDt;			/* 기변일자 */
	private String dvcModelId;			/* 기변모델ID */
	private String dvcModelNm;			/* 기변모델명 */
	private String dvcIntmSrlNo;		/* 기변단말일련번호 */
	private String dvcAgntNm;			/* 기변대리점명 */
	private String dvcOperTypeNm;		/* 기변유형명 */
	private Integer dvcHndstAmnt;		/* 기변단말출고가 */
	private Integer dvcInstOrginAmnt;	/* 기변단말할부원금 */
	private Integer dvcInstMnthCnt;		/* 기변단말할부개월수 */
	private Integer dvcEnggMnthCnt;		/* 기변약정개월수 */
	private String dvcSalePlcyNm;		/* 기변판매정책명 */
	
	// 2017-06-16 이관여부 추가
	private String trnsYn;
	
	// 2017-08-07 프로모션명 추가
	private String promotionNm;
	
	// 2018-11-19 ATM 개통체크를 위한 변수 추가
	private String scanFileCnt;			/*스캔파일갯수      */
	
	// 2020-12-14 국가,VISA_CD 추가
	private String cstmrForeignerNation; /* 국가 */
	private String visaCdNm;             /* VISA CODE 명 */
	
	//2022-08-10
	private String esimYn;   /* esim 여부 */

	private String disPrmtNm; /* 평생할인 프로모션명*/
	
	//2025-07-01 검색조건 추가
	private String pBirthDay; /* 생년월일 */
	private String pBirthDayVal; /* 생년월일 값*/

	private String imei; /* 단말 IMEI */
	public String getShopUsrId() {
		return shopUsrId;
	}
	public void setShopUsrId(String shopUsrId) {
		this.shopUsrId = shopUsrId;
	}
	public String getReqPayType() {
		return reqPayType;
	}
	public void setReqPayType(String reqPayType) {
		this.reqPayType = reqPayType;
	}
	public String getRequestStateCode() {
		return requestStateCode;
	}
	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getSsn1() {
		return ssn1;
	}
	public void setSsn1(String ssn1) {
		this.ssn1 = ssn1;
	}
	public String getCstmrForeignerRrn() {
		return cstmrForeignerRrn;
	}
	public void setCstmrForeignerRrn(String cstmrForeignerRrn) {
		this.cstmrForeignerRrn = cstmrForeignerRrn;
	}
	public String getMinorAgentRrn() {
		return minorAgentRrn;
	}
	public void setMinorAgentRrn(String minorAgentRrn) {
		this.minorAgentRrn = minorAgentRrn;
	}
	public String getReqCardNo() {
		return reqCardNo;
	}
	public void setReqCardNo(String reqCardNo) {
		this.reqCardNo = reqCardNo;
	}
	public String getReqCardRrn() {
		return reqCardRrn;
	}
	public void setReqCardRrn(String reqCardRrn) {
		this.reqCardRrn = reqCardRrn;
	}
	public String getReqAccountNumber() {
		return reqAccountNumber;
	}
	public void setReqAccountNumber(String reqAccountNumber) {
		this.reqAccountNumber = reqAccountNumber;
	}
	public String getReqAccountRrn() {
		return reqAccountRrn;
	}
	public void setReqAccountRrn(String reqAccountRrn) {
		this.reqAccountRrn = reqAccountRrn;
	}
	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getLstComActvDateF() {
		return lstComActvDateF;
	}
	public void setLstComActvDateF(String lstComActvDateF) {
		this.lstComActvDateF = lstComActvDateF;
	}
	public String getLstComActvDateT() {
		return lstComActvDateT;
	}
	public void setLstComActvDateT(String lstComActvDateT) {
		this.lstComActvDateT = lstComActvDateT;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	public String getCntpntShopNm() {
		return cntpntShopNm;
	}
	public void setCntpntShopNm(String cntpntShopNm) {
		this.cntpntShopNm = cntpntShopNm;
	}
	public String getpAgentCode() {
		return pAgentCode;
	}
	public void setpAgentCode(String pAgentCode) {
		this.pAgentCode = pAgentCode;
	}
	public String getpAgentName() {
		return pAgentName;
	}
	public void setpAgentName(String pAgentName) {
		this.pAgentName = pAgentName;
	}
	public String getReqBuyTypeS() {
		return reqBuyTypeS;
	}
	public void setReqBuyTypeS(String reqBuyTypeS) {
		this.reqBuyTypeS = reqBuyTypeS;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getMinorYn() {
		return minorYn;
	}
	public void setMinorYn(String minorYn) {
		this.minorYn = minorYn;
	}
	public String getAppBlckAgrmYn() {
		return appBlckAgrmYn;
	}
	public void setAppBlckAgrmYn(String appBlckAgrmYn) {
		this.appBlckAgrmYn = appBlckAgrmYn;
	}
	public String getAppInstYn() {
		return appInstYn;
	}
	public void setAppInstYn(String appInstYn) {
		this.appInstYn = appInstYn;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getSprtTp() {
		return sprtTp;
	}
	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getCstmrType() {
		return cstmrType;
	}
	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}
	public String getCstmrTypeNm() {
		return cstmrTypeNm;
	}
	public void setCstmrTypeNm(String cstmrTypeNm) {
		this.cstmrTypeNm = cstmrTypeNm;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getProdTypeNm() {
		return prodTypeNm;
	}
	public void setProdTypeNm(String prodTypeNm) {
		this.prodTypeNm = prodTypeNm;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getLstRateNm() {
		return lstRateNm;
	}
	public void setLstRateNm(String lstRateNm) {
		this.lstRateNm = lstRateNm;
	}
	public String getSprtTpNm() {
		return sprtTpNm;
	}
	public void setSprtTpNm(String sprtTpNm) {
		this.sprtTpNm = sprtTpNm;
	}
	public String getSalePlcyNm() {
		return salePlcyNm;
	}
	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}
	public String getEnggMnthCnt() {
		return enggMnthCnt;
	}
	public void setEnggMnthCnt(String enggMnthCnt) {
		this.enggMnthCnt = enggMnthCnt;
	}
	public String getInstMnthCnt() {
		return instMnthCnt;
	}
	public void setInstMnthCnt(String instMnthCnt) {
		this.instMnthCnt = instMnthCnt;
	}
	public String getLstModelNm() {
		return lstModelNm;
	}
	public void setLstModelNm(String lstModelNm) {
		this.lstModelNm = lstModelNm;
	}
	public String getLstModelSrlNum() {
		return lstModelSrlNum;
	}
	public void setLstModelSrlNum(String lstModelSrlNum) {
		this.lstModelSrlNum = lstModelSrlNum;
	}
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	public String getCanRsn() {
		return canRsn;
	}
	public void setCanRsn(String canRsn) {
		this.canRsn = canRsn;
	}
	public String getOnOffTypeNm() {
		return onOffTypeNm;
	}
	public void setOnOffTypeNm(String onOffTypeNm) {
		this.onOffTypeNm = onOffTypeNm;
	}
	public String getOpenMarketReferer() {
		return openMarketReferer;
	}
	public void setOpenMarketReferer(String openMarketReferer) {
		this.openMarketReferer = openMarketReferer;
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
	public String getRecYn() {
		return recYn;
	}
	public void setRecYn(String recYn) {
		this.recYn = recYn;
	}
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
	public String getShopNm() {
		return shopNm;
	}
	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}
	public String getShopUsrNm() {
		return shopUsrNm;
	}
	public void setShopUsrNm(String shopUsrNm) {
		this.shopUsrNm = shopUsrNm;
	}
	public String getDvcChgCnt() {
		return dvcChgCnt;
	}
	public void setDvcChgCnt(String dvcChgCnt) {
		this.dvcChgCnt = dvcChgCnt;
	}
	public String getCtnVoc() {
		return ctnVoc;
	}
	public void setCtnVoc(String ctnVoc) {
		this.ctnVoc = ctnVoc;
	}
	public String getCustNmVoc() {
		return custNmVoc;
	}
	public void setCustNmVoc(String custNmVoc) {
		this.custNmVoc = custNmVoc;
	}
	public String getDvcChgDt() {
		return dvcChgDt;
	}
	public void setDvcChgDt(String dvcChgDt) {
		this.dvcChgDt = dvcChgDt;
	}
	public String getDvcModelId() {
		return dvcModelId;
	}
	public void setDvcModelId(String dvcModelId) {
		this.dvcModelId = dvcModelId;
	}
	public String getDvcModelNm() {
		return dvcModelNm;
	}
	public void setDvcModelNm(String dvcModelNm) {
		this.dvcModelNm = dvcModelNm;
	}
	public String getDvcIntmSrlNo() {
		return dvcIntmSrlNo;
	}
	public void setDvcIntmSrlNo(String dvcIntmSrlNo) {
		this.dvcIntmSrlNo = dvcIntmSrlNo;
	}
	public String getDvcAgntNm() {
		return dvcAgntNm;
	}
	public void setDvcAgntNm(String dvcAgntNm) {
		this.dvcAgntNm = dvcAgntNm;
	}
	public String getDvcOperTypeNm() {
		return dvcOperTypeNm;
	}
	public void setDvcOperTypeNm(String dvcOperTypeNm) {
		this.dvcOperTypeNm = dvcOperTypeNm;
	}
	public Integer getDvcHndstAmnt() {
		return dvcHndstAmnt;
	}
	public void setDvcHndstAmnt(Integer dvcHndstAmnt) {
		this.dvcHndstAmnt = dvcHndstAmnt;
	}
	public Integer getDvcInstOrginAmnt() {
		return dvcInstOrginAmnt;
	}
	public void setDvcInstOrginAmnt(Integer dvcInstOrginAmnt) {
		this.dvcInstOrginAmnt = dvcInstOrginAmnt;
	}
	public Integer getDvcInstMnthCnt() {
		return dvcInstMnthCnt;
	}
	public void setDvcInstMnthCnt(Integer dvcInstMnthCnt) {
		this.dvcInstMnthCnt = dvcInstMnthCnt;
	}
	public Integer getDvcEnggMnthCnt() {
		return dvcEnggMnthCnt;
	}
	public void setDvcEnggMnthCnt(Integer dvcEnggMnthCnt) {
		this.dvcEnggMnthCnt = dvcEnggMnthCnt;
	}
	public String getDvcSalePlcyNm() {
		return dvcSalePlcyNm;
	}
	public void setDvcSalePlcyNm(String dvcSalePlcyNm) {
		this.dvcSalePlcyNm = dvcSalePlcyNm;
	}
	public String getTrnsYn() {
		return trnsYn;
	}
	public void setTrnsYn(String trnsYn) {
		this.trnsYn = trnsYn;
	}
	public String getPromotionNm() {
		return promotionNm;
	}
	public void setPromotionNm(String promotionNm) {
		this.promotionNm = promotionNm;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getScanFileCnt() {
		return scanFileCnt;
	}
	public void setScanFileCnt(String scanFileCnt) {
		this.scanFileCnt = scanFileCnt;
	}
	public String getCstmrForeignerNation() {
		return cstmrForeignerNation;
	}
	public void setCstmrForeignerNation(String cstmrForeignerNation) {
		this.cstmrForeignerNation = cstmrForeignerNation;
	}
	public String getVisaCdNm() {
		return visaCdNm;
	}
	public void setVisaCdNm(String visaCdNm) {
		this.visaCdNm = visaCdNm;
	}
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
	}
	public String getEsimYn() {
		return esimYn;
	}
	public void setEsimYn(String esimYn) {
		this.esimYn = esimYn;
	}
	public String getUsimOrgCd() {
		return usimOrgCd;
	}
	public void setUsimOrgCd(String usimOrgCd) {
		this.usimOrgCd = usimOrgCd;
	}

	public String getDisPrmtNm() {
		return disPrmtNm;
	}

	public void setDisPrmtNm(String disPrmtNm) {
		this.disPrmtNm = disPrmtNm;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
