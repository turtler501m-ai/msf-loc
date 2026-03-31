package com.ktmmobile.msf.formSvcChg.dto;

/**
 * MSF_REQUEST_OSST 테이블 DTO.
 * ASIS McpRequestOsstDto 와 동일 구조. TOBE 컬럼명 기준.
 */
public class SvcChgDataSharingOsstDto {

    private Long   requestKey;       // REQUEST_KEY (PK)
    private String mvnoOrdNo;        // MVNO_ORD_NO
    private String osstOrdNo;        // OSST_ORD_NO
    private String prgrStatCd;       // PRGR_STAT_CD
    private String svcCntrNo;        // SVC_CNTR_NO (NOT NULL — NCN)
    private String rsltCd;           // RSLT_CD
    private String rsltMsg;          // RSLT_MSG
    private String tlphNo;           // TLPH_NO
    private String tlphNoStatCd;     // TLPH_NO_STAT_CD
    private String encdTlphNo;       // ENCD_TLPH_NO
    private String tlphNoOwnCmpnCd;  // TLPH_NO_OWN_CMPN_CD
    private String asgnAgncId;       // ASGN_AGNC_ID
    private String openSvcIndCd;     // OPEN_SVC_IND_CD
    private String ifTypeCd;         // IF_TYPE_CD
    private String nstepGlobalId;    // NSTEP_GLOBAL_ID

    public Long   getRequestKey()            { return requestKey; }
    public void   setRequestKey(Long v)      { this.requestKey = v; }

    public String getMvnoOrdNo()             { return mvnoOrdNo; }
    public void   setMvnoOrdNo(String v)     { this.mvnoOrdNo = v; }

    public String getOsstOrdNo()             { return osstOrdNo; }
    public void   setOsstOrdNo(String v)     { this.osstOrdNo = v; }

    public String getPrgrStatCd()            { return prgrStatCd; }
    public void   setPrgrStatCd(String v)    { this.prgrStatCd = v; }

    public String getSvcCntrNo()             { return svcCntrNo; }
    public void   setSvcCntrNo(String v)     { this.svcCntrNo = v; }

    public String getRsltCd()                { return rsltCd; }
    public void   setRsltCd(String v)        { this.rsltCd = v; }

    public String getRsltMsg()               { return rsltMsg; }
    public void   setRsltMsg(String v)       { this.rsltMsg = v; }

    public String getTlphNo()                { return tlphNo; }
    public void   setTlphNo(String v)        { this.tlphNo = v; }

    public String getTlphNoStatCd()          { return tlphNoStatCd; }
    public void   setTlphNoStatCd(String v)  { this.tlphNoStatCd = v; }

    public String getEncdTlphNo()            { return encdTlphNo; }
    public void   setEncdTlphNo(String v)    { this.encdTlphNo = v; }

    public String getTlphNoOwnCmpnCd()           { return tlphNoOwnCmpnCd; }
    public void   setTlphNoOwnCmpnCd(String v)   { this.tlphNoOwnCmpnCd = v; }

    public String getAsgnAgncId()            { return asgnAgncId; }
    public void   setAsgnAgncId(String v)    { this.asgnAgncId = v; }

    public String getOpenSvcIndCd()          { return openSvcIndCd; }
    public void   setOpenSvcIndCd(String v)  { this.openSvcIndCd = v; }

    public String getIfTypeCd()              { return ifTypeCd; }
    public void   setIfTypeCd(String v)      { this.ifTypeCd = v; }

    public String getNstepGlobalId()             { return nstepGlobalId; }
    public void   setNstepGlobalId(String v)     { this.nstepGlobalId = v; }
}
