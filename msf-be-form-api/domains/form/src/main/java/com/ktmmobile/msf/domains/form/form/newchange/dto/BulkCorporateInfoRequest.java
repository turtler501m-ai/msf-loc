package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BulkCorporateInfoRequest {

    private String operTypeCd; //가입유형
    private String cstmrTypeCd; //고객유형
    private String agentCd; //화면에서 선택한 대리점코드
    private String cpntId; //로그인한 사용자의 판매점코드

    private Integer volumeMobileNoQnty = 0; //화면에서 입력한 대량개통건수

    private String useStartDate;
    private String useEndDate;

}
