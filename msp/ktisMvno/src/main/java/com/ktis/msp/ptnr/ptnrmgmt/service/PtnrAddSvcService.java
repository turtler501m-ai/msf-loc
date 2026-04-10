package com.ktis.msp.ptnr.ptnrmgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.ptnr.ptnrmgmt.mapper.PtnrAddSvcMapper;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInfoVO;

@Service
public class PtnrAddSvcService extends BaseService {

	@Autowired
	private PtnrAddSvcMapper ptnrAddSvcMapper;
	
	@Autowired
	private MaskingService maskingService;

	public List<?> ptnrAddSvcList(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PtnrInfoVO>();
		result = ptnrAddSvcMapper.ptnrAddSvcList(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public List<?> ptnrAddSvcHist(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PtnrInfoVO>();
		result = ptnrAddSvcMapper.ptnrAddSvcHist(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public List<?> ptnrRateAddSvcComboList(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> ptnrRateAddSvcComboList = new ArrayList<PtnrInfoVO>();
		ptnrRateAddSvcComboList = ptnrAddSvcMapper.ptnrRateAddSvcComboList(searchVO);

		return ptnrRateAddSvcComboList;
	}

	public void ptnrAddSvcInsert(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		ptnrAddSvcMapper.ptnrAddSvcInsert(searchVO);

	}

	public void ptnrAddSvcUpdate(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		ptnrAddSvcMapper.ptnrAddSvcHistInsert(searchVO);

		int ptnrAddSvcUpdate = ptnrAddSvcMapper.ptnrAddSvcUpdate(searchVO);


	}

	public List<?> getRateComboList(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> rateComboList = new ArrayList<PtnrInfoVO>();
		rateComboList = ptnrAddSvcMapper.getRateComboList(searchVO);

		return rateComboList;
	}

	public List<?> ptnrAddSvcInfo(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> ptnrAddSvcInfo = new ArrayList<PtnrInfoVO>();
		ptnrAddSvcInfo = ptnrAddSvcMapper.ptnrAddSvcInfo(searchVO);

		return ptnrAddSvcInfo;
	}

	public List<?> ptnrAddSvcListEx(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> ptnrAddSvcListEx = new ArrayList<PtnrInfoVO>();
		ptnrAddSvcListEx = ptnrAddSvcMapper.ptnrAddSvcListEx(searchVO);
		
		maskingService.setMask(ptnrAddSvcListEx, maskingService.getMaskFields(), pRequestParamMap);

		return ptnrAddSvcListEx;
	}
}

