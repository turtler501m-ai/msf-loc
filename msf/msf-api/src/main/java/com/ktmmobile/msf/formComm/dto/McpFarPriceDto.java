package com.ktmmobile.msf.formComm.dto;

/**
 * 요금제 변경 정보 DTO.
 * ASIS: McpFarPriceDto (mcp-api).
 * selectFarPricePlan / selectFarPricePlanList 결과 매핑.
 */
public class McpFarPriceDto {

    private String prvRateGrpCd;    // 변경전_요금그룹코드
    private String prvRateGrpNm;    // 변경전_요금그룹명
    private String prvRateGrpTypeCd;
    private String prvRateGrpTypeNm;
    private String prvRateCd;       // 변경전_요금제코드
    private String prvRateNm;       // 변경전_요금제명
    private String prvPtrnRateYn;
    private String prvPayClCd;
    private String prvDataType;
    private String prvRateType;
    private String prvRmk;
    private int    prvBaseAmt;      // 변경전_기본료
    private String prvApplStrtDt;
    private String prvApplEndDt;
    private String nxtRateGrpCd;
    private String nxtRateGrpNm;
    private String nxtRateGrpTypeCd;
    private String nxtRateGrpTypeNm;
    private String nxtRateCd;       // 변경후_요금제코드
    private String nxtRateNm;       // 변경후_요금제명
    private String nxtPtrnRateYn;
    private String nxtPayClCd;
    private String nxtDataType;
    private String nxtRateType;
    private String nxtRmk;
    private int    nxtBaseAmt;      // 변경후_기본료
    private String nxtApplStrtDt;
    private String nxtApplEndDt;
    private int    baseVatAmt;      // VAT 포함 금액
    private String appStartDd;      // 적용일시

    public String getPrvRateGrpCd() { return prvRateGrpCd; }
    public void setPrvRateGrpCd(String v) { this.prvRateGrpCd = v; }
    public String getPrvRateGrpNm() { return prvRateGrpNm; }
    public void setPrvRateGrpNm(String v) { this.prvRateGrpNm = v; }
    public String getPrvRateGrpTypeCd() { return prvRateGrpTypeCd; }
    public void setPrvRateGrpTypeCd(String v) { this.prvRateGrpTypeCd = v; }
    public String getPrvRateGrpTypeNm() { return prvRateGrpTypeNm; }
    public void setPrvRateGrpTypeNm(String v) { this.prvRateGrpTypeNm = v; }
    public String getPrvRateCd() { return prvRateCd; }
    public void setPrvRateCd(String v) { this.prvRateCd = v; }
    public String getPrvRateNm() { return prvRateNm; }
    public void setPrvRateNm(String v) { this.prvRateNm = v; }
    public String getPrvPtrnRateYn() { return prvPtrnRateYn; }
    public void setPrvPtrnRateYn(String v) { this.prvPtrnRateYn = v; }
    public String getPrvPayClCd() { return prvPayClCd; }
    public void setPrvPayClCd(String v) { this.prvPayClCd = v; }
    public String getPrvDataType() { return prvDataType; }
    public void setPrvDataType(String v) { this.prvDataType = v; }
    public String getPrvRateType() { return prvRateType; }
    public void setPrvRateType(String v) { this.prvRateType = v; }
    public String getPrvRmk() { return prvRmk; }
    public void setPrvRmk(String v) { this.prvRmk = v; }
    public int getPrvBaseAmt() { return prvBaseAmt; }
    public void setPrvBaseAmt(int v) { this.prvBaseAmt = v; }
    public String getPrvApplStrtDt() { return prvApplStrtDt; }
    public void setPrvApplStrtDt(String v) { this.prvApplStrtDt = v; }
    public String getPrvApplEndDt() { return prvApplEndDt; }
    public void setPrvApplEndDt(String v) { this.prvApplEndDt = v; }
    public String getNxtRateGrpCd() { return nxtRateGrpCd; }
    public void setNxtRateGrpCd(String v) { this.nxtRateGrpCd = v; }
    public String getNxtRateGrpNm() { return nxtRateGrpNm; }
    public void setNxtRateGrpNm(String v) { this.nxtRateGrpNm = v; }
    public String getNxtRateGrpTypeCd() { return nxtRateGrpTypeCd; }
    public void setNxtRateGrpTypeCd(String v) { this.nxtRateGrpTypeCd = v; }
    public String getNxtRateGrpTypeNm() { return nxtRateGrpTypeNm; }
    public void setNxtRateGrpTypeNm(String v) { this.nxtRateGrpTypeNm = v; }
    public String getNxtRateCd() { return nxtRateCd; }
    public void setNxtRateCd(String v) { this.nxtRateCd = v; }
    public String getNxtRateNm() { return nxtRateNm; }
    public void setNxtRateNm(String v) { this.nxtRateNm = v; }
    public String getNxtPtrnRateYn() { return nxtPtrnRateYn; }
    public void setNxtPtrnRateYn(String v) { this.nxtPtrnRateYn = v; }
    public String getNxtPayClCd() { return nxtPayClCd; }
    public void setNxtPayClCd(String v) { this.nxtPayClCd = v; }
    public String getNxtDataType() { return nxtDataType; }
    public void setNxtDataType(String v) { this.nxtDataType = v; }
    public String getNxtRateType() { return nxtRateType; }
    public void setNxtRateType(String v) { this.nxtRateType = v; }
    public String getNxtRmk() { return nxtRmk; }
    public void setNxtRmk(String v) { this.nxtRmk = v; }
    public int getNxtBaseAmt() { return nxtBaseAmt; }
    public void setNxtBaseAmt(int v) { this.nxtBaseAmt = v; }
    public String getNxtApplStrtDt() { return nxtApplStrtDt; }
    public void setNxtApplStrtDt(String v) { this.nxtApplStrtDt = v; }
    public String getNxtApplEndDt() { return nxtApplEndDt; }
    public void setNxtApplEndDt(String v) { this.nxtApplEndDt = v; }
    public int getBaseVatAmt() { return baseVatAmt; }
    public void setBaseVatAmt(int v) { this.baseVatAmt = v; }
    public String getAppStartDd() { return appStartDd; }
    public void setAppStartDd(String v) { this.appStartDd = v; }
}
