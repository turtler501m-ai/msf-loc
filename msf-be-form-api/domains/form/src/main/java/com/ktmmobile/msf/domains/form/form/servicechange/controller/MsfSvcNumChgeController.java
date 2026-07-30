package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumChgeRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumChgeResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvgNumChgeService;

@Slf4j
@RestController
public class MsfSvcNumChgeController {

    @Autowired
    private MsfSvgNumChgeService numChgeService;

    /**
     * numChgeList
     * 번호목록조회(X31)
     * @param req
     * @return
     */
    @PostMapping("/api/msf/formServiceChange/numChge/list")
    public CommonResponse<FormResponse<NumChgeResponse>> unpauseCheck(@RequestBody NumChgeRequest req) {
        return ResponseUtils.ok(numChgeService.numChgeList(req));
    }

}
