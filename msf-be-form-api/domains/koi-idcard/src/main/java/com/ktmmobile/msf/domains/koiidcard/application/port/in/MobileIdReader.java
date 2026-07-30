package com.ktmmobile.msf.domains.koiidcard.application.port.in;

import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationSaveResponse;

public interface MobileIdReader {

    MobileIdCardQrResponse mobileIdCardQr(MobileIdCardQrRequest request);

    MobileIdVerificationSaveResponse getVerificationResult(MobileIdVerificationRequest request);
}
