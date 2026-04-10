package com.ktis.msp.secu.securityMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.secu.securityMgmt.mapper.SecurityMapper;
import com.ktis.msp.secu.securityMgmt.vo.SecurityMgmtVo;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class SecurityService extends BaseService{

	@Autowired
	private SecurityMapper securityMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public List<?> accessFailLogList(Map<String, Object> param){
		List<EgovMap> result = (List<EgovMap>) securityMapper.accessFailLogList(param); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	public List<SecurityMgmtVo> accessFailLogListExcel(Map<String, Object> param){
	    List<SecurityMgmtVo> result = securityMapper.accessFailLogListExcel(param);
		
		maskingService.setMask(result, maskingService.getMaskFields(), param);
		
		return result;
	}
	
	public List<?> accessFailFileDownList(Map<String, Object> param){
		List<EgovMap> result = (List<EgovMap>) securityMapper.accessFailFileDownList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
    }
	public List<SecurityMgmtVo> accessFailFileDownListExcel(Map<String, Object> param){
        List<SecurityMgmtVo> result = securityMapper.accessFailFileDownListExcel(param); 
		
		maskingService.setMask(result, maskingService.getMaskFields(), param);
		
		return result;
    }
	
	public List<?> stopUserList(Map<String, Object> param){
		List<EgovMap> result = (List<EgovMap>) securityMapper.stopUserList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
    }
	public List<SecurityMgmtVo> stopUserListExcel(Map<String, Object> param){
	    List<SecurityMgmtVo> result = securityMapper.stopUserListExcel(param);
		
		maskingService.setMask(result, maskingService.getMaskFields(), param);
		
		return result;
	}
	
	public List<?> fileDownLogList(Map<String, Object> param){
		List<EgovMap> result = (List<EgovMap>) securityMapper.fileDownLogList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	public List<SecurityMgmtVo> fileDownLogListExcel(Map<String, Object> param){
	    List<SecurityMgmtVo> result = securityMapper.fileDownLogListExcel(param);
		
		maskingService.setMask(result, maskingService.getMaskFields(), param);
		
		return result;
	}
}
