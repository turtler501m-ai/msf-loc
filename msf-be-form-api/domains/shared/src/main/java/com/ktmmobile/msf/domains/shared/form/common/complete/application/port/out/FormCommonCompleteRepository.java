package com.ktmmobile.msf.domains.shared.form.common.complete.application.port.out;

import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormCondition;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestForm;

public interface FormCommonCompleteRepository {

    CompletedRequestForm getCompletedRequestForm(CompletedFormCondition condition);
}
