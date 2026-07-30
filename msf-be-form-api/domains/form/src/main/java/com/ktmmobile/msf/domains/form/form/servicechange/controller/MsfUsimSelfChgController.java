package com.ktmmobile.msf.domains.form.form.servicechange.controller;


import java.io.IOException;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Request;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Response;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfUsimChangeSvcServiceImpl;

@RestController
@RequiredArgsConstructor
public class MsfUsimSelfChgController {

    private final MsfUsimChangeSvcServiceImpl msfUsimChangeSvcService;

    /**
     * 유심 변경 처리(UC0)
     *
     *
     * ASIS: /mypage/usimSelfChgAjax.do
     * TOBE: POST /api/form/servicechange/usim/change
     *
     * @param req ncn(신청자 계약번호)/ctn
     * @return InsuranceProcessResponse
     */
    @PostMapping("/api/form/servicechange/usim/change")
    public CommonResponse<FormResponse<UsimChangeUC0Response>> usimChange(@RequestBody @Valid UsimChangeUC0Request request) throws IOException {
        return ResponseUtils.ok(msfUsimChangeSvcService.usimChange(request));
    }

    @PostMapping("/api/form/servicechange/usim/check")
    public CommonResponse<FormResponse<UsimChangeUC0Response>> usimCheck(@RequestBody @Valid UsimChangeUC0Request request) throws IOException {
        return ResponseUtils.ok(msfUsimChangeSvcService.usimCheck(request));
    }

}
