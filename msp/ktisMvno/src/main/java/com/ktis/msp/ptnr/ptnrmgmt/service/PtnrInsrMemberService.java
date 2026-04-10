package com.ktis.msp.ptnr.ptnrmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.ptnr.ptnrmgmt.mapper.PtnrInsrMemberMapper;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInsrMemberVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class PtnrInsrMemberService extends BaseService {

	@Autowired
	private PtnrInsrMemberMapper mapper;

	@Autowired
	protected EgovPropertyService propertyService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	/**
	 * @Description : 동부요금제를 조회한다. SELECT BOX SET
	 * @Param  : PtnrInsrMemberVO
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 05.
	 */
	public List<?> selectDongbuRate(PtnrInsrMemberVO vo){

		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("동부요금제를 조회 SELECT BOX START"));
		logger.debug(generateLogMsg("Return Vo [searchVO] = " + vo.toString()));
		logger.debug(generateLogMsg("================================================================="));

		List<?> dongbuRateList = new ArrayList<PtnrInsrMemberVO>();

		dongbuRateList = mapper.selectDongbuRate(vo);

		return dongbuRateList;

	}


	/**
	 * @Description : 보험가입자정보를 조회한다.
	 * @Param  : PtnrInsrMemberVO
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 05.
	 */
	public List<?> getInsrMemberList(PtnrInsrMemberVO vo, Map<String, Object> paramMap){

		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("보험가입자정보 조회"));
		logger.debug(generateLogMsg("Return Vo [searchVO] = " + vo.toString()));
		logger.debug(generateLogMsg("================================================================="));

		List<?> ptnrInsrMemberList = new ArrayList<PtnrInsrMemberVO>();

		ptnrInsrMemberList = mapper.getInsrMemberList(vo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(ptnrInsrMemberList, maskFields, paramMap);

		return ptnrInsrMemberList;

	}
	
	/**
	 * @Description : 보험가입자 상세내역 조회한다.
	 * @Param  : PtnrInsrMemberVO
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 05.
	 */
	public List<?> getInsrHistory(PtnrInsrMemberVO vo, Map<String, Object> paramMap){

		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("보험가입자 상세내역 조회"));
		logger.debug(generateLogMsg("Return Vo [searchVO] = " + vo.toString()));
		logger.debug(generateLogMsg("================================================================="));

		List<?> ptnrInsrMemberList = new ArrayList<PtnrInsrMemberVO>();

		ptnrInsrMemberList = mapper.getInsrHistory(vo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(ptnrInsrMemberList, maskFields, paramMap);

		return ptnrInsrMemberList;

	}

	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("mobileNo","MOBILE_PHO");
		maskFields.put("custNm","CUST_NAME");
		maskFields.put("ctn","MOBILE_PHO");

		maskFields.put("minorAgentNm","CUST_NAME");
		maskFields.put("minorAgentTel","MOBILE_PHO");

		return maskFields;
	}
}
