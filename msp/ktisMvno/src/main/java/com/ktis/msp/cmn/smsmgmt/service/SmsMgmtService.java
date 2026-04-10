package com.ktis.msp.cmn.smsmgmt.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsPhoneGrpVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendResVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsTemplateReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsTemplateResVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class SmsMgmtService extends BaseService {
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
    protected EgovPropertyService propertyService;
	
	/**
	 * SMS 템플릿 목록 조회
	 * @param vo
	 * @return
	 */
	public List<SmsTemplateResVO> getSmsTemplateList(SmsTemplateReqVO vo) {
		
		List<SmsTemplateResVO> result = smsMgmtMapper.getSmsTemplateList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regstNm",			"CUST_NAME");
		maskFields.put("rvisnNm",			"CUST_NAME");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	/**
	 * SMS 템플릿 저장(등록,수정,이력등록)
	 * @param vo
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveSmsTemplate(SmsTemplateResVO vo) {
		
		if((vo.getSaveType()).equals("U") && (vo.getTemplateId() == null || "".equals(vo.getTemplateId()))){
			throw new MvnoRunException(-1, "템플릿ID 누락");
		}
		
		if(vo.getTemplateNm() == null || "".equals(vo.getTemplateNm())){
			throw new MvnoRunException(-1, "템플릿명 누락");
		}
		
		if(vo.getMgmtOrgnId() == null || "".equals(vo.getMgmtOrgnId())){
			throw new MvnoRunException(-1, "관리부서 누락");
		}
		
		if(vo.getWorkType() == null || "".equals(vo.getWorkType())){
			throw new MvnoRunException(-1, "업무구분 누락");
		}
		
		if(vo.getCallback() == null || "".equals(vo.getCallback())){
			throw new MvnoRunException(-1, "발신자번호 누락");
		}
		
		if(vo.getExpireHour() == null || "".equals(vo.getExpireHour())){
			throw new MvnoRunException(-1, "만료시간 누락");
		}
		
		if(vo.getSubject() == null || "".equals(vo.getSubject())){
			throw new MvnoRunException(-1, "문자제목 누락");
		}
		
		if(vo.getText() == null || "".equals(vo.getText())){
			throw new MvnoRunException(-1, "문자내용 누락");
		}
		
		if(vo.getSessionUserId() == null || "".equals(vo.getSessionUserId())){
			throw new MvnoRunException(-1, "사용자ID 누락");
		}
		
		// 등록
		if((vo.getSaveType()).equals("I")) {
			
			while(true) {
				String newTemplateId = smsMgmtMapper.getTemplateId();
				String existFlag = smsMgmtMapper.checkTemplateId(newTemplateId);
				if("N".equals(existFlag)) {
					vo.setTemplateId(newTemplateId);
					break;
				}
			}
			
			smsMgmtMapper.insertSmsTemplate(vo);
			
		// 수정
		} else if((vo.getSaveType()).equals("U")) {
			
			smsMgmtMapper.updateSmsTemplate(vo);
			
		}
		
		// 이력등록
		smsMgmtMapper.insertSmsTemplateHst(vo);
		
	}
	
	/**
	 * SMS발송조회
	 * @param vo
	 * @return
	 */
	public List<?> getSmsSendList(SmsSendReqVO vo) {
		if(vo.getSearchStartDate() == null || "".equals(vo.getSearchStartDate())
				|| vo.getSearchEndDate() == null || "".equals(vo.getSearchEndDate())){
			throw new MvnoRunException(-1, "발송요청일자 누락");
		}
		vo.setSearchStartDate(vo.getSearchStartDate()+"000000");
		vo.setSearchEndDate(vo.getSearchEndDate()+"235959");
		
		List<?> result = smsMgmtMapper.getSmsSendList(vo);
		
		// 수신자명, 수신자번호 마스킹처리
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void setSmsSend(String msgId, String userId) {
		
		if(msgId == null || "".equals(msgId)){
			throw new MvnoRunException(-1, "SMS발송 고유값이 존재하지 않습니다.");
		}
		
		KtMsgQueueReqVO msgVO = new KtMsgQueueReqVO();
		msgVO.setReserved01("MSP");
		msgVO.setReserved02("MSP3000013");
		msgVO.setReserved03(userId);
		msgVO.setMsgId(msgId);
		
		smsMgmtMapper.insertMsgDetailDt(msgVO);
	}
	
	/**
	 * 마스킹필드 셋팅(수신자명,수신자번호)
	 * @return
	 */
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("receiveNm", "CUST_NAME");
		maskFields.put("maskMobileNo", "MOBILE_PHO");
		maskFields.put("msgRecvCtn", "MOBILE_PHO");

		maskFields.put("portalUserId",		"SYSTEM_ID");
		maskFields.put("procNm", 			"CUST_NAME");
		maskFields.put("regstId", 			"SYSTEM_ID");
		return maskFields;
	}
	
	/**
	 * SMS템플릿 콤보조회
	 */
	public List<?> getTemplateCombo(CmnCdMgmtVo vo) {
		logger.debug("=======================================================");
		logger.debug("템플릿콤보" + vo.toString());
		logger.debug("=======================================================");
		
		return smsMgmtMapper.getTemplateCombo(vo);
	}
	
	/**
	 * 요금제 콤보조회
	 */
	public List<?> getRateCombo(CmnCdMgmtVo vo) {
		return smsMgmtMapper.getRateCombo();
	}
	
	/**
	 * 수신자선택발송 대상조회
	 */
	public List<SmsSendResVO> getSmsSendReceiveList(SmsSendReqVO searchVO, Map<String, Object> paramMap) {
		logger.debug("=======================================================");
		logger.debug("수신자선택발송조회" + searchVO.toString());
		logger.debug("=======================================================");
		if (searchVO.getSearchCustDiv() == null || "".equals(searchVO.getSearchCustDiv())) {
			throw new MvnoRunException(-1, "고객유형을 선택하세요.");
		}
		
		if ((searchVO.getSearchCode() != null && !"".equals(searchVO.getSearchCode())) 
				&& (searchVO.getSearchValue() == null || "".equals(searchVO.getSearchValue()))) {
				throw new MvnoRunException(-1, "검색어를 입력하세요.");
		}
		
		List<SmsSendResVO> result = new ArrayList<SmsSendResVO>();
		
		if("MSP".equals(searchVO.getSearchCustDiv())) {
			// 가입고객
			result = smsMgmtMapper.getMspSelectList(searchVO);
		} else if("MCP".equals(searchVO.getSearchCustDiv())) {
			// 홈페이지
			result = smsMgmtMapper.getMcpSelectList(searchVO);
		} else {
			// 신청고객
			result = smsMgmtMapper.getReqSelectList(searchVO);
		}
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("custNmMsk", "CUST_NAME");
		maskFields.put("mobileNoMsk", "MOBILE_PHO");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int saveSmsSendReceiveList(List<SmsSendResVO> list, SmsSendResVO searchVO) {
		logger.debug("=======================================================");
		logger.debug("수신자선택발송조회" + searchVO.toString());
		logger.debug("=======================================================");
		
		int resultCnt = 0;
		
		for(SmsSendResVO vo : list) {
			logger.debug("vo=" + vo);
			
			if (vo.getMobileNo() == null || "".equals(vo.getMobileNo())) {
				throw new MvnoRunException(-1, "휴대폰번호를 입력해주십시오.");
			}
			
			if (vo.getSendType() == null || "".equals(vo.getSendType())) {
				throw new MvnoRunException(-1, "발송구분을 선택하세요.");
			}
			
			if("R".equals(vo.getSendType())) {
				if(vo.getRequestTime() == null || "".equals(vo.getRequestTime()))
					throw new MvnoRunException(-1, "예약발송시간을 선택하세요.");
			}else {
				vo.setRequestTime("");
			}
			
			if (vo.getTemplateId() == null || "".equals(vo.getTemplateId())) {
				throw new MvnoRunException(-1, "템플릿을 선택하세요.");
			}
			
			KtMsgQueueReqVO msgVO = new KtMsgQueueReqVO();
			
			msgVO.setRcptData(vo.getMobileNo());
			msgVO.setTemplateId(vo.getTemplateId());
			msgVO.setSendType(vo.getSendType());
			msgVO.setReserved01("MSP");
			msgVO.setReserved02("MSP3000014");
			msgVO.setReserved03(searchVO.getSessionUserId());
			if("R".equals(vo.getSendType())) {
				msgVO.setScheduleTime(vo.getRequestTime());
			}
			
			smsMgmtMapper.insertKtMsgTmpQueue(msgVO);
			
			/*SmsSendVO sendVO = new SmsSendVO();
			
			sendVO.setSendDivision(vo.getCustDiv());
			sendVO.setTemplateId(vo.getTemplateId());
			sendVO.setMseq(msgVO.getMsgId());
			sendVO.setReqId(searchVO.getSessionUserId());
			sendVO.setRequestTime(vo.getRequestTime());
			sendVO.setKtSmsYn("Y");
			if("MSP".equals(vo.getCustDiv())) {
				// CONTRACT_NUM 이 없을때 조회
				if (vo.getContractNum() == null || "".equals(vo.getContractNum())) {
					sendVO.setContractNum(smsMgmtMapper.getContractNum(vo.getMobileNo()));
				} else {
					sendVO.setContractNum(vo.getContractNum());
				}
			} else if("MCP".equals(vo.getCustDiv())) {
				sendVO.setPortalUserId(vo.getPortalId());
			}
			
			smsMgmtMapper.insertSmsSendMst(sendVO);*/
			
			resultCnt++;
		}
		
		return resultCnt;
	}
	
	/**
	 * 템플릿조회
	 */
	public SmsSendResVO getSendTemplate(SmsSendResVO searchVO) {
		if(searchVO.getTemplateId() == null || "".equals(searchVO.getTemplateId())) {
			throw new MvnoRunException(-1, "템플릿ID가 존재하지 않습니다.");
		}
		
		return smsMgmtMapper.getSendTemplate(searchVO);
	}
	
	/** 
	 * SMS 엑셀업로드 발송 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setSmsSendXlsDataComplete(SmsSendResVO voList, SmsSendResVO searchVO) {
		
		int resultCnt = 0;
		
		List<SmsSendResVO> itemList = voList.getItems();
		
		logger.debug("대상건수 : " + itemList.size());
		
		for( SmsSendResVO vo : itemList ) {
			KtMsgQueueReqVO msgVO = new KtMsgQueueReqVO();
			
			msgVO.setRcptData(vo.getMobileNo());
			msgVO.setTemplateId(searchVO.getTemplateId());
			msgVO.setSendType(searchVO.getSendType());
			msgVO.setReserved01("MSP");
			msgVO.setReserved02("MSP3000014");
			msgVO.setReserved03(searchVO.getSessionUserId());
			msgVO.setScheduleTime(searchVO.getRequestTime());
			
			smsMgmtMapper.insertKtMsgTmpQueue(msgVO);
			
			/*MsgQueueReqVO msgVO = new MsgQueueReqVO();
			
			msgVO.setUserId(searchVO.getSessionUserId());
			msgVO.setMobileNo(vo.getMobileNo());
			msgVO.setTemplateId(searchVO.getTemplateId());
			msgVO.setSendType(searchVO.getSendType());
			msgVO.setRequestTime(searchVO.getRequestTime());
			
			smsMgmtMapper.insertMsgTmpQueue(msgVO);*/
			resultCnt++;
		}
		
		logger.debug("처리건수 : " + resultCnt);
		
		return resultCnt;
	}


	/**
	 * 전화번호그룹 목록
	 */
	public List<?> smsSendPhoneGrpList(Map<String, Object> param){
		
		return smsMgmtMapper.smsSendPhoneGrpList(param);
	}
		
	/**
	 * 전화번호그룹 insert
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertSmsSendPhoneGrp(Map<String, Object> param){
		
		return smsMgmtMapper.insertSmsSendPhoneGrp(param);
		
	}

	/**
	 * 전화번호그룹 update
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateSmsSendPhoneGrp(Map<String, Object> param){
		
		return smsMgmtMapper.updateSmsSendPhoneGrp(param);
		
	}
	
	/**
	 * 전화번호그룹상세 목록
	 */
	public List<?> smsSendPhoneGrpDtl(Map<String, Object> param){
		
		List<EgovMap> result = (List<EgovMap>) smsMgmtMapper.smsSendPhoneGrpDtl(param);

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",		"SYSTEM_ID");
		maskFields.put("usrNmMsk",		"CUST_NAME");
		maskFields.put("mblphnNumMsk",	"MOBILE_PHO");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	/**
	 * 전화번호그룹상세(기타등록) insert
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertSmsSendPhoneGrpDtlEtc(Map<String, Object> param){
		
		return smsMgmtMapper.insertSmsSendPhoneGrpDtlEtc(param);
		
	}
	
	/**
	 * 전화번호그룹상세 insert
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertSmsSendPhoneGrpDtl(Map<String, Object> param){
		
		return smsMgmtMapper.insertSmsSendPhoneGrpDtl(param);
		
	}
	
	/**
	 * 전화번호그룹상세 delete
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteSmsSendPhoneGrpDtl(Map<String, Object> param){
		
		return smsMgmtMapper.deleteSmsSendPhoneGrpDtl(param);
		
	}
	
	/**
	 * 전화번호그룹이력 insert
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertSmsSendPhoneGrpHst(Map<String, Object> param){
		
		return smsMgmtMapper.insertSmsSendPhoneGrpHst(param);
		
	}
	
	/**
	 * 전화번호그룹상세이력(기타등록) insert
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertSmsSendPhoneGrpDtlHstEtc(Map<String, Object> param){
		
		return smsMgmtMapper.insertSmsSendPhoneGrpDtlHstEtc(param);
		
	}
	
	/**
	 * 전화번호그룹상세이력 insert
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertSmsSendPhoneGrpDtlHst(Map<String, Object> param){
		
		return smsMgmtMapper.insertSmsSendPhoneGrpDtlHst(param);
		
	}

	/**
	 * 전화번호 그룹 리스트 가져오기
	 * return 01012345678,01022225555 ....
	 */
	public String getSmsPhoneGrpDtlList(List<?> voList) {
		StringBuffer sb = new StringBuffer();
		HashSet<String> hashSetList = new HashSet<String>();	
		try {			
			for(int i=0; i<voList.size(); i++) {
				SmsPhoneGrpVO vo = (SmsPhoneGrpVO) voList.get(i);
				
				List<SmsPhoneGrpVO> phoneList = new ArrayList<SmsPhoneGrpVO>();
				
				phoneList = smsMgmtMapper.getSmsPhoneGrpDtlList(vo);
				
				for(int j=0; j<phoneList.size(); j++){
					hashSetList.add(phoneList.get(j).getMblphnNum());
				}
			}
			List<String> resultList = new ArrayList<String>(hashSetList);
			
			for(int k=0; k<resultList.size(); k++){
				if(k==0){
					sb.append(resultList.get(k));
				} else {
					sb.append(",").append(resultList.get(k));
				}
			}
			return sb.toString();
			
		} catch(Exception e) {
			logger.info(e.getMessage());
			throw new MvnoRunException(-1, "전화번호를 가져오는 중 오류가 발생하였습니다");
		}
		
	}
	
	/***************************************************************************************************************************/
		
		public String msgSmsListMsgNo(SmsSendResVO smsSendResVo , Map<String, Object> pRequestParamMap) {
			
			String msgSmsListMsgNo = "";
			msgSmsListMsgNo = smsMgmtMapper.msgSmsListMsgNo(smsSendResVo);
			
			return msgSmsListMsgNo;
		}
		
		/**
	     * @Description : 리스트 조회
	     * @Return : void
	     * @Author : 박효진
	     * @Create Date : 2022. 07. 08.
	     */
		public List<EgovMap> getMsgSmsSendList(SmsSendResVO smsSendResVo , Map<String, Object> paramMap){
			
			if(smsSendResVo.getSearchGbn() == null || "".equals(smsSendResVo.getSearchGbn())){
				if (smsSendResVo.getSrchStrtDt() == null || "".equals(smsSendResVo.getSrchStrtDt()) ||
						smsSendResVo.getSrchEndDt() == null || "".equals(smsSendResVo.getSrchEndDt())){
							throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
					}
			}
			
			List<EgovMap> list = smsMgmtMapper.getMsgSmsSendList(smsSendResVo);
			
			HashMap<String, String> maskFields =getMaskFields();
			
			maskingService.setMask(list, maskFields, paramMap);
	
			return list;
		}
		

	
		/**
	     * @Description : 상세리스트 조회
	     * @Return : void
	     * @Author : 박효진
	     * @Create Date : 2022. 07. 08.
	     */
		public List<EgovMap> getMsgSmsSendListDt(SmsSendResVO smsSendResVo , Map<String, Object> paramMap){
		
		List<EgovMap> list = smsMgmtMapper.getMsgSmsSendListDt(smsSendResVo);
		
		HashMap<String, String> maskFields =getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);

		return list;
		}
		
		
		/**
	     * @Description : TMP(템플릿) 등록
	     * @Return : void
	     * @Author : 박효진
	     * @Create Date : 2022. 07. 08.
	     */
		@Transactional(rollbackFor=Exception.class)
		public void insertMsgSmsTmp(SmsSendResVO vo) {
			
			/* MSG_TYPE은   1:SMS , 2:SMS URL , 3:MMS , 4:MMS URL  9:카카오 */ 
			String msgType = smsMgmtMapper.getMsgSendMsgType(vo);
			
			/* 템플릿 테이블을 조회하여 등록되지 않은 템플릿ID일시 에러메세지 */
			if(msgType == null || "".equals(msgType)) {		
				throw new MvnoRunException(-1, "사용가능한 템플릿ID를 입력해주세요.");
			}else {			
				/* 화면단에서 카카오여부를 'Y'로 등록했을때 템플릿테이블의 코드값을 조회해서 없으면 등록이 되지 않게끔 함*/
				/** 20230518 PMD 조치 */
				if("Y".equals(vo.getKakaoYn())) {
					/* 템플릿 테이블에서 카카오코드가 등록되어 있는지 체크*/
					String kakaoCode = smsMgmtMapper.getMsgSendKakaoYn(vo);
					if(kakaoCode == null || "".equals(kakaoCode)) {		
						throw new MvnoRunException(-1, "카카오알림톡 탬플릿 코드를 먼저 등록해주세요.");
					}
					vo.setMsgType("6");					
				}else {
					/* MSG_TYPE은   1:SMS , 2:SMS URL , 3:MMS , 4:MMS URL  6:카카오 */ 
					vo.setMsgType(msgType);
				}
				/* 'S'는 엑셀업로드 타입의 S:다른번호 같은내용 D:다른번호 다른내용 */
				vo.setMsgSmsType("S");			
				/* MSG_SMS_LIST에 인서트 */
				smsMgmtMapper.insertMsgSmsTmp(vo);	
				/* MSG_SMS_SEND_LIST의 MSG_NO를 가져와서 MSG_SMS_LIST SEND_CNT(전송요청건수) 업데이트 */
				smsMgmtMapper.getSmsSendListCount(vo);
				/* 템플릿테이블에서 템플릿ID를 조회해서 MSG_SMS_SEND_LIST의 SEND_CTN(발신번호),
				   TITLE(제목) , TEXT(내용) 업데이트 */
				smsMgmtMapper.getSmsTmpCallBack(vo);
				
			}
		}
		
		/**
	     * @Description : MMS 등록
	     * @Return : void
	     * @Author : 박효진
	     * @Create Date : 2022. 07. 08.
	     */
		@Transactional(rollbackFor=Exception.class)
		public void insertMsgSmsSend(SmsSendResVO vo) {
			
			/* 'S'는 엑셀업로드 타입의 S:다른번호 같은내용 D:다른번호 다른내용 */
			vo.setMsgSmsType("D");
			/* MSG_TYPE은   1:SMS , 2:SMS URL , 3:MMS , 4:MMS URL */ 
			vo.setMsgType("3");
			/* MSG_SMS_LIST에 인서트 */
			smsMgmtMapper.insertMsgSmsTmp(vo);	
			/* MSG_SMS_SEND_LIST의 MSG_NO를 가져와서 MSG_SMS_LIST SEND_CNT(전송요청건수) 업데이트 */
			smsMgmtMapper.getSmsSendListCount(vo);
		}
		
		/**
	     * @Description : 리스트삭제
	     * @Return : void
	     * @Author : 박효진
	     * @Create Date : 2022. 07. 08.
	     */
		@Transactional(rollbackFor=Exception.class)
	    public void deleteMsgSmsList(SmsSendResVO smsSendResVo){
			
			if(smsSendResVo.getMsgNo() != null && !"".equals(smsSendResVo.getMsgNo())) {
				if(smsMgmtMapper.isMsgSmsDeleteAuth(smsSendResVo).equals("Y")) {
					// MSG_SMS_SEND_LIST 상세 데이터 삭제
					smsMgmtMapper.deleteMsgSmsListDt(smsSendResVo); 
					// MSG_SMS_LIST 데이터 사용여부 'N' 전송요청건수 0으로 UPDATE
					smsMgmtMapper.deleteMsgSmsList(smsSendResVo);
				} else {
					throw new MvnoRunException(-1, "해당 데이터 삭제 권한이 없습니다.");
				}
			} else {
				throw new MvnoRunException(-1, "정상적인 호출이 아닙니다.");
			}
		}

		
		
		/**
	     * @Description : TEMP 엑셀업로드 테스트
	     * @Return : void
	     * @Author : 박효진
	     * @Create Date : 2022. 07. 08.
	     */	
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> smsTmpRecvOk(List<Object> aryObj,SmsSendResVO searchVO) {
			
			Pattern phonePattern = Pattern.compile("^010\\d{8}$");
		    List<String> invalidNumbers = new ArrayList<String>();
		    Map<String, Object> resultMap = new HashMap<String, Object>();
		    
		    // 사전 데이터 검증 (전체 데이터 검사)
		    for (Object vo : aryObj) {
		        String msgRecvCtn = ((SmsSendResVO) vo).getMsgRecvCtn();
		        Matcher matcher = phonePattern.matcher(msgRecvCtn);

		        if (!matcher.matches()) {
		            invalidNumbers.add(msgRecvCtn);
		        }
		    }

		    // 유효하지 않은 데이터가 있으면 실행 취소
		    if (!invalidNumbers.isEmpty()) {
		    	resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "전화번호 형식이 맞지 않는 데이터가 있습니다 : " + invalidNumbers);
		        throw new MvnoRunException(-1, "전화번호 형식이 맞지 않는 데이터가 있습니다 : " + invalidNumbers);
		    }
			
			String strDBUrl = propertyService.getString("msp.upload.dburl");
			String strDBID = propertyService.getString("msp.upload.dbuser");
	    	String strDBPW = propertyService.getString("msp.upload.dbpass");
	    	  	
	    	if(aryObj.size() > 20000) {
	    		resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "엑셀업로드는  20000건 이하로 등록하세요.");
				throw new MvnoRunException(-1, "엑셀업로드는  20000건 이하로 등록하세요.");
			}	   		
	    
			Connection con = null;
			PreparedStatement pstmt = null ;

			String sql = "INSERT INTO"
					+ " MSG_SMS_SEND_LIST("
					+ "MSG_NO, MSG_RECV_CTN, MSG_TEXT"
					+ ") VALUES (?, ?, ? ) " ;	
			try{
				con = DriverManager.getConnection(strDBUrl,strDBID,strDBPW);				
				pstmt = con.prepareStatement(sql) ;	
				
				String msgNo = 		searchVO.getMsgNo();	
				String msgText = 	" ";
			
				int i = 1;

				for( Object vo : aryObj) {
				
					String msgRecvCtn = ((SmsSendResVO) vo).getMsgRecvCtn();	
					
					pstmt.setString(1, msgNo);			
					pstmt.setString(2, msgRecvCtn);			
					pstmt.setString(3, msgText);
		
					// addBatch에 담기
					pstmt.addBatch();
					// 파라미터 Clear
					pstmt.clearParameters() ;
					
					// OutOfMemory를 고려하여 500건 단위로 커밋
					if( (i % 500) == 0){
							
						// Batch 실행
						pstmt.executeBatch() ;
						
						// Batch 초기화
						pstmt.clearBatch();
						
						// 커밋
						con.commit() ;			
					}			
				}
				// 커밋되지 못한 나머지 구문에 대하여 커밋
				pstmt.executeBatch() ;
				pstmt.clearBatch();
				con.commit() ;
				
				resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
				
			}catch(Exception e){
				try {
					con.rollback() ;
				} catch (SQLException e1) {
					throw new MvnoRunException(-1, "엑셀업로드 오류e1");
				}
				throw new MvnoRunException(-1, "엑셀업로드 오류");
			}finally{
				if (pstmt != null) {
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ex) {
						throw new MvnoRunException(-1, "엑셀업로드 오류ex");
					}
				}
				if (con != null) {
					try {
						con.close();
						con = null;
					} catch(SQLException ey){
						throw new MvnoRunException(-1, "엑셀업로드 오류ey");
					}
				}
			}
			
			return resultMap;
		}
		
		
		
		/**
	     * @Description : MMS 엑셀업로드
	     * @Return : void
	     * @Author : 박효진
	     * @Create Date : 2022. 07. 08.
	     */	
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> smsSendRecvOk(List<Object> aryObj,SmsSendResVO searchVO) {
			
			Pattern phonePattern = Pattern.compile("^010\\d{8}$");
		    List<String> invalidNumbers = new ArrayList<String>();
		    Map<String, Object> resultMap = new HashMap<String, Object>();
		    
		    // 사전 데이터 검증 (전체 데이터 검사)
		    for (Object vo : aryObj) {
		        String msgRecvCtn = ((SmsSendResVO) vo).getMsgRecvCtn();
		        Matcher matcher = phonePattern.matcher(msgRecvCtn);

		        if (!matcher.matches()) {
		            invalidNumbers.add(msgRecvCtn);
		        }
		    }

		    // 유효하지 않은 데이터가 있으면 실행 취소
		    if (!invalidNumbers.isEmpty()) {
		    	resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "전화번호 형식이 맞지 않는 데이터가 있습니다. " + invalidNumbers);
		        throw new MvnoRunException(-1, "전화번호 형식이 맞지 않는 데이터가 있습니다 : " + invalidNumbers);
		    }
		    
			String strDBUrl = propertyService.getString("msp.upload.dburl");
			String strDBID = propertyService.getString("msp.upload.dbuser");
	    	String strDBPW = propertyService.getString("msp.upload.dbpass");
	    				
			Connection con = null;
			PreparedStatement pstmt = null ;		
			
			if(aryObj.size() > 20000) {
				throw new MvnoRunException(-1, "엑셀업로드는  20000건 이하로 등록하세요.");
			}
			
			String sql = "INSERT INTO"
					+ " MSG_SMS_SEND_LIST("
					+ "MSG_NO, MSG_SEND_CTN, MSG_RECV_CTN, MSG_TEXT, MSG_TITLE"
					+ ") VALUES (?, ?, ? , ?, ?) " ;
			
			try{
				
				con = DriverManager.getConnection(strDBUrl,strDBID,strDBPW);
				pstmt = con.prepareStatement(sql);
				
				String msgNo = searchVO.getMsgNo();	
				
				int i = 1;
				
				for(Object vo : aryObj) {
					
					String  msgSendCtn =  ((SmsSendResVO) vo).getMsgSendCtn();		
					String	msgRecvCtn =  ((SmsSendResVO) vo).getMsgRecvCtn();
					String	msgTitle =  ((SmsSendResVO) vo).getMsgTitle();
					String	msgText =  ((SmsSendResVO) vo).getMsgText();
					// 특수문자 변환
					String escapeMsgText = StringEscapeUtils.unescapeHtml(msgText);
					pstmt.setString(1, msgNo);
					pstmt.setString(2, msgSendCtn);
					pstmt.setString(3, msgRecvCtn);
					pstmt.setString(4, escapeMsgText);
					pstmt.setString(5, msgTitle);
					
					// addBatch에 담기
					pstmt.addBatch();
			
					// 파라미터 Clear
					pstmt.clearParameters();
					
					// OutOfMemory를 고려하여 500 건 단위로 커밋
					if( (i % 500) == 0){
							
						// Batch 실행
						pstmt.executeBatch();
						
						// Batch 초기화
						pstmt.clearBatch();
						
						// 커밋
						con.commit();		
					}
				}		
				// 커밋되지 못한 나머지 구문에 대하여 커밋
				pstmt.executeBatch() ;
				pstmt.clearBatch();
				con.commit();
					
			}catch(Exception e){
				try {
					con.rollback() ;
				} catch (SQLException e1) {
					throw new MvnoRunException(-1, "엑셀업로드 오류e1");
				}
				// 20250523 취약점 소스 코드 수정
				logger.error("Connection Exception occurred");
				throw new MvnoRunException(-1, "엑셀업로드 오류");
			}finally{
				if (pstmt != null) {
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ex) {
						throw new MvnoRunException(-1, "엑셀업로드 오류ex");
					}
				}
				if (con != null) {
					try {
						con.close();
						con = null;
					} catch(SQLException ey){
						throw new MvnoRunException(-1, "엑셀업로드 오류ey");
					}
				}
			}
			
			return resultMap;
		}
		
		
		 /**
	     * @Description : 예약전송시간 중복체크
	     * @Return : int
	     * @Author : 박효진
	     * @Create Date : 2022. 07. 08.
	     */	
		public Map<String, Object> reqTimeCountChk(Map<String, Object> param) {	
			 return smsMgmtMapper.reqTimeCountChk(param);
		}

	
	
	
}
