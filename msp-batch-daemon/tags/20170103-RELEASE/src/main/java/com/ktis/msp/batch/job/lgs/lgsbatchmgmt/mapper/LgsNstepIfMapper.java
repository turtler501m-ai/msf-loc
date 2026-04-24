package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.vo.LgsNstepIfVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("lgsNstepIfMapper")
public interface LgsNstepIfMapper {
	
	int updLinkData(LgsNstepIfVo vo);
	LgsNstepIfVo getLinkDataDtlInfo(LgsNstepIfVo vo);
	List<?> getLinkNonProcData();
	
	int insChckDataInfo(LgsNstepIfVo vo);
	
	String getInvtSrl(String str);
	
	int insOpenHstInfo(LgsNstepIfVo vo);
	int updOpenHstInfo(LgsNstepIfVo vo);
	
}