package com.ktmmobile.msf.domains.koiidcard.adapter.client.httpClient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationRequest;


public interface KoiIdCardHttpClient {

    @PostExchange(value = "/ktmmobile/did/qrmpm/v1/application", contentType = MediaType.APPLICATION_JSON_VALUE)
    MobileIdCardQrHttpResponse mobileIdCardQr(@RequestBody MobileIdCardQrRequest request);

    @PostExchange(value = "/ktmmobile/did/mip/trans-info", contentType = MediaType.APPLICATION_JSON_VALUE)
    MobileIdVerificationHttpResponse fetchVerificationResult(@RequestBody MobileIdVerificationRequest request);
}
