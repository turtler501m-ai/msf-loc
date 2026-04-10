package com.ktis.msp.pps.warmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.smsmgmt.service.FileReadService;
import com.ktis.msp.pps.warmgmt.service.PpsWarMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * @Class Name : PpsWarMgmtController.java
 * @Description : PpsWarMgmt Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.03.16           최초생성
 *
 * @author 
 * @since 2016.03.16 
 * @version 1.0
 */

@Controller
public class PpsWarMgmtController extends BaseController {
	@Autowired
	private PpsWarMgmtService warMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	protected EgovPropertyService propertyService;

//	@Autowired
//	private PpsHdofcCommonService ppsCommonService;
	
	@Autowired
	private MaskingService  maskingService;
	
	@Autowired
	private FileReadService fileReadService;


	

	/**
	 * 다량문자모니터링 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/warmgmt/WarInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectWarInfoMgmtListForm(ModelMap model,
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

			
			logger.info(" 다량문자모니터링 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/warmgmt/hdofc_warmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 다량문자모니터링 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/warmgmt/WarInfoMgmtList.json" )
	public String selectWarInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcWarMgmtController.WarInfoMgmtListJson : 다량문자모니터링 목록조회");
		
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
	    	
	    	 List<?> resultList = warMgmtService.getWarInfoMgmtList(pRequestParamMap);
	    	 
	    	HashMap maskFields = new HashMap();
	 	 	maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	maskFields.put("subscriberNo", "MOBILE_PHO");
	 	 		//maskFields.put("userSsn", "SSN"); // 
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
	 * 선불 본사 다량문자모니터링 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/pps/hdofc/warmgmt/WarInfoMgmtListExcel.json" )
	public String selectWarInfoMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcWarMgmtController.selectWarMgmtListExcelJson: 선불 본사 다량문자모니터링 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	warMgmtService.getWarInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	return "jsonView";
	}
	
	/**
	 * 주의고객 등록
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/warmgmt/PpsWarReg.json" )
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
		
	
		logger.info("PpsHdofcOrgMgmtController.goPpsRcgCancel : 주의고객 등록 ");
	
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
	    	
	    	resultMap = warMgmtService.ppsWarReg(pRequestParamMap);
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
	 * 주의고객관리 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/warmgmt/WarCustInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectWarCustInfoMgmtListForm(ModelMap model,
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

			
			logger.info(" 주의고객관리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/warmgmt/hdofc_warmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 주의고객관리 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/warmgmt/WarRegInfoMgmtList.json" )
	public String selectWarRegInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcWarMgmtController.WarInfoMgmtListJson : 주의고객관리 목록조회");
		
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
	    	
	    	 List<?> resultList = warMgmtService.getWarRegInfoMgmtList(pRequestParamMap);
	    	 
	    	HashMap maskFields = new HashMap();
	 	 	maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	maskFields.put("subscriberNo", "MOBILE_PHO");
	 	 	maskFields.put("adminNm", "CUST_NAME");
	 	 		//maskFields.put("userSsn", "SSN"); // 
	 	 	maskingService.setMask(resultList, maskFields, pRequestParamMap);
	    	 
	    	resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
        
   	 } catch (Exception e) {
   		resultMap.clear();
		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
			throw new MvnoErrorException(e);
		}
     }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 선불 본사 주의고객관리 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/pps/hdofc/warmgmt/WarRegInfoMgmtListExcel.json" )
	public String selectWarRegInfoMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcWarMgmtController.selectWarRegMgmtListExcelJson: 선불 본사 주의고객관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	warMgmtService.getWarRegInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	return "jsonView";
	}
	
	/**
	 *  주의고객등록 Excel Read
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/warmgmt/uploadWarExcelFile.do")
	  public String uploadWarExcelFile (
	  												ModelMap model, 
	  												HttpServletRequest pRequest, 
	  												HttpServletResponse pResponse, 
	  									    		@RequestParam Map<String, Object> pRequestParamMap
	  											)
	  {				
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
  	    Map<String, Object> resultMap = new HashMap<String, Object>();
  	    String sBaseDir = null;
	  	  sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	  logger.info(sBaseDir);
	      	try
	      	{  
	      		// 본사 권한
		    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
		    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
		    	}
		    	
	      		fileReadService.fileUpLoad(pRequest, "file", "PPS" );
	  	        resultMap.put("state", true);
	  			resultMap.put("name", "xxx".replace("'","\\'"));
	  			resultMap.put("size", "" + 111);
	      	
	    	} catch (Exception e) {
	 	 	     		
	    		resultMap.put("state", false);
	    		resultMap.put("name", "lll".replace("'","\\'"));
	    		resultMap.put("size",  111);
	    		model.addAttribute("result", resultMap);
	    		resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
					throw new MvnoErrorException(e);
				}
	    	}
			model.addAttribute("result", resultMap);
		    return "jsonViewArray";

	  }
	
	/**
	 *  주의고객등록 Excel파일 읽기
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/warmgmt/readWarExcelFile.json")
	  public String readWarExcelFile (		ModelMap model,
			  								HttpServletRequest pRequest, 
			  								HttpServletResponse pResponse, 
			  								@RequestParam Map<String, Object> pRequestParamMap
	  											)
	  {				
			
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
	  	    Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    String sBaseDir = null;
	  	    sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	    
	      	try
	      	{  
	      		// 본사 권한
		    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
		    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
		    	}
		    	
	      		String filePath = sBaseDir+"/PPS/" +pRequestParamMap.get("fileName");
	      		String contractNumArr = warMgmtService.getPpsWarFileRead(pRequestParamMap.get("fileName").toString(), filePath);
	      		resultMap.put("contractNumArr", contractNumArr);
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
	 * 문자관리 다량문자발송 Excel Sample 다운로드
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/warmgmt/WarMgmtExcelSample.json" )
	public String selectWarExcelSampleJson( ModelMap model, 
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
        
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;
        
        try {
        	// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
        	String sBaseDir = propertyService.getString("fileUploadBaseDirectory") + "/PPS/";
        	String strFileName = sBaseDir+"war_sample.xls";
        	
            file = new File(strFileName);
            
            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());
            
            String encodingFileName = "";
            
            int excelPathLen2 = sBaseDir.length();

            try {
              encodingFileName = URLEncoder.encode(encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            pResponse.setHeader("Cache-Control", "");
            pResponse.setHeader("Pragma", "");

            pResponse.setContentType("Content-type:application/octet-stream;");
            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            pResponse.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);
            
            out = pResponse.getOutputStream();
            
            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }
            
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
 		} catch (Exception e) {
 			
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

}
