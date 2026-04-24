package com.ktis.msp.batch.job.rcp.courtmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.courtmgmt.mapper.CourtRcvSttcMapper;

@Service
public class CourtRcvSttcService extends BaseService {

	@Autowired
	private CourtRcvSttcMapper courtRcvSttcMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public int insertCrSttc() throws MvnoServiceException {
		int procCnt = 0;
		
		courtRcvSttcMapper.deleteCrSttc();
		
        try {
    		procCnt = courtRcvSttcMapper.insertCrSttc();
        } catch (NullPointerException npe) {
            return 0;
        }
		
		return procCnt;
	}
	
}