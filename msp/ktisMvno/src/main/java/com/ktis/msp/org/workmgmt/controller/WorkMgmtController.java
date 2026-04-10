package com.ktis.msp.org.workmgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;

import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;

import com.ktis.msp.org.workmgmt.service.WorkMgmtService;
import com.ktis.msp.org.workmgmt.vo.WorkMgmtVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.codehaus.jackson.map.ObjectMapper;
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
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class WorkMgmtController extends BaseController {

	@Autowired
	private WorkMgmtService workMgmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
		
	@Autowired
	private FileDownService  fileDownService;

	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	public WorkMgmtController() {
		setLogPrefix("[WorkMgmtController] ");
	}

	/**
	 * @Description : 업무유형별 업무관리 화면
	 */
	@RequestMapping(value = "/org/workMgmt/workTypeMgmt.do", method={POST, GET})
	public String workTypeMgmt(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){


		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);


			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/org/workmgmt/workTypeMgmt";
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description : 업무 목록 조회
	 */
	@RequestMapping(value = "/org/workMgmt/getWorkTmplList.json")
	public String getWorkTmplList(@ModelAttribute("searchVO") WorkMgmtVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = workMgmtService.getWorkTmplList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002301", "업무유형별 업무관리")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 업무 목록 엑셀다운로드
	 */

	@RequestMapping(value = "/org/workMgmt/getWorkTmplListExcel.json")
	@ResponseBody
	public String getWorkTmplListExcel(WorkMgmtVO searchVO, ModelMap model, HttpServletRequest pRequest,
										HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

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

			// 엑셀 데이터 조회
			List<EgovMap> resultList = workMgmtService.getWorkTmplListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "업무유형별 업무관리_";    // 파일명
			String strSheetname = "업무유형별 업무관리";                 // 시트명

			// 엑셀 첫줄
			String[] strHead = {"유형코드", "업무명", "사용여부","업무유형", "명의자구분",
								"요청자구분", "서류수", "등록자", "등록일", "수정자",
								"수정일", "시작일", "종료일"};
			String[] strValue = {"workTmplId", "workTmplNm", "useYnNm", "workNm", "ownerNm",
								"rqstrNm", "docCnt", "cretNm", "cretDt", "amdNm",
								"amdDt", "startDt", "endDt"};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {4000, 15000, 5000, 7000, 7000,
							7000, 2000, 2000, 3000, 2000,
							3000, 4000, 4000};

			int[] intLen = {0, 0, 0, 0, 0,
							0, 0, 0, 0, 0,
							0, 0, 0};

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
	 * @Description : 수취서류구분 저장 (등록/수정)
	 */
	@RequestMapping(value="/org/workMgmt/saveWorkItem.json")
	public String saveWorkItem(@ModelAttribute("searchVO") WorkMgmtVO searchVO,
								  @RequestBody String data,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  ModelMap modelMap,
								  @RequestParam Map<String, Object> paramMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);

			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			logger.info(data);

			WorkMgmtVO vo = new ObjectMapper().readValue(data, WorkMgmtVO.class);

			vo.setSessionUserId(searchVO.getSessionUserId());

			workMgmtService.saveWorkItem(vo);

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
	 * @Description : 수취서류구분 종료(삭제)
	 */
	@RequestMapping(value="/org/workMgmt/updateWorkItemEnd.json")
	public String updateWorkItemEnd(@ModelAttribute("searchVO") WorkMgmtVO searchVO,
								  @RequestBody String data,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  ModelMap modelMap,
								  @RequestParam Map<String, Object> paramMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);

			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			logger.info(data);

			WorkMgmtVO vo = new ObjectMapper().readValue(data, WorkMgmtVO.class);

			vo.setSessionUserId(searchVO.getSessionUserId());

			workMgmtService.updateWorkItemEnd(vo);

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
	 * @Description : 수취서류구분 목록 조회
	 */
	@RequestMapping(value = "/org/workMgmt/getWorkItemList.json")
	public String getWorkItemList(@ModelAttribute("searchVO") WorkMgmtVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = workMgmtService.getWorkItemList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002301", "업무유형별 업무관리")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 명의자구분 저장(등록/수정)
	 */
	@RequestMapping(value="/org/workMgmt/saveWorkOwner.json")
	public String saveWorkOwner(@ModelAttribute("searchVO") WorkMgmtVO searchVO,
								  @RequestBody String data,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  ModelMap modelMap,
								  @RequestParam Map<String, Object> paramMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);

			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			logger.info(data);

			WorkMgmtVO vo = new ObjectMapper().readValue(data, WorkMgmtVO.class);

			vo.setSessionUserId(searchVO.getSessionUserId());

			workMgmtService.saveWorkOwner(vo);

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
	 * @Description : 명의자구분 종료(삭제)
	 */
	@RequestMapping(value="/org/workMgmt/updateWorkOwnerEnd.json")
	public String updateWorkOwnerEnd(@ModelAttribute("searchVO") WorkMgmtVO searchVO,
								  @RequestBody String data,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  ModelMap modelMap,
								  @RequestParam Map<String, Object> paramMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);

			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			logger.info(data);

			WorkMgmtVO vo = new ObjectMapper().readValue(data, WorkMgmtVO.class);

			vo.setSessionUserId(searchVO.getSessionUserId());

			workMgmtService.updateWorkOwnerEnd(vo);

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
	 * @Description : 명의자구분 목록 조회
	 */
	@RequestMapping(value = "/org/workMgmt/getWorkOwnerList.json")
	public String getWorkOwnerList(@ModelAttribute("searchVO") WorkMgmtVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = workMgmtService.getWorkOwnerList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002301", "업무유형별 업무관리")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 요청자구분 저장(등록/수정)
	 */
	@RequestMapping(value="/org/workMgmt/saveWorkRqstr.json")
	public String saveWorkRqstr(@ModelAttribute("searchVO") WorkMgmtVO searchVO,
								  @RequestBody String data,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  ModelMap modelMap,
								  @RequestParam Map<String, Object> paramMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);

			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			logger.info(data);

			WorkMgmtVO vo = new ObjectMapper().readValue(data, WorkMgmtVO.class);

			vo.setSessionUserId(searchVO.getSessionUserId());

			workMgmtService.saveWorkRqstr(vo);

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
	 * @Description : 요청자구분 종료(삭제)
	 */
	@RequestMapping(value="/org/workMgmt/updateWorkRqstrEnd.json")
	public String updateWorkRqstrEnd(@ModelAttribute("searchVO") WorkMgmtVO searchVO,
								  @RequestBody String data,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  ModelMap modelMap,
								  @RequestParam Map<String, Object> paramMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);

			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			logger.info(data);

			WorkMgmtVO vo = new ObjectMapper().readValue(data, WorkMgmtVO.class);

			vo.setSessionUserId(searchVO.getSessionUserId());

			workMgmtService.updateWorkRqstrEnd(vo);

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
	 * @Description : 요청자구분 목록 조회
	 */
	@RequestMapping(value = "/org/workMgmt/getWorkRqstrList.json")
	public String getWorkRqstrList(@ModelAttribute("searchVO") WorkMgmtVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = workMgmtService.getWorkRqstrList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002301", "업무유형별 업무관리")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 문자템플릿 목록조회
	 */
	@RequestMapping(value = "/org/workMgmt/getWorkSmsList.json")
	public String getWorkSmsList(@ModelAttribute("searchVO") WorkMgmtVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = workMgmtService.getWorkSmsList(searchVO);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002301", "업무유형별 업무관리")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

    /**
     * @Description : 문자템플릿 목록조회
     */
    @RequestMapping(value = "/org/workMgmt/getWorkSmsListByWorkTmplId.json")
    public String getWorkSmsListByWorkTmplId(@ModelAttribute("searchVO") WorkMgmtVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<EgovMap> resultList = workMgmtService.getWorkSmsListByWorkTmplId(searchVO);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

            model.addAttribute("result", resultMap);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002301", "업무유형별 업무관리")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

	/**
	 * @Description : 업무템플릿 저장(등록/수정)
	 */
	@RequestMapping(value="/org/workMgmt/saveWorkTmpl.json")
	public String saveWorkTmpl(@ModelAttribute("searchVO") WorkMgmtVO searchVO,
								  @RequestBody String data,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  ModelMap modelMap,
								  @RequestParam Map<String, Object> paramMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);

			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			logger.info(data);

			WorkMgmtVO vo = new ObjectMapper().readValue(data, WorkMgmtVO.class);

			vo.setSessionUserId(searchVO.getSessionUserId());

			workMgmtService.saveWorkTmpl(vo);

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
	 * @Description : 업무유형 목록조회
	 */
	@RequestMapping(value = "/org/workMgmt/getWorkIdList.json")
	public String getWorkIdList(@ModelAttribute("searchVO") WorkMgmtVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}


			List<EgovMap> resultList = workMgmtService.getWorkIdList();
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002301", "업무유형별 업무관리")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

    @RequestMapping("/org/workMgmt/getRqstrCdList.json")
    public String getRqstrCdList(@ModelAttribute("searchVO") WorkMgmtVO searchVO,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 ModelMap model,
                                 @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // 로그인체크
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            // 본사 화면인 경우
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = workMgmtService.getRqstrCdList();
            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, resultMap))
                throw new MvnoErrorException(e);
        }
        model.addAttribute("result", resultMap);

        return "jsonView";
    }
}
