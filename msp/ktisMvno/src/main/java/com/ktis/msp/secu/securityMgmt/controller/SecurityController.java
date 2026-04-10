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
import com.ktis.msp.secu.securityMgmt.service.SecurityService;
import com.ktis.msp.secu.securityMgmt.vo.SecurityMgmtVo;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: SecurityController.java
 * 3. Package	: com.ktis.msp.secu.securityMgmt.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2019. 01. 17
 * </PRE>
 */
@Controller
public class SecurityController  extends BaseController { 

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
    private FileDownService  fileDownService;
    
    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
	
	/**
	 * <PRE>
	 * 1. MethodName: accessFailLog
	 * 2. ClassName	: SecurityController
	 * 3. Commnet	: 접속실패로그 조회 - 화면
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
	@RequestMapping(value = "/secu/securityMgmt/accessFailLog.do")
	public ModelAndView accessFailLog( ModelMap model, 
	                          HttpServletRequest pRequest, 
							  HttpServletResponse pResponse, 
							  @RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			logger.info("===========================================");
			logger.info("======  SecurityController.accessFailLog ======");
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
			modelAndView.setViewName("secu/securityMgmt/msp_org_bs_9001");
			
			return modelAndView; 
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	

	/**
	 * 1. MethodName: accessFailLogList
	 * 2. ClassName	: SecurityController
	 * 3. Commnet	: 접속실패로그 조회
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
	@RequestMapping(value = "/secu/securityMgmt/accessFailLogList.json")
	public String accessFailLogList(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		logger.info("===========================================");
		logger.info("======  SecurityController.accessFailLogList ======");
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
			List<?> resultList =  securityService.accessFailLogList(pRequestParamMap);
			
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
     * M전산 접속실패로그 엑셀다운로드
     * @param searchVO
     * @param request
     * @param response
     * @param paramMap
     * @param model
     * @return
     */
    @RequestMapping(value="/secu/securityMgmt/accessFailLogListExcel.json")
    public String accessFailLogListExcel(ModelMap model,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    @RequestParam Map<String, Object> pRequestParamMap) {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("M전산 접속실패로그 엑셀다운로드 START"));
        logger.info(generateLogMsg(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString()));
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
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            
            // 본사 화면인 경우
            if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            List<SecurityMgmtVo> resultList =  securityService.accessFailLogListExcel(pRequestParamMap);
            
            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "M전산_접속실패로그_";//파일명
            String strSheetname = "M전산_접속실패로그";//시트명
            
            String [] strHead = {"접속일시", "성공실패구분", "사용자ID", "사용자명", "조직ID", "조직명"};
            String [] strValue = {"logDttm","succYn","usrId", "usrNm", "orgnId", "orgnNm"};
            //엑셀 컬럼 사이즈
            int[] intWidth = {6000, 4000, 4000, 6000, 4000, 6000};
            int[] intLen = {0, 0, 0, 0, 0, 0};
            
            //파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
            // rqstMgmtService 함수 호출
            String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
            
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
                
                pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
                pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
                pRequestParamMap.put("DUTY_NM"   ,"MSP");                       //업무명
                pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
                pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
                pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
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
    
    
	/**
     * <PRE>
     * 1. MethodName: accessFailFileDown
     * 2. ClassName : SecurityController
     * 3. Commnet   : 접속실패자-파일다운 조회 화면
     * 4. 작성자   : Administrator
     * 5. 작성일   : 2019. 01. 17
     * </PRE>
     *      @return ModelAndView
     *      @param model
     *      @param pRequest
     *      @param pResponse
     *      @param pRequestParamMap
     *      @return
     *      @throws Exception
     */
    @RequestMapping(value = "/secu/securityMgmt/accessFailFileDown.do")
    public ModelAndView accessFailFileDown( ModelMap model, 
                              HttpServletRequest pRequest, 
                              HttpServletResponse pResponse, 
                              @RequestParam Map<String, Object> pRequestParamMap
                                            ) 
    {
        try {
            logger.info("===========================================");
            logger.info("======  SecurityController.accessFailFileDown ======");
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
            modelAndView.setViewName("secu/securityMgmt/msp_org_bs_9002");
            
            return modelAndView; 
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }
    

    /**
     * 1. MethodName: accessFailFileDownList
     * 2. ClassName : SecurityController
     * 3. Commnet   : 접속실패자-파일다운 조회
     * 4. 작성자   : Administrator
     * 5. 작성일   : 2019. 01. 17
     *      @return String
     *      @param model
     *      @param pRequest
     *      @param pResponse
     *      @param pRequestParamMap
     *      @return
     *      @throws Exception
     */
    @RequestMapping(value = "/secu/securityMgmt/accessFailFileDownList.json")
    public String accessFailFileDownList(ModelMap model, 
                            HttpServletRequest pRequest, 
                            HttpServletResponse pResponse, 
                            @RequestParam Map<String, Object> pRequestParamMap)
    {
        
        logger.info("===========================================");
        logger.info("======  SecurityController.accessFailFileDownList ======");
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
            List<?> resultList =  securityService.accessFailFileDownList(pRequestParamMap);
            
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
     * 접속실패자-파일다운 조회 엑셀다운로드
     * @param searchVO
     * @param request
     * @param response
     * @param paramMap
     * @param model
     * @return
     */
    @RequestMapping(value="/secu/securityMgmt/accessFailFileDownListExcel.json")
    public String accessFailFileDownListExcel(ModelMap model,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    @RequestParam Map<String, Object> pRequestParamMap) {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("M전산 접속실패자 파일다운 엑셀다운로드 START"));
        logger.info(generateLogMsg(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString()));
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
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            
            // 본사 화면인 경우
            if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            List<SecurityMgmtVo> resultList =  securityService.accessFailFileDownListExcel(pRequestParamMap);
            
            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "M전산_접속실패자_파일다운_";//파일명
            String strSheetname = "M전산_접속실패자_파일다운";//시트명
            
            String [] strHead = {"파일다운로드일시", "사용자ID", "사용자명", "조직ID", "조직명", "사유", "메뉴명", "파일명"};
            String [] strValue = {"fileDnDt","usrId", "usrNm", "orgnId", "orgnNm", "dwnldRsb", "menuNm", "fileNm"};
            //엑셀 컬럼 사이즈
            int[] intWidth = {6000, 4000, 6000, 4000, 6000, 8000, 8000, 14000};
            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0};
            
            //파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
            // rqstMgmtService 함수 호출
            String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
            
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
                
                pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
                pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
                pRequestParamMap.put("DUTY_NM"   ,"MSP");                       //업무명
                pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
                pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
                pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
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
    
    /**
     * <PRE>
     * 1. MethodName: stopUser
     * 2. ClassName : SecurityController
     * 3. Commnet   : M전산 정지계정 화면
     * 4. 작성자   : Administrator
     * 5. 작성일   : 2019. 01. 17
     * </PRE>
     *      @return ModelAndView
     *      @param model
     *      @param pRequest
     *      @param pResponse
     *      @param pRequestParamMap
     *      @return
     *      @throws Exception
     */
    @RequestMapping(value = "/secu/securityMgmt/stopUser.do")
    public ModelAndView stopAccount( ModelMap model, 
                              HttpServletRequest pRequest, 
                              HttpServletResponse pResponse, 
                              @RequestParam Map<String, Object> pRequestParamMap
                                            ) 
    {
        try {
            logger.info("===========================================");
            logger.info("======  SecurityController.stopUser ======");
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
            modelAndView.setViewName("secu/securityMgmt/msp_org_bs_9003");
            
            return modelAndView; 
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }
    
    
    

    /**
     * 1. MethodName: stopUserList
     * 2. ClassName : SecurityController
     * 3. Commnet   : M전산 정지계정 조회
     * 4. 작성자   : Administrator
     * 5. 작성일   : 2019. 01. 17
     *      @return String
     *      @param model
     *      @param pRequest
     *      @param pResponse
     *      @param pRequestParamMap
     *      @return
     *      @throws Exception
     */
    @RequestMapping(value = "/secu/securityMgmt/stopUserList.json")
    public String stopUserList(ModelMap model, 
                            HttpServletRequest pRequest, 
                            HttpServletResponse pResponse, 
                            @RequestParam Map<String, Object> pRequestParamMap)
    {
        
        logger.info("===========================================");
        logger.info("======  SecurityController.stopUserList ======");
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
            List<?> resultList =  securityService.stopUserList(pRequestParamMap);
            
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
     * 정지계정 조회 엑셀다운로드
     * @param searchVO
     * @param request
     * @param response
     * @param paramMap
     * @param model
     * @return
     */
    @RequestMapping(value="/secu/securityMgmt/stopUserListExcel.json")
    public String stopUserListExcel(ModelMap model,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    @RequestParam Map<String, Object> pRequestParamMap) {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("M전산 정지계정 엑셀다운로드 START"));
        logger.info(generateLogMsg(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString()));
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
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            
            // 본사 화면인 경우
            if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            List<SecurityMgmtVo> resultList =  securityService.stopUserListExcel(pRequestParamMap);
            
            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "M전산_정지계정_";//파일명
            String strSheetname = "M전산_정지계정";//시트명
            
            String [] strHead = {"조직ID", "조직명", "사용자ID", "사용자명", "계정구분", "계정수정일자", "계정생성일", "마지막로그인일자"};
            String [] strValue = {"orgnId", "orgnNm", "usrId", "usrNm", "attcSctnNm", "rvisnDttm", "regstDttm", "lastLoginDt"};
            //엑셀 컬럼 사이즈
            int[] intWidth = {4000, 6000, 4000, 6000, 4000, 6000, 6000, 6000};
            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0};
            
            //파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
            // rqstMgmtService 함수 호출
            String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
            
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
                
                pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
                pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
                pRequestParamMap.put("DUTY_NM"   ,"MSP");                       //업무명
                pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
                pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
                pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
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
    
    /**
     * <PRE>
     * 1. MethodName: fileDownLog
     * 2. ClassName : SecurityController
     * 3. Commnet   : M전산 파일다운로드로그
     * 4. 작성자   : Administrator
     * 5. 작성일   : 2019. 01. 17
     * </PRE>
     *      @return ModelAndView
     *      @param model
     *      @param pRequest
     *      @param pResponse
     *      @param pRequestParamMap
     *      @return
     *      @throws Exception
     */
    @RequestMapping(value = "/secu/securityMgmt/fileDownLog.do")
    public ModelAndView fileDownLog( ModelMap model, 
                              HttpServletRequest pRequest, 
                              HttpServletResponse pResponse, 
                              @RequestParam Map<String, Object> pRequestParamMap
                                            ) 
    {
        try {
            logger.info("===========================================");
            logger.info("======  SecurityController.fileDownLog ======");
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
            modelAndView.setViewName("secu/securityMgmt/msp_org_bs_9004");
            
            return modelAndView; 
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }
    

    /**
     * 1. MethodName: fileDownLogList
     * 2. ClassName : SecurityController
     * 3. Commnet   : M전산 파일다운로드로그 조회
     * 4. 작성자   : Administrator
     * 5. 작성일   : 2019. 01. 17
     *      @return String
     *      @param model
     *      @param pRequest
     *      @param pResponse
     *      @param pRequestParamMap
     *      @return
     *      @throws Exception
     */
    @RequestMapping(value = "/secu/securityMgmt/fileDownLogList.json")
    public String fileDownLogList(ModelMap model, 
                            HttpServletRequest pRequest, 
                            HttpServletResponse pResponse, 
                            @RequestParam Map<String, Object> pRequestParamMap)
    {
        
        logger.info("===========================================");
        logger.info("======  SecurityController.fileDownLogList ======");
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
            List<?> resultList =  securityService.fileDownLogList(pRequestParamMap);
            
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
     * 파일다운로드 로그 엑셀다운로드
     * @param searchVO
     * @param request
     * @param response
     * @param paramMap
     * @param model
     * @return
     */
    @RequestMapping(value="/secu/securityMgmt/fileDownLogListExcel.json")
    public String fileDownLogListExcel(ModelMap model,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    @RequestParam Map<String, Object> pRequestParamMap) {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("M전산 파일다운로드 엑셀다운로드 START"));
        logger.info(generateLogMsg(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString()));
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
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            
            // 본사 화면인 경우
            if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            List<SecurityMgmtVo> resultList =  securityService.fileDownLogListExcel(pRequestParamMap);
            
            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "M전산_파일다운로드_";//파일명
            String strSheetname = "M전산_파일다운로드";//시트명
            
            String [] strHead = {"파일다운로드일시", "사용자ID", "사용자명", "조직ID", "조직명", "사유", "메뉴명", "파일명"};
            String [] strValue = {"fileDnDt","usrId", "usrNm", "orgnId", "orgnNm", "dwnldRsb", "menuNm", "fileNm"};
            //엑셀 컬럼 사이즈
            int[] intWidth = {6000, 4000, 6000, 4000, 6000, 8000, 8000, 14000};
            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0};
            
            //파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
            // rqstMgmtService 함수 호출
            String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
            
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
                
                pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
                pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
                pRequestParamMap.put("DUTY_NM"   ,"MSP");                       //업무명
                pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
                pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
                pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
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


