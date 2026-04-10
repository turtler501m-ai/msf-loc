package com.ktis.msp.pps.rcgmgmt.controller;


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
import com.ktis.msp.pps.rcgmgmt.service.PpsHdofcRcgMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : PpsHdofcRcgMgmtController.java
 * @Description : PpsHdofcRcgMgmtController class
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
public class PpsHdofcRcgMgmtController  extends BaseController {
	
	@Autowired
	private PpsHdofcRcgMgmtService rcgService;
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private MaskingService  maskingService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
/*	
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/{id}.do", method={POST, GET})
	public String ppsHdofcRcgMgmt(@PathVariable("id") String id){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("test START."+id));
		logger.info(generateLogMsg("================================================================="));

		
		return "pps/hdofc/rcgmgmt/hdofc_rcgmgmt_"+id;
	}
*/	
	
	/**
	 * 충전내역 목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgInfoMgmtListForm( ModelMap model, 
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

			
			logger.info(" PpsHdofcRcgMgmtController.RcgInfoMgmtListForm:  충전내역 목록조회 페이지 호출");
			
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
			modelAndView.setViewName("pps/hdofc/rcgmgmt/hdofc_rcgmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 충전내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgInfoMgmtList.json" )
	public String selectRcgInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcRcgMgmtController.RcgInfoMgmtListJson : 충전내역 목록조회");
		
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
	    	
	    	 List<?> resultList = rcgService.getRcgInfoMgmtList(pRequestParamMap);
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
	 * 선불 본사 충전내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgInfoMgmtListExcel.json" )
	public String selectRcgInfoMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcOrgMgmtController.selectRcgRealCmsMgmtListExcelJson: 선불 본사 충전내역 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			rcgService.getRcgInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
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
	    	 List<?> resultList = rcgService.getRcgInfoMgmtListExcel(pRequestParamMap);
	    	
	    	 	//logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	 String serverInfo= null;
	    	 serverInfo = propertyService.getString("excelPath");
	    	 	
	    	 	
	    	 	String strFilename = serverInfo + "선불충전내역_"; //파일명
	    		String strSheetname = "충전내역";//시트명

	    		//엑셀 첫줄
	            String [] strHead = {"계약번호", "충전요청", "충전구분", "충전정보", "결제방식", "충전금액", "실입금액", "충전결과","결과메세지", "충전일자", "충전후잔액", "충전후만료일", "개통대리점", "충전대리점", "충전관리자", "취소여부", "취소일자"};

	            String [] strValue = {"contractNum","reqSrc","chgTypeName","rechargeInfo","rechargeMethod","amount","inAmount","ktResCode","remark","rechargeDate","basicRemains","basicExpire","openAgentNm","rechargeAgentName","adminNm","cancelFlag","cancelDt"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
	            int[] intLen = {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1,0,0,0,0,0,0};
	            
	            
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
					logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			            logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	  logger.error(e);
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
	 * 실시간자동출금내역 목록조회폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgRealCmsMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgRealCmsMgmtListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcRcgMgmtController.RcgRealCmsMgmtListForm : 실시간자동출금내역 목록조회 페이지 호출");
			
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
			modelAndView.setViewName("pps/hdofc/rcgmgmt/hdofc_rcgmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 실시간자동출금내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgRealCmsMgmtList.json" )
	public String selectRcgRealCmsMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info(" PpsHdofcRcgMgmtController.RcgRealCmsMgmtListJson : 실시간자동출금내역 목록조회");
		
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
	    	
	    	 List<?> resultList = rcgService.getRcgRealCmsMgmtList(pRequestParamMap);
	    	//----------------------------------------------------------------
			 	// Masking
			 	//----------------------------------------------------------------
			 		HashMap maskFields = new HashMap();
			 		
			 		maskFields.put("bankUserName", "CUST_NAME"); // 이름 
			 		
			 		
			 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
			 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	    	 
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
        // logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
   	return "jsonView";
	}
	
	/**
	 * 실시간 자동출금내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgRealCmsMgmtListExcel.json" )
	public String selectRcgRealCmsMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcOrgMgmtController.selectRcgRealCmsMgmtListExcelJson :실시간자동출금내역 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			rcgService.getRcgRealCmsMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}

	    	 
	    	 
	 	//----------------------------------------------------------------
	 	// Masking
	 	//----------------------------------------------------------------
	 		HashMap maskFields = new HashMap();
	 		
	 		maskFields.put("bankUserName", "CUST_NAME"); // 이름 
	 		
	 		
	 		/***
	 		
	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	 
	 String serverInfo= null;
	 	serverInfo = propertyService.getString("excelPath");
	
	 	String strFilename = serverInfo  + "선불실시간자동충금내역_"; //파일명
		String strSheetname = "실시간자동출금내역";//시트명

		//엑셀 첫줄
        String [] strHead = {"계약번호","요청일자","구분","출금여부","은행코드","예금주명","출금요청금액","실제출금액","출금결과코드","출금수수료","충전결과"};
        String [] strValue = {"contractNum","reqDate","chgTypeName","succFlagNm","bankCodeName","bankUserName","reqAmount","resAmount","resCodeName","chargeFee","rechargeResult"};

        //엑셀 컬럼 사이즈
        int[] intWidth = {3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
        int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0,0};
        String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

       
        
         file = new File(strFileName);
        
        pResponse.setContentType("applicaton/download");
        pResponse.setContentLength((int) file.length());
        
        String userAgent = pRequest.getHeader("User-Agent");
        boolean ie = userAgent.indexOf("Trident") > -1;

//        logger.debug("strFileName 123= " + strFileName);
        if(ie){
        	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
        }
        else
        {
        	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
        }
//        logger.debug("strFileName = " + strFileName);
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
			logger.error(e);
		}
		} finally {
        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	            logger.error(e);
	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
	        	  logger.error(e);
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
	 * 가상계좌입금내역 목록조회폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgVacRechargeMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgVacRechargeMgmtListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcRcgMgmtController.RcgVacRechargeMgmtListForm: 가상계좌입금내역 목록조회 페이지 호출 ");
			
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
			modelAndView.setViewName("pps/hdofc/rcgmgmt/hdofc_rcgmgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 가상계좌입금내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgVacRechargeMgmtList.json" )
	public String selectRcgVacRechargeMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcRcgMgmtController.RcgVacRechargeMgmtistJson : 가상계좌입금내역 목록조회");
		
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
	    	
	    	 List<?> resultList = rcgService.getRcgVacRechargeMgmtList(pRequestParamMap);
	    	//----------------------------------------------------------------
			 	// Masking
			 	//----------------------------------------------------------------
			 		HashMap maskFields = new HashMap();
			 		
			 		maskFields.put("requester", "CUST_NAME"); // 이름 
			 		
			 		
			 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
			 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	    	 
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
	 *  가상계좌입금내역 목록조회 엑셀출력 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgVacRechargeMgmtListExcel.json" )
	public String selectcRcgVacRechargeMgmtListExcelJson( ModelMap model, 
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

		logger.info("PpsHdofcOrgMgmtController.selectcRcgVacRechargeMgmtListExcelJson : 가상계좌입금내역 목록조회 엑셀출력  엑셀출력");
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			rcgService.getRcgVacRechargeMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
		/**
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	 List<?> resultList = rcgService.getRcgVacRechargeMgmtListExcel(pRequestParamMap);
	    	//----------------------------------------------------------------
			 	// Masking
			 	//----------------------------------------------------------------
			 		HashMap maskFields = new HashMap();
			 		
			 		maskFields.put("requester", "CUST_NAME"); // 이름 
			 		
			 		
			 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
			 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	    	 
	    	 String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 
	    	 	String strFilename = serverInfo  + "선불가상계좌입금내역_"; //파일명
	    		String strSheetname = "가상계좌입금내역";//시트명

	    		//엑셀 첫줄
	            String [] strHead = {"가상계좌번호", "은행명", "이용자구분", "이용자번호", "입금일자", "입금액", "송금자명", "송금은행", "충전결과"};

	            String [] strValue = {"vaccNo","bankName","userTypeNm","contractNum","txDate","payTotAmt","requester","payBankNm","endCode"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
	            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0};
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
					logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			            logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	  logger.error(e);
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
	 * 가상계좌 관리 목록조회 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgVacInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgVacInfoMgmtListForm( ModelMap model, 
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

		
			logger.info("PpsHdofcRcgMgmtController.RcgVacInfoMgmtListForm : 가상계좌 관리 목록조회 페이지 호출");
			
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
			modelAndView.setViewName("pps/hdofc/rcgmgmt/hdofc_rcgmgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 가상계좌 관리 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgVacInfoMgmtList.json" )
	public String selectRcgVacInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
		logger.info("PpsHdofcRcgMgmtController.RcgVacInfoMgmtListJson : 가상계좌 관리 목록조회");
		
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
	    	
	    	 List<?> resultList = rcgService.getRcgVacInfoMgmtList(pRequestParamMap);
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
	 * 가상계좌 관리 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgVacInfoMgmtListExcel.json" )
	public String selectcgVacInfoMgmtListExcellJson( ModelMap model, 
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
    	
    	
    	
		logger.info("PpsHdofcOrgMgmtController.selectcgVacInfoMgmtListExcellJson : 가상계좌 관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			rcgService.getRcgVacInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
		/**
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	 List<?> resultList = rcgService.getRcgVacInfoMgmtListExcel(pRequestParamMap);
	    	
	    	 
	    	 String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 	
	    	 	String strFilename = serverInfo + "선불가상계좌관리_"; //파일명
	    	 	String strSheetname = "가상계좌관리";//시트명

	    		//엑셀 첫줄
	            String [] strHead = {"은행명", "가상계좌", "이용자구분", "계약번호", "대리점명", "상태", "부여일자", "회수일자", "최종입금일자", "수납횟수"};

	            String [] strValue = {"vacBankName","vacId","userTypeNm","contractNum","agentName","statusNm","openDate","closeDate","lastPaymentDate","payCount"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
	            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0,0,0};
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
					logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			            logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	  logger.error(e);
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
	 * POS충전내역 목록조회 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgPosRechargeMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgPosRechargeMgmtListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcRcgMgmtController.RcgPosRechargeMgmtListForm : POS충전내역 목록조회 페이지 호출");
			
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
			modelAndView.setViewName("pps/hdofc/rcgmgmt/hdofc_rcgmgmt_0050");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * POS충전내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgPosRechargeMgmtList.json" )
	public String selectRcgPosRechargeMgmtJson( ModelMap model, 
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
    	
    	
    	
    	
    	
		logger.info("PpsHdofcRcgMgmtController.RcgPosRechargeMgmtListJson: POS충전내역 목록조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	   	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		try {
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			List<?> resultList = rcgService.getRcgPosRechargeMgmtList(pRequestParamMap);
			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	 		HashMap<String,String> maskFields = new HashMap<String,String>();
	 		
	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
	 		
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		//logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	 		
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
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
	 * POS충전내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgPosRechargeMgmtListExcel.json" )
	public String selectRcgPosRechargeMgmtListExcelJson( ModelMap model, 
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
    	
    	
    	
    	
    	
		logger.info("PpsHdofcOrgMgmtController.selectRcgPosRechargeMgmtListExcelJson : POS충전내역 목록조회 엑셀출력");
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			rcgService.getRcgPosRechargeMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
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
	    	 List<?> resultList = rcgService.getRcgPosRechargeMgmtListExcel(pRequestParamMap);
	    	//----------------------------------------------------------------
		 		// Masking
		 		//----------------------------------------------------------------
		 		HashMap<String,String> maskFields = new HashMap<String,String>();
		 		
		 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
		 		
		 		
		 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		 		logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	    	
		 		// Map<String, Object> resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 	logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	 	String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 	//String strFilename = "C:\\temp\\선불본사POS충전내역_";//파일명
	    	 	
	    	 	String strFilename = serverInfo + "선불POS충전내역_"; //파일명
	    		String strSheetname = "선불POS충전내역";//시트명

	    		//엑셀 첫줄
	            String [] strHead = {"계약번호","CTN","요청구분","판매점명","승인번호","응답코드" ,"충전금액","등록일자","취소여부","취소일자"};
	            String [] strValue = {"contractNum","subscriberNo","reqTypeNm","storeCodeNm","authCode","retCodeNm","amount","recordDate","cancelFlag","cancelDate"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000, 5000, 9000, 10000, 5000, 7000, 9000,5000,5000};
	            int[] intLen = {0, 0, 0, 0, 0, 0, 1, 0,0,0};
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
					logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			            logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	  logger.error(e);
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
	 * 일괄충전 목록조회 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgBatchRechargeMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgBatchRechargeMgmtListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcRcgMgmtController.RcgBatchRechargeMgmtForm : 일괄충전 목록조회 페이지 호출");
			
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
			modelAndView.setViewName("pps/hdofc/rcgmgmt/hdofc_rcgmgmt_0060");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 일괄충전 고객조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgBatchSearchMgmtList.json" )
	public String selectRcgBatchMgmtJson( ModelMap model, 
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
    	
    	
    	
    	
    	
		logger.info("PpsHdofcRcgMgmtController.RcgPosRechargeMgmtListJson : 일괄충전 고객조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		try {
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			List<?> resultList = rcgService.getRcgBatchSearchMgmtList(pRequestParamMap);

			// ----------------------------------------------------------------
			// Masking
			// ----------------------------------------------------------------
			HashMap maskFields = new HashMap();

			maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호

			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			//logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
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
	 * 일괄충전
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/PpsRcgBatch.json" )
	public String goPpsRcgBatch( ModelMap model, 
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
    	
    	
    	
    	
    	
		logger.info("PpsHdofcRcgMgmtController.goPpsRcgBatch :  일괄충전");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		
    	
		try {
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			pRequestParamMap.put("ip", pRequest.getRemoteAddr());
			resultMap = rcgService.ppsRcgBatch(pRequestParamMap);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
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
	 * 일괄충전 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgBatchSearchMgmtListExcel.json" )
	public String selectRcgBatchSearchMgmtListExcelJson( ModelMap model, 
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
    	
    	
    	
    	
    	
		logger.info("PpsHdofcOrgMgmtController.selectcgVacInfoMgmtListExcellJson : 가상계좌 관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			rcgService.getRcgBatchSearchMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
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
	    	 List<?> resultList = rcgService.getRcgBatchSearchMgmtListExcel(pRequestParamMap);
	    	 
	    	 
		    //----------------------------------------------------------------
			// Masking
			//----------------------------------------------------------------
				HashMap maskFields = new HashMap();
				 		
				maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
				 		
				 		
			 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
			 	 logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	    	 
	    	// Map<String, Object> resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 //	logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	 
	    	 	//String strFilename = "C:\\temp\\선불본사일괄충전_";//파일명
			 	String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 	//String strFilename = "C:\\temp\\선불본사POS충전내역_";//파일명
	    	 	String strFilename = serverInfo + "선불일괄충전내역_"; //파일명
	    		String strSheetname = "일괄충전";//시트명

	    		//엑셀 첫줄
	            String [] strHead = {"계약번호", "휴대폰번호", "요금제", "최근충전일자", "잔액소진일자", "상태", "개통일자", "정지일자", "만료일자", "개통대리점", "잔액", "충전횟수", "총충전금액"};

	            String [] strValue = {"contractNum","subscriberNo","serviceName","lastBasicChgDt","basicEmptDt","subStatusName","lstComActvDate","statusStopDt","basicExpire","agentName","basicRemains","rechargeCnt","rechargeSum"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
	            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0};
	            
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
					logger.error(e);
				}
	   		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			            logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	  logger.error(e);
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
	 * 가상계좌관리 - 가상계좌회수
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/PpsRcgVacReset.json" )
	public String goPpsRcgVacReset( ModelMap model, 
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
    	
    	
    	
		logger.info(" PpsHdofcOrgMgmtController.selectcgVacInfoMgmtListExcellJson : 가상계좌 관리 가상계좌회수");
		
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
	    	
	    	 resultMap = rcgService.goPpsRcgVacReset(pRequestParamMap);
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
	 * 충전내역 - 충전취소
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/PpsRcgCancel.json" )
	public String goPpsRcgCancel( ModelMap model, 
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
		
	
		logger.info("PpsHdofcOrgMgmtController.goPpsRcgCancel : 충전내역 - 충전취소 ");
	
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
	    	
    		pRequestParamMap.put("ip", pRequest.getRemoteAddr());
	    	 resultMap = rcgService.ppsRcgCancel(pRequestParamMap);
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
	 * 대리점정보 가져옴
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/PpsGetAgentInfo.json" )
	public String getAgentInfo( ModelMap model, 
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
    	
    	
    	
		
		logger.info("PpsHdofcRcgMgmtController.PpsGetAgentInfo : 대리점정보조회");
	
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
	    	
	    	 List<?> resultList = rcgService.getAgentInfo(pRequestParamMap);
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
	 * 대리점예치금조정
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/PpsDepositModify.json" )
	public String goPpsDepositModify( ModelMap model, 
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
    	
    	
    	
		
		logger.info("PpsHdofcOrgMgmtController.goPpsDepositModify : 대리점예치금조정");
		
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
	    	
	    	 resultMap = rcgService.goPpsDepositModify(pRequestParamMap);
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
	 * 일괄충전내역 목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgBatchInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgBatchInfoMgmtListForm( ModelMap model, 
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

			
			logger.info(" PpsHdofcRcgMgmtController.RcgBatchInfoMgmtListForm:  일괄충전내역 목록조회 페이지 호출");
			
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
			modelAndView.setViewName("pps/hdofc/rcgmgmt/hdofc_rcgmgmt_0070");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 충전내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgBatchInfoMgmtList.json" )
	public String selectRcgBatchInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcRcgMgmtController.RcgBatchInfoMgmtList : 일괄충전내역 목록조회");
		
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
	    	
	    	 List<?> resultList = rcgService.getRcgBatchInfoMgmtList(pRequestParamMap);
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
	 * 선불 본사 일괄충전내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgBatchInfoMgmtListExcel.json" )
	public String selectRcgBatchInfoMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcOrgMgmtController.selectRcgBatchInfoMgmtListExcelJson: 선불 본사 일괄충전내역 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			rcgService.getRcgBatchInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	return "jsonView";
	}
	
	/**
	 * ATM충전내역 목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgFtpAtmRcgInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgFtpAtmRcgInfoMgmtListForm( ModelMap model, 
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

			
			logger.info(" PpsHdofcRcgMgmtController.RcgFtpAtmRcgInfoMgmtForm:  ATM충전내역 목록조회 페이지 호출");
			
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
			modelAndView.setViewName("pps/hdofc/rcgmgmt/hdofc_rcgmgmt_0080");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * ATM충전내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgFtpAtmRcgInfoMgmtList.json" )
	public String selectRcgFtpAtmRcgInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcRcgMgmtController.RcgFtpAtmRcgInfoMgmtListJson : ATM충전내역 목록조회");
		
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
	    	
	    	 List<?> resultList = rcgService.getRcgFtpAtmRcgInfoMgmtList(pRequestParamMap);
	    	 
	    	 //----------------------------------------------------------------
		 	 // Masking
		 	 //----------------------------------------------------------------
	 		 HashMap maskFields = new HashMap();
	 		maskFields.put("reqTelNo", "MOBILE_PHO");
		 		
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
	 * 선불 본사 ATM충전내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/pps/hdofc/rcgmgmt/RcgFtpAtmRcgInfoMgmtListExcel.json" )
	public String selectRcgFtpAtmRcgInfoMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcOrgMgmtController.selectRcgRealCmsMgmtListExcelJson: 선불 본사 ATM충전내역 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			rcgService.getRcgFtpAtmRcgInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
    	
        
    	return "jsonView";
	}
	
}
