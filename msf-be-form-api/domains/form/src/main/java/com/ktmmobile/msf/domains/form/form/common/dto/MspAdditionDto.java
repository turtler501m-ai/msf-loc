package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 부가서비스 정보 조회
 */
@Getter
@Setter
@NoArgsConstructor
public class MspAdditionDto {

    private String rateCd;
    private String rateNm;
    private String baseAmt;
}
