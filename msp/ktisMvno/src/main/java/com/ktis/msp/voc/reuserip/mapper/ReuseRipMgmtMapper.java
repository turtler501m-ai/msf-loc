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
package com.ktis.msp.voc.reuserip.mapper;

import java.util.List;

import com.ktis.msp.voc.reuserip.vo.ReuseRipMgmtVO;
import com.ktis.msp.voc.reuseusim.vo.ReuseUsimMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("reuseRipMgmtMapper")
public interface ReuseRipMgmtMapper {
	
	/**
	 * 계약정보조회
	 */
	List<?> getContractInfo(ReuseRipMgmtVO vo);
	
	/**
	 * IP LOCK 등록 여부
	 */
	int getReuseRipCt(ReuseRipMgmtVO vo);
	
	/**
	 * ip 재사용 설저 MST INSERT
	 */
	int insertReuseRipMst(ReuseRipMgmtVO vo);
	
	/**
	 * ip 재사용 설정 HIST INSERT
	 */
	int insertReuseRipHist(ReuseRipMgmtVO vo);
	
	/**
	 * ip 재사용 설정 UPDATE HIST INSERT
	 */
	int insertReuseRipUpdHist(ReuseRipMgmtVO vo);
	
	/**
	 * IP재사용설정 리스트 조회
	 */
	List<?> selectReuseRipList(ReuseRipMgmtVO vo);
	
	/**
	 * IP 재사용 설저 MST UPDATE
	 */
	int updateReuseRipMst(ReuseRipMgmtVO vo);
	
	List<?> selectReuseRipListExcel(ReuseRipMgmtVO searchVO);
	
}

