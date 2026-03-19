package com.ktmmobile.msf.formSvcCncl.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 서비스해지 신청서 DB 저장 Mapper.
 * ASIS M포탈 해지 저장 처리와 동일.
 */
@Mapper
public interface SvcCnclMapper {

    /** 해지 접수번호 발급 (시퀀스) */
    Long nextCancelApyNo();

    /** 해지 신청서 저장 */
    int insertCancel(SvcCnclInsertDto dto);
}
