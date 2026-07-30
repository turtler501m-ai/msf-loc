package com.ktmmobile.msf.domains.koiidcard.application.port.out;

import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationRequest;

public interface MobileIdClient {

    MobileIdCardQrHttpResponse mobileIdCardQr(MobileIdCardQrRequest request);

    MobileIdVerificationHttpResponse fetchVerificationResult(MobileIdVerificationRequest request);
}
