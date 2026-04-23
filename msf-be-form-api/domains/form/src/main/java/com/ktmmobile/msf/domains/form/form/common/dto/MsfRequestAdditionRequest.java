package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestAdditionRequest {
    private String operTypeCd; //업무구분코드 NAC3 / MNP3 / HDN3
    private String prodCtgTypeCd; //상품 카테고리 코드 (R:부가서비스)
    private String prodCtgId; //상품 카테고리 코드의 매핑되는 여러개의 아이디 중 하나
    private List<ProductCategoryDto> listProductCategoryDto;

    //private List<MspAdditionDto> listMspAdditionDto; //
    //private List<Map<String, Object>> prodCtgIdList;
}
