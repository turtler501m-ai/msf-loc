package com.ktmmobile.msf.domains.form.form.newchange.controller;

import java.util.List;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeDefaultResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeFormSave;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeService;

/**
 * 스마트 신규/변경
 */
@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class NewChangeController {

    private final FormCommService formCommService; //신청서 공통
    private final NewChangeService newChangeService; //신규/변경
    private final NewChangeFormSave newChangeFormSave; //신청서 저장

    /**
     * 신청서 진입 시 초기값
     */
    @PostMapping("/newchange/getdefault")
    public CommonResponse<NewChangeDefaultResponse> getNewChangeDefaultInfo(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.getNewChangeDefaultInfo(request));
    }

    /**
     * 신청서 진입
     */
    @PostMapping("/newchange/get")
    public CommonResponse<NewChangeInfoResponse> getNewChangeInfo(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.getNewChangeRequestInfo(request));
    }

    /**
     * 신청서 저장 - 임시저장
     */
    @PostMapping("/newchange/save")
    public CommonResponse<FormResponse<NewChangeResponse>> registForm(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(newChangeFormSave.saveNewChangeFormInfo(request));
    }

    /**
     * 신청서 작성완료
     */
    @PostMapping("/newchange/complete")
    public CommonResponse<FormResponse<NewChangeResponse>> completeForm(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.completeAppformInfo(request));
    }

    /**
     * 신청서 복사하기 (접수완료에서 진입)
     */
    @PostMapping("/newchange/copyform")
    public CommonResponse<FormResponse<NewChangeResponse>> copyForm(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.copyForm(request));
    }

    /**
     * 대리점정보 조회 (조건 : 로그인사용자 세션의 판매점 조직코드)
     */
    @PostMapping("/agent/list")
    public CommonResponse<List<AgentInfoResponse>> getAgentList(@RequestBody AgentInfoRequest request) {
        return ResponseUtils.ok(formCommService.getAgentList(request));
    }


}
