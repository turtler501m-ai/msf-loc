package com.ktmmobile.msf.domains.form.form.termination.dto;

import java.util.List;

public class TerminationRemainChargeResVO {
    private boolean success;
    private String message;
    private String searchDay;   // 조회 날짜
    private String searchTime;  // 조회 기간
    private String sumAmt;      // 당월요금계
    private List<FareItem> items;

    /** requestView 위약금 블록 데이터 (X54/X16/mspAddInfo) */
    private String initActivationDate; // 개통일자(lst_com_actv_date)
    private String lstComActvDate;     // 최초개통일자(lst_com_actv_date)
    private TerminationSettlementDto settlement;

    private String penaltyFee;     // 위약금 (trmnForecBprmsAmt)
    private String settlementFee;  // 정산요금 (rtrnAmtAndChageDcAmt)
    private String remainPeriod;   // 상환기간 (잔여 할부개월, remainMonth)
    private String remainAmount;   // 금액 (잔여 할부금액, remainPay)

    public static class FareItem {
        private String gubun;    // 요금 항목명
        private String payment;  // 요금 금액

        public String getGubun() {
            return gubun;
        }

        public void setGubun(String gubun) {
            this.gubun = gubun;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSearchDay() {
        return searchDay;
    }

    public void setSearchDay(String searchDay) {
        this.searchDay = searchDay;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public String getSumAmt() {
        return sumAmt;
    }

    public void setSumAmt(String sumAmt) {
        this.sumAmt = sumAmt;
    }

    public List<FareItem> getItems() {
        return items;
    }

    public void setItems(List<FareItem> items) {
        this.items = items;
    }

    public String getInitActivationDate() {
        return initActivationDate;
    }

    public void setInitActivationDate(String initActivationDate) {
        this.initActivationDate = initActivationDate;
    }

    public String getLstComActvDate() {
        return lstComActvDate;
    }

    public void setLstComActvDate(String lstComActvDate) {
        this.lstComActvDate = lstComActvDate;
    }

    public TerminationSettlementDto getSettlement() {
        return settlement;
    }

    public void setSettlement(TerminationSettlementDto settlement) {
        this.settlement = settlement;
    }

    public String getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(String penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public String getSettlementFee() {
        return settlementFee;
    }

    public void setSettlementFee(String settlementFee) {
        this.settlementFee = settlementFee;
    }

    public String getRemainPeriod() {
        return remainPeriod;
    }

    public void setRemainPeriod(String remainPeriod) {
        this.remainPeriod = remainPeriod;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }
}
