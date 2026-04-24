package com.ktis.msp.batch.job.rcp.statsmgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.statsmgmt.vo.UsimChgHstListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UsimChgHstMapper")
public interface UsimChgHstMapper {
	
	List<String> selectUsimChgList();
	
	int insertUsimChgHst(String contractNum);
	
	void getUsimChgHstExcel(UsimChgHstListVO searchVO, ResultHandler resultHandler);

}
