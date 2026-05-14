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
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlFs0VO;
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
import com.ktmmobile.msf.domains.form.form.common.service.AuthInfoService;
import com.ktmmobile.msf.domains.form.form.common.service.ChoiceNumberService;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.service.NumberPortableService;
import com.ktmmobile.msf.domains.form.form.common.service.PaymentService;
import com.ktmmobile.msf.domains.form.form.common.service.SimInfoService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeDefaultResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionResponse;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeCheckService;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class NewChangeController {

    private final NewChangeService newChangeService; //신규/변경
    private final NewChangeCheckService newChangeCheckService; //신규/변경
    private final ChoiceNumberService choiceNumberService; //신규가입 희망번호 조회/예약/취소
    private final NumberPortableService numberPortableService; //번호이동 사전동의 요청/결과조회/납부주장
    private final SimInfoService simInfoService; //휴대폰일련번호 유효성체크, USIM유효성체크, eSIM유효성체크
    private final PaymentService paymentService; //청구계정아이디조회, 신용카드인증, 계좌인증
    private final FormCommService formCommService; //신청서 공통
    private final AuthInfoService authInfoService; //인증


    //가입조건조회
    @PostMapping("/eligibility/check")
    public CommonResponse<SubscriptionResponse> getEligibilityCheck(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeCheckService.getEligibilityCheck(request));
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

    //신청서 복사
    @PostMapping("/newchange/copyform")
    public CommonResponse<FormResponse<NewChangeResponse>> copyForm(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.copyForm(request));
    }


    //휴대폰 일련번호 유효성체크 - Y13
    @PostMapping("/verifyPhoneSerialNumberInfo")
    //@PostMapping("/phoneinfo/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyPhoneSerialNumberInfo(@RequestBody @Valid PhoneSerialRequest condition) {
        return ResponseUtils.ok(simInfoService.verifyPhoneSerialNumberInfo(condition));
    }

    //USIM 정보 유효성체크 - X85
    //@RequestMapping(value = "/msp/moscIntmMgmtAjax.do")
    @PostMapping("/verifyUsimInfo")
    //@PostMapping("/usiminfo/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyUsimInfo(@RequestBody @Valid MspJuoSubInfoRequest condition) {
        return ResponseUtils.ok(simInfoService.verifyUsimInfo(condition));
    }

    //eSIM 정보 유효성체크 - Y13, Y12, Y14, Y15
    //@RequestMapping(value = {"/appForm/eSimChkAjax.do", "/m/appForm/eSimChkAjax.do"})
    @PostMapping("/verifyEsimInfo")
    //@PostMapping("/esiminfo/verify")
    public CommonResponse<FormResponse<EsimResponse>> verifyEsimInfo(@RequestBody @Valid EsimRequest request) {
        return ResponseUtils.ok(simInfoService.verifyEsimInfo(request));
    }

    //신규가입 희망번호 조회 (NU1)
    //@RequestMapping(value = "/appform/searchNumberAjax.do")
    @PostMapping(value = "/newchange/searchNumber")
    //@PostMapping(value = "/hopenumber/get")
    public CommonResponse<FormResponse<SearchNumberResponse>> getSearchNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.getSearchNumber(request));
    }

    //신규가입 희망번호 예약 (NU2)
    //@RequestMapping(value = "/appform/setNumberAjax.do")
    @PostMapping(value = "/newchange/reserveNumber")
    //@PostMapping(value = "/hopenumber/reserve")
    public CommonResponse<FormResponse<SearchNumberResponse>> setChoiseNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.setChoiseNumber(request));
    }

    //신규가입 희망번호 취소 (NU2)
    //@RequestMapping(value = "/appform/cancelNumberAjax.do")
    @PostMapping(value = "/newchange/cancelNumber")
    //@PostMapping(value = "/hopenumber/cancel")
    public CommonResponse<FormResponse<SearchNumberResponse>> cancelNumber(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(choiceNumberService.cancelChoiseNumber(request));
    }

    //번호이동 사전동의 (NP1)
    //@RequestMapping(value = "/appform/reqNpPreCheckAjax.do")
    @PostMapping(value = "/newchange/reqNpPreCheck")
    //@PostMapping(value = "/mnp/precheck/request")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpPreCheck(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpPreCheck(request));
    }

    //번호이동 사전동의 결과조회 (NP3)
    //@RequestMapping(value = "/appform/reqNpAgreeAjax.do")
    @PostMapping(value = "/newchange/reqNpAgree")
    //@PostMapping(value = "/mnp/precheck/result")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpAgree(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpAgree(request));
    }

    //번호이동 납부주장 (NP2)
    //@RequestMapping(value = "/appform/reqPayOpnAjax.do")
    @PostMapping(value = "/newchange/reqPayOpn")
    //@PostMapping(value = "/mnp/payon
    public CommonResponse<FormResponse<MnpOsstResponse>> requestPayOpn(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestPayOpn(request));
    }

    //청구계정아이디 조회 (고객포탈은 없음?) >> 본인만 가능
    @PostMapping("/verifyBillInfo")
    //@PostMapping("/payment/bill/verify")
    public CommonResponse<FormResponse<MspJuoBanInfoResponse>> verifyBillInfo(@RequestBody @Valid MspJuoBanInfoRequest request) {
        return ResponseUtils.ok(paymentService.verifyBillInfo(request));
    }

    //신용카드인증 (X91)
    //@RequestMapping(value = "/crdtCardAthnInfoAjax.do")
    @PostMapping("/crdtCardAthnInfo")
    //@PostMapping("/payment/credit/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> crdtCardAthnInfo(@RequestBody @Valid CrdtCardAuthRequest request) {
        return ResponseUtils.ok(paymentService.crdtCardAthnInfo(request));
    }

    //계좌번호인증 (NICE)
    //@RequestMapping(value = "/nice/accountCheckAjax.do")
    @PostMapping("/accountCheck")
    //@PostMapping("/payment/account/verify")
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
    //@PostMapping("/newchange/preopen/req")
    public CommonResponse<Map<String, Object>> reqPreOpenCheck(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(formCommService.reqPreOpenCheck(request));
    }

    //개통전 사전체크 확인 (신규가입/번호이동)
    //@RequestMapping(value = "/appform/conPreCheckAjax.do")
    @PostMapping(value = "/newchange/conPreOpenCheck")
    //@PostMapping("/newchange/preopen/con")
    public CommonResponse<Map<String, Object>> conPreOpenCheck(@RequestBody @Valid NewChangeInfoRequest request) {
        return null;
        //return ResponseUtils.ok(formCommService.conPreOpenCheck(request));
    }

    //KNOTE 신분증 목록조회 >> 서식지 목록조회 - FS0
    @PostMapping(value = "/knote/getIdList")
    //@PostMapping("/knote/scaninfo/list")
    //public CommonResponse<FormResponse<MSimpleOsstXmlFs0VO>> getIdList(@RequestBody @Valid KnoteScanInfoRequest request) {
    public CommonResponse<FormResponse<MSimpleOsstXmlFs0VO>> getIdList() {
        return ResponseUtils.ok(authInfoService.getIdList());
    }

    //KNOTE 신분증 상태조회 >> 서식지 상태조회 - FS1
    @PostMapping(value = "/knote/checkIdStatus")
    //@PostMapping("/knote/scaninfo/check")
    public CommonResponse<FormResponse<KnoteScanInfoResponse>> checkIdStatus(@RequestBody @Valid KnoteScanInfoRequest request) {
        return ResponseUtils.ok(authInfoService.checkIdStatus(request));
    }

}
