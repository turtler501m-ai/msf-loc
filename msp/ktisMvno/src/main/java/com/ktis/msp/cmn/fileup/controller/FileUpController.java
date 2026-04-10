package com.ktis.msp.cmn.fileup.controller;


import java.io.File;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.fileup.service.FileUpService;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : FileUpController.java
 * 3. Package   : com.ktis.msp.cmn.fileup.controller
 * 4. Commnet   : 
 * 5. 작성자    : Administrator
 * 6. 작성일    : 2014. 9. 25. 오후 3:55:00
 * </PRE>
 */
@Controller
public class FileUpController extends BaseController { 

	protected Log log = LogFactory.getLog(this.getClass());
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private FileUpService  fileUpService;

	
	/**
	 * <PRE>
	 * 1. MethodName: fileUp
	 * 2. ClassName : FileUpController
	 * 3. Commnet   : 
	 * 4. 작성자    : Administrator
	 * 5. 작성일    : 2014. 9. 25. 오후 3:55:12
	 * </PRE>
	 *      @return String
	 *      @param model
	 *      @param pRequest
	 *      @param pResponse
	 *      @param pRequestParamMap
	 *      @return
	 *      @throws Exception
	 */
	@RequestMapping(value="/cmn/fileup/fileUp.do") 
	public String fileUp(ModelMap model, 
						HttpServletRequest pRequest, 
						HttpServletResponse pResponse, 
						@RequestParam Map<String, Object> pRequestParamMap) 
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			logger.info("===========================================");
			logger.info("====== /cmn/fileup/fileUp.do ======");
			logger.info("===========================================");
			logger.info(">>>>requestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//--------------------------------------
			// return JSON 변수 선언
			//--------------------------------------
			resultMap = new HashMap<String, Object>();
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) pRequest ;
			MultipartFile multiFile = mpRequest.getFile("file_upload1");
			
			//--------------------------------
			// upload base directory를 구함
			// 존재하지 않으면 exception 발생
			//--------------------------------
			String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
			if ( !new File(lBaseDir ).exists())
			{
//              throw new Exception(messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}
			
			//--------------------------------
			// 서버의 경로(모율별)
			//--------------------------------
			String lFilePath      = "/CMN";
			
			//--------------------------------
			// 사전정의된 파일 사이즈 limit 이하인 경우만 처리
			//--------------------------------
			if ( multiFile.getSize() > 0 &&  multiFile.getSize()  <  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
			{
				//--------------------------------
				// 저장할 파일 이름 구함
				//--------------------------------
				String lFileNamePc = multiFile.getOriginalFilename();
				String lSaveFileName = fileUpService.getAlternativeFileName(  lBaseDir + lFilePath   ,  lFileNamePc);
				String lTransferTargetFileName = lBaseDir + lFilePath + "/" + lSaveFileName;
		
				try
				{
					//--------------------------------
					// 모듈별 directory가 존재하지 않으면 생성
					//--------------------------------
					File lDir = new File(lBaseDir + lFilePath);
					if ( !lDir.exists())
					{
						lDir.mkdirs();
					}
					
					//--------------------------------
					// 파일 저장
					//--------------------------------
					multiFile.transferTo(new File(lTransferTargetFileName));
				}catch (Exception e) {
//					20200512 소스코드점검 수정
//			    	e.printStackTrace();
					//System.out.println("Connection Exception occurred");
					//20210722 pmd소스코드수정
					logger.error("Connection Exception occurred");
					throw new Exception("");
				}
			}
	
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch ( Exception e)
		{
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
			logger.debug(e);
		}finally{
//          sqlService.Close();
			dummyFinally();
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}   

	/**
	 * <PRE>
	 * 1. MethodName: fileUpUsingService
	 * 2. ClassName : FileUpController
	 * 3. Commnet   : 
	 * 4. 작성자    : Administrator
	 * 5. 작성일    : 2014. 9. 25. 오후 3:55:19
	 * </PRE>
	 *      @return String
	 *      @param model
	 *      @param pRequest
	 *      @param pResponse
	 *      @param pRequestParamMap
	 *      @return
	 *      @throws Exception
	 */
	@RequestMapping(value="/cmn/fileup/fileUpUsingService.do") 
	public String fileUpUsingService(ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap) 
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try
		{  
			logger.info("===========================================");
			logger.info("====== /cmn/fileup/fileUpUsingService.do ======");
			logger.info("===========================================");
			logger.info(">>>>requestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//--------------------------------------
			// return JSON 변수 선언
			//--------------------------------------
	
			fileUpService.fileUpLoad(pRequest, "file_upload1", "CMN" , pRequestParamMap);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		
		}catch ( Exception e)
		{
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
			logger.debug(e);
		} catch (Throwable e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		}finally{
//          sqlService.Close();
			dummyFinally();
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}   

	

	/**
	 * <PRE>
	 * 1. MethodName: fileDeleteSample
	 * 2. ClassName : FileUpController
	 * 3. Commnet   : 
	 * 4. 작성자    : Administrator
	 * 5. 작성일    : 2014. 9. 25. 오후 3:55:24
	 * </PRE>
	 *      @return String
	 *      @param model
	 *      @param pRequest
	 *      @param pResponse
	 *      @param pRequestParamMap
	 *      @return
	 *      @throws Exception
	 */
	@RequestMapping(value="/cmn/fileup/fileDeleteSample.do") 
	public String fileDeleteSample(ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap) 
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try
		{  
			logger.info("===========================================");
			logger.info("====== /cmn/fileup/fileDelete.do ======");
			logger.info("===========================================");
			logger.info(">>>>requestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//--------------------------------------
			// return JSON 변수 선언
			//--------------------------------------
	
			boolean deleteFlag = fileUpService.fileDelete("context-common.xml", "CMN" );
			if( deleteFlag )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "파일 삭제 실패");
			}
		
		}catch ( Exception e)
		{
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
			logger.debug(e);
		}finally{
//          sqlService.Close();
			dummyFinally();
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}   
	
}


