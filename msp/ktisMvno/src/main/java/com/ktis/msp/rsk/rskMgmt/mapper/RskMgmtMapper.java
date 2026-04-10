package com.ktis.msp.rsk.rskMgmt.mapper;

import java.util.List;

import com.ktis.msp.rsk.rskMgmt.vo.RskMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rskMgmtMapper")
public interface RskMgmtMapper {
	
	public List<?> getNgCustMgmtList(RskMgmtVO vo);
	
	public List<?> getNgCustMgmtListExcel(RskMgmtVO vo);
	
	public List<?> getNgAccMgmtList(RskMgmtVO vo);
	
	public List<?> getNgAccMgmtListExcel(RskMgmtVO vo);
}
