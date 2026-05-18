package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;

@Mapper
public interface McpRequestWriteMapper {

    int insertMcpCancelRequest(McpCancelRequestVo vo);

    int insertMcpRequestCstmr(McpRequestCstmrVo vo);

    int insertMcpRequest(McpRequestVo vo);

    void insertNmcpCustReqMst(MsfRequestNameChgVo request);

    void insertNmcpCustReqNameChg(MsfRequestNameChgVo request);

    void insertNmcpCustReqNameChgAgent(MsfRequestNameChgVo request);

    void updateNmcpCustReqMst(MsfRequestNameChgVo request);

    void updateNmcpCustReqNameChg(MsfRequestNameChgVo request);
}
