package com.ktis.msp.org.termssendmgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.termssendmgmt.service.TermsSendMgmtService;
import com.ktis.msp.org.termssendmgmt.vo.TermsSendVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class TermsSendMgmtController extends BaseController {

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private TermsSendMgmtService termsSendMgmtService;

	@Autowired
	private FileDownService  fileDownService;

	/**
	 * @Description : 약관 메일발송 대상자 관리 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 이춘수
	 * @Create Date : 2016.03.17
	 */
	@RequestMapping(value = "/org/termssendmgmt/termsSendMgmt.do")
	public ModelAndView userCanMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, TermsSendVO termsSendVO){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("약관발송대상자 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(termsSendVO);
			
			// 본사 권한
			if(!"10".equals(termsSendVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			model.addAttribute("startDate",orgCommonService.getToMonth());
//    		model.addAttribute("endDate",orgCommonService.getLastDay());
			modelAndView.setViewName("org/termsreconmgmt/msp_org_terms_1001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 약관발송 대상자 리스트 조회
	 * @param termsSendVO
	 * @param request
	 * @param response
	 * @param requestParam
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/org/termssendmgmt/getTermsSendTrgt.json")
	public String getTermsSendTrgt(TermsSendVO termsSendVO, HttpServletRequest request, HttpServletResponse response
									,@RequestParam Map<String, Object> pReqParamMap, ModelMap model) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("약관발송 대상자 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(termsSendVO);
			
			// 본사 권한
			if(!"10".equals(termsSendVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = termsSendMgmtService.getTermsSendTrgt(termsSendVO, pReqParamMap);
			
			resultMap = makeResultMultiRow(pReqParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
					throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 메일실행처리
	 * @param data
	 * @param model
	 * @param paramReq
	 * @param paramRes
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/org/termssendmgmt/updateResultY.json")
	public String updateResultY(@RequestBody String data, ModelMap model, HttpServletRequest paramReq, HttpServletResponse paramRes, @RequestParam Map<String, Object> pRequestParamMap, TermsSendVO termsSendVO)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("처리결과 Y START."));
		logger.info(generateLogMsg("================================================================="));

		/* -- 로그인 조직정보 및 권한 체크 -- */

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(termsSendVO);
			
			// 본사 권한
			if(!"10".equals(termsSendVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<TermsSendVO> list = getVoFromMultiJson(data, "items", TermsSendVO.class);
			
			int returnCnt = 0;
			
			for ( int i = 0 ; i < list.size(); i++)
			{
				list.get(i).setResultYn("Y");
				
				loginInfo.putSessionToVo(list.get(i));
				
				returnCnt = termsSendMgmtService.updateResultYN(list.get(i));
				
				logger.debug("returnCnt = " + returnCnt);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch ( Exception e)
		{
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 메일 미실행 처리
	 * @param data
	 * @param model
	 * @param paramReq
	 * @param paramRes
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/org/termssendmgmt/updateResultN.json")
	public String updateResultN(@RequestBody String data, ModelMap model, HttpServletRequest paramReq, HttpServletResponse paramRes, @RequestParam Map<String, Object> pRequestParamMap, TermsSendVO termsSendVO)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("처리결과 N START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			/* -- 로그인 조직정보 및 권한 체크 -- */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(termsSendVO);
			
			// 본사 권한
			if(!"10".equals(termsSendVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<TermsSendVO> list = getVoFromMultiJson(data, "items", TermsSendVO.class);
			
			int returnCnt = 0;
			
			for ( int i = 0 ; i < list.size(); i++)
			{
				list.get(i).setResultYn("N");
				
				loginInfo.putSessionToVo(list.get(i));
				
				returnCnt = termsSendMgmtService.updateResultYN(list.get(i));
				
				logger.debug("returnCnt = " + returnCnt);
			}
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch ( Exception e)
		{
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	 		resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 약관발송대상자 엑셀다운로드
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param pReqParamMap
	 * @return
	 */
	@RequestMapping(value = "/org/termssendmgmt/termsSendListEx.json")
	public String termsSendListEx(@ModelAttribute("searchVO") TermsSendVO searchVO, Model model
								, HttpServletRequest request, HttpServletResponse response
								, @RequestParam Map<String, Object> pReqParamMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [termsSendVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		String returnMsg = null;

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = termsSendMgmtService.termsSendListEx(searchVO, pReqParamMap);

			String serverInfo = propertyService.getString("excelPath");
			String strfilename = serverInfo + "약관발송대상자관리_";
			String strSheetname = "약관발송대상자관리";

			String[] strHead = {"대상일", "메일전송여부", "LMS처리여부", "LMS처리결과", "사용자구분", "계약번호", "사용자명", "사용자상태", "개통일", "해지일", "E-mail", "핸드폰번호", "수정자", "수정일자"};
			String[] strValue = {"trgtYm", "resultYn", "sendYn", "sendRsn", "typeCdNm", "contractNum", "name", "subStatNm", "openDt", "tmntDt",  "email", "mobileNo", "rvisnNm", "rvisnDttm"};

			int[] intWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000};
			int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strfilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
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


}
