package com.ktmmobile.msf.domains.form.form.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.code.CategoryType;
import com.ktmmobile.msf.domains.form.common.code.ReqBuyType;

@Getter
@Setter
@NoArgsConstructor
public class CategoryMstRequest {

    private CategoryType prodCtgTypeCd; //카테고리 구분코드 (P , R, I)
    private List<String> prodCtgId;

    private String rateAdsvcDivCd; //카테고리 구분코드 (P , R, I) >> prodCtgTypeCd 에서 변경하자
    private List<String> ctgCd; // >> prodCtgId 에서 변경하자~

    private String rprsPrdtId; //단말코드

    private ReqBuyType reqBuyTypeCd = ReqBuyType.MOBILE; //상품유형 MM , UU - 요금제 조회에서 사용해야하여 추가됨.
    private String serviceChangeYn; // Y: 서비스변경은 ASIS 후보 산출 조건으로 추천카테고리 조회


    //private String prodCtgId;

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
