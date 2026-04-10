package com.ktmmobile.mcp.common.mplatform.dto;

import com.ktmmobile.mcp.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class RegSvcChgRes extends CommonXmlNoSelfServiceException{
    private String rsltCd;

    public String getRsltCd() {
        return rsltCd;
    }

    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.rsltCd = XmlParse.getChildValue(body, "rsltCd");
    }
}