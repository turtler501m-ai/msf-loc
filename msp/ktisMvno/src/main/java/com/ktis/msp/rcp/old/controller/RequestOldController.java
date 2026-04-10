package com.ktis.msp.rcp.old.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.mapper.MenuAuthMapper;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.old.service.RequestOldService;
import com.ktis.msp.rcp.old.vo.RequestOldVo;
import com.ktis.msp.rcp.old.vo.RequestVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


@Controller
public class RequestOldController extends BaseController {
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private RequestOldService requestService;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

//	public static void main(String[] args) {
//		//System.out.println(StringUtils.containsNone("사당동 ", "*"));
//	}
	

	
	@RequestMapping("/request/list.do")
	public String listHandler(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			@ModelAttribute("requestSearchVO") RequestOldVo searchVO,
			@RequestParam Map<String, Object> paramMap)  {
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//----------------------------------------------------------------
			// 권한 체크 로직
			//----------------------------------------------------------------
			menuAuthService.chkUsrGrpAuthListForButton(loginInfo.getUserId(), "A-PREVIOUS", "권한이 없습니다.");
			
			String pstate 				= searchVO.getSearchPstate() == null ? "" : searchVO.getSearchPstate();
			String requestStateCode	= searchVO.getSearchRequestStateCode() == null ? "" : searchVO.getSearchRequestStateCode();
			
			//20141001 이전 고객을 위한 세팅 값
			if(pstate == null || "".equals(pstate)){
				searchVO.setSearchPstate("00");
			}
			if(requestStateCode == null || "".equals(requestStateCode)){
				searchVO.setSearchRequestStateCode("21");
			}
			
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getCurrentPageNo());
			paginationInfo.setRecordCountPerPage(searchVO.getRecordCountPerPage());
			paginationInfo.setPageSize(searchVO.getPageSize());
			
			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			
			List<EgovMap> requestList = requestService.selectList(searchVO);
			
			if(requestList != null){
				requestList = requestService.decryptDBMSList(requestList);
			}
			
			HashMap<String, String> maskFields = requestService.setMaskMap();
			
			maskingService.setMask(requestList, maskFields, paramMap);
			
			model.addAttribute("requestList", requestList);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			searchVO.setTotalRecordCount(requestService.selectListCount(searchVO));
			
			return "mcp/requestOldList/List";
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}	
	
	@RequestMapping("/request/view.do")
	public String viewHandler(HttpServletRequest request, 
			HttpServletResponse response, 
			ModelMap model,
			@ModelAttribute("requestSearchVO") RequestOldVo searchVO,
			@RequestParam Map<String, Object> paramMap)  {
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			String requestKey = searchVO.getRequestKey();
			if(requestKey != null && !"".equals(requestKey)){
				RequestVO requestVO = requestService.selectRowByPk(requestKey);
				//복호화
				requestVO = requestService.decryptDBMS(requestVO);
				
				HashMap<String, String> maskFields = requestService.setMaskMap();
				maskingService.setMask(requestVO, maskFields, paramMap);
				
				model.addAttribute("requestVO", requestVO);
				model.addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response)); 
			}
			
			return "mcp/requestOldList/View";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping("/request/viewPrint.do")
	public String viewPrintHandler(HttpServletRequest request, 
			HttpServletResponse response, ModelMap model,			
			@RequestParam(value = "requestKey", required = true) Integer requestKey,
			@ModelAttribute("requestSearchVO") RequestOldVo searchVO,
			@RequestParam Map<String, Object> paramMap)  {
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			RequestVO requestVO = requestService.selectRowByPk(String.valueOf(requestKey.intValue()));
			//복호화
			requestVO = requestService.decryptDBMS(requestVO);
			
			HashMap<String, String> maskFields = requestService.setMaskMap();
			maskingService.setMask(requestVO, maskFields, paramMap);
			
			model.addAttribute("requestVO", requestVO);
			model.addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response)); 
			return "mcp/requestOldList/ViewPrint";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping("/request/excelList.do")
	public ModelAndView excelListDefaultHandler(HttpServletRequest request, 
			HttpServletResponse response, ModelMap model,
			@ModelAttribute("requestSearchVO") RequestOldVo searchVO,
			@RequestParam Map<String, Object> paramMap)  {
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			String downloadName = "가입신청목록";
			if(searchVO.getSearchPstate().equals("00")){
				downloadName = downloadName.concat("[정상처리]");
			}else if(searchVO.getSearchPstate().equals("10")){
				downloadName = downloadName.concat("[고객취소]");
			}else if(searchVO.getSearchPstate().equals("20")){
				downloadName = downloadName.concat("[관리자삭제]");
			}
			
			if(StringUtils.isNotEmpty(searchVO.getSearchStartDate()) || StringUtils.isNotEmpty(searchVO.getSearchEndDate())){
				downloadName = downloadName.concat("[");
				if(StringUtils.isNotEmpty(searchVO.getSearchStartDate()))
					downloadName = downloadName.concat(searchVO.getSearchStartDate());
				downloadName = downloadName.concat("-");
				if(StringUtils.isNotEmpty(searchVO.getSearchEndDate()))
					downloadName = downloadName.concat(searchVO.getSearchEndDate());			
				downloadName = downloadName.concat("]");
			}
			
			List<EgovMap> requestList = requestService.selectListForExcel(searchVO);
			
			if(requestList != null){
				requestList = requestService.decryptDBMSList(requestList);
			}
			
			HashMap<String, String> maskFields = requestService.setMaskMap();
			
			maskingService.setMask(requestList, maskFields, paramMap);
			
			model.addAttribute("list", requestList);
			ModelAndView mav =  new ModelAndView(new RcpExcleView());
			mav.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response)); 
			mav.addAllObjects(model);
			return mav;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
}
