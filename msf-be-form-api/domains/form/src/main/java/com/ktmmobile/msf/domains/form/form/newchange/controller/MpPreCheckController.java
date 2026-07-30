package com.ktmmobile.msf.domains.form.form.newchange.controller;

import java.util.Map;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PreCheckRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PreCheckResponse;
import com.ktmmobile.msf.domains.form.form.newchange.service.MpPreCheckService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class MpPreCheckController {

    private final MpPreCheckService mpPreCheckService; //사전체크

    /**
     * 개통전 사전체크 (신규가입/번호이동)
     */
    //parameter :: 계좌점유키값(reqUniqId), globalNoNp1(번호이동사전동의), globalNoNp3(번호이동사전동의결과)
    //appformReqDto 파라미터 검토필요 >> MsfRequestDto 로 변경하고 위 파라미터는 Dto 에 추가하든지 해야할듯함. 추후!!!
    //@RequestMapping(value = "/appform/reqPreOpenCheckAjax.do")
    //AS-IS : 다음단계 진행 (isValidateStep2) 시 개통사전체크 진행함.
    //TO-BE : 고객단계 진행 시 개통사전체크 진행을 한다면 상품단계의 임시저장일 경우에는 무조건 재진행을 해야하는데,
    @PostMapping("/newchange/preopen/req")
    public CommonResponse<PreCheckResponse> reqPreOpenCheck(@RequestBody @Valid PreCheckRequest request) {
        return ResponseUtils.ok(mpPreCheckService.reqPreCheckOpen(request));
    }

    /**
     * 개통전 사전체크 확인 (신규가입/번호이동)
     */
    //@RequestMapping(value = "/appform/conPreCheckAjax.do")
    @PostMapping("/newchange/preopen/con")
    public CommonResponse<Map<String, Object>> conPreOpenCheck(@RequestBody @Valid NewChangeInfoRequest request) {
        return null;
        //return ResponseUtils.ok(formCommService.conPreOpenCheck(request));
    }
}
