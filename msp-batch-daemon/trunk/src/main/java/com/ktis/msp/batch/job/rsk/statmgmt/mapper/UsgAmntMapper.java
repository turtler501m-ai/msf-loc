package com.ktis.msp.batch.job.rsk.statmgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rsk.statmgmt.vo.UsgAmntVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UsgAmntMapper")
public interface UsgAmntMapper {

	List<UsgAmntVO> getUsgAmntStatOrgListExcel(UsgAmntVO searchVO);
	
	void getUsgAmntStatOrgListExcel(UsgAmntVO searchVO, ResultHandler resultHandler);

}
