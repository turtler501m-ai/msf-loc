package com.ktis.msp.rcp.prepaidMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.prepaidMgmt.mapper.PrepaidMgmtMapper;
import com.ktis.msp.rcp.prepaidMgmt.vo.PrepaidVo;

import egovframework.rte.psl.dataaccess.util.EgovMap;



@Service
public class PrepaidMgmtService extends BaseService{
	
	@Autowired
	private PrepaidMgmtMapper prepaidMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getList(PrepaidVo pVo, Map<String, Object> paramMap){
		
		List<EgovMap> list = (List<EgovMap>) prepaidMgmtMapper.getList(pVo);
		
		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);
		
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getlistExcel(PrepaidVo pVo, Map<String, Object> paramMap){
		List<EgovMap> list = (List<EgovMap>) prepaidMgmtMapper.getlistExcel(pVo);
				
		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);
		
		return list;

	}
	
	/**
	 * 마스킹 처리 
	 */
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subscriberNo",	"MOBILE_PHO");
		return maskFields;
	}
}
