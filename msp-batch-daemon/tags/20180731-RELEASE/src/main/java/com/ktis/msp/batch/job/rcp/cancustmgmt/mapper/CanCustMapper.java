package com.ktis.msp.batch.job.rcp.cancustmgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.cancustmgmt.vo.CanCustVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CanCustMapper")
public interface CanCustMapper {

	public List<CanCustVO> selectCanCustListExcel(CanCustVO canCustVO);
	void selectCanCustListExcel(CanCustVO canCustVO, ResultHandler resultHandler);
}
