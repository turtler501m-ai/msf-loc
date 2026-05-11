package com.ktmmobile.msf.domains.eformsign.application.port.out;

import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwRequest;

public interface EformRepository {

    Boolean verifyFormPw(VerifyFormPwRequest request, String authField, String requestResource);
}
