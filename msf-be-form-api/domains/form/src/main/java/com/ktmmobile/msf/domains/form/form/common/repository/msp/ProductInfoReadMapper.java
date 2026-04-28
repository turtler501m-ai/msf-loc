package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import com.ktmmobile.msf.domains.form.form.common.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoReadMapper {

    //판매정책조회
    List<MspSalePlcyMstInfoDto> selectMspSalePlcyMstList(ProductInfoRequest condition);

    //할인유형조회
    List<MspSalePlcyMstInfoDto> selectSaleTypeList(ProductInfoRequest condition);

    //요금 약정기간조회
    List<MspSaleAgrmMstInfoDto> selectMspSaleAgrmMstList(ProductInfoRequest condition);

    //휴대폰 할부기간조회
    List<PhoneInfoDto> selectModelMonthlyList(ProductInfoRequest condition);

    //휴대폰 색상목록조회
    List<PhoneInfoDto> selectPrdtColorList(ProductInfoRequest condition);

    //휴대폰 용량목록조회
    List<PhoneInfoDto> selectPrdtCapacityList(ProductInfoRequest condition);

    //휴대폰 목록 조회
    List<PhoneInfoDto> selectPhoneList(ProductInfoRequest condition);

    //요금제 목록 조회
    List<RateInfoDto> selectRateList(ProductInfoRequest condition);

    //공시지원금 조회
    List<PhoneInfoDto> selectMspOfficialNoticeSupport(ProductInfoRequest condition);
    // List<RateInfoDto> listMspOfficialNoticeSupport(CommonSearchCondition condition);

    //부가서비스 상세조회
    List<MspAdditionDto> selectMsfAdditionList(MsfRequestAdditionRequest condition);

}

