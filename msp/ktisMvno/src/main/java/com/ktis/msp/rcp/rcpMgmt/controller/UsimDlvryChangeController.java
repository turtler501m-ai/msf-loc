package com.ktis.msp.rcp.rcpMgmt.controller;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;

import com.ktis.msp.org.common.service.OrgCommonService;

import com.ktis.msp.rcp.rcpMgmt.service.UsimDlvryChangeService;

import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryChangeVO;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryMgmtVO;


import com.ktis.msp.util.ExcelFilesUploadUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
public class UsimDlvryChangeController extends BaseController {

	@Autowired
	private OrgCommonService orgCommonService;

	/**
	 * usimDlvryMgmtService
	 */
	@Autowired
	private UsimDlvryChangeService usimDlvryChangeService;

	/**
	 * propertiesService
	 */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private FileDownService fileDownService;

	@Autowired
	private LoginService loginService;

	/**
	 * Constructor
	 */
	public UsimDlvryChangeController() {
		setLogPrefix("[UsimDlvryChangeController] ");
	}


	/**
	 * @Description : 신청정보(유심교체) 화면 호출
	 * @Param  : void 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/usimChangeDlvry.do")
	public ModelAndView usimDlvryView(@ModelAttribute("searchVO") UsimDlvryMgmtVO searchVO,
									  ModelMap model,
									  HttpServletRequest pRequest,
									  HttpServletResponse pResponse,
									  @RequestParam Map<String, Object> pRequestParamMap
	) {
		ModelAndView modelAndView = new ModelAndView();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("maskingYn", maskingYn);

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpMgmtUsimChangeDlvry");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description : 신청정보(유심교체) 리스트 조회
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/selectUsimChangeMstList.json")
	public String selectUsimChangeMstList(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = usimDlvryChangeService.selectUsimChangeMstList(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010029", "신청정보(유심교체)")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 신청정보(유심교체) 리스트 엑셀다운
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/selectUsimChangeMstListExcel.json")
	public String selectUsimChangeMstListExcel(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = usimDlvryChangeService.selectUsimChangeMstListExcel(searchVO,pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "신청정보(유심교체)_";    // 파일명
			String strSheetname = "신청정보(유심교체)";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {
					"주문번호", "신청경로", "고객명", "수량", "신청일자", "접수시간",
					"배송연락처", "상태", "처리여부", "메모", "처리일시",
					"수령인", "배송우편번호", "배송주소", "배송연락처", "배송요청사항",
					"택배사", "송장번호"
			};
			String[] strValue = {
					"seq", "chnlNm","customerLinkName", "reqQnty", "reqInDay", "reqInTime",
					"dlvryTel", "statusNm", "procYnNm", "procMemo", "procDttm",
					"dlvryName", "dlvryPost", "dlvryAddr", "dlvryTel", "dlvryMemo",
					"tbNm", "dlvryNo"
			};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {
					3000, 3000, 3000, 3000, 3000, 3000,
					4000, 3000, 3000, 8000, 5000,
					4000, 5000, 10000, 4000, 6000,
					3000, 5000
			};

			int[] intLen = {
					0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0
			};
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
				pRequestParamMap.put("DATA_CNT", resultList.size()); // 자료건수

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
	 * @Description : 신청정보(유심교체) 상세리스트 조회
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/selectUsimChangeDtlList.json")
	public String selectUsimChangeDtlList(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = usimDlvryChangeService.selectUsimChangeDtlList(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010029", "신청정보(유심교체)")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 신청정보(유심교체) 상세리스트 엑셀다운
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/selectUsimChangeDtlListExcel.json")
	public String selectUsimChangeDtlListExcel(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = usimDlvryChangeService.selectUsimChangeDtlListExcel(searchVO,pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "신청정보상세(유심교체)_";    // 파일명
			String strSheetname = "신청정보상세(유심교체)";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {
					"주문번호", "신청경로", "계약번호", "고객명", "주문수량",
					"신청일시", "접수시간","제품명", "일련번호", "유심기변", "기변일시",
					"연동결과", "사유"
			};
			String[] strValue = {
					"seq", "chnlNm", "contractNum", "customerLinkName", "reqQnty",
					"reqInDay", "reqInTime","prdtCode", "usimSn", "chgYn", "chgDttm",
					"applyRsltCd", "applyRsltMsg"
			};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {
					3000, 3000, 3000, 3000, 3000,
					3000, 3000, 3000, 8000, 3000, 6000,
					3000, 9000
			};

			int[] intLen = {
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0,
					0, 0
			};
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
				pRequestParamMap.put("DATA_CNT", resultList.size()); // 자료건수

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
	 * @Description : SMS문자 전송 (배송안내, 교체독려) 
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/usimChangeSmsSend.json")
	public String usimChangeSmsSend(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO,
											HttpServletRequest pRequest,
											HttpServletResponse pResponse,
											ModelMap model,
											@RequestBody String pJsonString,
											@RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			//본사화면일경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<UsimDlvryChangeVO> list = getVoFromMultiJson(pJsonString, "ALL", UsimDlvryChangeVO.class);
			usimDlvryChangeService.usimChangeSmsSend(list, searchVO.getSessionUserId());

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch(Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : 일련번호/송장업로드 양식 다운로드
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/selectUsimChgDlvryExcelTemp.json")
	public String selectUsimChgDlvryExcelTemp(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String reqMode = (String) pRequestParamMap.get("reqMode");
			List<EgovMap> resultList = new ArrayList<EgovMap>();

			if ("NEW".equalsIgnoreCase(reqMode)) {
				resultList = Collections.emptyList();
			} else {
				resultList = usimDlvryChangeService.selectUsimChgDlvryExcelTemp(searchVO, pRequestParamMap);
				if (resultList == null) {
					resultList = Collections.emptyList();
				}
			}

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "일련번호,송장업로드 양식_";    // 파일명
			String strSheetname = "일련번호,송장업로드양식";              		  // 시트명

			// 엑셀 첫줄
			String[] strHead = {
					"주문번호", "신청경로", "계약번호", "고객명", "주문수량",
					"신청일자", "접수시간","메모", "수령인", "우편번호", 
					"배송주소", "배송연락처", "배송요청사항", "현유심", "현유심종류",
					"제품명", "일련번호", "택배사", "송장번호"
			};
			String[] strValue = {
					"seq", "chnlNm", "contractNum", "customerLinkName", "reqQnty",
					"reqInDay", "reqInTime","procMemo", "dlvryName", "dlvryPost",
					"dlvryAddr", "dlvryTel", "dlvryMemo", "usimModelCd", "nfcUsimType", 
					"prdtCode", "usimSn", "tbNm", "dlvryNo"
			};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {
					3000, 3000, 3000, 3000, 3000,
					3000, 3000, 5000, 3000, 3000,
					8000, 4000, 5000, 5000, 4000, 
					5000, 7000, 3000, 7000
			};

			int[] intLen = {
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0
			};
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
				pRequestParamMap.put("DATA_CNT", resultList.size()); // 자료건수

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
	 * @Description : 처리여부/메모업로드 양식 다운로드
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/usimChgProcExcelTemp.json")
	public String usimChgProcExcelTemp(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한 
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}


			List<?> resultList = new ArrayList<UsimDlvryChangeVO>();

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "처리여부,메모등록 양식_";    // 파일명
			String strSheetname = "처리여부,메모등록양식";              		  // 시트명

			// 엑셀 첫줄
			String[] strHead = {
					"주문번호", "처리여부", "처리메모"
			};
			String[] strValue = {
					"seq", "procYnNm", "procMemo"
			};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {
					3000, 3000, 5000
			};

			int[] intLen = {
					0, 0, 0
			};
			
			
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
	 * @Description : ESIM 엑셀등록 양식 다운로드
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/usimChgInfoExcelTemp.json")
	public String usimChgInfoExcelTemp(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = new ArrayList<UsimDlvryChangeVO>();

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "신청정보(유심교체)ESIM 엑셀등록양식_";    // 파일명
			String strSheetname = "신청정보(유심교체)ESIM엑셀등록";              		// 시트명
						
			// 엑셀 첫줄
			String[] strHead = {
					"신청경로", "계약번호", "고객명", "주문수량", "신청일시",
					"수령인", "우편번호", "배송주소", "상세주소", "배송연락처",
					"배송요청사항", "처리메모"
			};
			String[] strValue = {
					"chnlNm",  "contractNum", "customerLinkName", "reqQnty", "reqInDay",
					"dlvryName", "dlvryPost", "dlvryAddr", "dlvryAddrDtl", "dlvryTel",
					"dlvryMemo", "procMemo"
					
			};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {
					3000, 3000, 3000, 3000, 3000,
					3000, 3000, 8000, 5000, 4000,
					5000, 5000
			};

			int[] intLen = {
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0
			};
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
	 * @Description : 일련번호/송장업로드 파일 읽기
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value="/rcp/rcpMgmt/readUsimChgUsimDlvryExcel.json")
	public String readUsimChgUsimDlvryExcel(HttpServletRequest request,
									 HttpServletResponse response,
									 @ModelAttribute("usimDlvryChangeVO") UsimDlvryChangeVO searchVO,
									 ModelMap model,
									 @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			//마스킹 권한
			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String baseDir = propertiesService.getString("fileUploadBaseDirectory");

			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();

			String[] arrCell = {
					"seq", "chnlNm", "contractNum", "customerLinkName", "reqQnty",
					"reqInDay", "reqInTime","procMemo", "dlvryName", "dlvryPost",
					"dlvryAddr", "dlvryTel", "dlvryMemo", "nfcUsimType", "usimModelCd",
					"prdtCode", "usimSn", "tbNm", "dlvryNo"
			};

			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryChangeVO", sOpenFileName, arrCell);

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
	 * @Description : 일련번호/송장업로드 파일 엑셀업로드 수정
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value="/rcp/rcpMgmt/updateUsimChgUsimDlvryExcel.json")
	public String updateUsimChgUsimDlvryExcel(HttpServletRequest request,
									  HttpServletResponse response,
									  @ModelAttribute("usimDlvryChangeVO") UsimDlvryChangeVO searchVO,
									  @RequestBody String data,
									  ModelMap model,
									  @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			UsimDlvryChangeVO vo = new ObjectMapper().readValue(data, UsimDlvryChangeVO.class);
			vo.setSessionUserId(searchVO.getSessionUserId());

			int resultCnt = usimDlvryChangeService.updateUsimChgUsimDlvryExcel(vo);
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
	 * @Description : 처리여부/메모등록 파일읽기
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/readUsimChgProcExcel.json")
	public String readUsimChgProcExcel(HttpServletRequest request,
											HttpServletResponse response,
											@ModelAttribute("usimDlvryChangeVO") UsimDlvryChangeVO searchVO,
											ModelMap model,
											@RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String baseDir = propertiesService.getString("fileUploadBaseDirectory");

			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();

			String[] arrCell = {
					"seq", "procYnNm", "procMemo"
			};
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryChangeVO", sOpenFileName, arrCell);

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
	 * @Description : 처리메모/메모 파일 등록
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value="/rcp/rcpMgmt/updateUsimChgProcExcel.json")
	public String updateUsimChgProcExcel(HttpServletRequest request,
										  HttpServletResponse response,
										  @ModelAttribute("usimDlvryChangeVO") UsimDlvryChangeVO searchVO,
										  @RequestBody String data,
										  ModelMap model,
										  @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			UsimDlvryChangeVO vo = new ObjectMapper().readValue(data, UsimDlvryChangeVO.class);
			vo.setSessionUserId(searchVO.getSessionUserId());

			int resultCnt = usimDlvryChangeService.updateUsimChgProcExcel(vo);
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
	 * @Description : 유심무상교체 접수 가능 여부 조회
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/usimChgAccept.json")
	public String usimChgAccept(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> usimChgList = usimDlvryChangeService.usimChgAccept(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, usimChgList);
			
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010029", "신청정보(유심교체)")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 신청정보(유심교체) 직접등록(등록)
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/insertUsimChg.json")
	public String insertUsimChg(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse,
								@RequestBody String data,
								ModelMap model, 
								@RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			UsimDlvryChangeVO vo = new ObjectMapper().readValue(data, UsimDlvryChangeVO.class);
			vo.setSessionUserId(searchVO.getSessionUserId());

			usimDlvryChangeService.insertUsimChg(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010029", "신청정보(유심교체)")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 신청정보(유심교체) 직접등록 상세 조회
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/selectUsimChgInfo.json")
	public String selectUsimChgInfo(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = usimDlvryChangeService.selectUsimChgInfo(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010029", "신청정보(유심교체)")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 신청정보(유심교체) 직접등록(수정)
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/updateUsimChg.json")
	public String updateUsimChg(@ModelAttribute("searchVO") UsimDlvryChangeVO searchVO,
								HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestBody String data,
								ModelMap model,
								@RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			UsimDlvryChangeVO vo = new ObjectMapper().readValue(data, UsimDlvryChangeVO.class);
			vo.setSessionUserId(searchVO.getSessionUserId());

			usimDlvryChangeService.updateUsimChg(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010029", "신청정보(유심교체)")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 신청정보(유심교체) ESIM 파일 엑셀업로드 파일 읽기
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value="/rcp/rcpMgmt/readUsimChgInfoExcel.json")
	public String readUsimChgInfoExcel(HttpServletRequest request,
									   HttpServletResponse response,
									   @ModelAttribute("usimDlvryChangeVO") UsimDlvryChangeVO searchVO,
									   ModelMap model,
									   @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			//마스킹 권한
			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String baseDir = propertiesService.getString("fileUploadBaseDirectory");

			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();

			String[] arrCell = {
					"chnlNm", "contractNum", "customerLinkName", "reqQnty", "reqInDay",
					"dlvryName", "dlvryPost", "dlvryAddr", "dlvryAddrDtl", "dlvryTel", 
					"dlvryMemo", "procMemo" 
			};

			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryChangeVO", sOpenFileName, arrCell);

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
	 * @Description : 신청정보(유심교체) ESIM 엑셀업로드 등록
	 * @Param  : UsimDlvryChangeVO 
	 * @Return : String 
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07. 
	 */
	@RequestMapping(value="/rcp/rcpMgmt/insertUsimChgInfoExcel.json")
	public String insertUsimChgInfoExcel(HttpServletRequest request,
										 HttpServletResponse response,
										 @ModelAttribute("usimDlvryChangeVO") UsimDlvryChangeVO searchVO,
										 @RequestBody String data,
										 ModelMap model,
										 @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			UsimDlvryChangeVO vo = new ObjectMapper().readValue(data, UsimDlvryChangeVO.class);
			vo.setSessionUserId(searchVO.getSessionUserId());

			int resultCnt = usimDlvryChangeService.insertUsimChgInfoExcel(vo);
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
	
}