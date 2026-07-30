package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PricePlanReqDto {

    private String ncn; // 서비스 계약번호
    private String ctn; // 전화번호
    private String custId; // 고객번호
    private String parentScanId;

    // 변경 예약
    private String planSoc; // 상품코드
    private String planFtrNewParam; // 부가파람정보
}
