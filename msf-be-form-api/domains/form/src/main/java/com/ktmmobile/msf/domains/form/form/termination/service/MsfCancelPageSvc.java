package com.ktmmobile.msf.domains.form.form.termination.service;

import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeResVO;

import java.util.List;

public interface MsfCancelPageSvc {

    /**
     * 서비스해지 화면의 대리점 정보를 조회한다.
     */
    List<AgentInfoResponse> getTerminationAgentInfo(AgentInfoRequest request);

    /**
     * 계약번호(ncn)로 회선 정보를 보강한 뒤 잔여요금 정보를 조회한다.
     */
    TerminationRemainChargeResVO getRemainCharge(TerminationRemainChargeReqDto reqDto);

    /**
     * 작성완료 처리 시간을 로깅하고 실제 신청 처리는 apply에 위임한다.
     */
    TerminationApplyResVO complete(String applicationKey, TerminationApplyReqDto reqDto);

    /**
     * 서비스해지 신청 데이터를 MSF 저장소에 먼저 저장하고 MCP DB link 테이블로 이관한다.
     */
    TerminationApplyResVO apply(TerminationApplyReqDto reqDto);
}
