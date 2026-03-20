package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.PauseApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.PauseCheckReqDto;

import java.util.Map;

/**
 * 분실복구/일시정지해제 서비스.
 * ASIS PauseController.getSuspenPosInfoAjax/suspenCnlChgInAjax/pcsLostInfoAjax/pcsLostChgAjax 역할.
 */
public interface SvcChgPauseSvc {

    /** 분실복구/일시정지해제 조회 (X26 이력, X28 해제가능여부, X33 분실신고가능여부). */
    Map<String, Object> check(PauseCheckReqDto req);

    /** 분실복구(X35) 또는 일시정지해제(X30) 처리. */
    Map<String, Object> apply(PauseApplyReqDto req);
}
