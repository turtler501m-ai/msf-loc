package com.ktmmobile.msf.commons.websecurity.security.auth.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.security.auth.property.ExternalServiceAuthenticationProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.property.ExternalServiceAuthenticationServiceProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("외부 서비스 인증 필터")
@ExtendWith(OutputCaptureExtension.class)
class ExternalServiceAuthenticationFilterTest {

    private static final String API_KEY_HEADER = "X-Service-Api-Key";

    private final AuthenticationEntryPoint authenticationEntryPoint = (_, response, authException) ->
        response.sendError(MockHttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("보호 대상 URL이 아니면 인증을 수행하지 않는다")
    void skipWhenPathIsNotProtected() throws ServletException, IOException {
        ExternalServiceAuthenticationFilter filter = createFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/public/ping");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RecordingFilterChain filterChain = new RecordingFilterChain();

        filter.doFilter(request, response, filterChain);

        assertThat(filterChain.called).isTrue();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("보호 대상 URL에서 API key 헤더가 없으면 401을 응답한다")
    void rejectWhenApiKeyHeaderIsMissing() throws ServletException, IOException {
        ExternalServiceAuthenticationFilter filter = createFilter();
        MockHttpServletRequest request = protectedRequest("10.10.1.25");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RecordingFilterChain filterChain = new RecordingFilterChain();

        filter.doFilter(request, response, filterChain);

        assertThat(filterChain.called).isFalse();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_UNAUTHORIZED);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("서비스명이 없으면 401을 응답한다")
    void rejectWhenServiceNameIsMissing() throws ServletException, IOException {
        ExternalServiceAuthenticationFilter filter = createFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/external-services/");
        request.setRemoteAddr("10.10.1.25");
        request.addHeader(API_KEY_HEADER, "form-api-key");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RecordingFilterChain filterChain = new RecordingFilterChain();

        filter.doFilter(request, response, filterChain);

        assertThat(filterChain.called).isFalse();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_UNAUTHORIZED);
        assertThat(response.getErrorMessage()).isEqualTo("확인되지 않은 클라이언트입니다.");
    }

    @Test
    @DisplayName("설정에 없는 서비스명이면 401을 응답한다")
    void rejectWhenServiceNameIsNotConfigured() throws ServletException, IOException {
        ExternalServiceAuthenticationFilter filter = createFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/external-services/unknown-client/sample");
        request.setRemoteAddr("10.10.1.25");
        request.addHeader(API_KEY_HEADER, "form-api-key");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RecordingFilterChain filterChain = new RecordingFilterChain();

        filter.doFilter(request, response, filterChain);

        assertThat(filterChain.called).isFalse();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_UNAUTHORIZED);
        assertThat(response.getErrorMessage()).isEqualTo("확인되지 않은 클라이언트입니다.");
    }

    @Test
    @DisplayName("API key와 IP allowlist가 모두 유효하면 외부 서비스 인증을 생성한다")
    void authenticateWhenApiKeyAndIpAddressAreValid() throws ServletException, IOException {
        ExternalServiceAuthenticationFilter filter = createFilter();
        MockHttpServletRequest request = protectedRequest("10.10.1.25");
        request.addHeader(API_KEY_HEADER, "form-api-key");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RecordingFilterChain filterChain = new RecordingFilterChain();

        filter.doFilter(request, response, filterChain);

        assertThat(filterChain.called).isTrue();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
        assertThat(SecurityContextHolder.getContext().getAuthentication())
            .extracting(authentication -> authentication.getName())
            .isEqualTo("form-api");
        assertThat(SecurityContextHolder.getContext().getAuthentication().getAuthorities())
            .extracting("authority")
            .containsExactly("ROLE_EXTERNAL_SERVICE");
        MsfUser user = AuthenticationUtils.getUser();
        assertThat(user.getUserId()).isEqualTo("form-api");
        assertThat(user.getUserName()).isEqualTo("Form API");
        assertThat(user.getUserType()).isEqualTo(UserType.EXTERNAL_SERVICE_USER);
    }

    @Test
    @DisplayName("allowlist에 loopback IP가 있으면 인증한다")
    void authenticateWithLoopbackIpAllowlist() throws ServletException, IOException {
        ExternalServiceAuthenticationFilter filter = createFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/external-services/local-client/sample");
        request.setRemoteAddr("127.0.0.1");
        request.addHeader(API_KEY_HEADER, "local-api-key");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RecordingFilterChain filterChain = new RecordingFilterChain();

        filter.doFilter(request, response, filterChain);

        assertThat(filterChain.called).isTrue();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("local-client");
    }

    @Test
    @DisplayName("API key가 맞아도 IP allowlist에 없으면 401을 응답한다")
    void rejectWhenClientIpIsNotAllowed(CapturedOutput output) throws ServletException, IOException {
        ExternalServiceAuthenticationFilter filter = createFilter();
        MockHttpServletRequest request = protectedRequest("192.168.10.10");
        request.addHeader(API_KEY_HEADER, "form-api-key");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RecordingFilterChain filterChain = new RecordingFilterChain();

        filter.doFilter(request, response, filterChain);

        assertThat(filterChain.called).isFalse();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_UNAUTHORIZED);
        assertThat(response.getErrorMessage()).isEqualTo("허용되지 않은 IP입니다.");
        assertThat(output)
            .contains("외부 서비스 인증 허용 IP 불일치")
            .contains("serviceName=form-api")
            .contains("clientIp=192.168.10.10");
    }

    @Test
    @DisplayName("서비스명의 API key와 인증 헤더가 다르면 401을 응답한다")
    void rejectWhenServiceApiKeyDoesNotMatch() throws ServletException, IOException {
        ExternalServiceAuthenticationFilter filter = createFilter();
        MockHttpServletRequest request = protectedRequest("10.10.1.25");
        request.addHeader(API_KEY_HEADER, "admin-api-key");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RecordingFilterChain filterChain = new RecordingFilterChain();

        filter.doFilter(request, response, filterChain);

        assertThat(filterChain.called).isFalse();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_UNAUTHORIZED);
        assertThat(response.getErrorMessage()).isEqualTo("API Key가 유효하지 않습니다.");
    }

    private MockHttpServletRequest protectedRequest(String remoteAddr) {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/external-services/form-api/cache/reload");
        request.setRemoteAddr(remoteAddr);
        return request;
    }

    private ExternalServiceAuthenticationFilter createFilter() {
        ExternalServiceAuthenticationProperties properties = new ExternalServiceAuthenticationProperties(
            API_KEY_HEADER,
            Map.of(
                "form-api", new ExternalServiceAuthenticationServiceProperties("form-api-key", "Form API", List.of("10.10.1.0/24")),
                "admin-api", new ExternalServiceAuthenticationServiceProperties("admin-api-key", "Admin API", List.of("10.20.1.10")),
                "local-client", new ExternalServiceAuthenticationServiceProperties("local-api-key", "Local Client", List.of("127.0.0.1"))
            )
        );
        return new ExternalServiceAuthenticationFilter(properties, authenticationEntryPoint);
    }

    private static class RecordingFilterChain implements FilterChain {

        private boolean called;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) {
            called = true;
        }
    }
}
