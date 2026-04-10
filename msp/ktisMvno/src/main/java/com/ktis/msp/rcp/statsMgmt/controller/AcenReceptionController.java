package com.ktis.msp.rcp.statsMgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.statsMgmt.service.AcenReceptionService;
import com.ktis.msp.rcp.statsMgmt.vo.AcenReceptionVo;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
public class AcenReceptionController extends BaseController {

	protected Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private AcenReceptionService acenReceptionService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private FileDownService fileDownService;


	/** (A'Cen) 각종 내역서 발급요청 페이지 진입 */
	@RequestMapping(value = "/stats/statsMgmt/acenReception.do", method = {POST, GET})
	public String acenReceptionMgmt(HttpServletRequest pRequest
	                               ,HttpServletResponse pResponse
																 ,@RequestParam Map<String, Object> pRequestParamMap
																 ,@ModelAttribute("searchVO") AcenReceptionVo searchVO
																 ,ModelMap model) {

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());

			return "/rcp/statsMgmt/statsAcenReception";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/** (A'Cen) 각종 내역서 발급요청 리스트 조회 */
	@RequestMapping(value = "/stats/statsMgmt/getAcenReceptionList.json", method = {POST, GET})
	public String getAcenReceptionList(HttpServletRequest pRequest
                                    ,HttpServletResponse pResponse
																		,@RequestParam Map<String, Object> pRequestParamMap
																		,@ModelAttribute("searchVO") AcenReceptionVo searchVO
	                                  ,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 유효성검사
			boolean srchValidate = this.validationChk(searchVO);
			if(!srchValidate){
				throw new MvnoServiceException("검색조건이 누락되었습니다.");
			}

			// 각종 내역서 발급요청 리스트 조회
			List<?> resultList = acenReceptionService.getAcenReceptionList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1010055", "(A'Cen) 각종내역서 발급요청")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) 각종 내역서 발급요청 리스트 엑셀 다운로드 */
	@RequestMapping(value="/stats/statsMgmt/getAcenReceptionListExcel.json")
	public String getAcenReceptionListExcel(HttpServletRequest pRequest
																				 ,HttpServletResponse pResponse
																				 ,@RequestParam Map<String, Object> pRequestParamMap
																				 ,@ModelAttribute("searchVO") AcenReceptionVo searchVO
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
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 유효성검사
			boolean srchValidate = this.validationChk(searchVO);
			if(!srchValidate){
				throw new MvnoServiceException("검색조건이 누락되었습니다.");
			}

			// 엑셀 리스트 조회
			List<?> resultList = acenReceptionService.getAcenReceptionListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "(A'Cen)각종내역서발급요청_";  //파일명
			String strSheetname = "(A'Cen)각종내역서발급요청";                //시트명

			// 엑셀 첫줄
			String [] strHead = {"접수일시", "계약번호", "개통상태", "청구계정번호", "고객명", "서류유형", "수신방법", "FAX번호", "시작년월", "종료년월",
									"처리상태", "처리일시", "처리자", "수정일시", "수정자"};	// 15개
			String [] strValue = {"regstDttm", "contractNum", "subStatusNm", "ban", "cstmrName", "docTypeName", "receiveTypeName", "faxNo", "startYm", "endYm",
									"statusName", "procDttm", "procName", "rvisnDttm", "rvisnName"}; // 15개

			// 엑셀 컬럼 사이즈
			int[] intWidth = {7000, 5000, 5000, 6000, 8000, 7000, 4000, 5000, 4000, 4000, 4000, 7000, 8000, 7000, 8000}; // 15개
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 15개

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
				pRequestParamMap.put("DUTY_NM"   ,"RCP");                                 // 업무명
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

	/** 이메일 조회 (MP 연동) */
	@RequestMapping(value = "/stats/statsMgmt/getEmail.json", method = {POST, GET})
	public String getEmail(HttpServletRequest pRequest
												,HttpServletResponse pResponse
												,@ModelAttribute("searchVO") AcenReceptionVo searchVO
												,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			// 본사 화면
			if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 유효성검사
			if(KtisUtil.isEmpty(searchVO.getContractNum())){
				throw new MvnoServiceException("필수값이 누락되었습니다.");
			}

			// 유효성검사
			if("C".equals(searchVO.getSubStatus())){
				throw new MvnoServiceException("해지고객은 e-Mail 조회가 불가합니다.");
			}

			// 이메일 조회
			String email = acenReceptionService.getEmail(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "조회성공");
			resultMap.put("email", email);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010055", "(A'Cen) 각종내역서 발급요청")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) 각종 내역서 발급요청 완료처리 */
	@RequestMapping(value = "/stats/statsMgmt/completeAcenReception.json", method = {POST, GET})
	public String completeAcenReception(HttpServletRequest pRequest
																		 ,HttpServletResponse pResponse
																		 ,@RequestBody String pJsonString
											 							 ,@ModelAttribute("searchVO") AcenReceptionVo searchVO
																		 ,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			// 본사 화면
			if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<AcenReceptionVo> voList = getVoFromMultiJson(pJsonString, "ALL", AcenReceptionVo.class);

			// 각종 내역서 발급요청 완료처리
			acenReceptionService.completeAcenReception(voList, searchVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "처리성공");

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010055", "(A'Cen) 각종내역서 발급요청")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) 각종 내역서 발급요청 완료 취소처리 */
	@RequestMapping(value = "/stats/statsMgmt/cancelAcenReception.json", method = {POST, GET})
	public String cancelAcenReception(HttpServletRequest pRequest
																	 ,HttpServletResponse pResponse
																	 ,@RequestBody String pJsonString
																	 ,@ModelAttribute("searchVO") AcenReceptionVo searchVO
																	 ,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			// 본사 화면
			if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<AcenReceptionVo> voList = getVoFromMultiJson(pJsonString, "ALL", AcenReceptionVo.class);

			// 각종 내역서 발급요청 완료 취소처리
			acenReceptionService.cancelAcenReception(voList, searchVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "처리성공");

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010055", "(A'Cen) 각종내역서 발급요청")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) 각종 내역서 발급요청 유효성검사 */
	private boolean validationChk(AcenReceptionVo searchVO) {

		// 유효성 검사대상 여부
		boolean srchDateChk = KtisUtil.isEmpty(searchVO.getSearchGbn());
		boolean compDateChk = "1".equals(searchVO.getCompUseYn());
		boolean srchNameChk = !KtisUtil.isEmpty(searchVO.getSearchGbn());

		// 유효성 검사결과
		boolean srchDateRslt = true;
		boolean compDateRslt = true;
		boolean srchNameRslt = true;

		// 신청일자 검색조건 확인
		if(srchDateChk){
			srchDateRslt = !KtisUtil.isEmpty(searchVO.getSrchStrtDt()) && !KtisUtil.isEmpty(searchVO.getSrchEndDt());
		}

		// 완료일자 검색조건 확인
		if(compDateChk){
			compDateRslt = !KtisUtil.isEmpty(searchVO.getCompStrtDt()) && !KtisUtil.isEmpty(searchVO.getCompEndDt());
		}

		// 검색어 확인
		if(srchNameChk){
			srchNameRslt = !KtisUtil.isEmpty(searchVO.getSearchName());
		}

		return srchDateRslt && compDateRslt && srchNameRslt;
	}

}
