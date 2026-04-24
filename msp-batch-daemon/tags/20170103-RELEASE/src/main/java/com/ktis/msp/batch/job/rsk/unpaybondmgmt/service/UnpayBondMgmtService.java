package com.ktis.msp.batch.job.rsk.unpaybondmgmt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rsk.unpaybondmgmt.mapper.UnpayBondMgmtMapper;
import com.ktis.msp.batch.job.rsk.unpaybondmgmt.vo.UnpayBondMgmtVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class UnpayBondMgmtService extends BaseService {
	@Autowired
	private UnpayBondMgmtMapper unpayBondMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	public void getUnpayBondDtlListEx(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String execParam = batchCommonVO.getExecParam();
		
		UnpayBondMgmtVO unpayBondMgmtVO = (UnpayBondMgmtVO) JacksonUtil.makeVOFromJson(execParam, UnpayBondMgmtVO.class);
		
		if(KtisUtil.isEmpty(unpayBondMgmtVO.getStdrYm())) {
			throw new MvnoServiceException("ERSK1008");
		} else if (KtisUtil.isEmpty(unpayBondMgmtVO.getStrtYm())) {
			throw new MvnoServiceException("ERSK1009");
		} else if (KtisUtil.isEmpty(unpayBondMgmtVO.getEndYm())) {
			throw new MvnoServiceException("ERSK1009");
		}
		
		LOGGER.debug("시작 = {}", KtisUtil.toStr(new Date(), "yyyyMMddHHmmss"));
		
		String serverInfo = propertiesService.getString("excelPath");
		String fileName = serverInfo + "청구수미납상세_" + unpayBondMgmtVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
		String sheetName = "청구수미납상세";
		
		String[] strHead = {"기준월","사용월","청구월","서비스계약번호","계약번호","계약상태","고객명","개통대리점","청구항목","청구금액","청구금액부가세"
				,"할인금액","할인부가세","클레임금액","클레임부가세","위약금(공시지원금1)","위약금(공시지원금1)부가세","위약금(공시지원금2)","위약금(공시지원금2)부가세"
				,"위약금(추가지원금1)","위약금(추가지원금1)부가세","위약금(추가지원금2)","위약금(추가지원금2)부가세","절사금액","절사부가세","기타금액","기타 부가세","조정금액합계"
				,"조정금액부가세 합계","조정금액 총 합계","총청구금액","수납금액","수납금부가세","수납금합계","미납금액","미납금부가세","미납금총합계","납기일자","수납일자","미납개월수","납부방법"};
		
		String[] strValue = {"crtYmEx","usgYmEx","billYmEx","svcCntrNo","contractNum","statusNm","subLinkName","openAgntNm","saleItmNm","invAmnt","invVat"
		         ,"adjAAmnt","adjAVat","adjBAmnt","adjBVat","adjCAmnt","adjCVat","adjDAmnt","adjDVat","adjEAmnt","adjEVat","adjFAmnt","adjFVat","adjGAmnt"
		         ,"adjGVat","adjHAmnt","adjHVat","adjAmnt","adjVat","totAdjAmnt","totInvAmnt","pymAmnt","pymVat","totPymAmnt","unpdAmnt","unpdVat","totUnpdAmnt"
		         ,"duedatDate","blpymDate","unpdMnthCnt","pymnMthdNm"};
		
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,30,20,30,20,30,20,30,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		
		int[] intLen = {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0};
		
		try {
			List<?> list = unpayBondMgmtMapper.getUnpayBondDtlListEx(unpayBondMgmtVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", unpayBondMgmtVO.getUserId());
			
			list = setMaskingData(list, paramMap);
			
			fileDownService.saveBigExcel(unpayBondMgmtVO.getUserId(), fileName, sheetName, list, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
		}catch(Exception e){
			LOGGER.debug(e.getMessage());
		}
		
		LOGGER.debug("종료 = {}", KtisUtil.toStr(new Date(), "yyyyMMddHHmmss"));
	}
	
	
	/**
	 * 마스크필드
	 * @return
	 */
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("subLinkName", "CUST_NAME");
		
		return maskFields;
	}
	
	public List<?> setMaskingData(List<?> list, Map<String, Object> param){
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, param);
		
		return list;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public void callUnpdBondSttc() throws MvnoServiceException {
		try {
			unpayBondMgmtMapper.callUnpdBondSttc();
			
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1043", e);
		}
	}
}
