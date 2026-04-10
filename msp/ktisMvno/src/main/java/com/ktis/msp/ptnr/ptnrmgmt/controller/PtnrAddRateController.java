package com.ktis.msp.ptnr.ptnrmgmt.controller;

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
import com.ktis.msp.ptnr.ptnrmgmt.service.PtnrAddSvcService;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInfoVO;
import com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;
@Controller
public class PtnrAddRateController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
    protected EgovPropertyService propertyService;

	@Autowired
	private PtnrAddSvcService ptnrAddSvcService;

	@Autowired
	private FileDownService  fileDownService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	
	/**
	 * @Description : 제휴부가서비스관리 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrAddSvc/ptnrAddSvc.do")
	public ModelAndView ptnrAddSvc(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
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
			modelAndView.setViewName("ptnr/ptnrMgmt/ptnrAddSvc");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
		
	
	/**
	 * @Description : 제휴부가서비스관리 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrAddSvc/ptnrAddSvcList.json")
	public String ptnrAddSvcList(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴부가서비스관리 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrAddSvcService.ptnrAddSvcList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "MSP6000022", "제휴부가서비스관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		


	@RequestMapping("/ptnr/ptnrAddSvc/getRateComboList.json")
	public String getRateComboList(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
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
			
			List<?> resultList = ptnrAddSvcService.getRateComboList(searchVO, pRequestParamMap);
			
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
	 * @Description : 제휴부가서비스이력 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrAddSvc/ptnrAddSvcHist.json")
	public String ptnrAddSvcHist(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴부가서비스이력 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrAddSvcService.ptnrAddSvcHist(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "MSP6000022", "제휴부가서비스관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : 요금제에 등록된 부가서비스이력 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrAddSvc/ptnrRateAddSvcComboList.json")
	public String ptnrRateAddSvcComboList(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제에 등록된 부가서비스 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrAddSvcService.ptnrRateAddSvcComboList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "MSP6000022", "제휴부가서비스관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		
	
	
	/**
	 * @Description : 제휴부가서비스관리 등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrAddSvc/ptnrAddSvcInsert.json")
	public String ptnrAddSvcInsert(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴부가서비스관리 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ptnrAddSvcService.ptnrAddSvcInsert(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "MSP6000022", "제휴부가서비스관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	
	/**
	 * @Description : 제휴부가서비스관리 수정
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrAddSvc/ptnrAddSvcUpdate.json")
	public String ptnrAddSvcUpdate(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴부가서비스관리 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ptnrAddSvcService.ptnrAddSvcUpdate(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "MSP6000022", "제휴부가서비스관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	
	

	/**
	 * @Description : 제휴부가서비스제 등록,수정 팝업 요금제 정보
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrAddSvc/ptnrAddSvcInfo.json")
	public String ptnrAddSvcInfo(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴부가서비스제 등록,수정 팝업 요금제 정보 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrAddSvcService.ptnrAddSvcInfo(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "MSP6000022", "제휴부가서비스관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		
		

	/**
	 * @Description : 제휴부가서비스관리 엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/ptnr/ptnrAddSvc/ptnrAddSvcListEx.json")
	public String ptnrAddSvcListEx(@ModelAttribute("searchVO") PtnrInfoVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴부가서비스관리 엑셀다운 START."));
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
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","PTNR");
			excelMap.put("MENU_NM","제휴부가서비스관리");
			String searchVal = "제휴사:"+searchVO.getPtnrId()+
					"|제휴요금:"+searchVO.getRateNm()+
					"|제휴부가서비스:"+searchVO.getAddSvcNm()+
					"|사용여부:"+searchVO.getUseYn()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> ptnrAddSvcList = ptnrAddSvcService.ptnrAddSvcListEx(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "제휴부가서비스관리_";//파일명
			String strSheetname = "제휴부가서비스관리";//시트명
			
			String [] strHead = {"제휴사","제휴요금제","제휴부가서비스","기본요금","지급포인트","사용여부","적용시작일시","적용종료일시"
								,"등록자"}; //11
			String [] strValue = {"ptnrNm","rateNm","addSvcNm","baseAmt","pointVal","useYn","strtDate","endDate"
								, "usrNm"}; //11
			int[] intWidth = {9000, 9000, 9000, 6000, 6000, 6000, 9000, 9000
							 ,6000};	
			int[] intLen = {0, 0, 0, 1, 1, 0, 0, 0, 0
						  , 0}; // 11

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			if(ptnrAddSvcList.size() <= 1000) {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, ptnrAddSvcList, strHead, intWidth, strValue, request, response, intLen);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, ptnrAddSvcList, strHead, strValue, request, response);
			}
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
}
