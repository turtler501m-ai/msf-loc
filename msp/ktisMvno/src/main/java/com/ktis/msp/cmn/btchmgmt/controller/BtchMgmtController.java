package com.ktis.msp.cmn.btchmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.btchmgmt.vo.JuiceBtchLogMgmtVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
public class BtchMgmtController extends BaseController {
	
	@Autowired
	private BtchMgmtService btchMgmtService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	/**
	 * JUICE BATCH Log 관리
	 * @return ModelAndView
	 * @author KYM
	 * @version 1.0
	 * @created 2016.06.07
	 * @updated
	 */
	@RequestMapping(value="/cms/batch/getJuiceBtchLogMgmt.do", method={POST, GET})
	public ModelAndView getCmnJuiceBtchLogList(@ModelAttribute("searchVO") JuiceBtchLogMgmtVO vo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
			
			modelAndView.setViewName("cmn/btchmgmt/juiceBtchMgmt");
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoErrorException(e);
		}
	}
	
	/**
	 * JUICE BATCH ID 조회
	 * @param searchVO
	 * @param model
	 * @return jsonData
	 * @throws Exception
	 */
	@RequestMapping("/cms/batch/getJuiceBtchIdList.json")
	public String getJuiceBtchItmAJax(HttpServletRequest paramReq, HttpServletResponse paramRes, @ModelAttribute("JuiceBtchLogMgmtVO") JuiceBtchLogMgmtVO juicebtchlogmgmtvo, ModelMap model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(juicebtchlogmgmtvo);
			
			// 본사 화면인 경우
			if(!"10".equals(juicebtchlogmgmtvo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = btchMgmtService.getJuiceBtchIdList();
			
			resultMap =  makeResultMultiRow(juicebtchlogmgmtvo, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * JUICE BATCH Log 조회
	 * @param vo - 조회할 정보가 담긴 ClsAcntMgmtVO
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/cms/batch/getJuiceBtchLogListAjax.json")
	public String getCmnJuiceBtchLogListAjax(@ModelAttribute("searchVO") JuiceBtchLogMgmtVO vo, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pReqParamMap, ModelMap model) {
		logger.debug(">>>> BtchMgmtController.getCmnJuiceBtchLogListAjax");
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList = btchMgmtService.getCmnJuiceBtchLogList(vo);
			resultMap =  makeResultMultiRow(vo, resultList);
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	
	/*
	 * 배치 요청 저장
	 */
	@RequestMapping("/cmn/btchmgmt/saveBatchRequest.json")
	public String saveBatchRequest(@ModelAttribute BatchJobVO vo, 
								ModelMap model,
								HttpServletRequest request, 
								HttpServletResponse response, 
								@RequestParam Map<String, Object> paramMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			/* 로그인정보체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			vo.setExecTypeCd("REQ");
			
			if("Y".equals(vo.getLogYn())){
				// 다운로드 생성
				int exclDnldId = fileDownService.getSqCmnExclDnldHst();
				
				vo.setExclDnldId(exclDnldId);
				
				Map<String, Object> execParam = null;
				if(vo != null && vo.getExecParam() != null) {
					execParam = makeMapFromJson(vo.getExecParam());
				}
				
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");
			   
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
				
				if(execParam != null) {
					execParam.put("ipAddr", ipAddr);
					execParam.put("exclDnldId", exclDnldId);
					
					vo.setExecParam(makeJsonFromMap(execParam));
				} else {
					//v2018.11 PMD 적용 소스 수정
				   vo.setExecParam("{\"sHghrOrgnCd\":" + "\"" + ((paramMap.get("s_hghrOrgnCd") != null) ? paramMap.get("s_hghrOrgnCd").toString() : "") + "\""
							+ ",\"sCmsnCd\":" + "\"" + ((paramMap.get("s_cmsnCd") != null) ? paramMap.get("s_cmsnCd").toString() : "") + "\"" 
							+ ",\"sSrvcCntrNum\":" + "\"" + ((paramMap.get("s_srvcCntrNum") != null) ? paramMap.get("s_srvcCntrNum").toString() : "") + "\"" 
							+ ",\"sPrvdMon\":" + "\"" + ((paramMap.get("s_prvdMon") != null) ? paramMap.get("s_prvdMon").toString() : "") + "\"" 
							+ ",\"sCmsnPlcyInfoSrlNum\":" + "\"" + ((paramMap.get("s_cmsnPlcyInfoSrlNum") != null) ? paramMap.get("s_cmsnPlcyInfoSrlNum").toString() : "") + "\"" 
							+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"" 
							+ ",\"menuId\":" + "\"" + vo.getMenuId() + "\"" 
							+ ",\"ipAddr\":" + "\"" + ipAddr + "\""
							+ ",\"dwnldRsn\":" + "\"" + ((paramMap.get("s_dwnldRsn") != null) ? paramMap.get("s_dwnldRsn").toString() : "") + "\""
							+ ",\"userId\":" + "\"" + vo.getSessionUserId() + "\"}"  
						);
				}
				String searchVal = "지급년월:"+((paramMap.get("s_prvdMon") != null) ? paramMap.get("s_prvdMon").toString() : "")+
						"|조직ID:"+((paramMap.get("s_hghrOrgnCd") != null) ? paramMap.get("s_hghrOrgnCd").toString() : "")+
						"|수수료명:"+((paramMap.get("s_cmsnCd") != null) ? paramMap.get("s_cmsnCd").toString() : "")+
						"|계약번호:"+((paramMap.get("s_srvcCntrNum") != null) ? paramMap.get("s_srvcCntrNum").toString() : "")+
						"|정책명:"+((paramMap.get("s_cmsnPlcyInfoSrlNum") != null) ? paramMap.get("s_cmsnPlcyInfoSrlNum").toString() : "")
						;
				vo.setSearchVal(searchVal);
				
				fileDownService.insertCmnExclDnldHstVO(vo);
			}
			
			btchMgmtService.insertBatchRequest(vo);
			
			
			
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
	
	/*
	 * 재실행 배치 요청 저장
	 */
	@RequestMapping("/cmn/btchmgmt/retryBatchRequest.json")
	public String retryBatchRequest(@ModelAttribute BatchJobVO vo,
								ModelMap model,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			/* 로그인정보체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			vo.setExecTypeCd("RETRY");
			btchMgmtService.insertBatchRequest(vo);

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
	 * 배치정보
	 * @return "/cmn/btchmgmt/msp_cmn_batch_1001.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/batch/batchInfo.do")
	public ModelAndView batchInfo(@ModelAttribute BatchJobVO vo,
								ModelMap model,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			modelAndView.setViewName("cmn/btchmgmt/msp_cmn_batch_1001");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
	}
	
	/**
	 * 배치실행이력
	 * @return "/cmn/btchmgmt/msp_cmn_batch_1002.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/batch/batchExecHst.do")
	public ModelAndView batchExecHst(@ModelAttribute BatchJobVO vo,
								ModelMap model,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			modelAndView.setViewName("cmn/btchmgmt/msp_cmn_batch_1002");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
	}
	
	/**
	 * 배치정보조회
	 * @param vo
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/batch/getBatchInfo.json")
	public String getBatchInfo(@ModelAttribute BatchJobVO vo,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> pReqParamMap,
								ModelMap model)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = btchMgmtService.getBatchInfo(vo);
			
			resultMap =  makeResultMultiRow(vo, resultList);
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
	 * 배치정보저장
	 */
	@RequestMapping("/cmn/batch/savBatchInfo.json")
	public String savBatchInfo(@ModelAttribute BatchJobVO vo,
								ModelMap model,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			/* 로그인정보체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			btchMgmtService.savBatchInfo(vo);
			
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
	 * 배치실행이력조회
	 * @param vo
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/batch/getBatchExecHst.json")
	public String getBatchExecHst(@ModelAttribute BatchJobVO vo,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> pReqParamMap,
								ModelMap model)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = btchMgmtService.getBatchExecHst(vo);
			
			resultMap =  makeResultMultiRow(vo, resultList);
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
	
	public final static Map<String, Object> makeMapFromJson(String pJsonString) throws EgovBizException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		
		JsonFactory f = new JsonFactory();
		JsonParser p;
		
		String[] errParam = new String[2];
		errParam[0] = "createJsonParser";
		errParam[1] = pJsonString;
		
		try {
			p = f.createJsonParser( pJsonString );
		} catch (JsonParseException e) {
			throw new EgovBizException("makeMapFromJson - JsonParseException");
			
		} catch (IOException e) {
			throw new EgovBizException("makeMapFromJson - IOException");
		}
		Map<String, Object> items;
		
		errParam[0] = "readValue";
		try {
			items = mapper.readValue( p , new TypeReference<Map<String, Object>>() { });
		} catch (JsonParseException e) {
			throw new EgovBizException("makeMapFromJson - JsonParseException");
		} catch (JsonMappingException e) {
			throw new EgovBizException("makeMapFromJson - JsonMappingException");
		} catch (IOException e) {
			throw new EgovBizException("makeMapFromJson - IOException");
		}finally {
			/** 20230518 PMD 조치 */
			p.close();
		}
		
		return items;
		
	}
	
	public final static String makeJsonFromMap(Map<String, Object> map) throws EgovBizException {
		
		ObjectMapper mapper = new ObjectMapper();
		String resultJson = "";
		
		String[] errParam = new String[1];
		errParam[0] = map.toString();
		
		try {
			resultJson = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			throw new EgovBizException("makeJsonFromMap - JsonGenerationException");
		} catch (JsonMappingException e) {
			throw new EgovBizException("makeJsonFromMap - JsonMappingException");
		} catch (IOException e) {
			throw new EgovBizException("makeJsonFromMap - IOException");
		}
		
		return resultJson;
	}
	
   /*
    * 배치 요청 저장2
    */
   @RequestMapping("/cmn/btchmgmt/saveBatchRequest2.json")
   public String saveBatchRequest2(@ModelAttribute BatchJobVO vo, 
                        ModelMap model,
                        HttpServletRequest request, 
                        HttpServletResponse response, 
                        @RequestParam Map<String, Object> paramMap)
   {
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         
         /* 로그인정보체크 */
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(vo);
         
         // 본사 화면인 경우
         if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         vo.setExecTypeCd("REQ");
         
         // 다운로드 생성
         int exclDnldId = fileDownService.getSqCmnExclDnldHst();
         
         vo.setExclDnldId(exclDnldId);
         vo.setOrgnId(loginInfo.getUserOrgnId());
         vo.setUsrNm(loginInfo.getUserName());
         vo.setUsrId(vo.getSessionUserId());
         
         Map<String, Object> execParam = null;
         if(vo != null && vo.getExecParam() != null) {
            execParam = makeMapFromJson(vo.getExecParam());
         }
         
         String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
         
         if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
            ipAddr = request.getHeader("REMOTE_ADDR");
         
         if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
            ipAddr = request.getRemoteAddr();
         
         if(execParam != null) {
            execParam.put("ipAddr", ipAddr);
            execParam.put("exclDnldId", exclDnldId);
            execParam.put("userId", vo.getSessionUserId());
         
            vo.setExecParam(makeJsonFromMap(execParam));
         }
         
         fileDownService.insertCmnExclDnldHstVO(vo);
            
         btchMgmtService.insertBatchRequest(vo);
         
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
}
