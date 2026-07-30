package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 요금조회
 */
@Getter
@NoArgsConstructor
public class RateInfoResponse {

    private String salePlcyCd; //정책코드
    private String rateCd; //요금제코드
    //private String socCode;
    private String rateNm; //요금제명
    //private String socNm;
    private String serviceType; //서비스유형
    //private String cmnt; //요금제혜택
    private String jehuProdType; //요금제제휴처
    private String jehuProdNm; //요금제제휴처명
    private String sprtTp; /* 할인유형 */
    private String plcySctnCd; /* 정책구분코드 01:단말, 02:USIM */
    private String prdtSctnCd; /* 제품구분코드  LTE5G, 5G, 3G, LTE */
    private String dataType; /* 데이타 유형 : 5G / 3G LTE */
    private String baseAmt; //
    private String baseAmtVat;
    private String baseAmtWithVat; //
}
