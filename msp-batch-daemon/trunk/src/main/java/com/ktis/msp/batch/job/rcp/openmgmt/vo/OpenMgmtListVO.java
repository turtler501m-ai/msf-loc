package com.ktis.msp.batch.job.rcp.openmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class OpenMgmtListVO extends BaseVo  {
	
	// 엑셀 다운로드
	private String prodTypeNm;         /*상품구분        */
	private String contractNum;        /*계약번호        */
	private String resNo;              /*예약번호        */
	private String cstmrName;          /*고객명          */
	private String ssn;                /*주민번호        */
	private String birth;   		   /*생년월일        */
	private String gender;			   /*성별            */
	private String age;				   /*나이(만)        */
	private String reqBuyTypeNm;       /*구매유형        */
	private String operTypeNm;         /*업무구분        */
	private String moveCompany;        
	private String moveCompanyNm;      /*이동전 통신사   */
	private String subscriberNo;       /*휴대폰번호      */
	private String fstRateNm;          /*최초요금제      */
	private String lstRateNm;          /*현재요금제      */
	private String salePlcyNm;         /*판매정책        */
	private String enggMnthCnt;        /*약정개월수      */
	private String instMnthCnt;        /*할부개월수      */
	private String lstModelNm;         /*단말모델명      */
	private String lstModelSrlNum;     /*단말일련번호    */
	private String fstModelNm;         /*최초개통단말    */
	private String fstModelSrlNum;     /*최초단말일련번호*/
	private String subStatusNm;        /*상태            */
	private String canRsn;             /*해지사유        */
	private String onOffTypeNm;        /*모집경로        */
	private String openMarketReferer;  /*유입경로        */
	private String recommendFlagNm;    /*추천인구분      */
	private String recommendInfo;      /*추천인정보      */
	private String recYn;              /*녹취여부        */
	private String agentNm;            /*대리점          */
	private String shopNm;             /*판매점          */
	private String reqInDay;           /*신청일자        */
	private String openDt;             /*개통일자        */
	private String tmntDt;             /*해지일자        */
	private String minorAgentName;     /*법정대리인      */
	private String minorAgentRelation; /*관계            */
	private String minorAgentTel;      /*대리인연락처    */
	private String appNm;              /*유해차단APP명   */
	private String joinPayMthdNm;      /*가입비납부방법  */
	private String joinPrice;          /*가입비          */
	private String usimPayMthdNm;      /*USIM납부방법    */
	private String usimPrice;          /*USIM비          */
	private String openAgntCd;         /*대리점ID        */
	private String instOrginAmnt;      /*할부원금        */
	private String sprtTpNm;           /*할인유형        */
	private String grntInsrMngmNo;     /*보증보험관리번호*/
	private String grntInsrStatNm;     /*보증보험가입상태*/
	private String simId;              /*심플할인ID      */
	private String simEnggMnthCnt;     /*심플약정기간    */
	private String simStartDt;         /*심플약정시작일자*/
	private String simEndDt;           /*심플약정종료일자*/
	private String simStatNm;          /*심플약정상태    */
	private String rentalBaseAmt;      /*렌탈기본료      */
	private String rentalBaseDcAmt;    /*렌탈료할인      */
	private String rentalModelCpAmt;   /*렌탈단말배상금  */
	private String dvcChgCnt;		   /*기변횟수        */
	private String insrYn;			/*보험가입여부 20181012 추가 kwon*/
	private String usimorgnm;		   /*유심접점		 */
	private String fstusimorgnm; 	   /*최초유심접점	 */
	
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
		
	private String requestKey;		/**/
	private String scanId;			/*SCAN ID     */
	private String userId;
	

	// 복호화
	private String ssn1;				/**/
	private String cstmrForeignerRrn;	/*외국인주민번호    */
	private String minorAgentRrn;		/*법정대리인주민번호*/
	private String reqCardNo;			/*카드번호          */
	private String reqCardRrn;			/*카드주주민번호    */	
	private String reqAccountNumber;	/*계좌번호          */	
	private String reqAccountRrn;		/*예금주주민번호    */
	
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
	
	//캐치콜 
	private String catchcall;			/* 가입일자 */
	private String catchcallCan;		/* 해지일자 */
	
	// OTA
	private String otaDate;				/* OTA 등록일자 */

	// 엑셀다운로드 로그
	private String dwnldRsn;			/*다운로드 사유*/
	private String exclDnldId;	
	private String ipAddr;				/*ip정보*/
	private String menuId;				/*메뉴ID*/
	
	// 정지일자
	private String stopDt;				/*정지일자*/
	
	// 2018-12-10 KT해지사유 추가
    private String subStatusRsnNm;          /*KT해지사유 추가      */
    
    // 2019-01-31 해지후 이동 사업자정보 추가
    private String cmpnNm; 				/* 해지후 이동 사업자정보 */
    
    //2022-08-10 esim 여부 추가 
    private String esimYn;
    
    private String usimOrgCd; 			/*유심접점코드*/
    
    private String ktInterSvcNo;      /*kt 인터넷 id*/
    
    //2025-07-01 생년월일 추가
    private String pBirthDayVal;      /*생년월일*/
	
	
	public String getCatchcallCan() {
		return catchcallCan;
	}

	public void setCatchcallCan(String catchcallCan) {
		this.catchcallCan = catchcallCan;
	}

	public String getStopDt() {
		return stopDt;
	}

	public void setStopDt(String stopDt) {
		this.stopDt = stopDt;
	}

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

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getReqBuyTypeNm() {
		return reqBuyTypeNm;
	}

	public void setReqBuyTypeNm(String reqBuyTypeNm) {
		this.reqBuyTypeNm = reqBuyTypeNm;
	}

	public String getOperTypeNm() {
		return operTypeNm;
	}

	public void setOperTypeNm(String operTypeNm) {
		this.operTypeNm = operTypeNm;
	}

	public String getMoveCompany() {
		return moveCompany;
	}

	public void setMoveCompany(String moveCompany) {
		this.moveCompany = moveCompany;
	}

	public String getMoveCompanyNm() {
		return moveCompanyNm;
	}

	public void setMoveCompanyNm(String moveCompanyNm) {
		this.moveCompanyNm = moveCompanyNm;
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

	public String getReqInDay() {
		return reqInDay;
	}

	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}

	public String getOpenDt() {
		return openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	public String getTmntDt() {
		return tmntDt;
	}

	public void setTmntDt(String tmntDt) {
		this.tmntDt = tmntDt;
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

	public String getMinorAgentTel() {
		return minorAgentTel;
	}

	public void setMinorAgentTel(String minorAgentTel) {
		this.minorAgentTel = minorAgentTel;
	}

	public String getAppNm() {
		return appNm;
	}

	public void setAppNm(String appNm) {
		this.appNm = appNm;
	}

	public String getJoinPayMthdNm() {
		return joinPayMthdNm;
	}

	public void setJoinPayMthdNm(String joinPayMthdNm) {
		this.joinPayMthdNm = joinPayMthdNm;
	}

	public String getJoinPrice() {
		return joinPrice;
	}

	public void setJoinPrice(String joinPrice) {
		this.joinPrice = joinPrice;
	}

	public String getUsimPayMthdNm() {
		return usimPayMthdNm;
	}

	public void setUsimPayMthdNm(String usimPayMthdNm) {
		this.usimPayMthdNm = usimPayMthdNm;
	}

	public String getUsimPrice() {
		return usimPrice;
	}

	public void setUsimPrice(String usimPrice) {
		this.usimPrice = usimPrice;
	}

	public String getOpenAgntCd() {
		return openAgntCd;
	}

	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}

	public String getInstOrginAmnt() {
		return instOrginAmnt;
	}

	public void setInstOrginAmnt(String instOrginAmnt) {
		this.instOrginAmnt = instOrginAmnt;
	}

	public String getSprtTpNm() {
		return sprtTpNm;
	}

	public void setSprtTpNm(String sprtTpNm) {
		this.sprtTpNm = sprtTpNm;
	}

	public String getGrntInsrMngmNo() {
		return grntInsrMngmNo;
	}

	public void setGrntInsrMngmNo(String grntInsrMngmNo) {
		this.grntInsrMngmNo = grntInsrMngmNo;
	}

	public String getGrntInsrStatNm() {
		return grntInsrStatNm;
	}

	public void setGrntInsrStatNm(String grntInsrStatNm) {
		this.grntInsrStatNm = grntInsrStatNm;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public String getSimEnggMnthCnt() {
		return simEnggMnthCnt;
	}

	public void setSimEnggMnthCnt(String simEnggMnthCnt) {
		this.simEnggMnthCnt = simEnggMnthCnt;
	}

	public String getSimStartDt() {
		return simStartDt;
	}

	public void setSimStartDt(String simStartDt) {
		this.simStartDt = simStartDt;
	}

	public String getSimEndDt() {
		return simEndDt;
	}

	public void setSimEndDt(String simEndDt) {
		this.simEndDt = simEndDt;
	}

	public String getSimStatNm() {
		return simStatNm;
	}

	public void setSimStatNm(String simStatNm) {
		this.simStatNm = simStatNm;
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

	public String getScanId() {
		return scanId;
	}

	public void setScanId(String scanId) {
		this.scanId = scanId;
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

	public String getCatchcall() {
		return catchcall;
	}

	public void setCatchcall(String catchcall) {
		this.catchcall = catchcall;
	}

	public String getOtaDate() {
		return otaDate;
	}

	public void setOtaDate(String otaDate) {
		this.otaDate = otaDate;
	}

	public String getInsrYn() {
		return insrYn;
	}

	public void setInsrYn(String insrYn) {
		this.insrYn = insrYn;
	}
		
    public String getUsimorgnm() {
		return usimorgnm;
	}

	public void setUsimorgnm(String usimorgnm) {
		this.usimorgnm = usimorgnm;
	}

	public String getFstusimorgnm() {
		return fstusimorgnm;
	}

	public void setFstusimorgnm(String fstusimorgnm) {
		this.fstusimorgnm = fstusimorgnm;
	}

	public String getSubStatusRsnNm() {
        return subStatusRsnNm;
    }

    public void setSubStatusRsnNm(String subStatusRsnNm) {
        this.subStatusRsnNm = subStatusRsnNm;
    }

	public String getCmpnNm() {
		return cmpnNm;
	}

	public void setCmpnNm(String cmpnNm) {
		this.cmpnNm = cmpnNm;
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

	public String getKtInterSvcNo() {
		return ktInterSvcNo;
	}

	public void setKtInterSvcNo(String ktInterSvcNo) {
		this.ktInterSvcNo = ktInterSvcNo;
	}

	public String getpBirthDayVal() {
		return pBirthDayVal;
	}

	public void setpBirthDayVal(String pBirthDayVal) {
		this.pBirthDayVal = pBirthDayVal;
	}

		
}
