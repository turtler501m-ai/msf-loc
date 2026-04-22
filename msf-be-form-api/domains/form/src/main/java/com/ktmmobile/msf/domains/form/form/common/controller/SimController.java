package com.ktmmobile.msf.domains.form.form.common.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSerialCondition;
import com.ktmmobile.msf.domains.form.form.common.service.SimInfoService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class SimController {

    private final SimInfoService simInfoService;

    //휴대폰 일련번호 유효성체크 - Y13
    @PostMapping("/verifyPhoneSerialNumberInfo")
    public CommonResponse<Map<String, Object>> verifyPhoneSerialNumberInfo(@RequestBody @Valid PhoneSerialCondition condition) {
        return ResponseUtils.ok(simInfoService.verifyPhoneSerialNumberInfo(condition));
    }

    //USIM 정보 유효성체크 - X85
    @PostMapping("/verifyUsimInfo")
    //public CommonResponse<MoscInqrUsimUsePsblOutDTO> verifyUsimInfo(@RequestBody @Valid MspJuoSubInfoCondition condition) throws SocketTimeoutException {
    public CommonResponse<Map<String, Object>> verifyUsimInfo(@RequestBody @Valid MspJuoSubInfoCondition condition) {
        return ResponseUtils.ok(simInfoService.verifyUsimInfo(condition));
    }

    //eSIM 정보 유효성체크 - Y13, Y12, Y14, Y15
    @PostMapping("/verifyEsimInfo")
    public CommonResponse<Map<String, Object>> verifyEsimInfo(@RequestBody @Valid EsimDto condition) {
        return ResponseUtils.ok(simInfoService.verifyEsimInfo(condition));
    }
}
