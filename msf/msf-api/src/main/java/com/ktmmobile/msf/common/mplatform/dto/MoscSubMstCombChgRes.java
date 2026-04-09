package com.ktmmobile.msf.common.mplatform.dto;

import com.ktmmobile.msf.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MoscSubMstCombChgRes extends com.ktmmobile.msf.common.mplatform.vo.CommonXmlNoSelfServiceException{

    private String resultCd  ;
    private String resultMsg  ;


    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        this.resultCd = XmlParse.getChildValue(this.body, "resultCd");
        this.resultMsg = XmlParse.getChildValue(this.body, "resultMsg");
    }

    public String getResultCd() {
        return resultCd;
    }

    public void setResultCd(String resultCd) {
        this.resultCd = resultCd;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
