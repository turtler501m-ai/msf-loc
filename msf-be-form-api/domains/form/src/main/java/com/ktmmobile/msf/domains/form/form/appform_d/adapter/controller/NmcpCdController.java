package com.ktmmobile.msf.domains.form.form.appform_d.adapter.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.in.NmcpCdReader;
import com.ktmmobile.msf.external.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.external.websecurity.web.util.response.ResponseUtils;

@RestController
@RequiredArgsConstructor
public class NmcpCdController {

    private final NmcpCdReader nmcpCdReader;

    //@GetMapping("/getNmcpCodeList")
    @PostMapping("/mcpcode/list")
    public CommonResponse<List<NmcpCdResponse>> getCodeList(@RequestBody NmcpCdCondition condition) {
        return ResponseUtils.ok(nmcpCdReader.getCodeList(condition));
    }
}
