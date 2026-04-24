package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.RcpMgmtMapper;

@Service
public class RcpMgmtService extends BaseService {
	
	@Autowired
	private RcpMgmtMapper rcpMapper;
	
	/**
	 * 기변대상생성
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setDvcChgTrgtMst() throws MvnoServiceException{
		
		try {
				
			rcpMapper.callDvcChgTrgtProc();
				
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1042", e);
		}
		
	}
	
}
