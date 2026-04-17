package com.ktmmobile.msf.domains.form.form.common.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.common.dto.*;
import com.ktmmobile.msf.domains.form.form.common.service.ProductInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class ProductController {

    private final ProductInfoService productInfoService;

    //판매정책조회
    @PostMapping("/phone/saleplcy/list")
    public CommonResponse<List<MspSalePlcyMstInfoDto>> getMspSalePlcyMstList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getMspSalePlcyMstList(condition));
    }

    //할인유형
    @PostMapping("/phone/saletype/list")
    public CommonResponse<List<MspSalePlcyMstInfoDto>> getSaleTypeList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getSaleTypeList(condition));
    }

    //요금 약정기간조회
    @PostMapping("/rate/engg/list")
    public CommonResponse<List<MspSaleAgrmMstInfoDto>> getMspSaleAgrmMstList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getMspSaleAgrmMstList(condition));
    }

    //휴대폰 할부기간 조회
    @PostMapping("/phone/monthly/list")
    public CommonResponse<List<PhoneInfoDto>> getModelMonthlyList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getModelMonthlyList(condition));
    }

    //휴대폰 색상 조회
    @PostMapping("/phone/color/list")
    public CommonResponse<List<PhoneInfoDto>> getPrdtColorList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getPrdtColorList(condition));
    }

    //휴대폰 용량 조회
    @PostMapping("/phone/capacity/list")
    public CommonResponse<List<PhoneInfoDto>> getPrdtCapacityList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getPrdtCapacityList(condition));
    }

    //휴대폰 매장 재고 조회 (postgre)
    @PostMapping("/phone/inventory/list")
    public CommonResponse<List<ProductCategoryDto>> getPhoneInventoryList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getPhoneInventoryList(condition));
    }

    //휴대폰 목록 조회
    @PostMapping("/phone/list")
    public CommonResponse<List<PhoneInfoDto>> getPhoneList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getPhoneList(condition));
    }

    //요금제 카테고리 목록 조회
    @PostMapping("/rate/category/list")
    public CommonResponse<List<ProductCategoryDto>> getProductCategoryList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getProductCategoryList(condition));
    }

    //요금제 카테고리 상세 조회
    @PostMapping("/rate/categorydetail/list")
    public CommonResponse<List<ProductCategoryDto>> getProductCategoryDetailList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getProductCategoryDetailList(condition));
    }

    //요금제 목록 조회
    @PostMapping("/rate/list")
    public CommonResponse<List<RateInfoDto>> getRateList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getRateList(condition));
    }

    //공시지원금 조회 (출고가, 판매가 포함)
    @PostMapping("/phone/subsdamt")
    public CommonResponse<List<PhoneInfoDto>> getMspOfficialNoticeSupport(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getMspOfficialNoticeSupport(condition));
    }

    //제휴요금제 정보 조회 (요금제 목록 조회에는 distinct 가 있어서 일단 따로~)
    @PostMapping("/rate/jehuinfo/list")
    public CommonResponse<List<RateInfoDto>> getJehuInfoList(@RequestBody @Valid ProductSearchCondition condition) {
        return ResponseUtils.ok(productInfoService.getJehuInfoList(condition));
    }

    //부가서비스 목록 조회
    //@PostMapping("/addition/list")
    // S101030106 신규/변경 신청서 > 부가서비스 추가/삭제
    // 부가서비스 목록 조회시 스마트신청서 제공 대상만 세팅하도록 해주세요.
    // -- 스마트신청서 제공 대상만 세팅
    // AND RATE_CD IN (SELECT DTL_CD FROM smartform.MSF_CD_DTL
    //     WHERE CD_GROUP_ID = 'SVCCHG_RATE_CD'
    //     AND USE_YN = 'Y'
    //     AND TO_CHAR(NOW(),'yyyymmddhh24miss') BETWEEN PSTNG_START_DATE AND PSTNG_END_DATE
    //                  )

}
