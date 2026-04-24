package com.ktis.msp.batch.job.org.orgmgmt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rsk.statmgmt.vo.StatMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("orgnChnCodeDataMapper")
public interface OrgnChnCodeDataMapper {
	
	/**
	 * 조직 채널상세 코드 현행화 프로시저 호출
	 * @return
	 */
	public void callOrgnChnCodeData(Map<String, Object> param);
	
	
	/**
	 * MDS_CMN_GRP_MST MDS공통코드 삭제(전일제외)
	 * @return
	 */
	public void deleteOrgnChnCodeData(Map<String, Object> param);
		
		
}
