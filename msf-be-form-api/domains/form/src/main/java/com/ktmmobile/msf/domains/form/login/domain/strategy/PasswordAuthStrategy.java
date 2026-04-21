package com.ktmmobile.msf.domains.form.login.domain.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.port.out.LoginRepository;

@Component
@RequiredArgsConstructor
@Order(1)
public class PasswordAuthStrategy implements AuthStrategy {

    private final LoginRepository repository;

    @Override
    public boolean supports(String authType) {
        return "PASSWORD".equalsIgnoreCase(authType);
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        // [DB 로직 주석] 사용자 검증 생략
        LoginResponse res = repository.getUserInfo(request);
        System.out.println("id:" + res.getUserId());

        // Redis를 통한 중복 로그인 제어
        // sessionManager.login(request.getUserId(), request.getDeviceId());

        // System.out.println(">>> [" + request.getUserId() + "] Redis 세션 등록 완료");
        return res;
    }
}
