package com.ktmmobile.msf.domains.form.form.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.service.AuthInfoService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeCustomerInfoResponse;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class AuthController {

    private final AuthInfoService authInfoService;

    //KTM 고객인증 (핸드폰번호, 고객명) :: 추후 common 패키지로 이동하여 구성될 예정. 공통 controller 명칭 구상중?
    //as-is :: appform/selRMemberAjax.do
    @PostMapping("/ktmmember/auth")
    public CommonResponse<FormResponse<MspJuoSubInfoResponse>> authKtmMember(@RequestBody @Validated MspJuoSubInfoRequest request) {
        return ResponseUtils.ok(authInfoService.getKtmMemberInfo(request));
    }

    /**
     * 신규변경 - 기기변경 고객인증
     * 고객인증 : DB 조회
     * 가입정보조회(X01)
     * 납부방법조회(X23)
     */
    @PostMapping("/ktmmember/newchange-auth")
    public CommonResponse<FormResponse<NewChangeCustomerInfoResponse>> authKtmMemberForNewChange(@RequestBody @Validated MspJuoSubInfoRequest request) {
        return ResponseUtils.ok(authInfoService.authNewChangeAuthInfo(request));
    }

    // 서비스변경/해지는 인증 후 가입정보조회에서 NCN(SVC_CNTR_NO)을 사용하므로 신규/기기변경 인증과 분리한다.
    @PostMapping("/ktmmember/servicechange-auth")
    public CommonResponse<FormResponse<MspJuoSubInfoResponse>> authKtmMemberForServiceChange(@RequestBody @Validated MspJuoSubInfoRequest request) {
        return ResponseUtils.ok(authInfoService.getServiceChangeAuthInfo(request));
    }

}
