package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 휴대폰 일련번호 유효성체크 Request
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductInventoryRequest {

    //String searchType; //휴대폰일련번호(S) 또는 USIM(U)

    //MSF_PROD_STOR_INVENTORY_TXN
    private String storCd; //매장코드
    private String agentCd; //대리점코드 (request)
    private String prodId; //
    private String modelId; //단말코드
    private String prodSn; //휴대폰 일련번호 또는 유심 일련번호
    private String phoneModelId; //쓰지말자~

    //USIM 유효성체크에서 parameter
    private String iccId;

    private String useSttusCd; //사용관리 ( N : 미사용 / R : 접수완료 / A : 사용완료 )
    //
    private String uploadPhoneSrlNo; //eSIM 일 경우 eSIM 정보 저장된 테이블의 일련번호 : 해당 테이블에 휴대폰 일련번호를 업데이트 하기 위해서
}
