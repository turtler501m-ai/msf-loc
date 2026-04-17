package com.ktmmobile.msf.domains.form.form.appform_d.adapter.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.in.MspCdReader;

@RestController
@RequiredArgsConstructor
public class MspCdController {

    private final MspCdReader mspCdReader;

    @PostMapping("/mspcode/list")
    @Validated
    public CommonResponse<List<MspCdResponse>> getCodeList(@RequestBody MspCdCondition condition) {
        return ResponseUtils.ok(mspCdReader.getCodeList(condition));
    }
}
