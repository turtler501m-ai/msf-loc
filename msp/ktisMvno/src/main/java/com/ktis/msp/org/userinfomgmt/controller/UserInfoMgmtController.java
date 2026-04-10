package com.ktis.msp.org.userinfomgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.crypto.encryptor.sha.SHA512Encryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.authmgmt.service.AuthMgmtService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : UserInfoMgmtController
 * @Description : 사용자 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@Controller
public class UserInfoMgmtController extends BaseController {

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private AuthMgmtService authMgmtService;
	
	@Autowired
	private SHA512Encryptor  sHA512Encryptor;
	
	@Autowired
	private FileDownService  fileDownService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	public UserInfoMgmtController() {
		setLogPrefix("[UserInfoMgmtController] ");
	}

	/**
	 * @Description : 사용자 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 22.
	 */
	@RequestMapping(value = "/org/userinfomgmt/userInfoMgmt.do")
	public ModelAndView userInfoMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			modelAndView.getModelMap().addAttribute("startDate",orgCommonService.getFirstDay());
			modelAndView.getModelMap().addAttribute("endDate",orgCommonService.getLastDay());
			modelAndView.getModelMap().addAttribute("maskingYn",maskingYn);
			//model.addAttribute("startDate",orgCommonService.getFirstDay());
			//model.addAttribute("endDate",orgCommonService.getLastDay());
			modelAndView.setViewName("org/userinfomgmt/msp_org_bs_1028");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 사용자 찾기 팝업 호출
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 26.
	 */
	@RequestMapping(value = "/org/userinfomgmt/searchUserInfo.do", method={POST, GET})
	public String searchUserInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 찾기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		//v2018.11 PMD 적용 소스 수정
		//LoginInfo loginInfo = new LoginInfo(request, response);
		new LoginInfo(request, response);
		
		model.addAttribute("attcSctnCd", request.getParameter("attcSctnCd"));
		
		return "org/userinfomgmt/msp_org_pu_1028";
	}

	/**
	 * @Description : 사용자 리스트 조회
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/userinfomgmt/userInfoMgmtList.json")
	public String selectUserInfoMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = userInfoMgmtService.selectUserInfoMgmtList(userInfoMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("사용자 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 사용자이력화면
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 9. 1.
	 */
	@RequestMapping(value = "/org/userinfomgmt/userInfoHst.do", method={POST, GET})
	public ModelAndView userInfoHst(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 이력 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			model.addAttribute("startDate",orgCommonService.getFirstDay());
			model.addAttribute("endDate",orgCommonService.getLastDay());
			modelAndView.setViewName("org/userinfomgmt/msp_org_bs_1029");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}


	}

	/**
	 * @Description : 사용자이력조회
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 9. 1.
	 */
	@RequestMapping("/org/userinfomgmt/userInfoHstList.json")
	public String userInfoHstList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 이력 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = userInfoMgmtService.selectUserHst(userInfoMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("사용자 이력 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 사용자 등록
	 * @Param  : orgMgmtVO.class
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/userinfomgmt/insertUserInfoMgmt.json")
	public String insertUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 등록 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int returnCnt = 0;
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
			
			//전화번호, 핸드폰번호, Fax번호 합치기
			if(KtisUtil.isNotEmpty(userInfoMgmtVo.getTelNum2())){
				userInfoMgmtVo.setTelNum(userInfoMgmtVo.getTelNum1()+userInfoMgmtVo.getTelNum2()+userInfoMgmtVo.getTelNum3());
			}
			
			userInfoMgmtVo.setMblphnNum(userInfoMgmtVo.getMblphnNum1()+userInfoMgmtVo.getMblphnNum2()+userInfoMgmtVo.getMblphnNum3());
			if(KtisUtil.isNotEmpty(userInfoMgmtVo.getFax2())){
				userInfoMgmtVo.setFax(userInfoMgmtVo.getFax1()+userInfoMgmtVo.getFax2()+userInfoMgmtVo.getFax3());
			}
						
			userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toUpperCase());
			userInfoMgmtVo.setPass(getDefaultPass());
			userInfoMgmtVo.setPassInit("Y");	/** 임시패스워드 여부 */
			
			returnCnt = userInfoMgmtService.insertUserInfoMgmt(userInfoMgmtVo);
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "");
						
			logger.info(generateLogMsg("수정 건수 = " + returnCnt));
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * @Description : 사용자정보수정
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 26.
	 */
	@RequestMapping("/org/userinfomgmt/updateUserInfoMgmt.json")
	public String updateUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 수정 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringUtil util = new StringUtil();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
			
			//v2018.11 PMD 적용 소스 수정
			/*if (!userInfoMgmtService.isAdminGroupUser((String)request.getSession().getAttribute("SESSION_USER_ID"))){
				if (!request.getSession().getAttribute("SESSION_USER_ID").equals(userInfoMgmtVo.getUsrId())) {
					throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
				}
			}*/
         if (!userInfoMgmtService.isAdminGroupUser((String)request.getSession().getAttribute("SESSION_USER_ID"))
               && !request.getSession().getAttribute("SESSION_USER_ID").equals(userInfoMgmtVo.getUsrId()) ){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
						
			String pwd =  userInfoMgmtVo.getPass();
			String oldPass = userInfoMgmtVo.getOldPass();
			userInfoMgmtVo.setOldPass(sHA512Encryptor.encrypt(oldPass));
			
			if (!userInfoMgmtService.isOldPass(userInfoMgmtVo)) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "현재 비밀번호가 일치하지 않습니다.");
			} else {
				
				//전화번호, 핸드폰번호, Fax번호 합치기
				if(KtisUtil.isNotEmpty(userInfoMgmtVo.getTelNum2())){
					userInfoMgmtVo.setTelNum(userInfoMgmtVo.getTelNum1()+userInfoMgmtVo.getTelNum2()+userInfoMgmtVo.getTelNum3());
				}
				
				userInfoMgmtVo.setMblphnNum(userInfoMgmtVo.getMblphnNum1()+userInfoMgmtVo.getMblphnNum2()+userInfoMgmtVo.getMblphnNum3());
				if(KtisUtil.isNotEmpty(userInfoMgmtVo.getFax2())){
					userInfoMgmtVo.setFax(userInfoMgmtVo.getFax1()+userInfoMgmtVo.getFax2()+userInfoMgmtVo.getFax3());
				}
				
				if(!Pattern.matches("^.*(?=^.{8,20}$)(?=.*[a-zA-Z])(?=.*[!@#$%^*])(?=.*[0-9]).*$", pwd)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 8자리 이상 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
				} else if (pwd.equals((String) pRequestParamMap.get("oldPass"))) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 이전 패스워드는 사용할 수 없습니다.");
				} else if (!util.chkContinuePass(pwd)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 3자리 이상의 연속된 숫자 및 문자는 사용할 수 없습니다.");
				} else if (!util.chkKeyboardContinuePass(pwd)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 3자리 이상 키보드 자판의 연속된 문자는 사용할 수 없습니다.");
				} else if (!util.chkSamePass(pwd)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 3자리 이상의 같은 숫자 및 문자는 사용할 수 없습니다.");
				} else {
					userInfoMgmtVo.setKosId(null);
					
					//비밀번호가 변경되었으면 암호화 저장
					userInfoMgmtService.updateUserInfoMgmtEncrypt(userInfoMgmtVo);
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				}
				
			}
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", messageSource.getMessage("fail.common.msg", null, Locale.getDefault()));
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 사용자정보수정(관리자용)
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 26.
	 */
	@RequestMapping("/org/userinfomgmt/updateUserInfoMgmtAdm.json")
	public String updateUserInfoMgmtAdm(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 수정 ADM START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringUtil util = new StringUtil();
		boolean result = true;
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
			
			//v2018.11 PMD 적용 소스 수정
			/*if (!userInfoMgmtService.isAdminGroupUser((String)request.getSession().getAttribute("SESSION_USER_ID"))){
				if (!request.getSession().getAttribute("SESSION_USER_ID").equals(userInfoMgmtVo.getUsrId())) {
					throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
				}
			}*/
			if (!userInfoMgmtService.isAdminGroupUser((String)request.getSession().getAttribute("SESSION_USER_ID"))
			      && !request.getSession().getAttribute("SESSION_USER_ID").equals(userInfoMgmtVo.getUsrId())){
               throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
			
			int returnCnt = 0;
			String pwd =  userInfoMgmtVo.getPass();
			
			//전화번호, 핸드폰번호, Fax번호 합치기
			if(KtisUtil.isNotEmpty(userInfoMgmtVo.getTelNum2())){
				userInfoMgmtVo.setTelNum(userInfoMgmtVo.getTelNum1()+userInfoMgmtVo.getTelNum2()+userInfoMgmtVo.getTelNum3());
			}

			userInfoMgmtVo.setMblphnNum(userInfoMgmtVo.getMblphnNum1()+userInfoMgmtVo.getMblphnNum2()+userInfoMgmtVo.getMblphnNum3());
			if(KtisUtil.isNotEmpty(userInfoMgmtVo.getFax2())){
				userInfoMgmtVo.setFax(userInfoMgmtVo.getFax1()+userInfoMgmtVo.getFax2()+userInfoMgmtVo.getFax3());
			}
						
			if(KtisUtil.isNotEmpty(userInfoMgmtVo.getPass())) {
				result = false;
				
				if(!Pattern.matches("^.*(?=^.{8,20}$)(?=.*[a-zA-Z])(?=.*[!@#$%^*])(?=.*[0-9]).*$", pwd)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 8자리 이상 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
				} else if (!util.chkContinuePass(pwd)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 3자리 이상의 연속된 숫자 및 문자는 사용할 수 없습니다.");
				} else if (!util.chkKeyboardContinuePass(pwd)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 3자리 이상 키보드 자판의 연속된 문자는 사용할 수 없습니다.");
				} else if (!util.chkSamePass(pwd)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[비밀번호] 3자리 이상의 같은 숫자 및 문자는 사용할 수 없습니다.");
				} else {
					result = true;
					returnCnt = userInfoMgmtService.updateUserInfoMgmtEncrypt(userInfoMgmtVo);
				}
			}

			if (result) {
				returnCnt = userInfoMgmtService.updateUserInfoMgmt(userInfoMgmtVo);
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", messageSource.getMessage("fail.common.msg", null, Locale.getDefault()));
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 사용자삭제
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 26.
	 */
	@RequestMapping("/org/userinfomgmt/deleteUserInfoMgmt.json")
	public String deleteUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, SessionStatus status)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 삭제 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
			
			//v2018.11 PMD 적용 소스 수정
			/*if (!userInfoMgmtService.isAdminGroupUser((String)request.getSession().getAttribute("SESSION_USER_ID"))){
				if (!request.getSession().getAttribute("SESSION_USER_ID").equals(userInfoMgmtVo.getUsrId())) {
					throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
				}
			}*/
			if (!userInfoMgmtService.isAdminGroupUser((String)request.getSession().getAttribute("SESSION_USER_ID"))
			      && !request.getSession().getAttribute("SESSION_USER_ID").equals(userInfoMgmtVo.getUsrId())){
               throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
			
			//v2018.11 PMD 적용 소스 수정
			//int returnCnt = userInfoMgmtService.deleteUserInfoMgmt(userInfoMgmtVo);
			userInfoMgmtService.deleteUserInfoMgmt(userInfoMgmtVo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 사용자 비밀번호 초기화
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 26.
	 */
	@RequestMapping("/org/userinfomgmt/initUserPassword.json")
	public String initUserPassword(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 비번 초기화 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
			
			String pass = getDefaultPass();
			userInfoMgmtVo.setPass(pass);

			// 사용자패스워드 초기화 신청이 되어있을경우 처리
			int usrCnt  = loginService.selectUsrPwdReset(pRequestParamMap);
			if(usrCnt > 0){
				userInfoMgmtVo.setStatus("Y");
				userInfoMgmtVo.setProcId(loginInfo.getUserId());
				userInfoMgmtService.updateUserPwdReset(userInfoMgmtVo);
			}
			
			
			userInfoMgmtService.initUserPassword(userInfoMgmtVo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	/**
	 * @Description : 판매점 사용자 잠금상태 초기화
	 * @Param  :
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2024. 10. 30.
	 */
	@RequestMapping("/org/userinfomgmt/updateUserErrorReset.json")
	public String initUserErrorword(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("판매점 사용자 잠금상태 초기화 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
			
			// 패스워드 오류시 잠금해제
			if("A".equals(userInfoMgmtVo.getUsgYn()) || "E".equals(userInfoMgmtVo.getUsgYn())){
				//실패이력 Y로 변경
				userInfoMgmtVo.setStatus("Y");
				userInfoMgmtVo.setProcId(loginInfo.getUserId());
				userInfoMgmtService.updateUserPwdReset(userInfoMgmtVo);
				
				// PASS_ERR_NUM 초기화
				userInfoMgmtService.updateUserPassErrNum(userInfoMgmtVo);
				
				String pass = getDefaultPass();
				userInfoMgmtVo.setPass(pass);
				
				// PASSWORD 초기화
				userInfoMgmtService.initUserPassword(userInfoMgmtVo);		
			}
			
			// OTP 오류시  잠금해제
			if("A".equals(userInfoMgmtVo.getUsgYn()) || "L".equals(userInfoMgmtVo.getUsgYn())){
				// 잠금상태 초기화
				userInfoMgmtService.updateLockStatus(userInfoMgmtVo);			
			}
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}	
	
	
	/**
	* @Description : 사용자 ID 중복체크
	* @Param  :
	* @Return : String
	* @Author : 고은정
	* @Create Date : 2014. 9. 27.
	 */
	@RequestMapping("/org/userinfomgmt/isExistUsrId.json")
	public String isExistUsrId(UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, HttpServletRequest request, HttpServletResponse response){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 ID 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try{
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toUpperCase());

			int returnCnt = userInfoMgmtService.isExistUsrId(userInfoMgmtVo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

			logger.info(generateLogMsg("중복 건수 = " + returnCnt));

		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 직원 찾기 팝업 호출
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 9. 27.
	 */
	@RequestMapping(value = "/org/userinfomgmt/searchEmpInfo.do", method={POST, GET})
	public String searchEmpInfo(HttpServletRequest request, HttpServletResponse response){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("직원 찾기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		//v2018.11 PMD 적용 소스 수정
		//LoginInfo loginInfo = new LoginInfo(request, response);
		new LoginInfo(request, response);
		
		return "org/userinfomgmt/msp_org_pu_1029";
	}

	/**
	 * @Description : 사용자정보 수정 팝업 호출(사용자본인)
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 10. 7.
	 */
	@RequestMapping(value = "/org/userinfomgmt/usrModifyPopup.do", method={POST, GET})
	public ModelAndView usrModifyPopup(HttpServletRequest request, HttpServletResponse response, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자정보 수정 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.setViewName("org/userinfomgmt/msp_org_pu_1030");
			
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response)); 
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * @Description : 사용자 정보를 가져온다
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 10. 7.
	 */
	@RequestMapping("/org/userinfomgmt/userInfoMgmt.json")
	public String userInfoMgmt(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//v2018.11 PMD 적용 소스 수정
		   //LoginInfo loginInfo = new LoginInfo(request, response);
		   new LoginInfo(request, response);
			
			if (!request.getSession().getAttribute("SESSION_USER_ID").equals(userInfoMgmtVo.getUsrId())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			UserInfoMgmtVo resultVo = userInfoMgmtService.selectUserInfoMgmt(userInfoMgmtVo);
			
			resultMap =  makeResultSingleRow(userInfoMgmtVo, resultVo);
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("사용자 정보 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		return "jsonView";
	}
	
	/**
	 * @Description : 사용자 MAC 정보를 삭제한다
	 * @Param  :
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2017. 06. 10.
	 */
	@RequestMapping("/org/userinfomgmt/macAddressDelete.json")
	public String macAddressDelete(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 MAC 정보 삭제 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int resultCnt =  userInfoMgmtService.macAddressDelete(userInfoMgmtVo.getUsrId());


		resultMap.put("resultCnt", resultCnt);
		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		
		if(resultCnt > 0){
			resultMap.put("msg", "Success");
		} else {
			resultMap.put("msg", "Fail");		
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	String getDefaultPass() {
		String pass = "";
		
		Date now = new Date();
		
		//v2018.11 PMD 적용 소스 수정
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
		
		String year = sdf.format(now);
		
		pass = "new"+year+"!";
		
		return pass;
	}
	
	/**
	 * @Description : 판매점 사용자 초기 화면 호출
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	@RequestMapping(value = "/org/userinfomgmt/shopUserInfoMgmt.do", method={POST, GET})
	public ModelAndView shopUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			String authModYn = "N";
			if (menuAuthService.chkUsrGrpAuth(loginInfo.getUserId(),"A-SHPUSR")) {
				authModYn = "Y";
			}
			
			model.addAttribute("authModYn", authModYn);
						
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("org/userinfomgmt/msp_org_bs_1032");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 판매점 사용자 리스트 조회
	 * @Param  : userInfoMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	@RequestMapping("/org/userinfomgmt/shopUserInfoMgmtList.json")
	public String selectShopUserInfoMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			List<?> resultList = userInfoMgmtService.selectShopUserInfoMgmtList(userInfoMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("사용자 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 판매점 사용자 등록
	 * @Param  : userInfoMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	@RequestMapping("/org/userinfomgmt/insertShopUserInfoMgmt.json")
	public String insertShopUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int returnCnt = 0;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);

			userInfoMgmtVo.setRegstId(loginInfo.getUserId());
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
			userInfoMgmtVo.setAttcSctnCd("30");
			userInfoMgmtVo.setUsgYn("Y");

			userInfoMgmtVo.setMblphnNum(userInfoMgmtVo.getMblphnNum1()+userInfoMgmtVo.getMblphnNum2()+userInfoMgmtVo.getMblphnNum3());

			userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toUpperCase());
			userInfoMgmtVo.setPass(getDefaultPass());
			userInfoMgmtVo.setPassInit("Y");
			userInfoMgmtVo.setM2mYn("Y");

			returnCnt = userInfoMgmtService.insertUserInfoMgmt(userInfoMgmtVo);

			if (returnCnt > 0) {
				Map<String, Object> authMap = new HashMap<String, Object>();
				authMap.put("USR_ID", userInfoMgmtVo.getUsrId());
				authMap.put("GRP_ID", "G-PSCM2M"); //202408 M2M
				authMap.put("SESSION_USER_ID", loginInfo.getUserId());

				if(authMgmtService.usrGrpAsgnInsert(authMap) > 0)
				{
					authMap.put("operType", "I");
					authMgmtService.usrGrpAsgnHstInsert(authMap);
				}
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
						
			logger.info(generateLogMsg("수정 건수 = " + returnCnt));
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 판매점사용자정보수정
	 * @Param  : userInfoMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	@RequestMapping("/org/userinfomgmt/updateShopUserInfoMgmt.json")
	public String updateShopUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);

			userInfoMgmtVo.setMblphnNum(userInfoMgmtVo.getMblphnNum1()+userInfoMgmtVo.getMblphnNum2()+userInfoMgmtVo.getMblphnNum3());

			userInfoMgmtService.updateShopUserInfoMgmt(userInfoMgmtVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", messageSource.getMessage("fail.common.msg", null, Locale.getDefault()));
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : 판매점 사용자 ID 중복체크
	 * @Param  : userInfoMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	@RequestMapping("/org/userinfomgmt/isExistShopUsrId.json")
	public String isExistShopUsrId(UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, HttpServletRequest request, HttpServletResponse response){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try{
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toUpperCase());

			int returnCnt = userInfoMgmtService.isExistUsrId(userInfoMgmtVo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : 판매점 사용자 삭제
	 * @Param  : userInfoMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	@RequestMapping("/org/userinfomgmt/deleteShopUserInfoMgmt.json")
	public String deleteShopUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, SessionStatus status)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());

			int returnCnt = userInfoMgmtService.deleteUserInfoMgmt(userInfoMgmtVo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 판매점 사용자 비밀번호 초기화
	 * @Param  : userInfoMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	@RequestMapping("/org/userinfomgmt/initShopUserPassword.json")
	public String initShopUserPassword(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
						
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
			
			String pass = getDefaultPass();
			userInfoMgmtVo.setPass(pass);
			
			int returnCnt = userInfoMgmtService.initUserPassword(userInfoMgmtVo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : 사용자 상태 초기화
	 * @Param  : userInfoMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	@RequestMapping("/org/userinfomgmt/updateStopStatusInit.json")
	public String updateStopStatusInit(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			int returnCnt = userInfoMgmtService.updateStopStatusInit(userInfoMgmtVo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	
	/**
	 * @Description : 사용자정지상태 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2016. 06. 01.
	 */
	@RequestMapping(value = "/org/userinfomgmt/userStopStatus.do", method={POST, GET})
	public ModelAndView userStopStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자정지상태 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			model.addAttribute("startDate",orgCommonService.getFirstDay());
			model.addAttribute("endDate",orgCommonService.getLastDay());
			modelAndView.setViewName("org/userinfomgmt/msp_cmn_user_1001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 사용자정지상태 리스트 조회
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 01.
	 */
	@RequestMapping("/org/userinfomgmt/getUserStopStatus.json")
	public String getUserStopStatus(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 정지상태 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = userInfoMgmtService.getUserStopStatusList(userInfoMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("사용자 정지상태 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 사용자 정지상태 상태변경
	 * @param VO
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/org/userinfomgmt/updateUserStatusChg.json")
		public String updateUserStatusChg(@ModelAttribute("searchVO") UserInfoMgmtVo searchVO,
								@RequestBody String pJsonString,
								ModelMap model, 
								HttpServletRequest request, 
								HttpServletResponse response, 
								@RequestParam Map<String, Object> requestParamMap) {

		logger.debug("p_jsonString=" + pJsonString);
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 정지상태 상태변경 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(searchVO);
		
		try {
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List list = getVoFromMultiJson(pJsonString, "ALL", UserInfoMgmtVo.class );
			
			//사용자 정지상태 변경
			for ( int idx = 0 ; idx < list.size(); idx++)
			{
				UserInfoMgmtVo vo = (UserInfoMgmtVo) list.get(idx);			
				vo.setUsgYn("Y");
				vo.setStopYn("N");
				vo.setProcId(searchVO.getSessionUserId());
				vo.setRvisnId(searchVO.getSessionUserId());
				
				logger.info("건수 >>>" + list.size());
				logger.info("처리자 >>>" + vo.getProcId());
				logger.info("사용유무 >>>" + vo.getUsgYn());
				logger.info("정지유무>>>" + vo.getStopYn());
				
				userInfoMgmtService.updateUserStatusChg(vo);
			
			}
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * 청구항목별 엑셀다운로드
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/org/userinfomgmt/getStopUserDataListExcel.json")
	public String getInvItemDataListExcel(@ModelAttribute("searchVO")  UserInfoMgmtVo searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀다운로드 조회 START"));
		logger.info(generateLogMsg("Return Vo [UserInfoMgmtVo] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
//			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<UserInfoMgmtVo> list = userInfoMgmtService.getUserStopStatusListExcel(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "사용자정지상태관리_";//파일명
			String strSheetname = "사용자정지상태자료";//시트명
			
			String [] strHead = {"정지일자", "사용자ID", "사용자명", "상태","조직유형", "조직명","처리자", "처리일자"};
			String [] strValue = {"stopDt","usrId","usrNm","stopCdName","attcSctnNm","orgnNm","procNm","procDt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 7000, 5000,5000,7000,5000, 5000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list.iterator(), strHead, intWidth, strValue, request, response, intLen);
			
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
				
				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"MSP");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
     * @Description : 사용자맥주소 관리 초기 화면 호출
     * @Param  : userInfoMgmtVo
     * @Return : ModelAndView
     * @Author : 권성광
     * @Create Date : 2018. 9. 28.
     */
    @RequestMapping(value = "/org/userinfomgmt/userMacAddressMgmt.do", method={POST, GET})
    public ModelAndView userMacAddressMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소관리 초기 화면 START."));
        logger.info(generateLogMsg("================================================================="));
        
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(userInfoMgmtVo);

            // 본사 권한
            if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
            modelAndView.setViewName("org/userinfomgmt/msp_org_bs_1033");
            
            return modelAndView;
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }

    }
    
    /**
     * @Description : 사용자맥주소 관리 조회
     * @Param  : userInfoMgmtVo
     * @Return : ModelAndView
     * @Author : 권성광
     * @Create Date : 2018. 9. 28.
     */
    @RequestMapping("/org/userinfomgmt/userMacAddressMgmtList.json")
    public String selectUserMacAddressMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 관리 조회 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(userInfoMgmtVo);
            
            // 본사 권한
            if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            List<?> resultList = userInfoMgmtService.selectUserMacAddressMgmtList(userInfoMgmtVo);
            
            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
            
        } catch (Exception e) {
            resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
                //logger.info(generateLogMsg(String.format("사용자맥주소 관리 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
                throw new MvnoErrorException(e);
            }
            
        }
        
        model.addAttribute("result", resultMap);
        return "jsonView";
    }
    
    /**
     * @Description : 사용자맥주소 관리 등록
     * @Param  : userInfoMgmtVo
     * @Return : String
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    @RequestMapping("/org/userinfomgmt/insertUserMacAddressMgmt.json")
    public String insertUserMacAddressMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 관리 등록 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        int resultCnt = 0;
        
        try {
            //----------------------------------------------------------------
            //  Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(userInfoMgmtVo);
            
            // 본사 권한
            if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            userInfoMgmtVo.setOldMacAddr(userInfoMgmtVo.getMacAddr());
            userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
            userInfoMgmtVo.setActionFlag("I");
            
            resultCnt = userInfoMgmtService.insertUserMacAddressMgmt(userInfoMgmtVo);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            resultMap.put("resultCnt", resultCnt);
                        
            logger.info(generateLogMsg("등록 건수 = " + resultCnt));
            
        } catch (Exception e) {
            
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", messageSource.getMessage("fail.common.msg", null, Locale.getDefault()));
            throw new MvnoErrorException(e);
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        
        return "jsonView";
    }

    /**
     * @Description : 사용자맥주소관리 수정
     * @Param  :
     * @Return : String
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    @RequestMapping("/org/userinfomgmt/updatUserMacAddressMgmt.json")
    public String updateUserMacAddressMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소관리 수정 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            //----------------------------------------------------------------
            //  Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(userInfoMgmtVo);
            
            // 본사 권한
            if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
            userInfoMgmtVo.setActionFlag("U");
                        
            int resultCnt = userInfoMgmtService.updateUserMacAddressMgmt(userInfoMgmtVo);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            resultMap.put("resultCnt", resultCnt);
            
            logger.info(generateLogMsg("수정 건수 = " + resultCnt));
            
        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", messageSource.getMessage("fail.common.msg", null, Locale.getDefault()));
        }
        
        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";
    }
    
    /**
     * @Description : 사용자맥주소 관리 삭제
     * @Param  :
     * @Return : String
     * @Author : 권성광
     * @Create Date : 2018. 10. 216.
     */
    @RequestMapping("/org/userinfomgmt/deleteUserMacAddressMgmt.json")
    public String deleteUserMacAddressMgmt(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, SessionStatus status)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 관리 삭제 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(userInfoMgmtVo);
            
            // 본사 권한
            if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            userInfoMgmtVo.setOldMacAddr(userInfoMgmtVo.getMacAddr());
            userInfoMgmtVo.setRvisnId(loginInfo.getUserId());
            userInfoMgmtVo.setActionFlag("D");
            
            int resultCnt = userInfoMgmtService.deleteUserMacAddressMgmt(userInfoMgmtVo);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            
            logger.info(generateLogMsg("삭제 건수 = " + resultCnt));
        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", messageSource.getMessage("fail.common.msg", null, Locale.getDefault()));
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);

        return "jsonView";
    }
    
    /**
    * @Description : 사용자맥주소 중복체크
    * @Param  :
    * @Return : String
    * @Author : 권성광
    * @Create Date : 2014. 9. 27.
     */
    @RequestMapping("/org/userinfomgmt/isExistMacAddress.json")
    public String isExistMacAddress(UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, HttpServletRequest request, HttpServletResponse response){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 중복체크 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try{
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(userInfoMgmtVo);
            
            // 본사 권한
            if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            if(userInfoMgmtVo.getOldMacAddr().length() < 1){
                userInfoMgmtVo.setOldMacAddr(userInfoMgmtVo.getMacAddr());
            }

            int returnCnt = userInfoMgmtService.isExistMacAddress(userInfoMgmtVo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            resultMap.put("resultCnt", returnCnt);

            logger.info(generateLogMsg("중복 건수 = " + returnCnt));

        } catch (Exception e) {
            resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
            if ( ! getErrReturn(e, resultMap))
            {
                throw new MvnoErrorException(e);
            }
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);

        return "jsonView";
    }
    
    /**
     * @Description : 사용자 조회 초기 화면 호출
     * @Param  : void
     * @Return : String
     * @Author : 권성광
     * @Create Date : 2018. 10. 18.
     */
    @RequestMapping(value = "/org/userinfomgmt/salesUserInfoMgmt.do", method={POST, GET})
    public ModelAndView salesUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 조회 초기 화면 START."));
        logger.info(generateLogMsg("================================================================="));
        
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(userInfoMgmtVo);
            
            // 본사 권한
            if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
            
            modelAndView.setViewName("org/userinfomgmt/msp_org_bs_1034");
            
            return modelAndView;
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }

    }
    
    /**
     * @Description : 사용자조회 리스트
     * @Param  : userInfoMgmtVo
     * @Return : ModelAndView
     * @Author : 권성광
     * @Create Date : 2018. 10. 17.
     */
    @RequestMapping("/org/userinfomgmt/salesUserInfoMgmtList.json")
    public String selectSalesUserInfoMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자조회 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(userInfoMgmtVo);
            
            // 본사 권한
            if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            List<?> resultList = userInfoMgmtService.selectSalesUserInfoMgmtList(userInfoMgmtVo);
            
            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
            
        } catch (Exception e) {
            resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
                //logger.info(generateLogMsg(String.format("사용자조회 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
                throw new MvnoErrorException(e);
            }
            
        }
        
        model.addAttribute("result", resultMap);
        return "jsonView";
    }
    
    /**
     * 사용자조회 엑셀다운로드
     * @param searchVO
     * @param request
     * @param response
     * @param paramMap
     * @param model
     * @return
     */
    @RequestMapping(value="/org/userinfomgmt/salesUserInfoMgmtListExcel.json")
    public String getSalesUserInfoMgmtListExcel(@ModelAttribute("searchVO")  UserInfoMgmtVo searchVO,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    @RequestParam Map<String, Object> paramMap,
                    ModelMap model) {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 조회 엑셀다운로드 조회 START"));
        logger.info(generateLogMsg("Return Vo [UserInfoMgmtVo] = " + searchVO.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        
        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);
//          loginInfo.putSessionToParameterMap(paramMap);
            
            // 본사 화면인 경우
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            List<UserInfoMgmtVo> list = userInfoMgmtService.getSalesUserInfoMgmtListExcel(searchVO);
            
            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "사용자조회_";//파일명
            String strSheetname = "사용자조회";//시트명
            
            String [] strHead = {"사용자ID", "사용자명", "상태", "조직명", "소속구분", "핸드폰번호", "최종로그인일시", "등록자", "등록일시", "수정자", "수정일시"};
            String [] strValue = {"usrId","usrNm","usgYn", "orgnNm", "orgTypeNm", "mblphnNum", "lastLoginDt", "regstNm", "regstDttm", "rvisnNm", "rvisnDttm"};
            //엑셀 컬럼 사이즈
            int[] intWidth = {4000, 7000, 3000, 7000, 4000, 5000, 6000, 4000, 6000, 4000, 6000};
            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            
            //파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
            // rqstMgmtService 함수 호출
            String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list.iterator(), strHead, intWidth, strValue, request, response, intLen);
            
            file = new File(strFileName);
            
            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());
            
            in = new FileInputStream(file);
            
            out = response.getOutputStream();
            
            int temp = -1;
            while((temp = in.read()) != -1){
                out.write(temp);
            }
            
            
            //=======파일다운로드사유 로그 START==========================================================
            if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
                String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
                
                if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
                
                if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
                
                paramMap.put("FILE_NM"   ,file.getName());              //파일명
                paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
                paramMap.put("DUTY_NM"   ,"MSP");                       //업무명
                paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
                paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
                paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
                paramMap.put("DATA_CNT", 0);                            //자료건수
                paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
                
                fileDownService.insertCmnFileDnldMgmtMst(paramMap);
            }
            //=======파일다운로드사유 로그 END==========================================================
            
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "다운로드성공");
            
        } catch (Exception e) {
            
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
            resultMap.put("msg", returnMsg);
            throw new MvnoErrorException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new MvnoErrorException(e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    throw new MvnoErrorException(e);
                }
            }
        }
        file.delete();
        
        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        
        return "jsonView";
    }
    
	/**
	 * @Description : 사용자 승인 화면 호출
	 */
	@RequestMapping(value = "/org/userinfomgmt/userApproval.do", method={POST, GET})
	public ModelAndView userApproval(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 승인 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtVo.setUsrId(loginInfo.getUserId());
			userInfoMgmtVo.setUsrNm(userInfoMgmtService.selectUserInfoMgmt(userInfoMgmtVo).getUsrNm());
			
			if (!(userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()))){
				//admin 권한자외에는 본인것만 조회 되도록
				model.addAttribute("appUsrId",userInfoMgmtVo.getUsrId());
				model.addAttribute("appUsrNm",userInfoMgmtVo.getUsrNm());
				model.addAttribute("usrFindAuth","N");
			}
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("org/userinfomgmt/msp_cmn_user_1002");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	
	/**
	 * @Description : 사용자 승인 리스트 조회
	 */
	@RequestMapping("/org/userinfomgmt/userApprovalList.json")
	public String selectUserApprovalList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 승인 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);

			if (!(userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()))){
				//admin 권한자외에는 본인것만 조회 되도록
				userInfoMgmtVo.setAppUsrId(loginInfo.getUserId());    /** 사용자ID */
			}

			List<?> resultList = userInfoMgmtService.selectUserApprovalList(userInfoMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 사용자 승인 히스토리 조회
	 */
	@RequestMapping("/org/userinfomgmt/userApprovalHist.json")
	public String selectUserApprovalHist(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 승인 히스토리 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);

			List<?> resultList = userInfoMgmtService.selectUserApprovalHist(userInfoMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 사용자 승인
	 */
	@RequestMapping("/org/userinfomgmt/updateUserApproval.json")
	public String updateUserApproval(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 승인 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());

			userInfoMgmtVo.setPass(getDefaultPass());
			
			int returnCnt = 0;
			
			returnCnt = userInfoMgmtService.updateUserApproval(userInfoMgmtVo);

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));

			userInfoMgmtVo.setMblphnNum(userInfoMgmtVo.getMblphnNum1()+userInfoMgmtVo.getMblphnNum2()+userInfoMgmtVo.getMblphnNum3());
			
			if(returnCnt > 0){
				KtMsgQueueReqVO vo = new KtMsgQueueReqVO();
				vo.setMsgType("2");
				vo.setMessage("ID : " + userInfoMgmtVo.getUsrId() + "\n사용자 요청이 승인되었습니다.\n초기비밀번호는 " + getDefaultPass() + " 입니다.");
				vo.setCallbackNum(propertiesService.getString("otp.sms.callcenter"));
				vo.setSubject("사용자 요청 결과");
				vo.setRcptData(userInfoMgmtVo.getMblphnNum());
				vo.setReserved01("MSP");
				vo.setReserved02("MSP2000114");
				vo.setReserved03(loginInfo.getUserId());
				
				userInfoMgmtService.insertKtMsgQueue(vo);

				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			} else {
				resultMap.put("code", "NOK");
				resultMap.put("msg", "이미 처리된 사용자입니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", messageSource.getMessage("fail.common.msg", null, Locale.getDefault()));
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	/**
	 * @Description : 사용자반려
	 */
	@RequestMapping("/org/userinfomgmt/deleteUserApproval.json")
	public String deleteUserApproval(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, SessionStatus status)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 승인 반려 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtVo.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			userInfoMgmtVo.setRvisnId(loginInfo.getUserId());

			int returnCnt = 0;
			
			returnCnt = userInfoMgmtService.deleteUserApproval(userInfoMgmtVo);

			logger.info(generateLogMsg("삭제 건수 = " + returnCnt));
			
			if(returnCnt > 0){
				KtMsgQueueReqVO vo = new KtMsgQueueReqVO();
				vo.setMsgType("2");
				vo.setMessage("ID : " + userInfoMgmtVo.getUsrId() + "\n사용자 요청이 반려되었습니다.\n반려사유 : " + userInfoMgmtVo.getRefRsn() + "\n재요청 바랍니다.");
				vo.setCallbackNum(propertiesService.getString("otp.sms.callcenter"));
				vo.setSubject("사용자 요청 결과");
				vo.setRcptData(userInfoMgmtVo.getMblphnNum());
				vo.setReserved01("MSP");
				vo.setReserved02("MSP2000114");
				vo.setReserved03(pRequestParamMap.get("usrId").toString());
				
				loginService.insertKtMsgQueue(vo);

				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			} else {
				resultMap.put("code", "NOK");
				resultMap.put("msg", "이미 처리된 사용자입니다.");
			}
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 파일명 가져오기
	 */
	@RequestMapping("/org/userinfomgmt/getFile.json")
	public String getFile(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, SessionStatus status)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일명 가져오기 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("userInfoMgmtVo >>>>>>> " + userInfoMgmtVo);
		logger.info("pRequestParamMap >>>>>" + pRequestParamMap);
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			String appFileNm1 = userInfoMgmtService.getFile(userInfoMgmtVo.getAppFileId1());
			String appFileNm2 = userInfoMgmtService.getFile(userInfoMgmtVo.getAppFileId2());
			
			resultMap.put("appFileNm1", appFileNm1);
			resultMap.put("appFileNm2", appFileNm2);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : 고객정보 상세조회 이력 생성
	 */
	@RequestMapping("/org/userinfomgmt/insertSearchLog.json")
	public String insertSearchLog(
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap,
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객정보 상세조회 이력 생성 START."));
		logger.info(generateLogMsg("================================================================="));
		
		logger.info("pRequestParamMap >>>>>" + pRequestParamMap);
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			if("Y".equals(maskingYn) || "1".equals(maskingYn)) {
				String clientIp = "";

				if(request.getHeader("X-Forwarded-For") == null) {
					clientIp = request.getRemoteAddr();
				} else {
					clientIp = request.getHeader("X-Forwarded-For");
					
					if(clientIp !=null && ! clientIp.equals("") && clientIp.indexOf(",")>-1) {
						clientIp =  clientIp.split("\\,")[0].trim();
					}
				}
				
				pRequestParamMap.put("ipInfo", clientIp);
				
				userInfoMgmtService.insertSearchLog(pRequestParamMap);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "이력생성성공");
		}catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "이력생성실패");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : M2M 사용자 초기 화면 호출
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : wooki
	 * @CreateDate : 20240726
	 */
	@RequestMapping(value = "/org/userinfomgmt/shopM2MUserInfoMgmt.do", method={POST, GET})
	public ModelAndView shopM2MUserInfoMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		ModelAndView modelAndView = new ModelAndView();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);

			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			modelAndView.setViewName("org/userinfomgmt/msp_org_bs_1035");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	
	/**
	 * @Description : 사용자 패스워드 초기화 신청 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 19.
	 */
	@RequestMapping(value = "/org/userinfomgmt/userPwdReset.do", method={POST, GET})
	public ModelAndView userPwdReset(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 패스워드 초기화 신청 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			model.addAttribute("startDate",orgCommonService.getFirstDay());
			model.addAttribute("endDate",orgCommonService.getLastDay());
			modelAndView.setViewName("org/userinfomgmt/msp_cmn_user_1003");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 사용자 패스워드 초기화 신청 리스트 조회
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 19.
	 */
	@RequestMapping("/org/userinfomgmt/getUserPwdReset.json")
	public String getUserPwdReset(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 패스워드 초기화 신청 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = userInfoMgmtService.getUserPwdResetList(userInfoMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 사용자 패스워드 초기화
	 * @param VO
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/org/userinfomgmt/updateUserPwdReset.json")
		public String updateUserPwdReset(@ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo,
								@RequestBody String pJsonString,
								ModelMap model, 
								HttpServletRequest request, 
								HttpServletResponse response, 
								@RequestParam Map<String, Object> requestParamMap) {

		logger.debug("p_jsonString=" + pJsonString);
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 패스워드 초기화 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(userInfoMgmtVo);
		
		try {
			// 본사 화면인 경우
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List list = getVoFromMultiJson(pJsonString, "ALL", UserInfoMgmtVo.class );
			
			//사용자 정지상태 변경
			for ( int idx = 0 ; idx < list.size(); idx++)
			{
				UserInfoMgmtVo vo = (UserInfoMgmtVo) list.get(idx);			
				vo.setStatus("Y");
				vo.setProcId(userInfoMgmtVo.getSessionUserId());
				vo.setRvisnId(userInfoMgmtVo.getSessionUserId());
				
				logger.info("건수 >>>" + list.size());
				logger.info("처리자 >>>" + vo.getProcId());
				
				userInfoMgmtService.updateUserPwdReset(vo);
				
				String pass = getDefaultPass();
				vo.setPass(pass);
				
				userInfoMgmtService.initUserPassword(vo);
			
			}
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 사용자 패스워드 초기화 취소
	 * @param VO
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/org/userinfomgmt/updateUserPwdCansel.json")
	public String updateUserPwdCansel(@ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo,
			@RequestBody String pJsonString,
			ModelMap model, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestParam Map<String, Object> requestParamMap) {
		
		logger.debug("p_jsonString=" + pJsonString);
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 패스워드 초기화 취소 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(userInfoMgmtVo);
		
		try {
			// 본사 화면인 경우
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List list = getVoFromMultiJson(pJsonString, "ALL", UserInfoMgmtVo.class );
			
			//사용자 정지상태 변경
			for ( int idx = 0 ; idx < list.size(); idx++)
			{
				UserInfoMgmtVo vo = (UserInfoMgmtVo) list.get(idx);			
				vo.setStatus("C");
				vo.setProcId(userInfoMgmtVo.getSessionUserId());
				vo.setRvisnId(userInfoMgmtVo.getSessionUserId());
				
				logger.info("건수 >>>" + list.size());
				logger.info("처리자 >>>" + vo.getProcId());
				
				userInfoMgmtService.updateUserPwdReset(vo);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	
	/**
	 * @Description : 사용자 잠금상태 해제
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2026. 01. 28.
	 */
	@RequestMapping("/org/userinfomgmt/updateLockStatus.json")
	public String updateLockStatus(HttpServletRequest request, HttpServletResponse response, UserInfoMgmtVo userInfoMgmtVo, ModelMap model) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 잠금상태 해제 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			userInfoMgmtService.updateLockStatus(userInfoMgmtVo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", messageSource.getMessage("fail.common.msg", null, Locale.getDefault()));
			throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 사용자 잠금상태 초기화 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2026. 01. 28.
	 */
	@RequestMapping(value = "/org/userinfomgmt/userLockReset.do", method={POST, GET})
	public ModelAndView userLockReset(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 잠금상태 초기화 신청 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("org/userinfomgmt/msp_cmn_user_1004");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 사용자 잠금상태 초기화 리스트 조회
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : 이승국
	 * @Create Date : 2026. 01. 28.
	 */
	@RequestMapping("/org/userinfomgmt/getUserLockReset.json")
	public String getUserLockReset(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 잠금상태 초기화 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = userInfoMgmtService.getUserLockResetList(userInfoMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 사용자 잠금상태 초기화
	 * @param VO
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/org/userinfomgmt/updateUserLockReset.json")
		public String updateUserLockReset(@ModelAttribute("userInfoMgmtVo") UserInfoMgmtVo userInfoMgmtVo,
								@RequestBody String pJsonString,
								ModelMap model, 
								HttpServletRequest request, 
								HttpServletResponse response, 
								@RequestParam Map<String, Object> requestParamMap) {

		logger.debug("p_jsonString=" + pJsonString);
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 잠금상태 초기화 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(userInfoMgmtVo);
		
		try {
			// 본사 화면인 경우
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List list = getVoFromMultiJson(pJsonString, "ALL", UserInfoMgmtVo.class );
			
			//사용자 잠금상태 변경
			for ( int idx = 0 ; idx < list.size(); idx++)
			{
				UserInfoMgmtVo vo = (UserInfoMgmtVo) list.get(idx);			
				logger.info("건수 >>>" + list.size());
				userInfoMgmtService.updateLockStatus(vo);
			}
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
