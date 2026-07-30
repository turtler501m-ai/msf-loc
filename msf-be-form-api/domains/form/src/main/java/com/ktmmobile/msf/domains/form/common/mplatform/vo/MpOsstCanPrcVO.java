package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.jdom.Element;

public class MpOsstCanPrcVO extends CommonXmlVO {

    private String osstOrdNo;
    private String rslt;
    private String rsltMsg;

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        Element resultBody = this.body != null ? this.body : this.root;
        this.osstOrdNo = XmlParse.getChildValue(resultBody, "osstOrdNo");
        this.rslt = XmlParse.getChildValue(resultBody, "rsltCd");
        this.rsltMsg = XmlParse.getChildValue(resultBody, "rsltMsg");
    }

    public String getOsstOrdNo() {
        return osstOrdNo;
    }

    public void setOsstOrdNo(String osstOrdNo) {
        this.osstOrdNo = osstOrdNo;
    }

    public String getRslt() {
        return rslt;
    }

    public void setRslt(String rslt) {
        this.rslt = rslt;
    }

    public String getRsltMsg() {
        return rsltMsg;
    }

    public void setRsltMsg(String rsltMsg) {
        this.rsltMsg = rsltMsg;
    }
}
