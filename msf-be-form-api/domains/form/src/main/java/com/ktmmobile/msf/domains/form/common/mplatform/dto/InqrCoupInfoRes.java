package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoVo;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class InqrCoupInfoRes extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO{

    private List<CoupInfoDto> coupInfoList;

    private String rtnCode ;
    private String rtnMsg  ;
    private int totalContentCnt ;


    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        this.rtnCode = XmlParse.getChildValue(this.body, "rtnCode");
        this.rtnMsg = XmlParse.getChildValue(this.body, "rtnMsg");
        int tempInt = 1;

        try {
            tempInt = Integer.parseInt(XmlParse.getChildValue(this.body, "totalContentCnt"));
        } catch (Exception e) {
            tempInt = 1;
        }
        this.totalContentCnt = tempInt;

        List<Element> itemList = XmlParse.getChildElementList(this.body, "coupInfoList");
        coupInfoList = new ArrayList<CoupInfoDto>();
        for(Element item : itemList){
            CoupInfoDto coupInfo = new CoupInfoDto();

            coupInfo.setSvcTypeCd(XmlParse.getChildValue(item, "svcTypeCd"));
            coupInfo.setCoupTypeCd(XmlParse.getChildValue(item, "coupTypeCd"));
            coupInfo.setCoupNm(XmlParse.getChildValue(item, "coupNm"));
            coupInfo.setCoupSerialNo(XmlParse.getChildValue(item, "coupSerialNo"));
            coupInfo.setCoupCategoryCd(XmlParse.getChildValue(item, "coupCategoryCd"));
            coupInfo.setDscnTypeCd(XmlParse.getChildValue(item, "dscnTypeCd"));
            coupInfo.setCoupValu(XmlParse.getChildValue(item, "coupValu"));
            coupInfo.setUseStrtDt(XmlParse.getChildValue(item, "useStrtDt"));
            coupInfo.setUseEndDt(XmlParse.getChildValue(item, "useEndDt"));
            coupInfo.setRgstStrtDt(XmlParse.getChildValue(item, "rgstStrtDt"));
            coupInfo.setUseReqDt(XmlParse.getChildValue(item, "useReqDt"));
            coupInfo.setRsvPsblYn(XmlParse.getChildValue(item, "rsvPsblYn"));
            coupInfo.setCoupCreId(XmlParse.getChildValue(item, "coupCreId"));
            coupInfo.setCoupAplyLimitCd(XmlParse.getChildValue(item, "coupAplyLimitCd"));
            coupInfo.setSmsRcvCtn(XmlParse.getChildValue(item, "smsRcvCtn"));
            coupInfo.setCoupStatCd(XmlParse.getChildValue(item, "coupStatCd"));

            coupInfoList.add(coupInfo);
        }
    }





    public List<CoupInfoDto> getCoupInfoList() {
        return coupInfoList;
    }





    public void setCoupInfoList(List<CoupInfoDto> coupInfoList) {
        this.coupInfoList = coupInfoList;
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


    public int getTotalContentCnt() {
        return totalContentCnt;
    }


    public void setTotalContentCnt(int totalContentCnt) {
        this.totalContentCnt = totalContentCnt;
    }






}