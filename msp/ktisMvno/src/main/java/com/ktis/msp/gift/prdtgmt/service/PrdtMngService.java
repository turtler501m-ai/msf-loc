package com.ktis.msp.gift.prdtgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.gift.prdtgmt.mapper.PrdtMngMapper;
import com.ktis.msp.gift.prdtgmt.vo.PrdtMngVO;;

@Service
public class PrdtMngService extends BaseService {

	@Autowired
	private PrdtMngMapper prdtMngMapper;
	
	@Autowired
	private MaskingService maskingService;

	public List<?> prdtMngList(PrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PrdtMngVO>();
		result = prdtMngMapper.prdtMngList(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public List<?> prdtMngHist(PrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PrdtMngVO>();
		result = prdtMngMapper.prdtMngHist(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public void prdtInsert(PrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		prdtMngMapper.prdtInsert(searchVO);

	}

	public void prdtUpdate(PrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		prdtMngMapper.prdtHistInsert(searchVO);

		prdtMngMapper.prdtUpdate(searchVO);


	}

	public List<?> prdtMngListEx(PrdtMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PrdtMngVO>();
		result = prdtMngMapper.prdtMngListEx(searchVO);
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public List<?> getImageFile(PrdtMngVO searchVO){
		return prdtMngMapper.getImageFile(searchVO);
	}
	
	
	
	

}

