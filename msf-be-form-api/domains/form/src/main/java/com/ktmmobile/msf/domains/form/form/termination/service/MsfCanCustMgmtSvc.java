package com.ktmmobile.msf.domains.form.form.termination.service;

import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.DetailDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListResDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessResVO;

public interface MsfCanCustMgmtSvc {

    /** 신청서 목록 조회 (신규/변경·서비스변경·명의변경·서비스해지 통합) */
    ListResDto selectAppFormList(ListReqDto req);

    /** 관리자 해지신청 상세 조회 */
    DetailDto selectCanCustDetail(Long requestKey);

    /**
     * 처리완료 — EP0 실시간 해지 후 PROC_CD='CP' 저장
     * ASIS: updateCanCsl(PROC_CD만 DB 갱신) + BATCH00233(실해지)
     * TOBE: EP0 실시간 호출 → 성공 시 PROC_CD='CP' 한번에 처리
     */
    ProcessResVO processComplete(ProcessReqDto req);

    /** 완료취소 — PROC_CD를 'RC'(접수)로 되돌림 */
    ProcessResVO processRevert(Long requestKey);
}
