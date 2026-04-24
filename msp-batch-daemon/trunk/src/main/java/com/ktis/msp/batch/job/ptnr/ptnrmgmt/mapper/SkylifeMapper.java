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

import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.SkylifeVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface SkylifeMapper {
	/**
	 * 가입대상 조회
	 */
	List<SkylifeVO> getRegMemberList();
	
	/**
	 * 가입자정보변경 조회
	 */
	List<SkylifeVO> getChgMemberList();
	
	/**
	 * 가입여부 조회
	 */
	int getExistMember(String contractNum);
	
	/**
	 * 연동대상조회
	 */
	List<SkylifeVO> getSendMemberList();
	
	/**
	 * 가입등록
	 */
	int insertMemberMst(SkylifeVO vo);
	
	/**
	 * 연동이력등록
	 */
	int insertMemberIfHst(SkylifeVO vo);
	
	/**
	 * 가입자정보변경
	 */
	int updateMemberMst(SkylifeVO vo);
	
}
