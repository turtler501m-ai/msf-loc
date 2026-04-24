package com.ktis.msp.batch.job.rcp.statsmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class StatsMgmtListVO extends BaseVo  {
	
	// 엑셀 다운로드
	private String contractNum;
	private String cstmrNm;
	private String simStrtDt;
	private String simEndDt;
	private String simStatNm;
	private String lstComActvDate;
	private String tmntDt;
	private String fstModelInfo;
	private String fstRateNm;
	private String openAgntNm;
	private String sexNm;
	private String dataType;
	private String reqBuyType;
	private String subStatusNm;
	private String simEnggCnt;
	private String subscriberNo;
	private String presentNm;
	private String dlvryFullAddr;
	private int age;
	private String orderTypeNm;
	
	
	// 검색조건	
	private String srchStrtDt;
	private String srchEndDt;
	private String subStatus;
	private String phonCtn;
	private String searchName;
	private String searchGbn;
	private String onOffType;
	private String orderType;
	
	private String dlvryName;
	private String phonCtn1;
	private String phonCtn2;
	private String phonCtn3;
	
	private String dlvryPost;
	private String dlvryAddr;
	private String dlvryAddrDtl;
	private String agrmMemo;
	
	private String seq;
	
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


	public String getSubStatusNm() {
		return subStatusNm;
	}

	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}


	public String getTmntDt() {
		return tmntDt;
	}

	public void setTmntDt(String tmntDt) {
		this.tmntDt = tmntDt;
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

	public String getSrchStrtDt() {
		return srchStrtDt;
	}

	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getPhonCtn() {
		return phonCtn;
	}

	public void setPhonCtn(String phonCtn) {
		this.phonCtn = phonCtn;
	}

	public String getOnOffType() {
		return onOffType;
	}

	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDlvryName() {
		return dlvryName;
	}

	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}

	public String getPhonCtn1() {
		return phonCtn1;
	}

	public void setPhonCtn1(String phonCtn1) {
		this.phonCtn1 = phonCtn1;
	}

	public String getPhonCtn2() {
		return phonCtn2;
	}

	public void setPhonCtn2(String phonCtn2) {
		this.phonCtn2 = phonCtn2;
	}

	public String getPhonCtn3() {
		return phonCtn3;
	}

	public void setPhonCtn3(String phonCtn3) {
		this.phonCtn3 = phonCtn3;
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

	public String getAgrmMemo() {
		return agrmMemo;
	}

	public void setAgrmMemo(String agrmMemo) {
		this.agrmMemo = agrmMemo;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public String getOrderTypeNm() {
		return orderTypeNm;
	}

	public void setOrderTypeNm(String orderTypeNm) {
		this.orderTypeNm = orderTypeNm;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getCstmrNm() {
		return cstmrNm;
	}

	public void setCstmrNm(String cstmrNm) {
		this.cstmrNm = cstmrNm;
	}

	public String getSimStrtDt() {
		return simStrtDt;
	}

	public void setSimStrtDt(String simStrtDt) {
		this.simStrtDt = simStrtDt;
	}

	public String getLstComActvDate() {
		return lstComActvDate;
	}

	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}

	public String getFstModelInfo() {
		return fstModelInfo;
	}

	public void setFstModelInfo(String fstModelInfo) {
		this.fstModelInfo = fstModelInfo;
	}

	public String getOpenAgntNm() {
		return openAgntNm;
	}

	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}

	public String getSexNm() {
		return sexNm;
	}

	public void setSexNm(String sexNm) {
		this.sexNm = sexNm;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getReqBuyType() {
		return reqBuyType;
	}

	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}

	public String getSimEnggCnt() {
		return simEnggCnt;
	}

	public void setSimEnggCnt(String simEnggCnt) {
		this.simEnggCnt = simEnggCnt;
	}

	public String getPresentNm() {
		return presentNm;
	}

	public void setPresentNm(String presentNm) {
		this.presentNm = presentNm;
	}

	public String getDlvryFullAddr() {
		return dlvryFullAddr;
	}

	public void setDlvryFullAddr(String dlvryFullAddr) {
		this.dlvryFullAddr = dlvryFullAddr;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
