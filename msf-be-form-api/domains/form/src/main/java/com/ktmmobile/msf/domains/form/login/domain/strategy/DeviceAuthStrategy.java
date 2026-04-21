package com.ktmmobile.msf.domains.form.login.domain.strategy;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;

@Component
@Order(2)
public class DeviceAuthStrategy implements AuthStrategy {

    @Override
    public boolean supports(String authType) {
        return "DEVICE".equalsIgnoreCase(authType);
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        // [DB 로직 주석]
        // 1. 등록된 단말 정보 테이블에서 deviceId와 userId 매칭 확인
        //    (SELECT COUNT(*) FROM user_devices WHERE user_id = ? AND device_id = ?)
        // 2. 해당 단말이 활성화 상태인지 확인
        // System.out.println(">>> [단말 인증] 등록된 기기 정보 검증 완료");
        return null;
    }
}

