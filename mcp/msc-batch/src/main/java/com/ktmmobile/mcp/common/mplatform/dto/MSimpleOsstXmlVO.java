package com.ktmmobile.mcp.common.mplatform.dto;

import com.ktmmobile.mcp.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MSimpleOsstXmlVO extends CommonXmlVO{

    private String rsltMsg;    // rsltCd가 F인 경우, 해당하는 에러코드 메시지
    private String rsltCd;     // S: 성공, F: 실패

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException{
        this.rsltMsg = XmlParse.getChildValue(this.root, "rsltMsg");
        this.rsltCd = XmlParse.getChildValue(this.root, "rsltCd");
    }

    public String getRsltMsg() {
        return rsltMsg;
    }

    public void setRsltMsg(String rsltMsg) {
        this.rsltMsg = rsltMsg;
    }

    public String getRsltCd() {
        return rsltCd;
    }

    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }
}
