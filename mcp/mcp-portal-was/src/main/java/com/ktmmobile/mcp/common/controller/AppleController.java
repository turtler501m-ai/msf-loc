package com.ktmmobile.mcp.common.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.apple.dto.ApplePayload;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.SnsLoginDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpAutoLoginTxnDto;
import com.ktmmobile.mcp.common.service.AppleLoginSvc;
import com.ktmmobile.mcp.common.service.LoginSvc;
import com.ktmmobile.mcp.common.service.SnsSvc;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.service.JoinSvc;

/**
 * APPLE LOGIN Controller
 * @author jsh
 * @Date : 2021.12.30
 */
@Controller
public class AppleController {

	private static final Logger logger = LoggerFactory.getLogger(AppleController.class);

	@Autowired
	private SnsSvc snsSvc;

	@Autowired
	private LoginSvc loginSvc;

	@Autowired
	JoinSvc joinSvc;

	@Autowired
	private AppleLoginSvc appleLoginSvc;

	/**
	 * 설명 : APPLE 로그인 URL 조회
	 * @Author : jsh
	 * @Date : 2021.12.30
	 * @param session
	 * @param request
	 * @return
	 * @throws java.lang.Exception
	 */
	@RequestMapping(value = {"/login/getAppleAuthUrl.do", "/m/login/getAppleAuthUrl.do"})
	public @ResponseBody String getAppleAuthUrl(HttpSession session, HttpServletRequest request) throws java.lang.Exception {
		String reqUrl = appleLoginSvc.getAppleAuthUrl();
		session.setAttribute("snsLoginPreUrl", request.getHeader("referer"));
		return reqUrl;
	}

	/**
	 * 설명 : APPLE 연동정보 조회
	 * @Author : jsh
	 * @Date : 2021.12.30
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/login/appleCalbkUrl.do", "/m/login/appleCalbkUrl.do"})
	public String appleCalbkUrl(HttpServletRequest request, HttpServletResponse response, Model model) {

		String id_token = request.getParameter("id_token");

		try {

			boolean verify = false;
			String appleUniqueNo = "";

			//아이폰 13 이상의 경우
			if(!"".equals(StringUtil.NVL(request.getParameter("token"), ""))){
				verify = true;
				appleUniqueNo = request.getParameter("token");
				request.getSession().setAttribute("snsLoginPreUrl", request.getHeader("referer"));
			}

			//아이폰 13 이하의 경우
			if(!verify) {
				verify = appleLoginSvc.getVerifyIdentity(id_token); //JWT DATA 검증(EXP, payload, RSA서명)
			}

			if(verify) {

				ApplePayload applePayload= null;
				if("".equals(appleUniqueNo)) {
					//아이폰 13 이하의 경우
					applePayload= appleLoginSvc.getPayloadVO(id_token);
				}

				if(applePayload != null || !"".equals(appleUniqueNo)) {

					if(applePayload != null ) {
						appleUniqueNo = (String) applePayload.getSub();
					}

					SnsLoginDto snsLoginDto = new SnsLoginDto();
					snsLoginDto = snsSvc.selectSnsIdCheck(appleUniqueNo);

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

			    			String resltCd = snsSvc.snsLoginProcess(request, userSessionDto, appleUniqueNo);
							String redirectPage = "/main.do";
							if("Y".equals(NmcpServiceUtils.isMobile())){
								redirectPage = "/m/main.do";
							}

							setSnsLoginCookie(response, "APPLE");

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
					} else {
						HttpSession session = request.getSession();
						// 회원정보 수정 > 소셜로그인 관리  소셜로그인시 즉시 연동처리
						if(session.getAttribute("snsLoginPreUrl") != null && session.getAttribute("snsLoginPreUrl").toString().indexOf("userSnsList") > -1) {
							if (SessionUtils.hasLoginUserSessionBean()) {
								UserSessionDto userSession =  null;
								userSession = SessionUtils.getUserCookieBean();
								JoinDto joinDto = new JoinDto();
								joinDto.setUserId(userSession.getUserId());
								joinDto.setSnsCd("APPLE");
								joinDto.setSnsId(EncryptUtil.ace256Enc(appleUniqueNo));
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
							request.getSession().setAttribute("snsCd", "APPLE");
							request.getSession().setAttribute("snsId", EncryptUtil.ace256Enc(appleUniqueNo));

							model.addAttribute("resultCd", "-6");
							model.addAttribute("msg", "가입되어 있지 않은 소셜 계정입니다. 회원가입으로 이동합니다.");
							model.addAttribute("returnUrl", "/join/fstPage.do");
							if("Y".equals(NmcpServiceUtils.isMobile())){
								model.addAttribute("returnUrl", "/m/join/fstPage.do");
							}
							return "/mobile/login/snsCalbk";
						}
					}

				}else {
					// 애플 정보조회 실패했을경우
					model.addAttribute("resultCd", "-7");
					model.addAttribute("msg", "애플 정보조회에 실패했습니다.");
					model.addAttribute("returnUrl", "/loginForm.do");
					if("Y".equals(NmcpServiceUtils.isMobile())){
						model.addAttribute("returnUrl", "/m/loginForm.do");
					}

					return "/mobile/login/snsCalbk";
				}
			} else {
				// 애플 정보조회 실패했을경우
				model.addAttribute("resultCd", "-7");
				model.addAttribute("msg", "애플 정보조회에 실패했습니다.");
				model.addAttribute("returnUrl", "/loginForm.do");
				if("Y".equals(NmcpServiceUtils.isMobile())){
					model.addAttribute("returnUrl", "/m/loginForm.do");
				}

				return "/mobile/login/snsCalbk";

			}

		} catch(DataAccessException e) {
			// 애플 정보조회 실패했을경우
			model.addAttribute("resultCd", "-7");
			model.addAttribute("msg", "애플 정보조회에 실패했습니다.");
			model.addAttribute("returnUrl", "/loginForm.do");
			if("Y".equals(NmcpServiceUtils.isMobile())){
				model.addAttribute("returnUrl", "/m/loginForm.do");
			}

			return "/mobile/login/snsCalbk";
		} catch(Exception e) {
			// 애플 정보조회 실패했을경우
			model.addAttribute("resultCd", "-7");
			model.addAttribute("msg", "애플 정보조회에 실패했습니다.");
			model.addAttribute("returnUrl", "/loginForm.do");
			if("Y".equals(NmcpServiceUtils.isMobile())){
				model.addAttribute("returnUrl", "/m/loginForm.do");
			}

			return "/mobile/login/snsCalbk";
		}
	}

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


	public void setSnsLoginCookie(HttpServletResponse response, String snsCd) {
		Cookie cookie = new Cookie("LastSnsLogin", snsCd);
		cookie.setSecure(true);
//        cookie.setMaxAge(60*60*24*30); // 기존로직
		cookie.setMaxAge(60*60);	// 취약성 239
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
