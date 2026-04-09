package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.StringMakerUtil;
import com.ktmmobile.msf.common.util.StringUtil;
import com.ktmmobile.msf.common.util.XmlParse;

public class MpFarChangewayInfoVO extends CommonXmlVO{
    private final static String ITEM = "farChangewayInfo";
    private final static String PAY_METHOD1 = "자동이체";
    private final static String PAY_METHOD2 = "지로";
    private final static String PAY_METHOD3 = "신용카드";
    private final static String PAY_METHOD4 = "쿡전화 합산";
    private final static String PAY_METHOD5 = "쿡인터넷 합산";
    private final static String PAY_METHOD6 = "쇼와이브로 합산";

    private String payMethod         ;//납부방법
    private String billCycleDueDay   ;//납부일
    private String blBankName        ;//은행명
    private String blBankAcctNo      ;//계좌번호
    private String bankAcctHolderName;//납부자명
    private String blAddr            ;//청구지 주소
    private String creditCardName    ;//카드사명
    private String prevCardNo        ;//카드번호
    private String prevExpirDt       ;//카드만료기간
    private String jointBillWithKt   ;//KT합산구분 아이디
    private String emailBillReqType; //
    private String payBizrCd ;         //간편결제사업자코드    20  C   KKO(카카오페이), PYC(페이코)
    private String payTmsCd;		//납부회차

    private String prevCardNoRaw;   //카드번호 원본
    private String prevExpirDtRaw;  //카드만료기간 원본
    private String blAddrRaw;       //청구지 주소 원본
    private String blBankAcctNoRaw; //계좌번호 원본

    @Override
    public void parse()  {
//		this.enckey = body.getAttributeValue("a");
//		Element item = XmlParse.getChildElement(this.body, ITEM);
        this.payMethod = XmlParse.getChildValue(this.body, "payMethod");
        this.billCycleDueDay = XmlParse.getChildValue(this.body, "billCycleDueDay");

        if( StringUtil.equals(payMethod, PAY_METHOD1) ){//자동이체
            this.blBankName = XmlParse.getChildValue(this.body, "blBankName");
//			this.blBankAcctNo = StringMakerUtil.getBankNumber(XmlParse.getChildValue(item, "blBankAcctNo"));
            this.blBankAcctNo = StringMakerUtil.getBankNumber(XmlParse.getChildValue(this.body, "blBankAcctNo"));
            this.bankAcctHolderName = XmlParse.getChildValue(this.body, "bankAcctHolderName");
            this.blBankAcctNoRaw = XmlParse.getChildValue(this.body, "blBankAcctNo");
        } else if( StringUtil.equals(payMethod, PAY_METHOD2) ){//지로
            this.blAddr = StringMakerUtil.getAddress(XmlParse.getChildValue(this.body, "blAddr"));
            this.blAddrRaw = XmlParse.getChildValue(this.body, "blAddr");
        } else if( StringUtil.equals(payMethod, PAY_METHOD3) ){//신용카드
            this.creditCardName = XmlParse.getChildValue(this.body, "creditCardName");
//			this.prevCardNo = StringMakerUtil.getCardNumber(XmlParse.getChildValue(this.body, "prevCardNo"));
//            this.prevCardNo = XmlParse.getChildValue(this.body, "prevCardNo");
            this.prevCardNo =  StringMakerUtil.getCardNumber2(XmlParse.getChildValue(this.body, "prevCardNo"));
//			this.prevExpirDt = StringMakerUtil.getCardExpirDate(XmlParse.getChildValue(this.body, "prevExpirDt"));
//            this.prevExpirDt = XmlParse.getChildValue(this.body, "prevExpirDt");
            this.prevCardNoRaw = XmlParse.getChildValue(this.body, "prevCardNo");
            this.prevExpirDt = StringMakerUtil.getCardExpirDate2(XmlParse.getChildValue(this.body, "prevExpirDt"));
            this.prevExpirDtRaw = XmlParse.getChildValue(this.body, "prevExpirDt");
            this.payTmsCd = XmlParse.getChildValue(this.body, "payTmsCd");
        } else if( StringUtil.equals(payMethod, "간편결제") ){//신용카드
            this.payBizrCd = XmlParse.getChildValue(this.body, "payBizrCd");
        }
//		} else if( StringUtil.equals(payMethod, PAY_METHOD4) ){//쿡전화
//			this.jointBillWithKt = XmlParse.getChildValue(item, "jointBillWithKt");
//		} else if( StringUtil.equals(payMethod, PAY_METHOD5) ){//쿡인터넷
//			this.jointBillWithKt = XmlParse.getChildValue(item, "jointBillWithKt");
//		} else if( StringUtil.equals(payMethod, PAY_METHOD6) ){//쇼와이브로
//			this.jointBillWithKt = XmlParse.getChildValue(item, "jointBillWithKt");
//		}
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getBillCycleDueDay() {
        return billCycleDueDay;
    }

    public void setBillCycleDueDay(String billCycleDueDay) {
        this.billCycleDueDay = billCycleDueDay;
    }

    public String getBlBankName() {
        return blBankName;
    }

    public void setBlBankName(String blBankName) {
        this.blBankName = blBankName;
    }

    public String getBlBankAcctNo() {
        return blBankAcctNo;
    }

    public void setBlBankAcctNo(String blBankAcctNo) {
        this.blBankAcctNo = blBankAcctNo;
    }

    public String getBankAcctHolderName() {
        return bankAcctHolderName;
    }

    public void setBankAcctHolderName(String bankAcctHolderName) {
        this.bankAcctHolderName = bankAcctHolderName;
    }

    public String getBlAddr() {
        return blAddr;
    }

    public void setBlAddr(String blAddr) {
        this.blAddr = blAddr;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public String getPrevCardNo() {
        return prevCardNo;
    }

    public void setPrevCardNo(String prevCardNo) {
        this.prevCardNo = prevCardNo;
    }

    public String getPrevExpirDt() {
        return prevExpirDt;
    }

    public void setPrevExpirDt(String prevExpirDt) {
        this.prevExpirDt = prevExpirDt;
    }

    public String getJointBillWithKt() {
        return jointBillWithKt;
    }

    public void setJointBillWithKt(String jointBillWithKt) {
        this.jointBillWithKt = jointBillWithKt;
    }

    public String getItem() {
        return ITEM;
    }

    public String getEmailBillReqType() {
        return emailBillReqType;
    }

    public void setEmailBillReqType(String emailBillReqType) {
        this.emailBillReqType = emailBillReqType;
    }

    public static String getPayMethod1() {
        return PAY_METHOD1;
    }

    public static String getPayMethod2() {
        return PAY_METHOD2;
    }

    public static String getPayMethod3() {
        return PAY_METHOD3;
    }

    public static String getPayMethod4() {
        return PAY_METHOD4;
    }

    public static String getPayMethod5() {
        return PAY_METHOD5;
    }

    public static String getPayMethod6() {
        return PAY_METHOD6;
    }

    public String getPayBizrCd() {
        return payBizrCd;
    }

    public void setPayBizrCd(String payBizrCd) {
        this.payBizrCd = payBizrCd;
    }

    public String getPayTmsCd() {
        return payTmsCd;
    }

    public void setPayTmsCd(String payTmsCd) {
        this.payTmsCd = payTmsCd;
    }

    public String getPrevCardNoRaw() {
        return prevCardNoRaw;
    }

    public void setPrevCardNoRaw(String prevCardNoRaw) {
        this.prevCardNoRaw = prevCardNoRaw;
    }

    public String getPrevExpirDtRaw() {
        return prevExpirDtRaw;
    }

    public void setPrevExpirDtRaw(String prevExpirDtRaw) {
        this.prevExpirDtRaw = prevExpirDtRaw;
    }

    public String getBlAddrRaw() {
        return blAddrRaw;
    }

    public void setBlAddrRaw(String blAddrRaw) {
        this.blAddrRaw = blAddrRaw;
    }

    public String getBlBankAcctNoRaw() {
        return blBankAcctNoRaw;
    }

    public void setBlBankAcctNoRaw(String blBankAcctNoRaw) {
        this.blBankAcctNoRaw = blBankAcctNoRaw;
    }
}
