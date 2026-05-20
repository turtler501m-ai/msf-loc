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
import com.ktmmobile.msf.domains.form.form.common.dto.MnpOsstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MnpOsstResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.NiceAccountRequest;
import com.ktmmobile.msf.domains.form.form.common.service.AuthInfoService;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.service.PaymentService;
import com.ktmmobile.msf.domains.form.form.common.service.SimInfoService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeDefaultResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeEformResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SearchNumberRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SearchNumberResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.UsimRequest;
import com.ktmmobile.msf.domains.form.form.newchange.service.ChoiceNumberService;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeCheckService;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeService;
import com.ktmmobile.msf.domains.form.form.newchange.service.NumberPortableService;

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


    /**
     * 가입조건조회
     */
    @PostMapping("/eligibility/check")
    public CommonResponse<SubscriptionResponse> getEligibilityCheck(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeCheckService.getEligibilityCheck(request));
    }

    /**
     * 대리점정보 조회 (조건 : 로그인사용자 세션의 판매점 조직코드)
     */
    @PostMapping("/agent/list")
    public CommonResponse<List<AgentInfoResponse>> getAgentList(@RequestBody AgentInfoRequest request) {
        return ResponseUtils.ok(formCommService.getAgentList(request));
    }

    /**
     * 신청서 진입
     */
    @PostMapping("/newchange/get")
    public CommonResponse<NewChangeInfoResponse> getNewChangeInfo(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.getNewChangeRequestInfo(request));
    }

    /**
     * 신청서 진입 시 초기값
     */
    @PostMapping("/newchange/getdefault")
    public CommonResponse<NewChangeDefaultResponse> getNewChangeDefaultInfo(@RequestBody @Valid NewChangeRequest request) {
        return ResponseUtils.ok(newChangeService.getNewChangeDefaultInfo(request));
    }

    /**
     * 신청서 저장 - 임시저장
     */
    @PostMapping("/newchange/save")
    public CommonResponse<FormResponse<NewChangeResponse>> registeForm(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(newChangeService.saveNewChangeFormInfo(request));
    }

    /**
     * eForm 생성을 위한 데이타 SET
     * >> "신청서확인" 버튼 클릭해서 임시저장 후 결과값에 따라 eForm action 을 프론트에서 호출
     * >> 프론트에서 호출하면 직접호출할 수도 있을텐데 그에 대한 방어로직 필요함.
     */
    //신청서 확인 - 이미지생성,녹취,서명 또는 기 생성 이미지 확인
    //이때 데이타를 다시 다 보내서 저장하고 진행해야해.. 사전체크도 여기서~~~
    @PostMapping("/newchange/eform/set")
    public CommonResponse<FormResponse<NewChangeEformResponse>> eformNewChangeSet(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(newChangeService.eformNewChangeSet(request));
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
     * 휴대폰 일련번호 유효성체크 - Y13
     */
    @PostMapping("/phoneinfo/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyPhoneSerialNumberInfo(@RequestBody @Valid ProductInventoryRequest request) {
        return ResponseUtils.ok(simInfoService.verifyPhoneSerialNumberInfo(request));
    }

    @Deprecated
    @PostMapping("/verifyPhoneSerialNumberInfo")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyPhoneSerialNumberInfo2(@RequestBody @Valid ProductInventoryRequest request) {
        return ResponseUtils.ok(simInfoService.verifyPhoneSerialNumberInfo(request));
    }


    /**
     * USIM정보 유효성체크 - X85
     */
    @PostMapping("/usiminfo/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyUsimInfo(@RequestBody @Valid UsimRequest request) {
        return ResponseUtils.ok(simInfoService.verifyUsimInfo(request));
    }

    @Deprecated
    @PostMapping("/verifyUsimInfo")
    public CommonResponse<FormResponse<Map<String, Object>>> verifyUsimInfo2(@RequestBody @Valid UsimRequest request) {
        return ResponseUtils.ok(simInfoService.verifyUsimInfo(request));
    }

    /**
     * eSIM 정보 유효성체크 - Y13, Y12, Y14, Y15
     */
    //@RequestMapping(value = {"/appForm/eSimChkAjax.do", "/m/appForm/eSimChkAjax.do"})
    @PostMapping("/esiminfo/verify")
    public CommonResponse<FormResponse<EsimResponse>> verifyEsimInfo(@RequestBody @Valid EsimRequest request) {
        return ResponseUtils.ok(simInfoService.verifyEsimInfo(request));
    }

    @Deprecated
    @PostMapping("/verifyEsimInfo")
    public CommonResponse<FormResponse<EsimResponse>> verifyEsimInfo2(@RequestBody @Valid EsimRequest request) {
        return ResponseUtils.ok(simInfoService.verifyEsimInfo(request));
    }


    /**
     * 신규가입 희망번호 조회 (NU1)
     */
    //@RequestMapping(value = "/appform/searchNumberAjax.do")
    @PostMapping(value = "/hopenumber/get")
    public CommonResponse<FormResponse<SearchNumberResponse>> getSearchNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.getSearchNumber(request));
    }

    @Deprecated
    @PostMapping(value = "/newchange/searchNumber")
    public CommonResponse<FormResponse<SearchNumberResponse>> getSearchNumber2(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.getSearchNumber(request));
    }


    /**
     * 신규가입 희망번호 예약 (NU2)
     */
    //@RequestMapping(value = "/appform/setNumberAjax.do")
    @PostMapping(value = "/hopenumber/reserve")
    public CommonResponse<FormResponse<SearchNumberResponse>> setChoiseNumber(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.setChoiseNumber(request));
    }

    @Deprecated
    @PostMapping(value = "/newchange/reserveNumber")
    public CommonResponse<FormResponse<SearchNumberResponse>> setChoiseNumber2(@RequestBody @Valid SearchNumberRequest request) {
        return ResponseUtils.ok(choiceNumberService.setChoiseNumber(request));
    }

    /**
     * 신규가입 희망번호 취소 (NU2)
     */
    //
    //@RequestMapping(value = "/appform/cancelNumberAjax.do")
    @PostMapping(value = "/hopenumber/cancel")
    public CommonResponse<FormResponse<SearchNumberResponse>> cancelNumber(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(choiceNumberService.cancelChoiseNumber(request));
    }

    @Deprecated
    @PostMapping(value = "/newchange/cancelNumber")
    public CommonResponse<FormResponse<SearchNumberResponse>> cancelNumber2(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(choiceNumberService.cancelChoiseNumber(request));
    }

    /**
     * 번호이동 사전동의 (NP1)
     */
    //@RequestMapping(value = "/appform/reqNpPreCheckAjax.do")
    @PostMapping(value = "/portnumber/precheck/request")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpPreCheck(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpPreCheck(request));
    }

    @Deprecated
    @PostMapping(value = "/newchange/reqNpPreCheck")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpPreCheck2(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpPreCheck(request));
    }

    /**
     * 번호이동 사전동의 결과조회 (NP3)
     */
    //@RequestMapping(value = "/appform/reqNpAgreeAjax.do")
    @PostMapping(value = "/portnumber/precheck/result")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpAgree(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpAgree(request));
    }

    @Deprecated
    @PostMapping(value = "/newchange/reqNpAgree")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpAgree2(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpAgree(request));
    }

    /**
     * 번호이동 납부주장 (NP2)
     */
    //@RequestMapping(value = "/appform/reqPayOpnAjax.do")
    @PostMapping(value = "/portnumber/payon")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestPayOpn(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestPayOpn(request));
    }

    @Deprecated
    @PostMapping(value = "/newchange/reqPayOpn")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestPayOpn2(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestPayOpn(request));
    }

    /**
     * 청구계정아이디 조회 (고객포탈은 없음?) >> 본인만 가능
     */
    @PostMapping("/payment/bill/verify")
    public CommonResponse<FormResponse<MspJuoBanInfoResponse>> verifyBillInfo(@RequestBody @Valid MspJuoBanInfoRequest request) {
        return ResponseUtils.ok(paymentService.verifyBillInfo(request));
    }

    @Deprecated
    @PostMapping("/verifyBillInfo")
    public CommonResponse<FormResponse<MspJuoBanInfoResponse>> verifyBillInfo2(@RequestBody @Valid MspJuoBanInfoRequest request) {
        return ResponseUtils.ok(paymentService.verifyBillInfo(request));
    }

    /**
     * 신용카드인증 (X91)
     */
    //@RequestMapping(value = "/crdtCardAthnInfoAjax.do")
    @PostMapping("/payment/credit/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> crdtCardAthnInfo(@RequestBody @Valid CrdtCardAuthRequest request) {
        return ResponseUtils.ok(paymentService.crdtCardAthnInfo(request));
    }

    @Deprecated
    @PostMapping("/crdtCardAthnInfo")
    public CommonResponse<FormResponse<Map<String, Object>>> crdtCardAthnInfo2(@RequestBody @Valid CrdtCardAuthRequest request) {
        return ResponseUtils.ok(paymentService.crdtCardAthnInfo(request));
    }

    /**
     * 계좌번호인증 (NICE)
     */
    //@RequestMapping(value = "/nice/accountCheckAjax.do")
    @PostMapping("/payment/account/verify")
    public CommonResponse<FormResponse<Map<String, Object>>> accountCheck(
        @RequestBody @Valid NiceAccountRequest niceAccountRequest,
        HttpServletRequest request
    ) {
        return ResponseUtils.ok(paymentService.accountCheck(niceAccountRequest, request));
    }

    @Deprecated
    @PostMapping("/accountCheck")
    public CommonResponse<FormResponse<Map<String, Object>>> accountCheck2(
        @RequestBody @Valid NiceAccountRequest niceAccountRequest,
        HttpServletRequest request
    ) {
        return ResponseUtils.ok(paymentService.accountCheck(niceAccountRequest, request));
    }

    /**
     * 개통전 사전체크 (신규가입/번호이동)
     */
    //parameter :: 계좌점유키값(reqUniqId), globalNoNp1(번호이동사전동의), globalNoNp3(번호이동사전동의결과)
    //appformReqDto 파라미터 검토필요 >> MsfRequestDto 로 변경하고 위 파라미터는 Dto 에 추가하든지 해야할듯함. 추후!!!
    //@RequestMapping(value = "/appform/reqPreOpenCheckAjax.do")
    //AS-IS : 다음단계 진행 (isValidateStep2) 시 개통사전체크 진행함.
    //TO-BE : 고객단계 진행 시 개통사전체크 진행을 한다면 상품단계의 임시저장일 경우에는 무조건 재진행을 해야하는데,
    @PostMapping("/newchange/preopen/req")
    public CommonResponse<Map<String, Object>> reqPreOpenCheck(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(formCommService.reqPreOpenCheck(request));
    }

    @Deprecated
    @PostMapping(value = "/newchange/reqPreOpenCheck")
    public CommonResponse<Map<String, Object>> reqPreOpenCheck2(@RequestBody @Valid NewChangeInfoRequest request) {
        return ResponseUtils.ok(formCommService.reqPreOpenCheck(request));
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

    @Deprecated
    @PostMapping(value = "/newchange/conPreOpenCheck")
    public CommonResponse<Map<String, Object>> conPreOpenCheck2(@RequestBody @Valid NewChangeInfoRequest request) {
        return null;
        //return ResponseUtils.ok(formCommService.conPreOpenCheck(request));
    }

    /**
     * KNOTE 신분증 목록조회 >> 서식지 목록조회 - FS0
     */
    @PostMapping("/knote/scaninfo/list")
    //public CommonResponse<FormResponse<MSimpleOsstXmlFs0VO>> getIdList(@RequestBody @Valid KnoteScanInfoRequest request) {
    public CommonResponse<FormResponse<MSimpleOsstXmlFs0VO>> getIdList(@RequestBody @Valid KnoteScanInfoRequest request) {
        return ResponseUtils.ok(authInfoService.getIdList(request));
    }

    @Deprecated
    @PostMapping(value = "/knote/getIdList")
    public CommonResponse<FormResponse<MSimpleOsstXmlFs0VO>> getIdList2(@RequestBody @Valid KnoteScanInfoRequest request) {
        return ResponseUtils.ok(authInfoService.getIdList(request));
    }

    /**
     * KNOTE 신분증 상태조회 >> 서식지 상태조회 - FS1
     */
    @PostMapping("/knote/scaninfo/check")
    public CommonResponse<FormResponse<KnoteScanInfoResponse>> checkIdStatus(@RequestBody @Valid KnoteScanInfoRequest request) {
        return ResponseUtils.ok(authInfoService.checkIdStatus(request));
    }

    @Deprecated
    @PostMapping(value = "/knote/checkIdStatus")
    public CommonResponse<FormResponse<KnoteScanInfoResponse>> checkIdStatus2(@RequestBody @Valid KnoteScanInfoRequest request) {
        return ResponseUtils.ok(authInfoService.checkIdStatus(request));
    }


}
