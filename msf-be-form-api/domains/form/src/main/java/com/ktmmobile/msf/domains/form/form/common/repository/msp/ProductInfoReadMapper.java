package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestAdditionRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspAdditionDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleAgrmMstInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSalePlcyMstInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.ProductInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.RateInfoDto;

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
    List<PhoneInfoResponse> selectPhoneList(ProductInfoRequest condition);

    //요금제 목록 조회
    List<RateInfoDto> selectRateList(ProductInfoRequest condition);

    //가격정보조회 (단말, 요금, 지원금)
    MspSaleSubsdMstResponse selectMspSaleSubsdMst(MspSaleSubsdMstRequest condition);

    //가격정보조회 (가입비, 유심)
    PriceJoinUsimResponse selectJoinUsimPrice(PriceJoinUsimRequest condition);


    //부가서비스 상세조회
    List<MspAdditionDto> selectMsfAdditionList(MsfRequestAdditionRequest condition);

}

