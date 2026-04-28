package com.ktmmobile.msf.domains.form.form.common.dto;

import com.ktmmobile.msf.domains.form.common.code.CategoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryMstRequest {

    private CategoryType prodCtgTypeCd; //카테고리 구분코드 (P , R, I)

    //private String prodCtgId;

    private List<String> prodCtgId;

    //private List<String> prodCtgId;
    //private List<String> prodCtgIdList;

    //private List<String> prodCtgIdList;

    //private List<String> prodCtgId;

    //List<String> prodCtgIds;

    //private List<Map<String, Object>> productCategoryList;


    //@NotBlank
    //private String prodCtgId; //
    //private String ctgNm; //카테고리 구분값 (요금제, 부가서비스, 안심보험)
    //private String prodId; //상품코드 (요금제, 부가서비스, 안심보험)
}
