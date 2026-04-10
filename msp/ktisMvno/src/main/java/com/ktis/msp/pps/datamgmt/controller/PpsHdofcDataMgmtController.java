package com.ktis.msp.pps.datamgmt.controller;


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
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.datamgmt.service.PpsHdofcDataMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : PpsHdofcDataMgmtController.java
 * @Description : PpsHdofcDataMgmtController class
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
public class PpsHdofcDataMgmtController  extends BaseController {
	
	@Autowired
	private PpsHdofcDataMgmtService dataService;
	@Autowired
	private MenuAuthService  menuAuthService;
		
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private MaskingService  maskingService;

/*
	@RequestMapping(value = "/pps/hdofc/datamgmt/{id}.do", method={POST, GET})
	public String ppsHdofcDataMgmt(@PathVariable("id") String id){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("test START."+id));
		logger.info(generateLogMsg("================================================================="));

		
		return "pps/hdofc/datamgmt/hdofc_datamgmt_"+id;
	}
*/	
	
	/**
	 * 선불정산내역 목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectDataInfoMgmtListForm( ModelMap model, 
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

			
			logger.info("선불정산내역 목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/datamgmt/hdofc_datamgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    	
		
	}
	
	
	/**
	 * 유심임대선불관리 목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataUsimInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectDataUsimInfoMgmtListForm( ModelMap model, 
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

			logger.info("PpsHdofcDataMgmtController.DataInfoMgmtListForm :유심임대선불관리 목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/datamgmt/hdofc_datamgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 선불정산내역목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoMgmtList.json", method={POST, GET} )
	public String selectDataInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.DataInfoMgmtListJson:선불정산내역 목록조회");
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
    		
	    	 List<?> resultList = dataService.getDataInfoMgmtList(pRequestParamMap);
	    	 HashMap maskFields = new HashMap();
	 	 	 maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
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
	 * 선불정산대상 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
		
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoMgmtListExcel.json" )
	public String selectDataInfoMgmtListExcelJson( ModelMap model, 
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
    	
    	
    	try{
    		logger.info(" PpsHdofcDataMgmtController.selectDataRealCmsMgmtListExcelJson:선불정산대상엑셀출력 ");
    		
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
			dataService.getDataInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
    	}catch(Exception e){
    		//logger.error(e.getMessage());
    		throw new MvnoErrorException(e);
    	}
	        //----------------------------------------------------------------
	    	// 목록 db select
	    	//----------------------------------------------------------------
	    	
			/**
	    	Map<String, Object> resultMap = new HashMap<String, Object>();
	    	FileInputStream in  = null;
			OutputStream out = null;
			File file = null;
	    	try{
		    	 List<?> resultList = dataService.getDataInfoMgmtListExcel(pRequestParamMap);
		    	
		    	 	//logger.debug(" resultList : \n"+ resultList.toString());
		    	 
		    	 String serverInfo= null;
		    	 	serverInfo = propertyService.getString("excelPath");
		    	 	//String strFilename = "C:\\temp\\선불본사충전내역 _";//로컬테스트용파일명
		    	 	
		    	 	String strFilename = "";
		    	 	String strSheetname = "";
		    	 	if("basicPage".equals(pRequestParamMap.get("dataPageType").toString())){
			    	 	strFilename = serverInfo + "선불정산대상_"; //파일명
			    		strSheetname = "선불정산대상";//시트명
		    	 	}else if("usimPage".equals(pRequestParamMap.get("dataPageType").toString())){
		    	 		strFilename = serverInfo + "유심임대선불관리_"; //파일명
			    		strSheetname = "유심임대선불관리";//시트명
		    	 	}
		    		
		    		//엑셀 첫줄
		            String [] strHead = {"계약번호", "요금제", "상태", "개통일", "정지일", "해지일","만료일", "모델명", "사용기간", "총충전액", "총충전수", "최근충전일", "실충전액", "사용횟수", "사용분", "사용액", "데이타량(M)", "데이타사용액","사용액합계","잔액", "개통대리점"};
		            String [] strValue = {"contractNum", "serviceNm", "subStatusNm", "lstComActvDate", "statusStopDt", "statusCancelDt","basicExpire", "modelName", "useTurm", "totRcg", "rcgCnt", "lastBasicChgDt", "realRcg", "callCnt", "callDur", "callCharge", "pktDur", "pktCharge","totalCharge","basicRemains","agentName"};
	
		            //엑셀 컬럼 사이즈
		            int[] intWidth = {3000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,10000};
		            int[] intLen = {0, 0, 0,0, 0, 0, 0, 0, 1, 1, 1, 0,1,1,1,1,1,1,1,1,0};
		            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);
	
		           
		            
		             file = new File(strFileName);
		            
		            pResponse.setContentType("applicaton/download");
		            pResponse.setContentLength((int) file.length());
		            
		            String userAgent = pRequest.getHeader("User-Agent");
		            boolean ie = userAgent.indexOf("Trident") > -1;
	
	//	            logger.debug("strFileName 123= " + strFileName);
		            if(ie){
		            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
		            }
		            else
		            {
		            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
		            }
	//	            logger.debug("strFileName = " + strFileName);
		            strFileName = strFileName.substring(13);
		            
		            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");
	
		             in = new FileInputStream(file);
		            
		             out = pResponse.getOutputStream();
		            
		            int temp = -1;
		            while((temp = in.read()) != -1){
		            	out.write(temp);
		            }
		            
		            
		   			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		   			resultMap.put("msg", "다운로드성공");
		   			
		   		} catch (Exception e) {
		   			resultMap.clear();
					if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
						//logger.error(e);
					}
		   		} finally {
			        if (in != null) {
				          try {
				        	  in.close();
				          } catch (Exception e) {
				           // logger.error(e);
				          }
				        }
				        if (out != null) {
				          try {
				        	  out.close();
				          } catch (Exception e) {
				        	 // logger.error(e);
				          }
				        }
			            file.delete();
				    }
		   		//----------------------------------------------------------------
		   		// return json 
		   		//----------------------------------------------------------------	
		   		model.addAttribute("result", resultMap);
			*/
    	return "jsonView";
	}
	
	/**
	 * 유심선불정산내역목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoUsimMgmtList.json", method={POST, GET} )
	public String selectDataInfoUsimMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectDataInfoUsimMgmtListJson:유심선불정산내역 목록조회");
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
    		
	    	 List<?> resultList = dataService.getDataInfoUsimMgmtList(pRequestParamMap);
	    	 HashMap maskFields = new HashMap();
	 	 	 maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
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
	 * 유심선불정산대상 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
		
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoUsimMgmtListExcel.json" )
	public String selectDataInfoUsimMgmtListExcelJson( ModelMap model, 
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
    	
    	
    	logger.info(" PpsHdofcDataMgmtController.selectDataInfoUsimMgmtListExcelJson:유심선불정산대상엑셀출력 ");
    	
    	try{
    		
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
    		dataService.getDataInfoUsimMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		//logger.error(e.getMessage());
    		throw new MvnoErrorException(e);
    	}
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
		/**
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	 List<?> resultList = dataService.getDataInfoMgmtListExcel(pRequestParamMap);
	    	
	    	 	//logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	 String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 	//String strFilename = "C:\\temp\\선불본사충전내역 _";//로컬테스트용파일명
	    	 	
	    	 	String strFilename = "";
	    	 	String strSheetname = "";
	    	 	if("basicPage".equals(pRequestParamMap.get("dataPageType").toString())){
		    	 	strFilename = serverInfo + "선불정산대상_"; //파일명
		    		strSheetname = "선불정산대상";//시트명
	    	 	}else if("usimPage".equals(pRequestParamMap.get("dataPageType").toString())){
	    	 		strFilename = serverInfo + "유심임대선불관리_"; //파일명
		    		strSheetname = "유심임대선불관리";//시트명
	    	 	}
	    		
	    		//엑셀 첫줄
	            String [] strHead = {"계약번호", "요금제", "상태", "개통일", "정지일", "해지일","만료일", "모델명", "사용기간", "총충전액", "총충전수", "최근충전일", "실충전액", "사용횟수", "사용분", "사용액", "데이타량(M)", "데이타사용액","사용액합계","잔액", "개통대리점"};
	            String [] strValue = {"contractNum", "serviceNm", "subStatusNm", "lstComActvDate", "statusStopDt", "statusCancelDt","basicExpire", "modelName", "useTurm", "totRcg", "rcgCnt", "lastBasicChgDt", "realRcg", "callCnt", "callDur", "callCharge", "pktDur", "pktCharge","totalCharge","basicRemains","agentName"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,10000};
	            int[] intLen = {0, 0, 0,0, 0, 0, 0, 0, 1, 1, 1, 0,1,1,1,1,1,1,1,1,0};
	            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

	           
	            
	             file = new File(strFileName);
	            
	            pResponse.setContentType("applicaton/download");
	            pResponse.setContentLength((int) file.length());
	            
	            String userAgent = pRequest.getHeader("User-Agent");
	            boolean ie = userAgent.indexOf("Trident") > -1;

//	            logger.debug("strFileName 123= " + strFileName);
	            if(ie){
	            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
	            }
	            else
	            {
	            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
	            }
//	            logger.debug("strFileName = " + strFileName);
	            strFileName = strFileName.substring(13);
	            
	            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");

	             in = new FileInputStream(file);
	            
	             out = pResponse.getOutputStream();
	            
	            int temp = -1;
	            while((temp = in.read()) != -1){
	            	out.write(temp);
	            }
	            
	            
	   			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	   			resultMap.put("msg", "다운로드성공");
	   			
	   		} catch (Exception e) {
	   			resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			          //  logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	 // logger.error(e);
			          }
			        }
		            file.delete();
			    }
	   		//----------------------------------------------------------------
	   		// return json 
	   		//----------------------------------------------------------------	
	   		model.addAttribute("result", resultMap);
		*/
    	return "jsonView";
	}
	
	@RequestMapping(value = "/pps/hdofc/datamgmt/CmsDataInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectCmsDataInfoMgmtListForm( ModelMap model, 
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

			
			logger.info("CMS충전대상 목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/datamgmt/hdofc_datamgmt_0030");
			return modelAndView;
			
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    	
		
	}
	
	/**
	 * CMS충전대상 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/CmsDataInfoMgmtList.json", method={POST, GET} )
	public String selectCmsDataInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectCmsDataInfoMgmtListJson:CMS충전대상 목록조회");
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
    		
	    	 List<?> resultList = dataService.getCmsDataInfoMgmtList(pRequestParamMap);
	    	 HashMap maskFields = new HashMap();
	    	 maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
	 	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
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
	 * CMS충전대상 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/CmsDataInfoMgmtListExcel.json" )
	public String selectCmsDataInfoMgmtListExcelJson( ModelMap model, 
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
    	
    	logger.info(" PpsHdofcDataMgmtController.selectCmsDataInfoMgmtListExcelJson:CMS충전대상엑셀출력 ");
    	
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
    		dataService.getCmsDataInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		//logger.error(e.getMessage());
    		throw new MvnoErrorException(e);
    	}
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
		/**
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	 List<?> resultList = dataService.getDataInfoMgmtListExcel(pRequestParamMap);
	    	
	    	 	//logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	 String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 	//String strFilename = "C:\\temp\\선불본사충전내역 _";//로컬테스트용파일명
	    	 	
	    	 	String strFilename = "";
	    	 	String strSheetname = "";
	    	 	if("basicPage".equals(pRequestParamMap.get("dataPageType").toString())){
		    	 	strFilename = serverInfo + "선불정산대상_"; //파일명
		    		strSheetname = "선불정산대상";//시트명
	    	 	}else if("usimPage".equals(pRequestParamMap.get("dataPageType").toString())){
	    	 		strFilename = serverInfo + "유심임대선불관리_"; //파일명
		    		strSheetname = "유심임대선불관리";//시트명
	    	 	}
	    		
	    		//엑셀 첫줄
	            String [] strHead = {"계약번호", "요금제", "상태", "개통일", "정지일", "해지일","만료일", "모델명", "사용기간", "총충전액", "총충전수", "최근충전일", "실충전액", "사용횟수", "사용분", "사용액", "데이타량(M)", "데이타사용액","사용액합계","잔액", "개통대리점"};
	            String [] strValue = {"contractNum", "serviceNm", "subStatusNm", "lstComActvDate", "statusStopDt", "statusCancelDt","basicExpire", "modelName", "useTurm", "totRcg", "rcgCnt", "lastBasicChgDt", "realRcg", "callCnt", "callDur", "callCharge", "pktDur", "pktCharge","totalCharge","basicRemains","agentName"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,10000};
	            int[] intLen = {0, 0, 0,0, 0, 0, 0, 0, 1, 1, 1, 0,1,1,1,1,1,1,1,1,0};
	            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

	           
	            
	             file = new File(strFileName);
	            
	            pResponse.setContentType("applicaton/download");
	            pResponse.setContentLength((int) file.length());
	            
	            String userAgent = pRequest.getHeader("User-Agent");
	            boolean ie = userAgent.indexOf("Trident") > -1;

//	            logger.debug("strFileName 123= " + strFileName);
	            if(ie){
	            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
	            }
	            else
	            {
	            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
	            }
//	            logger.debug("strFileName = " + strFileName);
	            strFileName = strFileName.substring(13);
	            
	            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");

	             in = new FileInputStream(file);
	            
	             out = pResponse.getOutputStream();
	            
	            int temp = -1;
	            while((temp = in.read()) != -1){
	            	out.write(temp);
	            }
	            
	            
	   			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	   			resultMap.put("msg", "다운로드성공");
	   			
	   		} catch (Exception e) {
	   			resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//	logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			           // logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	  //logger.error(e);
			          }
			        }
		            file.delete();
			    }
	   		//----------------------------------------------------------------
	   		// return json 
	   		//----------------------------------------------------------------	
	   		model.addAttribute("result", resultMap);
		*/
    	return "jsonView";
	}
	
	/**
	 * 우량정산대상 목록조회 페이지 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoExcellentMgmtForm.do", method={POST, GET} )
	public ModelAndView selectDataInfoExcellentMgmtListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcDataMgmtController.DataInfoExcellentMgmtListForm :우량정산대상 목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/datamgmt/hdofc_datamgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	/**
	 * 우량정산대상 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoExcellentMgmtList.json", method={POST, GET} )
	public String selectDataInfoExcellentMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectDataInfoExcellentMgmtListJson:우량정산내역 목록조회");
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
    		
	    	 List<?> resultList = dataService.getDataInfoExcellentMgmtList(pRequestParamMap);
	    	 HashMap maskFields = new HashMap();
	 	 	 maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
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
	 * 우량정산대상 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoExcellentMgmtListExcel.json" )
	public String selectDataInfoExcellentMgmtListExcelJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectDataInfoExcellentMgmtListExcelJson:우량정산대상엑셀출력 ");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			dataService.getDataInfoExcellentMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
		/**
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	 List<?> resultList = dataService.getDataInfoMgmtListExcel(pRequestParamMap);
	    	
	    	 	//logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	 String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 	//String strFilename = "C:\\temp\\선불본사충전내역 _";//로컬테스트용파일명
	    	 	
	    	 	String strFilename = "";
	    	 	String strSheetname = "";
	    	 	if("basicPage".equals(pRequestParamMap.get("dataPageType").toString())){
		    	 	strFilename = serverInfo + "선불정산대상_"; //파일명
		    		strSheetname = "선불정산대상";//시트명
	    	 	}else if("usimPage".equals(pRequestParamMap.get("dataPageType").toString())){
	    	 		strFilename = serverInfo + "유심임대선불관리_"; //파일명
		    		strSheetname = "유심임대선불관리";//시트명
	    	 	}
	    		
	    		//엑셀 첫줄
	            String [] strHead = {"계약번호", "요금제", "상태", "개통일", "정지일", "해지일","만료일", "모델명", "사용기간", "총충전액", "총충전수", "최근충전일", "실충전액", "사용횟수", "사용분", "사용액", "데이타량(M)", "데이타사용액","사용액합계","잔액", "개통대리점"};
	            String [] strValue = {"contractNum", "serviceNm", "subStatusNm", "lstComActvDate", "statusStopDt", "statusCancelDt","basicExpire", "modelName", "useTurm", "totRcg", "rcgCnt", "lastBasicChgDt", "realRcg", "callCnt", "callDur", "callCharge", "pktDur", "pktCharge","totalCharge","basicRemains","agentName"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,10000};
	            int[] intLen = {0, 0, 0,0, 0, 0, 0, 0, 1, 1, 1, 0,1,1,1,1,1,1,1,1,0};
	            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

	           
	            
	             file = new File(strFileName);
	            
	            pResponse.setContentType("applicaton/download");
	            pResponse.setContentLength((int) file.length());
	            
	            String userAgent = pRequest.getHeader("User-Agent");
	            boolean ie = userAgent.indexOf("Trident") > -1;

//	            logger.debug("strFileName 123= " + strFileName);
	            if(ie){
	            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
	            }
	            else
	            {
	            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
	            }
//	            logger.debug("strFileName = " + strFileName);
	            strFileName = strFileName.substring(13);
	            
	            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");

	             in = new FileInputStream(file);
	            
	             out = pResponse.getOutputStream();
	            
	            int temp = -1;
	            while((temp = in.read()) != -1){
	            	out.write(temp);
	            }
	            
	            
	   			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	   			resultMap.put("msg", "다운로드성공");
	   			
	   		} catch (Exception e) {
	   			resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			          //  logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	//  logger.error(e);
			          }
			        }
		            file.delete();
			    }
	   		//----------------------------------------------------------------
	   		// return json 
	   		//----------------------------------------------------------------	
	   		model.addAttribute("result", resultMap);
		*/
    	return "jsonView";
	}
	
	/**
	 * 환수정산대상 목록조회 페이지 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoClawbackMgmtForm.do", method={POST, GET} )
	public ModelAndView selectDataInfoClawbackMgmtListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcDataMgmtController.DataInfoClawbackMgmtListForm :환수정산대상 목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/datamgmt/hdofc_datamgmt_0050");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	/**
	 * 우량정산대상 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoClawbackMgmtList.json", method={POST, GET} )
	public String selectDataInfoClawbackMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectDataInfoClawbackMgmtListJson:환수정산내역 목록조회");
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
    		
	    	 List<?> resultList = dataService.getDataInfoClawbackMgmtList(pRequestParamMap);
	    	 HashMap maskFields = new HashMap();
	 	 	 maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
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
	 * 우량정산대상 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/DataInfoClawbackMgmtListExcel.json" )
	public String selectDataInfoClawbackMgmtListExcelJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectDataInfoClawbackMgmtListExcelJson:환수정산대상엑셀출력 ");
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			dataService.getDataInfoClawbackMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
		/**
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	 List<?> resultList = dataService.getDataInfoMgmtListExcel(pRequestParamMap);
	    	
	    	 	//logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	 String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 	//String strFilename = "C:\\temp\\선불본사충전내역 _";//로컬테스트용파일명
	    	 	
	    	 	String strFilename = "";
	    	 	String strSheetname = "";
	    	 	if("basicPage".equals(pRequestParamMap.get("dataPageType").toString())){
		    	 	strFilename = serverInfo + "선불정산대상_"; //파일명
		    		strSheetname = "선불정산대상";//시트명
	    	 	}else if("usimPage".equals(pRequestParamMap.get("dataPageType").toString())){
	    	 		strFilename = serverInfo + "유심임대선불관리_"; //파일명
		    		strSheetname = "유심임대선불관리";//시트명
	    	 	}
	    		
	    		//엑셀 첫줄
	            String [] strHead = {"계약번호", "요금제", "상태", "개통일", "정지일", "해지일","만료일", "모델명", "사용기간", "총충전액", "총충전수", "최근충전일", "실충전액", "사용횟수", "사용분", "사용액", "데이타량(M)", "데이타사용액","사용액합계","잔액", "개통대리점"};
	            String [] strValue = {"contractNum", "serviceNm", "subStatusNm", "lstComActvDate", "statusStopDt", "statusCancelDt","basicExpire", "modelName", "useTurm", "totRcg", "rcgCnt", "lastBasicChgDt", "realRcg", "callCnt", "callDur", "callCharge", "pktDur", "pktCharge","totalCharge","basicRemains","agentName"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,10000};
	            int[] intLen = {0, 0, 0,0, 0, 0, 0, 0, 1, 1, 1, 0,1,1,1,1,1,1,1,1,0};
	            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

	           
	            
	             file = new File(strFileName);
	            
	            pResponse.setContentType("applicaton/download");
	            pResponse.setContentLength((int) file.length());
	            
	            String userAgent = pRequest.getHeader("User-Agent");
	            boolean ie = userAgent.indexOf("Trident") > -1;

//	            logger.debug("strFileName 123= " + strFileName);
	            if(ie){
	            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
	            }
	            else
	            {
	            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
	            }
//	            logger.debug("strFileName = " + strFileName);
	            strFileName = strFileName.substring(13);
	            
	            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");

	             in = new FileInputStream(file);
	            
	             out = pResponse.getOutputStream();
	            
	            int temp = -1;
	            while((temp = in.read()) != -1){
	            	out.write(temp);
	            }
	            
	            
	   			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	   			resultMap.put("msg", "다운로드성공");
	   			
	   		} catch (Exception e) {
	   			resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			          //  logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	//  logger.error(e);
			          }
			        }
		            file.delete();
			    }
	   		//----------------------------------------------------------------
	   		// return json 
	   		//----------------------------------------------------------------	
	   		model.addAttribute("result", resultMap);
		*/
    	return "jsonView";
	}
	

	/**
	 * 재충전대상 목록조회 페이지 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/ReChargeDataInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectReChargeDataInfoMgmtListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcDataMgmtController.selectReChargeDataInfoMgmtListForm :재충전대상 목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/datamgmt/hdofc_datamgmt_0060");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	/**
	 * 재충전대상 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/ReChargeDataInfoMgmtList.json", method={POST, GET} )
	public String selectReChargeDataInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectReChargeDataInfoMgmtListJson:재충전대상내역 목록조회");
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
    		
	    	 List<?> resultList = dataService.getReChargeDataInfoMgmtList(pRequestParamMap);
	    	 HashMap maskFields = new HashMap();
	 	 	 maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
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
	 * 우량정산대상 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/ReChargeDataInfoMgmtListExcel.json" )
	public String selectReChargeDataInfoMgmtListExcelJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectReChargeDataInfoMgmtListExcelJson:재충전대상엑셀출력 ");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			dataService.getReChargeDataInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
			
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
		/**
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	 List<?> resultList = dataService.getDataInfoMgmtListExcel(pRequestParamMap);
	    	
	    	 	//logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	 String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 	//String strFilename = "C:\\temp\\선불본사충전내역 _";//로컬테스트용파일명
	    	 	
	    	 	String strFilename = "";
	    	 	String strSheetname = "";
	    	 	if("basicPage".equals(pRequestParamMap.get("dataPageType").toString())){
		    	 	strFilename = serverInfo + "선불정산대상_"; //파일명
		    		strSheetname = "선불정산대상";//시트명
	    	 	}else if("usimPage".equals(pRequestParamMap.get("dataPageType").toString())){
	    	 		strFilename = serverInfo + "유심임대선불관리_"; //파일명
		    		strSheetname = "유심임대선불관리";//시트명
	    	 	}
	    		
	    		//엑셀 첫줄
	            String [] strHead = {"계약번호", "요금제", "상태", "개통일", "정지일", "해지일","만료일", "모델명", "사용기간", "총충전액", "총충전수", "최근충전일", "실충전액", "사용횟수", "사용분", "사용액", "데이타량(M)", "데이타사용액","사용액합계","잔액", "개통대리점"};
	            String [] strValue = {"contractNum", "serviceNm", "subStatusNm", "lstComActvDate", "statusStopDt", "statusCancelDt","basicExpire", "modelName", "useTurm", "totRcg", "rcgCnt", "lastBasicChgDt", "realRcg", "callCnt", "callDur", "callCharge", "pktDur", "pktCharge","totalCharge","basicRemains","agentName"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,10000};
	            int[] intLen = {0, 0, 0,0, 0, 0, 0, 0, 1, 1, 1, 0,1,1,1,1,1,1,1,1,0};
	            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

	           
	            
	             file = new File(strFileName);
	            
	            pResponse.setContentType("applicaton/download");
	            pResponse.setContentLength((int) file.length());
	            
	            String userAgent = pRequest.getHeader("User-Agent");
	            boolean ie = userAgent.indexOf("Trident") > -1;

//	            logger.debug("strFileName 123= " + strFileName);
	            if(ie){
	            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
	            }
	            else
	            {
	            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
	            }
//	            logger.debug("strFileName = " + strFileName);
	            strFileName = strFileName.substring(13);
	            
	            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");

	             in = new FileInputStream(file);
	            
	             out = pResponse.getOutputStream();
	            
	            int temp = -1;
	            while((temp = in.read()) != -1){
	            	out.write(temp);
	            }
	            
	            
	   			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	   			resultMap.put("msg", "다운로드성공");
	   			
	   		} catch (Exception e) {
	   			resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			          //  logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	//  logger.error(e);
			          }
			        }
		            file.delete();
			    }
	   		//----------------------------------------------------------------
	   		// return json 
	   		//----------------------------------------------------------------	
	   		model.addAttribute("result", resultMap);
		*/
    	return "jsonView";
	}
	
	/**
	 * 실시간선불정산내역 목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/RealDataInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRealDataInfoMgmtListForm( ModelMap model, 
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

			
			logger.info("실시간선불정산내역 목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/datamgmt/hdofc_datamgmt_0070");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    	
		
	}
	
	/**
	 * 실시간선불정산내역목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/datamgmt/RealDataInfoMgmtList.json", method={POST, GET} )
	public String selectRealDataInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcRealDataMgmtController.DataInfoMgmtListJson:실시간선불정산내역 목록조회");
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
    		
	    	 List<?> resultList = dataService.getRealDataInfoMgmtList(pRequestParamMap);
	    	 HashMap maskFields = new HashMap();
	 	 	 maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
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
	 * 실시간선불정산대상 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
		
	@RequestMapping(value = "/pps/hdofc/datamgmt/RealDataInfoMgmtListExcel.json" )
	public String selectRealDataInfoMgmtListExcelJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcDataMgmtController.selectRealDataInfoMgmtListExcelJson:실시간선불정산대상엑셀출력 ");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			dataService.getRealDataInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
		
    	return "jsonView";
	}
	
}
