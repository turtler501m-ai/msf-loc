package com.ktis.msp.batch.job.rcp.rcpmgmt.mapper;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.Daily3gSubVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("Daily3gSubMapper")
public interface Daily3gSubMapper {
	
	int insert3gSubContNum1st();
	
	int insert3gSubContNum2nd();
	
	int insert3gSubContNum3rd();
	
	int insert3gSubContNum4th();
	
	int insert3gSubContNum5th();
	
	int insertDaily3gSub();
	
	int insertDaily3gSub2();
	
	int delete3gSubContNum();
	
	void getDaily3gSubListExcel(Daily3gSubVO searchVO, ResultHandler resultHandler);
}
