package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.dto.CategoryInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryRelRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;

@Mapper
public interface ProductSmartInfoReadMapper {

    //요금제/부가서비스/안심보험 카테고리 목록 조회
    List<CategoryInfoDto> selectProductCategoryList(CategoryMstRequest request);

    //요금제/부가서비스/안심보험 카테고리 상세 조회
    List<CategoryInfoDto> selectProductCategoryDetailList(CategoryRelRequest request);

    //휴대폰 매장 재고 조회 : return >> prod_id 목록
    //List<PhoneInfoDto> selectPhoneInventoryList(ProductSearchCondition condition);
    List<CategoryInfoDto> selectPhoneInventoryList(ProductInventoryRequest request);

    //휴대폰 매장재고 단건 조회 : return >> imei
    String selectPhoneInventory(ProductInventoryRequest request);

    //유심 매장재고 조회
    int selectPhoneInventoryCount(ProductInventoryRequest request);


    //RES_NO 확인
    //String selectResNo(long requestKey);

}
