package com.ktis.msp.pps.filemgmt.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import CNPECMJava.CNPEncryptModule.crypto.FileHandle;

import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.filemgmt.mapper.PpsFileMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class PpsFileService extends ExcelAwareService  {
	
	@Autowired
	private PpsFileMapper ppsFileMapper;
	
    @Autowired
    protected EgovPropertyService propertyService;
    
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
    
	/**
	 * 현재시간 String
	 * @return
	 */
	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}	
    
	
	/**
	 * 고객 이미지등록
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
    @Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsProcImageUploadAndDelete(Map<String, Object> pRequestParamMap) {

		ppsFileMapper.ppsProcImageUploadAndDelete(pRequestParamMap);
		
		return pRequestParamMap;
	}
    
    /**
     * 서식지 목록조회
     * @param pRequestParamMap
     * @return
     */
    public List<?> getPpsCustomerImageInfoList(Map<String, Object> pRequestParamMap) {
    	
    	List<EgovMap> resultList = (List<EgovMap>)ppsFileMapper.getPpsCustomerImageInfoList(pRequestParamMap);
    	for(EgovMap map : resultList){
			if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
				String contractNum =  map.get("contractNum").toString().trim();
				if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
				{
					StringBuffer sb = new StringBuffer();
					String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
					sb.append(contractNum).append(tmp);
					map.put("contractNumStr",sb.toString());
				}
			}
			
			if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
				String contractNum =  map.get("contractNum").toString().trim();
				String imgFile =  map.get("imgFile").toString().trim();
				String imgSeq =  map.get("imgSeq").toString().trim();
				if(!StringUtils.isBlank(imgFile) && !imgFile.equals(""))
				{
					StringBuffer sb = new StringBuffer();
			    	String tmp = "^javaScript:userPaperImageView("+contractNum+","+imgSeq+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
					sb.append(imgFile).append(tmp);
					map.put("imgFileStr",sb.toString());
				}
			}
			
			if(map.get("status")!=null && !map.get("status").equals("") ){
				String status =  map.get("status").toString().trim();
				String imgSeq =  map.get("imgSeq").toString().trim();
				if(!StringUtils.isBlank(status) && !status.equals("D"))
				{
					StringBuffer sb = new StringBuffer();
					String tmp = "^javaScript:goDeleteFileData("+imgSeq+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
					sb.append("삭제").append(tmp);
					map.put("delBtn",sb.toString());
				}
			}
		}
    	
    	return resultList;
    }
    
    public void getPpsCustomerImageInfoListExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","가입자상태","서식지종류","파일명", "개통일자","등록일자","메모","처리자","상태");
		param.setStrValue("CONTRACT_NUM", "SUB_STATUS_NM","IMG_GUBUN_NM","IMG_FILE","ENTER_DATE","REC_DT","MEMO","REC_ID_NM","STATUS_NM");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000, 5000,5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("서식지관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.filemgmt.mapper.PpsFileMapper.getPpsCustomerImageInfoListExcel", pRequestParamMap, param, handler);
		String fileName = "서식지관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
		//=======파일다운로드사유 로그 START==========================================================
        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
           
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
            
            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
            
            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
        }
        //=======파일다운로드사유 로그 END==========================================================
        
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
		} catch (UnsupportedEncodingException e) {

//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
		
	}
    
  /**
   * 서식지 목록조회 탭조회
   * @param pRequestParamMap
   * @return
   */
  public List<?> getPpsCustomerImageInfoTabList(Map<String, Object> pRequestParamMap) {
    	
    	List<EgovMap> resultList = (List<EgovMap>)ppsFileMapper.getPpsCustomerImageInfoTabList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("recIdNm","CUST_NAME");

		maskingService.setMask(resultList, maskFields, pRequestParamMap);		
    	for(EgovMap map : resultList){			
			
			if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
				String contractNum =  map.get("contractNum").toString().trim();
				String imgFile =  map.get("imgFile").toString().trim();
				String imgSeq =  map.get("imgSeq").toString().trim();
				if(!StringUtils.isBlank(imgFile) && !imgFile.equals(""))
				{
					StringBuffer sb = new StringBuffer();
					String tmp = "^javaScript:userPaperImageView("+contractNum+","+imgSeq+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
					sb.append(imgFile).append(tmp);
					map.put("imgFileStr",sb.toString());
				}
			}
			
			if(map.get("status")!=null && !map.get("status").equals("") ){
				String status =  map.get("status").toString().trim();
				String imgSeq =  map.get("imgSeq").toString().trim();
				if(!StringUtils.isBlank(status) && !status.equals("D"))
				{
					StringBuffer sb = new StringBuffer();
			    	String tmp = "^javaScript:goDeleteFileData("+imgSeq+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
					sb.append("삭제").append(tmp);
					map.put("delBtn",sb.toString());
				}
			}
		}
    	
    	return resultList;
    }
  	
  
  public Map<String, Object>  fileUpload(
			HttpServletRequest pRequest, 
			String pFieldName,
			String pPath,
			Map<String, Object> pRequestParamMap
	) throws Throwable 
{		

		String lSaveFileName = "";
//		String filename = "";
		Integer filesize = 0;
  	InputStream filecontent = null;
		File f= null;
		OutputStream fout= null;
		String locPath =pPath;
		try
	    {
			
			ServletFileUpload sServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = sServletFileUpload.parseRequest(pRequest);
		    
	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
	  	    String lBaseDir = propertyService.getString("ppsPath");
			if ( !new File(lBaseDir ).exists())
			{
//				throw new Exception(messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
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
	  	    
	  	    StringBuffer tmplBaseDir = new StringBuffer();
	  	    
	  	    tmplBaseDir.append(lBaseDir);
	  	  	tmplBaseDir.append(sFilePath);
	  	
	  	    lBaseDir = tmplBaseDir.toString();
	  	     
	  	     
	  	    for (FileItem item : items) {
				
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		        // 파일 타입이 아니면 skip
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				if (item.isFormField()) 
					continue;
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				long limitSize = 209715200; //200메가로 제한
				
				if ( item.getSize() == 0 ||  item.getSize()  >  limitSize )
					continue;

				try	{
			  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					Date currtentDate = new Date(System.currentTimeMillis());
					
					SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd",Locale.getDefault());
					SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
					StringBuffer dateFolder = new StringBuffer(lBaseDir);
					String dateFileName = sdfFileName.format(currtentDate);
					String contractNum = String.valueOf(pRequestParamMap.get("contractNum"));
					String adminId= String.valueOf(pRequestParamMap.get("adminId"));
					lBaseDir = dateFolder.append(sdf.format(currtentDate)).toString();
					
					File sDir = new File(lBaseDir);
					if ( !sDir.exists())
					{
						
						//logger.debug("file can write... : " , lDir.canWrite());
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
						
						String ext = FilenameUtils.getExtension(item.getName());
						StringBuffer sb  = new StringBuffer();
						String sfileNamePc = sb.append(contractNum).append("_").append(dateFileName).append(adminId).append(".").append(ext).toString();	
						

						String sUrl =lBaseDir +"/";
						
						
						
 						lSaveFileName = getAlternativeFileName(  sUrl   ,  sfileNamePc);
						
						
						
				    	String sTransferTargetFileName = lBaseDir + "/" + lSaveFileName;
				    	pRequestParamMap.put("imgFile",lSaveFileName);
						pRequestParamMap.put("imgPath",sTransferTargetFileName);
						pRequestParamMap.put("name", sTransferTargetFileName);
						pRequestParamMap.put("baseDir", lBaseDir);
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
//						fout.close();
					}
				}catch (Exception e) {
//					20200512 소스코드점검 수정
//			    	e.printStackTrace();
//					20210706 소스코드점검 수정
//					System.out.println("Connection Exception occurred");
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
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
			 throw new MvnoRunException(-1,e.getMessage());
		}  	    	
		
		
		pRequestParamMap.put("state", true);
		pRequestParamMap.put("size", "" + 111);
		pRequestParamMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		pRequestParamMap.put("msg", "");
		
	  return pRequestParamMap;
}
  
  
  /**
   * 서식지등록처리 
   * @param pRequestParamMap
   * @return
   */
  @Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsCustomerImgeInsertUpdate(Map<String, Object> pRequestParamMap) {

		pRequestParamMap.put("oRetCode","");
		pRequestParamMap.put("oRetMsg","");
		pRequestParamMap.put("status", "A");
		
		/*
		String imgFile = pRequestParamMap.get("file_upload1_r_0").toString();
		if(!StringUtils.isBlank(imgFile)){
			pRequestParamMap.put("imgFile",imgFile);
		}
		*/
		
		String imgUrl = pRequestParamMap.get("file_upload1_s_0").toString();
		if(!StringUtils.isBlank(imgUrl)){
			pRequestParamMap.put("imgPath",imgUrl);
			
			String[] imgUrlArr = imgUrl.split("\\/");
			String imgFile = imgUrlArr[imgUrlArr.length-1];
			pRequestParamMap.put("imgFile",imgFile);
		}
		
		ppsFileMapper.procPpsCustomerImgeInsertUpdate(pRequestParamMap);
		
		return pRequestParamMap;
	}
  
  
  /**
   * 서식지삭제처리 
   * @param pRequestParamMap
   * @return
   */
  @Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsCustomerImgeDelete(Map<String, Object> pRequestParamMap) {

	  	pRequestParamMap.put("oRetCode","");
		pRequestParamMap.put("oRetMsg","");
		//pRequestParamMap.put("status", "D");
		pRequestParamMap.put("memo", "");
			
		
		ppsFileMapper.procPpsCustomerImgeInsertUpdate(pRequestParamMap);
		
		return pRequestParamMap;
	}
  
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
	    String lBaseDir = propertyService.getString("ppsPath");


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
	 * 회원증빙
	 * @param pRequestParamMap
	 * @return
	 */
	/* 2018-11-10, PMD 수정 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsRealPayInfoUpdate(Map<String, Object> pRequestParamMap) throws IOException {

		pRequestParamMap.put("oRetCode","");
		pRequestParamMap.put("oRetMsg","");
		pRequestParamMap.put("payMethod","R");
		
//			String imgFile = pRequestParamMap.get("file_upload1_r_0").toString();
		String imgUrl = pRequestParamMap.get("file_upload1_s_0").toString();
		//확장자를 구한다.
		String[] imgUrlArr = imgUrl.split("\\.");
		String imgUrlExt = imgUrlArr[imgUrlArr.length-1];
		pRequestParamMap.put("fileType",imgUrlExt);
		//파일을 base64로 암호화
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream (new FileInputStream(imgUrl));
			byte [] buff = new byte[bis.available()];
            bis.read(buff);
            String fileEnc =Base64.encodeBase64String(buff);
            pRequestParamMap.put("fileEnc", fileEnc);
		} catch(Exception e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		} finally {
			if(bis != null){
	            bis.close();
			}
		}
		
	    //파일용량을 구한다
	    File f = new File(imgUrl);
	    String fileLen = String.valueOf(f.length());
	    pRequestParamMap.put("fileLen", fileLen);
	    
	    //파일 암호화
	    CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
	    String encUrl = imgUrlArr[0] + "_e." + imgUrlArr[imgUrlArr.length-1] ;	
    	
//		    if(!"".equals((String) imgUrl) || (String) imgUrl != null){
//
//			    if(imgUrlArr[0] != null){
//				    
//				    
//				    //암호화된 파일로 변경
//				    fileHandle.Encrypt(imgUrl, encUrl);
//				    
//				    //2초 delay
///*					       long saveTime = System.currentTimeMillis();
//				       long currTime = 0;
//				       int delayTime = 100;
//		
//				       while( currTime - saveTime < delayTime){
//				            currTime = System.currentTimeMillis();
//				        }*/
//				       
//				       //암호화되기전 기존 파일삭제
//				        try {
//							File file = null;
//							file = new File(imgUrl);
//							file.delete();
//							
//						} catch (Exception e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//							pRequestParamMap = new HashMap<String, Object>();
//							pRequestParamMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
//						} 
//			    }
//			       
//		    }
	    
	    if((!"".equals((String) imgUrl) || (String) imgUrl != null) && (imgUrlArr[0] != null)){
    		
    		//암호화되기전 기존 파일삭제
    		try {
    			//암호화된 파일로 변경
    			fileHandle.Encrypt(imgUrl, encUrl);
    			
    			File file = null;
    			file = new File(imgUrl);
    			file.delete();
    			
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
//    			20200512 소스코드점검 수정
//    	    	e1.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
//	    			pRequestParamMap = new HashMap<String, Object>();
    			pRequestParamMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
    		} 
    	}
	    
	    
	    pRequestParamMap.put("filePath", encUrl);
	    //pRequestParamMap.put("filePath", imgUrl);
		ppsFileMapper.procPpsRealPayInfoUpdate(pRequestParamMap);
		return pRequestParamMap;
	}
	
	/**
	 * 회원증빙
	 * @param pRequestParamMap
	 * @return
	 */
	/* 2018-11-10, PMD 수정 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procAgencyPpsAgentCmsReq(Map<String, Object> pRequestParamMap) throws IOException {

//			String imgFile = pRequestParamMap.get("file_upload1_r_0").toString();
		String imgUrl = pRequestParamMap.get("filePath").toString();
		//확장자를 구한다.
		String[] imgUrlArr = imgUrl.split("\\.");
		String imgUrlExt = imgUrlArr[imgUrlArr.length-1];
		pRequestParamMap.put("fileType",imgUrlExt);
		//파일을 base64로 암호화
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream (new FileInputStream(imgUrl));
			byte [] buff = new byte[bis.available()];
            bis.read(buff);
            String fileEnc =Base64.encodeBase64String(buff);
            pRequestParamMap.put("fileEnc", fileEnc);
		} catch(Exception e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		} finally {
			if(bis != null){
	            bis.close();
			}
		}
		
	    //파일용량을 구한다
	    File f = new File(imgUrl);
	    String fileLen = String.valueOf(f.length());
	    pRequestParamMap.put("fileLen", fileLen);
	    
	    //파일 암호화
	    CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
	    String encUrl = imgUrlArr[0] + "_e." + imgUrlArr[imgUrlArr.length-1] ;	
    
	    if((!"".equals((String) imgUrl) || (String) imgUrl != null) && (imgUrlArr[0] != null)){
    		
    		//암호화되기전 기존 파일삭제
    		try {
    			//암호화된 파일로 변경
    			fileHandle.Encrypt(imgUrl, encUrl);
    			
    			File file = null;
    			file = new File(imgUrl);
    			file.delete();
    			
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
//    			20200512 소스코드점검 수정
//    	    	e1.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
//	    			pRequestParamMap = new HashMap<String, Object>();
    			pRequestParamMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
    		} 
    	}
	    
	    
	    pRequestParamMap.put("filePath", encUrl);
	    //pRequestParamMap.put("filePath", imgUrl);
		ppsFileMapper.procAgencyPpsAgentCmsReq(pRequestParamMap);
		return pRequestParamMap;
	}
    
	
}
