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
	
	// 고객채권정보 엑셀저장
	List<?> selectBondMstListEx(BondMgmtVO searchVO);
	
	// 보증보험리스트 엑셀로 저장
	List<?> selectGrntInsrInfoListEx(BondMgmtVO searchVO);
	
	// 공시용 자산명세서 리스트 가져오기 (엑셀)
	List<?> selectSoldAssetList2Ex(BondMgmtVO searchVO);
	void selectSoldAssetList2Ex(BondMgmtVO searchVO, ResultHandler resultHandler);
	
	// Scheduled Cf 가져오기
	List<?> selectScheduledCf(BondMgmtVO searchVO);
	
	// 회수채권 리스트 가져오기
	List<?> selectSaleBckInfoList(BondMgmtVO searchVO);
	
	// 판매채권 개통월채권정보목록조회 (STATIC DATA)
	List<?> selectSoldBondStaticInfoList(BondMgmtVO searchVO);
	
	// 판매채권 자산명세서 리스트 가져오기 (엑셀)
	List<?> selectSoldAssetListEx(BondMgmtVO searchVO);
	void selectSoldAssetListEx(BondMgmtVO searchVO, ResultHandler resultHandler);
	
	// 채권판매정보 계약내역 가져오기 (엑셀)
	List<?> selectBondSaleCntrListEx(BondMgmtVO searchVO);
	
	// 채권스케줄 대용량
	void getBondScheBig(Map<String, Object> param, ResultHandler handler);
	
}