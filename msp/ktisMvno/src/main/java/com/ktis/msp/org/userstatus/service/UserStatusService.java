package com.ktis.msp.org.userstatus.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.userstatus.mapper.UserStatusMapper;

@Service
public class UserStatusService extends BaseService {
	
	@Autowired
	private UserStatusMapper userStatusMapper;
	
	public Map<String, Object> callUserStopStatusProc(Map<String, Object> param) {
		try {
			userStatusMapper.callUserStopStatusProc();
		} catch (Exception e) {
			logger.error("사용자30일정지 오류===" + e);
		}
		return param;
	}
}