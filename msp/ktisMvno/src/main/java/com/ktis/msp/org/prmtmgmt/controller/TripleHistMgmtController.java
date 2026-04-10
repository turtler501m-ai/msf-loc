package com.ktis.msp.org.prmtmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.prmtmgmt.service.TripleHistMgmtService;
import com.ktis.msp.org.prmtmgmt.vo.TripleHistMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class TripleHistMgmtController extends BaseController {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private TripleHistMgmtService tripleHistMgmtService;
	
	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private FileDownService fileDownService;
	
	/**
	 * @Description : 트리플이력관리 화면 호출
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 20.
	 */
	@RequestMapping(value = "/org/prmtmgmt/getTripleHistMgmtView.do", method={POST, GET})
	public ModelAndView getTripleHistMgmtView(HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> commandMap,
			ModelMap model) throws EgovBizException {

		logger.debug("**********************************************************");
		logger.debug("* 트리플이력관리 : msp/org/prmtmgmt/msp_org_prmt_1008 *");
		logger.debug("**********************************************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			// 본사 권한
			if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			model.addAttribute("startDate", orgCommonService.getWantDay(-7));
			model.addAttribute("endDate", orgCommonService.getToDay());
			modelAndView.setViewName("org/prmtmgmt/msp_org_prmt_1008");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 트리플 이력관리 리스트 조회
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 20.
	 */
	@RequestMapping("/org/prmtmgmt/getTripleHistMgmtList.json")
	public String getTripleHistMgmtList(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute("searchVO") TripleHistMgmtVO tripleHistMgmtVO
			, ModelMap modelMap
			, @RequestParam Map<String, Object> paramMap) throws EgovBizException {

		logger.debug("**********************************************************");
		logger.debug("* 트리플 이력관리 리스트 조회 *");
		logger.debug("**********************************************************");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(tripleHistMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 권한
			if(!"10".equals(tripleHistMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = tripleHistMgmtService.getTripleHistList(tripleHistMgmtVO);
			
			resultMap =  makeResultMultiRow(paramMap, resultList);
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		return "jsonView";
	}
	
	/**
	 * @Description : 트리플 이력관리 엑셀 다운로드
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/org/prmtmgmt/getTripleHistMgmtListExcel.json")
	@ResponseBody
	public String getStatsInetListExcel(TripleHistMgmtVO searchVO, ModelMap model, HttpServletRequest pRequest,
										  HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//날짜 세팅
			if(KtisUtil.isEmpty(searchVO.getSrchStrtDt())){
				searchVO.setSrchStrtDt(orgCommonService.getWantDay(-1));
			}

			// 엑셀 데이터 조회
			List<?> resultList = tripleHistMgmtService.getTripleHistListExcel(searchVO);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "트리플이력관리_";    // 파일명
			String strSheetname = "트리플이력관리";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"계약번호","고객명","휴대폰번호","생년월일","신청 시 요금제","현요금제","혜택(부가서비스)","개통일","KT인터넷ID","인터넷설치일자","신청경로","신청일자","최초 성공여부","실패사유","처리여부","처리일시","처리자"}; //17
			String[] strValue = {"contractNum","subLinkName","subscriberNo","userSsn","rateNm","lstRateNm","additionNm","lstComActvDate","ktInternetId","installDd","appRoute","cretDt","succYn","reasonFail","procYn","procDt","procNm"}; //17

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 7000, 7000, 7000, 10000, 10000, 10000, 7000, 7000, 7000, 7000, 7000, 7000, 15000, 5000, 7000, 7000}; //17
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //17

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

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

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 트리플 이력관리 계약번호 조회
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 20.
	 */
	@RequestMapping("/org/prmtmgmt/getTripleHistContractNum.json")
	public String getTripleHistContractNum(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute("searchVO") TripleHistMgmtVO tripleHistMgmtVO
			, ModelMap modelMap
			, @RequestParam Map<String, Object> paramMap) throws EgovBizException {

		logger.debug("**********************************************************");
		logger.debug("* 트리플 이력관리 계약번호 조회 *");
		logger.debug("**********************************************************");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(tripleHistMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 권한
			if(!"10".equals(tripleHistMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<Map<String, Object>> list = tripleHistMgmtService.getTripleHistContractNum(tripleHistMgmtVO);
			tripleHistMgmtVO.setRateCd(list.get(0).get("lstRateCd").toString());
			List<Map<String, Object>> additionList = tripleHistMgmtService.getTriplePrmtData(tripleHistMgmtVO);
			if(additionList != null && !additionList.isEmpty()){
				list.get(0).put("additionCd", additionList.get(0).get("additionCd"));
				list.get(0).put("additionNm", additionList.get(0).get("additionNm"));
			}else{
				list.get(0).put("additionCd", "");
				list.get(0).put("additionNm", "");
			}

			resultMap = makeResultMultiRow(tripleHistMgmtVO, list);
			
		} catch (Exception e) {
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 트리플 이력관리 요금제 조회
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 20.
	 */
	@RequestMapping("/org/prmtmgmt/getTripleHistRate.json")
	public String getTripleHistRate(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute("searchVO") TripleHistMgmtVO tripleHistMgmtVO
			, ModelMap modelMap
			, @RequestParam Map<String, Object> paramMap) throws EgovBizException {

		logger.debug("**********************************************************");
		logger.debug("* 트리플 이력관리 요금제 조회 *");
		logger.debug("**********************************************************");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(tripleHistMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 권한
			if(!"10".equals(tripleHistMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = tripleHistMgmtService.getTripleHistRate(tripleHistMgmtVO);
			resultMap = makeResultMultiRow(tripleHistMgmtVO, list);
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		return "jsonView";
	}
	
	/**
	 * @Description : 트리플 이력관리 부가서비스 조회
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 20.
	 */
	@RequestMapping("/org/prmtmgmt/getTripleHistAddition.json")
	public String getTripleHistAddition(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute("searchVO") TripleHistMgmtVO tripleHistMgmtVO
			, ModelMap modelMap
			, @RequestParam Map<String, Object> paramMap) throws EgovBizException {

		logger.debug("**********************************************************");
		logger.debug("* 트리플 이력관리 부가서비스 조회 *");
		logger.debug("**********************************************************");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(tripleHistMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 권한
			if(!"10".equals(tripleHistMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = tripleHistMgmtService.getTripleHistAddition(tripleHistMgmtVO);
			resultMap = makeResultMultiRow(tripleHistMgmtVO, list);
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		return "jsonView";
	}
	
	/**
	 * @Description : 트리플 이력관리 직접등록
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 20.
	 */
	@RequestMapping("/org/prmtmgmt/insertTripleHist.json")
	public String insertTripleHist(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute("searchVO") TripleHistMgmtVO tripleHistMgmtVO
			, ModelMap model
			, @RequestParam Map<String, Object> paramMap) throws EgovBizException {

		logger.debug("**********************************************************");
		logger.debug("* 트리플 이력관리 직접등록 *");
		logger.debug("**********************************************************");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(tripleHistMgmtVO);
			tripleHistMgmtVO.setRip(request.getRemoteAddr());
			tripleHistMgmtVO.setRegId(loginInfo.getUserId());    /** 사용자ID */
			tripleHistMgmtVO.setRvisnId(loginInfo.getUserId());
			tripleHistMgmtService.insertTripleHist(tripleHistMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 트리플 이력관리 처리상태변경
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 20.
	 */
	@RequestMapping("/org/prmtmgmt/updateTripleHistStatus.json")
	public String updateTripleHistStatus(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute("searchVO") TripleHistMgmtVO tripleHistMgmtVO
			, ModelMap model
			, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		logger.debug("**********************************************************");
		logger.debug("* 트리플 이력관리 처리상태변경 *");
		logger.debug("**********************************************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(tripleHistMgmtVO);
			tripleHistMgmtVO.setRegId(loginInfo.getUserId());    /** 사용자ID */
			tripleHistMgmtVO.setRvisnId(loginInfo.getUserId());
			tripleHistMgmtService.updateTripleHistStatus(tripleHistMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
