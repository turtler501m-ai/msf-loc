package com.ktis.msp.batch.job.org.userlmsmgmt.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.userlmsmgmt.mapper.WebUsrLmsMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;
@Service
public class WebUsrLmsService extends BaseService {

	@Autowired
	private WebUsrLmsMapper webUsrLmsMapper;

	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	public WebUsrLmsService() {
		setLogPrefix("[WebUsrLmsService] ");
	}
	
	
	// 11개월 미접속 고객 휴면계정 LMS 발송
	@Transactional(rollbackFor=Exception.class)
	public void webUsrInfoLms(BatchCommonVO batchVo) throws MvnoServiceException {
		try {
	    	LOGGER.info(generateLogMsg("11개월 미접속 고객 select"));
			List<Map<String, Object>> list = webUsrLmsMapper.userInfoLmsSendList();
	    	LOGGER.info(generateLogMsg("11개월 미접속 고객 : " + list.size() + " 건"));
			
			Calendar cal = Calendar.getInstance();
			
			int lastDay = cal.getMaximum(Calendar.DAY_OF_MONTH);			

			Date toDay = new Date();
			String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATE_SHORT);

			if(list.size() > 0){

				for(int i=0; i<list.size(); i++) {
					
					String strUserId = String.valueOf(list.get(i).get("USERID"));
					String strMobileNo = String.valueOf(list.get(i).get("MOBILE_NO"));
					String strRegstDttm = String.valueOf(list.get(i).get("REGST_DTTM"));

					String templateId = "37";
					SmsCommonVO smsVO = smsCommonMapper.getTemplateText(templateId);

					smsVO.setSendMessage((smsVO.getTemplateText())
											.replaceAll(Pattern.quote("#{year}"), String.valueOf(strToDay.substring(0,4)))
											.replaceAll(Pattern.quote("#{month}"), String.valueOf(strToDay.substring(4,6)))
											.replaceAll(Pattern.quote("#{lastDay}"), String.valueOf(lastDay)));

					// MSG 테이블 시퀀스 가져오기
					String pMseq = webUsrLmsMapper.selectMsgSeq();
					smsVO.setMseq(pMseq);
										
//					LOGGER.info("LMS 전송 내용 : {}", smsVO.getSendMessage());
					
					int iLmsSendCnt = 0;
					
					smsVO.setTemplateId(templateId);
					smsVO.setMobileNo(strMobileNo);

					// SMS 발송 등록
					iLmsSendCnt = webUsrLmsMapper.insertTemplateMsgQueue(smsVO);
					
					smsVO.setSendDivision("MCP");
					smsVO.setContractNum("");
					smsVO.setReqId(BatchConstants.BATCH_USER_ID);
					smsVO.setPortalUserid(strUserId);

					// SMS 발송내역 등록
					smsCommonMapper.insertSmsSendMst(smsVO);
										
					smsVO.setUserId(strUserId);
					smsVO.setRegstDttm(strRegstDttm);
					
					// MST 테이블에 MSEQ 정보 추가
					webUsrLmsMapper.updateLmsSendList(smsVO);
					
					
				}
		    	LOGGER.info(generateLogMsg("LMS 발송 완료"));
			} else {
				LOGGER.info("LMS 대상 없음");				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}		


	// 휴면계정 LMS 처리 결과 확인
	@Transactional(rollbackFor=Exception.class)
	public void webUsrInfoLmsRsl(BatchCommonVO batchVo) throws MvnoServiceException {
		try {
	    	LOGGER.info(generateLogMsg("휴면계정 LMS 처리 결과 확인 select"));
			List<Map<String, Object>> list = webUsrLmsMapper.userInfoLmsSendListChk();
	    	LOGGER.info(generateLogMsg("휴면계정 LMS 발송건 : " + list.size() + " 건"));
	    	
	    	int cnt = 0;
			if(list.size() > 0){
				
				for(int i=0; i<list.size(); i++) {
					
					String strMseq = String.valueOf(list.get(i).get("MSEQ"));
					
					// LMS 처리 결과 확인
					String lmsSendChk = webUsrLmsMapper.lmsSendChk(strMseq);
					
					// LMS 처리 결과 저장
					if("1".equals(lmsSendChk)){
						webUsrLmsMapper.updateSendYn(strMseq);
						cnt++;
					}
				}
			}
	    	LOGGER.info(generateLogMsg("휴면계정 LMS : " + cnt + " 건 처리 완료"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
