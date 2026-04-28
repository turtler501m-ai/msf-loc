package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MpOsstCanPrcVO extends CommonXmlVO {

    private String osstOrdNo;
    private String rslt;
    private String rsltMsg;

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.osstOrdNo = XmlParse.getChildValue(this.body, "osstOrdNo");
        this.rslt = XmlParse.getChildValue(this.body, "rslt");
        this.rsltMsg = XmlParse.getChildValue(this.body, "rsltMsg");
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
