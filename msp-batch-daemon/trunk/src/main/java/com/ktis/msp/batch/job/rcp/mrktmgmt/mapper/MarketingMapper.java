package com.ktis.msp.batch.job.rcp.mrktmgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.rcp.mrktmgmt.vo.MarketingVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("marketingMapper")
public interface MarketingMapper {

	List<MarketingVO> getMarketingSendTarget();
	
	List<MarketingVO> getMarketingReSendTarget();
	
	int updateSmsSendMst(MarketingVO paramVO);
	
}
