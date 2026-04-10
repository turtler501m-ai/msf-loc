package com.ktis.msp.cmn.fileup2.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.fileup2.service.FileUp2Service;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileUp2Controller.java
 * 3. Package	: com.ktis.msp.cmn.fileup2.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:56:31
 * </PRE>
 */
@Controller
public class FileUp2Controller extends BaseController { 

	protected Log log = LogFactory.getLog(this.getClass());
	
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private FileUp2Service  fileUp2Service;

	/**
	 * <PRE>
	 * 1. MethodName: fileUp
	 * 2. ClassName	: FileUp2Controller
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:56:39
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/fileup2/fileUp.do", method=RequestMethod.POST) 
	public String fileUp(ModelMap model, 
						HttpServletRequest pRequest, 
						HttpServletResponse pResponse, 
						@RequestParam Map<String, Object> pRequestParamMap) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String lSaveFileName = "";
		String lFileNamePc = "";
		
		try{	
			logger.info("===========================================");
			logger.info("====== /cmn/fileup2/test.do ======");
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
			// 
			//----------------------------------------------------------------
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);
	
	  		//--------------------------------
			// upload base directory를 구함
	  		// 존재하지 않으면 exception 발생
			//--------------------------------
	  		String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
			if ( !new File(lBaseDir ).exists())
			{
//				throw new Exception(messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}
			
			//--------------------------------
			// 서버의 경로(모율별)
			//--------------------------------
			String lFilePath      = "/CMN";
			
//			String filename = "";
			Integer filesize = 0;
			for (FileItem item : items) {
				
				//--------------------------------
				// 파일 타입이 아니면 skip
				//--------------------------------
				if (item.isFormField()) 
					continue;
	
				//--------------------------------
				// 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
				//--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
					continue;
				/** 20230518 PMD 조치 */
				OutputStream fout = null;
				InputStream filecontent = null;
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
					
					//----------------------------------------------------------------
					// upload 이름 구함
					//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file"))
					{
						//----------------------------------------------------------------
						// 파일 이름 구함
						//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());
						lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir + lFilePath   ,  lFileNamePc);
						String lTransferTargetFileName = lBaseDir + lFilePath + "/" + lSaveFileName;
						
						//----------------------------------------------------------------
						// 파일 write
						//----------------------------------------------------------------
						filecontent = item.getInputStream();
						File f=new File(lTransferTargetFileName);
	
						fout = new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}
						
					  
					}
				}catch (Exception e) {
					throw new MvnoErrorException(e);
				}finally{
					if(fout != null){
						fout.close();
					}
					if(filecontent != null){
						filecontent.close();
					}
				}


		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
		} catch (Exception e) {
			resultMap.put("state", false);
			resultMap.put("name", lFileNamePc.replace("'","\\'"));
			resultMap.put("size",  111);
			
			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		}
		
		resultMap.put("state", true);
		resultMap.put("name", lSaveFileName.replace("'","\\'"));
		resultMap.put("size", "" + 111);
		
		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	} 	



	/**
	 * <PRE>
	 * 1. MethodName: fileUpUsingService
	 * 2. ClassName	: FileUp2Controller
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:56:48
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * @throws Throwable 
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/fileup2/fileUpUsingService.do") 
	public String fileUpUsingService(ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap) 
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String lSaveFileName = "";
		try
		{  
			logger.info("===========================================");
			logger.info("====== /cmn/fileup2/fileUpLoadUsingService.do ======");
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
  	
			lSaveFileName = fileUp2Service.fileUpLoad(pRequest, "file", "CMN" );
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		
		} catch (Exception e) {
			resultMap.put("state", false);
			resultMap.put("name", lSaveFileName.replace("'","\\'"));
			resultMap.put("size",  111);
			
			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		}
		
		resultMap.put("state", true);
		resultMap.put("name", lSaveFileName.replace("'","\\'"));
		resultMap.put("size", "" + 111);
		
		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	} 	

	


	/**
	 * <PRE>
	 * 1. MethodName: fileUpUsingService
	 * 2. ClassName	: FileUp2Controller
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:56:48
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value="/cmn/fileup2/fileUpById.do") 
	public String fileUpById(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap) 
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String lSaveFileName = "";
		try
		{  
			logger.info("===========================================");
			logger.info("====== /cmn/fileup2/fileUpById.do ======");
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

			lSaveFileName = fileUp2Service.fileUpLoadById(pRequest, "file_upload1", "CMN" , pRequestParamMap);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		
		} catch (Exception e) {
			resultMap.put("state", false);
			resultMap.put("name", lSaveFileName.replace("'","\\'"));
			resultMap.put("size",  111);
			
			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		} catch (Throwable e) {
			throw new MvnoErrorException(e);
		}
		
		resultMap.put("state", true);
		resultMap.put("name", lSaveFileName.replace("'","\\'"));
		resultMap.put("size", "" + 111);
		
		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	} 	


	@RequestMapping(value="/cmn/fileup2/fileDeleteById.do") 
	public String fileDeleteById(ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap) 
	{
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		try
		{
			fileUp2Service.deleteFileMgmtById(pRequestParamMap);
			
		} catch (Exception e)
		{
			throw new MvnoErrorException(e);
		}
		return null;
//        return "/cmn/fileDownLoad/fileDownLoad";
	}
	
}


