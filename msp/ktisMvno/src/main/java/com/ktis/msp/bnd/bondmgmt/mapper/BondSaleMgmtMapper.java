package com.ktis.msp.bnd.bondmgmt.mapper;

import java.util.List;

import com.ktis.msp.bnd.bondmgmt.vo.BondSaleMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bondSaleMgmtMapper")
public interface BondSaleMgmtMapper {
	
	// 매각채권수납내역 조회
	List<?> selectSoldReceiptByMonth(BondSaleMgmtVO vo);
	
	// 매각채권수납내역 합계
	List<?> selectSoldReceiptByMonthSum(BondSaleMgmtVO vo);
	
   // 채권판매대상조회 조회
   List<?> getBondSaleObjectLstInfo(BondSaleMgmtVO vo);
   
   // 채권판매대상건수 및 총금액 조회
   List<?> getBondSaleObjectHap(BondSaleMgmtVO vo);
   
   /* 
    * 채권판매생성
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
   void callCreateBondSale(Map<String, Object> commandMap) throws EgovBizException;
   */
   
   // 채권판매내역 조회
   List<?> getBondSaleInfo(BondSaleMgmtVO vo);
   
   // 채권판매상세내역 조회
   List<?> getBondSaleCntrList(BondSaleMgmtVO vo);
   
   // 채권상세정보 조회
   List<?> getBondSaleAssetDtlInfo(BondSaleMgmtVO vo);
   
   /* 
    * 채권판매제외 처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
   void deleteBondSaleDtl(Map<String, Object> commandMap) throws EgovBizException;
   */
   
   /* 
    * 채권판매처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
   void updateBondSaleInfo(Map<String, Object> commandMap) throws EgovBizException;
   */
   
   /* 
    * 채권판매회수처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
   void updateBckBondSaleDtl(Map<String, Object> commandMap) throws EgovBizException;
   void updateBckBondAsset(Map<String, Object> commandMap) throws EgovBizException;
   void updateBckBondSaleSttc(Map<String, Object> commandMap) throws EgovBizException;
   */
   
   // 자산명세서 채권정보 조회
   List<?> selectSoldAssetList(BondSaleMgmtVO vo);
   
   // 자산명세서 채권정보 합계 조회
   List<?> selectSoldAssetListBySum(BondSaleMgmtVO vo);
   
   // 자산명세서 채권 상세 정보 조회
   List<?> selectSoldAssetDtlInfo(BondSaleMgmtVO vo);
   
   // STATIC DATA 조회
   List<?> selectSoldBondStaticInfoList(BondSaleMgmtVO vo);
   
   // 수납내역서 조회
   List<?> selectSaleStaticDtl(BondSaleMgmtVO vo);
   
   // 수납내역서 확정
   int confirmReceipt(BondSaleMgmtVO vo);
   
   // 회수채권 정보 조회
   List<?> selectSaleBckInfoList(BondSaleMgmtVO vo);
   
   // 회수채권 정보 합계 조회
   List<?> selectSaleBckInfoListBySum(BondSaleMgmtVO vo);
   
   // Scheduled CF 조회
   List<?> selectScheduledCfList(BondSaleMgmtVO vo);
   
   // 공시용자산명세서 조회
   List<?> selectSoldAssetList2(BondSaleMgmtVO vo);
   
   // 공시용자산명세서 합계
   List<?> selectSoldAssetList2BySum(BondSaleMgmtVO vo);
   
}


