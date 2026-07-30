package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 가입조건조회 Response
 */
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionResponse {

    String subscriptionRestrictionsYn; //가입제한 조회 결과
    String subscriptionRestrictionsResultMessage; //가입제한 조회 결과 메세지
    String subscriptionLimitYn; //가입한도 조회 결과
    String subscriptionLimitResultMessage; //가입한도 조회 결과 메세지
    String unPaidYn; //미납 조회 결과
    String unPaidResultMessage; //미납 조회 결과 메세지
    String historyOfCancellationYn; //상습해지이력 조회 결과
    String historyOfCancellationResultMessage; //상습해지이력 조회 결과 메세지
    String installmentDiscountYn; //할부할인 조회 결과
    String installmentDiscountResultMessage; //할부할인 조회 결과 메세지


    int yearActCnt; //1년이내 사용회선 건수
    int yearCanCnt; //1년이내 해지 건수
    int thisMonthActCnt; //당월 개통 회선
    int delinqStatusCnt; //미납조회 건수
    int totActCnt; //전체 개통 회선
}
