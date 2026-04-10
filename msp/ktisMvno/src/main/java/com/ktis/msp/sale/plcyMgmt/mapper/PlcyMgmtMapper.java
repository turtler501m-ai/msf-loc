package com.ktis.msp.sale.plcyMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.login.vo.MsgQueueVO;
import com.ktis.msp.sale.plcyMgmt.vo.PlcyMgmtVO;
import com.ktis.msp.sale.plcyMgmt.vo.PolicyLogVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("plcyMgmtMapper")
public interface PlcyMgmtMapper {
	
	public List<?> getPlcyMgmtList(PlcyMgmtVO searchVO);
	
	public List<?> getPlcyMgmtListExcel(PlcyMgmtVO searchVO);
	
	public List<?> getAgncyTrgtList(PlcyMgmtVO searchVO);
	
	public List<?> getRateTrgtList(PlcyMgmtVO searchVO);
	
	public List<?> getPrdtTrgtList(PlcyMgmtVO searchVO);
	
	public List<?> getRateArpuTrgtList(PlcyMgmtVO searchVO);
	
	public String getSalePlcyCd(PlcyMgmtVO searchVO);
	
	public Map<String, Object> getPlcySubsdAmtList(Map<String, Object> map);
	
	public void insertSalePlcyMst(PlcyMgmtVO searchVO);
	
	public void deleteSalePlcyMst(String salePlcyCd);
	
	public int getSalePlcyApplChk(PlcyMgmtVO searchVO);
	
	public void deleteSaleRateMst(String salePlcyCd);
	
	public void insertSaleRateMst(PlcyMgmtVO searchVO);
	
	public void deleteSalePrdtMst(String salePlcyCd);
	
	public void insertSalePrdtMst(PlcyMgmtVO searchVO);
	
	public void deleteSaleAgrmMst(String salePlcyCd);
	
	public void insertSaleAgrmMst(PlcyMgmtVO searchVO);
	
	public void deleteSaleOperMst(String salePlcyCd);
	
	public void insertSaleOperMst(PlcyMgmtVO searchVO);
	
	public void deleteSaleOrgnMst(String salePlcyCd);
	
	public void insertSaleOrgnMst(PlcyMgmtVO searchVO);
	
	public void deleteSaleArpuMst(String salePlcyCd);
	
	public void insertSaleArpuMst(PlcyMgmtVO searchVO);
	
	public List<?> getRateGrpRateCdList(PlcyMgmtVO searchVO);
	
	public void deleteSaleSubsdMst(String salePlcyCd);
	
	public void insertSaleSubsdMst(PlcyMgmtVO searchVO);
	
	public List<?> getPlcySubsdList(PlcyMgmtVO searchVO);
	
	public List<?> getPlcySubsdListPart(PlcyMgmtVO searchVO);
	
	public List<?> getSaleOrgnList(PlcyMgmtVO searchVO);
	
	public List<?> getSaleRateList(PlcyMgmtVO searchVO);
	
	public List<?> getSaleOperList(PlcyMgmtVO searchVO);
	
	public List<?> getSaleAgrmList(PlcyMgmtVO searchVO);
	
	public List<?> getSalePrdtList(PlcyMgmtVO searchVO);
	
	public List<?> getSaleArpuList(PlcyMgmtVO searchVO);
	
	public void setPlcyConfirm(PlcyMgmtVO searchVO);
	
	public void setPlcyCancel(PlcyMgmtVO searchVO);
	
	public List<?> getAgncySubsdList(PlcyMgmtVO searchVO);

	public List<?> getAgncySubsdListExcel(PlcyMgmtVO searchVO);

	public void updateAgncySubsdAmt(PlcyMgmtVO searchVO);
	
	public void updatePlcyMgmtClose(PlcyMgmtVO searchVO);
	
	public List<?> getPlcyAgncyAddList(PlcyMgmtVO searchVO);
	
	/**
	 * @Description : 선불대리점 추가
	 * @Param  : PlcyMgmtVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 29.
	 */
	public List<?> getPlcyAgncyAddList2(PlcyMgmtVO searchVO);
	
	public void insertSalePlcyMstCopy(Map param);
	
	public void insertSaleOrgnMstCopy(Map param);
	
	public void insertSaleOperMstCopy(Map param);
	
	public void insertSaleAgrmMstCopy(Map param);
	
	public void insertSaleRateMstCopy(Map param);
	
	public void insertSalePrdtMstCopy(Map param);
	
	public void insertSaleArpuMstCopy(Map param);
	
	public void insertInstNomCopy(Map param);
	
	public void deleteInstNom(String salePlcyCd);
	
	public void insertInstNom(PlcyMgmtVO searchVO);
	
	public List<?> getInstList(PlcyMgmtVO searchVO);
	
	// 요금제추가
	public List<?> getPlcyRateAddList(PlcyMgmtVO searchVO);
	
	public String getMblphnNum(String userId);
	
	// 정책 상세 생성/초기화/삭제 프로시져 호출
	void callPlcyDetail(PlcyMgmtVO paramVO);
	
	// 정책 이벤트 로그 확인
	PolicyLogVO checkSaleEventLog(PolicyLogVO paramVO);
	
	// 정책 이벤트 로그 insert
	int insertSaleEventLog(PolicyLogVO paramVO);
	
	// 정책 이벤트 로그 update 비고
	int updateSaleEventLog(PolicyLogVO paramVO);
	
	// 정책 이벤트 로그 update 종료일시
	int updateEndSaleEventLog(PolicyLogVO paramVO);
	
	// 정책 확정 확인
	String checkPolicyConfirm(String salePlcyCd);

	
	// 할부 개월 수 팝업 SELECT
	List<?> selectInstNomPuList(PlcyMgmtVO plcyMgmtVO);

	// 할부 개월 수 삭제(할부기간 팝업)
    int deleteInstNomPu(PlcyMgmtVO plcyMgmtVO);
	
    // 할부 개월 수 등록(할부기간 팝업)
    int insertInstNomPu(PlcyMgmtVO plcyMgmtVO);
	
}
