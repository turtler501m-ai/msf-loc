package com.ktis.msp.ptnr.ptnrmgmt.controller;

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
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.ptnr.ptnrmgmt.service.PtnrPointMgmtService;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrPointMgmtVO;
import com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;
@Controller
public class PtnrPointMgmtController extends BaseController {
	
//	@Resource(name="ptnrpointService")
//	private PointService ptnrpointService;
	
	@Resource(name="ptnrPointMgmtService")
	private PtnrPointMgmtService ptnrPointMgmtService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	/**
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/ptnr/ptnrMgmt/ptnrpointmgmt.do")
	public ModelAndView formPtnrPoint(@ModelAttribute("searchVO") RateMgmtVO searchVO, 
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
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("ptnr/ptnrMgmt/ptnr_point_mgmt");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 제휴포인트관리 내역 조회
	 * @Param  : ptnrPointMgmtVO
	 * @Return : ModelAndView
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 07.
	 */
	@RequestMapping("/ptnr/ptnrMgmt/getPointMgmtList.json")
	public String getPointMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("ptnrPointMgmtVO") PtnrPointMgmtVO ptnrPointMgmtVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴포인트관리 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + ptnrPointMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(ptnrPointMgmtVO);
			
			// 본사 권한
			if(!"10".equals(ptnrPointMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrPointMgmtService.getPointMgmtList(ptnrPointMgmtVO, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("제휴포인트관리  리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 개인제휴포인트 내역 조회
	 * @Param  : ptnrPointMgmtVO
	 * @Return : ModelAndView
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 07.
	 */
	@RequestMapping("/ptnr/ptnrMgmt/getPtnrRateUsgHstInfo.json")
	public String getPtnrRateUsgHstInfo(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("ptnrPointMgmtVO") PtnrPointMgmtVO ptnrPointMgmtVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("개인제휴포인트 내역 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + ptnrPointMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(ptnrPointMgmtVO);
			
			// 본사 권한
			if(!"10".equals(ptnrPointMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrPointMgmtService.getPtnrRateUsgHstInfo(ptnrPointMgmtVO);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("개인제휴포인트 내역 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	@RequestMapping(value="/ptnr/ptnrMgmt/getPtnrPointListExcel.json")
	public String getPtnrPointListExcel(@ModelAttribute("searchVO") PtnrPointMgmtVO searchVO, 
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
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = ptnrPointMgmtService.getPtnrPointExcel(searchVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "제휴포인트_";//파일명
			String strSheetname = "제휴포인트관리";//시트명
			String strFileName = "";
			
			//제휴사가 동부일 경우
			if("dongbu".equals(searchVO.getPartnerId())){
				
				String [] strHead = {"가입계약번호", "서비스계약번호", "사용년월", "청구년월", "지급년월", "고객명", "계약상태", "연령(만)", "최초개통일", "가입일", "요금제", "사용일수","보험료계산","완납여부","지급대상","최종보험료정산","보험명"};
				String [] strValue = {"contractNum", "svcCntrNo", "usgYm", "billYm", "payYm", "custNm", "subStatus", "custAge", "openDt", "joinDt", "rateNm", "usgDtCnt", "reqPoint","fullPayYn","ptnrTrgt","calPoint","insrNm"};
				//엑셀 컬럼 사이즈
				int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000,5000};
				int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0};
				
				//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
				// rqstMgmtService 함수 호출
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
				
			}else{ //제휴사가 동부가 아닐경우
				
				String [] strHead = {"가입계약번호", "서비스계약번호", "ID", "고객명", "사용년월", "지급년월", "요금제", "지급유형", "사용일수", "요청포인트", "완납여부", "지급결과" , "지급포인트"};
				String [] strValue = {"contractNum", "svcCntrNo" ,"custId", "custNm" , "usgYm" , "payYm" ,"rateNm" , "pointType" , "usgDtCnt" ,"reqPoint" ,"fullPayYn" ,"payResult" ,"payPoint"};
				//엑셀 컬럼 사이즈
				int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000 , 5000};
				int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1};
				
				//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
				// rqstMgmtService 함수 호출
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
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
