package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MspJuoBanInfoResponse {
    //String customerType;
    String ban; //청구계정아이디
    String customerId; //고객아이디
    String contractNum; //계약번호
    String blBillingMethod; //요금납부구분

}
