package com.ktmmobile.mcp.usim.dao;

import com.ktmmobile.mcp.common.mplatform.vo.RetvUsimChgAcceptPsblVO;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimDto;

import java.util.List;

public interface ReplaceUsimDao {

	long generateReplaceUsimOsstNo();

	int saveFreeUsimOsst(RetvUsimChgAcceptPsblVO retvUsimChgAcceptPsblVO);

	int saveFreeUsimReqMst(ReplaceUsimDto replaceUsimDto);

	int saveFreeUsimReqDtl(RetvUsimChgAcceptPsblVO vo);

	List<RetvUsimChgAcceptPsblVO> selectFreeUsimOsstResult(long osstNo);
}
