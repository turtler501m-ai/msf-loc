package com.ktis.msp.m2m.usimrephistgmt.controller;

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
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;

import com.ktis.msp.m2m.usimrephistgmt.service.UsimRepHistService;
import com.ktis.msp.m2m.usimrephistgmt.vo.UsimRepHistVO;

import egovframework.rte.fdl.property.EgovPropertyService;
@Controller
public class UsimRepHistController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
    protected EgovPropertyService propertyService;

	@Autowired
	private UsimRepHistService usimRepHistService;

	@Autowired
	private FileDownService  fileDownService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	
	/**
	 * @Description : M2M USIM 보고서이력 조회 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimRepHist/usimRepHist.do")
	public ModelAndView usimRepHist(@ModelAttribute("searchVO") UsimRepHistVO searchVO, 
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
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("m2m/usimrephistgmt/usimRepHist");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
		
	
	/**
	 * @Description : M2M USIM 보고서이력 조회 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimRepHist/usimRepHistList.json")
	public String usimRepHistList(@ModelAttribute("searchVO") UsimRepHistVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 보고서이력 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = usimRepHistService.usimRepHistList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000021", "M2M USIM 보고서이력 조회"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : M2M USIM 보고서이력 등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimRepHist/usimRepHistInsert.json")
	public String usimRepHistInsert(@ModelAttribute("searchVO") UsimRepHistVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 보고서이력 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimRepHistService.usimRepHistInsert(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000021", "M2M USIM 보고서이력 조회"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	
	/**
	 * @Description : M2M USIM 보고서이력 조회 엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimRepHist/usimRepHistListEx.json")
	public String usimRepHistListEx(@ModelAttribute("searchVO") UsimRepHistVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 보고서이력 조회 엑셀다운 START."));
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
			excelMap.put("DUTY_NM","M2M");
			excelMap.put("MENU_NM","M2M USIM 보고서이력 조회");
			String searchVal = "주문번호:"+searchVO.getOrdId() +
                    "주문월:" + searchVO.getOrdDate() +
                    "USIM제품:"+searchVO.getRprsPrdtId() +
	                 "배송상태:"+searchVO.getOrdStatus() +
	                 "인수월:" +searchVO.getTakeDate() +
	                 "배송지명:"+searchVO.getAreaNm() +
	                 "보고서종류:"+searchVO.getReportCd()					                  
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> usimRepHistListEx = usimRepHistService.usimRepHistListEx(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "M2M USIM 보고서이력 조회_";//파일명
			String strSheetname = "M2M USIM 보고서이력 조회";//시트명

			String [] strHead = {"주문번호","보고서종류","출력일자","출력자","주문일자","출고일자","인수일자","배송상태","발주사","대표제품코드","제품명","단가(VAT별도)","수량","총 금액(VAT별도)","총 금액(VAT포함)","배송지담당자","유선전화번호","핸드폰전화번호","배송지명","등록자"}; //17
			String [] strValue = {"ordId","reportCdNm","reportDatetime","reportUsrNm","ordDate","sendDate","takeDate","ordStatusNm","orgnNm","rprsPrdtId","prdtNm","outUnitPric","ordCnt","pric","pricVat","maskMngNm","mngPhn","mngMblphn","areaNmText","usrNm"}; //17
			int[] intWidth = {6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000};	
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0}; 

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, usimRepHistListEx, strHead, intWidth, strValue, request, response, intLen);

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
				pRequestParamMap.put("DUTY_NM"   ,"PTNR");                       //업무명
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
	 * @Description : M2M USIM 보고서이력 조회 발주사 Combo 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimRepHist/getOrgIdComboList.json")
	public String getOrgIdComboList(@ModelAttribute("searchVO") UsimRepHistVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimRepHistService.getOrgIdComboList(searchVO, pRequestParamMap);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	

	/**
	 * @Description : M2M USIM 보고서 이력 조회 Mdl Combo 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimRepHist/getMdlIdComboList.json")
	public String getMdlIdComboList(@ModelAttribute("searchVO") UsimRepHistVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimRepHistService.getMdlIdComboList(searchVO, pRequestParamMap);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * @Description : M2M USIM 보고서이력조회 Area Nm Combo 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimRepHist/getAreaNmComboList.json")
	public String getAreaNmComboList(@ModelAttribute("searchVO") UsimRepHistVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimRepHistService.getAreaNmComboList(searchVO, pRequestParamMap);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	
	
}
