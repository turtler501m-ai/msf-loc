package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.SvcChgApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgApplyResVO;

/**
 * 서비스변경 통합 신청 서비스 인터페이스.
 * POST /api/v1/service-change/apply — 선택된 모든 항목을 순차 처리 후 신청서 DB 저장.
 */
public interface SvcChgApplySvc {

    /**
     * 서비스변경 통합 신청 처리.
     * 처리 순서:
     *   1. WIRELESS_BLOCK → X21(신청) / X38(해지)
     *   2. INFO_LIMIT → DB 기록 (SOC 미확정)
     *   3. RATE_CHANGE → X19(즉시) / X88(예약)
     *   4. USIM_CHANGE → X85 사전확인 + UC0 변경
     *   5. ADDITION 개별항목 → X21 / X38
     *   6. MSF_REQUEST_SVC_CHG INSERT (마스터)
     *   7. MSF_REQUEST_SVC_CHG_DTL INSERT (선택 항목별 상세)
     */
    SvcChgApplyResVO apply(SvcChgApplyReqDto req);
}
