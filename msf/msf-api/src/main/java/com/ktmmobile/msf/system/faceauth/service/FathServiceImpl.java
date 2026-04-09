//////////package com.ktmmobile.mcp.fath.service;
//////////
//////////import com.ktds.crypto.exception.CryptoException;
//////////import com.ktmmobile.mcp.appform.dto.AppformReqDto;
//////////import com.ktmmobile.mcp.appform.dto.OsstFathReqDto;
//////////import com.ktmmobile.mcp.cert.service.CertService;
//////////import com.ktmmobile.mcp.common.constants.Constants;
//////////import com.ktmmobile.mcp.common.dto.UserSessionDto;
//////////import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
//////////import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
//////////import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
//////////import com.ktmmobile.mcp.common.service.FCommonSvc;
//////////import com.ktmmobile.mcp.common.service.SmsSvc;
//////////import com.ktmmobile.mcp.common.util.DateTimeUtil;
//////////import com.ktmmobile.mcp.common.util.EncryptUtil;
//////////import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
//////////import com.ktmmobile.mcp.common.util.SessionUtils;
//////////import com.ktmmobile.mcp.fath.dao.FathDao;
//////////import com.ktmmobile.mcp.fath.dto.FathDto;
//////////import com.ktmmobile.mcp.fath.dto.FathFormInfo;
//////////import com.ktmmobile.mcp.fath.dto.FathSessionDto;
//////////import com.ktmmobile.mcp.mypage.dto.MyNameChgReqDto;
//////////import org.apache.commons.lang.StringUtils;
//////////import org.springframework.beans.factory.annotation.Autowired;
//////////import org.springframework.beans.factory.annotation.Value;
//////////import org.springframework.stereotype.Service;
//////////
//////////import java.util.*;
//////////import java.util.regex.Pattern;
//////////
//////////import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
//////////import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;
//////////import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
//////////
//////////@Service
//////////public class FathServiceImpl implements FathService {
//////////
//////////	@Autowired
//////////	private FathDao fathDao;
//////////
//////////	@Autowired
//////////	private FCommonSvc fCommonSvc;
//////////
//////////	@Autowired
//////////	SmsSvc smsSvc;
//////////
//////////	@Value("${sale.orgnId}")
//////////	private String orgnId;
//////////
//////////	@Autowired
//////////	private CertService certService;
//////////	/** 안면인증 사용여부 조회 */
//////////	@Override
//////////	public String getFathUseYn() {
//////////		String fathUseYn = NmcpServiceUtils.getCodeNm("Constant","fathUseYn");
//////////
//////////		//안면인증 공통코드가 Y인 경우 바로 Y리턴
//////////		if (!"N".equals(fathUseYn)) {
//////////			return fathUseYn;
//////////		}
//////////
//////////		UserSessionDto userSession = SessionUtils.getUserCookieBean();
//////////		if (userSession == null) {
//////////			return fathUseYn;
//////////		}
//////////		//예외아이디인 경우 N이여도 Y로 리턴
//////////		String isExceptionId = NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION, userSession.getUserId());
//////////
//////////		return "Y".equals(isExceptionId) ? "Y" : fathUseYn;
//////////	}
//////////
//////////
//////////	/** 셀프안면인증 URL 정보 조회 */
//////////	@Override
//////////	public FathDto getFathSelfUrl(String uuid) {
//////////		return fathDao.getFathSelfUrl(uuid);
//////////	}
//////////	/** 안면인증 결과 PUSH 조회 */
//////////	@Override
//////////	public FathDto selectFathResltPush(String fathTransacId) {
//////////		return fathDao.selectFathResltPush(fathTransacId);
//////////	}
//////////	
//////////	@Override
//////////	public FathFormInfo getFathFormInfo(String resNo, String operType) {
//////////		if ("MCN".equals(operType)) {
//////////			return fathDao.getNameChgInfo(resNo);
//////////		} else {
//////////			return fathDao.getFathAppForm(resNo);
//////////		}
//////////	}
//////////
//////////	@Override
//////////	public void updateFathTransacIdByResNo(AppformReqDto appformReqDto) {
//////////		if ("MCN".equals(appformReqDto.getOperType())) {
//////////			fathDao.updateNameChgFathTransacId(appformReqDto);
//////////		} else {
//////////			fathDao.updateFathTransacId(appformReqDto);
//////////		}
//////////	}
//////////
//////////	@Override
//////////	public void updateFathCompleteInfo(AppformReqDto appformReqDto) {
//////////		if ("MCN".equals(appformReqDto.getOperType())) {
//////////			fathDao.updateNameChgComplete(appformReqDto);
//////////			
//////////			MyNameChgReqDto myNameChgReqDto = new MyNameChgReqDto();
//////////			myNameChgReqDto.setMcnResNo(appformReqDto.getResNo());
//////////			myNameChgReqDto.setSelfCertType(appformReqDto.getSelfCertType());
//////////			myNameChgReqDto.setSelfIssuExprDt(appformReqDto.getSelfIssuExprDt());
//////////			myNameChgReqDto.setSelfIssuNum(appformReqDto.getSelfIssuNum());
//////////			
//////////			if("NM".equals(appformReqDto.getCstmrType())) { //미성년자
//////////				fathDao.updateNameChgSelfCertInfoMinor(myNameChgReqDto);
//////////			} else {
//////////				fathDao.updateNameChgSelfCertInfo(myNameChgReqDto);
//////////			}
//////////		} else {
//////////			fathDao.updateFathCmpltNtfyDt(appformReqDto);
//////////			if("NM".equals(appformReqDto.getCstmrType())) { //미성년자
//////////				fathDao.updateSelfCertInfoMinor(appformReqDto);
//////////			} else {
//////////				fathDao.updateSelfCertInfo(appformReqDto);
//////////			}
//////////		}
//////////	}
//////////
//////////	@Override
//////////	public void sendMsgFathUrl(String urlAdr, String smsRcvTelNo) {
//////////		MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(443);
//////////		String text = mspSmsTemplateMstDto.getText();
//////////		String smsMsg = text.replaceAll(Pattern.quote("#{url}"), urlAdr);
//////////		smsSvc.sendLms(mspSmsTemplateMstDto.getSubject(), smsRcvTelNo, smsMsg,
//////////				mspSmsTemplateMstDto.getCallback(),String.valueOf(443),"SYSTEM");
//////////	}
//////////
//////////	@Override
//////////	public String findAndSaveSessionCpntId(String cntpntShopId) {
//////////		String cpntId = SessionUtils.getFathSession().getCpntId();
//////////		if (!StringUtils.isEmpty(cpntId)) {
//////////			return cpntId;
//////////		}
//////////
//////////		if (orgnId.equals(cntpntShopId)) {
//////////			cpntId = cntpntShopId;
//////////		} else { //직영온라인이 아닌 경우
//////////			//(대리점)안면인증 가능 접점ID조회
//////////			cpntId = fathDao.getCpntId(cntpntShopId);
//////////		}
//////////		SessionUtils.saveFathCpntId(cpntId);
//////////
//////////		return cpntId;
//////////	}
//////////	
//////////	@Override
//////////	public String generateTempResNo() {		
//////////		return fathDao.generateTempResNo();
//////////	}
//////////	@Override
//////////	public void updateFathMcpRequestOsst(String resNo) {
//////////		String tempResNo = SessionUtils.getFathSession().getTempResNo();
//////////		if(StringUtils.isEmpty(tempResNo)) {
//////////			throw new McpCommonJsonException("0005", FATH_CERT_EXPIR_EXCEPTION);
//////////		}
//////////		FathDto fathDto = new FathDto();
//////////		fathDto.setResNo(resNo);
//////////		fathDto.setTempResNo(tempResNo);
//////////		fathDao.updateFathMcpRequestOsst(fathDto);
//////////	}
//////////	
//////////	@Override
//////////	public FathSessionDto validateFathSession() {
//////////		FathSessionDto fathSessionDto = SessionUtils.getFathSession();
//////////		if(StringUtils.isEmpty(fathSessionDto.getTransacId())) {
//////////			throw new McpCommonJsonException("0005", FATH_CERT_EXPIR_EXCEPTION);
//////////		}
//////////		
//////////		return fathSessionDto;
//////////	}
//////////
//////////	@Override
//////////	public HashMap<String, Object> fathResltRtn(String resltCd, OsstFathReqDto osstFathReqDto){
//////////			
//////////		/* 
//////////		0000 안면인증 필수
//////////		CD01 안면인증 불필요 - 오픈일자 미도래
//////////		CD02 안면인증 불필요 - 안면인증 스킵 권한 보유자
//////////		CD03 안면인증 미오픈 대면 사업자 (2026-02-11 기준 미사용)
//////////		CD04 안면인증 불필요 - MIS 장애 스킵 개통 불가
//////////		CD05 안면인증 필수 - MIS 장애 스킵
//////////		CD06 안면인증 불필요 - 안면인증 미대상 접점
//////////		CD07 안면인증 불필요 - 안면인증 미대상 신분증
//////////		CD08 안면인증 불필요 - 안면인증 실패 스킵 권한 보유자
//////////		CD09 안면인증 불필요 - 대리인 개통 안면인증 스킵 권한 보유자
//////////		* 안면인증 필수 = 오더 처리 시 안면인증트랜잭션ID 수록
//////////		* 응답값 CD02 / CD08 / CD09 의 경우, 온라인 개통/명변/우수기변 시 KOS를 통해서 처리 필수(MP 통해 오더 업무 처리 불가)
//////////		* 응답값 CD04 의 경우, MIS장애로 오더 업무 불가한 상태.  이후 MIS장애가 해소되면 안면인증 대상여부 조회부터 재수행 필수.
//////////		*/
//////////
//////////		HashMap<String, Object> rtnMap = new HashMap<>();
//////////		
//////////		
//////////		if(!"P".equals(osstFathReqDto.getGubun())) { //신청서 작성시점인 경우 (개인화 URL은 해당되지않음)
//////////			String stbznPerdYn = SessionUtils.getFathSession().getStbznPerdYn();
//////////			String onOffType = osstFathReqDto.getOnOffType();
//////////			if(StringUtils.isEmpty(stbznPerdYn)) {
//////////				rtnMap.put("RESULT_CODE", "9991");
//////////				rtnMap.put("RESULT_MSG", FATH_CERT_EXPIR_EXCEPTION);
//////////				return rtnMap;
//////////			}
//////////			if("N".equals(stbznPerdYn) && ("5".equals(onOffType) || "7".equals(onOffType))) {	//안정화 기간 미적용이고 셀프개통인 경우
//////////				List<String> happyCallList = Arrays.asList("CD02", "CD04", "CD08", "CD09"); //셀프개통 불가 -> 해피콜신청서로 전환 
//////////				if(happyCallList.contains(resltCd)) {
//////////					rtnMap.put("RESULT_CODE", "00001");
//////////					rtnMap.put("RESULT_MSG", "시스템에 문제가 발생하여 현재 상담사 개통 신청만 가능합니다. <br>상담사 개통신청으로 진행합니다.");
//////////					return rtnMap;
//////////				}
//////////			}
//////////		}		
//////////		
//////////		//CD02 안면인증 불필요 - 안면인증 스킵 권한 보유자는 상담사 개통 시 FS8, FS9 수행을 위해 안면인증 로직을 적용함
//////////		List<String> trgList = Arrays.asList("0000", "CD02", "CD05"); //안면인증 적용대상 
//////////		if(trgList.contains(resltCd)) {
//////////			rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//////////		} else {
//////////			rtnMap.put("RESULT_CODE", "00002");
//////////			rtnMap.put("RESULT_MSG", "안면인증 없이 신분증정보 입력으로 진행됩니다. <br> 향후 추가 인증이 필요할 수 있습니다.");
//////////		}
//////////		return rtnMap;
//////////	
//////////		
//////////	}
//////////
//////////	@Override
//////////	public HashMap<String, Object> fathSuccRtn(FathDto fathDto, OsstFathReqDto osstFathReqDto){
//////////
//////////		HashMap<String, Object> rtnMap = new HashMap<>();
//////////		// 안면인증일이 존재하지 않는경우 
//////////		String fathCmpltNtfyDt = fathDto.getFathCmpltNtfyDt();
//////////		
//////////		if(StringUtils.isEmpty(fathCmpltNtfyDt)) {
//////////			throw new McpCommonJsonException("0005", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
//////////		}
//////////
//////////		// 안면인증 결과와 신청정보 이름, 주민번호 비교
//////////		String fathCstmrName = fathDto.getCustNm();
//////////		String fathCstmrRrn = fathDto.getCustIdfyNo();
//////////
//////////		//안면인증 정보 
//////////		if (StringUtils.isEmpty(fathCstmrName) || StringUtils.isEmpty(fathCstmrRrn)) {
//////////			throw new McpCommonJsonException("9996", "안면인증 정보가 누락되었습니다.");
//////////		}
//////////		
//////////		if("P".equals(osstFathReqDto.getGubun())) { 
//////////			// 개인화URL(셀프안면인증)은 신청서와 비교 
//////////			if(StringUtils.isEmpty(osstFathReqDto.getOperType())){
//////////				throw new McpCommonJsonException("9995", F_BIND_EXCEPTION);
//////////			}
//////////			//신청서 조회
//////////			FathFormInfo fathFormInfo = this.getFathFormInfo(osstFathReqDto.getResNo(), osstFathReqDto.getOperType());
//////////			if(fathFormInfo == null) {
//////////				throw new McpCommonJsonException("9994", F_BIND_EXCEPTION);
//////////			}
//////////			String rcpCstmrRrn = ""; //신청서 주민번호 
//////////			try {
//////////				rcpCstmrRrn = EncryptUtil.ace256Dec(fathFormInfo.getCstmrRrn());     //신청서 주민번호 (신청서 이름은 암호화안되어있음)	
//////////			} catch (CryptoException e) {
//////////				throw new McpCommonJsonException("0001", ACE_256_DECRYPT_EXCEPTION);
//////////			}
//////////			String rcpCstmrName = fathFormInfo.getCstmrName();  //신청서 이름
//////////			//신청서 정보 
//////////			if (StringUtils.isEmpty(rcpCstmrName) || StringUtils.isEmpty(rcpCstmrRrn)) {
//////////				throw new McpCommonJsonException("9993", "신청서 고객 정보가 누락되었습니다.");
//////////			}
//////////			// 이름 비교
//////////			if (!fathCstmrName.trim().equals(rcpCstmrName.trim())) {
//////////				throw new McpCommonJsonException("1001", "안면인증 고객명과 신청서 고객명이 일치하지 않습니다.");
//////////			}
//////////			// 주민번호 비교
//////////			if (!fathCstmrRrn.equals(rcpCstmrRrn)) {
//////////				throw new McpCommonJsonException("1002", "안면인증 주민번호와 신청서 주민번호가 일치하지 않습니다.");
//////////			}
//////////			osstFathReqDto.setCstmrType(fathFormInfo.getCstmrType());
//////////			 
//////////		} else {
//////////			// 신청서 작성 (예약번호 미존재)건의 경우 STEP 비교
//////////			String ncType = "";
//////////			if("MCN".equals(osstFathReqDto.getOperType())) {
//////////				//명의변경 양수인이 미성년자면 3, 성인이면 1
//////////				if("NM".equals(osstFathReqDto.getCstmrType())) {
//////////					ncType = "3";
//////////				} else {
//////////					ncType = "1";
//////////				}
//////////
//////////			} else {
//////////				//명변이아니면 성인이면 빈값 , 미성년자면 1 
//////////				if("NM".equals(osstFathReqDto.getCstmrType())) {
//////////					ncType = "1";
//////////				} else {
//////////					ncType = "";
//////////				}
//////////			}
//////////			
//////////			// ============ STEP START ============
//////////			String[] certKey = {"urlType", "ncType", "name", "birthDate"};
//////////			String[] certValue = {"reqFathTxnRetv", ncType, fathCstmrName, fathCstmrRrn};
//////////
//////////			Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//////////			if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//////////				throw new McpCommonJsonException("9992", vldReslt.get("RESULT_DESC"));
//////////			}
//////////			//  =============== STEP END ===============
//////////		}
//////////		SessionUtils.saveFathCmpltNtfyDt(fathCmpltNtfyDt);  // 안면인증일 세션저장
//////////		
//////////		String fathIdcardTypeCd = fathDto.getRetvCdVal(); //신분증종류  (I : 주민등록증, D:운전면허증)
//////////		String selfCertType = "";
//////////		switch (fathIdcardTypeCd) {
//////////			case "I":
//////////				selfCertType = "01";    // 주민등록증
//////////				break;
//////////			case "D":
//////////				selfCertType = "02";    // 운전면허증
//////////				break;
//////////			default:
//////////				selfCertType = "";
//////////		}
//////////		String selfIssuExprDt = fathDto.getIssDateVal();  //발급일자
//////////		String driverSelfIssuNum = fathDto.getDriveLicnsNo();//운전면허번호
//////////		
//////////		rtnMap.put("RESULT_CODE",  AJAX_SUCCESS);
//////////		rtnMap.put("RESULT_MSG", "안면인증 결과확인 성공입니다.");
//////////		rtnMap.put("selfCertType", selfCertType);                //신분증타입
//////////		rtnMap.put("selfIssuExprDt", selfIssuExprDt);            //발급일자
//////////		rtnMap.put("driverSelfIssuNum", driverSelfIssuNum);      //운전면허번호
//////////		// 셀프안면인증 개인화 URL인 경우 
//////////		if("P".equals(osstFathReqDto.getGubun())) {
//////////			AppformReqDto appformReqDto = new AppformReqDto();
//////////			appformReqDto.setResNo(osstFathReqDto.getResNo());
//////////			appformReqDto.setCstmrType(osstFathReqDto.getCstmrType());
//////////			appformReqDto.setFathCmpltNtfyDt(SessionUtils.getFathSession().getCmpltNtfyDt());
//////////			appformReqDto.setSelfCertType(String.valueOf(rtnMap.get("selfCertType")));
//////////			appformReqDto.setSelfIssuExprDt(String.valueOf(rtnMap.get("selfIssuExprDt")));
//////////			appformReqDto.setSelfIssuNum(String.valueOf(rtnMap.get("driverSelfIssuNum")));
//////////			appformReqDto.setOperType(osstFathReqDto.getOperType());
//////////			this.updateFathCompleteInfo(appformReqDto);
//////////		}
//////////		
//////////		return rtnMap;
//////////	}
//////////
//////////	@Override
//////////	public HashMap<String, Object> fathFailRtn(OsstFathReqDto osstFathReqDto) {
//////////		HashMap<String, Object> rtnMap = new HashMap<>();
//////////		
//////////		if("P".equals(osstFathReqDto.getGubun())) {
//////////			rtnMap.put("RESULT_CODE", "00002");
//////////			rtnMap.put("RESULT_MSG", "안면인증 실패 하였습니다. <br> 향후 추가 인증이 필요할 수 있습니다.  <br>(개통은 계속 진행 됩니다.)");
//////////			return rtnMap;
//////////		}
//////////		
//////////		String stbznPerdYn = SessionUtils.getFathSession().getStbznPerdYn();
//////////		String onOffType = osstFathReqDto.getOnOffType();
//////////		if(StringUtils.isEmpty(stbznPerdYn)) {
//////////			throw new McpCommonJsonException("9991", FATH_CERT_EXPIR_EXCEPTION);
//////////		}
//////////
//////////		if("5".equals(onOffType) || "7".equals(onOffType)) { //셀프개통인 경우
//////////			if("Y".equals(stbznPerdYn)) {
//////////				rtnMap.put("RESULT_CODE", "00002");
//////////				rtnMap.put("RESULT_MSG", "안면인증 실패 하였습니다. <br> 향후 추가 인증이 필요할 수 있습니다.  <br>(개통은 계속 진행 됩니다.)");	
//////////			} else {
//////////				rtnMap.put("RESULT_CODE", "00001");
//////////				rtnMap.put("RESULT_MSG", "안면인증 실패 하여<br> 현재 상담사 개통 신청만 가능합니다. <br>상담사 개통신청으로 진행합니다.");
//////////			}
//////////		} else { //상담사 개통인 경우 
//////////			rtnMap.put("RESULT_CODE", "00002");
//////////			rtnMap.put("RESULT_MSG", "안면인증 실패 하였습니다. <br> 향후 추가 인증이 필요할 수 있습니다.  <br>(개통은 계속 진행 됩니다.)");
//////////		}
//////////		return rtnMap;
//////////	}
//////////
//////////	@Override
//////////	public void updateNameChgFathSkip(String resNo) {
//////////		fathDao.updateNameChgFathSkip(resNo);
//////////	}
//////////}
