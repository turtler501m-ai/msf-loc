package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;

public interface OwnerChgRestSvc {

    // 명의 변경 가입 가능 유효성 체크
    OwnerChangeValidationResponse ownerChangeValidation(OwnerChangeValidationRequest request);

}
