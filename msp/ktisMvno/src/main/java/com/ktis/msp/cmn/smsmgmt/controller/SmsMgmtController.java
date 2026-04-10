package com.ktis.msp.cmn.smsmgmt.controller;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.fileup2.service.FileUp2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.smsmgmt.service.SmsMgmtService;
import com.ktis.msp.cmn.smsmgmt.vo.SmsPhoneGrpVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendResVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsTemplateReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsTemplateResVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryMgmtVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class SmsMgmtController extends BaseController {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private SmsMgmtService smsMgmtService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private FileUp2Service fileUp2Service;
	
	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
		
	/**
	 * SMS 템플릿 관리 화면
	 * @return "/cmn/smsmgmt/msp_cmn_sms_1001.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/smsmgmt/getSmsTemplate.do")
	public ModelAndView getSmsTemplate(@ModelAttribute("searchVO") SmsTemplateReqVO vo, 
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",vo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("cmn/smsmgmt/msp_cmn_sms_1001");
			
			return modelAndView;
			
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * SMS발송조회 화면
	 * @return "/cmn/smsmgmt/msp_cmn_sms_1002.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/smsmgmt/getSmsSend.do")
	public ModelAndView getSmsSend(@ModelAttribute("searchVO") SmsTemplateReqVO vo, 
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",vo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("cmn/smsmgmt/msp_cmn_sms_1002");
			
			return modelAndView;
			
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * SMS 템플릿 목록 조회
	 * @param vo
	 * @param request
	 * @param response
	 * @param pReqParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cmn/smsmgmt/getSmsTemplateList.json")
	public String getSmsTemplateList(@ModelAttribute SmsTemplateReqVO vo,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> pReqParamMap,
								ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SmsTemplateResVO> resultList = smsMgmtService.getSmsTemplateList(vo);
			
			int cnt = 0;
			if(resultList != null && resultList.size() > 0) {
				cnt = Integer.parseInt(resultList.get(0).getTotalCount());
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, cnt);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
		
	}
	
	/**
	 * SMS 템플릿 저장(등록,수정,이력등록)
	 * @param vo
	 * @param model
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/cmn/smsmgmt/saveSmsTemplate.json")
	public String saveSmsTemplate(@ModelAttribute SmsTemplateResVO vo,
								ModelMap model,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			/* 로그인정보체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			smsMgmtService.saveSmsTemplate(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
		
	}
	
	/**
	 * SMS발송조회
	 * @param vo
	 * @param request
	 * @param response
	 * @param pReqParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cmn/smsmgmt/getSmsSendList.json")
	public String getSmsSendList(@ModelAttribute SmsSendReqVO vo,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> pReqParamMap,
								ModelMap model) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = smsMgmtService.getSmsSendList(vo);

			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	@RequestMapping("/cmn/smsmgmt/setSmsRetry.json")
	public String setSmsRetry(@ModelAttribute("searchVO") SmsSendReqVO vo,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap,
								ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			smsMgmtService.setSmsSend(vo.getMsgId(),loginInfo.getUserId());
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg",  "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/cmn/smsmgmt/getTemplateCombo.json")
	public String getTemplateCombo(HttpServletRequest paramReq,
							HttpServletResponse paramRes,
							@ModelAttribute("cmnCdMgmtVo") CmnCdMgmtVo cmnCdMgmtVo,
							ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(cmnCdMgmtVo);
			
			List<?> resultList = smsMgmtService.getTemplateCombo(cmnCdMgmtVo);
			
			resultMap =  makeResultMultiRow(cmnCdMgmtVo, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, paramReq.getServletPath(), "", "", "", ""))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 요금제 combo 조회
	 */
	@RequestMapping("/cmn/smsmgmt/getRateCombo.json")
	public String getRateCombo(HttpServletRequest paramReq,
							HttpServletResponse paramRes,
							@ModelAttribute("cmnCdMgmtVo") CmnCdMgmtVo cmnCdMgmtVo,
							ModelMap model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(cmnCdMgmtVo);
			
			List<?> resultList = smsMgmtService.getRateCombo(cmnCdMgmtVo);
			
			resultMap =  makeResultMultiRow(cmnCdMgmtVo, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, paramReq.getServletPath(), "", "", "", ""))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 수신자선택발송
	 * @return "/cmn/smsmgmt/msp_cmn_sms_1003.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/smsmgmt/smsSendReceiveView.do")
	public ModelAndView smsSendReceiveView(@ModelAttribute("searchVO") SmsSendReqVO searchVO, 
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("cmn/smsmgmt/msp_cmn_sms_1003");
			
			return modelAndView;
			
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	
	/**
	 * SMS 수신자선택발송 조회
	 */
	@RequestMapping(value = "/cmn/smsmgmt/getSmsSendReceiveList.json")
	public String getSmsSendReceiveList(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") SmsSendReqVO searchVO
								, @RequestParam Map<String, Object> pReqParamMap
								, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			//본사화면일경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SmsSendResVO> resultList = smsMgmtService.getSmsSendReceiveList(searchVO, pReqParamMap);
			
			int cnt = 0;
			if(resultList != null && resultList.size() > 0) {
				cnt = Integer.parseInt(resultList.get(0).getTotalCount());
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, cnt);
		} catch (Exception e) {
			
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 수신자선택 SMS 발송
	 */
	@RequestMapping(value="/cmn/smsmgmt/saveSmsSendReceiveList.json")
	public String saveSmsSendReceiveList(HttpServletRequest request
										, HttpServletResponse response
										, @RequestBody String pJsonString
										, @ModelAttribute("searchVO") SmsSendResVO searchVO
										, @RequestParam Map<String, Object> pReqParamMap
										, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			//본사화면일경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SmsSendResVO> list = getVoFromMultiJson(pJsonString, "ALL", SmsSendResVO.class);
			
			//v2018.11 PMD 적용 소스 수정
			//int resultCnt = smsMgmtService.saveSmsSendReceiveList(list, searchVO);
			smsMgmtService.saveSmsSendReceiveList(list, searchVO);
			
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
	 * 템플릿조회
	 */
	@RequestMapping(value="/cmn/smsmgmt/getSendTemplate.json")
	public String getSendTemplate(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") SmsSendResVO searchVO
								, @RequestParam Map<String, Object> pReqParamMap
								, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			//본사화면일경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			SmsSendResVO resVO = smsMgmtService.getSendTemplate(searchVO);
			// 템플릿 내용
			resultMap.put("text", resVO.getText());
            resultMap.put("template", resVO);

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
	 * 수신자선택발송 엑셀양식
	 */
	@RequestMapping(value="/cmn/smsmgmt/getSmsSendXlsTemplate.json")
	public String getSmsSendXlsTemplate(@ModelAttribute("searchVO") SmsSendResVO searchVO,
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
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SmsSendResVO> resultList = new ArrayList<SmsSendResVO>();
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "SMS발송 수신자등록양식_";//파일명
			String strSheetname = "수신자목록";//시트명
			
			//엑셀 제목
			ArrayList<String[]> aryHead = new ArrayList<String[]>();
			String [] strtitle = {"SMS수신자목록"};
			aryHead.add(strtitle);
			String [] strHead = {"휴대폰번호"};
			aryHead.add(strHead);
			
			//셀병합
			ArrayList<int[]> aryMerged = new ArrayList<int[]>();
			int[] nMerged = {0,0,0,0};
			aryMerged.add(nMerged);
			String [] strValue = {};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000};
			int[] intLen = {};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelCellMergedDownProc(strFilename, strSheetname, resultList.iterator(), aryHead, aryMerged, intWidth, strValue, request, response, intLen);
			
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
	 * 수신자선택발송 엑셀업로드
	 */
	@RequestMapping(value="/cmn/smsmgmt/setSmsSendXlsUpload.do")
	public String setSmsSendXlsUpload(ModelMap model,
									HttpServletRequest pRequest,
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//--------------------------------------
			// return JSON 변수 선언
			//--------------------------------------
			
			String uploadFileName = fileUp2Service.fileUpLoad(pRequest, "file", "CMN" );
			
			resultMap.put("state", true);
			resultMap.put("name", uploadFileName);
			resultMap.put("size", "");
			
		} catch (Exception e) {
			resultMap.put("state", false);
			resultMap.put("name", "");
			resultMap.put("size", "");
			model.addAttribute("result", resultMap);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonViewArray";
	}
	
	/**
	 * 수신자선택발송 엑셀업로드 목록
	 */
	@RequestMapping(value="/cmn/smsmgmt/setSmsSendXlsDataList.json")
	public String setSmsSendXlsDataList(HttpServletRequest request,
										HttpServletResponse response,
										@ModelAttribute("searchVO") SmsSendResVO searchVO,
										ModelMap model,
										@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();
			String[] arrCell = {"mobileNo"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.cmn.smsmgmt.vo.SmsSendResVO", sOpenFileName, arrCell, 2);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, uploadList, uploadList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	/**
	 * 
	 */
	@RequestMapping("/cmn/smsmgmt/setSmsSendXlsDataComplete.json")
	public String setSmsSendXlsDataComplete(@ModelAttribute("searchVO") SmsSendResVO searchVO, 
											@RequestBody String data,
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse,
											@RequestParam Map<String, Object> pRequestParamMap,
											ModelMap model){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			logger.debug("data=" + data);
			
			SmsSendResVO voList = new ObjectMapper().readValue(data, SmsSendResVO.class);
			
			logger.debug("sendType=" + voList.getSendType());
			logger.debug("requestTime=" + voList.getRequestTime());
			logger.debug("templateId=" + voList.getTemplateId());
			
			searchVO.setSendType(voList.getSendType());
			if("R".equals(searchVO.getSendType())) {
				searchVO.setRequestTime(voList.getRequestTime());
			}else {
				searchVO.setRequestTime("");
			}
			searchVO.setTemplateId(voList.getTemplateId());
			
			int resultCnt = smsMgmtService.setSmsSendXlsDataComplete(voList, searchVO);
			
			if ( resultCnt == 0 ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "변경대상이 없습니다.");
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}

	
	/**
	 * 전화번호그룹관리
	 * @return "/cmn/smsmgmt/msp_cmn_sms_1004.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/smsmgmt/smsSendPhoneGrpView.do")
	public ModelAndView smsSendPhoneGrpView(@ModelAttribute("searchVO") SmsSendReqVO searchVO, 
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("cmn/smsmgmt/msp_cmn_sms_1004");
			
			return modelAndView;
			
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 전화번호그룹 목록
	 */
	@RequestMapping(value = "/cmn/smsmgmt/smsSendPhoneGrpList.json")
	public String  smsSendPhoneGrpList(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  SmsMgmtController.smsSendPhoneGrpList -- 전화번호그룹 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  smsMgmtService.smsSendPhoneGrpList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		} catch ( Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 전화번호그룹 insert
	 */
	@RequestMapping(value = "/cmn/smsmgmt/insertSmsSendPhoneGrp.json")
	public String  insertSmsSendPhoneGrp(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  SmsMgmtController.insertSmsSendPhoneGrp -- 전화번호그룹 insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = smsMgmtService.insertSmsSendPhoneGrp(pRequestParamMap);
			
			if(returnCnt > 0) {
				pRequestParamMap.put("operType", "I");
				smsMgmtService.insertSmsSendPhoneGrpHst(pRequestParamMap);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch ( Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 전화번호그룹 update
	 */
	@RequestMapping(value = "/cmn/smsmgmt/updateSmsSendPhoneGrp.json")
	public String  updateSmsSendPhoneGrp(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  SmsMgmtController.updateSmsSendPhoneGrp -- 전화번호그룹 Update ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  smsMgmtService.updateSmsSendPhoneGrp(pRequestParamMap);
			if ( updateCnt == 0 ) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			} else {
				pRequestParamMap.put("operType", "U");
				smsMgmtService.insertSmsSendPhoneGrpHst(pRequestParamMap);
				
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		} catch ( Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 전화그룹상세 목록
	 */
	@RequestMapping(value = "/cmn/smsmgmt/smsSendPhoneGrpDtl.json")
	public String  smsSendPhoneGrpDtl(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  SmsMgmtController.smsSendPhoneGrpDtl -- 전화그룹상세 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  smsMgmtService.smsSendPhoneGrpDtl(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	/**
	 * 전화그룹상세 insert
	 */
	@RequestMapping(value = "/cmn/smsmgmt/insertSmsSendPhoneGrpDtl.json")
	public String  insertSmsSendPhoneGrpDtl(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  SmsMgmtController.insertSmsSendPhoneGrpDtl -- 전화그룹상세 insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = 0;
			if("Y".equals(pRequestParamMap.get("etc"))){
				returnCnt = smsMgmtService.insertSmsSendPhoneGrpDtlEtc(pRequestParamMap);
			} else {
				returnCnt = smsMgmtService.insertSmsSendPhoneGrpDtl(pRequestParamMap);				
			}
			
			if(returnCnt > 0){
				pRequestParamMap.put("operType", "I");
				if("Y".equals(pRequestParamMap.get("etc"))){
					smsMgmtService.insertSmsSendPhoneGrpDtlHstEtc(pRequestParamMap);
				} else {
					smsMgmtService.insertSmsSendPhoneGrpDtlHst(pRequestParamMap);			
				}
			}
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch ( Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * 전화번호그룹상세 delete
	 */
	@RequestMapping(value = "/cmn/smsmgmt/deleteSmsSendPhoneGrpDtl.json")
	public String  deleteSmsSendPhoneGrpDtl(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  SmsMgmtController.deleteSmsSendPhoneGrpDtl -- 전화번호그룹상세 Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			int updateCnt = 0;
			
			updateCnt =  smsMgmtService.deleteSmsSendPhoneGrpDtl(pRequestParamMap);		
			
			if ( updateCnt == 0 ) {
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			} else {
				if(updateCnt > 0) {
					pRequestParamMap.put("operType", "D");
					/** 20230518 PMD 조치 */
					if("".equals(pRequestParamMap.get("usrId")) || pRequestParamMap.get("usrId") == null){
						smsMgmtService.insertSmsSendPhoneGrpDtlHstEtc(pRequestParamMap);
					} else {
						smsMgmtService.insertSmsSendPhoneGrpDtlHst(pRequestParamMap);
					}
				}
				
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		} catch ( Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	

	/**
	 * 전화번호 그룹 리스트 가져오기
	 * return 01012345678,01022225555 ....
	 */
	@RequestMapping("/cmn/smsmgmt/getSmsPhoneGrpDtlList.json")
	public String getSmsPhoneGrpDtlList(@RequestBody String pJsonString,
								@ModelAttribute("searchVO") SmsPhoneGrpVO searchVO,
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
			
			List<?> list = getVoFromMultiJson(pJsonString, "ALL", SmsPhoneGrpVO.class);
			logger.debug("list=" + list);
			logger.debug("size()=" + list.size());
			
			String phoneList = smsMgmtService.getSmsPhoneGrpDtlList(list);
			
			resultMap.put("phoneList",phoneList);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/***************************************************************************************/
	/* 대량문자 발송 2022.06.27 추가 */
	/***************************************************************************************/
	
	/**
	 * 대량문자 발송 화면
	 * @return "/cmn/smsmgmt/msp_cmn_sms_1005.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/smsmgmt/msgSmsSend.do")
	public ModelAndView msgSmsSend(@ModelAttribute("searchVO") SmsSendReqVO searchVO, 
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			
			modelAndView.getModelMap().addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			modelAndView.getModelMap().addAttribute("srchEndDt", orgCommonService.getToDay());
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("cmn/smsmgmt/msp_cmn_sms_1005");
			
			return modelAndView;
			
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	* @Description : 대량문자발송 템플릿 엑셀 업로드 양식 다운로드
	* @Param  : 
	* @Return : String
	*/
	@RequestMapping(value="/cmn/smsmgmt/getSmsTmpExcelDown.json")
	public String getSmsTmpExcelDown(@ModelAttribute("usimDlvryMgmtVO") UsimDlvryMgmtVO searchVO,
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
			
			List<?> resultList = new ArrayList<UsimDlvryMgmtVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "템플릿엑셀업로드양식_";//파일명
			String strSheetname = "템플릿엑셀업로드양식";//시트명
			
			String [] strHead = {"수신번호"};
			String [] strValue = {"msgRecvCtn"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000};
			int[] intLen = {};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
			
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
	* @Description : 대량문자발송 대량 엑셀 업로드 양식 다운로드
	* @Param  : 
	* @Return : String
	*/
	@RequestMapping(value="/cmn/smsmgmt/getSmsSendExcelDown.json")
	public String getSmsSendExcelDown(@ModelAttribute("usimDlvryMgmtVO") UsimDlvryMgmtVO searchVO,
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
			
			List<?> resultList = new ArrayList<UsimDlvryMgmtVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "MMS엑셀업로드양식_";//파일명
			String strSheetname = "MMS엑셀업로드양식";//시트명
			
			String [] strHead = {"발신번호","수신번호","제목","내용"};
			String [] strValue = {"msgSendCtn","msgRecvCtn","msgTitle","msgText"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000,5000,10000,20000};
			int[] intLen = {};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
			
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
	 * @Description : 대량문자발송 조회
	 * @Return : String
	 * @Author : 박효진
	 * @Create Date : 2022. 06. 29.
	 */
	@RequestMapping(value = "/cmn/smsmgmt/getMsgSmsSendList.json",method = { POST, GET })
	public String getMsgSmsSendList(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") SmsSendResVO searchVO
								, @RequestParam Map<String, Object> pReqParamMap
								, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			//본사화면일경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = smsMgmtService.getMsgSmsSendList(searchVO, pReqParamMap);
		
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
		} catch (Exception e) {
			
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 대량문자발송 상세 조회
	 * @Return : String
	 * @Author : 박효진
	 * @Create Date : 2022. 06. 29.
	 */
	
	@RequestMapping(value = "/cmn/smsmgmt/getMsgSmsSendListDt.json")
	public String getMsgSmsSendListDt(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") SmsSendResVO searchVO
								, @RequestParam Map<String, Object> pReqParamMap
								, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			//본사화면일경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = smsMgmtService.getMsgSmsSendListDt(searchVO, pReqParamMap);
		
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
		} catch (Exception e) {
			
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	/**
	 * @Description : 대량문자발송 리스트 삭제
	 * @Return : String
	 * @Author : 박효진
	 * @Create Date : 2022. 07. 05
	 */
	@RequestMapping("/cmn/smsmgmt/deleteMsgSmsList.json")
	public String deleteMsgSmsList(HttpServletRequest request,
			HttpServletResponse response,
			SmsSendResVO smsSendResVo,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap,
			SessionStatus status)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(smsSendResVo);
			
			smsMgmtService.deleteMsgSmsList(smsSendResVo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
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
	 * @Description : 대량문자 템플릿등록
	 * @Return : String
	 * @Author : 박효진
	 * @Create Date : 2022. 07. 04
	 */
	
	@RequestMapping("/cmn/smsmgmt/insertMsgSmsTmp.json" )
	public String insertMsgSmsTmp(@ModelAttribute("searchVO") SmsSendResVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			String resultMsg = null;
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");	
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();		
			String[] arrCell = {"msgRecvCtn"};
			
			/* MSG_NO 가져오기 */
			String  msgSmsListMsgNo = smsMgmtService.msgSmsListMsgNo(searchVO,pRequestParamMap);
			searchVO.setMsgNo(msgSmsListMsgNo);	
			searchVO.setRegstId(loginInfo.getUserId());
		
			/* 예약전송시간체크 전송하려는 시간의 최소30분 전에 등록 체크 */
			resultMap = smsMgmtService.reqTimeCountChk(pRequestParamMap);
			
			if(resultMap == null || resultMap.isEmpty()) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMsg = "예약전송시간을 입력해주세요.";			
			}else {			
				int	overLap = Integer.parseInt(String.valueOf(resultMap.get("overLap")));
				int chkYn = Integer.parseInt(String.valueOf(resultMap.get("chkYn")));
				if(chkYn > 0 ) {
					resultMap.clear();
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMsg = "이미 지난 발송날짜이거나  예약하고싶은 시간의 최소30분전 등록완료해주세요";
				}else if(overLap > 0) {
					resultMap.clear();
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMsg = "이미 예약된 전송날짜입니다. 다른날짜로 변경해주세요";
				}else {
					/* 엑셀업로드가 된 데이터를 가져와서 리스트로 만든다 */
					List<Object> uploadList =
							ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.cmn.smsmgmt.vo.SmsSendResVO", sOpenFileName, arrCell);
					
					if (uploadList.size() > 20000 ) {
						resultMap.clear();
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
						resultMsg = "엑셀업로드는 2만건 이하로 등록해주세요.";
					}else {
						resultMap.clear();
						/* MSG_SMS_SEND_LIST에  MSG_NO랑 MSG_RECV_CTN 인서트 [ 엑셀업로드 ]  */
						resultMap = smsMgmtService.smsTmpRecvOk(uploadList, searchVO);
						/* 상세테이블에 먼저 인서트하고나서 메인테이블에 인서트 */
						smsMgmtService.insertMsgSmsTmp(searchVO);
						
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
						resultMap.put("msg", "");
					}
				}
			}
			resultMap.put("msg", resultMsg);
					
 			model.clear();
			model.addAttribute("result", resultMap);
		
		} catch (Exception e) {
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP3000016", "대량문자발송")) {
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
	 * @Description : 대량문자 등록
	 * @Return : String
	 * @Author : 박효진
	 * @Create Date : 2022. 07. 04
	 */
	
	@RequestMapping("/cmn/smsmgmt/insertMsgSmsSend.json")
	public String insertMsgSmsSend(@ModelAttribute("searchVO") SmsSendResVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			String resultMsg = null;
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();			
			String[] arrCell = {"msgSendCtn","msgRecvCtn","msgTitle","msgText"};
					
			/* MSG_NO 가져오기 */
			String  msgSmsListMsgNo = smsMgmtService.msgSmsListMsgNo(searchVO,pRequestParamMap);		
			searchVO.setMsgNo(msgSmsListMsgNo);
			searchVO.setRegstId(loginInfo.getUserId());

			resultMap = smsMgmtService.reqTimeCountChk(pRequestParamMap);
			
			if(resultMap == null || resultMap.isEmpty()) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMsg = "예약전송시간을 입력해주세요.";			
			}else {			
				int	overLap = Integer.parseInt(String.valueOf(resultMap.get("overLap")));
				int chkYn = Integer.parseInt(String.valueOf(resultMap.get("chkYn")));
				if(chkYn > 0 ) {
					resultMap.clear();
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMsg = "이미 지난 발송날짜이거나  예약하고싶은 시간의 최소30분전 등록완료해주세요";
				}else if(overLap > 0) {
					resultMap.clear();
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMsg = "이미 예약된 전송날짜입니다. 다른날짜로 변경해주세요";
				}else {		
					/* 엑셀업로드가 된 데이터를 가져와서 리스트로 만든다 */
					List<Object> uploadList =
							ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.cmn.smsmgmt.vo.SmsSendResVO", sOpenFileName, arrCell);
					
					/* 엑셍업로드 2만건 초과할 시 */
					if (uploadList.size() > 20000 ) {
						resultMap.clear();
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
						resultMsg = "엑셀업로드는 2만건 이하로 등록해주세요.";
					}else {
						resultMap.clear();
						/* MSG_SMS_SEND_LIST에  MSG_NO , MSG_SEND_CTN , MSG_RECV_CTN , MSG_TITLE , MSG_TEXT  인서트 [ 엑셀업로드 ]  */
						resultMap = smsMgmtService.smsSendRecvOk(uploadList,searchVO);				
						
						smsMgmtService.insertMsgSmsSend(searchVO);
						 
			 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			 			resultMap.put("msg", "");
					}
				}
			}
			resultMap.put("msg", resultMsg);		
			
 			model.clear();
			model.addAttribute("result", resultMap);
		
		} catch (Exception e) {
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP3000016", "대량문자발송")) {
				throw new MvnoErrorException(e);
			}
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
		
}
