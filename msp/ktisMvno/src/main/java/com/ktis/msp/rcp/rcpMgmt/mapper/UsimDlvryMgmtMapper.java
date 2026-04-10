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
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UsimDlvryMgmtMapper")
public interface UsimDlvryMgmtMapper {
	
	List<?> getUsimDlvryList(Map<String, Object> param);
	
	List<?> getUsimDlvryDtlList(Map<String, Object> param);
	
	List<?> getUsimDlvryListByExcel(Map<String, Object> param);
	
	List<?> getUsimDlvryDtlListByExcel(Map<String, Object> param);
	
	String getDlvryTbCd(String tbCd);
	
	String isDlvryInfoChk(String selfDlvryIdx);
	
	String isDlvryNoChk(String dlvryNo);
	
	String isDlvryIdxChk(String selfDlvryIdx);
	
	String isOpenInfoChk(String selfDlvryIdx);
	
	int updateUsimDlvryInfo(UsimDlvryMgmtVO vo);
	
	int updateDlvryNo(UsimDlvryMgmtVO vo);
	
	int updateDlvryWait(UsimDlvryMgmtVO vo);
	
	int updateDlvryOk(UsimDlvryMgmtVO vo);
	
	int updateOpenOk(UsimDlvryMgmtVO vo);
	
	//2020.12.10 유심번호일련번호 등록 2022.05.31 진행상태추가
	int updateUsimSn(UsimDlvryMgmtVO vo);
	int updateUsimBuyTxnSn(UsimDlvryMgmtVO vo);

	int updateUsimDlvryInfoMask(UsimDlvryMgmtVO usimDlvryMgmtVO);
	
	
	String usimReqOverChk(UsimDlvryMgmtVO vo);
	
	int usimSnDupChk(UsimDlvryMgmtVO vo);
	
	int insertUsimSn(UsimDlvryMgmtVO vo);
	
	String isUsimOpenInfoChk(UsimDlvryMgmtVO vo);
	
	int updateDlvryStateOpenOk(UsimDlvryMgmtVO vo);
	
	int updateUsimOpenOk(UsimDlvryMgmtVO vo);

	List<?> getUsimBuySeqNull(String selfDlvryIdx);
	
	String getUsimBuySeqNotNull(UsimDlvryMgmtVO vo);
	
	String isIdxCodeChk(String selfDlvryIdx);
	
}
