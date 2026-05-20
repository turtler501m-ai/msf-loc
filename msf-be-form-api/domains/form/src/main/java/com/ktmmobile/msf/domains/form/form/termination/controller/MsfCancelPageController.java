package com.ktmmobile.msf.domains.form.form.termination.controller;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.service.MsfCancelPageSvcImpl;

@RestController
@RequiredArgsConstructor
public class MsfCancelPageController {

    private final MsfCancelPageSvcImpl msfCancelPageSvc;

    /**
     * 해지 신청 화면의 대리점 정보를 서비스 계층에서 조회한다.
     */
    @PostMapping("/api/msf/formTermination/agent/list")
    public CommonResponse<List<AgentInfoResponse>> getTerminationAgentInfo(@RequestBody @Valid AgentInfoRequest request) {
        return ResponseUtils.ok(msfCancelPageSvc.getTerminationAgentInfo(request));
    }
    /*public CommonResponse<AgentInfoDto> getTerminationAgentInfo(@RequestBody @Valid AgentInfoRequest request) {
        return ResponseUtils.ok(msfCancelPageSvc.getTerminationAgentInfo(request));
    }*/

    /**
     * 해지 전 잔여 요금과 위약금 정보를 서비스 계층에서 조회한다.
     */
    @RequestMapping(value = "/remainCharge/list")
    public CommonResponse<FormResponse<TerminationRemainChargeResVO>> getRemainCharge(@RequestBody TerminationRemainChargeReqDto reqDto) {
        return ResponseUtils.ok(msfCancelPageSvc.getRemainCharge(reqDto));
    }

    /**
     * 해지 휴대폰번호 기준으로 진행중인 신청서 존재 여부를 확인한다.
     */
    @PostMapping(value = "/api/msf/formTermination/inprogress/get")
    public CommonResponse<FormResponse<Void>> checkInProgressApplication(@RequestBody Map<String, String> req) {
        return ResponseUtils.ok(msfCancelPageSvc.checkInProgressApplication(req != null ? req.get("mobileNo") : null));
    }

    /**
     * 해지 신청서 작성을 완료하고 신청 데이터를 서비스 계층에서 생성한다.
     */
    @PostMapping(value = "/api/msf/formTermination/{applicationKey}/complete")
    public CommonResponse<FormResponse<TerminationApplyResVO>> complete(
            @PathVariable("applicationKey") String applicationKey,
            @RequestBody TerminationApplyReqDto reqDto
    ) {
        return ResponseUtils.ok(msfCancelPageSvc.complete(applicationKey, reqDto));
    }

    /**
     * 저장된 MSF 신청 데이터를 MCP 테이블로 다시 이관한다.
     */
    @PostMapping(value = "/api/msf/formTermination/{requestKey}/mcp-transfer")
    public CommonResponse<FormResponse<Void>> transferToMcp(@PathVariable("requestKey") Long requestKey) {
        return ResponseUtils.ok(msfCancelPageSvc.transferToMcp(requestKey));
    }
}
