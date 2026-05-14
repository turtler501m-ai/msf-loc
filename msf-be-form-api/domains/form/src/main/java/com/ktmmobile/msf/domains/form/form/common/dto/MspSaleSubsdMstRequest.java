package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspSaleSubsdMstRequest {

    private String reqBuyTypeCd; //구매유형? 상품유형?

    //휴대폰 - 단말/요금/지원금 조회
    private String salePlcyCd; //판매정책코드
    private String prdtId; //상품아이디 : K7028268
    private String rateCd; //요금제코드 : Pl198G409
    private String agrmTrm; //요금 약정기간
    private String sprtTp; //할인유형 (단말 KD, 요금 PM)
    private String operTypeCd; //가입유형 (MNP3, NAC3, HDN3)
    private String orgnId; //조직코드 : 1100011741 >> 세션정보
    private String oldYn = "N"; //중고여부 >> 고정값

    //가입비 및 유심비용
    //private String operTypeCd; //업무구분 : NAC3 / MNP3 / HDN3 / HCN3
    private String prdtSctnCd; //데이타유형 : 3G / LTE / 5G / LTE5G
    private String priceGubun; //업무구분+데이타유형 >> 실제 쿼리에서 사용하는 parameter

    private String instAmt; //할부원금
    private String modelMonthly; //단말할부기간

}
