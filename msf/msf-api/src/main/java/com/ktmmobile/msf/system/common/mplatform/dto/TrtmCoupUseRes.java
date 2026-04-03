package com.ktmmobile.msf.system.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.system.common.mplatform.vo.MPhoneNoVo;
import com.ktmmobile.msf.system.common.util.XmlParse;

public class TrtmCoupUseRes extends com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlVO{

    private CoupInfoDto coupInfo;

    private String rtnCode ;
    private String rtnMsg  ;

    /*
    rtnCode
    rtnMsg
    dscnTypeCd
    dscnValue
    dscnMaxValue
    useStrtDt
    useEndDt
    */


    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        coupInfo = new CoupInfoDto();
        this.rtnCode = XmlParse.getChildValue(this.body, "rtnCode");
        this.rtnMsg = XmlParse.getChildValue(this.body, "rtnMsg");

        coupInfo.setDscnTypeCd(XmlParse.getChildValue(this.body, "dscnTypeCd"));
        coupInfo.setUseStrtDt(XmlParse.getChildValue(this.body, "useStrtDt"));
        coupInfo.setUseEndDt(XmlParse.getChildValue(this.body, "useEndDt"));


    }


    public String getRtnCode() {
        return rtnCode;
    }


    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }


    public String getRtnMsg() {
        return rtnMsg;
    }


    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }


    public CoupInfoDto getCoupInfo() {
        return coupInfo;
    }


    public void setCoupInfo(CoupInfoDto coupInfo) {
        this.coupInfo = coupInfo;
    }




}