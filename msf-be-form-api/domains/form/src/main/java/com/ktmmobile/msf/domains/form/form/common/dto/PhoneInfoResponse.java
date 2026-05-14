package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhoneInfoResponse {

    private String prodId; //상품아이디 (3308)
    private String prodNm; //상품명 (갤럭시 A32)
    private String modelId; //대표모델ID (K7004226)
    private String reqModelNm; //단말기명 (SM-A325NK)
    private String salePlcyCd; //판매정책코드 (N2022011018381)

}
