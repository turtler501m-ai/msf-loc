package com.ktmmobile.msf.formOwnChg.service;

import com.ktmmobile.msf.formOwnChg.dto.OwnChgApplyDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgApplyVO;

/**
 * 명의변경 신청서 등록 서비스 (TO-BE).
 * 파일명 규칙: service.서비스명칭Svc.java (10.서식지프로젝트.md)
 * 작성 완료 시 호출. MC0 사전체크·MP0 명의변경·NMCP_NFL_CHG_* 저장 연동 예정.
 */
public interface OwnChgApplySvc {

    /**
     * 명의변경 신청서 등록.
     * @param request 고객·상품 정보 (store 기반)
     * @return 접수번호 및 성공 여부 (OwnChgApplyVO)
     */
    OwnChgApplyVO apply(OwnChgApplyDto request);
}
