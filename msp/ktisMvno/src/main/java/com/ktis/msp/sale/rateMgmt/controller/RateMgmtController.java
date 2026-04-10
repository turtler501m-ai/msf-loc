package com.ktis.msp.sale.rateMgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktis.msp.sale.rateMgmt.vo.DisAddSvcVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.sale.rateMgmt.service.RateMgmtService;
import com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class RateMgmtController extends BaseController {

	@Resource(name="rateMgmtService")
	private RateMgmtService rateMgmtService;

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private FileDownService  fileDownService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/sale/rateMgmt/getRateMgmtList.do")
	public ModelAndView getRateMgmtList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
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

			modelAndView.setViewName("sale/rateMgmt/rateMgmt");

			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}


	@RequestMapping(value="/sale/rateMgmt/getRateMgmtList.json")
	public String getRateMgmtList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateMgmtList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP2001011", "요금제관리"))
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
	 * 요금제 등록
	 * @param vo
	 * @param model
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/sale/rateMgmt/insertRateCd.json")
	public String insertRateCd(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			searchVO.setUserId(loginInfo.getUserId());

			rateMgmtService.insertRateCd(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	/**
	 * 요금제 수정
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/sale/rateMgmt/updateRateCd.json")
	public String updateRateCd(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			searchVO.setUserId(loginInfo.getUserId());

			rateMgmtService.updateRateCd(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 요금제그룹목록조회
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/sale/rateMgmt/getRateGrpMgmtList.json")
	public String getRateGrpMgmtList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateGrpMgmtList(searchVO);

			resultMap =  makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	/**
	 * 요금제그룹 등록
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/sale/rateMgmt/insertRateGrpCd.json")
	public String insertRateGrpCd(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			searchVO.setUserId(loginInfo.getUserId());

			rateMgmtService.insertRateGrpCd(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 요금제그룹 등록
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/sale/rateMgmt/deleteRateGrpCd.json")
	public String deleteRateGrpCd(@ModelAttribute("searchVO") RateMgmtVO searchVO,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			rateMgmtService.deleteRateGrpCd(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 부가서비스 목록 조회
	 */
	@RequestMapping(value = "/sale/rateMgmt/getAddSvcList.json")
	public String getAddSvcList(HttpServletRequest request, HttpServletResponse response
			, ModelMap modelMap
			, @RequestParam Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);

			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = rateMgmtService.getAddSvcList();

			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());



		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2001011", "요금제관리")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 평생할인 부가서비스 목록 조회
	 */
	@RequestMapping(value = "/sale/rateMgmt/getDisAddSvcList.json")
	public String getDisAddSvcList(HttpServletRequest request, HttpServletResponse response
			, ModelMap modelMap
			, @RequestParam Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);

			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = rateMgmtService.getDisAddSvcList();

			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2001011", "요금제관리")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 평생할인 부가서비스 관리
	 */
	@RequestMapping(value = "/sale/rateMgmt/manageDisAddSvcList.json")
	public String manageDisAddSvcList(HttpServletRequest request, HttpServletResponse response
										, ModelMap model
										, @RequestBody String data
										, @RequestParam Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);

			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			DisAddSvcVO vo = new ObjectMapper().readValue(data, DisAddSvcVO.class);

			vo.setSessionUserId(loginInfo.getUserId());
			rateMgmtService.manageDisAddSvcList(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch ( Exception e){
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					e.getMessage(), "MSP2001011", "요금제관리"))
			{
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	@RequestMapping("/sale/rateMgmt/getRateComboList.json")
	public String getRateComboList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			List<?> resultList = rateMgmtService.getRateComboList();

			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/rateMgmt/getPlcyComboList.json")
	public String getPlcyComboList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{


		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			List<?> resultList = rateMgmtService.getPlcyComboList(searchVO);

			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping(value="/sale/rateMgmt/getRateSpecList.json")
	public String getRateSpecList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateSpecList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping("/sale/rateMgmt/insertRateSpec.json")
	public String insertRateSpec(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			searchVO.setUserId(loginInfo.getUserId());

			rateMgmtService.insertRateSpec(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping(value="/sale/rateMgmt/getRateSpecHist.json")
	public String getRateSpecHist(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateSpecHist(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping(value="/sale/rateMgmt/getRateCancelSpecList.json")
	public String getRateCancelSpecList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateCancelSpecList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping("/sale/rateMgmt/insertRateCancelSpec.json")
	public String insertRateCancelSpec(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			searchVO.setUserId(loginInfo.getUserId());

			rateMgmtService.insertRateCancelSpec(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping(value="/sale/rateMgmt/getRateCancelSpecHist.json")
	public String getRateCancelSpecHist(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateCancelSpecHist(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping(value = "/sale/rateMgmt/getRateMgmtListExcel.json")
	@ResponseBody
	public String getRateMgmtListExcel(@ModelAttribute RateMgmtVO vo,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> pReqParamMap,
											ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			loginInfo.putSessionToParameterMap(pReqParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "요금제목록_";	/** 파일명 */
			String strSheetname = "요금제목록";				/** 시트명 */

			/* 요금제목록 조회 */
			List<RateMgmtVO> resultList = rateMgmtService.getRateMgmtListExcel(vo);

			//엑셀 제목
			ArrayList<String[]> aryHead = new ArrayList<String[]>();

			String[] strHead1 = {"기본정보", "", "", "", "",
								"", "", "", "", "",
								"", "", "", "", "",
								"할인금액", "", "", "",
								"단말할인 위약금", "", "요금할인 위약금", "", "심플할인 위약금", ""};

			aryHead.add(strHead1);

			String[] strHead2 = {"요금제그룹", "요금제코드", "요금제명", "출시일자", "서비스유형",
								"선후불구분", "요금제유형", "데이터유형", "기본료", "무료통화",
								"망내(분)", "망외(분)", "문자", "데이터", "온라인노출유형",
								"약정기간", "기본할인", "요금할인", "심플할인",
								"12개월", "18개월", "12개월", "18개월", "12개월", "18개월"};

			aryHead.add(strHead2);

			//셀병합
			ArrayList<int[]> aryMerged = new ArrayList<int[]>();

			int[] nMerged1 = {0,0,0,14};	//기본 정보
			aryMerged.add(nMerged1);

			int[] nMerged2 = {0,0,15,18};	//할인금액
			aryMerged.add(nMerged2);

			int[] nMerged3 = {0,0,19,20};	//단말할인
			aryMerged.add(nMerged3);

			int[] nMerged4 = {0,0,21,22};	//요금할인
			aryMerged.add(nMerged4);

			int[] nMerged5 = {0,0,23,24};	//심플할인
			aryMerged.add(nMerged5);

			String[] strValue = {"rateGrpNm", "rateCd", "rateNm", "applStrtDt", "serviceTypeNm",
								 "payClCdNm", "rateTypeNm", "dataType", "baseAmt", "freeCallCnt",
								 "nwInCallCnt", "nwOutCallCnt", "freeSmsCnt", "freeDataCnt", "onlineTypeNm",
								 "agrmTrm", "tmAmt", "rtAmt", "spAmt",
								 "tmAmt12", "tmAmt18", "rtAmt12", "rtAmt18", "spAmt12", "spAmt18"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {4000, 5000, 7000, 4000, 4000,
							  4000, 4000, 4000, 4000, 4000,
							  4000, 4000, 4000, 4000, 5000,
							  4000, 4000, 4000, 4000,
							  3000, 3000, 3000, 3000, 3000, 3000};

			int[] intLen = {0, 0, 0, 0, 0,
							0, 0, 0, 1, 0,
							0, 0, 0, 0, 0,
							1, 1, 1, 1,
							1, 1, 1, 1, 1, 1};

			String strFileName = fileDownService.excelCellMergedDownProc(strFilename, strSheetname, resultList.iterator(), aryHead, aryMerged, intWidth, strValue, request, response, intLen);

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

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}

				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"RATE");                      //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", resultList.size());            //자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} finally {
			if(in != null) {
				try {
					in.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}

			if(out != null) {
				try {
					out.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			file.delete();
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 서비스관계관리 화면
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/sale/rateMgmt/rateRelMgmt.do")
	public ModelAndView rateRelMgmt(@RequestParam Map<String, Object> commandMap, ModelMap model,
								HttpServletRequest paramReq, HttpServletResponse paramRes) throws EgovBizException {
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToParameterMap(commandMap);
			ModelAndView modelAndView = new ModelAndView();

			if(!"10".equals(paramReq.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}

			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(paramReq, paramRes));
			model.addAllAttributes(commandMap);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(paramReq, paramRes));
			modelAndView.setViewName("/sale/rateMgmt/rateRelMgmt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}


	@RequestMapping(value="/sale/rateMgmt/getRateMgmtRelList.json")
	public String getRateMgmtRelList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateMgmtRelList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	/**
	 *20190102 서비스관계 관리 등록 팝업 부가서비스조회 추가
	 *	@param RateMgmtVO
	 * @return
	 * @throws EgovBizException
	 * */
	@RequestMapping(value="/sale/rateMgmt/getRateMgmtRelListPop.json")
	public String getRateMgmtRelListPop(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateMgmtRelListPop(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping(value="/sale/rateMgmt/getRateRelListAll.json")
	public String getRateRelListAll(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateRelListAll(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping("/sale/rateMgmt/insertRateRel.json")
	public String insertRateRel(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			rateMgmtService.insertRateRel(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/rateMgmt/deleteRateRel.json")
	public String deleteRateRel(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			rateMgmtService.deleteRateRel(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 요금그룹관계관리 화면
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/sale/rateMgmt/rateGroupRelMgmt.do")
	public ModelAndView rateGroupRelMgmt(@RequestParam Map<String, Object> commandMap, ModelMap model,
								HttpServletRequest paramReq, HttpServletResponse paramRes) throws EgovBizException {
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToParameterMap(commandMap);
			ModelAndView modelAndView = new ModelAndView();

			if(!"10".equals(paramReq.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}

			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(paramReq, paramRes));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(paramReq, paramRes));

			model.addAllAttributes(commandMap);
			modelAndView.setViewName("/sale/rateMgmt/rateGroupRelMgmt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	@RequestMapping(value="/sale/rateMgmt/getRateGroupList.json")
	public String getRateGroupList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateGroupList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping(value="/sale/rateMgmt/getGroupMappingRateList.json")
	public String getGroupMappingRateList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getGroupMappingRateList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping(value="/sale/rateMgmt/getGroupRelRateList.json")
	public String getGroupRelRateList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getGroupRelRateList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping("/sale/rateMgmt/setRateGroupInfo.json")
	public String setRateGroupInfo(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			if (searchVO.getMstRateGrpCd().isEmpty()) {
				rateMgmtService.insertRateGroupInfo(searchVO);
			} else {
				rateMgmtService.updateRateGroupInfo(searchVO);
			}

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/rateMgmt/deleteRateGroupInfo.json")
	public String deleteRateGroupInfo(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			rateMgmtService.deleteRateGroupInfo(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/rateMgmt/insertGroupRelRateInfo.json")
	public String insertGroupRelRateInfo(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			rateMgmtService.insertGroupRelRateInfo(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/rateMgmt/deleteGroupRelRateInfo.json")
	public String deleteGroupRelRateInfo(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			rateMgmtService.deleteGroupRelRateInfo(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/rateMgmt/insertGroupMappingRateInfo.json")
	public String insertGroupMappingRateInfo(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			rateMgmtService.insertGroupMappingRateInfo(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/rateMgmt/deleteGroupMappingRateInfo.json")
	public String deleteGroupMappingRateInfo(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			rateMgmtService.deleteGroupMappingRateInfo(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping(value="/sale/rateMgmt/getAllRateList.json")
	public String getAllRateList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = rateMgmtService.getAllRateList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	/**
	 * 요금그룹관계관리 화면
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/sale/rateMgmt/groupByRateReList.do")
	public String groupByRateReList(@RequestParam Map<String, Object> commandMap, ModelMap model,
								HttpServletRequest paramReq, HttpServletResponse paramRes) throws EgovBizException {
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToParameterMap(commandMap);

			if(!"10".equals(paramReq.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}

			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(paramReq, paramRes));
			model.addAllAttributes(commandMap);

			return "/sale/rateMgmt/rateGroupRelMgmtPopup";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	@RequestMapping(value="/sale/rateMgmt/getGroupByRateReList.json")
	public String getGroupByRateReList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = rateMgmtService.getGroupByRateReList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping(value="/sale/rateMgmt/getGroupByRateReListEx.json")
	public String getGroupByRateReListEx(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = rateMgmtService.getGroupByRateReList(searchVO);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "요금변경관계리스트_";	//파일명
			String strSheetname = "요금변경관계";	//시트명

			String [] strHead = {"변경전요금_그룹", "변경전요금_그룹유형", "변경전요금_상품", "변경전요금_제휴여부", "변경전요금_선후불", "변경전요금_데이터유형", "변경후요금_그룹", "변경후요금_그룹유형", "변경후요금_상품", "변경후요금_제휴여부", "변경후요금_선후불", "변경후요금_데이터유형"};

			String [] strValue = {"prvRateGrpNm", "prvRateGrpTypeNm", "prvRateNm", "prvPtrnRateYn", "prvPayClNm", "prvDataType", "nxtRateGrpNm", "nxtRateGrpTypeNm", "nxtRateNm", "nxtPtrnRateYn", "nxtPayClNm", "nxtDataType"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {10000, 6000, 10000, 6000, 6000, 6500, 10000, 6000, 10000, 6000, 6000, 6500};


			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);

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
			    paramMap.put("DUTY_NM"   ,"ORG");                       //업무명
			    paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID

			    fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
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
	 * 결합요금제관리 화면
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/sale/rateMgmt/rateCombMgmt.do")
	public ModelAndView combRateMappMgmt(@RequestParam Map<String, Object> commandMap, ModelMap model,
								HttpServletRequest paramReq, HttpServletResponse paramRes) throws EgovBizException {
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToParameterMap(commandMap);
			ModelAndView modelAndView = new ModelAndView();

			if(!"10".equals(paramReq.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}

			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(paramReq, paramRes));
			model.addAllAttributes(commandMap);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(paramReq, paramRes));
			modelAndView.setViewName("/sale/rateMgmt/rateCombMgmt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	/**
	 * 결합요금제 매핑 정보 조회
	 */
	@RequestMapping(value="/sale/rateMgmt/getRateCombMappList.json")
	public String getRateCombMappList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateCombMappList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP2001011", "요금제관리"))
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
	 * 결합요금제 부가서비스 매핑 정보 엑셀다운로드
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sale/rateMgmt/getRateCombMappListExcel.json")
	public String getRateCombMappListExcel(@ModelAttribute("searchVO")  RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀다운로드 조회 START"));
		logger.info(generateLogMsg("Return Vo [RateMgmtVO] = " + searchVO.toString()));
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

			List<RateMgmtVO> list = rateMgmtService.getRateCombMappListExcel(searchVO);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "결합요금제관리_";//파일명
			String strSheetname = "결합요금제관리";//시트명

			String [] strHead = {"요금제코드", "요금제명", "부가서비스코드", "부가서비스명","적용시작", "적용종료","등록자", "등록일자","수정자", "수정일자"};
			String [] strValue = {"pRateCd","pRateNm","rRateCd","rRateNm","appStrDt","appEndDt","regNm","regDt","rvisnNm","rvisnDt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 7000, 5000, 7000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

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
	 * 결합할 요금제 조회
	 * **/
	@RequestMapping(value="/sale/rateMgmt/getRatePListAll.json")
	public String getRatePListAll(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRatePListAll(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	/**
	 * 부가서비스 전체 조회
	 * **/
	@RequestMapping(value="/sale/rateMgmt/getRateRListAll.json")
	public String getRateRListAll(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateRListAll(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	/**
	 * 결합요금제 와 부가서비스 매핑
	 * @param vo
	 * @param model
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/sale/rateMgmt/insertRateCombMapp.json")
	public String insertRateCombMapp(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			searchVO.setUserId(loginInfo.getUserId());

			rateMgmtService.insertRateCombMapp(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	/**
	 * 결합요금제 와 부가서비스 적용일자 수정
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/sale/rateMgmt/updateRateCombMapp.json")
	public String updateRateCombMapp(@ModelAttribute("searchVO") RateMgmtVO searchVO,
				   ModelMap model,
				   HttpServletRequest request,
				   HttpServletResponse response,
				   @RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			searchVO.setUserId(loginInfo.getUserId());

			rateMgmtService.updateRateCombMapp(searchVO);

	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	/**
	 * @@Description : A'Cen 요금제 관리 화면
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/sale/rateMgmt/rateAcenMgmt.do")
	public ModelAndView rateAcenMgmt(@ModelAttribute("searchVO") RateMgmtVO searchVO,
										HttpServletRequest request,
										HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			modelAndView.setViewName("sale/rateMgmt/rateAcenMgmt");

			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	
	/**
	 * A'cen 요금제 리스트 조회
	 */

	@RequestMapping(value="/sale/rateMgmt/getRateAcenMgmtList.json")
	public String getRateAcenMgmtList(@ModelAttribute("searchVO") RateMgmtVO searchVO,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  @RequestParam Map<String, Object> paramMap,
								  ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateAcenMgmtList(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP2001011", "요금제관리"))
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
	 * A'cen 요금제 단건 등록 / 수정
	 */
	@RequestMapping("/sale/rateMgmt/mergeRateAcenCd.json")
	public String mergeRateAcenCd(@ModelAttribute("searchVO") RateMgmtVO searchVO,
							   ModelMap model,
							   HttpServletRequest request,
							   HttpServletResponse response,
							   @RequestParam Map<String, Object> paramMap) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			// 본사 화면인 경우
			if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			searchVO.setUserId(loginInfo.getUserId());

			rateMgmtService.mergeRateAcenCd(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap))
				throw new MvnoErrorException(e);
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : A'Cen 요금제 엑셀양식
	 */
	@RequestMapping(value="/sale/rateMgmt/getRateAcenExcelTemp.json")
	public String getRateAcenExcelTemp(@ModelAttribute("searchVO") RateMgmtVO searchVO,
									  HttpServletRequest request,
									  HttpServletResponse response,
									  @RequestParam Map<String, Object> paramMap,
									  ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = new ArrayList<RateMgmtVO>();

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "A'Cen요금제등록엑셀양식_";//파일명
			String strSheetname = "A'Cen요금제등록엑셀양식";//시트명

			String [] strHead = {"요금제코드", "요금제명",
								"월정액(VAT포함)", "할인후월정액(VAT포함)", "음성(기본/분)", "음성(추가/분)", "기타통화(분)",
								"문자(기본/건)", "문자(추가/건)", "데이터(기본/MB)", "데이터(추가/MB)", "데이터(일/MB)",
								"QOS", "QOS(단위)", "적용시작일", "적용시작시간", "적용종료일", "적용종료시간"};
			String [] strValue = {"rateCd", "rateNm",
								"baseVatAmt", "prmtVatAmt", "callCnt", "prmtCallCnt", "etcCallCnt",
								"smsCnt", "prmtSmsCnt", "dataCnt", "prmtDataCnt", "dayDataCnt",
								"qosDataCntDesc", "qosDataUnit", "pstngStartDate", "startTm", "pstngEndDate", "endTm"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {4000, 7000,
					6000, 8000, 4000, 4000, 4000,
					5000, 5000, 5000, 5000, 5000,
					3000, 4000, 5000, 5000, 5000, 5000};
			int[] intLen = {};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);

			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}

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
	 * @Description : A'cen 엑셀 업로드 파일 읽기
	 */
	@RequestMapping(value="/sale/rateMgmt/readRateAcenExcelUpList.json")
	public String readRateAcenExcelUpList(HttpServletRequest request,
									 HttpServletResponse response,
									 @ModelAttribute("searchVO") RateMgmtVO searchVO,
									 ModelMap model,
									 @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String baseDir = propertiesService.getString("fileUploadBaseDirectory");

			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();

			String[] arrCell = {"rateCd", "rateNm",
					"baseVatAmt", "prmtVatAmt", "callCnt", "prmtCallCnt", "etcCallCnt",
					"smsCnt", "prmtSmsCnt", "dataCnt", "prmtDataCnt", "dayDataCnt",
					"qosDataCntDesc", "qosDataUnit", "pstngStartDate", "startTm", "pstngEndDate", "endTm"};

			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO", sOpenFileName, arrCell);
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, uploadList, uploadList.size());

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	/**
	 * @Description : A'cen 요금제 엑셀등록
	 */
	@RequestMapping(value="/sale/rateMgmt/regRateAcenExcel.json")
	public String regRateAcenExcel(HttpServletRequest request,
									  HttpServletResponse response,
									  @ModelAttribute("searchVO") RateMgmtVO searchVO,
									  @RequestBody String data,
									  ModelMap model,
									  @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			RateMgmtVO vo = new ObjectMapper().readValue(data, RateMgmtVO.class);
			vo.setUserId(loginInfo.getUserId());
			vo.setFlag("I");

			int resultCnt = rateMgmtService.regRateAcenExcel(vo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", resultCnt+"건 등록되었습니다");

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : A'cen 요금제 리스트 엑셀다운로드
	 */

	@RequestMapping(value = "/sale/rateMgmt/getRateAcenMgmtListExcel.json")
	@ResponseBody
	public String getRateAcenMgmtListExcel(@ModelAttribute RateMgmtVO vo,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   @RequestParam Map<String, Object> pReqParamMap,
									   ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			loginInfo.putSessionToParameterMap(pReqParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "A'cen요금제목록_";	/** 파일명 */
			String strSheetname = "A'cen요금제목록";				/** 시트명 */

			List<RateMgmtVO> resultList = rateMgmtService.getRateAcenMgmtListExcel(vo);


			String [] strHead = { "요금제코드", "요금제유형", "요금제명",
					"월정액(VAT포함)", "할인후월정액(VAT포함)", "음성(기본/분)", "음성(추가/분)", "기타통화(분)",
					"문자(기본/건)", "문자(추가/건)", "데이터(기본/MB)", "데이터(추가/MB)", "데이터(일/MB)",
					"QOS", "QOS(단위)", "적용시작일시","적용종료일시",
					"등록자","등록일시","수정자","수정일시"};

			String [] strValue = {"rateCd", "rateTypeNm","rateNm",
					"baseVatAmt", "prmtVatAmt", "callCnt", "prmtCallCnt", "etcCallCnt",
					"smsCnt", "prmtSmsCnt", "dataCnt", "prmtDataCnt", "dayDataCnt",
					"qosDataCntDesc", "qosDataUnit", "pstngStartDate","pstngEndDate",
					"cretNm","cretDt","amdNm","amdDt"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {4000, 4000, 7000,
					6000, 8000, 4000, 4000, 4000,
					5000, 5000, 5000, 5000, 5000,
					3000, 4000, 5000, 5000,
					3000, 5000, 3000, 5000};
			int[] intLen = {0, 0, 0
					, 0, 0, 0, 0, 0
					, 0, 0, 0, 0, 0
					, 0, 0, 0, 0
					, 0, 0, 0, 0};

			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);

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

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}

				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"RATE");                      //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", resultList.size());            //자료건수
				pReqParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID

				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
		} finally {
			if(in != null) {
				try {
					in.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}

			if(out != null) {
				try {
					out.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			file.delete();
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : A'cen 요금제 이력 조회
	 */
	@RequestMapping(value="/sale/rateMgmt/getRateAcenHist.json")
	public String getRateAcenHist(@ModelAttribute("searchVO") RateMgmtVO searchVO,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  @RequestParam Map<String, Object> paramMap,
								  ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = rateMgmtService.getRateAcenHist(searchVO);

			resultMap = makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap))
				throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	/**
	 * @Description : A'cen 요금제 이력 엑셀다운로드
	 */

	@RequestMapping(value = "/sale/rateMgmt/getRateAcenHistExcel.json")
	@ResponseBody
	public String getRateAcenHistExcel(@ModelAttribute RateMgmtVO vo,
										   HttpServletRequest request,
										   HttpServletResponse response,
										   @RequestParam Map<String, Object> pReqParamMap,
										   ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			loginInfo.putSessionToParameterMap(pReqParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "_"+ vo.getRateCd() + "_A'cen요금제이력_";	/** 파일명 */
			String strSheetname = "A'cen요금제이력";				/** 시트명 */


			List<RateMgmtVO> resultList = rateMgmtService.getRateAcenHistExcel(vo);


			String [] strHead = { "요금제코드", "요금제유형", "요금제명",
					"월정액(VAT포함)", "할인후월정액(VAT포함)", "음성(기본/분)", "음성(추가/분)", "기타통화(분)",
					"문자(기본/건)", "문자(추가/건)", "데이터(기본/MB)", "데이터(추가/MB)", "데이터(일/MB)",
					"QOS", "QOS(단위)", "적용시작일시","적용종료일시",
					"등록자","등록일시","수정자","수정일시"};

			String [] strValue = {"rateCd", "rateTypeNm","rateNm",
					"baseVatAmt", "prmtVatAmt", "callCnt", "prmtCallCnt", "etcCallCnt",
					"smsCnt", "prmtSmsCnt", "dataCnt", "prmtDataCnt", "dayDataCnt",
					"qosDataCntDesc", "qosDataUnit", "pstngStartDate","pstngEndDate",
					"cretNm","cretDt","amdNm","amdDt"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {4000, 4000, 7000,
					6000, 8000, 4000, 4000, 4000,
					5000, 5000, 5000, 5000, 5000,
					3000, 4000, 5000, 5000,
					3000, 5000, 3000, 5000};
			int[] intLen = {0, 0, 0
					, 0, 0, 0, 0, 0
					, 0, 0, 0, 0, 0
					, 0, 0, 0, 0
					, 0, 0, 0, 0};

			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);

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

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}

				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"RATE");                      //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", resultList.size());            //자료건수
				pReqParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID

				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
		} finally {
			if(in != null) {
				try {
					in.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}

			if(out != null) {
				try {
					out.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			file.delete();
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	/**
	 * @Description : A'cen 요금제 List 종료일 변경
	 */
	@RequestMapping("/sale/rateMgmt/updRateAcenListEndDateMod.json")
	public String updRateAcenListEndDateMod(@ModelAttribute("searchVO") RateMgmtVO searchVO,
									@RequestBody String data,
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model,
									@RequestParam Map<String, Object> paramMap) {

		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			RateMgmtVO vo = new ObjectMapper().readValue(data, RateMgmtVO.class);
			vo.setUserId(loginInfo.getUserId());

			rateMgmtService.updRateAcenListEndDateMod(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage()) ;
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}



}
