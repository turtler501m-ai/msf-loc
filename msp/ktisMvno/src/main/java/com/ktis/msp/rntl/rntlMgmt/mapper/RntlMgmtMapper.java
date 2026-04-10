package com.ktis.msp.rntl.rntlMgmt.mapper;

import java.util.List;

import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByMnthCalcListVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByPurchSaleListVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByPurchStandVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtBySaleOpenMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rntlMgmtMapper")
public interface RntlMgmtMapper {
	List<RntlMgmtByPurchStandVO> getPurchStandList(RntlMgmtByPurchStandVO vo);
	
	List<RntlMgmtByPurchStandVO> getPurchStandListExcel(RntlMgmtByPurchStandVO vo);
	
	List<RntlMgmtByPurchStandVO> getPurchStandDtlList(RntlMgmtByPurchStandVO vo);
	
	List<RntlMgmtByPurchStandVO> getMspRntlBaseInfo(RntlMgmtByPurchStandVO vo);
	
	int regPurchStandByInfo(RntlMgmtByPurchStandVO vo);
	
	int updPurchStandByInfo(RntlMgmtByPurchStandVO vo);
	
	List<RntlMgmtBySaleOpenMgmtVO> getSaleOpenList(RntlMgmtBySaleOpenMgmtVO vo);
	
	List<RntlMgmtBySaleOpenMgmtVO> getSaleOpenListExcel(RntlMgmtBySaleOpenMgmtVO vo);
	
	List<RntlMgmtBySaleOpenMgmtVO> getMspRntlRtrnInfo(RntlMgmtBySaleOpenMgmtVO vo);
	
	int regReturnDt(RntlMgmtBySaleOpenMgmtVO vo);
	
	int updReturnDt(RntlMgmtBySaleOpenMgmtVO vo);
	
	int delReturnDt(RntlMgmtBySaleOpenMgmtVO vo);
	
	List<RntlMgmtByPurchSaleListVO> getPurchList(RntlMgmtByPurchSaleListVO vo);
	
	List<RntlMgmtByPurchSaleListVO> getSaleList(RntlMgmtByPurchSaleListVO vo);
	
	List<RntlMgmtByMnthCalcListVO> getMnthCalcList(RntlMgmtByMnthCalcListVO vo);
	
	List<RntlMgmtByMnthCalcListVO> getMnthCalcListExcel(RntlMgmtByMnthCalcListVO vo);
	
	List<RntlMgmtByMnthCalcListVO> getMnthCalcDtlList(RntlMgmtByMnthCalcListVO vo);
}
