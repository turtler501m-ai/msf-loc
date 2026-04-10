package com.ktis.msp.org.hndsetamtmgmt.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.hndsetamtmgmt.service.HndstAmtService;
import com.ktis.msp.org.hndsetamtmgmt.vo.HndstAmtVo;



/**
 * @Class Name : HndstAmtModelController
 * @Description : 제품 단가 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Controller
public class HndstAmtController extends BaseController {

	@Autowired
	private HndstAmtService hndstAmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService; 
	
	public HndstAmtController() {
		setLogPrefix("[HndstAmtController] ");
	}	
	
	/**
	 * @Description : 단통법 관련 보조금 MAX
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/hndsetamtmgmt/selectMaxAmt.json")
	public String selectMaxAmt(HndstAmtVo hndstAmtVo, ModelMap model, HttpServletRequest paramReq, HttpServletResponse paramRes)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단통법 관련 보조금 MAX START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* -- 로그인 조직정보 및 권한 체크 -- */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(hndstAmtVo);
			
			// 본사 권한
			if(!"10".equals(hndstAmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String strMaxAmt = hndstAmtService.selectMaxAmt(hndstAmtVo);
			
			resultMap.put("maxAmt", strMaxAmt);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
}
