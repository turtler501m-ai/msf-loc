package com.ktis.msp.batch.job.rsk.statmgmt.mapper;

import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rsk.statmgmt.vo.StatMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("statMgmtMapper")
public interface StatMgmtMapper {
	
	public int getInvPymPrdCheck(StatMgmtVO vo);
	
	public int getInvPymDataCheck(StatMgmtVO vo);
	
	public void callInvPymData(StatMgmtVO vo);
	
	void getInvPymDataListExcel(StatMgmtVO vo, ResultHandler handler);
	
	public void insertMsgQueue(String userId);
	
	/**
	 * 사용량집계
	 * @param param
	 * @return
	 */
	public int insertUsgAmntSttc(Map<String, Object> param);
	
	/**
	 * 청구수납집계
	 * @param param
	 * @return
	 */
	public int insertInvAmntSttc(Map<String, Object> param);
	
	/**
	 * 마감시간별 영업실적
	 * @return
	 */
	public void callInserSaleRsltSttc(Map<String, Object> param);
	
	/**
	 * 계약별요금제사용일수집계
	 * @return
	 */
	public void callInserCntrUsgDtSttc(Map<String, Object> param);
	
	/**
	 * 일마감집계
	 * @return
	 */
	public void callInserDailyCloseSttc(Map<String, Object> param);
	
	/**
	 * 청구수납자료생성
	 * @return
	 */
	public void callAcntDataSttc();
	
	
}
