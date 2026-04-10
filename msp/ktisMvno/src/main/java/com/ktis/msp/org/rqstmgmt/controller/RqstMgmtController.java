package com.ktis.msp.org.rqstmgmt.controller;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.ktis.msp.org.rqstmgmt.service.RqstMgmtService;
import com.ktis.msp.org.rqstmgmt.vo.RqstMgmtVo;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : RqstMgmtController
 * @Description : 청구 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@Controller
public class RqstMgmtController extends BaseController {

	@Autowired
	private RqstMgmtService rqstMgmtService;

	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownService  fileDownService;

	public RqstMgmtController() {
		setLogPrefix("[RqstMgmtController] ");
	}

	/**
	 * @Description : 청구 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/org/rqstmgmt/msp_org_bs_1045.do", method={POST, GET})
	public ModelAndView RqstMgmtGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rqstMgmtVo") RqstMgmtVo rqstMgmtVo, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("currentMonth",orgCommonService.getLastMonth());
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("org/rqstmgmt/msp_org_bs_1045");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 청구 리스트 조회
	 * @Param  :
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/rqstmgmt/rqstMgmtList.json")
	public String selectRqstMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rqstMgmtVo") RqstMgmtVo rqstMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구 조회 START."));
		logger.info(generateLogMsg("Return Vo [RqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = rqstMgmtService.selectRqstMgmtList(rqstMgmtVo);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * @Description : 판매점 보조금 내역 엑셀다운로드기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/rqstmgmt/excelDownProc1.json")
	@ResponseBody
	public String excelDownProc(@ModelAttribute("rqstMgmtVo") RqstMgmtVo rqstMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			rqstMgmtVo.setBillYm(request.getParameter("billYm"));
			rqstMgmtVo.setContractNum(request.getParameter("contractNum"));
			rqstMgmtVo.setBillAcntNo(request.getParameter("billAcntNo"));

			List<?> rqstMgmtVos = new ArrayList<RqstMgmtVo>();
			
			rqstMgmtVos = rqstMgmtService.selectRqstMgmtListEx(rqstMgmtVo);
			
			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "청구수납자료_";//파일명
			String strSheetname = "청구수납데이터";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"청구월", "판매회사코드", "서비스계약번호", "청구계정번호", "청구항목코드", "청구항목코드명", "청구반영구분명", "수납코드명", "수납대리점ID", "실수납일자",
					"적용일자", "수납방법코드", "수납방법명", "수납세부방법명", "납기일자", "청구수납금액", "부가세", "조정사유코드", "조정사유명"};

			String [] strValue = {"billYm", "slsCmpnCd", "svcCntrNo", "billAcntNo", "billItemCd", "itemCdNm", "billRflcIndNm", "pymnNm", "pymnAgncId", "blpymDate",
					"aplyDate", "pymnMthdCd", "pymnMthdNm", "pymnDtlMthdNm", "duedatDate", "pymnAmnt", "vatAmnt", "adjsRsnCd", "adjsRsnNm"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {3000, 5000, 5000, 5000, 4000, 5000, 5000, 5000, 5000, 5000,
					5000, 6000, 6000, 5000, 5000, 5000, 3000, 7000, 5000};

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 1, 1, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, rqstMgmtVos, strHead, intWidth, strValue, request, response, intInt);

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
				pReqParamMap.put("DUTY_NM"   ,"ORG");                       //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", 0);                            //자료건수
				pReqParamMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유

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
	 * 번호이동 내역 화면
	 * @Param  :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/org/rqstmgmt/mnpList.do")
	public ModelAndView mnpList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rqstMgmtVo") RqstMgmtVo rqstMgmtVo, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("번호이동 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("startDate",orgCommonService.getLastMonthDay());
			model.addAttribute("endDate",orgCommonService.getLastMonthMaxDay());
						
			rqstMgmtVo.setSearchEndDt(orgCommonService.getLastMonthDay());		/** 조회시작일자 */

			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			modelAndView.setViewName("org/rqstmgmt/msp_org_bs_1047");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}


	/**
	 * 번호이동 내역
	 * @Param  :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/org/rqstmgmt/getMnpList.json", method={POST, GET})
	public String getMnpList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rqstMgmtVo") RqstMgmtVo rqstMgmtVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("번호이동 내역 조회 START."));
		logger.info(generateLogMsg("Return Vo [RqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = rqstMgmtService.getMnpList(rqstMgmtVo);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 번호이동 조회 엑셀다운로드기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author :
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/rqstmgmt/excelDownProc3.json")
	@ResponseBody
	public String excelDownProc3(@ModelAttribute("rqstMgmtVo") RqstMgmtVo rqstMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pReqParamMap)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			rqstMgmtVo.setSearchStartDt(request.getParameter("searchStartDt"));
			rqstMgmtVo.setSearchEndDt(request.getParameter("searchEndDt"));
			rqstMgmtVo.setMnpTypeCd(request.getParameter("mnpTypeCd"));
			rqstMgmtVo.setInfoTypeCd(request.getParameter("infoTypeCd"));
			
			List<?> rqstMgmtVos = new ArrayList<RqstMgmtVo>();

			rqstMgmtVos = rqstMgmtService.getMnpListEx(rqstMgmtVo);

			String serverInfo = propertyService.getString("excelPath");

			String strFilename = serverInfo  + "번호이동_";//파일명
			String strSheetname = "번호이동";//시트명

			//엑셀 첫줄
			String [] strHead = {"번호이동일자","통신회사코드","번호이동구분","업무구분","정산월","정산건수","정산금액","신용카드수수료금액","단말기금액","지불보증지급일자"};

			String [] strValue = {"mnpDate","cmncCmpnCd","mnpType","infoType","sttsYm","mnpCnt","mnpAmnt","crdtCardCmsnAmnt","hndsetBillAmnt","payGarntDate"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 7000, 5000, 5000, 5000, 7000, 5000, 5000};

			int[] intLen = {0, 0, 0, 0, 0, 1, 1, 1, 1, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, rqstMgmtVos, strHead, intWidth, strValue, request, response, intLen);

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
				pReqParamMap.put("DUTY_NM"   ,"ORG");                       //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", 0);                            //자료건수
				pReqParamMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유

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
	 * @Description : 청구 상세 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/org/rqstmgmt/msp_org_bs_1049.do", method={POST, GET})
	public ModelAndView RqstMgmtDetailGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rqstMgmtVo") RqstMgmtVo rqstMgmtVo, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구 상세 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("currentMonth",orgCommonService.getLastMonth());
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			modelAndView.setViewName("org/rqstmgmt/msp_org_bs_1049");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 청구상세(TAX&VAT용) 리스트 조회
	 * @Param  : rqstMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 31.
	 */
	@RequestMapping("/org/rqstmgmt/rqstMgmtDetailList.json")
	public String selectRqstMgmtDetailList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rqstMgmtVo") RqstMgmtVo rqstMgmtVo, ModelMap model, @RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구상세(TAX&VAT용) 조회 START."));
		logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = rqstMgmtService.selectRqstMgmtDetailList(rqstMgmtVo, pReqParamMap);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			resultMap.clear();
			//logger.info(generateLogMsg(String.format("청구상세(TAX&VAT용) 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 청구상세(TAX&VAT용) 내역 엑셀다운로드기능
	 * @Param  : rqstMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 31.
	 */
	@RequestMapping("/org/rqstmgmt/rqstMgmtDetailListEx.json")
	public String selectRqstMgmtDetailListEx(RqstMgmtVo rqstMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rqstMgmtVo);
			
			// 본사 권한
			if(!"10".equals(rqstMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			rqstMgmtVo.setSearchStartDt(request.getParameter("searchStartDt"));
			rqstMgmtVo.setSearchEndDt(request.getParameter("searchEndDt"));
			rqstMgmtVo.setGubunCode(request.getParameter("gubunCode"));
			rqstMgmtVo.setEseroType(request.getParameter("eseroType"));
			
			List<?> rqstMgmtVos = new ArrayList<RqstMgmtVo>();
			
			rqstMgmtVos = rqstMgmtService.selectRqstMgmtDetailListEx(rqstMgmtVo, pReqParamMap);
			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "청구상세_";//파일명
			String strSheetname = "청구상세데이터";//시트명

			//엑셀 첫줄
			String [] strHead = {"작성일자","등록번호","청구계정","판매자코드","서비스구분","계산서종류","대표번호","공급가액","세액","사업자명","대표자명"};
			String [] strValue = {"pblsDate","bizrRgstNo","billAcntNo","soCd","gubunCode","eseroType","tlphNo","splyPricAmt","vatAmt","vndrNm","rprsPrsnNm"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			//엑셀 컬럼 속성 string:0, number:1
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0};
			//파일명,시트명,조회한 리스트Vo,헤드이름,헤드사이즈,값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, rqstMgmtVos, strHead, intWidth, strValue, request, response, intLen);
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
				pReqParamMap.put("DUTY_NM"   ,"ORG");                       //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", 0);                            //자료건수
				pReqParamMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유

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
