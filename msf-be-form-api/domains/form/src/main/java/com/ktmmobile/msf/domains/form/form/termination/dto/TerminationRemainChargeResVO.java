package com.ktmmobile.msf.domains.form.form.termination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TerminationRemainChargeResVO {
    private String searchDay;   // 조회 날짜
    private String searchTime;  // 조회 기간
    private String sumAmt;      // 당월요금계
    private List<FareItem> items;

    private TerminationSettlementDto settlement;

    private String penaltyFee;     // 위약금 (trmnForecBprmsAmt)
    private String settlementFee;  // 정산요금 (rtrnAmtAndChageDcAmt)
    private String remainPeriod;   // 상환기간 (잔여 할부개월, remainMonth)
    private String remainAmount;   // 금액 (잔여 할부금액, remainPay)

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FareItem {
        private String gubun;    // 요금 항목명
        private String payment;  // 요금 금액
    }
}
