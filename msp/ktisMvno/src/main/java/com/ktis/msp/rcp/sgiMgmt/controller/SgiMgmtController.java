package com.ktis.msp.rcp.sgiMgmt.controller;

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
import com.ktis.msp.rcp.sgiMgmt.service.SgiMgmtService;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtCanListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtDmndDtlListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtDmndListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtOverPymListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtRejectListVO;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : SgiMgmtController
 * @Description : 보증보험연동
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2018.06.07 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2018. 6. 07.
 */
@Controller
public class SgiMgmtController extends BaseController {

	@Autowired
	private SgiMgmtService sgiMgmtService;
	
	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	public SgiMgmtController() {
		setLogPrefix("[SgiMgmtController] ");
	}

	/**
	 * @Description : 단말계약조회 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping(value = "/rcp/sgiMgmt/msp_rcp_sgi_1001.do", method={POST, GET})
	public ModelAndView sgiDmndMgmtGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("sgiMgmtDmndListVO") SgiMgmtDmndListVO searchVO, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말계약조회 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("currentMonth",orgCommonService.getLastMonth());
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("rcp/sgiMgmt/msp_rcp_sgi_1001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 단말계약조회
	 * @Param  :
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgiMgmt/getSgiDmndList.json")
	public String getSgiDmndList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") SgiMgmtDmndListVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말계약조회 START."));
		logger.info(generateLogMsg("Return Vo [RqstMgmtVo] = " + searchVO.toString()));
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

			List<?> resultList = sgiMgmtService.getSgiDmndList(searchVO, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 단말계약조회 내역 엑셀다운로드기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgimgmt/getSgiDmndListEx.json")
	@ResponseBody
	public String getSgiDmndListEx(@ModelAttribute("sgiMgmtVo") SgiMgmtDmndListVO sgiMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [sgiMgmtVo] = " + sgiMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(sgiMgmtVo);
			
			// 본사 권한
			if(!"10".equals(sgiMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			sgiMgmtVo.setBaseDate(request.getParameter("baseDate"));

			List<?> sgiMgmtDmndListVOs = new ArrayList<SgiMgmtDmndListVO>();
			
			sgiMgmtDmndListVOs = sgiMgmtService.getSgiDmndListEx(sgiMgmtVo, pReqParamMap);
			
			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "단말계약정보_";//파일명
			String strSheetname = "단말계약정보";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"업무구분","회사구분","보험청구의뢰번호","결과코드","오류결과내용","증권번호","보험관리번호","서비스번호","고객구분","주민번호",
					"계약자명","사업자번호","지불인ID","최종단말모델명","최종단말연번","이동전화번호","직권해지일","분납해지여부","명의변경일","이동전화가입일",
					"보험개시일","보험종료일","할부총횟수","할부납입횟수","보험가입금액","청구일자","청구금액","할부이자율","최초연체일","최초연체회차",
					"거주지전화번호","거주지우편번호","청구지전화번호","청구지우편번호","거주지주소구분코드","거주지주소","청구지주소구분코드","청구지주소","담당지점코드","미성년자성명",
					"미성년자주민번호","개인회생여부","개인회생단계","개인회생변제여부","개인회생변제금액","파산면책여부"};	//46

			String [] strValue = {"dutySctn","cmpnSctn","insrBillReqNo","resultCd","errResultCntt","securitiesNo","insrMngmNo","svcNo","cstmrCd","userSsn",
					"cstmrName","indvEntrNo","payerId","lstModelName","lstModelSerialNo","subscriberNo","officioRevocationDate","partPayRevocationYn","nameChangeDate","phoneJoinDate",
					"insrStrtDate","insrTrmnDate","instCnt","instPayNum","insrJoinAmt","billDate","billAmt","instRate","fstUnpayDate","fstUnpayNum",
					"residenceSubscriberNo","residencePost","billAddrSubscriberNo","billAddrPost","residenceCd","residenceAddr","billAddrCd","billAddr","branchCd","minorName",
					"minorSsn","indvRevivalYn","indvRevivalStep","indvRevivalRepayYn","indvRevivalRepayAmt","bankruptcyExemptionYn"};	//46

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000, 15000, 5000, 15000, 5000, 5000,	
							  5000, 5000, 5000, 5000, 5000, 5000};	//46

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0, 1, 1, 1, 0, 1, 1, 0, 1,
							0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0, 0, 0, 1, 0};	//46

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, sgiMgmtDmndListVOs, strHead, intWidth, strValue, request, response, intInt);

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
				pReqParamMap.put("DUTY_NM"   ,"RCP");                       //업무명
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
	 * @Description : 단말계약상세조회 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping(value = "/rcp/sgiMgmt/msp_rcp_sgi_1002.do", method={POST, GET})
	public ModelAndView sgiDmndDtlMgmtGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("sgiMgmtVO") SgiMgmtDmndDtlListVO searchVO, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말계약조회 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("currentMonth",orgCommonService.getLastMonth());
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			modelAndView.setViewName("rcp/sgiMgmt/msp_rcp_sgi_1002");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 단말계약상세조회
	 * @Param  :
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgiMgmt/getSgiDmndDtlList.json")
	public String getSgiDmndDtlList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") SgiMgmtDmndDtlListVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구 조회 START."));
		logger.info(generateLogMsg("Return Vo [RqstMgmtVo] = " + searchVO.toString()));
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

			List<?> resultList = sgiMgmtService.getSgiDmndDtlList(searchVO, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 단말 상세 내역 엑셀다운로드기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgimgmt/getSgiDmndDtlListEx.json")
	@ResponseBody
	public String getSgiDmndDtlListEx(@ModelAttribute("sgiMgmtVo") SgiMgmtDmndDtlListVO sgiMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [sgiMgmtVo] = " + sgiMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(sgiMgmtVo);
			
			// 본사 권한
			if(!"10".equals(sgiMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			sgiMgmtVo.setBaseDate(request.getParameter("baseDate"));

			List<?> sgiMgmtDmndListVos = new ArrayList<SgiMgmtDmndDtlListVO>();
			
			sgiMgmtDmndListVos = sgiMgmtService.getSgiDmndDtlListEx(sgiMgmtVo, pReqParamMap);
			
			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "단말계약정보_";//파일명
			String strSheetname = "단말계약정보";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"업무구분","회사구분","보험청구의뢰번호","오류결과코드","보험관리번호","할부연차","납기일(1)","납기일(2)","납기일(3)","납기일(4)",
					"납기일(5)","납기일(6)","납기일(7)","납기일(8)","납기일(9)","납기일(10)","납기일(11)","납기일(12)","할부원금(1)","할부원금(2)",
					"할부원금(3)","할부원금(4)","할부원금(5)","할부원금(6)","할부원금(7)","할부원금(8)","할부원금(9)","할부원금(10)","할부원금(11)","할부원금(12)",
					"할부이자(1)","할부이자(2)","할부이자(3)","할부이자(4)","할부이자(5)","할부이자(6)","할부이자(7)","할부이자(8)","할부이자(9)","할부이자(10)",
					"할부이자(11)","할부이자(12)","가산금(1)","가산금(2)","가산금(3)","가산금(4)","가산금(5)","가산금(6)","가산금(7)","가산금(8)",
					"가산금(9)","가산금(10)","가산금(11)","가산금(12)","미납할부원금(1)","미납할부원금(2)","미납할부원금(3)","미납할부원금(4)","미납할부원금(5)","미납할부원금(6)",
					"미납할부원금(7)","미납할부원금(8)","미납할부원금(9)","미납할부원금(10)","미납할부원금(11)","미납할부원금(12)","미납할부이자(1)","미납할부이자(2)","미납할부이자(3)","미납할부이자(4)",
					"미납할부이자(5)","미납할부이자(6)","미납할부이자(7)","미납할부이자(8)","미납할부이자(9)","미납할부이자(10)","미납할부이자(11)","미납할부이자(12)","미납가산금(1)","미납가산금(2)",
					"미납가산금(3)","미납가산금(4)","미납가산금(5)","미납가산금(6)","미납가산금(7)","미납가산금(8)","미납가산금(9)","미납가산금(10)","미납가산금(11)","미납가산금(12)"};

			String [] strValue = {"dutySctn","cmpnSctn","insrBillReqNo","errResultCd","grntInsrMngmNo","instYearNum","duedatDate1","duedatDate2","duedatDate3","duedatDate4",
					"duedatDate5","duedatDate6","duedatDate7","duedatDate8","duedatDate9","duedatDate10","duedatDate11","duedatDate12","instAmt1","instAmt2",
					"instAmt3","instAmt4","instAmt5","instAmt6","instAmt7","instAmt8","instAmt9","instAmt10","instAmt11","instAmt12",
					"insrIntAmt1","insrIntAmt2","insrIntAmt3","insrIntAmt4","insrIntAmt5","insrIntAmt6","insrIntAmt7","insrIntAmt8","insrIntAmt9","insrIntAmt10",
					"insrIntAmt11","insrIntAmt12","addAmt1","addAmt2","addAmt3","addAmt4","addAmt5","addAmt6","addAmt7","addAmt8",
					"addAmt9","addAmt10","addAmt11","addAmt12","unpayInstAmt1","unpayInstAmt2","unpayInstAmt3","unpayInstAmt4","unpayInstAmt5","unpayInstAmt6",
					"unpayInstAmt7","unpayInstAmt8","unpayInstAmt9","unpayInstAmt10","unpayInstAmt11","unpayInstAmt12","unpayInstIntAmt1","unpayInstIntAmt2","unpayInstIntAmt3","unpayInstIntAmt4",
					"unpayInstIntAmt5","unpayInstIntAmt6","unpayInstIntAmt7","unpayInstIntAmt8","unpayInstIntAmt9","unpayInstIntAmt10","unpayInstIntAmt11","unpayInstIntAmt12","unpayInstAddAmt1","unpayInstAddAmt2",
					"unpayInstAddAmt3","unpayInstAddAmt4","unpayInstAddAmt5","unpayInstAddAmt6","unpayInstAddAmt7","unpayInstAddAmt8","unpayInstAddAmt9","unpayInstAddAmt10","unpayInstAddAmt11","unpayInstAddAmt12"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,	
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, sgiMgmtDmndListVos, strHead, intWidth, strValue, request, response, intInt);

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
				pReqParamMap.put("DUTY_NM"   ,"RCP");                       //업무명
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
	 * @Description : 청구취소조회 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping(value = "/rcp/sgiMgmt/msp_rcp_sgi_1003.do", method={POST, GET})
	public ModelAndView sgiCanListMgmtGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("sgiMgmtVO") SgiMgmtCanListVO searchVO, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구취소조회 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("currentMonth",orgCommonService.getLastMonth());
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			modelAndView.setViewName("rcp/sgiMgmt/msp_rcp_sgi_1003");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 청구취소조회
	 * @Param  :
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgiMgmt/getSgiCanList.json")
	public String getSgiCanList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") SgiMgmtCanListVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구취소조회 START."));
		logger.info(generateLogMsg("Return Vo [RqstMgmtVo] = " + searchVO.toString()));
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

			List<?> resultList = sgiMgmtService.getSgiCanList(searchVO, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 청구취소조회 엑셀다운로드기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgimgmt/getSgiCanListEx.json")
	@ResponseBody
	public String getSgiCanListEx(@ModelAttribute("sgiMgmtVo") SgiMgmtCanListVO sgiMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [sgiMgmtVo] = " + sgiMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(sgiMgmtVo);
			
			// 본사 권한
			if(!"10".equals(sgiMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			sgiMgmtVo.setBaseDate(request.getParameter("baseDate"));

			List<?> sgiMgmtCanListVOs = new ArrayList<SgiMgmtCanListVO>();
			
			sgiMgmtCanListVOs = sgiMgmtService.getSgiCanListEx(sgiMgmtVo, pReqParamMap);
			
			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "청구취소조회_";//파일명
			String strSheetname = "청구취소조회";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"보상청구의뢰일","보상청구의뢰번호","고객명","고객주민번호","보증보험관리번호","서비스계약번호","이동전화번호","취소사유설명","청구취소금액","취소사유코드"};

			String [] strValue = {"compensationBillDate","compensationBillNo","cstmrName","userSsn","grntInsrMngmNo","svcCntrNo","subscriberNo","canRsn","billCanAmt","canRsnCd"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, sgiMgmtCanListVOs, strHead, intWidth, strValue, request, response, intInt);

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
				pReqParamMap.put("DUTY_NM"   ,"RCP");                       //업무명
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
	 * @Description : 과납내역조회 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping(value = "/rcp/sgiMgmt/msp_rcp_sgi_1004.do", method={POST, GET})
	public ModelAndView sgiOverPymListMgmtGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("sgiMgmtVO") SgiMgmtOverPymListVO searchVO, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("과납내역조회 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("currentMonth",orgCommonService.getLastMonth());
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			modelAndView.setViewName("rcp/sgiMgmt/msp_rcp_sgi_1004");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 과납내역조회
	 * @Param  :
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgiMgmt/getSgiOverPymList.json")
	public String getSgiOverPymList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") SgiMgmtOverPymListVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("과납내역 조회 START."));
		logger.info(generateLogMsg("Return Vo [RqstMgmtVo] = " + searchVO.toString()));
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

			List<?> resultList = sgiMgmtService.getSgiOverPymList(searchVO, pRequestParamMap);
						
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 과납내역조회 엑셀다운로드기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgimgmt/getSgiOverPymListEx.json")
	@ResponseBody
	public String getSgiOverPymListEx(@ModelAttribute("sgiMgmtVo") SgiMgmtOverPymListVO sgiMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [sgiMgmtVo] = " + sgiMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(sgiMgmtVo);
			
			// 본사 권한
			if(!"10".equals(sgiMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			sgiMgmtVo.setBaseDate(request.getParameter("baseDate"));

			List<?> sgiMgmtOverPymListVOs = new ArrayList<SgiMgmtOverPymListVO>();
			
			sgiMgmtOverPymListVOs = sgiMgmtService.getSgiOverPymListEx(sgiMgmtVo, pReqParamMap);
			
			logger.info(generateLogMsg("================================================================="));
			logger.info(generateLogMsg("Return Vo [sgiMgmtOverPymListVOs] = " + sgiMgmtOverPymListVOs.toString()));
			logger.info(generateLogMsg("================================================================="));
						
			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "과납내역조회_";//파일명
			String strSheetname = "과납내역조회";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"청구계정번호","전화번호","주민/법인번호","고객명(상호)","보험관리번호","서비스계약번호","수납일자","청구금액","수납금액","과납금액"};

			String [] strValue = {"ban","subscriberNo","userSsn","cstmrName","grntInsrMngmNo","svcCntrNo","blpymDate","invAmt","pymnAmt","excessAmt"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, sgiMgmtOverPymListVOs, strHead, intWidth, strValue, request, response, intInt);

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
				pReqParamMap.put("DUTY_NM"   ,"RCP");                       //업무명
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
	 * @Description : 불능처리조회 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping(value = "/rcp/sgiMgmt/msp_rcp_sgi_1005.do", method={POST, GET})
	public ModelAndView sgiRejectListMgmtGrid(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("sgiMgmtVO") SgiMgmtRejectListVO searchVO, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("불능처리조회 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("currentMonth",orgCommonService.getLastMonth());
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			modelAndView.setViewName("rcp/sgiMgmt/msp_rcp_sgi_1005");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 불능처리조회
	 * @Param  :
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgiMgmt/getSgiRejectList.json")
	public String getSgiRejectList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") SgiMgmtRejectListVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("불능처리조회 START."));
		logger.info(generateLogMsg("Return Vo [RqstMgmtVo] = " + searchVO.toString()));
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

			List<?> resultList = sgiMgmtService.getSgiRejectList(searchVO, pRequestParamMap);
						
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 불능처리조회 엑셀다운로드기능
	 * @Param  : SgiMgmtRejectListVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	@RequestMapping("/rcp/sgimgmt/getSgiRejectListEx.json")
	@ResponseBody
	public String getSgiRejectListEx(@ModelAttribute("sgiMgmtVo") SgiMgmtRejectListVO sgiMgmtVo, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [sgiMgmtVo] = " + sgiMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(sgiMgmtVo);
			
			// 본사 권한
			if(!"10".equals(sgiMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			sgiMgmtVo.setBaseDate(request.getParameter("baseDate"));

			List<?> sgiMgmtRejectListVOs = new ArrayList<SgiMgmtRejectListVO>();
			
			sgiMgmtRejectListVOs = sgiMgmtService.getSgiRejectListEx(sgiMgmtVo, pReqParamMap);
						
			String serverInfo = propertyService.getString("excelPath");
			String strFilename = serverInfo  + "불능처리조회_";//파일명
			String strSheetname = "불능처리조회";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"회사코드","보험청구의뢰번호","보험관리번호","기각사유","보증사청구요청일","지불인ID","이동전화","고객구분","주민/법인번호","고객번호",
					"보험개시일","직권해지일","증권번호","보험가입금액","청구금액","계약자명","상위영업팀명","대리점코드","대리점명","홈지사명",
					"관리신용팀명"};

			String [] strValue = {"cmpnCd","insrBillReqNo","insrMngmNo","rejectRsn","grntBillReqDate","payerId","subscriberNo","cstmrCd","userSsn","cstmrNo",
					"insrStrtDate","officioRevocationDate","securitiesNo","insrJoinAmt","billAmt","cstmrName","topSalesTeamName","agntCd","agntName","homeBranchName",
					"mngmCreditTeamName"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000};

			//숫자 컬럼
			int[] intInt = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 1, 1, 0, 0, 0, 0, 0,
					0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, sgiMgmtRejectListVOs, strHead, intWidth, strValue, request, response, intInt);

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
				pReqParamMap.put("DUTY_NM"   ,"RCP");                       //업무명
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