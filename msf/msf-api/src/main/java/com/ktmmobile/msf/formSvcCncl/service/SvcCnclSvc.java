package com.ktmmobile.msf.formSvcCncl.service;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclConsultReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclDetailResVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclProcReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclRemainChargeResVO;

import java.util.Map;

/**
 * 서비스해지 신청서 서비스 인터페이스.
 */
public interface SvcCnclSvc {

    /**
     * 해지 사전체크.
     * ASIS: CancelConsultController.cancelConsultAjax() 체크 로직 동일.
     * resultCode: 00000=정상, 00002=미성년자, 00004=필수값없음, 00006=중복접수, 00007=본인인증미완료
     */
    Map<String, Object> cancelConsult(SvcCnclConsultReqDto req);

    /** 잔여요금/위약금 조회 (X18+X54+X62) */
    SvcCnclRemainChargeResVO getRemainCharge(SvcChgInfoDto req);

    /** 해지 신청서 등록 */
    SvcCnclApplyVO apply(SvcCnclApplyDto req);

    /**
     * 해지 신청서 단건 상세 조회.
     * M전산 SOURCE_CD='MSF' 분기 처리 시 계약정보 제공용.
     * GET /api/v1/cancel/detail/{requestKey}
     */
    SvcCnclDetailResVO getDetail(Long requestKey);

    /**
     * MSP 처리완료 통보 — MSF_REQUEST_CANCEL.PROC_CD 업데이트.
     * MSP에서 해지 처리 완료 후 호출 (선택적).
     * POST /api/v1/cancel/proc
     */
    Map<String, Object> updateProc(SvcCnclProcReqDto req);
}
