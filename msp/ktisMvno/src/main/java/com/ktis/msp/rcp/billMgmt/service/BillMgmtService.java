package com.ktis.msp.rcp.billMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.billMgmt.mapper.BillMgmtMapper;
import com.ktis.msp.rcp.billMgmt.vo.BillMgmtVO;

@Service
public class BillMgmtService extends BaseService {

	@Autowired
	private BillMgmtMapper billMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public List<?> getCustInvPymMgmtList(BillMgmtVO vo){
		if(vo.getCustomerId() == null || "".equals(vo.getCustomerId())){
			throw new MvnoRunException(-1, "고객정보가 존재하지 않습니다");
		}
		
		if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
			throw new MvnoRunException(-1, "청구월이 존재하지않습니다");
		}
		
		List<?> list = billMgmtMapper.getCustInvPymMgmtList(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> pReqParamMap = new HashMap<String, Object>();
		pReqParamMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(list, maskFields, pReqParamMap);
		
		return list; 
	}
	
	public List<?> getCustInvPymMgmtListExcel(BillMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getCustomerId() == null || "".equals(vo.getCustomerId())){
			throw new MvnoRunException(-1, "고객정보가 존재하지 않습니다");
		}
		
		if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
			throw new MvnoRunException(-1, "청구월이 존재하지않습니다");
		}
		
		List<?> list = billMgmtMapper.getCustInvPymMgmtListExcel(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> pReqParamMap = new HashMap<String, Object>();
		pReqParamMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(list, maskFields, pReqParamMap);
		
		return list; 
	}
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subLinkName","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		
		return maskFields;
	}

}
