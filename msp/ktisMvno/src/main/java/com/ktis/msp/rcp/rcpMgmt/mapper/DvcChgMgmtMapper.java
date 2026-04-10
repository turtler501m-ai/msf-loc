/*
\ * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
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
package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.rcpMgmt.vo.DvcChgMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface DvcChgMgmtMapper {
	
	List<?> getDvcChgTrgtList(DvcChgMgmtVO vo);
	
	List<?> getDvcChgTrgtListExcel(DvcChgMgmtVO vo);
	
	List<?> getAsgnTrgtPrsnList();
	
	List<?> getDvcChgAsgnTrgtList(DvcChgMgmtVO vo);
	
	void updateDvcChgTrgtMst(DvcChgMgmtVO vo);
	
	List<?> getDvcChgPsblCheck(DvcChgMgmtVO vo);
	
	//List<?> getDvcChgSimCheck(DvcChgMgmtVO vo);
		
	int getVocRcpId();
	
	void insertVocMgmtMst(DvcChgMgmtVO vo);
	
	void updateVocMgmtMst(DvcChgMgmtVO vo);
	
	void insertVocMgmtDtl(DvcChgMgmtVO vo);
	
	String getAuthCheck(DvcChgMgmtVO vo);
	
	List<?> getDvcChgList(DvcChgMgmtVO vo);
	
	List<?> getDvcChgListExcel(DvcChgMgmtVO vo);
	
	String getUsimPrice(RcpDetailVO vo);
}

