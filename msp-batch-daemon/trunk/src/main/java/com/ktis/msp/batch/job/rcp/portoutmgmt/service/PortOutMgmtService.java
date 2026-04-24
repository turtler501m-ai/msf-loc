package com.ktis.msp.batch.job.rcp.portoutmgmt.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.portoutmgmt.mapper.PortOutMgmtMapper;

@Service
public class PortOutMgmtService extends BaseService {
	
	@Autowired
	private PortOutMgmtMapper portOutMgmtMapper;
	
	
	/**
	 * @throws MvnoServiceException 
	 * MNP 해지고객의 해지 후 사업자 정보
	 * @param vo
	 * @throws 
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void callPortOutInfo(Map<String, Object> param) throws MvnoServiceException{
		
		try{
			LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			LOGGER.info("☆☆☆☆☆☆☆☆ call P_MDS_PORTOUT_INFO 프로시저시작☆☆☆☆☆☆☆ ");
			LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			portOutMgmtMapper.callPMdsPortOutInfo(param);
				
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}	
}
