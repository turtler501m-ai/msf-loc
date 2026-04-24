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
public class DelayAddSaleService extends BaseService {
	
	@Autowired
	private ClsAcntCloseMgmtMapper clsAcntCloseMgmtMapper;
	
	@Autowired
	private SaleMgmtMapper saleMgmtMapper;
	
	@Autowired
	private SaleProcService saleProcService;
	
	/**
	 * 단말연체가산금생성
	 * @param param 
	 * @param param
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setDelayAddAmtReg(Map<String, Object> param) throws MvnoServiceException {
		
		int procCnt = 0;
		
		try {
			
			LOGGER.info("단말연체가산금생성 param={}", param);
			
			Map<String, Object> inputMap = new HashMap<String, Object>();
			
			// 입금마감 마감상태 조회
			inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_DPST_CLOSE_CD);	// 입금마감(B010)
			inputMap.put("SRL_NUM", "1");
			
			String closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
			
			if(closeStatCd != null && !closeStatCd.equals(ClsAcntConfig.CLOSE_STAT_CMP)) {
				throw new MvnoServiceException("ECLS1024");
			}
				
			// 매출이월 정보조회
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("SALE_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_SALE_CARRY_OVER_CD);
			inputMap.put("SRL_NUM", "1");
			
			closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
			
			if(closeStatCd != null && !closeStatCd.equals(ClsAcntConfig.CLOSE_STAT_ING)) {
				throw new MvnoServiceException("ECLS1025");
			}
			
			// 매출확정 마감상태 조회
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_SALE_CNFM_CD);	// A010
			inputMap.put("SRL_NUM", "1");
			
			closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
			
			if(closeStatCd != null && !closeStatCd.equals(ClsAcntConfig.CLOSE_STAT_CMP)) {
				throw new MvnoServiceException("ECLS1026");
			}
			
			// 연체가산금생성여부 조회
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("SALE_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_EQT_DLY_ADD_AMT_CD);	// S012
			inputMap.put("SRL_NUM", "1");
			
			closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
			
			if(closeStatCd != null && closeStatCd.equals(ClsAcntConfig.CLOSE_STAT_CMP)) {
				throw new MvnoServiceException("ECLS1027");
			}
			
			// 입금상계업무 마감상태 정지로 변경
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", ClsAcntConfig.TMP_WORK_YM);
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_DPST_STOF_CD);	//S020
			inputMap.put("SRL_NUM", "1");
			inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_CMP);
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.info("입금상계업무 마감상태 변경 param={}", inputMap);
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
			// 단말연체가산금 조회
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
			
			LOGGER.info("연체가산금 조회 param={}", inputMap);
			
			List<Map<String, Object>> list = saleMgmtMapper.getDlyAddAmtList(inputMap);
			
			for(Map<String, Object> rtMap : list) {
				rtMap.put("SALE_ITM_CD", ClsAcntConfig.SALE_ITM_EQT_DLY_AMT);
				
				saleProcService.saveSaleInfoSchd(rtMap);
				
				procCnt++;
			}
			
			// 연체가산금생성업무 마감상태 완료로 변경
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM",			param.get("WORK_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD",	ClsAcntConfig.CLS_TSK_EQT_DLY_ADD_AMT_CD);
			inputMap.put("SRL_NUM",				"1");
			inputMap.put("CLS_ACNT_CLOSE_DT",	param.get("SALE_DT"));
			inputMap.put("CLOSE_STAT_CD",		ClsAcntConfig.CLOSE_STAT_CMP);
			inputMap.put("USER_ID",				ClsAcntConfig.USER_ID);
			
			LOGGER.info("연체가산금생성업무 마감상태 변경 param={}", inputMap);
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
			// 입금상계업무 마감상태 진행으로 변경
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM",			ClsAcntConfig.TMP_WORK_YM);
			inputMap.put("CLS_ACNT_DUTY_CD",	ClsAcntConfig.CLS_TSK_DPST_STOF_CD);
			inputMap.put("SRL_NUM",				"1");
			inputMap.put("CLOSE_STAT_CD",		ClsAcntConfig.CLOSE_STAT_ING);
			inputMap.put("USER_ID",				ClsAcntConfig.USER_ID);
			
			LOGGER.info("입금상계업무 마감상태 변경 param={}", inputMap);
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
		} catch(MvnoServiceException e){
			throw e;
		} catch(Exception e){
			throw new MvnoServiceException("ECLS1031", e);
		}
		
		return procCnt;
	}

}
