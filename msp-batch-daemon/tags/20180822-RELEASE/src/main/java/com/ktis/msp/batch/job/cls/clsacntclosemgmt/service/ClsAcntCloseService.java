package com.ktis.msp.batch.job.cls.clsacntclosemgmt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.ClsAcntConfig;
import com.ktis.msp.batch.job.cls.clsacntclosemgmt.mapper.ClsAcntCloseMgmtMapper;

@Service
public class ClsAcntCloseService extends BaseService {
	
	@Autowired
	private ClsAcntCloseMgmtMapper clsAcntCloseMgmtMapper;
	
	/**
	 * 결산대상생성
	 * - 매출확정(A010) 상태를 체크하여 결산대상을 생성
	 * @param param 
	 * @param param
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setClsAcntTrgtReg(Map<String, Object> param) throws MvnoServiceException {
		
//		int flag = -1;
		
		Map<String, Object> inputMap = new HashMap<String, Object>();

		// 매출확정여부 체크
//		inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
//		inputMap.put("CLS_ACNT_DUTY_CD", config.CLS_TSK_SALE_CNFM_CD);	// 매출확정 A010
//		inputMap.put("SRL_NUM", "1");
//		
//		String closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
//		
//		// A010 마감이 아니면 리턴
//		if(closeStatCd == null || !closeStatCd.equals(config.CLOSE_STAT_CMP)){
//			param.put("PARAM", "매출확정 마감상태가 아닙니다.");
//			param.put("PROC_STAT_CD", config.SCHD_PROC_STAT_FAIL);
//			
//			return param;
//		}
		
		try {
			
			LOGGER.info("결산대상생성 param={}", param);
			
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", ClsAcntConfig.TMP_WORK_YM);
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_DPST_STOF_CD);
			inputMap.put("SRL_NUM", "1");
			inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_STP);
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.info("입금상계 결산마감 정지 상태로 변경 inputMap={}", inputMap);
			
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
			// 결산대상 생성을 위한 매출정보 변경
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.info("결산대상 생성 inputMap={}", inputMap);
			
			// 매출정보 결산대상처리여부 변경
			clsAcntCloseMgmtMapper.updateSaleInfoClsAcntProcYn(inputMap);
			
//			// (-)영업매출 상계처리
//			inputMap.clear();
//			inputMap.put("CLS_ACNT_YM",		param.get("WORK_YM"));
//			inputMap.put("SALE_TYPE_CD",	config.SALE_TYPE_SALE_CD);	// 영업매출
//		    
//			List<Map<String, Object>> list = clsAcntCloseMgmtMapper.getSaleTypeSaleInfoList(inputMap);
//			
//			LOGGER.debug("list.size()=" + list.size());
//			readCnt = list.size();
//			
//			for(Map<String, Object> rtMap : list){
//				LOGGER.error("(-)영업매출 상계처리 rtMap=" + rtMap);
//				
//				rtMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
//				
//				// 상계처리대상조회
//				List<Map<String, Object>> alist = clsAcntCloseMgmtMapper.getStofTrgtSaleInfoList(rtMap);
//				
//				LOGGER.error("alist="  + alist.size());
//				
//				for(Map<String, Object> aMap : alist){
//					
//					LOGGER.error("aMap=" + aMap);
//					
//					// 상계금액 계산
//					long lStofAmt = 0;
//					if(Long.parseLong(rtMap.get("PROC_TRGT_AMT").toString()) * flag > Long.parseLong(aMap.get("PROC_TRGT_AMT").toString())){
//						lStofAmt = Long.parseLong(aMap.get("PROC_TRGT_AMT").toString());
//					}else{
//						lStofAmt = Long.parseLong(rtMap.get("PROC_TRGT_AMT").toString()) * flag;
//					}
//					
//					// 상계처리 호출
//					inputMap.clear();
//					inputMap.put("EXE_CD",					"B");	// 실행모드
//					inputMap.put("USER_ID",					config.USER_ID);
//					inputMap.put("PROC_CL",					"10");	// 처리구분
//					inputMap.put("ORGN_ID",					rtMap.get("ORGN_ID"));
//					inputMap.put("STOF_AMT",				lStofAmt * flag);
//					inputMap.put("STOF_OCCR_SCTN_CD",		"44");	// 상계발생구분코드: (-)매출상계
//					inputMap.put("DPST_PROC_DT",			"");
//					inputMap.put("DPST_SRL_NUM",			"");
//					inputMap.put("PREPAY_AMT_OCCR_DT",		"");
//					inputMap.put("PREPAY_AMT_SRL_NUM",		"");
//					inputMap.put("CLS_ACNT_STDR_YM",		param.get("WORK_YM"));
//					inputMap.put("CLS_ACNT_YM",				"");
//					inputMap.put("CLS_ACNT_ITM_CD",			rtMap.get("CLS_ACNT_ITM_CD"));
//					inputMap.put("CLS_ACNT_STRD_CD",		rtMap.get("CLS_ACNT_STDR_CD"));
//					inputMap.put("PRCH_YM",					"");
//					inputMap.put("SALE_DT",					rtMap.get("SALE_DT"));
//					inputMap.put("SALE_ITM_CD",				rtMap.get("SALE_ITM_CD"));
//					inputMap.put("SALE_SRL_NUM",			rtMap.get("SALE_SRL_NUM"));
//					inputMap.put("STOF_PROC_STDR_CD",		rtMap.get("CLS_ACNT_STDR_CD"));
//					inputMap.put("STOF_CLS_ACNT_YM",		"");
//					inputMap.put("STOF_CLS_ACNT_ITM_CD",	aMap.get("CLS_ACNT_ITM_CD"));
//					inputMap.put("STOF_CLS_ACNT_STDR_CD",	aMap.get("CLS_ACNT_STDR_CD"));
//					inputMap.put("STOF_PRCH_YM",			"");
//					inputMap.put("STOF_SALE_YM",			param.get("SALE_YM"));
//					inputMap.put("STOF_SALE_DT",			aMap.get("SALE_DT"));
//					inputMap.put("STOF_SALE_ITM_CD",		aMap.get("SALE_ITM_CD"));
//					inputMap.put("STOF_SALE_SRL_NUM",		aMap.get("SALE_SRL_NUM"));
//					inputMap.put("INVC_ID",					"");
//					inputMap.put("STOF_CNCL_OCCR_DT",		"");
//					inputMap.put("STOF_CNCL_SRL_NUM",		"");
//					inputMap.put("STOF_RSN",				"");
//					
//					LOGGER.error("상계처리 inputMap=" + inputMap);
//					
//					// 상계처리 호출
//					stofProcService.setStofProc(inputMap);
//				}
//				
//				procCnt++;
//			}
			
			// 결산상세대상 생성
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM",	param.get("WORK_YM"));
			inputMap.put("USER_ID",		ClsAcntConfig.USER_ID);
			
			LOGGER.info("결산상세대상 생성 inputMap={}", inputMap);
			
			clsAcntCloseMgmtMapper.insertClsAcntDltList(inputMap);
			
			// 결산정보 생성
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM",	param.get("WORK_YM"));
			inputMap.put("USER_ID",		ClsAcntConfig.USER_ID);
			
			LOGGER.info("결산정보 inputMap={}", inputMap);
			
			clsAcntCloseMgmtMapper.insertClsAcntInfoBatch(inputMap);
			
//			// 가결산상계정보 생성
//			LOGGER.error("가결산상계정보 inputMap=" + inputMap);
//			
//			clsAcntCloseMgmtMapper.insertTmpClsAcntStofInfo(inputMap);
			
			// 결산대상생성 마감
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM",			param.get("WORK_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD",	ClsAcntConfig.CLS_TSK_CLS_ACNT_TRGT_CD);
			inputMap.put("SRL_NUM",				"1");
			inputMap.put("CLOSE_STAT_CD",		ClsAcntConfig.CLOSE_STAT_CMP);
			inputMap.put("USER_ID",				ClsAcntConfig.USER_ID);
			
			LOGGER.info("결산대상생성 마감 inputMap={}", inputMap);
			
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM",			ClsAcntConfig.TMP_WORK_YM);
			inputMap.put("CLS_ACNT_DUTY_CD",	ClsAcntConfig.CLS_TSK_DPST_STOF_CD);
			inputMap.put("SRL_NUM",				"1");
			inputMap.put("CLOSE_STAT_CD",		ClsAcntConfig.CLOSE_STAT_ING);
			inputMap.put("USER_ID",				ClsAcntConfig.USER_ID);
			
			LOGGER.info("입금상계 결산마감 진행 상태로 변경 inputMap={}", inputMap);
			
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1001", e);
		}
		
	}
	
	
	
	/**
	 * 매출이월생성
	 * @param param
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setSaleCarryOver(Map<String, Object> param) throws MvnoServiceException {
		LOGGER.info("매출이월생성 param={}", param);
		
		try {
			boolean okCd = true;
			
			// 당월 조회
			param.put("SALE_YM", clsAcntCloseMgmtMapper.getSaleYm(param));
			
			// 업무처리용 map 선언
			Map<String, Object> inputMap = new HashMap<String, Object>();
			
			// 매출이월 결산마감상태 조회
			inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_SALE_CARRY_OVER_CD);	// 매출이월(S013)
			inputMap.put("SRL_NUM", "1");
			
			LOGGER.info("매출이월 결산마감상태 조회 inputMap={}", inputMap);
			String closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
			
			// 매출이월 진행상태가 아니면 return
			if(closeStatCd != null && !closeStatCd.equals(ClsAcntConfig.CLOSE_STAT_ING)) {
				throw new MvnoServiceException("ECLS1035");
			}
			
			// 미반영수불정보 존재여부 체크
			inputMap.clear();
			inputMap.put("WORK_YM", param.get("WORK_YM"));
			
			if(clsAcntCloseMgmtMapper.getSaleRecvnpayInfoCheck(inputMap) > 0) {
				throw new MvnoServiceException("ECLS1036");
			}
			
			// 매출이월대상의 결산마감정보 업무상태를 정지로 변경
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_SALE_CARRY_OVER_CD);	// 매출이월
			inputMap.put("SRL_NUM", "1");
			inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_STP);	// 정지
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.info("매출이월 정지 상태변경 inputMap={}", inputMap);
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
			// 입금상계 결산마감정보 업무상태 정지로 변경
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", ClsAcntConfig.TMP_WORK_YM);
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_DPST_STOF_CD);	// 입금상계(S020)
			inputMap.put("SRL_NUM", "1");
			inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_STP);	// 정지
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.info("입금상계 정지 변경 inputMap={}", inputMap);
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
			// 매출수불정보 등록
			inputMap.clear();
			inputMap.put("WORK_YM", param.get("WORK_YM"));
			inputMap.put("SALE_YM", param.get("SALE_YM"));
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.info("매출수불정보 등록 inputMap={}", inputMap);
			
			try {
				clsAcntCloseMgmtMapper.insertCarryOverSaleRecvnpayInfo(inputMap);
				
				// 매출수불정보 등록 성공시 매출이월 완료 상태로 변경 
				inputMap.clear();
				inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
				inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_SALE_CARRY_OVER_CD);	// 매출이월
				inputMap.put("SRL_NUM", "1");
				inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_CMP);	// 완료
				inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
				
				LOGGER.info("수불정보 등록성공, 매출이월(workYm) 완료 변경 inputMap= {}", inputMap);
				clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			} catch(Exception e) {
				// 매출수불정보 등록 실패시 매출이월 진행 상태로 변경 
				inputMap.clear();
				inputMap.put("CLS_ACNT_YM", param.get("WORK_YM"));
				inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_SALE_CARRY_OVER_CD);	// 매출이월
				inputMap.put("SRL_NUM", "1");
				inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_ING);	// 진행
				inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
				
				LOGGER.info("수불정보 등록실패, 매출이월(workYm) 진행 변경 inputMap={}", inputMap);
				clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
				
				okCd = false;
			}
			
			// 입금상계 결산마감정보 업무상태를 진행으로
			inputMap.clear();
			inputMap.put("CLS_ACNT_YM", ClsAcntConfig.TMP_WORK_YM);
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_DPST_STOF_CD);	// 입금상계
			inputMap.put("SRL_NUM", "1");
			inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_ING);	// 진행
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.info("입금상계 진행 변경 inputMap = {}", inputMap);
			clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			
			if(okCd) {
				inputMap.clear();
				inputMap.put("CLS_ACNT_YM", param.get("SALE_YM"));
				inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_SALE_CARRY_OVER_CD);	// 매출이월
				inputMap.put("SRL_NUM", "1");
				inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_ING);	// 진행
				inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
				LOGGER.info("매출이월(saleYm) 진행 변경 inputMap={}", inputMap);
				clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
			}
			
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1037", e);
		}
	}
	
}
