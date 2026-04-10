package com.ktis.msp.org.csanalyticmgmt.controller;

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
import com.ktis.msp.org.csanalyticmgmt.service.AcenVocAgntService;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenVocAgntVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class AcenVocAgntController extends BaseController {

	protected Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private AcenVocAgntService acenVocAgntService;

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private LoginService loginService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private FileDownService fileDownService;

	/** (A'Cen) VOC 담당자 정보 페이지 진입 */
	@RequestMapping(value = "/org/csAnalyticMgmt/acenVocAgnt.do", method = {POST, GET})
	public String acenVocAgnt(HttpServletRequest pRequest
													 ,HttpServletResponse pResponse
													 ,@RequestParam Map<String, Object> pRequestParamMap
													 ,@ModelAttribute("searchVO") AcenVocAgntVO searchVO
													 ,ModelMap model) {

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("maskingYn", loginService.getUsrMskYn(loginInfo.getUserId()));

			return "/org/csanalyticmgmt/acenVocAgnt";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/** (A'Cen) VOC 담당자 정보 리스트 조회 */
	@RequestMapping(value = "/org/csAnalyticMgmt/getAcenVocAgntList.json", method = {POST, GET})
	public String getAcenVocAgntList(HttpServletRequest pRequest
																	,HttpServletResponse pResponse
																	,@RequestParam Map<String, Object> pRequestParamMap
																	,@ModelAttribute("searchVO") AcenVocAgntVO searchVO
																	,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 유효성검사
			if(!KtisUtil.isEmpty(searchVO.getSearchGbn()) && KtisUtil.isEmpty(searchVO.getSearchName())){
				throw new MvnoServiceException("검색조건이 누락되었습니다.");
			}

			// VOC 담당자 정보 리스트 조회
			List<?> resultList = acenVocAgntService.getAcenVocAgntList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP2002203", "A'Cen VOC 담당자 정보")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) VOC 담당자 정보 리스트 엑셀 다운로드 */
	@RequestMapping(value = "/org/csAnalyticMgmt/getAcenVocAgntListExcel.json", method = {POST, GET})
	public String getAcenVocAgntListExcel(HttpServletRequest pRequest
																			 ,HttpServletResponse pResponse
																			 ,@RequestParam Map<String, Object> pRequestParamMap
																			 ,@ModelAttribute("searchVO") AcenVocAgntVO searchVO
																			 ,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 유효성검사
			if(!KtisUtil.isEmpty(searchVO.getSearchGbn()) && KtisUtil.isEmpty(searchVO.getSearchName())){
				throw new MvnoServiceException("검색조건이 누락되었습니다.");
			}

			// 엑셀 리스트 조회
			List<?> resultList = acenVocAgntService.getAcenVocAgntListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "(A'Cen)VOC담당자정보_";  //파일명
			String strSheetname = "(A'Cen)VOC담당자정보";                //시트명

			// 엑셀 첫줄
			String [] strHead = {"대리점ID", "대리점명", "담당자ID", "담당자명", "휴대폰번호", "사용여부", "등록일시", "등록자", "수정일시", "수정자"};	// 10개
			String [] strValue = {"vocAgntCd", "orgnName", "vocAgntId", "vocAgntName", "mobileNum", "useYn", "regstDttm", "regstName", "rvisnDttm", "rvisnName"}; // 10개

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 8000, 6000, 8000, 5000, 4000, 7000, 8000, 7000, 8000}; // 10개
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 10개

			String strFileName = "";
			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}

			// 파일 다운로드 사유 로그
			if(KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))){

				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM"   ,file.getName());                        // 파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());                        // 파일경로
				pRequestParamMap.put("DUTY_NM"   ,"ORG");                                 // 업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                                // IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());                   // 파일크기
				pRequestParamMap.put("menuId"    ,pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT"  ,0);                                     // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		}catch(Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", (e.getMessage() == null) ? "다운로드실패" : e.getMessage());

		}finally {
			if(in != null){try{in.close();}catch(Exception e){throw new MvnoErrorException(e);}}
			if(out != null){try{out.close();}catch(Exception e){throw new MvnoErrorException(e);}}
			if(file != null){file.delete();}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) VOC 담당자 정보 등록 및 수정 */
	@RequestMapping(value = "/org/csAnalyticMgmt/mergeAcenVocAgnt.json", method = {POST, GET})
	public String mergeAcenVocAgnt(HttpServletRequest pRequest
																,HttpServletResponse pResponse
																,@ModelAttribute("searchVO") AcenVocAgntVO searchVO
																,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			// 본사 화면
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 유효성검사
			if(KtisUtil.isEmpty(searchVO.getVocAgntCd()) || KtisUtil.isEmpty(searchVO.getUsrId()) || KtisUtil.isEmpty(searchVO.getUseYn())){
				throw new MvnoServiceException("필수값이 누락되었습니다.");
			}

			// VOC 담당자 정보 등록 및 수정
			acenVocAgntService.mergeAcenVocAgnt(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002203", "A'Cen VOC 담당자 정보")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

}
