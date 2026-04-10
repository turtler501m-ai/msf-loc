package com.ktis.msp.rsk.payTargetMgmt.controller;

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
import com.ktis.msp.rsk.payTargetMgmt.service.PayTargetService;
import com.ktis.msp.rsk.payTargetMgmt.vo.PayTargetVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class PayTargetController extends BaseController {

	@Autowired
	private PayTargetService payTargetService;
	
	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	/**
	 * @Description : 납부대상조회 초기화면호출
	 * @Param  : 
	 * @Return : ModelAndView
	 * @Author : 박준성
	 * @Create Date : 2016.10.14
	 */
	@RequestMapping(value = "/rsk/payTargetMgmt/payTargetMgmt.do", method={POST, GET})
	public ModelAndView payTargetMgmtGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("payTargetVO") PayTargetVO payTargetVO, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("납부대상조회 초기화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(payTargetVO);
			
			// 본사 권한
			if(!"10".equals(payTargetVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			modelAndView.setViewName("rsk/payTargetMgmt/payTargetMgmt");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 납부대상 리스트 조회
	 * @Param  : payTargetVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2016.10.14
	 */
	@RequestMapping("/rsk/payTargetMgmt/payTargetMgmtList.json")
	public String payTargetMgmList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("payTargetVO") PayTargetVO payTargetVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){
	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("납부대상 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(payTargetVO);
			
			// 본사 권한
			if(!"10".equals(payTargetVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> payTargetList = payTargetService.payTargetMgmtList(payTargetVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, payTargetList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1030112", "납부대상조회"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	
	}
	/**
	 * @Description : 납부대상리스트 엑셀다운로드
	 * @Param  : payTargetVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2016.10.14
	 */
	@RequestMapping("/rsk/payTargetMgmt/payTargetMgmtEx.json")
	public String payTargetMgmEx(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("payTargetVO") PayTargetVO payTargetVO, ModelMap model ,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 납부대상리스트 엑셀다운로드 START"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(payTargetVO);
			
			// 본사 권한
			if(!"10".equals(payTargetVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// vo에 값들을 채운다.

			List<?> payTargetList = new ArrayList<PayTargetVO>();
			
			payTargetList = payTargetService.payTargetMgmtEx(payTargetVO, pRequestParamMap);
			

			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "납부대상_";//파일명
			String strSheetname = "납부대상 상세데이터";//시트명
			
			
			//엑셀 첫줄
			String [] strHead = {"개통일자","계약번호","대리점명","요금제","판매정책","개통유형","구매유형","상태값"};

			String [] strValue = {"lstComActvDate","contractNum","openAgntCd","rateNm","salePlcyNm","operType","reqBuyType","subStatus"};
		
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 6000, 8000, 8000, 8000, 4000, 5000, 4000};

			//숫자 컬럼
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0};

			
			String strFileName = "";
			if(payTargetList.size() <= 1000) {
				//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
				strFileName = fileDownService.excelDownProcStream(strFilename, strSheetname, payTargetList, strHead, intWidth, strValue, request, response, intLen);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, payTargetList, strHead, strValue, request, response);
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
