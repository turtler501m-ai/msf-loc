package com.ktis.msp.batch.job.rcp.giftprmt.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.giftprmt.mapper.GiftPrmtLmsMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class GiftPrmtLmsService extends BaseService {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private GiftPrmtLmsMapper giftPrmtLmsMapper;

	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	public GiftPrmtLmsService() {
		setLogPrefix("[GiftPrmtLmsService]");
	}	

	// 대상 고객 추출
	// 1. 개통후 7일 경과한 사용중인 고객
	// 2. 사은품 프로모션에 해당하는 고객
	// 3. 고객 추출은 중복불가
    @Transactional(rollbackFor=Exception.class)
	public int insertGiftPrmtLmsTrgt() throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 고객 추출 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		int ret = 0;
		
		try {
			ret = giftPrmtLmsMapper.insertGiftPrmtLmsTrgt();
			LOGGER.info(generateLogMsg("처리건수 : "+ret+"건"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2016", e);
		}

    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 고객 추출 END"));
    	LOGGER.info(generateLogMsg("================================================================="));
    	
    	return ret;
	}    
    
	// LMS 전송
	// 매일 9시 부터 18시 까지 20분 간격  500건 			
    @Transactional(rollbackFor=Exception.class)
	public int sendLmsGiftTrgt(BatchCommonVO batchVo) throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 고객 LMS 발송 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
    	int procCnt = 0;
    	
		try {
			int maxCnt = 0;
			
			// 매일 9시 부터 18시 까지 20분 간격  500건 
			maxCnt = 500;				
			
	    	List<Map<String, Object>> list = giftPrmtLmsMapper.selectLmsSendList(maxCnt);
	    	LOGGER.info(generateLogMsg("발송 대상 : " + list.size() + " 건"));
	    	
	    	if(list.size() > 0){

				for(int i=0; i<list.size(); i++) {

					String strContractNum = String.valueOf(list.get(i).get("CONTRACT_NUM"));
					String strCtn = String.valueOf(list.get(i).get("SUBSCRIBER_NO"));
					String strTypeCd = "MSP";
					String prmtId = String.valueOf(list.get(i).get("PRMT_ID"));
					
					String templateId = "204";
					KtSmsCommonVO smsVO = smsCommonMapper.getKtTemplateText(templateId);
					smsVO.setMessage((smsVO.getTemplateText()).replace("#{prmtId}", (String)list.get(i).get("PRMT_ID") ) );
										
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02(templateId);
					smsVO.setReserved03("GiftPrmtLmsSchedule");
					smsVO.setTemplateId(templateId);
					smsVO.setRcptData(strCtn);
					
					
					// SMS 발송 등록
					int iLmsSendCnt = 0;
					iLmsSendCnt = smsCommonMapper.insertKtMsgTmpQueue(smsVO);

					if(iLmsSendCnt > 0){ // 문자 정상 발송시
						SmsSendVO sendVO = new SmsSendVO();
						// MSP_GIFT_PRMT_CUSTOMER 테이블 UPDATE 용도
						sendVO.setContractNum(strContractNum);
						sendVO.setPrmtId(prmtId);
						// MSP_SMS_SEND_MST 테이블 INSERT 용도
						sendVO.setTemplateId(templateId);
						sendVO.setMsgId(smsVO.getMsgId());
						sendVO.setSendReqDttm(smsVO.getScheduleTime());
						sendVO.setReqId(BatchConstants.BATCH_USER_ID);
						
						// SMS 발송내역 등록
						smsCommonMapper.insertKtSmsSendMst(sendVO);
						
						// MST 테이블에 처리결과 update
						giftPrmtLmsMapper.updateLmsSendList(sendVO);

						procCnt ++;

					} else {
				    	LOGGER.info(generateLogMsg("[ LMS 발송 실패 ] " + "계약번호 : " + strContractNum + "전화번호 : " + strCtn));						
					}
				}
				
		    	LOGGER.info(generateLogMsg("LMS 발송 종료"));
			} else {
				LOGGER.info("LMS 발송 대상 없음");				
			}
	    	
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2017", e);
		}

    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 고객 LMS 발송 END"));
    	LOGGER.info(generateLogMsg("================================================================="));
    	return procCnt;
	}    

	// LMS 재전송
	// 매일 9시 부터 18시 까지 20분 간격  500건 			
    @Transactional(rollbackFor=Exception.class)
	public int reSendLmsGiftTrgt(BatchCommonVO batchVo) throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 고객 LMS 재발송 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
    	int procCnt = 0;
    	
		try {
			int maxCnt = 0;
			
			// 매일 9시 부터 18시 까지 20분 간격  500건 
			maxCnt = 500;				
			
	    	List<Map<String, Object>> list = giftPrmtLmsMapper.selectLmsReSendList(maxCnt);
	    	LOGGER.info(generateLogMsg("재발송 대상 : " + list.size() + " 건"));
	    	
	    	if(list.size() > 0){

				for(int i=0; i<list.size(); i++) {

					String strContractNum = String.valueOf(list.get(i).get("CONTRACT_NUM"));
					String strCtn = String.valueOf(list.get(i).get("SUBSCRIBER_NO"));
					String strTypeCd = "MSP";
					String prmtId = String.valueOf(list.get(i).get("PRMT_ID"));
					
					String templateId = "208";
					KtSmsCommonVO smsVO = smsCommonMapper.getKtTemplateText(templateId);
					smsVO.setMessage((smsVO.getTemplateText()).replace("#{prmtId}", (String)list.get(i).get("PRMT_ID") ) );
					
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02(templateId);
					smsVO.setReserved03("GiftPrmtLmsSchedule");
					smsVO.setTemplateId(templateId);
					smsVO.setRcptData(strCtn);
					
					// SMS 발송 등록
					int iLmsSendCnt = 0;
					iLmsSendCnt = smsCommonMapper.insertKtMsgTmpQueue(smsVO);

					if(iLmsSendCnt > 0){ // 문자 정상 발송시
						SmsSendVO sendVO = new SmsSendVO();
						// MSP_GIFT_PRMT_CUSTOMER 테이블 UPDATE 용도
						sendVO.setContractNum(strContractNum);
						sendVO.setPrmtId(prmtId);
						// MSP_SMS_SEND_MST 테이블 INSERT 용도
						sendVO.setTemplateId(templateId);
						sendVO.setMsgId(smsVO.getMsgId());
						sendVO.setSendReqDttm(smsVO.getScheduleTime());
						sendVO.setReqId(BatchConstants.BATCH_USER_ID);
						
						// SMS 발송내역 등록
						smsCommonMapper.insertKtSmsSendMst(sendVO);
						
						// MST 테이블에 처리결과 update
						giftPrmtLmsMapper.updateLmsReSendList(sendVO);
						procCnt ++;
					} else {
				    	LOGGER.info(generateLogMsg("[ LMS 재발송 실패 ] " + "계약번호 : " + strContractNum + "전화번호 : " + strCtn));						
					}
					
				}
		    	LOGGER.info(generateLogMsg("LMS 재발송 종료"));
			} else {
				LOGGER.info("LMS 재발송 대상 없음");				
			}
	    	
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2018", e);
		}

    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 고객 LMS 재발송 END"));
    	LOGGER.info(generateLogMsg("================================================================="));
    	return procCnt;
	}   

	// M포털 가입 대상 고객 추출 (기존 로직 유지)
	// 1. 개통후 7일 경과한 사용중인 고객
	// 2. 사은품 프로모션에 해당하는 고객
	// 3. 고객 추출은 중복불가
    @Transactional(rollbackFor=Exception.class)
	public int insertGiftPrmtMPortalTrgt() throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 M포털 가입 고객 추출 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		int ret = 0;
		
		try {
			ret = giftPrmtLmsMapper.insertGiftPrmtMPortalTrgt();
			LOGGER.info(generateLogMsg("처리건수 : "+ret+"건"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2012", e);
		}

    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 M포털 가입 고객 추출 END"));
    	LOGGER.info(generateLogMsg("================================================================="));
    	
    	return ret;
	}

	// M포털 가입 고객 사은품 프로모션 결과 추출
    @Transactional(rollbackFor=Exception.class)
	public int insertGiftPrmtResultMPortalTrgt() throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 M포털 가입 고객 사은품 프로모션 결과 추출 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		int ret = 0;
		
		try {
			ret = giftPrmtLmsMapper.insertGiftPrmtResultMPortalTrgt();
			LOGGER.info(generateLogMsg("처리건수 : "+ret+"건"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2013", e);
		}

    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("사은품프로모션 M포털 가입 고객 사은품 프로모션 결과 추출 END"));
    	LOGGER.info(generateLogMsg("================================================================="));
    	
    	return ret;
	}
    
    
	// M포털 USIM 제휴처 대상 고객 추출
	@Transactional(rollbackFor = Exception.class)
	public int insertGiftPrmtMPortalUsimTrgt() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("사은품프로모션 M포털 가입 고객 추출(USIM 제휴처) START"));
		LOGGER.info(generateLogMsg("================================================================="));
		int ret = 0;

		try {
			ret = giftPrmtLmsMapper.insertGiftPrmtMPortalUsimTrgt();
			LOGGER.info(generateLogMsg("처리건수 : "+ret+"건"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2011", e);
		}

		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("사은품프로모션 M포털 가입 고객 추출(USIM 제휴처) END"));
		LOGGER.info(generateLogMsg("================================================================="));

		return ret;
	}

	// 0. 개통일 포함 3일 경과한 사용중인 고객(USIM 제휴처)
	@Transactional(rollbackFor = Exception.class)
	public int insertGiftPrmtLmsUsimTrgt() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("사은품프로모션 고객 추출(USIM 제휴처) START"));
		LOGGER.info(generateLogMsg("================================================================="));
		int ret = 0;

		try {
			ret = giftPrmtLmsMapper.insertGiftPrmtLmsUsimTrgt();
			LOGGER.info(generateLogMsg("처리건수 : "+ret+"건"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2015", e);
		}

		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("사은품프로모션 고객 추출(USIM 제휴처) END"));
		LOGGER.info(generateLogMsg("================================================================="));

		return ret;
	}

	// 대상고객 기본 사은품 프로모션 결과 추출, M포털에서 가입된 서식지는 제외
	@Transactional(rollbackFor = Exception.class)
	public int insertGiftPrmtResultTrgt() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("대상고객 기본 사은품 프로모션 결과 추출 START"));
		LOGGER.info(generateLogMsg("================================================================="));
		int ret = 0;

		try {
			ret = giftPrmtLmsMapper.insertGiftPrmtResultTrgt();
			LOGGER.info(generateLogMsg("처리건수 : "+ret+"건"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2019", e);
		}

		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("대상고객 기본 사은품 프로모션 결과 추출 END"));
		LOGGER.info(generateLogMsg("================================================================="));

		return ret;
	}

	// 사은품 프로모션 결과 이력 저장
	@Transactional(rollbackFor = Exception.class)
	public int insertGiftPrmtResultHistTrgt() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("사은품 프로모션 결과 이력 저장 START"));
		LOGGER.info(generateLogMsg("================================================================="));
		int ret = 0;

		try {
			ret = giftPrmtLmsMapper.insertGiftPrmtResultHistTrgt();
			LOGGER.info(generateLogMsg("처리건수 : "+ret+"건"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2014", e);
		}

		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("사은품 프로모션 결과 이력 저장  END"));
		LOGGER.info(generateLogMsg("================================================================="));

		return ret;
	}


	// M포털 이벤트 코드 적용 대상 고객 추출
	@Transactional(rollbackFor = Exception.class)
	public int insertGiftPrmtEventCodeTrgt() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("사은품프로모션 M포털 이벤트 코드 적용 대상 고객 추출 START"));
		LOGGER.info(generateLogMsg("================================================================="));
		int ret = 0;

		try {
			ret = giftPrmtLmsMapper.insertGiftPrmtEventCodeTrgt();
			LOGGER.info(generateLogMsg("처리건수 : "+ret+"건"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2011", e);
		}

		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("사은품프로모션 M포털 이벤트 코드 적용 대상 고객 추출 END"));
		LOGGER.info(generateLogMsg("================================================================="));

		return ret;
	}
}
