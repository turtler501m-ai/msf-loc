package com.ktmmobile.msf.domains.form.form.common.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
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
    public CommonResponse<List<MspSalePlcyMstInfoDto>> getMspSalePlcyMstList(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getMspSalePlcyMstList(condition));
    }

    //할인유형
    @PostMapping("/phone/saletype/list")
    public CommonResponse<List<MspSalePlcyMstInfoDto>> getSaleTypeList(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getSaleTypeList(condition));
    }

    //요금 약정기간조회
    @PostMapping("/rate/engg/list")
    public CommonResponse<List<MspSaleAgrmMstInfoDto>> getMspSaleAgrmMstList(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getMspSaleAgrmMstList(condition));
    }

    //휴대폰 할부기간 조회
    @PostMapping("/phone/monthly/list")
    public CommonResponse<List<PhoneInfoDto>> getModelMonthlyList(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getModelMonthlyList(condition));
    }

    //휴대폰 색상 조회
    @PostMapping("/phone/color/list")
    public CommonResponse<List<PhoneInfoDto>> getPrdtColorList(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getPrdtColorList(condition));
    }

    //휴대폰 용량 조회
    @PostMapping("/phone/capacity/list")
    public CommonResponse<List<PhoneInfoDto>> getPrdtCapacityList(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getPrdtCapacityList(condition));
    }

    //휴대폰 매장 재고 조회 (postgre)
    @PostMapping("/phone/inventory/list")
    public CommonResponse<List<CategoryInfoDto>> getPhoneInventoryList(@RequestBody @Valid PhoneSerialRequest condition) {
        return ResponseUtils.ok(productInfoService.getPhoneInventoryList(condition));
    }

    //휴대폰 목록 조회
    @PostMapping("/phone/list")
    public CommonResponse<List<PhoneInfoDto>> getPhoneList(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getPhoneList(condition));
    }

    //요금제 목록 조회
    @PostMapping("/rate/list")
    public CommonResponse<List<RateInfoDto>> getRateList(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getRateList(condition));
    }

    //공시지원금 조회 (출고가, 판매가 포함) >> 아직 안함.
    @PostMapping("/phone/subsdamt")
    public CommonResponse<List<PhoneInfoDto>> getMspOfficialNoticeSupport(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getMspOfficialNoticeSupport(condition));
    }

    //부가서비스 목록 조회 - 신규가입 및 번호이동 // 기기변경은 가입중 부가서비스 조회해서 합치기
    //고객포탈 : /appform/getMcpAdditionListAjax.do
    @PostMapping("/addition/list")
    public CommonResponse<List<MsfRequestAdditionResponse>> getAdditionList(@RequestBody @Valid MsfRequestAdditionRequest condition) {
        return ResponseUtils.ok(productInfoService.getAdditionList(condition));
    }

    //부가서비스 목록 조회 - 기기변경 신청서의 가입중 부가서비스목록 조회
    @PostMapping("/activeaddition/list")
    public CommonResponse<List<MsfRequestAdditionResponse>> getActiveAdditionList(@RequestBody @Valid MspJuoSubInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getActiveAdditionList(condition));
    }

    //요금제 카테고리 목록 조회
    @PostMapping("/rate/category/list")
    public CommonResponse<List<CategoryInfoDto>> getCategoryList(@RequestBody @Valid CategoryMstRequest condition) {
        return ResponseUtils.ok(productInfoService.getCategoryList(condition));
    }

    //요금제/부가서비스/안심보험 카테고리 상세 조회 >> 개발완료 후 삭제예정
    @PostMapping("/rate/categorydetail/list")
    public CommonResponse<List<CategoryInfoDto>> getCategoryDetailList(@RequestBody @Valid CategoryRelRequest condition) {
        return ResponseUtils.ok(productInfoService.getCategoryDetailList(condition));
    }

    //안심보험 목록 ( as-is :: /appform/selectInsrProdListAjax.do )
    @PostMapping("/product/selectInsrProdList")
    public CommonResponse<List<IntmInsrRelDTO>> getInsrProdList(@RequestBody @Valid InsrProdRequest condition) {
        return ResponseUtils.ok(productInfoService.getInsrProdList(condition));
    }


}
