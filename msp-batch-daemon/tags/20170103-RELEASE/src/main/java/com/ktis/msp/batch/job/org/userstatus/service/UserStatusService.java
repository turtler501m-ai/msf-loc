package com.ktis.msp.batch.job.org.userstatus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.userstatus.mapper.UserStatusMapper;

/**
 * @author TREXSHIN
 *
 */
@Service
public class UserStatusService extends BaseService {
	
	/**
	 * 
	 */
	@Autowired
	private UserStatusMapper userStatusMapper;
	
	/**
	 * @throws MvnoServiceException
	 */
	public void callUserStopStatusProc() throws MvnoServiceException {
		try {
			userStatusMapper.callUserStopStatusProc();
		} catch (Exception e) {
			throw new MvnoServiceException("EORG1004", e);
		}
	}
}