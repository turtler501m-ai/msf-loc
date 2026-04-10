package com.ktis.msp.rcp.rcpMgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.ptnr.grpinsrmgmt.service.GrpInsrMgmtService;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrReqVO;
import com.ktis.msp.rcp.rcpMgmt.service.FathService;
import com.ktis.msp.rcp.rcpMgmt.service.NstepCallService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpSimpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.*;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Pattern;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class FathController extends BaseController {
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
	private FathService fathService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private GrpInsrMgmtService grpInsrService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name="nStepCallService")
	private NstepCallService nStepCallService;
	
	/**
	 * 셀프안면인증 URL 발급
	 */
	@RequestMapping("/rcp/rcpMgmt/insertFathSelfUrl.json")
	public String insertFathSelfUrl(HttpServletRequest request,
										 HttpServletResponse response,
										 @ModelAttribute("searchVO") FathVO searchVO,
										 ModelMap model,
										 @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			fathService.insertFathSelfUrl(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	
	@RequestMapping("/rcp/rcpMgmt/reqFathTxnRetv.json")
	public String osstServiceCall(@ModelAttribute("OsstReqVO") OsstReqVO searchVO
			,RcpDetailVO rcpDetailVO
			,HttpServletRequest request
			,HttpServletResponse response
			,@RequestParam Map<String, Object> paramMap
			,ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			rcpDetailVO.setSessionUserId(loginInfo.getUserId()); //userId set
			
			String paramFathTransacId = rcpDetailVO.getFathTransacId();
			//안면인증 필수값 확인
			if (KtisUtil.isEmpty(paramFathTransacId)) {
				resultMap.put("code", "9999");
				resultMap.put("msg", "안면인증정보가 존재하지 않습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}
			//안면인증 PUSH 조회
			FathVO fathPushInfo = fathService.selectFathResltPush(paramFathTransacId);
			if(fathPushInfo == null) {  //PUSH알림이 들어오지 않은경우
				//FS9로 연동
				resultMap = nStepCallService.processOsstFs9Service((String) propertiesService.getString("simpleOpenUrl"), rcpDetailVO, "");
			} else {
				if("0000".equals(fathPushInfo.getFathResltCd())) {
					resultMap = fathService.fathSuccRtn(fathPushInfo, rcpDetailVO);
				} else {
					resultMap = fathService.fathFailRtn(rcpDetailVO);
				}
			}
		}catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg","MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	@RequestMapping("/rcp/rcpMgmt/requestCustFathTxnSkip.json")
	public String requestCustFathTxnSkip(RcpDetailVO rcpDetailVO
			,HttpServletRequest request
			,HttpServletResponse response
			,ModelMap model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			rcpDetailVO.setSessionUserId(loginInfo.getUserId()); //userId set

			if (!fathService.isEnabledFT1()) {
				resultMap.put("code", "9991");
				resultMap.put("msg", "안면인증 SKIP 기능 비활성화");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

            //안면인증 필수값 확인
			if (KtisUtil.isEmpty(rcpDetailVO.getFathTransacId())) {
				resultMap.put("code", "9999");
				resultMap.put("msg", "안면인증정보가 존재하지 않습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			resultMap = fathService.processOsstFT1(rcpDetailVO);
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
