package com.ktmmobile.msf.system.common.mplatform.vo;

import java.io.UnsupportedEncodingException;

import org.jdom.Element;

import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringMakerUtil;
import com.ktmmobile.msf.system.common.util.StringUtil;
import com.ktmmobile.msf.system.common.util.XmlParse;


public class MpPerMyktfInfoVO extends CommonXmlVO{
    private final static String ITEM = "item";

    private String ssn;//주민번호
    private String email;//이메일주소
    private String addr;//주소
    private String homeTel;//전화번호
    private String initActivationDate;//가입일

    @Override
    public void parse() throws UnsupportedEncodingException  {
        Element item = this.body;
        this.email = XmlParse.getChildValue(item, "email");
        this.addr = XmlParse.getChildValue(item, "addr");
        this.homeTel = XmlParse.getChildValue(item, "homeTel");
        this.initActivationDate = XmlParse.getChildValue(item, "initActivationDate");

        // 마스킹해제 인증 안했을시
        if(SessionUtils.getMaskingSession() == 0) {
            if(StringUtil.isNotNull(this.email)){
                this.email = StringMakerUtil.getEmail(this.email);
            }
            if(StringUtil.isNotNull(this.addr)){
                this.addr = StringMakerUtil.getAddress(this.addr);
            }
            if(StringUtil.isNotNull(this.homeTel)){
                this.homeTel = StringMakerUtil.getPhoneNum(this.homeTel);
            }
        }else {
            if(StringUtil.isNotNull(this.homeTel)){
                this.homeTel = StringUtil.getMobileFullNum(StringUtil.NVL(this.homeTel,""));
            }
        }
    }


    public String getSsn() {
        return ssn;
    }
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getHomeTel() {
        return homeTel;
    }
    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }
    public String getInitActivationDate() {
        return initActivationDate;
    }
    public void setInitActivationDate(String initActivationDate) {
        this.initActivationDate = initActivationDate;
    }
    public String getItem() {
        return ITEM;
    }
}
