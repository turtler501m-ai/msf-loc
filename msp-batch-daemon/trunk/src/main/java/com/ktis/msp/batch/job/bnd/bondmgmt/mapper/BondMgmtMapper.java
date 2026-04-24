package com.ktis.msp.batch.job.bnd.bondmgmt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.bnd.bondmgmt.vo.BondMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bondMgmtMapper")
public interface BondMgmtMapper {
	
	// 판매대상정보 엑셀저장	
	List<?> selectBondSaleObjectListEx(BondMgmtVO searchVO);
	void selectBondSaleObjectListEx(BondMgmtVO searchVO, ResultHandler resultHandler);
	
	// 보증보험리스트 엑셀로 저장
	List<?> selectGrntInsrInfoListEx(BondMgmtVO searchVO);
	
	// 공시용 자산명세서 리스트 가져오기 (엑셀)
	List<?> selectSoldAssetList2Ex(BondMgmtVO searchVO);
	void selectSoldAssetList2Ex(BondMgmtVO searchVO, ResultHandler resultHandler);
	
	// 판매채권 자산명세서 리스트 가져오기 (엑셀)
	List<?> selectSoldAssetListEx(BondMgmtVO searchVO);
	void selectSoldAssetListEx(BondMgmtVO searchVO, ResultHandler resultHandler);
	
	// 채권판매정보 계약내역 가져오기 (엑셀)
	List<?> selectBondSaleCntrListEx(BondMgmtVO searchVO);
	void selectBondSaleCntrListEx(BondMgmtVO searchVO, ResultHandler resultHandler);
	
	// 매각채권수납내역서 리스트 가져오기 (엑셀)
	List<?> selectSoldReceiptByMonth(BondMgmtVO searchVO);
	void selectSoldReceiptByMonth(BondMgmtVO searchVO, ResultHandler resultHandler);
	
}