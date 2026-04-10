/*
 * Copyright 2008-2009 the original author or authors.
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
package com.ktis.msp.org.orgmgmt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.orgmgmt.mapper.OrgMgmtMapper;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtPpsVo;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;



/**
 * @Class Name : OrgMgmtService
 * @Description : 조직 관리 서비스
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Service
public class OrgMgmtService extends BaseService {
	
	
    @Autowired
    private OrgMgmtMapper orgMgmtMapper;
    
    
    /** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
    
	public OrgMgmtService() {
		setLogPrefix("[OrgMgmtService] ");
	}
	
	/**
	 * @Description : 조직 리스트를 조회한다.
	 * @Param  : OrgMgmtVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
    public List<?> listOrgMgmt(OrgMgmtVO orgMgmtVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("listOrgMgmt START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> listOrgMgmts = new ArrayList<OrgMgmtVO>();
		
		listOrgMgmts = orgMgmtMapper.listOrgMgmt(orgMgmtVO);
		
        return listOrgMgmts;
    }
	
	/**
	* @Description : 조직 트리 리스트 
	* @Param  : 
	* @Return : List<?>
	* @Author : 고은정
	* @Create Date : 2014. 10. 13.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
    public List<?> treeListOrgMgmt(OrgMgmtVO orgMgmtVO){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("treeListOrgMgmt START."));
        logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        List listOrgMgmts = orgMgmtMapper.treeListOrgMgmt(orgMgmtVO);
        
        for(int i = 0; i < listOrgMgmts.size(); i++){
            EgovMap tempMap = (EgovMap)listOrgMgmts.get(i);
            String xmlkidsBo = (String) tempMap.get("xmlkidsBo");
            if("true".equals(xmlkidsBo)){
                tempMap.put("xmlkids", true);
            } else {
                tempMap.put("xmlkids", false);
            }
            
            listOrgMgmts.set(i, tempMap);
        }
       
        return listOrgMgmts;
    }

    /**
     * @Description : 조직상세 조회한다.
     * @Param  : 
     * @Return : OrgMgmtVO
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public OrgMgmtVO detailOrgMgmt(OrgMgmtVO orgMgmtVO){
        return orgMgmtMapper.detailOrgMgmt(orgMgmtVO);
    }    

    /**
     * @Description : 조직 리스트 건수를 조회한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public int listCntOrgMgmt(OrgMgmtVO orgMgmtVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("listCntOrgMgmt START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		int resultCnt = orgMgmtMapper.listCntOrgMgmt(orgMgmtVO);
		
		logger.debug(resultCnt);
		
        return resultCnt;
    }

    /**
     * @Description : 조직 정보를 업데이트한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	@Crypto(encryptName="DBMSEnc", fields = {"rprsenRrnum"}, throwable=true)
	@Transactional(rollbackFor=Exception.class)
	public int updateOrgMgmt(OrgMgmtVO orgMgmtVO) throws MvnoServiceException {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateOrgMgmt START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Date toDay = new Date();
		
		//조직 가동점 체크로직
		if(orgMgmtVO.getOrgnLvlCd().equals("10") && orgMgmtVO.getOrgnStatCd().equals("4"))
		{
			int valdCnt = 0;
			valdCnt = orgMgmtMapper.selectValCnt(orgMgmtVO);
			
			if(valdCnt == 0)
			{
				throw new MvnoServiceException("가상계좌를 등록하세요.");
			}
			
			int valdCnt1 = 0;
			valdCnt1 = orgMgmtMapper.selectValCnt1(orgMgmtVO);
			
			if(valdCnt1 == 0)
			{
				throw new MvnoServiceException("담보를 등록하세요.");
			}
		}
		
		int resultCnt = orgMgmtMapper.updateOrgMgmt(orgMgmtVO);
		
		//조직 정보가 정상적으로 변경이 되면 이력을 남긴다.
		if(resultCnt == 1)
		{
			int orgrMgmtHstcnt = orgMgmtMapper.listCntOrgMgmtHst(orgMgmtVO);
			
			if(orgrMgmtHstcnt == 0)
			{
				orgMgmtVO.setOrgnSeq("1");
			}
			else
			{
				String hstSeq = orgMgmtMapper.listSeqOrgMgmtHst(orgMgmtVO);
				orgMgmtVO.setOrgnSeq(hstSeq);
			}
			
			//이력 생성
			int resultCntHst = orgMgmtMapper.insertOrgMgmtHst(orgMgmtVO);
			
			logger.debug("resultCntHst = " + resultCntHst);
			
//			OrgMgmtVO returnOrgMgmtVO = orgMgmtMapper.detailOrgMgmt(orgMgmtVO);
			
			logger.debug("FAX 신: " + orgMgmtVO.getFax());
			logger.debug("FAX 구: " + orgMgmtVO.getOldFax());
			String newFax = (String) KtisUtil.changeNull(orgMgmtVO.getFax(), "");
			String oldFax = (String) KtisUtil.changeNull(orgMgmtVO.getOldFax(), "");
			
		}
		
		// 2014-12-19 추가
		// 대리점유형이력생성
		this.setAgncyTypeHst(orgMgmtVO);
		
        return resultCnt;
    }

	
    /**
     * @Description : 조직 정보를 업데이트한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Crypto(encryptName="DBMSEnc", fields = {"rprsenRrnum"}, throwable=true)
	@Transactional(rollbackFor=Exception.class)
	public int updateOrgMgmtMasking(OrgMgmtVO orgMgmtVO){

		int resultCnt = orgMgmtMapper.updateOrgMgmtMasking(orgMgmtVO);
        return resultCnt;
    }
	
	
    /**
     * @Description : 신규 조직을 생성한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="DBMSEnc", fields = {"rprsenRrnum"})
    public int insertOrgMgmt(OrgMgmtVO orgMgmtVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertOrgMgmt START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Date toDay = new Date();
		
		int resultCnt = 0;
		
		try{
			resultCnt = orgMgmtMapper.insertOrgMgmt(orgMgmtVO);
			
			//이력 생성
			orgMgmtVO.setOrgnSeq("1");
			
			int resultCntHst = orgMgmtMapper.insertOrgMgmtHst(orgMgmtVO);
			logger.debug("resultCntHst = " + resultCntHst);
			logger.debug("orgMgmtVO.getFax() = " + orgMgmtVO.getFax());
			
			
			// 2014-12-19 추가
			// 대리점유형이력생성
			this.setAgncyTypeHst(orgMgmtVO);
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
        return resultCnt;
    }

    /**
     * @Description : 조직을 삭제한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int deleteOrgMgmt(OrgMgmtVO orgMgmtVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("deleteOrgMgmt START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		int resultCnt = 0;
		int resultCntHst = 0;
		
		try {
			resultCntHst = orgMgmtMapper.deleteOrgMgmtHst(orgMgmtVO);
			
			if(resultCntHst != 0)
			{
				resultCnt = orgMgmtMapper.deleteOrgMgmt(orgMgmtVO);
				if(resultCnt == 1)
				{
					logger.debug("이력이 정상적으로 삭제되었습니다.");
				}
			}
		} catch (Exception e) {
			logger.error("조직 삭제 중 오류가 발생하였습니다.");
			throw new MvnoErrorException(e);
		}

        return resultCnt;
    }
    
    

    /**
     * @Description : 조직 이력을 조회한다.
     * @Param  : OrgMgmtVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public List<?> listOrgnHst(OrgMgmtVO orgMgmtVO){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("listOrgnHst START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> listOrgMgmts = new ArrayList<OrgMgmtVO>();
        
        try {
            listOrgMgmts = orgMgmtMapper.listOrgnHst(orgMgmtVO);
        } catch (NullPointerException e) {
            //logger.info(generateLogMsg(String.format("조직 이력 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
            throw new MvnoErrorException(e);
        }
        
        return listOrgMgmts;
    }
    
    
    /**
     * @Description : 조직 이력 건수를 조회한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public int listCntOrgMgmtHst(OrgMgmtVO orgMgmtVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("listCntOrgMgmtHst START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgMgmtMapper.listCntOrgMgmtHst(orgMgmtVO);
		
		logger.debug(resultCnt);
		return resultCnt;
		
    }

    /**
     * @Description : 조직 시퀀스번호를 가져온다
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public String detailOrgMgmtSeq(OrgMgmtVO orgMgmtVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("detailOrgMgmtSeq START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		String orgnId = orgMgmtMapper.detailOrgMgmtSeq(orgMgmtVO);
		
		logger.debug(orgnId);
		return orgnId;
		
    }
    
    /**
     * @Description : 조직이력을 생성한다.
     * @Param  : OrgMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertOrgMgmtHst(OrgMgmtVO orgMgmtVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertOrgMgmt START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgMgmtMapper.insertOrgMgmt(orgMgmtVO);
		
		logger.debug(resultCnt);
		
        return resultCnt;
    }
    
    /**
    * @Description : 조직레벨 공통코드조회 
    * @Param  : OrgMgmtVO
    * @Return : List<?>
    * @Author : 고은정
    * @Create Date : 2014. 9. 23.
     */
    public List<?> listCmnCdOrgnLvl(OrgMgmtVO orgMgmtVO){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("listCmnCdOrgnLvl START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> listCmnCd = new ArrayList<OrgMgmtVO>();
        
        try {
            listCmnCd = orgMgmtMapper.listCmnCdOrgnLvl(orgMgmtVO);
        } catch (NullPointerException e) {
            //logger.info(generateLogMsg(String.format("조직 레벨 공통코드 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
            throw new MvnoErrorException(e);
        }
        
        return listCmnCd;
    }
    
    /**
    * @Description : 조직유형 공통코드조회 
    * @Param  : OrgMgmtVO
    * @Return : List<?>
    * @Author : 고은정
    * @Create Date : 2014. 9. 24.
     */
    public List<?> listCmnCdOrgnType(OrgMgmtVO orgMgmtVO){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("listCmnCdOrgnLvl START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> listCmnCd = new ArrayList<OrgMgmtVO>();
        
        try {
            listCmnCd = orgMgmtMapper.listCmnCdOrgnType(orgMgmtVO);
        } catch (NullPointerException e) {
            //logger.info(generateLogMsg(String.format("조직 유형 공통코드 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
            throw new MvnoErrorException(e);
        }
        
        return listCmnCd;
    }
   
    /**
    * @Description : 조직ID 중복검색 
    * @Param  : 
    * @Return : int
    * @Author : 고은정
    * @Create Date : 2014. 9. 27.
     */
    public int isExistOrgnId(OrgMgmtVO orgMgmtVO){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("isExistOrgnId START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try {
            resultCnt = orgMgmtMapper.isExistOrgnId(orgMgmtVO);
        } catch (NullPointerException e) {
            //logger.info(generateLogMsg(String.format("조직ID 중복검색 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
            throw new MvnoErrorException(e);
        }
        
        return resultCnt;
    }
    
    
    /**
     * @Description : 조직 선불 정보 상세 
     * @Param  : 
     * @Return : List
     * @Author : 김웅
     * @Create Date : 2014. 9. 27.
      */
    public OrgMgmtPpsVo getPpsOrgnInfoDetail(Map<String, Object> pRequestParamMap){
		
		// 조직 선불 정보
		OrgMgmtPpsVo orgMgmtPpsVo = orgMgmtMapper.ppsOrgnInfoDetail(pRequestParamMap);
		if(orgMgmtPpsVo == null)
		{
			orgMgmtPpsVo = new OrgMgmtPpsVo();
		}

		return orgMgmtPpsVo;
	}
    
    
    /**
	 * @Description : 조직 선불 정보 등록/변경 
	 * @param Map<String, Object> pRequestParamMap
	 * @return Map
	 * @Author : 김웅
	 * @Create Date : 2014.10. 06.
	 */
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> ppsOrgnChgProc(Map<String, Object> pRequestParamMap){

    	Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("opCode",pRequestParamMap.get("opCode") );
		resultMap.put("agentId",pRequestParamMap.get("agentId") );
		resultMap.put("depositRate",pRequestParamMap.get("depositRate") );

		resultMap.put("dataRate",pRequestParamMap.get("dataRate") );
		resultMap.put("pinBuyRate",pRequestParamMap.get("pinBuyRate") );
		resultMap.put("rentalFee", pRequestParamMap.get("rentalFee") );
		resultMap.put("pps35DepositRate", pRequestParamMap.get("pps35DepositRate") );
		resultMap.put("ktAgencyId", pRequestParamMap.get("ktAgencyId") );
		resultMap.put("agentDocFlag", pRequestParamMap.get("agentDocFlag") );
		resultMap.put("adminId", pRequestParamMap.get("adminId"));

		resultMap.put("oRetCd","" );
		resultMap.put("oRetMsg","" );
    	
    	orgMgmtMapper.ppsOrgnChgProc(resultMap);
		//System.out.println(pRequestParamMap.get("oRetCd").toString());
		return resultMap;
	}
    
    /**
     * 대리점유형이력관리
     * @param pRequestParamMap
     */
    @Transactional(rollbackFor=Exception.class)
    public void setAgncyTypeHst(OrgMgmtVO vo){
    	logger.debug("대리점유형이력관리............." + vo);
    	
    	if(vo.getOrgnId() == null || "".equals(vo.getOrgnId())) return;
    	
    	// 대리점여부 체크
    	Map<String, Object> orgMap = orgMgmtMapper.getAgncyInfo(vo);
    	if(orgMap == null) return;
    	
    	// 대리점이력 생성
    	int i = orgMgmtMapper.updateAgncyTypeHst(orgMap);
    	if(i ==0){
    		// 기존 이력이 없으므로 변경일 기준으로 이력을 생성한다.
    		orgMgmtMapper.insertAgncyTypeHstFst(orgMap);
    	}
    	
    	// 9991231 이력 생성
    	orgMgmtMapper.insertAgncyTypeHst(orgMap);
    }
    
    public List<?> getAgncyOrgList(OrgMgmtVO vo){
    	
    	// 사용자 본래 조직ID 로 변경
    	String orgnId = orgMgmtMapper.getUsrOrgId(vo);
    	
    	if(orgnId != null && !"".equals(orgnId)) vo.setSessionUserOrgnId(orgnId);
    	
    	return orgMgmtMapper.listOrgMgmt(vo);
    }
    
    /**
     * @Description : 조직유형 대분류 조회한다
     * @Param  : MnfctMgmtVo
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    public List<?> selectOrgnType1(OrgMgmtVO orgMgmtVO){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("조직유형 대분류 조회 START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> orgMgmtVOs = new ArrayList<OrgMgmtVO>();
        
        orgMgmtVOs = orgMgmtMapper.selectOrgnType1(orgMgmtVO);
        
        return orgMgmtVOs;
    }
    
    /**
    * @Description :  조직유형을 조회한다
    * @Param  : 
    * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    public List<?> selectOrgnTypeDtl(String orgnType){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("조직유형을 조회 START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> resultList = new ArrayList<OrgMgmtVO>();

        resultList = orgMgmtMapper.selectOrgnTypeDtl(orgnType);
        
        return resultList;
    }
    
    
    /**
    * @Description :  조직유형을 조회한다
    * @Param  : 
    * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    public String selectOrgnTypeDtlCd(String orgnType){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("조직유형을 조회 START."));
        logger.info(generateLogMsg("================================================================="));
        
        String result = null;

        result = orgMgmtMapper.selectOrgnTypeDtlCd(orgnType);
        
        return result;
    }
    
    /**
    * @Description :  조직 하위 유형을 조회한다
    * @Param  : 
    * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    public String selectOrgnTypeChg(String orgnType){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("조직유형을 조회 START."));
        logger.info(generateLogMsg("================================================================="));
        
        String result = null;

        result = orgMgmtMapper.selectOrgnTypeChg(orgnType);
        
        return result;
    }
    
	/**
	 * 조직관리 신규
	 * 2018-02-19
	 */
	@Crypto(encryptName="DBMSEnc", fields = {"rprsenRrnum"}, throwable=true)
	@Transactional(rollbackFor=Exception.class)
	public int updateOrgMgmtNew(OrgMgmtVO orgMgmtVO) throws MvnoServiceException {
	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateOrgMgmtNew START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		//v2018.11 PMD 적용 소스 수정
		//Date toDay = new Date();
		
//		//조직 가동점 체크로직
//		if(orgMgmtVO.getOrgnLvlCd().equals("10") && orgMgmtVO.getOrgnStatCd().equals("4"))
//		{
//			int valdCnt = 0;
//			valdCnt = orgMgmtMapper.selectValCnt(orgMgmtVO);
//			
//			if(valdCnt == 0)
//			{
//				throw new MvnoServiceException("가상계좌를 등록하세요.");
//			}
//			
//			int valdCnt1 = 0;
//			valdCnt1 = orgMgmtMapper.selectValCnt1(orgMgmtVO);
//			
//			if(valdCnt1 == 0)
//			{
//				throw new MvnoServiceException("담보를 등록하세요.");
//			}
//		}
		
		int resultCnt = orgMgmtMapper.updateOrgMgmt(orgMgmtVO);
		
		//조직 정보가 정상적으로 변경이 되면 이력을 남긴다.
		if(resultCnt == 1)
		{
			int orgrMgmtHstcnt = orgMgmtMapper.listCntOrgMgmtHst(orgMgmtVO);
			
			if(orgrMgmtHstcnt == 0)
			{
				orgMgmtVO.setOrgnSeq("1");
			}
			else
			{
				String hstSeq = orgMgmtMapper.listSeqOrgMgmtHst(orgMgmtVO);
				orgMgmtVO.setOrgnSeq(hstSeq);
			}
			
			//이력 생성
			int resultCntHst = orgMgmtMapper.insertOrgMgmtHst(orgMgmtVO);
			
			logger.debug("resultCntHst = " + resultCntHst);
			
//			logger.debug("FAX 신: " + orgMgmtVO.getFax());
//			logger.debug("FAX 구: " + orgMgmtVO.getOldFax());
//			String newFax = (String) KtisUtil.changeNull(orgMgmtVO.getFax(), "");
//			String oldFax = (String) KtisUtil.changeNull(orgMgmtVO.getOldFax(), "");
//			
//			if(KtisUtil.notEquals(newFax, oldFax))
//			{
//				logger.debug("orgMgmtVO.getFax( = " + orgMgmtVO.getFax());
//				//FAX 정보 생성
//				FaxHstVO faxMgmtVo = new FaxHstVO();
//				
//				faxMgmtVo.setFax(orgMgmtVO.getFax());
//				faxMgmtVo.setOrgnId(orgMgmtVO.getOrgnId());
//				faxMgmtVo.setRegId(orgMgmtVO.getRegId());
//				faxMgmtVo.setRvisnId(orgMgmtVO.getRvisnId());
//				faxMgmtVo.setApplCmpltDttm("99991231");
//				faxMgmtVo.setApplStrtDttm(KtisUtil.toStr(toDay, KtisUtil.DATE_SHORT));
//				
//				int resultFaxCnt = faxMgmtService.insertFaxMgmt(faxMgmtVo);
//				logger.debug("resultFaxCnt = " + resultFaxCnt);
//			}
			
		}
		
		// 2014-12-19 추가
		// 대리점유형이력생성
		this.setAgncyTypeHst(orgMgmtVO);
		
		return resultCnt;
	}
	
    /**
     * @Description : 조직 변경이력을 조회한다.
     * @Param  : OrgMgmtVO
     * @Return : List<?>
     * @Author : 김민지
     * @Create Date : 2019. 2. 28.
     */
    public List<?> listOrgChgHst(OrgMgmtVO orgMgmtVO, Map<String, Object> paramMap){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("listOrgnChgHst START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> listOrgChbHst = new ArrayList<OrgMgmtVO>();
        
        try {
        	listOrgChbHst = orgMgmtMapper.listOrgChgHst(orgMgmtVO);
        	
    		paramMap.put("SESSION_USER_ID", orgMgmtVO.getSessionUserId());

    		maskingService.setMask(listOrgChbHst, maskingService.getMaskFields(), paramMap);    		
        } catch (NullPointerException e) {
            throw new MvnoErrorException(e);
        }
        
        return listOrgChbHst;
    }
    
    /**
     * @Description : 조직 변경이력 엑셀다운
     * @Param  : OrgMgmtVO
     * @Return : List<?>
     * @Author : 김민지
     * @Create Date : 2019. 2. 28.
     */
    
	public List<?> listOrgChgHstExcel(OrgMgmtVO orgMgmtVO, Map<String, Object> paramMap){
		
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("listOrgnChgHstExcel START."));
        logger.info(generateLogMsg("================================================================="));
       
        List<?> listOrgChbHstExcel = new ArrayList<OrgMgmtVO>();

        try {
        	listOrgChbHstExcel = orgMgmtMapper.listOrgChgHstExcel(orgMgmtVO);
        	
    		paramMap.put("SESSION_USER_ID", orgMgmtVO.getSessionUserId());
    		
    		maskingService.setMask(listOrgChbHstExcel, maskingService.getMaskFields(), paramMap);
        } catch (NullPointerException e) {
            throw new MvnoErrorException(e);
        }
		
		return listOrgChbHstExcel;

	}
}
