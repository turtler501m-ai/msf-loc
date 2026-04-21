package com.ktmmobile.msf.domains.form.login.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.port.in.LoginSvcReader;
import com.ktmmobile.msf.domains.form.login.application.port.out.LoginRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginSvcService implements LoginSvcReader {

    private final LoginRepository repository;

    @Transactional(noRollbackFor = {SimpleDomainException.class})
    public LoginResponse login(LoginRequest request) {
        log.debug(request.getAuthType());
        LoginResponse loginResponse = null;
        if(request.getAuthType().equals("BIOPASS")) {
            if(request.getUuid() == null) {
                throw new SimpleDomainException("uuid는 필수 입력 값입니다.");
            }
            loginResponse = repository.getUserAppInfo(request);
        } else {
            loginResponse = repository.getUserInfo(request);
        }

        if (loginResponse != null) {
            if (loginResponse.getAccessLimitYn().equals("Y")) {
                throw new SimpleDomainException("로그인에 실패했습니다.\n [관리자에게 문의하세요.]");
            }
            if (!loginResponse.getUserSttusCd().equals("A")) {
                throw new SimpleDomainException("로그인에 실패했습니다.\n [관리자에게 문의하세요..]");
            }
            if(request.getAuthType().equals("PASSWORD")) {
                if (!loginResponse.getPwd().equals(request.getUserPw())) {
                    if (loginResponse.getLoginChkCnt() >= 3) {
                        request.setUserSttusCd("C");
                        // 사용자 변경이력 생성
                        repository.insertUserHistory(request);
                    }
                    repository.updateLoginFail(request);
                    log.debug("Login request: {}", request.getFailCnt());
                    throw new SimpleDomainException("로그인에 실패했습니다.[" + loginResponse.getLoginChkCnt() + "회]");
                }
                Integer retInt = repository.updateLoginSucc(request);
            } else if(request.getAuthType().equals("BIOPASS")) {
                Integer retBioInt = repository.updateBioLoginSucc(request);
            }
            return loginResponse;
        }
        throw new SimpleDomainException("로그인에 실패했습니다.");
    }
}
