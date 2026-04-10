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
package com.ktis.msp.voc.reuseusim.mapper;

import java.util.List;

import com.ktis.msp.gift.custgmt.vo.CustMngVO;
import com.ktis.msp.rcp.chgRcptMgmt.vo.ChgRcptMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.PointTxnVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpNewMgmtVO;
import com.ktis.msp.voc.reuseusim.vo.ReuseUsimMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("reuseUsimMgmtMapper")
public interface ReuseUsimMgmtMapper {
	
	/**
	 * 계약정보조회
	 */
	List<?> getContractInfo(ReuseUsimMgmtVO vo);
	
	/**
	 * 유심 LOCK 등록 여부
	 */
	int getReuseUsimCt(ReuseUsimMgmtVO vo);
	
	/**
	 * 유심 재사용 설저 MST INSERT
	 */
	int insertReuseUsimMst(ReuseUsimMgmtVO vo);
	
	/**
	 * 유심 재사용 설저 HIST INSERT
	 */
	int insertReuseUsimHist(ReuseUsimMgmtVO vo);
	
	/**
	 * 유심재사용설정 리스트 조회
	 */
	List<?> selectReuseUsimList(ReuseUsimMgmtVO vo);
	
	/**
	 * 유심 재사용 설저 MST UPDATE
	 */
	int updateReuseUsimMst(ReuseUsimMgmtVO vo);
	
	List<?> selectReuseUsimListExcel(ReuseUsimMgmtVO searchVO);
	
	String getRealUsim(String reuseSeq);
	
	String getContUsim(ReuseUsimMgmtVO vo);
}

