package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;

public class PromotionDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private String phone;
    private String agree;
    private String birthDate;
    private String ci;
    private String di;
    private String useTelCode;
    private String usePayAmt;
    private String sysRdt;
    private String sysRdate;
    private String sel1;
    private String sel2;
    private String sel3;
    private String sel4;
    private String sel5;
    private long eventPromotionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public String getUseTelCode() {
        return useTelCode;
    }

    public void setUseTelCode(String useTelCode) {
        this.useTelCode = useTelCode;
    }

    public String getUsePayAmt() {
        return usePayAmt;
    }

    public void setUsePayAmt(String usePayAmt) {
        this.usePayAmt = usePayAmt;
    }

    public String getSysRdt() {
        return sysRdt;
    }

    public void setSysRdt(String sysRdt) {
        this.sysRdt = sysRdt;
    }

    public String getSysRdate() {
        return sysRdate;
    }

    public void setSysRdate(String sysRdate) {
        this.sysRdate = sysRdate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getSel1() {
        return sel1;
    }

    public void setSel1(String sel1) {
        this.sel1 = sel1;
    }

    public String getSel2() {
        return sel2;
    }

    public void setSel2(String sel2) {
        this.sel2 = sel2;
    }

    public String getSel3() {
        return sel3;
    }

    public void setSel3(String sel3) {
        this.sel3 = sel3;
    }

    public String getSel4() {
        return sel4;
    }

    public void setSel4(String sel4) {
        this.sel4 = sel4;
    }

    public String getSel5() {
        return sel5;
    }

    public void setSel5(String sel5) {
        this.sel5 = sel5;
    }

    public long getEventPromotionId() {
        return eventPromotionId;
    }

    public void setEventPromotionId(long eventPromotionId) {
        this.eventPromotionId = eventPromotionId;
    }
}
