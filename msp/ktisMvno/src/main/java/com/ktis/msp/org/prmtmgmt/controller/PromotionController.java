package com.ktis.msp.org.prmtmgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.prmtmgmt.service.PromotionService;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtCopyVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.RecommenIdStateVO;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;
import com.ktis.msp.org.usimmgmt.vo.UsimMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class PromotionController extends BaseController {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	public PromotionController() {
		setLogPrefix("[PromotionController] ");
	}

	/**
	 * 채널별 요금할인 관리 화면호출
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/getChrgPrmtMgmtView.do")
	public ModelAndView getChrgPrmtMgmtView(HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> commandMap,
								ModelMap model) throws EgovBizException {
		
		logger.debug("**********************************************************");
		logger.debug("* 채널별 요금할인 관리 화면 : msp/org/prmtmgmt/msp_org_prmt_1002 *");
		logger.debug("**********************************************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			//본사화면일경우
			if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.setViewName("/org/prmtmgmt/msp_org_prmt_1002");
			
			return modelAndView;
			
		} catch (Exception e) {
			//logger.debug(e.getMessage());
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 채널별 요금 할인 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getChrgPrmtList.json")
	public String getChrgPrmtList(HttpServletRequest request, HttpServletResponse response
														, @ModelAttribute("searchVO") ChrgPrmtMgmtVO chrgPrmtMgmtVO
														, ModelMap modelMap
														, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(chrgPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(chrgPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<ChrgPrmtMgmtVO> resultList = promotionService.getChrgPrmtList(chrgPrmtMgmtVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 채널별 요금 할인 조직 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getChrgPrmtOrgnList.json")
	public String getChrgPrmtOrgnList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<ChrgPrmtMgmtSubVO> resultList = promotionService.getChrgPrmtOrgnList(chrgPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 채널별 요금 할인 요금제 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getChrgPrmtSocList.json")
	public String getChrgPrmtSocList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<ChrgPrmtMgmtSubVO> resultList = promotionService.getChrgPrmtSocList(chrgPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 채널별 요금 할인 부가서비스 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getChrgPrmtAddList.json")
	public String getChrgPrmtAddList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<ChrgPrmtMgmtSubVO> resultList = promotionService.getChrgPrmtAddList(chrgPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 채널별 요금 할인 등록
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/regChrgPrmtInfo.json")
	public String regChrgPrmtInfo(@ModelAttribute("chrgPrmtMgmtVO") ChrgPrmtMgmtVO chrgPrmtMgmtVO, 
								@RequestBody String data,
								HttpServletRequest request,
								HttpServletResponse response,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			loginInfo.putSessionToVo(chrgPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			if(!"10".equals(chrgPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			logger.info(data);
			
			ChrgPrmtMgmtVO vo = new ObjectMapper().readValue(data, ChrgPrmtMgmtVO.class);
			
			vo.setSessionUserId(chrgPrmtMgmtVO.getSessionUserId());
			
			promotionService.regChrgPrmtInfo(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
			//throw new MvnoErrorException(e);
		}
		
		modelMap.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	/**
	 *채널별 요금 할인 변경
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/updChrgPrmtByInfo.json")
	public String updChrgPrmtByInfo(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute ChrgPrmtMgmtVO chrgPrmtMgmtVO,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(chrgPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(chrgPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			promotionService.updChrgPrmtByInfo(chrgPrmtMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		
		} catch( EgovBizException e ) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		}
		
		modelMap.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	/**
	 * 채널별 요금 할인 상세 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getChrgPrmtDtlList.json")
	public String getChrgPrmtDtlList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") ChrgPrmtMgmtVO chrgPrmtMgmtVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(chrgPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(chrgPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<ChrgPrmtMgmtVO> resultList = promotionService.getChrgPrmtDtlList(chrgPrmtMgmtVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
		
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 모집경로 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getChrgPrmtOnoffList.json")
	public String getChrgPrmtOnoffList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<ChrgPrmtMgmtSubVO> resultList = promotionService.getChrgPrmtOnoffList(chrgPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	@RequestMapping("/org/prmtmgmt/copyChrgPrmtInfo.json")
	public String copyChrgPrmtInfo(@ModelAttribute ChrgPrmtMgmtCopyVO chrgPrmtMgmtCopyVO,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap) {
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(chrgPrmtMgmtCopyVO);
			
			// 본사 화면인 경우
			if(!"10".equals(chrgPrmtMgmtCopyVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			promotionService.copyChrgPrmtInfo(chrgPrmtMgmtCopyVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 추천인ID발급현황 화면호출
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/getRecommenIdStateView.do")
	public ModelAndView getRecommenIdStateView(HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> commandMap,
								ModelMap model) throws EgovBizException {
		
		logger.debug("**********************************************************");
		logger.debug("*추천인ID발급 현황 화면 : msp/org/prmtmgmt/msp_org_prmt_1003 *");
		logger.debug("**********************************************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			//본사화면일경우
			if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.setViewName("/org/prmtmgmt/msp_org_prmt_1003");
			
			return modelAndView;
			
		} catch (Exception e) {
			//logger.debug(e.getMessage());
			throw new MvnoRunException(-1, "");
		}
	}
			
	/**
	 * 추천인ID발급현황 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getRecommenIdStateList.json")
	public String getRecommenIdStateList(HttpServletRequest request, HttpServletResponse response
														, @ModelAttribute("searchVO") RecommenIdStateVO recommenIdStateVO
														, ModelMap modelMap
														, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(recommenIdStateVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(recommenIdStateVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RecommenIdStateVO> resultList = promotionService.getRecommenIdStateList(recommenIdStateVO, paramMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	* @Description : 추천인ID발급현황 엑셀 다운로드
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping("/org/prmtmgmt/getRecommenIdStateListByExcel.json")
	public String getRecommenIdStateListByExcel( @ModelAttribute("searchVO") RecommenIdStateVO recommenIdStateVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);

			List<RecommenIdStateVO> resultList = promotionService.getRecommenIdStateListByExcel(recommenIdStateVO, paramMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "추천인ID발급현황_";//파일명
			String strSheetname = "추천인ID발급현황";//시트명

			String [] strHead = { "추천인ID","계약번호","고객명","휴대폰번호","링크종류","발급일자","이동전 통신사"};

			String [] strValue = { "commendId","contractNum","subLinkName","subscriberNo","linkTypeNm","sysRdate","bfCommCmpnNm"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 10000, 7000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0};

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

			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();

				paramMap.put("FILE_NM"	,file.getName());				//파일명
				paramMap.put("FILE_ROUT" ,file.getPath());				//파일경로
				paramMap.put("DUTY_NM"	,"INSR");						//업무명
				paramMap.put("IP_INFO"	,ipAddr);						//IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				paramMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				paramMap.put("DATA_CNT", 0);							//자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId());	//사용자ID

				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
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
	 * 추천이력관리 화면
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/getRecommenHstMgmtView.do")
	public ModelAndView getRecommenHstMgmtView(HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> commandMap,
								ModelMap model) throws EgovBizException {
		
		logger.debug("**********************************************************");
		logger.debug("*추천이력관리 화면 : msp/org/prmtmgmt/msp_org_prmt_1004 *");
		logger.debug("**********************************************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			//본사화면일경우
			if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.setViewName("/org/prmtmgmt/msp_org_prmt_1004");
			
			return modelAndView;
			
		} catch (Exception e) {
			//logger.debug(e.getMessage());
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 추천이력관리 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getRecommenHstList.json")
	public String getRecommenHstList(HttpServletRequest request, HttpServletResponse response
														, @ModelAttribute("searchVO") RecommenIdStateVO recommenIdStateVO
														, ModelMap modelMap
														, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(recommenIdStateVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(recommenIdStateVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RecommenIdStateVO> resultList = promotionService.getRecommenHstList(recommenIdStateVO, paramMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}


	/**
	* @Description : 추천이력관리 엑셀 다운로드
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping("/org/prmtmgmt/getRecommenHstListByExcel.json")
	public String getRecommenHstListByExcel( @ModelAttribute("searchVO") RecommenIdStateVO recommenIdStateVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);

			List<RecommenIdStateVO> resultList = promotionService.getRecommenHstListByExcel(recommenIdStateVO, paramMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "추천이력관리_";//파일명
			String strSheetname = "추천이력관리";//시트명

			String [] strHead = {
								"추천인ID",			"링크유형",			"발급일자",					"계약번호",				"추천인",
								"휴대폰번호",		"추천인생년월일",	"현재요금제코드",			"현재요금제",			"이동전통신사",
								"유심접점",			"이벤트코드",		"계약상태",					"계약번호,",				"서비스계약번호",
								"피추천인",			"업무구분",			"이동전통신사",				"휴대폰번호",			"최초요금제코드",		
								"최초요금제",		"현재요금제코드",	"현재요금제명",				"대리점",					"구매유형",
								"신청일자",			"개통일자",			"해지일자",					"유심접점",				"성별",
								"생년월일",			"나이",				"이벤트코드",				"계약상태",				"KT인터넷가입여부",
								"추천요금제1",		"추천요금제2",		"추천요금제3"
								};

			String [] strValue = { 
								"commendId",				"linkTypeNm",					"sysRdate",					"contractNum",			"subLinkName",
								"subscriberNo",				"birthDt",							"rateCd",						"rateNm",					"bfCommCmpnNm",
								"orgnNm",					"evntCdPrmt",					"subStatus",					"dContractNum",		"dSvcCntrNo",
								"deSubLinkName",			"dOperTypeNm",				"dBfCommCmpnNm",		"deSubscriberNo",		"dFstRateCd",
								"dFstRateNm",				"dLstRateCd",					"dLstRateNm",				"dOpenAgntNm",		"dReqBuyTypeNm",
								"dReqInDay",					"dLstComActvDate",			"dSubStatusDate",			"dOrngNm",				"dGender",
								"dBirthDt",					"dAge",							"dEvntCdPrmt",				"dSubStatus",			"dInetStatus",
								"commendSocCode01",	"commendSocCode02",		"commendSocCode03"
								};

			//엑셀 컬럼 사이즈
			int[] intWidth = {
								5000, 5000, 10000, 5000, 5000,
								8000, 8000, 5000, 8000, 5000,
								5000, 5000, 5000, 7000, 7000,
								5000, 5000, 5000, 8000, 5000,
								8000, 5000, 8000, 5000, 5000,
								10000, 10000, 10000, 5000, 5000,
								8000, 5000, 5000, 5000, 5000,
								8000, 8000, 8000
							};
			int[] intLen = {
								0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, 0, 0
						};

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

			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();

				paramMap.put("FILE_NM"	,file.getName());				//파일명
				paramMap.put("FILE_ROUT" ,file.getPath());				//파일경로
				paramMap.put("DUTY_NM"	,"INSR");						//업무명
				paramMap.put("IP_INFO"	,ipAddr);						//IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				paramMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				paramMap.put("DATA_CNT", 0);							//자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId());	//사용자ID

				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
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
	 * 혜택적용 엑셀업로드 양식다운
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/org/prmtmgmt/getBenefitsTempExcelDown")
	public String getUsimMgmtTempExcelDown(@ModelAttribute("searchVO") RecommenIdStateVO searchVO,
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
			
			List<?> resultList = new ArrayList<UsimMgmtVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "혜택적용등록엑셀양식_";//파일명
			String strSheetname = "혜택적용등록엑셀양식";//시트명
			
			String [] strHead = {"피추천인계약번호"};
			String [] strValue = {"dContractNum"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {8000};
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
	 * @Description : 혜택적용대상 엑셀업로드 목록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/org/prmtmgmt/benefitsUpList.json")
	public String benefitsUpList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("searchVO") RecommenIdStateVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pRequestParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();
			
			String[] arrCell = {"uploadContractNum"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.org.prmtmgmt.vo.RecommenIdStateVO", sOpenFileName, arrCell);
			
			resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, uploadList, uploadList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	/**
	 * @Description : 혜택적용대상 엑셀등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/org/prmtmgmt/regBenefitsList.json")
	public String regBenefitsList(@ModelAttribute("searchVO") RecommenIdStateVO searchVO, 
					@RequestBody String data,
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("혜택적용대상 엑셀등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			RecommenIdStateVO vo = new ObjectMapper().readValue(data, RecommenIdStateVO.class);
			
			promotionService.regBenefitsList(vo, loginInfo.getUserId());

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP2002410", "추천이력관리")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}

	/**
	 * @Description : 채널별 요금 할인 엑셀 자료생성
	*/
	@RequestMapping("/org/prmtmgmt/getChrgPrmtListExcelDown.json")
	public String getChrgPrmtListExcelDown(@ModelAttribute("searchVO") ChrgPrmtMgmtVO searchVO,
					Model model,
					UserInfoMgmtVo userInfoMgmtVo,
					HttpServletRequest request,
					HttpServletResponse response)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("채널별 요금 할인 엑셀 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [ChrgPrmtMgmtVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoRunException(-1, "엑셀 다운로드 권한 미충족");
			}

			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","CHRG");
			excelMap.put("MENU_NM","채널별요금할인관리");
			String searchVal = "기준일자:"+searchVO.getSearchBaseDate()+
					"|프로모션명:"+searchVO.getPrmtNm()+
					"|채널유형:"+searchVO.getOrgnType()+
					"|구매유형:"+searchVO.getReqBuyType()+
					"|모집경로:"+searchVO.getOnOffType()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();


			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00216");
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

			vo.setExecParam("{\"searchBaseDate\":" + "\"" + searchVO.getSearchBaseDate() + "\""
						+ ",\"prmtNm\":" + "\"" + searchVO.getPrmtNm() + "\""
						+ ",\"orgnType\":" + "\"" + searchVO.getOrgnType() + "\""
						+ ",\"reqBuyType\":" + "\"" + searchVO.getReqBuyType() + "\""
						+ ",\"onOffType\":" + "\"" + searchVO.getOnOffType() + "\""
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
	 * @Description : 채널별 요금할인 엑셀 업로드 양식 다운로드
	 */
	@RequestMapping(value="/org/prmtmgmt/getChrgTempExcelDown.json")
	public String getChrgTempExcelDown(@ModelAttribute("chrgPrmtMgmtVO") ChrgPrmtMgmtVO chrgPrmtMgmtVO,
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
			loginInfo.putSessionToVo(chrgPrmtMgmtVO);

			// 본사 화면인 경우
			if(!"10".equals(chrgPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = new ArrayList<ChrgPrmtMgmtVO>();

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "채널별요금할인일괄등록엑셀양식_";//파일명
			String strSheetname = "채널별요금할인일괄등록엑셀양식";//시트명

			String [] strHead = {"번호", "프로모션명", "시작일자","종료일자",
								 "채널유형", "구매유형", "신규", "번호이동",
								 "약정기간_무약정", "약정기간_12개월", "약정기간_18개월", "약정기간_24개월", "약정기간_30개월", "약정기간_36개월",
								 "대상조직", "대상요금제", "모집경로", "대상부가서비스"};
			String [] strValue = {"regNum", "prmtNm", "strtDt","endDt",
								  "orgnType", "reqBuyType", "nacYn", "mnpYn",
								  "enggCnt0", "enggCnt12", "enggCnt18", "enggCnt24", "enggCnt30", "enggCnt36",
								  "orgnId", "rateCd","onOffType" ,"soc"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {1500, 8000, 3000, 3000,
							  3000, 3000, 2000, 3000,
							  5000, 5000, 5000, 5000, 5000, 5000,
							  3000, 4000, 3000, 5000};
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
	 * @Description : 채널별요금할인 정책 엑셀 업로드 파일 읽기
	 */
	@RequestMapping(value="/org/prmtmgmt/readChrgExcelUpList.json")
	public String readChrgExcelUpList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("chrgPrmtMgmtVO") ChrgPrmtMgmtVO chrgPrmtMgmtVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(chrgPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);

			if(!"10".equals(chrgPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");

			String sOpenFileName = baseDir + "/CMN/" + chrgPrmtMgmtVO.getFileName();

			String[] arrCell = {"regNum", "prmtNm", "strtDt","endDt",
						        "orgnType", "reqBuyType", "nacYn", "mnpYn",
							    "enggCnt0", "enggCnt12", "enggCnt18", "enggCnt24", "enggCnt30", "enggCnt36",
							    "orgnId", "rateCd","onOffType" ,"soc"};

			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO", sOpenFileName, arrCell);

			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, uploadList, uploadList.size());



		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 채널별요금할인 엑셀등록(List)
	 * @Param  : chrgPrmtMgmtVO
	 * @Param  : data
	 * @Param  : pRequest
	 * @Param  : pResponse
	 * @Param  : pRequestParamMap
	 * @Param  : model
	 * @Return : String
	 * @Author : 김동혁
	 * @Create Date : 2023.11.10.
	 */

	@RequestMapping("/org/prmtmgmt/regChrgPrmtInfoExcel.json")
	public String regChrgPrmtInfoExcel(@ModelAttribute("chrgPrmtMgmtVO") ChrgPrmtMgmtVO chrgPrmtMgmtVO,
			@RequestBody String data,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(chrgPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if(!"10".equals(chrgPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			ChrgPrmtMgmtVO vo = new ObjectMapper().readValue(data, ChrgPrmtMgmtVO.class);
			vo.setSessionUserId(chrgPrmtMgmtVO.getSessionUserId());

			int resultCnt = promotionService.regChrgPrmtInfoExcel(vo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", resultCnt+"건 등록되었습니다");


		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 채널별 요금할인 목록 콤보 조회
	 * @Param  : void
	 * @Return : String
	 * @Author : 김동혁
	 */
    @RequestMapping(value="/org/prmtmgmt/getChrgPrmtListCombo.json")
    public String getChrgPrmtListCombo(@ModelAttribute("searchVO") RcpDetailVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("채널별 요금할인 콤보 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            List<EgovMap> prmtList = promotionService.getChrgPrmtListCombo(searchVO);

            Map<String, String> prmtMap = new HashMap<String, String>();

            if(KtisUtil.isNotEmpty(prmtList)) {
             prmtMap.put("prmtId", (String) prmtList.get(0).get("prmtId"));
             prmtMap.put("prmtNm", (String) prmtList.get(0).get("prmtNm"));
             prmtMap.put("disCnt", (String) prmtList.get(0).get("disCnt"));
            }

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("prmtMap", prmtMap);

        } catch (Exception e) {
        	resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
	}
	
    /**
	 * @Description : List 프로모션 엑셀 다운로드
	 */
	@RequestMapping("/org/prmtmgmt/getChoChrgPrmtListExcelDown.json")
   	public String getChoChrgPrmtListExcelDown(@ModelAttribute("searchVO") ChrgPrmtMgmtVO searchVO,
			@RequestBody String data,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> paramMap) {
    	
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
						
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String prmtIdListParam = request.getParameter("prmtIdList");
			
			if (KtisUtil.isNotEmpty(prmtIdListParam)) {
			    List<String> prmtIdList = Arrays.asList(prmtIdListParam.split(","));
			    searchVO.setPrmtIdList(prmtIdList);
			}
			
			List<?> resultList = promotionService.getChoChrgPrmtListExcelDown(searchVO);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "프로모션정책_";//파일명
			String strSheetname = "프로모션정책";//시트명

			String [] strHead = { 
								"순번",					"프로모션명",			"시작일자",				"종료일자",				"채널유형",
								"구매유형",				"신규",					"번호이동",				"약정기간_무약정",		"약정기간_12개월",
								"약정기간_18개월",		"약정기간_24개월",		"약정기간_30개월",		"약정기간_36개월",		"대상조직",
								"대상요금제",			"모집경로",				"대상부가서비스"
								};

			String [] strValue = { 
								"rnum",			"prmtNm",		"strtDt",			"endDt",			"orgnType",
								"reqBuyType",	"nacYn",			"mnpYn",			"enggCnt0",		"enggCnt12",
								"enggCnt18",	"enggCnt24",	"enggCnt30",	"enggCnt36",	"orgnId",
								"rateCd",			"onOffType",		"soc"
								};

			//엑셀 컬럼 사이즈
			int[] intWidth = {
							1500, 13000, 3000, 3000, 3000,
							3000, 3000, 5000, 5000, 5000,
							5000, 5000, 5000, 5000, 3000,
							4000, 3000, 5000
							};
			int[] intLen = {
							0, 0, 0, 0, 0,
							0, 0, 0, 0, 0,
							0, 0, 0, 0, 0,
							0, 0, 0
							};

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
	 * @Description : List  종료일 변경
	 */
	@RequestMapping("/org/prmtmgmt/updPrmtEndDttm.json")
	public String updPrmtEndDttm(@ModelAttribute("searchVO") ChrgPrmtMgmtVO searchVO,
			@RequestBody String data,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> paramMap) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			JSONObject jSONObject = (JSONObject) new JSONParser().parse(data);
			
			if(KtisUtil.isNotEmpty(jSONObject)){
				searchVO.setPrmtIdList((List<String>) jSONObject.get("prmtIdList"));
				searchVO.setEndDt((String) jSONObject.get("endDt"));
				searchVO.setChngTypeCd((String) jSONObject.get("chngTypeCd"));
			}
			
			promotionService.updPrmtEndDttm(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage()) ;
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	/**
	 * @Description : List 대리점 추가	
	 */
    
	@RequestMapping("/org/prmtMgmt/setChrgPrmtOrgAdd.json")
	public String setChrgPrmtOrgAdd(@ModelAttribute("searchVO") ChrgPrmtMgmtVO searchVO,
			@RequestBody String data,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> paramMap) {
    	
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
						
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			JSONObject jSONObject = (JSONObject) new JSONParser().parse(data);
			
			if(KtisUtil.isNotEmpty(jSONObject)){
				searchVO.setPrmtIdList((List<String>) jSONObject.get("prmtIdList"));
				searchVO.setOrgnIdList((List<String>) jSONObject.get("orgnList"));
			}
			
			promotionService.setChrgPrmtOrgAdd(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage()) ;
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
}
