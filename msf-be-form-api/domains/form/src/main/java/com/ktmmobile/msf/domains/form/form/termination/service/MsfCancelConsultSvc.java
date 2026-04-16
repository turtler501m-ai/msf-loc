package com.ktmmobile.msf.domains.form.form.termination.service;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;

public interface MsfCancelConsultSvc {

    int countCancelConsult(CancelConsultDto cancelConsultDto);

    String cancelConsultRequest(CancelConsultDto cancelConsultDto);

    List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto);

}
