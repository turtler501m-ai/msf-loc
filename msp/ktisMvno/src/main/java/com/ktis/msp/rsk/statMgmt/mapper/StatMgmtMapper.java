package com.ktis.msp.rsk.statMgmt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.rsk.statMgmt.vo.StatMgmt3gSubVO;
import com.ktis.msp.rsk.statMgmt.vo.StatMgmtUagAmntVO;
import com.ktis.msp.rsk.statMgmt.vo.StatMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("statMgmtMapper")
public interface StatMgmtMapper {
	
	public List<?> getBillItemMgmtList(StatMgmtVO vo);
	
	public List<?> getBillItemMgmtListExcel(StatMgmtVO vo);
	
	public void insertBillItmInfo(StatMgmtVO vo);
	
	public void updateBillItmInfo(StatMgmtVO vo);
	
	public int getBillItmChk(StatMgmtVO vo);
	
	public int getInvPymPrdCheck(StatMgmtVO vo);
	
	public List<?> getInvPymDataList(StatMgmtVO vo);
	
	public int getInvPymDataCheck(StatMgmtVO vo);
	
	public void callInvPymData(StatMgmtVO vo);
	
	void getInvPymDataListExcel(StatMgmtVO vo, ResultHandler handler);
	
	public String getMblphnNum(String userId);
	
	public List<StatMgmtUagAmntVO> getUsgAmntStatOrgList(StatMgmtVO vo);
	
	public List<StatMgmtUagAmntVO> getUsgAmntStatOrgListExcel(StatMgmtVO vo);
	
	//20180323 권오승 청구항목별자료조회 추가
	public List<?> getInvItemDataList(StatMgmtVO vo);
	//20180323 권오승 청구항목별자료엑셀조회 추가
	public List<StatMgmtVO> getInvItemDataListExcel(StatMgmtVO vo);
	
	//3G 유지가입자 현황 조회
	public List<?> getDaily3gSubList(StatMgmt3gSubVO vo);
}
