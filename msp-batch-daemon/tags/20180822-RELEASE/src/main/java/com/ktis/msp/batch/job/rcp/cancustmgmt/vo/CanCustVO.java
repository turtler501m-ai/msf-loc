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
	
}
