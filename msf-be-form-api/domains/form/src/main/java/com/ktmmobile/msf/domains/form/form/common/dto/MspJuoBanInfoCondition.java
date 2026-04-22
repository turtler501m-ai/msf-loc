package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspJuoBanInfoCondition {
    private String cstmrTypeCd; //고객유형 (작성 시 받아온 것)
    private String customerType; //고객유형 (I/B/G)
    private String customerSsn; //고객식별번호
    private String customerLinkName; //고객명
    private String subscriberNo; //핸드폰번호
    private String ban;

}
