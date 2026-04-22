package com.ktmmobile.msf.domains.form.form.newchange.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.newchange.dto.*;
import com.ktmmobile.msf.domains.form.form.newchange.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class NewChangeController {

    private final FormCommService formCommService;
    private final NewChangeService newChangeService;

    //generateRequestKey - 추후 삭제해야함.
    @PostMapping("/generateRequestKey")
    public CommonResponse<Long> getGenerateRequestKey() {
        return ResponseUtils.ok(formCommService.generateRequestKey());
    }

    //getCustRequestSeq - 추후 삭제해야함.
    @PostMapping("/getCustRequestSeq")
    public CommonResponse<Long> getCustRequestSeq() {
        return ResponseUtils.ok(formCommService.getCustRequestSeq());
    }

    //generateResNo - 추후 삭제해야함.
    @PostMapping("/generateResNo")
    public CommonResponse<String> getGenerateResNo() {
        return ResponseUtils.ok(formCommService.generateResNo());
    }

    //대리점정보 조회 (조건 : 매장코드)
    @PostMapping("/agent/list")
    public CommonResponse<AgentInfoDto> getAgentInfo(@RequestBody @Valid AgentInfoRequest request) {
        return ResponseUtils.ok(formCommService.getAgentInfo(request.shopOrgnId()));
    }

    //신청서 진입
    @PostMapping("/newchange/get")
    public CommonResponse<MsfNewChangeInfoDto> getNewChangeInfo(@RequestBody @Valid NewChangeInfoCondition condition) {
        return ResponseUtils.ok(newChangeService.getNewChangeInfo(condition));
    }
    /*public CommonResponse<List<MsfNewChangeInfoDto>> getNewChangeInfo(@RequestBody @Valid NewChangeInfoCondition condition) {
        return ResponseUtils.ok(newChangeService.getNewChangeInfo(condition));
    }*/

    //신청서 저장 - 임시저장
    @PostMapping("/newchange/save")
    public CommonResponse<String> registerForm(@RequestBody @Valid NewChangeInfoRequest request) {
        String rtnRequestKey = "";
        rtnRequestKey = newChangeService.saveAppformInfo(request);
        return ResponseUtils.ok(rtnRequestKey);
    }

    //신청서 확인 - 이미지생성,녹취,서명 또는 기 생성 이미지 확인
    //@PostMapping("/newchange/eform/get")

    //신청서 작성완료
    //@PostMapping("/newchange/complete")

    //핸드폰단말일련번호 유효성체크
    //@PostMapping("/newchange/validPhoneSerialNumber")

    //USIM 유효성체크
    //@PostMapping("/newchange/validUsimNumber")

    //eSIM 유효성체크
    //@PostMapping("/newchange/validEsimNumber")

    //신규가입 희망번호 조회
    //@PostMapping("/newchange/validEsimNumber")

    //신규가입 희망번호 예약
    //신규가입 희망번호 취소
    //번호이동 사전동의
    //번호이동 사전동의 결과조회
    //번호이동 납부주장
    //안심보험 목록 조회
    //부가서비스 목록 조회

}
