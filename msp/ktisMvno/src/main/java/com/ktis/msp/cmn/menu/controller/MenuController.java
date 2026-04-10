package com.ktis.msp.cmn.menu.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.menu.service.MenuService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**  
 * @Class Name : 
 * @Description : 
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @
 * 
 * @author 
 * @version 1.0
 * @see
 * 
 */

@Controller
public class MenuController extends BaseController { 

	protected Log log = LogFactory.getLog(this.getClass());
	
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private MenuService  menuService;


	/**
	 * <PRE>
	 * 1. MethodName: menuPrgmMstList
	 * 2. ClassName	: MenuController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:59:49
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/menu/menuPrgmMstList.json")
	public String menuPrgmMstList(
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List resultListNew = new ArrayList();
		
		try{	
			logger.info("===========================================");
			logger.info("======  MenuController.menuPrgmMstList -- ======");
			logger.info("===========================================");
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
			List<?> resultList = menuService.menuPrgmMstList(pRequestParamMap);
			
			EgovMap lResultListRow = (EgovMap) resultList.get(0);
			Map<String, Object> lUserDataMap = new HashMap<String, Object>();
			
			lUserDataMap.put("menuId", lResultListRow.get("menuId"));
			lUserDataMap.put("url", lResultListRow.get("url") );
			lUserDataMap.put("menuLvlPrior", lResultListRow.get("menuLvlPrior"));
			lUserDataMap.put("menuLvlPrev", lResultListRow.get("menuLvlPrev"));
			lUserDataMap.put("menuLvl", lResultListRow.get("menuLvl"));
			lUserDataMap.put("ibsheetYn", lResultListRow.get("ibsheetYn"));
			
			List<Map> userDataList = new ArrayList();

			Map<String, Object> lUserDataMapMenuId = new HashMap<String, Object>();
			lUserDataMapMenuId.put("name", "menuId");
			lUserDataMapMenuId.put("content", lResultListRow.get("menuId"));
			userDataList.add(lUserDataMapMenuId);
			

			Map<String, Object> lUserDataMapUrl = new HashMap<String, Object>();
			lUserDataMapUrl.put("name", "url");
			lUserDataMapUrl.put("content", lResultListRow.get("url"));
			
			userDataList.add(lUserDataMapUrl);

			
			Map<String, Object> lUserDataMapMenuLvlPrior = new HashMap<String, Object>();
			lUserDataMapMenuLvlPrior.put("name", "menuLvlPrior");
			lUserDataMapMenuLvlPrior.put("content", lResultListRow.get("menuLvlPrior"));
			userDataList.add(lUserDataMapMenuLvlPrior);

			
			Map<String, Object> lUserDataMapMenuLvlPrev = new HashMap<String, Object>();
			lUserDataMapMenuLvlPrev.put("name", "menuLvlPrev");
			lUserDataMapMenuLvlPrev.put("content", lResultListRow.get("menuLvlPrev"));
			userDataList.add(lUserDataMapMenuLvlPrev);

			
			Map<String, Object> lUserDataMapMenuLvl = new HashMap<String, Object>();
			lUserDataMapMenuLvl.put("name", "menuLvl");
			lUserDataMapMenuLvl.put("content", lResultListRow.get("menuLvl"));
			userDataList.add(lUserDataMapMenuLvl);
			
			Map<String, Object> lUserDataMapIbsheetYn = new HashMap<String, Object>();
			lUserDataMapIbsheetYn.put("name", "ibsheetYn");
			lUserDataMapIbsheetYn.put("content", lResultListRow.get("ibsheetYn"));
			userDataList.add(lUserDataMapIbsheetYn);


			lResultListRow.put("userdata", userDataList);
			
			lResultListRow.remove("rowIndex");
			lResultListRow.remove("menuDsc");
			lResultListRow.remove("hghrMenuId");
			lResultListRow.remove("algnSeq");
			lResultListRow.remove("usgYn");
			lResultListRow.remove("lmtOrgnLvlCd");
			lResultListRow.remove("delYn");
			lResultListRow.remove("dutyId");
			lResultListRow.remove("prgmId");
			lResultListRow.remove("prgmNm");
			lResultListRow.remove("prgmDesc");
			lResultListRow.remove("prgmType");
			lResultListRow.remove("dutyNm");
			lResultListRow.remove("url");
			lResultListRow.remove("menuLvlPrior");
			lResultListRow.remove("menuLvlPrev");
			lResultListRow.remove("menuLvl");
			lResultListRow.remove("menuId");
			lResultListRow.remove("ibsheetYn");
			resultListNew.add(lResultListRow);
			
			List resultListCurrentLevel = new ArrayList();
			lResultListRow.put("item", resultListCurrentLevel);
			
			getHghrResultMap(resultList,  1, lResultListRow);
			logger.debug("=========================================xxxxxxxxxx");
			logger.debug(resultListNew);
			logger.debug("=========================================xxxxxxxxxx");

		}catch ( Exception e)
		{
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
			logger.debug(e);
		}finally{
//			sqlService.Close();
			dummyFinally();
		}
		
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultListNew.get(0));

		return "jsonViewArray";

	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName: getHghrResultMap
	 * 2. ClassName	: MenuController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:59:54
	 * </PRE>
	 * 		@return void
	 * 		@param p_resultList
	 * 		@param p_inx
	 * 		@param l_resultListRowPrior
	 */
	public void getHghrResultMap(List<?> pResultList, Integer ppInx, EgovMap lResultListRowPrior)  // pmd에 걸리는데 check가 잘못된거 같음 RECURSIVE라서 값 할당하여야 함*****************
	{
		
		int pInx = ppInx; // PMD
		List resultListCurrentLevel = new ArrayList();
		
		if ( pResultList.size()   <= pInx )
		{
			return ;
		}
		
		List lTempUserdataList1 = (List)lResultListRowPrior.get("userdata");
		int lMenuLvlUpper = 0;
		
		for( int inx = 0 ;inx < lTempUserdataList1.size();inx++)
		{
			Map lTempUserdataMap = (Map) lTempUserdataList1.get(inx);
			if ( lTempUserdataMap.get("name").equals("menuLvl"))
			{
				lMenuLvlUpper = Integer.parseInt((String) lTempUserdataMap.get("content"));
			}
		}

		EgovMap lResultListRow = null;
		
		lResultListRowPrior.put("item", resultListCurrentLevel);
		
		while( pInx  < pResultList.size())
		{
			 
			lResultListRow = (EgovMap) pResultList.get(pInx );

			int lMemuLvl = 0 ;
			
			if ( lResultListRow.containsKey("userdata"))
			{
				List lTempUserdataList = (List)lResultListRow.get("userdata");
				
				for( int inx = 0 ;inx < lTempUserdataList.size();inx++)
				{
					Map lTempUserdataMap = (Map) lTempUserdataList.get(inx);
					if ( lTempUserdataMap.get("name").equals("menuLvl"))
					{
						lMemuLvl = Integer.parseInt((String) lTempUserdataMap.get("content"));
					}

				}
			}else
			{
				lMemuLvl = Integer.parseInt((String) lResultListRow.get("menuLvl"));
			}

			
			if ( lMenuLvlUpper == lMemuLvl - 1 )
			{
				Map<String, Object> lUserDataMap = new HashMap<String, Object>();
				lUserDataMap.put("menuId", lResultListRow.get("menuId"));
				lUserDataMap.put("url", lResultListRow.get("url"));
				lUserDataMap.put("menuLvlPrior", lResultListRow.get("menuLvlPrior"));
				lUserDataMap.put("menuLvlPrev", lResultListRow.get("menuLvlPrev"));
				lUserDataMap.put("menuLvl", lResultListRow.get("menuLvl"));
				lUserDataMap.put("ibsheetYn", lResultListRow.get("ibsheetYn"));
				
				List<Map> userDataList = new ArrayList();

				Map<String, Object> lUserDataMapMenuId = new HashMap<String, Object>();
				lUserDataMapMenuId.put("name", "menuId");
				lUserDataMapMenuId.put("content", lResultListRow.get("menuId"));
				userDataList.add(lUserDataMapMenuId);
				

				Map<String, Object> lUserDataMapUrl = new HashMap<String, Object>();
				lUserDataMapUrl.put("name", "url");
				lUserDataMapUrl.put("content", lResultListRow.get("url"));
				userDataList.add(lUserDataMapUrl);

				
				Map<String, Object> lUserDataMapMenuLvlPrior = new HashMap<String, Object>();
				lUserDataMapMenuLvlPrior.put("name", "menuLvlPrior");
				lUserDataMapMenuLvlPrior.put("content", lResultListRow.get("menuLvlPrior"));
				userDataList.add(lUserDataMapMenuLvlPrior);

				
				Map<String, Object> lUserDataMapMenuLvlPrev = new HashMap<String, Object>();
				lUserDataMapMenuLvlPrev.put("name", "menuLvlPrev");
				lUserDataMapMenuLvlPrev.put("content", lResultListRow.get("menuLvlPrev"));
				userDataList.add(lUserDataMapMenuLvlPrev);

				
				Map<String, Object> lUserDataMapMenuLvl = new HashMap<String, Object>();
				lUserDataMapMenuLvl.put("name", "menuLvl");
				lUserDataMapMenuLvl.put("content", lResultListRow.get("menuLvl"));
				userDataList.add(lUserDataMapMenuLvl);
				
				Map<String, Object> lUserDataMapIbsheetYn = new HashMap<String, Object>();
				lUserDataMapIbsheetYn.put("name", "ibsheetYn");
				lUserDataMapIbsheetYn.put("content", lResultListRow.get("ibsheetYn"));
				userDataList.add(lUserDataMapIbsheetYn);

				lResultListRow.put("userdata", userDataList);
						
				
				lResultListRow.remove("rowIndex");
				lResultListRow.remove("menuDsc");
				lResultListRow.remove("hghrMenuId");
				lResultListRow.remove("algnSeq");
				lResultListRow.remove("usgYn");
				lResultListRow.remove("lmtOrgnLvlCd");
				lResultListRow.remove("delYn");
				lResultListRow.remove("dutyId");
				lResultListRow.remove("prgmId");
				lResultListRow.remove("prgmNm");
				lResultListRow.remove("prgmDesc");
				lResultListRow.remove("prgmType");
				lResultListRow.remove("dutyNm");
				lResultListRow.remove("url");
				lResultListRow.remove("menuLvlPrior");
				lResultListRow.remove("menuLvlPrev");
				lResultListRow.remove("menuLvl");
				lResultListRow.remove("menuId");
				lResultListRow.remove("ibsheetYn");	
				resultListCurrentLevel.add(lResultListRow);
			}
				
					
			if ( lMenuLvlUpper == lMemuLvl - 2  )
			{
				getHghrResultMap(pResultList,  pInx , (EgovMap) pResultList.get(pInx -1 ));
			}
			
			if ( lMenuLvlUpper >= lMemuLvl   )
			{
				return;
			}

			pInx ++;
		}
		
		

	}

	
}
