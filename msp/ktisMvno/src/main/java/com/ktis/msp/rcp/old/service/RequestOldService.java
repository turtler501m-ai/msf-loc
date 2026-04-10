package com.ktis.msp.rcp.old.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.old.mapper.RequestOldMapper;
import com.ktis.msp.rcp.old.vo.RequestOldVo;
import com.ktis.msp.rcp.old.vo.RequestVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RequestOldService extends BaseService {

	@Autowired
	private RequestOldMapper requestOlMapper;

	public List<EgovMap> selectList(RequestOldVo requestSearchVO) {
		return requestOlMapper.selectList(requestSearchVO);
	}
	public List<EgovMap> selectListForExcel(RequestOldVo requestSearchVO) {
		return requestOlMapper.selectListForExcel(requestSearchVO);
	}

	public int selectListCount(RequestOldVo requestSearchVO) {
		return requestOlMapper.selectListCount(requestSearchVO);
	}

	public RequestVO selectRowByPk(String requestKey) {
		return requestOlMapper.selectRowByPk(requestKey);
	}

	public HashMap<String, String> setMaskMap(){
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName","CUST_NAME");
		maskFields.put("cstmrForeignerRrn","SSN");
		maskFields.put("cstmrMobile","MOBILE_PHO");
		maskFields.put("cstmrForeignerPn","PASSPORT");
		maskFields.put("cstmrJuridicalRrn","CORPORATE");
		maskFields.put("cstmrMail","EMAIL");
		maskFields.put("cstmrAddr","ADDR");
		maskFields.put("cstmrTel","TEL_NO");
		maskFields.put("dlvryName","CUST_NAME");
		maskFields.put("dlvryTel","TEL_NO");
		maskFields.put("dlvryMobile","MOBILE_PHO");
		maskFields.put("dlvryAddr","ADDR");
		maskFields.put("reqAccountNumber","ACCOUNT");
		maskFields.put("reqAccountName","CUST_NAME");
		maskFields.put("reqAccountRrn","SSN");
		maskFields.put("reqCardNo","CREDIT_CAR");
		maskFields.put("reqCardMm","CARD_EXP");
		maskFields.put("reqCardYy","CARD_EXP");
		maskFields.put("reqCardName","CUST_NAME");
		maskFields.put("reqCardRrn","SSN");
		maskFields.put("reqGuide","MOBILE_PHO");
		maskFields.put("moveMobile","MOBILE_PHO");
		maskFields.put("minorAgentName","CUST_NAME");
		maskFields.put("minorAgentRrn","SSN");
		maskFields.put("minorAgentTel","TEL_NO");
		maskFields.put("reqUsimSn","USIM");
		maskFields.put("reqPhoneSn","DEV_NO");
		maskFields.put("cstmrNativeRrn","SSN");
		maskFields.put("faxnum","TEL_NO");
		maskFields.put("jrdclAgentTel","TEL_NO");
		
		return maskFields;
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"cstmrForeignerPn", "cstmrForeignerRrn", "cstmrNativeRrn", "othersPaymentRrn"
			, "minorAgentRrn", "jrdclAgentRrn", "entrustResRrn", "nameChangeRrn"
			, "reqAccountRrn", "reqAccountNumber", "reqCardRrn", "reqCardNo"})
	public List<EgovMap> decryptDBMSList(List<EgovMap> list){
		return list;
	}

	@Crypto(decryptName="DBMSDec", fields = {"cstmrForeignerPn", "cstmrForeignerRrn", "cstmrNativeRrn", "othersPaymentRrn"
			, "minorAgentRrn", "jrdclAgentRrn", "entrustResRrn", "nameChangeRrn"
			, "reqAccountRrn", "reqAccountNumber", "reqCardRrn", "reqCardNo"})
	public RequestVO decryptDBMS(RequestVO vo){
		return vo;
	}
}
