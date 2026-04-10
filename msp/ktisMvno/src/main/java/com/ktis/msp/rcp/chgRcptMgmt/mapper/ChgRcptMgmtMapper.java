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
package com.ktis.msp.rcp.chgRcptMgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.chgRcptMgmt.vo.ChgRcptMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("chgRcptMgmtMapper")
public interface ChgRcptMgmtMapper {
	
	/**
	 * 변경신청목록조회
	 */
	List<?> getChgRcptList(ChgRcptMgmtVO vo);
	
	/**
	 * 계약정보조회
	 */
	List<?> getContractInfo(ChgRcptMgmtVO vo);
	
	/**
	 * 전화번호검색이력 생성
	 */
	int insertChgRcptSearchHst(ChgRcptMgmtVO vo);
	
	/**
	 * 일련번호 채번
	 */
	int getSeqNum();
	
	/**
	 * 변경신청등록
	 */
	int insertChgRcptMst(ChgRcptMgmtVO vo);
	
	/**
	 * 보험변경이력 조회
	 */
	int getInsrChangeHst(ChgRcptMgmtVO vo);
	
	/**
	 * 단체보험해지
	 */
	void cancelInsrMember(ChgRcptMgmtVO vo);
	
	/**
	 * ATM 개통 계약번호 확인
	 */
	ChgRcptMgmtVO getAtmOpenContract(ChgRcptMgmtVO vo);
	
	/**
	 * MSP_JUO_SUB_INFO.SCAN_ID 업데이트
	 */
	void updateSubInfoScanId(ChgRcptMgmtVO vo);
	
	/**
	 * 서식지 파일체크
	 */
	int getScanFileCount(ChgRcptMgmtVO vo);
	
	/**
	 * 개통서식지 scanId 조회
	 */
	ChgRcptMgmtVO getSubInfoScanId(ChgRcptMgmtVO vo);
	
	/**
	 * ATM 고객 서식지 합본(마스터 생성)
	 */
	void insertEmvScanMst(ChgRcptMgmtVO vo);
	
	/**
	 * 서식지 합본
	 */
	void insertEmvScanFile(ChgRcptMgmtVO vo);

	List<?> getInsrPsblYn(ChgRcptMgmtVO vo);
	
}

