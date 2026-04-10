package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoVo;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MoscWireUseTimeInfoRes extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO{


    private String svcContSbscDt   ;//서비스계약가입일시
    private String totUseDayNum    ;//총사용일수
    private String totStopDayNum   ;//총정지일수
    private String longUseAdjDayNum    ;//장기조정기간일수
    private String realUseDayNum   ;//실사용기간



    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.svcContSbscDt = XmlParse.getChildValue(this.body, "svcContSbscDt");
        this.totUseDayNum = XmlParse.getChildValue(this.body, "totUseDayNum");
        this.totStopDayNum = XmlParse.getChildValue(this.body, "totStopDayNum");
        this.longUseAdjDayNum = XmlParse.getChildValue(this.body, "longUseAdjDayNum");
        this.realUseDayNum = XmlParse.getChildValue(this.body, "realUseDayNum");
    }



    public String getSvcContSbscDt() {
        return svcContSbscDt;
    }



    public void setSvcContSbscDt(String svcContSbscDt) {
        this.svcContSbscDt = svcContSbscDt;
    }



    public String getTotUseDayNum() {
        return totUseDayNum;
    }



    public void setTotUseDayNum(String totUseDayNum) {
        this.totUseDayNum = totUseDayNum;
    }



    public String getTotStopDayNum() {
        return totStopDayNum;
    }



    public void setTotStopDayNum(String totStopDayNum) {
        this.totStopDayNum = totStopDayNum;
    }



    public String getLongUseAdjDayNum() {
        return longUseAdjDayNum;
    }



    public void setLongUseAdjDayNum(String longUseAdjDayNum) {
        this.longUseAdjDayNum = longUseAdjDayNum;
    }



    public String getRealUseDayNum() {
        return realUseDayNum;
    }



    public void setRealUseDayNum(String realUseDayNum) {
        this.realUseDayNum = realUseDayNum;
    }




}