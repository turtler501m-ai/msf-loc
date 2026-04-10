package com.ktis.msp.m2m.usimrephistgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.m2m.usimrephistgmt.mapper.UsimRepHistMapper;
import com.ktis.msp.m2m.usimrephistgmt.vo.UsimRepHistVO;



@Service
public class UsimRepHistService extends BaseService {

	@Autowired
	private UsimRepHistMapper usimRepHistMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public List<?> usimRepHistList(UsimRepHistVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimRepHistList = new ArrayList<UsimRepHistVO>();
		usimRepHistList = usimRepHistMapper.usimRepHistList(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimRepHistList, maskFields, paramMap);

		return usimRepHistList;
	}

	public void usimRepHistInsert(UsimRepHistVO searchVO, Map<String, Object> pRequestParamMap){

		usimRepHistMapper.usimRepHistInsert(searchVO);

	}

	public List<?> usimRepHistListEx(UsimRepHistVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimRepHistListEx = new ArrayList<UsimRepHistVO>();
		usimRepHistListEx = usimRepHistMapper.usimRepHistListEx(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimRepHistListEx, maskFields, paramMap);

		return usimRepHistListEx;
	}
	

	public List<?> getOrgIdComboList(UsimRepHistVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getOrgIdComboList = new ArrayList<UsimRepHistVO>();
		getOrgIdComboList = usimRepHistMapper.getOrgIdComboList(searchVO);

		return getOrgIdComboList;
	}

	public List<?> getMdlIdComboList(UsimRepHistVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getMdlIdComboList = new ArrayList<UsimRepHistVO>();
		getMdlIdComboList = usimRepHistMapper.getMdlIdComboList(searchVO);

		return getMdlIdComboList;
	}

	public List<?> getAreaNmComboList(UsimRepHistVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getAreaNmComboList = new ArrayList<UsimRepHistVO>();
		getAreaNmComboList = usimRepHistMapper.getAreaNmComboList(searchVO);

		return getAreaNmComboList;
	}

	/**
	 * 마스킹필드 셋팅(고객명,전화번호,유선전화번호,핸드폰전화번호,상세주소)
	 * @return
	 */
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("maskMngNm", "CUST_NAME");
		maskFields.put("mngPhn", "TEL_NO");
		maskFields.put("mngMblphn", "MOBILE_PHO");
		maskFields.put("maskAddrDtl", "ADDR");

		maskFields.put("reportUsrNm", "CUST_NAME"); //출력자
		maskFields.put("usrNm", "CUST_NAME"); //등록자
		
		return maskFields;
	}
	 
	
}

