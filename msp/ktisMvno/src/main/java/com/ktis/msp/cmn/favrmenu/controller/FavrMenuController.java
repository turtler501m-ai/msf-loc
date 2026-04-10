package com.ktis.msp.cmn.favrmenu.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.favrmenu.service.FavrMenuService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import com.ktis.msp.base.exception.MvnoErrorException;

/**
 * @Class Name : AuthMgmtController.java
 * @Description : AuthMgmtController Class
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.09.01     임지혜          최초생성
 * 
 *
 * @author 임지혜
 */

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FavrMenuController.java
 * 3. Package	: com.ktis.msp.cmn.favrmenu.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:53:16
 * </PRE>
 */
@Controller
public class FavrMenuController extends BaseController {

	@Autowired
	private FavrMenuService favrMenuService;
	
	/**
	 * 즐겨찾기 화면
	 */
	/**
	 * <PRE>
	 * 1. MethodName: usrGrp
	 * 2. ClassName	: FavrMenuController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:17
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param request
	 * 		@param searchVO
	 * 		@param response
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/favrmenu/favrMenuForm.do")
	public ModelAndView favrMenuForm(ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap) 
	{
		
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 즐겨찾기 화면");
		logger.info("===========================================");
		

		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/cmn/favrmenu/msp_org_bs_1030_usrGrp");
		
		return mv;
	}
	
	/**
	 * 즐겨찾기 목록
	 */
	/**
	 * <PRE>
	 * 1. MethodName: prgmList
	 * 2. ClassName	: FavrMenuController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:19
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/favrmenu/favrMenuList.json")
	public String  favrMenuList(ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap) 
	{
		
		try{
			logger.info("===========================================");
			logger.info("======  FavrMenuController.favrMenuList -- 즐겨찾기 목록  ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  favrMenuService.favrMenuList(pRequestParamMap);
			
			EgovMap lResultListRow = null;
			
			//----------------------------------------------------------------
			// root
			//----------------------------------------------------------------
			
			Map<String, Object> itemMapRoot = new HashMap<String, Object>();
			itemMapRoot.put("id", 0);
			itemMapRoot.put("text", "HOME");
			
			List<Map> lUserDataListRoot = new ArrayList();

			Map<String, Object> lUserDataMapMenuIdRoot = new HashMap<String, Object>();
			lUserDataMapMenuIdRoot.put("content", "HOME");
			lUserDataMapMenuIdRoot.put("name", "menuId");
			lUserDataListRoot.add(0, lUserDataMapMenuIdRoot);

			Map<String, Object> lUserDataMapUrlRoot = new HashMap<String, Object>();
			lUserDataMapUrlRoot.put("content", null);
			lUserDataMapUrlRoot.put("name", "url");
			lUserDataListRoot.add(0, lUserDataMapUrlRoot);
			
			Map<String, Object> lUserDataMapIbsheetYnRoot = new HashMap<String, Object>();
			lUserDataMapIbsheetYnRoot.put("content", null);
			lUserDataMapIbsheetYnRoot.put("name", "ibsheetYn");
			lUserDataListRoot.add(0, lUserDataMapIbsheetYnRoot);

			itemMapRoot.put("userdata", lUserDataListRoot);
			
			
			List<Map> lItemList = new ArrayList();
			//----------------------------------------------------------------
			// DB select 
			//----------------------------------------------------------------
			for ( int inx = 0 ; inx <  resultList.size(); inx++)
			{
				 
				lResultListRow = (EgovMap) resultList.get(inx );
				
				Map<String, Object> itemMap = new HashMap<String, Object>();
				itemMap.put("id", lResultListRow.get("id"));
				itemMap.put("text", lResultListRow.get("text"));
				itemMap.put("ibsheetYn", lResultListRow.get("ibsheetYn"));
				itemMap.put("style", lResultListRow.get("style"));
				
				List<Map> lUserDataList = new ArrayList();

				Map<String, Object> lUserDataMapMenuId = new HashMap<String, Object>();
				lUserDataMapMenuId.put("content", lResultListRow.get("menuId"));
				lUserDataMapMenuId.put("name", "menuId");
				lUserDataList.add(0, lUserDataMapMenuId);

				Map<String, Object> lUserDataMapUrl = new HashMap<String, Object>();
				lUserDataMapUrl.put("content", lResultListRow.get("url"));
				lUserDataMapUrl.put("name", "url");
				lUserDataList.add(0, lUserDataMapUrl);
				
				Map<String, Object> lUserDataMapIbsheetYn = new HashMap<String, Object>();
				lUserDataMapIbsheetYn.put("content", lResultListRow.get("ibsheetYn"));
				lUserDataMapIbsheetYn.put("name", "ibsheetYn");
				lUserDataList.add(0, lUserDataMapIbsheetYn);
				
				itemMap.put("userdata", lUserDataList);
				
				lItemList.add(inx, itemMap);
				
			}
			
			itemMapRoot.put("item", lItemList);
			
			model.addAttribute("result", itemMapRoot);
		}catch ( Exception e)
		{
			model.addAttribute("result", "");
		}finally{
			dummyFinally();
		}
	

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		
		return "jsonViewArray";

	}
	

	/**
	 * 프로그램  insert
	 */
	/**
	 * <PRE>
	 * 1. MethodName: prgmInsert
	 * 2. ClassName	: FavrMenuController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:22
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/favrmenu/favrMenuInsert.json")
	public String  favrMenuInsert(ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap) 
	{
		
		logger.info("===========================================");
		logger.info("======  FavrMenuController.favrMenuInsert -- 즐겨찾기 insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);

		try {
			
			favrMenuService.favrMenuInsert(pRequestParamMap);
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch ( DuplicateKeyException e)
		{
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "이미 등록되어있습니다" );
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 프로그램  delete
	 */
	/**
	 * <PRE>
	 * 1. MethodName: prgmDelete
	 * 2. ClassName	: FavrMenuController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:25
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/favrmenu/favrMenuDelete.json")
	public String  favrMenuDelete(ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap) 
	{
		
		logger.info("===========================================");
		logger.info("======  FavrMenuController.favrMenuDelete -- 즐겨찾기 Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);

		try {
			
			int updateCnt =  favrMenuService.favrMenuDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	
}
