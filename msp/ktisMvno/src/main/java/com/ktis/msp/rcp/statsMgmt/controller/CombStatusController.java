package com.ktis.msp.rcp.statsMgmt.controller;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rcp.statsMgmt.service.CombStatusService;
import com.ktis.msp.rcp.statsMgmt.vo.CombStatusVo;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CombStatusController extends BaseController {

	protected Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;
	@Autowired
	private CombStatusService combStatusService;


	@Autowired
	private UserInfoMgmtService userInfoMgmtService;


	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private BtchMgmtService btchMgmtService;
	@Autowired
	private FileDownService fileDownService;


	/** 결합현황 페이지 진입 */
	@RequestMapping(value = "/stats/statsMgmt/statsCombStatus.do", method = {POST, GET})
	public String statsCombStatus(HttpServletRequest pRequest
	                               ,HttpServletResponse pResponse
																 ,@RequestParam Map<String, Object> pRequestParamMap
																 ,@ModelAttribute("searchVO") CombStatusVo searchVO
																 ,ModelMap model) {

		try {

			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());

			return "/rcp/statsMgmt/statsCombStatus";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/** 결합현황 리스트 조회 */
	@RequestMapping(value = "/stats/statsMgmt/selectCombStatusList.json")
	public String selectCombStatusList(@ModelAttribute("searchVO") CombStatusVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = combStatusService.selectCombStatusList(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010029", "결합현황")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	/** 결합현황 리스트 조회 */
	@RequestMapping(value = "/stats/statsMgmt/selectCombStatusListExcel.json")
	public String selectCombStatusListExcel(@ModelAttribute("searchVO") CombStatusVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("결합현황 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [CombStatusVo] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",pRequest.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","결합현황");
			String searchVal = "|조회기간:" + searchVO.getSrchStrtDt() + "~" + searchVO.getSrchEndDt()
					+ "|검색구분:" + searchVO.getSearchGbn()
					+ "|검색값:" + searchVO.getSearchName()
					+ "|대리점:" + searchVO.getOpenAgntCd()
					+ "|계약상태:"+ searchVO.getSubStatus()
					+ "|결합상태:" + searchVO.getCombSvcContSttusCd();
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();

			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00256");
			vo.setSessionUserId(loginInfo.getUserId());
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID

			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId()))
				vo.setExclDnldYn(vo.getExclDnldYn()+1);

			String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
			if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getHeader("REMOTE_ADDR");

			if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getRemoteAddr();

			vo.setExecParam("{\"srchStrtDt\":" + "\"" + searchVO.getSrchStrtDt() + "\""
					+ ",\"srchEndDt\":" + "\"" + searchVO.getSrchEndDt() + "\""
					+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\""
					+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\""
					+ ",\"openAgntCd\":" + "\"" + searchVO.getOpenAgntCd() + "\""
					+ ",\"subStatus\":" + "\"" + searchVO.getSubStatus() + "\""
					+ ",\"combSvcContSttusCd\":" + "\"" + searchVO.getCombSvcContSttusCd() + "\""
					+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
					+ ",\"dwnldRsn\":" + "\"" + pRequest.getParameter("DWNLD_RSN") + "\""
					+ ",\"ipAddr\":" + "\"" + ipAddr + "\""
					+ ",\"menuId\":" + "\"" + pRequest.getParameter("menuId") + "\""
					+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}"
			);
			btchMgmtService.insertBatchRequest(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");


		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010057", "평생할인 부가서비스 가입 검증")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

}
