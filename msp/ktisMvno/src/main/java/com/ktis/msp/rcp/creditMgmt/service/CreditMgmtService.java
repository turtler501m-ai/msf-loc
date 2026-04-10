package com.ktis.msp.rcp.creditMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.creditMgmt.mapper.CreditMgmtMapper;
import com.ktis.msp.rcp.creditMgmt.vo.CreditVO;

@Service
public class CreditMgmtService extends BaseService{
	
	@Autowired
	private  CreditMgmtMapper creditMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * @Description : 포인트 회원 리스트를 조회 한다.
	 * @Param  : pointMgmtVo
	 * @Return : List<?>
	 * @Author : 한상욱
	 * @Create Date : 2016. 04. 06.
	 */
	public List<?> selectCreditList(CreditVO pVo, Map<String, Object> paramMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신용정보 동의서 조회 START."));
        logger.info(generateLogMsg("Return Vo [CreditVO] = " + pVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> creditMgmtVoList = new ArrayList();
		
		creditMgmtVoList = creditMgmtMapper.selectCreditList(pVo);
		
		maskingService.setMask(creditMgmtVoList, maskingService.getMaskFields(), paramMap);
		
		return creditMgmtVoList;
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String creditInsert(Map<String, Object> param){
		
		String id = creditMgmtMapper.getCreditId();
		
		param.put("CREDIT_ID", "CRE" + id);
		
		creditMgmtMapper.creditInsert(param);
		
		return "CRE" + id;
	}

	public List<?> getFile1(CreditVO pVo){
		
		List<?> creditMgmtVoList = new ArrayList();
		
		creditMgmtVoList = creditMgmtMapper.getFile1(pVo);
		
		return creditMgmtVoList;
	}	
}