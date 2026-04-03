package com.ktmmobile.msf.formOwnChg.service;

import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 명의변경 신청서 - 부가서비스 조회 서비스.
 * msf/formOwnChg – 명의변경.
 */
public interface OwnChgAddSvc {

    /**
     * X20 현재 부가서비스 조회.
     */
    AdditionCurrentResVO selectAdditionCurrent(SvcChgInfoDto req);
}
