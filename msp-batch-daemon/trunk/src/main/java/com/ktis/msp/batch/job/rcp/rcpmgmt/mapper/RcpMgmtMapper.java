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
package com.ktis.msp.batch.job.rcp.rcpmgmt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RcpMgmtMapper")
public interface RcpMgmtMapper {
	
	/**
	 * 기변대상고객생성
	 */
	void callDvcChgTrgtProc(Map<String, Object> param);
	
	// 신청관리 엑셀 다운로드
	public List<RcpListVO> selectRcpListExcel(RcpListVO rcpListVO);
	void selectRcpListExcel(RcpListVO rcpListVO, ResultHandler resultHandler);
	public String getpContractNum(String pSearchName);
	
	//개통지연대상조회
	public List<RcpListVO> getDelayOpenList(RcpListVO rcpListVO);

	// 신청관리(New) 엑셀 다운로드
	void selectRcpRequestListExcel(RcpListVO rcpListVO, ResultHandler resultHandler);
}
