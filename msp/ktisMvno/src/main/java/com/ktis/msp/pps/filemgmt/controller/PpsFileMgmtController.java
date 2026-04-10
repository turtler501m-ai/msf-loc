package com.ktis.msp.pps.filemgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.pps.filemgmt.service.PpsFileService;
import com.ktis.msp.pps.smsmgmt.service.FileReadService;
import com.ktis.msp.pps.usimmgmt.vo.PpsUsimVo;

import CNPECMJava.CNPEncryptModule.crypto.FileHandle;
import egovframework.rte.fdl.property.EgovPropertyService;
/**
 * @Class Name : FileMgmtController
 * @Description : 파일 관리 프로그램
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.10.31 김웅 
 * @ 
 * @author : 김웅
 * @Create Date : 2014. 10. 22.
 */
@Controller
public class PpsFileMgmtController extends BaseController { 

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    
	
	
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	

	@Autowired
	private PpsFileService ppsFileService;
	
	@Autowired
	private FileReadService fileReadService;
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	/** Constructor */
	public PpsFileMgmtController() {
		setLogPrefix("[FileMgmtController] ");
	}
	

	/**
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 10. 15.
	 */
	
    @SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/pps/filemgmt/fileUpLoad.do")
    public String fileUpUsingService (ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {
    	
    	//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	
		logger.info("===========================================");
		logger.info("======  PpsFileMgmtController.fileUpUsingService-- 이미지 업로드 ======");
		logger.info("===========================================");
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	    logger.info("===========================================");
        
        String contractNum = String.valueOf(pRequestParamMap.get("contractNum"));
        String logInOrgnId = loginInfo.getUserId();
        
		String lSaveFileName = "";
		StringBuilder lFileNamePc = new StringBuilder();

		Integer filesize = 0;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	//String returnMsg = null;
    	String lTransferTargetFileName = "";
		
		try{	
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);
	
	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
	  	    String lBaseDir = propertyService.getString("ppsPath");
			if ( !new File(lBaseDir ).exists())
			{
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}
			
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
				
				
				
				InputStream filecontent = null;
				OutputStream fout = null;

				try
				{
					Date currtentDate = new Date(System.currentTimeMillis());
					
					SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd",Locale.getDefault());
					SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
					StringBuffer dateFolder = new StringBuffer(lBaseDir);
					String dateFileName = sdfFileName.format(currtentDate);
					
					lBaseDir = dateFolder.append(sdf.format(currtentDate)).toString();
					
					//--------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			        //--------------------------------
					File lDir = new File(lBaseDir);
					if ( !lDir.exists())
					{
						
						//logger.debug("file can write... : " , lDir.canWrite());
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
						lFileNamePc = new StringBuilder();
						//lFileNamePc.append(FilenameUtils.getName(item.getName()));
						
						String orginFileName = FilenameUtils.getName(item.getName());
						int extIndex = orginFileName.lastIndexOf(".");
				
						String fileExt = orginFileName.substring(extIndex+1);
						//lFileNamePc.append(orginFileName);
						if(fileExt != null && !"".equals(fileExt))
						{
							fileExt = fileExt.toLowerCase();
						}
						
						lFileNamePc.append(contractNum);
						lFileNamePc.append("_");
						lFileNamePc.append(dateFileName);
						lFileNamePc.append(logInOrgnId);
						lFileNamePc.append(".");
						lFileNamePc.append(fileExt);
						
						if(!"jpg".equals(fileExt) && !"gif".equals(fileExt) && !"bmp".equals(fileExt)
								&& !"tiff".equals(fileExt) && !"jpeg".equals(fileExt) && !"pdf".equals(fileExt)
								&& !"png".equals(fileExt) && !"tif".equals(fileExt) )
						{
							resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
							resultMap.put("name", generationUUID().replaceAll("-",""));
				    		resultMap.put("state", true);
				    		resultMap.put("size", "" + 111);
				    		
				    		model.addAttribute("result", resultMap);
				    		return "jsonViewArray";
						}
						
						
						
						//item.setFieldName(String.valueOf(lFileNamePc));
						
				    	lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  String.valueOf(lFileNamePc));
				    	lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;
				    	
						//6----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------
				    	filecontent = item.getInputStream();
						File f=new File(lTransferTargetFileName);
	
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}
						
					}
					
					resultMap.put("name", generationUUID().replaceAll("-",""));
					resultMap.put("state", true);
					resultMap.put("size", "" + 111);
					
				}catch (Exception e) {
					//e.printStackTrace();
					
					resultMap.put("name", generationUUID().replaceAll("-",""));
					resultMap.put("state", true);
					resultMap.put("size", "" + 111);
					throw new MvnoErrorException(e);
				}
				finally {
					if (filecontent != null) {
						try 
						{ 
							filecontent.close(); 
						} 
						catch (IOException e) 
						{
							//logger.error(e);
							throw new MvnoErrorException(e);
						}
					}
					if (fout != null) {
						try 
						{ 
							fout.close(); 
						}
						catch (IOException e) 
						{
							//logger.error(e);
							throw new MvnoErrorException(e);
						}
					}
				}
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
    	} catch (Exception e) {
    		resultMap.put("name", generationUUID().replaceAll("-",""));
    		resultMap.put("state", true);
    		resultMap.put("size", "" + 111);
    		
    		model.addAttribute("result", resultMap);
    		
    		return "jsonView";
    	}
    	
		
			pRequestParamMap.put("opCode", "EDT");
			pRequestParamMap.put("contractNum", contractNum);
			pRequestParamMap.put("paperImage", lTransferTargetFileName);
		
		try {
			resultMap = ppsFileService.ppsProcImageUploadAndDelete(pRequestParamMap);
			if(resultMap != null)
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("name", generationUUID().replaceAll("-",""));
				resultMap.put("state", true);
				resultMap.put("size", "" + 111);
				
			}
			else
			{
				resultMap = new HashMap<String, Object>();
				resultMap.put("name", generationUUID().replaceAll("-",""));
				resultMap.put("state", true);
				resultMap.put("size", "" + 111);
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				//resultMap.put("msg", returnMsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//logger.error(generateLogMsg(String.format(" 이미지업로드오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
         	resultMap.clear();
         	
         	resultMap.put("name", generationUUID().replaceAll("-",""));
    		resultMap.put("state", true);
    		resultMap.put("size", "" + 111);
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		
		
		resultMap.put("name", generationUUID().replaceAll("-",""));
		resultMap.put("state", true);
		resultMap.put("size", "" + 111);
		
		model.addAttribute("result", resultMap);
        logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
        return "jsonView";
    }

	
    

	/**
	 * @Description : UUID 생성
	 * @Param  : 
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
    public static String generationUUID(){
    	  return UUID.randomUUID().toString();
    	 }


    /**
     * @Description : 파일 다운로드 기능
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/pps/filemgmt/downFile.json")
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
        	
        	String strFileName = pReqParamMap.get("path").toString();
        	
            file = new File(strFileName);
            
            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());
            
            String encodingFileName = "";

    		int excelPathLen2 = Integer.parseInt(propertyService.getString("ppsPathLen2"));
    		
            try {
              encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }
            logger.debug(strFileName);
            logger.debug(encodingFileName);
            response.setHeader("Cache-Control", "");
            response.setHeader("Pragma", "");

            response.setContentType("Content-type:application/octet-stream;");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);
            
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
 			resultMap.put("msg", "");
 			throw new MvnoErrorException(e);
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	        	  throw new MvnoErrorException(e);
	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
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
     * @Description : 파일 삭제 기능
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/pps/filemgmt/deleteFile.json")
    public String deleteFile(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap){
    	
    	//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	
		logger.info("===========================================");
		logger.info("======  PpsFileMgmtController.deleteFile-- 이미지 삭제 ======");
		logger.info("===========================================");
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	    logger.info("===========================================");
        
        String contractNum = String.valueOf(pRequestParamMap.get("contractNum"));
        String paperImage = String.valueOf(pRequestParamMap.get("paperImage"));
        //String logInOrgnId = loginInfo.getUserOrgnId();
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
    	
        
        try {
        	// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
        	
			File file = null;
			file = new File(paperImage);
			
			file.delete();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e1.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
			resultMap = new HashMap<String, Object>();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		}
		pRequestParamMap.put("opCode", "DEL");
		pRequestParamMap.put("contractNum", contractNum);
		pRequestParamMap.put("paperImage", paperImage);
		
		try {
			resultMap = ppsFileService.ppsProcImageUploadAndDelete(pRequestParamMap);
			if(resultMap != null)
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				
			}
			else
			{
				resultMap = new HashMap<String, Object>();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				//resultMap.put("msg", returnMsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
         	resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
			resultMap = new HashMap<String, Object>();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		}
		
		model.addAttribute("result", resultMap);
        logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	    return "jsonView";
    }
    
    /**
     * @Description : 파일 삭제 기능 2
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/pps/filemgmt/deleteFile2.json")
    public String deleteFile2( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 삭제 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);
        
        //File file = null;
        String returnMsg = null;
        
        try {
        	// 본사 권한
	    	if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
            
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "삭제성공");
 			
 		} catch (Exception e) {
 			//logger.error(e.getMessage());
 			
 			resultMap.put("fileTotCnt", "0");
            resultMap.put("deleteCnt", "0");
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", returnMsg);
 			
 			throw new MvnoErrorException(e);
	    }
 		//----------------------------------------------------------------
 		// return json 
 		//----------------------------------------------------------------	
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }
    
    
    @RequestMapping("/pps/filemgmt/ppsFileUpLoad.do")
    public String fileUpService (ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {
    	
    	//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());

		logger.info("===========================================");
		logger.info("======  PpsFileMgmtController.fileUpUsingService-- 이미지 업로드 ======");
		logger.info("===========================================");
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	    logger.info("===========================================");
        
    	
    	
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	try{
    		// 본사, 대리점 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD")))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
    		
    		resultMap = ppsFileService.fileUpload(pRequest, "file_upload1", "PPS",pRequestParamMap);
    		
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
    
    
    @RequestMapping("/pps/filemgmt/ppsFileEncUpLoad.do")
    public String fileUpEncService (ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {
    	
    	//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());

		logger.info("===========================================");
		logger.info("======  PpsFileMgmtController.fileUpUsingService-- 이미지 업로드 ======");
		logger.info("===========================================");
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	    logger.info("===========================================");
        
	    

    	
    	
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	try{
    		
    		// 본사, 대리점 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD")))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		resultMap = ppsFileService.fileUpload(pRequest, "file_upload1", "PPS",pRequestParamMap);
    		
    		
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
		     // e.printStackTrace();
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
    
    
    
    @RequestMapping(value = "/pps/filemgmt/procPpsCustomerImgeInsertUpdate.json" )
	public String procPpsCustomerImgeInsertUpdate( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{

	
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		
		
		
		logger.info("  PpsFileMgmtController.procPpsCustomerImgeInsertUpdate -- =====");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

	// ----------------------------------------------------------------
	// 목록 db select
	// ----------------------------------------------------------------
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	
	
	
	try {
		
		// 본사 권한
		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD")))){
    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    	}
		
		resultMap = ppsFileService.procPpsCustomerImgeInsertUpdate(pRequestParamMap);
		
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
    
    @RequestMapping(value = "/pps/filemgmt/procPpsAgecyCustomerImgeInsertUpdate.json" )
	public String procPpsAgencyCustomerImgeInsertUpdate( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{

	
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		
		
		
		logger.info("  PpsFileMgmtController.procPpsAgencyCustomerImgeInsertUpdate -- =====");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		
		
		try {
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD")))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			resultMap = ppsFileService.fileUpload(pRequest, "file_upload1", "PPS",pRequestParamMap);
			logger.info("resultMap==111==="+resultMap.toString());
			
			 // 암호화 
		    try {
		    	
		    	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
		    	
		    	logger.info("(String) resultMap.get(imgFile)====="+(String) resultMap.get("imgFile"));	
		    	
			    if(!"".equals((String) resultMap.get("imgFile")) || (String) resultMap.get("imgFile") != null){
			    	
				    String fileNameSub[] = 	((String) resultMap.get("imgFile")).split("\\.");
				    
				    if(fileNameSub[0] != null){
				    
				    	StringBuffer fileName = new StringBuffer();
				    	String strFileName = fileNameSub[0] + "_" + pRequestParamMap.get("imgIdx") + "_e." + fileNameSub[1];
				    	fileName.append(strFileName);
				    	StringBuffer retUrl = new StringBuffer();
				    	String strRetUrl = resultMap.get("baseDir") +"/" + resultMap.get("imgFile");
				    	retUrl.append(strRetUrl);
				    	StringBuffer encUrl = new StringBuffer();
				    	String strEncUrl = resultMap.get("baseDir") +"/" + fileName.toString();
				    	encUrl.append(strEncUrl);
				    	
					    //암호화된 파일로 변경
					    fileHandle.Encrypt(retUrl.toString(), encUrl.toString());
					    
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
								file = new File(retUrl.toString());
								file.delete();
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
	//							20200512 소스코드점검 수정
	//					    	e1.printStackTrace();
//								20210706 소스코드점검 수정
//								System.out.println("Connection Exception occurred");
								logger.error("Connection Exception occurred");
								resultMap = new HashMap<String, Object>();
								resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
							} 
					        
					        /*
					        //암호화된 정보 SET
						    resultMap.put("imgFile", fileName);
						    resultMap.put("imgPath", encUrl);	
						    resultMap.put("name", encUrl);	
						    */
						    
						    pRequestParamMap.put("file_upload1_s_0", encUrl);
						    pRequestParamMap.put("memo", URLDecoder.decode(pRequestParamMap.get("memo").toString(), "UTF-8"));
						    
						    resultMap = ppsFileService.procPpsCustomerImgeInsertUpdate(pRequestParamMap);
						    
						    if("0000".equals(resultMap.get("oRetCode"))){
						    	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
								resultMap.put("msg", "");
								resultMap.put("state", true);
					    		resultMap.put("size", "" + 111);
						    }else{
						    	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
								resultMap.put("msg", "");
								resultMap.put("state", false);
					    		resultMap.put("size", "" + 111);
						    }
							
						    logger.info("resultMap==222==="+resultMap.toString());
						    
				    }
				       
			    }
		    
		    } catch (Exception e) {
		      // TODO Auto-generated catch block
		     // e.printStackTrace();
		    	throw new MvnoErrorException(e);
		    }
			
		} catch (Exception e) {
			resultMap.clear();
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		} catch (Throwable e) {
			throw new MvnoErrorException(e);
		}finally{
	//		sqlService.Close();
			dummyFinally();
		}
	
		model.addAttribute("result", resultMap);
 		return "jsonViewArray";
	}

    
    /**
     * @Description : 파일 삭제 기능
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/pps/filemgmt/ppsDeleteFile.json")
    public String ppsDeleteFile(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap){
    	
    	//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		
    	
    	
		logger.info("===========================================");
		logger.info("======  PpsFileMgmtController.deleteFile-- 이미지 삭제 ======");
		logger.info("===========================================");
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	    logger.info("===========================================");
        
       
        String paperImage = String.valueOf(pRequestParamMap.get("imgPath"));
        //String logInOrgnId = loginInfo.getUserOrgnId();
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
    	
        
        try {
        	// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
        	
			File file = null;
			file = new File(paperImage);
			
			file.delete();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e1.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
			resultMap = new HashMap<String, Object>();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		}
		
		
		try {
			resultMap = ppsFileService.procPpsCustomerImgeDelete(pRequestParamMap);
			if(resultMap != null)
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				
			}
			else
			{
				resultMap = new HashMap<String, Object>();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				//resultMap.put("msg", returnMsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//logger.error(generateLogMsg(String.format(" 이미지업로드오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
         	resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
			resultMap = new HashMap<String, Object>();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		}
		
		model.addAttribute("result", resultMap);
        logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	    return "jsonView";
    }
    
    /**
     * @Description : 회원증빙
     * @Param  : 
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping(value = "/pps/filemgmt/procPpsRealPayInfoUpdate.json" )
	public String procPpsRealPayInfoUpdate( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{

	
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		
		
		
		logger.info("  PpsFileMgmtController.procPpsCustomerImgeInsertUpdate -- =====");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

	// ----------------------------------------------------------------
	// 목록 db select
	// ----------------------------------------------------------------
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	
	
	
	try {
		// 본사 권한
    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    	}
		
		resultMap = ppsFileService.procPpsRealPayInfoUpdate(pRequestParamMap);
		
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
     * @Description : 대리점 회원증빙
     * @Param  : 
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping(value = "/pps/filemgmt/procAgencyPpsAgentCmsReq.json" )
	public String procAgencyPpsAgentCmsReq( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{
	
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());
	    pRequestParamMap.put("agentId", loginInfo.getUserOrgnId());
		
		logger.info("  PpsFileMgmtController.procAgencyPpsAgentCmsReq -- =====");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 대리점 권한
			if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			resultMap = ppsFileService.procAgencyPpsAgentCmsReq(pRequestParamMap);
			
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
     * @Description : 파일 체크 기능
     * @Param  : 
     * @Return : String
     * @Author : 
     * @Create Date : 2016. 4. 8.
     */
    
	@RequestMapping(value = "/pps/filemgmt/ppsCheckFile.json" )
	public String ppsCheckFile( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsUsimInfoVo")PpsUsimVo searchUsimInfoVo,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{

		//----------------------------------------------------------------
   	// Login check
   	//----------------------------------------------------------------
   	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
   	loginInfo.putSessionToParameterMap(pRequestParamMap);
   	

		logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		 String filePath = String.valueOf(pRequestParamMap.get("filePath"));

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	String scanSearchUrl =  propertyService.getString("scan.search.url");
	    	resultMap.put("scanSearchUrl", scanSearchUrl);
	    	
			File file = null;
			file = new File(filePath);
			
		    // 파일 존재 여부 판단
		    if (file.isFile()) {
		    	
		    	resultMap.put("retCode", "0000");
		    	resultMap.put("retMsg", "파일 존재합니다.");
		      
		      
		    }
		    else {
		    	resultMap.put("retCode", "9999");
		    	resultMap.put("retMsg", "파일이 존재하지 않습니다.");
		    }

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		    
		} catch (Exception e) {
	    	resultMap.put("retCode", "9999");
	    	resultMap.put("retMsg", "파일이 존재하지 않습니다.");
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}
		model.addAttribute("result", resultMap);
		//logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		return "jsonView";
	}
    
    
    /*
    @RequestMapping("/pps/filemgmt/ppsCheckFile.json")
    public String checkFile(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap){
        
    	
		//----------------------------------------------------------------
       	// Login check
       	//----------------------------------------------------------------
       	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
       	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
        String filePath = String.valueOf(pRequestParamMap.get("filePath"));
        
        
        logger.debug(">>>>>>>>>>>>>> pRequestParamMap:" + pRequestParamMap);
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
			File file = null;
			file = new File(filePath);
			
		    // 파일 존재 여부 판단
		    if (file.isFile()) {
		    	
		    	resultMap.put("code", "0000");
		    	resultMap.put("msg", "파일 존재합니다.");
		      
		      
		    }
		    else {
		    	resultMap.put("code", "9999");
		    	resultMap.put("msg", "파일이 존재하지 않습니다.");
		    }
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			resultMap = new HashMap<String, Object>();
	    	resultMap.put("code", "9999");
	    	resultMap.put("msg", "파일이 존재하지 않습니다.");
		}
		
		model.addAttribute("result", resultMap);
        logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	    return "jsonView";
    }
    */
    
    

  }
