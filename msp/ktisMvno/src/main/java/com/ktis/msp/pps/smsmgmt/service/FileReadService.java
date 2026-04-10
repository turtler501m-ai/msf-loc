package com.ktis.msp.pps.smsmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;

import egovframework.rte.fdl.property.EgovPropertyService;


@Service
public class FileReadService {

		@Autowired  
		protected MessageSource messageSource; 
	    /** EgovPropertyService */
	    @Resource(name = "propertiesService")
	    protected EgovPropertyService propertyService;
	    
	    protected static Logger logger = LogManager.getLogger(FileReadService.class);
	    
		//소스코드점검 수정 20200513
		//Delete 동기화 함수 생성 
//		private static synchronized void FileDelete(File file) { 
//				file.delete(); 
//		} 
	    
  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	    // upload log까지 남기지 않는 file upload
  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public String fileUpLoad(
										HttpServletRequest pRequest, 
										String pfieldName,
										String pPath
								) throws MvnoRunException
		{				
	
			String lSaveFileName = "";
			Integer filesize = 0;
	    	InputStream filecontent = null;
			File f= null;
			OutputStream fout= null;
			String locPath =pPath;
			try {    	
				ServletFileUpload sServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
				List<FileItem> items = sServletFileUpload.parseRequest(pRequest);
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		        // upload base directory를 구함
		  	    // 존재하지 않으면 exception 발생
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		  	    String sBaseDir = propertyService.getString("fileUploadBaseDirectory");
		  	    //String l_base_dir = propertyService.getString("fileUploadBaseDirectory");
				if ( !new File(sBaseDir ).exists())	{
					throw new MvnoRunException(-1,messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  
							new Object[] { sBaseDir }  , Locale.getDefault()));
				}
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		        // 모듈별 경로에 앞뒤로 "/" 가 붙거나 붙지않은 경우 check하여 "/path" 형태로 변경함
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		        if ( locPath.contains(".."))  {
		        		return null;
		        }
		        
		        locPath = locPath.replaceAll("^/", "");
		        locPath = locPath.replaceAll("/$", "");
		  	    String sFilePath      = "/" + locPath; 
		  	    
				for (FileItem item : items) {
					
			  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			        // 파일 타입이 아니면 skip
			  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					if (item.isFormField()) 
						continue;
			  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
			  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
						continue;

					try	{
				  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				        // 모듈별 directory가 존재하지 않으면 생성
				  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
						File sDir = new File(sBaseDir + sFilePath);
						if ( !sDir.exists()){
							sDir.mkdirs();
						}
						
						//----------------------------------------------------------------
				    	// upload 이름 구함
				    	//----------------------------------------------------------------
						String fieldname = item.getFieldName();
						if ( fieldname.equals("file")){
							//----------------------------------------------------------------
					    	// 파일 이름 구함
					    	//----------------------------------------------------------------
							String sfileNamePc = FilenameUtils.getName(item.getName());
							lSaveFileName = getAlternativeFileName(  sBaseDir + sFilePath   ,  sfileNamePc);
					    	String sTransferTargetFileName = sBaseDir + sFilePath + "/" + lSaveFileName;
							//----------------------------------------------------------------
					    	// 파일 write
					    	//----------------------------------------------------------------
					    	 filecontent = item.getInputStream();
					    	 
							 f=new File(sTransferTargetFileName);

							fout=new FileOutputStream(f);
							byte buf[]=new byte[1024];
							int len;
							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
//							fout.close();
						}
					}catch (Exception e) {
//						20200512 소스코드점검 수정
//				    	e.printStackTrace();
						//System.out.println("Connection Exception occurred");
						//20210722 pmd소스코드수정
						logger.error("Connection Exception occurred");
					    throw e;
					}
					finally {
						if (filecontent != null) {
							try { filecontent.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage());}
						}
						if (fout != null) {
							try { fout.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage());}
						}
					}
				}
				
			} catch (Exception e)
			{
//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
				//System.out.println("Connection Exception occurred");
				//20210722 pmd소스코드수정
				logger.error("Connection Exception occurred");
				 throw new MvnoRunException(-1,e.getMessage());
			}

			
			return  lSaveFileName;
	    } 	
	
		//20210722 pmd소스코드수정
		public static String getAlternativeFileName(String ppath, String pfileName){
			synchronized(FileReadService.class) {
				boolean lexist = true;
				File lfile = null ; 
				String lfileNameBeforeExt = "";
				String lfileExtName = "";
				
				logger.info(lfileNameBeforeExt);
				logger.info(lfileExtName);
				
				int lextDotIndex = pfileName.lastIndexOf('.');
				
				if ( lextDotIndex > -1 ) {
					lfileNameBeforeExt = pfileName.substring(0, lextDotIndex ); 
					lfileExtName = pfileName.substring(lextDotIndex); 
				} 
				else {
					lfileNameBeforeExt = pfileName;
					lfileExtName = "" ;             
				}
				String lnewFileName = pfileName ;
				while(lexist){ // 같은 파일이 존재하는가?
					lfile = new File( ppath + File.separator + lnewFileName ); 
					
					if (lfile.exists()) {	// 같은 파일이 존재하면
						// 파일삭제
						//lfileNameCount++;
						//lnewFileName = lfileNameBeforeExt + "(" + lfileNameCount + ")" + lfileExtName;
						lfile.delete();
						//FileDelete(lfile);
						lexist = false;
					}else { 
						lexist = false;
					}
				}
				
				return lnewFileName;
			}
			}

//		public synchronized static String getAlternativeFileName(String ppath, String pfileName)
//		{
			
			
}
