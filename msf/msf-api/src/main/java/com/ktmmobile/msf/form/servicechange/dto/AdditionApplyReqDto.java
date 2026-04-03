package com.ktmmobile.msf.form.servicechange.dto;

public class AdditionApplyReqDto {

    private String ncn;
    private String ctn;
    private String custId;
    private String soc;
    private String ftrNewParam;
    private String prodHstSeq;
    private String flag; // "Y"이면 선해지 후 신청 (변경)

    public String getNcn() { return ncn; }
    public void setNcn(String ncn) { this.ncn = ncn; }

    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getFtrNewParam() { return ftrNewParam; }
    public void setFtrNewParam(String ftrNewParam) { this.ftrNewParam = ftrNewParam; }

    public String getProdHstSeq() { return prodHstSeq; }
    public void setProdHstSeq(String prodHstSeq) { this.prodHstSeq = prodHstSeq; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
}
