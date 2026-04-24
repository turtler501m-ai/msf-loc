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
import java.util.Map;

import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GoodLifeMemberVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GoodLifeReceiveVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GoodLifeVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("goodLifeSettleMapper")
public interface GoodLifeSettleMapper {
	//====================================================================
	// 2017-04-05, 좋은라이프 제휴
	List<Map<String, Object>> getGoodLifeSmsSendList(String partnerId);
	
	// 해지후 재가입여부 확인
	int checkGoodLifeMember(GoodLifeMemberVO vo);
	
	// 가입자정보등록
	void insertGoodLifeMemberMst(GoodLifeMemberVO vo);
	
	// 해지후 재가입자를 위한 처리
	void insertGoodLifeMemberMst2(GoodLifeMemberVO vo);
	
	// 가입자정보변경
	void updateGoodLifeMemberMst(GoodLifeMemberVO vo);
	
	// 가입자정보해지
	void deleteGoodLifeMemberMst(GoodLifeMemberVO vo);
	
	// 부가서비스가입정보변경
	void updateAddSvcYn();
	
	// 가입자정보조회(파일업로드)
	List<?> getGoodLifeMemberList();
	
	// 정산금액조회(파일업로드)
	List<?> getGoofLifeSettleAmntList();
	
	// 연동이력생성
	void insertPartnerGoodLife(GoodLifeVO vo);
	
	// 정산결과update
	void updateGoodLifePointSettle(GoodLifeVO vo);
	
	// 좋은라이프 수신 리스트 가져오기
	List<PointFileVO> getGoodlifeFileDownList(PointFileVO vo);
	
	// 제휴사 인터페이스 정보 가져오기
	List<PointFileVO> getPartnerSubList(PointFileVO vo);
	
	// 연동 데이터 삭제
	void deleteGoodlifeInterface(GoodLifeReceiveVO vo);
	
	// 연동 데이터 입력(GOODLIFE_002, GOODLIFE_004)
	void insertGoodlifeInterface(GoodLifeReceiveVO vo);
	
	// 좋은라이프 가입자정보 수정(GOODLIFE_002)
	void updateGoodlifeMember(GoodLifeReceiveVO vo);
	
	// 정산테이블 확인
	String checkGoodlifePoint(GoodLifeReceiveVO vo);
	
	// 정산 테이블 UPDATE - 상조 수납여부(GOODLIFE_002)
	void updateGoodlifePymnYm(GoodLifeReceiveVO vo);
	
	// 정산 테이블 UPDATE - 상조 지급여부(GOODLIFE_004)
	void updateGoodlifePoint(GoodLifeReceiveVO vo);
	
	// 정산테이블 생성을 위한 부가서비스 가입정보 조회
	List<EgovMap> getCheckAddSvcList();
	
	// 할인정보 update
	void updateMemberDcInfo(GoodLifeVO vo);
	
	// 정산테이블 36개월 생성(GOODLIFE_002)
	void insertAllCnt(Map<String, Object> map);
	
}
