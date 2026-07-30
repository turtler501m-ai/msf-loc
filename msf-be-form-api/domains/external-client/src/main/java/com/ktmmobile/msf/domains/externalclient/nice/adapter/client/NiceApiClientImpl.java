package com.ktmmobile.msf.domains.externalclient.nice.adapter.client;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.nice.adapter.client.httpclient.NiceApiHttpClient;
import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckRequest;
import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckResponse;
import com.ktmmobile.msf.domains.externalclient.nice.application.port.out.NiceApiClient;
import com.ktmmobile.msf.domains.externalclient.nice.application.port.out.NiceApiLogRepository;
import com.ktmmobile.msf.domains.externalclient.nice.domain.entity.NiceApiAccountCheckLog;
import com.ktmmobile.msf.domains.externalclient.nice.support.builder.NiceApiRequestBuilder;

/**
 * NICE API 외부 연동 어댑터
 */
@Component
@RequiredArgsConstructor
class NiceApiClientImpl implements NiceApiClient {

    private static final String ACCOUNT_AUTH_TYPE_CODE = "A";
    private static final String SUCCESS_RESULT = "O";
    private static final String FAILURE_RESULT = "X";
    private static final String CALL_FAILURE_ERROR_CODE = "FAIL";
    private static final String PC_CERTIFY_TYPE = "P";
    private static final int ERROR_CODE_MAX_LENGTH = 4;

    private final NiceApiHttpClient httpClient;
    private final NiceApiRequestBuilder requestBuilder;
    private final NiceApiLogRepository logRepository;

    @Override
    public NiceApiAccountCheckResponse checkAccount(NiceApiAccountCheckRequest request) {
        String clientIp = resolveClientIp();
        String responseBody;
        try {
            responseBody = httpClient.checkAccount(requestBuilder.buildAccountCheckFormRequest(request).toFormData());
        } catch (RuntimeException e) {
            logRepository.saveAccountCheckLog(accountCheckFailureLog(request, clientIp));
            throw e;
        }

        NiceApiAccountCheckResponse response = NiceApiAccountCheckResponse.from(responseBody);
        logRepository.saveAccountCheckLog(accountCheckLog(request, response, clientIp));
        return response;
    }

    private NiceApiAccountCheckLog accountCheckLog(
        NiceApiAccountCheckRequest request,
        NiceApiAccountCheckResponse response,
        String clientIp
    ) {
        return accountCheckLogBuilder(request, clientIp)
            .result(response.success() ? SUCCESS_RESULT : FAILURE_RESULT)
            .errorCode(response.success() ? null : truncate(response.resultCode()))
            .build();
    }

    private NiceApiAccountCheckLog accountCheckFailureLog(
        NiceApiAccountCheckRequest request,
        String clientIp
    ) {
        return accountCheckLogBuilder(request, clientIp)
            .result(FAILURE_RESULT)
            .errorCode(CALL_FAILURE_ERROR_CODE)
            .build();
    }

    private NiceApiAccountCheckLog.NiceApiAccountCheckLogBuilder accountCheckLogBuilder(
        NiceApiAccountCheckRequest request,
        String clientIp
    ) {
        return NiceApiAccountCheckLog.builder()
            .certifyType(PC_CERTIFY_TYPE)
            .authTypeCode(ACCOUNT_AUTH_TYPE_CODE)
            .ip(clientIp)
            .name(request.name())
            .authBirth(request.identityNumber())
            .bankCode(request.bankCode())
            .accountNo(request.accountNo());
    }

    private String resolveClientIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        return RequestUtils.getClientIp(request);
    }

    private String truncate(String value) {
        if (value == null || value.length() <= ERROR_CODE_MAX_LENGTH) {
            return value;
        }
        return value.substring(0, ERROR_CODE_MAX_LENGTH);
    }

}
