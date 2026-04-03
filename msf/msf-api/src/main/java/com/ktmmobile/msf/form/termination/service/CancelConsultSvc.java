package com.ktmmobile.msf.form.servicechange.service;

import java.util.List;

import com.ktmmobile.msf.form.servicechange.dto.CancelConsultDto;

public interface CancelConsultSvc {

    int countCancelConsult(CancelConsultDto cancelConsultDto);

    String cancelConsultRequest(CancelConsultDto cancelConsultDto);

    public List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto);
}
