package com.ktmmobile.msf.domains.form.form.newchange.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeEformInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeEformService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class NewChangeEformController {

    private final NewChangeEformService newChangeEformService; //신규/변경 eForm

    /**
     * eForm 생성을 위한 데이타 SET
     * >> "신청서확인" 버튼 클릭해서 임시저장 후 결과값에 따라 eForm action 을 프론트에서 호출
     * >> 프론트에서 호출하면 직접호출할 수도 있을텐데 그에 대한 방어로직 필요함.
     */
    @PostMapping("/newchange/eform/set")
    public CommonResponse<NewChangeEformInfoResponse> eformNewChangeSet(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeEformService.getNewChangeRequestEformInfo(request));
    }

}
