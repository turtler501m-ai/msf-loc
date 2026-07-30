package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.logincore.adapter.repository.mybatis.smartform.mapper.LoginCoreMapper;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("전역 IP 제한 로그인 완료 정책")
class GlobalIpLimitLoginCompletionPolicyTest {

    private final LoginCoreMapper loginCoreMapper = mock(LoginCoreMapper.class);
    private final GlobalIpLimitLoginCompletionPolicy policy = new GlobalIpLimitLoginCompletionPolicy(loginCoreMapper);

    @Test
    @DisplayName("클라이언트 IP가 있으면 정책 적용 대상이다")
    void supportsWhenClientIpExists() {
        assertThat(policy.supports(context("127.0.0.1", user()))).isTrue();
    }

    @Test
    @DisplayName("클라이언트 IP가 없으면 정책 적용 대상이 아니다")
    void doesNotSupportWhenClientIpIsBlank() {
        assertThat(policy.supports(context("", user()))).isFalse();
    }

    @Test
    @DisplayName("제한 IP 조회 시 로그인 사용자 ID를 함께 전달한다")
    void verifyWithLoginUserId() {
        policy.verify(context("127.0.0.1", user()));

        verify(loginCoreMapper).existsLimitedAccessIp("127.0.0.1", "user01");
    }

    @Test
    @DisplayName("제한 IP이면 로그인을 거부한다")
    void rejectLimitedAccessIp() {
        when(loginCoreMapper.existsLimitedAccessIp("127.0.0.1", "user01")).thenReturn(true);

        assertThatThrownBy(() -> policy.verify(context("127.0.0.1", user())))
            .isInstanceOf(LoginException.class)
            .hasMessage("제한된 접속 IP입니다.");
    }

    private LoginCompletionContext<TestCredential> context(String clientIp, LoginUser user) {
        return new LoginCompletionContext<>(user, new TestCredential(clientIp));
    }

    private LoginUser user() {
        return new LoginUser("user01", "사용자", "01012345678", "password", true, 0, false, null);
    }

    private record TestCredential(String clientIp) implements LoginCompletionCredential {

        @Override
        public String password() {
            return null;
        }

        @Override
        public String deviceUuid() {
            return null;
        }

        @Override
        public boolean isPasswordAuth() {
            return false;
        }

        @Override
        public boolean isDeviceAuth() {
            return false;
        }
    }
}
