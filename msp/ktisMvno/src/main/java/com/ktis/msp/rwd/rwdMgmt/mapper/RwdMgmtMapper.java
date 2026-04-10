package com.ktis.msp.rwd.rwdMgmt.mapper;

import java.util.List;

import com.ktis.msp.rwd.rwdMgmt.vo.RwdMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("RwdMgmtMapper")
public interface RwdMgmtMapper {
	
	List<RwdMgmtVO> getRwdProdList(RwdMgmtVO rwdMgmtVO);
	
	int chkDupRwdProd(RwdMgmtVO rwdMgmtVO);
	
	int regRwdMst(RwdMgmtVO rwdMgmtVO);
	
	int updRwdMst(RwdMgmtVO rwdMgmtVO);
	
	List<RwdMgmtVO> getRwdMstList(RwdMgmtVO rwdMgmtVO);
	
	List<RwdMgmtVO> getPrdtListByPrdtNm(RwdMgmtVO rwdMgmtVO);
	
	int regRwdRel(RwdMgmtVO rwdMgmtVO);
	
	int chkDupRwdPrdt(RwdMgmtVO rwdMgmtVO);
	
	List<RwdMgmtVO> getRwdRelList(RwdMgmtVO rwdMgmtVO);
	
	List<RwdMgmtVO> getRwdRelListByPaging(RwdMgmtVO rwdMgmtVO);
	
	int updRwdRel(RwdMgmtVO rwdMgmtVO);
	
	List<EgovMap> getRwdCombo(RwdMgmtVO rwdMgmtVO);
	
	List<EgovMap> getChgRwdCombo(RwdMgmtVO rwdMgmtVO);
	
}
