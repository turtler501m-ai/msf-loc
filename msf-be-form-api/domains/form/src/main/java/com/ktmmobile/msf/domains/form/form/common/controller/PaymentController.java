package com.ktmmobile.msf.domains.form.form.common.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.CrdtCardAuthRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.NiceAccountRequest;
import com.ktmmobile.msf.domains.form.form.common.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
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
public class PaymentController {
    private final PaymentService paymentService;

    //청구계정아이디 조회 (고객포탈은 없음?) >> 본인만 가능
    @PostMapping("/verifyBillInfo")
    public CommonResponse<FormResponse<MspJuoBanInfoResponse>> verifyBillInfo(@RequestBody @Valid MspJuoBanInfoRequest request) {
        return ResponseUtils.ok(paymentService.verifyBillInfo(request));
    }

    //신용카드인증 (고객포탈:/crdtCardAthnInfoAjax.do)
    @PostMapping("/crdtCardAthnInfo")
    public CommonResponse<FormResponse<Map<String, Object>>> crdtCardAthnInfo(@RequestBody @Valid CrdtCardAuthRequest request) {
        return ResponseUtils.ok(paymentService.crdtCardAthnInfo(request));
    }

    //계좌번호인증 (고객포탈:/nice/accountCheckAjax.do)
    @PostMapping("/accountCheck")
    public CommonResponse<FormResponse<Map<String, Object>>> accountCheck(@RequestBody @Valid NiceAccountRequest niceAccountRequest, HttpServletRequest request) {
        return ResponseUtils.ok(paymentService.accountCheck(niceAccountRequest, request));
    }
}
