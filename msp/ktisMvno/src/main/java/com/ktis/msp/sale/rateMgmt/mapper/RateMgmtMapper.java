package com.ktis.msp.sale.rateMgmt.mapper;

import java.util.List;

import com.ktis.msp.sale.rateMgmt.vo.DisAddSvcVO;
import com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rateMgmtMapper")
public interface RateMgmtMapper {
	
	public List<?> getRateMgmtList(RateMgmtVO searchVO);
	
	public Integer getRateCdChk(RateMgmtVO searchVO);
	
	public void insertRateCd(RateMgmtVO searchVO);
	
	public void updateRateCd(RateMgmtVO searchVO);
	
	public List<?> getRateGrpMgmtList(RateMgmtVO searchVO);
	
	public Integer getRateGrpCdChk(RateMgmtVO searchVO);
	
	public void insertRateGrpCd(RateMgmtVO searchVO);
	
	public void deleteRateGrpCd(RateMgmtVO searchVO);
	
	public void updateRateGrpCd(RateMgmtVO searchVO);

	public List<?> getAddSvcList();

	public List<?> getDisAddSvcList();

	public List<DisAddSvcVO> getAllDisAddSvcList();

	public List<DisAddSvcVO> getDisAddSvcToVOList();

	public void deleteDisAddSvc(DisAddSvcVO vo);

	public void updateDisAddSvc(DisAddSvcVO vo);

	public void insertDisAddSvc(DisAddSvcVO vo);

	public List<?> getRateComboList();
	
	public List<?> getRateSpecList(RateMgmtVO searchVO);
	
	public void insertRateSpec(RateMgmtVO searchVO);

	public Integer getRateSpecCheck(RateMgmtVO searchVO);

	public void deleteRateSpec(RateMgmtVO searchVO);

	public void updateRateSpec(RateMgmtVO searchVO);
	
	public List<?> getRateSpecHist(RateMgmtVO searchVO);

	public List<?> getPlcyComboList(RateMgmtVO searchVO);
	
	public List<?> getRateCancelSpecList(RateMgmtVO searchVO);
	
	public void insertRateCancelSpec(RateMgmtVO searchVO);

	public void deleteRateCancelSpec(RateMgmtVO searchVO);

	public void updateRateCancelSpec(RateMgmtVO searchVO);
	
	public List<?> getRateCancelSpecHist(RateMgmtVO searchVO);
	
	public List<RateMgmtVO> getRateMgmtListExcel(RateMgmtVO searchVO);
	
	public List<?> getAllRateList(RateMgmtVO searchVO);
	
	public List<?> getRateMgmtRelList(RateMgmtVO searchVO);
	/*20190102 서비스관계관리 등록팝업 부가서비스조회*/
	public List<?> getRateMgmtRelListPop(RateMgmtVO searchVO);
	
	public List<?> getRateRelListAll(RateMgmtVO searchVO);
	
	public void insertRateRel(RateMgmtVO searchVO);
	
	public void deleteRateRel(RateMgmtVO searchVO);
	
	public void insertRateRelHist(RateMgmtVO searchVO);
	
	public List<?> getRateGroupList(RateMgmtVO searchVO);
	
	public void insertRateGroupInfo(RateMgmtVO searchVO);
	
	public void updateRateGroupInfo(RateMgmtVO searchVO);
	
	public void deleteRateGroupInfo(RateMgmtVO searchVO);
	
	public void deleteRateGroupInnInfo(RateMgmtVO searchVO);
	
	public void deleteRateGroupRelInnInfo(RateMgmtVO searchVO);
	
	public List<?> getGroupMappingRateList(RateMgmtVO searchVO);
	
	public void insertGroupMappingRateInfo(RateMgmtVO searchVO);
	
	public void deleteGroupMappingRateInfo(RateMgmtVO searchVO);
	
	public List<?> getGroupRelRateList(RateMgmtVO searchVO);
	
	public List<?> getGroupByRateReList(RateMgmtVO searchVO);
	
	public void insertGroupRelRateInfo(RateMgmtVO searchVO);
	
	public void deleteGroupRelRateInfo(RateMgmtVO searchVO);
	
	public String selectFreeCallCnt(RateMgmtVO searchVO);
	
	public String selectNwInCallCnt(RateMgmtVO searchVO);
	
	public String selectFreeSmslCnt(RateMgmtVO searchVO);
	
	public String selectFreeDataCnt(RateMgmtVO searchVO);
	/**
	 * 결합요금제 매핑 정보 조회
	 * */
	public List<?> getRateCombMappList(RateMgmtVO searchVO);
	/**
	 * 결합요금제 매핑 정보 엑셀조회
	 * */
	public List<RateMgmtVO> getRateCombMappListExcel(RateMgmtVO searchVO);
	
	public List<?> getRatePListAll(RateMgmtVO searchVO);
	
	public List<?> getRateRListAll(RateMgmtVO searchVO);
	
	public Integer getCombRateCount(RateMgmtVO searchVO);
	
	public void insertRateCombMapp(RateMgmtVO searchVO);
	
	public void updateRateCombMapp(RateMgmtVO searchVO);

	public List<?> getRateAcenMgmtList(RateMgmtVO searchVO);

	public Integer getRateAcenCdChk(RateMgmtVO searchVO);

	public void insertRateAcenCd(RateMgmtVO searchVO);
	public void updateRateAcenCd(RateMgmtVO searchVO);

	public List<RateMgmtVO> getRateAcenMgmtListExcel(RateMgmtVO searchVO);

	public List<?> getRateAcenHist(RateMgmtVO searchVO);

	public List<RateMgmtVO> getRateAcenHistExcel(RateMgmtVO searchVO);
}
