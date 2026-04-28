package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.List;
import jakarta.validation.Valid;

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

public interface SvcChgRestSvc {

    // 요금제 목록 조회
    List<String> selectChargePlan(ChargePlanRequest searchVO);

    // 일시정지 가능 여부(X28)
    MpSuspenCnlPosInfoInVO selectResumeState(ResumeSearchRequest searchVO);

    // 일시정지 이력 조회(X26)
    MpSuspenPosHisVO selectPauseHistory(ResumeSearchRequest request);

    // 미납정지 해제 신청(X63)
    MpSuspenCnlPosInfoInVO unpaidResume(ResumeSearchRequest request);

    // 부정사용 일시정지 해제 신청(X65)
    MpSuspenCnlPosInfoInVO invalidResume(ResumeSearchRequest request);

    // 일시정지 해제 신청(X30)
    MpSuspenCnlChgInVO pauseResume(ResumeRequest request);

    // 분실신고 해제 신청(X35)
    MpSuspenCnlPosInfoInVO lossReportResume(ResumeRequest request);

    // 아무나SOLO 결합 가능 여부 체크
    MpSuspenCnlPosInfoInVO combineSelfPossibleCheck(@Valid ResumeRequest request);

    // 실시간 요금 조회(X18)
    RealtimePayInfoResponse selectRealTimeCharge(@Valid ResumeRequest request);

    // 가입중인 요금 조회(Y02)
    ChargePlanResponse selectActiveChargePlan(ChargePlanRequest request);

    // 상품변경사전체크(Y24)
    ChargePlanResponse productChangePreCheck(productChangeCheckRequest request);

    // 상품변경처리(Y25)
    ChargePlanResponse productChangeProcess(productChangeCheckRequest request);

    // 요금제 변경 예약 처리(X88)
    ChargePlanChangeProcessResponse chargePlanChangeReservationProcess(ChargePlanChangeProcessRequest request);

    // 요금제 변경 예약 조회(X89)
    ChargePlanChangeReservationResponse selectChargePlanChangeReservation(ChargePlanRequest request);

    // 요금제 변경 예약 취소(X90)
    ChargePlanChangeReservationCancelResponse chargePlanChangeReservationCancel(ChargePlanChangeReservationCancelRequest request);

}
