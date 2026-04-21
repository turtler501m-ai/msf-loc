package com.ktmmobile.msf.domains.shared.form.common.application.port.in;

import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsGroupResponse;

public interface TermsReader {

    TermsGroupResponse getListTerms(TermsCondition condition);
}
