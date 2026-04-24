package com.ktis.msp.batch.job.rcp.statsmgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.statsmgmt.vo.StatsMgmtListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("StatsMgmtMapper")
public interface StatsMgmtMapper {

	List<StatsMgmtListVO> selectStatsMgmtListExcel(StatsMgmtListVO searchVO);
	
	void selectStatsMgmtListExcel(StatsMgmtListVO searchVO, ResultHandler resultHandler);
}
