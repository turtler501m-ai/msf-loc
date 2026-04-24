package com.ktis.msp.batch.job.rcp.inetInfo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.inetInfo.mapper.InetInfoMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class InetInfoService extends BaseService {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private InetInfoMapper inetInfoMapper;

	public InetInfoService() {
		setLogPrefix("[InetInfoService]");
	}	

	// 매일 7시 00분 시작 			
	@Transactional(rollbackFor=Exception.class)
	public void inerInfoTrgt(BatchCommonVO batchVo) throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("트리플할인 실적 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
		try {
	    	LOGGER.info(generateLogMsg("트리플할인 실적 select"));
	    	List<Map<String,Object>> list = inetInfoMapper.selectInetInfoList();
	    	LOGGER.info(generateLogMsg("트리플할인 실적 대상: " + list.size() + " 건"));
	    	
	    	if(list.size() > 0){
	    		for(Map<String,Object> map : list){
	    			String inetInfoSeq = map.get("INET_INFO_SEQ").toString();
		    		inetInfoMapper.updateInetInfoMst(inetInfoSeq);
		    		inetInfoMapper.InsertInetInfoHist(inetInfoSeq);
		    		inetInfoMapper.updateInetInfo(inetInfoSeq);
	    		}
			} else {
				LOGGER.info("트리플할인 실적 대상 없음");				
			}
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2020", e);
		}
	}    
}
