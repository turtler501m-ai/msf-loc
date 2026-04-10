package com.ktis.msp.img.applicationForm.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktis.msp.base.mvc.BaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.img.applicationForm.service.AppFormService;
import com.ktis.msp.img.applicationForm.vo.ScanInfoVo;

import egovframework.rte.fdl.property.EgovPropertyService;


@Controller
public class AppFormController extends BaseController {
	
	private static final Log LOGGER = LogFactory.getLog(AppFormController.class);
	
	@Resource(name = "appFormService")
	protected AppFormService appFormService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	
    @Autowired  
    protected MessageSource messageSource; 

	@RequestMapping("/appForm/imageDelete.do")
	public String  imageDelete(HttpServletRequest request
								, HttpServletResponse response
								, @RequestParam Map<String, Object> paramMap
								, ModelMap model
								, RedirectAttributes redirectAttr) {
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// --------------------------------------------------------------------
			// Initialize.
			// --------------------------------------------------------------------
			//String fileId = String.valueOf(paramMap.get("fileId"));
			String callType = String.valueOf(paramMap.get("callType"));
			String resNo = String.valueOf(paramMap.get("resNo"));
			
			redirectAttr.addAttribute("resNo", resNo);
			redirectAttr.addAttribute("callType", callType);
			
			appFormService.deleteScanImage(paramMap);
			
			return "redirect:/scannerViewer.do";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	//스캐너 프로그램 실행
	@RequestMapping("/appForm/scannerAppliaction.do")
	public String  scannerAppliaction(HttpServletRequest request
									, HttpServletResponse response
									, @RequestParam Map<String, Object> paramMap
									, ModelMap model) {
		
		ScanInfoVo scanInfoVo = new ScanInfoVo();
		
		try {
			
			// --------------------------------------------------------------------
			// Initialize.
			// --------------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			String userId = loginInfo.getUserId();
			String userNm = loginInfo.getUserName();
			String orgNm = loginInfo.getUserOrgnNm();
			String orgOid = loginInfo.getUserOrgnId();
			String orgType = loginInfo.getUserOrgnTypeCd();
			
			String scanId = request.getParameter("scanId");
			String scanType = request.getParameter("scanType") == null ? "" : request.getParameter("scanType");
			
			
			//작성 사용자
			String regUserName = request.getParameter("userName") == null ? "" : request.getParameter("userName");
			String decodeUserName = URLDecoder.decode(request.getParameter("userName") == null ? "" : request.getParameter("userName") ,"UTF-8");
			
			//예약번호
			String resNo = request.getParameter("resNo") == null ? "" : request.getParameter("resNo");
			
			String version = appFormService.getScanViewerVersion();
			
			scanInfoVo.setRegUserName(regUserName);
			scanInfoVo.setResNo(resNo);
			scanInfoVo.setVersion(version);
			scanInfoVo.setScanType(scanType);
			//scanInfoVo.setScanId(scanId);
			
			scanInfoVo.setUrl(propertiesService.getString("scan.app.url"));
			scanInfoVo.setDownloadUrl(propertiesService.getString("scan.download.url"));
			scanInfoVo.setPort(propertiesService.getString("scan.app.port"));
			
			scanInfoVo.setUserId(userId);
			scanInfoVo.setUserNm(userNm);
			scanInfoVo.setOrgNm(orgNm);
			scanInfoVo.setOrgOid(orgOid);
			scanInfoVo.setRegUserName(decodeUserName);
			
			if(scanInfoVo.getUserId() == null) {
				scanInfoVo.setUserId("none");
			}
			
			if(scanInfoVo.getUserNm() == null) {
				scanInfoVo.setUserNm("none");
			}
			
			if(scanInfoVo.getOrgNm() == null) {
				scanInfoVo.setOrgNm("none");
			}
			
			if(scanInfoVo.getOrgOid() == null) {
				scanInfoVo.setOrgOid("none");
			}
			
			//조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
			if(orgType != null) {
				if(orgType.equals("10")) {
					scanInfoVo.setOrgType("K");
				}else if(orgType.equals("20")) {
					scanInfoVo.setOrgType("D");
				}else if(orgType.equals("30")) {
					scanInfoVo.setOrgType("S");
				}
			}else {
				scanInfoVo.setOrgType("D");
			}
			
			if(scanInfoVo.getScanType() != null && scanInfoVo.getScanType().equals("new")) {
				scanInfoVo.setScanId(scanId);
				//scanInfoVo.setResNo(res_no);
			}else if(scanInfoVo.getScanType() != null && scanInfoVo.getScanType().equals("edit")) {
				scanInfoVo.setScanId("");
			}else {
				scanInfoVo.setScanId("");
			}
			
			model.addAttribute("scanInfoVo", scanInfoVo);
			return "img/startScanner";
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	
	
	/**
	 * 스캔 뷰어 실헹
	 * @param request
	 * @param model
	 * @param scanInfoVo
	 * @param response
	 * @return
	 */
	@RequestMapping("/scannerViewer.do")
	public String  scannerViewer(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("scanInfoVo") ScanInfoVo scanInfoVo
								, ModelMap model){
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			String resNo = scanInfoVo.getResNo();
			String type = scanInfoVo.getCallType();
			
			if(resNo == null) {
				resNo = request.getParameter("resNo");
			}
			if (type == null) {
				type = request.getParameter("callType");
			}
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("callType", type);
			params.put("resNo", resNo);
			HashMap<String, String>  resultScanInfo = new HashMap<String, String>();
				
			resultScanInfo = appFormService.getScnViewerInfo(params);
			
			String userId = loginInfo.getUserId(); //사용자 아이디
			String userNm = loginInfo.getUserName(); // 사용자 이름
			String orgNm = loginInfo.getUserOrgnNm();
			String orgOid = loginInfo.getUserOrgnId();
			String orgType = loginInfo.getUserOrgnTypeCd();

			String scanId = resultScanInfo.get("SCAN_ID");
			String reqStateCode = resultScanInfo.get("REQUEST_STATE_CODE");
			String version = resultScanInfo.get("version");
			String rgstDt = resultScanInfo.get("rgstDt");
			
			scanInfoVo.setUrl(propertiesService.getString("scan.app.url"));
			scanInfoVo.setDownloadUrl(propertiesService.getString("scan.download.url"));
			scanInfoVo.setPort(propertiesService.getString("scan.app.port"));
			
			scanInfoVo.setVersion(version);
			scanInfoVo.setUserId(userId);
			scanInfoVo.setUserNm(userNm);
			scanInfoVo.setOrgNm(orgNm);
			scanInfoVo.setOrgOid(orgOid);
			
			if(scanInfoVo.getUserId() == null) {
				scanInfoVo.setUserId("none");
			}

			if(scanInfoVo.getUserNm() == null) {
				scanInfoVo.setUserNm("none");
			}

			if(scanInfoVo.getCallType() == null) {
				scanInfoVo.setCallType("C");
			}

			if(scanInfoVo.getOrgNm() == null) {
				scanInfoVo.setOrgNm("none");
			}

			if(scanInfoVo.getOrgOid() == null) {
				scanInfoVo.setOrgOid("none");
			}

			//조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
			if(orgType != null) {
				if(orgType.equals("10")) {
					scanInfoVo.setOrgType("K");
				}else if(orgType.equals("20")) {
					scanInfoVo.setOrgType("D");
				}else if(orgType.equals("30")) {
					scanInfoVo.setOrgType("S");
				}
			}else {
				scanInfoVo.setOrgType("S");
			}
			
			scanInfoVo.setScanId(scanId);
			
			scanInfoVo.setRequestStateCode(reqStateCode);
			
			scanInfoVo.setRgstDt(rgstDt);
			
			model.addAttribute("scanInfoVo", scanInfoVo);
			
			return "img/scannerImageViewer";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	
	/**
	 * 스캔 및 이미지 테스트
	 * @param request
	 * @param model
	 * @param paramMap
	 * @param response
	 * @return
	 */
	@RequestMapping("/appForm/scanTest.do")
	public String scanTest(HttpServletRequest request
							, HttpServletResponse response
							, @ModelAttribute("scanInfoVo") ScanInfoVo scanInfoVo
							, ModelMap model){
		
		//v2018.11 PMD 적용 소스 수정
	   //LoginInfo loginInfo = new LoginInfo(request, response);
	   new LoginInfo(request, response);
		
		String scanSearch = propertiesService.getString("scan.search.url");
		String faxSerarch = propertiesService.getString("fax.search.url");
		
		
		model.addAttribute("faxSerarch", faxSerarch);
		model.addAttribute("scanSearch", scanSearch);
		
		return "img/imageTest";
	}
	
	@RequestMapping("/appForm/getScanId.json")
	public String getUUID(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("scanInfoVo") ScanInfoVo scanInfoVo
								, ModelMap model){
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//v2018.11 PMD 적용 소스 수정
		   //LoginInfo loginInfo = new LoginInfo(request, response);
		   new LoginInfo(request, response);
			
			String scanId = appFormService.getUUID();
			
			resultMap.put("scanId", scanId);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
}
