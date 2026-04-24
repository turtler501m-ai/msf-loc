package com.ktis.msp.batch.job.cls.crdtloanmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.ClsAcntConfig;
import com.ktis.msp.batch.job.cls.crdtloanmgmt.mapper.CrdtLoanMgmtMapper;

@Service
public class CrdtLoanRvlService extends BaseService {
	
	@Autowired
	private CrdtLoanMgmtMapper crdtLoanMgmtMapper;
	
	@Autowired
	private CrdtLoanProcService crdtLoanProcService;
	
	/**
	 * 대리점여신초기화
	 * @param param 
	 * @param parameters
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setCrdtLoanInit(Map<String, Object> param) throws MvnoServiceException {
		
		int procCnt = 0;
		
		try {
			
			LOGGER.info("대리점여신초기화 param={}", param);
			
			// 여신초기화대상조회
			List<Map<String, Object>> list = crdtLoanMgmtMapper.getEndCrdtAgncyLoanDtlList(param);
			
			for(Map<String, Object> rtMap : list) {
				Map<String, Object> inputMap = new HashMap<String, Object>();
				
				inputMap.put("ORGN_ID", rtMap.get("ORGN_ID"));
				inputMap.put("CRDT_LOAN_TYPE_CD", rtMap.get("CRDT_LOAN_TYPE_CD"));
				inputMap.put("CRDT_LOAN_SCTN_CD", rtMap.get("CRDT_LOAN_SCTN_CD"));
				inputMap.put("CRDT_LOAN_STUP_AMT", rtMap.get("CRDT_LOAN_STUP_AMT"));
				inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
				
				LOGGER.info("대리점여신초기화 inputMap={}", inputMap);
				
				crdtLoanMgmtMapper.setCrdtLoanInit(inputMap);
				
				procCnt++;
			}
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1004", e);
		}
		
		return procCnt;
	}
	
	/**
	 * 여신부활서비스(개통익일)
	 * @param param 
	 * @param param
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setCrdtLoanRvl(Map<String, Object> param) throws MvnoServiceException {
		
		int procCnt = 0;
		
		LOGGER.debug("대리점 여신 처리 param={}", param);
		
		try {
			// 전일 개통분 + 14일이내 취소 여신부활대상조회
//			List<Map<String, Object>> list = crdtLoanMgmtMapper.getCrdtLoanRvlTrgtCntrList(param);
//			
//			for(Map<String, Object> rtMap : list) {
//				rtMap.put("CRDT_LOAN_APPL_YN", "Y");							/* 여신적용여부 */
//				rtMap.put("CRDT_LOAN_TSK",		rtMap.get("CRDT_LOAN_TSK"));	/* 여신업무 */
//				rtMap.put("TSK_TP",				rtMap.get("TSK_TP"));			/* 사용유형 */
//				rtMap.put("CRDT_LOAN_CL",		"2");							/* 당월 */
//				rtMap.put("ORGN_ID",			rtMap.get("ORGN_ID"));			/* 개통대리점 */
//				rtMap.put("CRDT_LOAN_AMT",		rtMap.get("CRDT_LOAN_AMT"));	/* 여신금액=출고단가+부가세 */
//				rtMap.put("CRDT_LOAN_DT",		rtMap.get("CRDT_LOAN_DT"));		/* 여신일자 */
//				rtMap.put("CUST_ID",			rtMap.get("CUST_ID"));			/* 고객ID */
//				rtMap.put("SRVC_CNTR_NUM",		rtMap.get("SRVC_CNTR_NUM"));	/* 서비스계약번호 */
//				rtMap.put("QNTY",				"1");							/* 수량 */
//				rtMap.put("OUT_AMT",			rtMap.get("OUT_AMT"));			/* 출고단가(MSP_REQUEST_SALEINFO.MODEL_PRICE) */
//				rtMap.put("INST_AMT",			rtMap.get("INST_AMT"));			/* 할부원금 */
//				rtMap.put("DPST_PROC_DT",		"");							/* 입금처리일자 */
//				rtMap.put("DPST_SRL_NUM",		"");							/* 입금일련번호 */
//				rtMap.put("USER_ID",			ClsAcntConfig.USER_ID);
//			
//				LOGGER.info("rtMap={}", rtMap);
//				
//				crdtLoanProcService.setCrdtLoanInfoProc(rtMap);
//				
//				procCnt++;
//			}
			
			// 개통부활조회 ( 개통단말정보 추가 )
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> inputMap = new HashMap<String, Object>();
			
			list = crdtLoanMgmtMapper.getOpenRvlList(param);
			
			LOGGER.debug("size=" + list.size());
			
			for(Map<String, Object> rtMap : list) {
				inputMap.clear();
				
				inputMap.put("CRDT_LOAN_APPL_YN",	"Y");							/* 여신적용여부 */
				inputMap.put("CRDT_LOAN_TSK",		"R");							/* 여신업무 */
				inputMap.put("TSK_TP",				"20");							/* 부활유형(20:개통, 21:유심, 22:기변) */
				inputMap.put("CRDT_LOAN_CL",		"2");							/* 당월 */
				inputMap.put("ORGN_ID",				rtMap.get("ORGN_ID"));			/* 개통대리점 */
				inputMap.put("CRDT_LOAN_AMT",		rtMap.get("CRDT_LOAN_AMT"));	/* 여신금액=출고단가+부가세 */
				inputMap.put("CRDT_LOAN_DT",		rtMap.get("CRDT_LOAN_DT"));		/* 여신일자 */
				inputMap.put("CUST_ID",				rtMap.get("CUSTOMER_ID"));		/* 고객ID */
				inputMap.put("SRVC_CNTR_NUM",		rtMap.get("CONTRACT_NUM"));		/* 계약번호 */
				inputMap.put("QNTY",				"1");							/* 수량 */
				inputMap.put("OUT_AMT",				rtMap.get("OUT_AMT"));			/* 출고가 */
				inputMap.put("INST_AMT",			rtMap.get("INST_AMT"));			/* 할부원금 */
				inputMap.put("USER_ID",				ClsAcntConfig.USER_ID);
				inputMap.put("MODEL_ID",			rtMap.get("MODEL_ID"));
				inputMap.put("INTM_SRL_NO",			rtMap.get("INTM_SRL_NO"));
				
				LOGGER.info("inputMap={}", inputMap);
				
				crdtLoanProcService.setCrdtLoanInfoProc(inputMap);
				
				procCnt++;
			}
			
			// 14일이내 취소분 조회
			list = crdtLoanMgmtMapper.getOpenCnclList(param);
			
			for(Map<String, Object> rtMap : list) {
				inputMap.clear();
				
				inputMap.put("CRDT_LOAN_APPL_YN",	"Y");							/* 여신적용여부 */
				inputMap.put("CRDT_LOAN_TSK",		"U");							/* 여신업무 */
				inputMap.put("TSK_TP",				"60");							/* 사용유형(개통취소) */
				inputMap.put("CRDT_LOAN_CL",		"2");							/* 당월 */
				inputMap.put("ORGN_ID",				rtMap.get("ORGN_ID"));			/* 개통대리점 */
				inputMap.put("CRDT_LOAN_AMT",		rtMap.get("CRDT_LOAN_AMT"));	/* 여신금액=출고단가+부가세 */
				inputMap.put("CRDT_LOAN_DT",		rtMap.get("CRDT_LOAN_DT"));		/* 여신일자 */
				inputMap.put("CUST_ID",				rtMap.get("CUST_ID"));			/* 고객ID */
				inputMap.put("SRVC_CNTR_NUM",		rtMap.get("SRVC_CNTR_NUM"));	/* 서비스계약번호 */
				inputMap.put("QNTY",				"1");							/* 수량 */
				inputMap.put("OUT_AMT",				rtMap.get("OUT_AMT"));			/* 출고가 */
				inputMap.put("INST_AMT",			rtMap.get("INST_AMT"));			/* 할부원금 */
				inputMap.put("USER_ID",				ClsAcntConfig.USER_ID);
			
				LOGGER.info("inputMap={}", inputMap);
				
				crdtLoanProcService.setCrdtLoanInfoProc(inputMap);
				
				procCnt++;
			}
			
			
			// 기기변경 여신부활 서비스
			list = crdtLoanMgmtMapper.getDvcChgRvlList(param);
			
			for(Map<String, Object> rtMap : list) {
				inputMap.clear();
				
				inputMap.put("CRDT_LOAN_APPL_YN",	rtMap.get("CRDT_LOAN_APPL_YN"));/* 여신적용여부 */
				inputMap.put("CRDT_LOAN_TSK",		rtMap.get("CRDT_LOAN_TSK"));	/* 여신업무 */
				inputMap.put("TSK_TP",				rtMap.get("TSK_TP"));			/* 부활유형(20:개통, 21:유심, 22:기변) */
				inputMap.put("CRDT_LOAN_CL",		rtMap.get("CRDT_LOAN_CL"));		/* 당월 */
				inputMap.put("ORGN_ID",				rtMap.get("ORGN_ID"));			/* 기변대리점 */
				inputMap.put("CRDT_LOAN_AMT",		rtMap.get("CRDT_LOAN_AMT"));	/* 여신금액=출고단가+부가세 */
				inputMap.put("CRDT_LOAN_DT",		rtMap.get("CRDT_LOAN_DT"));		/* 여신일자 */
				inputMap.put("CUST_ID",				rtMap.get("CUST_ID"));			/* 고객ID */
				inputMap.put("SRVC_CNTR_NUM",		rtMap.get("SRVC_CNTR_NUM"));	/* 서비스계약번호 */
				inputMap.put("QNTY",				rtMap.get("QNTY"));				/* 수량 */
				inputMap.put("OUT_AMT",				rtMap.get("OUT_AMT"));			/* 출고가 */
				inputMap.put("INST_AMT",			rtMap.get("INST_AMT"));			/* 할부원금 */
				inputMap.put("USER_ID",				ClsAcntConfig.USER_ID);
				inputMap.put("MODEL_ID",			rtMap.get("MODEL_ID"));
				inputMap.put("INTM_SRL_NO",			rtMap.get("INTM_SRL_NO"));
			
				LOGGER.info("inputMap={}", inputMap);
				
				crdtLoanProcService.setCrdtLoanInfoProc(inputMap);
				
				procCnt++;
			}
			
			// 기변취소 여신부활 서비스( 14일 이내 취소 )
			list = crdtLoanMgmtMapper.getDvcChgCnclList(param);
			
			for(Map<String, Object> rtMap : list) {
				inputMap.clear();
				
				inputMap.put("CRDT_LOAN_APPL_YN",	rtMap.get("CRDT_LOAN_APPL_YN"));/* 여신적용여부 */
				inputMap.put("CRDT_LOAN_TSK",		rtMap.get("CRDT_LOAN_TSK"));	/* 여신업무 */
				inputMap.put("TSK_TP",				rtMap.get("TSK_TP"));			/* 사용유형(61:기변취소) */
				inputMap.put("CRDT_LOAN_CL",		rtMap.get("CRDT_LOAN_CL"));		/* 당월 */
				inputMap.put("ORGN_ID",				rtMap.get("ORGN_ID"));			/* 기변대리점 */
				inputMap.put("CRDT_LOAN_AMT",		rtMap.get("CRDT_LOAN_AMT"));	/* 여신금액=출고단가+부가세 */
				inputMap.put("CRDT_LOAN_DT",		rtMap.get("CRDT_LOAN_DT"));		/* 여신일자 */
				inputMap.put("CUST_ID",				rtMap.get("CUST_ID"));			/* 고객ID */
				inputMap.put("SRVC_CNTR_NUM",		rtMap.get("SRVC_CNTR_NUM"));	/* 서비스계약번호 */
				inputMap.put("QNTY",				rtMap.get("QNTY"));				/* 수량 */
				inputMap.put("OUT_AMT",				rtMap.get("OUT_AMT"));			/* 출고가 */
				inputMap.put("INST_AMT",			rtMap.get("INST_AMT"));			/* 할부원금 */
				inputMap.put("USER_ID",				ClsAcntConfig.USER_ID);
			
				LOGGER.info("inputMap={}", inputMap);
				
				crdtLoanProcService.setCrdtLoanInfoProc(inputMap);
				
				procCnt++;
			}
			
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1034", e);
		}
		
		return procCnt;
	}
	
	/**
	 * 유심수납금액 여신부활
	 * @param param 
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setUsimPymCrdtLoanRvl(Map<String, Object> param) throws MvnoServiceException{
		
		int procCnt = 0;
		
		try {
			
			if(!param.containsKey("WORK_YM") || "".equals(param.get("WORK_YM"))){
				throw new MvnoServiceException("ECLS1030");
			}
			
			List<Map<String, Object>> list = crdtLoanMgmtMapper.getCrdtLoanRvlUsimPymList(param);
			
			for(Map<String, Object> map : list){
				// 조회된 대상들에 대한 여신부활 처리
				crdtLoanProcService.setCrdtLoanInfoProc(map);
				procCnt++;
			}
		} catch(MvnoServiceException e){
			throw e;
		} catch(Exception e){
			throw new MvnoServiceException("ECLS1005", e);
		}
		
		return procCnt;
	}
	
	
	
}
