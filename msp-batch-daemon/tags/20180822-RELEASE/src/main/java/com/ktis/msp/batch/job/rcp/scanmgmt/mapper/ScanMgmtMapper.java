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
package com.ktis.msp.batch.job.rcp.scanmgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.rcp.scanmgmt.vo.ScanMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ScanMgmtMapper")
public interface ScanMgmtMapper {
	
	/**
	 * 서식지삭제대상생성
	 */
	int setScanMgmtList() throws Exception;
	
	/**
	 * 서식지삭제대상조회
	 */
	List<ScanMgmtVO> getScanMgmtList() throws Exception;
	
	/**
	 * 서식지삭제처리
	 */
	int setScanMgmt(ScanMgmtVO vo) throws Exception;
	
	
}
