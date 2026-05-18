package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신규/변경 최초 진입에 대한 기본값 설정 Response
 */
@Getter
@Setter
@NoArgsConstructor
public class NewChangeDefaultResponse {

    String prodId;
    String prodNm;
    String reqModelNm;
    String sntyCapacCd;
    String sntyCapacNm;
    String sntyColorCd;
    String sntyColorNm;
    String reqModelColor;
    String modelSalePolicyCd;
    String modelId;
    String modelMonthly;
    String sprtTypeCd;
    Long enggMnthCnt;
    String socCode;
    String socNm;
}
