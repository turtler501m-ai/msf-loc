package com.ktis.msp.org.shopmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.orgmgmt.service.OrgMgmtService;
import com.ktis.msp.org.shopmgmt.service.ShopMgmtService;
import com.ktis.msp.org.shopmgmt.vo.ShopMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;
/**
 * @Class Name : ShopMgmtController
 * @Description : 판매점관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.03 이춘수 최초생성
 * @
 * @author :
 * @Create Date :
 */

@Controller
public class ShopMgmtController extends BaseController {

	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	protected OrgMgmtService orgMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private ShopMgmtService shopMgmtService;

	/** Constructor */
	public ShopMgmtController() {
		setLogPrefix("[ShopMgmtController] ");
	}


	/**
	 * 매핑판매점 리스트 조회
	 * @param shopMgmtVO
	 * @param model
	 * @param request
	 * @param response
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value="/org/shopmgmt/getMappingShopList.json")
	public String getMappingShopList(ShopMgmtVO shopMgmtVO, ModelMap model, HttpServletRequest request,
									HttpServletResponse response,
									@RequestParam Map<String, Object> pRequestParamMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("매핑판매점리스트 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//login Check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(shopMgmtVO);
			
			// 본사 권한
			if(!"10".equals(shopMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			loginInfo.putSessionToVo(shopMgmtVO);
			
			List<?> resultList = shopMgmtService.getMappingShopList(shopMgmtVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			resultMap.clear();
			 if ( ! getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			 }
		}

		return "jsonView";
	}

}
