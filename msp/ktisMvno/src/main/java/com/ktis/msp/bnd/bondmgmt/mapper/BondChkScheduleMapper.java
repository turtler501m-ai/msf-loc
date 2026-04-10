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
package com.ktis.msp.bnd.bondmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BondChkScheduleMapper")
public interface BondChkScheduleMapper {

	// 청구월 수납정보 가져오기
	List<Map<String, Object>> getMspBondPayList();
	
	// 미납월 수납정보 가져오기
	List<Map<String, Object>> getMspBondUnpayList();
	
	// 미납월 업데이트월 가져오기
	List<Map<String, Object>> getMspBondUnpayMonthList();
	
	// 수납정보(MSP_BOND_DTL) 업데이트
	void updateMspBondDtlPay(Map<String, Object> param);
	
	// 수납정보(MSP_BOND_MST) 업데이트
	void updateMspBondMstPay(Map<String, Object> param);
	
	// 수납정보(MSP_BOND_DTL) 미납여부 일괄 업데이트
	void updateMspBondDtlUnpayY(Map<String, Object> param);
	
	// 수납정보(MSP_BOND_DTL) 완납여부 일괄 업데이트
	void updateMspBondDtlUnpayN(Map<String, Object> param);
	
	// 수납정보(MSP_BOND_MST) 미납여부 일괄 업데이트
	void updateMspBondMstUnpayY();
	
	// 수납정보(MSP_BOND_MST) 완납 대상자 가져오기
	List<Map<String, Object>> getMspBondMstFullPayList();
	
	// 수납정보(MSP_BOND_MST) 완납여부 일괄 업데이트
	void updateMspBondMstFullPay(Map<String, Object> param);
	
	// 개통월별채권정보 업데이트 정보 가져오기
	List<Map<String, Object>> getMspBondStaticMstTrgt();
	
	// 개통월별채권정보 업데이트
	void updateMspBondStaticMst(Map<String, Object> param);
	
	// 개통월회차별채권정보 업데이트 정보 가져오기
	List<Map<String, Object>> getMspBondStaticDtlTrgt();
	
	// 개통월회차별채권정보 업데이트
	void updateMspBondStaticDtl(Map<String, Object> param);
	
	
	// 해지자 정보 가져오기
	List<Map<String, Object>> getMspBondMstTmntTrgtList();
	
	// 해지자 해지일 업데이트
	void updateMspBondMstTmnt(Map<String, Object> param);
	
	// 개통월별 해지건수 정보 가져오기
	List<Map<String, Object>> getMspBondMstTmntList();
	
	// 개통월회차별 해지건수 업데이트
	void updateMspBondStaticDtlTmnt(Map<String, Object> param);
	
	// 개통월별 해지건수 업데이트
	void updateMspBondStaticMstTmnt(Map<String, Object> param);
	
	
	// 개통월조기상환자 업데이트 정보 가져오기
	List<Map<String, Object>> getMspBondStaticErlyTrgt();

	// 개통월별 조기상환자 업데이트
	void updateMspBondStaticMstErly(Map<String, Object> param);
	
	// 개통월회차별 조기상환자 업데이트 (청구월기준으로 업데이트)
	void updateMspBondStaticDtlErly(Map<String, Object> param);
	

	// 개통월회차별 잔여할부계약 업데이트 정보 가져오기
	List<Map<String, Object>> getMspBondStaticRmnTrgt();

	// 개통월회차별 잔여할부계약수
	void updateBondStaticDtlRmn(Map<String, Object> param);
	
	// 채권판매상세내역 수납정보 업데이트 (BOND_SALE_DTL)
	void updateBondSaleDtlPay(Map<String, Object> param);
	
	// 채권판매계약번호 수납정보 업데이트 (BOND_SALE_CNTR)
	void updateBondSaleCntrPay(Map<String, Object> param);
	
	
	// 연체가산금 대상 정보 가져오기(2달전 청구에 대해서 연체가산금 수납 확인)
	List<Map<String, Object>> getMspBondStaticDlyTrgt();

	// 연체가산금 정보 업데이트
	void updateMspBondStaticDtlDly(Map<String, Object> param);
	
	
	// 조정금액 대상 정보 가져오기 (2달전 청구에 대해서 조정된 금액 확인)
	List<Map<String, Object>> getMspBondStaticAdjTrgt();

	// 조정금액, 건수 업데이트
	void updateMspBondStaticDtlAdj(Map<String, Object> param);
	
	
	
	// 월초/월말 건수 및 금액 업데이트 대상 가져오기
	List<Map<String, Object>> getMspBondStaticDtlMnthTrgt();

	// 월초/월말 건수 및 금액 업데이트 (MSP_BOND_STATIC_DTL)
	void updateMspBondStaticDtlMnth(Map<String, Object> param);
	
	
	// 11개월이내 연체인 경우 연체금액 구하기 업데이트 대상 가져오기
	List<Map<String, Object>> getMspBondStaticDtlMnthDlyTrgt();

	// 11개월이내 연체인 경우 연체금액 업데이트 (MSP_BOND_STATIC_DTL)
	void updateMspBondStaticDtlMnthDly(Map<String, Object> param);
	
	// 12개월이상 연체인 경우 연체금액 구하기 업데이트 대상 가져오기
	List<Map<String, Object>> getMspBondStaticDtlMnthDlyTrgt2();

	// 12개월이상 연체인 경우 연체금액 업데이트 (MSP_BOND_STATIC_DTL)
	void updateMspBondStaticDtlMnthDly2(Map<String, Object> param);
	
	
	
	// 정상청구 금액 구하기
	List<Map<String, Object>> getMspBondStaticDtlNmlAmt();

	// 정상청구금액 및 정상입금액(수납금액 - 조기상환금액) 업데이트
	void updateMspBondStaticDtlNmlAmt(Map<String, Object> param);
	
	
	
	// 연체청구금액 구하기 (수납처리 하기 전에 구해야 함)
	List<Map<String, Object>> getMspBondStaticDtlDlyBillAmt();

	// 연체청구금액 업데이트
	void updateMspBondStaticDtlDlyBillAmt(Map<String, Object> param);
	
	// 연체청구 수납금액 구하기 (수납처리 하기 전에 구해야 함)
	List<Map<String, Object>> getMspBondStaticDtlDlyPayAmt();

	// 연체수납금액 업데이트
	void updateMspBondStaticDtlDlyPayAmt(Map<String, Object> param);
	

	
	// 연체잔액 중 미청구분 (연체가 한번이라도 남아 있는 채권의 청구월 이후의 채권잔액의 합계)
	List<Map<String, Object>> getMspBondStaticDtlNotBillAmt();

	// 연체금액 중 미청구금액 업데이트 (MSP_BOND_STATIC_DTL)
	void updateMspBondStaticDtlNotBillAmt(Map<String, Object> param);
	
	
	
	// 개통월회차별 연체건수 구해오기
	List<Map<String, Object>> getMspBondStaticDlyCnt();

	// 개통월회차별 연체건수 업데이트  (MSP_BOND_STATIC_DTL)
	void updateMspBondStaticDtlDlyCnt(Map<String, Object> param);
	
	
	// 수납정보처리 프로시저 호출
	void callPayCheck();

}
