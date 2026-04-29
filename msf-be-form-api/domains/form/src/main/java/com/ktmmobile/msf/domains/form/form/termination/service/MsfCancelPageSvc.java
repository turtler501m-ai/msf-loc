package com.ktmmobile.msf.domains.form.form.termination.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;

public interface MsfCancelPageSvc {

    /** Looks up agent information for the termination screen. */
    AgentInfoDto getTerminationAgentInfo(AgentInfoRequest request);

    /** Enriches line information by ncn and returns remaining charge data. */
    TerminationRemainChargeResVO getRemainCharge(TerminationRemainChargeReqDto reqDto);

    /** Returns subscription information needed by the termination screen. */
    Map<String, Object> getMyinfoView(HttpServletRequest request, MyPageSearchDto searchVO);

    /** Wraps completion logging around termination application processing. */
    TerminationApplyResVO complete(String applicationKey, TerminationApplyReqDto reqDto);

    /** Persists termination application data into MSF and MCP stores. */
    TerminationApplyResVO apply(TerminationApplyReqDto reqDto);
}
