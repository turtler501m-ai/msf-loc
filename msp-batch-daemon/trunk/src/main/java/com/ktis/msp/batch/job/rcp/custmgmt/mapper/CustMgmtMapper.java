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
package com.ktis.msp.batch.job.rcp.custmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CustMgmtMapper")
public interface CustMgmtMapper {

	List<Map<String, Object>> getMinorSmsTrgtList();
	
	/**
	 * 월말확인
	 * @return
	 */
	String getLastDayCheck();
	
	/**
	 * 약정만료전 고객고지 대상조회
	 * @return
	 */
	List<Map<String, Object>> getEnggCloseNotiList(String lastDayYn);
	
	/**
	 * 약정만료전 고객고지 대상조회
	 * @return
	 */
	List<Map<String, Object>> getEnggDayCloseNotiList();
	
	
	/**
	 * 약정만료후 30일 , 60일 , 90일 , 120일 , 180일 고객 재약정 고지
	 *  20210316 추가
	 * @return
	 */
	List<Map<String, Object>> getEnggExtendNotiList();
	
	/**
	 * 약정만료 후 리텐션 고지 대상조회
	 * @return
	 */
	List<Map<String, Object>> getRetentionNotiList();
	
}
