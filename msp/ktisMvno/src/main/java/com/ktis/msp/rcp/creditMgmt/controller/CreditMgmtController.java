package com.ktis.msp.rcp.creditMgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.rcp.creditMgmt.service.CreditMgmtService;
import com.ktis.msp.rcp.creditMgmt.vo.CreditVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class CreditMgmtController extends BaseController {
	
	@Autowired
	private MenuAuthService  menuAuthService;
	@Autowired
	private OrgCommonService orgCommonService;
	@Autowired
	private CreditMgmtService creditMgmtService;
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	/**
	 * @Description : 신용정보 동의서 초기화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 한상욱
	 * @Create Date : 2016. 04. 06.
	 */
	@RequestMapping(value="/rcp/credit/creditMgmt.do", method={POST, GET})
	public ModelAndView view( HttpServletRequest paramReq, HttpServletResponse paramRes, @ModelAttribute("creditVO") CreditVO creditVO, ModelMap model ) {
		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
		ModelAndView modelAndView = new ModelAndView();
		
		logger.info	("==========================================================");
		logger.debug("CreditMgmtController  : 신용정보 동의서 조회 화면 컨트롤러");
		logger.info	("==========================================================");
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(creditVO);
			
			creditVO.setUserOrgnTypeCd(creditVO.getSessionUserOrgnTypeCd());	/** 사용자조직유형코드 */
			
			/* 본사조직이 아니면 강제 조직 세팅 */
			if(!creditVO.getUserOrgnTypeCd().equals("10")) {
				creditVO.setOrgnId(creditVO.getSessionUserOrgnId());	/** 조직ID */
				creditVO.setOrgnNm(creditVO.getSessionUserOrgnNm());	/** 조직명 */
			}
			
			creditVO.setStartDate(orgCommonService.getToDay());		/** 조회시작일자 */
    		creditVO.setEndDate(orgCommonService.getWantDay(7));	/** 조회종료일자 */
			
			modelAndView.getModelMap().addAttribute("info",creditVO);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(paramReq, paramRes));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(paramReq, paramRes));
			
			modelAndView.setViewName("/rcp/creditMgmt/creditMgmt");
			return modelAndView;
		}
		catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	/**
	 * @Description : 신용정보 동의서 리스트
	 * @Param  : void
	 * @Return : String
	 * @Author : 한상욱
	 * @Create Date : 2016. 04. 06.
	 */
	@RequestMapping("/rcp/credit/list.json")	
    public String selectCreditMgmtList( @ModelAttribute("CreditVO") CreditVO creditVO,
    		HttpServletRequest paramReq, 
    		HttpServletResponse paramRes,
    		@RequestParam Map<String, Object> paramMap,
    		ModelMap model ) {     
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("신용정보 동의서 조회 START."));
        logger.info(generateLogMsg("Return Vo [CreditVO] = " + creditVO.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try { 
        	/* 로그인정보체크 */
        	LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
        	loginInfo.putSessionToVo(creditVO);
        	loginInfo.putSessionToParameterMap(paramMap);
        	
			/* 본사조직이 아니면 강제 조직 세팅 */
			if(!creditVO.getSessionUserOrgnTypeCd().equals("10"))
				creditVO.setOrgnId(creditVO.getSessionUserOrgnId());	/** 조직ID */
			
			@SuppressWarnings("unchecked")
			List<EgovMap> resultList = (List<EgovMap>) creditMgmtService.selectCreditList(creditVO, paramMap);
			
			resultMap =  makeResultMultiRow(creditVO, resultList);

			model.addAttribute("result", resultMap);

        } catch (Exception e) {
            resultMap.clear();
            throw new MvnoErrorException(e);
        
        }
        
        return "jsonView"; 
    }
	
	/**
	 * 신용정보조회 동의서 insert
	 */
	@RequestMapping(value = "/rcp/credit/creditInsert.json")
	public String  creditInsert(FileInfoVO fileInfoVO,
								ModelMap model,
								HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info("===================================================================");
		logger.info("======  CreditMgmtController  : 신용정보조회 insert 컨트롤러 ======");
		logger.info("===================================================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===================================================================");

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//--------------------------------------
		// DB Insert/Updaet/Delete
		//--------------------------------------
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) 
					&& !pRequestParamMap.get("SESSION_USER_ORGN_ID").equals(pRequestParamMap.get("orgnId"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String id = creditMgmtService.creditInsert(pRequestParamMap);
			
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
			
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_3()))
			{
				orgCommonService.updateFile23(fileInfoVO);
			}
			
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_4()))
			{
				orgCommonService.updateFile24(fileInfoVO);
			}

		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
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
	 * 신용정보조회 동의서 update
	 */
	@RequestMapping(value = "/rcp/credit/creditUpdate.json")
	public String  creditUpdate(  FileInfoVO fileInfoVO,
													ModelMap model,
													HttpServletRequest pRequest,
													HttpServletResponse pResponse,
													@RequestParam Map<String, Object> pRequestParamMap
												)
	{
		logger.info("==========================================================================");
		logger.info("======  CreditMgmtController  : 신용정보조회 동의서 update 컨트롤러 ======");
		logger.info("==========================================================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("==========================================================================");

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//--------------------------------------
		// DB Insert/Updaet/Delete
		//--------------------------------------
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) 
					&& !pRequestParamMap.get("SESSION_USER_ORGN_ID").equals(pRequestParamMap.get("orgnId"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			fileInfoVO.setOrgnId(pRequestParamMap.get("creditId").toString() );

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

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_3()))
			{
				orgCommonService.updateFile23(fileInfoVO);
			}
			
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_4()))
			{
				orgCommonService.updateFile24(fileInfoVO);
			}
			
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
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
	 * @Create Date : 2016. 04. 06.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/rcp/credit/fileUpLoad.do")
	public String fileUpUsingService(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

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
//							20200512 소스코드점검 수정
//					    	e1.printStackTrace();
//							20210706 소스코드점검 수정
//							System.out.println("Connection Exception occurred");
							logger.error("Connection Exception occurred");
						} finally {
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
		fileInfoVO.setAttSctn("CRE");//신용정보조회 동의서 파일첨부

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
	 * @Create Date : 2016. 4. 06.
	 */
	public static String generationUUID(){
		  return UUID.randomUUID().toString();
	}
	
	/**
	 * @Description : 첨부된 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2016. 4. 06.
	 */
	@RequestMapping("/rcp/credit/getFile.json")
	public String getFile1(@ModelAttribute("CreditVO") CreditVO creditVO, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("첨부 파일 명 찾기 START."+creditVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(creditVO);
			
			List<?> resultList = creditMgmtService.getFile1(creditVO);

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
				
				if(i==3)
				{
					resultMap.put("fileNm3", tempMap.get("fileNm"));
					resultMap.put("fileId3", tempMap.get("fileId"));
				}
				
				if(i==4)
				{
					resultMap.put("fileNm4", tempMap.get("fileNm"));
					resultMap.put("fileId4", tempMap.get("fileId"));
				}
			}
			resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.debug("result = " + resultMap);
		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : 파일 삭제 기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2016. 4. 06.
	 */
	@RequestMapping("/rcp/credit/deleteFile.json")
	public String deleteFile( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 삭제 START."));
		logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		File file = null;
		String returnMsg = null;

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			String strFileName = orgCommonService.getFileNm(pReqParamMap.get("fileId").toString());

			file = new File(strFileName);

			file.delete();

			int delcnt = orgCommonService.deleteFile(pReqParamMap.get("fileId").toString());
			logger.info(generateLogMsg("삭제건수 = " + delcnt));

			FileInfoVO fileInfoVO = new FileInfoVO();
			fileInfoVO.setOrgnId(pReqParamMap.get("creditId").toString());
			fileInfoVO.setAttSctn("CRE");
			int fileCnt = orgCommonService.getFileCnt(fileInfoVO);
			logger.info(generateLogMsg("getFileCnt(fileInfoVO); fileCnt :  = " + fileCnt));

			resultMap.put("fileTotCnt", String.valueOf(fileCnt));
			resultMap.put("deleteCnt", String.valueOf(delcnt));

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "파일삭제성공");

 		} catch (Exception e) {
 			
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
}