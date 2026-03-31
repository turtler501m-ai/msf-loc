package com.ktmmobile.msf.formOwnChg.mapper;

import com.ktmmobile.msf.formOwnChg.dto.OwnChgCustReqInsertDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgSyncItemDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgTrnsInsertDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgTrnsfeInsertDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 명의변경 DB 매퍼.
 * ASIS: CsTransferMapper(양도/양수인 2테이블) + MyNameChgMapper(신청서 3테이블) 통합.
 */
@Mapper
public interface OwnChgMapper {

    // ──── 기존: CsTransfer 양도/양수인 2테이블 ────

    /** 접수번호 시퀀스 조회 */
    Long nextTrnsApyNo();

    /** 양도인 저장 (NMCP_NFL_CHG_TRNS) */
    int insertGrantor(OwnChgTrnsInsertDto dto);

    /** 양수인 저장 (NMCP_NFL_CHG_TRNSFE) */
    int insertTransferee(OwnChgTrnsfeInsertDto dto);

    /** 양도인 상태값 변경 */
    int updateGrantorStatus(@Param("trnsApyNo") Long trnsApyNo, @Param("trnsStatusVal") String trnsStatusVal);

    // ──── 신규: ASIS MyNameChg 신청서 3테이블 ────

    /** 신청서 시퀀스 발급 (MSF_CUST_REQUEST_MST) */
    Long nextCustReqSeq();

    /** 예약번호 발급 (ASIS appformDao.generateResNo 대응) */
    String generateResNo();

    /** 양도인 마스터 저장 (MSF_CUST_REQUEST_MST) */
    int insertCustReqMst(OwnChgCustReqInsertDto dto);

    /** 양수인 상세 저장 (MSF_CUST_REQUEST_NAME_CHG) */
    int insertCustReqNameChg(OwnChgCustReqInsertDto dto);

    /** 양도인 상세 + 법정대리인 저장 (MSF_REQUEST_NAME_TRNS). 미성년자 양도인 시 호출. */
    int insertCustReqNameChgAgent(OwnChgCustReqInsertDto dto);

    /** 양도인 마스터 인증정보 초기화 — 미성년자 양도인 시 법정대리인 인증으로 대체 */
    int updateCustReqMstAuth(OwnChgCustReqInsertDto dto);

    /** 양수인 상세 인증정보 초기화 — 미성년자 양수인 시 법정대리인 인증으로 대체 */
    int updateCustReqNameChgAuth(OwnChgCustReqInsertDto dto);

    // ──── grantorReqChk: 양도인 체크용 조회 ────

    /** 명의변경 회선 조회 (ASIS MypageMapper.selectCntrListNmChg 대응) */
    List<Map<String, String>> selectCntrListNmChg(HashMap<String, String> map);

    // ──── 배치 전용 ────

    /**
     * [배치] PROC_CD='RC'(접수) 상태인 명의변경 신청 목록 조회.
     * 100건 단위 페이지 처리용 offset/limit 파라미터 지원.
     */
    List<OwnChgSyncItemDto> selectPendingOwnChgList(@Param("offset") int offset,
                                                    @Param("limit") int limit);

    /**
     * [배치] 명의변경 완료(CP) 동기화 -- REQUEST_KEY 기준 PROC_CD 업데이트.
     * 이미 CP인 건은 업데이트하지 않음 (이중 처리 방지).
     */
    int updateOwnChgProcCd(@Param("requestKey") Long requestKey,
                           @Param("procCd") String procCd);
}
