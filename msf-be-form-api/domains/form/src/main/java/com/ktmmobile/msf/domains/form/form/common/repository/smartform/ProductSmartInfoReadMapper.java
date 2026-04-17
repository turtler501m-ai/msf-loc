package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import com.ktmmobile.msf.domains.form.form.common.dto.ProductCategoryDto;
import com.ktmmobile.msf.domains.form.form.common.dto.ProductSearchCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSmartInfoReadMapper {

    //요금제/부가서비스/안심보험 카테고리 목록 조회
    List<ProductCategoryDto> selectProductCategoryList(ProductSearchCondition condition);

    //요금제/부가서비스/안심보험 카테고리 상세 조회
    List<ProductCategoryDto> selectProductCategoryDetailList(ProductSearchCondition condition);

    //휴대폰 매장 재고 조회
    //List<PhoneInfoDto> selectPhoneInventoryList(ProductSearchCondition condition);
    List<ProductCategoryDto> selectPhoneInventoryList(ProductSearchCondition condition);

}
