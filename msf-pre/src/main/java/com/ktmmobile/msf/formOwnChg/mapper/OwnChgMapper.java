package com.ktmmobile.msf.formOwnChg.mapper;

import com.ktmmobile.msf.formOwnChg.dto.OwnChgTrnsInsertDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgTrnsfeInsertDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 명의변경 양도인/양수인 DB 저장.
 * CsTransferMapper.xml grantorRegSave, assigneeRegSave 참조.
 */
@Mapper
public interface OwnChgMapper {

    /** 접수번호 시퀀스 조회 */
    Long nextTrnsApyNo();

    /** 양도인 저장 (NMCP_NFL_CHG_TRNS) */
    int insertGrantor(OwnChgTrnsInsertDto dto);

    /** 양수인 저장 (NMCP_NFL_CHG_TRNSFE) */
    int insertTransferee(OwnChgTrnsfeInsertDto dto);

    /** 양도인 상태값 변경 (ASIS statusEdit). 양수인 저장 후 TRNS_STATUS_VAL='2' 신청완료 */
    int updateGrantorStatus(@Param("trnsApyNo") Long trnsApyNo, @Param("trnsStatusVal") String trnsStatusVal);
}
