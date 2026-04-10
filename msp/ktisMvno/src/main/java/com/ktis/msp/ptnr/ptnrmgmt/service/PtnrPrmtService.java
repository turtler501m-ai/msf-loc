package com.ktis.msp.ptnr.ptnrmgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.ptnr.ptnrmgmt.mapper.PtnrPrmtMapper;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInfoVO;

@Service
public class PtnrPrmtService extends BaseService {

	@Autowired
	private PtnrPrmtMapper ptnrPrmtMapper;
	
	@Autowired
	private MaskingService maskingService;

	public List<?> ptnrPrmtList(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PtnrInfoVO>();
		result = ptnrPrmtMapper.ptnrPrmtList(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public void ptnrPrmtInsert(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		ptnrPrmtMapper.ptnrPrmtInsert(searchVO);

	}

	public void ptnrPrmtUpdate(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		int ptnrPrmtUpdate = ptnrPrmtMapper.ptnrPrmtUpdate(searchVO);

	}

	public List<?> ptnrPrmtListEx(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PtnrInfoVO>();
		result = ptnrPrmtMapper.ptnrPrmtListEx(searchVO);
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}
}

