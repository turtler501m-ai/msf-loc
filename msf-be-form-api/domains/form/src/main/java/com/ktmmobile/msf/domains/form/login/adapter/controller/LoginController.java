package com.ktmmobile.msf.domains.form.login.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.dto.OnSelect;
import com.ktmmobile.msf.domains.form.login.application.port.in.LoginSvcReader;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginSvcReader loginSvcReader;

    @PostMapping("/api/login/login")
    public CommonResponse<LoginResponse> login(@RequestBody @Validated(OnSelect.class) LoginRequest request) {
        return ResponseUtils.ok(loginSvcReader.login(request));
    }
}
