package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import jakarta.validation.Valid;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestStateDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpErrVO;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestChangeVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestMstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestNameChgAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestOsstVo;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpReqCombineDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Request;

@Mapper
public interface McpRequestWriteMapper {

    int insertMcpCancelRequest(McpCancelRequestVo vo);

    int insertMcpRequestCstmr(McpRequestCstmrVo vo);

    int insertMcpRequest(McpRequestVo vo);

    int insertMcpRequestState(McpRequestStateDto dto);

    int insertMcpRequestOsstIfAbsent(McpRequestOsstDto dto);

    int insertOsstErrLog(MpErrVO vo);

    void insertNmcpCustReqMst(McpCustRequestMstVo request);

    void insertNmcpCustReqNameChg(McpCustRequestNameChgVo request);

    void insertNmcpCustReqNameChgAgent(McpCustRequestNameChgAgentVo request);

    void updateNmcpCustReqMst(McpCustRequestMstVo request);

    void updateNmcpCustReqNameChg(McpCustRequestNameChgVo request);

    void insertCustRequestInsr(InsuranceProcessRequest request);

    void insertMcpSelfUsimChg(@Valid UsimChangeUC0Request request);

    void updateMcpSelfUsimChgUC0(@Valid UsimChangeUC0Request request);

    void insertMcpReqCombine(McpReqCombineDto request);

    void updateRequestOsst(MsfRequestOsstVo msfRequestOsstVo);

    void insertNmcpCustReqChange(McpCustRequestChangeVo mcpCustRequestChangeVo);

    String getDisPrmtId(MspSaleSubsdMstRequest request);

    Long selectPromoBaseAmt(String prmtId);
}
