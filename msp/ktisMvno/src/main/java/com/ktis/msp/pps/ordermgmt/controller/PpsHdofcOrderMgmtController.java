package com.ktis.msp.pps.ordermgmt.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ktis.msp.pps.ordermgmt.service.PpsHdofcOrderMgmtService;

import CNPECMJava.CNPEncryptModule.crypto.FileHandle;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @Class Name : PpsHdofcOrderMgmtController.java
 * @Description : PpsHdofcOrderMgmtController class
 * @Modification Information
 * @
 * @  수정일			수정자			수정내용
 * @ ---------		---------	-------------------------------
 * @ 2017.05.01		김웅			최초생성
 *
 * @author 김웅
 * @since 2017. 05.01
 * @version 1.0
 */

@Controller
public class PpsHdofcOrderMgmtController  extends BaseController {

	@Autowired
	private PpsHdofcOrderMgmtService orderService;
	@Autowired
	private MenuAuthService  menuAuthService;

//	@Autowired
//	private MaskingService  maskingService;

	@Autowired
	protected EgovPropertyService propertyService;

//	@Autowired
//	private PpsFileService ppsFileService;

//	@Autowired
//	private FileReadService fileReadService;

	/**
	 * 본사 재고관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/OrderGoodsForm.do", method={POST, GET} )
	public ModelAndView selectOrderGoodsMgmtListForm( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{

		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);


			logger.info(" PpsHdofcOrderMgmtController.OrderGoodsForm:  본사 재고관리 페이지 호출");

			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);


			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);

			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/ordermgmt/hdofc_ordermgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 본사 재고관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderGoodsList.json" )
	public String selectOrderGoodsListJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{



		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

		logger.info("PpsHdofcOrderMgmtController.selectOrderGoodsListJson : 본사 재고관리 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = orderService.getOrderGoodsList(pRequestParamMap);

			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		//maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름

	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/

	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");

    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}

	/**
	 * 본사 재고관리 조회 엑셀저장
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 *
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderGoodsListExcel.json" )
	public String selectOrderGoodsExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {


		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);



		logger.info("PpsHdofcOrderMgmtController.selectOrderGoodsExcelJson :선불 본사 재고관리 엑셀출력");

		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			orderService.getOrderGoodsListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}

    	return "jsonView";
	}

	/**
	 * 본사 재고관리 타입 Select용
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderGoodsType.json" )
	public String selectOrderGoodsTypeJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{



		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

		logger.info("PpsHdofcOrderMgmtController.selectOrderGoodsListJson : 본사 재고관리 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = orderService.getOrderGoodsType(pRequestParamMap);

			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		//maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름

	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/

	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");

    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}

	/**
	 * 본사 재고관리 코드  Select용
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderGoodsCd.json" )
	public String selectOrderGoodsCdJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{



		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

		logger.info("PpsHdofcOrderMgmtController.selectOrderGoodsListJson : 본사 재고관리 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = orderService.getOrderGoodsCd(pRequestParamMap);

			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		//maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름

	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/

	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");

    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}

	/**
	 * 선불 상품코드 내역 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 *
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/PpsOrderCodeList.json" )
	public String selectPpsOrderCodeListJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{

		logger.info("PpsHdofcOrderMgmtController.selectPpsOrderCodeListJson: 선불 상품코드 내역 리스트 ");

		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);

		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();


		try{
			List<?> resultList = orderService.getPpsOrderCodeList(pRequestParamMap);
	      	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

	      	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	      	 resultMap.put("msg", "");


		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		 model.addAttribute("result", resultMap);
	  	 //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	  	 return "jsonView";
	}

	/**
	 * 재고관리, 입출고관리 등록/수정/삭제
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/ordermgmt/ppsOrderGoodsProc.json")
	public String ppsOrderGoodsProc (
										ModelMap model,
										HttpServletRequest pRequest,
										HttpServletResponse pResponse,
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{

		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try
		{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			resultMap = orderService.ppsOrderGoodsProc(pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }

	/**
	 * 본사입출고관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/OrderInoutForm.do", method={POST, GET} )
	public ModelAndView selectOrderInoutMgmtListForm( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{

		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);


			logger.info(" PpsHdofcOrderMgmtController.OrderInoutForm:  본사 입출고관리 페이지 호출");

			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);


			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);

			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/ordermgmt/hdofc_ordermgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 본사 입출고관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderInoutList.json" )
	public String selectOrderInoutListJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{



		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

		logger.info("PpsHdofcOrderMgmtController.selectOrderInoutListJson : 본사 입출고관리 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = orderService.getOrderInoutList(pRequestParamMap);

			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		//maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름

	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/

	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");

    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}

	/**
	 * 본사 입출고관리 조회 엑셀저장
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 *
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderInoutListExcel.json" )
	public String selectOrderInoutExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {


		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);



		logger.info("PpsHdofcOrderMgmtController.selectOrderInoutExcelJson :선불 본사 입출고관리 엑셀출력");

		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			orderService.getOrderInoutListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}

    	return "jsonView";
	}

	/**
	 * 본사 주문관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/OrderInfoForm.do", method={POST, GET} )
	public ModelAndView selectOrderInfoMgmtListForm( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{

		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);


			logger.info(" PpsHdofcOrderMgmtController.OrderInfoForm:  본사 주문관리 페이지 호출");

			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);


			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);

			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/ordermgmt/hdofc_ordermgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 본사 주문관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderInfoList.json" )
	public String selectOrderInfoListJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{



		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

		logger.info("PpsHdofcOrderMgmtController.selectOrderInfoListJson : 본사 주문관리 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = orderService.getOrderInfoList(pRequestParamMap);

			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		//maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름

	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/

	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");

    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}

	/**
	 * 본사주문관리 조회 엑셀저장
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 *
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderInfoListExcel.json" )
	public String selectOrderInfoExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {


		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);



		logger.info("PpsHdofcOrderMgmtController.selectOrderInfoExcelJson :선불 본사 주문관리 엑셀출력");

		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			orderService.getOrderInfoListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}

    	return "jsonView";
	}

	/**
	 * 주문관리, 출고관리 등록/수정/삭제
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/ordermgmt/ppsOrderInfoProc.json")
	public String ppsOrderInfoProc (
										ModelMap model,
										HttpServletRequest pRequest,
										HttpServletResponse pResponse,
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{

		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try
		{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			resultMap = orderService.ppsOrderInfoProc(pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }

	/**
	 * 본사 대리점 정보
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/orderAgentInfo.json" )
	public String selectOrderAgentInfoJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{



		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

		logger.info("PpsHdofcOrderMgmtController.selectOrderAgentInfoJson : 대리점정보 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = orderService.getOrderAgentInfo(pRequestParamMap);

			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		//maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름

	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/

	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");

    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}

	@RequestMapping("/pps/ordermgmt/ppsFileEncUpLoad.do")
    public String fileUpEncService (ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

    	//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());

		logger.info("===========================================");
		logger.info("======  PpsOrderMgmtController.orderService-- 이미지 업로드 ======");
		logger.info("===========================================");
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	    logger.info("===========================================");

    	 Map<String, Object> resultMap = new HashMap<String, Object>();

    	try{

    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}

    		resultMap = orderService.fileUpload(pRequest, "file_upload1", "PPS/ORDER",pRequestParamMap);


    		logger.info("resultMap==111==="+resultMap.toString());

    		//resultMap====={ imgFile=681079800_201603301547581.jpg, imgPath=D:/data//PPS/2016/03/30/681079800_201603301547581.jpg, name=D:/data//PPS/2016/03/30/681079800_201603301547581.jpg, state=true, size=111, code=OK, msg=}



			 // 암호화
		    try {

		    	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();

		    	logger.info("(String) resultMap.get(imgFile)====="+(String) resultMap.get("imgFile"));

			    if(!"".equals((String) resultMap.get("imgFile")) || (String) resultMap.get("imgFile") != null){

				    String fileNameSub[] = 	((String) resultMap.get("imgFile")).split("\\.");

				    if(fileNameSub[0] != null){

					    String fileName = fileNameSub[0] + "_e." + fileNameSub[1] ;
					    String retUrl = (String) resultMap.get("baseDir") +"/" +  (String) resultMap.get("imgFile");
					    String encUrl = (String) resultMap.get("baseDir") +"/" +  fileName;

					    //암호화된 파일로 변경
					    fileHandle.Encrypt(retUrl, encUrl);

					    // 복호화
/*					    try {
					      fileHandle.Decrypt("D:/temp/1000_1.jpg", "D:/temp/1000_2.jpg");
					    } catch (Exception e) {
					      // TODO Auto-generated catch block
					      //e.printStackTrace();
					    }*/


					    //2초 delay
/*					       long saveTime = System.currentTimeMillis();
					       long currTime = 0;
					       int delayTime = 100;

					       while( currTime - saveTime < delayTime){
					            currTime = System.currentTimeMillis();
					        }*/

					       //암호화되기전 기존 파일삭제
					        try {
								File file = null;
								file = new File(retUrl);
								file.delete();

							} catch (Exception e1) {
								// TODO Auto-generated catch block
//								20200512 소스코드점검 수정
//						    	e1.printStackTrace();
//								20210706 소스코드점검 수정
//								System.out.println("Connection Exception occurred");
								logger.error("Connection Exception occurred");
								resultMap = new HashMap<String, Object>();
								resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
							}

					        //암호화된 정보 SET
						    resultMap.put("imgFile", fileName);
						    resultMap.put("imgPath", encUrl);
						    resultMap.put("name", encUrl);


						    logger.info("resultMap==222==="+resultMap.toString());

				    }

			    }

		    } catch (Exception e) {
		      // TODO Auto-generated catch block
		      //e.printStackTrace();
		    	throw new MvnoErrorException(e);
		    }

    	}catch ( Exception e)
		{
			resultMap.clear();
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		} catch (Throwable e) {
			throw new MvnoErrorException(e);
		}finally{
//			sqlService.Close();
			dummyFinally();
		}


    	model.addAttribute("result", resultMap);
 		return "jsonViewArray";

    }

	 /**
     * @Description : 파일 다운로드 기능
     * @Return : String
     * @Author : 홍은표
     * @Create Date : 2017. 8. 17.
     */
    @RequestMapping("/pps/hdofc/ordermgmt/orderFileDown.json")
    public String downFile( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;

        try {
        	// 본사 권한
	    	if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}

	    	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();

	    	List<EgovMap> resultList = (List<EgovMap>)orderService.getOrderSendFile(pReqParamMap);

	    	String sendFileOld = "";
	    	String sendOrgFile = "";
	    	for( EgovMap map : resultList  ){
				if(map.get("sendFile")!=null && !map.get("sendFile").equals("") ){
					sendFileOld =  map.get("sendFile").toString().trim();
				}

				if(map.get("sendOrgFile")!=null && !map.get("sendOrgFile").equals("") ){
					sendOrgFile =  map.get("sendOrgFile").toString().trim();
				}
			}

	    	if(sendFileOld == null || "".equals(sendFileOld)){
	    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	 			resultMap.put("msg", "등록된 파일이 없습니다.");
	    	}

	    	sendOrgFile = URLEncoder.encode(sendOrgFile, "UTF-8");

	    	String sendFileOld_arr[] = sendFileOld.split("\\.");
	    	StringBuffer sendFileSb = new StringBuffer();

	    	sendFileSb.append(sendFileOld_arr[0]);
	    	sendFileSb.append("c.");
	    	sendFileSb.append(sendFileOld_arr[1]);

	    	String sendFile = sendFileSb.toString();

	    	try {
				//1.TO-BE 암호화 여부 확인
				if (rosis.crypt.module.eSonicCrypt.isEncryptLea(sendFileOld)) {
					//1.1 TO-BE 암호화 모듈로 복호화
					int decInt = rosis.crypt.module.eSonicCrypt.RS_DecryptLea(sendFileOld, sendFile, 24, 0);
				} else {
					//2. AS-IS 암호화 모듈로 복호화
					fileHandle.Decrypt(sendFileOld, sendFile);
				}
		    } catch (Exception e) {
		      // TODO Auto-generated catch block
		      //.printStackTrace();
		    	throw new MvnoErrorException(e);
		    }

            file = new File(sendFile);

            in = new FileInputStream(file);

            long fileLen = file.length();

            response.setContentType("applicaton/download");
            response.setContentLength((int) fileLen);

            response.setHeader("Cache-Control", "");
            response.setHeader("Pragma", "");

            response.setContentType("Content-type:application/octet-stream;");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + sendOrgFile + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            out = response.getOutputStream();

            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");

 		} catch (Exception e) {
 			//logger.error(e.getMessage());


 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", returnMsg);

 			throw new MvnoErrorException(e);
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	           // logger.error(e);
	        	  throw new MvnoErrorException(e);
	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();

	        	  //다운로드 완료 후 암호해제파일 삭제처리
	        	  file.delete();

	          } catch (Exception e) {
	        	 // logger.error(e);
	        	  throw new MvnoErrorException(e);
	          }
	        }
	    }
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }

    /**
	 * 본사 출고관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/OrderInfoOutForm.do", method={POST, GET} )
	public ModelAndView selectOrderInfoOutMgmtListForm( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{

		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);


			logger.info(" PpsHdofcOrderMgmtController.OrderInfoOutForm:  본사 출고관리 페이지 호출");

			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);


			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);

			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/ordermgmt/hdofc_ordermgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 본사 인수관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/OrderInfoEndForm.do", method={POST, GET} )
	public ModelAndView selectOrderInfoEndMgmtListForm( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{

		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);


			logger.info(" PpsHdofcOrderMgmtController.OrderInfoEndForm:  본사 인수관리 페이지 호출");

			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);


			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);

			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/ordermgmt/hdofc_ordermgmt_0050");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 선불 팝업에서 저장시 저장목록을 불러오기 위해
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 *
	 */
	@RequestMapping(value = "/pps/hdofc/ordermgmt/PpsOrderRefreshList.json" )
	public String selectPpsOrderRefreshListJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{

		logger.info("PpsHdofcOrderMgmtController.selectPpsOrderCodeListJson: 선불 리플레쉬 내역 ");

		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);

		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();


		try{
			List<?> resultList = orderService.getPpsOrderRefreshList(pRequestParamMap);
	      	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

	      	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	      	 resultMap.put("msg", "");


		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		 model.addAttribute("result", resultMap);
	  	 //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	  	 return "jsonView";
	}


}