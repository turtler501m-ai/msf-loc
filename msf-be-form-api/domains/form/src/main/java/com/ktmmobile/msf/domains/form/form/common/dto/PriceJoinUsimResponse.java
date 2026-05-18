package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 가격정보조회 중 가입비 및 유심비 조회용 Response
 */
@Getter
@Setter
@NoArgsConstructor
public class PriceJoinUsimResponse {

    String joinPrice; //가입비
    String joinIsPay; //가입비 납부여부 ( Y:납부, N:면제 )
    String simPrice; //유심가격
    String simIsPay; //일반유심비용 납부여부 ( Y:납부, N:면제 )
    String nfcSimIsPay; //NFC유심비용 납부여부 ( Y:납부, N:면제 )

}
