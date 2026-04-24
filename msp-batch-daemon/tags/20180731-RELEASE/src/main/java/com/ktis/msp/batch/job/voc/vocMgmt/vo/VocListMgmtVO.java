package com.ktis.msp.batch.job.voc.vocMgmt.vo;


public class VocListMgmtVO {
	
	private String regStartDt;
	private String regEndDt;
	private String comStartDt;
	private String comEndDt;
	private String reqOrgnId;
	private String reqrId;
	private String procOrgnId;
	private String procrId;
	private String cmplOrgnId;
	private String cmplrId;
	private String cnslMstCd;
	private String cnslMidCd;
	private String cnslDtlCd;
	private String cnslStat;
	private String procMm;
	private String pppo;
	private String prodType;
	private String userId;
	
	// 엑셀다운로드 로그
	private String dwnldRsn    ;	/*다운로드 사유*/
	private String exclDnldId  ;	
	private String ipAddr      ;	/*ip정보*/
	private String menuId      ;	/*메뉴ID*/
	
	// 2017-07-24, 잔여시간 추가
	private String remainMm;
	
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
	public String getRegStartDt() {
		return regStartDt;
	}
	public void setRegStartDt(String regStartDt) {
		this.regStartDt = regStartDt;
	}
	public String getRegEndDt() {
		return regEndDt;
	}
	public void setRegEndDt(String regEndDt) {
		this.regEndDt = regEndDt;
	}
	public String getComStartDt() {
		return comStartDt;
	}
	public void setComStartDt(String comStartDt) {
		this.comStartDt = comStartDt;
	}
	public String getComEndDt() {
		return comEndDt;
	}
	public void setComEndDt(String comEndDt) {
		this.comEndDt = comEndDt;
	}
	public String getReqOrgnId() {
		return reqOrgnId;
	}
	public void setReqOrgnId(String reqOrgnId) {
		this.reqOrgnId = reqOrgnId;
	}
	public String getReqrId() {
		return reqrId;
	}
	public void setReqrId(String reqrId) {
		this.reqrId = reqrId;
	}
	public String getProcOrgnId() {
		return procOrgnId;
	}
	public void setProcOrgnId(String procOrgnId) {
		this.procOrgnId = procOrgnId;
	}
	public String getProcrId() {
		return procrId;
	}
	public void setProcrId(String procrId) {
		this.procrId = procrId;
	}
	public String getCmplOrgnId() {
		return cmplOrgnId;
	}
	public void setCmplOrgnId(String cmplOrgnId) {
		this.cmplOrgnId = cmplOrgnId;
	}
	public String getCmplrId() {
		return cmplrId;
	}
	public void setCmplrId(String cmplrId) {
		this.cmplrId = cmplrId;
	}
	public String getCnslMstCd() {
		return cnslMstCd;
	}
	public void setCnslMstCd(String cnslMstCd) {
		this.cnslMstCd = cnslMstCd;
	}
	public String getCnslMidCd() {
		return cnslMidCd;
	}
	public void setCnslMidCd(String cnslMidCd) {
		this.cnslMidCd = cnslMidCd;
	}
	public String getCnslDtlCd() {
		return cnslDtlCd;
	}
	public void setCnslDtlCd(String cnslDtlCd) {
		this.cnslDtlCd = cnslDtlCd;
	}
	public String getCnslStat() {
		return cnslStat;
	}
	public void setCnslStat(String cnslStat) {
		this.cnslStat = cnslStat;
	}
	public String getProcMm() {
		return procMm;
	}
	public void setProcMm(String procMm) {
		this.procMm = procMm;
	}
	public String getPppo() {
		return pppo;
	}
	public void setPppo(String pppo) {
		this.pppo = pppo;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRemainMm() {
		return remainMm;
	}
	public void setRemainMm(String remainMm) {
		this.remainMm = remainMm;
	}
	
}
