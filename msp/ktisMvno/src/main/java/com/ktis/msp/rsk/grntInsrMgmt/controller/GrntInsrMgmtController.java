package com.ktis.msp.rsk.grntInsrMgmt.controller;

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
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.rsk.grntInsrMgmt.service.GrntInsrMgmtService;
import com.ktis.msp.rsk.grntInsrMgmt.vo.GrntInsrMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class GrntInsrMgmtController extends BaseController {

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private GrntInsrMgmtService grntInsrMgmtService;

	@Autowired
	private FileDownService fileDownService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	/** Constructor */
	public GrntInsrMgmtController() {
		setLogPrefix("[GrntInsrMgmtController] ");
	}

	/**
	 * 보증보험청구조회 화면 이동
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rsk/grntInsrMgmt/grntInsrBillView.do")
	public ModelAndView grntInsrBillView(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {

		logger.debug("=================================================================");
		logger.debug("보증보험청구조회 START.");
		logger.debug("=================================================================");

		ModelAndView modelAndView = new ModelAndView();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			// 본사 화면인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",
					menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			modelAndView.setViewName("rsk/grntInsrMgmt/msp_rsk_grntInsr_1001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * 보증보험청구 조회
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param pRequestParam
	 * @param grntInsrMgmtVO
	 * @return
	 */
	@RequestMapping(value = "/rsk/grntInsrMgmt/getGrntInsrList.json")
	public String getGrntInsrList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParam,
			@ModelAttribute("searchVO") GrntInsrMgmtVO grntInsrMgmtVO) {
		logger.debug("=================================================================");
		logger.debug("보증보험청구List조회 START.");
		logger.debug("=================================================================");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grntInsrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParam);
			
			// 본사 화면인 경우
			if(!"10".equals(grntInsrMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> list = grntInsrMgmtService.getGrntInsrList(grntInsrMgmtVO,
					pRequestParam);

			resultMap = makeResultMultiRow(pRequestParam, list);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 보증보험청구조회 엑셀다운로드
	 * 
	 * @param grntInsrMgmtVO
	 * @param request
	 * @param response
	 * @param pReqParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rsk/grntInsrMgmt/getGrntInsrListEx.json")
	public String getGrntInsrListEx(
			@ModelAttribute("searchVO") GrntInsrMgmtVO grntInsrMgmtVO,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> pReqParamMap, Model model) {

		logger.debug("=================================================================");
		logger.debug("보증보험청구조회 엑셀다운로드 START.");
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
			loginInfo.putSessionToVo(grntInsrMgmtVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(grntInsrMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = grntInsrMgmtService.getGrntInsrListEx(
					grntInsrMgmtVO, pReqParamMap);

			String serverInfo = propertyService.getString("excelPath");
			String strfilename = serverInfo + "보증보험청구조회_";
			String strSheetname = "보증보험청구조회";

			String[] strHead = { "연동월", "계약번호", "계약상태",	"보증보험관리번호", "청구일자",
					"청구금액", "지급일자", "지급금액", "오류사유" };
			String[] strValue = { "workYmEx", "contractNum", "subStatusNm", "grntInsrMngmNo", "billDtEx",
					"billAmnt", "pymnDtEx", "pymnAmnt", "errDscr" };

			int[] inWidth = { 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000 };
			int[] inLen = { 0, 0, 0, 0, 0, 1, 0, 1, 0 };

			String strFileName = fileDownService.excelDownProc(strfilename,
					strSheetname, list, strHead, inWidth, strValue, request,
					response, inLen);
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

				if (ipAddr == null || ipAddr.length() == 0
						|| ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0
						|| ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pReqParamMap.put("FILE_NM", file.getName()); // 파일명
				pReqParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pReqParamMap.put("DUTY_NM", "CMN"); // 업무명
				pReqParamMap.put("IP_INFO", ipAddr); // IP정보
				pReqParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pReqParamMap.put("DATA_CNT", 0); // 자료건수
				pReqParamMap
						.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); // 사유
				pReqParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); // 사용자ID

				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}

			resultMap.put("code", messageSource.getMessage(
					"ktis.msp.rtn_code.OK", null, Locale.getDefault()));
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
}
