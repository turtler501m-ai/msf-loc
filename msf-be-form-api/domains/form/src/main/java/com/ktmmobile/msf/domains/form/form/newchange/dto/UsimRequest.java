package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsimRequest {

    String iccId;
    String agentCd; //선택한 대리점코드
    String storCd; //로그인 세션의 매장코드

    String orgnId; //안쓸것임
}
