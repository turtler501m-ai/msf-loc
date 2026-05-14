package com.ktmmobile.msf.domains.cache.terms.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheRequest;
import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheResponse;

public interface TermsCacheReader {

    List<TermsCacheResponse> getListTerms(TermsCacheRequest request);

    TermsCacheResponse getTermsContent(TermsCacheRequest request);
}
