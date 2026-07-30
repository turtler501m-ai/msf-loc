package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BulkCorporateInfoResponse {

    private String canBulkCorporateConditionYn; //대량 법인 개통 가입조건 가능여부
    private String canBulkCorporateOpenYn; //대량 법인 개통 대상여부

    private Integer sbscLmtQnty; //대량 법인 개통 가능건수 (DB 입력값)
    private Integer openCount; //실제 개통건수
    private Integer completeCount; //실제 작성건수
    private Integer limitCount;
    //private int totalCnt;

    private String useStartDate;
    private String useEndDate;

    //private Integer limitCount;

}
