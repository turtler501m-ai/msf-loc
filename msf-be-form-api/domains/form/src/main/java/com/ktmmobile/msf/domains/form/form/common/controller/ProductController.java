package com.ktmmobile.msf.domains.form.form.common.controller;

import java.util.List;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.CategoryRelRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.InsrProdRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestAdditionRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestAdditionResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSalePlcyMstInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSerialRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.ProductInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.RateInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneModelCapacityResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneModelColorResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneModelMonthlyResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneSaleAgrmResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PhoneSaleTypeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.service.ProductInfoService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class ProductController {

    private final ProductInfoService productInfoService;

    //판매정책조회
    //MSP_SALE_PLCY_MST PL,
    //MSP_SALE_ORGN_MST ORG,
    //MSP_SALE_PRDT_MST PRDT
    @PostMapping("/phone/saleplcy/list")
    public CommonResponse<List<MspSalePlcyMstInfoDto>> getMspSalePlcyMstList(@RequestBody @Valid ProductInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getMspSalePlcyMstList(request));
    }

    //할인유형
    //MSP_SALE_PLCY_MST PL,
    //MSP_SALE_ORGN_MST ORG,
    //MSP_SALE_PRDT_MST PRDT
    @PostMapping("/phone/saletype/list")
    public CommonResponse<List<PhoneSaleTypeResponse>> getSaleTypeList(@RequestBody @Valid ProductInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getSaleTypeList(request));
    }

    //요금 약정기간조회
    //MSP_SALE_AGRM_MST
    @PostMapping("/rate/engg/list")
    public CommonResponse<List<PhoneSaleAgrmResponse>> getMspSaleAgrmMstList(@RequestBody @Valid ProductInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getMspSaleAgrmMstList(request));
    }

    //휴대폰 할부기간 조회
    //ORG_INST_NOM_MST
    @PostMapping("/phone/monthly/list")
    public CommonResponse<List<PhoneModelMonthlyResponse>> getModelMonthlyList(@RequestBody @Valid ProductInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getModelMonthlyList(request));
    }

    //휴대폰 색상 조회
    //CMN_INTM_MDL A
    //CMN_GRP_CD_MST B
    @PostMapping("/phone/color/list")
    public CommonResponse<List<PhoneModelColorResponse>> getPrdtColorList(@RequestBody @Valid ProductInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getPrdtColorList(request));
    }

    //휴대폰 용량 조회
    //NMCP_SNTY_PROD_BAS@DL_MCP A
    //NMCP_PROD_ATRIB_VAL_DTL@DL_MCP B
    @PostMapping("/phone/capacity/list")
    public CommonResponse<List<PhoneModelCapacityResponse>> getPrdtCapacityList(@RequestBody @Valid ProductInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getPrdtCapacityList(request));
    }

    /**
     * 휴대폰 매장 재고 조회 (postgre)
     */
    //MSF_PROD_STOR_INVENTORY_TXN
    @PostMapping("/phone/inventory/list")
    public CommonResponse<List<CategoryInfoDto>> getPhoneInventoryList(@RequestBody @Valid PhoneSerialRequest request) {
        return ResponseUtils.ok(productInfoService.getPhoneInventoryList(request));
    }

    /**
     * 휴대폰 목록 조회
     */
    @PostMapping("/phone/list")
    public CommonResponse<List<PhoneInfoResponse>> getPhoneList(@RequestBody @Valid ProductInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getPhoneList(request));
    }

    /**
     * 요금제 목록 조회
     */
    //MSP_SALE_ORGN_MST ORGN,
    //MSP_SALE_PLCY_MST PLCY,
    //MSP_SALE_RATE_MST RATE,
    //MSP_RATE_MST RATE_MST
    @PostMapping("/rate/list")
    public CommonResponse<List<RateInfoResponse>> getRateList(@RequestBody @Valid ProductInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getRateList(request));
    }

    /**
     * 가격정보 조회 (단말, 요금, 지원금 ) 등등등
     */
    @PostMapping("/phone/getMspSalePriceInfo")
    //@PostMapping("/phone/mspsaleprice/get")
    public CommonResponse<MspSaleSubsdMstResponse> getMspSalePriceInfo(@RequestBody @Valid MspSaleSubsdMstRequest request) {
        return ResponseUtils.ok(productInfoService.getMspSalePriceInfo(request));
    }

    //부가서비스 목록 조회 - 신규가입 및 번호이동 // 기기변경은 가입중 부가서비스 조회해서 합치기
    //MSP_RATE_MST A
    //MSP_SALE_RATE_MST B
    //고객포탈 : /appform/getMcpAdditionListAjax.do
    @PostMapping("/addition/list")
    public CommonResponse<List<MsfRequestAdditionResponse>> getAdditionList(@RequestBody @Valid MsfRequestAdditionRequest request) {
        return ResponseUtils.ok(productInfoService.getAdditionList(request));
    }

    //부가서비스 목록 조회 - 기기변경 신청서의 가입중 부가서비스목록 조회
    //기기변경 가입중 부가서비스 조회 : /mypage/regService
    @PostMapping("/activeaddition/list")
    public CommonResponse<List<MsfRequestAdditionResponse>> getActiveAdditionList(@RequestBody @Valid MspJuoSubInfoRequest request) {
        return ResponseUtils.ok(productInfoService.getActiveAdditionList(request));
    }

    //안심보험 목록 ( as-is :: /appform/selectInsrProdListAjax.do )
    /* appform/selectInsrProdList */
    @PostMapping("/product/selectInsrProdList")
    //@PostMapping("/product/insr/list")
    public CommonResponse<List<IntmInsrRelDTO>> getInsrProdList(@RequestBody @Valid InsrProdRequest request) {
        return ResponseUtils.ok(productInfoService.getInsrProdList(request));
    }

    //요금제 카테고리 목록 조회
    @PostMapping("/rate/category/list")
    public CommonResponse<List<CategoryInfoDto>> getCategoryList(@RequestBody @Valid CategoryMstRequest request) {
        return ResponseUtils.ok(productInfoService.getCategoryList(request));
    }

    //요금제/부가서비스/안심보험 카테고리 상세 조회 >> 개발완료 후 삭제예정
    @PostMapping("/rate/categorydetail/list")
    //@PostMapping("/rate/category/detail/list")
    public CommonResponse<List<CategoryInfoDto>> getCategoryDetailList(@RequestBody @Valid CategoryRelRequest request) {
        return ResponseUtils.ok(productInfoService.getCategoryDetailList(request));
    }


    //여기서부터 ~~ 삭제할꺼에요~~~~~~ @@@@@@@@@@@@@@@@@@@
    //가격정보조회 (단말,요금,지원금) - 출고가, 기본요금, 공시지원금 등
    //mcp-api : MspMapper.findMspSaleSubsdMst
    //MSP_SALE_SUBSD_MST
    /*@PostMapping("/phone/subsdamt")
    public CommonResponse<MspSaleSubsdMstResponse> getMspSaleSubsdMst(@RequestBody @Valid MspSaleSubsdMstRequest request) {
        return ResponseUtils.ok(productInfoService.getMspSaleSubsdMst(request));
    }*/
    /*public CommonResponse<List<PhoneInfoDto>> getMspOfficialNoticeSupport(@RequestBody @Valid ProductInfoRequest condition) {
        return ResponseUtils.ok(productInfoService.getMspOfficialNoticeSupport(condition));
    }*/
    //가입비, 유심비용 등 조회
    //mcp-api : StoreUsimMapper.selectJoinUsimPriceNew , StoreUsimMapper.selectUsimDcamt
    //MCP : /usim/selectUsimBasJoinPriceAjax.do , /storeUsim/usimDcamt
    /*@PostMapping("/usim/getJoinUsimPrice")
    public CommonResponse<PriceJoinUsimResponse> getUsimBasJoinPrice(@RequestBody @Valid PriceJoinUsimRequest request) {
        return ResponseUtils.ok(productInfoService.getUsimBasJoinPrice(request));
    }*/
    //여기서부터 ~~ 삭제할꺼에요~~~~~~ @@@@@@@@@@@@@@@@@@@

}
