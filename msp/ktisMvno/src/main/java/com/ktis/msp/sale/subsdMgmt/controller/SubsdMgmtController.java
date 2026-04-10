package com.ktis.msp.sale.subsdMgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.ktis.msp.cmn.fileup2.service.FileUp2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.sale.subsdMgmt.service.SubsdMgmtService;
import com.ktis.msp.sale.subsdMgmt.vo.SubsdMgmtByExcelVO;
import com.ktis.msp.sale.subsdMgmt.vo.SubsdMgmtVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class SubsdMgmtController extends BaseController {

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private FileDownService  fileDownService;

	@Resource(name="subsdMgmtService")
	private SubsdMgmtService subsdMgmtService;

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private FileUp2Service fileUp2Service;

	/**
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/sale/subsdMgmt/getSubsdMgmtList.do")
	public ModelAndView getSubsdMgmtList(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
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
			
			modelAndView.setViewName("sale/subsdMgmt/subsdMgmt");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}


	@RequestMapping(value="/sale/subsdMgmt/getSubsdMgmtMainList.json")
	public String getSubsdMgmtMainList(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = subsdMgmtService.getSubsdMgmtMainList(searchVO);

			resultMap =  makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					"", "MSP2002011", "공시지원금관리"))
			{
				throw new MvnoErrorException(e);
			}
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";

	}

	@RequestMapping("/sale/subsdMgmt/getSubsdMgmtMainListExcel.json")
	public String getSubsdMgmtMainListExcel(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
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
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = subsdMgmtService.getSubsdMgmtMainListExcel(searchVO);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "공시지원금_";//파일명
			String strSheetname = "공시지원금";//시트명

			String [] strHead = {"시작일자", "종료일자", "제품ID", "제품코드", "제품명", "요금제", "공시지원금"};
			String [] strValue = {"applStrtDt", "applEndDt", "prdtId", "prdtCode", "prdtNm", "rateNm", "subsdAmt"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 8000, 15000, 12000, 8000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 1};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);

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

	@RequestMapping(value="/sale/subsdMgmt/getSubsdMgmtDtlList.json")
	public String getSubsdMgmtDtlList(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = subsdMgmtService.getSubsdMgmtDtlList(searchVO);

			resultMap =  makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";

	}

	@RequestMapping("/sale/subsdMgmt/getSubsdMgmtDtlListExcel.json")
	public String getSubsdMgmtDtlListExcel(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
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
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = subsdMgmtService.getSubsdMgmtDtlListExcel(searchVO);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "공시지원금_";//파일명
			String strSheetname = "공시지원금";//시트명

			String [] strHead = {"시작일자", "종료일자", "제품ID", "제품코드", "제품명", "요금제", "개통유형", "공시지원금"};
			String [] strValue = {"applStrtDt", "applEndDt", "prdtId", "prdtCode", "prdtNm", "rateNm", "operTypeNm", "subsdAmt"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 8000, 10000, 10000, 5000, 8000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 1};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);

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

	@RequestMapping("/sale/subsdMgmt/insertSubsdAmtList.json")
	public String insertSubsdAmtList(@RequestBody String pJsonString,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		logger.debug("pJsonString=" + pJsonString);
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List list = getVoFromMultiJson(pJsonString, "NEW", SubsdMgmtVO.class);
			logger.debug("size()=" + list.size());

			for(int i=0; i<list.size(); i++){
				SubsdMgmtVO searchVO = (SubsdMgmtVO) list.get(i);

				// 사용자ID
				searchVO.setUserId(loginInfo.getUserId());

				//요금제그룹 등록인 경우 요금제그룹코드 체크
				if(searchVO.getRateGrpYn().equals("Y")){
					if(searchVO.getRateGrpCd() == null || searchVO.getRateGrpCd().equals("")){
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
						resultMap.put("msg",  "요금제그룹코드를 선택하세요");

						model.addAttribute("result", resultMap);

						return "jsonView";
					}else{
						List<EgovMap> rateList = (List<EgovMap>) subsdMgmtService.getRateGrpRateCdList(searchVO);

						for(EgovMap rateMap : rateList){
							searchVO.setRateCd((String) rateMap.get("rateCd"));

							subsdMgmtService.insertSubsdAmtMst(searchVO);
						}
					}
				}else{
					subsdMgmtService.insertSubsdAmtMst(searchVO);

				}
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch(Exception e){
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/subsdMgmt/getPrdtComboList.json")
	public String getPrdtComboList(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = subsdMgmtService.getPrdtComboList();
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/subsdMgmt/getCommonGridList.json")
	public String getCommonGridList(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			logger.debug("searchVO=" + searchVO);
			logger.debug("paramMap=" + paramMap);

			List<?> resultList = subsdMgmtService.getCommonGridList(paramMap);

			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(paramMap, resultList);
		}
		catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/subsdMgmt/updateSubsdAmtInfo.json")
	public String updateSubsdAmtInfo(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
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
			
			subsdMgmtService.updateSubsdAmtInfo(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	@RequestMapping("/sale/subsdMgmt/insertSubsdAmtGrpList.json")
	public String insertSubsdAmtGrpList(@RequestBody String pJsonString,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap)
	{

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		logger.debug("pJsonString=" + pJsonString);
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List list = getVoFromMultiJson(pJsonString, "ALL", SubsdMgmtVO.class);
			logger.debug("size()=" + list.size());

/*			// 개통유형조회(기변 제외)
			paramMap.put("grpId", "CMN0049");
			paramMap.put("optVal","HCN");
			List<EgovMap> olist = (List<EgovMap>) subsdMgmtService.getCommonGridList(paramMap);

			// 약정기간(0개월 제외)
			paramMap.put("grpId", "CMN0053");
			paramMap.put("optVal", "0");
			//
			List<EgovMap> alist = (List<EgovMap>) subsdMgmtService.getCommonGridList(paramMap);*/
			
			
			paramMap.put("grpId", "CMN0053");
			paramMap.put("optVal", "Y");
			List<EgovMap> listMap = (List<EgovMap>) subsdMgmtService.getCmnGrpCdListByEtc5(paramMap);
			ArrayList<String> aAryList = new ArrayList<String>();
			
			for(EgovMap aMap : listMap){
				aAryList.add((String) aMap.get("cdVal"));
			}
			
			ArrayList<String> alist = null;
			
			for(int i=0; i<list.size(); i++){
				SubsdMgmtVO searchVO = (SubsdMgmtVO) list.get(i);

				// 사용자ID
				searchVO.setUserId(loginInfo.getUserId());
				
				//업무구분
				ArrayList<String> olist = subsdMgmtService.getDataTokenList(searchVO.getOperType(), "|", "N");
				
				//약정기간
				if("All".equals(searchVO.getAgrmTrm())){
					alist = aAryList;
				}else{
					alist = subsdMgmtService.getDataTokenList(searchVO.getAgrmTrm(), "|", "N");
				}

				//요금제그룹 등록인 경우 요금제그룹코드 체크
				if(searchVO.getRateGrpYn().equals("Y")){
					if(searchVO.getRateGrpCd() == null || searchVO.getRateGrpCd().equals("")){
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
						resultMap.put("msg",  "요금제그룹코드를 선택하세요");

						model.addAttribute("result", resultMap);

						return "jsonView";
					}else{
						
						String strPrdtIndCd = subsdMgmtService.getPrdtIndCd(searchVO.getPrdtId());
						searchVO.setPrdtIndCd(strPrdtIndCd);

						List<EgovMap> rateList = (List<EgovMap>) subsdMgmtService.getRateGrpRateCdList(searchVO);

						for(EgovMap rateMap : rateList){
							searchVO.setRateCd((String) rateMap.get("rateCd"));

							for(int nIdx1 = 0; nIdx1 <  olist.size(); nIdx1++){
								searchVO.setOperType(olist.get(nIdx1));

								for(int nIdx2 = 0; nIdx2 < alist.size(); nIdx2++){
									searchVO.setAgrmTrm(alist.get(nIdx2));

									subsdMgmtService.insertSubsdAmtMst(searchVO);

								}
							}
						}
					}
				}else{

					for(int nIdx1 = 0; nIdx1 <  olist.size(); nIdx1++){
						searchVO.setOperType(olist.get(nIdx1));

						for(int nIdx2 = 0; nIdx2 < alist.size(); nIdx2++){
							searchVO.setAgrmTrm(alist.get(nIdx2));

							subsdMgmtService.insertSubsdAmtMst(searchVO);

						}
					}

				}
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch(Exception e){
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @param 공시지원금 복사 대상 조회
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/sale/subsdMgmt/getCopySubsdAmtInfo.json")
	public String getCopySubsdAmtInfo(@ModelAttribute("searchVO") SubsdMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

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
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = subsdMgmtService.getCopySubsdAmtInfo(searchVO);

			resultMap =  makeResultMultiRow(searchVO, resultList);

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";

	}
	
	/**
	 * @param 공시지원금 엑셀 파일 업로드
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/sale/subsdMgmt/regSubsdExcelUp.do")
	public String regSubsdExcelUp(
								ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			String uploadFileName = fileUp2Service.fileUpLoad(pRequest, "file", "CMN" );
			
			resultMap.put("state", true);
			resultMap.put("name", uploadFileName);
			resultMap.put("size", "");
			
		} catch (Exception e) {
			
			resultMap.put("state", false);
			resultMap.put("name", "");
			resultMap.put("size", "");
			model.addAttribute("result", resultMap);
			
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonViewArray";
	}
	
	
	/**
	 * 공시지원금 엑셀 파일 임시 테이블에 저장
	 * 
	 * @return String
	 * @author 
	 * @version 
	 * @created 
	 * @updated
	 */
	@RequestMapping(value="/rcp/dlvyMgmt/regTmpOfclSubsdData.json")
	public String regTmpOfclSubsdData(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("subsdMgmtByExcelVO") SubsdMgmtByExcelVO subsdMgmtByExcelVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(subsdMgmtByExcelVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(subsdMgmtByExcelVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + subsdMgmtByExcelVO.getFileName();
			
			String[] arrCell = {  "rprsPrdtId"
								, "col01", "col02", "col03", "col04", "col05", "col06", "col07", "col08", "col09", "col10"
								, "col11", "col12", "col13", "col14", "col15", "col16", "col17", "col18", "col19", "col20"
								, "col21", "col22", "col23", "col24", "col25", "col26", "col27", "col28", "col29", "col30"
								, "col31", "col32", "col33", "col34", "col35", "col36", "col37", "col38", "col39", "col40"
			};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.sale.subsdMgmt.vo.SubsdMgmtByExcelVO", sOpenFileName, arrCell, 0);
			
			subsdMgmtService.regTmpOfclSubsdData(uploadList, subsdMgmtByExcelVO, arrCell);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP2002011", "공시지원금관리")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
}
