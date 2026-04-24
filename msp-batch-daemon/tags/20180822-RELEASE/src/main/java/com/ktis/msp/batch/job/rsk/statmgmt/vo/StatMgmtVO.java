package com.ktis.msp.batch.job.rsk.statmgmt.vo;


public class StatMgmtVO {
	
	// 조회조건
	private String trgtYm;
	private String strtYm;
	private String endYm;
	private String pppo;
	private String contractNum;
	private String unpdYn;
	
	private String userId;
	
	// 엑셀다운로드 로그
	private String dwnldRsn    ;	/*다운로드 사유*/
	private String exclDnldId  ;	
	private String ipAddr      ;	/*ip정보*/
	private String menuId      ;	/*메뉴ID*/
	
	// 2018-01-17, 프로시져 output 추가
	private String reqId;
	private String errCode;
	private String errMsg;
	
	//2018-03-29
	private String stDt;		//시작일자
	private String edDt;		//종료일자
	private String fileNm;	//파일명
	private String oprtSctnCd;
	private String oprtStrtDt;
	private String oprtEndDt;
	private String oprtStatCd;
	
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
	public String getTrgtYm() {
		return trgtYm;
	}
	public void setTrgtYm(String trgtYm) {
		this.trgtYm = trgtYm;
	}
	public String getStrtYm() {
		return strtYm;
	}
	public void setStrtYm(String strtYm) {
		this.strtYm = strtYm;
	}
	public String getEndYm() {
		return endYm;
	}
	public void setEndYm(String endYm) {
		this.endYm = endYm;
	}
	public String getPppo() {
		return pppo;
	}
	public void setPppo(String pppo) {
		this.pppo = pppo;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getUnpdYn() {
		return unpdYn;
	}
	public void setUnpdYn(String unpdYn) {
		this.unpdYn = unpdYn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getStDt() {
		return stDt;
	}
	public void setStDt(String stDt) {
		this.stDt = stDt;
	}
	public String getEdDt() {
		return edDt;
	}
	public void setEdDt(String edDt) {
		this.edDt = edDt;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getOprtSctnCd() {
		return oprtSctnCd;
	}
	public void setOprtSctnCd(String oprtSctnCd) {
		this.oprtSctnCd = oprtSctnCd;
	}
	public String getOprtStrtDt() {
		return oprtStrtDt;
	}
	public void setOprtStrtDt(String oprtStrtDt) {
		this.oprtStrtDt = oprtStrtDt;
	}
	public String getOprtEndDt() {
		return oprtEndDt;
	}
	public void setOprtEndDt(String oprtEndDt) {
		this.oprtEndDt = oprtEndDt;
	}
	public String getOprtStatCd() {
		return oprtStatCd;
	}
	public void setOprtStatCd(String oprtStatCd) {
		this.oprtStatCd = oprtStatCd;
	}
		
}
