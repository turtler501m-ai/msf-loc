package com.ktis.msp.batch.job.cls.salemgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.job.cls.salemgmt.vo.SaleMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SaleMgmtMapper")
public interface SaleMgmtMapper {

	/**
	 * 매출정보 등록
	 * @param vo
	 * @throws Exception
	 */
	public void insertSaleInfo(SaleMgmtVO vo);
	
	/**
	 * 매출일련번호 조회
	 * @return
	 * @throws Exception
	 */
	public long getSaleSrlNum();
	
	/**
	 * 매출수불정보저장
	 * @param vo
	 * @throws Exception
	 */
	public void saveSaleRecvnpayInfo(SaleMgmtVO vo);
	
	/**
	 * 연체가산금조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDlyAddAmtList(Map<String, Object> param);
	
	/**
	 * 출고가 변동에 의한 데이터 생성
	 * @param param
	 */
	public void setStockCompensationList(Map<String, Object> param);
	
	/**
	 * 출고가 변동에 의한 매출생성 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getStockCompensationList(Map<String, Object> param);
}
