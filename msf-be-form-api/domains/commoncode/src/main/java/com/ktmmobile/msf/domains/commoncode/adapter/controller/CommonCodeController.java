package com.ktmmobile.msf.domains.commoncode.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesResponse;
import com.ktmmobile.msf.domains.commoncode.application.port.in.GroupCommonCodeReader;

@RestController
@RequestMapping("/api/common-codes")
@RequiredArgsConstructor
public class CommonCodeController {

    private final GroupCommonCodeReader groupCommonCodeReader;

    @PostMapping("/list")
    public CommonResponse<CommonCodesResponse> getCommonCodeList(
        @RequestBody @Valid CommonCodesRequest request
    ) {
        return ResponseUtils.ok(groupCommonCodeReader.getCommonCodes(request.groupIds()));
    }
}
