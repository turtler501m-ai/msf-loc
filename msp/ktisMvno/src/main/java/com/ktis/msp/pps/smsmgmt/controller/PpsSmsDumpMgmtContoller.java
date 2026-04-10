package com.ktis.msp.pps.smsmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.board.service.BoardService;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.pps.smsmgmt.service.FileReadService;
import com.ktis.msp.pps.smsmgmt.service.PpsSmsDumpMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;
/**
 * @Class Name : PpsHdofcSttcMgmtContoller.java
 * @Description : PpsHdofcSttcMgmtContoller class
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
public class PpsSmsDumpMgmtContoller extends BaseController {
	
	@Autowired
	private PpsSmsDumpMgmtService smsDumpService;
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private FileReadService fileReadService;
	
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private OrgCommonService orgCommonService;
	
	/**
	 * 문자관리 문자전송 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSmsDumpMgmtForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcOpenMgmtListForm : 문자관리(과거) 문자전송 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.setViewName("pps/sms/smsmgmt/sms_smsmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 문자관리 문자전송 폼 호출(신규)
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpMgmtFormNew.do", method={POST, GET} )
	public ModelAndView selectSmsDumpMgmtFormNew( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcOpenMgmtListFormNew : 문자관리(신규) 문자전송 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.setViewName("pps/sms/smsmgmt/sms_smsmgmt_0010New");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 문자관리 문자전송내역 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpMgmtListForm.do", method={POST, GET} )
	public ModelAndView selectSmsDumpMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcOpenMgmtListForm : 문자관리 문자전송내역 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.setViewName("pps/sms/smsmgmt/sms_smsmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 *  문자관리 단문문자전송
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpShortSmsProc.json" )
	public String selectSmsDumpShortSmsProcJson( ModelMap model,
			HttpServletRequest pRequest,
			/*@ModelAttribute("ppsPinInfoVo")PpsPinInfoVo searchPinInfoVo,*/
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
    	
		
		logger.info("문자관리 단문문자전송");
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
	    	
			resultMap= smsDumpService.getPpsSmsDumpShortProc(pRequestParamMap);


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
	 * @Description :  SMS 파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2025. 02. 13.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/pps/sms/smsmgmt/fileUpLoad.do")
	public String fileUpUsingService(ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		FileInfoVO fileInfoVO = new FileInfoVO();
		
		String lSaveFileName = "";
		String lFileNamePc = "";
		
		Integer filesize = 0;
		
		try{
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(fileInfoVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			fileInfoVO.setRvisnId(loginInfo.getUserId());
			
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

			//--------------------------------
			// upload base directory를 구함
			// 존재하지 않으면 exception 발생
			//--------------------------------
			String lBaseDir = propertyService.getString("smsPath");
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

				try
				{
					//--------------------------------
					// 모듈별 directory가 존재하지 않으면 생성
					//--------------------------------
					File lDir = new File(lBaseDir );
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
						// UPLOAD 확장자 체크
						//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());
						int pos = lFileNamePc.lastIndexOf(".");
						String strExt = lFileNamePc.substring( pos + 1).toUpperCase(); //확장자
						
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("ext", strExt);
						
						//v2018.11 PMD 적용 소스 수정
						//boolean bExtChk = false;
				    	int iCnt = boardService.getFileExtList(map);
												
						if(iCnt < 1) {
							resultMap.put("state", false);
							resultMap.put("name", lFileNamePc.replace("'","\\'"));
							resultMap.put("size",  111);

							model.addAttribute("result", resultMap);
							return "jsonViewArray";
						}
						
						//----------------------------------------------------------------
						// 파일 이름 구함
						//----------------------------------------------------------------
//						lFileNamePc = FilenameUtils.getName(item.getName());

						fileInfoVO.setAttRout(lBaseDir);

//						System.out.println("UUID -> " + generationUUID().replaceAll("-",""));

						lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
						String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;

						//----------------------------------------------------------------
						// 파일 write
						//----------------------------------------------------------------
						/** 20230518 PMD 조치 */
						InputStream filecontent = null;
						File f = null;
						OutputStream fout = null;
						try {
							filecontent = item.getInputStream();
							f=new File(lTransferTargetFileName);
							fout=new FileOutputStream(f);
							byte buf[]=new byte[1024];
							int len;
							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
						} catch (Exception e1) {
						    throw new MvnoErrorException(e1);
						} finally
						{
							if (fout != null) {
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
				}catch (Exception e) {
					throw new MvnoErrorException(e);
				}
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
		} catch (Exception e) {
			resultMap.put("state", false);
			resultMap.put("name", lFileNamePc.replace("'","\\'"));
			resultMap.put("size",  111);
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "PPS3000000", "문자전송"))
			{
			   //v2018.11 PMD 적용 소스 수정
			   throw new MvnoErrorException(e);
			}

			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		}

		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lSaveFileName);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("NTC");//공지사항 파일첨부

		//파일 테이블 insert
		orgCommonService.insertFile(fileInfoVO);

		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	}

	/**
	 * @Description : UUID 생성
	 * @Param  :
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 8. 13.
	 */
	public static String generationUUID(){
		  return UUID.randomUUID().toString();
		 }
	
	/**
	 *  문자관리 MMS전송
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/smsDumpMmsSend.json" )
	public String smsDumpMmsSend( ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			FileInfoVO fileInfoVO )
	{

		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	pRequestParamMap.put("adminId", loginInfo.getUserId());	
    	
		logger.info("문자관리 MMS전송");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	logger.info("pRequestParamMap 가공전 <<<<<  " + pRequestParamMap);
	    	logger.info("fileInfoVO 가공전 <<<<<  " + fileInfoVO);
			
			fileInfoVO.setOrgnId(smsDumpService.getMsgQueueSeq());
			int fileCnt = Integer.parseInt((String) pRequestParamMap.get("file_upload1_count"));

			// file Update
			if(fileCnt > 0){
				smsDumpService.uploadFileJuice(pRequestParamMap,fileInfoVO);				
			}
			
			logger.info("문자발송 테이블에 INSERT 시작 <<<<<  ");			
			// 문자발송 테이블에 INSERT
	    	resultMap = smsDumpService.sendMms(pRequestParamMap,fileInfoVO);

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
	 *  문자관리 MMS전송(신규)
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/smsDumpMmsSendNew.json" )
	public String smsDumpMmsSendNew( ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			FileInfoVO fileInfoVO )
	{

		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	pRequestParamMap.put("adminId", loginInfo.getUserId());	
    	
		logger.info("문자관리 MMS전송(신규)");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	logger.info("pRequestParamMap 가공전 <<<<<  " + pRequestParamMap);
	    	logger.info("fileInfoVO 가공전 <<<<<  " + fileInfoVO);
			
			fileInfoVO.setOrgnId(smsDumpService.getMsgQueueSeq());
			int fileCnt = Integer.parseInt((String) pRequestParamMap.get("file_upload1_count"));

			// file Update
			if(fileCnt > 0){
				smsDumpService.uploadFileJuiceNew(pRequestParamMap,fileInfoVO);				
			}
			
			logger.info("문자발송 테이블에 INSERT 시작 <<<<<  ");			
			// 문자발송 테이블에 INSERT
	    	resultMap = smsDumpService.sendMmsNew(pRequestParamMap,fileInfoVO);

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
	 *  문자관리 Excel파일 업로드
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/smsmgmt/uploadSmsExcelFile.do")
	  public String uploadSmsExcelFile (
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
	 	 	  
	    		//logger.debug( e.getStackTrace() );
	    		//logger.debug(">>>>>>>>Exception result:" + e.toString());
	 	 	     		
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
	 *  문자관리 Excel파일 읽기
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/smsmgmt/readSmsExcelFile.do")
	  public String readSmsExcelFile (
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
	  	    
	      	try
	      	{  
	      		// 본사 권한
		    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
		    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
		    	}
		    	
	      		String filePath = sBaseDir+"/PPS/" +pRequestParamMap.get("fileName");
	      		List<?> resultList = smsDumpService.getPpsSmsFileRead(filePath);
	      		resultMap = makeResultMultiRow(pRequestParamMap, resultList);
	      	
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
	 * 문자 Excel파일 읽은 grid sms발송
	 * 1. MethodName: insertUpdateMulti
	 * 2. ClassName	: DemoController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:52:42
	 * </PRE>
	 * 		@return String
	 * 		@param p_jsonString
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpExcelSmsProc.json", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
    public String selectSmsDumpExcelSmsProc(@RequestBody String sJson,
											ModelMap model,
											HttpServletRequest pRequest,
											HttpServletResponse pResponse,
								    		@RequestParam Map<String, Object> pRequestParamMap) {
    	
    	logger.debug("Json  insertSuplyPo.json :" + sJson);
    	logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
    	try {
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	JSONObject iJSONObject = new JSONObject();
	    	iJSONObject = (JSONObject) new JSONParser().parse(sJson);
	    	
	    	//JSONArray l_jsonArray = ((JSONArray)l_jSONObject.get("newData"));
	    	//JSONArray l_jsonArray2 = ((JSONArray)l_jSONObject.get("oldData"));
		    JSONArray iJsonArray = ((JSONArray)iJSONObject.get("allData"));
		    String smsTitle = iJSONObject.get("smsTitle").toString();
		    
		    pRequestParamMap.put("smsTitle", smsTitle);
	    	
		    ObjectMapper iObjectMapper = new ObjectMapper();
		    iObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    
		    resultMap = smsDumpService.getPpsSmsDumpExcelProc(pRequestParamMap, iJsonArray);
	   
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
    	} catch (Exception e) {
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			 throw new MvnoErrorException(e);
			
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
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpMgmtExcelSample.json" )
	public String selectSmsDumpMgmtExcelSampleJson( ModelMap model, 
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
        	String strFileName = sBaseDir+"PpsSmsSample.xls";
        	
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
 			//logger.error(e.getMessage());
 			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", returnMsg);
 			
 			 throw new MvnoErrorException(e);
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	           // logger.error(e);
	        	  throw new MvnoErrorException(e);

	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
	        	//  logger.error(e);
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
	 * 문자관리 문자전송내역 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpMgmtList.json" )
	public String selectSmsDumpMgmtListJson( ModelMap model, 
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

		logger.info("PpsAgencySttcMgmtContoller.selectAgencySttcOpenMgmtListJson : 문자관리 문자전송내역 목록조회");
		
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
	    	
	    	 List<?> resultList = smsDumpService.getSmsDumpMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
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
	 * 문자관리 문자전송내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpMgmtListExcel.json" )
	public String selectSmsDumpMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcOrgMgmtController.selectRcgRealCmsMgmtListExcelJson: 문자관리 문자전송내역 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			smsDumpService.getSmsDumpMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	return "jsonView";
	}
	
	/**
	 * 회신번호 목록을 가져옴
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/sms/smsmgmt/ppsCallingNumList.json" )
	public String selectPpsCallingNumListJson( ModelMap model,
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
    	
		logger.info("회신번호조회");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	//----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
	    	 List<?> resultList = smsDumpService.getSelectPpsCallingNumList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

	    	 

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
	

		
}
