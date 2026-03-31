package com.ktmmobile.msf.formSvcChg.dto;

/**
 * 부가서비스 항목 DTO. X20 MpSocVO → 프론트엔드 전달용 변환.
 */
public class AdditionItemDto {

    private String soc;
    private String socDescription;
    private String socRateValue;
    private String effectiveDate;
    private String prodHstSeq;
    private String paramSbst;
    /** 해지 안내 문구 (getMspRateMst canCmnt). ASIS myAddSvcListAjax enrichment. */
    private String canCmnt;
    /** 온라인 해지 가능 여부 Y/N (getMspRateMst onlineCanYn). ASIS myAddSvcListAjax enrichment. */
    private String onlineCanYn;

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

    public String getCanCmnt() { return canCmnt; }
    public void setCanCmnt(String canCmnt) { this.canCmnt = canCmnt; }

    public String getOnlineCanYn() { return onlineCanYn; }
    public void setOnlineCanYn(String onlineCanYn) { this.onlineCanYn = onlineCanYn; }
}
