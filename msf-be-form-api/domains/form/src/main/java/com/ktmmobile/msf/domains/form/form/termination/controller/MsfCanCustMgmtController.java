package com.ktmmobile.msf.domains.form.form.termination.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.DetailDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListResDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessResVO;
import com.ktmmobile.msf.domains.form.form.termination.service.MsfCanCustMgmtSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/msf/admin/cancel", "/api/msf/admin/application"})
public class MsfCanCustMgmtController {

    private final MsfCanCustMgmtSvc msfCanCustMgmtSvc;

    @PostMapping("/list")
    public CommonResponse<ListResDto> list(@RequestBody ListReqDto req) {
        return ResponseUtils.ok(msfCanCustMgmtSvc.list(req));
    }

    @PostMapping("/get")
    public CommonResponse<DetailDto> get(@RequestBody ProcessReqDto req) {
        return ResponseUtils.ok(msfCanCustMgmtSvc.get(req));
    }

    @PostMapping("/status/check")
    public CommonResponse<FormResponse<ProcessResVO>> statusCheck(@RequestBody ProcessReqDto req) {
        return ResponseUtils.ok(msfCanCustMgmtSvc.statusCheck(req));
    }

    @PostMapping("/complete")
    public CommonResponse<FormResponse<ProcessResVO>> complete(@RequestBody ProcessReqDto req) {
        return ResponseUtils.ok(msfCanCustMgmtSvc.complete(req));
    }

    @PostMapping("/revert")
    public CommonResponse<FormResponse<ProcessResVO>> revert(@RequestBody ProcessReqDto req) {
        return ResponseUtils.ok(msfCanCustMgmtSvc.revert(req));
    }
}
