package com.ktis.msp.cmn.fileup2.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.cmn.filedown.mapper.FileDownMapper;
import com.ktis.msp.cmn.fileup2.mapper.FileUp2Mapper;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileUp2Service.java
 * 3. Package	: com.ktis.msp.cmn.fileup2.service
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:56:59
 * </PRE>
 */
@Service
public class FileUp2Service {
		protected Logger logger = LogManager.getLogger(getClass());
		@Autowired  
		protected MessageSource messageSource; 

		@Autowired
		private FileDownMapper fileDownMapper;
		
		@Autowired
		private FileUp2Mapper fileUp2Mapper;
		
	//	@Autowired
	//	private MenuAuthMapper menuAuthMapper;
	
	    /** EgovPropertyService */
	    @Resource(name = "propertiesService")
	    protected EgovPropertyService propertyService;
	    
		

		/**
		 * <PRE>
		 * 1. MethodName: fileUpLoad
		 * 2. ClassName	: FileUp2Service
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 9. 25. 오후 3:57:02
		 * </PRE>
		 * 		@return String
		 * 		@param pRequest
		 * 		@param p_fieldName
		 * 		@param p_path
		 * 		@return
		 * @throws FileUploadException 
		 * @throws IOException 
		 * 		@throws Exception
		 */
		public String fileUpLoad(
										HttpServletRequest pRequest, 
										String pFieldName,
										String pPath
								) throws FileUploadException
		{				
	
			String lSaveFileName = "";
			String lPath = pPath;
 	
				
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
	  	    
//				String filename = "";
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
				if ( fieldname.equals(pFieldName))
				{
					//----------------------------------------------------------------
			    	// 파일 이름 구함
			    	//----------------------------------------------------------------
					String lFileNamePc = FilenameUtils.getName(item.getName());
					lSaveFileName = getAlternativeFileName(  lBaseDir + lFilePath   ,  lFileNamePc);
			    	String lTransferTargetFileName = lBaseDir + lFilePath + "/" + lSaveFileName;
			    	
					//----------------------------------------------------------------
			    	// 파일 write
			    	//----------------------------------------------------------------
			    	InputStream filecontent = null;
			    	OutputStream fout = null;
			    	try {
						filecontent = item.getInputStream();
						File f = new File(lTransferTargetFileName);
						
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}
					} catch (IOException e) {
//						20200512 소스코드점검 수정
//				    	e.printStackTrace();
						//System.out.println("Connection Exception occurred");
						//20210722 pmd소스코드수정
						logger.error("Connection Exception occurred");
					} finally {
						if (filecontent != null) {
							try { filecontent.close(); } catch (IOException e) {
								// PMD : catch 문 empty -> e.print추가
//								20200512 소스코드점검 수정
//						    	e.printStackTrace();
								//System.out.println("Connection Exception occurred");
								//20210722 pmd소스코드수정
								logger.error("Connection Exception occurred");
							}
						}
						if (fout != null) {
							try { fout.close(); } catch (IOException e) {
								// PMD : catch 문 empty -> e.print추가
//								20200512 소스코드점검 수정
//						    	e.printStackTrace();
								//System.out.println("Connection Exception occurred");
								//20210722 pmd소스코드수정
								logger.error("Connection Exception occurred");
							}
						}
					}



				}

			}
				

			
			return  lSaveFileName;
	    } 	
	


		/**
		 * <PRE>
		 * 1. MethodName: fileUpLoad
		 * 2. ClassName	: FileUp2Service
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 9. 25. 오후 3:57:02
		 * </PRE>
		 * 		@return String
		 * 		@param pRequest
		 * 		@param p_fieldName
		 * 		@param p_path
		 * 		@return
		 * 		@throws Exception
		 */
		public String fileUpLoadById(
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
		  	    
//				String filename = "";
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
					File f = null;					
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
						if ( fieldname.equals(pFieldName))
						{
							//----------------------------------------------------------------
					    	// 파일 이름 구함
					    	//----------------------------------------------------------------
							String lFileNamePc = FilenameUtils.getName(item.getName());
							lSaveFileName = getAlternativeFileName(  lBaseDir + lFilePath   ,  lFileNamePc);
					    	String lTransferTargetFileName = lBaseDir + lFilePath + "/" + lSaveFileName;
					    	
					    	//----------------------------------------------------------------
					    	// CMN_FILE_MGMT_MST INSERT
					    	//----------------------------------------------------------------
					    	List<?> resultList =  fileUp2Mapper.getFileMgmtMstId(pRequestParamMap);
					    	EgovMap lRow =  (EgovMap) resultList.get(0);
					    	pRequestParamMap.put("fileId", lRow.get("fileId"));
					    	pRequestParamMap.put("moduleId", pPath);
					    	pRequestParamMap.put("fileNm", lSaveFileName);
					    	pRequestParamMap.put("fileNmPc", lFileNamePc);
					    	pRequestParamMap.put("fileRout", "/" + pPath);
					    	pRequestParamMap.put("fileSize", item.getSize());
					    	fileUp2Mapper.insertFileMgmtMst(pRequestParamMap);
							
					    	
							//----------------------------------------------------------------
					    	// 파일 write
					    	//----------------------------------------------------------------
		
							filecontent = item.getInputStream();
							f=new File(lTransferTargetFileName);		
							fout=new FileOutputStream(f);
							byte buf[]=new byte[1024];
							int len;
							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
						}
					}catch (Exception e) {
//						20200512 소스코드점검 수정
//				    	e.printStackTrace();
						//System.out.println("Connection Exception occurred");
						//20210722 pmd소스코드수정
						logger.error("Connection Exception occurred");
					    throw e;
					}finally{
						if(fout != null){
							try {
								fout.close();
							} catch (Exception e) {
								throw new MvnoErrorException(e);
							}
						}
						if (filecontent != null) {
							try {
								filecontent.close();
							} catch (Exception e) {
								throw new MvnoErrorException(e);
							}
						}						
					}
				}
				
			} catch (Exception e)
			{
				throw e;
			}
			
			return  lSaveFileName;
	    } 	
		
		


		/**
		 * <PRE>
		 * 1. MethodName: deleteFileMgmtById
		 * 2. ClassName	: FileUp2Service
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 10. 7. 오후 2:58:17
		 * </PRE>
		 * 		@return boolean
		 * 		@param pRequestParamMap
		 * 		@return
		 */
		//20210722 pmd소스코드수정
		//취약점명 AvoidSynchronizedAtMethodLevel 조치
		@Transactional(rollbackFor=Exception.class)
		public  boolean  deleteFileMgmtById( Map<String, Object> pRequestParamMap)  {
			synchronized(this) {
				List<?> resultList =  fileDownMapper.getFileMgmtById(pRequestParamMap);
		    	
		    	if ( resultList.size() == 0 )
		    	{
		    		return false;
		    	}
		    	
		    	EgovMap lRow = (EgovMap) resultList.get(0);
		    	
	    		fileUp2Mapper.deleteFileMgmtById(pRequestParamMap);

		    	//---------------------------------
		        // 
		    	//---------------------------------
		    	String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
	
		        //--------------------------------
		        // 파일 이름
		        //--------------------------------
		        String lFileName = StringUtils.defaultString((String)lRow.get("fileNm"),"no_file_name");
		        try {
					lFileName =  URLDecoder.decode(lFileName, "UTF-8")   ;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
//					20200512 소스코드점검 수정
//			    	e.printStackTrace();
					//System.out.println("Connection Exception occurred");
					//20210722 pmd소스코드수정
					logger.error("Connection Exception occurred");
				}
	
		        //--------------------------------
		        // 서버의 경로(모율별)
		        //--------------------------------
		        String lFilePath = StringUtils.defaultString((String)lRow.get("fileRout"),"no_path"); 
		        if ( lFilePath.contains(".."))
		        {
		        	return false;
		        }
		        
//		        int    lFileSize = 0; 

		        //--------------------------------
		        // 파일명이 없으면 return
		        //--------------------------------
		        if( lFileName == null ) 
		        {
		        	return false;
		        }
		        
		        String fileNameFullPath = lBaseDir + lFilePath  + "/" +  lFileName;
		        File file = new File(fileNameFullPath);
	
		        //--------------------------------
		        // 파일이 존재하면 down 시작
		        //--------------------------------
		        if (file.exists() && ! file.isDirectory()){
		        	//file.delete();
		        	//소스코드점검 수정 20200513
		        	file.delete();
		        }else{
	
		        	return false;
		        }

	    	return true;
			}

		}
//		public synchronized boolean  deleteFileMgmtById( Map<String, Object> pRequestParamMap)  
//		{
//		    	//---------------------------------
//		        // DB에서 file 정보 가져오기
//		    	//---------------------------------
//		   
//		}
		
		//소스코드점검 수정 20200513
		//Delete 동기화 함수 생성 
//		private synchronized void FileDelete(File file) { 
//				file.delete(); 
//		} 
//		
		/**
		 * <PRE>
		 * 1. MethodName: getAlternativeFileName
		 * 2. ClassName	: FileUp2Service
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 9. 25. 오후 3:57:07
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
				
				if (lFile.exists()) {	// 같은 파일이 존재하면
					// 파일 이름에 숫자를 붙인 새로운 파일 이름 생성
					lFileNameCount++;
				
					lNewFileName = lFileNameBeforeExt + "(" + lFileNameCount + ")" + lFileExtName;	
				}else { 
					lExist = false;
				}
			}
			
			return lNewFileName;
		}
		
		
}
