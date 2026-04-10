package com.ktis.msp.m2m.usimprdtgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.m2m.usimprdtgmt.mapper.UsimOrgnPrdtRelMapper;

import com.ktis.msp.m2m.usimprdtgmt.vo.UsimPrdtMngVO;

@Service
public class UsimOrgnPrdtRelService extends BaseService {

	@Autowired
	private UsimOrgnPrdtRelMapper usimOrgnPrdtRelMapper;
	
	@Autowired
	private MaskingService maskingService;

	public List<?> usimOrgnList(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> usimOrgnList = new ArrayList<UsimPrdtMngVO>();
		usimOrgnList = usimOrgnPrdtRelMapper.usimOrgnList(searchVO);

		return usimOrgnList;
	}

	public List<?> usimPrdtRelList(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<UsimPrdtMngVO>();
		result = usimOrgnPrdtRelMapper.usimPrdtRelList(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public void usimPrdtRelInsert(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrgnPrdtRelMapper.usimPrdtRelInsert(searchVO);

	}

	public void usimPrdtRelDelete(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimOrgnPrdtRelMapper.usimPrdtRelDelete(searchVO);

	}

}

