package com.ktis.msp.batch.job.rcp.combmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.combmgmt.mapper.CombMgmtMapper;

@Service
public class CombMgmtService extends BaseService {
	
	@Autowired
	private CombMgmtMapper combMgmtMapper;
	
	/*
	 * 대상 등록일자 = 현재일자 - 6, 상태값 R -> N
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setResultCdN() throws MvnoServiceException {
		
		int cnt = 0;
		
		try {
			cnt = combMgmtMapper.setResultCdN();
			
			LOGGER.info("[결합승인상태관리] 미제출 처리 건수 : "+cnt+"건");
			
		}
		catch(Exception e) {
			throw new MvnoServiceException("ECMN1001", e);
		}
		
		return cnt;
	}
	
	/*
	 * 대상 수정일자 = 현재일자 - 3, 상태값 N,B -> C, 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setResultCdC() throws MvnoServiceException {
		
		int cnt = 0;
		
		try {
			
			cnt = combMgmtMapper.setResultCdC();
			
			LOGGER.info("[결합승인상태관리] 신청취소 처리 건수 : "+cnt+"건");
			
		}
		catch(Exception e) {
			throw new MvnoServiceException("ECMN1001", e);
		}
		
		return cnt;
	}
}
