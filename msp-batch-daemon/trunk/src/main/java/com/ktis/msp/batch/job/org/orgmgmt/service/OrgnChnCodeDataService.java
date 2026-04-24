package com.ktis.msp.batch.job.org.orgmgmt.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.mapper.OrgnChnCodeDataMapper;

@Service
public class OrgnChnCodeDataService extends BaseService {
	
	@Autowired
	private OrgnChnCodeDataMapper orgnChnCodeDataMapper;
	
	
	/**
	 * @throws MvnoServiceException 
	 * 조직채널상세 코드 현행화
	 * @param vo
	 * @throws 
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void callOrgnChnCodeData(Map<String, Object> param) throws MvnoServiceException{
		
		try{
				
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				LOGGER.info("☆☆☆☆☆☆☆☆callOrgnChnCodeData 프로시저시작☆☆☆☆☆☆☆☆☆ : ");
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				orgnChnCodeDataMapper.callOrgnChnCodeData(param);
				
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @throws MvnoServiceException 
	 * 
	 * @param vo
	 * @throws 
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void deleteOrgnChnCodeData(Map<String, Object> param) throws MvnoServiceException{
		
		try{
				
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				LOGGER.info("☆☆☆☆☆☆☆☆deleteOrgnChnCodeData 서비스 시작☆☆☆☆☆☆☆☆ : ");
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				orgnChnCodeDataMapper.deleteOrgnChnCodeData(param);
				
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
}
