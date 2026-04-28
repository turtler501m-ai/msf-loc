package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestAdditionRequest {
    private String operTypeCd; //업무구분코드 NAC3 / MNP3 / HDN3 -> 있을수도 없을수도 있음?
    private String prodCtgTypeCd; //상품 카테고리 코드 (R:부가서비스) - 소스에 고정 할까?

    private String prodCtgId; //상품 카테고리 코드의 매핑되는 여러개의 아이디 중 하나

    private List<CategoryInfoDto> productCategoryInfoDtoList;
    //private List<ProductCategoryCodeRequest> productCategoryCodeRequestList;
    private List<CategoryMstRequest> categoryMstRequestList;

    private CategoryMstRequest categoryMstRequest;

    List<String> prodCtgIdList;


    //private List<MspAdditionDto> listMspAdditionDto; //
    //private List<Map<String, Object>> prodCtgIdList;
}
