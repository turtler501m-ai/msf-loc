package com.ktis.msp.cmn.payinfo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.payinfo.service.PayInfoService;
import com.ktis.msp.cmn.payinfo.vo.PayInfoDtlVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoMstVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoNonBindVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;

import CNPECMJava.CNPEncryptModule.crypto.FileHandle;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import imageResizer.imageResizer;

/**
 * @Class Name : PayInfoController
 * @Description : 대리점 신규고객 CMS 정보를 등록하기 위한 프로세스
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.07.11  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 7. 11.
 */

@Controller
public class PayInfoController extends BaseController {

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private FileUpload2Service  fileUp2Service;

	@Autowired
	private FileDownService  fileDownService;

	/** PayInfo 서비스 */
	@Autowired
	private PayInfoService payInfoService;

	/** 메뉴 권한 서비스 */
	@Autowired
    private MenuAuthService  menuAuthService;

	/** 공통 Utill 서비스 */
	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private BtchMgmtService btchMgmtService;

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;

	/** Constructor */
	public PayInfoController() {
		setLogPrefix("[PayInfoController] ");
	}

	//소스코드점검 수정 20200513
	//Delete 동기화 함수 생성
//	private synchronized void FileDelete(File file) {
//
//	}
	//20210722 소스코드점검 수정
	private void FileDelete(File file){
		synchronized(this){
			file.delete();
		}
	}

	/**
	 * @Description : 개통/변경관리 화면
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping(value = "/cmn/payinfo/searchPayInfo.do", method={POST, GET})
    public ModelAndView searchPayInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("대리점 CMS 동의 정보 등록한 결과 조회 화면 START."));
        logger.info(generateLogMsg("================================================================="));

        ModelAndView modelAndView = new ModelAndView();

        LoginInfo loginInfo = new LoginInfo(request, response);

        try {

    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());

            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            modelAndView.setViewName("cmn/payinfo/msp_cmn_bs_0001");
        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }
        return modelAndView;
    }

	/**
	 * @Description : CMS 동의 정보 등록한 결과 조회
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/orgpayinfo.json")
	public String orgPayInfoList(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("CMS 동의 정보 등록한 결과 조회 조회 START"));
		logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

	        LoginInfo loginInfo = new LoginInfo(request, response);
	        loginInfo.putSessionToParameterMap(pRequestParamMap);

	        searchVO.setSessionUserId(loginInfo.getUserId());

	        /* 본사조직이 아니면 강제 조직 세팅 */
	        if (!loginInfo.getUserOrgnTypeCd().equals("10"))
	        	searchVO.setOrgnId(loginInfo.getUserOrgnId());

			@SuppressWarnings("unchecked")
			List<EgovMap> resultList = ((List<EgovMap>) payInfoService.orgPayInfoList(searchVO, pRequestParamMap));

			resultMap =  makeResultMultiRow(searchVO, resultList);

			model.addAttribute("result", resultMap);
			logger.info(generateLogMsg("result:" + resultMap));
		} catch (Exception e) {
		    resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020210", "관리화면"))
			{
				throw new MvnoErrorException(e);
			}
		}

		return "jsonView";
	}

	/**
	 * @Description : CMS 동의 정보 등록한 결과 엑셀다운로드기능
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/orgPayInfoExcel.json")
	public String orgPayInfoExcel(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, Model model, @RequestParam Map<String, Object> pRequestParamMap){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("CMS 동의 정보 등록한 결과 엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [PayInfoVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);

			/* 본사조직이 아니면 강제 조직 세팅 */
	        if (!loginInfo.getUserOrgnTypeCd().equals("10"))
	        	searchVO.setOrgnId(loginInfo.getUserOrgnId());

	        @SuppressWarnings("unchecked")
	        List<EgovMap> resultList = ((List<EgovMap>) payInfoService.orgPayInfoListExcel(searchVO, pRequestParamMap));

			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "변경동의서관리_";//파일명
			String strSheetname = "변경동의서관리데이터";//시트명

			//엑셀 첫줄
			String [] strHead = {"등록구분","등록일자","계약번호","개통구분","상태","고객명","전화번호","생년월일","개통일자","납부방법","대리점명","주소","검증상태","검증일시","검증내용"};
			String [] strValue = {"regYnNm","rgstDt","contractNum","onOffTypeNm","subStatusNm","custName","telNo","userSsn","lstComActvDate","blBillingMethodNm","openAgntNm","addr","approvalCdNm","approvalDt","approvalMsg"};//15
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
			//엑셀 컬럼 속성 string:0, number:1
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			//파일명,시트명,조회한 리스트Vo,헤드이름,헤드사이즈,값
			String strFileName = "";
			if(resultList.size() <= 1000) {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, resultList, strHead, strValue, request, response);
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

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					returnMsg, "MSP1020200", "변경동의서관리"))
			{
				throw new MvnoErrorException(e);
			}
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
	 * @Description : 변경동의서관리 자료생성(대용량데이터 엑셀다운로드)
	 * @Param  : PayInfoVO
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2018. 03. 08.
	 */
	@RequestMapping("/cmn/payinfo/getPayInfoListExcelDownload.json")
	public String getPayInfoListExcelDownload(@ModelAttribute("searchVO") PayInfoVO searchVO,
					Model model,
					@RequestParam Map<String, Object> pRequestParamMap,
					HttpServletRequest request,
					HttpServletResponse response)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("변경동의서관리 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [PayInfoVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;

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
			excelMap.put("DUTY_NM","CMN");
			excelMap.put("MENU_NM","변경동의서관리");
			String searchVal = "개통일자:"+searchVO.getSearchStartDt()+"~"+searchVO.getSearchEndDt()+
					"|검색구분:["+searchVO.getSearchGbn()+","+searchVO.getSearchName()+"]"+
					"|대리점:"+searchVO.getOrgnId()+
					"|등록구분:"+searchVO.getRegYn();

			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}

			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();

			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			//vo.setBatchJobId("BATCH00132"); 개발배치 ID
			vo.setBatchJobId("BATCH00112");	//운영 배치ID
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

			vo.setExecParam("{\"searchStartDt\":" + "\"" + searchVO.getSearchStartDt() + "\""
						+ ",\"searchEndDt\":" + "\"" + searchVO.getSearchEndDt() + "\""
						+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\""
						+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\""
						+ ",\"regYn\":" + "\"" + searchVO.getRegYn() + "\""
						+ ",\"orgnId\":" + "\"" + searchVO.getOrgnId() + "\""
						+ ",\"dateGbn\":" + "\"" + searchVO.getDateGbn() + "\""
						+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
						+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\""
						+ ",\"ipAddr\":" + "\"" + ipAddr + "\""
						+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\""
						+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}"
					);

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
     * @Description : 개통 고객 찾기 팝업 화면
     * @Param  : void
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    @RequestMapping(value = "/msp_cmn_pu_0001.do", method={POST, GET})
    public String serchPopupCust(HttpServletRequest request, HttpServletResponse response, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("개통 고객 찾기 팝업 화면 START."));
        logger.info(generateLogMsg("================================================================="));

		model.addAttribute("startDate",orgCommonService.getFirstDay());
		model.addAttribute("endDate",orgCommonService.getToDay());

        return "cmn/payinfo/msp_cmn_pu_0001";
    }

	/**
	 * @Description : 개통 고객 찾기
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/custmgmtLists.json")
	public String custmgmtLists(PayInfoVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("개통고객찾기 조회 START"));
		logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			List<?> resultList = payInfoService.custmgmtLists(searchVO);

			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);
			logger.info(generateLogMsg("result:" + resultMap));
		} catch (Exception e) {
		    resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
            	throw new MvnoErrorException(e);
            }
		}
		return "jsonView";
	}

	/**
	 * @Description : 고객 찾기 팝업 화면
	 * @Param  : void
	 * @Return : String
	 * @Author : 김동혁
	 * @Create Date : 2024. 9. 21.
	 */
	@RequestMapping(value = "/msp_cmn_pu_0002.do", method={POST, GET})
	public String searchPopupCstmr(HttpServletRequest request, HttpServletResponse response, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객 찾기 팝업 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		return "cmn/payinfo/msp_cmn_pu_0002";
	}

	/**
	 * @Description : 고객 찾기
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 김동혁
	 * @Create Date : 2024. 09. 21.
	 */
	@RequestMapping("/cmn/payinfo/findCustomer.json")
	public String getCustomerInfo(PayInfoVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("개통고객찾기 조회 START"));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			List<?> resultList = payInfoService.getCustomerInfo(searchVO);

			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);
			logger.info(generateLogMsg("result:" + resultMap));
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}
		return "jsonView";
	}

	@RequestMapping("/cmn/payinfo/getRateComboList.json")
	public String getRateComboList(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			try {
				logger.info("=========================================================");
				logger.info("코드 조회 시작.");
				logger.info("=========================================================");
				LoginInfo loginInfo = new LoginInfo(request, response);
				loginInfo.putSessionToVo(searchVO);

				List<?> resultList = payInfoService.getBankCdComboList();

				resultMap =  makeResultMultiRow(searchVO, resultList);
			} catch(Exception e) {
				resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
				if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020220", "자동이체동의서관리상세"))
				{
					throw new MvnoErrorException(e);
				}
			}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	@RequestMapping("/cmn/payinfo/setPayinfoDataSyn.json")
	public String setPayinfoDataSyn(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			logger.info("=========================================================");
			logger.info("변경동의서관리 데이터와 현행화 시작.");
			logger.info("searchVO : " + searchVO);
			logger.info("=========================================================");
			LoginInfo loginInfo = new LoginInfo(request, response);
	        loginInfo.putSessionToParameterMap(pRequestParamMap);
	        searchVO.setSessionUserId(loginInfo.getUserId());

			List<PayInfoDtlVO> arrTmpDtl = payInfoService.getPayinfoDataSynDTL(searchVO.getKftcMstSeq());
			int iTotCnt = 0;
			for(int iDtl=0; iDtl<arrTmpDtl.size(); iDtl++) {
				PayInfoDtlVO payinfodtlvo = arrTmpDtl.get(iDtl);

				// DTL UPDATE
				payinfodtlvo.setRvisnId(searchVO.getSessionUserId());

				payinfodtlvo.setApprovalId(searchVO.getSessionUserId());
				payinfodtlvo.setApprovalMsg("변경동의서관리 데이터와 현행화");
				payinfodtlvo.setApprovalCd("0000");

				int iUpdateDtl = payInfoService.setPayinfoDataSynDTL(payinfodtlvo);
				iTotCnt = iTotCnt + iUpdateDtl;
				logger.info("UPDATE 데이터 : " + payinfodtlvo);
				logger.info("UPDATE 결과 : " + iUpdateDtl);
			}
			logger.info("총 " + iTotCnt + "건의 데이터 UPDATE");
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.PAYINFO_SYN_OK", new Object[] { iTotCnt }, Locale.getDefault()));
		} catch(Exception e) {

			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020210", "관리화면"))
			{
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	@RequestMapping("/cmn/payinfo/getFileUploadChk.json")
	public String getFileUploadChk(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			try {
				logger.info("=========================================================");
				logger.info("파일 업로드 체크 시작.");
				logger.info("=========================================================");
				LoginInfo loginInfo = new LoginInfo(request, response);
				loginInfo.putSessionToVo(searchVO);

//				List<?> resultList = payInfoService.getBankCdComboList();

				if(searchVO.getKftcDtlSeq() != null) {
					PayInfoDtlVO payinfoDtlVO = new PayInfoDtlVO();
		        	payinfoDtlVO.setKftcDtlSeq(Integer.parseInt(searchVO.getKftcDtlSeq()));

//		        	현행화 체크
		            int iDtlReadyChk = payInfoService.getDtlReadyChk(payinfoDtlVO);
		            if(iDtlReadyChk > 0) {
		         	   resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
		         	   resultMap.put("msg", "데이터 현행화 중이니 잠시후 등록하세요.");
		         	   model.addAttribute("result", resultMap);
		         	   return "jsonView";
		            }
				}


				HttpSession session = request.getSession();
				String realFilePath = "";
		    	String realFileNm = "";
		    	String ext = "";
		    	if (session.getAttribute("realFilePath") != null) {
		    		realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
		    	}
		    	if (session.getAttribute("realFileNm") != null) {
		    		realFileNm = (String) session.getAttribute("realFileNm"); //파일명
		    	}
		    	if (session.getAttribute("ext") != null) {
		    		ext = (String) session.getAttribute("ext"); //파일확장자
		    		if(ext != null) {
		    			ext = ext.toUpperCase();
		    		}
		    	}
		    	boolean bExtChk = false;
		    	String strEvidenceExtErrorMsg = "";
		    	List<String> listEvidenceFileExts = payInfoService.getEvidenceFileExtList(searchVO.getEvidenceTypeCd());
		    	for(int idx=0; idx<listEvidenceFileExts.size(); idx++) {
		    		String strTempExt = listEvidenceFileExts.get(idx);

		    		if("".equals(strEvidenceExtErrorMsg)) {
		    			strEvidenceExtErrorMsg = strTempExt;
		    		} else {
		    		   //v2018.11 PMD 적용 소스 수정
		    		   //strEvidenceExtErrorMsg = strEvidenceExtErrorMsg + ", " + strTempExt;
		    		   StringBuilder strBld = new StringBuilder(strEvidenceExtErrorMsg);
		    		   strBld.append(", ");
		    		   strBld.append(strTempExt);
		    		   strEvidenceExtErrorMsg = strBld.toString();
		    		}

		    		if(ext.equals(strTempExt.toUpperCase())) {
		    			bExtChk = true;
		    		}
		    	}
		    	//v2018.11 PMD 적용 소스 수정
		    	//strEvidenceExtErrorMsg = strEvidenceExtErrorMsg + " 확장만 허용합니다.";
		    	StringBuilder strBld = new StringBuilder(strEvidenceExtErrorMsg);
		    	strBld.append(" 확장만 허용합니다.");
		    	strEvidenceExtErrorMsg = strBld.toString();

		    	if(!bExtChk) {
		    		if("0000".equals(searchVO.getEvidenceTypeCd())) {
		    			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
						resultMap.put("msg", strEvidenceExtErrorMsg);
						model.addAttribute("result", resultMap);
			    		return "jsonView";
	    			} else if("0001".equals(searchVO.getEvidenceTypeCd())) {
	    				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
						resultMap.put("msg", strEvidenceExtErrorMsg);
						model.addAttribute("result", resultMap);
			    		return "jsonView";
	    			}
		    	}

		    	File file = new File(realFilePath + realFileNm);
		    	if(file.exists()) {
		    		long lFileSize = file.length();
			    	long lFileGSize = payInfoService.getEvidenceFileSize(searchVO.getEvidenceTypeCd());
		    		if("N".equals(payInfoService.getEvidenceFileSizeTargetChk(ext))) {
				    	if(lFileSize >= lFileGSize) {
				    		String strTempSizeMsg = payInfoService.getEvidenceFileSizeMsg(searchVO.getEvidenceTypeCd());
				    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
							resultMap.put("msg", strTempSizeMsg + "이하 용량의 파일만 허용합니다.");
							model.addAttribute("result", resultMap);
				    		return "jsonView";
				    	}
		    		} else {
						CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
		    			// 복호화
						String encFullPath = realFilePath + realFileNm;
						String decFullPath = realFilePath + realFileNm + ".DEC";
						//1.TO-BE 암호화 여부 확인
						if (rosis.crypt.module.eSonicCrypt.isEncryptLea(encFullPath)) {
							//1.1 TO-BE 암호화 모듈로 복호화
							int decInt = rosis.crypt.module.eSonicCrypt.RS_DecryptLea(encFullPath, decFullPath, 24, 0);
						} else {
							//2. AS-IS 암호화 모듈로 복호화
							fileHandle.Decrypt(encFullPath, decFullPath);
						}

						File del1File = new File(realFilePath + realFileNm);
						if(del1File.exists()) {
							//del1File.delete();
							FileDelete(del1File);
						}
						File cFile = new File(realFilePath + realFileNm + ".DEC");
						if(cFile.exists()) {
							cFile.renameTo(new File(realFilePath + realFileNm));
						}
						File dirFile = new File(realFilePath + realFileNm + ".DEC");
						if(dirFile.exists()) {
							//dirFile.delete();
							FileDelete(dirFile);
						}
			        	
		    			// 리사이징
		    			imageResizer imageresizer = new imageResizer();
		    			String strResizeMsg = imageresizer.imgResizer(lFileGSize
		    					, 1024
		    					, 1024
		    					, payInfoService.getEvidenceFileResizeExtList("Y")
		    					, 512
		    					, 512
		    					, realFilePath
		    					, realFileNm
		    					, realFilePath
		    					, realFileNm + ".RESIZE");
		    			logger.info("이미지 리사이징 결과 : " + strResizeMsg);
		    			Matcher mReq = Pattern.compile("true").matcher(strResizeMsg);
		    			// 암호화 작업
						if(mReq.find()) {
							logger.info("리사이징 이미지로 암호화");
							fileHandle.Encrypt(realFilePath + realFileNm + ".RESIZE", realFilePath + realFileNm + ".ENC");
						} else {
							logger.info("용량을 초과하지 않아서 원본으로 암호화");
							fileHandle.Encrypt(realFilePath + realFileNm, realFilePath + realFileNm + ".ENC");
						}
						File del2File = new File(realFilePath + realFileNm);
						if(del2File.exists()) {
							//del2File.delete();
							FileDelete(del2File);
						}

						File targetFile = new File(realFilePath + realFileNm + ".ENC");
						if(targetFile.exists()) {
							targetFile.renameTo(new File(realFilePath + realFileNm));
						}
						File dFile = new File(realFilePath + realFileNm + ".ENC");
						if(dFile.exists()) {
							//dFile.delete();
							FileDelete(dFile);
						}
						File rFile = new File(realFilePath + realFileNm + ".RESIZE");
						if(rFile.exists()) {
							//rFile.delete();
							FileDelete(rFile);
						}
						/**
						 * ********************************************************
						 */
//						String strTempFileMN = realFilePath + realFileNm + ".ENC";
//
//						fileHandle.Encrypt(realFilePath + realFileNm, strTempFileMN);
//						File eFile = new File(realFilePath + realFileNm);
//						if(eFile.exists()) {
//							eFile.delete();
//						}
//						File etFile = new File(realFilePath + realFileNm + ".ENC");
//						if(etFile.exists()) {
//							etFile.renameTo(new File(realFilePath + realFileNm));
//						}
		    		}
		    	} else {
		    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "존재하지 않는 데이터 입니다");
					model.addAttribute("result", resultMap);
		    		return "jsonView";
		    	}


				resultMap.put("fileTotalCnt", 1);
				logger.info("request.getSession().getAttribute : " + request.getSession().getAttribute("realFileNm"));
				if(request.getSession().getAttribute("realFileNm") != null && !"".equals(request.getSession().getAttribute("realFileNm"))) {
					resultMap.put("chkFileUpload", "Y");
				} else {
					resultMap.put("chkFileUpload", "N");
				}
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");

//				resultMap =  makeResultMultiRow(searchVO, resultList);
			} catch(Exception e) {

				resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
				if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
				{
	                throw new MvnoErrorException(e);
				}
			}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 *
	 * @Description : 선택한 고객 정보를 대상정보로 등록
	 * @Param  :
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/insertOffline.json")
	public String insertOffline(HttpServletRequest request, HttpServletResponse response, PayInfoVO payinfoVO, Model model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" insertOffline 등록 "));
		logger.info(generateLogMsg("================================================================="));
		logger.info(">>>> payinfoVO : " + payinfoVO);
		logger.info("===================================================================");

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		HttpSession session = request.getSession();

    	String realFilePath = "";
    	String realFileNm = "";
    	String ext = "";
    	String fileId = "";

    	if (session.getAttribute("realFilePath") != null) {
    		realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
    		payinfoVO.setRealFilePath(realFilePath);
    	}

    	if (session.getAttribute("realFileNm") != null) {
    		realFileNm = (String) session.getAttribute("realFileNm"); //파일명
    		payinfoVO.setRealFileNm(realFileNm);
    	}

    	if (session.getAttribute("ext") != null) {
    		ext = (String) session.getAttribute("ext"); //파일확장자
    		if(ext != null) {
    			ext = ext.toUpperCase();
    		}
    		payinfoVO.setExt(ext);
    	}

    	if (session.getAttribute("fileId") != null) {
    		fileId = (String) session.getAttribute("fileId"); //파일ID
    		payinfoVO.setFileId(fileId);
    	}

		logger.info("############################## ");
		logger.info("파일경로 === " + realFilePath);
		logger.info("파일명 === " + realFileNm);
		logger.info("확장자 === " + ext);
		logger.info("파일ID === " + fileId);

		if ( realFileNm != null)
		logger.info("realFileNm.length() ===" + realFileNm.length());

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToVo(payinfoVO);

		try {
			if (realFileNm != null && realFileNm.length() > 0){
				int returnCnt = payInfoService.insertOffline(payinfoVO);
				logger.debug("returnCnt" + returnCnt);

				boolean bImgBind = false;
				List<String> extList = new ArrayList<String>();
				extList = payInfoService.getImgBindTarget("CMN0212");
				for(int extI=0; extI<extList.size(); extI++) {
					String strTmpExt = extList.get(extI);
					if(strTmpExt.equals(ext.toUpperCase())) {
						HashMap<String, String> param = new HashMap<String, String>();
						param.put("param1", payinfoVO.getContractNum());
						param.put("param2", payinfoVO.getDpstBankCd());
						param.put("param3", payinfoVO.getDpstAcntNum());
						param.put("param4", ext.toUpperCase());
						param.put("param5", "payinfo_" + payinfoVO.getSessionUserId() + "_D1");
						payInfoService.mspPayinfoImg(param);
						bImgBind = true;
					}
				}
				if(!bImgBind) {
					PayInfoNonBindVO payinfononbindvo = new PayInfoNonBindVO();
					payinfononbindvo.setContractNum(payinfoVO.getContractNum());
					payinfononbindvo.setBan(payinfoVO.getBan());
					payinfononbindvo.setFileName(realFileNm);
					payinfononbindvo.setFilePath(realFilePath);
					payinfononbindvo.setExt(ext);
					payinfononbindvo.setDpstBankCd(payinfoVO.getDpstBankCd());
					payinfononbindvo.setDpstAcntNum(payinfoVO.getDpstAcntNum());
					payinfononbindvo.setDpstPrsnNm(payinfoVO.getDpstPrsnNm());
					payinfononbindvo.setRegstId(payinfoVO.getSessionUserId());
					payinfononbindvo.setRvisnId(payinfoVO.getSessionUserId());
					payInfoService.insertCmnKftcNonBindFileHst(payinfononbindvo);
				}
			} else {
				int returnCnt = payInfoService.updateOffline(payinfoVO);
				logger.debug("returnCnt" + returnCnt);
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
		    resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020200", "변경동의서관리"))
			{
                throw new MvnoErrorException(e);
			}
		} finally {
			//session 정보 초기화
			session.setAttribute("realFilePath", ""); //파일경로
	    	session.setAttribute("realFileNm", ""); //파일명
	    	session.setAttribute("ext", ""); //파일확장자
	    	session.setAttribute("fileId", ""); //파일아이디
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
    *
    * @Description : 자동이체동의서상세 - 선택한 고객 정보를 대상정보로 등록
    * @Param  :
    * @Return : String
    * @Author : 장익준
    * @Create Date : 2016. 7. 11.
    */
   @RequestMapping("/cmn/payinfo/updateDtl.json")
   public String updateDtl(HttpServletRequest request, HttpServletResponse response, PayInfoDtlVO payinfoDtlVO, Model model){

       logger.info(generateLogMsg("================================================================="));
       logger.info(generateLogMsg(" updateDtl 등록 "));
       logger.info(generateLogMsg("================================================================="));
       logger.info(">>>> payinfoDtlVO : " + payinfoDtlVO);
       logger.info("===================================================================");

       //--------------------------------------
       // return JSON 변수 선언
       //--------------------------------------
       Map<String, Object> resultMap = new HashMap<String, Object>();

       HttpSession session = request.getSession();

       String realFilePath = "";
       String realFileNm = "";
       String ext = "";
       String fileId = "";

       if (session.getAttribute("realFilePath") != null) {
           realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
           if(!"".equals(realFilePath)) {
        	   payinfoDtlVO.setRealFileDir(realFilePath);
           }
       }

       if (session.getAttribute("realFileNm") != null) {
           realFileNm = (String) session.getAttribute("realFileNm"); //파일명
           if(!"".equals(realFileNm)) {
        	   payinfoDtlVO.setRealFileName(realFileNm);
           }
       }

       if (session.getAttribute("ext") != null) {
    	   ext = (String) session.getAttribute("ext"); //파일확장자
    	   if(ext != null) {
    		   ext = ext.toUpperCase();
    	   }
       }

       logger.info("############################## ");
       logger.info("파일경로 === " + realFilePath);
       logger.info("파일명 === " + realFileNm);
       logger.info("확장자 === " + ext);
       logger.info("파일ID === " + fileId);

       if ( realFileNm != null)
       logger.info("realFileNm.length() ===" + realFileNm.length());

       //----------------------------------------------------------------
       //  Login check
       //----------------------------------------------------------------
       LoginInfo loginInfo = new LoginInfo(request, response);
       loginInfo.putSessionToVo(payinfoDtlVO);

       try {
    	   int returnCnt = payInfoService.updateDtl(payinfoDtlVO);
           logger.debug("returnCnt" + returnCnt);

           boolean bImgBind = false;
			List<String> extList = new ArrayList<String>();
			extList = payInfoService.getImgBindTarget("CMN0212");
			for(int extI=0; extI<extList.size(); extI++) {
				String strTmpExt = extList.get(extI);
				if(strTmpExt.equals(ext.toUpperCase())) {
					HashMap<String, String> param = new HashMap<String, String>();
					param.put("param1", payinfoDtlVO.getContractNum());
					param.put("param2", payinfoDtlVO.getDpstBankCd());
					param.put("param3", payinfoDtlVO.getDpstAcntNum());
					param.put("param4", ext.toUpperCase());
					param.put("param5", "payinfo_" + payinfoDtlVO.getSessionUserId() + "_D2");
					payInfoService.mspPayinfoImg(param);
					bImgBind = true;
				}
			}
			if(!bImgBind) {
				PayInfoNonBindVO payinfononbindvo = new PayInfoNonBindVO();
				payinfononbindvo.setContractNum(payinfoDtlVO.getContractNum());
				payinfononbindvo.setBan(payinfoDtlVO.getBillAcntNo());
				payinfononbindvo.setFileName(realFileNm);
				payinfononbindvo.setFilePath(realFilePath);
				payinfononbindvo.setExt(ext);
				payinfononbindvo.setDpstBankCd(payinfoDtlVO.getDpstBankCd());
				payinfononbindvo.setDpstAcntNum(payinfoDtlVO.getDpstAcntNum());
				payinfononbindvo.setDpstPrsnNm(payinfoDtlVO.getDpstPrsnNm());
				payinfononbindvo.setRegstId(payinfoDtlVO.getSessionUserId());
				payinfononbindvo.setRvisnId(payinfoDtlVO.getSessionUserId());
				payInfoService.insertCmnKftcNonBindFileHst(payinfononbindvo);
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
       } catch (Exception e) {
    	   resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
    	   if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020220", "자동이체동의서관리상세"))
    	   {
    		   throw new MvnoErrorException(e);
    	   }
       } finally {
           //session 정보 초기화
           session.setAttribute("realFilePath", ""); //파일경로
           session.setAttribute("realFileNm", ""); //파일명
           session.setAttribute("ext", ""); //파일확장자
       }

       //----------------------------------------------------------------
       // return json
       //----------------------------------------------------------------
       model.addAttribute("result", resultMap);

       return "jsonView";
   }

	/**
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2016. 07. 12.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/cmn/payinfo/fileUpLoad.do")
	public String fileUpUsingService(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		logger.info("===================================================================");
		logger.info("======  PayInfoController  : 오프라인 동의서관리 insert 컨트롤러 ======");
		logger.info("===================================================================");
		logger.info(">>>>model.toString() : " + model);
		logger.info(">>>>pRequest.toString() : " + pRequest);
		logger.info(">>>>pResponse.toString() : " + pResponse);
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap);
		logger.info("===================================================================");


		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInfoVO fileInfoVO = new FileInfoVO();

		HttpSession session = pRequest.getSession();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());

		String strUploadFileNm = "";      // 업로드한 파일 풀 경로
		String strSaveFileNm = "";        // 저장될 파일명(중복이 발생하면 (2)를 붙이는 작업까지 완료)
		String strExt = "";               // 확장자
		String strSaveFullPath = "";      // 저장될 파일 경로
		String strSaveDir = propertyService.getString("payInfoPath");
		Integer filesize = 0;

		try{
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
			if ( !new File(strSaveDir ).exists()) {
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { strSaveDir }  , Locale.getDefault()));
			}

			for (FileItem item : items) {
				//--------------------------------
		        // 파일 타입이 아니면 skip
		        //--------------------------------
				if (item.isFormField())
					continue;

				//--------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		        //--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) ) {
					continue;
				}
		    	/** 20230518 PMD 처리 */
		    	InputStream filecontent = null;
				File f = null;
				OutputStream fout = null;

				try {
					//----------------------------------------------------------------
			    	// 파일 이름관련 변수 선언 및 초기화
			    	//----------------------------------------------------------------
					strUploadFileNm = "";
					strSaveFileNm = "";
					strExt = "";
					strSaveFullPath = "";
					filesize = 0;

					//--------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			        //--------------------------------
					//오늘날짜구하기
					Calendar c = Calendar.getInstance();
					//v2018.11 PMD 적용 소스 수정
					//String ntime = new String();
					String ntime = "";
					String yyyy = String.valueOf(c.get(Calendar.YEAR));

					int mm = Integer.parseInt(String.valueOf(c.get(Calendar.MONTH)+1));
					int dd = Integer.parseInt(String.valueOf(c.get(Calendar.DATE)));
					ntime = yyyy + "/" + String.format("%02d", mm) + "/" + String.format("%02d", dd);
					//v2018.11 PMD 적용 소스 수정
					//strSaveDir = strSaveDir + ntime + "/";
					StringBuilder strBld = new StringBuilder(strSaveDir);
	            strBld.append(ntime);
	            strBld.append("/");
	            strSaveDir = strBld.toString();

					File lDir = new File(strSaveDir );
					if ( !lDir.exists()) {
						lDir.mkdirs();
					}
					fileInfoVO.setAttRout(strSaveDir);

					//----------------------------------------------------------------
			    	// upload 이름 구함
			    	//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file")) {
						//----------------------------------------------------------------
				    	// 파일 이름에 붙일 4자리 시퀀스 조회
				    	//----------------------------------------------------------------
						String fileNmSeq = payInfoService.getFileNmSeq();

						//----------------------------------------------------------------
				    	// 파일 이름 구함
				    	//----------------------------------------------------------------
						strUploadFileNm = FilenameUtils.getName(item.getName());

						int pos = strUploadFileNm.lastIndexOf(".");
						strExt = strUploadFileNm.substring( pos + 1); //확장자
						strSaveFileNm = "KIS" + fileNmSeq + "." + strExt; //파일명구하기 ex)KIS201607140001.jpg
						strSaveFileNm = fileUp2Service.getAlternativeFileName(strSaveDir, strSaveFileNm);
						strSaveFullPath = strSaveDir + strSaveFileNm;

						logger.info(generateLogMsg("strUploadFileNm : " + strUploadFileNm));
				    	logger.info(generateLogMsg("strSaveFileNm : " + strSaveFileNm));
				    	logger.info(generateLogMsg("strExt : " + strExt));
				    	logger.info(generateLogMsg("strSaveFullPath : " + strSaveFullPath));

				    	session.setAttribute("realFilePath", strSaveDir);  //파일경로
				    	session.setAttribute("realFileNm", strSaveFileNm); //파일명
				    	session.setAttribute("ext", strExt);               //파일확장자
						//----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------

						filecontent = item.getInputStream();
						f = new File(strSaveFullPath);
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}

						/**
						 * ********************************************************
						 * 파일 암호화 로직.
						 */
						CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
						String strTempFileMN = strSaveDir + strSaveFileNm + ".ENC";

						fileHandle.Encrypt(strSaveFullPath, strTempFileMN);
						File dirFile = new File(strSaveFullPath);
						if(dirFile.exists()) {
							//dirFile.delete();
							FileDelete(dirFile);
						}
						File targetFile = new File(strTempFileMN);
						if(targetFile.exists()) {
							targetFile.renameTo(dirFile);
						}
						/**
						 * ********************************************************
						 */
					}

				}catch (Exception e) {
					throw new MvnoErrorException(e);
				}finally{
			        if (fout != null) {
			        	try {
			        		fout.close();
			        	} catch (Exception e) {
			        		throw new MvnoErrorException(e);
			        	}
			        }
			        if (filecontent != null) {
			        	try {
			        		filecontent.close();
			        	} catch (Exception e) {
			        		throw new MvnoErrorException(e);
			        	}
			        }
				}

		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
    	} catch (Exception e) {
    		resultMap.put("state", false);
    		resultMap.put("name", strUploadFileNm.replace("'","\\'"));
    		resultMap.put("size",  111);

    		model.addAttribute("result", resultMap);

			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}
    	    return "jsonViewArray";
    	}

		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(strSaveFileNm);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("CRD"); //신용등급

		//파일 테이블에 등록
		//orgCommonService.insertFile(fileInfoVO);
		//payInfoService.insertOffline(pRequestParamMap);

		session.setAttribute("fileId", fileInfoVO.getFileId()); //파일ID

		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
	    return "jsonViewArray";
    }

	/**
	 * @Description : UUID 생성
	 * @Param  :
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2016. 4. 06.
	 */
	public static String generationUUID(){
		  return UUID.randomUUID().toString();
	}

	/**
	 * @Description : 첨부된 파일의 명을 찾아온다.
	 * @Param  : PayInfoVO
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2016. 7. 13.
	 */
	@RequestMapping("/cmn/payinfo/getFile.json")
	public String getFile1(@ModelAttribute("PayInfoVO") PayInfoVO payInfoVO, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("첨부 파일 명 찾기 START."+payInfoVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			List<?> resultList = payInfoService.getFile1(payInfoVO);

			EgovMap tempMap = (EgovMap)resultList.get(0);

			resultMap.put("realFilePath", tempMap.get("realFilePath"));
			resultMap.put("realFileNm", tempMap.get("realFileNm"));
			resultMap.put("kftcEvidenceSeq", tempMap.get("kftcEvidenceSeq"));
			resultMap.put("rgstInflowCd", tempMap.get("rgstInflowCd"));
			resultMap.put("dpstBankCd", tempMap.get("dpstBankCd"));
			resultMap.put("dpstAcntNum", tempMap.get("dpstAcntNum"));
			resultMap.put("dpstPrsnNm", tempMap.get("dpstPrsnNm"));

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.debug("result = " + resultMap);

		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
     * @Description : 자동이체동의서상세 첨부된 파일의 명을 찾아온다.
     * @Param  : PayInfoVO
     * @Return : String
     * @Author : FileMgmtController 에서 가져 옴.
     * @Create Date : 2016. 7. 13.
     */
    @RequestMapping("/cmn/payinfo/getFileDtl1.json")
    public String getFileDtl1(@ModelAttribute("PayInfoDtlVO") PayInfoDtlVO payInfoDtlVO, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("자동이체동의서상세 첨부 파일 명 찾기 START."+payInfoDtlVO.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            List<?> resultList = payInfoService.getFileDtl1(payInfoDtlVO);

            EgovMap tempMap = (EgovMap)resultList.get(0);

            //resultMap.put("realFileDir", tempMap.get("realFileDir"));
            resultMap.put("realFileName", tempMap.get("realFileName"));
            resultMap.put("kftcDtlSeq", tempMap.get("kftcDtlSeq"));
            resultMap.put("dpstBankCd", tempMap.get("dpstBankCd"));
            resultMap.put("dpstAcntNum", tempMap.get("dpstAcntNum"));
            resultMap.put("dpstPrsnNm", tempMap.get("dpstPrsnNm"));
//            resultMap.put("rgstInflowCd", tempMap.get("rgstInflowCd"));

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

            logger.debug("result = " + resultMap);

//            throw new Exception();
        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }


	/**
     * @Description : 파일 다운로드 기능
     * @Param  : PayInfoVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/cmn/payinfo/downFile.json")
    public String downFile( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
//        String returnMsg = null;

        try {
        	String strFileName = payInfoService.getFileNm(pReqParamMap.get("fileId").toString());

        	if(!"".equals(strFileName)) {
        		File chkFile = new File(strFileName);
        		if(chkFile.exists()) {
		        	/**
					 * ********************************************************
					 * 파일 복호화 로직.
					 */
		        	int S = 0;
					if(strFileName.startsWith("\\")) {
						S = strFileName.lastIndexOf("\\");
					} else {
						S = strFileName.lastIndexOf("/");
					}
					int M = strFileName.lastIndexOf(".");
					int E = strFileName.length();

					String filename = strFileName.substring(S+1, M);
					String extname = strFileName.substring(M+1, E);

					String strDecFileName = "";

		        	strDecFileName = strFileName + ".DEC";

		        	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
					//1.TO-BE 암호화 여부 확인
					if (rosis.crypt.module.eSonicCrypt.isEncryptLea(strFileName)) {
						//1.1 TO-BE 암호화 모듈로 복호화
						int decInt = rosis.crypt.module.eSonicCrypt.RS_DecryptLea(strFileName, strDecFileName, 24, 0);
					} else {
						//2. AS-IS 암호화 모듈로 복호화
						fileHandle.Decrypt(strFileName, strDecFileName);
					}

					if("".equals(strDecFileName)) {
						strDecFileName = strFileName;
					}
		        	/**
		        	 * ********************************************************
		        	 */
					logger.info(generateLogMsg("filename = " + filename));
					logger.info(generateLogMsg("extname = " + extname));

		            file = new File(strDecFileName);

		            response.setContentType("applicaton/download");
		            response.setContentLength((int) file.length());

		            String encodingFileName = "";

		            //v2018.11 PMD 적용 소스 수정
		            //int excelPathLen2 = Integer.parseInt(propertyService.getString("payInfoPathLen"));

		            try {
//		            	encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8").replace("+", "%20");
		            	encodingFileName = URLEncoder.encode(filename + "." + extname, "UTF-8").replace("+", "%20");
		            	logger.info("encodingFileName : " + encodingFileName);
		            } catch (UnsupportedEncodingException uee) {
		            	encodingFileName = strFileName;
		            }

		            response.setHeader("Cache-Control", "");
		            response.setHeader("Pragma", "");

		            response.setContentType("Content-type:application/octet-stream;");

		            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
		            response.setHeader("Content-Transfer-Encoding", "binary");

		            in = new FileInputStream(file);

		            out = response.getOutputStream();

		            int temp = -1;
		            while((temp = in.read()) != -1){
		            	out.write(temp);
		            }
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
		  	        /**
					 * ********************************************************
					 * JPG 파일 복호화 로직.
					 */
	  	        	File delFile = new File(strDecFileName);
	  	        	if(delFile.exists()) {
	  	        		//delFile.delete();
	  	        		FileDelete(delFile);
		  	        }
		  	        /**
		  	         * ********************************************************
		  	         */
		  	        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		  	        resultMap.put("msg", "다운로드성공");
        		} else {
        			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
    	  	        resultMap.put("msg", "다운받을 파일이 존재하지 않습니다.");
        		}
        	} else {
        		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	  	        resultMap.put("msg", "다운받을 파일 정보가 존재하지 않습니다.");
        	}
 		} catch (Exception e) {
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
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }

    /**
     * @Description : 자동이체동의서상세 - 파일 다운로드 기능
     * @Param  : PayInfoVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/cmn/payinfo/downFileDtl.json")
    public String downFileDtl( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("자동이체동의서상세 - 파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
//        String returnMsg = null;

        try {

            String strFileName = payInfoService.getFileNmDtl(pReqParamMap.get("fileId").toString());

            /**
			 * ********************************************************
			 * JPG 파일 복호화 로직.
			 */
        	int S = 0;
			if(strFileName.startsWith("\\")) {
				S = strFileName.lastIndexOf("\\");
			} else {
				S = strFileName.lastIndexOf("/");
			}
			int M = strFileName.lastIndexOf(".");
			int E = strFileName.length();

			String filename = strFileName.substring(S+1, M);
			String extname = strFileName.substring(M+1, E);
			String strDecFileName = "";

			StringBuffer sb = new StringBuffer();

			//v2018.11 PMD 적용 소스 수정
			//sb.append(strFileName + "." );
			sb.append(strFileName);
			sb.append(".");
			sb.append(extname);

        	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
			//1.TO-BE 암호화 여부 확인
			if (rosis.crypt.module.eSonicCrypt.isEncryptLea(strFileName)) {
				//1.1 TO-BE 암호화 모듈로 복호화
				int decInt = rosis.crypt.module.eSonicCrypt.RS_DecryptLea(strFileName, sb.toString(), 24, 0);
			} else {
				//2. AS-IS 암호화 모듈로 복호화
				fileHandle.Decrypt(strFileName, sb.toString());
			}

        	strDecFileName = sb.toString();

			if("".equals(strDecFileName)) {
				strDecFileName = strFileName;
			}
        	/**
        	 * ********************************************************
        	 */

			logger.info(generateLogMsg("filename = " + filename));

            file = new File(strDecFileName);

            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());

            String encodingFileName = "";

            int excelPathLen2 = Integer.parseInt(propertyService.getString("payInfoPathLen"));

            try {
              encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            response.setHeader("Cache-Control", "");
            response.setHeader("Pragma", "");

            response.setContentType("Content-type:application/octet-stream;");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);

            out = response.getOutputStream();

            int temp = -1;
            while((temp = in.read()) != -1){
                out.write(temp);
            }
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
    	    /**
  			 * ********************************************************
  			 * JPG 파일 복호화 로직.
  			 */
        	File delFile = new File(strDecFileName);
        	if(delFile.exists()) {
        		//delFile.delete();
        		FileDelete(delFile);
        	}

    	    /**
    	     * ********************************************************
    	     */
             resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
             resultMap.put("msg", "다운로드성공");

         } catch (Exception e) {
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
         //----------------------------------------------------------------
         // return json
         //----------------------------------------------------------------
         model.addAttribute("result", resultMap);
         return "jsonView";
    }

	/**
	 * @Description : 파일 삭제 기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2016. 4. 06.
	 */
	@RequestMapping("/cmn/payinfo/deleteFile.json")
	public String deleteFile( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 삭제 START."));
		logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pReqParamMap);

		HttpSession session = request.getSession();

//		File file = null;
		String returnMsg = null;

		try {

//			String strFileName = payInfoService.getFileNm(pReqParamMap.get("fileId").toString());
//
//			file = new File(strFileName);
//
//			file.delete();
//
			int delcnt = payInfoService.deleteFile(pReqParamMap.get("fileId").toString());
			logger.info(generateLogMsg("삭제건수 = " + delcnt));

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "파일변경시작");

 		} catch (Exception e) {
 			resultMap.put("fileTotCnt", "0");
			resultMap.put("deleteCnt", "0");

			//session 정보 초기화
            session.setAttribute("realFilePath", ""); //파일경로
            session.setAttribute("realFileNm", ""); //파일명
            session.setAttribute("ext", ""); //파일확장자
            session.setAttribute("fileId", ""); //파일아이디

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					returnMsg, "MSP1020200", "변경동의서관리"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
		}
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
	}

	/**
     * @Description : 자동이체동의서상세 - 파일 삭제 기능
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : FileMgmtController 에서 가져 옴.
     * @Create Date : 2016. 4. 06.
     */
    @RequestMapping("/cmn/payinfo/deleteFileDtl.json")
    public String deleteFileDtl( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("자동이체동의서상세 - 파일 삭제 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        File file = null;
        String returnMsg = null;

        try {
//          현행화 체크
        	PayInfoDtlVO payinfoDtlVO = new PayInfoDtlVO();
        	payinfoDtlVO.setKftcDtlSeq(Integer.parseInt(pReqParamMap.get("fileId").toString()));
            int iDtlReadyChk = payInfoService.getDtlReadyChk(payinfoDtlVO);
            if(iDtlReadyChk > 0) {
         	   resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
         	   resultMap.put("msg", "데이터 현행화 중이니 잠시후 변경하세요.");
         	   model.addAttribute("result", resultMap);
         	   return "jsonView";
            }

            String strFileName = payInfoService.getFileNmDtl(pReqParamMap.get("fileId").toString());

            file = new File(strFileName);

            file.delete();

            int delcnt = payInfoService.updateFileDtl(pReqParamMap.get("fileId").toString(), loginInfo.getUserId());
            logger.info(generateLogMsg("삭제건수 = " + delcnt));

             resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
             resultMap.put("msg", "파일삭제성공");

         } catch (Exception e) {
			resultMap.put("fileTotCnt", "0");
			resultMap.put("deleteCnt", "0");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					returnMsg, "MSP1020220", "자동이체동의서관리상세"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
        }
         //----------------------------------------------------------------
         // return json
         //----------------------------------------------------------------
         model.addAttribute("result", resultMap);
         return "jsonView";
    }

    /**
     * @Description : 파일 삭제 기능2 - 빨간엑박 눌렀을때 물리적인 파일만 지움
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : FileMgmtController 에서 가져 옴.
     * @Create Date : 2016. 4. 06.
     */
    @RequestMapping("/cmn/payinfo/deleteFile2.json")
    public String deleteFile2( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 삭제2 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        File file = null;
        String returnMsg = null;
        String realFilePath = null;
        String realFileNm = null;

        HttpSession session = request.getSession();

        try {

            if (session.getAttribute("realFilePath") != null) {
                realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
            }

            if (session.getAttribute("realFileNm") != null) {
                realFileNm = (String) session.getAttribute("realFileNm"); //파일명
            }

            file = new File(realFilePath + realFileNm );

            file.delete();

             resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
             resultMap.put("msg", "파일삭제성공");

         } catch (Exception e) {
        	 resultMap.put("fileTotCnt", "0");
        	 resultMap.put("deleteCnt", "0");

        	 if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
        			 messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
        			 returnMsg, "", ""))
        	 {
             //v2018.11 PMD 적용 소스 수정
        		 throw new MvnoErrorException(e);
        	 }
        } finally {
            //session 정보 초기화
            session.setAttribute("realFilePath", ""); //파일경로
            session.setAttribute("realFileNm", ""); //파일명
            session.setAttribute("ext", ""); //파일확장자
            session.setAttribute("fileId", ""); //파일아이디
        }
         //----------------------------------------------------------------
         // return json
         //----------------------------------------------------------------
         model.addAttribute("result", resultMap);
         return "jsonView";
    }

	/**
	 * @Description : 대리점 CMS 동의 정보 등록한 결과 조회
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping(value = "/cmn/payinfo/payInfoMst.do", method={POST, GET})
    public ModelAndView payInfoMst(HttpServletRequest request, HttpServletResponse response, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("CMS 마스터 화면 START."));
        logger.info(generateLogMsg("================================================================="));

        ModelAndView modelAndView = new ModelAndView();

        LoginInfo loginInfo = new LoginInfo(request, response);

        try {

    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());

            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            modelAndView.setViewName("cmn/payinfo/msp_cmn_bs_0003");
        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }
        return modelAndView;
    }

	/**
	 * @Description : CMS 동의 정보 등록한 결과 조회
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/payInfoMstList.json")
	public String payInfoMstList(HttpServletRequest request, HttpServletResponse response, PayInfoMstVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("CMS 동의 정보 등록한 결과 조회 조회 START"));
		logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pRequestParamMap);

        searchVO.setSessionUserId(loginInfo.getUserId());

		try {
			List<?> resultList = payInfoService.payInfoMstList(searchVO, pRequestParamMap);

			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);
			logger.info(generateLogMsg("result:" + resultMap));
		} catch (Exception e) {
		    resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020210", "관리화면"))
			{
				throw new MvnoErrorException(e);
			}

		}

		return "jsonView";
	}



	/**
     * @Description : 자동이체동의서 상세 자료
     * @Param  : PayInfoDtlVO
     * @Return : void
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    @RequestMapping(value = "/cmn/payinfo/payInfoDtl.do", method={POST, GET})
    public ModelAndView payInfoDtl(HttpServletRequest request, HttpServletResponse response, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("자동이체동의서 상세 자료 START."));
        logger.info(generateLogMsg("================================================================="));

        ModelAndView modelAndView = new ModelAndView();
        LoginInfo loginInfo = new LoginInfo(request, response);

        try {
            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            modelAndView.setViewName("cmn/payinfo/msp_cmn_bs_0004");
        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }
        return modelAndView;
    }

    /**
     * @Description : 자동이체동의서 상세 결과 조회
     * @Param  : PayInfoVO
     * @Return : void
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    @RequestMapping("/cmn/payinfo/payInfoDtlList.json")
    public String payInfoDtlList(HttpServletRequest request, HttpServletResponse response, PayInfoDtlVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("자동이체동의서 상세 결과 조회 START"));
        logger.info(generateLogMsg("searchVO == " + searchVO));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pRequestParamMap);

        searchVO.setSessionUserId(loginInfo.getUserId());

        try {
            List<?> resultList = payInfoService.payInfoDtlList(searchVO, pRequestParamMap);

            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

            model.addAttribute("result", resultMap);
            logger.info(generateLogMsg("result:" + resultMap));
        } catch (Exception e) {
            resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020220", "자동이체동의서관리상세"))
			{
				throw new MvnoErrorException(e);
			}

        }
        return "jsonView";
    }







	/**
	 *
	 * @Description : 선택한 고객 정보를 대상정보로 등록
	 * @Param  :
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/updateApprovalYn.json")
	public String updateApprovalYn(HttpServletRequest request, HttpServletResponse response, PayInfoMstVO searchVO, Model model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" insertOffline 등록 " ));
		logger.info(generateLogMsg("================================================================="));

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToVo(searchVO);

		try {

			int returnCnt = payInfoService.updateApprovalYn(searchVO);
			logger.debug("returnCnt" + returnCnt);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
		    resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리

		    throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : KT연동자료관리(대리점/고객센터용)
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 18.
	 */
	@RequestMapping(value = "/cmn/payinfo/searchKtPayinfo.do", method={POST, GET})
    public ModelAndView searchKtPayinfo(HttpServletRequest request, HttpServletResponse response, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("대리점 CMS 동의 정보 등록한 결과 조회 화면 START."));
        logger.info(generateLogMsg("================================================================="));

        ModelAndView modelAndView = new ModelAndView();

        LoginInfo loginInfo = new LoginInfo(request, response);

        try {
    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());

            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            modelAndView.setViewName("cmn/payinfo/msp_cmn_bs_0002");
        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }
        return modelAndView;
    }


	/**
	 * @Description : CMS 동의 정보 등록한 결과 조회
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/payInfoKtList.json")
	public String payInfoKtList(HttpServletRequest request, HttpServletResponse response, PayInfoDtlVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("KT 연동 자료 상세 조회(대리점/고객센터용) START"));
		logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

        	LoginInfo loginInfo = new LoginInfo(request, response);

        	loginInfo.putSessionToVo(searchVO);

        	/* 본사조직이 아니면 강제 조직 세팅 */
        	if (!loginInfo.getUserOrgnTypeCd().equals("10"))
        	{
        		searchVO.setOrgnId(loginInfo.getUserOrgnId());
        	}

			List<?> resultList = payInfoService.payInfoKtList(searchVO, pRequestParamMap);

			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);
			logger.info(generateLogMsg("result:" + resultMap));
		} catch (Exception e) {
		    resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020230", "KT연동동의서관리"))
			{
				throw new MvnoErrorException(e);
			}

		}
		return "jsonView";
	}






	/**
	 *
	 * @Description : 선택한 고객 정보를 대상정보로 등록
	 * @Param  :
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/updateCheYn.json")
	public String updateCheYn(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, Model model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" updateCheYn 등록 " ));
		logger.info(generateLogMsg("================================================================="));

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToVo(searchVO);

		try {

			//검증상태가 부적격이면 사유는 필수
			if("0001".equals(searchVO.getApprovalCd()) && "".equals(searchVO.getApprovalMsg())){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	         	resultMap.put("msg", "검증상태가 부적격이면 사유는 필수 값입니다.");
	         	model.addAttribute("result", resultMap);
	         	return "jsonView";
			}


			int returnCnt = payInfoService.updateCheYn(searchVO);
			logger.debug("returnCnt" + returnCnt);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
		    resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020200", "변경동의서관리"))
			{
                throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 *
	 * @Description : 선택한 고객 정보를 대상정보로 등록
	 * @Param  :
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/updateAppYn.json")
	public String updateAppYn(HttpServletRequest request, HttpServletResponse response, PayInfoDtlVO searchVO, Model model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" updateAppYn 등록 " ));
		logger.info(generateLogMsg("================================================================="));

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToVo(searchVO);

		try {

			int returnCnt = payInfoService.updateAppYn(searchVO);
			logger.debug("returnCnt" + returnCnt);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
		    resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020230", "KT연동동의서관리"))
			{
                throw new MvnoErrorException(e);
			}

		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 파일 이력 확인 화면
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping(value = "/cmn/payinfo/searchFileInfo.do", method={POST, GET})
    public ModelAndView searchFileInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 이력 확인 화면 START."));
        logger.info(generateLogMsg("================================================================="));

        ModelAndView modelAndView = new ModelAndView();

        LoginInfo loginInfo = new LoginInfo(request, response);

        try {
            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            modelAndView.setViewName("cmn/payinfo/msp_cmn_bs_0005");
        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }
        return modelAndView;
    }

	/**
	 * @Description : 파일 이력 확인 화면
	 * @Param  : PayInfoVO
	 * @Return : void
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/cmn/payinfo/fileList.json")
	public String fileList(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("CMS 동의 정보 등록한 결과 조회 조회 START"));
		logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

        	LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

        	loginInfo.putSessionToVo(searchVO);

            searchVO.setSessionUserId(loginInfo.getUserId());

        	/* 본사조직이 아니면 강제 조직 세팅 */
        	if (!loginInfo.getUserOrgnTypeCd().equals("10"))
        	{
        		searchVO.setOrgnId(loginInfo.getUserOrgnId());
        	}

			@SuppressWarnings("unchecked")
			List<EgovMap> resultList = ((List<EgovMap>) payInfoService.fileList(searchVO, pRequestParamMap));

			resultMap =  makeResultMultiRow(searchVO, resultList);

			model.addAttribute("result", resultMap);
			logger.info(generateLogMsg("result:" + resultMap));
		} catch (Exception e) {
		    resultMap.clear();

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020240", "동의서파일이력조회"))
			{
				throw new MvnoErrorException(e);
			}

		}

		return "jsonView";
	}




	/**
     * @Description : 파일 다운로드 기능
     * @Param  : PayInfoVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/cmn/payinfo/downFileSec.json")
    public String downFileSec( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;

        try {

        	String strFileName = payInfoService.getFileNmSec(pReqParamMap.get("fileId").toString());

        	/**
			 * ********************************************************
			 * JPG 파일 복호화 로직.
			 */
        	//v2018.11 PMD 적용 소스 수정
        	/*int S = 0;
			if(strFileName.startsWith("\\")) {
				S = strFileName.lastIndexOf("\\");
			} else {
				S = strFileName.lastIndexOf("/");
			}*/
			int M = strFileName.lastIndexOf(".");
			int E = strFileName.length();

			String extname = strFileName.substring(M+1, E);
			String strDecFileName = "";
			StringBuffer sb = new StringBuffer();

			//v2018.11 PMD 적용 소스 수정
			//sb.append(strFileName + "." );
			sb.append(strFileName);
			sb.append(".");
			sb.append(extname);

        	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
			//1.TO-BE 암호화 여부 확인
			if (rosis.crypt.module.eSonicCrypt.isEncryptLea(strFileName)) {
				//1.1 TO-BE 암호화 모듈로 복호화
				int decInt = rosis.crypt.module.eSonicCrypt.RS_DecryptLea(strFileName, sb.toString(), 24, 0);
			} else {
				//2. AS-IS 암호화 모듈로 복호화
				fileHandle.Decrypt(strFileName, sb.toString());
			}

        	strDecFileName = sb.toString();

        	if("".equals(sb.toString())) {
				strDecFileName = strFileName;
			}

            file = new File(strDecFileName);

            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());

            String encodingFileName = "";

    		int excelPathLen2 = Integer.parseInt(propertyService.getString("payInfoPathLen"));

            try {
              encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            response.setHeader("Cache-Control", "");
            response.setHeader("Pragma", "");

            response.setContentType("Content-type:application/octet-stream;");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);

            out = response.getOutputStream();

            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }
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
  	        /**
			 * ********************************************************
			 * JPG 파일 복호화 로직.
			 */
  	        	File delFile = new File(strDecFileName);
  	        	if(delFile.exists()) {
  	        		//delFile.delete();
  	        		FileDelete(delFile);
  	        }
  	        /**
  	         * ********************************************************
  	         */
  	        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
  	        resultMap.put("msg", "다운로드성공");
 		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					returnMsg, "MSP1020240", "동의서파일이력조회"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
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
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }



	/**
	 * @Description : 관리화면 엑셀 다운로드
	 * @Param  : PayInfoMstVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2016. 9. 01.
	 */
	@RequestMapping("/cmn/payinfo/selectPayinfoMstEx.json")
	public String selectPayinfoMstEx(HttpServletRequest request, HttpServletResponse response, PayInfoMstVO payInfoMstVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 관리화면 엑셀 다운로드 START"));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(payInfoMstVO);

			// 본사 권한
			if(!"10".equals(payInfoMstVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// vo에 값들을 채운다.

			List<?> payInfoVOs = new ArrayList<PayInfoMstVO>();

			payInfoVOs = payInfoService.selectPayinfoMstEx(payInfoMstVO);


			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "자동이체동의서_";//파일명
			String strSheetname = "관리화면";//시트명


			//엑셀 첫줄
			String [] strHead = {"순번","등록일자","종료일자","연동파일명","상태","수신건수","송신건수","승인여부","승인자","승인일시","사유"};

			String [] strValue = {"kftcMstSeq","startDtStr","endDt","kftcFileNm","stateNm","receiveCnt","sendCnt","approvalNm","approvalIdNm","approvalDt","approvalMsg"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {3000, 5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000, 5000, 15000};

			//숫자 컬럼
			int[] intInt = {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = "";
			if(payInfoVOs.size() <= 1000) {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, payInfoVOs, strHead, intWidth, strValue, request, response, intInt);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, payInfoVOs, strHead, strValue, request, response);
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
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					 messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					 returnMsg, "MSP1020210", "관리화면"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
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
	 * @Description : 관리화면 상세 엑셀 다운로드
	 * @Param  : PayInfoDtlVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2016. 9. 01.
	 */
	@RequestMapping("/cmn/payinfo/selectPayinfoDtlEx.json")
	public String selectPayinfoDtlEx(HttpServletRequest request, HttpServletResponse response, PayInfoDtlVO payInfoDtlVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 자동이체동의서 상세 결과 엑셀 다운로드 START"));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(payInfoDtlVO);

			// 본사 권한
			if(!"10".equals(payInfoDtlVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// vo에 값들을 채운다.

			List<?> payInfoDtlVOs = new ArrayList<PayInfoDtlVO>();

			payInfoDtlVOs = payInfoService.selectPayinfoDtlEx(payInfoDtlVO, pRequestParamMap);


			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "자동이체동의서상세_";//파일명
			String strSheetname = "자동이체동의서 상세데이터";//시트명


			//엑셀 첫줄 19
			String [] strHead = {"등록구분","등록일자","계약번호","개통구분","상태","고객명","전화번호","생년월일","개통일자","납부방법","대리점명","은행명","계좌번호","주소","파일명","시스템오류코드","검증상태","검증일시","검증내용"};

			String [] strValue = {"fileRegYnNm","rgstDt","contractNum","onOffTypeNm","subStatusNm","subLinkName","subscriberNo","userSsn","lstComActvDate","blBillingMethodNm","openAgntNm","bankCdNm","bankBnkacnNo","addr","realFileName","errorCdNm","approvalCdNm","approvalDt","approvalMsg"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 8000, 5000, 8000, 8000, 8000, 5000, 5000, 5000, 10000};

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = "";

			if(payInfoDtlVOs.size() <= 1000) {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, payInfoDtlVOs, strHead, intWidth, strValue, request, response, intInt);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, payInfoDtlVOs, strHead, strValue, request, response);
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

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					returnMsg, "MSP1020220", "자동이체동의서관리상세"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
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
	 * @Description : KT연동자료관리(대리점/고객센터용) 엑셀 다운로드
	 * @Param  : payInfoDtlVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2016.09.01
	 */
	@RequestMapping(value = "/cmn/payinfo/selectPayinfoKtEx.json", method={POST, GET})
	public String selectPayinfoKtEx(HttpServletRequest request, HttpServletResponse response, PayInfoDtlVO payInfoDtlVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" KT연동자료관리(대리점/고객센터용) 엑셀 다운로드 START"));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(payInfoDtlVO);

			// 본사 권한
			if(!"10".equals(payInfoDtlVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// vo에 값들을 채운다.

			List<?> payInfoDtlVOs = new ArrayList<PayInfoDtlVO>();

			payInfoDtlVOs = payInfoService.selectPayinfoKtEx(payInfoDtlVO, pRequestParamMap);


			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "KT연동자료관리_";//파일명
			String strSheetname = "KT연동자료관리 데이터";//시트명


			//엑셀 첫줄 19
			String [] strHead = {"등록구분","등록일자","계약번호","개통구분","상태","고객명","전화번호","생년월일","개통일자","납부방법","대리점명","은행명","계좌번호","주소","파일명","시스템오류코드","검증상태","검증일시","검증내용"};

			String [] strValue = {"fileRegYnNm","rgstDt","contractNum","onOffTypeNm","subStatusNm","subLinkName","subscriberNo","usrSsn","lstComActvDate","blBillingMethodNm","openAgntNm","bankCdNm","bankBnkacnNo","addr","realFileName","errorCdNm","approvalCdNm","approvalDt","approvalMsg"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 8000, 5000, 8000, 8000, 8000, 5000, 5000, 5000, 10000};

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			String strFileName = "";
			if(payInfoDtlVOs.size() <= 1000) {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, payInfoDtlVOs, strHead, intWidth, strValue, request, response, intInt);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, payInfoDtlVOs, strHead, strValue, request, response);
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
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					returnMsg, "MSP1020230", "KT연동동의서관리"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
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
	 * @Description : KT연동동의서관리 자료생성(대용량데이터 엑셀다운로드)
	 * @Param  : PayInfoDtlVO
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2018. 03. 08.
	 */
	@RequestMapping("/cmn/payinfo/getPayInfoKtExcelDownload.json")
	public String getPayInfoKtExcelDownload(@ModelAttribute("searchVO") PayInfoDtlVO searchVO,
					Model model,
					@RequestParam Map<String, Object> pRequestParamMap,
					HttpServletRequest request,
					HttpServletResponse response)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("KT연동동의서관리 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [PayInfoDtlVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;

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
			excelMap.put("DUTY_NM","CMN");
			excelMap.put("MENU_NM","KT연동동의서관리");
			String searchVal = "등록일자:"+searchVO.getSearchStartDt()+"~"+searchVO.getSearchEndDt()+
					"|검색구분:["+searchVO.getSearchGbn()+","+searchVO.getSearchName()+"]"+
					"|대리점:"+searchVO.getOrgnId()+
					"|등록구분:"+searchVO.getFileRegYn();

			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}

			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();

			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			//vo.setBatchJobId("BATCH00133");		개발배치ID
			vo.setBatchJobId("BATCH00113");		// 운영배치ID
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

			vo.setExecParam("{\"searchStartDt\":" + "\"" + searchVO.getSearchStartDt() + "\""
						+ ",\"searchEndDt\":" + "\"" + searchVO.getSearchEndDt() + "\""
						+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\""
						+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\""
						+ ",\"fileRegYn\":" + "\"" + searchVO.getFileRegYn() + "\""
						+ ",\"orgnId\":" + "\"" + searchVO.getOrgnId() + "\""
						+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
						+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\""
						+ ",\"ipAddr\":" + "\"" + ipAddr + "\""
						+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\""
						+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}"
					);

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
	 * @Description : 동의서 파일 이력 엑셀 다운로드
	 * @Param  : payInfoVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2016.09.01
	 */
	@RequestMapping("/cmn/payinfo/searchFileInfoEx.json")
	public String searchFileInfoEx(HttpServletRequest request, HttpServletResponse response, PayInfoVO payInfoVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 동의서 파일 이력 엑셀 다운로드 START"));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(payInfoVO);

			// 본사 권한
			if(!"10".equals(payInfoVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// vo에 값들을 채운다.

			List<?> payInfoVOs = new ArrayList<PayInfoVO>();

			payInfoVOs = payInfoService.searchFileInfoEx(payInfoVO, pRequestParamMap);


			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "동의서 파일_";//파일명
			String strSheetname = "동의서 파일 데이터";//시트명


			//엑셀 첫줄
			String [] strHead = {"파일구분","계약번호","고객명","전화번호","은행명","계좌번호","파일명","등록자","등록일자"};

			String [] strValue = {"fileType","contractNum","subLinkName","subscriberNo","bankCdNm","bankCdNo","fileName","regstId","regstDttm"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 8000, 5000, 5000};

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = "";
			if(payInfoVOs.size() <= 1000) {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, payInfoVOs, strHead, intWidth, strValue, request, response, intInt);
			} else {
				strFileName = fileDownService.csvDownProcStream(strFilename, payInfoVOs, strHead, strValue, request, response);
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
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					returnMsg, "MSP1020240", "동의서파일이력조회"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
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

	@RequestMapping("/cmn/payinfo/getReqBankComboList.json")
	public String getReqBankComboList(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			try {
				logger.info("=========================================================");
				logger.info("코드 조회 시작.");
				logger.info("=========================================================");
				LoginInfo loginInfo = new LoginInfo(request, response);
				loginInfo.putSessionToVo(searchVO);

				List<?> resultList = payInfoService.getReqBankComboList();

				resultMap =  makeResultMultiRow(searchVO, resultList);
			} catch(Exception e) {

				resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
				if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
				{
					throw new MvnoErrorException(e);
				}
			}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	@RequestMapping("/cmn/payinfo/getReqCardComboList.json")
	public String getReqCardComboList(HttpServletRequest request, HttpServletResponse response, PayInfoVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			try {
				logger.info("=========================================================");
				logger.info("코드 조회 시작.");
				logger.info("=========================================================");
				LoginInfo loginInfo = new LoginInfo(request, response);
				loginInfo.putSessionToVo(searchVO);

				List<?> resultList = payInfoService.getReqCardComboList(searchVO);

				resultMap =  makeResultMultiRow(searchVO, resultList);
			} catch(Exception e) {

				resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
				if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
				{
					throw new MvnoErrorException(e);
				}
			}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
