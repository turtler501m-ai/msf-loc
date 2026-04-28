package com.ktmmobile.msf.domains.form.form.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRelRequest {
    //private String ctgCd; //카테고리 구분코드 (P , R, I)
    //private String ctgNm; //카테고리 구분값 (요금제, 부가서비스, 안심보험)
    //private String prodId; //상품코드 (요금제, 부가서비스, 안심보험)

    @NotBlank
    private String prodCtgTypeCd; //상품관리의 카테고리별 구분코드 (P , R, I)
    @NotBlank
    private String prodCtgId; //카테고리 구분코드(P, R, I) 별 상품코드 (ex. RFREESVC, I000001 )
}
