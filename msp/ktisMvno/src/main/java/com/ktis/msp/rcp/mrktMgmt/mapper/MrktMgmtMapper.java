package com.ktis.msp.rcp.mrktMgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.mrktMgmt.vo.MrktMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("mrktMgmtMapper")
public interface MrktMgmtMapper {
	
	List<?> getMarketingSmsSendList(MrktMgmtVO searchVO);
	
	List<?> getMarketingSmsSendListOld(MrktMgmtVO searchVO);
	
	List<?> getMarketingHistoryList(MrktMgmtVO searchVO);

	List<?> getMspMrktAgrMgmtByInfo(MrktMgmtVO searchVO);

	int expireMrktAgrMgmt(MrktMgmtVO searchVO);

	int regMspMrktAgrMgmtByInfo(MrktMgmtVO searchVO);
	
	int updMspRequestByInfo(MrktMgmtVO searchVO);

	MrktMgmtVO getContractInfo(MrktMgmtVO searchVO);
}
