package com.ktmmobile.msf.formSvcCncl.service;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclRemainChargeResVO;

/**
 * 서비스해지 신청서 서비스 인터페이스.
 */
public interface SvcCnclSvc {

    /** 해지 가능 여부 (MC0 연동 예정) */
    boolean eligible(SvcChgInfoDto req);

    /** 잔여요금/위약금 조회 (X18 연동 예정) */
    SvcCnclRemainChargeResVO getRemainCharge(SvcChgInfoDto req);

    /** 해지 신청서 등록 */
    SvcCnclApplyVO apply(SvcCnclApplyDto req);
}
