package com.ktmmobile.msf.domains.form.login.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.dto.PassChangeRequest;
import com.ktmmobile.msf.domains.form.login.application.port.in.LoginSvcReader;
import com.ktmmobile.msf.domains.form.login.application.port.in.LoginSvcWriter;
import com.ktmmobile.msf.domains.form.login.application.port.out.LoginRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginSvcService implements LoginSvcReader, LoginSvcWriter {

    private final LoginSessionFlowProcessor loginSessionFlowProcessor;
    private final PasswordEncoder passwordEncoder;
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
            LoginResponse loginChk = repository.getUserAppInfo(request);
            if(loginChk != null && !loginChk.getUserId().equals(request.getUuid())) {
                throw new SimpleDomainException("아이디에 등록된 단말기가 아닙니다.");
            }
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
                String failMsg = "비밀번호를 다시 확인해 주세요.";
                if (!loginResponse.getPwd().equals(request.getUserPw())) {
                    if (loginResponse.getLoginChkCnt() >= 3) {
                        request.setUserSttusCd("C");
                        // 사용자 변경이력 생성
                        repository.insertUserHistory(request.getUserId());
                        failMsg = "3회 실패시 계정이 잠금처리되어 사용이 불가합니다.";
                    } else {
                        failMsg = "비밀번호를 다시 확인해 주세요. ["+ loginResponse.getLoginChkCnt() + "회]";
                    }
                    repository.updateLoginFail(request);
                    log.debug("Login request: {}", request.getFailCnt());
                    throw new SimpleDomainException(failMsg);
                }
                Integer retInt = repository.updateLoginSucc(request);
            } else if(request.getAuthType().equals("BIOPASS")) {
                Integer retBioInt = repository.updateBioLoginSucc(request);
            }
            // 암호 삭제
            loginResponse.setPwd("");
            return loginResponse;
        }
        throw new SimpleDomainException("로그인에 실패했습니다.");
    }

    @Transactional(noRollbackFor = {SimpleDomainException.class})
    public Integer modifyPassword(PassChangeRequest request) {
        String strUserId = "";
        if(StringUtil.isBlank(request.getLoginSessionId())) {
            strUserId = AuthenticationUtils.getUser().getId();
        } else {
            LoginSessionUser sessionUser = loginSessionFlowProcessor.getSessionUser(request.getLoginSessionId());
            strUserId = sessionUser.userId();
        }
        log.debug("modifyPassword -- strUserId: {}", strUserId);
        // 사용자 변경이력 생성
        repository.insertUserHistory(strUserId);

        request.setUserId(strUserId);
        request.setNewPassword(passwordEncoder.encode(request.getNewPassword()));
        Integer retInt = repository.modifyPass(request);
        log.debug("modifyPassword retInt:{}", retInt);
        if (retInt != 1) {
            throw new SimpleDomainException("비밀번호 변경에 실패했습니다.");
        }
        return retInt;
    }
}
