package com.ktis.msp.ptnr.ptnrmgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.ptnr.ptnrmgmt.service.PtnrCalcService;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrCalcVO;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInfoVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class PtnrCalcController extends BaseController  {

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private PtnrCalcService ptnrCalcService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * @Description : 제주항공지급내역 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/ptnr_point_jeju.do")
	public ModelAndView ptnr_point_jeju(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response,
					ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("ptnr/ptnrCalc/ptnr_point_jeju");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 제주항공지급내역 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcJejuList.json")
	public String getPtnrCalcJejuList(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제주항공지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcJejuList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "제주항공지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	

	/**
	 * @Description : 제주항공지급내역 합계
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcJejuListSum.json")
	public String getPtnrCalcJejuListSum(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제주항공지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcJejuListSum(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
						
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "제주항공지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		
	
	//////////////////////////////
	
	/**
	 * @Description : 기프티쇼지급내역 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/ptnr_point_gifti.do")
	public ModelAndView ptnr_point_gifti(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response,
					ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("ptnr/ptnrCalc/ptnr_point_gifti");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 기프티쇼지급내역 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcGiftiList.json")
	public String getPtnrCalcGiftiList(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("기프티쇼지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcGiftiList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "기프티쇼지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	

	/**
	 * @Description : 기프티쇼지급내역 합계
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcGiftiListSum.json")
	public String getPtnrCalcGiftiListSum(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("기프티쇼지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcGiftiListSum(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
						
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "기프티쇼지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	
	/////////////////////////////////

	/**
	 * @Description : 티머니지급내역 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/ptnr_point_tmoney.do")
	public ModelAndView ptnr_point_tmoney(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response,
					ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("ptnr/ptnrCalc/ptnr_point_tmoney");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 티머니지급내역 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcTmoneyList.json")
	public String getPtnrCalcTmoneyList(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("티머니지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcTmoneyList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "티머니지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	

	/**
	 * @Description : 티머니지급내역 합계
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcTmoneyListSum.json")
	public String getPtnrCalcTmoneyListSum(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("티머니지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcTmoneyListSum(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
						
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "티머니지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	
	////////////////////////////////////////

	/**
	 * @Description : 구글PLAY지급내역 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/ptnr_point_googleplay.do")
	public ModelAndView ptnr_point_googleplay(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response,
					ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("ptnr/ptnrCalc/ptnr_point_googleplay");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 구글PLAY지급내역 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcGooglePlayList.json")
	public String getPtnrCalcGooglePlayList(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("구글PLAY지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcGooglePlayList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "구글PLAY지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	


	/**
	 * @Description : 구글PLAY지급내역 합계
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcGooglePlayListSum.json")
	public String getPtnrCalcGooglePlayListSum(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("구글PLAY지급내역 합계 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcGooglePlayListSum(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
						
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "구글PLAY지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	

	/**
	 * @Description : 구글PLAY지급내역 조회 Excel Download
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcGooglePlayListEx.json")
	public String ptnrCalcGooglePlayListEx(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("구글PLAY지급내역 조회 Excel Download START."));
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
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","PTNR");
			excelMap.put("MENU_NM","구글PLAY지급내역");
			String searchVal = "정산월:"+searchVO.getFromSearchYm()+ " ~ " + searchVO.getToSearchYm() +
					"|구매유형:"+searchVO.getSvcType()+
					"|할인율:"+searchVO.getDcCd()+
					"|지급결과:"+searchVO.getResultCd() +
					"|검색구분: [ "+searchVO.getSearchCd() + " ] " + searchVO.getSearchVal()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				

			List<?> resultList = ptnrCalcService.getPtnrCalcGooglePlayListEx(searchVO, pRequestParamMap);
			
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "구글PLAY지급내역_";//파일명
			String strSheetname = "구글PLAY지급내역";//시트명

			String [] strHead = {"전화번호","고객명","계약번호","구매일","정산월","구매유형","구매유형명","할인율","지급포인트","지급결과","거래번호"}; //11
			String [] strValue = {"subscriberNo","cusNm","contractNum","regstDt","linkYm","svcTypeNm","svcNm","dcCdNm","reqPoint","resultCd","dealNum"}; //11
			int[] intWidth = {6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000};	
			int[] intLen = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}; // 11

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);

			logger.info("strFileName : "+strFileName);
			logger.info("#################### 데이터 엑셀로 변환 종료 #####################");
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
			
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"PTNR");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID", exclDnldId);
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		}finally {
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
	
	////////////////////////////////////////


	/**
	 * @Description : 롯데멤버스지급내역 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/ptnr_point_lpoint.do")
	public ModelAndView ptnr_point_lpoint(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response,
					ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("ptnr/ptnrCalc/ptnr_point_lpoint");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 롯데멤버스지급내역 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcLpointList.json")
	public String getPtnrCalcLpointList(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("롯데멤버스지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcLpointList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "롯데멤버스지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	

	/**
	 * @Description : 롯데멤버스지급내역 합계
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcLpointListSum.json")
	public String getPtnrCalcLpointListSum(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("롯데멤버스지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcLpointListSum(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
						
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "롯데멤버스지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		
	

	/**
	 * @Description : 롯데멤버스정산내역
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcLpointSttlList.json")
	public String getPtnrCalcLpointSttlList(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("롯데멤버스지급내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrCalcService.getPtnrCalcLpointSttlList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
						
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "롯데멤버스지급내역"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	

	/**
	 * @Description : 롯데멤버스지급내역 엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcLpointListEx.json")
	public String getPtnrCalcLpointListEx(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("롯데멤버스지급내역 엑셀다운 START."));
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
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","PTNR");
			excelMap.put("MENU_NM","롯데멤버스지급내역");
			String searchVal = "연동년월:"+searchVO.getSearchYm()+
					"|고객명:"+searchVO.getCustNm()+
					"|지급결과:"+searchVO.getResultCd()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> lpointList = ptnrCalcService.getPtnrCalcLpointListEx(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				//e1.printStackTrace();
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "롯데멤버스지급내역_";//파일명
			String strSheetname = "롯데멤버스지급내역";//시트명
			
			String [] strHead = {"연동년월","사용년월","고객명","지급포인트","지급결과","지급결과내용"}; 
			String [] strValue = {"linkYm","billYm","custNm","reqPoint","resultCd","resMsg"};
			int[] intWidth = {3000, 3000, 9000, 6000, 6000, 9000};	
			int[] intLen = {0, 0, 0, 1, 0, 0}; // 6

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			
			if(lpointList.size() <= 1000) {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, lpointList, strHead, intWidth, strValue, request, response, intLen);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, lpointList, strHead, strValue, request, response);
			}
			logger.info("strFileName : "+strFileName);
			logger.info("#################### 데이터 엑셀로 변환 종료 #####################");
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
			
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"PTNR");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID", exclDnldId);
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		}finally {
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
    * @Description : 미래에셋가입자정보 화면
    * @Param  : 
    * @Return : ModelAndView
    */
   @RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcMrAsstView.do")
   public ModelAndView getPtnrCalcMrAsstView(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response,
                                             ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));

	     modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.setViewName("ptnr/ptnrCalc/ptnr_point_mirae");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoRunException(-1, "");
      }
      
   }
   
   /**
    * @Description : 미래에셋가입자정보 조회
    * @Param  : 
    * @Return : ModelAndView
    */
   @RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcMrAsstList.json")
   public String getPtnrCalcMrAsstList(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response, 
                                       ModelMap model, 
                                       @RequestParam Map<String, Object> pRequestParamMap) {
      
      logger.info(generateLogMsg("================================================================="));
      logger.info(generateLogMsg("미래에셋가입자정보 조회 START."));
      logger.info(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         loginInfo.putSessionToParameterMap(pRequestParamMap);
         
         List<?> resultList = ptnrCalcService.getPtnrCalcMrAsstList(searchVO, pRequestParamMap);
         
         resultMap = makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "미래에셋가입자정보");
         throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      return "jsonView";
      
   }  
   

   /**
    * @Description : 미래에셋생명연동내역 엑셀 다운로드
    * @Param  : 
    * @Return : ModelAndView
    */
   @RequestMapping(value="/ptnr/ptnrCalc/getPtnrCalcMrAsstListByExcel.json")
   public String getPtnrCalcMrAsstListByExcel(@ModelAttribute("searchVO") PtnrCalcVO searchVO, 
               HttpServletRequest request, 
               HttpServletResponse response, 
               ModelMap model, 
               @RequestParam Map<String, Object> pRequestParamMap) {
      
      
      logger.info(generateLogMsg("================================================================="));
      logger.info(generateLogMsg("미래에셋생명연동내역 엑셀다운 START."));
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
         loginInfo.putSessionToParameterMap(pRequestParamMap);
         
         //----------------------------------------------------------------
         // 엑셀다운로드 HISTORY 저장
         Map<String, Object> excelMap = new HashMap<String, Object>();
         int exclDnldId = fileDownService.getSqCmnExclDnldHst();
         excelMap.put("EXCL_DNLD_ID", exclDnldId);
         excelMap.put("MENU_ID",request.getParameter("menuId"));
         excelMap.put("USR_ID",loginInfo.getUserId());
         excelMap.put("USR_NM",loginInfo.getUserName());
         excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
         excelMap.put("DUTY_NM","PTNR");
         excelMap.put("MENU_NM","미래에셋생명연동내역");
         String searchVal = "연동년월:"+searchVO.getFromSearchYm()+
               " ~ "+searchVO.getToSearchYm()+
               "|전화번호:"+searchVO.getSubscriberNo()+
               "|보험상태:"+searchVO.getInsrStatCd()
               ;
         if(searchVal.length() > 500) {
            searchVal = searchVal.substring(0, 500);
         }
         
         excelMap.put("SEARCH_VAL",searchVal);
         fileDownService.insertCmnExclDnldHst(excelMap);
         excelMap.clear();
         //----------------------------------------------------------------
            
         List<?> aryRsltList = ptnrCalcService.getPtnrCalcMrAsstListByExcel(searchVO, pRequestParamMap);
         
         String serverInfo = "";
         try {
            serverInfo = propertiesService.getString("excelPath");
         } catch(Exception e1) {
            logger.error("ERROR Exception");
            //e1.printStackTrace();
            throw new MvnoErrorException(e1);
         }
         
         String strFilename = serverInfo  + "미래에셋생명연동내역_";//파일명
         String strSheetname = "미래에셋생명연동내역";//시트명
         
         String [] strHead = {"연동년월","계약번호","고객명","선후불","데이터타입","전화번호","보험상태","보험가입일","보험해지일","광고비"}; 
         String [] strValue = {"ifYm","contractNum","subLinkName","serviceTypeNm","dataType","subscriberNo","insrStatNm","insrJoinDt","insrTmntDt","ktmAdFee"};
         int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
         int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
         
         //파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
         logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
         String strFileName = "";
         
/*         try {
         } catch (Exception e) {
          //  logger.error(e);
         }*/
         if(aryRsltList.size() <= 1000) {
            strFileName = fileDownService.excelDownProc(strFilename, strSheetname, aryRsltList, strHead, intWidth, strValue, request, response, intLen);
         } else {
            strFileName = fileDownService.csvDownProcStream(strFilename, aryRsltList, strHead, strValue, request, response);
         }
         logger.info("strFileName : "+strFileName);
         logger.info("#################### 데이터 엑셀로 변환 종료 #####################");
         
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
         
            pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
            pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PTNR");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
            pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId());   //사용자ID
            pRequestParamMap.put("EXCL_DNLD_ID", exclDnldId);
            
            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
         }
         //=======파일다운로드사유 로그 END==========================================================
         
         resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
         resultMap.put("msg", "다운로드성공");
         
      } catch (Exception e) {
         resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
         resultMap.put("msg", returnMsg);
         throw new MvnoErrorException(e);
      }finally {
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
   
}
