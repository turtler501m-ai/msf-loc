package com.ktmmobile.msf.formSvcCncl.mapper;

import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclDetailResVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclSyncItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 서비스해지 신청서 DB Mapper.
 * 대상 테이블: MSF_REQUEST_CANCEL (SQ_REQUEST_KEY 시퀀스)
 */
@Mapper
public interface SvcCnclMapper {

    /**
     * 동일 휴대폰번호로 접수 중인 해지 신청 건수 조회.
     * PROC_CD != 'BK'(취소) 조건으로 중복 체크.
     */
    int selectCancelCount(String mobileNo);

    /**
     * 해지 신청서 저장 (MSF_REQUEST_CANCEL).
     * REQUEST_KEY는 SQ_REQUEST_KEY DEFAULT로 자동 생성.
     * 저장 후 requestKey 필드에 생성된 키 반환 (useGeneratedKeys).
     */
    int insertCancel(SvcCnclInsertDto dto);

    /**
     * 서비스해지 신청서 단건 상세 조회.
     * M전산 연동 시 MSF 계약정보 제공용.
     */
    SvcCnclDetailResVO selectCancelDetail(@Param("requestKey") Long requestKey);

    /**
     * M포탈 적재 완료 후 custReqSeq(RES_NO) 업데이트.
     */
    int updateCancelResNo(@Param("requestKey") Long requestKey,
                          @Param("resNo") String resNo);

    /**
     * MSP 처리완료 통보 — PROC_CD 업데이트.
     * RES_NO(custReqSeq) 기준. PROC_CD='CP' 건은 이중 업데이트 방지.
     */
    int updateCancelProcCd(@Param("custReqSeq") String custReqSeq,
                           @Param("procCd") String procCd);

    // ── 배치 전용 ──────────────────────────────────────────────────────────

    /**
     * [배치] PROC_CD='RC'(접수) 상태인 해지 신청 목록 조회.
     * 100건 단위 페이지 처리용 offset/limit 파라미터 지원.
     */
    List<SvcCnclSyncItemDto> selectPendingCancelList(@Param("offset") int offset,
                                                     @Param("limit") int limit);

    /**
     * [배치] 해지 완료(CP) 동기화 — REQUEST_KEY 기준 PROC_CD 업데이트.
     * 이미 CP인 건은 업데이트하지 않음 (이중 처리 방지).
     */
    int updateSyncProcCd(@Param("requestKey") Long requestKey,
                         @Param("procCd") String procCd);
}
