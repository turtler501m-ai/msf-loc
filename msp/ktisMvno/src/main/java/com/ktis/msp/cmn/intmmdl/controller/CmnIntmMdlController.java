/**
 * 
 */
package com.ktis.msp.cmn.intmmdl.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.intmmdl.service.CmnIntmMdlService;
import com.ktis.msp.cmn.intmmdl.vo.CmnIntmMdlVO;
import com.ktis.msp.cmn.login.service.MenuAuthService;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : CmnIntmMdlController
 * @Description : 제품관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.07.28 심정보 최초생성
 * @
 * @author : 심정보
 * @Create Date : 2015. 7. 28.
 */
@Controller
public class CmnIntmMdlController extends BaseController {

	@Autowired
	private CmnIntmMdlService cmnIntmMdlService;
	
	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownService  fileDownService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	public CmnIntmMdlController() {
		setLogPrefix("[CmnIntmMdlController] ");
	}
	
	
		/**
		 * @Description : 제품관리 초기 화면 호출
		 * @Param  : void
		 * @Return : ModelAndView
		 * @Author : 심정보
		 * @Create Date : 2015. 7. 28.
		 */
	@RequestMapping(value = "/org/hndsetmgmt/hmdstModel.do")
	public ModelAndView intmMdlGrid(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("cmnIntmMdlVO") CmnIntmMdlVO cmnIntmMdlVO,
								ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("cmn/intmmdl/msp_org_bs_1020_1");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 대표모델 리스트 조회
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	@RequestMapping("/cmn/intmmdl/intmMdlList.json")
	public String selectIntmMdlList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("cmnIntmMdlVO") CmnIntmMdlVO cmnIntmMdlVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			List<?> resultList = cmnIntmMdlService.selectIntmMdlList(cmnIntmMdlVO);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP2000030", "모델관리"))
			{
				throw new MvnoErrorException(e);
			} 
		}
		
		return "jsonView"; 
	}
	
	/**
	 * 대표모델 리스트 엑셀다운로드
	 */
	@RequestMapping(value = "/cmn/intmmdl/selectIntmMdlListExcel.json")
	public String selectIntmMdlListExcel(@ModelAttribute("searchVO") CmnIntmMdlVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
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
			excelMap.put("DUTY_NM","CMN");
			excelMap.put("MENU_NM","모델관리(대표모델)");
			String searchVal = 
					"|대표모델ID:"+searchVO.getRprsPrdtId()+
					"|색상모델ID:"+searchVO.getPrdtId()+
					"|제조사명:"+searchVO.getMnfctId()+
					"|모델유형:"+searchVO.getPrdtTypeCd()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
									
			List<?> resultList = cmnIntmMdlService.selectIntmMdlListExcel(paramMap);

			String serverInfo = propertiesService.getString("excelPath");

			String strFilename = serverInfo  + "모델관리(대표모델)_";//파일명
			String strSheetname = "모델관리(대표모델)";//시트명

			//엑셀 첫줄
			String [] strHead = {"대표모델ID","대표모델코드","모델명","제조사","모델유형","모델구분","NFC사용여부","출시일자","단종일자"}; // 9

			String [] strValue = {"rprsPrdtId","prdtCode","prdtNm","mnfctNm","prdtTypeNm","prdtIndNm","nfcUsimYn","prdtLnchDt","prdtDt"}; // 9

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000,8000,8000,8000,5000,5000,5000,5000,5000};	// 9

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 9

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
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
					paramMap.put("DUTY_NM"   ,"CMN");                       //업무명
					paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
					paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
					paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
					paramMap.put("DATA_CNT", resultList.size());            //자료건수
					paramMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
					paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
					paramMap.put("EXCL_DNLD_ID", exclDnldId);

					fileDownService.insertCmnFileDnldMgmtMst(paramMap);
				}
				//=======파일다운로드사유 로그 END==========================================================


			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
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
	 * @Description : 색상모델 리스트 조회
	 * @Param  : HndstAmtVo
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	@RequestMapping("/cmn/intmmdl/intmColrMdlList.json")
	public String selectIntmColrMdlList(HttpServletRequest request, 
								HttpServletResponse response, 
								CmnIntmMdlVO cmnIntmMdlVO, 
								ModelMap model, 
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		try { 
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			List<?> resultList = cmnIntmMdlService.selectIntmColrMdlList(cmnIntmMdlVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		return "jsonView"; 
	}
	
	
	
	/**
	 * 색상모델 리스트 엑셀다운로드
	 */
	@RequestMapping(value = "/cmn/intmmdl/selectIntmColrMdlListExcel.json")
	public String selectIntmColrMdlListExcel(@ModelAttribute("searchVO") CmnIntmMdlVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
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
			excelMap.put("DUTY_NM","CMN");
			excelMap.put("MENU_NM","모델관리(색상모델)");
			String searchVal = 
					"|대표모델ID:"+searchVO.getRprsPrdtId()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
									
			List<?> resultList = cmnIntmMdlService.selectIntmColrMdlListExcel(paramMap);

			String serverInfo = propertiesService.getString("excelPath");

			String strFilename = serverInfo  + "모델관리(색상모델)_";//파일명
			String strSheetname = "모델관리(색상모델)";//시트명

			//엑셀 첫줄
			String [] strHead = {"대표모델ID","색상모델ID","색상모델코드","색상","단종일자"}; // 5

			String [] strValue = {"rprsPrdtId","prdtId","prdtCode","prdtColrNm","prdtDt"}; // 5

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000,5000,5000,5000,5000};	//5

			int[] intLen = {0, 0, 0, 0, 0};

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
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
					paramMap.put("DUTY_NM"   ,"CMN");                       //업무명
					paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
					paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
					paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
					paramMap.put("DATA_CNT", resultList.size());            //자료건수
					paramMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
					paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
					paramMap.put("EXCL_DNLD_ID", exclDnldId);

					fileDownService.insertCmnFileDnldMgmtMst(paramMap);
				}
				//=======파일다운로드사유 로그 END==========================================================


			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
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
	 * @Description : 대표모델명 조회
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	@RequestMapping("/cmn/intmmdl/selectRprsPrdtNm.json")
	public String selectRprsPrdtNm(HttpServletRequest request, 
								HttpServletResponse response, 
								CmnIntmMdlVO cmnIntmMdlVO, 
								ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델명 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			List<?> resultList = cmnIntmMdlService.selectRprsPrdtNm(cmnIntmMdlVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("resultList", resultList);
			resultMap.put("resultCnt", resultList.size());
			resultMap.put("msg", "");
			
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP2000030", "모델관리"))
			{
				throw new MvnoErrorException(e);
			} 
		}
		model.addAttribute("result", resultMap);
		return "jsonView"; 
	}
	
	/**
	 * @Description : 대표모델ID 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	@RequestMapping("/cmn/intmmdl/isExistRprsPrdtId.json")
	public String isExistRprsPrdtId(HttpServletRequest request, 
									HttpServletResponse response, 
									CmnIntmMdlVO cmnIntmMdlVO, 
									ModelMap model)
	{ 
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델ID 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnIntmMdlVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = cmnIntmMdlService.isExistRprsPrdtId(cmnIntmMdlVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

			logger.info(generateLogMsg("중복 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
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
	 * @Description : 모델코드(대표) 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	@RequestMapping("/cmn/intmmdl/isExistRprsPrdtCode.json")
	public String isExistRprsPrdtCode(CmnIntmMdlVO cmnIntmMdlVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("모델코드(대표) 중복체크 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnIntmMdlVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = cmnIntmMdlService.isExistRprsPrdtCode(cmnIntmMdlVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

			logger.info(generateLogMsg("중복 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
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
	 * @Description : 대표모델 등록
	 * @Param  : CmnIntmMdlVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	@RequestMapping("/cmn/intmmdl/insertIntmMdl.json")
	public String insertIntmMdl(CmnIntmMdlVO cmnIntmMdlVO, HttpServletRequest request, HttpServletResponse response ,ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		//  Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnIntmMdlVO);
		
		try {
			
			// 본사 화면인 경우
			if(!"10".equals(cmnIntmMdlVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			cmnIntmMdlVO.setRegId(loginInfo.getUserId());	/** 사용자ID */
			cmnIntmMdlVO.setRvisnId(loginInfo.getUserId());
			cmnIntmMdlVO.setPrdtId(cmnIntmMdlVO.getRprsPrdtId());
			cmnIntmMdlVO.setRprsYn("Y");
			logger.info(generateLogMsg("Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
			
			int returnCnt = cmnIntmMdlService.insertIntmMdl(cmnIntmMdlVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("등록 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
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
	 * @Description : 대표모델 수정
	 * @Param  : CmnIntmMdlVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	@RequestMapping("/cmn/intmmdl/updateIntmMdl.json")
	public String updateIntmMdl(CmnIntmMdlVO cmnIntmMdlVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 수정 START."));
		logger.info(generateLogMsg("Return Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		//  Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnIntmMdlVO);
		
		try {
			
			// 본사 화면인 경우
			if(!"10".equals(cmnIntmMdlVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			cmnIntmMdlVO.setRegId(loginInfo.getUserId());	/** 사용자ID */
			cmnIntmMdlVO.setRvisnId(loginInfo.getUserId());
			logger.info(generateLogMsg("Return Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
			
			int returnCnt = cmnIntmMdlService.updateIntmMdl(cmnIntmMdlVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
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
	 * @Description : 대표모델 찾기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
	@RequestMapping(value = "/cmn/intmmdl/intmMdlForm.do", method={POST, GET})
	public String intmMdlForm(@ModelAttribute("CmnIntmMdlVO") CmnIntmMdlVO cmnIntmMdlVO, 
							HttpServletRequest pRequest,
							HttpServletResponse pResponse)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 찾기 화면 호출 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToVo(cmnIntmMdlVO);
		
		return "cmn/intmmdl/msp_org_pu_1020_1";
	}
	
	/**
	 * @Description : 대표모델찾기(단말기) 화면 호출  
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 21.
	 */
	 @RequestMapping("/cmn/intmmdl/intmMdlMbForm.do")
	public ModelAndView intmMdlMbForm( @ModelAttribute("CmnIntmMdlVO") CmnIntmMdlVO cmnIntmMdlVO, 
									ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap)
	{
		ModelAndView mv = new ModelAndView();
		
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToVo(cmnIntmMdlVO);
		
		mv.setViewName("/cmn/intmmdl/msp_org_pu_1020_1");
		return mv; 
	}
	/**
	 * @Description : 색상모델 찾기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
	@RequestMapping(value = "/cmn/intmmdl/intmColrMdlForm.do", method={POST, GET})
	public String intmColrMdlForm( @ModelAttribute("CmnIntmMdlVO") CmnIntmMdlVO cmnIntmMdlVO, 
								ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 찾기 화면 호출 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToVo(cmnIntmMdlVO);
		
		return "cmn/intmmdl/msp_org_pu_1020_2";
//		return "org/hndsetmgmt/msp_org_pu_1020";
	}
	
	/**
	 * @Description : 색상모델 찾기 화면 호출
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 21.
	 */
	 @RequestMapping("/cmn/intmmdl/intmColrMdlMbForm.do")
	public ModelAndView intmColrMdlMbForm( @ModelAttribute("CmnIntmMdlVO") CmnIntmMdlVO cmnIntmMdlVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		ModelAndView mv = new ModelAndView();
		
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToVo(cmnIntmMdlVO);
		
		mv.setViewName("cmn/intmmdl/msp_org_pu_1020_2");
		return mv; 
	}
	 
	/**
	 * @Description : 색상모델 팝업 리스트 조회
	 * @Param  : HndstAmtVo
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	@RequestMapping("/cmn/intmmdl/intmColrMdlPopUpList.json")
	public String intmColrMdlPopUpList(HttpServletRequest request, 
									HttpServletResponse response, 
									CmnIntmMdlVO cmnIntmMdlVO, 
									ModelMap model, 
									@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 팝업 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = null;
		try { 
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			List<?> resultList = cmnIntmMdlService.intmColrMdlPopUpList(cmnIntmMdlVO);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			} 
		}
		return "jsonView"; 
	}

	/**
	 * @Description : 색상모델ID 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	@RequestMapping("/cmn/intmmdl/isExistPrdtId.json")
	public String isExistPrdtId(CmnIntmMdlVO cmnIntmMdlVO,
								HttpServletRequest request, 
								HttpServletResponse response,
								ModelMap model)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델ID 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			int returnCnt = cmnIntmMdlService.isExistPrdtId(cmnIntmMdlVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

			logger.info(generateLogMsg("중복 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
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
	 * @Description : 색상모델 등록
	 * @Param  : CmnIntmMdlVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	@RequestMapping("/cmn/intmmdl/insertIntmColrMdl.json")
	public String insertIntmColrMdl(CmnIntmMdlVO cmnIntmMdlVO,
									HttpServletRequest request, 
									HttpServletResponse response,
									ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnIntmMdlVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			cmnIntmMdlVO.setRegId(loginInfo.getUserId());	/** 사용자ID */
			cmnIntmMdlVO.setRvisnId(loginInfo.getUserId());
			cmnIntmMdlVO.setRprsYn("N"); //대표여부
			
//			int returnCnt = 0;
			int returnCnt = cmnIntmMdlService.insertIntmColrMdl(cmnIntmMdlVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("등록 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
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
	 * @Description : 색상모델 수정
	 * @Param  : CmnIntmMdlVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	@RequestMapping("/cmn/intmmdl/updateIntmColrMdl.json")
	public String updateIntmColrMdl(CmnIntmMdlVO cmnIntmMdlVO, 
									HttpServletRequest request, 
									HttpServletResponse response, 
									ModelMap model)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 수정 START."));
		logger.info(generateLogMsg("Return Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnIntmMdlVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnIntmMdlVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			cmnIntmMdlVO.setRegId(loginInfo.getUserId());	/** 사용자ID */
			cmnIntmMdlVO.setRvisnId(loginInfo.getUserId());
			cmnIntmMdlVO.setRprsYn("N"); //대표여부
			
			logger.info(generateLogMsg("Return Vo [cmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
			
			int returnCnt = cmnIntmMdlService.updateIntmColrMdl(cmnIntmMdlVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			logger.info(generateLogMsg("수정 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
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
