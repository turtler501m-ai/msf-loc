package com.ktmmobile.msf.formSvcCncl.service;

import com.ktmmobile.msf.formSvcCncl.dto.McpCancelRegisterReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.McpCancelRegisterResVO;

/**
 * M포탈 서비스해지 적재 서비스 인터페이스.
 * MSF 신청 완료 후 M포탈 MCP_CANCEL_REQUEST에 동기 적재.
 * M전산이 기존 MCP_CANCEL_REQUEST를 그대로 조회할 수 있도록 MSF 건도 M포탈 테이블에 등록.
 */
public interface McpCancelRegisterSvc {

    /**
     * M포탈 서비스해지 적재 API 호출.
     * POST {mcp.portal.url}/mcp/api/cancel/msf-register
     * LOCAL 환경(mcp.cancel.register.url 미설정): Mock 응답 반환.
     *
     * @param req MSF 신청서 데이터
     * @return 성공 시 M포탈 custReqSeq 포함, 실패 시 message
     */
    McpCancelRegisterResVO register(McpCancelRegisterReqDto req);
}
