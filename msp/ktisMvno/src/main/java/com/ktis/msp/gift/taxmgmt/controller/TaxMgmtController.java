package com.ktis.msp.gift.taxmgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.gift.taxmgmt.service.TaxMgmtService;
import com.ktis.msp.gift.taxmgmt.vo.TaxMgmtVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryMgmtVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class TaxMgmtController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private FileDownService  fileDownService;

	@Autowired
    protected EgovPropertyService propertyService;

	@Autowired
	private TaxMgmtService taxMgmtService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/***************************************************************************************/
	/* 제세공과금관리 2024.01.09 추가 */
	/***************************************************************************************/
	
	/**
	 * @Description : 제세공과금 관리 화면
	 * @Author : 이승국
	 * @Create Date : 2024. 01. 10.
	 */
	@RequestMapping(value = "/gift/taxMgmt/taxMgmt.do")
	public ModelAndView taxMgmt(@ModelAttribute("searchVO") TaxMgmtVO searchVO, 
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			
			modelAndView.getModelMap().addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			modelAndView.getModelMap().addAttribute("srchEndDt", orgCommonService.getWantDay(+7));
			modelAndView.getModelMap().addAttribute("tomorrow", orgCommonService.getWantDay(+1));
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("gift/taxmgmt/taxMgmt");
			
			return modelAndView;
			
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
		
	
	/**
	 * @Description : 제세공과금 관리 조회
	 * @Author : 이승국
	 * @Create Date : 2024. 01. 10.
	 */
	@RequestMapping(value="/gift/taxMgmt/taxMgmtList.json")
	public String taxMgmtList(@ModelAttribute("searchVO") TaxMgmtVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> paramMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("GIFT 제세공과금 관리 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = taxMgmtService.getTaxSmsSendList(searchVO, paramMap);
			
			resultMap = makeResultMultiRow(paramMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "GIFT100041", "제세공과금관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		
	
	/**
	 * @Description : 제세공과금 상세 조회
	 * @Author : 이승국
	 * @Create Date : 2024. 01. 10.
	 */
	
	@RequestMapping(value = "/gift/taxMgmt/taxMgmtHist.json")
	public String taxMgmtHist(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") TaxMgmtVO searchVO
								, @RequestParam Map<String, Object> pReqParamMap
								, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			//본사화면일경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = taxMgmtService.getTaxSmsSendListDt(searchVO, pReqParamMap);
		
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
		} catch (Exception e) {
			
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 제세공과금 상세 엑셀다운
	 * @Author : 이승국
	 * @Create Date : 2024. 01. 10.
	 */
	@RequestMapping(value="/gift/taxMgmt/taxMgmtSendListEx.json")
	public String taxMgmtSendListEx(@ModelAttribute("searchVO") TaxMgmtVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> paramMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("GIFT 제세공과금 상세 엑셀다운 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","TAX");
			excelMap.put("MENU_NM","제세공과금관리");
			
			String searchVal = "제세공과금 번호:"+searchVO.getTaxNo();
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> resultList = taxMgmtService.getTaxMgmtListEx(searchVO, paramMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "제세공과금 관리_";//파일명
			String strSheetname = "제세공과금 관리";//시트명
			
			String [] strHead = {"계약번호","고객명","수신번호","주민번호","법정대리인 연락처","주소","회신일자"}; //7
			String [] strValue = {"contractNum","custName","telNo","ssn","agentTelNum","addr","recvTime"}; //7
			int[] intWidth = {6000, 6000, 9000, 9000, 9000, 30000 ,6000}; // 7	
			int[] intLen = {0, 0, 0, 0, 0, 0, 0}; // 7

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);

			logger.info("strFileName : "+strFileName);
			logger.info("#################### 데이터 엑셀로 변환 종료 #####################");
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
			
				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"TAX");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", resultList.size());                 //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				paramMap.put("EXCL_DNLD_ID", exclDnldId);
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		}finally {
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
	
	/**
	* @Description : 제세공과금 템플릿 엑셀 업로드 양식 다운로드
	* @Return : String
	*/
	@RequestMapping(value="/gift/taxMgmt/getTaxTmpExcelDown.json")
	public String getTaxTmpExcelDown(@ModelAttribute("searchVO") TaxMgmtVO searchVO,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam Map<String, Object> paramMap,
												ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = new ArrayList<UsimDlvryMgmtVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "템플릿엑셀업로드양식_";//파일명
			String strSheetname = "템플릿엑셀업로드양식";//시트명
			
			String [] strHead = {"계약번호"};
			String [] strValue = {"contractNum"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000};
			int[] intLen = {};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
			
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
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
	
	/**
	 * @Description : 제세공과금 템플릿등록
	 * @Author : 이승국
	 * @Create Date : 2024. 01. 10
	 */
	
	@RequestMapping("/gift/taxMgmt/insertTaxSmsTmp.json" )
	public String insertTaxSmsTmp(@ModelAttribute("searchVO") TaxMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제세공과금 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(searchVO);
			
			String resultMsg = null;
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");	
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();		
			String[] arrCell = {"contractNum"};
			
			// 예약전송시간체크 전송하려는 날짜 당일, 이전날짜는 신규등록 불가 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			Date now = new Date();
			Date reqDt = sdf.parse(paramMap.get("requestTime").toString()); // 예약일자
			
			if(!reqDt.after(now)){
				resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMsg = "이미 지난 발송일자이거나 예약하고싶은 일자는 오늘 이후의 일자로 등록완료해주세요";
			}else if(taxMgmtService.getChkTaxCnt(searchVO) > 0){
				resultMap.clear();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMsg = "이미 예약된 전송시간입니다. 다른시간으로 변경해주세요";
			}else{
				 // 엑셀업로드가 된 데이터를 가져와서 리스트로 만든다 
				List<Object> uploadList =
						ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.gift.taxmgmt.vo.TaxMgmtVO", sOpenFileName, arrCell);
				
				if (uploadList.size() > 1000 ) {
					resultMap.clear();
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMsg = "엑셀업로드는 1000건 이하로 등록해주세요.";
				}else {
					resultMap.clear();
					// TAX_NO 가져오기
					String  taxSmsListTaxNo = taxMgmtService.taxSmsListTaxNo(searchVO,paramMap);
					searchVO.setTaxNo(taxSmsListTaxNo);	
					searchVO.setRegstId(loginInfo.getUserId());
					taxMgmtService.insertTaxSmsTmp(uploadList,searchVO);
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
					resultMap.put("msg", "");
				}
			}
			resultMap.put("msg", resultMsg);
					
 			model.clear();
			model.addAttribute("result", resultMap);
		
		} catch (Exception e) {
			if(!getErrReturn(e, resultMap, request.getServletPath(), "", e.getMessage(), "GIFT100041", "제세공과금")) {
				throw new MvnoErrorException(e);
			}
			
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 제세공과금 리스트 삭제
	 * @Author : 이승국
	 * @Create Date : 2024. 01. 10
	 */
	@RequestMapping("/gift/taxMgmt/deleteTaxSmsList.json")
	public String deleteTaxSmsList(HttpServletRequest request,
			HttpServletResponse response,
			TaxMgmtVO searchVO,
			ModelMap model,
			@RequestParam Map<String, Object> paramMap,
			SessionStatus status)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			taxMgmtService.deleteTaxSmsList(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}
}
