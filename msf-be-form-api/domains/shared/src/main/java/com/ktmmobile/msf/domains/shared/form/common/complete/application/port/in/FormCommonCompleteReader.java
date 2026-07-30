package com.ktmmobile.msf.domains.shared.form.common.complete.application.port.in;

import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormCondition;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormResponse;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestForm;

public interface FormCommonCompleteReader {

    CompletedRequestForm getCompletedForm(CompletedFormCondition condition);

    CompletedFormResponse getCompletedFormResponse(CompletedFormCondition condition);

    CompletedFormResponse.CompletedFormMobileInfo generateFirstMobileInfo(CompletedRequestForm form);

    CompletedFormResponse.CompletedFormMobileInfo generateSecondMobileInfo(CompletedRequestForm form);
}
