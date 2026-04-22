package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestAdditionRequest {
    private String formTypeCd; //신청서 구분코드
    private String prodCtgTypeCd; //상품 카테고리 코드 (R:부가서비스)
    //private List<MspAdditionDto> listMspAdditionDto; //
    private List<ProductCategoryDto> listProductCategoryDto;
}
