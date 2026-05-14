package com.ktmmobile.msf.domains.cache.terms.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

public interface TermsCacheRepository {

    List<TermsType> getListTermsType(List<String> groupList);

    List<TermsDetail> getListTermsDetail(List<TermsDetail> detailList);

    // TermsGroup getTermsGroup(TermsCondition condition);
    //
    // List<TermsItem> getListTerms(TermsCondition condition);
    //
    // List<TermsContent> getListTermsContent(List<TermsContentRequest> termsContentRequests);
    //
    // TermsContent getTermsContent(TermsContentRequest request);
}
