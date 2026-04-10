package com.ktis.msp.m2m.pscboard.controller;

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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;

import com.ktis.msp.m2m.pscboard.service.PscBoardService;

import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : BoardController.java
 * @Description : BoardController Class
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.09.01     임지혜          최초생성
 *
 *
 * @author 임지혜
 */

@Controller
public class PscBoardController extends BaseController {
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private OrgCommonService orgCommonService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	@Autowired
	private PscBoardService pscBoardService;
	
	/**
	 * PSC 공지사항 관리 화면
	 */

	@RequestMapping(value = "/m2m/usimPscNoticeMng/usimPscNoticeMng.do")
	public ModelAndView notice (ModelMap model,
								HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info("===========================================");
		logger.debug("board.controller  : PSC 공지사항 관리 조회 화면 컨트롤러");
		logger.info("===========================================");
		
		ModelAndView mv = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			model.addAttribute("startDate",orgCommonService.getToDay());
			model.addAttribute("endDate",orgCommonService.getWantDay(7));
			model.addAttribute("pastDate",orgCommonService.getWantDay(-7));
			
			model.addAttribute("hqAuth",pscBoardService.hqAuth(pRequestParamMap));
			
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			mv.setViewName("/m2m/pscboard/psc_notice");
			return mv;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * PSC 공지사항 관리 리스트 조회
	 */
	@RequestMapping(value = "/m2m/pscboard/noticeList.json")
	public String  noticeList(ModelMap model,
							HttpServletRequest pRequest,
							HttpServletResponse pResponse,
							@RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info("===========================================");
		logger.debug("board.controller  : PSC 공지사항 관리 리스트 컨트롤러");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//보안테스트<고의적 sql에러>
		//pRequestParamMap.put("SRCH_END_DT", "20181122;");
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  pscBoardService.noticeList(pRequestParamMap);

			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

		}catch ( Exception e){
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "M2M1000061", "PSC 공지사항 관리"))
			{
				//throw new MvnoErrorException(e);
				throw new MvnoErrorException(e);
}
		}finally{
			dummyFinally();
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * PSC 공지사항 관리 insert
	 */
	@RequestMapping(value = "/m2m/pscboard/noticeInsert.json")
	public String  noticeInsert(FileInfoVO fileInfoVO,
								ModelMap model,
								HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info("===========================================");
		logger.info("======  board.controller  : PSC 공지사항 관리 insert 컨트롤러 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if (!pscBoardService.hqAuth(pRequestParamMap).equals("10")) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 재목, 본문 특수문자(<, >) 체크
			//----------------------------------------------------------------
			String strTitle = String.valueOf(pRequestParamMap.get("TITLE"));
			String strContents = String.valueOf(pRequestParamMap.get("CONTENTS"));
			if(StringUtil.chkString(strTitle) || StringUtil.chkString(strContents)) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.CHK_STRING", null, Locale.getDefault()));
			}

			String id = pscBoardService.noticeInsert(pRequestParamMap);

			fileInfoVO.setOrgnId(id);

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_0()))
			{
				orgCommonService.updateFile(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_1()))
			{
				orgCommonService.updateFile1(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_2()))
			{
				orgCommonService.updateFile2(fileInfoVO);
			}

		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch ( Exception e)
		{
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "M2M1000061", "PSC 공지사항 관리"))
			{
				throw new MvnoErrorException(e);
			}
		}finally{
			dummyFinally();
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";

	}


	/**
	 * PSC 공지사항 관리 update
	 */
	@RequestMapping(value = "/m2m/pscboard/noticeUpdate.json")
	public String  noticeUpdate(FileInfoVO fileInfoVO,
								ModelMap model,
								HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info("===========================================");
		logger.info("======  board.controller  : PSC 공지사항 관리 update 컨트롤러 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if (!pscBoardService.hqAuth(pRequestParamMap).equals("10")) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 재목, 본문 특수문자(<, >) 체크
			//----------------------------------------------------------------
			String strTitle = String.valueOf(pRequestParamMap.get("TITLE"));
			String strContents = String.valueOf(pRequestParamMap.get("CONTENTS"));
			if(StringUtil.chkString(strTitle) || StringUtil.chkString(strContents)) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.CHK_STRING", null, Locale.getDefault()));
			}

			pscBoardService.noticeUpdate(pRequestParamMap);

			fileInfoVO.setOrgnId(pRequestParamMap.get("NOTICE_ID").toString() );

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_0()))
			{
				orgCommonService.updateFile(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_1()))
			{
				orgCommonService.updateFile1(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_2()))
			{
				orgCommonService.updateFile2(fileInfoVO);
			}

		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch ( Exception e)
		{
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "M2M1000061", "PSC 공지사항 관리"))
			{
				throw new MvnoErrorException(e);
			}
		}finally{
			dummyFinally();
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	/**
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 10. 15.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/m2m/pscboard/fileUpLoad.do")
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
			String lBaseDir = propertyService.getString("orgPath");
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
				    	int iCnt = pscBoardService.getFileExtList(map);
												
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
						InputStream filecontent = item.getInputStream();
						File f=new File(lTransferTargetFileName);

						OutputStream fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;

						try {

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
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1010011", "업무게시판"))
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
	 * @Description :  파일업로드 2
	 * @Param  : model
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 10. 15.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/m2m/pscboard/fileUpLoad2.do")
	public String fileUpUsingService2(ModelMap model, 
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
			String lBaseDir = propertyService.getString("orgPath");
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
						// 파일 이름 구함
						//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());

						fileInfoVO.setAttRout(lBaseDir);

//						System.out.println("UUID -> " + generationUUID().replaceAll("-",""));

						lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
						String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;

						//----------------------------------------------------------------
						// 파일 write
						//----------------------------------------------------------------
						InputStream filecontent = item.getInputStream();
						File f=new File(lTransferTargetFileName);

						OutputStream fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;

						try {

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
	 * @Description :  파일업로드 3
	 * @Param  : model
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 10. 15.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/m2m/pscboard/fileUpLoad3.do")
	public String fileUpUsingService3(ModelMap model, 
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
			
			// 본사 화면인 경우
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
			String lBaseDir = propertyService.getString("orgPath");
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
						// 파일 이름 구함
						//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());

						fileInfoVO.setAttRout(lBaseDir);

//						System.out.println("UUID -> " + generationUUID().replaceAll("-",""));

						lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
						String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;

						//----------------------------------------------------------------
						// 파일 write
						//----------------------------------------------------------------
						InputStream filecontent = item.getInputStream();
						File f=new File(lTransferTargetFileName);

						OutputStream fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;

						try {

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
	 * @Description : 파일 삭제 기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/m2m/pscboard/deleteFile.json")
	public String deleteFile( Model model, 
							HttpServletRequest request, 
							HttpServletResponse response,
							@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 삭제 START."));
		logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		File file = null;
		String returnMsg = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String strFileName = orgCommonService.getFileNm(pReqParamMap.get("fileId").toString());

			file = new File(strFileName);

			file.delete();

			int delcnt = orgCommonService.deleteFile(pReqParamMap.get("fileId").toString());
			logger.info(generateLogMsg("삭제건수 = " + delcnt));

			FileInfoVO fileInfoVO = new FileInfoVO();
			fileInfoVO.setOrgnId(pReqParamMap.get("noticeId").toString());
			fileInfoVO.setAttSctn("NTC");
			int fileCnt = orgCommonService.getFileCnt(fileInfoVO);
			logger.info(generateLogMsg("getFileCnt(fileInfoVO); fileCnt :  = " + fileCnt));

			resultMap.put("fileTotCnt", String.valueOf(fileCnt));
			resultMap.put("deleteCnt", String.valueOf(delcnt));

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "파일삭제성공");

		} catch (Exception e) {
			resultMap.put("fileTotCnt", "0");
			resultMap.put("deleteCnt", "0");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					returnMsg, "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}		
		}
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
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
	 * @Description : 첨부된 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/m2m/pscboard/getNoticeFile.json")
	public String getNoticeFile(FileInfoVO fileInfoVO, 
							Model model, 
							HttpServletRequest request, 
							HttpServletResponse response,
							@RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("첨부 파일 명 찾기 START."+fileInfoVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			List<?> resultList = pscBoardService.getNoticeFile(pReqParamMap);
			
			for (int i = 0; i < resultList.size(); i++) {
				EgovMap tempMap = (EgovMap)resultList.get(i);
				
				if(i==0)
				{
					resultMap.put("fileNm", tempMap.get("fileNm"));
					resultMap.put("fileId", tempMap.get("fileId"));
				}
				
				if(i==1)
				{
					resultMap.put("fileNm1", tempMap.get("fileNm"));
					resultMap.put("fileId1", tempMap.get("fileId"));
				}
				
				if(i==2)
				{
					resultMap.put("fileNm2", tempMap.get("fileNm"));
					resultMap.put("fileId2", tempMap.get("fileId"));
				}
			}
			resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);

			//resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			//resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : 파일 다운로드 기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/m2m/pscboard/downFile.json")
	public String downFile( Model model,
							HttpServletRequest request,
							HttpServletResponse response,
							@RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일다운로드 START."));
		logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		String returnMsg = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			String strFileName = orgCommonService.getFileNm(pReqParamMap.get("fileId").toString());
			
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			String encodingFileName = "";

			int excelPathLen2 = Integer.parseInt(propertyService.getString("orgPathLen2"));

			try {
			  encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8");
			} catch (UnsupportedEncodingException uee) {
			  encodingFileName = strFileName;
			}
			
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
			resultMap.put("msg", returnMsg);
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					returnMsg, "", ""))
			{
				throw new MvnoErrorException(e);
			}				
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
	 * notice  delete
	 */
	@RequestMapping(value = "/m2m/pscboard/noticeDelete.json")
	public String  noticeDelete(ModelMap model,
								HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> pRequestParamMap)
	{

		logger.info("===========================================");
		logger.info("======  /m2m/pscboard/noticeDelete.json PSC 공지사항 관리 삭제 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if (!pscBoardService.hqAuth(pRequestParamMap).equals("10")) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =   pscBoardService.noticeDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "M2M1000061", "PSC 공지사항 관리"))
			{
				throw new MvnoErrorException(e);
			}
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	/**
     * PSC 공지사항 관리 게시글 수정 권한 체크
     */
    @RequestMapping(value = "/m2m/pscboard/checkNoticeAuth.json")
    public String  checkNoticeAuth(ModelMap model,
                            HttpServletRequest pRequest,
                            HttpServletResponse pResponse,
                            @RequestParam Map<String, Object> pRequestParamMap)
    {
        logger.info("===========================================");
        logger.debug("board.controller  : PSC 공지사항 관리 게시글 수정 권한 체크 컨트롤러");
        logger.info("===========================================");
        logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
        logger.info("===========================================");
        
        //--------------------------------------
        // return JSON 변수 선언
        //--------------------------------------
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
        
        boolean check = false;
        
        try{
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            
            // 본사 권한
            if (!pscBoardService.hqAuth(pRequestParamMap).equals("10")) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            } else {
                check = menuAuthService.chkUsrGrpAuth(loginInfo.getUserId(), "admin");
            }

            //----------------------------------------------------------------
            // 목록 db select
            //----------------------------------------------------------------
            String regstId =  pscBoardService.checkNoticeAuth((String)pRequestParamMap.get("NOTICE_ID"));
            if (loginInfo.getUserId().equals(regstId) || check){ // 작성자 이거나 admin 권한
                resultMap.put("msg", "OK");
            } else {
                resultMap.put("msg", "NOK");
            }

        }catch ( Exception e){
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), 
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
                    "시스템 오류 - 관리자에게 문의하세요", "M2M1000061", "PSC 공지사항 관리"))
            {
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
            }       
            
        }finally{
            dummyFinally();
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";

    }
}