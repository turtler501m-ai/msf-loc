package com.ktis.msp.batch.job.org.orgmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class AcenRcpMgmtVO extends BaseVo {

  // 조회 파라미터
  private String requestKey;         // 신청키
  private String srchStrtDt;         // 조회시작일
  private String srchEndDt;          // 조회종료일
  private String pSuccYn;            // 성공여부
  private String pAcenErrCd;         // 실패사유코드
  private String pAcenEvntGrp;       // 시나리오구분
  private String pAgentCode;         // 대리점
  private String pOnOffType;         // 모집경로
  private String pSearchGbn;         // 검색구분
  private String pSearchName;        // 검색어
  private String pReqBuyType;        // 구매유형
  private String pOperType;          // 업무구분
  private String pRequestStateCode;  // 진행상태
  private String pPstate;            // 신청취소
  private String pEsimYn;            // esim여부
  private String pSelfYn;            // 셀프개통제외

  private String userId;
  private String dwnldRsn;
  private String exclDnldId;
  private String ipAddr;
  private String menuId;

  public String getRequestKey() {
    return requestKey;
  }

  public void setRequestKey(String requestKey) {
    this.requestKey = requestKey;
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

  public String getpSuccYn() {
    return pSuccYn;
  }

  public void setpSuccYn(String pSuccYn) {
    this.pSuccYn = pSuccYn;
  }

  public String getpAcenErrCd() {
    return pAcenErrCd;
  }

  public void setpAcenErrCd(String pAcenErrCd) {
    this.pAcenErrCd = pAcenErrCd;
  }

  public String getpAcenEvntGrp() {
    return pAcenEvntGrp;
  }

  public void setpAcenEvntGrp(String pAcenEvntGrp) {
    this.pAcenEvntGrp = pAcenEvntGrp;
  }

  public String getpAgentCode() {
    return pAgentCode;
  }

  public void setpAgentCode(String pAgentCode) {
    this.pAgentCode = pAgentCode;
  }

  public String getpOnOffType() {
    return pOnOffType;
  }

  public void setpOnOffType(String pOnOffType) {
    this.pOnOffType = pOnOffType;
  }

  public String getpSearchGbn() {
    return pSearchGbn;
  }

  public void setpSearchGbn(String pSearchGbn) {
    this.pSearchGbn = pSearchGbn;
  }

  public String getpSearchName() {
    return pSearchName;
  }

  public void setpSearchName(String pSearchName) {
    this.pSearchName = pSearchName;
  }

  public String getpReqBuyType() {
    return pReqBuyType;
  }

  public void setpReqBuyType(String pReqBuyType) {
    this.pReqBuyType = pReqBuyType;
  }

  public String getpOperType() {
    return pOperType;
  }

  public void setpOperType(String pOperType) {
    this.pOperType = pOperType;
  }

  public String getpRequestStateCode() {
    return pRequestStateCode;
  }

  public void setpRequestStateCode(String pRequestStateCode) {
    this.pRequestStateCode = pRequestStateCode;
  }

  public String getpPstate() {
    return pPstate;
  }

  public void setpPstate(String pPstate) {
    this.pPstate = pPstate;
  }

  public String getpEsimYn() {
    return pEsimYn;
  }

  public void setpEsimYn(String pEsimYn) {
    this.pEsimYn = pEsimYn;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public String getpSelfYn() {
	return pSelfYn;
  }

  public void setpSelfYn(String pSelfYn) {
	this.pSelfYn = pSelfYn;
  }

}
