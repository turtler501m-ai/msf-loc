package com.ktmmobile.msf.domains.form.form.appform_d.adapter.controller;


import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.in.SmartCdReader;

@RestController
@RequiredArgsConstructor
public class SmartCdController {

    private final SmartCdReader smartCdReader;

    @PostMapping("/smartcode/list")
    public CommonResponse<List<SmartCdResponse>> getCodeList(@RequestBody SmartCdCondition condition) {
        return ResponseUtils.ok(smartCdReader.getCodeList(condition));
    }
}
