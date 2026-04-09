package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MSimpleOsstXmlSt1VO extends CommonXmlVO{

    // ST1 : M-Platform Order API 처리 상태 및 결과 조회

    private String mvnoOrdNo;   /*MVNO 오더번호*/

    private String prgrStatCd;  /*진행 상태 코드*/

    private String rsltCd;      /*처리결과코드*/

    private String rsltMsg;     /*처리결과내용*/

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

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        this.mvnoOrdNo = XmlParse.getChildValue(this.root, "mvnoOrdNo");
        this.prgrStatCd = XmlParse.getChildValue(this.root, "prgrStatCd");
        this.rsltCd = XmlParse.getChildValue(this.root, "rsltCd");
        this.rsltMsg = XmlParse.getChildValue(this.root, "rsltMsg");
    }
}
