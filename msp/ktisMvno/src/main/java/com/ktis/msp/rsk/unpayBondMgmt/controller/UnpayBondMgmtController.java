package com.ktis.msp.rsk.unpayBondMgmt.controller;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rsk.unpayBondMgmt.service.UnpayBondMgmtService;
import com.ktis.msp.rsk.unpayBondMgmt.vo.UnpayBondMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class UnpayBondMgmtController extends BaseController {
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private UnpayBondMgmtService unpayBondMgmtService;
	
	@Autowired
	private FileDownService fileDownService;
	

	@Autowired
	private BtchMgmtService btchMgmtService;	

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	/** Constructor */
	public UnpayBondMgmtController() {
		setLogPrefix("[UnpayBondMgmtController] ");
	}
	
	/**
	 * 미납채권관리 화면 이동
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/rsk/unpayBondMgmt/getUnpayMgmtView.do")
	public ModelAndView getUnpayBondMgmtView(ModelMap modelMap
											, HttpServletRequest request
											, HttpServletResponse response) {
		logger.debug("=================================================================");
		logger.debug("미납채권관리 START.");
		logger.debug("=================================================================");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			// 본사 화면인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			modelAndView.setViewName("rsk/unpayBondMgmt/msp_rsk_unpBond_1001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 미납채권상세 화면 이동
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/rsk/unpayBondMgmt/unpayBondDtlView.do")
	public ModelAndView unpayBondDtlView(ModelMap modelMap
											, HttpServletRequest request
											, HttpServletResponse response) {
		logger.debug("=================================================================");
		logger.debug("미납채권상세 START.");
		logger.debug("=================================================================");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			// 본사 화면인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",
			menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			modelAndView.setViewName("rsk/unpayBondMgmt/msp_rsk_unpBond_1002");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 미납채권관리 조회
	 * @param request
	 * @param response
	 * @param model
	 * @param paramMap
	 * @param unpayBondMgmtVO
	 * @return
	 */
	@RequestMapping(value="/rsk/unpayBondMgmt/getUnpayBondList.json")
	public String getUnpayBondList(HttpServletRequest request
								, HttpServletResponse response
								, Model model
								, @RequestParam Map<String, Object> paramMap
								, @ModelAttribute("searchVO") UnpayBondMgmtVO unpayBondMgmtVO) {
		logger.debug("=================================================================");
		logger.debug("미납채권관리 조회 START.");
		logger.debug("=================================================================");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(unpayBondMgmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(unpayBondMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = unpayBondMgmtService.getUnpayBondList(unpayBondMgmtVO);
			
			resultMap = makeResultMultiRow(paramMap, list);
		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					messageSource.getMessage("ktis.msp.rtn_code.SQL_ERROR", null, Locale.getDefault()), "MSP1030109", "청구수미납현황"))
			{
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 조정금액 상세 조회
	 * @param request
	 * @param response
	 * @param model
	 * @param paramMap
	 * @param unpayBondMgmtVO
	 * @return
	 */
	@RequestMapping(value = "/rsk/unpayBondMgmt/getAdjAmntDtl.json")
	public String getAdjAmntDtl(HttpServletRequest request
							, HttpServletResponse response
							, Model model
							, @RequestParam Map<String, Object> paramMap
							, @ModelAttribute("searchVO") UnpayBondMgmtVO unpayBondMgmtVO) {
		logger.debug("=================================================================");
		logger.debug("조정금액 상세 조회 START.");
		logger.debug("=================================================================");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(unpayBondMgmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(unpayBondMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = unpayBondMgmtService.getAdjAmntDtl(unpayBondMgmtVO);
			
			resultMap = makeResultMultiRow(paramMap, list);
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.SQL_ERROR", null, Locale.getDefault()));
		}
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 미납채권관리 엑셀다운로드
	 * @param unpayBondMgmtVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rsk/unpayBondMgmt/getUnpayBondListEx.json")
	public String getUnpayBondListEx(@ModelAttribute("searchVO") UnpayBondMgmtVO unpayBondMgmtVO
									, HttpServletRequest request, HttpServletResponse response
									, @RequestParam Map<String, Object> paramMap, Model model) {

		logger.debug("=================================================================");
		logger.debug("미납채권관리 엑셀다운로드 START.");
		logger.debug("=================================================================");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		String returnMsg = null;

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(unpayBondMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(unpayBondMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = unpayBondMgmtService.getUnpayBondListEx(unpayBondMgmtVO);

			String serverInfo = propertyService.getString("excelPath");
			String strfilename = serverInfo + "청구수미납현황_";
			String strSheetname = "청구수미납현황";

			String[] strHead = {"기준월","청구월","청구항목","청구금액","청구금액부가세","총청구금액","조정금액","조정금액부가세","총조정금액","실청구금액","수납금액","수납금액부가세","총수납금액","미납금액","청구건수","수납건수","미납건수"};
			String[] strValue = {"stdrYmEx","billYmEx","saleItmNm","invAmnt","invVat","totInvAmnt","adjAmnt","adjVat","totAdjAmnt","realInvAmnt","pymAmnt","pymVat","totPymAmnt","unpdAmnt","invCnt","pymCnt","unpdCnt"};

			int[] inWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000};
			int[] inLen = {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

			String strFileName = fileDownService.excelDownProc(strfilename,strSheetname, list, strHead, inWidth, strValue, request,response, inLen);
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
				
				paramMap.put("FILE_NM", file.getName()); // 파일명
				paramMap.put("FILE_ROUT", file.getPath()); // 파일경로
				paramMap.put("DUTY_NM", "CMN"); // 업무명
				paramMap.put("IP_INFO", ipAddr); // IP정보
				paramMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				paramMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				paramMap.put("DATA_CNT", 0); // 자료건수
				paramMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); // 사유
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); // 사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
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
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 미납채권상세 조회
	 * @param request
	 * @param response
	 * @param model
	 * @param paramMap
	 * @param unpayBondMgmtVO
	 * @return
	 */
	@RequestMapping(value="/rsk/unpayBondMgmt/getUnpayBondDtlList.json")
	public String getUnpayBondDtlList(HttpServletRequest request
								, HttpServletResponse response
								, Model model
								, @RequestParam Map<String, Object> paramMap
								, @ModelAttribute("searchVO") UnpayBondMgmtVO unpayBondMgmtVO) {
		logger.debug("=================================================================");
		logger.debug("미납채권상세 조회 START.");
		logger.debug("=================================================================");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(unpayBondMgmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(unpayBondMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = unpayBondMgmtService.getUnpayBondDtlList(unpayBondMgmtVO, paramMap);
			
			resultMap = makeResultMultiRow(paramMap, list);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 조정금액 상세 조회
	 * @param request
	 * @param response
	 * @param model
	 * @param paramMap
	 * @param unpayBondMgmtVO
	 * @return
	 */
	@RequestMapping(value = "/rsk/unpayBondMgmt/getAdjRsnList.json")
	public String getAdjRsnList(HttpServletRequest request
							, HttpServletResponse response
							, Model model
							, @RequestParam Map<String, Object> paramMap
							, @ModelAttribute("searchVO") UnpayBondMgmtVO unpayBondMgmtVO) {
		logger.debug("=================================================================");
		logger.debug("조정사유 조회 START.");
		logger.debug("=================================================================");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(unpayBondMgmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(unpayBondMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = unpayBondMgmtService.getAdjRsnList(unpayBondMgmtVO);
			
			resultMap = makeResultMultiRow(paramMap, list);
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.SQL_ERROR", null, Locale.getDefault()));
		}
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	/**
	 * @Description : 청구수미납상세 자료생성
	 * @Param  : UnpayBondMgmtVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 
	 */
	@RequestMapping("/rsk/unpayBondMgmt/getUnpayBondDtlListExcelDownload.json")
	public String getOpenMgmtListExcelDwonload(@ModelAttribute("searchVO") UnpayBondMgmtVO searchVO, 
					Model model, 
					@RequestParam Map<String, Object> pRequestParamMap,
					HttpServletRequest request, 
					HttpServletResponse response)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구수미납상세 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [UnpayBondMgmtVO] = " + searchVO.toString()));
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
			excelMap.put("MENU_NM","청구수미납상세");
			String searchVal = "기준월"+searchVO.getStdrYm()+
					"|청구월:"+searchVO.getStrtYm()+"~"+searchVO.getEndYm()+
					"|검색구분:["+searchVO.getSearchType()+","+searchVO.getSearchVal()+"]"+
					"|미납여부:"+searchVO.getUnpdYn()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
						
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00036");
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
			
			vo.setExecParam("{\"stdrYm\":" + "\"" + searchVO.getStdrYm() + "\""
						+ ",\"strtYm\":" + "\"" + searchVO.getStrtYm() + "\"" 
						+ ",\"endYm\":" + "\"" + searchVO.getEndYm() + "\"" 
						+ ",\"searchType\":" + "\"" + searchVO.getSearchType() + "\"" 
						+ ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\"" 
						+ ",\"unpdYn\":" + "\"" + searchVO.getUnpdYn() + "\"" 
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
}
