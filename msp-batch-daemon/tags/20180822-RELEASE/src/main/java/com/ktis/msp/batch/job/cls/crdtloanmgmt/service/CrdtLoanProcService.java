package com.ktis.msp.batch.job.cls.crdtloanmgmt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.crdtloanmgmt.mapper.CrdtLoanMgmtMapper;

/**
 * 2016-08-26
 *      여신부활시 단말모델ID, 일련번호 부활이력에 추가 
 */
@Service
public class CrdtLoanProcService extends BaseService {
	
	@Autowired
	private CrdtLoanMgmtMapper crdtLoanMgmtMapper;
	
	// 여신사용
	static final String USG = "U";
	
	// 여신부활
	static final String RVL = "R";
	
	// 조직유형(대리점)
	static final String AGNCY_ORG_TYPE = "20";
	
	/**
	 * 여신정보(사용/부활)처리
	 * @param paramters
	 * <ul>여신정보(사용/부활이력)를 처리한다</li>
	 * <li>CRDT_LOAN_APPL_YN=여신적용여부(Y/N)</li>
	 * <li>CRDT_LOAN_TSK=여신업무(U:사용, R:부활)</li>
	 * <li>TSK_TP=업무유형(여신사용/부활유형)</li>
	 * <li>CRDT_LOAN_CL=여신구분(1:전월, 2:당월, 3:익월, 4:익익월)</li>
	 * <li>ORGN_ID=조직ID</li>
	 * <li>CRDT_LOAN_AMT=여신금액</li>
	 * <li>CRDT_LOAN_DT=여신사용/부활일자</li>
	 * <li>CUST_ID=고객번호</li>
	 * <li>SRVC_CNTR_NUM=서비스계약번호</li>
	 * <li>ORD_ID=주문ID</li>
	 * <li>INVC_ID=송장ID</li>
	 * <li>MV_REQ_NUM=이동요청번호</li>
	 * <li>QNTY=수량</li>
	 * <li>OUT_AMT=출고가</li>
	 * <li>INST_AMT=할부금액</li>
	 * <li>DPST_PROC_DT=입금처리일자</li>
	 * <li>DPST_SRL_NUM=입금일련번호</li>
	 * <li>USER_ID=사용자ID</li>
	 * <li>INOUT_ID=입출고ID</li>
	 * <li>MODEL_ID=대표모델ID</li>
	 * <li>INTM_SRL_NO=단말일련번호</li>
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setCrdtLoanInfoProc(Map<String, Object> param) throws MvnoServiceException {
		LOGGER.debug("여신정보(사용/부활)처리 param={}", param);
		
		// 여신적용여부 체크
		if(param.get("CRDT_LOAN_APPL_YN") == null || !"Y".equals(param.get("CRDT_LOAN_APPL_YN"))) {
			throw new MvnoServiceException("ECLS1006");
		}
		
		// 여신업무 체크 
		if(param.get("CRDT_LOAN_TSK") == null || "".equals(param.get("CRDT_LOAN_TSK"))) {
			throw new MvnoServiceException("ECLS1007");
		}
		
		// 업무유형 체크 
		if(param.get("TSK_TP") == null || "".equals(param.get("TSK_TP"))) {
			throw new MvnoServiceException("ECLS1008");
		}
		
		// 여신구분 체크 
//		if(param.get("CRDT_LOAN_CL") == null || "".equals(param.get("CRDT_LOAN_CL"))){
//			throw new MvnoRunException(-1, "여신구분 누락");
//		}
		
		// 조직ID 체크
		if(param.get("ORGN_ID") == null || "".equals(param.get("ORGN_ID"))) {
			throw new MvnoServiceException("ECLS1009");
		}
		
		// 사용자ID 체크
		if(param.get("USER_ID") == null || "".equals(param.get("USER_ID"))) {
			throw new MvnoServiceException("ECLS1010");
		}
		
		// 조직정보 조회
		LOGGER.debug("조직정보조회..................................");
		Map<String, Object> orgMap = crdtLoanMgmtMapper.getOrgnInfo(param);
		
		if(orgMap == null) {
			throw new MvnoServiceException("ECLS1011");
		}
		
		// 여신 사용시 조직유형 체크
		if(USG.equals(param.get("CRDT_LOAN_TSK")) &&
			// 단말주문(10), 단말반품취소(40), 단말이동(50)은 대리점만 가능
			("10".equals(param.get("TSK_TP")) || "40".equals(param.get("TSK_TP")) || "50".equals(param.get("TSK_TP"))) &&
				(!AGNCY_ORG_TYPE.equals(orgMap.get("TYPE_CD"))) ) {
					throw new MvnoServiceException("ECLS1012");
		}
		
		// 여신 부활시 조직유형 체크
		if(RVL.equals(param.get("CRDT_LOAN_TYPE")) &&
			// 단말반품(40), 단말이동(50) 은 대리점만 가능
			("40".equals(param.get("TSK_TP")) || "50".equals(param.get("TSK_TP"))) &&
					(!AGNCY_ORG_TYPE.equals(orgMap.get("TYPE_CD"))) ) {
						throw new MvnoServiceException("ECLS1012");
		}
		
		// 데이터 처리를 위한 Map
		Map<String, Object> inputMap = new HashMap<String, Object>();
		
		// 여신유형
		String crdtLoanTp = "";
		
		// 사용/부활유형코드에 따라서 여신유형코드 세팅
		if(USG.equals(param.get("CRDT_LOAN_TSK"))) {
			LOGGER.debug("여신사용이력 등록....................................");
			/**
			 * TSK_TP 에 따른 validation 체크
			 */
			// 단말주문 체크
			if("10".equals(param.get("TSK_TP"))) {
				// 화면에서 매출등록시 예외처리
				if(param.get("INVC_ID") != null && !"".equals(param.get("INVC_ID")) && (param.get("ORD_ID") == null || "".equals(param.get("ORD_ID")))) {
					param.put("ORD_ID", param.get("INVC_ID"));
				}
				
				if(param.get("ORD_ID") == null || "".equals(param.get("ORD_ID"))) {
					throw new MvnoServiceException("ECLS1013");
				}
				
				if(param.get("QNTY") == null || "".equals(param.get("QNTY"))) {
					throw new MvnoServiceException("ECLS1014");
				}
			}
			
			// 단말반품(취소)시 체크
			if("40".equals(param.get("TSK_TP"))) {
				if(param.get("INVC_ID") == null || "".equals(param.get("INVC_ID"))) {
					throw new MvnoServiceException("ECLS1015");
				}
				
				if(param.get("QNTY") == null || "".equals(param.get("QNTY"))) {
					throw new MvnoServiceException("ECLS1014");
				}
			}
			
			// 단말이동(입고대리점)시 체크
			if("50".equals(param.get("TSK_TP"))) {
				if(param.get("MV_REQ_NUM") == null || "".equals(param.get("MV_REQ_NUM"))) {
					throw new MvnoServiceException("ECLS1016");
				}
				
				if(param.get("QNTY") == null || "".equals(param.get("QNTY"))) {
					throw new MvnoServiceException("ECLS1014");
				}
			}
			
			/**
			 * 여신유형 세팅
			 */
			// 10 : 단말주문, 40 : 반품취소, 50 : 단말이동, 60:개통취소
			if("10".equals(param.get("TSK_TP")) || "40".equals(param.get("TSK_TP")) || "50".equals(param.get("TSK_TP")) || "60".equals(param.get("TSK_TP"))) {
				if("1".equals(param.get("CRDT_LOAN_CL"))) {
					crdtLoanTp = "20";	// 여신유형(개통)
				} else {
					crdtLoanTp = "10";	// 여신유형(담보)
				}
			}
			
			// 요금수납(20), 선불충전(30), 패널티(81), 수수료환수(82), 할부채권매입환수(83), 기타(99), 이면 개통여신 
			if("20".equals(param.get("TSK_TP")) || "30".equals(param.get("TSK_TP")) || "81".equals(param.get("TSK_TP")) || "82".equals(param.get("TSK_TP")) || "83".equals(param.get("TSK_TP")) || "99".equals(param.get("TSK_TP")) || "ZZ".equals(param.get("TSK_TP"))) {
				crdtLoanTp = "20";	// 개통여신
			}
			
			// 현재 개통여신 관리를 안하고 있으므로 여신유형을 담보여신으로 고정
			crdtLoanTp = "10";
			
			/**
			 * 단말주문시 여신잔액 비교 조회
			 */
			LOGGER.debug("여신잔액조회..................................");
			long crdtLoanBal = crdtLoanMgmtMapper.getCrdtLoanBal(param);
			
			LOGGER.debug("TSK_TP={}", param.get("TSK_TP"));
			LOGGER.debug("여신잔액={}", crdtLoanBal);
			LOGGER.debug("여신사용금액={}", param.get("CRDT_LOAN_AMT"));
			
			if("10".equals(param.get("TSK_TP")) && crdtLoanBal < Long.parseLong(param.get("CRDT_LOAN_AMT").toString())) {
				// 단말주문이고 여신잔액이 사용금액보다 작은 경우 오류처리
				throw new MvnoServiceException("ECLS1017");
			}
			
			/**
			 * 여신사용이력등록
			 */
			inputMap.clear();
			inputMap.put("ORGN_ID",				param.get("ORGN_ID"));
			inputMap.put("CRDT_LOAN_TYPE_CD",	crdtLoanTp);
			inputMap.put("CRDT_LOAN_USG_TYPE",	param.get("TSK_TP"));
			inputMap.put("CRDT_LOAN_USG_DT",	param.get("CRDT_LOAN_DT"));
			inputMap.put("CRDT_LOAN_USG_AMT",	param.get("CRDT_LOAN_AMT"));
			// 주문ID, 송장ID 판단
			if(param.containsKey("ORD_ID") && param.get("ORD_ID") != null) {
				inputMap.put("ORD_ID",			param.get("ORD_ID"));
			} else {
				inputMap.put("ORD_ID",			param.get("INVC_ID"));
			}
			inputMap.put("MV_REQ_NUM",			param.get("MV_REQ_NUM"));
			inputMap.put("CUST_ID",				param.get("CUST_ID"));
			inputMap.put("SRVC_CNTR_NUM",		param.get("SRVC_CNTR_NUM"));
			inputMap.put("QNTY",				param.get("QNTY"));
			inputMap.put("USER_ID",				param.get("USER_ID"));
			
			LOGGER.debug("여신사용이력등록 inputMap={}", inputMap);
			
			crdtLoanMgmtMapper.insertCrdtLoanUsgHst(inputMap);
			
			/**
			 * 여신정보 수정
			 */
			inputMap.clear();
			inputMap.put("ORGN_ID",				param.get("ORGN_ID"));
			inputMap.put("CRDT_LOAN_TYPE_CD",	crdtLoanTp);
			inputMap.put("CRDT_LOAN_AMT",		param.get("CRDT_LOAN_AMT"));
			inputMap.put("USER_ID",				param.get("USER_ID"));
			
			LOGGER.debug("여신정보수정 inputMap={}", inputMap);
			
			crdtLoanMgmtMapper.updateCrdtLoanInfoUsg(inputMap);
		}
		
		// 여신부활
		if(RVL.equals(param.get("CRDT_LOAN_TSK"))) {
			if("10".equals(param.get("TSK_TP"))) {
				// 요금수납대체
				crdtLoanTp = "10";
			} else if("20".equals(param.get("TSK_TP"))) {
				// 개통
				crdtLoanTp = "20";
			} else if("30".equals(param.get("TSK_TP"))) {
				// 결산
				crdtLoanTp = "10";	
			} else if("40".equals(param.get("TSK_TP")) || "50".equals(param.get("TSK_TP")) || "60".equals(param.get("TSK_TP"))) {
				// 단말반품, 대리점이동(출고대리점), 주문취소
				if("1".equals(param.get("CRDT_LOAN_CL"))) {
					// 여신유형(개통)
					crdtLoanTp = "20";
				} else {
					// 여신유형(담보)
					crdtLoanTp = "10";
				}
			} else if("21".equals(param.get("TSK_TP"))){
				// 유심부활
				crdtLoanTp = "10";
			} else if("22".equals(param.get("TSK_TP"))){
				// 기기변경
				crdtLoanTp = "22";
			}
			
			// 2014-11-05
			// 여신부활시 담보여신으로 통일해서 적용
			crdtLoanTp = "10";
			
			/**
			 * Validation Check
			 */
			// 단말반품
			if("40".equals(param.get("TSK_TP"))) {
				if(param.get("INVC_ID") == null || "".equals(param.get("INVC_ID"))) {
					throw new MvnoServiceException("ECLS1015");
				}
				
				if(param.get("QNTY") == null || "".equals(param.get("QNTY"))) {
					throw new MvnoServiceException("ECLS1014");
				}
			}
			
			// 단말이동
			if("50".equals(param.get("TSK_TP"))){
				if(param.get("MV_REQ_NUM") == null || "".equals(param.get("MV_REQ_NUM"))) {
					throw new MvnoServiceException("ECLS1016");
				}
				
				if(param.get("QNTY") == null || "".equals(param.get("QNTY"))) {
					throw new MvnoServiceException("ECLS1014");
				}
			}
			
			// 주문취소
			if("60".equals(param.get("TSK_TP"))) {
				if((param.get("ORD_ID") == null || "".equals(param.get("ORD_ID"))) && (param.get("INVC_ID") == null || "".equals(param.get("INVC_ID")))) {
					throw new MvnoServiceException("ECLS1018");
				}
				
				if(param.get("QNTY") == null || "".equals(param.get("QNTY"))) {
					throw new MvnoServiceException("ECLS1014");
				}
			}
			
			// 20 : 개통, 21 : 유심, 22 : 기기변경
			if("20".equals(param.get("TSK_TP")) || "21".equals(param.get("TSK_TP")) || "22".equals(param.get("TSK_TP"))) {
				if(param.get("SRVC_CNTR_NUM") == null || "".equals(param.get("SRVC_CNTR_NUM"))) {
					throw new MvnoServiceException("ECLS1019");
				}
				
				if(param.get("OUT_AMT") == null || "".equals(param.get("OUT_AMT"))) {
					throw new MvnoServiceException("ECLS1020");
				}
				
				if(param.get("INST_AMT") == null || "".equals(param.get("INST_AMT"))) {
					throw new MvnoServiceException("ECLS1021");
				}
			}
			
			// 채권변제
			/*if("41".equals(param.get("TSK_TP"))) {
				if(param.get("INOUT_ID") == null || "".equals(param.get("INOUT_ID"))) {
					throw new MvnoServiceException("ECLS1022");
				}
			}*/
			if("41".equals(param.get("TSK_TP")) && (param.get("INOUT_ID") == null || "".equals(param.get("INOUT_ID")))) {
				throw new MvnoServiceException("ECLS1022");
			}
						
			// 담보여신 또는 개통여신이고 개통여신사용가능한 경우
			if("10".equals(crdtLoanTp) || ("20".equals(crdtLoanTp) && "Y".equals(orgMap.get("OPEN_CRDT_LOAN_YN")))) {
				// 여신부활이력 등록
				inputMap.clear();
				inputMap.put("ORGN_ID",				param.get("ORGN_ID"));
				inputMap.put("CRDT_LOAN_TYPE_CD",	crdtLoanTp);
				inputMap.put("CRDT_LOAN_RVL_TYPE",	param.get("TSK_TP"));
				inputMap.put("CRDT_LOAN_RVL_DT",	param.get("CRDT_LOAN_DT"));
				inputMap.put("CRDT_LOAN_RVL_AMT",	param.get("CRDT_LOAN_AMT"));
				inputMap.put("ORD_ID",				param.get("ORD_ID"));
				inputMap.put("INVC_ID",				param.get("INVC_ID"));
				inputMap.put("MV_REQ_NUM",			param.get("MV_REQ_NUM"));
				inputMap.put("QNTY",				param.get("QNTY"));
				inputMap.put("CUST_ID",				param.get("CUST_ID"));
				inputMap.put("SRVC_CNTR_NUM",		param.get("SRVC_CNTR_NUM"));
				inputMap.put("SRVC_TELNUM",			param.get("SRVC_TELNUM"));
				inputMap.put("OUT_AMT",				param.get("OUT_AMT"));
				inputMap.put("INST_AMT",			param.get("INST_AMT"));
				inputMap.put("DPST_PROC_DT",		param.get("DPST_PROC_DT"));
				inputMap.put("DPST_SRL_NUM",		param.get("DPST_SRL_NUM"));
				inputMap.put("SRL_MGMT_NUM",		param.get("SRL_MGMT_NUM"));
				inputMap.put("USER_ID",				param.get("USER_ID"));
				inputMap.put("INOUT_ID",			param.get("INOUT_ID"));
				inputMap.put("MODEL_ID",			param.get("MODEL_ID"));
				inputMap.put("INTM_SRL_NO",			param.get("INTM_SRL_NO"));
				
				LOGGER.debug("여신부활 inputMap={}", inputMap);
				
				// 여신부활이력 등록
				crdtLoanMgmtMapper.insertCrdtLoanRvlHst(inputMap);
				
				// 여신정보 수정
				inputMap.clear();
				inputMap.put("ORGN_ID",				param.get("ORGN_ID"));
				inputMap.put("CRDT_LOAN_TYPE_CD",	crdtLoanTp);
				inputMap.put("CRDT_LOAN_AMT",		param.get("CRDT_LOAN_AMT"));
				inputMap.put("USER_ID",				param.get("USER_ID"));
				
				LOGGER.debug("여신정보 update inputMap={}", inputMap);
				
				// 여신정보(부활) 수정
				int i = crdtLoanMgmtMapper.updateCrdtLoanInfoRvl(inputMap);
				
				// 수정건수가 0 이고 개통여신이면 개통여신정보 생성
				if(i == 0 && "20".equals(crdtLoanTp)) {
					inputMap.clear();
					
					inputMap.put("ORGN_ID",				param.get("ORGN_ID"));
					inputMap.put("CRDT_LOAN_TYPE_CD",	crdtLoanTp);
					inputMap.put("GNRL_CRDT_LOAN_AMT",	"0");
					inputMap.put("SPCL_CRDT_LOAN_AMT",	"0");
					inputMap.put("CRDT_LOAN_USG_AMT",	"0");
					inputMap.put("CRDT_LOAN_RVL_AMT",	param.get("CRDT_LOAN_AMT"));
					inputMap.put("CRDT_LOAN_BAL",		param.get("CRDT_LOAN_AMT"));
					inputMap.put("USER_ID",				param.get("USER_ID"));
					
					LOGGER.debug("개통여신등록 inputMap={}", inputMap);
					
					crdtLoanMgmtMapper.insertCrdtLoanInfoMap(inputMap);
				}
			}
			
		}
	}
	
}
