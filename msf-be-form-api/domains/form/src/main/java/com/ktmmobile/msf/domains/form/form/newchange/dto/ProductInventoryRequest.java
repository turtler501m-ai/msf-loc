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
    String storCd; //매장코드
    String agentCd; //대리점코드 (request)
    String prodId; //
    String modelId; //단말코드
    String prodSn; //휴대폰 일련번호 또는 유심 일련번호
    String phoneModelId; //쓰지말자~

    //USIM 유효성체크에서 parameter
    String iccId;
}
