package com.ktis.msp.cmn.codefind.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.codefind.mapper.CodeFindMapper;

@Service
public class CodeFindService extends BaseService {
	
	@Autowired
	private CodeFindMapper  codeFindMapper;
	
	public CodeFindService() {
		setLogPrefix("[CodeFindService] ");
	}
	
	public List<?> cmnMenuMst(Map<String, Object> param){
		return codeFindMapper.cmnMenuMst(param);
	}
	
	
	
}
