package com.ktmmobile.msf.domains.form.form.common.repository;

import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.MsfRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class McpRequestRepositoryImpl {

    private final MsfRequestReadMapper msfRequestReadMapper;
    private final McpRequestWriteMapper mcpRequestWriteMapper;

    public int insertMcpCancelRequest(Long requestKey) {
        /* 채지신청서관련 MSF 조회후 MCP INSERT 처리 */
        McpCancelRequestVo vo = msfRequestReadMapper.selectMcpCancelRequest(requestKey);
        if (vo == null) {
            return 0;
        }
        return mcpRequestWriteMapper.insertMcpCancelRequest(vo);
    }

    public int insertMcpRequestCstmr(Long requestKey) {
        McpRequestCstmrVo vo = msfRequestReadMapper.selectMcpCancelRequestCstmr(requestKey);
        if (vo == null) {
            return 0;
        }
        return mcpRequestWriteMapper.insertMcpRequestCstmr(vo);
    }

}
