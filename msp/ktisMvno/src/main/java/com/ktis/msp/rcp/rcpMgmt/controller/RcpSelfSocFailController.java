package com.ktis.msp.rcp.rcpMgmt.controller;

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

import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.statsMgmt.service.StatsMgmtService;
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
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;
import com.ktis.msp.rcp.rcpMgmt.service.RcpSelfSocFailService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpSelfSocFailVO;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : UserInfoMgmtController
 * @Description : 사용자 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@Controller
public class RcpSelfSocFailController extends BaseController {

	@Autowired
	private RcpSelfSocFailService rcpSelfSocFailService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
		
	@Autowired
	private FileDownService  fileDownService;

	@Autowired
	private StatsMgmtService statsMgmtService;

	@Autowired
	private OrgCommonService orgCommonService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	public RcpSelfSocFailController() {
		setLogPrefix("[RcpSelfSocFailController] ");
	}

	
	/**
	 * @Description : 요금제 셀프변경 실패 고객 관리
	 * @Param  : void
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 16.
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/SelfSocFail.do", method={POST, GET})
	public ModelAndView SelfSocFail(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제변경 실패 고객관리 START"));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			
			modelAndView.setViewName("rcp/rcpMgmt/rcpSelfSocFailMgmt");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 요금제변경 실패고객 조회
	 * @Param  : rcpSelfSocFailVo
	 * @Return : ModelAndView
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 16.
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpSelfSocFailList.json")
	public String getRcpSelfSocFailList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rcpSelfSocFailVO") RcpSelfSocFailVO rcpSelfSocFailVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제 셀프변경 실패고객 조회 조회 START."));
		logger.info(generateLogMsg("Return Vo [rcpSelfSocFailVo] = " + rcpSelfSocFailVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rcpSelfSocFailVo);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(rcpSelfSocFailVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RcpSelfSocFailVO> resultList = rcpSelfSocFailService.getRcpSelfSocFailList(rcpSelfSocFailVo , pRequestParamMap);
			int totalCount = 0;
			if(resultList != null && resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}else{
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("사용자 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	/**
	 * @Description : 요금제변경 실패고객 처리상세 결과 조회
	 * @Param  : rcpSelfSocFailVo
	 * @Return : ModelAndView
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 17.
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpSelfSocFailDetail.json")
	public String getRcpSelfSocFailDetail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rcpSelfSocFailVO") RcpSelfSocFailVO rcpSelfSocFailVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제변경 실패고객 처리상세 결과 조회 START."));
		logger.info(generateLogMsg("Return Vo [rcpSelfSocFailVo] = " + rcpSelfSocFailVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rcpSelfSocFailVo);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(rcpSelfSocFailVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = rcpSelfSocFailService.getRcpSelfSocFailDetail(rcpSelfSocFailVo,pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("사용자 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 요금제셀프변경 실패고객 상담원 처리 프로세스
	 * @param VO
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/updateSelfSocFailProc.json")
		public String updateSelfSocFailProc(@ModelAttribute("searchVO") RcpSelfSocFailVO searchVO,
								@RequestBody String pJsonString,
								ModelMap model, 
								HttpServletRequest request, 
								HttpServletResponse response, 
								@RequestParam Map<String, Object> requestParamMap) {

		logger.debug("p_jsonString=" + pJsonString);
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제셀프변경 실패고객 상담원 처리 프로세스 START."));
		logger.info(generateLogMsg("Return Vo [RcpSelfSocFailVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(searchVO);
		
		try {
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
		
			searchVO.setProcId(searchVO.getSessionUserId());
			rcpSelfSocFailService.updateSelfSocFailProc(searchVO);	
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
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
	 * @Description : 요금제변경 실패고객 조회 엑셀다운로드
	 * @Param  : rcpSelfSocFailVo
	 * @Return : ModelAndView
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 17.
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpSelfSocFailListExcel.json")
	public String getRcpSelfSocFailListExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rcpSelfSocFailVO") RcpSelfSocFailVO rcpSelfSocFailVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제 셀프변경 실패고객 조회 엑셀다운로드 START."));
		logger.info(generateLogMsg("Return Vo [rcpSelfSocFailVo] = " + rcpSelfSocFailVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rcpSelfSocFailVo);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			// 본사 권한
			if(!"10".equals(rcpSelfSocFailVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			
			List<RcpSelfSocFailVO> resultList = rcpSelfSocFailService.getRcpSelfSocFailListExcel(rcpSelfSocFailVo , pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "요금제셀프변경현황_";//파일명
			String strSheetname = "요금제셀프변경현황";//시트명
			
			String [] strHead = {"요금제변경요청일", "구분", "변경전요금제" , "변경희망요금제", "계약번호", "고객명","성공여부", "실패사유", "처리결과", "처리일시","처리자ID" //10
											, "처리메모" ,"CTN" ,"성별","개통일자","나이","가입대리점","모집경로","최초유심접점","변경전요금제할인월정액","변경후요금제할인월정액"};
			String [] strValue = {"sysRdate","chgTypeNm","aSocNm", "tSocNm","contractNum","custNm","succYn", "prcsSbst", "procYn","procDate","procId"
											,"procMemo", "ctn","gender" , "lstComActvDate","age","openAgntNm","onOffType","fstUsimOrgNm","aSocAmnt","tSocAmnt" };
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 7000 , 7000, 5000, 5000, 5000, 12000, 5000, 5000, 5000, 10000 
									, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0
								   ,0 ,0, 0, 0, 0, 0, 0, 1, 1};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
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
			
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"RCP");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size());                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
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
	 * 요금제셀프변경 실패고객 재처리
	 * @param searchVO
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/retrySelfSocFail.json")
	public String retrySelfSocFail(@ModelAttribute("searchVO") RcpSelfSocFailVO searchVO,
										@RequestBody String pJsonString,
										ModelMap model,
										HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam Map<String, Object> requestParamMap) {

		logger.debug("p_jsonString=" + pJsonString);
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("요금제셀프변경 실패고객 상담원 처리 프로세스 START."));
		logger.info(generateLogMsg("Return Vo [RcpSelfSocFailVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(searchVO);

		try {
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//필수값 세팅
			searchVO.setUserId(loginInfo.getUserId());
			searchVO.setAccessIp(statsMgmtService.getClientIp());
			searchVO.setStrDate(orgCommonService.getToDay());

			//1. 재처리 검사
			rcpSelfSocFailService.retryPrmtAutoChk(searchVO);

			// 2. 부가서비스 가입 연동
			String resultCd = rcpSelfSocFailService.retrySocAutoAdd(searchVO);
			resultMap.put("resultCd" , resultCd);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		}catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP1010032", "요금제 셀프변경 현황")) {
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
