package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import java.util.List;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.RealtimePayInfoResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenCnlChgInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenCnlPosInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenPosHisVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeProcessResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeReservationCancelRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeReservationCancelResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeReservationResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ResumeRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ResumeSearchRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.productChangeCheckRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.service.SvcChgRestSvc;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class svcChgRestController {

    private final SvcChgRestSvc svcChgRestSvc;

    // 요금제 목록 조회
    @PostMapping("/charge-plan/get")
    public CommonResponse<List<String>> selectChargePlan(@RequestBody ChargePlanRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.selectChargePlan(request));
    }

    // 가입중인 요금제 조회(Y02)
    @PostMapping("/active-charge-plan/get")
    public CommonResponse<ChargePlanResponse> selectActiveChargePlan(@RequestBody ChargePlanRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.selectActiveChargePlan(request));
    }

    // 상품변경사전체크(Y24)
    @PostMapping("/product/change/pre-check")
    public CommonResponse<ChargePlanResponse> productChangePreCheck(@RequestBody productChangeCheckRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.productChangePreCheck(request));
    }

    // 상품변경처리(Y25? X19?)
    @PostMapping("/product/change/process")
    public CommonResponse<ChargePlanResponse> productChangeProcess(@RequestBody productChangeCheckRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.productChangeProcess(request));
    }

    // 요금제 변경 예약 처리(X88) BEF_CHG_RATE_CD:변경전요금제코드, BEF_CHG_RATE_AMNT:변경전요금제금액 이력 저장을 위해 필요
    @PostMapping("/charge-plan/change/reservation/process")
    public CommonResponse<ChargePlanChangeProcessResponse> chargePlanChangeReservationProcess(@RequestBody ChargePlanChangeProcessRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.chargePlanChangeReservationProcess(request));
    }

    // 요금제 변경 예약 조회(X89)
    @PostMapping("/charge-plan/change/reservation/get")
    public CommonResponse<ChargePlanChangeReservationResponse> selectChargePlanChangeReservation(@RequestBody ChargePlanRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.selectChargePlanChangeReservation(request));
    }

    // 요금제 변경 예약 취소(X90)
    @PostMapping("/charge-plan/change/reservation/cancel")
    public CommonResponse<ChargePlanChangeReservationCancelResponse> chargePlanChangeReservationCancel(@RequestBody ChargePlanChangeReservationCancelRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.chargePlanChangeReservationCancel(request));
    }

    // 실시간 요금 조회(X18) - 요금제 변경 즉시변경 가능한 경우 팝업 노출
    @PostMapping("/real-time-charge/list")
    public CommonResponse<RealtimePayInfoResponse> selectRealTimeCharge(@RequestBody ResumeRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.selectRealTimeCharge(request));
    }

    // 일시정지 이력 조회(X26)
    @PostMapping("/pause-history/get")
    public CommonResponse<MpSuspenPosHisVO> selectPauseHistory(@RequestBody @Valid ResumeSearchRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.selectPauseHistory(request));
    }

    // 일시정지 해제 가능 여부 조회(X28)
    @PostMapping("/resume-stats/get")
    public CommonResponse<MpSuspenCnlPosInfoInVO> selectResumeState(@RequestBody @Valid ResumeSearchRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.selectResumeState(request));
    }

    // 미납정지해제신청(X63)
    @PostMapping("/unpaid-resume/request")
    public CommonResponse<MpSuspenCnlPosInfoInVO> unpaidResume(@RequestBody @Valid ResumeSearchRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.unpaidResume(request));
    }

    // 부정사용 일시정지해제신청(X65)
    @PostMapping("/invalid-usage-resume/request")
    public CommonResponse<MpSuspenCnlPosInfoInVO> invalidResume(@RequestBody @Valid ResumeSearchRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.invalidResume(request));
    }

    // 일시정지 해제신청(X30)
    @PostMapping("/pause-resume/request")
    public CommonResponse<MpSuspenCnlChgInVO> pauseResume(@RequestBody @Valid ResumeRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.pauseResume(request));
    }

    // 분실신고 해제 신청(X35)
    @PostMapping("/loss-report-resume/request")
    public CommonResponse<MpSuspenCnlPosInfoInVO> lossReportResume(@RequestBody @Valid ResumeRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.lossReportResume(request));
    }

    // 아무나SOLO 결합 가능 여부 체크
    @PostMapping("/combile-self/check")
    public CommonResponse<MpSuspenCnlPosInfoInVO> combineSelfPossibleCheck(@RequestBody @Valid ResumeRequest request) {
        return ResponseUtils.ok(svcChgRestSvc.combineSelfPossibleCheck(request));
    }
}
