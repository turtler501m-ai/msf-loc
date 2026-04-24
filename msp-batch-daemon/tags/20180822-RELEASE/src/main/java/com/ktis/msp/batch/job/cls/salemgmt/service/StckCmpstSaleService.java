package com.ktis.msp.batch.job.cls.salemgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.ClsAcntConfig;
import com.ktis.msp.batch.job.cls.clsacntclosemgmt.mapper.ClsAcntCloseMgmtMapper;
import com.ktis.msp.batch.job.cls.salemgmt.mapper.SaleMgmtMapper;

@Service
public class StckCmpstSaleService extends BaseService {
	
	@Autowired
	private ClsAcntCloseMgmtMapper clsAcntCloseMgmtMapper;
	
	@Autowired
	private SaleMgmtMapper saleMgmtMapper;
	
	@Autowired
	private SaleProcService saleProcService;
	
	/**
	 * 출고가 변동에 의한 매출생성
	 * @param param
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setStockCompensation(Map<String, Object> param) throws MvnoServiceException{
		LOGGER.info("재고보상매출생성 param={}", param);
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		int procCnt = 0;
		
		try {
			
			// 매출이월 진행상태 체크
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("SALE_YM"));		// 당월 매출이월 진행상태 조회
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_SALE_CARRY_OVER_CD);	// 매출이월 S013
			inputMap.put("SRL_NUM", "1");
			
			LOGGER.info("매출이월여부 조회 param={}", inputMap);
			String closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
			
			if(closeStatCd == null || !closeStatCd.equals(ClsAcntConfig.CLOSE_STAT_ING)){
				throw new MvnoServiceException("ECLS1039");
			}
			
			// 재고보상 대상모델, 대리점 추출
			inputMap.clear();
			inputMap.put("PROC_DT", param.get("SALE_DT"));
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			// 재고보상 근거데이터 생성
			saleMgmtMapper.setStockCompensationList(inputMap);
			
			List<Map<String, Object>> list = saleMgmtMapper.getStockCompensationList(inputMap);
			
			for(Map<String, Object> rtMap : list) {
				rtMap.put("SALE_ITM_CD", ClsAcntConfig.SALE_ITM_STCK_CMPS);
				
				saleProcService.saveSaleInfoSchd(rtMap);
				
				procCnt++;
			}
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1040", e);
		} 
		
		return procCnt;
	}

}
