package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionResponse {

    String subscriptionRestrictionsYn; //가입제한
    String subscriptionLimitYn; //가입한도
    String unPaidYn; //미납
    String historyOfCancellationYn; //상습해지이력
    String installmentDiscountYn; //할부할인


    //String yearActCnt; //1년이내 사용회선 건수
    //String yearCanCnt; //1년이내 해지 건수
    //String thisMonthActCnt; //당월 개통 회선
    //String delinqStatusCnt; //미납조회 건수
    //String totActCnt; //전체 개통 회선
}
