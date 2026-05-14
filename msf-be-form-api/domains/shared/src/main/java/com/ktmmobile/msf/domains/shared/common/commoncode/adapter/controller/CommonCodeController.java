package com.ktmmobile.msf.domains.shared.common.commoncode.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesResponse;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;

@RequiredArgsConstructor
@RequestMapping("/api/shared/common/common-codes")
@RestController
public class CommonCodeController {

    private final CommonCodeReader commonCodeReader;

    @PostMapping("/list")
    public CommonResponse<CommonCodesResponse> getCommonCodeList(
        @RequestBody @Valid CommonCodesRequest request
    ) {
        return ResponseUtils.ok(CommonCodesResponse.toResponse(
            commonCodeReader.getCommonCodes(request),
            request.shouldIncludeDetail()
        ));
    }
}
