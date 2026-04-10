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
package com.ktis.msp.cmn.cdmgmt.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 공통코드에 관한 데이터처리 매퍼 클래스
 *
 * @author  표준프레임워크센터
 * @since 2014.01.24
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2014.01.24        표준프레임워크센터          최초 생성
 *
 * </pre>
 */
@Mapper("cmnCdMgmtMapper")
public interface CmnCdMgmtMapper {
		
	/**
     * 공통코드 호출 
     */
    List<HashMap<String, String>> getCmnCdList(HashMap<String, String> map);
    
    List<CmnCdMgmtVo> getCmnCdList2(HashMap<String, String> map);
    
	/* *********************************************************************** */
    /*  CMN_GRP_MST 테이블 Manipulation (Retrieval, Insert, Delete)              */
    /* *********************************************************************** */  
	List<Map<String, String>> getCmnGrpMst(HashMap<String, String> map);
	List<Map<String, String>> getFormMapping();
	List<Map<String, String>> getAllTables(String username);
	List<Map<String, String>> getAllComments();
	
	/**
	 * 공통코드Combo
	 * @param  : InvtrMvVO
	 * @Return : List<?>
	 */
	List<?> getCommCombo(CmnCdMgmtVo vo);
	
	/**
	 * MCP 공통코드Combo
	 * @param  : CmnCdMgmtVo
	 * @Return : List<?>
	 */
	List<?> getMcpCommCombo(CmnCdMgmtVo vo);

	Map<String, String> getCmnGrpCdMst(HashMap<String, String> map);

	Map<String, String> getMcpCommCdDtl(CmnCdMgmtVo vo);

}
