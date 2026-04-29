package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@JsonIgnoreProperties({"body"})
public class MspJuoSubInfoRequest {

    //가입계약번호
    private String contractNum;

    //주민등록번호/
    private String customerSsn;

    //고객명/
    private String customerLinkName;

    //전화번호_이전CTN
    private String subscriberNo;

    //최초 개통일자
    private String lstComActvDate;

    //USIM 카드 일련번호
    private String iccId;

    //기변 여부
    private String dvcChgYn;

    //성별
    private String gender;

    //고객아이디 : CUSTOMER_ID
    private String customerId;

    //고객유형
    private String customerType;

    /* 2025-01-21 다이렉트몰 회원 가입 및 정보 변경시 본인인증 강화 */
    //법정대리인 Ci
    private String legalCi;
    //법정대리인 주민번호
    private String lglAgntSsn;

}
