package com.ktis.msp.rcp.formVrfyMgmt.vo;
import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class FormVrfyMgmtVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String contractNum;		
	private String shopCd;
	private String clausePriAdFlag;
	private String insrFormFlag;
	private String insrCfmtFlag;
	private String regstId;
	private String regstDttm;
	private String rvisnId;
	private String rvisnNm;
	private String rvisnDttm;
	private String requestKey;        /**/
	private String prodTypeNm;        /*상품구분    */
	private String cstmrName;         /*고객명      */
	private String ssn;               /*주민번호    */
	private String birth;             /*생년월일    */
	private String age;               /*나이(만)    */
	private String subscriberNo;      /*휴대폰번호  */
	private String fstRateNm;         /*최초요금제  */
	private String lstRateNm;         /*현재요금제  */
	private String sprtTpNm;          /*할인유형    */
	private String salePlcyNm;        /*판매정책명  */
	private String enggMnthCnt;       /*약정개월수  */
	private String instMnthCnt;       /*할부개월수  */
	private String fstModelNm;        /*단말모델    */
	private String fstModelSrlNum;    /*단말일련번호*/
	private String subStatusNm;       /*상태        */
	private String canRsn;            /*해지사유    */
	private String onOffTypeNm;       /*모집경로    */
	private String openMarketReferer; /*유입경로    */
	private String recommendFlagNm;   /*추천인구분  */
	private String recommendInfo;     /*추천인정보  */
	private String recYn;             /*녹취여부    */
	private String agentNm;           /*대리점      */
	private String shopNm;            /*판매점명    */
	private String shopUsrId;         /*판매자ID    */
	private String shopUsrNm;         /*판매자명    */
	private String dvcChgCnt;         /*기변횟수    */
	
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
	private String operTypeNm;
	
	private String cstmrType;       /*고객구분코드*/
	private String cstmrTypeNm;     /*고객구분    */
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
	
	private String scanId;				/*스캔ID            */
	private String resNo;				/*예약번호          */
	
	private String ctnVoc;
	private String custNmVoc;
	
	private String reqPayType;			/*요금납부방법      */
	private String requestStateCode;	/*개통진행상태      */
	
	private String dvcChgYn;			/* 기변여부 */
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
	private String trnsYn;
	private String promotionNm;
	private String scanFileCnt;
	
	private Integer modelInstallment;	/* 할부원금 */
	private long monthlyInstallment;	/* 월할부금 */
	private long monthlyFee;			/* 월할부수수료 */
	private long monthylTotal;			/* 월납부액 */
	private long totalFee;				/* 총할부수수료 */
	
	private String searchCd;
	private String searchVal;
	private String agntId;

	private String vrfyCd;
	private String vrfyUsrId;
	private String vrfyStatCd;
	private String vrfyStatNm;

	private String fstUsrId;
	private String fstUsrNm;
	private String fstVrfyDttm;
	private String fstRsltCd;
	private String fstRsltNm;
	private String fstRemark;
	private String fstActCd;
	private String fstActRemark;
	
	private String sndUsrId;
	private String sndUsrNm;
	private String sndVrfyDttm;
	private String sndRsltCd;
	private String sndRsltNm;
	private String sndRemark;
	private String sndActCd;
	private String sndActRemark;
	
	private String clausePriAdFlagNm;
	private String insrFormFlagNm;
	private String insrCfmtFlagNm;
		
	private String reqClausePriAdFlagNm;
	private String insrYn2;
	private String insrNm2;
	
	private String fstModifyYn;
	private String sndModifyYn;
	private String adminYn;
	
	public String getShopUsrId() {
		return shopUsrId;
	}
	public void setShopUsrId(String shopUsrId) {
		this.shopUsrId = shopUsrId;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getClausePriAdFlag() {
		return clausePriAdFlag;
	}
	public void setClausePriAdFlag(String clausePriAdFlag) {
		this.clausePriAdFlag = clausePriAdFlag;
	}
	public String getInsrFormFlag() {
		return insrFormFlag;
	}
	public void setInsrFormFlag(String insrFormFlag) {
		this.insrFormFlag = insrFormFlag;
	}
	public String getInsrCfmtFlag() {
		return insrCfmtFlag;
	}
	public void setInsrCfmtFlag(String insrCfmtFlag) {
		this.insrCfmtFlag = insrCfmtFlag;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnNm() {
		return rvisnNm;
	}
	public void setRvisnNm(String rvisnNm) {
		this.rvisnNm = rvisnNm;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
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
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
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
	public String getFstRateNm() {
		return fstRateNm;
	}
	public void setFstRateNm(String fstRateNm) {
		this.fstRateNm = fstRateNm;
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
	public String getFstModelNm() {
		return fstModelNm;
	}
	public void setFstModelNm(String fstModelNm) {
		this.fstModelNm = fstModelNm;
	}
	public String getFstModelSrlNum() {
		return fstModelSrlNum;
	}
	public void setFstModelSrlNum(String fstModelSrlNum) {
		this.fstModelSrlNum = fstModelSrlNum;
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
	public String getOperTypeNm() {
		return operTypeNm;
	}
	public void setOperTypeNm(String operTypeNm) {
		this.operTypeNm = operTypeNm;
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
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
	public String getDvcChgYn() {
		return dvcChgYn;
	}
	public void setDvcChgYn(String dvcChgYn) {
		this.dvcChgYn = dvcChgYn;
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
	public String getScanFileCnt() {
		return scanFileCnt;
	}
	public void setScanFileCnt(String scanFileCnt) {
		this.scanFileCnt = scanFileCnt;
	}
	public Integer getModelInstallment() {
		return modelInstallment;
	}
	public void setModelInstallment(Integer modelInstallment) {
		this.modelInstallment = modelInstallment;
	}
	public long getMonthlyInstallment() {
		return monthlyInstallment;
	}
	public void setMonthlyInstallment(long monthlyInstallment) {
		this.monthlyInstallment = monthlyInstallment;
	}
	public long getMonthlyFee() {
		return monthlyFee;
	}
	public void setMonthlyFee(long monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	public long getMonthylTotal() {
		return monthylTotal;
	}
	public void setMonthylTotal(long monthylTotal) {
		this.monthylTotal = monthylTotal;
	}
	public long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(long totalFee) {
		this.totalFee = totalFee;
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
	public String getAgntId() {
		return agntId;
	}
	public void setAgntId(String agntId) {
		this.agntId = agntId;
	}
	public String getVrfyCd() {
		return vrfyCd;
	}
	public void setVrfyCd(String vrfyCd) {
		this.vrfyCd = vrfyCd;
	}
	public String getVrfyUsrId() {
		return vrfyUsrId;
	}
	public void setVrfyUsrId(String vrfyUsrId) {
		this.vrfyUsrId = vrfyUsrId;
	}
	public String getVrfyStatCd() {
		return vrfyStatCd;
	}
	public void setVrfyStatCd(String vrfyStatCd) {
		this.vrfyStatCd = vrfyStatCd;
	}
	public String getVrfyStatNm() {
		return vrfyStatNm;
	}
	public void setVrfyStatNm(String vrfyStatNm) {
		this.vrfyStatNm = vrfyStatNm;
	}
	public String getFstUsrId() {
		return fstUsrId;
	}
	public void setFstUsrId(String fstUsrId) {
		this.fstUsrId = fstUsrId;
	}
	public String getFstUsrNm() {
		return fstUsrNm;
	}
	public void setFstUsrNm(String fstUsrNm) {
		this.fstUsrNm = fstUsrNm;
	}
	public String getFstVrfyDttm() {
		return fstVrfyDttm;
	}
	public void setFstVrfyDttm(String fstVrfyDttm) {
		this.fstVrfyDttm = fstVrfyDttm;
	}
	public String getFstRsltCd() {
		return fstRsltCd;
	}
	public void setFstRsltCd(String fstRsltCd) {
		this.fstRsltCd = fstRsltCd;
	}
	public String getFstRsltNm() {
		return fstRsltNm;
	}
	public void setFstRsltNm(String fstRsltNm) {
		this.fstRsltNm = fstRsltNm;
	}
	public String getFstRemark() {
		return fstRemark;
	}
	public void setFstRemark(String fstRemark) {
		this.fstRemark = fstRemark;
	}
	public String getFstActCd() {
		return fstActCd;
	}
	public void setFstActCd(String fstActCd) {
		this.fstActCd = fstActCd;
	}
	public String getFstActRemark() {
		return fstActRemark;
	}
	public void setFstActRemark(String fstActRemark) {
		this.fstActRemark = fstActRemark;
	}
	public String getSndUsrId() {
		return sndUsrId;
	}
	public void setSndUsrId(String sndUsrId) {
		this.sndUsrId = sndUsrId;
	}
	public String getSndUsrNm() {
		return sndUsrNm;
	}
	public void setSndUsrNm(String sndUsrNm) {
		this.sndUsrNm = sndUsrNm;
	}
	public String getSndVrfyDttm() {
		return sndVrfyDttm;
	}
	public void setSndVrfyDttm(String sndVrfyDttm) {
		this.sndVrfyDttm = sndVrfyDttm;
	}
	public String getSndRsltCd() {
		return sndRsltCd;
	}
	public void setSndRsltCd(String sndRsltCd) {
		this.sndRsltCd = sndRsltCd;
	}
	public String getSndRsltNm() {
		return sndRsltNm;
	}
	public void setSndRsltNm(String sndRsltNm) {
		this.sndRsltNm = sndRsltNm;
	}
	public String getSndRemark() {
		return sndRemark;
	}
	public void setSndRemark(String sndRemark) {
		this.sndRemark = sndRemark;
	}
	public String getSndActCd() {
		return sndActCd;
	}
	public void setSndActCd(String sndActCd) {
		this.sndActCd = sndActCd;
	}
	public String getSndActRemark() {
		return sndActRemark;
	}
	public void setSndActRemark(String sndActRemark) {
		this.sndActRemark = sndActRemark;
	}
	public String getClausePriAdFlagNm() {
		return clausePriAdFlagNm;
	}
	public void setClausePriAdFlagNm(String clausePriAdFlagNm) {
		this.clausePriAdFlagNm = clausePriAdFlagNm;
	}
	public String getInsrFormFlagNm() {
		return insrFormFlagNm;
	}
	public void setInsrFormFlagNm(String insrFormFlagNm) {
		this.insrFormFlagNm = insrFormFlagNm;
	}
	public String getInsrCfmtFlagNm() {
		return insrCfmtFlagNm;
	}
	public void setInsrCfmtFlagNm(String insrCfmtFlagNm) {
		this.insrCfmtFlagNm = insrCfmtFlagNm;
	}
	public String getReqClausePriAdFlagNm() {
		return reqClausePriAdFlagNm;
	}
	public void setReqClausePriAdFlagNm(String reqClausePriAdFlagNm) {
		this.reqClausePriAdFlagNm = reqClausePriAdFlagNm;
	}
	public String getInsrYn2() {
		return insrYn2;
	}
	public void setInsrYn2(String insrYn2) {
		this.insrYn2 = insrYn2;
	}
	public String getInsrNm2() {
		return insrNm2;
	}
	public void setInsrNm2(String insrNm2) {
		this.insrNm2 = insrNm2;
	}
		public String getFstModifyYn() {
		return fstModifyYn;
	}
	public void setFstModifyYn(String fstModifyYn) {
		this.fstModifyYn = fstModifyYn;
	}
	public String getSndModifyYn() {
		return sndModifyYn;
	}
	public void setSndModifyYn(String sndModifyYn) {
		this.sndModifyYn = sndModifyYn;
	}
	public String getAdminYn() {
		return adminYn;
	}
	public void setAdminYn(String adminYn) {
		this.adminYn = adminYn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
				
}
