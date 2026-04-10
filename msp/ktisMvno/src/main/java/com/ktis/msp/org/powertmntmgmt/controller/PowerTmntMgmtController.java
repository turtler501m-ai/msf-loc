package com.ktis.msp.org.powertmntmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.powertmntmgmt.service.PowerTmntMgmtService;
import com.ktis.msp.org.powertmntmgmt.vo.PowerTmntMgmtVo;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : PowerTmntMgmtController
 * @Description : 직권해지 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@Controller
public class PowerTmntMgmtController extends BaseController {

	@Autowired
	private PowerTmntMgmtService powerTmntMgmtService;

	@Autowired
	private MaskingService maskingService;

	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownService  fileDownService;

	public PowerTmntMgmtController() {
		setLogPrefix("[PowerTmntMgmtController] ");
	}

	/**
	 * @Description : 직권해지 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/org/powertmntmgmt/msp_org_bs_1044.do", method={POST, GET})
	public ModelAndView PowerTmntMgmtGrid(HttpServletRequest request, HttpServletResponse response, PowerTmntMgmtVo powerTmntMgmtVo, ModelMap model){

		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("직권해지 초기 화면 START."));
		logger.debug(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(powerTmntMgmtVo);
			
			// 본사 권한
			if(!"10".equals(powerTmntMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("org/powertmntmgmt/msp_org_bs_1044");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description : 직권해지 리스트 조회
	 * @Param  : powerTmntMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/powertmntmgmt/powerTmntMgmtList.json")
	public String selectPowerTmntMgmtList(HttpServletRequest request, HttpServletResponse response, PowerTmntMgmtVo powerTmntMgmtVo, ModelMap model, @RequestParam Map<String, Object> pReqParamMap){

		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("직권해지 조회 START."));
		logger.debug(generateLogMsg("Return Vo [powerTmntMgmtVo] = " + powerTmntMgmtVo.toString()));
		logger.debug(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 권한
			if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = powerTmntMgmtService.selectPowerTmntMgmtList(powerTmntMgmtVo);
			
			resultMap = makeResultMultiRow(pReqParamMap, resultList);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("tlphNo", "TEL_NO");
			maskFields.put("custNm", "CUST_NAME");
			maskFields.put("bnkacnNo", "ACCOUNT");
			maskFields.put("address", "ADDR");
			maskFields.put("empNm", "CUST_NAME");
			maskFields.put("telNo1", "TEL_NO");
			
			maskingService.setMask(resultList, maskFields, pReqParamMap);
			
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			resultMap.clear();
			//logger.debug(generateLogMsg(String.format("사용자 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 판매점 보조금 내역 엑셀다운로드기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/powertmntmgmt/excelDownProc.json")
	public String excelDownProc(PowerTmntMgmtVo powerTmntMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("엑셀 저장 START."));
		logger.debug(generateLogMsg("Return Vo [powerTmntMgmtVo] = " + powerTmntMgmtVo.toString()));
		logger.debug(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 권한
			if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			powerTmntMgmtVo.setSearchStartDt(request.getParameter("searchStartDt"));
			powerTmntMgmtVo.setSearchEndDt(request.getParameter("searchEndDt"));
			
			List<?> powerTmntMgmtVos = new ArrayList<PowerTmntMgmtVo>();
			
			powerTmntMgmtVos = powerTmntMgmtService.selectPowerTmntMgmtListEx(powerTmntMgmtVo);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("tlphNo", "TEL_NO");
			maskFields.put("custNm", "CUST_NAME");
			maskFields.put("bnkacnNo", "ACCOUNT");
			maskFields.put("address", "ADDR");
			maskFields.put("empNm", "CUST_NAME");
			maskFields.put("telNo1", "TEL_NO");
			
			maskingService.setMask(powerTmntMgmtVos, maskFields, pReqParamMap);
			
			String serverInfo = propertyService.getString("excelPath");
			
			String strFilename = serverInfo  + "직권해지_";//파일명
			
			String strSheetname = "직권해지데이터";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"판매회사코드", "서비스계약번호", "청구계정번호", "고객번호", "전화번호", "고객명", "개통일시", "이용정지일시", "은행코드명", "가상계좌번호", "상품명", "주소", "우편번호",
					"담당자명", "담당자연락처", "미납금액", "데이터추출일자","구매유형", "모집대리점", "유심접점"};
			
			String [] strValue = {"slsCmpnCd", "svcCntrNo", "billAcntNo", "custNo", "tlphNo", "custNm", "openDt", "svcCntrStopDt", "bankNm", "bnkacnNo", "prdcNm", "address", "zipNo",
					"empNm", "telNo1", "unpdAmnt", "inputDate","reqBuyTypeNm","orgNm","fstUsimOrgNm" };
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 3000, 3000, 5000, 5000, 5000, 5000, 7000, 5000, 10000, 5000,
					3000, 5000, 5000, 8000, 5000, 5000, 5000};
			
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1, 0, 0, 0, 0};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, powerTmntMgmtVos, strHead, intWidth, strValue, request, response, intLen);
			
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}

			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
				
				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"ORG");                       //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", 0);                            //자료건수
				pReqParamMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유

				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
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
}
