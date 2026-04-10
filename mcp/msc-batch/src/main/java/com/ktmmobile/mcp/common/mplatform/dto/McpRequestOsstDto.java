package com.ktmmobile.mcp.common.mplatform.dto;

import java.io.Serializable;

public class McpRequestOsstDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mvnoOrdNo;   //MVNO오더번호
    private int seq;            //일련번호
    private String osstOrdNo;   //OSST오더번호
    private String prgrStatCd;  //진행상태코드
    private String custId;      //고객ID
    private String svcCntrNo;   //서비스계약번호
    private String rsltCd;      //처리결과코드
    private String rsltMsg;     //처리결과내용
    private String rsltDt;      //처리일시

    public String getMvnoOrdNo() {
        return mvnoOrdNo;
    }

    public void setMvnoOrdNo(String mvnoOrdNo) {
        this.mvnoOrdNo = mvnoOrdNo;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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
}
