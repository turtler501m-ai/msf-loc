package com.ktis.msp.rcp.commdatmgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.commdatmgmt.vo.CommDatMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("commDatMgmtMapper")
public interface CommDatMgmtMapper {
	public List<?> getCommDatePrvList(CommDatMgmtVO commDatMgmtVo);
	
	public List<?> getCommDatePrvListEx(CommDatMgmtVO commDatMgmtVo);
	
	public int updateResultYN(CommDatMgmtVO commDatMgmtVo);
	
	public CommDatMgmtVO getCommDatePrv(CommDatMgmtVO commDatMgmtVo);
}
