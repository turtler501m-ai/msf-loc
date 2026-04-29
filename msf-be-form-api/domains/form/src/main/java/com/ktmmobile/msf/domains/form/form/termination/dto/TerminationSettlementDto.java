package com.ktmmobile.msf.domains.form.form.termination.dto;

/**
 * requestView 위약금 정산 블록 데이터.
 * X54(스폰서/위약금), X16(잔여 할부금), mspAddInfo(할부원금) 결과를 담는다.
 */
public class TerminationSettlementDto {
    private boolean prePayment;             // 선불 요금제 여부
    private String saleEngtNm;              // 스폰서 유형명
    private String saleEngtOptnCd;          // 스폰서 유형 옵션 코드 (KD/PM)
    private String trmnForecBprmsAmt;       // 예상 위약금
    private String chageDcAmt;              // 요금할인(월)
    private String rtrnAmtAndChageDcAmt;    // 반환금(요금할인)
    private String chageDcAmtSuprtRtrnAmt;  // 요금할인(지원금) 반환금
    private String ktSuprtPenltAmt;         // 위약금(공시지원금)
    private String storSuprtPenltAmt;       // 위약금(추가지원금)
    private String engtAplyStDate;          // 가입일
    private String engtExpirPamDate;        // 만료예정일
    private String engtRmndDate;            // 잔여약정기간
    private String installmentAmt;          // 잔여 할부 금액
    private String totalNoOfInstall;        // 잔여 개월
    private String installmentYN;           // 할부 여부
    private int instOrginAmnt;              // 할부원금
    private int instMnthCnt;                // 할부개월수
    private int remainPay;                  // 잔여 할부금액
    private int remainMonth;                // 잔여 할부개월
    private String modelName;               // 단말기 모델명

    public boolean isPrePayment() { return prePayment; }
    public void setPrePayment(boolean prePayment) { this.prePayment = prePayment; }
    public String getSaleEngtNm() { return saleEngtNm; }
    public void setSaleEngtNm(String saleEngtNm) { this.saleEngtNm = saleEngtNm; }
    public String getSaleEngtOptnCd() { return saleEngtOptnCd; }
    public void setSaleEngtOptnCd(String saleEngtOptnCd) { this.saleEngtOptnCd = saleEngtOptnCd; }
    public String getTrmnForecBprmsAmt() { return trmnForecBprmsAmt; }
    public void setTrmnForecBprmsAmt(String trmnForecBprmsAmt) { this.trmnForecBprmsAmt = trmnForecBprmsAmt; }
    public String getChageDcAmt() { return chageDcAmt; }
    public void setChageDcAmt(String chageDcAmt) { this.chageDcAmt = chageDcAmt; }
    public String getRtrnAmtAndChageDcAmt() { return rtrnAmtAndChageDcAmt; }
    public void setRtrnAmtAndChageDcAmt(String rtrnAmtAndChageDcAmt) { this.rtrnAmtAndChageDcAmt = rtrnAmtAndChageDcAmt; }
    public String getChageDcAmtSuprtRtrnAmt() { return chageDcAmtSuprtRtrnAmt; }
    public void setChageDcAmtSuprtRtrnAmt(String chageDcAmtSuprtRtrnAmt) { this.chageDcAmtSuprtRtrnAmt = chageDcAmtSuprtRtrnAmt; }
    public String getKtSuprtPenltAmt() { return ktSuprtPenltAmt; }
    public void setKtSuprtPenltAmt(String ktSuprtPenltAmt) { this.ktSuprtPenltAmt = ktSuprtPenltAmt; }
    public String getStorSuprtPenltAmt() { return storSuprtPenltAmt; }
    public void setStorSuprtPenltAmt(String storSuprtPenltAmt) { this.storSuprtPenltAmt = storSuprtPenltAmt; }
    public String getEngtAplyStDate() { return engtAplyStDate; }
    public void setEngtAplyStDate(String engtAplyStDate) { this.engtAplyStDate = engtAplyStDate; }
    public String getEngtExpirPamDate() { return engtExpirPamDate; }
    public void setEngtExpirPamDate(String engtExpirPamDate) { this.engtExpirPamDate = engtExpirPamDate; }
    public String getEngtRmndDate() { return engtRmndDate; }
    public void setEngtRmndDate(String engtRmndDate) { this.engtRmndDate = engtRmndDate; }
    public String getInstallmentAmt() { return installmentAmt; }
    public void setInstallmentAmt(String installmentAmt) { this.installmentAmt = installmentAmt; }
    public String getTotalNoOfInstall() { return totalNoOfInstall; }
    public void setTotalNoOfInstall(String totalNoOfInstall) { this.totalNoOfInstall = totalNoOfInstall; }
    public String getInstallmentYN() { return installmentYN; }
    public void setInstallmentYN(String installmentYN) { this.installmentYN = installmentYN; }
    public int getInstOrginAmnt() { return instOrginAmnt; }
    public void setInstOrginAmnt(int instOrginAmnt) { this.instOrginAmnt = instOrginAmnt; }
    public int getInstMnthCnt() { return instMnthCnt; }
    public void setInstMnthCnt(int instMnthCnt) { this.instMnthCnt = instMnthCnt; }
    public int getRemainPay() { return remainPay; }
    public void setRemainPay(int remainPay) { this.remainPay = remainPay; }
    public int getRemainMonth() { return remainMonth; }
    public void setRemainMonth(int remainMonth) { this.remainMonth = remainMonth; }
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
}
