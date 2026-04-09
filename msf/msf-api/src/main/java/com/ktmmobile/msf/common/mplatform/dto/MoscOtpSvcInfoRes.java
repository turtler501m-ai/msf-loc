package com.ktmmobile.msf.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.common.mplatform.vo.MPhoneNoVo;
import com.ktmmobile.msf.common.util.XmlParse;

public class MoscOtpSvcInfoRes extends com.ktmmobile.msf.common.mplatform.vo.CommonXmlNoSelfServiceException{

    private String otpNo ;
    private String resltCd  ;
    private String resltMsgSbst  ;



    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        this.otpNo = XmlParse.getChildValue(this.body, "otpNo");
        this.resltCd = XmlParse.getChildValue(this.body, "resltCd");
        this.resltMsgSbst = XmlParse.getChildValue(this.body, "resltMsgSbst");
    }

    public String getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(String otpNo) {
        this.otpNo = otpNo;
    }

    public String getResltCd() {
        return resltCd;
    }

    public void setResltCd(String resltCd) {
        this.resltCd = resltCd;
    }

    public String getResltMsgSbst() {
        return resltMsgSbst;
    }

    public void setResltMsgSbst(String resltMsgSbst) {
        this.resltMsgSbst = resltMsgSbst;
    }


}