package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.RcpNowDlvryUsimMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpNowDlvryUsimVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class RcpNowDlvryUsimService extends BaseService {

	@Autowired
	private RcpNowDlvryUsimMapper rcpNowDlvryUsimMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	

	// 바로배송 온라인 신청서 3일 이내 USIM 미등록
	final static int NOT_REG_USIM       = 3;
	// 바로배송 온라인 신청서 5일 이내 USIM 미등록
	final static int NOT_OPEN_DT      = 5;
	
	// 바로배송 온라인 신청서 5일 이내 USIM 미등록
	//final static String URL      = "https://www.ktmmobile.com/m/appform/setUsimNoDlvey.do";
	
	/**
	 * 바로배송 온라인 신청서 3일이내 또는 5일이내 USIM 번호 미등록시 미개통
	 * +2025-07-22 추가 : 제휴온라인서식지 유심미보유 대상
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procRcpNowDlvryMgmt() throws MvnoServiceException {
		int procCnt = 0;
		LOGGER.info("바로배송 온라인 신청서 3일이내 또는 5일이내 USIM 번호 미등록시 미개통 처리 프로세스 시작");
		LOGGER.info("온라인신청서 바로배송 배송 완료 후 3일 지난 고객 조회");

		// 온라인신청서 바로배송 배송 완료 후 3일동안 지난 고객 조회(유심번호 미등록 고객)
		// 2025-07-22 추가 : 제휴온라인서식지 유심미보유 대상 신청서작성완료 후
		List<RcpNowDlvryUsimVO> list = rcpNowDlvryUsimMapper.getNotRegUsimCust(NOT_REG_USIM);

		try{
			for(RcpNowDlvryUsimVO vo : list){
				LOGGER.debug("가입신청키=" + vo.getRequestKey());
				LOGGER.debug("고객전화번호=" + vo.getDstaddr());
				LOGGER.debug("개통완료기한=" + vo.getOpenExpDate());

				//04:제휴온라인 유심미보유건 템플릿 -378   /    02:기존 바로배송 템플릿 205
				String templateId = "04".equals(vo.getDlvryType()) ? "378" : "205";
				KtSmsCommonVO smsVO = smsCommonMapper.getKtTemplateText(templateId);
				String message = smsVO.getTemplateText();

				if ("04".equals(vo.getDlvryType())) {
					message = message
							.replaceAll(Pattern.quote("#{resNo}"), String.valueOf(vo.getResNo()))
							.replaceAll(Pattern.quote("#{cntpntShopNm}"), String.valueOf(vo.getCntpntShopNm()));
				} else {
					message = message
							.replaceAll(Pattern.quote("#{openExpDate}"), String.valueOf(vo.getOpenExpDate()))
							.replaceAll(Pattern.quote("#{resNo}"), String.valueOf(vo.getResNo()));
					//.replaceAll(Pattern.quote("#{Url}"), ...)
				}

				smsVO.setMessage(message);
				
				int iLmsSendCnt = 0;
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02(templateId);
				smsVO.setReserved03("RcpNowDlvryUsimSchedule");
				smsVO.setTemplateId(templateId);
				smsVO.setRcptData(vo.getDstaddr());
				smsVO.setMsgTypeSecond(propertiesService.getString("msg.kakao.ktKNextType"));
				smsVO.setkSenderkey(propertiesService.getString("msg.kakao.ktSenderKey"));
								
				// SMS 발송 등록
				iLmsSendCnt = smsCommonMapper.insertKtMsgTmpQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				sendVO.setTemplateId(templateId);
				sendVO.setMsgId(smsVO.getMsgId());
				sendVO.setSendReqDttm(smsVO.getScheduleTime());
				sendVO.setReqId(BatchConstants.BATCH_USER_ID);
				
				if(iLmsSendCnt > 0 ) {
					//SMS 발송내역 등록
					smsCommonMapper.insertKtSmsSendMst(sendVO);
					
					//20250618 해당 테이블 미사용으로 주석처리
					/*vo.setText(smsVO.getMessage());
					//유심바로배송 개통신청안내 TABLE에 저장
					rcpNowDlvryUsimMapper.insertMspNowDlvrySms(vo);*/
				}
			}
			
			LOGGER.info("온라인신청서 바로배송 배송 완료 후 5일 지난 고객 조회");
			// 온라인신청서 바로배송 배송 완료 후 5일동안 지난 고객 조회(유심번호 미등록 고객)
			List<RcpNowDlvryUsimVO> list2 = rcpNowDlvryUsimMapper.getNotOpenNowDlvry(NOT_OPEN_DT);
			for(RcpNowDlvryUsimVO vo : list2){
				LOGGER.debug("가입신청키=" + vo.getRequestKey());
				LOGGER.debug("고객전화번호=" + vo.getDstaddr());
				LOGGER.debug("개통완료기한=" + vo.getOpenExpDate());
				
				// 신청서상태 변경  >>>   관리자삭제(유심번호 미입력)
				rcpNowDlvryUsimMapper.updateMcpRequestPstate(vo.getRequestKey());
				
			}

		}catch(Exception e){
			throw new MvnoServiceException("ERCP1006", e);
		}
		LOGGER.info("바로배송 온라인 신청서 3일이내 또는 5일이내 USIM 번호 미등록시 미개통 처리 프로세스 종료");
		return procCnt;
	}

}
