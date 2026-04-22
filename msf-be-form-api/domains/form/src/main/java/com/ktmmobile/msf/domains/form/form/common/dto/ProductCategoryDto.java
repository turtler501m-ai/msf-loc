package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCategoryDto {

    private String ctgCd; //카테고리 구분코드 (P , R, I)
    private String ctgNm; //카테고리 구분값 (요금제, 부가서비스, 안심보험)
    private String prodId; //상품코드 (요금제, 부가서비스, 안심보험)

    //private List<MspAdditionDto> listMspAdditionDto; //

}
