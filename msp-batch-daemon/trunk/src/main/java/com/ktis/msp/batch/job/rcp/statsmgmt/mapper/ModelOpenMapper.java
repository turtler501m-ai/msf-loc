package com.ktis.msp.batch.job.rcp.statsmgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.openmgmt.vo.OpenMgmtListVO;
import com.ktis.msp.batch.job.rcp.statsmgmt.vo.ModelOpenListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ModelOpenMapper")
public interface ModelOpenMapper {

	//List<OpenMgmtListVO> selectOpenMgmtListExcel(OpenMgmtListVO searchVO);
	
	void selectModelOpenListExcel(ModelOpenListVO searchVO, ResultHandler resultHandler);
}
