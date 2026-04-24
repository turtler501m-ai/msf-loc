package com.ktis.msp.batch.job.rcp.cancustmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class CanCustVO extends BaseVo {
	
	private String canDt        ;  /* 해지일자     */
	private String contractNum  ;  /* 계약번호     */
	private String reqBuyTypeNm ;  /* 구매유형     */
	private String fstRateNm    ;  /* 최초요금제명 */
	private String salePlcyNm   ;  /* 판매정책     */
	private String fstModelNm   ;  /* 최초개통단말 */
	private String subStatusNm  ;  /* 상태         */
	private String canRsn       ;  /* 해지사유     */
	private String onOffTypeNm  ;  /* 모집경로     */
	private String agentNm      ;  /* 대리점명     */
	private String openDt       ;  /* 개통일자     */
	private String sprtTpNm     ;  /* 할인유형     */
	private String userId		;
	
	// 검색조건
	private String searchStartDt ;
	private String searchEndDt   ;
	private String searchGbn     ;
	private String searchName    ;
    private String cntpntShopId  ;
	

	// 엑셀다운로드 로그
	private String dwnldRsn    ;	/*다운로드 사유*/
	private String exclDnldId  ;	
	private String ipAddr      ;	/*ip정보*/
	private String menuId      ;	/*메뉴ID*/
	
	// 2019-01-31 해지후 이동 사업자정보 추가
    private String cmpnNm;			/* 해지후 이동 사업자정보 */
	
	private String svcCntrNo;
	private String rclDttm;
	private String rclRsn;
	private String rclCanDttm;
	private String rclCanRsn;
	private String canDttm;
	private String dataType;
	private String fstModel;
	private String fstRateCd;
	private String openAgntNm;
	private String lstComActvDate;
	private String operTypeNm;
	private String bfCommCmpnNm;
	private String npCommCmpnNm;
	private String shopNm;
	private String openMarketReferer;
	private String age;
	private String gender;
	private String modelName;
	private String lstRateCd;
	private String lstRateNm;
	private String usimOrgNm;
	private String fstUsimOrgNm;
	private String esimYn;
	private String fstEsimYn;
	
	
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
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
	public String getCanDt() {
		return canDt;
	}
	public void setCanDt(String canDt) {
		this.canDt = canDt;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getReqBuyTypeNm() {
		return reqBuyTypeNm;
	}
	public void setReqBuyTypeNm(String reqBuyTypeNm) {
		this.reqBuyTypeNm = reqBuyTypeNm;
	}
	public String getFstRateNm() {
		return fstRateNm;
	}
	public void setFstRateNm(String fstRateNm) {
		this.fstRateNm = fstRateNm;
	}
	public String getSalePlcyNm() {
		return salePlcyNm;
	}
	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}
	public String getFstModelNm() {
		return fstModelNm;
	}
	public void setFstModelNm(String fstModelNm) {
		this.fstModelNm = fstModelNm;
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
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getSprtTpNm() {
		return sprtTpNm;
	}
	public void setSprtTpNm(String sprtTpNm) {
		this.sprtTpNm = sprtTpNm;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getCmpnNm() {
		return cmpnNm;
	}
	public void setCmpnNm(String cmpnNm) {
		this.cmpnNm = cmpnNm;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getRclDttm() {
		return rclDttm;
	}
	public void setRclDttm(String rclDttm) {
		this.rclDttm = rclDttm;
	}
	public String getRclRsn() {
		return rclRsn;
	}
	public void setRclRsn(String rclRsn) {
		this.rclRsn = rclRsn;
	}
	public String getRclCanDttm() {
		return rclCanDttm;
	}
	public void setRclCanDttm(String rclCanDttm) {
		this.rclCanDttm = rclCanDttm;
	}
	public String getRclCanRsn() {
		return rclCanRsn;
	}
	public void setRclCanRsn(String rclCanRsn) {
		this.rclCanRsn = rclCanRsn;
	}
	public String getCanDttm() {
		return canDttm;
	}
	public void setCanDttm(String canDttm) {
		this.canDttm = canDttm;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getFstModel() {
		return fstModel;
	}
	public void setFstModel(String fstModel) {
		this.fstModel = fstModel;
	}
	public String getFstRateCd() {
		return fstRateCd;
	}
	public void setFstRateCd(String fstRateCd) {
		this.fstRateCd = fstRateCd;
	}
	public String getOpenAgntNm() {
		return openAgntNm;
	}
	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}
	public String getLstComActvDate() {
		return lstComActvDate;
	}
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}
	public String getOperTypeNm() {
		return operTypeNm;
	}
	public void setOperTypeNm(String operTypeNm) {
		this.operTypeNm = operTypeNm;
	}
	public String getBfCommCmpnNm() {
		return bfCommCmpnNm;
	}
	public void setBfCommCmpnNm(String bfCommCmpnNm) {
		this.bfCommCmpnNm = bfCommCmpnNm;
	}
	public String getNpCommCmpnNm() {
		return npCommCmpnNm;
	}
	public void setNpCommCmpnNm(String npCommCmpnNm) {
		this.npCommCmpnNm = npCommCmpnNm;
	}
	public String getShopNm() {
		return shopNm;
	}
	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}
	public String getOpenMarketReferer() {
		return openMarketReferer;
	}
	public void setOpenMarketReferer(String openMarketReferer) {
		this.openMarketReferer = openMarketReferer;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getLstRateCd() {
		return lstRateCd;
	}
	public void setLstRateCd(String lstRateCd) {
		this.lstRateCd = lstRateCd;
	}
	public String getLstRateNm() {
		return lstRateNm;
	}
	public void setLstRateNm(String lstRateNm) {
		this.lstRateNm = lstRateNm;
	}
	public String getUsimOrgNm() {
		return usimOrgNm;
	}
	public void setUsimOrgNm(String usimOrgNm) {
		this.usimOrgNm = usimOrgNm;
	}
	public String getFstUsimOrgNm() {
		return fstUsimOrgNm;
	}
	public void setFstUsimOrgNm(String fstUsimOrgNm) {
		this.fstUsimOrgNm = fstUsimOrgNm;
	}
	public String getEsimYn() {
		return esimYn;
	}
	public void setEsimYn(String esimYn) {
		this.esimYn = esimYn;
	}
	public String getFstEsimYn() {
		return fstEsimYn;
	}
	public void setFstEsimYn(String fstEsimYn) {
		this.fstEsimYn = fstEsimYn;
	}
		
}
