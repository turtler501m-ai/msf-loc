package com.ktmmobile.msf.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.common.util.StringMakerUtil;
import com.ktmmobile.msf.common.util.XmlParse;


public class MpBilPrintInfoVO extends CommonXmlVO{
    private List<ItemBillDto> itemBillDto;
    private List<SndGuDto> sndGuDto;
    private List<ReqReDto> reqReDto;

    private String disableFlag;//사이버 명세서 이용고객 체크
    private String option;//요금명세서 이메일 유무
    private String arBalance;//실 미납금
    private String zipCode1;//우편번호(-포함)
    private String zipCode;//우편번호(-미포함)
    private String pAddr;//메인주소
    private String sAddr;//상세주소
    private String ban;//청구계정번호
    private String addr;//주소

    @Override
    public void parse() throws UnsupportedEncodingException  {
        Element item = this.body;
        this.disableFlag = XmlParse.getChildValue(item, "disableFlag");
        this.option = XmlParse.getChildValue(item, "option");
        this.arBalance = XmlParse.getChildValue(item, "arBalance");

        this.zipCode1 = XmlParse.getChildValue(item, "zipCode1");
        this.zipCode = XmlParse.getChildValue(item, "zipCode");
        this.pAddr = XmlParse.getChildValue(item, "pAddr");
        this.sAddr = XmlParse.getChildValue(item, "sAddr");
        this.ban = XmlParse.getChildValue(item, "ban");
        this.addr = StringMakerUtil.getAddress(pAddr + " " + sAddr);


        List<Element> itemBillList = XmlParse.getChildElementList(this.body, "outItemBillDto");
        itemBillDto = new ArrayList<ItemBillDto>();
        for(Element itemBill : itemBillList){
            ItemBillDto vo = new ItemBillDto();

            vo.setBillDate(XmlParse.getChildValue(itemBill, "billDate"));
            vo.setAmt(XmlParse.getChildValue(itemBill, "amt"));
            itemBillDto.add(vo);
        }

        List<Element> sndGuList = XmlParse.getChildElementList(this.body, "outSndGuDto");
        sndGuDto = new ArrayList<SndGuDto>();
        for(Element sndGu : sndGuList){
            SndGuDto vo = new SndGuDto();

            vo.setSendGubun(XmlParse.getChildValue(sndGu, "sendGubun"));
            sndGuDto.add(vo);
        }

        List<Element> reqReList = XmlParse.getChildElementList(this.body, "outReqReDto");
        reqReDto = new ArrayList<ReqReDto>();
        for(Element reqRe : reqReList){
            ReqReDto vo = new ReqReDto();

            vo.setRequestReason(XmlParse.getChildValue(reqRe, "requestReason"));
            reqReDto.add(vo);
        }

    }


    public String getDisableFlag() {
        return disableFlag;
    }
    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getOption() {
        return option;
    }
    public void setOption(String option) {
        this.option = option;
    }

    public String getArBalance() {
        return arBalance;
    }
    public void setArBalance(String arBalance) {
        this.arBalance = arBalance;
    }

    public String getZipCode1() {
        return zipCode1;
    }
    public void setZipCode1(String zipCode1) {
        this.zipCode1 = zipCode1;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getpAddr() {
        return pAddr;
    }
    public void setpAddr(String pAddr) {
        this.pAddr = pAddr;
    }

    public String getsAddr() {
        return sAddr;
    }
    public void setsAddr(String sAddr) {
        this.sAddr = sAddr;
    }

    public String getBan() {
        return ban;
    }
    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }

    public class ItemBillDto {
        private String billDate;//청구월
        private String amt;//월별 미납금

        public String getBillDate() {
            return billDate;
        }
        public void setBillDate(String billDate) {
            this.billDate = billDate;
        }
        public String getAmt() {
            return amt;
        }
        public void setAmt(String amt) {
            this.amt = amt;
        }
    }

    public class SndGuDto {
        private String sendGubun;//일반우편:F, 등기우편:R

        public String getSendGubun() {
            return sendGubun;
        }
        public void setSendGubun(String sendGubun) {
            this.sendGubun = sendGubun;
        }
    }

    public class ReqReDto {
        private String requestReason;//미수령:NR, 주소변경(1회성):AC

        public String getRequestReason() {
            return requestReason;
        }
        public void setRequestReason(String requestReason) {
            this.requestReason = requestReason;
        }
    }
}
