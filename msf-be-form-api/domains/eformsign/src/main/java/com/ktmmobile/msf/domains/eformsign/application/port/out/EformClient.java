package com.ktmmobile.msf.domains.eformsign.application.port.out;

import com.ktmmobile.msf.domains.eformsign.application.dto.EformApiTokenResponse;

public interface EformClient {

    EformApiTokenResponse getEformApiToken();
}

