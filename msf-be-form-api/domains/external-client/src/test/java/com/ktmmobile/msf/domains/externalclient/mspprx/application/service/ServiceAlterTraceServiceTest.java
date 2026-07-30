package com.ktmmobile.msf.domains.externalclient.mspprx.application.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.MsfUserDetails;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceId;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.ServiceAlterTraceRepository;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.entity.ServiceAlterTrace;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("서비스 변경 이력 서비스")
class ServiceAlterTraceServiceTest {

    private final ServiceAlterTraceRepository repository = mock(ServiceAlterTraceRepository.class);
    private final ServiceAlterTraceService service = new ServiceAlterTraceService(repository);

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("로그인 세션이 없으면 NCN 기본값과 SYSTEM 사용자로 저장한다")
    void recordDefaultNcnWhenNcnIsBlank() {
        when(repository.recordTrace(org.mockito.ArgumentMatchers.any()))
            .thenReturn(new ServiceAlterTraceId("1000000001", "20260727", 1));

        service.recordTrace(ServiceAlterTraceRequest.builder()
            .eventCd("X77")
            .userId("request-user")
            .build());

        ArgumentCaptor<ServiceAlterTrace> traceCaptor = ArgumentCaptor.forClass(ServiceAlterTrace.class);
        verify(repository).recordTrace(traceCaptor.capture());

        assertThat(traceCaptor.getValue().getNcn()).isEqualTo("0000000000");
        assertThat(traceCaptor.getValue().getUserId()).isEqualTo("SYSTEM");
    }

    @Test
    @DisplayName("서비스 변경 이력은 로그인 세션 사용자로 저장한다")
    void recordLoginSessionUserId() {
        when(repository.recordTrace(org.mockito.ArgumentMatchers.any()))
            .thenReturn(new ServiceAlterTraceId("1000000001", "20260727", 1));
        setLoginUser("session-user");

        service.recordTrace(ServiceAlterTraceRequest.builder()
            .eventCd("X77")
            .userId("request-user")
            .build());

        ArgumentCaptor<ServiceAlterTrace> traceCaptor = ArgumentCaptor.forClass(ServiceAlterTrace.class);
        verify(repository).recordTrace(traceCaptor.capture());

        assertThat(traceCaptor.getValue().getUserId()).isEqualTo("session-user");
    }

    private void setLoginUser(String userId) {
        MsfUser user = mock(MsfUser.class);
        MsfUserDetails userDetails = mock(MsfUserDetails.class);
        when(user.getUserId()).thenReturn(userId);
        when(userDetails.getUser()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userDetails, null, List.of())
        );
    }
}
