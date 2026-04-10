package com.ktmmobile.mcp.usim.service;

import com.ktmmobile.mcp.common.mplatform.vo.RetvUsimChgAcceptPsblVO;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimDto;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimSubDto;

import java.util.List;

public interface ReplaceUsimService {

	String selectCustomerIdByConnInfo(ReplaceUsimDto replaceUsimDto);

	List<ReplaceUsimSubDto> selectReplaceUsimSubInfo(String customerId);

	List<RetvUsimChgAcceptPsblVO> selectReplaceUsimReqInfo(ReplaceUsimDto replaceUsimDto);

	void saveReplaceUsimRequest(ReplaceUsimDto replaceUsimDto, List<RetvUsimChgAcceptPsblVO> retvUsimChgAcceptPsblVOs);

	McpUserCntrMngDto selectContractInfoForReplaceUsim(ReplaceUsimDto replaceUsimDto);

	ReplaceUsimDto selectNfcUsimYn(String intmMdlId);
}
