package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.mapper;

import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.vo.LgsDayBatchVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("invtrDaySumMapper")
public interface InvtrDaySumMapper {
	
	// 일재고생성
	int createTodayInvtrDayMst(LgsDayBatchVo  inVo) ;
	
}
