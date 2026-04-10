package com.ktis.msp.cmn.usrobjhst.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.usrobjhst.service.UsrObjHstService;
import com.ktis.msp.cmn.usrobjhst.vo.UsrObjHstVO;
import com.ktis.msp.org.common.service.OrgCommonService;


@Controller
public class UsrObjHstController extends BaseController {

	/** 공통 Utill 서비스 */
	@Autowired
	private OrgCommonService orgCommonService;

	/** 메뉴 권한 서비스 */
	@Autowired
    private MenuAuthService  menuAuthService; 

	@Autowired
    private UsrObjHstService usrObjHstService; 
	
	//v2018.11 PMD 적용 소스 수정
	/** propertiesService */
	/*@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;*/
	
	
	/**
	 * @Description : OBJECT 정보 화면
	 * @Param  : 
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2017. 03. 28.
	 */
	@RequestMapping(value = "/cmn/usrobjhst/getUsrObjHst.do", method={POST, GET})
	public ModelAndView getUsrObjHst(@ModelAttribute("searchVO") UsrObjHstVO searchVO,
				HttpServletRequest request, 
				HttpServletResponse response, 
				ModelMap model){
		
		ModelAndView modelAndView = new ModelAndView();

        LoginInfo loginInfo = new LoginInfo(request, response);
        
		try {
    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());
			    		
            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/cmn/usrobjhst/usrObjHst");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}		
	

	@RequestMapping(value = "/cmn/usrobjhst/getObjHstList.json", method={POST, GET})
	public String getObjHstList(@ModelAttribute("searchVO") UsrObjHstVO searchVO,
				HttpServletRequest request, 
				HttpServletResponse response, 
				@RequestParam Map<String, Object> paramMap,
				ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();
        
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			
			List<UsrObjHstVO> resultList = usrObjHstService.getObjHstList(searchVO, paramMap);
			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			}
			
			model.addAttribute("result", resultMap);
			logger.info(generateLogMsg("result:" + resultMap));
		} catch (Exception e) {
		    resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSPBTCH133", "OBJECT 이력관리"))
			{
			    throw new MvnoErrorException(e);
			}        
		}
		

		return "jsonView";
	}		
	
	

	@RequestMapping(value = "/cmn/usrobjhst/getObjSource.json", method={POST, GET})
	public String getObjSource(@ModelAttribute("searchVO") UsrObjHstVO searchVO,
				HttpServletRequest request, 
				HttpServletResponse response, 
				@RequestParam Map<String, Object> paramMap,
				ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();
        
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			
			List<UsrObjHstVO> resultList = usrObjHstService.getObjSource(searchVO, paramMap);
			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			}
			
			model.addAttribute("result", resultMap);
			logger.info(generateLogMsg("result:" + resultMap));
		} catch (Exception e) {
		    resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSPBTCH133", "OBJECT 이력관리"))
			{
			    throw new MvnoErrorException(e);
			}        
		}
		

		return "jsonView";
	}		

}
