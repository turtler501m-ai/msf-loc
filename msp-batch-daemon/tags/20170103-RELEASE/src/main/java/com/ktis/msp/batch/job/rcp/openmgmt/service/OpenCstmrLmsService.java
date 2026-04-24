package com.ktis.msp.batch.job.rcp.openmgmt.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.openmgmt.mapper.OpenCstmrLmsMapper;

@Service
public class OpenCstmrLmsService extends BaseService {
	
	@Autowired
	private OpenCstmrLmsMapper OpenMapper;
	
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
			LOGGER.info("개통고객 LMS 전송 : 작업 SEQ => " + lSeq);

			if(lSeq > 0) {
//				2. SELECT INSERT QRY 수행
				// 단말할인
				int iSetKDCnt = OpenCstmrLmsSelectInsert(lSeq, "KD");
				LOGGER.info("개통고객 LMS 전송(단말할인) : 발송 데이터 추출 갯수 => " + iSetKDCnt);
				
				// 요금할인
				int iSetPMCnt = OpenCstmrLmsSelectInsert(lSeq, "PM");
				LOGGER.info("개통고객 LMS 전송(요금할인) : 발송 데이터 추출 갯수 => " + iSetPMCnt);
				
				// 심플할인
				int iSetSMCnt = OpenCstmrLmsSelectInsert(lSeq, "SM");
				LOGGER.info("개통고객 LMS 전송(심플할인) : 발송 데이터 추출 갯수 => " + iSetSMCnt);

				if(iSetKDCnt > 0 || iSetPMCnt > 0 || iSetSMCnt > 0) {
//					3. LMS 발송 고객 조회
					List<Map<String, Object>> list = OpenMapper.getOpenCstmrLmsList(lSeq);
					LOGGER.info("개통고객 LMS 전송(단말할인, 요금할인, 심플할인) : 발송 데이터 조회 갯수 => " + list.size());
					
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

						LOGGER.info("요금제코드(" + strRateCd + "), 지원금유형(" + strReqBuyType + "), 할부기간(" + strModelMonthly + ")의 위약금은 12개월" + strCcAmt12 + "원, 18개월" + strCcAmt18 + "원 입니다.");
						
//							6(1/2). LMS 발송 INSERT : 발송조건 만족
						StringBuffer bufSmsMsg = new StringBuffer();
						bufSmsMsg.append("[kt 엠모바일] 무선서비스 계약 표준 안내서\n")
						.append("안녕하세요, 고객님.\n")
						.append("kt 엠모바일 무선서비스 계약 표준 안내 드립니다.\n")
						.append("■ 휴대폰 월 납부액 및 통신요금(VAT포함)\n")
						.append("▶ A. 휴대폰 월 납부액 : "+StringNumbericComma(String.valueOf(rtMap.get("MONTH_MODEL_AMT")))+"원 (금액표기 천단위 구분)\n")
						.append("① 출고가 : "+StringNumbericComma(String.valueOf(rtMap.get("REAL_MDL_INSTAMT")))+"원\n")
						.append("② 단말기지원금 : "+StringNumbericComma(String.valueOf(rtMap.get("MODEL_DISCOUNT")))+"원\n")
						.append("③ 선 납부금 : 0원\n")
						.append("④ 기타할인 : 0원\n")
						.append("⑤ 총 할부원금 : "+StringNumbericComma(String.valueOf(rtMap.get("MODEL_INSTALLMENT")))+"원\n")
						.append("⑥ 총 할부수수료 ("+String.valueOf(rtMap.get("MODEL_MONTHLY"))+"개월 할부, 연 3.24%) : "+StringNumbericComma(String.valueOf(rtMap.get("INST_CMSN")))+"원\n")
						.append("■ B. 통신요금 월 납부액 : "+StringNumbericComma(String.valueOf(rtMap.get("MONTH_RATE_AMT")))+"원\n")
						.append(" - 선택 요금제명 : "+String.valueOf(rtMap.get("RATE_NM"))+"\n");
//						if("PM".equals(String.valueOf(rtMap.get("BASE_AMT")))) {
//							String strDc1 = String.valueOf(rtMap.get("DC_AMT"));
//							String strDc2 = String.valueOf(rtMap.get("ADD_DC_AMT"));
//						} else if("KD".equals(String.valueOf(rtMap.get("BASE_AMT")))) {
//							String strDc1 = String.valueOf(rtMap.get("DC_AMT"));
//							String strDc2 = "0";
//						}
						bufSmsMsg.append("① 월 정액요금("+StringNumbericComma(String.valueOf(rtMap.get("BASE_AMT")))+"원) - 월 요금할인("+StringNumbericComma(String.valueOf(rtMap.get("DC_AMT")))+"원) - 선택약정할인("+StringNumbericComma(String.valueOf(rtMap.get("ADD_DC_AMT")))+"원)\n");
						bufSmsMsg.append("▶C. 월 기본 납부액(A+B) = "+StringNumbericComma(String.valueOf(rtMap.get("MONTH_AMT")))+"원\n")
						.append("■ 해지시 예상 위약금 (VAT포함)\n")
						.append("12개월째 : "+StringNumbericComma(String.valueOf(strCcAmt12))+"원\n")
						.append("18개월째 : "+StringNumbericComma(String.valueOf(strCcAmt18))+"원\n")
						.append(" - 위약금이 부과되는 경우\n")
						.append("① 공시지원금 받고 약정기간 내 해지 시\n")
						.append("② 요금할인 받다가 약정기간 내 해지 시\n")
						.append("③ 기타약정할인 받다가 약정기간 내 해지 시\n")
						.append("④ 공시지원금을 받은 후 요금제 하향 변경 시\n")
						.append("⑤ 요금할인 받다가 중도에 기기변경 시\n")
						.append("※요금할인은 휴대폰 가입 시 공시지원금 대신 요금에서 추가 할인을 해주는 프로모션 혜택입니다.\n")
						.append("※ 기타약정할인은 유심단독상품을 약정가입할 경우 제공하는 할인입니다.\n")
						.append("※ 상기 예상 위약금은 금번에 가입한 서비스에 한하며, 서비스 가입/변경 및 해지 시점에 따라 위약금은 다르게 청구됩니다.\n")
						.append("■ 기타안내사항\n")
						.append("① 유료부가서비스 가입 시 추가요금이 발생하며, 타 상품을 결합하거나 결합을 해지하는 등의 경우 서비스요금은 변동됩니다.\n")
						.append("② 월 초에 데이터를 많이 사용한 상태에서 요금제를 변경할 경우 일할계산방식으로 인해 데이터 초과 사용에 따른 추가 요금이 발생할 수 있습니다.\n")
						.append("③ 해외에서는 데이터를 사용한만큼 과금되므로 출국 전에 정액로밍 상품에 가입하거나 데이터로밍이 차단되도록 설정하시기 바랍니다.\n")
						.append("④ 기본제공량이 없는 표준형 요금제는 데이터를 사용한만큼 과금되므로 데이터 사용을 원천적으로 차단하고자 할 경우 데이터차단 서비스(무료 부가서비스)를 신청하시기 바랍니다.\n")
						.append("⑤ 본 표준안내서는 이용요금, 예상 위약금, 약정조건, 요금할인 등의 중요한 사항을 정리한 것이며, 그 밖의 사항은 가입신청서 등을 반드시 확인하시기 바랍니다.\n");
						String smsMsg = bufSmsMsg.toString();
						String subject = "[kt 엠모바일] 무선서비스 계약 표준 안내서";
						LOGGER.error("개통고객 LMS 전송 내용 : " + smsMsg);
						
						Map<String, Object> paramLMS = new HashMap<String, Object>();
						paramLMS.put("MSG_TYPE", "3");	// 문자유형(1:SMS, 3:MMS)
						paramLMS.put("DSTADDR", strSubscriberNo);
						paramLMS.put("CALLBACK", "18995000");
						paramLMS.put("TEXT_TYPE", "0");	// 텍스트유형(0:PLAIN, 1:HTML)
						paramLMS.put("MSG", smsMsg);
						paramLMS.put("SUBJECT", subject	); //MMS 타이틀
						int iLmsSendCnt = 0;
						iLmsSendCnt = OpenMapper.setLmsInsert(paramLMS);

//							2(2/2). 수행 결과 UPDATE
						if(iLmsSendCnt > 0) {
							LOGGER.info("개통고객 LMS 전송 : LMS 발송 성공.");
							iReturn++;
							OpenCstmrLmsUpdate(strContractNum, s0000, "LMS 발송 성공.", smsMsg);
						} else {
							LOGGER.error("개통고객 LMS 전송 : LMS 발송 실패.");
							OpenCstmrLmsUpdate(strContractNum, s9993, "LMS 발송 실패.", smsMsg);
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
			LOGGER.info("개통고객 LMS 전송 : LMS 필수값(CONTRACT_NUM - 계약번호) => " + strReqBuyType);
		} else {
			LOGGER.error("개통고객 LMS 전송 : LMS 필수값(CONTRACT_NUM - 계약번호)이 존재하지 않습니다.");
			throw new Exception("개통고객 LMS 전송 : LMS 필수값(CONTRACT_NUM - 계약번호)이 존재하지 않습니다.");
		}
		
//		RATE_CD, REQ_BUY_TYPE, MODEL_MONTHLY : 해지위약금 조회 필수값 체크
		if(strRateCd != null && !"".equals(strRateCd)) {
			LOGGER.info("개통고객 LMS 전송 : 위약금 조회 필수값(RATE_CD - 요금제코드) => " + strRateCd);
		} else {
			LOGGER.error("개통고객 LMS 전송 : 위약금 조회 필수값(RATE_CD - 요금제코드)이 존재하지 않습니다.");
//			ERROR 발생, 상태와 메시지 UPDATE 후 다음 LMS 대상으로 이동.
			OpenCstmrLmsUpdate(strContractNum, s9991, "위약금 조회 필수값(RATE_CD - 요금제코드)이 존재하지 않습니다.", null);
			throw new Exception("위약금 조회 필수값(RATE_CD - 요금제코드)이 존재하지 않습니다.");
		}
		
		if(strReqBuyType != null && !"".equals(strReqBuyType)) {
			LOGGER.info("개통고객 LMS 전송 : 위약금 조회 필수값(REQ_BUY_TYPE - 지원금유형) => " + strReqBuyType);
		} else {
			LOGGER.error("개통고객 LMS 전송 : 위약금 조회 필수값(REQ_BUY_TYPE - 지원금유형)이 존재하지 않습니다.");
//			ERROR 발생, 상태와 메시지 UPDATE 후 다음 LMS 대상으로 이동.
			OpenCstmrLmsUpdate(strContractNum, s9991, "위약금 조회 필수값(REQ_BUY_TYPE - 지원금유형)이 존재하지 않습니다.", null);
			throw new Exception("위약금 조회 필수값(REQ_BUY_TYPE - 지원금유형)이 존재하지 않습니다.");
		}
		
		if(strModelMonthly != null && !"".equals(strModelMonthly)) {
			LOGGER.info("개통고객 LMS 전송 : 위약금 조회 필수값(MODEL_MONTHLY - 할부기간) => " + strReqBuyType);
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
