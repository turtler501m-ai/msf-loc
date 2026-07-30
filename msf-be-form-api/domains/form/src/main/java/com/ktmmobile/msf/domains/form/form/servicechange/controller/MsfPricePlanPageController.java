package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PossibleStateCheckRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PossibleStateCheckResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanX89ResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanX90ResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanY02ResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.RealTimeInfoRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.RealTimeInfoResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfPricePlanServiceImpl;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvgChargePlanChangeService;

@RestController
public class MsfPricePlanPageController {

    @Autowired MsfPricePlanServiceImpl msfPricePlanService;

    @Autowired
    private MsfSvgChargePlanChangeService chargePlanChangeService;

    // 가입중인 요금제 조회
    @PostMapping("/api/form/pricechange/currentpriceplan")
    public CommonResponse<FormResponse<PricePlanY02ResDto>> currentPrice(@RequestBody PricePlanReqDto req) {
        return ResponseUtils.ok(msfPricePlanService.currentPrice(req));
    }

    // 요금제 변경 예약 정보 조회
    @PostMapping("/api/form/pricechange/reservedpriceplan")
    public CommonResponse<FormResponse<PricePlanX89ResDto>> reservedPrice(@RequestBody PricePlanReqDto req) {
        return ResponseUtils.ok(msfPricePlanService.reservedPrice(req));
    }

    // 요금제 변경 예약
    @PostMapping("/api/form/pricechange/reservedpriceplanchange")
    public CommonResponse<FormResponse<PossibleStateCheckResponse>> reservedPriceChange(@RequestBody ServiceChangeCompleteReqDto request) {

        PossibleStateCheckRequest chkReq = new PossibleStateCheckRequest();
        chkReq.setActCode(request.getPlanChange().getActCode());
        chkReq.setContractNum(request.getContractNum());
        chkReq.setCustId(request.getCustId());
        chkReq.setNcn(request.getNcn());
        chkReq.setCtn(request.getCtn());
        chkReq.setCustomerSsn(request.getUserBirthDate());
        chkReq.setParentScanId(request.getParentScanId());
        chkReq.setOpeningDate(request.getPlanChange().getOpeningDate());
        chkReq.setPlanSoc(request.getPlanChange().getPlanSoc());
        chkReq.setBeforePlanSoc(request.getPlanChange().getBeforePlanSoc());

        PossibleStateCheckRequest.ProductInfo productInfo = new PossibleStateCheckRequest.ProductInfo();
        List<PossibleStateCheckRequest.ProductInfo> productInfoList = new ArrayList<>();
        productInfo.setPrdcCd(request.getPlanChange().getPlanSoc());
        productInfo.setFtrNewParam(request.getPlanChange().getPlanFtrNewParam());
        productInfoList.add(productInfo);
        chkReq.setPrdcList(productInfoList);

        return ResponseUtils.ok(chargePlanChangeService.reservedPriceChange(chkReq));
    }

    // 요금제 변경 예약 취소
    @PostMapping("/api/form/pricechange/reservedpriceplancancel")
    public CommonResponse<FormResponse<PricePlanX90ResDto>> reservedPriceCancel(@RequestBody PricePlanReqDto req) {
        return ResponseUtils.ok(msfPricePlanService.reservedPriceCancel(req));
    }

    /**
     * realTimeChargeList
     * 실시간 요금 조회(X18) - 요금제 변경 즉시변경 가능한 경우 팝업 노출
     * @param req
     * @return
     */
    @PostMapping("/api/msf/formServiceChange/realTimeCharge/list")
    public CommonResponse<FormResponse<RealTimeInfoResponse>> realTimeChargeList(@RequestBody RealTimeInfoRequest req) {
        return ResponseUtils.ok(chargePlanChangeService.realTimeChargeList(req));
    }

    /**
     * possibleStateCheck
     * 요금제 변경 가능여부 - 사전 체크
     * @param req
     * @return
     */
    @PostMapping("/api/msf/formServiceChange/possibleState/check")
    public CommonResponse<FormResponse<PossibleStateCheckResponse>> possibleStateCheck(@RequestBody PossibleStateCheckRequest req) {
        PossibleStateCheckRequest.ProductInfo productInfo = new PossibleStateCheckRequest.ProductInfo();
        List<PossibleStateCheckRequest.ProductInfo> productInfoList = new ArrayList<>();
        productInfo.setPrdcCd(req.getPlanSoc());
        productInfoList.add(productInfo);
        req.setPrdcList(productInfoList);
        return ResponseUtils.ok(chargePlanChangeService.possibleStateCheck(req));
    }

    // 요금제 변경
    @PostMapping("/api/msf/formServiceChange/possibleState/change")
    public CommonResponse<FormResponse<PossibleStateCheckResponse>> possibleStateChange(@RequestBody ServiceChangeCompleteReqDto request) {

        PossibleStateCheckRequest chkReq = new PossibleStateCheckRequest();
        chkReq.setActCode(request.getPlanChange().getActCode());
        chkReq.setContractNum(request.getContractNum());
        chkReq.setCustId(request.getCustId());
        chkReq.setNcn(request.getNcn());
        chkReq.setCtn(request.getCtn());
        chkReq.setCustomerSsn(request.getUserBirthDate());
        chkReq.setParentScanId(request.getParentScanId());
        chkReq.setOpeningDate(request.getPlanChange().getOpeningDate());
        chkReq.setPlanSoc(request.getPlanChange().getPlanSoc());
        chkReq.setBeforePlanSoc(request.getPlanChange().getBeforePlanSoc());

        PossibleStateCheckRequest.ProductInfo productInfo = new PossibleStateCheckRequest.ProductInfo();
        List<PossibleStateCheckRequest.ProductInfo> productInfoList = new ArrayList<>();
        productInfo.setPrdcCd(request.getPlanChange().getPlanSoc());
        productInfo.setFtrNewParam(request.getPlanChange().getPlanFtrNewParam());
        productInfoList.add(productInfo);
        chkReq.setPrdcList(productInfoList);

        return ResponseUtils.ok(chargePlanChangeService.possibleStateChange(chkReq));
    }

}
