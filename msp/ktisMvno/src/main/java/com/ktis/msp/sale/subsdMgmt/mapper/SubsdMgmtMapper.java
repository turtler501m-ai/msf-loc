package com.ktis.msp.sale.subsdMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.sale.subsdMgmt.vo.SubsdMgmtByExcelVO;
import com.ktis.msp.sale.subsdMgmt.vo.SubsdMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("subsdMgmtMapper")
public interface SubsdMgmtMapper {
	
	public List<?> getSubsdMgmtMainList(SubsdMgmtVO searchVO);
	
	public List<?> getSubsdMgmtMainListExcel(SubsdMgmtVO searchVO);
	
	public List<?> getSubsdMgmtDtlList(SubsdMgmtVO searchVO);
	
	public List<?> getSubsdMgmtDtlListExcel(SubsdMgmtVO searchVO);
	
	public List<?> getPrdtComboList();
	
	public List<?> getCommonGridList(Map<String, Object> param);
	
	public List<?> getRateGrpRateCdList(SubsdMgmtVO searchVO);
	
	public EgovMap getSubsbEndDt(SubsdMgmtVO searchVO);
	
	public void updateOfclSubsdEndDt(EgovMap map);
	
	public void insertOfclSubsdMst(SubsdMgmtVO searchVO);
	
	public void updateSubsdAmtInfo(SubsdMgmtVO searchVO);
	
	public List<?> getCopySubsdAmtInfo(SubsdMgmtVO searchVO);
	
	public List<?> getCmnGrpCdListByEtc5(Map<String, Object> param);
	
	int regMspOfclSubsdDataUpload(SubsdMgmtByExcelVO subsdMgmtByExcelVO);
	
	int callOfclSubsdMst(SubsdMgmtByExcelVO subsdMgmtByExcelVO);

	String getPrdtIndCd(String prdtId);
}
