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

import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.IntmInsrVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface IntmInsrMapper {
	
	/**
	 * 단말보험 부가서비스 가입자조회
	 */
	List<IntmInsrVO> getIntmInsrTrgtList();
	
	/**
	 * 단말보험 부가서비스 가입자조회(일일)
	 */
	List<IntmInsrVO> getIntmInsrTrgtListDaily();
	
	/**
	 * 보험가입 오더정보 조회
	 */
	IntmInsrVO getIntmInsrOrder(IntmInsrVO vo);
	
	/**
	 * 개통 단말정보 조회
	 */
	IntmInsrVO getOpenModelInfo(IntmInsrVO vo);
	
	/**
	 * 기기변경 단말정보 조회
	 */
	IntmInsrVO getDvcChgModelInfo(IntmInsrVO vo);
		
	/**
	 * 보험가입 오더정보 생성
	 */
	int insertIntmInsrOrder(IntmInsrVO vo);
	
	/**
	 * 보험가입정보조회
	 */
	IntmInsrVO getIntmInsrMember(IntmInsrVO vo);
			
	/**
	 * 보험가입 오더정보 삭제(상태코드 변경)
	 */
	int deleteIntmInsrOrder(IntmInsrVO vo);
	
	/**
	 * 보험가입 오더정보 전송완료(상태코드 변경)
	 */
	int completeIntmInsrOrder(IntmInsrVO vo);
	
	/**
	 * 부가서비스 해지 대상자 등록
	 */
	int insertIntmInsrCanInfoOvr();
	
	/**
	 * 잔액한도소진 해지 대상자 등록
	 */
	int insertIntmInsrCanInfoLtl();
	
	/**
	 * 보험가입 오더정보 만료(상태코드 변경)
	 */
	int expireIntmInsrOrder();
	
	/**
	 * 부가서비스 해지 대상자 조회
	 */
	List<IntmInsrVO> getIntmInsrCanInfoList();
	
	/**
	 * 단말보험 기간만료 해지 대상자 조회
	 */
	List<IntmInsrVO> getExpireIntmInsrMemberList();
	
	/**
	 * 부가서비스 해지 완료 처리
	 */
	int completeIntmInsrCanInfo(IntmInsrVO vo);
	
	/**
	 * 해지자 조회
	 */
	List<IntmInsrVO> getIntmInsrDeleteTrgtList();
	
	/**
	 * 보험가입 원부정보 삭제(상태코드 변경)
	 */
	int deleteIntmInsrMember(IntmInsrVO vo);
	
	/**
	 * 연동정보생성
	 */
	int insertIntmInsrIfOut(IntmInsrVO vo);
	
	/**
	 * 보험가입자 명의변경 대상조회 
	 */
	List<IntmInsrVO> getIntmInsrMcnTrgtList();
	
	/**
	 * 보험가입자 개인정보수정 대상조회 
	 */
	List<IntmInsrVO> getIntmInsrChgTrgtList();
	
	/**
	 * 보험가입자 개인정보 현행화
	 */
	int updateIntmInsrMember(IntmInsrVO vo);
	
	/**
	 * 가입자 대상 조회
	 */
	List<IntmInsrVO> getIntmInsrJoinTrgtList();
	
	/**
	 * 단말보험 가입자 번호 생성
	 */
	String getIntmInsrMemberSeq();
	
	/**
	 * 단말보험 가입정보 생성
	 */
	int insertIntmInsrMember(IntmInsrVO vo);
	
	/**
	 * 연동대상 정보 조회
	 */
	List<IntmInsrVO> getIntmInsrIfOut();
	
	/**
	 * DB연동 가입자 정보 조회
	 */
	List<IntmInsrVO> getIntmInsrIfOutDB();
	
	/**
	 * 연동완료처리
	 */
	int updateIntmInsrIfOut(IntmInsrVO vo);
	
	/**
	 * 단말보험보상 번호 생성
	 */
	String getIntmInsrCmpnSeq();
	
	/**
	 * 단말보험보상연동정보 저장
	 */
	int insertIntmInsrCmpnMstIf(IntmInsrVO vo);
	
	/**
	 * 단말보험보상연동정보 반영
	 */
	int updateIntmInsrCmpnMember(IntmInsrVO vo);
	
	/**
	 * 단말보험전손처리정보 저장
	 */
	int insertIntmInsrCmpnDtl(IntmInsrVO vo);
	
	/**
	 * 단말보험상품명 조회
	 */
	String getIntmInsrProdNm(String prodCd);
	
	/**
	 * 단말보험가입 신청내역 조회(연동)
	 */
	List<IntmInsrVO> getIntmInsrOrderList();
	
	/**
	 * 단말보험가입 부적격내역 조회(연동)
	 */
	List<IntmInsrVO> getIntmInsrOrderFailList();

	/**
	 * 부가서비스 해지 임시 재처리 대상 조회
	 */
	List<IntmInsrVO> getIntmInsrCanRetryList();

	/**
	 * 부가서비스 해지 완료 임시 재처리
	 */
	int completeIntmInsrCanRetry(IntmInsrVO vo);
	
	/**
	 * 단말보험 부가서비스 처리 대상자 생성
	 */
	int insertIntmInsrReady();
	
	/**
	 * 단말보험 부가서비스 처리 대상자 가입 및 해지 완료
	 */
	int updateIntmInsrReady(IntmInsrVO vo);
	
	/**
	 * 단말보험 부가서비스 처리 대상자 기간만료(+3일) 해지 처리 요청
	 */
	int insertIntmInsrCanInfoReady();
	
	/**
	 * 단말보험 부가서비스 처리 대상자 기간만료(+3일) 해지 처리 요청 완료
	 */
	int updateIntmInsrReadyExp();

}
