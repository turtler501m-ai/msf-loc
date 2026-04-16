package com.ktmmobile.msf.domains.form.form.termination.service;

import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;

public interface MsfCancelPageSvc {

    RemainChargeResVO getRemainCharge(RemainChargeReqDto reqDto);

    TerminationApplyResVO apply(TerminationApplyReqDto reqDto);
}
