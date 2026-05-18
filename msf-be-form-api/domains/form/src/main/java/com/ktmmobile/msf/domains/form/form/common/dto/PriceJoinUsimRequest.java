package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 가격정보조회 중 가입비 및 유심비 조회용 Request
 */
@Getter
@Setter
@NoArgsConstructor
public class PriceJoinUsimRequest {

    String orgnId; //조직코드
    String reqBuyTypeCd; //상품유형
    String operTypeCd; //가입유형 : NAC3 / MNP3 / HDN3 / HCN3
    String dataType; //요금제 조회에서 리턴된 데이터유형 LTE / 5G 등
    String usimKindsCd; //유심종류 : 유심 해당없음(06) , LTE일반유심(02) , 5G일반유심(08) , NFC유심(08) , eSIM(09)

    String priceGubun; //업무구분+데이타유형 >> 실제 쿼리에서 사용하는 parameter
}
