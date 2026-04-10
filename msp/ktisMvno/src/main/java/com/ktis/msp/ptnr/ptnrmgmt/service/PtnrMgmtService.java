package com.ktis.msp.ptnr.ptnrmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.ptnr.ptnrmgmt.mapper.PtnrMgmtMapper;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInfoVO;

@Service
public class PtnrMgmtService extends BaseService {

	@Autowired
	private PtnrMgmtMapper ptnrMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;

	public List<?> ptnrMgmtList(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PtnrInfoVO>();
		result = ptnrMgmtMapper.ptnrMgmtList(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("managerNm",		"CUST_NAME");
		maskFields.put("contactNum",	"MOBILE_PHO");
		
		maskingService.setMask(result, maskFields, pRequestParamMap);
		
		return result;
	}

	public List<?> ptnrMgmtLinkList(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> ptnrMgmtLinkList = new ArrayList<PtnrInfoVO>();
		ptnrMgmtLinkList = ptnrMgmtMapper.ptnrMgmtLinkList(searchVO);

		return ptnrMgmtLinkList;
	}

	public void ptnrMgmtInsert(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		ptnrMgmtMapper.ptnrMgmtInsert(searchVO);

	}

	public void ptnrMgmtUpdate(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		ptnrMgmtMapper.ptnrMgmtUpdate(searchVO);

	}

	public void ptnrMgmtLinkInsert(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		ptnrMgmtMapper.ptnrMgmtLinkInsert(searchVO);

	}

	public List<?> ptnrRateList(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PtnrInfoVO>();
		result = ptnrMgmtMapper.ptnrRateList(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		
		return result;
	}

	public List<?> ptnrRateHist(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> result = new ArrayList<PtnrInfoVO>();
		result = ptnrMgmtMapper.ptnrRateHist(searchVO);
        
		pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);

		return result;
	}

	public void ptnrRateInsert(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		ptnrMgmtMapper.ptnrRateInsert(searchVO);

	}

	public void ptnrRateUpdate(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		int ptnrRateUpdate = ptnrMgmtMapper.ptnrRateUpdate(searchVO);

		if(ptnrRateUpdate > 0){
			ptnrMgmtMapper.ptnrRateHistInsert(searchVO);
		}

	}

	public List<?> ptnrRateInfo(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> ptnrRateInfo = new ArrayList<PtnrInfoVO>();
		ptnrRateInfo = ptnrMgmtMapper.ptnrRateInfo(searchVO);

		return ptnrRateInfo;
	}

	public List<?> ptnrRateListEx(PtnrInfoVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> ptnrRateListEx = new ArrayList<PtnrInfoVO>();
		ptnrRateListEx = ptnrMgmtMapper.ptnrRateListEx(searchVO);
		
		maskingService.setMask(ptnrRateListEx, maskingService.getMaskFields(), pRequestParamMap);

		return ptnrRateListEx;
	}
}

