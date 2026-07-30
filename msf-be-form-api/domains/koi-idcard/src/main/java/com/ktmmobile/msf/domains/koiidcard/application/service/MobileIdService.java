package com.ktmmobile.msf.domains.koiidcard.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationSaveResponse;
import com.ktmmobile.msf.domains.koiidcard.application.port.in.MobileIdReader;
import com.ktmmobile.msf.domains.koiidcard.application.port.out.MobileIdClient;
import com.ktmmobile.msf.domains.koiidcard.support.util.MobileIdUtils;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MobileIdService implements MobileIdReader {

    private final MobileIdClient mobileIdClient;
    private final MobileIdUtils mobileIdUtils;

    @Override
    public MobileIdCardQrResponse mobileIdCardQr(MobileIdCardQrRequest request) {
        MobileIdCardQrHttpResponse response = mobileIdClient.mobileIdCardQr(request);
        return mobileIdUtils.decode(response.data());
    }

    @Override
    public MobileIdVerificationSaveResponse getVerificationResult(
        MobileIdVerificationRequest request
    ) {
        MobileIdVerificationHttpResponse httpResponse =
            mobileIdClient.fetchVerificationResult(request);

        if (httpResponse == null) {
            return MobileIdVerificationSaveResponse.processing();
        }

        if (!Boolean.parseBoolean(httpResponse.result())) {
            return MobileIdVerificationSaveResponse.processing();
        }

        if (httpResponse.data() == null || httpResponse.data().isBlank()) {
            return MobileIdVerificationSaveResponse.processing();
        }

        MobileIdCardHttpResponse response =
            mobileIdUtils.decodeVerificationResult(httpResponse.data());

        return MobileIdVerificationSaveResponse.from(response);
    }
}
