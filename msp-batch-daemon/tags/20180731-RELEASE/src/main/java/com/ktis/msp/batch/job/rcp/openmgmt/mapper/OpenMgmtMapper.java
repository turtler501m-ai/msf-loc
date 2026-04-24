package com.ktis.msp.batch.job.rcp.openmgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.openmgmt.vo.OpenMgmtListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("OpenMgmtMapper")
public interface OpenMgmtMapper {

	List<OpenMgmtListVO> selectOpenMgmtListExcel(OpenMgmtListVO searchVO);
	
	void selectOpenMgmtListExcel(OpenMgmtListVO searchVO, ResultHandler resultHandler);
}
