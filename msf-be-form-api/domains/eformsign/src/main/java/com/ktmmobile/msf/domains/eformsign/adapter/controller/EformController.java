package com.ktmmobile.msf.domains.eformsign.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.eformsign.application.dto.EformApiTokenResponse;
import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwResponse;
import com.ktmmobile.msf.domains.eformsign.application.port.in.EformReader;

@RestController
@RequestMapping("/api/form/common")
@RequiredArgsConstructor
public class EformController {

    // private final FormWriter writer;
    private final EformReader reader;

    /*@PostMapping("/files/form-submit")
    public CommonResponse<List<FormResponse>> formSubmit(@RequestBody FormRequest request) {
        return ResponseUtils.ok(writer.formSubmit(request));
    }*/

    @PostMapping("/eform-api-token/get")
    public CommonResponse<EformApiTokenResponse> getEformApiToken() {
        return ResponseUtils.ok(reader.getEformApiToken());
    }

    @PostMapping("/verifyFormPw/get")
    public CommonResponse<VerifyFormPwResponse> verifyFormPw(@RequestBody VerifyFormPwRequest request) {
        return ResponseUtils.ok(reader.verifyFormPw(request));
    }


}
