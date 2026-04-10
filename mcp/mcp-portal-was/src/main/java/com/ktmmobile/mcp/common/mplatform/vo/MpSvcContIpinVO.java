package com.ktmmobile.mcp.common.mplatform.vo;

import com.ktmmobile.mcp.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MpSvcContIpinVO extends CommonXmlVO{

    private String ipinCi; // 아이핀 CI

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.ipinCi = XmlParse.getChildValue(this.body, "ipinCi");
    }

    public String getIpinCi() {
        return ipinCi;
    }

    public void setIpinCi(String ipinCi) {
        this.ipinCi = ipinCi;
    }
}
