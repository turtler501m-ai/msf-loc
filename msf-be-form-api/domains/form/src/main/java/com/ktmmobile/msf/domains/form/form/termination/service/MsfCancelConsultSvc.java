package com.ktmmobile.msf.domains.form.form.termination.service;

import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;

import java.util.List;

public interface MsfCancelConsultSvc {

    int countCancelConsult(CancelConsultDto cancelConsultDto);

    String cancelConsultRequest(CancelConsultDto cancelConsultDto);

    List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto);

    RemainChargeResVO getRemainCharge(RemainChargeReqDto reqDto);

    TerminationApplyResVO apply(TerminationApplyReqDto reqDto);
}
