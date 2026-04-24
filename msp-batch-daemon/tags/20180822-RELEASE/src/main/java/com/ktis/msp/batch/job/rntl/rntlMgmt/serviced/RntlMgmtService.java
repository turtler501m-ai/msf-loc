package com.ktis.msp.batch.job.rntl.rntlMgmt.serviced;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rntl.rntlMgmt.mapper.RntlMgmtMapper;


@Service
public class RntlMgmtService extends BaseService {
	
	@Autowired
	private RntlMgmtMapper rntlMgmtMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public void callFreeRntlCalcInfo(Map<String, Object> param) throws MvnoServiceException{
		
		try {
			
			rntlMgmtMapper.callFreeRntlCalcInfo(param);
				
		} catch(Exception e) {
			e.printStackTrace();
			throw new MvnoServiceException(e.getMessage());
		}
		
	}
	
}