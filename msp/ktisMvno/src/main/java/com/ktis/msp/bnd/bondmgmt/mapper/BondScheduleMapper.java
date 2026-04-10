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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BondScheduleMapper")
public interface BondScheduleMapper {

	// 단말 할부정보입력대상 가져오기 (신규개통)
	List<Map<String, Object>> getMspBondEqistInsertList();
	
	// 단말 할부정보입력대상 가져오기 (개통후 기기변경 또는 유심개통후 기기추가 구매인 경우)
	List<Map<String, Object>> getMspBondEqistInsertList2();
	
	// 유심/가입비 할부정보입력대상 가져오기
	List<Map<String, Object>> getMspBondUsimInsertList();
	
	// 이미 할부정보에 있는지 확인하기
	HashMap<String, Object> getMspBondMstSeqNum(HashMap<String, Object> param2);
	
	// 이미 할부정보에 있으면 N 으로 업데이트 하기
	void updateMspBondMst(int bondSeqNum);
	
	// 할부정보 입력하기
	int insertMspBondMstSelectSeq(Map<String, Object> param);
	void insertMspBondDtl(Map<String, Object> param);
	
	// 할부정보삭제대상 가져오기
	List<Map<String, Object>> getBondDeleteList();
	
	// 할부정보 삭제하기
	void deleteMspBondMst(String contractNum);
	void deleteMspBondDtl(String contractNum);

	// 개통월별채권정보 등록
	void insertSelectMspBondStaticMst();

	// 개통월회차별채권정보 등록
	void insertSelectMspBondStaticDtl();
	
	// 보증보험정보 등록(신규)
	void insertSelectMspGrntInsrInfo();
	
	// 보증보험정보 업데이트 정보 가져오기
	List<Map<String, Object>> getMspGrntInsrInfoTrgt();
	
	// 보증보험정보 업데이트
	void updateMspGrntInsrInfo(Map<String, Object> param);
	
	// 보증보험정보 등록(업데이트 된 것)
	void insertMspGrntInsrInfo(Map<String, Object> param);
	
	// 채권정보생성 프로시저 호출
	void callCreateBondMst();
	
}
