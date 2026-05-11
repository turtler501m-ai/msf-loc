package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhoneInfoDto {

    private String prodId;
    private String prdtNm; //상품명
    private String modelId; //대표모델ID
    private String reqModelNm; //단말기명


    private String prodCtgId;
    private String rprsPrdtId;

    private String prodNm;
    private String modelMonthly;

    private String prdtId;
    //private String prdtNm;
    private String modelColorCd;
    private String modelColorNm;

    private String modelCapacityCd;
    private String modelCapacityNm;

    private String prdtIndCd;
    private String rateNm;
    private String subsdAmt;
    private String outUnitPric;
    private String pricAmt;

    private String saleTypeCd;
    private String saleTypeNm;


}
