package com.ktmmobile.msf.domains.form.form.common.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.common.dto.CrdtCardAuthRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    //청구계정아이디 조회 (고객포탈:x) >> 타인가능?
    @PostMapping("/verifyBillInfo")
    //1. KTM모바일 고객인증 >> 핸드폰번호, 고객명
    //2. 청구계정아이디 조회
    public CommonResponse<MspJuoBanInfoResponse> verifyBillInfo(@RequestBody @Validated MspJuoBanInfoRequest request) {
        return ResponseUtils.ok(paymentService.verifyBillInfo(request));
    }

    //신용카드인증 (고객포탈:/crdtCardAthnInfoAjax.do)
    @PostMapping("/crdtCardAthnInfo")
    public CommonResponse<Map<String, Object>> crdtCardAthnInfo(@RequestBody @Validated CrdtCardAuthRequest request) {
        return ResponseUtils.ok(paymentService.crdtCardAthnInfo(request));
    }

    //계좌번호인증 (고객포탈:/nice/accountCheckAjax.do)
    /*@PostMapping("/accountCheck")
    public CommonResponse<Map<String, Object>> accountCheck(@RequestBody @Validated MspJuoBanInfoRequest request) {
        return ResponseUtils.ok(paymentService.accountCheck(request));
    }*/
}
