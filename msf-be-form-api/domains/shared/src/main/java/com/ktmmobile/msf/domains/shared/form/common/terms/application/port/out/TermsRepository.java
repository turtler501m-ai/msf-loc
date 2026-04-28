package com.ktmmobile.msf.domains.shared.form.common.terms.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsContentRequest;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsContent;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsGroup;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsItem;

public interface TermsRepository {

    TermsGroup getTermsGroup(TermsCondition condition);

    List<TermsItem> getListTerms(TermsCondition condition);

    List<TermsContent> getListTermsContent(List<TermsContentRequest> termsContentRequests);
}
