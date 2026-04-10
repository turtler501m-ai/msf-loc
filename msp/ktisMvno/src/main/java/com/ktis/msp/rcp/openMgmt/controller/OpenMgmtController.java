package com.ktis.msp.rcp.openMgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rcp.custMgmt.vo.FileInfoCuVO;
import com.ktis.msp.rcp.openMgmt.service.OpenMgmtService;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtListExVO;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtListVO;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @Class Name : OpenMgmtController
 * @since 2011. 11. 08
 * @version 1.0
 * @see
 *
 */

@Controller
public class OpenMgmtController extends BaseController {
	
	/** openMgmtService */
	@Autowired
	private OpenMgmtService openMgmtService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;	

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	/**
	 * @Description : 개통관리 화면
	 * @Param  : OpenMgmtVO
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2017. 03. 28.
	 */
	@RequestMapping(value = "/rcp/openMgmt/getOpenMgmtListNew.do", method={POST, GET})
	public ModelAndView getOpenMgmtListNew(@ModelAttribute("searchVO") OpenMgmtVO searchVO,
				HttpServletRequest request, 
				HttpServletResponse response,
				@RequestParam Map<String, Object> pRequestParamMap,
				ModelMap model){
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");
			
			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			
			
			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			model.addAttribute("maskingYn", maskingYn);

			// 평생할인 부가서비스 가입 검증
			if("MSP1010057".equals(pRequestParamMap.get("trgtMenuId"))){
				model.addAttribute("prmContNum", pRequestParamMap.get("prmContNum"));
			}
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/openMgmt/openMgmtNew");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}		
	
	
	/**
	 * @Description : 개통관리 목록 조회
	 * @Param  : OpenMgmtListVO
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2017. 03. 28.
	 */
	@RequestMapping(value = "/rcp/openMgmt/getOpenMgmtListAjaxNew.json")
	public String getOpenMgmtListAjaxNew(@ModelAttribute("searchVO") OpenMgmtListVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------

			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd >= 20){
				if(typeCd == 20){//대리점
					searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
				} else if(typeCd == 30){//판매점
					searchVO.setpAgentCode(loginInfo.getUserOrgnId());
				}
			}
			
			List<OpenMgmtListVO> resultList = openMgmtService.getOpenMgmtListNew(searchVO, paramMap);
			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			}
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";

	}
	/**
	 * @Description : 스캔프로그램 validation체크(해지이관 고객은 사용X)
	 * @Param  : OpenMgmtListVO
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2018. 08. 09.
	 */
	@RequestMapping(value = "/rcp/openMgmt/ValidationScan.json")
	public String ValidationScan( 
			OpenMgmtListVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("스캔 Validation START."+searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		logger.info("paramMap.toString() : " + paramMap.toString());
		
		
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(resultMap);
			
			List<?> resultList = openMgmtService.ValidationScan(paramMap);
			
			for (int i = 0; i < resultList.size(); i++) {
				EgovMap tempMap = (EgovMap)resultList.get(i);
				logger.info("******계약번호 >> " +tempMap.get("contractNum") + "****************************");
				resultMap.put("contractNum", tempMap.get("contractNum"));
			}
			
			logger.info("******************************************************");
			logger.info("******건수 >> " + resultList.size() + "****************************");
			resultMap.put("TotalCnt", String.valueOf(resultList.size()));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			logger.info("******************************************************");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";

	}
	
	/**
	 * @Description : 개통관리 자료생성
	 * @Param  : OpenMgmtListExVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2017. 03. 28.
	 */
	@RequestMapping("/rcp/openMgmt/getOpenMgmtListExcelDownload.json")
	public String getOpenMgmtListExcelDwonload(@ModelAttribute("searchVO") OpenMgmtListExVO searchVO, 
					Model model, 
					@RequestParam Map<String, Object> pRequestParamMap,
					HttpServletRequest request, 
					HttpServletResponse response)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("개통관리 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [OpenMgmtVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","개통관리");
			String searchVal = "개통일자:"+searchVO.getLstComActvDateF()+"~"+searchVO.getLstComActvDateT()+
					"|검색구분:["+searchVO.getSearchGbn()+","+searchVO.getSearchName()+"]"+
					"|계약상태:"+searchVO.getSubStatus()+
					"|대리점:"+searchVO.getCntpntShopId()+
					"|판매점:"+searchVO.getpAgentCode()+
					"|구매유형:"+searchVO.getReqBuyTypeS()+
					"|미성년자여부:"+searchVO.getMinorYn()+
					"|업무구분:"+searchVO.getOperType()+
					"|상품구분:"+searchVO.getProdType()+
					"|모집경로:"+searchVO.getOnOffType()+
					"|할인유형:"+searchVO.getSprtTp() + 
					"|eSIM여부:" + searchVO.getEsimYn() + 
					"|유심접점:" + searchVO.getUsimOrgCd() +
					"|생년월일:" + searchVO.getpBirthDayVal()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
						
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00002");
			vo.setSessionUserId(loginInfo.getUserId());		
			vo.setExclDnldId(exclDnldId);	// 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"lstComActvDateF\":" + "\"" + searchVO.getLstComActvDateF() + "\""
						+ ",\"lstComActvDateT\":" + "\"" + searchVO.getLstComActvDateT() + "\"" 
						+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\"" 
						+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\"" 
						+ ",\"subStatus\":" + "\"" + searchVO.getSubStatus() + "\"" 
						+ ",\"cntpntShopId\":" + "\"" + searchVO.getCntpntShopId() + "\"" 
						+ ",\"cntpntShopNm\":" + "\"" + searchVO.getCntpntShopNm() + "\"" 
						+ ",\"pAgentCode\":" + "\"" + searchVO.getpAgentCode() + "\"" 
						+ ",\"pAgentName\":" + "\"" + searchVO.getpAgentName() + "\"" 
						+ ",\"reqBuyTypeS\":" + "\"" + searchVO.getReqBuyTypeS() + "\"" 
						+ ",\"minorYn\":" + "\"" + searchVO.getMinorYn() + "\"" 
						+ ",\"operType\":" + "\"" + searchVO.getOperType() + "\"" 
						+ ",\"prodType\":" + "\"" + searchVO.getProdType() + "\"" 
						+ ",\"onOffType\":" + "\"" + searchVO.getOnOffType() + "\"" 
						+ ",\"sprtTp\":" + "\"" + searchVO.getSprtTp() + "\"" 
						+ ",\"esimYn\":" + "\"" + searchVO.getEsimYn() + "\""
						+ ",\"usimOrgCd\":" + "\"" + searchVO.getUsimOrgCd() + "\"" 
						+ ",\"pBirthDayVal\":" + "\"" + searchVO.getpBirthDayVal() + "\"" 
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
	
	// 가입자 정보
	@RequestMapping("/rcp/openMgmt/getOpenMgmtListDtl.json")
	public String getOpenMgmtListDtl(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){

		logger.info("pRequestParamMap.toString() : " + pRequestParamMap.toString());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> resultList = openMgmtService.getOpenMgmtListDtl(pRequestParamMap);
			
			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}
			
			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		return "jsonView"; 
	}			
	
	// 계약 이력
	@RequestMapping("/rcp/openMgmt/getSubInfoHist.json")
	public String getSubInfoHist(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){

		logger.info("pRequestParamMap.toString() : " + pRequestParamMap.toString());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> resultList = openMgmtService.getSubInfoHist(pRequestParamMap);

			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);				
			}
			

			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		return "jsonView"; 
	}		
	
	
	// 단말기 이력
	@RequestMapping("/rcp/openMgmt/getModelHist.json")
	public String getModelHist(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){

		logger.info("pRequestParamMap.toString() : " + pRequestParamMap.toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> resultList = openMgmtService.getModelHist(pRequestParamMap);

			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}
			

			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		return "jsonView"; 
	}	
	

	// 상품 이력
	@RequestMapping("/rcp/openMgmt/getFeatureHist.json")
	public String getFeatureHist(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info("pRequestParamMap.toString() : " + pRequestParamMap.toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> resultList = openMgmtService.getFeatureHist(pRequestParamMap);

			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}
			


			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		return "jsonView"; 
	}	
	
	
	// 납부방법
	@RequestMapping("/rcp/openMgmt/getBanHist.json")
	public String getBanHist(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){

		logger.info("pRequestParamMap.toString() : " + pRequestParamMap.toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> resultList = openMgmtService.getBanHist(pRequestParamMap);
			
			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}
			


			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		return "jsonView"; 
	}	
	
	
	// 청구 수납 이력
	@RequestMapping("/rcp/openMgmt/getInvPymList.json")
	public String getInvPymList(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info("pRequestParamMap.toString() : " + pRequestParamMap.toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> resultList = openMgmtService.getInvPymList(pRequestParamMap);

			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);				
			}
			

			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		return "jsonView"; 
	}
	
	
	// 법정 대리인
	@RequestMapping("/rcp/openMgmt/getAgentInfo.json")
	public String getAgentInfo(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){

		logger.info("pRequestParamMap.toString() : " + pRequestParamMap.toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> resultList = openMgmtService.getAgentInfo(pRequestParamMap);

			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}
			

			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		return "jsonView"; 
	}
	
	
	// 법정대리인(앱설치확인)
	@RequestMapping("/rcp/openMgmt/updateAppInstCnfm.json")
	public String updateAppInstCnfm(@ModelAttribute("searchVO") OpenMgmtVO searchVO, 
			Model model, 
			@RequestParam Map<String, Object> paramMap,
			HttpServletRequest request, 
			HttpServletResponse response)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			searchVO.setInstCnfmId(loginInfo.getUserId());
			
			openMgmtService.updateAppInstYnCnfm(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	
	// 할인유형이력
	@RequestMapping("/rcp/openMgmt/getAddInfoList.json")
	public String getAddInfoList(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			logger.info("pRequestParamMap.toString() : " + pRequestParamMap.toString());
			
			List<OpenMgmtVO> resultList = openMgmtService.getAddInfoList(pRequestParamMap);

			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}
			

			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
	
	
	// 기기변경이력조회
	@RequestMapping("/rcp/openMgmt/getDvcChgList.json")
	public String getDvcChgList(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> resultList = openMgmtService.getDvcChgList(pRequestParamMap);
			
			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);				
			}
			
			model.addAttribute("result", resultMap);
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		return "jsonView";
	}
	
		
	// 기기변경이력조회
	@RequestMapping("/rcp/openMgmt/getDvcChgListExcel.json")
	public String getDvcChgListExcel(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<OpenMgmtVO> openMgmtList = (List<OpenMgmtVO>) openMgmtService.getDvcChgList(pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "기변이력_";//파일명
			String strSheetname = "기변이력";//시트명
			
			//엑셀 첫줄
			String [] strHead = {
					"계약번호","기변일시","기변유형","기변사유","모델ID","단말일련번호","할부개월","약정개월","출고가","할부원금","보증보험번호","판매정책"
			};
			String [] strValue = {
					"contractNum","applStrtDttm","dvcChgTpNm","dvcChgRsnNm","modelId","intmSrlNo","instMnthCnt","enggMnthCnt","outAmnt","instAmnt","grntInsrMngmNo","salePlcyNm"
			};
			int[] intWidth = {
					5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000
			};
			
			int[] intLen = {
					0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0
			};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			
			String strFileName = "";
			if(openMgmtList.size() <= 1000) {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, openMgmtList.iterator(), strHead, intWidth, strValue, pRequest, pResponse, intLen);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, openMgmtList.iterator(), strHead, strValue, pRequest, pResponse);
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
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))){
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
			
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");
			   
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();
			
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"RCP");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

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
	
	
	// 신청정보조회
	@RequestMapping("/rcp/openMgmt/getApplFormInfo.json")
	public String getApplFormInfo(@ModelAttribute("searchVO") OpenMgmtVO searchVO, 
			Model model, 
			@RequestParam Map<String, Object> paramMap,
			HttpServletRequest request, 
			HttpServletResponse response)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "sucess");
			
			resultMap.put("data", openMgmtService.getApplFormInfo(searchVO, paramMap));
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);

		return "jsonView";
	}
		
	
	// 녹취파일조회
	@RequestMapping("/rcp/openMgmt/getFile1.json")
	public String getFile1(FileInfoCuVO fileInfoVO, Model model, HttpServletRequest pRequest, HttpServletResponse pResponse)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("첨부 파일 명 찾기 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(fileInfoVO);
			
			List<?> resultList = openMgmtService.getFile1(fileInfoVO);
			
			for (int i = 0; i < resultList.size(); i++) {
				EgovMap tempMap = (EgovMap)resultList.get(i);
				
				if(i==0)
				{
					resultMap.put("fileNm", tempMap.get("fileNm"));
					resultMap.put("fileId", tempMap.get("fileId"));
				}
				
				if(i==1)
				{
					resultMap.put("fileNm1", tempMap.get("fileNm"));
					resultMap.put("fileId1", tempMap.get("fileId"));
				}
				
				if(i==2)
				{
					resultMap.put("fileNm2", tempMap.get("fileNm"));
					resultMap.put("fileId2", tempMap.get("fileId"));
				}
				if(i==3)
				{
					resultMap.put("fileNm3", tempMap.get("fileNm"));
					resultMap.put("fileId3", tempMap.get("fileId"));
				}
				if(i==4)
				{
					resultMap.put("fileNm4", tempMap.get("fileNm"));
					resultMap.put("fileId4", tempMap.get("fileId"));
				}
			}
			resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}	
	
	
	// 녹취파일업로드
	@RequestMapping("/rcp/openMgmt/fileUpLoad.do")
	public String fileUpUsingService(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pRequestParamMap.toString()));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInfoCuVO fileInfoVO = new FileInfoCuVO();
		
		String contractNum = "";
		Object temp = pRequestParamMap.get("contractNum");
		
		if(temp != null) {
			contractNum = String.valueOf(temp);
			fileInfoVO.setContractNum(contractNum);
		}
		
		String lSaveFileName = "";
		String lFileNamePc = "";

		Integer filesize = 0;
		
		try{	
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(fileInfoVO);
			
			fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			fileInfoVO.setRvisnId(loginInfo.getUserId());
			fileInfoVO.setFileSeq(loginInfo.getUserOrgnId());//초기값 조직ID
			
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);
	
			//--------------------------------
			// upload base directory를 구함
			// 존재하지 않으면 exception 발생
			//--------------------------------
			String lBaseDir = propertiesService.getString("orgPath");
			if ( !new File(lBaseDir ).exists())
			{
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}
			
			for (FileItem item : items) {
				
				//--------------------------------
				// 파일 타입이 아니면 skip
				//--------------------------------
				if (item.isFormField()) 
					continue;
	
				//--------------------------------
				// 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
				//--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertiesService.getString("fileUploadSizeLimit")) )
					continue;

				try
				{
					//--------------------------------
					// 모듈별 directory가 존재하지 않으면 생성
					//--------------------------------
					File lDir = new File(lBaseDir );
					if ( !lDir.exists())
					{
						lDir.mkdirs();
					}
					
					//----------------------------------------------------------------
					// upload 이름 구함
					//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file"))
					{
						//----------------------------------------------------------------
						// 파일 이름 구함
						//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());
						
						fileInfoVO.setAttRout(lBaseDir);
						
						lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
						String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;
						
						//----------------------------------------------------------------
						// 파일 write
						//----------------------------------------------------------------
						/** 20230518 PMD 조치 */
						InputStream filecontent = null;
						File f = null;
						OutputStream fout=null;
						
						try {
							filecontent = item.getInputStream();
							f=new File(lTransferTargetFileName);
		
							fout = new FileOutputStream(f);
							byte buf[]=new byte[1024];
							int len;

							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
						} catch (Exception e1) {
							//20200512 소스코드점검 수정
							//e1.printStackTrace();
							//System.out.println("Connection Exception occurred");
							//20210722 pmd소스코드수정
							logger.error("Connection Exception occurred");
						} finally{
							if (fout != null) {
								try {
									fout.close();
								} catch (Exception e) {
									throw new MvnoErrorException(e);
								}
							}

							if (filecontent != null) {
								try {
									filecontent.close();
								} catch (Exception e) {
									throw new MvnoErrorException(e);
								}
							}
						}
					}
				}catch (Exception e) {
					throw new MvnoErrorException(e);
				}
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
		} catch (Exception e) {
			resultMap.put("state", false);
			resultMap.put("name", lFileNamePc.replace("'","\\'"));
			resultMap.put("size",  111);
			
			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		}
		
		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lSaveFileName);
		fileInfoVO.setAttSctn("RCP");//조직 파일첨부
		
		//파일 테이블 insert
		openMgmtService.insertFileByCust(fileInfoVO);
		
		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);
		
		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	}
	
	public static String generationUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * @Description : 개통관리 화면 SCAN 테스트용 임시 화면
	 * @Param  : OpenMgmtVO
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2025. 09. 08.
	 */
	@RequestMapping(value = "/rcp/openMgmt/getOpenMgmtListNewScanT.do", method={POST, GET})
	public ModelAndView getOpenMgmtListNewScanT(@ModelAttribute("searchVO") OpenMgmtVO searchVO,
				HttpServletRequest request, 
				HttpServletResponse response,
				@RequestParam Map<String, Object> pRequestParamMap,
				ModelMap model){
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String scanSearchUrlT =  propertiesService.getString("scan.search.url.t");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");
			
			String scanDownloadUrl =  propertiesService.getString("scan.download.url.t");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanSearchUrlT : " + scanSearchUrlT);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrlT", scanSearchUrlT);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			
			
			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			model.addAttribute("maskingYn", maskingYn);

			// 평생할인 부가서비스 가입 검증
			if("MSP1010057".equals(pRequestParamMap.get("trgtMenuId"))){
				model.addAttribute("prmContNum", pRequestParamMap.get("prmContNum"));
			}
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/openMgmt/openMgmtNewScanT");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	
}
