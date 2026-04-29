package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import com.ktmmobile.msf.domains.form.form.common.dto.CategoryInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryRelRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSerialRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSmartInfoReadMapper {

    //요금제/부가서비스/안심보험 카테고리 목록 조회
    List<CategoryInfoDto> selectProductCategoryList(CategoryMstRequest condition);

    //요금제/부가서비스/안심보험 카테고리 상세 조회
    List<CategoryInfoDto> selectProductCategoryDetailList(CategoryRelRequest condition);

    //휴대폰 매장 재고 조회 : return >> prod_id 목록
    //List<PhoneInfoDto> selectPhoneInventoryList(ProductSearchCondition condition);
    List<CategoryInfoDto> selectPhoneInventoryList(PhoneSerialRequest condition);

    //휴대폰 매장재고 단건 조회 : return >> imei
    String selectPhoneInventory(PhoneSerialRequest condition);

}
