package com.ktis.msp.rcp.canCustMgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.rcp.canCustMgmt.vo.CanBatchVo;
import com.ktis.msp.util.ExcelFilesUploadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rcp.canCustMgmt.service.CanCustService;
import com.ktis.msp.rcp.canCustMgmt.vo.CanCustVO;
import com.ktis.msp.rcp.familyMgmt.vo.FamilyMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.statsMgmt.vo.StatsMgmtVo;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.stringtemplate.v4.ST;

/**
 * @Class Name : CanCustController
 * @Description : 해지 고객 정보 화면 프로세스
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.08.10  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 8. 10.
 */

@Controller
public class CanCustController extends BaseController {
	
	/** 해지고객 서비스 */
	@Autowired
	private CanCustService canCustService;

	/** 메뉴 권한 서비스 */
	@Autowired
    private MenuAuthService  menuAuthService; 

	/** 마스킹 서비스 */
	//v2018.11 PMD 적용 소스 수정
	/*	@Autowired
    private MaskingService maskingService;*/

	@Autowired
	private LoginService loginService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 공통 Utill 서비스 */
	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;	

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	/** Constructor */
	public CanCustController() {
		setLogPrefix("[CanCustController] ");
	}

	/**
	 * @Description : 해지 고객 화면
	 * @Param  : CanCustVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 8. 10.
	 */
	@RequestMapping(value = "/rcp/canCustMgmt/searchCanInfo.do", method={POST, GET})
    public ModelAndView searchPayInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg(" 해지 고객 화면 START."));
        logger.info(generateLogMsg("================================================================="));
        
        ModelAndView modelAndView = new ModelAndView();
        
        LoginInfo loginInfo = new LoginInfo(request, response);

        try {

    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());
    		
            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            modelAndView.setViewName("rcp/canCustMgmt/msp_rcp_bs_0001");
        } catch (Exception e) {
            //e.printStackTrace();
        	throw new MvnoErrorException(e);
        }
        return modelAndView;
    }
	
	/**
	 * @Description : 해지 고객 조회
	 * @Param  : CanCustVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 8. 10.
	 */
	@RequestMapping("/rcp/canCustMgmt/selectCanCustList.json")
	public String orgPayInfoList(HttpServletRequest request, HttpServletResponse response, CanCustVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 해지 고객 조회 START"));
		logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {

	        LoginInfo loginInfo = new LoginInfo(request, response);
	        loginInfo.putSessionToParameterMap(pRequestParamMap);
	
	        searchVO.setSessionUserId(loginInfo.getUserId());
	        
			@SuppressWarnings("unchecked")
			List<EgovMap> resultList = ((List<EgovMap>) canCustService.selectCanCustList(searchVO, pRequestParamMap));
			
			resultMap =  makeResultMultiRow(searchVO, resultList);
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
		    resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000321", "해지자조회"))
			{
                //logger.error(e);
				throw new MvnoErrorException(e);
			}    
        
		}

		return "jsonView";
	}
	
	/**
	 * @Description : 해지 고객 엑셀 다운로드(배치)
	 * @Param  : CanCustVO
	 * @Return : void
	 * @Author : 박준성
	 * @Create Date : 2017. 03. 16.
	 */
	@RequestMapping("/rcp/canCustMgmt/getCanCustListExcelDwonload.json")
	public String getCanCustListExcelDwonload(HttpServletRequest request, HttpServletResponse response, CanCustVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 해지 고객 엑셀다운로드 START"));
		logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
		String returnMsg = null;
		
        try {

			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","해지자조회");
			String searchVal = "해지일자:"+searchVO.getSearchStartDt()+"~"+searchVO.getSearchEndDt()+
					"|대리점:"+searchVO.getCntpntShopId()+
					"|검색구분:["+searchVO.getSearchGbn()+","+searchVO.getSearchName()+"]"
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			
			
			BatchJobVO vo = new BatchJobVO();
						
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00082");
			vo.setSessionUserId(loginInfo.getUserId());			
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"searchStartDt\":" + "\"" + searchVO.getSearchStartDt() + "\""
						+ ",\"searchEndDt\":" + "\"" + searchVO.getSearchEndDt() + "\"" 
						+ ",\"cntpntShopId\":" + "\"" + searchVO.getCntpntShopId() + "\""
						+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\"" 
						+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\"" 
						+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" 
						+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\"" 
						+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" 
						+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\"" 
						+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}" 
						
					);
			
			btchMgmtService.insertBatchRequest(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {

			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	
	/**
	 * @Description : 해지복구 고객 화면
	 * @Param  : HttpServletRequest
	 * @Return : HttpServletResponse
	 * @Author : 장준화
	 * @Create Date : 2019. 3. 26.
	 */
	@RequestMapping(value = "/rcp/canCustMgmt/searchRclInfo.do", method={POST, GET})
    public ModelAndView searchRclInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg(" 해지복구 고객 화면 START."));
        logger.info(generateLogMsg("================================================================="));
        
        ModelAndView modelAndView = new ModelAndView();
        
        LoginInfo loginInfo = new LoginInfo(request, response);

        try {

    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());
    		
            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            modelAndView.setViewName("rcp/canCustMgmt/msp_rcp_bs_0002");
        } catch (Exception e) {
            //e.printStackTrace();
        	throw new MvnoErrorException(e);
        }
        return modelAndView;
    }
	
	/**
	 * @Description : 해지복구 고객 조회
	 * @Param  : CanCustVO
	 * @Return : void
	 * @Author : 장준화
	 * @Create Date : 2019. 3. 26.
	 */
	@RequestMapping("/rcp/canCustMgmt/selectRclCustList.json")
	public String selectRclCustList(HttpServletRequest request, HttpServletResponse response, CanCustVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 해지복구 고객 조회 START"));
		logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {

	        LoginInfo loginInfo = new LoginInfo(request, response);
	        loginInfo.putSessionToParameterMap(pRequestParamMap);
	
	        searchVO.setSessionUserId(loginInfo.getUserId());
	        
			@SuppressWarnings("unchecked")
			List<EgovMap> resultList = ((List<EgovMap>) canCustService.selectRclCustList(searchVO, pRequestParamMap));
			
			resultMap =  makeResultMultiRow(searchVO, resultList);
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
		    resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000321", "해지자조회"))
			{
                //logger.error(e);
				throw new MvnoErrorException(e);
			}    
        
		}

		return "jsonView";
	}
	
	/**
	 * @Description : 해지복구 고객 엑셀 다운로드(배치)
	 * @Param  : CanCustVO
	 * @Return : void
	 * @Author : 장준화
	 * @Create Date : 2019. 03. 26.
	 */
	@RequestMapping("/rcp/canCustMgmt/getRclCustListExcelDwonload.json")
	public String getRclCustListExcelDwonload(HttpServletRequest request, HttpServletResponse response, CanCustVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 해지복구 고객 엑셀다운로드 START"));
		logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
		String returnMsg = null;
		
        try {

			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","해지복구자조회");
			String searchVal = "복구일자:"+searchVO.getSearchStartDt()+"~"+searchVO.getSearchEndDt()+
					"|검색구분:["+searchVO.getSearchGbn()+","+searchVO.getSearchName()+"]"
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			
			
			BatchJobVO vo = new BatchJobVO();
						
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00154");
			vo.setSessionUserId(loginInfo.getUserId());			
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"searchStartDt\":" + "\"" + searchVO.getSearchStartDt() + "\""
						+ ",\"searchEndDt\":" + "\"" + searchVO.getSearchEndDt() + "\"" 
						+ ",\"cntpntShopId\":" + "\"" + searchVO.getCntpntShopId() + "\""
						+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\"" 
						+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\"" 
						+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" 
						+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\"" 
						+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" 
						+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\"" 
						+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}" 
						
					);
			
			btchMgmtService.insertBatchRequest(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 일괄직권해지 화면
	 */
	@RequestMapping(value = "/rcp/canCustMgmt/canBatchMgmt.do", method = {POST, GET})
	public String canBatchMgmt(@ModelAttribute("searchVO") CanBatchVo searchVO, HttpServletRequest pRequest,
							   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/canCustMgmt/canBatchMgmt";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}


	/**
	 * @Description 일괄직권해지 목록 조회
	 */
	@RequestMapping(value = "/rcp/canCustMgmt/getCanBatchList.json")
	public String getCanBatchList(@ModelAttribute("searchVO") CanBatchVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			pRequestParamMap.put("SESSION_USER_MASKING",maskingYn);	//마스킹 여부

			List<EgovMap> resultList = canCustService.getCanBatchList(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010054", "평생할인 자동가입 검증")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 일괄직권해지 엑셀 업로드 양식 다운로드
	 */
	@RequestMapping(value="/rcp/canCustMgmt/getCanBatchExcelDown.json")
    public String getSmsTmpExcelDown(@ModelAttribute("searchVO") CanBatchVo searchVO,
									 HttpServletRequest request,
									 HttpServletResponse response,
									 @RequestParam Map<String, Object> pRequestParamMap,
									 ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);

			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = new ArrayList<CanBatchVo>();

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "일괄해지엑셀업로드양식_";//파일명
			String strSheetname = "일괄해지엑셀업로드양식";//시트명

			String [] strHead = {"계약번호"};
			String [] strValue = {"contractNum"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000};
			int[] intLen = {};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
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
	 * @Description : 일괄직권해지 목록 엑셀 다운로드
	 */
	@RequestMapping("/rcp/canCustMgmt/getCanBatchListExcelDown.json")
	@ResponseBody
	public String getCanBatchListExcelDown(@ModelAttribute("searchVO") CanBatchVo searchVO, ModelMap model, HttpServletRequest request, HttpServletResponse response,
										   @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("일괄직권해지 엑셀 저장 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			pRequestParamMap.put("SESSION_USER_MASKING",maskingYn);	//마스킹 여부

			List<?> resultList = canCustService.getCanBatchListExcelDown(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");

			String strFilename = serverInfo + "일괄직권해지_";// 파일명
			String strSheetname = "일괄직권해지";// 시트명

			// 엑셀 첫줄
            String[] strHead = {"등록일자","계약번호","전화번호","회선상태","해지처리일","처리상태","해지코드","해지불가시 사유","처리자","처리일시"}; //10

            String[] strValue = {"regstDttm","contractNum","subscriberNo","subStatus","tcpRsltDt","procStatus","canCode","canErrorMsg", "reqNm", "procDttm"}; //10

			// 엑셀 컬럼 사이즈
            int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 20000, 5000, 5000}; //10

            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //9

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CANBATCH"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
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
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 직권해지 일괄 엑셀 등록
	 */
	@RequestMapping("/rcp/canCustMgmt/insertCanBatch.json" )
	public String insertCanBatch(@ModelAttribute("searchVO") CanBatchVo searchVO,
								 HttpServletRequest request,
								 HttpServletResponse response,
								 @RequestParam Map<String, Object> pRequestParamMap,
								 ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();


		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);

			String resultMsg = null;

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}


			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();
			String[] arrCell = {"contractNum"};

			searchVO.setSessionUserId(loginInfo.getUserId());

			/* 엑셀업로드가 된 데이터를 가져와서 리스트로 만든다 */
			List<Object> uploadList =
					ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.canCustMgmt.vo.CanBatchVo", sOpenFileName, arrCell);

			if (uploadList.size() > 20000 ) {
				resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMsg = "엑셀업로드는 2만건 이하로 등록해주세요.";
			}else {
				resultMap.clear();

				canCustService.insertCanBatch(uploadList, searchVO);

				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
				resultMap.put("msg", "");
			}
			resultMap.put("msg", resultMsg);

			model.clear();
			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP1060001", "일괄직권해지")) {
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
	 * @Description : 일괄 직권 해지 배치 요청 저장
	 */
	@RequestMapping("/rcp/canCustMgmt/retryCanBatchRequest.json")
	public String retryBatchRequest(@ModelAttribute BatchJobVO vo,
									ModelMap model,
									HttpServletRequest request,
									HttpServletResponse response,
									@RequestParam Map<String, Object> paramMap)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			/* 로그인정보체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);

			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String batchReqCount = canCustService.getCanBatchRequestCount();
			if(!StringUtils.isEmpty(batchReqCount)){
				throw new MvnoServiceException("일괄해지실행 요청이 이미 존재합니다.");
			}

			String targetCount = canCustService.getCanBatchTargetCount();
			//미처리 대상이 존재하지 않을 경우
			if(StringUtils.isEmpty(targetCount)){
				throw new MvnoServiceException("처리할 대상이 존재하지 않습니다.");
			}

			vo.setBatchJobId("BATCH00233");
			vo.setExecTypeCd("REQ");

			Date toDay = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String reqDttm = sdf1.format(toDay);

			vo.setExecParam("{\"reqId\":" + "\"" + vo.getSessionUserId() + "\""
					+ ",\"reqDttm\":" + "\"" + reqDttm + "\"}"
			);

			btchMgmtService.insertBatchRequest(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";

	}
	
	/**
	 * @Description : 해지상담신청
	 * @Return : void
	 * @Author : 이승국
	 * @Create Date : 2025. 9. 22.
	 */
	@RequestMapping(value = "/rcp/canCanCslMgmt/searchCanCslInfo.do", method={POST, GET})
    public ModelAndView searchCanCslInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg(" 해지상담신청 화면 START."));
        logger.info(generateLogMsg("================================================================="));
        
        ModelAndView modelAndView = new ModelAndView();
        
        LoginInfo loginInfo = new LoginInfo(request, response);

        try {

    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());
    		
    		String scnUrl = propertiesService.getString("mcp.url");
			String scanSearchUrl = propertiesService.getString("scan.search.url");
			String faxSearchUrl = propertiesService.getString("fax.search.url");
			String scanDownloadUrl = propertiesService.getString("scan.download.url");
			
			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i = 0; i < macInfoList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String) map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");    // 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");        // 서버환경 (로컬 : L, 개발 : D, 운영 : R)
    		
            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
            modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
            
            modelAndView.setViewName("rcp/canCustMgmt/msp_rcp_bs_0003");
        } catch (Exception e) {
            //e.printStackTrace();
        	throw new MvnoErrorException(e);
        }
        return modelAndView;
    }
	
	/**
	 * @Description : 해지상담신청 고객 조회
	 * @Param  : CanCustVO
	 * @Return : void
	 * @Author : 이승국
	 * @Create Date : 2025. 9. 22.
	 */
	@RequestMapping("/rcp/canCanCslMgmt/selectCanCslList.do")
	public String selectCanCslList(HttpServletRequest request, HttpServletResponse response, CanCustVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 해지상담신청 고객 조회 START"));
		logger.info(generateLogMsg("================================================================="));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {

	        LoginInfo loginInfo = new LoginInfo(request, response);
	        loginInfo.putSessionToParameterMap(pRequestParamMap);
	        searchVO.setSessionUserId(loginInfo.getUserId());
	        
			@SuppressWarnings("unchecked")
			List<EgovMap> resultList = ((List<EgovMap>) canCustService.selectCanCslList(searchVO, pRequestParamMap));
			
			resultMap =  makeResultMultiRow(searchVO, resultList);
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
		    resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000324", "해지상담신청"))
			{
                //logger.error(e);
				throw new MvnoErrorException(e);
			}    
        
		}
		return "jsonView";
	}
	
	/**
	 * @Description : 해지상담신청 목록 엑셀 다운로드
	 */
	@RequestMapping("/rcp/canCanCslMgmt/selectCanCslListByExcel.json")
	@ResponseBody
	public String selectCanCslListByExcel(@ModelAttribute("searchVO") CanCustVO searchVO, ModelMap model, HttpServletRequest request, HttpServletResponse response,
										   @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지상담신청 엑셀 다운로드 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			pRequestParamMap.put("SESSION_USER_MASKING",maskingYn);	//마스킹 여부

			List<?> resultList = canCustService.selectCanCslListByExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");

			String strFilename = serverInfo + "해지상담신청_";// 파일명
			String strSheetname = "해지상담신청";// 시트명

			// 엑셀 첫줄
			String[] strHead = {
									"계약번호",			"고객명",				"생년월일",		"성별",			"해지신청번호",
									"연락가능연락처",	"현재요금제코드",	"현재요금제",	"단말모델",		"단말일련번호",
									"해지사유",			"신청일자",			"개통일자",		"개통대리점",	"해지일자",
									"처리결과",			"메모",				"수정자",			"수정일시",		"Q1",
									"A1",					"A1 상세",			"Q2",				"A2",				"Q3",
									"A3"
			}; //26

			String[] strValue = {
									"contractNum",			"cstmrName",		"birth",			"gender",				"cancelMobileNo",
									"receiveMobileNo",		"lstRateCd",			"lstRateNm",		"lstModelNm", 		"lstModelSrlNum",
									"canRsn",					"regstDttm",			"openDt",			"openAgntNm",		"canDt",
									"procCdNm",			"memo",				"rvisnNm",		"rvisnDttm",			"q1",
									"survey1Text",			"survey2Text",		"q2",				"surveyScore",		"q3",
									"surveySuggestion"
									
			}; //26

			// 엑셀 컬럼 사이즈
			int[] intWidth = {
									5000, 5000, 5000, 3000, 7000, 
									7000, 5000, 20000, 5000, 5000,
									20000, 7000, 5000, 5000, 5000,
									5000, 20000, 5000, 7000, 5000,
									8000, 8000, 5000, 3000, 5000,
									8000
									
			}; //26

			int[] intLen = {
									0, 0, 0, 0, 0,
									0, 0, 0, 0, 0,
									0, 0, 0, 0, 0,
									0, 0, 0, 0, 0,
									0, 0, 0, 0, 0,
									0
			}; //26

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
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
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 해지상담신청 상태 처리 및 서식지 합본
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2025. 11. 07.
	 */
	@RequestMapping("/rcp/canCanCslMgmt/getCustStatus.json")
	public String getCustStatus(CanCustVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse,
		 						@RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String chkStatus = canCustService.getCustStatus(searchVO);
			if("CP".equals(chkStatus)){
				resultMap.put("msg", "NOK");
			}else{
				resultMap.put("msg", "OK");
			}

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
        return "jsonView";
	}
	
	/**
	 * @Description : 해지상담신청 상태 처리 및 서식지 합본
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2025. 09. 23.
	 */
	@RequestMapping("/rcp/canCanCslMgmt/updateCanCsl.json")
	public String updateCanCsl(CanCustVO searchVO, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse,
							 @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			int resultCnt = canCustService.updateCanCsl(searchVO, loginInfo.getUserId());

			if (resultCnt == 0) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMap.put("msg", "변경대상이 없습니다.");
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
				resultMap.put("msg", "");
			}

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);

		return "jsonView";
	}
}