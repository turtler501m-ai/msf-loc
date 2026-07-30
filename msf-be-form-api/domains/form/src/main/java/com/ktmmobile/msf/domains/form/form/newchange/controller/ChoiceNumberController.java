package com.ktmmobile.msf.domains.form.form.newchange.controller;

import java.util.List;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SearchNumberRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SearchNumberResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNU1Response;
import com.ktmmobile.msf.domains.form.form.newchange.service.ChoiceNumberService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class ChoiceNumberController {

    private final ChoiceNumberService choiceNumberService; //신규가입 희망번호 조회/예약/취소


    /**
     * 신규가입 희망번호 조회 (NU1)
     */
    //@RequestMapping(value = "/appform/searchNumberAjax.do")
    //prx 연동
    @PostMapping(value = "/hopenumber/get")
    public CommonResponse<FormResponse<List<MplatFormNU1Response.OutDto.SvcNoList>>> getSearchNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.getSearchNumber(request));
    }

    /**
     * 신규가입 희망번호 예약 (NU2)
     */
    //@RequestMapping(value = "/appform/setNumberAjax.do")
    @PostMapping(value = "/hopenumber/reserve")
    //public CommonResponse<FormResponse<MplatFormNU1ListResponse>> getSearchNumber(@RequestBody @Valid SearchNumberRequest request) {
    public CommonResponse<FormResponse<SearchNumberResponse>> setChoiseNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.setChoiseNumber(request));
    }

    /**
     * 신규가입 희망번호 취소 (NU2)
     */
    //@RequestMapping(value = "/appform/cancelNumberAjax.do")
    @PostMapping(value = "/hopenumber/cancel")
    public CommonResponse<FormResponse<SearchNumberResponse>> cancelNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.cancelChoiseNumber(request));
    }

}
