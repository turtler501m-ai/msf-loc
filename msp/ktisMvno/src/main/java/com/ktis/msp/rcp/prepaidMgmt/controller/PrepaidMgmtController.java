package com.ktis.msp.rcp.prepaidMgmt.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.prepaidMgmt.service.PrepaidMgmtService;
import com.ktis.msp.rcp.prepaidMgmt.vo.PrepaidVo;

import egovframework.rte.fdl.property.EgovPropertyService;




@Controller
public class PrepaidMgmtController extends BaseController {
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private PrepaidMgmtService prepaidMgmtService;
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Autowired
    private FileDownService  fileDownService;
	
	@RequestMapping(value = "/rcp/prepaid/view.do")
	public ModelAndView view (
				ModelMap model, 
				HttpServletRequest pRequest, 
				HttpServletResponse pResponse, 
				@RequestParam Map<String, Object> pRequestParamMap
	){
		logger.info("===========================================");
		logger.debug("PrepaidController  : 청소년요금제 조회 화면 컨트롤러");
		logger.info("===========================================");
		
		
		ModelAndView mv = new ModelAndView();
		
		try {		
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
	    	model.addAttribute("startDate",orgCommonService.getToDay());
    		model.addAttribute("endDate",orgCommonService.getWantDay(7));
    		model.addAttribute("pastDate",orgCommonService.getWantDay(-7));
    		
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

//	        System.out.println(menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.setViewName("/rcp/prepaid/view");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}   	
	}
	
	@RequestMapping(value = "/rcp/prepaid/list.json")
	public String ListData(@ModelAttribute("PrepaidVo") PrepaidVo pVo, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> rowsMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(pVo);
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------

			/*int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd >= 20){
				if(typeCd == 20){//대리점
					sucVo.setCntpntShopId(loginInfo.getUserOrgnId());
				}
			}*/
			
			List<?> custList = prepaidMgmtService.getList(pVo, paramMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			rowsMap.put("pageIndex", paramMap.get("pageIndex"));
			rowsMap.put("pageSize", paramMap.get("pageSize"));
			rowsMap.put("total",  custList.size() == 0 ?  "0" : ((Map) custList.get(0)).get("totalCount"));
			rowsMap.put("rows", custList);

			resultMap.put("data", rowsMap);

		} catch (Exception e) {
			
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					e.getMessage(), "MSP100011", "충전내역조회"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			} 			
		}
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping(value = "/rcp/prepaid/listExcel.json")
	public String getlistExcel(@ModelAttribute("PrepaidVo") PrepaidVo pVo,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
			@RequestParam Map<String, Object> paramMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("충전내역 조회 엑셀 저장 START"));
		logger.info(generateLogMsg("Return Vo [PrepaidVo] = " + pVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
	
			
			List<?> aryRsltList = prepaidMgmtService.getlistExcel(pVo, paramMap);

			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch (Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}

			String strFilename = serverInfo + "충전내역_";// 파일명
			String strSheetname = "충전내역";// 시트명
			
			String[] strHead = {  "요청일자", "계약번호", "전화번호", "요금제", "충전대리점"
								, "충전금액", "충전성공여부", "충전결과", "최대충전가능금액", "기본알"
								, "충전알", "데이터알", "문자알"};
			String[] strValue = { "reqDate", "contractNum", "subscriberNo", "lstRateNm", "rechargeAgent"
								, "recharge", "retCodeNm", "retMsg", "oTesChargeMax", "oTesBaser"
								, "oTesChgr", "oTesMagicr", "oTesFsmsr"};
			int[] intWidth = {    4000, 4000, 4000, 6000, 4000
								, 3000, 4000, 10000, 5000, 3000
								, 3000, 3000, 3000};
			int[] intLen = {  0, 0, 0, 0, 0
							, 1, 0, 0, 1, 1
							, 1, 1, 1};
			
			// 파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, aryRsltList, strHead, intWidth, strValue, request, response, intLen);

			logger.info("strFileName : " + strFileName);
			logger.info("#################### 데이터 엑셀로 변환 종료 #####################");
			
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
				
				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");
				
				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
				
				paramMap.put("FILE_NM", file.getName()); // 파일명
				paramMap.put("FILE_ROUT", file.getPath()); // 파일경로
				paramMap.put("DUTY_NM", "CUST"); // 업무명
				paramMap.put("IP_INFO", ipAddr); // IP정보
				paramMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				paramMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				paramMap.put("DATA_CNT", 0); // 자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); // 사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================
			
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
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}	
		
		
}
