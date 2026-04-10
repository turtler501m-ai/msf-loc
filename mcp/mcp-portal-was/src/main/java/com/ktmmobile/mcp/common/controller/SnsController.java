package com.ktmmobile.mcp.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;
import com.ktmmobile.mcp.app.dto.AppInfoDTO;
import com.ktmmobile.mcp.app.service.AppSvc;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.SnsLoginDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpAutoLoginTxnDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.service.KakaoLoginSvc;
import com.ktmmobile.mcp.common.service.LoginSvc;
import com.ktmmobile.mcp.common.service.NaverLoginSvc;
import com.ktmmobile.mcp.common.service.SnsSvc;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.service.JoinSvc;

import net.sf.json.JSONObject;

/**
 * SNS 연동 Controller
 * @author jsh
 * @Date : 2021.12.30
 */
@Controller
public class SnsController {

	private static final Logger logger = LoggerFactory.getLogger(SnsController.class);

	@Autowired
	private SnsSvc snsSvc;

	@Autowired
	private LoginSvc loginSvc;

	@Autowired
	JoinSvc joinSvc;

	@Autowired
	private NaverLoginSvc naverLoginSvc;

	@Autowired
	private KakaoLoginSvc kakaoLoginSvc;

	@Autowired
	private AppSvc appSvc;

	/**
	 * 설명 : 네이버 로그인 URL 조회
	 * @Author : jsh
	 * @Date : 2021.12.30
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/login/getNaverAuthUrl.do", "/m/login/getNaverAuthUrl.do"})
	public @ResponseBody String getNaverAuthUrl(HttpSession session, HttpServletRequest request) {
		String reqUrl = naverLoginSvc.getAuthorizationUrl(session);

		session.setAttribute("snsLoginPreUrl", request.getHeader("referer"));
		return reqUrl;
	}

	/**
	 * 설명 : 네이버 연동 정보 조회
	 * @Author : jsh
	 * @Date : 2021.12.30
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value ={"/login/naverCalbkUrl.do", "/m/login/naverCalbkUrl.do"})
	public String naverCalbkUrl(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, ParseException {

		try {
			JSONParser parser = new JSONParser();
			Gson gson = new Gson();

			HttpSession session = request.getSession();
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			String error = request.getParameter("error");

			// 로그인 팝업창에서 취소버튼 눌렀을경우
			if ( error != null ){
				if(error.equals("access_denied")){
					model.addAttribute("resultCd", "-1");
					model.addAttribute("msg", "사용자 취소");
					model.addAttribute("returnUrl", "/loginForm.do");
					if("Y".equals(NmcpServiceUtils.isMobile())){
						model.addAttribute("returnUrl", "/m/loginForm.do");
					}
					return "/mobile/login/snsCalbk";

				}
			}

			OAuth2AccessToken oauthToken;
			oauthToken = naverLoginSvc.getAccessToken(session, code, state);
			//로그인 사용자 정보를 읽어온다.
			String loginInfo = naverLoginSvc.getUserProfile(session, oauthToken);

			// JSON 형태로 변환
			Object obj = parser.parse(loginInfo);
			JSONObject jsonObj = JSONObject.fromObject(gson.toJson(obj));
			JSONObject callbackResponse = (JSONObject) jsonObj.get("response");

			if (callbackResponse.get("id") != null && !callbackResponse.get("id").equals("")) {
				String naverUniqueNo = callbackResponse.get("id").toString();
				String snsMobileNo = callbackResponse.get("mobile").toString().replaceAll("-", "");
				String snsGender = callbackResponse.get("gender").toString();
				String snsEmail = callbackResponse.get("email").toString();
				String snsBirthday = "";
				if(callbackResponse.get("birthyear") != null && !"".equals(callbackResponse.get("birthday").toString())) {
					snsBirthday = (callbackResponse.get("birthyear").toString() + callbackResponse.get("birthday").toString()).replaceAll("-", "");
				}

				SnsLoginDto snsLoginDto = new SnsLoginDto();
				snsLoginDto = snsSvc.selectSnsIdCheck(naverUniqueNo);

				if(snsLoginDto != null) {
					//로그인 처리 필요
					UserSessionDto userSessionDto = snsSvc.selectSnsMcpUser(snsLoginDto.getUserId());

					//휴면계정 조회
					UserSessionDto param = new UserSessionDto();
					param.setUserId(snsLoginDto.getUserId());
					UserSessionDto dormancyUserDto = loginSvc.dormancyLoginProcess(param);

					if(userSessionDto == null && dormancyUserDto == null) {
						ResponseSuccessDto responseSuccessDto = noSnsInfo(snsLoginDto);

						model.addAttribute("resultCd", "-2");
						model.addAttribute("msg", responseSuccessDto.getSuccessMsg());
						model.addAttribute("returnUrl", "/loginForm.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/loginForm.do");
						}

						return "/mobile/login/snsCalbk";

					}else {

						if(dormancyUserDto != null) {
							SessionUtils.invalidateSession();
							SessionUtils.saveDormancySession(dormancyUserDto);	//휴면계정 세션저장

							model.addAttribute("resultCd", "-2");
							model.addAttribute("msg", "kt M모바일을 오랫동안 이용하지 않아 아이디가</br>휴면 상태로 전환되었습니다.</br></br>본인인증을 통해 해제 후 서비스 이용 부탁드립니다.");
							model.addAttribute("returnUrl", "/login/dormantUserView.do");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/login/dormantUserView.do");
							}
							return "/mobile/login/snsCalbk";

						}

		    			String resltCd = snsSvc.snsLoginProcess(request, userSessionDto, naverUniqueNo);
						String redirectPage = "/main.do";
						if("Y".equals(NmcpServiceUtils.isMobile())){
							redirectPage = "/m/main.do";
						}

						setSnsLoginCookie(response, "NAVER");

		    			if("-1".equals(resltCd)) {
							model.addAttribute("resultCd", "-3");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", "/pwChgInfoView.do");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/pwChgInfoView.do");
							}
							return "/mobile/login/snsCalbk";

		    			}else if("-2".equals(resltCd)){
							model.addAttribute("resultCd", "-4");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", "/addBirthGenderView.do?loginType=SNS");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/addBirthGenderView.do?loginType=SNS");
							}
							return "/mobile/login/snsCalbk";

						}else {
							model.addAttribute("resultCd", "0000");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", redirectPage);

							return "/mobile/login/snsCalbk";
						}
					}
				}else {
					// 회원정보 수정 > 소셜로그인 관리  소셜로그인시 즉시 연동처리
					if(session.getAttribute("snsLoginPreUrl") != null && session.getAttribute("snsLoginPreUrl").toString().indexOf("userSnsList") > -1) {
						if (SessionUtils.hasLoginUserSessionBean()) {
							UserSessionDto userSession =  null;
							userSession = SessionUtils.getUserCookieBean();
							JoinDto joinDto = new JoinDto();
							joinDto.setUserId(userSession.getUserId());
							joinDto.setSnsCd("NAVER");
							joinDto.setSnsId(EncryptUtil.ace256Enc(naverUniqueNo));
							ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
							try {
								joinSvc.insertSnsInfo(joinDto);
								responseSuccessDto.setSuccessMsg("정상적으로 연결되었습니다.");
								model.addAttribute("msg", "정상적으로 연결되었습니다.");
							} catch (DataAccessException e) {
								responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
								model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
							} catch(Exception e){
								responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
								model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
							}
							responseSuccessDto.setRedirectUrl("/m/mypage/userSnsList.do");
							model.addAttribute("responseSuccessDto", responseSuccessDto);
						}
						model.addAttribute("resultCd", "-5");
						model.addAttribute("returnUrl", "/mypage/userSnsList.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/mypage/userSnsList.do");
						}
						return "/mobile/login/snsCalbk";


					}else {
						//회원가입용
						request.getSession().setAttribute("snsCd", "NAVER");
						request.getSession().setAttribute("snsId", EncryptUtil.ace256Enc(naverUniqueNo));
						request.getSession().setAttribute("snsMobileNo", snsMobileNo);
						request.getSession().setAttribute("snsGender", snsGender);
						request.getSession().setAttribute("snsEmail", snsEmail);
						request.getSession().setAttribute("snsBirthday", snsBirthday);

						model.addAttribute("resultCd", "-6");
						model.addAttribute("msg", "가입되어 있지 않은 소셜 계정입니다. 회원가입으로 이동합니다.");
						model.addAttribute("returnUrl", "/join/fstPage.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/join/fstPage.do");
						}
						return "/mobile/login/snsCalbk";
					}
				}

				// 네이버 정보조회 실패
			} else {
				model.addAttribute("resultCd", "-7");
				model.addAttribute("msg", "네이버 정보조회에 실패했습니다.");
				model.addAttribute("returnUrl", "/loginForm.do");
				if("Y".equals(NmcpServiceUtils.isMobile())){
					model.addAttribute("returnUrl", "/m/loginForm.do");
				}
				return "/mobile/login/snsCalbk";

			}
		} catch(DataAccessException e) {
			model.addAttribute("resultCd", "-7");
			model.addAttribute("msg", "네이버 정보조회에 실패했습니다.");
			model.addAttribute("returnUrl", "/loginForm.do");
			if("Y".equals(NmcpServiceUtils.isMobile())){
				model.addAttribute("returnUrl", "/m/loginForm.do");
			}
			return "/mobile/login/snsCalbk";
		} catch(Exception e) {
			model.addAttribute("resultCd", "-7");
			model.addAttribute("msg", "네이버 정보조회에 실패했습니다.");
			model.addAttribute("returnUrl", "/loginForm.do");
			if("Y".equals(NmcpServiceUtils.isMobile())){
				model.addAttribute("returnUrl", "/m/loginForm.do");
			}
			return "/mobile/login/snsCalbk";
		}


	}

	/**
	 * 설명 : 카카오 로그인 URL 조회
	 * @Author : jsh
	 * @Date : 2021.12.30
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/login/getKakaoAuthUrl.do", "/m/login/getKakaoAuthUrl.do"})
	public @ResponseBody String getKakaoAuthUrl(HttpSession session,HttpServletRequest request) {
		String reqUrl = kakaoLoginSvc.getKakaoAuthUrl();
		session.setAttribute("snsLoginPreUrl", request.getHeader("referer"));
		return reqUrl;
	}

	/**
	 * 설명 : 카카오 연동 정보 조회
	 * @Author : jsh
	 * @Date : 2021.12.30
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/login/kakaoCalbkUrl.do", "/m/login/kakaoCalbkUrl.do"})
	public String oauthKakao(HttpServletRequest request, HttpServletResponse response, Model model) {

		try {
			String code = request.getParameter("code");
			String error = request.getParameter("error");
			// 카카오로그인 페이지에서 취소버튼 눌렀을경우
			if (error != null) {
				if (error.equals("access_denied")) {
					model.addAttribute("resultCd", "-1");
					model.addAttribute("msg", "사용자 취소");
					model.addAttribute("returnUrl", "/loginForm.do");
					if("Y".equals(NmcpServiceUtils.isMobile())){
						model.addAttribute("returnUrl", "/m/loginForm.do");
					}

					return "/mobile/login/snsCalbk";
				}
			}

			String accessToken = kakaoLoginSvc.getAccessToken(code);
			HashMap<String, Object> kakaoUserInfo = kakaoLoginSvc.getKakaoUserInfo(accessToken);

			if (kakaoUserInfo != null && !kakaoUserInfo.get("id").equals("")) {
				String kakaoUniqueNo = (String) kakaoUserInfo.get("id");
				String snsMobileNo = (String) kakaoUserInfo.get("snsMobileNo");

				SnsLoginDto snsLoginDto = new SnsLoginDto();
				snsLoginDto = snsSvc.selectSnsIdCheck(kakaoUniqueNo);

				if(snsLoginDto != null) {
					//로그인 처리 필요
					UserSessionDto userSessionDto = snsSvc.selectSnsMcpUser(snsLoginDto.getUserId());

					//휴면계정 조회
					UserSessionDto param = new UserSessionDto();
					param.setUserId(snsLoginDto.getUserId());
					UserSessionDto dormancyUserDto = loginSvc.dormancyLoginProcess(param);

					if(userSessionDto == null && dormancyUserDto == null) {
						ResponseSuccessDto responseSuccessDto = noSnsInfo(snsLoginDto);

						model.addAttribute("resultCd", "-2");
						model.addAttribute("msg", responseSuccessDto.getSuccessMsg());
						model.addAttribute("returnUrl", "/loginForm.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/loginForm.do");
						}

						return "/mobile/login/snsCalbk";

					}else {

						if(dormancyUserDto != null) {
							SessionUtils.invalidateSession();
							SessionUtils.saveDormancySession(dormancyUserDto);	//휴면계정 세션저장

							model.addAttribute("resultCd", "-2");
							model.addAttribute("msg", "kt M모바일을 오랫동안 이용하지 않아 아이디가</br>휴면 상태로 전환되었습니다.</br></br>본인인증을 통해 해제 후 서비스 이용 부탁드립니다.");
							model.addAttribute("returnUrl", "/login/dormantUserView.do");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/login/dormantUserView.do");
							}
							return "/mobile/login/snsCalbk";
						}

		    			String resltCd = snsSvc.snsLoginProcess(request, userSessionDto, kakaoUniqueNo);
						String redirectPage = "/main.do";
						if("Y".equals(NmcpServiceUtils.isMobile())){
							redirectPage = "/m/main.do";
						}

						setSnsLoginCookie(response, "KAKAO");

		    			if("-1".equals(resltCd)) {
							model.addAttribute("resultCd", "-3");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", "/pwChgInfoView.do");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/pwChgInfoView.do");
							}
		    			}else if("-2".equals(resltCd)){
							model.addAttribute("resultCd", "-4");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", "/addBirthGenderView.do?loginType=SNS");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/addBirthGenderView.do?loginType=SNS");
							}

						}else {
							model.addAttribute("resultCd", "0000");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", redirectPage);

							setSnsAccessTokenCookie(response, accessToken);
						}

						return "/mobile/login/snsCalbk";

					}
				}else {
					HttpSession session = request.getSession();
					// 회원정보 수정 > 소셜로그인 관리  소셜로그인시 즉시 연동처리
					if(session.getAttribute("snsLoginPreUrl") != null && session.getAttribute("snsLoginPreUrl").toString().indexOf("userSnsList") > -1) {
						if (SessionUtils.hasLoginUserSessionBean()) {
							UserSessionDto userSession =  null;
							userSession = SessionUtils.getUserCookieBean();
							JoinDto joinDto = new JoinDto();
							joinDto.setUserId(userSession.getUserId());
							joinDto.setSnsCd("KAKAO");
							joinDto.setSnsId(EncryptUtil.ace256Enc(kakaoUniqueNo));
							ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
							try {
								joinSvc.insertSnsInfo(joinDto);
								responseSuccessDto.setSuccessMsg("정상적으로 연결되었습니다.");
								model.addAttribute("msg", "정상적으로 연결되었습니다.");
							} catch(DataAccessException e) {
								responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
								model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
							} catch(Exception e){
								responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
								model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
							}

							responseSuccessDto.setRedirectUrl("/m/mypage/userSnsList.do");
							model.addAttribute("responseSuccessDto", responseSuccessDto);

						}
						model.addAttribute("resultCd", "-5");
						model.addAttribute("returnUrl", "/mypage/userSnsList.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/mypage/userSnsList.do");
						}
						return "/mobile/login/snsCalbk";

					}else {
						request.getSession().setAttribute("snsCd", "KAKAO");
						request.getSession().setAttribute("snsId", EncryptUtil.ace256Enc(kakaoUniqueNo));
						request.getSession().setAttribute("snsMobileNo", snsMobileNo);

						model.addAttribute("resultCd", "-6");
						model.addAttribute("msg", "가입되어 있지 않은 소셜 계정입니다. 회원가입으로 이동합니다.");
						model.addAttribute("returnUrl", "/join/fstPage.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/join/fstPage.do");
						}
						return "/mobile/login/snsCalbk";
					}
				}

			} else {
				// 카카오톡 정보조회 실패했을경우
				model.addAttribute("resultCd", "-7");
				model.addAttribute("msg", "카카오톡 정보조회에 실패했습니다.");
				model.addAttribute("returnUrl", "/loginForm.do");
				if("Y".equals(NmcpServiceUtils.isMobile())){
					model.addAttribute("returnUrl", "/m/loginForm.do");
				}
				return "/mobile/login/snsCalbk";

			}
		} catch(DataAccessException e) {
			// 카카오톡 정보조회 실패했을경우
			model.addAttribute("resultCd", "-7");
			model.addAttribute("msg", "카카오톡 정보조회에 실패했습니다.");
			model.addAttribute("returnUrl", "/loginForm.do");
			if("Y".equals(NmcpServiceUtils.isMobile())){
				model.addAttribute("returnUrl", "/m/loginForm.do");
			}
			return "/mobile/login/snsCalbk";
		} catch(Exception e) {
			// 카카오톡 정보조회 실패했을경우
			model.addAttribute("resultCd", "-7");
			model.addAttribute("msg", "카카오톡 정보조회에 실패했습니다.");
			model.addAttribute("returnUrl", "/loginForm.do");
			if("Y".equals(NmcpServiceUtils.isMobile())){
				model.addAttribute("returnUrl", "/m/loginForm.do");
			}
			return "/mobile/login/snsCalbk";
		}
	}

	/**
	 * 설명 : 페이스북 연동정보 조회
	 * @Author : jsh
	 * @Date : 2021.12.30
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/login/facebookCalbkUrl.do", "/m/login/facebookCalbkUrl.do"})
	public String oauthFacebook(HttpServletRequest request, HttpServletResponse response, Model model) {

		try {
			if (!"".equals(StringUtil.NVL(request.getParameter("snsId"), ""))) {
				String facebookUniqueNo = (String) request.getParameter("snsId");

				SnsLoginDto snsLoginDto = new SnsLoginDto();
				snsLoginDto = snsSvc.selectSnsIdCheck(facebookUniqueNo);

				if(snsLoginDto != null) {
					//로그인 처리 필요
					UserSessionDto userSessionDto = snsSvc.selectSnsMcpUser(snsLoginDto.getUserId());

					//휴면계정 조회
					UserSessionDto param = new UserSessionDto();
					param.setUserId(snsLoginDto.getUserId());
					UserSessionDto dormancyUserDto = loginSvc.dormancyLoginProcess(param);

					if(userSessionDto == null && dormancyUserDto == null) {
						ResponseSuccessDto responseSuccessDto = noSnsInfo(snsLoginDto);

						model.addAttribute("resultCd", "-2");
						model.addAttribute("msg", responseSuccessDto.getSuccessMsg());
						model.addAttribute("returnUrl", "/loginForm.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/loginForm.do");
						}

						return "/mobile/login/snsCalbk";

					}else {

						if(dormancyUserDto != null) {
							SessionUtils.invalidateSession();
							SessionUtils.saveDormancySession(dormancyUserDto);	//휴면계정 세션저장

							model.addAttribute("resultCd", "-2");
							model.addAttribute("msg", "kt M모바일을 오랫동안 이용하지 않아 아이디가</br>휴면 상태로 전환되었습니다.</br></br>본인인증을 통해 해제 후 서비스 이용 부탁드립니다.");
							model.addAttribute("returnUrl", "/login/dormantUserView.do");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/login/dormantUserView.do");
							}
							return "/mobile/login/snsCalbk";
						}

		    			String resltCd = snsSvc.snsLoginProcess(request, userSessionDto, facebookUniqueNo);
						String redirectPage = "/main.do";
						if("Y".equals(NmcpServiceUtils.isMobile())){
							redirectPage = "/m/main.do";
						}

						setSnsLoginCookie(response, "FACEBOOK");

		    			if("-1".equals(resltCd)) {
							model.addAttribute("resultCd", "-3");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", "/pwChgInfoView.do");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/pwChgInfoView.do");
							}
		    			}else if("-2".equals(resltCd)){
							model.addAttribute("resultCd", "-4");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", "/addBirthGenderView.do?loginType=SNS");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/addBirthGenderView.do?loginType=SNS");
							}

						}else {
							model.addAttribute("resultCd", "0000");
							model.addAttribute("msg", "");
							model.addAttribute("returnUrl", redirectPage);

						}

						return "/mobile/login/snsCalbk";

					}
				}else {

					// 회원정보 수정 > 소셜로그인 관리  소셜로그인시 즉시 연동처리
					if(request.getHeader("referer").toString().indexOf("userSnsList") > -1) {
						if (SessionUtils.hasLoginUserSessionBean()) {
							UserSessionDto userSession =  null;
							userSession = SessionUtils.getUserCookieBean();
							JoinDto joinDto = new JoinDto();
							joinDto.setUserId(userSession.getUserId());
							joinDto.setSnsCd("FACEBOOK");
							joinDto.setSnsId(EncryptUtil.ace256Enc(facebookUniqueNo));
							ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
							try {
								joinSvc.insertSnsInfo(joinDto);
								responseSuccessDto.setSuccessMsg("정상적으로 연결되었습니다.");
								model.addAttribute("msg", "정상적으로 연결되었습니다.");
							} catch(DataAccessException e){
								responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
								model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
							} catch(Exception e){
								responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
								model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
							}
							responseSuccessDto.setRedirectUrl("/m/mypage/userSnsList.do");
							model.addAttribute("responseSuccessDto", responseSuccessDto);

						}
						model.addAttribute("resultCd", "-5");
						model.addAttribute("returnUrl", "/mypage/userSnsList.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/mypage/userSnsList.do");
						}
						return "/mobile/login/snsCalbk";

					}else {
						request.getSession().setAttribute("snsCd", "FACEBOOK");
						request.getSession().setAttribute("snsId", EncryptUtil.ace256Enc(facebookUniqueNo));

						model.addAttribute("resultCd", "-6");
						model.addAttribute("msg", "가입되어 있지 않은 소셜 계정입니다.</br>회원가입으로 이동합니다.");
						model.addAttribute("returnUrl", "/join/fstPage.do");
						if("Y".equals(NmcpServiceUtils.isMobile())){
							model.addAttribute("returnUrl", "/m/join/fstPage.do");
						}
						return "/mobile/login/snsCalbk";
					}
				}

			} else {
				// 페이스북 정보조회 실패했을경우
				model.addAttribute("resultCd", "-7");
				model.addAttribute("msg", "페이스북 정보조회에 실패했습니다.");
				model.addAttribute("returnUrl", "/loginForm.do");
				if("Y".equals(NmcpServiceUtils.isMobile())){
					model.addAttribute("returnUrl", "/m/loginForm.do");
				}

				return "/mobile/login/snsCalbk";

			}
		} catch(DataAccessException e){
			// 페이스북 정보조회 실패했을경우
			model.addAttribute("resultCd", "-7");
			model.addAttribute("msg", "페이스북 정보조회에 실패했습니다.");
			model.addAttribute("returnUrl", "/loginForm.do");
			if("Y".equals(NmcpServiceUtils.isMobile())){
				model.addAttribute("returnUrl", "/m/loginForm.do");
			}

			return "/mobile/login/snsCalbk";
		} catch(Exception e) {
			// 페이스북 정보조회 실패했을경우
			model.addAttribute("resultCd", "-7");
			model.addAttribute("msg", "페이스북 정보조회에 실패했습니다.");
			model.addAttribute("returnUrl", "/loginForm.do");
			if("Y".equals(NmcpServiceUtils.isMobile())){
				model.addAttribute("returnUrl", "/m/loginForm.do");
			}

			return "/mobile/login/snsCalbk";
		}


	}

	/** SNS 정보 없을시 처리
	 * 설명 :
	 * @Author : jsh
	 * @Date : 2021.12.30
	 * @param snsLoginDto
	 * @return
	 */
	public ResponseSuccessDto noSnsInfo(SnsLoginDto snsLoginDto) {

		//SNS 로그인 실패 정보 저장
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("loginResult", "-1");
		param.put("snsId", snsLoginDto.getSnsId());
		param.put("userId", snsLoginDto.getUserId());
		snsSvc.updateSnsLoginInfo(param);

		ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
		responseSuccessDto.setSuccessMsg("존재하지 않거나 유효하지 않은 아이디입니다.");
		if("Y".equals(NmcpServiceUtils.isMobile())){
			responseSuccessDto.setRedirectUrl("/m/loginForm.do");
		} else {
			responseSuccessDto.setRedirectUrl("/loginForm.do");
		}

		return responseSuccessDto;
	}

	/**
	 * 마지막 SNS 로그인 정보 쿠키 저장
	 * @author jsh
	 * @Date : 2021.12.30
	 */
	public void setSnsLoginCookie(HttpServletResponse response, String snsCd) {
		Cookie cookie = new Cookie("LastSnsLogin", snsCd);
		cookie.setSecure(true);
//        cookie.setMaxAge(60*60*24*30); // 기존로직
		cookie.setMaxAge(60*60);	// 취약성 249
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * SNS accessToken 쿠키 저장
	 * @author jsh
	 * @Date : 2021.12.30
	 */
	public void setSnsAccessTokenCookie(HttpServletResponse response, String accessToken) {
		Cookie cookie = new Cookie("accKey", EncryptUtil.ace256Enc(accessToken));
		cookie.setSecure(true);
		cookie.setMaxAge(60*60);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@RequestMapping(value = "/m/app/getUuidAppInfoSns.do")
	@ResponseBody
	public Map<String, Object> getUuidAppInfoSns(HttpServletRequest request, @RequestParam String uuid) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String retCode = "";
		try {

			if(!"".equals(StringUtil.NVL(uuid,  ""))) {
				UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
				if(userSessionDto != null) {
					AppInfoDTO appInfoDTO = new AppInfoDTO();
					appInfoDTO.setUuid(uuid);
					appInfoDTO.setUserId(userSessionDto.getUserId());
					appSvc.mergeUsrAppInfo(appInfoDTO);

					NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto = loginSvc.getLoginAutoLogin(userSessionDto);

					if(nmcpAutoLoginTxnDto == null) {
						loginSvc.insertAutoLoginTxn(userSessionDto);
					}else {
						loginSvc.updateAutoLoginTxn(userSessionDto);
					}
				}
			}

			AppInfoDTO appInfoDTOParam = new AppInfoDTO();
			appInfoDTOParam.setUuid(uuid);

			AppInfoDTO uuidAppInfo = appSvc.selectUsrAppDetail(appInfoDTOParam);
			if(uuidAppInfo == null) {
				retCode = "-1";
			} else {
				retCode = "00000";
				returnMap.put("token", uuidAppInfo.getToken());
				returnMap.put("wdgtUseQntyCallCyclCd", uuidAppInfo.getWdgtUseQntyCallCyclCd());
				returnMap.put("simpleLoginYn", uuidAppInfo.getSimpleLoginYn());
				returnMap.put("bioLoginYn", uuidAppInfo.getBioLoginYn());
			}
		} catch(DataAccessException e){
			logger.error("getUuidAppInfoSns Exception = {}", e.getMessage());
		} catch(Exception e) {
			logger.error("getUuidAppInfoSns Exception = {}", e.getMessage());
		}

		returnMap.put("resltCd", retCode);

		return returnMap;

	}

}
