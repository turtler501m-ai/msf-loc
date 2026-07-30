package com.ktmmobile.msf.domains.form.form.newchange.controller;

import java.util.Map;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.service.SimInfoService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.UsimRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.UsimResponse;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class SimInfoController {

    private final SimInfoService simInfoService; //휴대폰일련번호 유효성체크, USIM유효성체크, eSIM유효성체크


    /**
     * 휴대폰 일련번호 유효성체크 - Y13
     */
    @PostMapping("/phoneinfo/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyPhoneSerialNumberInfo(@RequestBody @Valid ProductInventoryRequest request) {
        return ResponseUtils.ok(simInfoService.verifyPhoneSerialNumberInfo(request));
    }

    /**
     * USIM 정보 유효성체크 - X85
     */
    //@RequestMapping(value = "/msp/moscIntmMgmtAjax.do")
    @PostMapping("/usiminfo/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyUsimInfo(@RequestBody @Valid UsimRequest request) {
        return ResponseUtils.ok(simInfoService.verifyUsimInfo(request));
    }

    /**
     * eSIM 정보 유효성체크 - Y13, Y12, Y14, Y15
     * 매장재고확인은 하지 않음. 휴대폰 일련번호로 매장재고조회를 하기 때문. 2026-06-18
     */
    //@RequestMapping(value = {"/appForm/eSimChkAjax.do", "/m/appForm/eSimChkAjax.do"})
    @PostMapping("/esiminfo/verify")
    public CommonResponse<FormResponse<EsimResponse>> verifyEsimInfo(@RequestBody @Valid EsimRequest request) {
        return ResponseUtils.ok(simInfoService.verifyEsimInfo(request));
    }

    /**
     * USIM 가격 가져오기
     */
    //@RequestMapping(value = {"/appForm/eSimChkAjax.do", "/m/appForm/eSimChkAjax.do"})
    @PostMapping("/price/get")
    public CommonResponse<FormResponse<UsimResponse>> getPrice(@RequestBody @Valid UsimRequest request) {
        return ResponseUtils.ok(simInfoService.getPrice(request));
    }
}
