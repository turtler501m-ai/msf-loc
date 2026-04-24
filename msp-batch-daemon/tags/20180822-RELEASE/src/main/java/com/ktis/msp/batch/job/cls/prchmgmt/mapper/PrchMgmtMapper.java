package com.ktis.msp.batch.job.cls.prchmgmt.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("prchMgmtMapper")
public interface PrchMgmtMapper {
	
	/**
	 * 수수료매입등록(SELECT~INSERT)
	 * @param vo
	 * @throws Exception
	 */
	public int insertCmsnPrchInfo(Map<String, Object> param);
	
	/**
	 * 할부채권매입상세등록
	 * @param param
	 * @throws Exception
	 */
	public void insertInstBondPrchDtlBatch(Map<String, Object> param);
	
	/**
	 * 할부채권매입등록
	 * @param param
	 * @throws Exception
	 */
	public void insertInstBondPrchInfo(Map<String, Object> param);
	
	/**
	 * 수수료매입정보삭제(수수료재처리시)
	 * @param map
	 */
	public void deleteCmsnPrchInfo(Map<String, Object> param);
	
	/**
	 * 할부채권(해지)매입내역 생성
	 * @param param
	 */
	public void insertInstBondPrchDtlTmntBatch(Map<String, Object> param);
}
