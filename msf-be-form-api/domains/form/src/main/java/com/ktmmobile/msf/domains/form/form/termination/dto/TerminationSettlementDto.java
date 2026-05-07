package com.ktmmobile.msf.domains.form.form.termination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * requestView 위약금 정산 블록 데이터.
 * X54(스폰서/위약금), X16(잔여 할부금), mspAddInfo(할부원금) 결과를 담는다.
 */
@Getter
@Setter
@NoArgsConstructor
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
}
