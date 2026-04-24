package com.ktis.msp.batch.job.rcp.rcpmgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.openmgmt.vo.OpenMgmtListVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpCombMgmtListVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RcpCombMgmtMapper")
public interface RcpCombMgmtMapper {
	
	public List<RcpCombMgmtListVO> selectRcpCombListExcel(RcpCombMgmtListVO rcpCombMgmtListVO);
	void selectRcpCombListExcel(RcpCombMgmtListVO rcpCombMgmtListVO, ResultHandler resultHandler);
	
	
}
