package com.ktis.msp.gift.custgmt.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.gift.custgmt.service.CustMngService;
import com.ktis.msp.gift.custgmt.vo.CustMngVO;
import com.ktis.msp.gift.custgmt.vo.PrmtResultVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class CustMngController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
    protected EgovPropertyService propertyService;

	@Autowired
	private CustMngService custMngService;

	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;
	
	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	public CustMngController() {
		setLogPrefix("[CustMngController] ");
	}	
	
	/**
	 * @Description : 고객사은품 관리 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/gift/custgmt/custMngView.do")
	public ModelAndView custMngView(@ModelAttribute("searchVO") CustMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response,
					ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("billDt",orgCommonService.getToMonth());
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("gift/custgmt/custMng");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
		
	
	/**
	 * @Description : 고객 사은품 관리 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/gift/custgmt/custMngList.json")
	public String custMngList(@ModelAttribute("searchVO") CustMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객 사은품 관리 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = custMngService.custMngList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "GIFT100031", "고객 사은품 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : 고객이 선택한 사은품 List 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/gift/custgmt/custGiftList.json")
	public String custGiftList(@ModelAttribute("searchVO") CustMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객별 사은품 List 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = custMngService.getCustGiftList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "GIFT100031", "고객 사은품 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : 고객이 선택 가능한 사은품 List 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/gift/custgmt/custGiftPrmtPrdtList.json")
	public String custGiftPrmtPrdtList(@ModelAttribute("searchVO") CustMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객별 사은품 List 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = custMngService.getCustGiftPrmtPrdtList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "GIFT100031", "고객 사은품 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	
	
	
	
	
	/**
	 * @Description : 고객별 사은품 저장 
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/gift/custgmt/custGiftInsert.json")
	public String custGiftInsert(@ModelAttribute("searchVO") CustMngVO searchVO, 
					@RequestBody String data,
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객별 사은품 저장 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			//CustMngVO vo = new ObjectMapper().readValue(data, CustMngVO.class);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//제품정보
			String itemData = searchVO.getItemData();
			logger.debug("itemData:" + itemData);			

			List<PrmtResultVO> list = getVoFromMultiJson(itemData, "ALL", PrmtResultVO.class);
			
			//vo.setSessionUserId(searchVO.getSessionUserId());

			if(list.size() < 1){
				throw new EgovBizException("사은품 정보가 존재하지 않습니다");
			}
			
			//고객 사은품 배송지 저장
			custMngService.giftPrmtCustomerUpdate(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "GIFT100031", "고객 사은품 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : 고객 사은품 관리 엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/gift/custgmt/custGiftMngListEx.json")
	public String custGiftMngListEx(@ModelAttribute("searchVO") CustMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객 사은품 관리 엑셀다운 START."));
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
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);

			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","GIFT");
			excelMap.put("MENU_NM","고객 사은품 관리");
			String searchVal = "가입신청시작월:"+searchVO.getFrDate() +
                                     "가입신청종료월:" + searchVO.getToDate() +
	                                 "검색구분:"+searchVO.getSearchGbn() +
					                 "검색어:"+searchVO.getSearchTxt()					                  
					;
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> custMngListEx = custMngService.custMngListEx(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "고객 사은품 관리_";//파일명
			String strSheetname = "고객 사은품 관리";//시트명
			
			String [] strHead = {"프로모션ID","프로모션명","프로모션유형","계약번호","고객명","CTN","받는분고객명","받는분 전화번호1","받는분 전화번호2","우편번호","주소","상세주소","사은품명","수량","합계금액","제한수량","제한금액","신청일","개통일","사은품신청일","고객최초요금제명","현재요금제명","대리점" ,"최초유심접점" , "현요금제변경일시" , "현상태"};	//26
			
			String [] strValue = {"prmtId","prmtNm","prmtTypeNm","contractNum","subLinkName","subscriberNo","maskMngNm","tel1","tel2","post","addr","maskAddrDtl","prdtNm","quantity","sumAmount","choiceLimit","amountLimit","reqInDay","lstComActvDate","giftRegstDate","fstRateNm","lstRateNm", "openAgntNm" ,"fstUsimOrgNm", "lstRateDt" , "subStatusNm"};//26

			int[] intWidth = {6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000};	
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; 

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, custMngListEx, strHead, intWidth, strValue, request, response, intLen);

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
			
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"GIFT");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID", exclDnldId);
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
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
	 * @Description : 고객 사은품 관리 제품엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/gift/custgmt/custGiftMngListEx2.json")
	public String custGiftMngListEx2(@ModelAttribute("searchVO") CustMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객 사은품 관리 제품 엑셀다운 START."));
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
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);

			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","GIFT");
			excelMap.put("MENU_NM","고객 사은품 관리");
			String searchVal = "가입신청시작월:"+searchVO.getFrDate() +
                                     "가입신청종료월:" + searchVO.getToDate() +
                                     "검색구분:"+searchVO.getSearchGbn() +
	                                 "검색어:"+searchVO.getSearchTxt()					                  
					                 ;
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> custMngListEx2 = custMngService.custMngListEx2(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFilename = serverInfo  + "고객 사은품 제품_";//파일명
			String strSheetname = "고객 사은품 제품";//시트명

			String [] strHead = {"프로모션ID","프로모션명","프로모션유형","계약번호","고객명","CTN","받는분고객명","받는분 전화번호1","받는분 전화번호2","우편번호","주소","상세주소","사은품명","수량","합계금액","제한수량","제한금액","신청일","개통일","사은품신청일","고객최초요금제명","현재요금제명" ,"대리점" ,"최초유심접점" , "현요금제변경일시" , "현상태"};	//22
			
			String [] strValue = {"prmtId","prmtNm","prmtTypeNm","contractNum","subLinkName","subscriberNo","maskMngNm","tel1","tel2","post","addr","maskAddrDtl","prdtNm","quantity","sumAmount","choiceLimit","amountLimit","reqInDay","lstComActvDate","giftRegstDate","fstRateNm","lstRateNm" , "openAgntNm" ,"fstUsimOrgNm", "lstRateDt" , "subStatusNm"};//22

			int[] intWidth = {6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000 , 6000, 6000, 6000, 6000};	
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; 


			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, custMngListEx2, strHead, intWidth, strValue, request, response, intLen);

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
			
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"GIFT");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID", exclDnldId);
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
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
	 * @Description : 고객 사은품 관리 엑셀다운 excel to batch
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/gift/custgmt/getCustGiftMngListExcelToBatch.json")
	public String getCustGiftMngListExcelToBatch(@ModelAttribute("searchVO") CustMngVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> pRequestParamMap,
					ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();


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
			excelMap.put("DUTY_NM","GIFT");
			excelMap.put("MENU_NM","고객 사은품 관리 엑셀다운");
			String searchVal = "가입신청시작월:"+searchVO.getFrDate() +
                    " | 가입신청종료월:" + searchVO.getToDate() +
                    " | 검색구분:"+searchVO.getSearchGbn() +
	                " | 검색어:"+searchVO.getSearchTxt() ;
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			// 다운로드 이력 저장
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			// 다운로드 할 데이터를 조회 > 조회하지않고 파라미터만 넘김
			//List<?> custMngListEx = custMngService.custMngListEx(searchVO, pRequestParamMap);
			
			
			// 본사 화면인 경우 Exception 발생시키기 필요한지?
			//if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
			//	throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			//}
			
			BatchJobVO vo = new BatchJobVO();
			
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00193");
			vo.setSessionUserId(loginInfo.getUserId());	
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"frDate\":" + "\"" + searchVO.getFrDate() + "\""
					+ ",\"toDate\":" + "\"" + searchVO.getToDate() + "\"" 
					+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\"" 
					+ ",\"searchTxt\":" + "\"" + searchVO.getSearchTxt() + "\""
					+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" 
					+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\"" 
					+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" 
					+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\"" 
					+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}" 
				);
			
			// 배치 작업 요청 insert 
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
	

	/**
	 * @Description : 고객 사은품 관리 제품 엑셀다운 excel to batch
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/gift/custgmt/custGiftMngProdListExcelToBatch.json")
	public String custGiftMngProdListExcelToBatch(@ModelAttribute("searchVO") CustMngVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> pRequestParamMap,
					ModelMap model)
	{


		Map<String, Object> resultMap = new HashMap<String, Object>();


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
			excelMap.put("DUTY_NM","GIFT");
			excelMap.put("MENU_NM","고객 사은품 관리 제품 엑셀다운");
			String searchVal = "가입신청시작월:"+searchVO.getFrDate() +
                    " | 가입신청종료월:" + searchVO.getToDate() +
                    " | 검색구분:"+searchVO.getSearchGbn() +
	                " | 검색어:"+searchVO.getSearchTxt() ;
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			// 다운로드 이력 저장
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			

			BatchJobVO vo = new BatchJobVO();
			
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00194");
			vo.setSessionUserId(loginInfo.getUserId());	
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"frDate\":" + "\"" + searchVO.getFrDate() + "\""
					+ ",\"toDate\":" + "\"" + searchVO.getToDate() + "\"" 
					+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\"" 
					+ ",\"searchTxt\":" + "\"" + searchVO.getSearchTxt() + "\""
					+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" 
					+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\"" 
					+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" 
					+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\"" 
					+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}" 
				);
			
			// 배치 작업 요청 insert 
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
		
	
	
	/**
	 * @Description : 고객이 선택한(M포털 포함) 사은품 List 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/gift/custgmt/custGiftPrmtPrdtResultList.json")
	public String custGiftPrmtPrdtResultList(@ModelAttribute("searchVO") CustMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객이 선택한(M포털 포함) 사은품 List 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			List<?> prmtList = custMngService.custGiftPrmtPrdtResultList(searchVO.getRequestKey(), searchVO.getMenuId());
			resultMap = makeResultMultiRow(pRequestParamMap, prmtList);
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			getErrReturn(e, resultMap, request.getServletPath(),  messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), "", "GIFT100031", "고객 사은품 관리");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

}
