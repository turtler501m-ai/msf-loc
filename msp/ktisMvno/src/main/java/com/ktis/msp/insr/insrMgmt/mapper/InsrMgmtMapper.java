package com.ktis.msp.insr.insrMgmt.mapper;

import java.util.List;

import com.ktis.msp.insr.insrMgmt.vo.InsrMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("InsrMgmtMapper")
public interface InsrMgmtMapper {
	
	List<InsrMgmtVO> getIntmInsrProdList(InsrMgmtVO insrMgmtVO);
	
	int chkDupIntmInsrProd(InsrMgmtVO insrMgmtVO);
	
	int regIntmInsrMst(InsrMgmtVO insrMgmtVO);
	
	int updIntmInsrMst(InsrMgmtVO insrMgmtVO);
	
	List<InsrMgmtVO> getIntmInsrMstList(InsrMgmtVO insrMgmtVO);
	
	List<InsrMgmtVO> getPrdtListByPrdtNm(InsrMgmtVO insrMgmtVO);
	
	int regIntmInsrRel(InsrMgmtVO insrMgmtVO);
	
	int chkDupIntmInsrPrdt(InsrMgmtVO insrMgmtVO);
	
	List<InsrMgmtVO> getIntmInsrRelList(InsrMgmtVO insrMgmtVO);
	
	List<InsrMgmtVO> getIntmInsrRelListByPaging(InsrMgmtVO insrMgmtVO);
	
	int updIntmInsrRel(InsrMgmtVO insrMgmtVO);
	
	List<EgovMap> getInsrCombo(InsrMgmtVO insrMgmtVO);
	
	List<EgovMap> getChgInsrCombo(InsrMgmtVO insrMgmtVO);
	
}
