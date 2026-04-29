package com.ktmmobile.msf.domains.form.form.newchange.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.common.service.ChoiceNumberService;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.service.NumberPortableService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.*;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class NewChangeController {

    private final FormCommService formCommService; //신청서 공통
    private final ChoiceNumberService choiceNumberService; //신규가입 희망번호 조회/예약/취소
    private final NumberPortableService numberPortableService; //번호이동 사전동의 서비스
    private final NewChangeService newChangeService; //신규/변경

    //generateRequestKey - 추후 생성을 호출하지는 않을 것이므로 삭제해야함.
    @PostMapping("/generateRequestKey")
    public CommonResponse<Long> getGenerateRequestKey() {
        return ResponseUtils.ok(formCommService.generateRequestKey());
    }

    //getCustRequestSeq - 추후 생성을 호출하지는 않을 것이므로 삭제해야함.
    @PostMapping("/getCustRequestSeq")
    public CommonResponse<Long> getCustRequestSeq() {
        return ResponseUtils.ok(formCommService.getCustRequestSeq());
    }

    //generateResNo - 추후 생성을 호출하지는 않을 것이므로 삭제해야함.
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
    public CommonResponse<NewChangeInfoResponse> getNewChangeInfo(@RequestBody @Valid NewChangeRequest condition) {
        return ResponseUtils.ok(newChangeService.getNewChangeRequestInfo(condition));
    }

    //신청서 저장 - 임시저장
    @PostMapping("/newchange/save")
    public CommonResponse<String> registerForm(@RequestBody @Valid NewChangeInfoRequest request) {
        String rtnRequestKey = "";
        rtnRequestKey = newChangeService.saveAppformInfo(request);
        return ResponseUtils.ok(rtnRequestKey);
    }


    //신청서 확인 - 이미지생성,녹취,서명 또는 기 생성 이미지 확인
    //@PostMapping("/newchange/eform/set")

    //신청서 작성완료
    //@PostMapping("/newchange/complete")


    //신규가입 희망번호 조회 (NU1)
    //@RequestMapping(value = "/appform/searchNumberAjax.do")
    @PostMapping(value = "/newchange/searchNumber")
    public CommonResponse<Map<String, Object>> getSearchNumber(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(choiceNumberService.getSearchNumber(request));
    }

    //신규가입 희망번호 예약 (NU2)
    //@RequestMapping(value = "/appform/setNumberAjax.do")
    @PostMapping(value = "/newchange/reserveNumber")
    public CommonResponse<Map<String, Object>> setChoiseNumber(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(choiceNumberService.setChoiseNumber(request));
    }

    //신규가입 희망번호 취소 (NU2)
    //@RequestMapping(value = "/appform/cancelNumberAjax.do")
    @PostMapping(value = "/newchange/cancelNumber")
    public CommonResponse<Map<String, Object>> cancelNumber(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(choiceNumberService.cancelChoiseNumber(request));
    }

    //번호이동 사전동의 (NP1)
    //@RequestMapping(value = "/appform/reqNpPreCheckAjax.do")
    @PostMapping(value = "/newchange/reqNpPreCheck")
    public CommonResponse<Map<String, Object>> requestNpPreCheck(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpPreCheck(request));
    }

    //번호이동 사전동의 결과조회 (NP3)
    //@RequestMapping(value = "/appform/reqNpAgreeAjax.do")
    @PostMapping(value = "/newchange/reqNpAgree")
    public CommonResponse<Map<String, Object>> requestNpAgree(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpAgree(request));
    }

    //번호이동 납부주장 (NP2)
    //@RequestMapping(value = "/appform/reqPayOpnAjax.do")
    @PostMapping(value = "/newchange/reqPayOpn")
    public CommonResponse<Map<String, Object>> requestPayOpn(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(numberPortableService.requestPayOpn(request));
    }


    //개통사전체크 (신규가입/번호이동)
    //parameter :: 계좌점유키값(reqUniqId), globalNoNp1(번호이동사전동의), globalNoNp3(번호이동사전동의결과)
    //appformReqDto 파라미터 검토필요 >> MsfRequestDto 로 변경하고 위 파라미터는 Dto 에 추가하든지 해야할듯함. 추후!!!
    @PostMapping(value = "/newchange/reqPreOpenCheck")
    public CommonResponse<Map<String, Object>> reqPreOpenCheck(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(formCommService.reqPreOpenCheck(request));
    }

    //2026.04.29
    //번호이동 사전동의

    //2026.04 마지막주 할일
    //usim, esim, 휴대폰일련번호 유효성 정리
    //신용카드, 계좌인증, 청구계정에 유효성 정리
    //신용카드인증 , 번호이동 , 희망번호 prx 호출전까지 붙이기
    //신청서 저장의 유효성체크 정리할 것

    //부가서비스 msf_request_addition 저장 mapstruct
    //부가서비스 msf_request 저장

    //AuthController
    // >> KTM 고객인증
    //    >> query 확인하여 mapper 경로 변경필요.
    //ProductController >> 쿼리확인하여 동일한 쿼리는 mapper 경로변경 필요함.
    // >> 판매정책조회
    //    >> /api/form/phone/saleplcy/list
    // >> 공시지원금조회 @개발필요@
    //    >> /api/form/phone/subsdamt
    // >> 단말 목록 조회 @개선필요@
    //    >> /api/form/phone/list
    // >> 단말 색상 조회
    //    >> /api/form/phone/color/list
    // >> 단말 용량조회
    //    >> /api/form/phone/capacity/list
    // >> 단말 할부기간 조회
    //    >> /api/form/phone/monthly/list
    // >> 요금제 목록 조회 @개선필요@
    //    >> /api/form/rate/list
    // >> 요금제 약정기간 조회
    //    >> /api/form/rate/engg/list
    // >> 할인유형조회
    //    >> /api/form/phone/saletype/list
    // >> 부가서비스 목록 조회
    //    >> /api/form/addition/list
    // >> 부가서비스 목록 조회 (가입중)
    //    >> /api/form/activeaddition/list
    // >> 안심보험 목록 조회
    //    >> /api/form/product/selectInsrProdList
    // >> 요금제/부가서비스/안심보험 카테고리 목록 조회
    //    >> /api/form/rate/category/list
    // >> 요금제/부가서비스/안심보험 카테고리 상세 조회
    //    >> /api/form/rate/categorydetail/list
    // >> 단말 매장 재고 조회 >> 추후 필요없는 action으로 삭제필요
    //    >> /api/form/phone/inventory/list
    //PaymentController
    // >> 청구계정아이디조회
    //    >> /api/form/verifyBillInfo
    // >> 신용카드번호인증 @개발필요@
    //    >> /api/form/crdtCardAthnInfo
    //    >> prx
    // >> 계좌번호인증 @개발예정@
    //    >> /api/form/accountCheck
    //    >> NICE
    //SimController
    // >> 핸드폰단말일련번호 유효성체크 @개선필요@
    //    >> /api/form/verifyPhoneSerialNumberInfo
    //    >> prx
    // >> USIM 유효성체크 @개선필요@
    //    >> /api/form/verifyUsimInfo
    //    >> prx
    // >> eSIM 유효성체크 @개발필요@
    //    >> /api/form/verifyEsimInfo
    //    >> prx
    //NewChangeController
    // >> 신청서 저장
    //    >> /api/form/newchange/save
    // >> 신청서 확인
    //    >> /api/form/newchange/get
    // >> 신청서 이미징 생성
    //    >> /api/form/newchange/eform/set
    // >> 신청서 작성완료
    //    >> /api/form/newchange/complete
    // >> 신규가입 희망번호 조회,예약,취소
    //    >> /api/form/newchange/searchNumber
    //    >> /api/form/newchange/reserveNumber
    //    >> /api/form/newchange/cancelNumber
    //    >> prx
    // >> 번호이동 사전동의 요청, 납부주장, 결과조회
    //    >> /api/form/newchange/reqNpPreCheck
    //    >> /api/form/newchange/reqPayOpn
    //    >> /api/form/newchange/reqNpAgree
    //    >> prx
    // >> 개통전 사전체크
    //    >> /api/form/newchange/reqPreOpenCheck
    // >> 대리점조회
    //    >> /api/form/agent/list
}
