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
import com.ktis.msp.rsk.statMgmt.service.SaleSttcMgmtService;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtAddVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtAgncyVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtChnlVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtPrdtVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtRateVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 수정일		수정자		수정내용
 * ---------------------------------------------
 * 2017-12-29	이상직		SRM17122947882 채널별영업실적 데이터유형 추가
 *
 */

@Controller
public class SaleSttcMgmtController extends BaseController {
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Resource(name="saleSttcMgmtService")
	private SaleSttcMgmtService sttcService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;
	
	
	/**
	 * 채널별영업실적
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getChnlSaleSttc.do")
	public ModelAndView getChnlSaleSttc(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO,
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
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_4001");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 채널별영업실적조회
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getChnlSaleSttcList.json")
	public String getChnlSaleSttcList(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtChnlVO> list = sttcService.getChnlSaleSttcList(searchVO);
			
			resultMap = makeResultMultiRowNotEgovMap(paramMap, list, list.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 채널별영업실적엑셀저장
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getChnlSaleSttcListExcel.json")
	public String getChnlSaleSttcListExcel(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtChnlVO> list = sttcService.getChnlSaleSttcList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "채널별영업실적_";//파일명
			String strSheetname = "채널별영업실적";//시트명
			
			String [] strHead = {"선후불", "상품구분", "채널유형", "유지건수", "(월)개통건수", "(월)취소건수", "(월)신규건수", "(월)해지건수", "(월)해지복구건수", "(월)순증건수", "(일)개통건수", "(일)취소건수", "(일)신규건수", "(일)해지건수", "(일)해지복구", "(일)순증건수"};
			String [] strValue = {"pppo", "prodNm", "chnlTpNm", "keepCnt", "mmOpenCnt", "mmCanCnt", "mmNewCnt", "mmTmntCnt", "mmTmntRclCnt", "mmNetCnt", "openCnt", "canCnt", "newCnt", "tmntCnt", "tmntRclCnt", "netCnt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
			
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
	
	
	/**
	 * 최초요금제별실적
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getFstRateSaleSttc.do")
	public ModelAndView getFstRateSaleSttc(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO,
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
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_4002");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 최초요금제별실적조회
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getFstRateSaleSttcList.json")
	public String getFstRateSaleSttcList(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtRateVO> list = sttcService.getFstRateSaleSttcList(searchVO);
			
			resultMap = makeResultMultiRowNotEgovMap(paramMap, list, list.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 최초요금제별실적엑셀저장
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getFstRateSaleSttcListExcel.json")
	public String getFstRateSaleSttcListExcel(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtRateVO> list = sttcService.getFstRateSaleSttcList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "최초요금제별영업실적_";//파일명
			String strSheetname = "최초요금제별영업실적";//시트명
			
			String [] strHead = {"선후불", "데이터유형", "요금제", "유지건수", "(월)개통건수", "(월)취소건수", "(월)신규건수", "(월)해지건수", "(월)순증건수", "(일)개통건수", "(일)취소건수", "(일)신규건수", "(일)해지건수", "(일)순증건수"};
			String [] strValue = {"pppo", "dataType", "rateNm", "keepCnt", "mmOpenCnt", "mmCanCnt", "mmNewCnt", "mmTmntCnt", "mmNetCnt", "openCnt", "canCnt", "newCnt", "tmntCnt", "netCnt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
			
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
	
	
	/**
	 * 현재요금제별실적
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getLstRateSaleSttc.do")
	public ModelAndView getLstRateSaleSttc(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO,
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
			
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_4003");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 현재요금제별실적조회
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getLstRateSaleSttcList.json")
	public String getLstRateSaleSttcList(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtRateVO> list = sttcService.getLstRateSaleSttcList(searchVO);
			
			resultMap = makeResultMultiRowNotEgovMap(paramMap, list, list.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 현재요금제별실적엑셀저장
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getLstRateSaleSttcListExcel.json")
	public String getLstRateSaleSttcListExcel(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtRateVO> list = sttcService.getLstRateSaleSttcList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "현재요금제별영업실적_";//파일명
			String strSheetname = "현재요금제별영업실적";//시트명
			
			String [] strHead = {"선후불", "데이터유형", "상품코드", "요금제", "유지건수", "(월)개통건수", "(월)취소건수", "(월)신규건수", "(월)해지건수", "(월)순증건수", "(일)개통건수", "(일)취소건수", "(일)신규건수", "(일)해지건수", "(일)순증건수"};
			String [] strValue = {"pppo", "dataType", "rateCd", "rateNm", "keepCnt", "mmOpenCnt", "mmCanCnt", "mmNewCnt", "mmTmntCnt", "mmNetCnt", "openCnt", "canCnt", "newCnt", "tmntCnt", "netCnt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
			
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
	
	
	/**
	 * 대리점별실적
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getAgncySaleSttc.do")
	public ModelAndView getAgncySaleSttc(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO,
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
			
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_4005");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 대리점별실적조회
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getAgncySaleSttcList.json")
	public String getAgncySaleSttcList(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtAgncyVO> list = sttcService.getAgncySaleSttcList(searchVO);
			
			resultMap = makeResultMultiRowNotEgovMap(paramMap, list, list.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 대리점별실적엑셀저장
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getAgncySaleSttcListExcel.json")
	public String getAgncySaleSttcListExcel(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtAgncyVO> list = sttcService.getAgncySaleSttcList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "대리점별영업실적_";//파일명
			String strSheetname = "대리점별영업실적";//시트명
			
			String [] strHead = {"대리점", "상품구분", "네트워크구분", "유지건수", "(월)개통건수", "(월)취소건수", "(월)신규건수", "(월)해지건수", "(월)순증건수", "(일)개통건수", "(일)취소건수", "(일)신규건수", "(일)해지건수", "(일)순증건수"};
			String [] strValue = {"agncyNm", "buyType", "dataType", "keepCnt", "mmOpenCnt", "mmCanCnt", "mmNewCnt", "mmTmntCnt", "mmNetCnt", "openCnt", "canCnt", "newCnt", "tmntCnt", "netCnt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
			
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
	
	/**
	 * 부가서비스가입자조회 View
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getAddSrvcJoinView.do")
	public ModelAndView getAddSrvcJoinView(@ModelAttribute("searchVO") SaleSttcMgmtAddVO searchVO,
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
			
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_4007");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 부가서비스가입자조회
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getAddProdList.json")
	public String getAddProdList(@ModelAttribute("searchVO") SaleSttcMgmtAddVO searchVO, 
										HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam Map<String, Object> paramMap,
										ModelMap model) {
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
			
			List<SaleSttcMgmtAddVO> rsltList = sttcService.getAddProdList(searchVO);
			
			int totalCount = 0;
			
			if(rsltList.size() > 0){
				totalCount = rsltList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(paramMap, rsltList, totalCount);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 부가서비스가입자조회 엑셀 자료생성
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getAddProdListExcel.json")
	public String getAddProdListExcel(@ModelAttribute("searchVO") SaleSttcMgmtAddVO searchVO, 
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> paramMap,
											ModelMap model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RSK");
			excelMap.put("MENU_NM","부가서비스가입자조회");
			String searchVal = "조회기간:"+searchVO.getStrtDt()+"~"+searchVO.getEndDt()+
					"|전체기간:"+searchVO.getAllDt()+
					"|검색구분:["+searchVO.getSearchGbn()+","+searchVO.getSearchName()+"]"
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();


			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00247");
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
					+ ",\"allDt\":" + "\"" + searchVO.getAllDt() + "\""
					+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\""
					+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\""
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
	
	/**
	 * 부가서비스가입자상세조회
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getAddProdHistList.json")
	public String getAddProdHistList(@ModelAttribute("searchVO") SaleSttcMgmtAddVO searchVO, 
										HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam Map<String, Object> paramMap,
										ModelMap model) {
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
			
			List<SaleSttcMgmtAddVO> rsltList = sttcService.getAddProdHistList(searchVO);
			
			resultMap = makeResultMultiRowNotEgovMap(paramMap, rsltList, rsltList.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 부가서비스실적통계 View
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getAddSrvcSttcView.do")
	public ModelAndView getAddSrvcSttcView(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO,
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
			
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_4006");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 부가서비스실적통계조회
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getAddProdSttcList.json")
	public String getAddProdSttcList(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
										HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam Map<String, Object> paramMap,
										ModelMap model) {
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
			
			List<SaleSttcMgmtRateVO> rsltList = sttcService.getAddProdSttcList(searchVO);
			
			resultMap = makeResultMultiRowNotEgovMap(paramMap, rsltList, rsltList.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 부가서비스실적통계 엑셀저장
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getAddProdSttcListExcel.json")
	public String getAddProdSttcListExcel(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SaleSttcMgmtRateVO> list = sttcService.getAddProdSttcList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "부가서비스실적통계_";	//파일명
			String strSheetname = "부가서비스실적통계";				//시트명
			
			String [] strHead = {"부가서비스코드","부가서비스명","기본료","유지건수","(월)신규가입","(월)해지","(월)순증","(일)신규가입","(일)해지","(일)순증"};
			String [] strValue = {"rateCd","rateNm","baseAmt","keepCnt","mmOpenCnt","mmTmntCnt","mmNetCnt","openCnt","tmntCnt","netCnt"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 8000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
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
				paramMap.put("DUTY_NM"   ,"RSK");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId"    ,request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT"  ,list.size());                    //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId());    //사용자ID
				
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
	 * 채널별영업실적
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsk/statMgmt/getChnlDataSttc.do")
	public ModelAndView getChnlDataSttc(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO,
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
			
			modelAndView.setViewName("rsk/statMgmt/msp_stat_bs_4008");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 채널별영업실적조회
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getChnlDataSttcList.json")
	public String getChnlDataSttcList(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtChnlVO> list = sttcService.getChnlDataSttcList(searchVO);
			
			resultMap = makeResultMultiRowNotEgovMap(paramMap, list, list.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 채널별영업실적엑셀저장
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rsk/statMgmt/getChnlDataSttcListExcel.json")
	public String getChnlDataSttcListExcel(@ModelAttribute("searchVO") SaleSttcMgmtVO searchVO, 
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
			
			List<SaleSttcMgmtChnlVO> list = sttcService.getChnlDataSttcList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "채널별영업실적_";//파일명
			String strSheetname = "채널별영업실적";//시트명
			
			String [] strHead = {"선후불", "상품구분", "채널유형", "데이터유형", "유지건수", "(월)개통건수", "(월)취소건수", "(월)신규건수", "(월)해지건수", "(월)해지복구건수", "(월)순증건수", "(일)개통건수", "(일)취소건수", "(일)신규건수", "(일)해지건수", "(일)해지복구", "(일)순증건수"};
			String [] strValue = {"pppo", "prodNm", "chnlTpNm", "dataType", "keepCnt", "mmOpenCnt", "mmCanCnt", "mmNewCnt", "mmTmntCnt", "mmTmntRclCnt", "mmNetCnt", "openCnt", "canCnt", "newCnt", "tmntCnt", "tmntRclCnt", "netCnt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
			
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
}