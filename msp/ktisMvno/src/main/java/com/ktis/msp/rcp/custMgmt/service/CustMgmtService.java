package com.ktis.msp.rcp.custMgmt.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.custMgmt.mapper.CustMgmtMapper;
import com.ktis.msp.rcp.custMgmt.vo.FileInfoCuVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;

@Service
public class CustMgmtService extends BaseService {
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private CustMgmtMapper custMgmtMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	/**
	 * <PRE>
	 *
	 * </PRE>
	 * @param 
	 * @param 
	 * @return 
	 * @exception 
	 */
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName","CUST_NAME");
		maskFields.put("cstmrMobile","MOBILE_PHO");
		maskFields.put("cstmrNativeRrn","SSN");
		maskFields.put("cstmrForeignerRrn","SSN");
		maskFields.put("cstmrForeignerPn","PASSPORT");
		maskFields.put("cstmrJuridicalRrn","CORPORATE");
		maskFields.put("cstmrMail","EMAIL");
		maskFields.put("cstmrTel","MOBILE_PHO");
		maskFields.put("cstmrMobile","MOBILE_PHO");
		maskFields.put("faxnum","MOBILE_PHO");
		maskFields.put("dlvryTel","MOBILE_PHO");
		maskFields.put("reqAccountNumber","ACCOUNT");
		maskFields.put("reqCardNo","CREDIT_CAR");
		maskFields.put("reqCardMm","CARD_EXP");
		maskFields.put("reqCardYy","CARD_EXP");
		maskFields.put("reqAccountName","CUST_NAME");
		maskFields.put("reqAccountRrn","SSN");
		maskFields.put("reqCardName","CUST_NAME");
		maskFields.put("reqCardRrn","SSN");
		maskFields.put("reqGuide","MOBILE_PHO");
		maskFields.put("moveMobile","MOBILE_PHO");
		maskFields.put("minorAgentName","CUST_NAME");
		maskFields.put("minorAgentRrn","SSN");
		maskFields.put("minorAgentTel","MOBILE_PHO");
		maskFields.put("jrdclAgentName","CUST_NAME");
		maskFields.put("jrdclAgentRrn","SSN");
		maskFields.put("jrdclAgentTel","MOBILE_PHO");
		maskFields.put("reqUsimSn","USIM");
		maskFields.put("reqPhoneSn","DEV_NO");
		maskFields.put("iccId","USIM");
		maskFields.put("intmSrlNo","DEV_NO");
		maskFields.put("cstmrAddr","ADDR");
		maskFields.put("dlvryAddr","ADDR");
		maskFields.put("realUseCustNm","CUST_NAME");
		maskFields.put("cstmrAddrDtl","PASSWORD");
		maskFields.put("customerAdr","ADDR");
		maskFields.put("dlvryName","CUST_NAME");
		maskFields.put("dlvryMobile","MOBILE_PHO");
		maskFields.put("dlvryAddrDtl","PASSWORD");
		maskFields.put("customerLinkName","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("ssn","SSN");
		maskFields.put("ssn1","SSN");
		maskFields.put("ssn2","SSN");
		maskFields.put("ssn3","SSN");
		// 개통관리 엑셀다운로드시 최초단말일련번호 마스킹적용
		maskFields.put("fstModelSrlNum","DEV_NO");
		maskFields.put("lstModelSrlNum","DEV_NO");
		
		return maskFields;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int updateRecYn(String requestKey) {
		return custMgmtMapper.updateRecYn(requestKey);
	}
	
	
	
	/**
	 * 파일 업로드
	 * @param fileInfoVO
	 * @return
	 */
   @Transactional(rollbackFor=Exception.class)
	public int insertFileByCust(FileInfoCuVO fileInfoVO){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = custMgmtMapper.fileInsertByCust(fileInfoVO);
		
		return resultCnt;
	} 
}

