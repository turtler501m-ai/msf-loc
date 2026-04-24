package com.ktis.msp.batch.manager.mapper;

import com.ktis.msp.batch.manager.vo.VacCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("vacCommonMapper")
public interface VacCommonMapper {
	
	VacCommonVO callVacOnceInfo(VacCommonVO vo);
	
	String selectVacBankNm(String bankCd);
	
}
