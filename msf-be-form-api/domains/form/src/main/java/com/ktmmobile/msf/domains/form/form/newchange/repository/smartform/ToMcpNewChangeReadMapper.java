package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestSaleinfoVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpUploadPhoneInfoVo;

@Mapper
public interface ToMcpNewChangeReadMapper {

    McpRequestVo selectMsfRequestToMcp(Long requestKey);

    McpRequestCstmrVo selectMsfRequestCstmrToMcp(Long requestKey);

    McpRequestAgentVo selectMsfRequestAgentToMcp(Long requestKey);

    McpRequestReqVo selectMsfRequestBillReqToMcp(Long requestKey);

    McpRequestSaleinfoVo selectMsfRequestSaleinfoToMcp(Long requestKey);

    List<McpRequestAdditionVo> selectMsfRequestAdditionToMcp(Long requestKey);

    McpRequestDvcChgVo selectMsfRequestDvcChgToMcp(Long requestKey);

    McpRequestMoveVo selectMsfRequestMoveToMcp(Long requestKey);

    McpRequestVo selectMsfRequestTempToMcp(Long requestKey);

    McpRequestCstmrVo selectMsfRequestCstmrTempToMcp(Long requestKey);

    McpRequestSaleinfoVo selectMsfRequestSaleinfoTempToMcp(Long requestKey);

    McpRequestMoveVo selectMsfRequestMoveTempToMcp(Long requestKey);

    McpUploadPhoneInfoVo selectMsfUploadPhoneInfoToMcp(Long requestKey);

    //McpRequestClauseVo selectMsfRequestClauseToMcp(Long requestKey);

    //McpRequestStateVo selectMsfRequestStateToMcp(Long requestKey);

    //McpRequestOsstVo selectMsfRequestOsstToMcp(Long requestKey);


}
