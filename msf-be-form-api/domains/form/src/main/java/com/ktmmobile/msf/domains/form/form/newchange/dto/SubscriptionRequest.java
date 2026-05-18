package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.code.CstmrType;

/**
 * 가입조건조회 Request
 */
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionRequest {

    CstmrType cstmrTypeCd; //고객유형코드
    String customerSsn; //식별번호

    //String drivrLicnsNo; //운전면허증 번호
    //String taxId; //사업자 번호

}
