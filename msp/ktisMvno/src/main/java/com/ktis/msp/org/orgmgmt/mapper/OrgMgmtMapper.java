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
package com.ktis.msp.org.orgmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.org.orgmgmt.vo.OrgMgmtPpsVo;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : OrgMgmtMapper
 * @Description : 업무설명
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Mapper("orgMgmtMapper")
public interface OrgMgmtMapper {
	
	/**
	 * @Description : 조직 리스트를 조회한다.
	 * @Param  : OrgMgmtVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
    List<?> listOrgMgmt(OrgMgmtVO orgMgmtVO);
    
    /**
    * @Description : 조직 트리 리스트를 조회한다 
    * @Param  : 
    * @Return : List<?>
    * @Author : 고은정
    * @Create Date : 2014. 10. 13.
     */
    List<?> treeListOrgMgmt(OrgMgmtVO orgMgmtVO);

	/**
	 * @Description : 조직 리스트 Count 조회한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int listCntOrgMgmt(OrgMgmtVO orgMgmtVO);

	/**
	 * @Description : 조직 정보를 변경한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */     
    int updateOrgMgmt(OrgMgmtVO orgMgmtVO);

	/**
	 * @Description : 조직 정보를 변경한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */     
    int updateOrgMgmtMasking(OrgMgmtVO orgMgmtVO);
    
	/**
	 * @Description : 조직 FAX 정보를 변경한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */     
    int updateOrgMgmtFax(OrgMgmtVO orgMgmtVO);
    
	/**
	 * @Description : 신규 조직을 생성한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int insertOrgMgmt(OrgMgmtVO orgMgmtVO);
    
	/**
	 * @Description : 조직을 삭제한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */      
    int deleteOrgMgmt(OrgMgmtVO orgMgmtVO); 
    
	/**
	 * @Description : 조직이력을 삭제한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */      
    int deleteOrgMgmtHst(OrgMgmtVO orgMgmtVO); 
    
    /**
     * @Description : 조직 이력 리스트를 조회한다.
     * @Param  : OrgMgmtVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    List<?> listOrgnHst(OrgMgmtVO orgMgmtVO);
    
    /**
     * @Description : 조직상세정보를 조회한다.
     * @Param  : OrgMgmtVO
     * @Return : OrgMgmtVO
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    OrgMgmtVO detailOrgMgmt(OrgMgmtVO orgMgmtVO);
    
	/**
	 * @Description : 조직 이력 리스트 Count 조회한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int listCntOrgMgmtHst(OrgMgmtVO orgMgmtVO);
    
	/**
	 * @Description : 조직 이력의 MAX SEQ 조회한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    String listSeqOrgMgmtHst(OrgMgmtVO orgMgmtVO);
    
	/**
	 * @Description : 조직 이력의 MAX SEQ 조회한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    String detailOrgMgmtSeq(OrgMgmtVO orgMgmtVO);
    
	/**
	 * @Description : 조직 이력 정보를 넣는다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int insertOrgMgmtHst(OrgMgmtVO orgMgmtVO);
    
    /**
    * @Description : 조직레벨 공통코드 조회 
    * @Param  : 
    * @Return : List<?>
    * @Author : 고은정
    * @Create Date : 2014. 9. 23.
     */
    List<?> listCmnCdOrgnLvl(OrgMgmtVO orgMgmtVO);
    
    /**
    * @Description : 조직유형 공통코드 조회 
    * @Param  : 
    * @Return : List<?>
    * @Author : 고은정
    * @Create Date : 2014. 9. 24.
     */
    List<?> listCmnCdOrgnType(OrgMgmtVO orgMgmtVO);
    
    /**
    * @Description : 조직ID 중복검색 
    * @Param  : 
    * @Return : int
    * @Author : 고은정
    * @Create Date : 2014. 9. 27.
     */
    int isExistOrgnId(OrgMgmtVO orgMgmtVO);
    
    /**
	 * @Description : 조직 상태 가동점 체크로직
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int selectValCnt(OrgMgmtVO orgMgmtVO);  
    
	/**
	 * @Description : 조직 상태 가동점 체크로직
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int selectValCnt1(OrgMgmtVO orgMgmtVO);  
    
    /**
     * @Description : 조직 선불 정보 상세
     * @Param  : 
     * @Return : OrgMgmtPpsVo
     * @Author : 김웅
     * @Create Date : 2014.10. 06.
     */
	OrgMgmtPpsVo ppsOrgnInfoDetail(Map<String, Object> pRequestParamMap);
    
    /**
     * @Description : 조직 선불 정보 등록/변경 
     * @Param  : Map
     * @Return : Map
     * @Author : 김웅
     * @Create Date : 2014. 10. 06.
      */
	Map<String, Object> ppsOrgnChgProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 정보 조회
	 * @param orgMgmtVO
	 * @return
	 */
	Map<String, Object> getAgncyInfo(OrgMgmtVO orgMgmtVO);
    
	/**
	 * 대리점유형이력 생성
	 * @param map
	 */
	void insertAgncyTypeHst(Map<String, Object> map);
	
	
	/**
	 * 대리점유형이력 수정
	 * @param map
	 */
	int updateAgncyTypeHst(Map<String, Object> map);
	
	/**
	 * 대리점유형이력 최초 생성
	 * @param map
	 * @return
	 */
	void insertAgncyTypeHstFst(Map<String, Object> map);
	
	/**
	 * 사용자 조직 조회
	 * @param orgMgmtVO
	 * @return
	 */
	String getUsrOrgId(OrgMgmtVO orgMgmtVO);
	
    /**
     * @Description : 조직 유형 대분류를 가져온다
     * @Param  : 
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    List<?> selectOrgnType1(OrgMgmtVO orgMgmtVO);
    
    /**
     * @Description : 조직 유형 대분류를 가져온다
     * @Param  : orgnType
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    List<?> selectOrgnTypeDtl(String orgnType);
    
    
    /**
     * @Description : 조직 유형 대분류를 가져오기 위해 그룹ID 찾기
     * @Param  : orgnType
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    String selectOrgnTypeDtlCd(String orgnType);
    
    /**
     * @Description : 조직 하위 유형
     * @Param  : orgnType
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    String selectOrgnTypeChg(String orgnType);
    
    /**
     * @Description : 조직 변경 이력 리스트를 조회한다.
     * @Param  : OrgMgmtVO
     * @Return : List<?>
     * @Author : 김민지
     * @Create Date : 2018. 2. 28.
     */
    List<?> listOrgChgHst(OrgMgmtVO orgMgmtVO);
    
    /**
     * @Description : 조직 변경 이력 리스트 엑셀다운
     * @Param  : OrgMgmtVO
     * @Return : List<?>
     * @Author : 김민지
     * @Create Date : 2018. 2. 28.
     */
    List<?> listOrgChgHstExcel(OrgMgmtVO orgMgmtVO);
}
