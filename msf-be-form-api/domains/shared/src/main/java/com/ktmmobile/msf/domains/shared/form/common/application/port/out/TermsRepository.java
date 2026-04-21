package com.ktmmobile.msf.domains.shared.form.common.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsContent;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsGroup;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsItem;

public interface TermsRepository {

    TermsGroup getTermsGroup(TermsCondition condition);

    List<TermsItem> getListTerms(TermsCondition condition);

    List<TermsContent> getListTermsContent(List<TermsItem> itemList);
}
