package com.ktis.msp.cmn.menuaccesssrch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.menuaccesssrch.mapper.MenuAccessSrchMapper;

@Service
public class MenuAccessSrchService extends BaseService {
	
	@Autowired
	private MenuAccessSrchMapper  menuAccessSrchMapper;	
	
	@Autowired
	private MaskingService maskingService;
	
	public MenuAccessSrchService() {
		setLogPrefix("[MenuAccessSrchService] ");
	}
	
	public List<?> selectList(Map<String, Object> param){

		List<?> result = menuAccessSrchMapper.selectList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	
	
}
