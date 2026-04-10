package com.ktis.msp.cmn.favrmenu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.favrmenu.mapper.FavrMenuMapper;

@Service
public class FavrMenuService extends BaseService {
	
	@Autowired
	private FavrMenuMapper  favrMenuMapper;
	
	public FavrMenuService() {
		setLogPrefix("[CodeFindService] ");
	}
	
	public List<?> favrMenuList(Map<String, Object> param){
		return favrMenuMapper.favrMenuList(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int favrMenuInsert(Map<String, Object> param){
		return favrMenuMapper.favrMenuInsert(param);
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public int favrMenuDelete(Map<String, Object> param){
		return favrMenuMapper.favrMenuDelete(param);
	}
	
	
	
	
}
