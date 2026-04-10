package com.ktis.msp.cmn.smsmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsPhoneGrpVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendResVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsTemplateReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsTemplateResVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("SmsMgmtMapper")
public interface SmsMgmtMapper {
	
	/**
	 * SMS 템플릿 목록 조회
	 * @param vo
	 * @return
	 */
	List<SmsTemplateResVO> getSmsTemplateList(SmsTemplateReqVO vo);
	
	/**
	 * SMS 템플릿 수정
	 * @param vo
	 */
	void updateSmsTemplate(SmsTemplateResVO vo);
	
	/**
	 * SMS 템플릿 등록
	 * @param vo
	 */
	void insertSmsTemplate(SmsTemplateResVO vo);
	
	/**
	 * 신규SMS템플릿ID
	 * @return
	 */
	String getTemplateId();
	
	/**
	 * 신규SMS템플릿ID 확인
	 * @param newTemplateId
	 * @return
	 */
	String checkTemplateId(String newTemplateId);
	
	/**
	 * SMS 템플릿 이력등록
	 * @param vo
	 */
	void insertSmsTemplateHst(SmsTemplateResVO vo);
	
	/**
	 * SMS발송조회
	 * @param vo
	 * @return
	 */
	List<?> getSmsSendList(SmsSendReqVO vo);
	
	/**
	 * MSG_QUEUE 에 등록
	 * @param vo
	 */
	int insertMsgQueue(MsgQueueReqVO vo);
	
	/**
	 * MSG_QUEUE 템플릿에 등록
	 * @param vo
	 */
	int insertMsgTmpQueue(MsgQueueReqVO vo);
	
	/**
	 * 발송내역 등록
	 * @param vo
	 */
	void insertSmsSendMst(SmsSendVO vo);
	
	MsgQueueReqVO getTemplateText(String templateId);
	
	/**
	 * MSG_RESULT 확인
	 * @param vo
	 * @return
	 */
	int checkMsgResult(MsgQueueReqVO vo);
	
	/**
	 * 템플릿 combo
	 * @return
	 */
	List<?> getTemplateCombo(CmnCdMgmtVo vo);
	
	/**
	 * 요금제 combo
	 * @return
	 */
	List<?> getRateCombo();
	
	/**
	 * SMS 수신자선택발송 조회 (개통고객)
	 */
	List<SmsSendResVO> getMspSelectList(SmsSendReqVO vo);
	
	/**
	 * SMS 수신자선택발송 조회 (홈페이지)
	 */
	List<SmsSendResVO> getMcpSelectList(SmsSendReqVO vo);
	
	/**
	 * SMS 수신자선택발송 조회 (신청고객)
	 */
	List<SmsSendResVO> getReqSelectList(SmsSendReqVO vo);
	
	/**
	 * SMS 템플릿 확인
	 */
	SmsSendResVO getSendTemplate(SmsSendResVO vo);
	
	/**
	 * CONTRACT_NUM 조회
	 */
	String getContractNum(String mobileNo);
	
	
	/**
	 * 전화번호그룹 목록
	 */
	List<?> smsSendPhoneGrpList(Map<String, Object> pReqParamMap);
	
	/**
	 * 전화번호그룹 insert
	 */
	int insertSmsSendPhoneGrp(Map<String, Object> pReqParamMap);

	/**
	 * 전화번호그룹 update
	 */
	int updateSmsSendPhoneGrp(Map<String, Object> pReqParamMap);

	/**
	 * 전화번호그룹상세 목록
	 */
	List<?> smsSendPhoneGrpDtl(Map<String, Object> pReqParamMap);
	
	/**
	 * 전화번호그룹상세(기타등록) insert
	 */
	int insertSmsSendPhoneGrpDtlEtc(Map<String, Object> pReqParamMap);
	
	/**
	 * 전화번호그룹상세 insert
	 */
	int insertSmsSendPhoneGrpDtl(Map<String, Object> pReqParamMap);

	/**
	 * 전화번호그룹상세 delete
	 */
	int deleteSmsSendPhoneGrpDtl(Map<String, Object> pReqParamMap);

	/**
	 * 전화번호 그룹 리스트 가져오기
	 */
	List<SmsPhoneGrpVO> getSmsPhoneGrpDtlList(SmsPhoneGrpVO vo);

	/**
	 * 전화번호그룹이력 insert
	 */
	int insertSmsSendPhoneGrpHst(Map<String, Object> pReqParamMap);
	
	/**
	 * 전화번호그룹상세이력(기타등록) insert
	 */
	int insertSmsSendPhoneGrpDtlHstEtc(Map<String, Object> pReqParamMap);

	/**
	 * 전화번호그룹상세이력 insert
	 */
	int insertSmsSendPhoneGrpDtlHst(Map<String, Object> pReqParamMap);
	
	
	
	/***************************************************************************************/
	/* 대량문자 발송 2022.06.27 추가 */
	/***************************************************************************************/
	
	/* 대량문자발송 MsgNo 생성 */
	String msgSmsListMsgNo(SmsSendResVO smsSendResVo);
   /* 대량문자발송 리스트 조회	*/
	List<EgovMap> getMsgSmsSendList(SmsSendResVO smsSendResVo);
   /* 대량문자발송 상세리스트 조회 */
	List<EgovMap> getMsgSmsSendListDt(SmsSendResVO smsSendResVo);	  
    /* 대량문자발송 TEMP insert */
	void insertMsgSmsTmp (SmsSendResVO vo);
	/* 대량문자발송 리스트 삭제 권한 확인 */
	String isMsgSmsDeleteAuth(SmsSendResVO smsSendResVo);
    /* 대량문자발송 리스트 삭제	*/
	int deleteMsgSmsList(SmsSendResVO smsSendResVo);
	/* 대량문자발송 리스트 상세내용 삭제*/
	int deleteMsgSmsListDt(SmsSendResVO smsSendResVo);
	/* 엑셀업로드 후 MSG_SMS_LIST 에 SEND_CNT 업데이트 */
	int getSmsSendListCount (SmsSendResVO smsSendResVo);
	/* 예약전송시간이 겹치기 않게 체크 */
	Map<String, Object> reqTimeCountChk(Map<String, Object> pReqParamMap);
	/* SMS 템플릿 번호 조회해서 일치하는 메세지타입 가져오기 */
	String getMsgSendMsgType(SmsSendResVO smsSendResVo);
	/* SMS 템플릿 K_TEMPLATE_CODE 조회해서 코드값이 등록되어 있는지 확인*/
	String getMsgSendKakaoYn(SmsSendResVO smsSendResVo);
	/* SMS 템플릿 번호 조회해서 일치하는 메세지타입 가져오기 */
	int getSmsTmpCallBack(SmsSendResVO smsSendResVo);

	
	/***************************************************************************************/
	/* 대량문자 발송 2022.06.27 끝 */
	/***************************************************************************************/
	
	/***************************************************************************************/
	/* KT SMS 문자발송 변경 2024.11.26 추가 */
	/***************************************************************************************/
	/**
	 * Kt 메세지 전송 Agent 사용 여부
	 * @param vo
	 */
	String isOtpUseKt();
	
	/**
	 * AM2X_SUBMIT 에 등록
	 * @param vo
	 */
	int insertKtMsgQueue(KtMsgQueueReqVO vo);
	
	/**
	 * AM2X_SUBMIT TMPLATE 에 등록
	 * @param vo
	 */
	int insertKtMsgTmpQueue(KtMsgQueueReqVO vo);

	/**
	 * 발송내역을 재발송내역으로 수정(KT 시스템 
	 * @param vo
	 */
	void updateKtSmsSendMst(KtMsgQueueReqVO vo);
	
	/**
	 * AM2X_SUBMIT_LOG 확인
	 * @param vo
	 * @return
	 */
	int checkKtMsgResult(KtMsgQueueReqVO vo);
	
	/**
	 * AM2X_SUBMIT 삭제
	 * @param vo
	 */
	void deleteKtMsgQueue(KtMsgQueueReqVO vo);
	
	/**
	 * MSP_SMS_SEND_MST 삭제
	 * @param vo
	 */
	void deleteKtSmsSendMst(KtMsgQueueReqVO vo);
	
	KtMsgQueueReqVO getKtTemplateText(String templateId);
	
	void insertMsgDetailDt(KtMsgQueueReqVO vo);
}
