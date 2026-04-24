package com.ktis.msp.batch.job.cms.cmsbatchmgmt.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cms.cmsbatchmgmt.mapper.CmsTrgtMgmtMapper;


@Service
public class CmsTrgtMgmtService extends BaseService {
	
	@Autowired
	private CmsTrgtMgmtMapper cmsTrgtMgmtMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public void callCmsTrgtInfoProc() throws MvnoServiceException{
		
		try {
			Date toDay = new Date();
			String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATE_SHORT);
			
			cmsTrgtMgmtMapper.callCmsTrgtInfoProc(strToDay.substring(0,6));
				
		} catch(Exception e) {
			e.printStackTrace();
			throw new MvnoServiceException(e.getMessage());
		}
		
	}
	
}