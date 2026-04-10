package com.ktis.msp.cmn.accesslogsrch.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.accesslogsrch.mapper.AccessLogSrchMapper;
import com.ktis.msp.cmn.accesslogsrch.vo.AccessLogSrchVo;
import com.ktis.msp.cmn.masking.service.MaskingService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class AccessLogSrchService extends BaseService{

	@Autowired
	private AccessLogSrchMapper accessLogSrchMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public List<?> selectList(Map<String, Object> param){
		
		List<EgovMap> result = (List<EgovMap>) accessLogSrchMapper.selectList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");	
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	/**
	 * @Description : 사용자별접속로그 엑셀다운로드
	 * @Param  : userInfoMgmtVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2021. 01. 28.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<AccessLogSrchVo> selecExcelList(AccessLogSrchVo accessLogSrcvo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자별접속로그 엑셀다운로드 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + accessLogSrcvo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<AccessLogSrchVo> accessLogSrcvoList = new ArrayList<AccessLogSrchVo>();
		accessLogSrcvoList = accessLogSrchMapper.selecExcelList(accessLogSrcvo);
		
		return accessLogSrcvoList;
		
	}
}
