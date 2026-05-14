package com.ktmmobile.msf.domains.form.form.termination.service;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;

@Deprecated
public interface MsfCancelConsultSvc {

    int countCancelConsult(CancelConsultDto cancelConsultDto);

    FormResponse<Void> cancelConsultRequest(CancelConsultDto cancelConsultDto);

    FormResponse<Void> cancelConsultAjax(CancelConsultDto cancelConsultDto, HttpSession session);

    List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto);

}
