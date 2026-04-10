package com.ktis.msp.rcp.selfHpcMgmt.controller;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rcp.selfHpcMgmt.service.SelfHpcMgmtService;
import com.ktis.msp.rcp.selfHpcMgmt.vo.SelfHpcMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class SelfHpcMgmtController extends BaseController {
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private SelfHpcMgmtService selfHpcMgmtService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private FileDownService fileDownService;

	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;
	
	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	public SelfHpcMgmtController() {
		setLogPrefix("[SelfHpcMgmtController] ");
	}
	

	/**
	 * @Description : 010셀프개통대상 화면
	 * @Param :
	 * @Return : String
	 */
	@RequestMapping(value = "/rcp/selfHpcMgmt/selfHpcMgmt.do", method={POST, GET})
	public String selfHpcMgmtView(HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap){
		
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("maskingYn", maskingYn);
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			return "/rcp/selfHpcMgmt/selfHpcMgmt";
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 010셀프개통대상 목록 조회
	* @Param  : 
	* @Return : String
	*/
	@RequestMapping(value="/rcp/selfHpcMgmt/getSelfHpcList.json")
	public String getSelfHpcList(@ModelAttribute("selfHpcMgmtVO") SelfHpcMgmtVO selfHpcMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("010셀프개통대상 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(selfHpcMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<EgovMap> resultList = selfHpcMgmtService.getSelfHpcList(selfHpcMgmtVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 010셀프개통대상 엑셀 다운로드 batch
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/selfHpcMgmt/getSelfHpcListByExcel.json")
	public String getSelfHpcListByExcel(@ModelAttribute("searchVO") SelfHpcMgmtVO searchVO,
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
			excelMap.put("MENU_NM","010셀프개통대상");
			String searchVal = "가입신청시작월:"+searchVO.getSrchStrtDt() +
                    " | 가입신청종료월:" + searchVO.getSrchEndDt() +
                    " | 해피콜여부:" + searchVO.getSrchHpcStat() +
                    " | 해피콜결과:" + searchVO.getSrchHpcRst() +
					" | A'CEN 결과:" + searchVO.getSrchAcenRst() +
                    " | 검색구분:"+searchVO.getSearchGbn() +
	                " | 검색어:"+searchVO.getSearchName() ;
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			// 다운로드 이력 저장
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
			
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00199");
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
			
			vo.setExecParam("{\"srchStrtDt\":" + "\"" + searchVO.getSrchStrtDt() + "\""
					+ ",\"srchEndDt\":" + "\"" + searchVO.getSrchEndDt() + "\"" 
					+ ",\"srchHpcStat\":" + "\"" + searchVO.getSrchHpcStat() + "\"" 
					+ ",\"srchHpcRst\":" + "\"" + searchVO.getSrchHpcRst() + "\""
					+ ",\"srchAcenRst\":" + "\"" + searchVO.getSrchAcenRst() + "\""
					+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\"" 
					+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\""
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
	 * @Description : 010셀프개통대상 해피콜 결과 수정
	 * @Param  : 
	 * @Return : String
	 */
	@RequestMapping("/rcp/selfHpcMgmt/updateHpcRst.json")
	public String updateHpcRst(@ModelAttribute("selfHpcMgmtVO") SelfHpcMgmtVO selfHpcMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(selfHpcMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(selfHpcMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			int resultCnt = selfHpcMgmtService.updateHpcRst(selfHpcMgmtVO);
			
			if ( resultCnt == 0 ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "변경대상이 없습니다.");
			} else if ( resultCnt == -1 ) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "중복에러 입니다. 다시 시도해주세요.");
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
}
