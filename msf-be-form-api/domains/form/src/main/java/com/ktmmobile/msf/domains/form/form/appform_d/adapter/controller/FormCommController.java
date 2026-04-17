package com.ktmmobile.msf.domains.form.form.appform_d.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.in.JuoSubInfoReader;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class FormCommController {

    private final JuoSubInfoReader juoSubInfoReader;

    //KTM 고객인증 (핸드폰번호, 고객명)
    @PostMapping("/ktmmember/auth")
    @Validated
    public CommonResponse<JuoSubInfoResponse> authKtmMember(@RequestBody JuoSubInfoCondition condition) {
        return ResponseUtils.ok(juoSubInfoReader.getJuoSubInfo(condition));
    }

}
