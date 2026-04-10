package com.ktis.msp.sale.ktPlcyMgmt.mapper;

import java.util.List;

import com.ktis.msp.sale.ktPlcyMgmt.vo.KtPlcyMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("ktPlcyMgmtMapper")
public interface KtPlcyMgmtMapper {
	
	List<KtPlcyMgmtVO> getKtPlcyList(KtPlcyMgmtVO vo);
	
	List<KtPlcyMgmtVO> getKtPlcyDtlList(KtPlcyMgmtVO vo);
	
	int getMspKtPlcyMstBySlsNo(KtPlcyMgmtVO vo);
	
	int regMspKtPlcyMst(KtPlcyMgmtVO vo);
	
	int regMspKtPlcyHst(KtPlcyMgmtVO vo);
	
	int regMspKtPlcyDiscount(KtPlcyMgmtVO vo);
	
	int regMspKtPlcyOper(KtPlcyMgmtVO vo);
	
	int regMspKtPlcyInst(KtPlcyMgmtVO vo);
	
	int regMspKtPlcyEngg(KtPlcyMgmtVO vo);
	
	int updMspKtPlcyMst(KtPlcyMgmtVO vo);
	
	List<KtPlcyMgmtVO> getMspKtPlcyDiscountByGrid(KtPlcyMgmtVO vo);
	
	List<KtPlcyMgmtVO> getMspKtPlcyOperByGrid(KtPlcyMgmtVO vo);
	
	List<KtPlcyMgmtVO> getMspKtPlcyInstGrid(KtPlcyMgmtVO vo);
	
	List<KtPlcyMgmtVO> getMspKtPlcyEnggByGrid(KtPlcyMgmtVO vo);
	
	List<EgovMap> getKtPlcyListCombo(KtPlcyMgmtVO vo);
	
}
