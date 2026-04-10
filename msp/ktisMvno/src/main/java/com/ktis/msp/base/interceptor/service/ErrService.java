package com.ktis.msp.base.interceptor.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.interceptor.mapper.ErrMapper;
import com.ktis.msp.base.mvc.BaseService;

@Service
public class ErrService extends BaseService {
	@Autowired
	private ErrMapper errMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public int insertLog(Map<String, Object> pReqParamMap) {
		return errMapper.insertLog(pReqParamMap);
	}
}
