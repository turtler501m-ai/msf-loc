package com.ktis.msp.cmn.accesslogsrch.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.accesslogsrch.service.AccessLogSrchService;
import com.ktis.msp.cmn.accesslogsrch.vo.AccessLogSrchVo;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: AccessLogSrchController.java
 * 3. Package	: com.ktis.msp.cmn.accesslogsrch.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:35:29
 * </PRE>
 */
@Controller
public class AccessLogSrchController  extends BaseController { 

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private AccessLogSrchService accessLogSrchService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	
	/**
	 * <PRE>
	 * 1. MethodName: form
	 * 2. ClassName	: AccessLogSrchController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:35:42
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/accesslogsrch/form.do")
	public ModelAndView form( //@ModelAttribute("mnfctMgmtXXVo") X3XVo mnfctMgmtXXVo, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			logger.info("===========================================");
			logger.info("======  AccessLogSrch.form -- 화면 접속 로그 화면 ======");
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
			modelAndView.setViewName("cmn/accesslogsrch/msp_org_bs_1040");
			
			return modelAndView; 
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	

	/**
	 * <PRE>
	 * 1. MethodName: formButton
	 * 2. ClassName	: AccessLogSrchController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:50:08
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/accesslogsrch/formButton.do")
	public ModelAndView formButton(ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap)
	{
		try {
			logger.info("===========================================");
			logger.info("======  AccessLogSrch.form -- 화면 접속 로그 화면 ======");
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
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("cmn/accesslogsrch/msp_org_bs_1040_button");

			
			return modelAndView; 
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		} 

	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName: selecList
	 * 2. ClassName	: AccessLogSrchController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:50:18
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/accesslogsrch/selecList.json")
	public String selecList(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		logger.info("===========================================");
		logger.info("======  AccessLogSrch.selecList -- 화면 접속 로그 화면 목록 ======");
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
			List<?> resultList =  accessLogSrchService.selectList(pRequestParamMap);
			
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
	 * 사용자별 접속로그 엑셀다운로드
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/cmn/accesslogsrch/selecExcelList.json")
	public String selecExcelList(@ModelAttribute("searchVO")  AccessLogSrchVo searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀다운로드 조회 START"));
		logger.info(generateLogMsg("Return Vo [AccessLogSrchVo] = " + searchVO.toString()));
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
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
//			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<AccessLogSrchVo> list = accessLogSrchService.selecExcelList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "사용자별접속로그_";//파일명
			String strSheetname = "사용자별접속로그";//시트명
			
			String [] strHead = { "사용자ID", "사용자명", "조직ID", "조직명","접속일시", "성공여부"};
			String [] strValue = {"usrId","usrNm","orgnId","orgnNm","logDttm","succYn"};
		
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 7000, 5000, 7000, 5000 , 5000};
			int[] intLen = {0, 0, 0, 0, 0, 0};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list.iterator(), strHead, intWidth, strValue, request, response, intLen);
			
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
				
				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"MSP");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
		file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	
}


