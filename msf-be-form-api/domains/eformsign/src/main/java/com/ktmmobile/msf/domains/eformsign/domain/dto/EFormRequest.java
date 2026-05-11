package com.ktmmobile.msf.domains.eformsign.domain.dto;

import com.ktmmobile.msf.domains.eformsign.application.dto.NameChangeEformRequest;
import com.ktmmobile.msf.domains.eformsign.application.dto.NewChangeEformRequest;
import com.ktmmobile.msf.domains.eformsign.application.dto.ServiceChangeEformRequest;
import com.ktmmobile.msf.domains.eformsign.application.dto.ServiceTerminationEformRequest;

public record EFormRequest(
    String requestKey,
    String formTypeCd,
    NewChangeEformRequest newChange,
    ServiceChangeEformRequest serviceChange,
    ServiceTerminationEformRequest serviceTermination,
    NameChangeEformRequest nameChange
) {

}
