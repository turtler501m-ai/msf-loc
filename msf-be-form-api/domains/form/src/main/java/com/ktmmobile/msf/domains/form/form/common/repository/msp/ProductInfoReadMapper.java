package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import com.ktmmobile.msf.domains.form.form.common.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoReadMapper {

    //판매정책조회
    List<MspSalePlcyMstInfoDto> selectMspSalePlcyMstList(ProductSearchCondition condition);

    //할인유형조회
    List<MspSalePlcyMstInfoDto> selectSaleTypeList(ProductSearchCondition condition);

    //요금 약정기간조회
    List<MspSaleAgrmMstInfoDto> selectMspSaleAgrmMstList(ProductSearchCondition condition);

    //휴대폰 할부기간조회
    List<PhoneInfoDto> selectModelMonthlyList(ProductSearchCondition condition);

    //휴대폰 색상목록조회
    List<PhoneInfoDto> selectPrdtColorList(ProductSearchCondition condition);

    //휴대폰 용량목록조회
    List<PhoneInfoDto> selectPrdtCapacityList(ProductSearchCondition condition);

    //휴대폰 목록 조회
    List<PhoneInfoDto> selectPhoneList(ProductSearchCondition condition);

    //요금제 목록 조회
    List<RateInfoDto> selectRateList(ProductSearchCondition condition);

    //공시지원금 조회
    List<PhoneInfoDto> selectMspOfficialNoticeSupport(ProductSearchCondition condition);
    // List<RateInfoDto> listMspOfficialNoticeSupport(CommonSearchCondition condition);

    //부가서비스 상세조회
    List<MspAdditionDto> selectMsfAdditionList(MsfRequestAdditionRequest condition);

}

