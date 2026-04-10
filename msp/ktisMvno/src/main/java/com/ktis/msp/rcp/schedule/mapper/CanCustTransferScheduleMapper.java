/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.rcp.schedule.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CanCustTransferScheduleMapper")
public interface CanCustTransferScheduleMapper {

	// 이관 대상자 추출
	List<Map<String, Object>> getCanCustTrgtList();
	
	// PPS_CUSTOMER 테이블 작업
//	Map<String, Object> getPpsCustomer(String contractNum);
//	int getPpsCustomerCnt(String contractNum);
	void insertCanPpsCustomer(String contractNum);
	void deletePpsCustomer(String contractNum);
	
	// PPS_CUSTOMER_KT 테이블 작업
	//List<Map<String, Object>> getPpsCustomerKtList(String contractNum);
	//Map<String, Object> getPpsCustomerKt(String enterDate);
	//int getPpsCustomerKtCnt(String enterDate);
	void insertCanPpsCustomerKt(Map<String, Object> map);
	void deletePpsCustomerKt(Map<String, Object> map);
	
	// MSP_JUO_ADD_INFO 테이블 작업
	//Map<String, Object> getMspJuoAddInfo(String contractNum);
	//int getMspJuoAddInfoCnt(String contractNum);
	void insertCanMspJuoAddInfo(String contractNum);
	void deleteMspJuoAddInfo(String contractNum);
	
	// MSP_JUO_BAN_INFO 테이블 작업
	//Map<String, Object> getMspJuoBanInfo(String ban);
	//int getMspJuoBanInfoCnt(String ban);
	void insertCanMspJuoBanInfo(String ban);
	void deleteMspJuoBanInfo(String ban);
	
	// MSP_JUO_BAN_INFO_HIST 테이블 작업
	//List<Map<String, Object>> getMspJuoBanInfoHistList(String ban);
	//Map<String, Object> getMspJuoBanInfoHist(String evntChangeDate);
	//int getMspJuoBanInfoHistCnt(String evntChangeDate);
	void insertCanMspJuoBanInfoHist(String ban);
	void deleteMspJuoBanInfoHist(String ban);
	
	// MSP_JUO_CUS_INFO 테이블 테이블 작업
	//Map<String, Object> getMspJuoCusInfo(String customerId);
	//int getMspJuoCusInfoCnt(String customerId);
	void insertCanMspJuoCusInfo(String customerId);
	void deleteMspJuoCusInfo(String customerId);
	
	// MSP_JUO_CUS_INFO_HIST 테이블 테이블 작업
	//List<Map<String, Object>> getMspJuoCusInfoHistList(String customerId);
	//Map<String, Object> getMspJuoCusInfoHist(String evntChangeDate);
	//int getMspJuoCusInfoHistCnt(String evntChangeDate);
	void insertCanMspJuoCusInfoHist(String customerId);
	void deleteMspJuoCusInfoHist(String customerId);
	
	// MSP_REQUEST_AGENT 테이블 작업
	//Map<String, Object> getMspRequestAgent(int requestKey);
	//int getMspRequestAgentCnt(int requestKey);
	void insertCanMspRequestAgent(int requestKey);
	void deleteMspRequestAgent(int requestKey);
	
	// MSP_REQUEST_MOVE 테이블 작업
	//Map<String, Object> getMspRequestMove(int requestKey);
	//int getMspRequestMoveCnt(int requestKey);
	void insertCanMspRequestMove(int requestKey);
	void deleteMspRequestMove(int requestKey);
	
	// MSP_REQUEST_CSTMR 테이블 작업
	//Map<String, Object> getMspRequestCstmr(int requestKey);
	//int getMspRequestCstmrCnt(int requestKey);
	void insertCanMspRequestCstmr(int requestKey);
	void deleteMspRequestCstmr(int requestKey);
	
	// MSP_REQUEST_REQ 테이블 작업
	//Map<String, Object> getMspRequestReq(int requestKey);
	//int getMspRequestReqCnt(int requestKey);
	void insertCanMspRequestReq(int requestKey);
	void deleteMspRequestReq(int requestKey);
	
	// MSP_REQUEST_CHANGE 테이블 작업
//	Map<String, Object> getMspRequestChange(int requestKey);
//	int getMspRequestChangeCnt(int requestKey);
	void insertCanMspRequestChange(int requestKey);
	void deleteMspRequestChange(int requestKey);
	
	// MSP_REQUEST_DLVRY 테이블 작업
//	Map<String, Object> getMspRequestDlvry(int requestKey);
//	int getMspRequestDlvryCnt(int requestKey);
	void insertCanMspRequestDlvry(int requestKey);
	void deleteMspRequestDlvry(int requestKey);
	
	// MCP_REQUEST_AGENT 테이블 작업
//	Map<String, Object> getMcpRequestAgent(int requestKey);
//	int getMcpRequestAgentCnt(int requestKey);
	void insertCanMcpRequestAgent(int requestKey);
	void deleteMcpRequestAgent(int requestKey);
	
	// MCP_REQUEST_MOVE 테이블 작업
//	Map<String, Object> getMcpRequestMove(int requestKey);
//	int getMcpRequestMoveCnt(int requestKey);
	void insertCanMcpRequestMove(int requestKey);
	void deleteMcpRequestMove(int requestKey);
	
	// MCP_REQUEST_CSTMR 테이블 작업
//	Map<String, Object> getMcpRequestCstmr(int requestKey);
//	int getMcpRequestCstmrCnt(int requestKey);
	void insertCanMcpRequestCstmr(int requestKey);
	void deleteMcpRequestCstmr(int requestKey);
	
	// MCP_REQUEST_REQ 테이블 작업
//	Map<String, Object> getMcpRequestReq(int requestKey);
//	int getMcpRequestReqCnt(int requestKey);
	void insertCanMcpRequestReq(int requestKey);
	void deleteMcpRequestReq(int requestKey);
	
	// MCP_REQUEST_CHANGE 테이블 작업
//	Map<String, Object> getMcpRequestChange(int requestKey);
//	int getMcpRequestChangeCnt(int requestKey);
	void insertCanMcpRequestChange(int requestKey);
	void deleteMcpRequestChange(int requestKey);
	
	// MCP_REQUEST_DLVRY 테이블 작업
//	Map<String, Object> getMcpRequestDlvry(int requestKey);
//	int getMcpRequestDlvryCnt(int requestKey);
	void insertCanMcpRequestDlvry(int requestKey);
	void deleteMcpRequestDlvry(int requestKey);
	
	// MSP_JUO_SUB_INFO 테이블 작업
//	Map<String, Object> getMspJuoSubInfo(String contractNum);
	int getCanMspJuoSubInfoCnt(String contractNum);
	void insertCanMspJuoSubInfo(String contractNum);
	void deleteMspJuoSubInfo(String contractNum);
	
	// MSP_JUO_SUB_INFO_HIST 테이블 작업
//	List<Map<String, Object>> getMspJuoSubInfoHistList(String contractNum);
//	Map<String, Object> getMspJuoSubInfoHist(String evntChangeDate);
//	int getMspJuoSubInfoHistCnt(String evntChangeDate);
	void insertCanMspJuoSubInfoHist(String contractNum);
	void deleteMspJuoSubInfoHist(String contractNum);
	
	// 이관 통계(MSP_CAN_CUST_TRNS_RSLT) 테이블에 넣기
	void insertMspCanCustTrnsRslt(Map<String, Object> param);
}
