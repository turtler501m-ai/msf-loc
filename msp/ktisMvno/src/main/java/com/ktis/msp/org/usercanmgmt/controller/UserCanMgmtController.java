package com.ktis.msp.org.usercanmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.usercanmgmt.service.UserCanMgmtService;
import com.ktis.msp.org.usercanmgmt.vo.UserCanMgmtListVo;
import com.ktis.msp.org.usercanmgmt.vo.UserCanMgmtVo;

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
public class UserCanMgmtController extends BaseController {

	@Autowired
	private UserCanMgmtService userCanMgmtService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownService  fileDownService;

	@Autowired
	private MaskingService maskingService;

	@Autowired
	protected EgovPropertyService propertyService;

	public UserCanMgmtController() {
		setLogPrefix("[UserInfoMgmtController] ");
	}

	/**
	 * @Description : 사용자 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 22.
	 */
	@RequestMapping(value = "/org/usercanmgmt/userCanMgmt.do", method={POST, GET})

	public ModelAndView userCanMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserCanMgmtVo userCanMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지자 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userCanMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userCanMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			model.addAttribute("startDate",orgCommonService.getToMonth());
//    		model.addAttribute("endDate",orgCommonService.getLastDay());
			modelAndView.setViewName("org/usercanmgmt/msp_org_bs_1048");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 사용자 리스트 조회
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/usercanmgmt/userCanMgmtList.json")
	public String selectUserCanMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userCanMgmtVo") UserCanMgmtVo userCanMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지자 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userCanMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(userCanMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userCanMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = userCanMgmtService.selectUserCanMgmtList(userCanMgmtVo);

			maskingService.setMask(resultList, maskingService.getMaskFields(), pRequestParamMap);

			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {

			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("해지자 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}

		}

		return "jsonView";
	}

	/**
	 * @Description : 해지자 정보 엑셀다운로드기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2015. 8. 19.
	 */
	@RequestMapping("/org/usercanmgmt/userCanMgmtListEx.json")
	public String excelDownProc(UserCanMgmtVo userCanMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [userCanMgmtVo] = " + userCanMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(userCanMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userCanMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			userCanMgmtVo.setSearchStartDt(request.getParameter("searchStartDt"));
			userCanMgmtVo.setSearchEndDt(request.getParameter("searchEndDt"));
			
			List<?> userCanMgmtVos = new ArrayList<UserCanMgmtVo>();

			userCanMgmtVos = userCanMgmtService.selectUserCanMgmtListEx(userCanMgmtVo);

			maskingService.setMask(userCanMgmtVos, maskingService.getMaskFields(), pReqParamMap);

			String serverInfo = propertyService.getString("excelPath");

			String strFilename = serverInfo  + "일반해지자_";//파일명

			String strSheetname = "일반해지자데이터";//시트명

			//엑셀 첫줄
			String [] strHead = {"대상월", "메일전송여부", "LMS처리여부", "LMS처리결과", "사용자ID", "사용자명", "핸드폰번호", "E-mail", "가입일자", "최종로그인일자", "우편번호", "주소", "주소상세", "수정자", "수정일시"};

			String [] strValue = {"regstDttm", "resultYn", "sendYn", "sendRsn", "userid", "name", "mobileNo", "email", "firstLoginDt", "lastLoginDt", "zipcd", "address", "addressDtl", "rvisnNm", "rvisnDttm"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 10000, 5000, 5000, 5000};

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = orgCommonService.excelDownProc(strFilename, strSheetname, userCanMgmtVos, strHead, intWidth, strValue, request, response, intLen);

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

				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"CMN");                       //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", 0);                            //자료건수
				pReqParamMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
				pReqParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID

				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
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
	 * @Description : 처리 결과 Y
	 * @Param  : SalePlcyPrdtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/org/usercanmgmt/userCanMgmUpdateY.json")
	public String userCanMgmUpdateY(@RequestBody String data, ModelMap model, HttpServletRequest paramReq, HttpServletResponse paramRes, @RequestParam Map<String, Object> pRequestParamMap, UserCanMgmtVo userCanMgmtVo)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("처리결과 Y START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			/* -- 로그인 조직정보 및 권한 체크 -- */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(userCanMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userCanMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			UserCanMgmtListVo userCanMgmtListVos = om.readValue(data, UserCanMgmtListVo.class);
			
			int returnCnt = 0;
			
			for ( int i = 0 ; i < userCanMgmtListVos.getItems().size(); i++)
			{
				userCanMgmtListVos.setUserid(userCanMgmtListVos.getItems().get(i).getUserid());
				userCanMgmtListVos.setRvisnId(loginInfo.getUserId());
				userCanMgmtListVos.setResultYn("Y");
				
				returnCnt =  userCanMgmtService.updateResultYN(userCanMgmtListVos);
				logger.debug("returnCnt = " + returnCnt);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch ( Exception e)
		{
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.DUP", null, Locale.getDefault()) );
			resultMap.put("msg", "중복데이터가 존재합니다.");
		}
		
		model.addAttribute("result", resultMap);
		logger.debug("resultMap:" + resultMap);
		return "jsonView";

	}

	/**
	 * @Description : 처리 결과 Y
	 * @Param  : SalePlcyPrdtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/org/usercanmgmt/userCanMgmUpdateN.json")
	public String userCanMgmUpdateN(@RequestBody String data, ModelMap model, HttpServletRequest paramReq, HttpServletResponse paramRes, @RequestParam Map<String, Object> pRequestParamMap, UserCanMgmtVo userCanMgmtVo)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("처리결과 Y START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			/* -- 로그인 조직정보 및 권한 체크 -- */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(userCanMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userCanMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			UserCanMgmtListVo userCanMgmtListVos = om.readValue(data, UserCanMgmtListVo.class);
			
			int returnCnt = 0;

			for ( int i = 0 ; i < userCanMgmtListVos.getItems().size(); i++)
			{

				userCanMgmtListVos.setUserid(userCanMgmtListVos.getItems().get(i).getUserid());
				userCanMgmtListVos.setRvisnId(loginInfo.getUserId());
				userCanMgmtListVos.setResultYn("N");

				returnCnt =  userCanMgmtService.updateResultYN(userCanMgmtListVos);
				logger.debug("returnCnt = " + returnCnt);
			}
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch ( Exception e)
		{
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.DUP", null, Locale.getDefault()) );
			resultMap.put("msg", "중복데이터가 존재합니다.");
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		logger.debug("resultMap:" + resultMap);
		return "jsonView";

	}
}
