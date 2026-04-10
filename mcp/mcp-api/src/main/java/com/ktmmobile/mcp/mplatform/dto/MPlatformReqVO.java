package com.ktmmobile.mcp.mplatform.dto;

import java.io.Serializable;

public class MPlatformReqVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String resNo;
    private String appEventCd;
    private String gubun;
    // 번호이동사전인증요청 파라미터
    private String npTlphNo;
    private String moveCompany;
    private String cstmrType;
    private String indvBizrYn;
    private String selfCertType;
    private String custIdntNo;
    private String crprNo;
    private String cstmrName;
    private String cntpntShopId;
    //번호조회
    private String tlphNo;
    //연동결과
    private String osstOrdNo;
    private String rsltCd;
    private String rsltMsg;
    private String nstepGlobalId;

    private String mvnoOrdNo;
    private String prgrStatCd;


    /** 청구계정번호 */
    private String billAcntNo;

    // OSST 연동 Param
    private String prdcChkNotiMsg;

    private String mcnResNo;         //명의변경 예약번호


    public String getResNo() {
        return resNo;
    }
    public void setResNo(String resNo) {
        this.resNo = resNo;
    }
    public String getAppEventCd() {
        return appEventCd;
    }
    public void setAppEventCd(String appEventCd) {
        this.appEventCd = appEventCd;
    }
    public String getGubun() {
        return gubun;
    }
    public void setGubun(String gubun) {
        this.gubun = gubun;
    }
    public String getNpTlphNo() {
        return npTlphNo;
    }
    public void setNpTlphNo(String npTlphNo) {
        this.npTlphNo = npTlphNo;
    }
    public String getMoveCompany() {
        return moveCompany;
    }
    public void setMoveCompany(String moveCompany) {
        this.moveCompany = moveCompany;
    }
    public String getCstmrType() {
        return cstmrType;
    }
    public void setCstmrType(String cstmrType) {
        this.cstmrType = cstmrType;
    }
    public String getIndvBizrYn() {
        return indvBizrYn;
    }
    public void setIndvBizrYn(String indvBizrYn) {
        this.indvBizrYn = indvBizrYn;
    }
    public String getSelfCertType() {
        return selfCertType;
    }
    public void setSelfCertType(String selfCertType) {
        this.selfCertType = selfCertType;
    }
    public String getCustIdntNo() {
        return custIdntNo;
    }
    public void setCustIdntNo(String custIdntNo) {
        this.custIdntNo = custIdntNo;
    }
    public String getCrprNo() {
        return crprNo;
    }
    public void setCrprNo(String crprNo) {
        this.crprNo = crprNo;
    }
    public String getCstmrName() {
        return cstmrName;
    }
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }
    public String getCntpntShopId() {
        return cntpntShopId;
    }
    public void setCntpntShopId(String cntpntShopId) {
        this.cntpntShopId = cntpntShopId;
    }
    public String getTlphNo() {
        return tlphNo;
    }
    public void setTlphNo(String tlphNo) {
        this.tlphNo = tlphNo;
    }
    public String getOsstOrdNo() {
        return osstOrdNo;
    }
    public void setOsstOrdNo(String osstOrdNo) {
        this.osstOrdNo = osstOrdNo;
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
    public String getNstepGlobalId() {
        return nstepGlobalId;
    }
    public void setNstepGlobalId(String nstepGlobalId) {
        this.nstepGlobalId = nstepGlobalId;
    }
    public String getBillAcntNo() {
        if (billAcntNo == null) {
            return "";
        }
        return billAcntNo;
    }
    public void setBillAcntNo(String billAcntNo) {
        this.billAcntNo = billAcntNo;
    }
	public String getMvnoOrdNo() {
		return mvnoOrdNo;
	}
	public void setMvnoOrdNo(String mvnoOrdNo) {
		this.mvnoOrdNo = mvnoOrdNo;
	}

    public String getPrgrStatCd() {
        return prgrStatCd;
    }

    public void setPrgrStatCd(String prgrStatCd) {
        this.prgrStatCd = prgrStatCd;
    }

    public String getPrdcChkNotiMsg() {
        return prdcChkNotiMsg;
    }

    public void setPrdcChkNotiMsg(String prdcChkNotiMsg) {
        this.prdcChkNotiMsg = prdcChkNotiMsg;
    }

    public String getMcnResNo() {
        return mcnResNo;
    }

    public void setMcnResNo(String mcnResNo) {
        this.mcnResNo = mcnResNo;
    }
}
