package com.ktmmobile.msf.common.mplatform.vo;

/**
 * X20 부가서비스 목록 항목 VO. ASIS MpSocVO 주요 필드 동일.
 */
public class MpSocVO {

    private String soc;
    private String socDescription;
    private String socRateValue;
    private String effectiveDate;
    private String prodHstSeq;
    private String paramSbst;
    private int socRateVat;
    private String socRateVatValue;
    private String onlineCanYn;
    private String canCmnt;

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getSocDescription() { return socDescription; }
    public void setSocDescription(String socDescription) { this.socDescription = socDescription; }

    public String getSocRateValue() { return socRateValue; }
    public void setSocRateValue(String socRateValue) { this.socRateValue = socRateValue; }

    public String getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(String effectiveDate) { this.effectiveDate = effectiveDate; }

    public String getProdHstSeq() { return prodHstSeq; }
    public void setProdHstSeq(String prodHstSeq) { this.prodHstSeq = prodHstSeq; }

    public String getParamSbst() { return paramSbst; }
    public void setParamSbst(String paramSbst) { this.paramSbst = paramSbst; }

    public int getSocRateVat() { return socRateVat; }
    public void setSocRateVat(int socRateVat) { this.socRateVat = socRateVat; }

    public String getSocRateVatValue() { return socRateVatValue; }
    public void setSocRateVatValue(String socRateVatValue) { this.socRateVatValue = socRateVatValue; }

    public String getOnlineCanYn() { return onlineCanYn; }
    public void setOnlineCanYn(String onlineCanYn) { this.onlineCanYn = onlineCanYn; }

    public String getCanCmnt() { return canCmnt; }
    public void setCanCmnt(String canCmnt) { this.canCmnt = canCmnt; }
}
