package com.ktmmobile.msf.domains.form.form.newchange.controller;

import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.CrdtCardAuthRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.EsimRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.EsimResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MnpOsstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MnpOsstResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.NiceAccountRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSerialRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.SearchNumberRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.SearchNumberResponse;
import com.ktmmobile.msf.domains.form.form.common.service.ChoiceNumberService;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.service.NumberPortableService;
import com.ktmmobile.msf.domains.form.form.common.service.PaymentService;
import com.ktmmobile.msf.domains.form.form.common.service.SimInfoService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeDefaultResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionResponse;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class NewChangeController {

    private final NewChangeService newChangeService; //신규/변경
    private final ChoiceNumberService choiceNumberService; //신규가입 희망번호 조회/예약/취소
    private final NumberPortableService numberPortableService; //번호이동 사전동의 요청/결과조회/납부주장
    private final SimInfoService simInfoService; //휴대폰일련번호 유효성체크, USIM유효성체크, eSIM유효성체크
    private final PaymentService paymentService; //청구계정아이디조회, 신용카드인증, 계좌인증
    private final FormCommService formCommService; //신청서 공통


    //가입조건조회
    @PostMapping("/eligibility/check")
    public CommonResponse<SubscriptionResponse> getEligibilityCheck(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeService.getEligibilityCheck(request));
    }

    //대리점정보 조회 (조건 : 매장코드)
    @PostMapping("/agent/list")
    public CommonResponse<List<AgentInfoResponse>> getAgentList(@RequestBody @Valid AgentInfoRequest request) {
        return ResponseUtils.ok(formCommService.getAgentList(request));
    }

    //신청서 진입
    @PostMapping("/newchange/get")
    public CommonResponse<NewChangeInfoResponse> getNewChangeInfo(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.getNewChangeRequestInfo(request));
    }

    //신청서 초기값 조회
    @PostMapping("/newchange/getDefault")
    public CommonResponse<NewChangeDefaultResponse> getNewChangeDefaultInfo(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.getNewChangeDefaultInfo(request));
    }

    //신청서 저장 - 임시저장
    @PostMapping("/newchange/save")
    public CommonResponse<FormResponse<NewChangeResponse>> registeForm(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(newChangeService.saveAppformInfo(request));
    }

    //신청서 확인 - 이미지생성,녹취,서명 또는 기 생성 이미지 확인
    //이때 데이타를 다시 다 보내서 저장하고 진행해야해.. 사전체크도 여기서~~~
    @PostMapping("/newchange/eform/set")
    public CommonResponse<FormResponse<NewChangeInfoResponse>> eformNewChangeSet(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(newChangeService.eformNewChangeSet(request));
    }

    //신청서 작성완료
    @PostMapping("/newchange/complete")
    public CommonResponse<FormResponse<NewChangeResponse>> completeForm(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.completeAppformInfo(request));
    }


    //휴대폰 일련번호 유효성체크 - Y13
    @PostMapping("/verifyPhoneSerialNumberInfo")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyPhoneSerialNumberInfo(@RequestBody @Valid PhoneSerialRequest condition) {
        return ResponseUtils.ok(simInfoService.verifyPhoneSerialNumberInfo(condition));
    }

    //USIM 정보 유효성체크 - X85
    //@RequestMapping(value = "/msp/moscIntmMgmtAjax.do")
    @PostMapping("/verifyUsimInfo")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyUsimInfo(@RequestBody @Valid MspJuoSubInfoRequest condition) {
        return ResponseUtils.ok(simInfoService.verifyUsimInfo(condition));
    }

    //eSIM 정보 유효성체크 - Y13, Y12, Y14, Y15
    //@RequestMapping(value = {"/appForm/eSimChkAjax.do", "/m/appForm/eSimChkAjax.do"})
    @PostMapping("/verifyEsimInfo")
    public CommonResponse<FormResponse<EsimResponse>> verifyEsimInfo(@RequestBody @Valid EsimRequest request) {
        return ResponseUtils.ok(simInfoService.verifyEsimInfo(request));
    }

    //신규가입 희망번호 조회 (NU1)
    //@RequestMapping(value = "/appform/searchNumberAjax.do")
    /*@PostMapping(value = "/newchange/searchNumber")
    public CommonResponse<FormResponse<SearchNumberResponse>> getSearchNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.getSearchNumber(request));
    }*/
    @PostMapping(value = "/newchange/searchNumber")
    public CommonResponse<FormResponse<SearchNumberResponse>> getSearchNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.getSearchNumber(request));
    }

    //신규가입 희망번호 예약 (NU2)
    //@RequestMapping(value = "/appform/setNumberAjax.do")
    @PostMapping(value = "/newchange/reserveNumber")
    public CommonResponse<FormResponse<SearchNumberResponse>> setChoiseNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.setChoiseNumber(request));
    }

    //신규가입 희망번호 취소 (NU2)
    //@RequestMapping(value = "/appform/cancelNumberAjax.do")
    @PostMapping(value = "/newchange/cancelNumber")
    public CommonResponse<FormResponse<SearchNumberResponse>> cancelNumber(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(choiceNumberService.cancelChoiseNumber(request));
    }

    //번호이동 사전동의 (NP1)
    //@RequestMapping(value = "/appform/reqNpPreCheckAjax.do")
    @PostMapping(value = "/newchange/reqNpPreCheck")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpPreCheck(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpPreCheck(request));
    }

    //번호이동 사전동의 결과조회 (NP3)
    //@RequestMapping(value = "/appform/reqNpAgreeAjax.do")
    @PostMapping(value = "/newchange/reqNpAgree")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpAgree(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpAgree(request));
    }

    //번호이동 납부주장 (NP2)
    //@RequestMapping(value = "/appform/reqPayOpnAjax.do")
    @PostMapping(value = "/newchange/reqPayOpn")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestPayOpn(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestPayOpn(request));
    }

    //청구계정아이디 조회 (고객포탈은 없음?) >> 본인만 가능
    @PostMapping("/verifyBillInfo")
    public CommonResponse<FormResponse<MspJuoBanInfoResponse>> verifyBillInfo(@RequestBody @Valid MspJuoBanInfoRequest request) {
        return ResponseUtils.ok(paymentService.verifyBillInfo(request));
    }

    //신용카드인증 (X91)
    //@RequestMapping(value = "/crdtCardAthnInfoAjax.do")
    @PostMapping("/crdtCardAthnInfo")
    public CommonResponse<FormResponse<Map<String, Object>>> crdtCardAthnInfo(@RequestBody @Valid CrdtCardAuthRequest request) {
        return ResponseUtils.ok(paymentService.crdtCardAthnInfo(request));
    }

    //계좌번호인증 (NICE)
    //@RequestMapping(value = "/nice/accountCheckAjax.do")
    @PostMapping("/accountCheck")
    public CommonResponse<FormResponse<Map<String, Object>>> accountCheck(
        @RequestBody @Valid NiceAccountRequest niceAccountRequest,
        HttpServletRequest request
    ) {
        return ResponseUtils.ok(paymentService.accountCheck(niceAccountRequest, request));
    }


    //개통전 사전체크 (신규가입/번호이동)
    //parameter :: 계좌점유키값(reqUniqId), globalNoNp1(번호이동사전동의), globalNoNp3(번호이동사전동의결과)
    //appformReqDto 파라미터 검토필요 >> MsfRequestDto 로 변경하고 위 파라미터는 Dto 에 추가하든지 해야할듯함. 추후!!!
    //@RequestMapping(value = "/appform/reqPreOpenCheckAjax.do")
    //AS-IS : 다음단계 진행 (isValidateStep2) 시 개통사전체크 진행함.
    //TO-BE : 고객단계 진행 시 개통사전체크 진행을 한다면 상품단계의 임시저장일 경우에는 무조건 재진행을 해야하는데,
    @PostMapping(value = "/newchange/reqPreOpenCheck")
    public CommonResponse<Map<String, Object>> reqPreOpenCheck(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(formCommService.reqPreOpenCheck(request));
    }

    //개통전 사전체크 확인 (신규가입/번호이동)
    //@RequestMapping(value = "/appform/conPreCheckAjax.do")
    @PostMapping(value = "/newchange/conPreOpenCheck")
    public CommonResponse<Map<String, Object>> conPreOpenCheck(@RequestBody @Valid NewChangeInfoRequest request) {
        return null;
        //return ResponseUtils.ok(formCommService.conPreOpenCheck(request));
    }


    //신청서 저장의 유효성체크 정리할 것
    //공시지원금 조회
    //단말 및 요금제 조회 request 정리

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
    //NewChangeController
    // >> 신청서 저장
    //    >> /api/form/newchange/save
    // >> 신청서 확인
    //    >> /api/form/newchange/get
    // >> 신청서 이미징 생성
    //    >> /api/form/newchange/eform/set
    // >> 신청서 작성완료
    //    >> /api/form/newchange/complete
    // >> 개통전 사전체크
    //    >> /api/form/newchange/reqPreOpenCheck
    // >> 대리점조회
    //    >> /api/form/agent/list
    //AuthController
    // >> KTM 고객인증
    //    >> query 확인하여 mapper 경로 변경필요.
}
