package com.ktmmobile.msf.domains.form.form.termination.repository.smartform;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CancelPageMapper {

    Long nextRequestKey();

    int countInProgressApplicationByMobileNo(@Param("mobileNo") String mobileNo);

    // [ASIS] insertRequestCancel(TerminationApplyReqDto) — MsfRequestWriteMapper.insertMsfRequestCancel 으로 이관
}
