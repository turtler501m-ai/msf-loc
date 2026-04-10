package com.ktis.msp.sale.plcyMgmt.controller;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.ktis.msp.sale.plcyMgmt.common.PolicyConstants;
import com.ktis.msp.sale.plcyMgmt.service.PlcyMgmtService;
import com.ktis.msp.sale.plcyMgmt.vo.PlcyMgmtVO;
import com.ktis.msp.sale.plcyMgmt.vo.PolicyLogVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class PlcyMgmtController extends BaseController {

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private FileDownService  fileDownService;

	@Resource(name="plcyMgmtService")
	private PlcyMgmtService plcyMgmtService;

	@Autowired
	private MenuAuthService menuAuthService;


	/**
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/sale/plcyMgmt/getPlcyMgmtList.do")
	public ModelAndView getPlcyMgmtList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
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
			
			modelAndView.setViewName("sale/plcyMgmt/plcyMgmt");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	@RequestMapping(value="/sale/plcyMgmt/getPlcyMgmtList.json")
	public String getPlcyMgmtList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {
		
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
			List<?> resultList = plcyMgmtService.getPlcyMgmtList(searchVO);

			resultMap =  makeResultMultiRow(searchVO, resultList);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";

	}

	@RequestMapping("/sale/plcyMgmt/getPlcyMgmtListExcel.json")
	public String getPlcyMgmtListExcel(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
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
			
			List<?> list = plcyMgmtService.getPlcyMgmtListExcel(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "판매정책_";//파일명
			String strSheetname = "판매정책";//시트명
			
			String [] strHead = {"시작일자", "종료일자", "제품ID", "제품코드", "제품명", "요금제", "약정기간", "개통유형", "공시지원금"};
			String [] strValue = {"applStrtDt", "applEndDt", "prdtId", "prdtCode", "prdtNm", "rateNm", "agrmTrm", "operTypeNm", "subsdAmt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 8000, 15000, 12000, 5000, 5000, 8000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 1};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
			file = new File(strFileName);
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			in = new FileInputStream(file);
			out = response.getOutputStream();
			int temp = -1;
			while((temp = in.read()) != -1) {
				out.write(temp);
			}
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}
				
				paramMap.put("FILE_NM"   , file.getName());              //파일명
				paramMap.put("FILE_ROUT" , file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   , "MSP");                       //업무명
				paramMap.put("IP_INFO"   , ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" , (int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId"));  //메뉴ID
				paramMap.put("DATA_CNT", 0);                             //자료건수
				
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

	@RequestMapping("/sale/plcyMgmt/getAgncyTrgtList.json")
	public String getAgncyTrgtList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
			
			List<?> resultList = plcyMgmtService.getAgncyTrgtList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getRateTrgtList.json")
	public String getRateTrgtList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
			
			List<?> resultList = plcyMgmtService.getRateTrgtList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getPrdtTrgtList.json")
	public String getPrdtTrgtList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
			
			List<?> resultList = plcyMgmtService.getPrdtTrgtList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getRateArpuTrgtList.json")
	public String getRateArpuTrgtList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
			
			List<?> resultList = plcyMgmtService.getRateArpuTrgtList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getSalePlcyTrgtList.json")
	public String getSalePlcyTrgtList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		logger.debug("paramMap=" + paramMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			// 판매정책기본정보 등록
			logger.debug("searchVO=" + searchVO);
			logger.debug("prdtId=" + paramMap.get("prdtId"));
			logger.debug("orgnId=" + paramMap.get("orgnId"));
			logger.debug("rateCd=" + paramMap.get("rateCd"));
			logger.debug("arpuId=" + paramMap.get("arpuId"));
			logger.debug("operType=" + paramMap.get("operType"));
			logger.debug("agrmTrm=" + paramMap.get("agrmTrm"));
			logger.debug("instTrm=" + paramMap.get("instTrm"));
			logger.debug("sprtTp=" + paramMap.get("sprtTp"));
			logger.debug("selfOpenYn=" + paramMap.get("selfOpenYn"));

			searchVO.setFlag((String) paramMap.get("flag"));
			searchVO.setSalePlcyCd((String) paramMap.get("salePlcyCd"));
			searchVO.setSalePlcyNm((String) paramMap.get("salePlcyNm"));
			searchVO.setPlcyTypeCd((String) paramMap.get("plcyTypeCd"));
			searchVO.setPlcySctnCd((String) paramMap.get("plcySctnCd"));
			searchVO.setApplSctnCd((String) paramMap.get("applSctnCd"));
			searchVO.setPrdtSctnCd((String) paramMap.get("prdtSctnCd"));
			searchVO.setSprtTp((String) paramMap.get("sprtTp"));
			searchVO.setSaleStrtDt((String) paramMap.get("saleStrtDt"));
			searchVO.setSaleStrtTm((String) paramMap.get("saleStrtTm"));
			searchVO.setSaleEndDt((String) paramMap.get("saleEndDt"));
			searchVO.setSaleEndTm((String) paramMap.get("saleEndTm"));
			searchVO.setInstRate((String) paramMap.get("instRate"));
			searchVO.setNewYn((String) paramMap.get("newYn"));
			searchVO.setMnpYn((String) paramMap.get("mnpYn"));
			searchVO.setHcnYn((String) paramMap.get("hcnYn"));
			searchVO.setHdnYn((String) paramMap.get("hdnYn"));
			searchVO.setSelfOpenYn((String) paramMap.get("selfOpenYn"));
			searchVO.setUserId(loginInfo.getUserId());
			
			String salePlcyCd = plcyMgmtService.setSalePlcyMst(searchVO);
			
			plcyMgmtService.setSalePlcyInfo(searchVO, paramMap);
			
			searchVO.setSalePlcyCd(salePlcyCd);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			//List<?> resultList = plcyMgmtService.getPlcySubsdList(searchVO);
			
			//resultMap = makeResultMultiRow(paramMap, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/sale/plcyMgmt/getSaleOrgnList.json")
	public String getSaleOrgnList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
			
			List<?> resultList = plcyMgmtService.getSaleOrgnList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getSaleRateList.json")
	public String getSaleRateList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> resultList = plcyMgmtService.getSaleRateList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	@RequestMapping("/sale/plcyMgmt/getSaleOperList.json")
	public String getSaleOperList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> resultList = plcyMgmtService.getSaleOperList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getSaleAgrmList.json")
	public String getSaleAgrmList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> resultList = plcyMgmtService.getSaleAgrmList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getSalePrdtList.json")
	public String getSalePrdtList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> resultList = plcyMgmtService.getSalePrdtList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getSaleArpuList.json")
	public String getSaleArpuList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> resultList = plcyMgmtService.getSaleArpuList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getSaleSubsdList.json")
	public String getSaleSubsdList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> resultList = plcyMgmtService.getPlcySubsdList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	@RequestMapping("/sale/plcyMgmt/setPlcyConfirm.json")
	public String setPlcyConfirm(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			searchVO.setUserId(loginInfo.getUserId());
			
			plcyMgmtService.setPlcyConfirm(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/setPlcyCancel.json")
	public String setPlcyCancel(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			searchVO.setUserId(loginInfo.getUserId());
			
			plcyMgmtService.setPlcyCancel(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	@RequestMapping(value = "/sale/plcyMgmt/getAgncySubsdList.do", method={POST, GET})
	public ModelAndView getAgncySubsdList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			/* 본사조직이 아니면 강제 조직 세팅 */
			if(!searchVO.getSessionUserOrgnTypeCd().equals("10")) {
				searchVO.setOrgnId(searchVO.getSessionUserOrgnId());	/** 조직ID */
				searchVO.setOrgnNm(searchVO.getSessionUserOrgnNm());	/** 조직명 */
			}
			
			searchVO.setUserOrgnTypeCd(searchVO.getSessionUserOrgnTypeCd());	/** 사용자조직유형코드 */
			
			modelAndView.getModelMap().addAttribute("info", searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("sale/plcyMgmt/agncySubsdMgmt");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	@RequestMapping(value="/sale/plcyMgmt/getAgncySubsdList.json")
	public String getAgncySubsdList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {
		
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
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getOrgnId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = plcyMgmtService.getAgncySubsdList(searchVO);
			
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch (Exception e) {
				
				
				if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
						messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
						"", "MSP2002013", "대리점보조금관리"))
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
	
	@RequestMapping(value="/sale/plcyMgmt/getAgncySubsdListExcel.json")
	public String getAgncySubsdListExcel(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
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
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getOrgnId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = plcyMgmtService.getAgncySubsdListExcel(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "판매정책_";//파일명
			String strSheetname = "판매정책";//시트명
			String [] strHead = {"판매정책명", "대리점", "판매시작일시", "판매종료일시", "제품코드", "제품명", "중고여부", "요금제", "약정기간", "개통유형", "지원금유형", "기본료", "할인금액", "추가할인금액", "단말금액", "공시지원금", "대리점보조금", "할부원금", "할부수수료", "신규수수료", "번호이동수수료", "기변수수료", "구분", "URL경로","모바일URL경로", "제휴전용URL", "제휴전용 모바일URL"};
			String [] strValue = {"salePlcyNm", "orgnNm", "strtDtm", "endDtm", "prdtCode", "prdtNm", "oldNm", "rateNm", "agrmTrm", "operTypeNm", "sprtTpNm", "baseAmt", "dcAmt", "addDcAmt", "hndstAmt", "subsdAmt", "agncySubsdAmt", "instAmt", "instCmsn", "newCmsnAmt", "mnpCmsnAmt", "hcnCmsnAmt", "selfOpenNm", "urlPath","murlPath", "urlPath2","murlPath2"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {10000, 5000, 5000, 5000, 5000, 5000, 3000, 10000, 3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 20000, 20000, 20000, 20000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
			
			file = new File(strFileName);
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			in = new FileInputStream(file);
			out = response.getOutputStream();
			int temp = -1;
			while((temp = in.read()) != -1) {
				out.write(temp);
			}
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}

				paramMap.put("FILE_NM"   , file.getName());              //파일명
				paramMap.put("FILE_ROUT" , file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   , "MSP");                       //업무명
				paramMap.put("IP_INFO"   , ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" , (int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId"));  //메뉴ID
				paramMap.put("DATA_CNT", 0);                             //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID

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
	
	@RequestMapping(value="/sale/plcyMgmt/updateAgncySubsdAmt.json")
	public String updateAgncySubsdAmt(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
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
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getOrgnId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			searchVO.setUserId(loginInfo.getUserId());
			
			plcyMgmtService.updateAgncySubsdAmt(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	
	
	@RequestMapping(value="/sale/plcyMgmt/deleteSalePlcyMst.json")
	public String deleteSalePlcyMst(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
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
			searchVO.setUserId(loginInfo.getUserId());
			logger.debug("paramMap=" + paramMap);
			
			// 확정체크
			plcyMgmtService.checkPolicyConfirm((String) paramMap.get("salePlcyCd"));
			
			// 이벤트로그확인
			plcyMgmtService.checkPlcyEventLog((String) paramMap.get("salePlcyCd"));
			
			plcyMgmtService.deleteSalePlcyMst((String) paramMap.get("salePlcyCd"), loginInfo.getUserId());
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	
	@RequestMapping(value="/sale/plcyMgmt/initSalePlcyMst.json")
	public String initSalePlcyMst(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
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
			searchVO.setUserId(loginInfo.getUserId());
			logger.debug("paramMap=" + paramMap);
			
			// 확정체크
			plcyMgmtService.checkPolicyConfirm((String) paramMap.get("salePlcyCd"));
			
			// 이벤트로그확인
			plcyMgmtService.checkPlcyEventLog((String) paramMap.get("salePlcyCd"));
			
			plcyMgmtService.initSalePlcyMst((String) paramMap.get("salePlcyCd"), loginInfo.getUserId());
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	
	@RequestMapping(value="/sale/plcyMgmt/updatePlcyMgmtClose.json")
	public String updatePlcyMgmtClose(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
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
			searchVO.setUserId(loginInfo.getUserId());
			logger.debug("paramMap=" + paramMap);
			
			plcyMgmtService.updatePlcyMgmtClose(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	
	@RequestMapping(value="/sale/plcyMgmt/getPlcyAgncyAddList.json")
	public String getPlcyAgncyAddList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {
		
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
			List<?> resultList = plcyMgmtService.getPlcyAgncyAddList(searchVO);

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
	 * @Description : 선불대리점 추가
	 * @Param  : PlcyMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 29.
	 */
	@RequestMapping(value="/sale/plcyMgmt/getPlcyAgncyAddList2.json")
	public String getPlcyAgncyAddList2(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {
		
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
			List<?> resultList = plcyMgmtService.getPlcyAgncyAddList2(searchVO);

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
	
	@RequestMapping("/sale/plcyMgmt/setPlcyAgncyAdd.json")
	public String setPlcyAgncyAdd(@RequestBody String pJsonString,
			@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap) {
		
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
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = getVoFromMultiJson(pJsonString, "ALL", PlcyMgmtVO.class);
			logger.debug("size()=" + list.size());
			
			plcyMgmtService.setPlcyAgncyAdd(list, loginInfo, "addAgency");
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/setPlcyPreAgncyAdd.json")
	public String setPlcyPreAgncyAdd(@RequestBody String pJsonString,
			@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap) {
		
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
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = getVoFromMultiJson(pJsonString, "ALL", PlcyMgmtVO.class);
			logger.debug("size()=" + list.size());
			
			plcyMgmtService.setPlcyAgncyAdd(list, loginInfo, "addPreAgency");
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getPlcyPrdtListExcel.json")
	public String getPlcyPrdtListExcel(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
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
			
			List<?> list = plcyMgmtService.getSalePrdtList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "판매정책제품_";//파일명
			String strSheetname = "판매정책제품";//시트명
			
			String [] strHead = {"판매정책코드", "제품코드", "제품명", "중고여부", "신규수수료", "번호이동수수료", "기변수수료"};
			String [] strValue = {"salePlcyCd", "prdtCode", "prdtNm", "oldYn", "newCmsnAmt", "mnpCmsnAmt", "hcnCmsnAmt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 1, 1, 1};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
			file = new File(strFileName);
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			in = new FileInputStream(file);
			out = response.getOutputStream();
			int temp = -1;
			while((temp = in.read()) != -1) {
				out.write(temp);
			}
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}
				
				paramMap.put("FILE_NM"   , file.getName());              //파일명
				paramMap.put("FILE_ROUT" , file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   , "MSP");                       //업무명
				paramMap.put("IP_INFO"   , ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" , (int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId"));  //메뉴ID
				paramMap.put("DATA_CNT", 0);                             //자료건수
				
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
	
	@RequestMapping("/sale/plcyMgmt/getPlcyArpuCmsnListExcel.json")
	public String getPlcyArpuCmsnListExcel(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
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
			
			List<?> list = plcyMgmtService.getSaleArpuList(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "판매정책ARPU수수료_";//파일명
			String strSheetname = "ARPU수수료";//시트명
			
			String [] strHead = {"판매정책코드", "제품코드", "중고여부", "요금제코드", "ARPU수수료"};
			String [] strValue = {"salePlcyCd", "prdtCode", "oldYn", "rateCd", "arpuCmsnAmt"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 1};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
			file = new File(strFileName);
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			in = new FileInputStream(file);
			out = response.getOutputStream();
			int temp = -1;
			while((temp = in.read()) != -1) {
				out.write(temp);
			}
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}
				
				paramMap.put("FILE_NM"   , file.getName());              //파일명
				paramMap.put("FILE_ROUT" , file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   , "MSP");                       //업무명
				paramMap.put("IP_INFO"   , ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" , (int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId"));  //메뉴ID
				paramMap.put("DATA_CNT", 0);                             //자료건수
				
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
	
	@RequestMapping("/sale/plcyMgmt/setPlcyCopy.json")
	public String setPlcyCopy(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap) {
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, String> resultMap = new HashMap<String, String>();
		
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
			
			logger.debug("paramMap=" + paramMap);
			
			plcyMgmtService.setPlcyCopy(paramMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/sale/plcyMgmt/getInstList.json")
	public String getInstList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			List<?> resultList = plcyMgmtService.getInstList(searchVO);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value="/sale/plcyMgmt/getPlcyRateAddList.json")
	public String getPlcyRateAddList(@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model) {
		
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
			List<?> resultList = plcyMgmtService.getPlcyRateAddList(searchVO);

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
	
	@RequestMapping("/sale/plcyMgmt/setPlcyRateAdd.json")
	public String setPlcyRateAdd(@RequestBody String pJsonString,
								@ModelAttribute("searchVO") PlcyMgmtVO searchVO,
								ModelMap model,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap) {
		
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
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = getVoFromMultiJson(pJsonString, "ALL", PlcyMgmtVO.class);
			logger.debug("size()=" + list.size());
			
			plcyMgmtService.setPlcyRateAdd(list, loginInfo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 할부 개월수 팝업 화면
	 * @Param  : void
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/sale/plcyMgmt/sertchInst.do", method={POST, GET})
	public String selectSaleInstPu(HttpServletRequest request, HttpServletResponse response){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("할부 개월수 팝업 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		//v2018.11 PMD 
		//LoginInfo loginInfo = new LoginInfo(request, response);
		new LoginInfo(request, response);
		
		return "sale/plcyMgmt/msp_org_pu_1024";
	}

	/**
	 * @Description : 할부 개월 수 팝업 SELECT
	 * @Param  : PlcyMgmtVO
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/sale/plcyMgmt/listInstNomPu.json")
	public String listInstNomPu(PlcyMgmtVO plcyMgmtVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, HttpServletRequest pRequest, HttpServletResponse pResponse){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("할부 개월 수 팝업 SELECT START."));
		logger.info(generateLogMsg("================================================================="));

		try {
			
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = plcyMgmtService.selectInstNomPuList(plcyMgmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		return "jsonView";
	}
	
	/**
	 * @Description : 할부 개월수 일괄 저장
	 * @Param  : data
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/sale/plcyMgmt/insertInstNom.json")
	public String insertInstNom(@RequestBody String data, ModelMap model, HttpServletRequest paramReq, HttpServletResponse paramRes, @RequestParam Map<String, Object> pRequestParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("할부 개월수 일괄 저장 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		PolicyLogVO policyLogVO = new PolicyLogVO();
		PlcyMgmtVO listVOs = new PlcyMgmtVO();
		LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
		boolean checkFlag = true;
		
		try {
			
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 蹂몄궗 沅뚰븳
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			listVOs = om.readValue(data, PlcyMgmtVO.class);
			
			plcyMgmtService.checkPlcyEventLog(listVOs.getSalePlcyCd());
			
		} catch ( Exception e)
		{
			resultMap.clear();
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	 		resultMap.put("msg", "");
	 		checkFlag = false;
	 		throw new MvnoErrorException(e);
		}
		
		if(checkFlag) {
			try {
				policyLogVO = plcyMgmtService.insertSaleEventLog(listVOs.getSalePlcyCd(), loginInfo.getUserId(), PolicyConstants.PLCY_EVENT_1012);
				
				int returnCnt =  plcyMgmtService.deleteInstNom(listVOs);
	
				if (listVOs.getItems() != null) {
					for ( int i = 0 ; i < listVOs.getItems().size(); i++)
					{						
						listVOs.setInstNom(listVOs.getItems().get(i).getInstNom());
						listVOs.setSessionUserId(loginInfo.getUserId());
	
						returnCnt =  plcyMgmtService.insertInstNom(listVOs);
						logger.debug("returnCnt = " + returnCnt);
					}
				}
				
				plcyMgmtService.updateEndSaleEventLog(policyLogVO, "할부개월수 저장 성공");
				
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			} catch ( Exception e)
			{
				resultMap.clear();
				plcyMgmtService.updateEndSaleEventLog(policyLogVO, "할부개월수 저장 에러");
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.DUP", null, Locale.getDefault()) );
		 		resultMap.put("msg", "할부개월수 저장 에러");
		 		throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		logger.debug("resultMap:" + resultMap);
		return "jsonView";
	}
	
}