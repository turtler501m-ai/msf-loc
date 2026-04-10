package com.ktis.msp.cmn.filedownsrch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.filedownsrch.mapper.FileDownSrchMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class FileDownSrchService extends BaseService {
	
	@Autowired
	private FileDownSrchMapper fileDownSrchMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public FileDownSrchService() {
		setLogPrefix("[FileDownSrchService] ");
	}
	
	public List<?> selectList(Map<String, Object> param){
		
		List<EgovMap> result = (List<EgovMap>) fileDownSrchMapper.selectList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	
	
}
