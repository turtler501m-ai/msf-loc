package com.ktis.msp.rcp.mrktMgmt.controller;

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

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;
import com.ktis.msp.rcp.mrktMgmt.service.MrktMgmtService;
import com.ktis.msp.rcp.mrktMgmt.vo.MrktMgmtVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
@Controller
public class MrktMgmtController extends BaseController  {

	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private MrktMgmtService mrktMgmtService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private FileDownService fileDownService;
	
	/**
	 * @Description : 마케팅동의 SMS 발송이력 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/msp_rcp_mrkt_1001.do")
	public ModelAndView msp_rcp_mrkt_1001(@ModelAttribute("searchVO") MrktMgmtVO searchVO, 
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
			modelAndView.setViewName("rcp/mrktMgmt/msp_rcp_mrkt_1001");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
		
	/**
	 * @Description : 마케팅동의 SMS 발송이력 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/getMarketingSmsSendList.json")
	public String getMarketingSmsSendList(@ModelAttribute("searchVO") MrktMgmtVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("마케팅동의 SMS 발송이력 조회 START."));
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
			
			List<?> resultList = mrktMgmtService.getMarketingSmsSendList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : 마케팅동의 SMS 발송이력(과거) 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/msp_rcp_mrkt_1001Old.do")
	public ModelAndView msp_rcp_mrkt_1001Old(@ModelAttribute("searchVO") MrktMgmtVO searchVO, 
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
			modelAndView.setViewName("rcp/mrktMgmt/msp_rcp_mrkt_1001Old");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
		
	/**
	 * @Description : 마케팅동의 SMS 발송이력(과거) 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/getMarketingSmsSendListOld.json")
	public String getMarketingSmsSendListOld(@ModelAttribute("searchVO") MrktMgmtVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("마케팅동의 SMS 발송이력 조회 START."));
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
			
			List<?> resultList = mrktMgmtService.getMarketingSmsSendListOld(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * @Description : 마케팅동의조회 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/msp_rcp_mrkt_1002.do")
	public ModelAndView msp_rcp_mrkt_1002(@ModelAttribute("searchVO") MrktMgmtVO searchVO, 
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
			modelAndView.setViewName("rcp/mrktMgmt/msp_rcp_mrkt_1002");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 마케팅동의조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/getMarketingHistoryList.json")
	public String getMarketingHistoryList(@ModelAttribute("searchVO") MrktMgmtVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("마케팅동의조회 START."));
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
			
			List<?> resultList = mrktMgmtService.getMarketingHistoryList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	

	/**
	 * 마케팅동의 등록 양식다운
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/rcp/mrktMgmt/getMarketingHistoryTempExcelDown.json")
	public String getMarketingHistoryTempExcelDown(@ModelAttribute("mrktMgmtVO") MrktMgmtVO searchVO,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam Map<String, Object> pRequestParamMap,
												ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = new ArrayList<MrktMgmtVO>();
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "마케팅동의등록엑셀양식_";//파일명
			String strSheetname = "마케팅동의등록엑셀양식";//시트명
			
			String [] strHead = {"계약번호", "고객 혜택 제공을 위한 개인정보 수집 및 관련 동의", "개인정보 처리 위탁 및 혜택 제공 동의" , "혜택 제공을 위한 제 3자 제공 동의 : M모바일", "혜택 제공을 위한 제 3자 제공 동의 : KT", "제 3자 제공관련 광고 수신 동의"};
			String [] strValue = {};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 14000, 15000, 13000, 12000, 9000};
			int[] intLen = {};
			
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

	/**
	 * @Description : 마케팅동의등록 업로드 목록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/regMarketingUpList.json")
	public String regParComExcelUpList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("mrktMgmtVO") MrktMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pRequestParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();
			
			String[] arrCell = {"contractNum", "personalInfoCollectAgree", "agrYn", "othersTrnsAgree", "othersTrnsKtAgree", "othersAdReceiveAgree"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.mrktMgmt.vo.MrktMgmtVO", sOpenFileName, arrCell);
			
			try {
                for (Object uploadObject : uploadList) {
                    MrktMgmtVO vo = (MrktMgmtVO) uploadObject;
                    mrktMgmtService.validateMrktAgrInfo(vo);
                }

                resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, uploadList, uploadList.size());
            } catch (EgovBizException e) {
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
                resultMap.put("msg", e.getMessage());
			}
        } catch (Exception e) {
            logger.error("Connection Exception occurred");
            resultMap.clear();
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
	}	
	
	
	/**
	 * @Description : 마케팅동의등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/regMarketingHistoryList.json")
	public String regMarketingHistoryList(@ModelAttribute("searchVO") MrktMgmtVO searchVO, 
					@RequestBody String data,
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("마케팅동의등록 START."));
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
			MrktMgmtVO vo = new ObjectMapper().readValue(data, MrktMgmtVO.class);
			
			mrktMgmtService.regMarketingHistoryList(vo, loginInfo.getUserId());

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}			

	/**
	 * @Description : 마케팅동의수정
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/mrktMgmt/modMarketingHistory.json")
	public String modMarketingHistory(@ModelAttribute("searchVO") MrktMgmtVO searchVO, 
			@RequestBody String data,
			HttpServletRequest request, 
			HttpServletResponse response, 
			ModelMap model, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("마케팅동의수정 START."));
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
			
			mrktMgmtService.modMarketingHistory(searchVO, loginInfo.getUserId());
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}			
	
}
