package com.ktmmobile.msf.formComm.dto;

/**
 * 데이터쉐어링 OSST DB 레코드 DTO.
 * MSF_REQUEST_OSST 테이블 매핑.
 * ASIS McpRequestOsstDto 와 동일 구조.
 */
public class SmartFormOsstDto {

    private Long   requestKey;
    private String mvnoOrdNo;
    private String svcCntrNo;
    private String custId;
    private String prgrStatCd;
    private String rsltCd;
    private String rsltMsg;
    private String osstOrdNo;
    private String tlphNo;
    private String tlphNoStatCd;
    private String encdTlphNo;
    private String tlphNoOwnCmpnCd;
    private String asgnAgncId;
    private String openSvcIndCd;

    public Long getRequestKey() { return requestKey; }
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public String getMvnoOrdNo() { return mvnoOrdNo; }
    public void setMvnoOrdNo(String mvnoOrdNo) { this.mvnoOrdNo = mvnoOrdNo; }
    public String getSvcCntrNo() { return svcCntrNo; }
    public void setSvcCntrNo(String svcCntrNo) { this.svcCntrNo = svcCntrNo; }
    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
    public String getPrgrStatCd() { return prgrStatCd; }
    public void setPrgrStatCd(String prgrStatCd) { this.prgrStatCd = prgrStatCd; }
    public String getRsltCd() { return rsltCd; }
    public void setRsltCd(String rsltCd) { this.rsltCd = rsltCd; }
    public String getRsltMsg() { return rsltMsg; }
    public void setRsltMsg(String rsltMsg) { this.rsltMsg = rsltMsg; }
    public String getOsstOrdNo() { return osstOrdNo; }
    public void setOsstOrdNo(String osstOrdNo) { this.osstOrdNo = osstOrdNo; }
    public String getTlphNo() { return tlphNo; }
    public void setTlphNo(String tlphNo) { this.tlphNo = tlphNo; }
    public String getTlphNoStatCd() { return tlphNoStatCd; }
    public void setTlphNoStatCd(String tlphNoStatCd) { this.tlphNoStatCd = tlphNoStatCd; }
    public String getEncdTlphNo() { return encdTlphNo; }
    public void setEncdTlphNo(String encdTlphNo) { this.encdTlphNo = encdTlphNo; }
    public String getTlphNoOwnCmpnCd() { return tlphNoOwnCmpnCd; }
    public void setTlphNoOwnCmpnCd(String tlphNoOwnCmpnCd) { this.tlphNoOwnCmpnCd = tlphNoOwnCmpnCd; }
    public String getAsgnAgncId() { return asgnAgncId; }
    public void setAsgnAgncId(String asgnAgncId) { this.asgnAgncId = asgnAgncId; }
    public String getOpenSvcIndCd() { return openSvcIndCd; }
    public void setOpenSvcIndCd(String openSvcIndCd) { this.openSvcIndCd = openSvcIndCd; }
}
