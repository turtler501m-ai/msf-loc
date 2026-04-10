package com.ktis.msp.rsk.statMgmt.controller;
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
import org.springframework.ui.Model;
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
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rsk.statMgmt.service.StatMgmtService;
import com.ktis.msp.rsk.statMgmt.vo.StatMgmt3gSubVO;
import com.ktis.msp.rsk.statMgmt.vo.StatMgmtUagAmntVO;
import com.ktis.msp.rsk.statMgmt.vo.StatMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class StatMgmtController extends BaseController {
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Resource(name="statMgmtService")
	private StatMgmtService statMgmtService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	/**
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getBillItemMgmt.do")
	public ModelAndView getBillItemMgmt(@ModelAttribute("searchVO") StatMgmtVO searchVO,
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
			
			modelAndView.getModelMap().addAttribute("info", searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_1001");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	@RequestMapping(value="/rsk/statMgmt/getBillItemMgmtList.json")
	public String getBillItemMgmtList(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> list = statMgmtService.getBillItemMgmtList(searchVO);
			
			resultMap = makeResultMultiRow(searchVO, list);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value="/rsk/statMgmt/getBillItemMgmtListExcel.json")
	public String getBillItemMgmtListExcel(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
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
//			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = statMgmtService.getBillItemMgmtListExcel(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "청구항목관리_";//파일명
			String strSheetname = "청구항목관리";//시트명
			
			String [] strHead = {"청구항목코드", "청구항목명", "시작일자", "종료일자", "회계유형", "정산항목", "KT청구구분", "KT청구율", "등록자", "등록일시", "수정자", "수정일시"};
			String [] strValue = {"billItemCd", "itemCdNm", "applStrtDt", "applEndDt", "acntTpNm", "sttlItmCdNm", "ktInvCdNm", "ktInvRate", "regstNm", "regstDttm", "rvisnNm", "rvisnDttm"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 10000, 5000, 5000, 10000, 10000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
			
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
				paramMap.put("DUTY_NM"   ,"MSP");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
	
	@RequestMapping(value="/rsk/statMgmt/insertBillItmInfo.json")
	public String insertBillItmInfo(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			statMgmtService.insertBillItmInfo(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value="/rsk/statMgmt/updateBillItmInfo.json")
	public String updateBillItmInfo(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			statMgmtService.updateBillItmInfo(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
		
	/**
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getInvPymData.do")
	public ModelAndView getInvPymData(@ModelAttribute("searchVO") StatMgmtVO searchVO,
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
			
			modelAndView.getModelMap().addAttribute("info", searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_3001");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping(value="/rsk/statMgmt/getInvPymDataList.json")
	public String getInvPymDataList(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> list = statMgmtService.getInvPymDataList(searchVO);
			
			resultMap = makeResultMultiRow(searchVO, list);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	/**
	 * @Description : 청구수납자료 자료생성
	 * @Param  : StatMgmtVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 
	 */
	@RequestMapping("/rsk/statMgmt/setInvPymDataListDownload.json")
	public String getOpenMgmtListExcelDwonload(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
					Model model, 
					@RequestParam Map<String, Object> pRequestParamMap,
					HttpServletRequest request, 
					HttpServletResponse response)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구수납자료 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [StatMgmtVO] = " + searchVO.toString()));
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
			excelMap.put("DUTY_NM","MSP");
			excelMap.put("MENU_NM","청구수납자료");
			String searchVal = "기준월:"+searchVO.getTrgtYm()+
					"|조회기간:"+searchVO.getStrtYm()+"~"+searchVO.getEndYm()+
					"|선후불:"+searchVO.getPppo()+
					"|계약번호:"+searchVO.getContractNum()+
					"|미납여부:"+searchVO.getUnpdYn()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
						
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00035");
			vo.setSessionUserId(loginInfo.getUserId());		
			vo.setExclDnldId(exclDnldId);// 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"trgtYm\":" + "\"" + searchVO.getTrgtYm() + "\""
						+ ",\"strtYm\":" + "\"" + searchVO.getStrtYm() + "\"" 
						+ ",\"endYm\":" + "\"" + searchVO.getEndYm() + "\"" 
						+ ",\"pppo\":" + "\"" + searchVO.getPppo() + "\"" 
						+ ",\"contractNum\":" + "\"" + searchVO.getContractNum() + "\"" 
						+ ",\"unpdYn\":" + "\"" + searchVO.getUnpdYn() + "\"" 
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
	
	
	
	@RequestMapping(value="/rsk/statMgmt/setInvPymDataList.json")
	public String setInvPymDataList(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
//		String rtnMsg = "자료생성을 요청하였습니다";
		
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
			
			statMgmtService.setInvPymDataList(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "자료생성을 요청하였습니다.");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
//		return rtnMsg;
	}
	
	/** 사용량조회(대리점) view
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getUsgAmntStatOrgView.do")
	public ModelAndView getUsgAmntStatOrgView(@ModelAttribute("searchVO") StatMgmtVO searchVO,
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
			
			modelAndView.getModelMap().addAttribute("info", searchVO);
			modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_2002");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	/** 사용량조회(대리점)
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getUsgAmntStatOrgList.json")
	public String getUsgAmntStatOrgList(@ModelAttribute("searchVO") StatMgmtVO searchVO,
									HttpServletRequest request,
									HttpServletResponse response,
									@RequestParam Map<String, Object> paramMap,
									ModelMap model)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			List<StatMgmtUagAmntVO> resultList = statMgmtService.getUsgAmntStatOrgList(searchVO, paramMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			
			
		} catch(Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
			if(e.getMessage() == null || "".equals(e.getMessage())){
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		
		return "jsonView";
		
	}
	
	/** 사용량조회(대리점) 엑셀다운
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getUsgAmntStatOrgListExcel.json")
	public String getUsgAmntStatOrgListExcel(@ModelAttribute("searchVO") StatMgmtVO searchVO,
										HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam Map<String, Object> paramMap,
										ModelMap model) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용량조회(대리점) 엑셀 저장 START"));
		logger.info(generateLogMsg("Return Vo [StatMgmtVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		
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
			excelMap.put("DUTY_NM","RSK");
			excelMap.put("MENU_NM","사용량조회(대리점)");
			String searchVal = "|검색구분:["+searchVO.getSearchType()+","+searchVO.getSearchVal()+"]"+
					"|사용월:"+searchVO.getUsgYm()+
					"|개통월:"+searchVO.getOpenYm()+
					"|대리점:"+searchVO.getOrgnId()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
						
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00149");
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
			
			vo.setExecParam("{\"searchType\":" + "\"" + searchVO.getSearchType() + "\""
						+ ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\"" 
						+ ",\"usgYm\":" + "\"" + searchVO.getUsgYm() + "\"" 
						+ ",\"openYm\":" + "\"" + searchVO.getOpenYm() + "\"" 
						+ ",\"orgnId\":" + "\"" + searchVO.getOrgnId() + "\"" 
						+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" 
						+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\"" 
						+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" 
						+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\"" 
						+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}" 
						
					);
			
			btchMgmtService.insertBatchRequest(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
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
	 * 청구항목별자료
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getInvItemData.do")
	public ModelAndView getInvItemData(@ModelAttribute("searchVO") StatMgmtVO searchVO,
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
			
			modelAndView.getModelMap().addAttribute("info", searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_3002");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 청구항목별자료 조회
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/rsk/statMgmt/getInvItemDataList.json")
	public String getInvItemDataList(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구항목별자료 조회 START"));
		logger.info(generateLogMsg("Return Vo [StatMgmtVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> list = statMgmtService.getInvItemDataList(searchVO);
			resultMap = makeResultMultiRow(searchVO, list);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 청구항목별 엑셀다운로드
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getInvItemDataListExcel.json")
	public String getInvItemDataListExcel(@ModelAttribute("searchVO") StatMgmtVO searchVO, 
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀다운로드 조회 START"));
		logger.info(generateLogMsg("Return Vo [StatMgmtVO] = " + searchVO.toString()));
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
//			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<StatMgmtVO> list = statMgmtService.getInvItemDataListExcel(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "청구항목별자료_";//파일명
			String strSheetname = "청구항목별자료";//시트명
			
			String [] strHead = {"기준년월", "최근파일명" , "청구항목코드", "창구항목명", "즉납분 수납","즉납분 수납 VAT", "즉납분 수납취소","즉납분 수납취소 VAT", "즉납분 조정" , "즉납분 조정 VAT", "즉납분 조정취소","즉납분 조정취소 VAT", "즉납분 청구","즉납분 청구 VAT", "즉납분 환불","즉납분 환불 VAT", "즉납분 환불취소","즉납분 환불취소 VAT", 
											"정기청구 수납","정기청구 수납 VAT", "정기청구 수납취소","정기청구 수납취소 VAT", "정기청구 조정","정기청구 조정 VAT", "정기청구 조정취소","정기청구 조정취소 VAT", "정기청구 청구","정기청구 청구 VAT"
											,"차액정산금 즉납 수납","차액정산금 즉납 수납VAT","차액정산금 즉납 수납취소","차액정산금 즉납 수납취소VAT","차액정산금 즉납 환불","차액정산금 즉납 환불VAT" , "차액정산금 즉납 환불취소" , "차액정산금 즉납 환불취소VAT"
											,"차액정산금 정기청구 수납","차액정산금 정기청구 수납VAT","차액정산금 정기청구 수납취소","차액정산금 정기청구 수납취소VAT"
										  };
			String [] strValue = {"billYm", "newFileNm","billCd","itemNm","iPymnPym","iPymnPymVat","iPymnBck","iPymnBckVat","iPymnAdj","iPymnAdjVat","iPymnAdjr","iPymnAdjrVat","iPymnInv","iPymnInvVat","iPymnRfn","iPymnRfnVat","iPymnRfnr","iPymnRfnrVat",
											"bPymnPym","bPymnPymVat","bPymnBck","bPymnBckVat","bPymnAdj","bPymnAdjVat","bPymnAdjr","bPymnAdjrVat","bPymnInv","bPymnInvVat"
											,"iAdPym","iAdPymVat","iAdBck","iAdBckVat","iAdRfn","iAdRfnVat","iAdRfnr","iAdRfnrVat"
											,"bAdPym","bAdPymVat","bAdBck","bAdBckVat",
										   };
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000,5000, 5000, 5000,7000,5000,7000, 5000,7000, 5000,7000, 5000,7000, 5000,7000, 5000,7000, 6000,7000, 7000,7000, 5000,7000, 7000,7000, 6000,7000
									 ,7000,10000,7000,10000,7000,10000,7000,10000
									 ,7000,10000,7000,10000};
			int[] intLen = {0, 0, 0, 0,  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1,1,1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1 
								  ,1,1,1,1,1,1,1,1,1,1,1,1
								  };
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list.iterator(), strHead, intWidth, strValue, request, response, intLen);
			
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
				paramMap.put("DUTY_NM"   ,"MSP");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
	 * 3G 유지가입자 현황
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getDaily3gSub.do")
	public ModelAndView getDaily3gSub(@ModelAttribute("searchVO") StatMgmtVO searchVO,
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
			
			modelAndView.getModelMap().addAttribute("info", searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_5001");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 3G 유지가입자 현황 조회
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/rsk/statMgmt/getDaily3gSubList.json")
	public String getDaily3gSubList(@ModelAttribute("searchVO") StatMgmt3gSubVO searchVO, 
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("3G 유지가입자 현황 조회 START"));
		logger.info(generateLogMsg("Return Vo [StatMgmt3gSubVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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

			List<?> list = statMgmtService.getDaily3gSubList(searchVO);
			resultMap = makeResultMultiRow(searchVO, list);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}	

	/**
	 * @Description : 3G 유지가입자 현황 엑셀 다운로드 batch
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rsk/statMgmt/getDaily3gSubListByExcel.json")
	public String getDaily3gSubListByExcel(@ModelAttribute("searchVO") StatMgmt3gSubVO searchVO,
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
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","3G유지가입자현황");
			String searchVal = "기준일자:"+searchVO.getStdrDt() +
                    " | 계약상태:" + searchVO.getSubStatus() +
                    " | 검색구분:" + searchVO.getSearchCd() +
                    " | 검색어:" + searchVO.getSearchVal() +
                    " | 최종단말망:"+searchVO.getLstModelTp() +
                    " | 최종요금제망:"+searchVO.getLstRateTp() +
                    " | 최종유심망:"+searchVO.getLstUsimTp() +
                    " | VOLTE지원여부:"+searchVO.getVolteYn()
                    ;
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			// 다운로드 이력 저장
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
			
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00209");
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
			
			vo.setExecParam("{\"stdrDt\":" + "\"" + searchVO.getStdrDt() + "\""
					+ ",\"subStatus\":" + "\"" + searchVO.getSubStatus() + "\"" 
					+ ",\"searchCd\":" + "\"" + searchVO.getSearchCd() + "\"" 
					+ ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\"" 
					+ ",\"lstModelTp\":" + "\"" + searchVO.getLstModelTp() + "\"" 
					+ ",\"lstRateTp\":" + "\"" + searchVO.getLstRateTp() + "\""
					+ ",\"lstUsimTp\":" + "\"" + searchVO.getLstUsimTp() + "\""
					+ ",\"volteYn\":" + "\"" + searchVO.getVolteYn() + "\""
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
}