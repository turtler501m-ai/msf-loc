package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCancelReqDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import com.ktmmobile.msf.formSvcChg.dto.AdditionRegReqDto;

import java.util.Map;

/**
 * 부가서비스 신청/해지 서비스 인터페이스.
 */
public interface SvcChgRegSvc {

    /** X20 현재 가입 부가서비스 조회 */
    AdditionCurrentResVO selectAdditionCurrent(SvcChgInfoDto req);

    /** X21 부가서비스 신청 */
    Map<String, Object> additionReg(AdditionRegReqDto req);

    /** X38 부가서비스 해지 */
    Map<String, Object> additionCancel(AdditionCancelReqDto req);
}
