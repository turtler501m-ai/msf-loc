package com.ktis.msp.batch.job.org.termssendmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.termssendmgmt.mapper.TermsSendMgmtMapper;

@Service
public class TermsSendMgmtService extends BaseService {
	
	@Autowired
	private TermsSendMgmtMapper termsSendMgmtMapper;
	
	public TermsSendMgmtService() {
		setLogPrefix("[TermsSendMgmtService]");
	}
	
    
    /**
     * 약관발송대상자 추출
     * @param param
     * @return
     * @throws MvnoServiceException 
     */
    @Transactional(rollbackFor=Exception.class)
	public void insertTermsSendTrgt() throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("약관발송대상자 추출 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
		try {
			termsSendMgmtMapper.insertTermsSendTrgt();
		} catch (Exception e) {
			throw new MvnoServiceException("EORG1001", e);
		}
	}

}
