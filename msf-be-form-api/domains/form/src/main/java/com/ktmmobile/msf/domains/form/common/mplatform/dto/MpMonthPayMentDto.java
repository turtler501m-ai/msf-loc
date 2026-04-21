package com.ktmmobile.msf.domains.form.common.mplatform.dto;


public class MpMonthPayMentDto {
    private String billSeqNo;//청구일련번호
    private String billStartDate;//요금사용시작일
    private String billEndDate;//요금사용종료일
    private String billMonth ;// 청구년월
    private String searchBillMonth ;// 청구년월
    private String thisMonth ;// 당월요금
    private String pastDueAmt ;// 미납요금
    private String totalDueAmt ;// 청구총액
    private String billDueDateList ;// 전체청구년월
    private int month;

    public String getSearchBillMonth() {
        return searchBillMonth;
    }
    public void setSearchBillMonth(String searchBillMonth) {
        this.searchBillMonth = searchBillMonth;
    }
    public String getBillSeqNo() {
        return billSeqNo;
    }
    public void setBillSeqNo(String billSeqNo) {
        this.billSeqNo = billSeqNo;
    }
    public String getBillStartDate() {
        return billStartDate;
    }
    public void setBillStartDate(String billStartDate) {
        this.billStartDate = billStartDate;
    }
    public String getBillEndDate() {
        return billEndDate;
    }
    public void setBillEndDate(String billEndDate) {
        this.billEndDate = billEndDate;
    }
    public String getBillMonth() {
        return billMonth;
    }
    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }
    public String getThisMonth() {
        return thisMonth;
    }
    public void setThisMonth(String thisMonth) {
        this.thisMonth = thisMonth;
    }
    public String getPastDueAmt() {
        return pastDueAmt;
    }
    public void setPastDueAmt(String pastDueAmt) {
        this.pastDueAmt = pastDueAmt;
    }
    public String getTotalDueAmt() {
        return totalDueAmt;
    }
    public void setTotalDueAmt(String totalDueAmt) {
        this.totalDueAmt = totalDueAmt;
    }
    public String getBillDueDateList() {
        return billDueDateList;
    }
    public void setBillDueDateList(String billDueDateList) {
        this.billDueDateList = billDueDateList;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getPreMonth() {
       if (month == 1) {
           return 12;
       } else {
           return month-1;
       }
    }

}
