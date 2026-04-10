package com.ktis.msp.insr.insrMgmt.mapper;

import java.util.List;

import com.ktis.msp.insr.insrMgmt.vo.InsrCmpnVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InsrCmpnMapper")
public interface InsrCmpnMapper {
	
	List<InsrCmpnVO> getInsrCmpnListByInsrMngmNo(InsrCmpnVO insrCmpnVO);
	
	List<InsrCmpnVO> getInsrCmpnList(InsrCmpnVO insrCmpnVO);
	
	List<InsrCmpnVO> getInsrCmpnListByExcel(InsrCmpnVO insrCmpnVO);
	
	int updIntmInsrCmpnDtl(InsrCmpnVO insrCmpnVO);
	
	InsrCmpnVO pVacOnce(InsrCmpnVO insrCmpnVO);
	
	List<InsrCmpnVO> getInsrCmpnMbsList(InsrCmpnVO insrCmpnVO);

	List<InsrCmpnVO> getInsrCmpnMbsListByExcel(InsrCmpnVO insrCmpnVO);

	List<InsrCmpnVO> getInsrCanList(InsrCmpnVO insrCmpnVO);

	List<InsrCmpnVO> getInsrCanListByExcel(InsrCmpnVO insrCmpnVO);
	
}
