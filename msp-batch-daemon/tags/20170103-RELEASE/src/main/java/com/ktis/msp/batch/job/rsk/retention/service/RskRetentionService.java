package com.ktis.msp.batch.job.rsk.retention.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rsk.retention.mapper.RskRetentionMapper;

@Service
public class RskRetentionService extends BaseService {
	
	@Autowired
	private RskRetentionMapper rskRetentionMapper;
	
	/**
	 * 해지후재가입정보생성
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setRetentionProc() throws MvnoServiceException{
		
		try {
			
			rskRetentionMapper.callRetentionProc();
				
		} catch(Exception e) {
			e.printStackTrace();
			throw new MvnoServiceException(e.getMessage());
		}
		
	}
}
