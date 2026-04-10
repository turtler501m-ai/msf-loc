package com.ktis.msp.rcp.dlvyMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.dlvyMgmt.vo.ParcelServiceVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("parcelServiceMapper")
public interface ParcelServiceMapper {
	
	List<ParcelServiceVO> getParcelServiceLstInfo(ParcelServiceVO vo);
	
	List<ParcelServiceVO> getParcelServiceLstInfoByExcel(ParcelServiceVO vo);
	
	int updMcpParcelServiceCodeInfo(ParcelServiceVO vo);
	
	int updMspParcelServiceCodeInfo(ParcelServiceVO vo);
	
	int updMspRequest(ParcelServiceVO vo);
	
	String selectMsgCnt(String resNo);
	
	Map<String, Object> getResNo(ParcelServiceVO vo);
	
	int updMspRequestByParcelInfo(ParcelServiceVO vo);
	
	int updMcpRequestByParcelInfo(ParcelServiceVO vo);
	
	int updMspRequestStateByParcelInfo(ParcelServiceVO vo);
	
	int updMcpRequestStateByParcelInfo(ParcelServiceVO vo);
	
	String getTbCd(ParcelServiceVO vo);
	String getTbCdName(ParcelServiceVO vo);
	
}
