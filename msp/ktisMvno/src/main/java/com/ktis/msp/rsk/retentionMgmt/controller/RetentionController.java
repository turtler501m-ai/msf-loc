package com.ktis.msp.rsk.retentionMgmt.controller;

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
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rsk.retentionMgmt.service.RetentionService;
import com.ktis.msp.rsk.retentionMgmt.vo.RetentionVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class RetentionController extends BaseController {

	@Autowired
	private RetentionService retentionService;
	
	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	
	/**
	 * @Description : 해지 후 재가입 정보 초기 화면 호출
	 * @Param  : 
	 * @Return : ModelAndView
	 * @Author : 박준성
	 * @Create Date : 2016.08.31
	 */
	@RequestMapping(value = "/rsk/retentionMgmt/retentionMgmt.do", method={POST, GET})
	public ModelAndView canRejoinMgmtGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("retentionVO") RetentionVO retentionVO, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지 후 재가입 정보 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(retentionVO);
			
			// 본사 권한
			if(!"10".equals(retentionVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getWantDay(-1));
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			modelAndView.setViewName("rsk/retentionMgmt/retentionMgmt");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 해지 후 재가입 정보 리스트 조회
	 * @Param  : retentionVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2016.08.31
	 */
	@RequestMapping("/rsk/retentionMgmt/retentionMgmtList.json")
	public String retentionMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("retentionVO") RetentionVO retentionVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){
	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지 후 재가입 정보 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(retentionVO);
			
			// 본사 권한
			if(!"10".equals(retentionVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = retentionService.retentionMgmtList(retentionVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000322", "해지 후 재가입 정보"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	
	}
	/**
	 * @Description : 해지 후 재가입 정보 엑셀다운로드
	 * @Param  : retentionVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2016.09.02
	 */
	@RequestMapping("/rsk/retentionMgmt/retentionMgmtEx.json")
	public String retentionMgmtEx(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("retentionVO")RetentionVO retentionVO, ModelMap model ,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 해지 후 재가입 정보 엑셀다운로드 START"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(retentionVO);
			
			// 본사 권한
			if(!"10".equals(retentionVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// vo에 값들을 채운다.

			List<?> retentionList = new ArrayList<RetentionVO>();
			
			retentionList = retentionService.retentionMgmtEx(retentionVO, pRequestParamMap);
			

			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "재가입정보_";//파일명
			String strSheetname = "재가입정보 상세데이터";//시트명
			
			
			//엑셀 첫줄
			String [] strHead = {"계약번호","고객명","생년월일","구매유형","휴대폰번호","개통일자","개통대리점","모델명","일련번호", "상태" , "해지사유" ,  "계약번호","고객명","생년월일","구매유형","휴대폰번호","개통일자","개통대리점","모델명","일련번호","해지일자","해지사유","사용기간"};

			String [] strValue = {"contractNum","subLinkName","userSsn","reqByType","subscriberNo","lstComActvDate","openAgntCd","modelName","intmSrlNo", "subStatus", "subStatusRsnCode" ,  "cContractNum","cSubLinkName","cUserSsn","cReqByType","cSubscriberNo","cLstComActvDate","cOpenAgntCd","cModelName","cIntmSrlNo","cSubStatusDate","cSubStatusRsnCode","cUseDay"};
		
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 8000, 8000, 5000, 5000, 5000 , 8000 , 6500, 5000, 5000, 5000, 5000, 8000, 8000, 5000, 5000, 8000, 5000, 5000};

			//숫자 컬럼
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = "";
			if(retentionList.size() <= 1000) {
				strFileName = fileDownService.excelDownProcStream(strFilename, strSheetname, retentionList, strHead, intWidth, strValue, request, response, intLen);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, retentionList, strHead, strValue, request, response);
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

				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"ORG");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
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
