package com.ktmm.tcp;

import java.io.Serializable;

public class DataVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String mvnoOrdNo;
	private String osstOrdNo;
	private String prgrStatCd;
	private String custId;
	private String svcCntrNo;
	private String rsltCd;
	private String rsltMsg;
	private String rsltDt;
	private String nstepGlobalId;
	private String remark;
	private String npBchngCmpnCntrTypeCd;	// 번호이동변경전사업자계약유형코드
	private String npFee;			// 번호이동수수료
	private String npNchrgAmt;		// 번호이동미청구금
	private String npPnltAmt;		// 번호이동위약금
	private String npUnpayAmt;		// 번호이동미납금
	private String npHndstInstAmt;	// 번호이동단말할부금
	private String npPrepayAmt;		// 번호이동선납금
	private String npBaseChrgAmt;	// 번호이동기본료
	private String npNtnlChrgAmt;	// 번호이동국내통화료
	private String npIntlChrgAmt;	// 번호이동국제통화료
	private String npAddChrgAmt;	// 번호이동부가서비스이용료
	private String npEtcChrgAmt;	// 번호이동기타금액
	private String npVat;			// 번호이동부가세
	private String npRmnStrtDt;		// 번호이동수납대상시작일
	private String npRmnEndDt;		// 번호이동수납대상종료일
	private String tlphNoStatCd;	// 번호상태코드(NU1 처리시)
	private String asgnAgncId;		// 번호할당대리점(NU1 처리시)
	private String tlphNoOwnCmpnCd;	// 번호소유사업자(NU1 처리시)
	private String openSvcIndCd;	// 개통서비스구분코드(NU1 처리시)
	private String encTlphNo;		// 암호화전화번호(NU1 처리시)
	private String tlphNo;			// 전화번호
	private String dclaDeedEftDt;	// 외국인 체류 만료 일자
	private String sbscLmtQnty;		// 가입한도수량
	private String sbscCircuitNum;	// 현재가입수량
	private String dlnqAmt;			// 미납금액
	private String eiccId;			// eSIM ICC ID

    private String rcvBillAcntNo;
    private String rcvCustNo;
	
	public String getMvnoOrdNo() {
		return mvnoOrdNo;
	}
	public void setMvnoOrdNo(String mvnoOrdNo) {
		this.mvnoOrdNo = mvnoOrdNo;
	}
	public String getOsstOrdNo() {
		return osstOrdNo;
	}
	public void setOsstOrdNo(String osstOrdNo) {
		this.osstOrdNo = osstOrdNo;
	}
	public String getPrgrStatCd() {
		return prgrStatCd;
	}
	public void setPrgrStatCd(String prgrStatCd) {
		this.prgrStatCd = prgrStatCd;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getRsltCd() {
		return rsltCd;
	}
	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}
	public String getRsltMsg() {
		return rsltMsg;
	}
	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}
	public String getRsltDt() {
		return rsltDt;
	}
	public void setRsltDt(String rsltDt) {
		this.rsltDt = rsltDt;
	}
	public String getNstepGlobalId() {
		return nstepGlobalId;
	}
	public void setNstepGlobalId(String nstepGlobalId) {
		this.nstepGlobalId = nstepGlobalId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNpBchngCmpnCntrTypeCd() {
		return npBchngCmpnCntrTypeCd;
	}
	public void setNpBchngCmpnCntrTypeCd(String npBchngCmpnCntrTypeCd) {
		this.npBchngCmpnCntrTypeCd = npBchngCmpnCntrTypeCd;
	}
	public String getNpFee() {
		return npFee;
	}
	public void setNpFee(String npFee) {
		this.npFee = npFee;
	}
	public String getNpNchrgAmt() {
		return npNchrgAmt;
	}
	public void setNpNchrgAmt(String npNchrgAmt) {
		this.npNchrgAmt = npNchrgAmt;
	}
	public String getNpPnltAmt() {
		return npPnltAmt;
	}
	public void setNpPnltAmt(String npPnltAmt) {
		this.npPnltAmt = npPnltAmt;
	}
	public String getNpUnpayAmt() {
		return npUnpayAmt;
	}
	public void setNpUnpayAmt(String npUnpayAmt) {
		this.npUnpayAmt = npUnpayAmt;
	}
	public String getNpHndstInstAmt() {
		return npHndstInstAmt;
	}
	public void setNpHndstInstAmt(String npHndstInstAmt) {
		this.npHndstInstAmt = npHndstInstAmt;
	}
	public String getNpPrepayAmt() {
		return npPrepayAmt;
	}
	public void setNpPrepayAmt(String npPrepayAmt) {
		this.npPrepayAmt = npPrepayAmt;
	}
	public String getNpBaseChrgAmt() {
		return npBaseChrgAmt;
	}
	public void setNpBaseChrgAmt(String npBaseChrgAmt) {
		this.npBaseChrgAmt = npBaseChrgAmt;
	}
	public String getNpNtnlChrgAmt() {
		return npNtnlChrgAmt;
	}
	public void setNpNtnlChrgAmt(String npNtnlChrgAmt) {
		this.npNtnlChrgAmt = npNtnlChrgAmt;
	}
	public String getNpIntlChrgAmt() {
		return npIntlChrgAmt;
	}
	public void setNpIntlChrgAmt(String npIntlChrgAmt) {
		this.npIntlChrgAmt = npIntlChrgAmt;
	}
	public String getNpAddChrgAmt() {
		return npAddChrgAmt;
	}
	public void setNpAddChrgAmt(String npAddChrgAmt) {
		this.npAddChrgAmt = npAddChrgAmt;
	}
	public String getNpEtcChrgAmt() {
		return npEtcChrgAmt;
	}
	public void setNpEtcChrgAmt(String npEtcChrgAmt) {
		this.npEtcChrgAmt = npEtcChrgAmt;
	}
	public String getNpVat() {
		return npVat;
	}
	public void setNpVat(String npVat) {
		this.npVat = npVat;
	}
	public String getNpRmnStrtDt() {
		return npRmnStrtDt;
	}
	public void setNpRmnStrtDt(String npRmnStrtDt) {
		this.npRmnStrtDt = npRmnStrtDt;
	}
	public String getNpRmnEndDt() {
		return npRmnEndDt;
	}
	public void setNpRmnEndDt(String npRmnEndDt) {
		this.npRmnEndDt = npRmnEndDt;
	}
	public String getTlphNoStatCd() {
		return tlphNoStatCd;
	}
	public void setTlphNoStatCd(String tlphNoStatCd) {
		this.tlphNoStatCd = tlphNoStatCd;
	}
	public String getAsgnAgncId() {
		return asgnAgncId;
	}
	public void setAsgnAgncId(String asgnAgncId) {
		this.asgnAgncId = asgnAgncId;
	}
	public String getTlphNoOwnCmpnCd() {
		return tlphNoOwnCmpnCd;
	}
	public void setTlphNoOwnCmpnCd(String tlphNoOwnCmpnCd) {
		this.tlphNoOwnCmpnCd = tlphNoOwnCmpnCd;
	}
	public String getOpenSvcIndCd() {
		return openSvcIndCd;
	}
	public void setOpenSvcIndCd(String openSvcIndCd) {
		this.openSvcIndCd = openSvcIndCd;
	}
	public String getEncTlphNo() {
		return encTlphNo;
	}
	public void setEncTlphNo(String encTlphNo) {
		this.encTlphNo = encTlphNo;
	}
	public String getTlphNo() {
		return tlphNo;
	}
	public void setTlphNo(String tlphNo) {
		this.tlphNo = tlphNo;
	}
	public String getDclaDeedEftDt() {
		return dclaDeedEftDt;
	}
	public void setDclaDeedEftDt(String dclaDeedEftDt) {
		this.dclaDeedEftDt = dclaDeedEftDt;
	}
	public String getSbscLmtQnty() {
		return sbscLmtQnty;
	}
	public void setSbscLmtQnty(String sbscLmtQnty) {
		this.sbscLmtQnty = sbscLmtQnty;
	}
	public String getSbscCircuitNum() {
		return sbscCircuitNum;
	}
	public void setSbscCircuitNum(String sbscCircuitNum) {
		this.sbscCircuitNum = sbscCircuitNum;
	}
	public String getDlnqAmt() {
		return dlnqAmt;
	}
	public void setDlnqAmt(String dlnqAmt) {
		this.dlnqAmt = dlnqAmt;
	}
	public String getEiccId() {
		return eiccId;
	}
	public void setEiccId(String eiccId) {
		this.eiccId = eiccId;
	}

    public String getRcvBillAcntNo() {
        return rcvBillAcntNo;
    }

    public void setRcvBillAcntNo(String rcvBillAcntNo) {
        this.rcvBillAcntNo = rcvBillAcntNo;
    }

    public String getRcvCustNo() {
        return rcvCustNo;
    }

    public void setRcvCustNo(String rcvCustNo) {
        this.rcvCustNo = rcvCustNo;
    }
}
