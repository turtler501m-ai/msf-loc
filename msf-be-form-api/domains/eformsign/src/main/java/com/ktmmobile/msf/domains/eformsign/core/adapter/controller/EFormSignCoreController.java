package com.ktmmobile.msf.domains.eformsign.core.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.eformsign.core.application.dto.EFormSignCoreApiTokenResponse;
import com.ktmmobile.msf.domains.eformsign.core.application.port.in.EFormSignCoreReader;

@RestController
@RequestMapping("/api/form/common")
@RequiredArgsConstructor
public class EFormSignCoreController {

    private final EFormSignCoreReader reader;

    @PostMapping("/eform-api-token/get")
    public CommonResponse<EFormSignCoreApiTokenResponse> getEformSignApiToken() {
        return ResponseUtils.ok(reader.getEformSignApiToken());
    }

}
