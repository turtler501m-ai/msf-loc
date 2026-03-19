package com.ktmmobile.msf.formOwnChg.service;

import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgRegSvc;
import org.springframework.stereotype.Service;

/**
 * 명의변경 신청서 - 부가서비스 조회 서비스 구현.
 * SvcChgRegSvc 위임.
 */
@Service
public class OwnChgAddSvcImpl implements OwnChgAddSvc {

    private final SvcChgRegSvc svcChgRegSvc;

    public OwnChgAddSvcImpl(SvcChgRegSvc svcChgRegSvc) {
        this.svcChgRegSvc = svcChgRegSvc;
    }

    @Override
    public AdditionCurrentResVO selectAdditionCurrent(SvcChgInfoDto req) {
        return svcChgRegSvc.selectAdditionCurrent(req);
    }
}
