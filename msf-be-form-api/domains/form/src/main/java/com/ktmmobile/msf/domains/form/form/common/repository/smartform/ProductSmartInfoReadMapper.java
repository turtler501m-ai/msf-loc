package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.dto.CategoryInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryRelRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;

@Mapper
public interface ProductSmartInfoReadMapper {

    //요금제/부가서비스/안심보험 카테고리 목록 조회
    List<CategoryInfoDto> selectProductCategoryList(CategoryMstRequest request);

    //요금제/부가서비스/안심보험 카테고리 상세 조회
    List<CategoryInfoDto> selectProductCategoryDetailList(CategoryRelRequest request);

    List<CategoryInfoDto> selectProductCategoryDetailList2(CategoryRelRequest request); //추후 삭제예정

    //휴대폰 매장 재고 조회 : return >> prod_id 목록
    List<CategoryInfoDto> selectPhoneInventoryList(ProductInventoryRequest request);
    //List<PhoneInfoDto> selectPhoneInventoryList(ProductSearchCondition condition);

    //휴대폰 매장재고 단건 조회 : return >> imei
    //String selectPhoneInventory(ProductInventoryRequest request);

    //유심/휴대폰 매장재고 조회
    int selectPhoneInventoryCount(ProductInventoryRequest request);

    //매장재고에서 상품 일련번호 상품아이디 추출
    String selectProdId(String prodSn);

    //2026.06.20
    List<CategoryInfoDto> selectCategoryCdList(CategoryInfoRequest categoryInfoRequest);

    //2026.06.20
    List<CategoryInfoDto> selectCategoryList(CategoryInfoRequest categoryInfoRequest);

}
