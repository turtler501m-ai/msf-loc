package com.ktmmobile.msf.domains.form.form.common.repository;

import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.MsfRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class McpRequestRepositoryImpl {

    private final MsfRequestReadMapper msfRequestReadMapper;
    private final McpRequestWriteMapper mcpRequestWriteMapper;
    private final McpRequestReadMapper mcpRequestReadMapper;

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

    public int insertMcpSvcChgRequest(Long requestKey) {
        McpRequestVo vo = msfRequestReadMapper.selectMcpSvcChgRequest(requestKey);
        if (vo == null) {
            return 0;
        }
        return mcpRequestWriteMapper.insertMcpRequest(vo);
    }

    public int insertMcpSvcChgRequestCstmr(Long requestKey) {
        McpRequestCstmrVo vo = msfRequestReadMapper.selectMcpSvcChgRequestCstmr(requestKey);
        if (vo == null) {
            return 0;
        }
        return mcpRequestWriteMapper.insertMcpRequestCstmr(vo);
    }

    public String selectGenerateResNo() { return mcpRequestReadMapper.generateResNo(); }

    public void insertNmcpCustReqMst(MsfRequestNameChgVo request) {
        mcpRequestWriteMapper.insertNmcpCustReqMst(request);
    }

    public void insertNmcpCustReqNameChg(MsfRequestNameChgVo request) { mcpRequestWriteMapper.insertNmcpCustReqNameChg(request); }

    public void insertNmcpCustReqNameChgAgent(MsfRequestNameChgVo request) { mcpRequestWriteMapper.insertNmcpCustReqNameChgAgent(request); }

    public void updateNmcpCustReqMst(MsfRequestNameChgVo request) { mcpRequestWriteMapper.updateNmcpCustReqMst(request); }

    public void updateNmcpCustReqNameChg(MsfRequestNameChgVo request) { mcpRequestWriteMapper.updateNmcpCustReqNameChg(request); }

}
