package com.ktmmobile.msf.domains.shared.form.common.terms.application.port.in;

import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsGroupResponse;

public interface TermsReader {

    TermsGroupResponse getListTerms(TermsCondition condition);
}
