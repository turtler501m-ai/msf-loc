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
import com.ktmmobile.msf.domains.form.form.common.dto.CrdtCardAuthRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.NiceAccountRequest;
import com.ktmmobile.msf.domains.form.form.common.service.PaymentService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeValidCheckService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService; //청구계정아이디조회, 신용카드인증, 계좌인증
    private final NewChangeValidCheckService newChangeValidCheckService; //신규/변경 유효성체크

    /**
     * 개통이력조회
     */
    @PostMapping("/openhistory/get")
    public CommonResponse<FormResponse<String>> getOpenHistory(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getOpenHistory(request));
    }

    /**
     * 청구계정아이디 조회 (고객포탈은 없음?) >> 본인만 가능
     */
    @PostMapping("/payment/bill/verify")
    public CommonResponse<FormResponse<MspJuoBanInfoResponse>> verifyBillInfo(@RequestBody @Valid MspJuoBanInfoRequest request) {
        return ResponseUtils.ok(paymentService.verifyBillInfo(request));
    }

    /**
     * 신용카드인증 (X91)
     */
    //@RequestMapping(value = "/crdtCardAthnInfoAjax.do")
    @PostMapping("/payment/credit/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> crdtCardAthnInfo(@RequestBody @Valid CrdtCardAuthRequest request) {
        return ResponseUtils.ok(paymentService.crdtCardAthnInfo(request));
    }

    /**
     * 계좌번호인증 (NICE)
     */
    //@RequestMapping(value = "/nice/accountCheckAjax.do")
    @PostMapping("/payment/account/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> accountCheck(@RequestBody @Valid NiceAccountRequest niceAccountRequest) {
        return ResponseUtils.ok(paymentService.accountCheck(niceAccountRequest));
    }
}
