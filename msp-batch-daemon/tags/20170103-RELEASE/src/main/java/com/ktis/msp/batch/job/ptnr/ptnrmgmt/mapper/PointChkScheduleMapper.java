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

import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GiftiPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.JejuairPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.TmoneyPointVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("pointChkScheduleMapper")
public interface PointChkScheduleMapper {

	// 제휴사 사용량 계산 프로시저 호출
	void callUsgPoint(PointFileVO vo);
	
	// 제주항공 제휴 포인트 정산 프로시저 호출
	void callPointJeju(PointFileVO vo);
	
	// 제휴사 아이디 가져오기
	List<?> getPartnerMstList();
	
	// 제휴사 파일 생성 결과
	void insertPointFile(PointFileVO vo);
	
	// 제휴사 파일 업로드 결과 업데이트
	void updatePointFileUp(PointFileVO vo);
	
	// 제휴사 파일 다운로드 결과 업데이트
	void updatePointFileDown(PointFileVO vo);
	
	// 제휴사 정산파일 업로드 리스트 가져오기
	List<?> getPointFileUpList(PointFileVO vo);
	
	// 제휴사 지급결과파일 다운로드 리스트 가져오기
	List<?> getPointFileDownList(PointFileVO vo);
	
	// 제휴사 지급결과 읽어야할 파일 리스트 가져오기
	List<?> getPointFileReadList(PointFileVO vo);
	
	// 제휴사 인터페이스 정보 가져오기
	List<?> getPartnerSubList(String ifNo);
	
	
	
	
	
	/* 제휴사 정산 포인트 가져오기 */
	
	// 제주항공 포인트 정산 내역 가져오기
	List<?> getJejuPointList(JejuairPointVO vo);
	
	// 제주항공 포인트 정산 내역 INSERT
	void insertJejuPointList(JejuairPointVO vo);
	
	// 엠하우스 포인트 정산 내역 가져오기
	List<?> getGiftiPointList(GiftiPointVO vo);
	
	// 엠하우스 포인트 정산 내역 INSERT
	void insertGiftiPointList(GiftiPointVO vo);
	
	// 티머니 포인트 정산 내역 가져오기
	List<?> getTmoneyPointList(TmoneyPointVO vo);
	
	// 티머니 포인트 정산 내역 INSERT
	void insertTmoneyPointList(TmoneyPointVO vo);
	
	
	
	/* 제휴사 정산 지급결과 업데이트 */
	
	// 제주항공 포인트 지급결과 업데이트
	void updateJejuPointMst(JejuairPointVO vo);
	
	// 제주항공 포인트 연동결과 업데이트
	void updatePartnerJejuair(JejuairPointVO vo);
	
	// 엠하우스 포인트 지급결과 업데이트
	void updateGiftiPointMst(GiftiPointVO vo);
	
	// 제주항공 포인트 연동결과 업데이트
	void updatePartnerGifti(GiftiPointVO vo);
	
	// 티머니 포인트 지급결과 업데이트
	void updateTmoneyPointMst(TmoneyPointVO vo);
	
	// 티머니 포인트 연동결과 업데이트
	void updatePartnerTmoney(TmoneyPointVO vo);
	
	
}
