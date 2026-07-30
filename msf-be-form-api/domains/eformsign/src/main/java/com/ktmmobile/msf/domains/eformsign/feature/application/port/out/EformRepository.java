package com.ktmmobile.msf.domains.eformsign.feature.application.port.out;

import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestJoinForm;

public interface EformRepository {

    Boolean verifyNewChangeFormPw(VerifyFormPwRequest request);

    Boolean verifyServiceChangeFormPw(VerifyFormPwRequest request);

    Boolean verifyOwnerChangeFormPw(VerifyFormPwRequest request);

    Boolean verifyTerminationFormPw(VerifyFormPwRequest request);

    Integer registryRequestJoinForm(CompletedRequestJoinForm joinForm);

    Integer modifyRequestJoinForm(CompletedRequestJoinForm joinForm);

    String getR14DocumentIdOfServiceChange(Long requestKey);
}
