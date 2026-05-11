package com.ktmmobile.msf.domains.form.login.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.login.application.dto.OnModify;
import com.ktmmobile.msf.domains.form.login.application.dto.PassChangeRequest;
import com.ktmmobile.msf.domains.form.login.application.port.in.LoginSvcReader;
import com.ktmmobile.msf.domains.form.login.application.port.in.LoginSvcWriter;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginSvcReader loginSvcReader;
    private final LoginSvcWriter loginSvcWriter;

    // @PostMapping("/api/login/login")
    // public CommonResponse<LoginResponse> login(@RequestBody @Validated(OnSelect.class) LoginRequest request) {
    //     return ResponseUtils.ok(loginSvcReader.login(request));
    // }

    @PostMapping({"/api/n/auth/passwd/modify", "/api/users/passwd/modify"})
    public CommonResponse<Integer> modifyPassword(@RequestBody @Validated(OnModify.class) PassChangeRequest request) {
        return ResponseUtils.ok(loginSvcWriter.modifyPassword(request));
    }

}
