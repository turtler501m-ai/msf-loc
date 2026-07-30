package com.ktmmobile.msf.domains.externalclient.nice.adapter.client;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.util.MultiValueMap;

import com.ktmmobile.msf.domains.externalclient.nice.adapter.client.httpclient.NiceApiHttpClient;
import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckRequest;
import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckResponse;
import com.ktmmobile.msf.domains.externalclient.nice.application.port.out.NiceApiLogRepository;
import com.ktmmobile.msf.domains.externalclient.nice.domain.entity.NiceApiAccountCheckLog;
import com.ktmmobile.msf.domains.externalclient.nice.support.builder.NiceApiRequestBuilder;
import com.ktmmobile.msf.domains.externalclient.common.property.ExternalServiceProperties;
import com.ktmmobile.msf.domains.externalclient.common.property.ServiceProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("NICE API Client 구현체")
class NiceApiClientImplTest {

    private static final String NICE_UID = "Nktisc";
    private static final String SVC_PWD = "svc-password";

    private final NiceApiHttpClient httpClient = mock(NiceApiHttpClient.class);
    private final NiceApiLogRepository logRepository = mock(NiceApiLogRepository.class);
    private final NiceApiClientImpl client = new NiceApiClientImpl(
        httpClient,
        new NiceApiRequestBuilder(externalServiceProperties()),
        logRepository
    );

    @Test
    @DisplayName("계좌 실명확인 요청을 레거시 rlnmCheck 파라미터와 동일한 form-urlencoded 값으로 전달한다")
    @SuppressWarnings("unchecked")
    void sendAccountCheckFormParameters() {
        when(httpClient.checkAccount(any())).thenReturn("2026042000001|0000|정상처리|");

        NiceApiAccountCheckResponse response = client.checkAccount(requestBuilder()
            .orderNo("2026042000001")
            .build());

        assertThat(response.success()).isTrue();
        assertThat(response.orderNo()).isEqualTo("2026042000001");
        assertThat(response.resultCode()).isEqualTo("0000");
        assertThat(response.resultMessage()).isEqualTo("정상처리");

        ArgumentCaptor<MultiValueMap<String, String>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).checkAccount(formCaptor.capture());

        MultiValueMap<String, String> form = formCaptor.getValue();
        assertThat(form.getFirst("niceUid")).isEqualTo(NICE_UID);
        assertThat(form.getFirst("svcPwd")).isEqualTo(SVC_PWD);
        assertThat(form.getFirst("service")).isEqualTo("1");
        assertThat(form.getFirst("strGbn")).isEqualTo("1");
        assertThat(form.getFirst("strResId")).isEqualTo("9001011234567");
        assertThat(form.getFirst("strNm")).isEqualTo("홍길동");
        assertThat(form.getFirst("strBankCode")).isEqualTo("004");
        assertThat(form.getFirst("strAccountNo")).isEqualTo("1234567890");
        assertThat(form.getFirst("svcGbn")).isEqualTo("5");
        assertThat(form.getFirst("strOrderNo")).isEqualTo("2026042000001");
        assertThat(form.getFirst("svc_cls")).isEqualTo("1");
        assertThat(form.getFirst("inq_rsn")).isEqualTo("10");
        assertThat(form.getFirst("seq")).isEqualTo("0000001");

        NiceApiAccountCheckLog log = savedLog();
        assertThat(log.certifyType()).isEqualTo("P");
        assertThat(log.authTypeCode()).isEqualTo("A");
        assertThat(log.name()).isEqualTo("홍길동");
        assertThat(log.authBirth()).isEqualTo("9001011234567");
        assertThat(log.bankCode()).isEqualTo("004");
        assertThat(log.accountNo()).isEqualTo("1234567890");
        assertThat(log.result()).isEqualTo("O");
        assertThat(log.errorCode()).isNull();
    }

    @Test
    @DisplayName("NICE 실패 응답은 NICE가 내려준 코드와 메시지를 그대로 반환한다")
    void parseFailureResponse() {
        when(httpClient.checkAccount(any())).thenReturn("202606045079647809|B102|계좌오류|");

        NiceApiAccountCheckResponse response = client.checkAccount(requestBuilder().build());

        assertThat(response.success()).isFalse();
        assertThat(response.orderNo()).isEqualTo("202606045079647809");
        assertThat(response.resultCode()).isEqualTo("B102");
        assertThat(response.resultMessage()).isEqualTo("계좌오류");
        assertThat(response.rawResponse()).isEqualTo("202606045079647809|B102|계좌오류|");
        assertThat(response.values()).containsExactly("202606045079647809", "B102", "계좌오류", "");

        NiceApiAccountCheckLog log = savedLog();
        assertThat(log.result()).isEqualTo("X");
        assertThat(log.errorCode()).isEqualTo("B102");
    }

    @Test
    @DisplayName("계좌주명은 레거시와 동일하게 UTF-8 60바이트 이내로 전달한다")
    @SuppressWarnings("unchecked")
    void truncateAccountOwnerNameByUtf8Bytes() {
        when(httpClient.checkAccount(any())).thenReturn("2026042000001|0000|정상처리|");

        client.checkAccount(requestBuilder()
            .name("가".repeat(30))
            .build());

        ArgumentCaptor<MultiValueMap<String, String>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).checkAccount(formCaptor.capture());

        String accountOwnerName = formCaptor.getValue().getFirst("strNm");
        assertThat(accountOwnerName).isEqualTo("가".repeat(20));
        assertThat(accountOwnerName.getBytes(StandardCharsets.UTF_8)).hasSizeLessThanOrEqualTo(60);
    }

    @Test
    @DisplayName("NICE 호출 자체가 실패해도 실패 이력을 저장하고 예외를 그대로 전달한다")
    void saveFailureLogWhenHttpClientThrowsException() {
        when(httpClient.checkAccount(any())).thenThrow(new IllegalStateException("connection timeout"));

        assertThatThrownBy(() -> client.checkAccount(requestBuilder().build()))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("connection timeout");

        NiceApiAccountCheckLog log = savedLog();
        assertThat(log.result()).isEqualTo("X");
        assertThat(log.errorCode()).isEqualTo("FAIL");
    }

    private NiceApiAccountCheckRequest.NiceApiAccountCheckRequestBuilder requestBuilder() {
        return NiceApiAccountCheckRequest.builder()
            .service("1")
            .customerType("1")
            .identityNumber("9001011234567")
            .name("홍길동")
            .bankCode("004")
            .accountNo("1234567890")
            .serviceType("5")
            .serviceClass("1")
            .inquiryReason("10");
    }

    private ExternalServiceProperties externalServiceProperties() {
        return new ExternalServiceProperties(Map.of(
            "nice-api", new ServiceProperties("https://secure.nuguya.com", Map.of(
                "nice-uid", NICE_UID,
                "svc-pwd", SVC_PWD
            ))
        ));
    }

    private NiceApiAccountCheckLog savedLog() {
        ArgumentCaptor<NiceApiAccountCheckLog> logCaptor = ArgumentCaptor.forClass(NiceApiAccountCheckLog.class);
        verify(logRepository).saveAccountCheckLog(logCaptor.capture());
        return logCaptor.getValue();
    }
}
