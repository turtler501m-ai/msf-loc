package com.ktmmobile.msf.domains.shared.form.common.terms.adapter.controller;

import java.util.List;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheRequest;
import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheResponse;
import com.ktmmobile.msf.domains.cache.terms.application.port.in.TermsCacheReader;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsCondition;

@RestController
@RequestMapping("/api/shared/form/common/terms")
@RequiredArgsConstructor
public class TermsController {

    private final TermsCacheReader termsCacheReader;

    @PostMapping("/list")
    public CommonResponse<List<TermsCacheResponse>> getListTerms(@RequestBody @Valid TermsCondition condition) {
        List<TermsCacheRequest.SpecTerms> requestSpecList = generateRequestSpecList(condition);
        TermsCacheRequest request = TermsCacheRequest.of(condition.groupCode(), requestSpecList);
        List<TermsCacheResponse> list = termsCacheReader.getListTerms(request);

        return ResponseUtils.ok(list);
    }

    @PostMapping("/content")
    public CommonResponse<TermsCacheResponse> getTermsContent(@RequestBody @Valid TermsCondition condition) {
        List<TermsCacheRequest.SpecTerms> requestSpecList = generateRequestSpecList(condition);
        TermsCacheRequest request = TermsCacheRequest.of(condition.groupCode(), condition.code(), condition.contentGroup(), condition.contentCode(), condition.version(), requestSpecList);
        return ResponseUtils.ok(termsCacheReader.getTermsContent(request));
    }

    private List<TermsCacheRequest.SpecTerms> generateRequestSpecList(TermsCondition condition) {
        List<TermsCondition.SpecTerms> specList = condition.specTermsList();
        List<TermsCacheRequest.SpecTerms> requestSpecList = null;
        if (specList != null && !specList.isEmpty()) {
            requestSpecList = specList.stream().map(v -> TermsCacheRequest.SpecTerms.of(v.code(), v.specType(), v.specCode(), v.specName())).toList();
        }
        return requestSpecList;
    }
}
