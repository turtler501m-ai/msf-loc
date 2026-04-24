package com.ktis.msp.batch.job.org.termssendmgmt.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.termssendmgmt.mapper.TermsSendMgmtMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.fdl.property.EgovPropertyService;


@Service
public class TermsSendMgmtService extends BaseService {

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private TermsSendMgmtMapper termsSendMgmtMapper;

	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	public TermsSendMgmtService() {
		setLogPrefix("[TermsSendMgmtService]");
	}
	
    
    /**
     * 약관발송대상자 추출 LMS	2018년
     * @param param
     * @return
     * @throws MvnoServiceException 
     */
    @Transactional(rollbackFor=Exception.class)
	public void insertTermsLmsSendTrgt() throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("약관발송대상자 추출 LMS START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
		try {
			termsSendMgmtMapper.insertTermsLmsSendTrgt();
		} catch (Exception e) {
			throw new MvnoServiceException("EORG1001", e);
		}
	}
    
    /**
     * 약관발송대상자 추출 LMS	2019년~
     * @param param
     * @return
     * @throws MvnoServiceException 
     */
    @Transactional(rollbackFor=Exception.class)
	public void insertTermsLmsSendTrgt2() throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("약관발송대상자 추출 LMS START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
		try {
			termsSendMgmtMapper.insertTermsLmsSendTrgt2();
		} catch (Exception e) {
			throw new MvnoServiceException("EORG1001", e);
		}
	}    
    
    /**
     * 약관발송대상 LMS 발송
     * @param param
     * @return
     * @throws MvnoServiceException 
     */
    @Transactional(rollbackFor=Exception.class)
	public int termsSendTrgt(BatchCommonVO batchVo) throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("약관발송대상자 LMS 발송 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		int procCnt = 0;
		try {
	    	LOGGER.info(generateLogMsg("약관발송대상자 select"));
	    	List<Map<String, Object>> list = termsSendMgmtMapper.termsLmsSendList();
	    	LOGGER.info(generateLogMsg("약관발송대상자 : " + list.size() + " 건"));
	    	
	    	procCnt = list.size();
	    	if(list.size() > 0){

				for(int i=0; i<list.size(); i++) {

					String strContractNum = String.valueOf(list.get(i).get("CONTRACT_NUM"));
					String strMobileNo = String.valueOf(list.get(i).get("MOBILE_NO"));
					String strSeqNum = String.valueOf(list.get(i).get("SEQ_NUM"));
					String strUserId = String.valueOf(list.get(i).get("USER_ID"));
					String strTypeCd = String.valueOf(list.get(i).get("TYPE_CD"));

					if("JG".equals(strTypeCd)){
						strTypeCd = "MSP";
					} else if ("WEB".equals(strTypeCd)) {
						strTypeCd = "MCP";						
					}
					
					String templateId = "36";
					KtSmsCommonVO smsVO = smsCommonMapper.getKtTemplateText(templateId);
					smsVO.setMessage((smsVO.getTemplateText()));
					smsVO.setkMessage((smsVO.getTemplateText()));
//					LOGGER.info("LMS 전송 내용 : {}", smsVO.getSendMessage());
					
//					int iLmsSendCnt = 0;
					
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02(templateId);
					smsVO.setReserved03("TermsLmsSendSchedule");
					smsVO.setTemplateId(templateId);
					smsVO.setRcptData(strMobileNo);
					
					// 카카오 알림톡
					smsVO.setMsgTypeSecond(propertiesService.getString("msg.kakao.ktKNextType"));
					smsVO.setkSenderkey(propertiesService.getString("msg.kakao.ktSenderKey"));	
					
					// SMS 발송 등록
					smsCommonMapper.insertKtMsgTmpQueue(smsVO);

					SmsSendVO sendVO = new SmsSendVO();
					sendVO.setTemplateId(templateId);
					sendVO.setMsgId(smsVO.getMsgId());
					sendVO.setSendReqDttm(smsVO.getScheduleTime());
					sendVO.setReqId(BatchConstants.BATCH_USER_ID);
					
					// SMS 발송내역 등록
					smsCommonMapper.insertKtSmsSendMst(sendVO);
					
					sendVO.setSeqNum(strSeqNum);
					
					// MST 테이블에 MSEQ 정보 추가
					termsSendMgmtMapper.updateLmsSendList(sendVO);
					
					
				}
		    	LOGGER.info(generateLogMsg("LMS 발송 완료"));
			} else {
				LOGGER.info("LMS 대상 없음");				
			}
	    	
		} catch (Exception e) {
			throw new MvnoServiceException("EORG1001", e);
		}
		
		return procCnt;
	}    
    
	// 약관 LMS 처리 결과 확인
	@Transactional(rollbackFor=Exception.class)
	public void termsSendRsl(BatchCommonVO batchVo) throws MvnoServiceException {
		try {
	    	LOGGER.info(generateLogMsg("약관 LMS 처리 결과 확인 select"));
			List<Map<String, Object>> list = termsSendMgmtMapper.termsLmsSendListChk();
	    	LOGGER.info(generateLogMsg("약관 LMS 발송건 : " + list.size() + " 건"));
	    	
	    	int cnt = 0;
			if(list.size() > 0){
				
				for(int i=0; i<list.size(); i++) {
					
					String strMseq = String.valueOf(list.get(i).get("MSEQ"));
					
					// LMS 처리 결과 확인
					String lmsSendChk = termsSendMgmtMapper.lmsSendChk(strMseq);
					
					// LMS 처리 결과 저장
					if("1".equals(lmsSendChk)){
						termsSendMgmtMapper.updateSendYn(strMseq);
						cnt++;
					}
				}
			}
	    	LOGGER.info(generateLogMsg("약관 LMS : " + cnt + " 건 처리 완료"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    

}
