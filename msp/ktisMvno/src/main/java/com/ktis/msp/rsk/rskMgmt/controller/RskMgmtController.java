package com.ktis.msp.rsk.rskMgmt.controller;
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
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rsk.rskMgmt.service.RskMgmtService;
import com.ktis.msp.rsk.rskMgmt.vo.RskMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class RskMgmtController extends BaseController {
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Resource(name="rskMgmtService")
	private RskMgmtService rskMgmtService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
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
	@RequestMapping(value = "/rsk/rskMgmt/getNgCustMgmt.do")
	public ModelAndView getNgCustMgmt(@ModelAttribute("searchVO") RskMgmtVO searchVO,
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
			
			modelAndView.setViewName("rsk/rskMgmt/msp_rsk_bs_1001");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	@RequestMapping(value="/rsk/rskMgmt/getNgCustMgmtList.json")
	public String getNgCustMgmtList(@ModelAttribute("searchVO") RskMgmtVO searchVO, 
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
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = rskMgmtService.getNgCustMgmtList(searchVO, paramMap);
			
			resultMap = makeResultMultiRow(searchVO, list);
			
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value="/rsk/rskMgmt/getNgCustMgmtListExcel.json")
	public String getNgCustMgmtListExcel(@ModelAttribute("searchVO") RskMgmtVO searchVO, 
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			// ----------------------------------------------------------------
			// 2022-01-18 엑셀다운 배치등록
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","부실가입자조회");
			
			String searchVal = "조회기간:"+searchVO.getStrtDt()+"~"+searchVO.getEndDt()+
					"|조회조건:"+searchVO.getSearchType()+
					"|조직ID:"+searchVO.getOrgnId()+
					"|검색구분:["+searchVO.getSearchCd()+","+searchVO.getSearchVal()+"]"
					;
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
						
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00187");
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
			
			vo.setExecParam("{\"strtDt\":" + "\"" + searchVO.getStrtDt() + "\""
						+ ",\"endDt\":" + "\"" + searchVO.getEndDt() + "\""
						+ ",\"searchType\":" + "\"" + searchVO.getSearchType() + "\""
						+ ",\"orgnId\":" + "\"" + searchVO.getOrgnId() + "\""
						+ ",\"searchCd\":" + "\"" + searchVO.getSearchCd() + "\""
						+ ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\""
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
		
//		2022-01-18 배치등록 이전 소스 
//		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		
//		String returnMsg = null;
//		
//		try {
//			//----------------------------------------------------------------
//			// Login check
//			//----------------------------------------------------------------
//			LoginInfo loginInfo = new LoginInfo(request, response);
//			loginInfo.putSessionToVo(searchVO);
//			loginInfo.putSessionToParameterMap(paramMap);
//			
//			// 본사 화면인 경우
//			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
//				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
//			}
//			
//			List<?> list = rskMgmtService.getNgCustMgmtListExcel(searchVO, paramMap);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String strFilename = serverInfo  + "부실가입자_";//파일명
//			String strSheetname = "부실가입자";//시트명
//			
//			String [] strHead = {"계약번호","서비스구분","고객명","생년월일","성별","CTN","납부계정번호","계약상태","가입경로","가입유형","신청일자","개통일자","해지일자","해지사유","개통대리점","판매정책","할부기간","약정기간","완납여부","최초요금제","최종요금제","최초요금제변경일","최초단말","최초단말일련번호","현재단말","현재단말일련번호","최초기변일","최종기변일","최초정지일","최초명변일","가입기간","정지일수"};
//			String [] strValue = {"contractNum","serviceTypeNm","custNm","birthDt","gender","subscriberNo","ban","subStatusNm","onOffTypeNm","reqBuyTypeNm","reqDt","openDt","tmntDt","tmntRsnNm","agncyNm","plcySaleNm","instMnthCnt","agrmTrm","fullPayYn","fstRateNm","lstRateNm","chgRateDt","fstPrdtCode","fstIntmSrlNo","lstPrdtCode","lstIntmSrlNo","fstDvcChgDt","lstDvcChgDt","fstSusdt","fstMcnDt","usgDtCnt","stopDtCnt"};
//			//엑셀 컬럼 사이즈
//			int[] intWidth = {5000,5000,5000,5000,3000,5000,5000,3000,3000,5000,5000,5000,5000,8000,10000,10000,3000,3000,3000,5000,5000,5000,8000,5000,8000,5000,5000,5000,5000,5000,3000,3000};
//			int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1};
//			
//			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
//			// rqstMgmtService 함수 호출
//			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
//			
//			file = new File(strFileName);
//			
//			response.setContentType("applicaton/download");
//			response.setContentLength((int) file.length());
//			
//			in = new FileInputStream(file);
//			
//			out = response.getOutputStream();
//			
//			int temp = -1;
//			while((temp = in.read()) != -1){
//				out.write(temp);
//			}
//			
//			
//			//=======파일다운로드사유 로그 START==========================================================
//			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
//				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
//				
//				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
//				ipAddr = request.getHeader("REMOTE_ADDR");
//				
//				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
//				ipAddr = request.getRemoteAddr();
//				
//				paramMap.put("FILE_NM"   ,file.getName());              //파일명
//				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
//				paramMap.put("DUTY_NM"   ,"MSP");                       //업무명
//				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
//				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
//				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
//				paramMap.put("DATA_CNT", 0);                            //자료건수
//				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
//				
//				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
//			}
//			//=======파일다운로드사유 로그 END==========================================================
//			
//			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
//			resultMap.put("msg", "다운로드성공");
//			
//		} catch (Exception e) {
//			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
//			resultMap.put("msg", returnMsg);
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (Exception e) {
//					throw new MvnoErrorException(e);
//				}
//			}
//			if (out != null) {
//				try {
//					out.close();
//				} catch (Exception e) {
//					throw new MvnoErrorException(e);
//				}
//			}
//		}
//		file.delete();
		
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
	@RequestMapping(value = "/rsk/rskMgmt/getNgAccMgmt.do")
	public ModelAndView getNgAccMgmt(@ModelAttribute("searchVO") RskMgmtVO searchVO,
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
			
			modelAndView.setViewName("rsk/rskMgmt/msp_rsk_bs_1002");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	@RequestMapping(value="/rsk/rskMgmt/getNgAccMgmtList.json")
	public String getNgAccMgmtList(@ModelAttribute("searchVO") RskMgmtVO searchVO, 
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
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = rskMgmtService.getNgAccMgmtList(searchVO, paramMap);
			
			resultMap = makeResultMultiRow(searchVO, list);
			
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value="/rsk/rskMgmt/getNgAccMgmtListExcel.json")
	public String getNgAccMgmtListExcel(@ModelAttribute("searchVO") RskMgmtVO searchVO, 
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
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = rskMgmtService.getNgAccMgmtListExcel(searchVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "다중계좌정보_";//파일명
			String strSheetname = "다중계좌정보";//시트명
			
			String [] strHead = {"계약번호","고객명","CTN","대리점명","정책명","최초요금제","납부자명","납부자생년월일","최초단말기","사용자상태","법정대리인명","법정대리인관계"};
			String [] strValue = {"contractNum","subLinkName","ctn","agncyNm","plcySaleNm","fstRateNm","banLinkName","birthDt","fstModelNm","subStatusNm","minorAgentName","minorAgentRelation"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000,5000,5000,8000,10000,10000,3000,5000,5000,5000,5000,5000,5000,5000,8000,10000,10000,3000,3000,3000,5000,5000,5000,8000,5000,8000,5000,5000,5000,5000,5000,3000,3000,5000,5000};
			int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0};
			
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
}