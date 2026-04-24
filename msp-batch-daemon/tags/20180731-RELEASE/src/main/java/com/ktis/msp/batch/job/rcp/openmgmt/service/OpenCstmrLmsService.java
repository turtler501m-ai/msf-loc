package com.ktis.msp.batch.job.rcp.openmgmt.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.openmgmt.mapper.OpenCstmrLmsMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

@Service
public class OpenCstmrLmsService extends BaseService {
	
	@Autowired
	private OpenCstmrLmsMapper OpenMapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	/*
	 * LMS 상태값.
	 */
	final String s0000 = "0000";	// 성공
	final String s9991 = "9991";	// valid 실패
	final String s9992 = "9992";	// 위약금 조회 실패
	final String s9993 = "9993";	// LMS 발송 실패
	
	/**
	 * 개통고객 LMS 전송
	 * @param param
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setOpenCstmrLms() throws MvnoServiceException{
		int iReturn = 0;
		try {
			LOGGER.info("LMS SEND START");
			
//			1. SEQ 조회
			long lSeq = OpenMapper.getOpenCstmrLmsInsertSeq();
			LOGGER.info("개통고객 LMS 전송 : 작업 SEQ => {}", lSeq);

			if(lSeq > 0) {
//				2. SELECT INSERT QRY 수행
				// 단말할인
				int iSetKDCnt = OpenCstmrLmsSelectInsert(lSeq, "KD");
				LOGGER.info("개통고객 LMS 전송(단말할인) : 발송 데이터 추출 갯수 => {}", iSetKDCnt);
				
				// 요금할인
				int iSetPMCnt = OpenCstmrLmsSelectInsert(lSeq, "PM");
				LOGGER.info("개통고객 LMS 전송(요금할인) : 발송 데이터 추출 갯수 => {}", iSetPMCnt);
				
				// 심플할인
				int iSetSMCnt = OpenCstmrLmsSelectInsert(lSeq, "SM");
				LOGGER.info("개통고객 LMS 전송(심플할인) : 발송 데이터 추출 갯수 => {}", iSetSMCnt);

				if(iSetKDCnt > 0 || iSetPMCnt > 0 || iSetSMCnt > 0) {
//					3. LMS 발송 고객 조회
					List<Map<String, Object>> list = OpenMapper.getOpenCstmrLmsList(lSeq);
					LOGGER.info("개통고객 LMS 전송(단말할인, 요금할인, 심플할인) : 발송 데이터 조회 갯수 => {}", list.size());
					
					for(Map<String, Object> rtMap : list) {
						/*
						 * LMS 필수값.
						 */
						String strContractNum = String.valueOf(rtMap.get("CONTRACT_NUM"));
						String strSubscriberNo = String.valueOf(rtMap.get("SUBSCRIBER_NO"));
						/*
						 * 위약금 조회 필수값.
						 */
						String strRateCd = String.valueOf(rtMap.get("RATE_CD"));
						String strReqBuyType = String.valueOf(rtMap.get("REQ_BUY_TYPE"));
						String strModelMonthly = String.valueOf(rtMap.get("MODEL_MONTHLY"));
						String strblDcType = String.valueOf(rtMap.get("BL_DC_TYPE"));
						
//						4. 필수값 체크
						if(valid(strRateCd, strReqBuyType, strModelMonthly, strContractNum, strSubscriberNo)) {
							LOGGER.info("개통고객 LMS 전송 : Validation 성공.");
						}
						
//						5. 해지위약금 조회
						Map<String, Object> rateCanParam = new HashMap<String, Object>();
						rateCanParam.put("contractNum", strContractNum);
						
						Map<String, Object> rateCan = new HashMap<String, Object>(); 
						rateCan = OpenMapper.getMspRateCancelSpec(rateCanParam);
						int iCcAmt12 = 0;
						int iCcAmt18 = 0;
						if(rateCan != null && rateCan.size() > 0) {
							iCcAmt12 = Integer.parseInt(String.valueOf(rateCan.get("CC_AMT12")));
							iCcAmt18 = Integer.parseInt(String.valueOf(rateCan.get("CC_AMT18")));
						}
						
//						if(rateCan != null && rateCan.size() > 0) {
						int strCcAmt12 = 0;
						int strCcAmt18 = 0;
						if(!"KD".equals(strblDcType)) {
							strCcAmt12 = iCcAmt12;
							strCcAmt18 = iCcAmt18;
						} else {
							strCcAmt12 = iCcAmt12 + Integer.parseInt(String.valueOf(rtMap.get("TERMINAL_RATE_CANCEL_FEE_12")));
							strCcAmt18 = iCcAmt18 + Integer.parseInt(String.valueOf(rtMap.get("TERMINAL_RATE_CANCEL_FEE_18")));
						}
						
						LOGGER.info("요금제코드({}), 지원금유형({}), 할부기간({})의 위약금은 12개월 {}원, 18개월 {}원 입니다.", strRateCd, strReqBuyType, strModelMonthly, strCcAmt12, strCcAmt18);
						
//							6(1/2). LMS 발송 INSERT : 발송조건 만족
						String templateId = "8";
						SmsCommonVO smsVO = smsCommonMapper.getTemplateText(templateId);
						smsVO.setSendMessage((smsVO.getTemplateText())
												.replaceAll(Pattern.quote("#{monthModelAmt}"), StringNumbericComma(String.valueOf(rtMap.get("MONTH_MODEL_AMT"))) )
												.replaceAll(Pattern.quote("#{realMdlInstamt}"), StringNumbericComma(String.valueOf(rtMap.get("REAL_MDL_INSTAMT"))) )
												.replaceAll(Pattern.quote("#{modelDiscount}"), StringNumbericComma(String.valueOf(rtMap.get("MODEL_DISCOUNT"))) )
												.replaceAll(Pattern.quote("#{modelInstallment}"), StringNumbericComma(String.valueOf(rtMap.get("MODEL_INSTALLMENT"))) )
												.replaceAll(Pattern.quote("#{modelMonthly}"), String.valueOf(rtMap.get("MODEL_MONTHLY")) )
												.replaceAll(Pattern.quote("#{instCmsn}"), StringNumbericComma(String.valueOf(rtMap.get("INST_CMSN"))) )
												.replaceAll(Pattern.quote("#{monthRateAmt}"), StringNumbericComma(String.valueOf(rtMap.get("MONTH_RATE_AMT"))) )
												.replaceAll(Pattern.quote("#{rateNm}"), String.valueOf(rtMap.get("RATE_NM")) )
												.replaceAll(Pattern.quote("#{baseAmt}"), StringNumbericComma(String.valueOf(rtMap.get("BASE_AMT"))) )
												.replaceAll(Pattern.quote("#{dcAmt}"), StringNumbericComma(String.valueOf(rtMap.get("DC_AMT"))) )
												.replaceAll(Pattern.quote("#{addDcAmt}"), StringNumbericComma(String.valueOf(rtMap.get("ADD_DC_AMT"))) )
												.replaceAll(Pattern.quote("#{monthAmt}"), StringNumbericComma(String.valueOf(rtMap.get("MONTH_AMT"))) )
												.replaceAll(Pattern.quote("#{strCcAmt12}"), StringNumbericComma(String.valueOf(strCcAmt12)) )
												.replaceAll(Pattern.quote("#{strCcAmt18}"), StringNumbericComma(String.valueOf(strCcAmt18)) )
											);
						
						LOGGER.info("개통고객 LMS 전송 내용 : {}", smsVO.getSendMessage());
						
						int iLmsSendCnt = 0;
						
						smsVO.setTemplateId(templateId);
						smsVO.setMobileNo(strSubscriberNo);
						
						// SMS 발송 등록
						iLmsSendCnt = smsCommonMapper.insertTemplateMsgQueue(smsVO);
						
						smsVO.setSendDivision("MSP");
						smsVO.setContractNum(strContractNum);
						smsVO.setReqId(BatchConstants.BATCH_USER_ID);
						// SMS 발송내역 등록
						smsCommonMapper.insertSmsSendMst(smsVO);
						
//							2(2/2). 수행 결과 UPDATE
						if(iLmsSendCnt > 0) {
							LOGGER.info("개통고객 LMS 전송 : LMS 발송 성공.");
							iReturn++;
							OpenCstmrLmsUpdate(strContractNum, s0000, "LMS 발송 성공.", smsVO.getSendMessage());
						} else {
							LOGGER.error("개통고객 LMS 전송 : LMS 발송 실패.");
							OpenCstmrLmsUpdate(strContractNum, s9993, "LMS 발송 실패.", smsVO.getSendMessage());
							continue;
						}
//						} else {
//							LOGGER.error("개통고객 LMS 전송 : 위약금 조회 결과 등록된 값이 존재하지 않습니다.");
////							ERROR 발생, 상태와 메시지 UPDATE 후 다음 LMS 대상으로 이동.
//							OpenCstmrLmsUpdate(strContractNum, s9992, "위약금 조회 결과 등록된 값이 존재하지 않습니다.", null);
//							continue;
//						}
					}
				} else {
					LOGGER.info("개통고객 LMS 전송 : 개통 고객이 존재하지 않습니다.");
				}
			} else {
				LOGGER.error("개통고객 LMS 전송 : 시퀀스 추출 실패하였습니다.");
				throw new Exception("개통고객 LMS 전송 : 시퀀스 추출 실패하였습니다.");
			}
			
			LOGGER.info("LMS SEND END");
		} catch(Exception e) {
			e.printStackTrace();
			throw new MvnoServiceException(e.getMessage());
		}
		return iReturn;
	}
	
	public int OpenCstmrLmsSelectInsert(long lSeq, String strblDcType) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mspRateCancelLmsSeq", lSeq);
		param.put("blDcType", strblDcType);
		int iSetCnt = OpenMapper.setOpenCstmrLmsSelectInsert(param);
		return iSetCnt;
	}
	
	public int OpenCstmrLmsUpdate(String strContract_num, String strState_Cd, String strState_Desc, String strLms) throws Exception {
		int iSetCnt = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("stateCd", strState_Cd);
			param.put("stateDesc", strState_Desc);
			param.put("lms", strLms);
			param.put("contractNum", strContract_num);
			iSetCnt = OpenMapper.setOpenCstmrLmsStateUpdate(param);
		} catch(Exception e) {
			LOGGER.error("개통고객 LMS 전송 : 상태값 UPDATE 수행중 ERROR 발생 => ");
			e.printStackTrace();
		}
		return iSetCnt;
	}
	
	public boolean valid(String strRateCd
						, String strReqBuyType
						, String strModelMonthly
						, String strContractNum
						, String strSubscriberNo) throws Exception {
		if(strContractNum != null && !"".equals(strContractNum)) {
			LOGGER.info("개통고객 LMS 전송 : LMS 필수값(CONTRACT_NUM - 계약번호) => {}", strContractNum);
		} else {
			LOGGER.error("개통고객 LMS 전송 : LMS 필수값(CONTRACT_NUM - 계약번호)이 존재하지 않습니다.");
			throw new Exception("개통고객 LMS 전송 : LMS 필수값(CONTRACT_NUM - 계약번호)이 존재하지 않습니다.");
		}
		
//		RATE_CD, REQ_BUY_TYPE, MODEL_MONTHLY : 해지위약금 조회 필수값 체크
		if(strRateCd != null && !"".equals(strRateCd)) {
			LOGGER.info("개통고객 LMS 전송 : 위약금 조회 필수값(RATE_CD - 요금제코드) => {}", strRateCd);
		} else {
			LOGGER.error("개통고객 LMS 전송 : 위약금 조회 필수값(RATE_CD - 요금제코드)이 존재하지 않습니다.");
//			ERROR 발생, 상태와 메시지 UPDATE 후 다음 LMS 대상으로 이동.
			OpenCstmrLmsUpdate(strContractNum, s9991, "위약금 조회 필수값(RATE_CD - 요금제코드)이 존재하지 않습니다.", null);
			throw new Exception("위약금 조회 필수값(RATE_CD - 요금제코드)이 존재하지 않습니다.");
		}
		
		if(strReqBuyType != null && !"".equals(strReqBuyType)) {
			LOGGER.info("개통고객 LMS 전송 : 위약금 조회 필수값(REQ_BUY_TYPE - 지원금유형) => {}", strReqBuyType);
		} else {
			LOGGER.error("개통고객 LMS 전송 : 위약금 조회 필수값(REQ_BUY_TYPE - 지원금유형)이 존재하지 않습니다.");
//			ERROR 발생, 상태와 메시지 UPDATE 후 다음 LMS 대상으로 이동.
			OpenCstmrLmsUpdate(strContractNum, s9991, "위약금 조회 필수값(REQ_BUY_TYPE - 지원금유형)이 존재하지 않습니다.", null);
			throw new Exception("위약금 조회 필수값(REQ_BUY_TYPE - 지원금유형)이 존재하지 않습니다.");
		}
		
		if(strModelMonthly != null && !"".equals(strModelMonthly)) {
			LOGGER.info("개통고객 LMS 전송 : 위약금 조회 필수값(MODEL_MONTHLY - 할부기간) => {}", strModelMonthly);
		} else {
			LOGGER.error("개통고객 LMS 전송 : 위약금 조회 필수값(MODEL_MONTHLY - 할부기간)이 존재하지 않습니다.");
//			ERROR 발생, 상태와 메시지 UPDATE 후 다음 LMS 대상으로 이동.
			OpenCstmrLmsUpdate(strContractNum, s9991, "위약금 조회 필수값(MODEL_MONTHLY - 할부기간)이 존재하지 않습니다.", null);
			throw new Exception("위약금 조회 필수값(MODEL_MONTHLY - 할부기간)이 존재하지 않습니다.");
		}
		
		if(strSubscriberNo != null && !"".equals(strSubscriberNo)) {
			LOGGER.info("개통고객 LMS 전송 : LMS 필수값(SUBSCRIBER_NO - 전화번호) => " + strSubscriberNo);
		} else {
			LOGGER.error("개통고객 LMS 전송 : LMS 필수값(SUBSCRIBER_NO - 전화번호)이 존재하지 않습니다.");
//			ERROR 발생, 상태와 메시지 UPDATE 후 다음 LMS 대상으로 이동.
			OpenCstmrLmsUpdate(strContractNum, s9991, "LMS 필수값(SUBSCRIBER_NO - 전화번호)이 존재하지 않습니다.", null);
			throw new Exception("LMS 필수값(SUBSCRIBER_NO - 전화번호)이 존재하지 않습니다.");
		}
		return true;
	}
	
	public String StringNumbericComma(String strWon) throws Exception {
		String strCommaNumberic = "";

		try {
			DecimalFormat df = new DecimalFormat("##,###");
			strCommaNumberic = df.format(Integer.parseInt(calcMath(Double.parseDouble(strWon))));
		} catch(Exception e) {
			throw e;
		}
		return strCommaNumberic;
	}
	
	public static String calcMath(double nCalcVal) {
	    return String.valueOf((int)Math.floor(nCalcVal / 10) * 10);
	}
}
