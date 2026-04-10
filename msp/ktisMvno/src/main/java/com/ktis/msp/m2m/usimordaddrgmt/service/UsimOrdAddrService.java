package com.ktis.msp.m2m.usimordaddrgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.m2m.usimordaddrgmt.mapper.UsimOrdAddrMapper;
import com.ktis.msp.m2m.usimordaddrgmt.vo.UsimOrdAddrVO;



@Service
public class UsimOrdAddrService extends BaseService {

	@Autowired
	private UsimOrdAddrMapper usimOrdAddrMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public List<?> usimOrdAddrList(UsimOrdAddrVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdAddrList = new ArrayList<UsimOrdAddrVO>();
		usimOrdAddrList = usimOrdAddrMapper.usimOrdAddrList(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimOrdAddrList, maskingService.getMaskFields(), paramMap);

		return usimOrdAddrList;
	}

	public void usimOrdAddrInsert(UsimOrdAddrVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrdAddrMapper.usimOrdAddrInsert(searchVO);

	}

	public void usimOrdAddrUpdate(UsimOrdAddrVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrdAddrMapper.usimOrdAddrUpdate(searchVO);

	}

	public List<?> usimOrdAddrListEx(UsimOrdAddrVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrdAddrListEx = new ArrayList<UsimOrdAddrVO>();
		usimOrdAddrListEx = usimOrdAddrMapper.usimOrdAddrListEx(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(usimOrdAddrListEx, maskingService.getMaskFields(), paramMap);

		return usimOrdAddrListEx;
	}
	

	public List<?> getOrgIdComboList(UsimOrdAddrVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getOrgIdComboList = new ArrayList<UsimOrdAddrVO>();
		getOrgIdComboList = usimOrdAddrMapper.getOrgIdComboList(searchVO);

		return getOrgIdComboList;
	}

	public void usimOrdAddrDelete(UsimOrdAddrVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrdAddrMapper.usimOrdAddrDelete(searchVO);

	}	
	
}

