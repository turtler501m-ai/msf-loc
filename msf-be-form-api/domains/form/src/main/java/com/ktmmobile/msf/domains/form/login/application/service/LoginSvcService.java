package com.ktmmobile.msf.domains.form.login.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.support.context.LoginSessionContext;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.dto.PassChangeRequest;
import com.ktmmobile.msf.domains.form.login.application.port.in.LoginSvcWriter;
import com.ktmmobile.msf.domains.form.login.application.port.out.LoginRepository;
import com.ktmmobile.msf.domains.policy.password.vo.Password;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginSvcService implements LoginSvcWriter {

    private final LoginSessionFlowProcessor loginSessionFlowProcessor;
    private final PasswordEncoder passwordEncoder;
    private final LoginRepository repository;

    @LoginSessionContext
    @Transactional(noRollbackFor = {SimpleDomainException.class})
    public Integer modifyPassword(PassChangeRequest request) {
        String userId = resolveUserID(request);
        log.debug("modifyPassword -- userId: {}", userId);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId(userId);
        LoginResponse userInfo = repository.getUserInfo(loginRequest);
        if (!passwordEncoder.matches(request.getPassword(), userInfo.getPwd())) {
            throw new SimpleDomainException("비밀번호 변경에 실패했습니다.(현재 비밀번호 확인)");
        }

        if (request.getPassword().equals(request.getNewPassword())) {
            throw new SimpleDomainException("비밀번호 변경에 실패했습니다.(비밀번호 동일)");
        }

        // 비밀번호 체크
        chkPassword(request.getNewPassword());

        // 이전 비밀번호와 비교
        LoginResponse userHstInfo = repository.getUserHstInfo(loginRequest);
        if (userHstInfo != null) {
            log.debug("Modify Password newPwd: {}, hstPwd: {}", request.getNewPassword(), userHstInfo.getPwd());
            if (passwordEncoder.matches(request.getNewPassword(), userHstInfo.getPwd())) {
                throw new SimpleDomainException("비밀번호 변경에 실패했습니다.(이전 암호를 사용할 수 없습니다.)");
            }
        }

        // 사용자 변경이력 생성
        repository.insertUserHistory(userId);

        request.setUserId(userId);
        request.setNewPassword(passwordEncoder.encode(request.getNewPassword()));
        Integer retInt = repository.modifyPass(request);
        log.debug("modifyPassword retInt:{}", retInt);
        if (retInt != 1) {
            throw new SimpleDomainException("비밀번호 변경에 실패했습니다.");
        }

        // 사용자(관리자) 변경이력 생성
        repository.insertAdminUserHistory(userId);

        request.setUserId(userId);
        request.setNewPassword(passwordEncoder.encode(request.getNewPassword()));
        Integer retIntAd = repository.modifyAdminPass(request);
        log.debug("modifyAdminPass retIntAd:{}", retIntAd);
        return retInt;
    }

    private String resolveUserID(PassChangeRequest request) {
        if (StringUtils.hasText(request.getLoginSessionId())) {
            LoginSessionUser sessionUser = loginSessionFlowProcessor.getSessionUser(request.getLoginSessionId());
            return sessionUser.userId();
        }
        return AuthenticationUtils.getUser().getUserId();
    }

    public void chkPassword(String chkPassword) {
        if (!Password.isValidFormat(chkPassword)) {
            String invalidFormatMessage = "비밀번호는 영문, 숫자, 특수문자(!@#$%^*+=-)를 포함한 10~15자여야 합니다.";
            log.debug(invalidFormatMessage);
            throw new SimpleDomainException(invalidFormatMessage);
        }
    }

}
