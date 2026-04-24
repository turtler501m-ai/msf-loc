package com.ktis.msp.batch.job.cls.prchmgmt.serivce;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.ClsAcntConfig;
import com.ktis.msp.batch.job.cls.clsacntclosemgmt.mapper.ClsAcntCloseMgmtMapper;
import com.ktis.msp.batch.job.cls.prchmgmt.mapper.PrchMgmtMapper;

@Service
public class InstBondPrchService extends BaseService {
	
	@Autowired
	private PrchMgmtMapper prchMgmtMapper;
	
	@Autowired
	private ClsAcntCloseMgmtMapper clsAcntCloseMgmtMapper;
	
	/**
	 * 할부채권매입정보생성
	 * @param param 
	 * @param param
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setInstBondPrchReg(Map<String, Object> param) throws MvnoServiceException {
		
		LOGGER.info("할부채권매입정보생성 param={}", param);
		
		// 매출확정 마감상태 조회
		Map<String, Object> inputMap = new HashMap<String, Object>();

		// 2015-11-11, 결산마감여부 무관하게 매입 진행 처리
//		inputMap.put("CLS_ACNT_YM",			param.get("WORK_YM"));
//		inputMap.put("CLS_ACNT_DUTY_CD",	config.CLS_TSK_SALE_CNFM_CD);	// 매출확정 A010
//		inputMap.put("SRL_NUM",				"1");
//		
//		String closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
//		
//		// 매출확정 완료가 아니면 리턴처리
//		if(closeStatCd == null || !closeStatCd.equals(config.CLOSE_STAT_CMP)){
//			
//			param.put("READ_CNT", readCnt);
//			param.put("PROC_CNT", procCnt);
//			param.put("PARAM", "매출확정(A010) 완료상태가 아닙니다.");
//			param.put("PROC_STAT_CD", config.SCHD_PROC_STAT_FAIL);
//			
//			return param;
//		}
//		
//		// 할부채권매입대상생성 마감상태 체크
//		inputMap.put("CLS_ACNT_YM",			param.get("WORK_YM"));
//		inputMap.put("CLS_ACNT_DUTY_CD",	config.CLS_TSK_INST_BOND_PRCH_TRGT_CD);	// 할부채권매입대상
//		inputMap.put("SRL_NUM",				"1");
//		
//		closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
//		
//		if(closeStatCd != null && closeStatCd.equals(config.CLOSE_STAT_CMP)){
//			
//			param.put("READ_CNT", readCnt);
//			param.put("PROC_CNT", procCnt);
//			param.put("PARAM", "할부채권매입대상생성(S032)이 이미 완료상태입니다.");
//			param.put("PROC_STAT_CD", config.SCHD_PROC_STAT_FAIL);
//			
//			return param;
//		}
//				
//		// 할부채권매입 마감상태 체크
//		inputMap.put("CLS_ACNT_YM",			param.get("WORK_YM"));
//		inputMap.put("CLS_ACNT_DUTY_CD",	config.CLS_TSK_INST_BOND_PRCH_CD);	// 할부채권매입
//		inputMap.put("SRL_NUM",				"1");
//		
//		closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
//		
//		if(closeStatCd == null || !closeStatCd.equals(config.CLOSE_STAT_ING)){
//			
//			param.put("READ_CNT", readCnt);
//			param.put("PROC_CNT", procCnt);
//			param.put("PARAM", "할부채권매입(C020) 진행상태가 아닙니다.");
//			param.put("PROC_STAT_CD", config.SCHD_PROC_STAT_FAIL);
//			
//			return param;
//		}
		
		try {
		
			// 할부채권(개통) 매입대상 생성
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
//			inputMap.put("SCTN_CD",     config.INST_BOND_PRCH_OPEN);		//개통
			inputMap.put("USER_ID",     ClsAcntConfig.USER_ID);
			
			LOGGER.info("할부채권매입상세(개통) 생성 param={}", inputMap);
			prchMgmtMapper.insertInstBondPrchDtlBatch(inputMap);
			
			// 2014-12-20, 할부채권(해지) 매입대상 생성
			LOGGER.info("할부채권매입상세(해지) 생성 param={}", inputMap);
			prchMgmtMapper.insertInstBondPrchDtlTmntBatch(inputMap);
			
			// 할부채권매입정보 생성
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
			inputMap.put("USER_ID",     ClsAcntConfig.USER_ID);
			
			LOGGER.info("할부채권매입 생성 param={}", inputMap);
			prchMgmtMapper.insertInstBondPrchInfo(inputMap);
			
			
			// 할부채권매입대상생성 마감처리
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM",			param.get("WORK_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD",	ClsAcntConfig.CLS_TSK_INST_BOND_PRCH_TRGT_CD);	// 할부채권매입대상생성 S032
			inputMap.put("SRL_NUM",				"1");
			inputMap.put("CLOSE_STAT_CD",		ClsAcntConfig.CLOSE_STAT_CMP);		// 완료
			inputMap.put("USER_ID", 			ClsAcntConfig.USER_ID);
			
			LOGGER.info("할부채권매입 마감생성 param={}", inputMap);
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
			
			// 할부채권매입 완료상태로 변경
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM",			param.get("WORK_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD",	ClsAcntConfig.CLS_TSK_INST_BOND_PRCH_CD);
			inputMap.put("SRL_NUM",				"1");
			inputMap.put("CLOSE_STAT_CD",		ClsAcntConfig.CLOSE_STAT_CMP);		// 완료
			inputMap.put("USER_ID", 			ClsAcntConfig.USER_ID);
			
			LOGGER.info("할부채권매입 마감생성 param={}", inputMap);
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1032", e);
		}
	}
	
}
