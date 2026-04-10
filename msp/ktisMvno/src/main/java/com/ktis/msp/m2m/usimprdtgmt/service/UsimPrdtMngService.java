package com.ktis.msp.m2m.usimprdtgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.m2m.usimprdtgmt.mapper.UsimPrdtMngMapper;

import com.ktis.msp.m2m.usimprdtgmt.vo.UsimPrdtMngVO;

@Service
public class UsimPrdtMngService extends BaseService {

	@Autowired
	private UsimPrdtMngMapper usimPrdtMngMapper;
	
	@Autowired
	private MaskingService maskingService;

	public List<?> usimPrdtMngList(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<UsimPrdtMngVO>();
		result = usimPrdtMngMapper.usimPrdtMngList(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public List<?> usimPrdtMngHist(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<UsimPrdtMngVO>();
		result = usimPrdtMngMapper.usimPrdtMngHist(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public void usimPrdtInsert(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimPrdtMngMapper.usimPrdtInsert(searchVO);

	}

	public void usimPrdtUpdate(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		usimPrdtMngMapper.usimPrdtHistInsert(searchVO);

		usimPrdtMngMapper.usimPrdtUpdate(searchVO);


	}

	public List<?> usimPrdtMngListEx(UsimPrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<UsimPrdtMngVO>();
		result = usimPrdtMngMapper.usimPrdtMngListEx(searchVO);
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public List<?> getImageFile(UsimPrdtMngVO searchVO){
		return usimPrdtMngMapper.getImageFile(searchVO);
	}
	
	
	
	

}

