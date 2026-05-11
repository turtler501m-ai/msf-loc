package com.ktmmobile.msf.domains.eformsign.application.port.in;

import com.ktmmobile.msf.domains.eformsign.application.dto.EformApiTokenResponse;
import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwResponse;

public interface EformReader {

    EformApiTokenResponse getEformApiToken();

    VerifyFormPwResponse verifyFormPw(VerifyFormPwRequest request);
}
