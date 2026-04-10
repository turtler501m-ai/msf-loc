package com.ktis.msp.rcp.familyMgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.familyMgmt.service.FamilyMgmtService;
import com.ktis.msp.rcp.familyMgmt.vo.FamilyMgmtVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class FamilyMgmtController extends BaseController {

	protected Logger logger = LogManager.getLogger(getClass());

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private FamilyMgmtService familyMgmtService;

	@Autowired
	private FileDownService fileDownService;

	/** Constructor */
	public FamilyMgmtController() {
		setLogPrefix("[FamilyMgmtController] ");
	}

	/**
	 * 가족관계 관리 페이지 이동
	 */
	@RequestMapping(value = "/rcp/familyMgmt/familyRelMgmt.do", method = {POST, GET})
	public String familyRelMgmt(@ModelAttribute("familyMgmtVO") FamilyMgmtVO familyMgmtVO, HttpServletRequest pRequest,
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

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/familyMgmt/familyRelMgmt";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description 가족관계 목록 조회
	 * @Param : familyMgmtVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.09.20
	 */
	@RequestMapping(value = "/rcp/familyMgmt/getFamilyRelList.do")
	public String getFamilyRelList(@ModelAttribute("familyMgmtVO") FamilyMgmtVO familyMgmtVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 가족관계 목록 조회
			List<EgovMap> resultList = familyMgmtService.getFamilyRelList(familyMgmtVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010023", "가족관계 관리")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 가족관계 목록 엑셀 다운로드
	 * @Param : familyMgmtVO
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.09.21
	 */
	@RequestMapping(value = "/rcp/familyMgmt/getFamilyRelListExcel.json")
	@ResponseBody
	public String getFamilyRelListExcel(FamilyMgmtVO familyMgmtVO, ModelMap model, HttpServletRequest pRequest,
									HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//날짜 세팅
			if(KtisUtil.isEmpty(familyMgmtVO.getSrchStrtDt())){
				familyMgmtVO.setSrchStrtDt(orgCommonService.getWantDay(-1));
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = familyMgmtService.getFamilyRelListExcel(familyMgmtVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "가족관계목록조회";    // 파일명
			String strSheetname = "가족관계 목록조회";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"서비스계약번호", "고객명", "생년월일", "나이(만)", "휴대폰번호", "가족관계상태", "시작일시", "종료일시", "종료사유", "종료메모", "등록자", "등록일시", "수정자", "수정일시"};	// 14
			String[] strValue = {"svcCntrNo", "subLinkName", "birthDt", "age", "subscriberNo", "useYnNm", "strtDttm", "endDttm", "endCodeNm", "endMsg", "regstId", "regstDttm", "rvisnId", "rvisnDttm"}; // 14

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 3500, 3500, 3500, 5000, 5000, 5000, 5000, 5000, 10000, 3500, 5000, 3500, 5000}; // 14

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 14

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
	 * @Description 가족관계 구성원 조회
	 * @Param : familyMgmtVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.09.21
	 */
	@RequestMapping(value = "/rcp/familyMgmt/getFamilyMemberList.do")
	public String getFamilyMemberList(@ModelAttribute("familyMgmtVO") FamilyMgmtVO familyMgmtVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 가족관계 목록 조회
			List<EgovMap> resultList = familyMgmtService.getFamilyMemberList(familyMgmtVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010023", "가족관계 관리")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 가족관계 신규 등록
	 * @Param : familyMgmtVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.09.23
	 */
	@RequestMapping("/rcp/familyMgmt/regstFamilyRel.json")
	public String regstFamilyRel(FamilyMgmtVO familyMgmtVO, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(familyMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if(!"10".equals(familyMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			familyMgmtService.regstFamilyRel(pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description 가족관계 구성원 추가
	 * @Param : familyMgmtVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.09.23
	 */
	@RequestMapping("/rcp/familyMgmt/addFamilyMember.json")
	public String addFamilyMember(FamilyMgmtVO familyMgmtVO, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(familyMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if(!"10".equals(familyMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			familyMgmtService.addFamilyMember(pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description 가족관계 종료
	 * @Param : familyMgmtVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.09.23
	 */
	@RequestMapping("/rcp/familyMgmt/cancelFamilyRel.json")
	public String cancelFamilyRel(FamilyMgmtVO familyMgmtVO, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(familyMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if(!"10".equals(familyMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			familyMgmtService.cancelFamilyRel(pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description 가족관계 구성원 종료
	 * @Param : familyMgmtVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.09.23
	 */
	@RequestMapping("/rcp/familyMgmt/cancelFamilyMember.json")
	public String cancelFamilyMember(FamilyMgmtVO familyMgmtVO, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(familyMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if(!"10".equals(familyMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			familyMgmtService.cancelFamilyMember(pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
}