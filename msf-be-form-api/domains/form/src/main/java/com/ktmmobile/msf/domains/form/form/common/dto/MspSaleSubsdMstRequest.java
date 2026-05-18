package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 가격정보조회 (단말, 요금, 지원금, 가입비, 유심비 등) Request
 */
@Getter
@Setter
@NoArgsConstructor
public class MspSaleSubsdMstRequest {

    //단말/요금/지원금 조회 Request
    private String oldYn = "N"; //중고여부 >> 고정값
    private String orgnId; //조직코드 : 1100011741 >> 세션정보
    private String reqBuyTypeCd; //상품유형 : 휴대폰(MM) / USIM(UU) >> 없을 경우 MM 으로 고정
    private String operTypeCd; //가입유형 (MNP3, NAC3, HDN3)
    private String salePlcyCd; //판매정책코드
    private String sprtTp; //할인유형 (단말 KD, 요금 PM)
    private String prdtId; //상품아이디 : K7028268
    private String rateCd; //요금제코드 : Pl198G409
    private String agrmTrm; //요금 약정기간
    private String prdtSctnCd; //데이타유형(판매정책 테이블정보) : 3G / LTE / 5G / LTE5G
    private String dataType; //요금제 조회에서 전달되는 DATA_TYPE 으로 LTE / 5G 등이 전달됨. LTE5G 는 없음.(참고)

    private String instAmt; //할부원금 >> 총할부수수료 산출을 위해 지원금조회에서 Response 받은 걸로 내부적으로 사용
    private String modelMonthly; //단말할부기간 >> 총할부수수료 산출을 위해 지원금조회에서 Response 받은 걸로 내부적으로 사용

    //가입비 및 유심비 조회 Request
    //String orgnId;
    //String reqBuyTypeCd;
    //String operTypeCd;
    //private String dataType; //요금제 조회에서 전달되는 DATA_TYPE 으로 LTE / 5G 등이 전달됨. LTE5G 는 없음.(참고)
    private String usimKindsCd; //유심종류 (06: 해당없음, 02: LTE유심, 07: 5G유심, 09: eSIM
    private String priceGubun; //업무구분(operTypeCd)+데이타유형(dataType) 을 쿼리에서 사용하기 위함. (내부적인 parameter)

}
