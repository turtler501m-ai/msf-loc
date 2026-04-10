package com.ktis.msp.rcp.rcpMgmt.controller;

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
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpCustPointService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpCustPointVO;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDirDlvryMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class RcpCustPointController extends BaseController {

	@Autowired
	private OrgCommonService orgCommonService;

	/** rcpCustPointService */
	@Autowired
	private RcpCustPointService rcpCustPointService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownService  fileDownService;

	/** Constructor */
	public RcpCustPointController() {
		setLogPrefix("[RcpCustPointController] ");
	}

	/**
	 * 고객포인트조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/rcpCustPoint.do")
	public ModelAndView rcpCustPoint( @ModelAttribute("searchVO") RcpCustPointVO searchVO,
			ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
			)
	{
		ModelAndView modelAndView = new ModelAndView();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			model.addAttribute("strtDt",orgCommonService.getWantDay(-7));
			model.addAttribute("endDt",orgCommonService.getToDay());

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpCustPoint");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}
	/**
	* @Description : 고객포인트 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/rcpMgmt/getRcpCustPointList.json")
	public String getRcpCustPointList(@ModelAttribute("searchVO") RcpCustPointVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객포인트 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = rcpCustPointService.getRcpCustPointList(pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
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
	* @Description : 고객포인트 목록 엑셀 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rcp/rcpMgmt/getRcpCustPointListByExcel.json")
	public String getRcpCustPointListByExcel(@ModelAttribute("searchVO") RcpCustPointVO searchVO, 
			HttpServletRequest request,	
			HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model) 
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<?> resultList = rcpCustPointService.getRcpCustPointListByExcel(pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "고객포인트목록_";//파일명
			String strSheetname = "고객포인트목록";//시트명
			
			String [] strHead = {
					"포인트내역일련번호", "포인트고객일련번호", "아이디", "주문번호", "계약번호", "누적적립포인트", "누적사용포인트", "잔여포인트", "소멸포인트", "포인트집계기준일자",
					"포인트명", "포인트내역", "포인트", "시점잔여포인트", "포인트사용가능시작일", "포인트사용가능종료일", "포인트내역사유", "포인트처리상세사유", "포인트처리일시", "포인트처리메모", 
					"생성일시"};
			
			String [] strValue = {
					"pointTxnSeq", "pointCustSeq", "userId", "requestKey", "svcCntrNo", "totAcuPoint", "totUsePoint", "totRemainPoint", "totExtinctionPoint", "pointSumBaseDate",
					"pointDivCd", "pointTrtCd", "point", "totRemainPointTxn", "pointUsePosblStDate", "pointUsePosblEndDate", "pointTxnRsnCd", "pointTxnDtlRsnDesc", "pointTrtDt", "pointTrtMemo", 
					"cretDt"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {
					6000, 6000 ,5000, 5000, 5000, 5000, 5000, 5000, 5000, 8000, 
					5000, 5000, 5000, 5000, 8000, 8000, 15000, 15000, 8000, 15000, 
					8000};
			int[] intLen = {
					0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 
					0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 
					0};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
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
				
				pRequestParamMap.put("FILE_NM"	,file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());				//파일경로
				pRequestParamMap.put("DUTY_NM"	,"RCP");						//업무명
				pRequestParamMap.put("IP_INFO"	,ipAddr);						//IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size());			//자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId());	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
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
