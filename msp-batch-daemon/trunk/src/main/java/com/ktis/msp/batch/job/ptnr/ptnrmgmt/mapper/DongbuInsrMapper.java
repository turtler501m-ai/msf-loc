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

import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.DongbuPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface DongbuInsrMapper {
	/**
	 * 가입대상조회
	 */
	List<?> getRegInsrMemberList(int dayCnt);
	
	/**
	 * 보험가입자정보 유무
	 */
	DongbuPointVO getExistInsrMember(DongbuPointVO vo);
	
	/**
	 * 보험가입등록
	 */
	void insertInsrMemberMst(DongbuPointVO vo);
	
	/**
	 * 해지대상조회
	 */
	List<?> getCanInsrMemberList(int dayCnt);
	
	/**
	 * 보험가입해지
	 */
	void updateCanInsrMember(DongbuPointVO vo);
	
	/**
	 * 연동대상생성
	 */
	void insertPartnerDongbu01();
	
	/**
	 * 연동대상조회
	 */
	List<?> getInsrMemberSendList();
	
	void deletePartnerDongbu01();
	void deletePointFile(PointFileVO vo);
	
}
