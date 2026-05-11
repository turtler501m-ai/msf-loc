package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceJoinUsimRequest {

    String operTypeCd; //업무구분 : NAC3 / MNP3 / HDN3 / HCN3
    String prdtSctnCd; //데이타유형 : 3G / LTE / 5G / LTE5G
    String priceGubun; //업무구분+데이타유형 >> 실제 쿼리에서 사용하는 parameter
}
