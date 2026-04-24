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
package com.ktis.msp.batch.job.msp.canmgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.msp.canmgmt.vo.CanMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CanMgmtMapper")
public interface CanMgmtMapper {
	
	/**
	 * 직권해지대상조회
	 */
	List<CanMgmtVO> getCanMgmtList() throws Exception;
	
	/**
	 * 직권해지연동결과처리
	 */
	int setCanMgmt(CanMgmtVO vo) throws Exception;
	
	/**
	 * 직권해지연동결과처리 프로시져호출
	 */
	void callPpsCanUsrRes(CanMgmtVO vo);
}
