package com.ktis.msp.rcp.commdatmgmt.controller;

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

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.ktis.msp.rcp.commdatmgmt.service.CommDatMgmtService;
import com.ktis.msp.rcp.commdatmgmt.vo.CommDatMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class CommDatMgmtController extends BaseController {
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private CommDatMgmtService  commDatMgmtService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	/** Constructor */
	public CommDatMgmtController() {
		setLogPrefix("[CommDatMgmtController] ");
	}
	
	/**
	 * 통신자료제공신청 화면 가기
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/rcp/commdatmgmt/CommDatView.do")
	public ModelAndView getCommDatView (ModelMap modelMap
									, HttpServletRequest request
									, HttpServletResponse response) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("통신자료제공신청 START."));
		logger.info(generateLogMsg("================================================================="));
		
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			modelAndView.setViewName("rcp/commdatmgmt/msp_rcp_cdat_1001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 통신자료제공신청 리스트 조회
	 * @param model
	 * @param request
	 * @param response
	 * @param pRequestParam
	 * @param commDatMgmtVO
	 * @return
	 */
	@RequestMapping(value = "/rcp/commdatmgmt/getCommDatePrvList.json")
	public String getCommDatePrvList(Model model, HttpServletRequest request
									, HttpServletResponse response
									, @RequestParam Map<String, Object> pRequestParam
									, @ModelAttribute("searchVO") CommDatMgmtVO commDatMgmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("통신자료제공신청 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(commDatMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParam);
			
			// 본사 화면인 경우
			if(!"10".equals(commDatMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = commDatMgmtService.getCommDatePrvList(commDatMgmtVO, pRequestParam);
		
			resultMap = makeResultMultiRow(commDatMgmtVO, list);
		} catch (Exception e) {
			//logger.error(e.getMessage());
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 통신자료제공신청 엑셀다운로드
	 * @param commDatMgmtVO
	 * @param request
	 * @param response
	 * @param pReqParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rcp/commdatmgmt/getCommDatePrvListEx.json")
	public String getCommDatePrvListEx(@ModelAttribute("searchVO") CommDatMgmtVO commDatMgmtVO
									, HttpServletRequest request, HttpServletResponse response
									, @RequestParam Map<String, Object> pReqParamMap, Model model) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("통신자료제공신청 엑셀다운로드 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
//		String returnMsg = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(commDatMgmtVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(commDatMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = commDatMgmtService.getCommDatePrvListEx(commDatMgmtVO, pReqParamMap);
			
			String serverInfo = propertyService.getString("excelPath");
			String strfilename = serverInfo + "통신자료제공신청_";
			String strSheetname = "통신자료제공신청";
			
			String[] strHead = {"처리여부","이름","생년월일","가입상품","연락처","대상번호","제공여부","수령이메일","요청기간","제공일자","요청사유","제공내역","신청일자","처리자","처리일자"};
			String[] strValue = {"resultYn","apyNm","bthday","sbscPrdtNm","cntcTelNo","tgtSvcNo","isInvstProcNm","recpEmail","confSbst01Yn","confSbst02Yn","confSbst03Yn","confSbst04Yn","apyDt","procrNm","procDt"};
			
			int[] inWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000};
			int[] inLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
			
			String strFileName = fileDownService.excelDownProc(strfilename, strSheetname, list, strHead, inWidth, strValue, request, response, inLen);
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			out = response.getOutputStream();
			
			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
 
				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"CMN");                       //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", 0);                            //자료건수
				pReqParamMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
				pReqParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID

				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
		} catch (Exception e) {
 			
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
		file.delete();
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value = "/rcp/commdatmgmt/updateResultY.json")
	public String updateResultY(@RequestBody String data, ModelMap model
								, HttpServletRequest request, HttpServletResponse response
								, @RequestParam Map<String, Object> paramMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("처리결과 Y START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<CommDatMgmtVO> list = getVoFromMultiJson(data, "items", CommDatMgmtVO.class);
			
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setResultYn("Y");
				loginInfo.putSessionToVo(list.get(i));
				
				commDatMgmtService.updateResultYN(list.get(i));
			}
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch (Exception e) {
			resultMap.clear();
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	 		resultMap.put("msg", "");
	 		throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		logger.debug("resultMap:" + resultMap);
		return "jsonView";
	}
	
	@RequestMapping(value = "/rcp/commdatmgmt/updateResultN.json")
	public String updateResultN(@RequestBody String data, ModelMap model
								, HttpServletRequest request, HttpServletResponse response
								, @RequestParam Map<String, Object> paramMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("처리결과 Y START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<CommDatMgmtVO> list = getVoFromMultiJson(data, "items", CommDatMgmtVO.class);
			
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setResultYn("N");
				loginInfo.putSessionToVo(list.get(i));
				
				commDatMgmtService.updateResultYN(list.get(i));
			}
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch (Exception e) {
			resultMap.clear();
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	 		resultMap.put("msg", "");
	 		throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		logger.debug("resultMap:" + resultMap);
		return "jsonView";
	}
	
}
