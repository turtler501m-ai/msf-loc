/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.cmn.login.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ktis.msp.base.login.SessionBindingListener;
import com.ktis.msp.base.login.UniqueLoginManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.crypto.encryptor.sha.SHA512Encryptor;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name :
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.02.13           최초생성
 *
 * @author
 * @version 1.0
 * @see
 *
 */


/**
 * <PRE>
 * 1. ClassName	:
 * 2. FileName	: LoginController.java
 * 3. Package	: com.ktis.msp.cmn.login.controller
 * 4. Commnet	:
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:57:21
 * </PRE>
 */
@Controller
public class LoginController   extends BaseController {

	protected Log log = LogFactory.getLog(this.getClass());

	@Autowired
	protected MessageSource messageSource;


	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private SHA512Encryptor  sHA512Encryptor;

	@Autowired
	private LoginService loginService;

	/**
	 * <PRE>
	 * 1. MethodName: loginForm
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:57:30
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/loginForm.do")
	public String loginForm(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{

		log.info("===========================================");
		log.info("======  loginForm ======");
		log.info("===========================================");
		log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		log.info("===========================================");

		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			pRequestParamMap.put("grpId", "DEV0000");
			pRequestParamMap.put("cdVal", "OTP");
			resultMap = loginService.selectAuthUseYN(pRequestParamMap);

			pRequest.setAttribute("otpYn", propertyService.getString("otpYn"));
			pRequest.setAttribute("otpUse", resultMap.get("usgYn"));
			
			pRequestParamMap.put("cdVal", "MAC");
			resultMap = loginService.selectAuthUseYN(pRequestParamMap);
			
			pRequest.setAttribute("macYn", propertyService.getString("macYn"));
			pRequest.setAttribute("macUse", resultMap.get("usgYn"));
			
			pRequest.setAttribute("scanUrl", propertyService.getString("scan.search.url"));
			pRequest.setAttribute("downUrl", propertyService.getString("scan.download.url"));

			return "/cmn/login/loginForm";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * OTP 인증권한 체크
	 */
	@RequestMapping(value="/cmn/login/otpAuthChk.json")
	public String otpAuthChk(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{
		log.info("===========================================");
		log.info("====== OTP 인증권한 체크 ======");
		log.info("===========================================");
		log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try{
			pRequestParamMap.put("grpId", "DEV0002");
			pRequestParamMap.put("cdVal", pRequestParamMap.get("usrId"));
			resultMap = loginService.selectAuthUseYN(pRequestParamMap);
			
		} catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", ""))
			{
				throw new MvnoErrorException(e);
			}
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.clear();
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: loginProcJson
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:  로그인 성공시
	 *                   3-1.session setting
	 *                   3-2. 접근정보 log insert -> sql : cmn.login.insertLog
	 *                   3-3. 최근login일시 update -> sql : cmn.login.updateUserLastLoginDt
	 *                   3-4. 비밀번호 실패 횟수 update -> cmn.login.updateUserPassErrNum
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:57:32
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/loginProc.json")
	public String loginProcJson(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		loginProc(pRequest,pResponse,pRequestParamMap,model);

		resultMap.clear();
		if ( model.get("msg") == null || model.get("msg").equals(""))
		{
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("passChange", model.get("passChange"));
		}else
		{
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", model.get("msg"));
			resultMap.put("type", model.get("type"));
		}


		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.clear();
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	/**
	 * <PRE>
	 * 1. MethodName: loginProc
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:57:36
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cmn/login/loginProc.do")
	public String loginProc(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)

	{
		try {
			//---------------------------------------------------
			// declare local variables
			//---------------------------------------------------
			String lMsg = "";
			String lType = "";
			String lUri = "";
			String lLoginResult = "";
			String lLoginYn = "";

			String dbIdCnt				= "";
			String dbPassCnt			= "";
			String dbOtpCnt				= "";
			String dbUseOtp				= "";
			String dbUsrNm				= "";
			String dbUsrId				= "";
			String dbUsrOrgnId			= "";
			String dbUsrOrgnNm			= "";
			String dbUsrOrgnTypeCd		= "";	// 2015-03-12, 파라미터명 수정
			String dbUsrOrgnLvlCd		= "";
			String dbUsrLogisCnterYn	= "";
			String dbUsrMngrRule		= "";
			String dbUsgEndDt			= "";
			String dbTypeDtlCd1			= "";
			String dbOrgChgYn			= "";	// 2015-03-12, 조직변경가능여부
			
			String dbStopCnt			= "";	// 2016-02-22, 사용자 정지상태
			String dbPassChgYn			= "";	// 2016-09-01, 90일패스워드변경대상여부
			String dbAdminYn			= "";	// 2016-11-18, ADMIN계정여부
			String dbFirstYn			= "";	// 2017-07-26, 처음로그인여부
			
			// [SRM18072573065] ARS 관련 API 연동
			String dbEncUserId			= "";

			String dbOtpInitYn			= "";	// 2018-11-29, OTP 인증실패 3회 이상 여부
			String dbOtpOnetym			= "";	// 2019-03-07, OTP 인증 1일 1회 적용 여부
			String dbTodayLoginYn		= "";   // 2019-03-07, 금일 로그인 여부
			String dbOtpAuthYn		    = "";   // 2019-10-11, OTP 인증권한 여부
			String dbStatusReq			= "";   // 2020-07-28, 정지상태해제요청여부
			String dbUniqueYn			= "";   // 2024-08-21, 중복 로그인 방지 사용여부

			log.info("===========================================");
			log.info("======  loginProc ======");
			log.info("===========================================");
			log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
	//    	printRequest(pRequest);
			log.info("===========================================");

			String lReqUri   = StringUtils.defaultString((String)pRequestParamMap.get("reqUri"),"/main.do");
			String lUsrId    = StringUtils.defaultString((String)pRequestParamMap.get("usrId"),"");


			log.info("===========================================");
			log.info("======  SQL pRequestParamMap ======");
			log.info(">>>>" +  pRequestParamMap.toString());
			log.info("===========================================");

			//---------------------------------
			// 암호 encryption
			// 설정에서 encryption 사용이 true로 되어있는지 check
			//---------------------------------
			String lPassInput = (String) pRequestParamMap.get("pass");
			String lPassEnc = sHA512Encryptor.encrypt((String) pRequestParamMap.get("pass"));
			log.info(">>>> l_passEnc:" +  lPassEnc);
			log.info(">>>> lPassInput:" +  lPassInput);

			if ( propertyService.getString("passEnc").equals("true"))
			{
				pRequestParamMap.remove("pass");
				pRequestParamMap.put("pass", lPassEnc);
			}


			//---------------------------------
			// 사용자 정보 DB select
			//---------------------------------
			List<?> lDbList = loginService.selectLogin(pRequestParamMap);
			log.info("===========================================" + lDbList);

			model.addAttribute("resultList", lDbList);

			//---------------------------------
			// select된 row 가져옴
			//---------------------------------
			Map<String,Object> dbRow = null;

			if ( lDbList.size() > 0 )
			{
				dbRow = (Map<String,Object>)  lDbList.get(0);

				dbIdCnt               = (String)dbRow.get("idCnt");
				dbPassCnt             = (String)dbRow.get("passCnt");
				dbOtpCnt              = (String)dbRow.get("otpCnt");
				dbUseOtp              = (String)dbRow.get("useOtp");
				dbUsrNm               = (String)dbRow.get("usrNm");
				dbUsrId               = (String)dbRow.get("usrId");
				dbUsrOrgnId           = (String)dbRow.get("orgnId");
				dbUsrOrgnNm           = (String)dbRow.get("orgnNm");
				dbUsrOrgnTypeCd       = (String)dbRow.get("typeCd");
				dbUsrOrgnLvlCd        = (String)dbRow.get("orgnLvlCd");
				dbUsrLogisCnterYn     = (String)dbRow.get("logisCnterYn");
				dbUsrMngrRule         = (String)dbRow.get("mngrRule");
				dbUsgEndDt            = (String)dbRow.get("usgEndDt");
				dbTypeDtlCd1          = (String)dbRow.get("typeDtlCd1");
				dbOrgChgYn            = (String)dbRow.get("orgChgYn");
				
				dbStopCnt             = (String)dbRow.get("stopCnt");
				dbPassChgYn			  = (String)dbRow.get("passChgYn");
				dbAdminYn			  = (String)dbRow.get("adminYn");
				dbFirstYn			  = (String)dbRow.get("firstYn");
				
				// [SRM18072573065] ARS 관련 API 연동
				dbEncUserId			  = (String)dbRow.get("encUserId");
				// 2018-11-29, OTP 인증실패 3회 이상 여부
				dbOtpInitYn		      = (String)dbRow.get("otpInitYn");
				// 2019-03-07, OTP 인증 1일 1회 적용 여부
				dbOtpOnetym			  = (String)dbRow.get("otpOnetym");
				// 2019-03-07, 금일 로그인 여부
				dbTodayLoginYn		  = (String)dbRow.get("todayLoginYn");
				// 2019-10-11, OTP 인증권한 여부
				dbOtpAuthYn		      = (String)dbRow.get("otpAuthYn");

				// 2020-08-25, 정지상태해제요청여부
				dbStatusReq	 = (String)dbRow.get("statusReq");

				// 2024-08-21, 중복 로그인 방지 사용여부
				dbUniqueYn	 = (String)dbRow.get("uniqueYn");
			}else
			{
				dbIdCnt = "0";
			}

			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getRequestURI" + pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) );
			//---------------------------------
			// 등록된 사용자가 아닌 경우
			//---------------------------------
			if ( "0".equals(dbIdCnt) ) {

				lMsg = "아이디 또는 비밀번호를 다시 확인하세요.";
				lType = "ID";
				lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
				lLoginResult = "NO_USER_ID";
				lLoginYn = "N";
			//---------------------------------
			// 등록된 사용자인 경우
			//---------------------------------
			}else{
				//---------------------------------
				// 비밀번호가 일치하지 않는 경우
				//---------------------------------
				if ( "0".equals( dbPassCnt) ) {
					lMsg = "아이디 또는 비밀번호를 다시 확인하세요.";
					lType = "PASS";
					lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
					lLoginResult = "WRONG_PASSWORD";
					lLoginYn = "N";
					
				//---------------------------------
				// 사용자가 정지상태인 경우
				//---------------------------------
				} else if(!"0".equals(dbStopCnt)) {
					if("20".equals(dbUsrOrgnTypeCd) && "N".equals(dbStatusReq)){
						lMsg = "5일 이상 로그인 하지 않아 정지된 사용자 입니다.  관리자에게 정지해제 요청을 하시겠습니까?";
						lType = "STATUSREQ";
						lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
						lLoginResult = "USER_STOP_STATUS";
						lLoginYn = "N";
					}else if("20".equals(dbUsrOrgnTypeCd) && "R".equals(dbStatusReq)){
						lMsg = "5일 이상 로그인 하지 않아 정지된 사용자 입니다.  관리자에게 정지해제 요청 중 입니다.";
						lType = "STATUS";
						lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
						lLoginResult = "USER_STOP_STATUS";
						lLoginYn = "N";
					}else{
						lMsg = "5일 이상 로그인 하지 않아 정지된 사용자 입니다. 관리자에게 문의하세요.";
						lType = "STATUS";
						lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
						lLoginResult = "USER_STOP_STATUS";
						lLoginYn = "N";
					}
				//---------------------------------
				// 비밀번호가 일치하는 경우
				//---------------------------------
				} else if( "Y".equals(propertyService.getString("admChkYn")) && "N".equals(dbAdminYn) ) {
					lMsg = "테스트 장비입니다. 운영팀에 사용 문의하세요.";
					lType = "ADMIN";
					lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
					lLoginResult = "NOT_ADMIN_USER";
					lLoginYn = "N";
					
				}else{
					boolean otpPass = true;
					//---------------------------------
					// OTP인증권한자가 아닌경우 2019-10-11
					//---------------------------------
					if("N".equals(dbOtpAuthYn)){
						//---------------------------------
						// OTP일일 인증 통과 대상이 아닌경우
						//---------------------------------
						if( "N".equals(dbOtpOnetym) || ("Y".equals(dbOtpOnetym) && "N".equals(dbTodayLoginYn))) {
							//---------------------------------
							// OTP인증 오류가 3회 이상인 상태인 경우
							//---------------------------------
							if( "Y".equals(propertyService.getString("otpYn")) && "Y".equals(dbUseOtp) && "Y".equals(dbOtpInitYn)) {
								lMsg = "OTP 오류가 3회 이상입니다. 다시 시도해 주세요.";
								lType = "OTPINIT";
								lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
								lLoginResult = "OTP_INIT";
								lLoginYn = "N";
								otpPass = false;
							//---------------------------------
							// OTP가 일치하지 않는 경우
							//---------------------------------
							} else if ( "Y".equals(propertyService.getString("otpYn")) && "Y".equals(dbUseOtp) && "0".equals( dbOtpCnt) ) {
								lMsg = "발송된 인증 번호와 입력하신 인증번호가 일치하지 않습니다.";
								lType = "OTP";
								lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
								lLoginResult = "WRONG_OTP";
								lLoginYn = "N";
								otpPass = false;
							}
						}
					}

					
					if(otpPass) {
						if ( UniqueLoginManager.getInstance().existsLogin(dbUsrId) ) {
							lMsg = "이미 다른 기기에서 접속 중인 사용자입니다. 기존 연결을 해제하시겠습니까?";
							lType = "ING";
							lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
							lLoginResult = "ALREADY_LOGIN";
							lLoginYn = "N";
						} else if ("Y".equals(dbPassChgYn)) {
							lMsg = "90일 이상 패스워드를 변경하지 않았습니다. 패스워드를 변경하세요";
							lType = "OLDPASS";
							lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
							lLoginResult = "OLD_PASS";
							lLoginYn = "N";
						} else if ("Y".equals(dbFirstYn)) {
							lMsg = "패스워드 변경 후 사용 가능합니다.";
							lType = "FIRST";
							lUri = pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + lReqUri;
							lLoginResult = "FIRST_LOGIN";
							lLoginYn = "N";
						//---------------------------------
						// OTP가 일치하는 경우
						//---------------------------------
						} else {
							lLoginResult = "SUCCESS";
							lLoginYn = "Y";
							lMsg = null;
							lUri = lReqUri;
							HttpSession  session = pRequest.getSession(true);

							session.setAttribute("CONNECTION_USER_IDENTITY_ABCDEFGHIJKLMN",    lUsrId);
							session.setAttribute("SESSION_USER_ID",    dbUsrId);
							session.setAttribute("SESSION_USER_NAME",  dbUsrNm);
							session.setAttribute("SESSION_USER_ORGN_ID",  dbUsrOrgnId);
							session.setAttribute("SESSION_USER_ORGN_NM",  dbUsrOrgnNm);
							session.setAttribute("SESSION_USER_ORGN_TYPE_CD",  dbUsrOrgnTypeCd);

							session.setAttribute("SESSION_USER_ORGN_LVL_CD",  dbUsrOrgnLvlCd);
							session.setAttribute("SESSION_USER_LOGIS_CNTER_YN",  dbUsrLogisCnterYn);
							session.setAttribute("SESSION_USER_MNGR_RULE",  dbUsrMngrRule);

							session.setAttribute("SESSION_TYPE_DTL_CD1",  dbTypeDtlCd1);

							session.setAttribute("SESSION_LAST_LOGIN_DT",  dbUsgEndDt);
							session.setAttribute("LOCALE",  Locale.getDefault());

							// 2015-03-12, 조직변경가능여부 추가
							session.setAttribute("SESSION_ORG_CHG_YN", dbOrgChgYn);

							int lSessionPeriodSeconds = Integer.parseInt(StringUtils.defaultString((String)propertyService.getString("sessionPeriodSeconds"),"3600")) ;
							session.setMaxInactiveInterval( lSessionPeriodSeconds );
							
							// 2017-12-26, 타임아웃 추가
							session.setAttribute("SESSION_TIME_OUT", String.valueOf(lSessionPeriodSeconds));
							
							// [SRM18072573065] ARS 관련 API 연동
							session.setAttribute("SESSION_ENC_USER_ID", dbEncUserId);

							// [SR-2024-046] 중복 로그인 방지 기능 개발
							if ( "Y".equals(dbUniqueYn) ) {
								session.setAttribute(dbUsrId, new SessionBindingListener());
							}

							// 2026-01-28 로그인 성공 시 OTP 횟수 초기화
							pRequestParamMap.put("otpChkNum", 0);
							pRequestParamMap.put("lockYn", 'N');
							
							loginService.updateUserOtpChk(pRequestParamMap);
						}
					}
				}
			}

			//---------------------------------
			// 로그인 체크에 대한 결과를 DB에 log로 남김
			//---------------------------------
			String ipAddr = "";
			
			if(pRequest.getHeader("X-Forwarded-For") == null) {
				ipAddr = pRequest.getRemoteAddr();
			} else {
				ipAddr = pRequest.getHeader("X-Forwarded-For");
				
				if(ipAddr !=null && !ipAddr.equals("") && ipAddr.indexOf(",")>-1) {
					ipAddr = ipAddr.split("\\,")[0].trim();
				}
			}
			
			log.info("user ipAddr -------- " + ipAddr);

			pRequestParamMap.put("ipInfo", ipAddr );
			pRequestParamMap.put("macAddr",  "");
			pRequestParamMap.put("succYn", lLoginYn );
			pRequestParamMap.put("regstPrgm", "");
			pRequestParamMap.put("regststId", "");
			pRequestParamMap.put("loginResult", lLoginResult);

			loginService.insertLog(pRequestParamMap);
			
			if (lLoginYn.equals("Y"))
			{
				loginService.updateUserLastLoginDt(pRequestParamMap);
			}

			loginService.updateUserPassErrNum(pRequestParamMap);
			loginService.updateUserOtpErrNum(pRequestParamMap);

			model.addAttribute("msg", lMsg);
			model.addAttribute("type", lType);
			model.addAttribute("uri", lUri);

			if ( propertyService.getString("passChange").equals("true"))
			{
				if( propertyService.getString("initPass").equals(lPassEnc))
				{
					model.addAttribute("passChange", "Y");
				}else
				{
					model.addAttribute("passChange", "N");
				}
			}

			return "/cmn/login/loginProc";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}



	/**
	 * <PRE>
	 * 1. MethodName: logout
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:57:41
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/logout.do"  )
	public String logout(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			Model model)

	{

		log.info("===========================================");
		log.info("======  logout ======");
		log.info("===========================================");
		log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		log.info("===========================================");

		Map<String, String> resultMap = new HashMap<String, String>();

		HttpSession  session = pRequest.getSession(false);
		 if(session != null){

			 session.invalidate();
		 }

		 resultMap.put("code",  messageSource.getMessage("ktis.msp.rtn_code.SESSION_FINISH", null, Locale.getDefault()) );
		 resultMap.put("msg",  messageSource.getMessage("ktis.msp.rtn_code.SESSION_FINISH_MSG", null, Locale.getDefault()) );


		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	@RequestMapping(value="/cmn/login/sessionNullForAjaxReturn.json")
	public  String sessionNullForAjaxReturn(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{

		log.info("===========================================");
		log.info("======  sessionNullForAjaxReturn ======");
		log.info("===========================================");
		log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		log.info("===========================================");

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code",  messageSource.getMessage("ktis.msp.rtn_code.SESSION_FINISH", null, Locale.getDefault()) );
		resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.SESSION_FINISH_MSG", null, Locale.getDefault()) );
		log.info("resultMap:" + resultMap);

		model.addAttribute("result", resultMap);

		  return "jsonView";

	}

	/**
	 * <PRE>
	 * 1. MethodName: noAuthRedirect2
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 29. 오후 4:38:06
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 */
	@RequestMapping(value="/cmn/login/noAuthRedirect2.do")
	public String noAuthRedirect2(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			 @RequestParam Map<String, Object> pRequestParamMap,
			 ModelMap model)
	{

		log.info("===========================================");
		log.info("======  loginForm ======");
		log.info("===========================================");
		log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		log.info("===========================================");
		model.put("pRequestParamMap", pRequestParamMap);
		return "/cmn/login/noAuthRedirect";
	}


	/**
	 * <PRE>
	 * 1. MethodName: noAuthRedirect
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:57:44
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/noAuthRedirect.json")
	public  String noAuthRedirect(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{
		try {
			log.info("===========================================");
			log.info("======  noAuthRedirect ======");
			log.info("===========================================");
			log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			log.info("===========================================");

			//----------------------------------------------------------------
			// return json
			//----------------------------------------------------------------
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()) +"(" + pRequestParamMap.get("noAuthUrl") + ")");

			model.addAttribute("result", resultMap);
			return "jsonView";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * <PRE>
	 * 1. MethodName: otpProcJson
	 * 2. ClassName	: LoginController
	 * 3. Commnet	: 아이디, 패스워드 채크 후 OTP 발송
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:57:32
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/otpProc.json")
	public String otpProcJson(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			String resultMsg = null;
			String sendNo = null;

			String lPassEnc = sHA512Encryptor.encrypt((String) pRequestParamMap.get("pass"));
			if ( propertyService.getString("passEnc").equals("true"))
			{
				pRequestParamMap.remove("pass");
				pRequestParamMap.put("pass", lPassEnc);
			}
			resultMap = loginService.selectLoginChk(pRequestParamMap);
			
			if ( resultMap == null || resultMap.isEmpty() ) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMsg = "아이디 또는 비밀번호를 다시 확인하세요.";
			} else if(Integer.parseInt(resultMap.get("passErrNum").toString()) >= 5){
				resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMsg = "비밀번호 5회 실패로 로그인이 불가능합니다.  관리자에게 문의하세요.";
			} else if(Integer.parseInt(resultMap.get("otpChkNum").toString()) >= 5){ 
				resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMsg = "인증문자 발송 횟수를 초과했습니다. 관리자에게 문의 바랍니다.";
			} else {
				String passYn = (String)resultMap.get("passYn");
				String mobileNum = (String)resultMap.get("mblphnNum");
				String stopYn = (String)resultMap.get("stopYn");
				String openYn = (String)resultMap.get("openYn");
				String openStart = (String)resultMap.get("openStart");
				String openEnd = (String)resultMap.get("openEnd");
				// 2019-03-07, OTP 인증 1일 1회 적용 여부
				String otpOnetym  = (String)resultMap.get("otpOnetym");
				// 2019-03-07, 금일 로그인 여부
				String todayLoginYn = (String)resultMap.get("todayLoginYn"); 
				// 2019-10-11, OTP인증권한여부
				String otpAuthYn = (String)resultMap.get("otpAuthYn"); 
				String statusReq = (String)resultMap.get("statusReq");
				String typeCd = (String)resultMap.get("attcSctnCd");
				
				if ( passYn.equals("Y") ) {
					if(openYn.equals("N")) {
						resultMap.clear();
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
						resultMsg = "업무 시간은 "+openStart+" ~ "+openEnd+" 입니다.";
					} else if (stopYn.equals("Y")) {
						
						if(statusReq.equals("N") && typeCd.equals("20")){
							resultMap.clear();
							resultMap.put("code", "STATUSREQ");
							resultMsg = "5일 이상 로그인 하지 않아 정지된 사용자 입니다.  관리자에게 정지해제 요청을 하시겠습니까?";
						}else if (statusReq.equals("R")&& typeCd.equals("20")){
							resultMap.clear();
							resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
							resultMsg = "5일 이상 로그인 하지 않아 정지된 사용자 입니다.  관리자에게 정지해제 요청 중 입니다.";
						}else{
							resultMap.clear();
							resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
							resultMsg = "5일 이상 로그인 하지 않아 정지된 사용자 입니다. 관리자에게 문의하세요.";
						}
						
					
					} else if (otpOnetym.equals("Y") && todayLoginYn.equals("Y")) {
						resultMap.clear();
						resultMap.put("code", "1TYMOK");
					} else if ("Y".equals(otpAuthYn)) {
						resultMap.clear();
						resultMap.put("code", "OTPPASS");
					} else {
						if (mobileNum.isEmpty()) {
							resultMap.clear();
							resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
							resultMsg = "등록된 휴대폰 번호 정보를 찾을수가 없습니다.";
						} else {
//							20200512 소스코드점검 수정
//							Random random = new Random();
							try {
								Random random = SecureRandom.getInstance("SHA1PRNG");
								String a = String.valueOf(random.nextInt(10));
								String b = String.valueOf(random.nextInt(10));
								String c = String.valueOf(random.nextInt(10));
								String d = String.valueOf(random.nextInt(10));
								String e = String.valueOf(random.nextInt(10));
								String f = String.valueOf(random.nextInt(10));
								sendNo = a+b+c+d+e+f;
							} catch (NoSuchAlgorithmException e1) {
								//System.out.println("Connection Exception occurred");
								//20210722 pmd소스코드수정
								logger.error("Connection Exception occurred");
							}
							
							//20241007 SMS 전송 모듈 변경
							if (loginService.isOtpUseKt() != null && loginService.isOtpUseKt().equals("Y")) {
								KtMsgQueueReqVO vo = new KtMsgQueueReqVO();
								vo.setMsgType("1");
								vo.setMessage("[kt M mobile 인증] 인증 번호는 "+sendNo+"입니다. 3분내에 입력해 주세요.");
								vo.setCallbackNum(propertyService.getString("otp.sms.callcenter"));
								vo.setRcptData(mobileNum);
								vo.setReserved01("MSP");
								vo.setReserved02("OTP");
								vo.setReserved03(pRequestParamMap.get("usrId").toString());
								
								loginService.insertKtMsgQueue(vo);
							} else {
								MsgQueueReqVO vo = new MsgQueueReqVO();
								vo.setMsgType("1");
								vo.setDstaddr(mobileNum);
								vo.setCallback(propertyService.getString("otp.sms.callcenter"));
								vo.setStat("0");
								vo.setText("[kt M mobile 인증] 인증 번호는 "+sendNo+"입니다. 3분내에 입력해 주세요.");
								vo.setExpiretime("7200");
								vo.setOptId("MSP");

								loginService.insertMsgQueue(vo);
							}
							
							pRequestParamMap.put("otp", sendNo);
							loginService.updateOtp(pRequestParamMap);
							
							//OTP 발송 5회일 경우 상태잠금 변경
							int otpChkNum = Integer.parseInt(resultMap.get("otpChkNum").toString())+1;
							pRequestParamMap.put("otpChkNum", otpChkNum);
							pRequestParamMap.put("lockYn", 'N');
							if(otpChkNum > 4){
								pRequestParamMap.put("lockYn", 'Y');
							}
							
							loginService.updateUserOtpChk(pRequestParamMap);

							resultMap.clear();
							resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
						}
					}
					
				} else {
					// 패스워드 오류시 체크로직 추가(20240925)
					if(Integer.parseInt(resultMap.get("passErrNum").toString()) >= 5){
						
						resultMap.clear();
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
						resultMsg = "비밀번호 5회 실패로 로그인이 불가능합니다.  관리자에게 문의하세요.";
					}else{
						pRequestParamMap.put("loginResult", "WRONG_PASSWORD");
						loginService.updateUserPassErrNum(pRequestParamMap);
						int usrCnt  = loginService.selectUsrPwdReset(pRequestParamMap);
						if(usrCnt == 0){
							if(Integer.parseInt(resultMap.get("passErrNum").toString()) == 4){
								//패스워드 초기화 등록
								pRequestParamMap.put("status", "E");
								loginService.insertUsrPwdReset(pRequestParamMap);
							}
						}
						resultMap.clear();
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
						resultMsg = "아이디 또는 비밀번호를 다시 확인하세요.";
					}
				}
			}
			resultMap.put("msg", resultMsg);
			//----------------------------------------------------------------
			// return json
			//----------------------------------------------------------------
			model.clear();
			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		return "jsonView";

	}
	
	/**
	 * <PRE>
	 * 1. MethodName: macProcJson
	 * 2. ClassName	: LoginController
	 * 3. Commnet	: 아이디, 패스워드 채크 후 MAC주소 체크 기준정보 조회
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:57:32
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cmn/login/macProc.json")
	public String macProcJson(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			boolean result = false;
			String resultMsg = null;

			String lPassEnc = sHA512Encryptor.encrypt((String) pRequestParamMap.get("pass"));
			if ( propertyService.getString("passEnc").equals("true"))
			{
				pRequestParamMap.remove("pass");
				pRequestParamMap.put("pass", lPassEnc);
			}
			resultMap = loginService.selectMacLoginChk(pRequestParamMap);

			if ( resultMap == null || resultMap.isEmpty() ) {
				resultMsg = "아이디 또는 비밀번호를 다시 확인하세요.";
			} else {
				String passYn = (String)resultMap.get("passYn");
				String targetYn = (String)resultMap.get("targetYn");
				if ( passYn.equals("Y") ) {
					if (targetYn.equals("Y")) {
						List<?> macInfoList = loginService.selectMacChkInfo();
						for (int i=0;i<macInfoList.size();i++){
							Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
							resultMap.put((String)map.get("cdVal"),map.get("cdDsc"));
						}
							
						resultMap.put("RGST_PRSN_ID", pRequestParamMap.get("usrId"));
					}
					
					result = true;
				} else {
					resultMsg = "아이디 또는 비밀번호를 다시 확인하세요.";
				}
			}

			if (result) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMap.put("msg", resultMsg);
			}

			//----------------------------------------------------------------
			// return json
			//----------------------------------------------------------------
			model.clear();
			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		return "jsonView";

	}
	
	/**
	 * @Description : 사용자정보 수정 팝업 호출(사용자본인)
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 10. 7.
	 */
	@RequestMapping(value="/cmn/login/passChgPopup.do")
	public ModelAndView passChgPopup(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자정보 수정 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.setViewName("cmn/login/pass_chg_popup");
			
			request.setAttribute("usrId", pRequestParamMap.get("usrId"));
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * <PRE>
	 * 1. MethodName: updatePassInfo
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:57:44
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/updatePassInfo.json")
	public  String updatePassInfo(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{
		try {
			log.info("===========================================");
			log.info("======  updatePassInfo ======");
			log.info("===========================================");
			log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			log.info("===========================================");

			//----------------------------------------------------------------
			// return json
			//----------------------------------------------------------------
			Map<String, String> resultMap = new HashMap<String, String>();
			StringUtil util = new StringUtil();
			String pwd = ((String)pRequestParamMap.get("pass")).trim();
			String oldPass = ((String)pRequestParamMap.get("oldPass")).trim();
			
			if(!Pattern.matches("^.*(?=^.{8,20}$)(?=.*[a-zA-Z])(?=.*[!@#$%^*])(?=.*[0-9]).*$", pwd)) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "8자리 이상 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
			} else if (pwd.equals(oldPass)) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "이전 패스워드는 사용할 수 없습니다.");
			} else if (!util.chkContinuePass(pwd)) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "3자리 이상의 연속된 숫자 및 문자는 사용할 수 없습니다.");
			} else if (!util.chkKeyboardContinuePass(pwd)) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "3자리 이상 키보드 자판의 연속된 문자는 사용할 수 없습니다.");
			} else if (!util.chkSamePass(pwd)) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "3자리 이상의 같은 숫자 및 문자는 사용할 수 없습니다.");
			} else {
			
				String lPassEnc = sHA512Encryptor.encrypt(pwd);
				String lOldPassEnc = sHA512Encryptor.encrypt(oldPass);
				
				if ( propertyService.getString("passEnc").equals("true"))
				{
					pRequestParamMap.remove("pass");
					pRequestParamMap.put("pass", lPassEnc);
					pRequestParamMap.remove("oldPass");
					pRequestParamMap.put("oldPass", lOldPassEnc);
				}
				int result = loginService.updatePassInfo(pRequestParamMap);
				
				if(result == 1) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				} else if (result == 0) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "사용중인 패스워드가 일치하지 않습니다." );
				} else {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
				}
			}
			
			model.addAttribute("result", resultMap);
			return "jsonView";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 세션 시간연장
	 */
	@RequestMapping(value="/cmn/loginTime/sessTime.json"  )
	public String sessTime(HttpServletRequest pRequest,
							HttpServletResponse pResponse,
							@RequestParam Map<String, Object> pRequestParamMap,
							Model model){
		
		log.debug("===========================================");
		log.debug("======  시간연장 ======");
		log.debug("===========================================");
		log.debug(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		log.debug("===========================================");
		printRequest(pRequest);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		int sessionSecond = Integer.parseInt(propertyService.getString("sessionPeriodSeconds"));
		
		try{
			HttpSession session = pRequest.getSession(false);
			
			if(session != null){
			
				session.setMaxInactiveInterval(sessionSecond);
				
				resultMap.put("code",  messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			}
			else{
				resultMap.put("code",  messageSource.getMessage("ktis.msp.rtn_code.SESSION_FINISH", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.SESSION_FINISH_MSG", null, Locale.getDefault()) );
			}
			
			model.addAttribute("result", resultMap);
			return "jsonView";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * <PRE>
	 * 1. MethodName: cpntStatusReq
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2020. 7. 28. 오후 3:57:44
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/cpntStatusReq.json")
	public  String cpntStatusReq(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{
		try {
			log.info("===========================================");
			log.info("======  cpntStatusReq ======");
			log.info("===========================================");
			log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			log.info("===========================================");

			//----------------------------------------------------------------
			// return json
			//----------------------------------------------------------------
		
			//대리점/판매점 상위 대리점 매니저의 전화번호 추출 / 조직명 / ID /
			String UsrOrgnNm = "";
			String LoginUsrId = "";
			String MngTelNo = "";
		    String TypeNm = "";
			String Cnt = "";
			
			//String resultMap = ;
			List<?> lDbList = loginService.getUpMngTel(pRequestParamMap);
			
			//---------------------------------
			// select된 row 가져옴
			//---------------------------------
			Map<String,Object> dbRow = null;

			if ( lDbList.size() > 0 )
			{
				dbRow = (Map<String,Object>)  lDbList.get(0);
				UsrOrgnNm            = (String)dbRow.get("orgnNm");
				LoginUsrId             = (String)dbRow.get("usrId");
				MngTelNo               = (String)dbRow.get("mblphnNum");
				TypeNm                  = (String)dbRow.get("orgType");
			}else
			{
				Cnt = "0";
			}
			
			Map<String, String> resultMap = new HashMap<String, String>();	
			if(!"0".equals(Cnt)){
				
			String text = UsrOrgnNm +"("+TypeNm +")" + "의 사용자가 정지해제 요청을 하였습니다.\n"
					             + "사용자계정 : " + LoginUsrId;
			
			//20241031 SMS 전송 모듈 변경
			KtMsgQueueReqVO vo = new KtMsgQueueReqVO();
			vo.setMsgType("2");
			vo.setMessage(text);
			vo.setSubject("[kt 엠모바일] 사용자 계정 정지 해제 요청");
			vo.setCallbackNum("18995000");
			vo.setRcptData(MngTelNo);
			vo.setReserved01("MSP");
			vo.setReserved02("loginForm");
			vo.setReserved03(pRequestParamMap.get("usrId").toString());
			
			loginService.insertKtMsgQueue(vo);
			//CMN_USR_MST UPDATE
		
		   int result = loginService.updateUsrStatusReq(pRequestParamMap);
		   
			if(result == 1) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
					resultMap.put("msg", "정지해제 요청이 완료되었습니다." );
				}  else {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
				}
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "정지해제 요청 할 매니저가 없습니다. 관리자에게 문의하세요" );
			}
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			
			logger.debug("---------------------------------------");
			logger.debug("cpntStatusReq.json. - cpntStatusReq fail!!");
			logger.debug("---------------------------------------");
			//throw new MvnoRunException(-1, "updatePassInfo.json error!!");
			throw new MvnoErrorException(e);
		}
		return "jsonView";
	}

	/**
	 * <PRE>
	 * 1. MethodName: disconnectCurrentLogin
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: 개발팀 김동혁
	 * 5. 작성일	: 2024. 8. 8. 오후 6:34:44
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/disconnectCurrentLogin.json")
	public  String disconnectCurrentLogin(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			log.info("===========================================");
			log.info("======  disconnectCurrentLogin ======");
			log.info("===========================================");
			log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			log.info("===========================================");

			//----------------------------------------------------------------
			// return json
			//----------------------------------------------------------------

			String usrId = (String) pRequestParamMap.get("usrId");
			UniqueLoginManager.getInstance().invalidateLogin(usrId);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "기존 연결이 해제되었습니다. 다시 로그인 해주시기 바랍니다.");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "처리 중 오류가 발생했습니다.");
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	/**
	 * @Description : 패스워드 초기화
	 * @Param  :
	 * @Return : ModelAndView
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 12.
	 */
	@RequestMapping(value="/cmn/login/usrPwdResetPopup.do")
	public ModelAndView usrPwdResetPopup(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("패스워드 초기화 팝업 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			pRequestParamMap.put("grpId", "DEV0000");
			pRequestParamMap.put("cdVal", "OTP");
			resultMap = loginService.selectAuthUseYN(pRequestParamMap);
			
			modelAndView.setViewName("cmn/login/pass_reset_popup");
			modelAndView.getModelMap().addAttribute("otpYn", propertyService.getString("otpYn"));
			modelAndView.getModelMap().addAttribute("otpUse", resultMap.get("usgYn"));
			modelAndView.getModelMap().addAttribute("result",resultMap);
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName: userPwdOtpProc
	 * 2. ClassName	: LoginController
	 * 3. Commnet	: 아이디, 전화번호 체크 후 OTP 발송
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2024. 8. 13.
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/usrOtpProc.json")
	public String usrOtpProc(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model) {
		try {
			Map<String, Object> usrMap = new HashMap<String, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();

			String resultMsg = null;
			String sendNo = null;

			usrMap = loginService.selectUsrChk(pRequestParamMap);

			if( usrMap == null || usrMap.isEmpty() ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMsg = "사용자ID 또는 전화번호를 찾을 수 없습니다. 다시 확인해 주세요.";
			}else if (!"A30040999".equals(usrMap.get("orgnId"))) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMsg = "고객센터 권한 계정만 이용 가능합니다.";
			} else {
				String mobileNum = (String)usrMap.get("mblphnNum");
				String usgYn = (String)usrMap.get("usgYn");
				
				if (usgYn.equals("Y")) {
					try {
						Random random = SecureRandom.getInstance("SHA1PRNG");
						String a = String.valueOf(random.nextInt(10));
						String b = String.valueOf(random.nextInt(10));
						String c = String.valueOf(random.nextInt(10));
						String d = String.valueOf(random.nextInt(10));
						String e = String.valueOf(random.nextInt(10));
						String f = String.valueOf(random.nextInt(10));
						sendNo = a+b+c+d+e+f;
					} catch (NoSuchAlgorithmException e1) {
						logger.error("Connection Exception occurred");
					}
					
					if (loginService.isOtpUseKt() != null && loginService.isOtpUseKt().equals("Y")) {
						KtMsgQueueReqVO vo = new KtMsgQueueReqVO();
						vo.setMsgType("1");
						vo.setMessage("[kt M mobile 인증] 인증 번호는 "+sendNo+"입니다. 3분내에 입력해 주세요.");
						vo.setCallbackNum(propertyService.getString("otp.sms.callcenter"));
						vo.setRcptData(mobileNum);
						vo.setReserved01("MSP");
						vo.setReserved02("OTP");
						vo.setReserved03(pRequestParamMap.get("usrId").toString());
						
						loginService.insertKtMsgQueue(vo);
					} else {
						MsgQueueReqVO vo = new MsgQueueReqVO();
						vo.setMsgType("1");
						vo.setDstaddr(mobileNum);
						vo.setCallback(propertyService.getString("otp.sms.callcenter"));
						vo.setStat("0");
						vo.setText("[kt M mobile 인증] 인증 번호는 "+sendNo+"입니다. 3분내에 입력해 주세요.");
						vo.setExpiretime("7200");
						vo.setOptId("MSP");

						loginService.insertMsgQueue(vo);
					}					
					
					pRequestParamMap.put("otp", sendNo);
					loginService.updateOtp(pRequestParamMap);

					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
				}else{
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
					resultMsg = "사용할 수 없는 상태입니다. 다시 확인해 주세요.";
				}
			}
			resultMap.put("msg", resultMsg);
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		return "jsonView";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: usrPwdOtpChk
	 * 2. ClassName	: LoginController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2024. 8. 13.
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cmn/login/usrOtpChk.json")
	public String usrOtpChk(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model) {
		try {
			//---------------------------------------------------
			// declare local variables
			//---------------------------------------------------
			String lMsg				= "";
			String lType				= "";
			String lLoginResult		= "";
			String dbOtpCnt		= "";
			String dbUseOtp		= "";
			String dbPassChgYn	= "";
			String dbFirstYn		= "";
			String dbOtpInitYn	= "";
			Map<String, Object> resultMap = new HashMap<String, Object>();

			//---------------------------------
			// 사용자 정보 DB select
			//---------------------------------
			List<?> lDbList = loginService.selectUsrOtpChk(pRequestParamMap);
			log.info("===========================================" + lDbList);

			model.addAttribute("resultList", lDbList);

			//---------------------------------
			// select된 row 가져옴
			//---------------------------------
			Map<String,Object> dbRow = null;

			if ( lDbList.size() > 0 )
			{
				dbRow = (Map<String,Object>)  lDbList.get(0);
				dbOtpCnt		= (String)dbRow.get("otpCnt");
				dbUseOtp		= (String)dbRow.get("useOtp");
				dbFirstYn			= (String)dbRow.get("firstYn");
				dbOtpInitYn		= (String)dbRow.get("otpInitYn");
			}

			boolean otpPass = true;
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			//---------------------------------
			// OTP인증 오류가 3회 이상인 상태인 경우
			//---------------------------------
			if( "Y".equals(propertyService.getString("otpYn")) && "Y".equals(dbUseOtp) && "Y".equals(dbOtpInitYn)) {
				lMsg = "OTP 오류가 3회 이상입니다. 다시 시도해 주세요.";
				lType = "OTPINIT";
				lLoginResult = "OTP_INIT";
				otpPass = false;
			//---------------------------------
			// OTP가 일치하지 않는 경우
			//---------------------------------
			} else if ( "Y".equals(propertyService.getString("otpYn")) && "Y".equals(dbUseOtp) && "0".equals( dbOtpCnt) ) {
				lMsg = "발송된 인증 번호와 입력하신 인증번호가 일치하지 않습니다.";
				lType = "OTP";
				lLoginResult = "WRONG_OTP";
				otpPass = false;
			}
			
			if(otpPass) {
				if ("Y".equals(dbFirstYn)) {
					lMsg = "패스워드 변경 후 사용 가능합니다.";
					lType = "FIRST";
					lLoginResult = "FIRST_LOGIN";
				//---------------------------------
				// OTP가 일치하는 경우
				//---------------------------------
				} else {
					lMsg = "OTP인증이 확인되었습니다.";
					lType = "SUCCESS";
					lLoginResult = "SUCCESS";
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				}
			}
			resultMap.put("msg", lMsg);
			resultMap.put("type", lType);
			pRequestParamMap.put("loginResult", lLoginResult);
			
			loginService.updateUserOtpErrNum(pRequestParamMap);
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		return "jsonView";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: usrPwdResetSave
	 * 2. ClassName	: LoginController
	 * 3. Commnet	: 패스워드 초기화 접수
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2024. 8. 19.
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/login/usrPwdResetSave.json")
	public String usrPwdResetSave(
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model) {
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			int usrCnt  = loginService.selectUsrPwdReset(pRequestParamMap);
			if(usrCnt > 0){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "이미 접수된 계정입니다.\n다시 확인하여 주십시요.");
			}else{
				pRequestParamMap.put("status", "R");
				loginService.insertUsrPwdReset(pRequestParamMap);
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "패스워드 초기화 접수가 완료되었습니다.");
			}
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "패스워드 초기화 접수에 실패하였습니다.");
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
