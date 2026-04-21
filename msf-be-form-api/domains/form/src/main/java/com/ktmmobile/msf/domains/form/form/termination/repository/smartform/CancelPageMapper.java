package com.ktmmobile.msf.domains.form.form.termination.repository.smartform;

import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CancelPageMapper {

    Long nextRequestKey();

    int insertRequestCancel(TerminationApplyReqDto dto);
}
