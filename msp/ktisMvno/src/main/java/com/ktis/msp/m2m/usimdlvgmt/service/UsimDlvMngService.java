package com.ktis.msp.m2m.usimdlvgmt.service;

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
import com.ktis.msp.m2m.usimdlvgmt.mapper.UsimDlvMngMapper;
import com.ktis.msp.m2m.usimdlvgmt.vo.UsimDlvMngVO;



@Service
public class UsimDlvMngService extends BaseService {

	@Autowired
	private UsimDlvMngMapper usimDlvMngMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public List<?> usimDlvMngList(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdMngList = new ArrayList<UsimDlvMngVO>();
		usimOrdMngList = usimDlvMngMapper.usimDlvMngList(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimOrdMngList, maskingService.getMaskFields(), paramMap);

		return usimOrdMngList;
	}

	public String usimOrdId(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		String usimOrdId = "";
		usimOrdId = usimDlvMngMapper.usimOrdId(searchVO);

		return usimOrdId;
	}

	public void usimDlvMstInsert(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimDlvMngMapper.usimDlvMstInsert(searchVO);

	}

	public void usimDlvMstUpdate(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimDlvMngMapper.usimDlvMstUpdate(searchVO);

	}

	public void usimDlvMstUpdateMsk(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimDlvMngMapper.usimDlvMstUpdateMsk(searchVO);

	}

	public List<?> usimOrdMdlList(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdMdlList = new ArrayList<UsimDlvMngVO>();
		usimOrdMdlList = usimDlvMngMapper.usimOrdMdlList(searchVO);

		return usimOrdMdlList;
	}

	public void usimOrdMdlInsert(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimDlvMngMapper.usimOrdMdlInsert(searchVO);

	}

	public void usimOrdMdlUpdate(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimDlvMngMapper.usimOrdMdlUpdate(searchVO);

	}

	public List<?> usimDlvMngListEx(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdMngListEx = new ArrayList<UsimDlvMngVO>();
		usimOrdMngListEx = usimDlvMngMapper.usimDlvMngListEx(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimOrdMngListEx, maskingService.getMaskFields(), paramMap);

		return usimOrdMngListEx;
	}
	

	public List<?> getOrgIdComboList(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getOrgIdComboList = new ArrayList<UsimDlvMngVO>();
		getOrgIdComboList = usimDlvMngMapper.getOrgIdComboList(searchVO);

		return getOrgIdComboList;
	}

	public List<?> getMdlIdComboList(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getMdlIdComboList = new ArrayList<UsimDlvMngVO>();
		getMdlIdComboList = usimDlvMngMapper.getMdlIdComboList(searchVO);

		return getMdlIdComboList;
	}

	public List<?> getAreaNmComboList(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getAreaNmComboList = new ArrayList<UsimDlvMngVO>();
		getAreaNmComboList = usimDlvMngMapper.getAreaNmComboList(searchVO);

		return getAreaNmComboList;
	}

	public List<?> getM2mAddr(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getM2mAddr = new ArrayList<UsimDlvMngVO>();
		getM2mAddr = usimDlvMngMapper.getM2mAddr(searchVO);

		return getM2mAddr;
	}

	public List<?> getM2mMdl(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getM2mMdl = new ArrayList<UsimDlvMngVO>();
		getM2mMdl = usimDlvMngMapper.getM2mMdl(searchVO);

		return getM2mMdl;
	}

	public List<?> getDlvMethodCombo(){

		List<?> getDlvMethodCombo = new ArrayList<UsimDlvMngVO>();
		getDlvMethodCombo = usimDlvMngMapper.getDlvMethodCombo();

		return getDlvMethodCombo;
	}

	public List<?> getM2mMngHTel(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getM2mMngHTel = new ArrayList<UsimDlvMngVO>();
		getM2mMngHTel = usimDlvMngMapper.getM2mMngHTel(searchVO);

		return getM2mMngHTel;
	}

    @Transactional(rollbackFor=Exception.class)
    public void insertMsgQueue(KtMsgQueueReqVO vo){
    	smsMgmtMapper.insertKtMsgQueue(vo);
    }

	public List<?> getTbNm(UsimDlvMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getTbNm = new ArrayList<UsimDlvMngVO>();
		getTbNm = usimDlvMngMapper.getTbNm(searchVO);

		return getTbNm;
	}
	 
}

