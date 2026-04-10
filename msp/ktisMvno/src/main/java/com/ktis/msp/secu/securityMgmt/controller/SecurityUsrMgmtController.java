package com.ktis.msp.secu.securityMgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.secu.securityMgmt.service.SecurityUsrMgmtService;
import com.ktis.msp.secu.securityMgmt.vo.SecurityMgmtVo;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: SecurityUsrMgmtController.java
 * 3. Package	: com.ktis.msp.secu.securityMgmt.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2019. 01. 17
 * </PRE>
 */
@Controller
public class SecurityUsrMgmtController  extends BaseController { 

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private SecurityUsrMgmtService securityUsrMgmtService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	
	/**
	 * <PRE>
	 * 1. MethodName: mspUsrMgmt
	 * 2. ClassName	: SecurityUsrMgmtController
	 * 3. Commnet	: M전산_등록계정 - 화면
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 01. 17
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/secu/securityMgmt/mspUsrMgmt.do")
	public ModelAndView mspUsrMgmt( ModelMap model, 
	                          HttpServletRequest pRequest, 
							  HttpServletResponse pResponse, 
							  @RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			logger.info("===========================================");
			logger.info("======  SecurityUsrMgmtController.accessFailLog ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("secu/securityMgmt/msp_org_bs_9005");
			
			return modelAndView; 
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	

	/**
	 * 1. MethodName: mspUsrMgmtList
	 * 2. ClassName	: SecurityUsrMgmtController
	 * 3. Commnet	: M전산 등록계정 조회
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 01. 17
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/secu/securityMgmt/mspUsrMgmtList.json")
	public String mspUsrMgmtList(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		logger.info("===========================================");
		logger.info("======  SecurityUsrMgmtController.mspUsrMgmtList ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  securityUsrMgmtService.mspUsrMgmtList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
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
	 * M전산 등록계정 엑셀다운로드
	 * @param searchVO
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/secu/securityMgmt/mspUsrMgmtListExcel.json")
	public String mspUsrMgmtListExcel(ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀다운로드 조회 START"));
		logger.info(generateLogMsg("pRequestParamMap.toString() =  " + pRequestParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);		
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SecurityMgmtVo> list = securityUsrMgmtService.mspUsrMgmtListExcel(pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "M전산등록계정_";//파일명
			String strSheetname = "M전산등록계정";//시트명
			
			String [] strHead = {"조직ID", "조직명" , "사용자ID", "사용자명", "계정구분","계정생성일", "마지막로그인일자","계정수정일", "삭제여부" , "사용여부" };
			String [] strValue = {"orgnId", "orgnNm","usrId","usrNm","attcSctnNm","regstDttm","lastLoginDt","rvisnDttm","delYn","usgYn"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {4000, 4000,4000, 4000, 4000, 6000,6000, 6000, 4000, 4000};
			int[] intLen = {0, 0, 0, 0,  0, 0, 0, 0, 0, 0 };
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list.iterator(), strHead, intWidth, strValue, pRequest, pResponse, intLen);
			
			file = new File(strFileName);
			
			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = pResponse.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))){
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"MSP");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
		file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: mspUsrMgmt
	 * 2. ClassName	: SecurityUsrMgmtController
	 * 3. Commnet	: M전산_마스킹권한자 - 화면
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 01. 21
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/secu/securityMgmt/mspUsrMask.do")
	public ModelAndView mspUsrMask( ModelMap model, 
	                          HttpServletRequest pRequest, 
							  HttpServletResponse pResponse, 
							  @RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			logger.info("===========================================");
			logger.info("======  SecurityUsrMgmtController.mspUsrMask ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("secu/securityMgmt/msp_org_bs_9006");
			
			return modelAndView; 
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 1. MethodName: mspUsrMgmtList
	 * 2. ClassName	: SecurityUsrMgmtController
	 * 3. Commnet	: M전산 마스킹권한자 조회
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 01. 21
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/secu/securityMgmt/mspUsrMaskList.json")
	public String mspUsrMaskList(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		logger.info("===========================================");
		logger.info("======  SecurityUsrMgmtController.mspUsrMaskList ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  securityUsrMgmtService.mspUsrMaskList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
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
	 * M전산 마스킹권한자 엑셀다운로드
	 * @param searchVO
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/secu/securityMgmt/mspUsrMaskListExcel.json")
	public String mspUsrMaskListExcel(ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀다운로드 조회 START"));
		logger.info(generateLogMsg("pRequestParamMap.toString() = " + pRequestParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);		
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SecurityMgmtVo> list = securityUsrMgmtService.mspUsrMaskListExcel(pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "M전산마스킹권한자_";//파일명
			String strSheetname = "M전산마스킹권한자";//시트명
			
			String [] strHead = {"사용자ID", "사용자명" , "조직명"};
			String [] strValue = {"usrId", "usrNm","orgnNm"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {4000, 4000, 4000, 4000};
			int[] intLen = {0, 0, 0, 0};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list.iterator(), strHead, intWidth, strValue, pRequest, pResponse, intLen);
			
			file = new File(strFileName);
			
			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = pResponse.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))){
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"MSP");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
		file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: mspUsrMgmt
	 * 2. ClassName	: SecurityUsrMgmtController
	 * 3. Commnet	: M전산_삭제계정 - 화면
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 01. 21
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/secu/securityMgmt/mspAllUsrMgmt.do")
	public ModelAndView mspAllUsrMgmt( ModelMap model, 
	                          HttpServletRequest pRequest, 
							  HttpServletResponse pResponse, 
							  @RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			logger.info("===========================================");
			logger.info("======  SecurityUsrMgmtController.mspAllUsrMgmt ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("secu/securityMgmt/msp_org_bs_9008");
			
			return modelAndView; 
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 1. MethodName: mspUsrDeltList
	 * 2. ClassName	: SecurityUsrMgmtController
	 * 3. Commnet	: M전산 전체계정 조회
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 01. 21
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/secu/securityMgmt/mspAllUsrMgmtList.json")
	public String mspAllUsrMgmtList(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		logger.info("===========================================");
		logger.info("======  SecurityUsrMgmtController.mspAllUsrMgmtList ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  securityUsrMgmtService.mspAllUsrMgmtList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
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
	 * M전산 전체계정 엑셀다운로드
	 * @param searchVO
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/secu/securityMgmt/mspAllUsrMgmtExcel.json")
	public String mspAllUsrMgmtExcel(ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀다운로드 조회 START"));
		logger.info(generateLogMsg("pRequestParamMap.toString() = " + pRequestParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);		
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SecurityMgmtVo> list = securityUsrMgmtService.mspAllUsrMgmtListExcel(pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "M전산전체계정_";//파일명
			String strSheetname = "M전산전체계정";//시트명
			
			String [] strHead = {"조직ID", "조직명" , "사용자ID", "사용자명", "계정구분","계정생성일", "마지막로그인일자"};
			String [] strValue = {"orgnId", "orgnNm","usrId","usrNm","attcSctnNm","regstDttm","lastLoginDt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {4000, 4000, 4000, 4000, 4000, 6000, 6000};
			int[] intLen = {0, 0, 0, 0,  0, 0, 0, 0, 0 };
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list.iterator(), strHead, intWidth, strValue, pRequest, pResponse, intLen);
			
			file = new File(strFileName);
			
			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = pResponse.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))){
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"MSP");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
		file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: mspUsrMgmt
	 * 2. ClassName	: SecurityUsrMgmtController
	 * 3. Commnet	: M전산_삭제계정 - 화면
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 01. 21
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/secu/securityMgmt/mspUsrDel.do")
	public ModelAndView mspUsrDel( ModelMap model, 
	                          HttpServletRequest pRequest, 
							  HttpServletResponse pResponse, 
							  @RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			logger.info("===========================================");
			logger.info("======  SecurityUsrMgmtController.mspUsrDel ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("secu/securityMgmt/msp_org_bs_9007");
			
			return modelAndView; 
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 1. MethodName: mspUsrDeltList
	 * 2. ClassName	: SecurityUsrMgmtController
	 * 3. Commnet	: M전산 삭제계정 조회
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 01. 21
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/secu/securityMgmt/mspUsrDelList.json")
	public String mspUsrDelList(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		logger.info("===========================================");
		logger.info("======  SecurityUsrMgmtController.mspUsrDelList ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  securityUsrMgmtService.mspUsrDelList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
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
	 * M전산 삭제계정 엑셀다운로드
	 * @param searchVO
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/secu/securityMgmt/mspUsrDelListExcel.json")
	public String mspUsrDelListExcel(ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀다운로드 조회 START"));
		logger.info(generateLogMsg("pRequestParamMap.toString() = " + pRequestParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);		
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SecurityMgmtVo> list = securityUsrMgmtService.mspUsrDelListExcel(pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "M전산삭제계정_";//파일명
			String strSheetname = "M전산삭제계정";//시트명
			
			String [] strHead = {"조직ID", "조직명" , "사용자ID", "사용자명", "계정구분","계정생성일", "마지막로그인일자","계정수정일", "구분" };
			String [] strValue = {"orgnId", "orgnNm","usrId","usrNm","attcSctnNm","regstDttm","lastLoginDt","rvisnDttm","status"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {4000, 4000, 4000, 4000, 4000, 6000, 6000, 6000, 4000};
			int[] intLen = {0, 0, 0, 0,  0, 0, 0, 0, 0 };
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list.iterator(), strHead, intWidth, strValue, pRequest, pResponse, intLen);
			
			file = new File(strFileName);
			
			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = pResponse.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))){
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"MSP");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
		file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
}


