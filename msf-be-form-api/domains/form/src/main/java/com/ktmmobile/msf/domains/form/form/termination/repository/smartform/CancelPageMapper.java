package com.ktmmobile.msf.domains.form.form.termination.repository.smartform;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CancelPageMapper {

    Long nextRequestKey();

    // [ASIS] insertRequestCancel(TerminationApplyReqDto) — MsfRequestWriteMapper.insertMsfRequestCancel 으로 이관
}
