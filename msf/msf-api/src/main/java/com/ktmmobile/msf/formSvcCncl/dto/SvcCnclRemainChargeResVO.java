package com.ktmmobile.msf.formSvcCncl.dto;

import java.util.List;

/**
 * 서비스해지 잔여요금/위약금 조회 응답 VO.
 * X18 실시간요금조회: 사용요금(remainCharge)
 * X54 스폰서조회 + X62 심플할인: 위약금(penalty) — trmnForecBprmsAmt + ppPenlt 합산
 * Y37 잔여단말기대금: 삭제_미사용(확정) → installmentRemain null
 */
public class SvcCnclRemainChargeResVO {

    private boolean success;
    private String message;

    /** X18 조회 날짜 (현재 날짜) */
    private String searchDay;
    /** X18 조회 기간 (현재 월 1일 ~ 현재 날짜) */
    private String searchTime;
    /** 당월요금계 포맷 문자열 (예: "55,000 원") */
    private String sumAmt;
    /** X18 요금 항목 목록 */
    private List<FareItemDto> items;

    /** 사용요금 숫자값 — X18 sumAmt 파싱 (프론트 폼 자동입력용) */
    private Long remainCharge;
    /** 위약금 (원) — X54.trmnForecBprmsAmt + X62.ppPenlt 합산 */
    private Long penalty;
    /** 잔여단말기대금 (원) — Y37 삭제_미사용(확정): null */
    private Long installmentRemain;

    public static SvcCnclRemainChargeResVO empty() {
        SvcCnclRemainChargeResVO vo = new SvcCnclRemainChargeResVO();
        vo.success = true;
        vo.message = "";
        return vo;
    }

    public static SvcCnclRemainChargeResVO fail(String message) {
        SvcCnclRemainChargeResVO vo = new SvcCnclRemainChargeResVO();
        vo.success = false;
        vo.message = message;
        return vo;
    }

    /**
     * X18 요금 항목 DTO. ASIS RealFareVO와 동일 구조.
     * gubun: 요금항목명 (예: 월정액, 당월요금계)
     * payment: 금액 문자열 (예: "55,000 원")
     */
    public static class FareItemDto {
        private String gubun;
        private String payment;

        public FareItemDto() {}
        public FareItemDto(String gubun, String payment) {
            this.gubun = gubun;
            this.payment = payment;
        }

        public String getGubun()            { return gubun; }
        public void setGubun(String gubun)  { this.gubun = gubun; }
        public String getPayment()              { return payment; }
        public void setPayment(String payment)  { this.payment = payment; }
    }

    public boolean isSuccess()                          { return success; }
    public void setSuccess(boolean success)             { this.success = success; }
    public String getMessage()                          { return message; }
    public void setMessage(String message)              { this.message = message; }
    public String getSearchDay()                        { return searchDay; }
    public void setSearchDay(String searchDay)          { this.searchDay = searchDay; }
    public String getSearchTime()                       { return searchTime; }
    public void setSearchTime(String searchTime)        { this.searchTime = searchTime; }
    public String getSumAmt()                           { return sumAmt; }
    public void setSumAmt(String sumAmt)                { this.sumAmt = sumAmt; }
    public List<FareItemDto> getItems()                 { return items; }
    public void setItems(List<FareItemDto> items)       { this.items = items; }
    public Long getRemainCharge()                       { return remainCharge; }
    public void setRemainCharge(Long remainCharge)      { this.remainCharge = remainCharge; }
    public Long getPenalty()                            { return penalty; }
    public void setPenalty(Long penalty)                { this.penalty = penalty; }
    public Long getInstallmentRemain()                  { return installmentRemain; }
    public void setInstallmentRemain(Long v)            { this.installmentRemain = v; }
}
