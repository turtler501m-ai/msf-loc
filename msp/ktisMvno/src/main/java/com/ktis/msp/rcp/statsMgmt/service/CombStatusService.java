package com.ktis.msp.rcp.statsMgmt.service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.statsMgmt.mapper.CombStatusMapper;
import com.ktis.msp.rcp.statsMgmt.vo.CombStatusVo;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CombStatusService extends BaseService {

	@Autowired
	private CombStatusMapper combStatusMapper;

	@Autowired
	private MaskingService maskingService;


	public List<EgovMap> selectCombStatusList(CombStatusVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = combStatusMapper.selectCombStatusList(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();

		maskFields.put("subscriberNo",		"MOBILE_PHO");
		maskFields.put("svcNo",				"MOBILE_PHO");
		maskFields.put("subLinkName",		"CUST_NAME");

		return maskFields;
	}
}
