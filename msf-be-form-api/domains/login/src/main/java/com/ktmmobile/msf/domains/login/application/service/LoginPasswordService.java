package com.ktmmobile.msf.domains.login.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.application.service.LoginSessionService;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.LoginUserMapper;
import com.ktmmobile.msf.domains.login.application.dto.LoginPasswordChangeRequest;
import com.ktmmobile.msf.domains.login.application.port.in.LoginPasswordChanger;

@RequiredArgsConstructor
@Service
public class LoginPasswordService implements LoginPasswordChanger {

    private final LoginSessionService loginSessionService;
    private final LoginSessionFlowProcessor loginSessionFlowProcessor;
    private final LoginUserMapper loginUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public LoginResult changePassword(LoginPasswordChangeRequest request) {
        if (!StringUtils.hasText(request.loginSessionId())) {
            updatePassword(AuthenticationUtils.getUser().getUserId(), request.password());
            return null;
        }

        LoginSessionUser principal = loginSessionService.getVerifiedPrincipal(request.loginSessionId());
        if (principal.userType() != UserType.FORM_USER) {
            throw new LoginException("지원하지 않는 사용자 유형입니다.");
        }

        updatePassword(principal.userId(), request.password());
        return loginSessionFlowProcessor.completeAction(request.loginSessionId(), LoginRequiredAction.PASSWORD_CHANGE_CODE);
    }

    private void updatePassword(String userId, String password) {
        loginUserMapper.insertFormUserHistory(userId);
        int updated = loginUserMapper.updateFormPassword(userId, passwordEncoder.encode(password));
        if (updated != 1) {
            throw new LoginException("비밀번호 변경에 실패했습니다.");
        }
    }
}
