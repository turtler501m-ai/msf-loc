package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsimRequest {

    private String iccId;
    private String agentCd; //선택한 대리점코드
    private String storCd; //로그인 세션의 매장코드
    private boolean hasSim; //유심보유여부

    private String orgnId; //안쓸것임

    private String dataType;
    private String soc;
}
