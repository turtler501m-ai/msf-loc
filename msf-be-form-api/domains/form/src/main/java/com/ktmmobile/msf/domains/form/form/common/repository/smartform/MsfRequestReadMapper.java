package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0FrmInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestMstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormInfoResponse;

@Mapper
public interface MsfRequestReadMapper {

    McpCancelRequestVo selectMcpCancelRequest(@Param("requestKey") Long requestKey);

    McpRequestCstmrVo selectMcpCancelRequestCstmr(@Param("requestKey") Long requestKey);

    McpRequestAgentVo selectMcpCancelRequestAgent(@Param("requestKey") Long requestKey);

    McpCustRequestMstVo selectMcpCancelCustRequestMst(@Param("requestKey") Long requestKey);

    McpRequestCstmrVo selectMcpSvcChgRequestCstmr(@Param("requestKey") Long requestKey);

    McpRequestAgentVo selectMcpSvcChgRequestAgent(@Param("requestKey") Long requestKey);

    McpCustRequestMstVo selectMcpSvcChgCustRequestMst(@Param("requestKey") Long requestKey);

    int countMsfRequestSvcChg(@Param("requestKey") Long requestKey);

    OwnerChangeFormInfoResponse selectMsfRequestOwnerChgInfo(@Param("requestKey") Long requestKey);

    MplatFormFMC0FrmInfoResponse selectMsfFMC0(MsfRequestNameChgVo request);
}
