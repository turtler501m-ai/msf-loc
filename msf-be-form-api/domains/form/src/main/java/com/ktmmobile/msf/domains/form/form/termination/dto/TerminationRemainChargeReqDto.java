package com.ktmmobile.msf.domains.form.form.termination.dto;

public class TerminationRemainChargeReqDto {
    private String ncn;     // 계약번호
    private String ctn;     // 휴대폰번호
    private String custId;  // 고객ID

    public String getNcn() {
        return ncn;
    }

    public void setNcn(String ncn) {
        this.ncn = ncn;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
