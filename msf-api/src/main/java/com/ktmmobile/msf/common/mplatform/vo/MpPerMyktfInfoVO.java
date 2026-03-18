package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * X01 가입정보조회 응답 VO. ASIS MpPerMyktfInfoVO 동일 필드.
 */
public class MpPerMyktfInfoVO extends CommonXmlVO {

    private String email;
    private String addr;
    private String homeTel;
    private String initActivationDate;

    @Override
    protected void parse() {
        if (body == null) return;
        this.email = XmlParseUtil.getChildValue(body, "email");
        this.addr = XmlParseUtil.getChildValue(body, "addr");
        this.homeTel = XmlParseUtil.getChildValue(body, "homeTel");
        this.initActivationDate = XmlParseUtil.getChildValue(body, "initActivationDate");
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }

    public String getHomeTel() { return homeTel; }
    public void setHomeTel(String homeTel) { this.homeTel = homeTel; }

    public String getInitActivationDate() { return initActivationDate; }
    public void setInitActivationDate(String initActivationDate) { this.initActivationDate = initActivationDate; }
}
