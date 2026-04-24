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
package com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.RwdCmpnVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.RwdVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface RwdMapper {
	
	// 보상서비스 신청자 정보 생성
	int insertRwdMember();
	
	// 보상서비스 이미지 데이터 생성
	int insertRwdImg();
	
	// 보상서비스 신청자 정보 가져오기
	List<RwdVO> getRwdIfOut();
	
	List<RwdVO> getImageFileUpList();
	
	int uptImageFileUpList(RwdVO rwdVO);
	
	int uptRwdOutUpList(RwdVO rwdVO);
	
	int uptRwdUpList(RwdVO rwdVO);

	// 보상서비스 가입신청서 처리 결과 update
	int updateResult(RwdVO rwdVO);
	
	// 위니아에서 수정한 단말기구입가 update
	int updateBuyPric(RwdVO rwdVO);
	
	// 보상서비스 가입자 정보 생성
	int insertRwdSub();
	
	// 보상서비스 신청서 정보 변경
	int updateOrder();
	
	// 보상서비스 신청자 정보 가져오기
	List<RwdVO> selectRwdOrder();
	
	// 부가서비스 연동 정보 변경
	int updateRwdAddSvc(RwdVO rwdVO);
	
	// 보상서비스 가입자 정보
	List<RwdVO> selectAddSub();
	
	// 보상서비스 해지자 정보
	List<RwdVO> selectAddCan();
	
	// 보상서비스 변경자 정보
	List<RwdVO> selectAddChg();
	
	// 부가서비스 연동 정보 변경
	int updateRwdSvc(RwdVO rwdVO);
	
	// MSP_RWD_MEMBER 테이블 상태값 변경
	int updateMemberStat(RwdVO rwdVO);
	
	// 부가서비스 가입/해지/변경 파일 생성 데이터
	List<RwdVO> getRwdIfOutAdd();
	
	// 부가서비스 청구/수납 파일 생성 데이터
	void insertRwdBillPymn();
	
	// 부가서비스 청구/수납 정보 가져오기
	List<RwdVO> getBillPymn();
	
	// 권리실행 접수현황 등록
	int insertRwdCmpnRes(RwdCmpnVO rwdVO);
	
	// 권리실행 접수현황 원장 건수 확인
	int selectRwdCmpnRes(RwdCmpnVO rwdVO);
	
	// 권리실행 접수현황 원장 update
	void uptRwdCmpnRes(RwdCmpnVO rwdVO);
	
	// 권리실행 접수현황 원장 insert
	void intRwdCmpnRes(RwdCmpnVO rwdVO);
	
	// 보상처리현황 등록
	int insertRwdCmpnProc(RwdCmpnVO rwdVO);
	
	// 보상처리현황 원장 update
	void uptRwdCmpnProc(RwdCmpnVO rwdVO);
	
	// 보상처리현황 원장 insert
	void intRwdCmpnProc(RwdCmpnVO rwdVO);
	
	// 보상지급현황 등록
	int insertRwdCmpnPay(RwdCmpnVO rwdVO);

	// 보상지급현황 원장 update
	void uptRwdCmpnPay(RwdCmpnVO rwdVO);
	
	// 보상지급현황 원장 insert
	void intRwdCmpnPay(RwdCmpnVO rwdVO);
	
}