package com.ktis.msp.voc.vocMgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.voc.vocMgmt.service.AcenVocService;
import com.ktis.msp.voc.vocMgmt.vo.AcenVocVo;
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
public class AcenVocController extends BaseController {

	protected Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private RcpMgmtService rcpMgmtService;

	@Autowired
	private AcenVocService acenVocService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private FileDownService fileDownService;


	/** (A'Cen) 대리점 VOC 목록 페이지 진입 */
	@RequestMapping(value = "/voc/vocMgmt/acenVoc.do", method = {POST, GET})
	public String acenVocMgmt(HttpServletRequest pRequest
													 ,HttpServletResponse pResponse
													 ,@RequestParam Map<String, Object> pRequestParamMap
													 ,@ModelAttribute("searchVO") AcenVocVo searchVO
													 ,ModelMap model) {

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 화면
			String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
			String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

			if(!"10".equals(orgnTypeCd) && (!"20".equals(orgnTypeCd) || !"10".equals(orgnLvlCd))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());

			return "/voc/vocMgmt/acenVoc";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/** (A'Cen) 대리점 VOC 목록 리스트 조회 */
	@RequestMapping(value = "/voc/vocMgmt/getAcenVocList.json", method = {POST, GET})
	public String getAcenVocList(HttpServletRequest pRequest
															,HttpServletResponse pResponse
															,@RequestParam Map<String, Object> pRequestParamMap
															,@ModelAttribute("searchVO") AcenVocVo searchVO
															,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 화면
			String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
			String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

			if(!"10".equals(orgnTypeCd) && (!"20".equals(orgnTypeCd) || !"10".equals(orgnLvlCd))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 유효성검사
			boolean srchValidate = this.validationChk(searchVO);
			if(!srchValidate){
				throw new MvnoServiceException("검색조건이 누락되었습니다.");
			}

			// 대리점코드 세팅
			if(!"10".equals(orgnTypeCd)){
				searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			// 대리점 VOC 목록 리스트 조회
			searchVO.setSessionUserOrgnTypeCd(orgnTypeCd);
			List<?> resultList = acenVocService.getAcenVocList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "VOC1001060", "대리점 VOC 목록")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) 대리점 VOC 목록 리스트 엑셀 다운로드 */
	@RequestMapping(value = "/voc/vocMgmt/getAcenVocListExcel.json")
	public String getAcenVocListExcel(HttpServletRequest pRequest
																	 ,HttpServletResponse pResponse
																	 ,@RequestParam Map<String, Object> pRequestParamMap
																	 ,@ModelAttribute("searchVO") AcenVocVo searchVO
																	 ,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 화면
			String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
			String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

			if(!"10".equals(orgnTypeCd) && (!"20".equals(orgnTypeCd) || !"10".equals(orgnLvlCd))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 유효성검사
			boolean srchValidate = this.validationChk(searchVO);
			if(!srchValidate){
				throw new MvnoServiceException("검색조건이 누락되었습니다.");
			}

			// 대리점코드 세팅
			if(!"10".equals(orgnTypeCd)){
				searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			// 엑셀 리스트 조회
			searchVO.setSessionUserOrgnTypeCd(orgnTypeCd);
			List<?> resultList = acenVocService.getAcenVocListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "대리점VOC목록_";  //파일명
			String strSheetname = "대리점VOC목록";                //시트명

			// 엑셀 첫줄
			String [] strHead = {"상담일련번호", "계약번호", "개통상태", "고객명", "대리점", "VOC유형", "접수일시", "현재상태", "접수자", "처리기한", "수정일시", "수정자"};	// 12개
			String [] strValue = {"vocSeq", "contractNum", "subStatusNm", "cstmrName", "vocAgntCdName", "vocSubCtgCdName", "vocDttm", "statusName", "vocRcpName", "dueDt", "rvisnDttm", "rvisnName"}; // 12개

			// 엑셀 컬럼 사이즈
			int[] intWidth = {7000, 5000, 5000, 8000, 8000, 8000, 7000, 5000, 8000, 5000, 7000, 8000}; // 12개
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 12개

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
				pRequestParamMap.put("DUTY_NM"   ,"VOC");                                 // 업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                                // IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());                   // 파일크기
				pRequestParamMap.put("menuId"    ,pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT"  ,0);                                     // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		}catch (Exception e) {

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

	/** (A'Cen) 대리점 VOC 목록 리스트 수정이력 조회 */
	@RequestMapping(value = "/voc/vocMgmt/getAcenVocHistList.json", method = {POST, GET})
	public String getAcenVocHistList(HttpServletRequest pRequest
															    ,HttpServletResponse pResponse
															    ,@RequestParam Map<String, Object> pRequestParamMap
															    ,@ModelAttribute("searchVO") AcenVocVo searchVO
															    ,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 화면
			String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
			String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

			if(!"10".equals(orgnTypeCd) && (!"20".equals(orgnTypeCd) || !"10".equals(orgnLvlCd))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 필수값 검사
			if(KtisUtil.isEmpty(searchVO.getReqSeq())){
				throw new MvnoServiceException("필수값이 누락되었습니다.");
			}

			// 대리점코드 세팅
			if(!"10".equals(orgnTypeCd)){
				searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			// 대리점 VOC 목록 리스트 수정이력 조회
			searchVO.setSessionUserOrgnTypeCd(orgnTypeCd);
			List<?> resultList = acenVocService.getAcenVocHistList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "VOC1001060", "대리점 VOC 목록")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) 대리점 VOC 목록 리스트 수정이력 엑셀 다운로드 */
	@RequestMapping(value = "/voc/vocMgmt/getAcenVocHistListExcel.json", method = {POST, GET})
	public String getAcenVocHistListExcel(HttpServletRequest pRequest
																	     ,HttpServletResponse pResponse
																	     ,@RequestParam Map<String, Object> pRequestParamMap
																	     ,@ModelAttribute("searchVO") AcenVocVo searchVO
																	     ,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 화면
			String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
			String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

			if(!"10".equals(orgnTypeCd) && (!"20".equals(orgnTypeCd) || !"10".equals(orgnLvlCd))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 필수값 검사
			if(KtisUtil.isEmpty(searchVO.getReqSeq())){
				throw new MvnoServiceException("필수값이 누락되었습니다.");
			}

			// 대리점코드 세팅
			if(!"10".equals(orgnTypeCd)){
				searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			// 엑셀 리스트 조회
			searchVO.setSessionUserOrgnTypeCd(orgnTypeCd);
			List<?> resultList = acenVocService.getAcenVocHistListExcel(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "(개별)대리점VOC수정이력_";  //파일명
			String strSheetname = "(개별)대리점VOC수정이력";                //시트명

			// 엑셀 첫줄
			String [] strHead = {"상담일련번호", "계약번호", "수정일시", "대리점", "VOC유형", "처리기한", "현재상태", "상담내용", "처리내용", "해피콜 결과", "수정자"};	// 11개
			String [] strValue = {"vocSeq", "contractNum", "regstDttm", "vocAgntCdName", "vocSubCtgCdName", "dueDt", "statusName", "vocContent", "ansContent", "callContent", "regstName"}; // 11개

			// 엑셀 컬럼 사이즈
			int[] intWidth = {7000, 5000, 7000, 8000, 8000, 5000, 5000, 20000, 20000, 20000, 8000}; // 11개
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 11개

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
				pRequestParamMap.put("DUTY_NM"   ,"VOC");                                 // 업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                                // IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());                   // 파일크기
				pRequestParamMap.put("menuId"    ,pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT"  ,0);                                     // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		}catch (Exception e) {

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

	/** (A'Cen) 대리점 VOC 목록 개별건 조회 */
	@RequestMapping(value = "/voc/vocMgmt/getAcenVocInfo.json", method = {POST, GET})
	public String getAcenVocInfo(HttpServletRequest pRequest
															,HttpServletResponse pResponse
															,@RequestParam Map<String, Object> pRequestParamMap
															,@ModelAttribute("searchVO") AcenVocVo searchVO
															,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 화면
			String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
			String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

			if(!"10".equals(orgnTypeCd) && (!"20".equals(orgnTypeCd) || !"10".equals(orgnLvlCd))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 필수값 검사
			if(KtisUtil.isEmpty(searchVO.getReqSeq())){
				throw new MvnoServiceException("필수값이 누락되었습니다.");
			}

			// 대리점코드 세팅
			if(!"10".equals(orgnTypeCd)){
				searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			// 대리점 VOC 개별건 조회
			AcenVocVo rtnVocInfo = null; // 마스킹처리 필요없는 정보만 리턴하기 위한 용도
			AcenVocVo vocInfo = acenVocService.getAcenVocInfo(searchVO);

			if(vocInfo != null){
				rtnVocInfo = new AcenVocVo();
				rtnVocInfo.setVocContent(vocInfo.getVocContent());
				rtnVocInfo.setAnsContent(vocInfo.getAnsContent());
				rtnVocInfo.setCallContent(vocInfo.getCallContent());
				rtnVocInfo.setReqSeq(vocInfo.getReqSeq());
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("vocInfo", rtnVocInfo);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "VOC1001060", "대리점 VOC 목록")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) VOC 처리점 등록 */
	@RequestMapping(value = "/voc/vocMgmt/updateAcenVocInfo.json", method = {POST, GET})
	public String updateAcenVocInfo(HttpServletRequest pRequest
																 ,HttpServletResponse pResponse
																 ,@ModelAttribute("searchVO") AcenVocVo searchVO
																 ,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			// 본사만 허용
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 필수값 검사
			if(KtisUtil.isEmpty(searchVO.getReqSeq())){
				throw new MvnoServiceException("필수값이 누락되었습니다.");
			}

			// VOC 처리점 등록
			acenVocService.updateAcenVocInfo(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "VOC1001060", "대리점 VOC 목록")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) VOC 처리내용 수정 */
	@RequestMapping(value = "/voc/vocMgmt/updateAcenVocContent.json", method = {POST, GET})
	public String updateAcenVocContent(HttpServletRequest pRequest
																		,HttpServletResponse pResponse
																		,@ModelAttribute("searchVO") AcenVocVo searchVO
																		,ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);

			// 본사, 대리점 화면
			String orgnTypeCd = searchVO.getSessionUserOrgnTypeCd();
			String orgnLvlCd = searchVO.getSessionUserOrgnLvlCd();

			if(!"10".equals(orgnTypeCd) && (!"20".equals(orgnTypeCd) || !"10".equals(orgnLvlCd))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 필수값 검사
			String content = "ANS".equalsIgnoreCase(searchVO.getModType()) ? searchVO.getAnsContent() : searchVO.getCallContent();
			if(KtisUtil.isEmpty(searchVO.getReqSeq()) || KtisUtil.isEmpty(content) || KtisUtil.isEmpty(searchVO.getModType())){
				throw new MvnoServiceException("필수값이 누락되었습니다.");
			}

			// 해피콜 결과 수정은 본사만 허용
			if(!"ANS".equalsIgnoreCase(searchVO.getModType()) && !"10".equals(orgnTypeCd)){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 대리점코드 세팅
			if(!"10".equals(orgnTypeCd)){
				searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			// VOC 내용 수정
			acenVocService.updateAcenVocContent(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "VOC1001060", "대리점 VOC 목록")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** (A'Cen) 대리점 VOC 목록 유효성검사 */
	private boolean validationChk(AcenVocVo searchVO) {

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
