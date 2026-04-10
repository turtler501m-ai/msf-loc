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
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCustMgmtService;
import com.ktis.msp.pps.ordermgmt.service.PpsAgencyOrderMgmtService;

import CNPECMJava.CNPEncryptModule.crypto.FileHandle;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @Class Name : PpsAgencyOrderMgmtController.java
 * @Description : PpsAgencyOrderMgmt Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.08           최초생성
 *
 * @author 장익준
 * @since 2014. 08.08
 * @version 1.0
 */

@Controller
public class PpsAgencyOrderMgmtController extends BaseController {


	@Autowired
	private PpsAgencyOrderMgmtService  ppsAgencyOrderMgmtService;

	@Autowired
	PpsHdofcCustMgmtService ppsHdofcCustMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;


	@Autowired
	protected EgovPropertyService propertyService;

//	@Autowired
//	private PpsHdofcCommonService  hdofcCommonService;


	/**
	 * 대리점 주문관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/ordermgmt/OrderGoodsForm.do", method={POST, GET} )
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
		    pRequestParamMap.put("adminId", loginInfo.getUserId());

		    String lvlCd   = loginInfo.getUserOrgnLvlCd();
		    String typeCd      = loginInfo.getUserOrgnTypeCd();
		    if(typeCd.equals("20") && lvlCd.equals("10"))
		    {
		    	pRequestParamMap.put("agentId", loginInfo.getUserOrgnId());
		    }


			logger.info(" PpsHdofcOrderMgmtController.OrderGoodsForm:  대리점 주문관리 페이지 호출");

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
			modelAndView.setViewName("pps/agency/ordermgmt/agency_ordermgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 대리점 주문관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/ordermgmt/orderInfoList.json" )
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

	    pRequestParamMap.put("adminId", loginInfo.getUserId());
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	//pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}

		logger.info("PpsHdofcOrderMgmtController.selectOrderInfoListJson : 대리점 주문관리 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}

	    	 List<?> resultList = ppsAgencyOrderMgmtService.getOrderInfoList(pRequestParamMap);

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
	 * 대리점 주문관리 조회 엑셀저장
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 *
	 */
	@RequestMapping(value = "/pps/agency/ordermgmt/orderInfoListExcel.json" )
	public String selectOrderInfoExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {


		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

	    pRequestParamMap.put("adminId", loginInfo.getUserId());
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	//pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}



		logger.info("PpsHdofcOrderMgmtController.selectOrderInfoExcelJson :선불 대리점 주문관리 엑셀출력");

		try{
			// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		ppsAgencyOrderMgmtService.getOrderInfoListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}

    	return "jsonView";
	}

	/**
	 * 대리점 재고관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/ordermgmt/orderGoodsList.json" )
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
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
	    	 List<?> resultList = ppsAgencyOrderMgmtService.getOrderGoodsList(pRequestParamMap);

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
	 * 대리점 정보
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/ordermgmt/orderAgentInfo.json" )
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

	    pRequestParamMap.put("adminId", loginInfo.getUserId());
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	//pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}

		logger.info("PpsHdofcOrderMgmtController.selectOrderAgentInfoJson : 대리점정보 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
	    	 List<?> resultList = ppsAgencyOrderMgmtService.getOrderAgentInfo(pRequestParamMap);

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
	 * 주문관리, 출고관리 등록/수정/삭제
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/agency/ordermgmt/ppsOrderInfoProc.json")
	public String ppsOrderInfoProc (
										ModelMap model,
										HttpServletRequest pRequest,
										HttpServletResponse pResponse,
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{

		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);

	    pRequestParamMap.put("adminId", loginInfo.getUserId());
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	//pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try
		{
			// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}

			resultMap = ppsAgencyOrderMgmtService.ppsOrderInfoProc(pRequestParamMap);

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
	 * 대리점 인수관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/ordermgmt/OrderInfoEndForm.do", method={POST, GET} )
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
		    pRequestParamMap.put("adminId", loginInfo.getUserId());

		    String lvlCd   = loginInfo.getUserOrgnLvlCd();
		    String typeCd      = loginInfo.getUserOrgnTypeCd();
		    if(typeCd.equals("20") && lvlCd.equals("10"))
		    {
		    	pRequestParamMap.put("agentId", loginInfo.getUserOrgnId());
		    }


			logger.info(" PpsHdofcOrderMgmtController.OrderInfoEndForm:  대리점 인수관리 페이지 호출");

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
			modelAndView.setViewName("pps/agency/ordermgmt/agency_ordermgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
     * @Description : 파일 다운로드 기능
     * @Return : String
     * @Author : 홍은표
     * @Create Date : 2017. 8. 17.
     */
    @RequestMapping("/pps/agency/ordermgmt/orderFileDown.json")
    public String downFile (
			ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
				)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pRequestParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToParameterMap(pRequestParamMap);

        pRequestParamMap.put("adminId", loginInfo.getUserId());

	    String lvlCd   = loginInfo.getUserOrgnLvlCd();
	    String typeCd      = loginInfo.getUserOrgnTypeCd();
	    if(typeCd.equals("20") && lvlCd.equals("10"))
	    {
	    	pRequestParamMap.put("agentId", loginInfo.getUserOrgnId());
	    }

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;

        try {
        	// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
	    	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();

	    	List<EgovMap> resultList = (List<EgovMap>)ppsAgencyOrderMgmtService.getOrderSendFile(pRequestParamMap);

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
		      //e.printStackTrace();
		    	throw new MvnoErrorException(e);
		    }

            file = new File(sendFile);

            in = new FileInputStream(file);

            long fileLen = file.length();

            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) fileLen);

            pResponse.setHeader("Cache-Control", "");
            pResponse.setHeader("Pragma", "");

            pResponse.setContentType("Content-type:application/octet-stream;");
            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + sendOrgFile + "\";");
            pResponse.setHeader("Content-Transfer-Encoding", "binary");

            out = pResponse.getOutputStream();

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
	            //logger.error(e);
	        	  throw new MvnoErrorException(e);
	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();

	        	  //다운로드 완료 후 암호해제파일 삭제처리
	        	  file.delete();

	          } catch (Exception e) {
	        	  //logger.error(e);
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
	 * 대리점 재고관리 코드  Select용
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/ordermgmt/orderGoodsCd.json" )
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

		logger.info("PpsHdofcOrderMgmtController.selectOrderGoodsListJson : 대리점 재고관리 조회");

		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);


        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();


    	try{
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
	    	 List<?> resultList = ppsAgencyOrderMgmtService.getOrderGoodsCd(pRequestParamMap);

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

}
