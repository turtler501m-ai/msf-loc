package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.UsimChangeReqDto;

import java.util.Map;

/**
 * USIM 변경 서비스 인터페이스.
 * ASIS AppformController.usimChangeUC0() 와 동일 역할.
 * X85 USIM 유효성 체크는 SvcChgInfoSvc.checkUsim() 으로 이전.
 */
public interface SvcChgUsimSvc {

    /**
     * UC0 USIM 변경 처리.
     * ASIS: AppformSvcImpl.usimChangeUC0()
     */
    Map<String, Object> changeUsim(UsimChangeReqDto req);
}
