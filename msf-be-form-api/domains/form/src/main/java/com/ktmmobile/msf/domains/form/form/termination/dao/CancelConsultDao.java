package com.ktmmobile.msf.domains.form.form.termination.dao;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationInsertDto;

public interface CancelConsultDao {

    int countCancelConsult(CancelConsultDto cancelConsultDto);

    int insertNmcpCustReqMst(CancelConsultDto cancelConsultDto);

    int insertCancelConsult(CancelConsultDto cancelConsultDto);

    List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto);

    Long nextRequestKey();

    int insertRequestCancel(TerminationInsertDto dto);

}
