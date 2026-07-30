package com.ktmmobile.msf.domains.eformsign.core.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.eformsign.core.application.dto.EFormSignCoreApiTokenResponse;

public interface EFormSignCoreClient {

    EFormSignCoreApiTokenResponse issueApiToken();

    void cancelDocument(List<String> documentIds);
}
