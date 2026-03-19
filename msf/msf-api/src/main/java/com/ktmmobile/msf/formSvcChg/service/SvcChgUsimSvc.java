package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.UsimChangeReqDto;
import com.ktmmobile.msf.formSvcChg.dto.UsimCheckReqDto;

import java.util.Map;

/**
 * USIM 변경 서비스 인터페이스.
 * ASIS AppformController.moscIntmMgmt(X85) / usimChangeUC0() 와 동일 역할.
 */
public interface SvcChgUsimSvc {

    /**
     * X85 USIM 유효성 체크.
     * ASIS: AppformController /msp/moscIntmMgmtAjax.do
     */
    Map<String, Object> checkUsim(UsimCheckReqDto req);

    /**
     * UC0 USIM 변경 처리.
     * ASIS: AppformSvcImpl.usimChangeUC0()
     */
    Map<String, Object> changeUsim(UsimChangeReqDto req);
}
