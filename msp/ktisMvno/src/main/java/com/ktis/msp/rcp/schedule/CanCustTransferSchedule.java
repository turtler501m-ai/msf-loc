package com.ktis.msp.rcp.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.rcp.schedule.mapper.CanCustTransferScheduleMapper;

/**
 * 해지고객이관스케줄러
 * 
 * M모바일 계약 해지후 6개월이 경과된 고객은 미납이 없는 경우 해지고객 DB로 이관하고, 관련 데이터를 삭제한다. 
 * 
 */
public class CanCustTransferSchedule {
	
	private final transient Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private CanCustTransferScheduleMapper mapper;
	
	/**
	 * Job 실행
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void executeJob() {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		logger.info("--- 해지고객 이관 스케줄러 ---");
		// 해지고객 정보 조회
		// 이관 대상자 추출
		List<Map<String, Object>> list = mapper.getCanCustTrgtList();
		
		String contractNum = "";
		String customerId = "";
		String reqBuyType = "";
		String sRequestKey = "";
		String ban = "";
		String typeCode = "";
		
		int cnt = 0;
		int totalCnt = 0;
		int requestKey;
		
//		int n = 0;
		int a = 0;
		
		int ppCnt = 0;	// PP 선불
		int mmCnt = 0;	// PO 후불 (단말)
		int uuCnt = 0;	// PO 후불 (유심)
		
		logger.info("---이관 대상자 수 : [" + list.size() + "]");
		
		for(Map<String, Object> rtMap : list){
			
			totalCnt++;
			contractNum = String.valueOf(rtMap.get("CONTRACT_NUM"));
			sRequestKey = String.valueOf(rtMap.get("REQUEST_KEY"));
			ban = String.valueOf(rtMap.get("BAN"));
			customerId = String.valueOf(rtMap.get("CUSTOMER_ID"));
			reqBuyType = String.valueOf(rtMap.get("REQ_BUY_TYPE"));
			typeCode = String.valueOf(rtMap.get("TYPE_CODE"));
			
			logger.info("contractNum : [" + contractNum + "], customerId : [" + customerId + "], reqBuyType : [" + reqBuyType + "], typeCode : " + typeCode+ "], requestKey : " + sRequestKey + "], ban : [" + ban + "]");
			
			
			// 이미 이관되어 있는지 확인
			cnt = mapper.getCanMspJuoSubInfoCnt(contractNum);
			
			if(cnt > 0) {
				deleteAllCanCustTrgt(rtMap);
				a++;
				continue;
			}

			// PPS_CUSTOMER_KT 테이블 이관 작업
			transferPpsCustomerKt(contractNum, ban, customerId);

			if(!StringUtils.isEmpty(contractNum) && !"null".equals(contractNum)) {

				// PPS_CUSTOMER 테이블 이관 작업
				transferPpsCustomer(contractNum);
				
				
				// MSP_JUO_ADD_INFO 테이블 이관 작업
				transferMspJuoAddInfo(contractNum);
				
				// MSP_JUO_SUB_INFO 테이블 이관 작업
				transferMspJuoSubInfo(contractNum);
				
				// MSP_JUO_SUB_INFO_HIST 테이블 이관 작업
				transferMspJuoSubInfoHist(contractNum);
				
			}
			
			if(!StringUtils.isEmpty(sRequestKey) && !"null".equals(sRequestKey)) {
				
				requestKey = Integer.parseInt(sRequestKey);
				// MSP_REQUEST_AGENT 테이블 이관 작업
				transferMspRequestAgent(requestKey);
				
				// MSP_REQUEST_MOVE 테이블 이관 작업
				transferMspRequestMove(requestKey);
				
				// MSP_REQUEST_CSTMR 테이블 이관 작업
				transferMspRequestCstmr(requestKey);
				
				// MSP_REQUEST_REQ 테이블 이관 작업
				transferMspRequestReq(requestKey);
				
				// MSP_REQUEST_CHANGE 테이블 이관 작업
				transferMspRequestChange(requestKey);
				
				// MSP_REQUEST_DLVRY 테이블 이관 작업
				transferMspRequestDlvry(requestKey);
				
				// MCP_REQUEST_AGENT 테이블 이관 작업
				transferMcpRequestAgent(requestKey);
				
				// MCP_REQUEST_MOVE 테이블 이관 작업
				transferMcpRequestMove(requestKey);
				
				// MCP_REQUEST_CSTMR 테이블 이관 작업
				transferMcpRequestCstmr(requestKey);
				
				// MCP_REQUEST_REQ 테이블 이관 작업
				transferMcpRequestReq(requestKey);
				
				// MCP_REQUEST_CHANGE 테이블 이관 작업
				transferMcpRequestChange(requestKey);
				
				// MCP_REQUEST_DLVRY 테이블 이관 작업
				transferMcpRequestDlvry(requestKey);
				
			}
			
			if(!StringUtils.isEmpty(ban) && !"null".equals(ban)) {

				// PPS_CUSTOMER_KT 테이블 이관 작업
//				transferPpsCustomerKt(contractNum, ban, customerId);

				// MSP_JUO_BAN_INFO 테이블 이관 작업
				transferMspJuoBanInfo(ban);
				
				// MSP_JUO_BAN_INFO_HIST 테이블 이관 작업
				transferMspJuoBanInfoHist(ban);
				
			}
			
			if(!StringUtils.isEmpty(customerId) && !"null".equals(customerId)) {

				// PPS_CUSTOMER_KT 테이블 이관 작업
//				transferPpsCustomerKt(contractNum, ban, customerId);

				// MSP_JUO_CUS_INFO 테이블 이관 작업
				transferMspJuoCusInfo(customerId);
				
				// MSP_JUO_CUS_INFO_HIST 테이블 이관 작업
				transferMspJuoCusInfoHist(customerId);
				
			}
			
			// 값에 따라 카운터해서 통계 테이블에 넣기 
			if("PP".equals(typeCode)) {
				ppCnt++;
			} else if ("PO".equals(typeCode)) {
				if ("UU".equals(reqBuyType)) {
					uuCnt++;
				} else if ("MM".equals(reqBuyType)) {
					mmCnt++;
				}
			} 

			logger.info("================= 처리된 건수 totalCnt ========== [" + totalCnt + "]");
			//break;
			
		}
		
		// 이관 통계(MSP_CAN_CUST_TRNS_RSLT) 테이블에 넣기
		insertMspCanCustTrnsRslt(ppCnt, mmCnt, uuCnt);
		stopWatch.stop();
		logger.info("--- 이미 이관된 건수 : [" + a + "]");
		logger.info("--- 선불(PP) : [" + ppCnt + "], 후불단말(MM) : [" + mmCnt + "], 후불유심(UU) : [" + uuCnt + "]");
		logger.info("--- 해지고객 이관 스케줄러 끝 --- 걸린시간 : [" + stopWatch.toString() + "]");
		
	}
	
	// 이미 이관되어 있으므로 모든 테이블에 데이터 삭제
	private int deleteAllCanCustTrgt(Map<String, Object> rtMap) {
		int nResult = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		
		String contractNum = String.valueOf(rtMap.get("CONTRACT_NUM"));
		String sRequestKey = String.valueOf(rtMap.get("REQUEST_KEY"));
		String ban = String.valueOf(rtMap.get("BAN"));
		String customerId = String.valueOf(rtMap.get("CUSTOMER_ID"));
		int requestKey;
		
		param.put("contractNum", contractNum);
		param.put("ban", ban);
		param.put("customerId", customerId);
		
		// PPS_CUSTOMER_KT 테이블 이관 작업
		mapper.deletePpsCustomerKt(param);
		
		if(!StringUtils.isEmpty(contractNum) && !"null".equals(contractNum)) {

			// PPS_CUSTOMER 테이블 삭제 작업
			mapper.deletePpsCustomer(contractNum);
			
			// MSP_JUO_ADD_INFO 테이블 이관 작업
			mapper.deleteMspJuoAddInfo(contractNum);
			
			// MSP_JUO_SUB_INFO 테이블 이관 작업
			mapper.deleteMspJuoSubInfo(contractNum);
			
			// MSP_JUO_SUB_INFO_HIST 테이블 이관 작업
			mapper.deleteMspJuoSubInfoHist(contractNum);
		}
		
		if(!StringUtils.isEmpty(sRequestKey) && !"null".equals(sRequestKey)) {
			
			requestKey = Integer.parseInt(sRequestKey);
			// MSP_REQUEST_AGENT 테이블 이관 작업
			mapper.deleteMspRequestAgent(requestKey);
			
			// MSP_REQUEST_MOVE 테이블 이관 작업
			mapper.deleteMspRequestMove(requestKey);
			
			// MSP_REQUEST_CSTMR 테이블 이관 작업
			mapper.deleteMspRequestCstmr(requestKey);
			
			// MSP_REQUEST_REQ 테이블 이관 작업
			mapper.deleteMspRequestReq(requestKey);
			
			// MSP_REQUEST_CHANGE 테이블 이관 작업
			mapper.deleteMspRequestChange(requestKey);
			
			// MSP_REQUEST_DLVRY 테이블 이관 작업
			mapper.deleteMspRequestDlvry(requestKey);
			
			// MCP_REQUEST_AGENT 테이블 이관 작업
			mapper.deleteMcpRequestAgent(requestKey);
			
			// MCP_REQUEST_MOVE 테이블 이관 작업
			mapper.deleteMcpRequestMove(requestKey);
			
			// MCP_REQUEST_CSTMR 테이블 이관 작업
			mapper.deleteMcpRequestCstmr(requestKey);
			
			// MCP_REQUEST_REQ 테이블 이관 작업
			mapper.deleteMcpRequestReq(requestKey);
			
			// MCP_REQUEST_CHANGE 테이블 이관 작업
			mapper.deleteMcpRequestChange(requestKey);
			
			// MCP_REQUEST_DLVRY 테이블 이관 작업
			mapper.deleteMcpRequestDlvry(requestKey);
		}
		
		if(!StringUtils.isEmpty(ban) && !"null".equals(ban)) {
			// MSP_JUO_BAN_INFO 테이블 이관 작업
			mapper.deleteMspJuoBanInfo(ban);
			
			// MSP_JUO_BAN_INFO_HIST 테이블 이관 작업
			mapper.deleteMspJuoBanInfoHist(ban);
		}
		
		if(!StringUtils.isEmpty(customerId) && !"null".equals(customerId)) {
			// MSP_JUO_CUS_INFO 테이블 이관 작업
			mapper.deleteMspJuoCusInfo(customerId);
			
			// MSP_JUO_CUS_INFO_HIST 테이블 이관 작업
			mapper.deleteMspJuoCusInfoHist(customerId);
		}
		
		return nResult;
	}
	
	// PPS_CUSTOMER 테이블 이관 작업
	private int transferPpsCustomer(String contractNum) {
		logger.info("========== PPS_CUSTOMER 테이블 이관 작업 ==========");
		int nResult = 0;
		
		// CAN_PPS_CUSTOMER 테이블에 insert
		mapper.insertCanPpsCustomer(contractNum);
		// PPS_CUSTOMER 테이블에서 delete
		mapper.deletePpsCustomer(contractNum);
		
		return nResult;
	}
	
	// PPS_CUSTOMER_KT 테이블 이관 작업
	private int transferPpsCustomerKt(String contractNum, String ban, String customerId) {
		logger.info("========== PPS_CUSTOMER_KT 테이블 이관 작업 ==========");
		int nResult = 0;
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("contractNum", contractNum);
		param.put("ban", ban);
		param.put("customerId", customerId);
		logger.info("param : [" + param.toString() + "]");

		// CAN_PPS_CUSTOMER_KT 테이블에 insert
		mapper.insertCanPpsCustomerKt(param);
		// PPS_CUSTOMER_KT 테이블에서 delete
		mapper.deletePpsCustomerKt(param);

		return nResult;
	}
	
	// MSP_JUO_ADD_INFO 테이블 이관 작업
	private int transferMspJuoAddInfo(String contractNum) {
		logger.info("========== MSP_JUO_ADD_INFO 테이블 이관 작업 ==========");
		int nResult = 0;

		// CAN_MSP_JUO_ADD_INFO 테이블에 insert
		mapper.insertCanMspJuoAddInfo(contractNum);
		// MSP_JUO_ADD_INFO 테이블에서 delete
		mapper.deleteMspJuoAddInfo(contractNum);
		
		return nResult;
	}
	
	// MSP_JUO_BAN_INFO 테이블 이관 작업
	private int transferMspJuoBanInfo(String ban) {
		logger.info("========== MSP_JUO_BAN_INFO 테이블 이관 작업 ==========");
		int nResult = 0;
		
		// CAN_MSP_JUO_BAN_INFO 테이블에 insert
		mapper.insertCanMspJuoBanInfo(ban);
		// MSP_JUO_BAN_INFO 테이블에서 delete
		mapper.deleteMspJuoBanInfo(ban);
		
		return nResult;
	}
	
	// MSP_JUO_BAN_INFO_HIST 테이블 이관 작업
	private int transferMspJuoBanInfoHist(String ban) {
		logger.info("========== MSP_JUO_BAN_INFO_HIST 테이블 이관 작업 ==========");
		int nResult = 0;
		
		mapper.insertCanMspJuoBanInfoHist(ban);
		// MSP_JUO_BAN_INFO_HIST 테이블에서 delete
		mapper.deleteMspJuoBanInfoHist(ban);
		
		return nResult;
	}
	
	// MSP_JUO_CUS_INFO 테이블 이관 작업
	private int transferMspJuoCusInfo(String customerId) {
		logger.info("========== MSP_JUO_CUS_INFO 테이블 이관 작업 ==========");
		int nResult = 0;
			
		// CAN_MSP_JUO_CUS_INFO 테이블에 insert
		mapper.insertCanMspJuoCusInfo(customerId);
		// MSP_JUO_CUS_INFO 테이블에서 delete
		mapper.deleteMspJuoCusInfo(customerId);
		
		return nResult;
	}

	// MSP_JUO_CUS_INFO_HIST 테이블 이관 작업
	private int transferMspJuoCusInfoHist(String customerId) {
		logger.info("========== MSP_JUO_CUS_INFO_HIST 테이블 이관 작업 ==========");
		int nResult = 0;
			
		// CAN_MSP_JUO_CUS_INFO_HIST 테이블에 insert
		mapper.insertCanMspJuoCusInfoHist(customerId);
		// MSP_JUO_CUS_INFO_HIST 테이블에서 delete
		mapper.deleteMspJuoCusInfoHist(customerId);
		
		return nResult;
	}
	
	// MSP_REQUEST_AGENT 테이블 이관 작업
	private int transferMspRequestAgent(int requestKey) {
		logger.info("========== MSP_REQUEST_AGENT 테이블 이관 작업 ==========");
		int nResult = 0;

		// CAN_MSP_REQUEST_AGENT 테이블에 insert
		mapper.insertCanMspRequestAgent(requestKey);
		// MSP_REQUEST_AGENT 테이블에서 delete
		mapper.deleteMspRequestAgent(requestKey);
		
		return nResult;
	}
	
	// MSP_REQUEST_MOVE 테이블 이관 작업
	private int transferMspRequestMove(int requestKey) {
		logger.info("========== MSP_REQUEST_MOVE 테이블 이관 작업 ==========");
		int nResult = 0;
			
		// CAN_MSP_REQUEST_AGENT 테이블에 insert
		mapper.insertCanMspRequestMove(requestKey);
		// MSP_REQUEST_AGENT 테이블에서 delete
		mapper.deleteMspRequestMove(requestKey);
		
		return nResult;
	}
	
	// MSP_REQUEST_CSTMR 테이블 이관 작업
	private int transferMspRequestCstmr(int requestKey) {
		int nResult = 0;

		// CAN_MSP_REQUEST_CSTMR 테이블에 insert
		mapper.insertCanMspRequestCstmr(requestKey);
		// MSP_REQUEST_CSTMR 테이블에서 delete
		mapper.deleteMspRequestCstmr(requestKey);
		
		return nResult;
	}
	
	// MSP_REQUEST_REQ 테이블 이관 작업
	private int transferMspRequestReq(int requestKey) {
		logger.info("========== MSP_REQUEST_REQ 테이블 이관 작업 ==========");
		int nResult = 0;

		// CAN_MSP_REQUEST_REQ 테이블에 insert
		mapper.insertCanMspRequestReq(requestKey);
		// MSP_REQUEST_REQ 테이블에서 delete
		mapper.deleteMspRequestReq(requestKey);
		
		return nResult;
	}
	
	// MSP_REQUEST_CHANGE 테이블 이관 작업
	private int transferMspRequestChange(int requestKey) {
		logger.info("========== MSP_REQUEST_CHANGE 테이블 이관 작업 ==========");
		int nResult = 0;
			
		// CAN_MSP_REQUEST_CHANGE 테이블에 insert
		mapper.insertCanMspRequestChange(requestKey);
		// MSP_REQUEST_CHANGE 테이블에서 delete
		mapper.deleteMspRequestChange(requestKey);
		
		return nResult;
	}
	
	// MSP_REQUEST_DLVRY 테이블 이관 작업
	private int transferMspRequestDlvry(int requestKey) {
		logger.info("========== MSP_REQUEST_DLVRY 테이블 이관 작업 ==========");
		int nResult = 0;
			
		// CAN_MSP_REQUEST_DLVRY 테이블에 insert
		mapper.insertCanMspRequestDlvry(requestKey);
		// MSP_REQUEST_DLVRY 테이블에서 delete
		mapper.deleteMspRequestDlvry(requestKey);
		
		return nResult;
	}
	
	// MCP_REQUEST_AGENT 테이블 이관 작업
	private int transferMcpRequestAgent(int requestKey) {
		logger.info("========== MCP_REQUEST_AGENT 테이블 이관 작업 ==========");
		int nResult = 0;
			
		// CAN_MCP_REQUEST_AGENT 테이블에 insert
		mapper.insertCanMcpRequestAgent(requestKey);
		// MCP_REQUEST_AGENT 테이블에서 delete
		mapper.deleteMcpRequestAgent(requestKey);
		
		return nResult;
	}
	
	// MCP_REQUEST_MOVE 테이블 이관 작업
	private int transferMcpRequestMove(int requestKey) {
		int nResult = 0;

		// CAN_MCP_REQUEST_AGENT 테이블에 insert
		mapper.insertCanMcpRequestMove(requestKey);
		// MCP_REQUEST_AGENT 테이블에서 delete
		mapper.deleteMcpRequestMove(requestKey);
		
		return nResult;
	}
	
	// MCP_REQUEST_CSTMR 테이블 이관 작업
	private int transferMcpRequestCstmr(int requestKey) {
		logger.info("========== MCP_REQUEST_MOVE 테이블 이관 작업 ==========");
		int nResult = 0;

		// CAN_MCP_REQUEST_CSTMR 테이블에 insert
		mapper.insertCanMcpRequestCstmr(requestKey);
		// MCP_REQUEST_CSTMR 테이블에서 delete
		mapper.deleteMcpRequestCstmr(requestKey);
		
		return nResult;
	}
	
	// MCP_REQUEST_REQ 테이블 이관 작업
	private int transferMcpRequestReq(int requestKey) {
		logger.info("========== MCP_REQUEST_REQ 테이블 이관 작업 ==========");
		int nResult = 0;

		// CAN_MCP_REQUEST_REQ 테이블에 insert
		mapper.insertCanMcpRequestReq(requestKey);
		// MCP_REQUEST_REQ 테이블에서 delete
		mapper.deleteMcpRequestReq(requestKey);
		
		return nResult;
	}
	
	// MCP_REQUEST_CHANGE 테이블 이관 작업
	private int transferMcpRequestChange(int requestKey) {
		logger.info("========== MCP_REQUEST_CHANGE 테이블 이관 작업 ==========");
		int nResult = 0;

		// CAN_MCP_REQUEST_CHANGE 테이블에 insert
		mapper.insertCanMcpRequestChange(requestKey);
		// MCP_REQUEST_CHANGE 테이블에서 delete
		mapper.deleteMcpRequestChange(requestKey);
		
		return nResult;
	}
	
	// MCP_REQUEST_DLVRY 테이블 이관 작업
	private int transferMcpRequestDlvry(int requestKey) {
		logger.info("========== MCP_REQUEST_DLVRY 테이블 이관 작업 ==========");
		int nResult = 0;

		// CAN_MCP_REQUEST_DLVRY 테이블에 insert
		mapper.insertCanMcpRequestDlvry(requestKey);
		// MCP_REQUEST_DLVRY 테이블에서 delete
		mapper.deleteMcpRequestDlvry(requestKey);
		
		return nResult;
	}
	
	// MSP_JUO_SUB_INFO 테이블 이관 작업
	private int transferMspJuoSubInfo(String contractNum) {
		logger.info("========== MSP_JUO_SUB_INFO 테이블 이관 작업 ==========");
		int nResult = 0;

		// CAN_MSP_JUO_SUB_INFO 테이블에 insert
		mapper.insertCanMspJuoSubInfo(contractNum);
		// MSP_JUO_SUB_INFO 테이블에서 delete
		mapper.deleteMspJuoSubInfo(contractNum);
		
		return nResult;
	}
	
	// MSP_JUO_SUB_INFO_HIST 테이블 이관 작업
	private int transferMspJuoSubInfoHist(String contractNum) {
		int nResult = 0;

		// CAN_MSP_JUO_SUB_INFO_HIST 테이블에 insert
		mapper.insertCanMspJuoSubInfoHist(contractNum);
		// MSP_JUO_SUB_INFO_HIST 테이블에서 delete
		mapper.deleteMspJuoSubInfoHist(contractNum);
		
		return nResult;
	}
	
	// 이관 통계(MSP_CAN_CUST_TRNS_RSLT) 테이블에 넣기
	private int insertMspCanCustTrnsRslt(int ppCnt, int mmCnt, int uuCnt) {
		int nResult = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		Map<String, Object> param2 = new HashMap<String, Object>();
		Map<String, Object> param3 = new HashMap<String, Object>();

		param1.put("serviceType", "PP");
		param1.put("openType", "05");
		param1.put("trnsCnt", ppCnt);
		list.add(param1);
		
		param2.put("serviceType", "PO");
		param2.put("openType", "00");
		param2.put("trnsCnt", mmCnt);
		list.add(param2);
		
		param3.put("serviceType", "PO");
		param3.put("openType", "05");
		param3.put("trnsCnt", uuCnt);
		list.add(param3);

		// 선불카운트 등록 (ppCnt)
		for (Map<String, Object> param : list) {
			mapper.insertMspCanCustTrnsRslt(param);
		}

		return nResult;
		
	}
	
}

