package com.ktmmobile.msf.form.termination.dao;

import java.util.List;

import com.ktmmobile.msf.form.termination.dto.CancelConsultDto;

public interface CancelConsultDao {

    int countCancelConsult(CancelConsultDto cancelConsultDto);

    int insertNmcpCustReqMst(CancelConsultDto cancelConsultDto);

    int insertCancelConsult(CancelConsultDto cancelConsultDto);

    List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto);

}
