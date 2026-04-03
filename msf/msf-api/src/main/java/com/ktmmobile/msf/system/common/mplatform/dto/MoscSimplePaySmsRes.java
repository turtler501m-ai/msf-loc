package com.ktmmobile.msf.system.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.system.common.mplatform.vo.MPhoneNoVo;
import com.ktmmobile.msf.system.common.util.XmlParse;

public class MoscSimplePaySmsRes extends com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlVO{


    private String rsltMsg ;
    private String rsltYn  ;


    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        this.rsltMsg = XmlParse.getChildValue(this.body, "rsltMsg");
        this.rsltYn = XmlParse.getChildValue(this.body, "rsltYn");

    }


    public String getRsltMsg() {
        return rsltMsg;
    }



    public void setRsltMsg(String rsltMsg) {
        this.rsltMsg = rsltMsg;
    }


    public String getRsltYn() {
        return rsltYn;
    }


    public void setRsltYn(String rsltYn) {
        this.rsltYn = rsltYn;
    }










}