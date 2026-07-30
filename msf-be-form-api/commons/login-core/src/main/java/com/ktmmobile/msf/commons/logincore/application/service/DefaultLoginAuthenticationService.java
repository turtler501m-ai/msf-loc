package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.context.LoginContextHolder;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticator;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticationRecorder;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionPolicyComposite;
import com.ktmmobile.msf.commons.logincore.domain.policy.failure.LoginFailureContext;
import com.ktmmobile.msf.commons.logincore.domain.policy.failure.LoginFailurePolicyComposite;
import com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction.LoginRequiredActionPolicyComposite;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

/**
 * ID/PW 또는 앱별 인증 credential을 공통 로그인 세션으로 변환하는 기본 인증 서비스
 *
 * @param <C> 앱별 로그인 credential 타입
 */
@RequiredArgsConstructor
@Service
public class DefaultLoginAuthenticationService<C extends LoginAuthenticationCredential> implements LoginAuthenticator<C> {

    private final LoginUserFinder<C> loginUserFinder;
    private final LoginAuthenticationRecorder<C> loginAuthenticationRecorder;
    private final PasswordEncoder passwordEncoder;
    private final LoginCompletionPolicyComposite loginCompletionPolicyComposite;
    private final LoginFailurePolicyComposite loginFailurePolicyComposite;
    private final LoginRequiredActionPolicyComposite loginRequiredActionPolicyComposite;
    private final LoginSessionService loginSessionService;
    private final LoginUserInfoCacheService loginUserInfoCacheService;

    /**
     * 로그인 인증 처리
     *
     * @param credential 로그인 인증 정보
     * @return 로그인 진행 결과
     */
    @Override
    @Transactional(noRollbackFor = LoginException.class)
    public LoginResult authenticate(C credential) {
        // 앱별 저장소에서 비밀번호 검증과 정책 판단에 필요한 최소 사용자 정보 조회
        LoginUser user = loginUserFinder.findByCredential(credential)
            .orElseThrow(() -> {
                loginAuthenticationRecorder.recordLoginFailure(credential);
                return new LoginException("아이디 또는 비밀번호가 불일치합니다.");
            });

        // 계정 상태, IP 허용 여부, 인증 방식 지원 여부처럼 비밀번호 비교 전 확인 가능한 정책 검증
        LoginCompletionContext<C> context = new LoginCompletionContext<>(user, credential);
        loginCompletionPolicyComposite.verify(context);

        // 아이디 존재 여부와 비밀번호 불일치 여부는 동일한 메시지로 응답
        if (credential.isPasswordAuth() && !matches(credential.password(), user.encodedPassword())) {
            boolean shouldLock = loginFailurePolicyComposite.shouldLock(new LoginFailureContext<>(user, credential));
            loginAuthenticationRecorder.recordLoginFailure(user, credential, shouldLock);
            throw new LoginException("아이디 또는 비밀번호가 불일치합니다.");
        }

        return LoginContextHolder.withUserId(user.userId(), () -> completeAuthentication(user, credential));
    }

    /**
     * 인증 완료 후 로그인 세션 생성
     *
     * @param user 로그인 사용자
     * @param credential 로그인 인증 정보
     * @return 로그인 진행 결과
     */
    private LoginResult completeAuthentication(LoginUser user, C credential) {
        loginUserFinder.verifyAuthenticatedUser(user, credential);

        // 로그인 완료 전 필요한 사용자 상세 정보와 앱별 attributes 구성
        LoginUserInfo userInfo = loginUserFinder.findUserInfo(user, credential)
            .orElseGet(() -> LoginUserInfo.of(user, credential.userType()));
        userInfo = withClientIp(userInfo, credential.clientIp());

        // 비밀번호 변경, 2FA, 단말 인증처럼 토큰 발급 전후 조치를 정책으로 산출
        LoginCompletionContext<C> requiredActionContext = new LoginCompletionContext<>(user, credential, userInfo);
        List<LoginRequiredAction> requiredActions = loginRequiredActionPolicyComposite.resolve(requiredActionContext);
        LoginSessionUser principal = new LoginSessionUser(
            userInfo.userId(),
            userInfo.userType(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            userInfo.clientIp(),
            userInfo.organization(),
            userInfo.attributes(),
            requiredActions
        );
        loginUserInfoCacheService.save(userInfo);
        List<LoginRequiredAction> actionsBeforeTokenIssue = actionsBeforeTokenIssue(requiredActions);

        // 토큰 발급 전 필수 조치가 없더라도 issue API를 통해 토큰을 발급받도록 ready session 생성
        if (actionsBeforeTokenIssue.isEmpty()) {
            return loginSessionService.createReadySession(principal);
        }
        if (actionsBeforeTokenIssue.getFirst().isVerifyTwoFactor()) {
            return loginSessionService.createTwoFactorSession(principal);
        }
        return loginSessionService.createActionSession(principal, actionsBeforeTokenIssue);
    }

    /**
     * 비밀번호 일치 여부 확인
     *
     * @param rawPassword 원문 비밀번호
     * @param savedPassword 저장 비밀번호
     * @return 일치 여부
     */
    private boolean matches(String rawPassword, String savedPassword) {
        return passwordEncoder.matches(rawPassword, savedPassword);
    }

    /**
     * 토큰 발급 전 필수 조치 목록 추출
     *
     * @param requiredActions 필수 조치 목록
     * @return 토큰 발급 전 필수 조치 목록
     */
    private List<LoginRequiredAction> actionsBeforeTokenIssue(List<LoginRequiredAction> requiredActions) {
        return requiredActions.stream()
            .filter(action -> !action.tokenIssuable())
            .toList();
    }

    /**
     * 사용자 정보에 클라이언트 IP 반영
     *
     * @param userInfo 사용자 정보
     * @param clientIp 클라이언트 IP
     * @return 클라이언트 IP 반영 사용자 정보
     */
    private LoginUserInfo withClientIp(LoginUserInfo userInfo, String clientIp) {
        return new LoginUserInfo(
            userInfo.userId(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            userInfo.userType(),
            clientIp,
            userInfo.organization(),
            userInfo.attributes()
        );
    }
}
