package com.ktis.msp.cmn.fileup.service;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.cmn.fileup.mapper.FileUpMapper;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileUpService.java
 * 3. Package	: com.ktis.msp.cmn.fileup.service
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:55:41
 * </PRE>
 */
@Service
public class FileUpService {
		protected Logger logger = LogManager.getLogger(getClass());
		@Autowired  
		protected MessageSource messageSource; 

		@Autowired
		private FileUpMapper fileUpMapper;

	
	    /** EgovPropertyService */
	    @Resource(name = "propertiesService")
	    protected EgovPropertyService propertyService;
	    

	    
	    //----------------------------------------------------
	    // upload log까지 남기는 file upload
	    //----------------------------------------------------
		/**
		 * <PRE>
		 * 1. MethodName: fileUpLoad
		 * 2. ClassName	: FileUpService
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 9. 25. 오후 3:55:51
		 * </PRE>
		 * 		@return String
		 * 		@param pRequest
		 * 		@param p_fieldName
		 * 		@param p_path
		 * 		@param pRequestParamMap
		 * 		@return
		 * 		@throws Exception
		 */
		public String fileUpLoad(
										HttpServletRequest pRequest, 
										String pFieldName,
										String pPath,
										Map<String, Object> pRequestParamMap
								) throws Throwable 
		{				
	
			String lSaveFileName = "";
			String lPath = pPath;
			
			try
		    {    	
		    	//--------------------------------------
		  		// return JSON 변수 선언
		  		//--------------------------------------
		  	    MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) pRequest ;
		  	    MultipartFile multiFile = mpRequest.getFile(pFieldName ); // "file_upload1");
			    
		  	    //--------------------------------
		        // upload base directory를 구함
		  	    // 존재하지 않으면 exception 발생
		        //--------------------------------
		  	    String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
				if ( !new File(lBaseDir ).exists())
				{
//					throw new Exception(messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
					throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
				}

		  	    //--------------------------------
		        // 모듈별 경로에 앞뒤로 "/" 가 붙거나 붙지않은 경우 check하여 "/path" 형태로 변경함
		        //--------------------------------
		        if ( lPath.contains(".."))
		        {
		        		return null;
		        }
		        
		        lPath = lPath.replaceAll("^/", "");
		        lPath = lPath.replaceAll("/$", "");
		  	    String lFilePath      = "/" + lPath; 
		  	  
		  	    //--------------------------------
		        // 사전정의된 파일 사이즈 limit 이하인 경우만 처리
		        //--------------------------------
				if ( multiFile.getSize() > 0 &&  multiFile.getSize()  <  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
				{
					//--------------------------------
			        // 저장할 파일 이름 구함
			        //--------------------------------
					String lFileNamePc = multiFile.getOriginalFilename();
					lSaveFileName = getAlternativeFileName(  lBaseDir + lFilePath   ,  lFileNamePc);
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
						
				        if (StringUtils.defaultString((String)pRequestParamMap.get("writeFileUpDownLog"),"N").equals("Y") )
				        {
							pRequestParamMap.put("FILE_NM",lSaveFileName);
							pRequestParamMap.put("DUTY_NM","");
							pRequestParamMap.put("FILE_URL",lTransferTargetFileName);
							pRequestParamMap.put("FILE_SIZE",multiFile.getSize());
							pRequestParamMap.put("DATA_CNT", 0);
							
							insertCmnFileUpldMgmtMst(pRequestParamMap);
				        }

						
						//--------------------------------
				        // 파일 저장
				        //--------------------------------
						multiFile.transferTo(new File(lTransferTargetFileName));
					}catch (Exception e) {
//						20200512 소스코드점검 수정
//				    	e.printStackTrace();
						//System.out.println("Connection Exception occurred");
						//20210722 pmd소스코드수정
						logger.error("Connection Exception occurred");
					    throw e;
					}
				}
			} catch (Exception e)
			{
				throw e;
			}
			
			return  lSaveFileName;
	    } 	
	
		
	    //----------------------------------------------------
	    // upload log까지 남기지 않는 file upload
	    //----------------------------------------------------
//		/**
//		 * <PRE>
//		 * 1. MethodName: fileUpLoad
//		 * 2. ClassName	: FileUpService
//		 * 3. Commnet	: 
//		 * 4. 작성자	: Administrator
//		 * 5. 작성일	: 2014. 9. 25. 오후 3:56:09
//		 * </PRE>
//		 * 		@return String
//		 * 		@param pRequest
//		 * 		@param p_fieldName
//		 * 		@param p_path
//		 * 		@return
//		 * 		@throws Exception
//		 */
//		public String fileUpLoad(
//										HttpServletRequest pRequest, 
//										String pFieldName,
//										String pPath
//								) throws Throwable
//		{				
//	
//			String lSaveFileName = "";
//			String lPath = pPath;
//			try
//		    {    	
//		    	//--------------------------------------
//		  		// return JSON 변수 선언
//		  		//--------------------------------------
//		  	    MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) pRequest ;
//		  	    MultipartFile multiFile = mpRequest.getFile(pFieldName ); // "file_upload1");
//			    
//		  	    //--------------------------------
//		        // upload base directory를 구함
//		  	    // 존재하지 않으면 exception 발생
//		        //--------------------------------
//		  	    String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
//				if ( !new File(lBaseDir ).exists())
//				{
////					throw new Exception(messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
//					throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
//				}
//
//		  	    //--------------------------------
//		        // 모듈별 경로에 앞뒤로 "/" 가 붙거나 붙지않은 경우 check하여 "/path" 형태로 변경함
//		        //--------------------------------
//		        if ( lPath.contains(".."))
//		        {
//		        		return null;
//		        }
//		        
//		        lPath = lPath.replaceAll("^/", "");
//		        lPath = lPath.replaceAll("/$", "");
//		  	    String lFilePath      = "/" + lPath; 
//		  	  
//		  	    //--------------------------------
//		        // 사전정의된 파일 사이즈 limit 이하인 경우만 처리
//		        //--------------------------------
//				if ( multiFile.getSize() > 0 &&  multiFile.getSize()  <  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
//				{
//					//--------------------------------
//			        // 저장할 파일 이름 구함
//			        //--------------------------------
//					String lFileNamePc = multiFile.getOriginalFilename();
//					lSaveFileName = getAlternativeFileName(  lBaseDir + lFilePath   ,  lFileNamePc);
//			    	String lTransferTargetFileName = lBaseDir + lFilePath + "/" + lSaveFileName;
//			
//			    	
//					try
//					{
//						//--------------------------------
//				        // 모듈별 directory가 존재하지 않으면 생성
//				        //--------------------------------
//						File lDir = new File(lBaseDir + lFilePath);
//						if ( !lDir.exists())
//						{
//							lDir.mkdirs();
//						}
//						
//						//--------------------------------
//				        // 파일 저장
//				        //--------------------------------
//						multiFile.transferTo(new File(lTransferTargetFileName));
//					}catch (Exception e) {
//						e.printStackTrace();
//					    throw e;
//					}
//				}
//			} catch (Exception e)
//			{
//				throw e;
//			}
//			
//			return  lSaveFileName;
//	    } 	
	


		/**
		 * <PRE>
		 * 1. MethodName: getFilePath
		 * 2. ClassName	: FileUpService
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 9. 25. 오후 3:56:14
		 * </PRE>
		 * 		@return String
		 * 		@param p_fileName
		 * 		@param p_path
		 * 		@return
		 * 		@throws Exception
		 */
		public String getFilePath(
										String pFileName,
										String pPath
								) 
		{				
	
			
			String lTransferTargetFileName = "";
			String lPath = pPath;
 
	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
	  	    String lBaseDir = propertyService.getString("fileUploadBaseDirectory");


	  	    //--------------------------------
	        // 모듈별 경로에 앞뒤로 "/" 가 붙거나 붙지않은 경우 check하여 "/path" 형태로 변경함
	        //--------------------------------
	  	    lPath = lPath.replaceAll("^/", "");
	  	    lPath = lPath.replaceAll("/$", "");
	  	    String lFilePath      = "/" + lPath; 
		  	  
		  	lTransferTargetFileName = lBaseDir + lFilePath + "/" + pFileName;
			
			return  lTransferTargetFileName;
	    } 	
		

		/**
		 * <PRE>
		 * 1. MethodName: fileDelete
		 * 2. ClassName	: FileUpService
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 9. 25. 오후 3:56:18
		 * </PRE>
		 * 		@return boolean
		 * 		@param p_fileName
		 * 		@param p_path
		 * 		@return
		 * 		@throws Exception
		 */
		//20210722 pmd소스코드수정
		//취약점명 AvoidSynchronizedAtMethodLevel 조치
		public  boolean fileDelete(
				String pFileName,
				String pPath
				)
		{
			synchronized(this) {

//				String l_saveFileName = "";
				String lPath = pPath;

		  	    //--------------------------------
		        // upload base directory를 구함
		  	    // 존재하지 않으면 exception 발생
		        //--------------------------------
		  	    String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
				if ( !new File(lBaseDir ).exists())
					return false;

		  	    //--------------------------------
		        // 모듈별 경로에 앞뒤로 "/" 가 붙거나 붙지않은 경우 check하여 "/path" 형태로 변경함
		        //--------------------------------
				lPath = lPath.replaceAll("^/", "");
				lPath = lPath.replaceAll("/$", "");
		  	    String lFilePath      = "/" + lPath; 
		  	    if ( lFilePath.contains(".."))
		  	    	return false;
		  	  
		  	    String lTransferTargetFileName = lBaseDir + lFilePath + "/" + pFileName;
		  	    
		  	    File lFile = new File(lTransferTargetFileName);
		  	    if(lFile.isDirectory())
		  	    	return false;
		  	    
		  	    lFile.delete();

				return  true;
				
			}
			
		}
//		public synchronized boolean fileDelete(
//										String pFileName,
//										String pPath
//								) 
//		{				
//	
//	    } 	
	
//		//소스코드점검 수정 20200513
//		//Delete 동기화 함수 생성 
//		private synchronized void FileDelete(File file) { 
//				file.delete(); 
//		} 

		
		/**
		 * <PRE>
		 * 1. MethodName: getAlternativeFileName
		 * 2. ClassName	: FileUpService
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 9. 25. 오후 3:56:22
		 * </PRE>
		 * 		@return String
		 * 		@param p_path
		 * 		@param p_fileName
		 * 		@return
		 */
		public static String getAlternativeFileName(String pPath, String pFileName)
		{
			
			int lFileNameCount  = 0;
			boolean lExist = true;
			File lFile = null ; 
			String lFileNameBeforeExt = "";
			String lFileExtName = "";
			
			
			
			int lExtDotIndex = pFileName.lastIndexOf('.');
			
			if ( lExtDotIndex > -1 ) {
				lFileNameBeforeExt = pFileName.substring(0, lExtDotIndex ); 
				lFileExtName = pFileName.substring(lExtDotIndex); 
			} 
			else {
				lFileNameBeforeExt = pFileName;
				lFileExtName = "" ;             
			}
			
			String lNewFileName = pFileName ;
		
			while(lExist){ // 같은 파일이 존재하는가?
				 lFile = new File( pPath + File.separator + lNewFileName ); 
				
				if ( lFile.exists()) {	// 같은 파일이 존재하면
					// 파일 이름에 숫자를 붙인 새로운 파일 이름 생성
					lFileNameCount++;
				
					lNewFileName = lFileNameBeforeExt + "(" + lFileNameCount + ")" + lFileExtName;	
				}else { 
					lExist = false;
				}
			}
			
			return lNewFileName;
		}
		
		@Transactional(rollbackFor=Exception.class)
		public int insertCmnFileUpldMgmtMst(Map<String, Object> pRequestParamMap)  {
			
			return fileUpMapper.insertCmnFileUpldMgmtMst(pRequestParamMap);
		}		
		
}
