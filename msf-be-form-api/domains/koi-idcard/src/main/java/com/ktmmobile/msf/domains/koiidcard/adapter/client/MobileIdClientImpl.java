package com.ktmmobile.msf.domains.koiidcard.adapter.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.koiidcard.adapter.client.httpClient.KoiIdCardHttpClient;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationRequest;
import com.ktmmobile.msf.domains.koiidcard.application.port.out.MobileIdClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class MobileIdClientImpl implements MobileIdClient {

    private final KoiIdCardHttpClient koiIdCardHttpClient;

    @Override
    public MobileIdCardQrHttpResponse mobileIdCardQr(MobileIdCardQrRequest request) {
        return koiIdCardHttpClient.mobileIdCardQr(request);
    }

    @Override
    public MobileIdVerificationHttpResponse fetchVerificationResult(MobileIdVerificationRequest request) {
        return koiIdCardHttpClient.fetchVerificationResult(request);
    }
}
