package com.ktis.msp.m2m.usimordgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.m2m.usimordgmt.mapper.UsimOrdMngMapper;
import com.ktis.msp.m2m.usimordgmt.vo.UsimOrdMngVO;



@Service
public class UsimOrdMngService extends BaseService {

	@Autowired
	private UsimOrdMngMapper usimOrdMngMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public List<?> usimOrdMngList(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdMngList = new ArrayList<UsimOrdMngVO>();
		usimOrdMngList = usimOrdMngMapper.usimOrdMngList(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimOrdMngList, maskingService.getMaskFields(), paramMap);

		return usimOrdMngList;
	}

	public String usimOrdId(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		String usimOrdId = "";
		usimOrdId = usimOrdMngMapper.usimOrdId(searchVO);

		return usimOrdId;
	}

	public void usimOrdMstInsert(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrdMngMapper.usimOrdMstInsert(searchVO);

	}

	public void usimOrdMstUpdate(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrdMngMapper.usimOrdMstUpdate(searchVO);

	}

	public void usimOrdMstUpdateMsk(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrdMngMapper.usimOrdMstUpdateMsk(searchVO);

	}

	public List<?> usimOrdMdlList(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdMdlList = new ArrayList<UsimOrdMngVO>();
		usimOrdMdlList = usimOrdMngMapper.usimOrdMdlList(searchVO);

		return usimOrdMdlList;
	}

	public void usimOrdMdlInsert(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrdMngMapper.usimOrdMdlInsert(searchVO);

	}

	public void usimOrdMdlUpdate(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrdMngMapper.usimOrdMdlUpdate(searchVO);

	}

	public List<?> usimOrdMngListEx(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdMngListEx = new ArrayList<UsimOrdMngVO>();
		usimOrdMngListEx = usimOrdMngMapper.usimOrdMngListEx(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimOrdMngListEx, maskingService.getMaskFields(), paramMap);

		return usimOrdMngListEx;
	}

	public List<?> usimOrdMngListEx2(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdMngListEx2 = new ArrayList<UsimOrdMngVO>();
		usimOrdMngListEx2 = usimOrdMngMapper.usimOrdMngListEx2(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimOrdMngListEx2, maskingService.getMaskFields(), paramMap);

		return usimOrdMngListEx2;
	}
	

	public List<?> getOrgIdComboList(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getOrgIdComboList = new ArrayList<UsimOrdMngVO>();
		getOrgIdComboList = usimOrdMngMapper.getOrgIdComboList(searchVO);

		return getOrgIdComboList;
	}

	public List<?> getMdlIdComboList(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getMdlIdComboList = new ArrayList<UsimOrdMngVO>();
		getMdlIdComboList = usimOrdMngMapper.getMdlIdComboList(searchVO);

		return getMdlIdComboList;
	}

	public List<?> getAreaNmComboList(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getAreaNmComboList = new ArrayList<UsimOrdMngVO>();
		getAreaNmComboList = usimOrdMngMapper.getAreaNmComboList(searchVO);

		return getAreaNmComboList;
	}

	public List<?> getM2mAddr(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getM2mAddr = new ArrayList<UsimOrdMngVO>();
		getM2mAddr = usimOrdMngMapper.getM2mAddr(searchVO);

		return getM2mAddr;
	}

	public List<?> getM2mMdl(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getM2mMdl = new ArrayList<UsimOrdMngVO>();
		getM2mMdl = usimOrdMngMapper.getM2mMdl(searchVO);

		return getM2mMdl;
	}

	public List<?> getDlvMethodCombo(){

		List<?> getDlvMethodCombo = new ArrayList<UsimOrdMngVO>();
		getDlvMethodCombo = usimOrdMngMapper.getDlvMethodCombo();

		return getDlvMethodCombo;
	}

	public List<?> getM2mMngHTel(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getM2mMngHTel = new ArrayList<UsimOrdMngVO>();
		getM2mMngHTel = usimOrdMngMapper.getM2mMngHTel(searchVO);

		return getM2mMngHTel;
	}

    @Transactional(rollbackFor=Exception.class)
    public void insertMsgQueue(KtMsgQueueReqVO vo){
    	smsMgmtMapper.insertKtMsgQueue(vo);
    }

	public List<?> getTbNm(UsimOrdMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getTbNm = new ArrayList<UsimOrdMngVO>();
		getTbNm = usimOrdMngMapper.getTbNm(searchVO);

		return getTbNm;
	} 
}

