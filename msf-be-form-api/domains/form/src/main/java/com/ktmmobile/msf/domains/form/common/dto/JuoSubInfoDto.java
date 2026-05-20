package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JuoSubInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contractNum;         // 가입계약번호
    private String customerSsn;         // 주민등록번호
    private String customerLinkName;    // 고객명
    private String subscriberNo;        // 전화번호_이전CTN
    private String lstComActvDate;      // 최초 개통일자
    private String iccId;               // USIM 카드 일련번호
    private String dvcChgYn;            // 기변 여부
    private String gender;              // 성별
    private String customerId;          // CUSTOMER_ID
    private String customerType;
    /*2025-01-21 다이렉트몰 회원 가입 및 정보 변경시 본인인증 강화 */
    private String legalCi;             // 법정대리인 Ci
    private String lglAgntSsn;          // 법정대리인 주민번호

}
