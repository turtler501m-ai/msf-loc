package com.ktis.msp.batch.job.rsk.rskmgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rsk.rskmgmt.vo.RskMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RskMgmtMapper")
public interface RskMgmtMapper {

	List<RskMgmtVO> selectNgCustListExcel(RskMgmtVO searchVO);
	
	void selectNgCustListExcel(RskMgmtVO searchVO, ResultHandler resultHandler);
	
	void selectSaleSttcListExcel(RskMgmtVO searchVO, ResultHandler resultHandler);
}
