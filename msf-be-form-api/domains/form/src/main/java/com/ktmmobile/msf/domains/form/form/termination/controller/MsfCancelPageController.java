package com.ktmmobile.msf.domains.form.form.termination.controller;

import java.util.Map;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;
import com.ktmmobile.msf.domains.form.form.termination.service.MsfCancelPageSvc;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MsfCancelPageController {

    private final MsfCancelPageSvc msfCancelPageSvc;

    /** 해지 신청 화면의 대리점 정보를 서비스 계층에서 조회한다. */
    @PostMapping("/api/msf/formTermination/agent/list")
    public CommonResponse<AgentInfoDto> getTerminationAgentInfo(@RequestBody @Valid AgentInfoRequest request) {
        return ResponseUtils.ok(msfCancelPageSvc.getTerminationAgentInfo(request));
    }

    /** 해지 전 잔여 요금과 위약금 정보를 서비스 계층에서 조회한다. */
    @RequestMapping(value = "/remainCharge/list")
    public TerminationRemainChargeResVO getRemainCharge(@RequestBody TerminationRemainChargeReqDto reqDto) {
        return msfCancelPageSvc.getRemainCharge(reqDto);
    }

    /** 해지 화면에서 사용할 가입정보 데이터를 조회한다. */
    @PostMapping(value = "/api/msf/formTermination/myinfo/view")
    public Map<String, Object> getMyinfoView(
        HttpServletRequest request,
        @RequestBody MyPageSearchDto searchVO
    ) {
        return msfCancelPageSvc.getMyinfoView(request, searchVO);
    }

    /** 해지 신청서 작성을 완료하고 신청 데이터를 서비스 계층에서 생성한다. */
    @PostMapping(value = "/api/msf/formTermination/{applicationKey}/complete")
    public TerminationApplyResVO complete(
        @PathVariable("applicationKey") String applicationKey,
        @RequestBody TerminationApplyReqDto reqDto
    ) {
        return msfCancelPageSvc.complete(applicationKey, reqDto);
    }
}
