package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formComm.dto.ConcurrentCheckReqDto;
import com.ktmmobile.msf.formComm.dto.ConcurrentCheckResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseCheckReqDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseCheckResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseTargetResVO;

/**
 * 서비스변경 기본 서비스 인터페이스.
 */
public interface SvcChgBaseSvc {

    /** 서비스변경 업무 목록 조회 */
    SvcChgBaseTargetResVO getChangeTargets();

    /** 서비스 체크 유효성 */
    SvcChgBaseCheckResVO validateServiceCheck(SvcChgBaseCheckReqDto req);

    /** 동시처리 불가 체크 */
    ConcurrentCheckResVO concurrentCheck(ConcurrentCheckReqDto req);
}
