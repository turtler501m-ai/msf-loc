package com.ktis.msp.m2m.usimordaddrgmt.controller;

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
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;

import com.ktis.msp.m2m.usimordaddrgmt.service.UsimOrdAddrService;
import com.ktis.msp.m2m.usimordaddrgmt.vo.UsimOrdAddrVO;

import egovframework.rte.fdl.property.EgovPropertyService;
@Controller
public class UsimOrdAddrController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
    protected EgovPropertyService propertyService;

	@Autowired
	private UsimOrdAddrService usimOrdAddrService;

	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private LoginService loginService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	
	/**
	 * @Description : M2M USIM 발주사 배송지 관리 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimOrdAddr/usimOrdAddr.do")
	public ModelAndView usimOrdAddr(@ModelAttribute("searchVO") UsimOrdAddrVO searchVO, 
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
	        modelAndView.getModelMap().addAttribute("maskingYn", loginService.getUsrMskYn(loginInfo.getUserId()));
	        
			//////
			modelAndView.setViewName("m2m/usimordaddrgmt/usimOrdAddr");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
		
	
	/**
	 * @Description : M2M USIM 발주사 배송지관리 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimOrdAddr/usimOrdAddrList.json")
	public String usimOrdAddrList(@ModelAttribute("searchVO") UsimOrdAddrVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 발주사 배송지관리 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = usimOrdAddrService.usimOrdAddrList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000021", "M2M USIM 발주사 배송지 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : M2M USIM 발주사 배송지관리 등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimOrdAddr/usimOrdAddrInsert.json")
	public String usimOrdAddrInsert(@ModelAttribute("searchVO") UsimOrdAddrVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 발주사 배송지관리 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimOrdAddrService.usimOrdAddrInsert(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000021", "M2M USIM 발주사 배송지 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	
	/**
	 * @Description : M2M USIM 발주사 배송지관리 수정
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimOrdAddr/usimOrdAddrUpdate.json")
	public String usimOrdAddrUpdate(@ModelAttribute("searchVO") UsimOrdAddrVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 발주사 배송지관리 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimOrdAddrService.usimOrdAddrUpdate(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000021", "M2M USIM 발주사 배송지 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	
	/**
	 * @Description : M2M USIM 발주사 배송지관리 엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimOrdAddr/usimOrdAddrListEx.json")
	public String usimOrdAddrListEx(@ModelAttribute("searchVO") UsimOrdAddrVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 발주사 배송지관리 엑셀다운 START."));
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
			excelMap.put("MENU_NM","M2M USIM 발주사 배송지관리");
			String searchVal = "발주사:"+searchVO.getOrgnId()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> usimOrdAddrListEx = usimOrdAddrService.usimOrdAddrListEx(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "M2M USIM 발주사 배송지관리_";//파일명
			String strSheetname = "M2M USIM 발주사 배송지관리";//시트명
			
			String [] strHead = {"발주사","배송지명","배송지담당자","유선전화번호","핸드폰전화번호","우편번호","주소","상세주소","상용여부","등록자"}; //10
			String [] strValue = {"orgnNm","areaNm","maskMngNm","mngPhn","mngMblphn","post","addr","maskAddrDtl","useYn","usrNm"}; //10
			int[] intWidth = {6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000};	
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 10

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, usimOrdAddrListEx, strHead, intWidth, strValue, request, response, intLen);

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
	 * @Description : M2M USIM 발주사 배송지관리 발주사 Combo 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimOrdAddr/getOrgIdComboList.json")
	public String getOrgIdComboList(@ModelAttribute("searchVO") UsimOrdAddrVO searchVO, 
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
			
			List<?> resultList = usimOrdAddrService.getOrgIdComboList(searchVO, pRequestParamMap);
			
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
	 * @Description : M2M USIM 발주사 배송지관리 삭제
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimOrdAddr/usimOrdAddrDelete.json")
	public String usimOrdAddrDelete(@ModelAttribute("searchVO") UsimOrdAddrVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 발주사 배송지관리 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimOrdAddrService.usimOrdAddrDelete(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000021", "M2M USIM 발주사 배송지 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	
	
	
	
	
}
