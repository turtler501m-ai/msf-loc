package com.ktis.msp.batch.job.m2m.custmgmt.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.m2m.custmgmt.mapper.M2mUsimMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class M2mUsimService extends BaseService {
	
	@Autowired
	private M2mUsimMapper m2mUsimMapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * 배송중으로 3일경과한 M2M USIM 주문 등록자에게 SMS
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setStatus4Sms() throws MvnoServiceException{
		
		int procCnt = 0;
		
		try {
			// 배송중으로 3일경과한 M2M USIM 주문 발송대상 추출
			List<Map<String, Object>> list = m2mUsimMapper.getStatus4SmsTrgtList();
			
			for(Map<String, Object> rtMap : list) {

				
				String strCtn = (String) rtMap.get("MBLPHN_NUM");
				String strTypeCd = "M2M";
				String mspUserId = (String) rtMap.get("USR_ID");
				String requestTime = (String) rtMap.get("REQUEST_TIME");
				
				String templateId = "206";
				
				KtSmsCommonVO smsVO = smsCommonMapper.getKtTemplateText(templateId);
				
				smsVO.setMessage((smsVO.getTemplateText())
						                         .replaceAll(Pattern.quote("#{ordId}"), (String)rtMap.get("ORD_ID") )
						                         .replaceAll(Pattern.quote("#{sendDate}"), (String)rtMap.get("SEND_DATE") )
						                       );

				smsVO.setTemplateId(templateId);
				smsVO.setRcptData(strCtn);
				smsVO.setScheduleTime(requestTime);
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02(templateId);
				smsVO.setReserved03("OrdStatus4SmsSchedule");

				// SMS 발송 등록
				int iLmsSendCnt = 0;
				iLmsSendCnt = smsCommonMapper.insertKtMsgTmpQueue(smsVO);

				SmsSendVO sendVO = new SmsSendVO();
				if(iLmsSendCnt > 0){ // 문자 정상 발송시
					sendVO.setTemplateId(templateId);
					sendVO.setMsgId(smsVO.getMsgId());
					sendVO.setSendReqDttm(smsVO.getScheduleTime());
					sendVO.setReqId(BatchConstants.BATCH_USER_ID);
					
					// SMS 발송내역 등록
					smsCommonMapper.insertKtSmsSendMst(sendVO);
					
				} else {
			    	LOGGER.info(generateLogMsg("[ SMS 발송 실패 ] " + "전화번호 : " + smsVO.getRcptData()));						
				}
								
				procCnt++;
			}
			
		} catch(Exception e) {
			throw new MvnoServiceException("EM2M1001", e);
		}
		return procCnt;
	}
	
	
	
}
