package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class MPayViewDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String svcCntrNo;    // 계약번호
    private String mPayPeriod;   // 조회기간
    
    private String settlDealNo;  // 결제거래번호
    private String settleNo;     // 결제번호
    private String payStatus; 	 // 결재구분 (PRTLPAY, FULLPAY, PRTLCNCL, FULLCNCL)
    private String payStatusNm;  // 결재구분명 
    private String payStatusCd;  // 결제구분 순서코드
    private String settlDt; 	 // 결제일시  YYYYMMDDHH24MISS
    private String settlDtFmt; 	 // 결제일시 YYYY-MM-DD HH24:MI:SS
    private String settlNm; 	 // 결제명
    private String amt; 	     // 결제금액 또는 취소금액
    private String tmonLmtAmt; 	 // 당월한도금액
    private String rmndLmtAmt; 	 // 잔여한도금액
    private String ptnrPgId;  	 // 파트너 pg 아이디
    private String ptnrPgNm; 	 // 파트너 pg명
    private String ptnrCpId; 	 // 파트너 cp 아이디
    private String ptnrCpNm; 	 // 파트너 cp명
    private String ptnrSvcId; 	 // 파트너 서비스 아이디
    private String ptnrSvcNm; 	 // 파트너 서비스명
 
    public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getmPayPeriod() {
		return mPayPeriod;
	}
	public void setmPayPeriod(String mPayPeriod) {
		this.mPayPeriod = mPayPeriod;
	}
	public String getSettlDealNo() {
		return settlDealNo;
	}
	public void setSettlDealNo(String settlDealNo) {
		this.settlDealNo = settlDealNo;
	}
	public String getSettleNo() {
		return settleNo;
	}
	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getSettlDt() {
		return settlDt;
	}
	public void setSettlDt(String settlDt) {
		this.settlDt = settlDt;
	}
	public String getSettlDtFmt() {
		return settlDtFmt;
	}
	public void setSettlDtFmt(String settlDtFmt) {
		this.settlDtFmt = settlDtFmt;
	}
	public String getSettlNm() {
		return settlNm;
	}
	public void setSettlNm(String settlNm) {
		this.settlNm = settlNm;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getTmonLmtAmt() {
		return tmonLmtAmt;
	}
	public void setTmonLmtAmt(String tmonLmtAmt) {
		this.tmonLmtAmt = tmonLmtAmt;
	}
	public String getRmndLmtAmt() {
		return rmndLmtAmt;
	}
	public void setRmndLmtAmt(String rmndLmtAmt) {
		this.rmndLmtAmt = rmndLmtAmt;
	}
	public String getPtnrPgId() {
		return ptnrPgId;
	}
	public void setPtnrPgId(String ptnrPgId) {
		this.ptnrPgId = ptnrPgId;
	}
	public String getPtnrPgNm() {
		return ptnrPgNm;
	}
	public void setPtnrPgNm(String ptnrPgNm) {
		this.ptnrPgNm = ptnrPgNm;
	}
	public String getPtnrCpId() {
		return ptnrCpId;
	}
	public void setPtnrCpId(String ptnrCpId) {
		this.ptnrCpId = ptnrCpId;
	}
	public String getPtnrCpNm() {
		return ptnrCpNm;
	}
	public void setPtnrCpNm(String ptnrCpNm) {
		this.ptnrCpNm = ptnrCpNm;
	}
	public String getPtnrSvcId() {
		return ptnrSvcId;
	}
	public void setPtnrSvcId(String ptnrSvcId) {
		this.ptnrSvcId = ptnrSvcId;
	}
	public String getPtnrSvcNm() {
		return ptnrSvcNm;
	}
	public void setPtnrSvcNm(String ptnrSvcNm) {
		this.ptnrSvcNm = ptnrSvcNm;
	}
	public String getPayStatusNm() {
		return payStatusNm;
	}
	public void setPayStatusNm(String payStatusNm) {
		this.payStatusNm = payStatusNm;
	}
	public String getPayStatusCd() {
		return payStatusCd;
	}
	public void setPayStatusCd(String payStatusCd) {
		this.payStatusCd = payStatusCd;
	}

}
