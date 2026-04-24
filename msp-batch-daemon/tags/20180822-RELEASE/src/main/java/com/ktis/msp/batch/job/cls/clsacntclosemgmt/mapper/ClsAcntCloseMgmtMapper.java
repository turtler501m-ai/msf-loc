package com.ktis.msp.batch.job.cls.clsacntclosemgmt.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("clsAcntCloseMgmtMapper")
public interface ClsAcntCloseMgmtMapper {
	
	/**
	 * 결산마감상태조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String getClsAcntCloseStat(Map<String, Object> param);
	
	/**
	 * 결산마감정보merge
	 * @param param
	 * @throws Exception
	 */
	public void saveClsAcntCloseMerge(Map<String, Object> param);
	
	/**
	 * 매출수불미반영정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int getSaleRecvnpayInfoCheck(Map<String, Object> param);
	
	/**
	 * 매출이월에 의한 매출수불정보 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertCarryOverSaleRecvnpayInfo(Map<String, Object> param);
	
	/**
	 * 매출정보 결산대상처리여부 변경
	 * @param param
	 * @throws Exception
	 */
	public void updateSaleInfoClsAcntProcYn(Map<String, Object> param);
	
	/**
	 * 결산상세등록
	 * @param param
	 * @throws Exception
	 */
	public void insertClsAcntDltList(Map<String, Object> param);
	
	/**
	 * 결산정보등록
	 * @param param
	 * @throws Exception
	 */
	public void insertClsAcntInfoBatch(Map<String, Object> param);
	
	/**
	 * 매출년월조회
	 * @param param
	 * @return
	 */
	public String getSaleYm(Map<String, Object> param);
	
}
