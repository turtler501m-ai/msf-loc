package com.ktis.msp.org.prmtmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.prmtmgmt.service.DisPrmtMgmtService;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtVO;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @Class Name : DisPrmtMgmtController
 * @Description : 평생할인 정책 등록/관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2023.09.27 김동혁 최초생성
 * @
 * @author : 김동혁
 * @Create Date : 2023.09.27.
 */
@Controller
public class DisPrmtMgmtController extends BaseController {

	@Autowired
	private DisPrmtMgmtService disPrmtMgmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
		
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	public DisPrmtMgmtController() {
		setLogPrefix("[DisPrmtMgmtController] ");
	}

	/**
	 * @Description : 평생할인 정책 관리 화면
	 * @Author : 김동혁
	 * @Create Date : 2023.09.27.
	 */
	@RequestMapping(value = "/org/prmtMgmt/disPrmtMgmt.do", method={POST, GET})
	public ModelAndView disPrmtMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("평생할인 정책 관리 화면 START"));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("/org/prmtmgmt/msp_org_prmt_1005"); 
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 평생할인 정책 등록 화면
	 * @Author : 김동혁
	 * @Create Date : 2023.10.06.
	 */
	@RequestMapping(value="/org/prmtMgmt/getDisPrmtNew.do")
	public ModelAndView getDisPrmtNew(HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@ModelAttribute("searchVO") DisPrmtMgmtVO searchVO,
								@RequestParam Map<String, Object> commandMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("평생할인 정책 등록 화면 START"));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		DisPrmtMgmtVO resultVo = new DisPrmtMgmtVO();
		 
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			//본사화면일경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			if(KtisUtil.isNotEmpty(searchVO.getPrmtId())) {
				resultVo = disPrmtMgmtService.getDisPrmtDtlInfo(searchVO);
			}
            
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("result", resultVo);
			modelAndView.setViewName("/org/prmtmgmt/msp_org_prmt_1006");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 평생할인 정책 목록 조회
	 * @Param  : void
	 * @Return : String
	 * @Author : 김동혁
	 * @Create Date : 2023.09.27.
	 */
    @RequestMapping(value="/org/prmtMgmt/getDisPrmtList.json")
    public String getDisPrmtList(@ModelAttribute("searchVO") DisPrmtMgmtVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("평생할인 정책 목록 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            List<DisPrmtMgmtVO> resultList = disPrmtMgmtService.getDisPrmtList(searchVO);                    

			int totalCount = 0;
			
			if(resultList.size() > 0){				
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			            
        } catch (Exception e) {
        	resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP2002510", "프로모션관리")) {
				throw new MvnoErrorException(e);
			}
        }
       
        model.addAttribute("result", resultMap);
      
        return "jsonView";
    }
        
     /**
	 * 평생할인 조직 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getDisPrmtOrgnList.json")
	public String getDisPrmtOrgnList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") DisPrmtMgmtSubVO disPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			
			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault())); 
			}
			
			List<DisPrmtMgmtSubVO> resultList = disPrmtMgmtService.getDisPrmtOrgnList(disPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2002510", "프로모션관리")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 평생할인 요금제 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getDisPrmtSocList.json")
	public String getDisPrmtSocList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") DisPrmtMgmtSubVO disPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault())); 
			}
			
			
			List<DisPrmtMgmtSubVO> resultList = disPrmtMgmtService.getDisPrmtSocList(disPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
	
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2002610", "프로모션관리등록")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 평생할인 부가서비스 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getDisPrmtAddList.json")
	public String getDisPrmtAddList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") DisPrmtMgmtSubVO disPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault())); 
			}
			
			List<DisPrmtMgmtSubVO> resultList = disPrmtMgmtService.getDisPrmtAddList(disPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2002610", "프로모션관리등록")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}
	

	/**
	 * 평생할인 상세 조직 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getDisPrmtDtlOrgnList.json")
	public String getDisPrmtDtlOrgnList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") DisPrmtMgmtSubVO disPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault())); 
			}
			
			List<DisPrmtMgmtSubVO> resultList = disPrmtMgmtService.getDisPrmtDtlOrgnList(disPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2002610", "프로모션등록관리")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 평생할인 상세 요금제 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getDisPrmtDtlSocList.json")
	public String getDisPrmtDtlSocList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") DisPrmtMgmtSubVO disPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault())); 
			}
			
			List<DisPrmtMgmtSubVO> resultList = disPrmtMgmtService.getDisPrmtDtlSocList(disPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2002510", "프로모션관리")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}
		
	/**
	 * 평생할인 상세 부가서비스 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getDisPrmtDtlAddList.json")
	public String getDisPrmtDtlAddList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") DisPrmtMgmtSubVO disPrmtMgmtSubVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault())); 
			}
						
			List<DisPrmtMgmtSubVO> resultList = disPrmtMgmtService.getDisPrmtDtlAddList(disPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2002510", "프로모션관리")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		return "jsonView";
	}

    /**
	 * @Description : 평생할인 정책 단건등록
	 * @Author : 김동혁
	 * @Create Date : 2023.10.06.
	 */
    @RequestMapping(value="/org/prmtMgmt/regDisPrmtInfo.json")
	public String regDisPrmtInfo(@ModelAttribute("searchVO") DisPrmtMgmtVO searchVO, 
								@RequestBody String data,
								HttpServletRequest request,
								HttpServletResponse response,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
						
			DisPrmtMgmtVO vo = new ObjectMapper().readValue(data, DisPrmtMgmtVO.class);
			
			vo.setSessionUserId(searchVO.getSessionUserId());
			disPrmtMgmtService.regDisPrmtInfo(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
			//throw new MvnoErrorException(e);
		}
		
		modelMap.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
    
    /**
	 * @Description : List 대리점 추가	
	 */
    
	@RequestMapping("/org/prmtMgmt/setDisPrmtOrgAdd.json")
	public String setDisPrmtOrgAdd(@ModelAttribute("searchVO") DisPrmtMgmtVO searchVO,
			@RequestBody String data,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> paramMap) {
    	
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
						
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			JSONObject jSONObject = (JSONObject) new JSONParser().parse(data);
			
			if(KtisUtil.isNotEmpty(jSONObject)){
				searchVO.setPrmtIdList((List<String>) jSONObject.get("prmtIdList"));
				searchVO.setOrgnList((List<String>) jSONObject.get("orgnList"));
			}
			
			disPrmtMgmtService.setDisPrmtOrgAdd(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage()) ;
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}

	/**
	 * @Description : List 종료일 변경
	 */
	@RequestMapping("/org/prmtmgmt/updDisPrmtEndDttm.json")
   	public String updDisPrmtEndDttm(@ModelAttribute("searchVO") DisPrmtMgmtVO searchVO,
			@RequestBody String data,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> paramMap) {
    	
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
						
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			JSONObject jSONObject = (JSONObject) new JSONParser().parse(data);
			
			if(KtisUtil.isNotEmpty(jSONObject)){
				searchVO.setPrmtIdList((List<String>) jSONObject.get("prmtIdList"));
				searchVO.setEndDttmMod((String) jSONObject.get("endDttmMod"));
			}
			
			disPrmtMgmtService.updDisPrmtEndDttm(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage()) ;
		}

		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}  
    
    /**
	 *평생할인 프로모션 변경 
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/updDisPrmtByInfo.json")
	public String updDisPrmtByInfo(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute DisPrmtMgmtVO disPrmtMgmtVO,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(disPrmtMgmtVO);
			//본사화면일경우
			if(!"10".equals(disPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			if(KtisUtil.isEmpty(disPrmtMgmtVO.getTypeCd())) {
				throw new MvnoRunException(-1, "변경구분값이 없습니다.");
			}
			disPrmtMgmtService.updDisPrmtByInfo(disPrmtMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage()) ;
		}
		
		modelMap.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	
	/**
	 * 평생 할인 상세정보 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getDisPrmtDtlList.json")
	public String getDisPrmtDtlList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") DisPrmtMgmtVO disPrmtMgmtVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(disPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(disPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<DisPrmtMgmtVO> resultList = disPrmtMgmtService.getDisPrmtDtlList(disPrmtMgmtVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
		
		
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "조회에 실패하였습니다.", "MSP2002510", "프로모션관리")) {
				throw new MvnoErrorException(e);
			}
		}
		modelMap.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	/**
	* @Description : 상세정보 목록 엑셀 다운로드
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/org/prmtmgmt/getDisPrmtDtlListExcel.json")
	public String getDisPrmtDtlListExcel( @ModelAttribute("searchVO") DisPrmtMgmtVO disPrmtMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			List<DisPrmtMgmtVO> resultList = disPrmtMgmtService.getDisPrmtDtlListExcel(disPrmtMgmtVO, paramMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "평생할인상세정보_";//파일명
			String strSheetname = "평생할인상세정보";//시트명

			String [] strHead = { "프로모션ID","프로모션명"
								 ,"조직ID","조직명"
							     ,"요금제코드","요금제명"
							     ,"부가서비스코드","부가서비스명","기본료"
								};

			String [] strValue = { "prmtId","prmtNm"
								  ,"orgnId","orgnNm"
								  ,"rateCd","rateNm"
								  ,"soc","addNm","disAmt"
								};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 4000
							, 5000, 5000
							, 5000, 5000
							, 5000, 5000, 5000,
							};
			int[] intLen = {0, 0
						  , 0, 0
						  , 0, 0
						  , 0, 0, 0
						};

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

			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");

				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();

				paramMap.put("FILE_NM"	,file.getName());				//파일명
				paramMap.put("FILE_ROUT" ,file.getPath());				//파일경로
				paramMap.put("DUTY_NM"	,"INSR");						//업무명
				paramMap.put("IP_INFO"	,ipAddr);						//IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				paramMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				paramMap.put("DATA_CNT", 0);							//자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId());	//사용자ID

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




    /**
	 * @Description : 평생할인 엑셀 자료생성
	*/
	@RequestMapping("/org/prmtmgmt/getDisPrmtListExcelDown.json")
	public String getDisPrmtListExcelDown(@ModelAttribute("searchVO") DisPrmtMgmtVO searchVO,
					Model model,
					UserInfoMgmtVo userInfoMgmtVo,
					@RequestParam Map<String, Object> pRequestParamMap,
					HttpServletRequest request,
					HttpServletResponse response)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("평생할인 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [DisPrmtMgmtVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoRunException(-1, "엑셀 다운로드 권한 미충족");
			}

			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","DIS");
			excelMap.put("MENU_NM","프로모션관리(KOS 업무 처리 대상)");
			String searchVal = "기준일자:"+searchVO.getSearchBaseDate()+
					"|프로모션명:"+searchVO.getSearchPrmtNm()+
					"|채널유형:"+searchVO.getSearchChnlTp()+
					"|업무구분:"+searchVO.getSearchEvntCd()+
					"|가입유형:"+searchVO.getSearchSlsTp()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();


			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00212");
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

			vo.setExecParam("{\"searchBaseDate\":" + "\"" + searchVO.getSearchBaseDate() + "\""
						+ ",\"searchPrmtNm\":" + "\"" + searchVO.getSearchPrmtNm() + "\""
						+ ",\"searchChnlTp\":" + "\"" + searchVO.getSearchChnlTp() + "\""
						+ ",\"searchEvntCd\":" + "\"" + searchVO.getSearchEvntCd() + "\""
						+ ",\"searchSlsTp\":" + "\"" + searchVO.getSearchSlsTp() + "\""
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
	 * @Description : 평생할인 정책 엑셀 업로드 양식 다운로드
	 * @Param  : disPrmtMgmtVO
	 * @Param  : request
	 * @Param  : response
	 * @Param  : pRequestParamMap
	 * @Param  : model
	 * @Return : String
	 * @Author : 김동혁
	 * @Create Date : 2023.10.06.
	 */
	@RequestMapping(value="/org/prmtmgmt/getDisTempExcelDown.json")
	public String getDisTempExcelDown(@ModelAttribute("disPrmtMgmtVO") DisPrmtMgmtVO disPrmtMgmtVO,
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
			loginInfo.putSessionToVo(disPrmtMgmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(disPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = new ArrayList<DisPrmtMgmtVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "프로모션일괄등록엑셀양식_";//파일명
			String strSheetname = "프로모션일괄등록엑셀양식";//시트명
			
			String [] strHead = {"번호", "프로모션명", "시작일자","시작시간", "종료일자","종료시간",
								 "채널유형", "업무구분", "가입유형", "개통단말기", "대표모델ID",
								 "약정기간_무약정", "약정기간_12개월", "약정기간_18개월", "약정기간_24개월", "약정기간_30개월", "약정기간_36개월",
								 "대상조직", "대상요금제", "대상부가서비스"};
			String [] strValue = {"regNum", "prmtNm", "strtDt","strtTm","endDt","endTm",
								  "chnlTp", "evntCd", "slsTp", "intmYn", "modelId",
								  "enggCnt0", "enggCnt12", "enggCnt18", "enggCnt24", "enggCnt30", "enggCnt36",
								  "orgnId", "rateCd", "soc"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {1500, 8000, 3000, 3000, 3000, 3000,
							  3000, 3000, 3000, 4000, 4000, 
							  5000, 5000, 5000, 5000, 5000, 5000,
							  3000, 4000, 5000};
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
	 * @Description : 평생할인 정책 엑셀 업로드 파일 읽기
	 * @Param  : request
	 * @Param  : response
	 * @Param  : disPrmtMgmtVO
	 * @Param  : model
	 * @Param  : pReqParamMap
	 * @Return : String
	 * @Author : 김동혁
	 * @Create Date : 2023.10.06.
	 */
	@RequestMapping(value="/org/prmtmgmt/readDisExcelUpList.json")
	public String readDisExcelUpList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("disPrmtMgmtVO") DisPrmtMgmtVO disPrmtMgmtVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(disPrmtMgmtVO);
			
			if(!"10".equals(disPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + disPrmtMgmtVO.getFileName();
			
			String[] arrCell = {"regNum", "prmtNm", "strtDt","strtTm", "endDt","endTm", 
								"chnlTp", "evntCd", "slsTp", "intmYn", "modelId",
								"enggCnt0", "enggCnt12", "enggCnt18", "enggCnt24", "enggCnt30", "enggCnt36",
								"orgnId", "rateCd", "soc"};
						
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtVO", sOpenFileName, arrCell);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, uploadList, uploadList.size());
			
		
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView"; 
	}
	
	
	/**
	 * @Description : 평생할인 정책 엑셀등록 (List)
	 * @Param  : disPrmtMgmtVO
	 * @Param  : data
	 * @Param  : pRequest
	 * @Param  : pResponse
	 * @Param  : pRequestParamMap
	 * @Param  : model
	 * @Return : String
	 * @Author : 김동혁
	 * @Create Date : 2023.10.06.
	 */
	@RequestMapping(value="/org/prmtmgmt/regDisPrmtInfoExcel.json")
	public String regDisPrmtInfoExcel(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("disPrmtMgmtVO") DisPrmtMgmtVO disPrmtMgmtVO,
								@RequestBody String data,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(disPrmtMgmtVO);
			
			if(!"10".equals(disPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			DisPrmtMgmtVO vo = new ObjectMapper().readValue(data, DisPrmtMgmtVO.class);
			vo.setSessionUserId(disPrmtMgmtVO.getSessionUserId());
			
			int resultCnt = disPrmtMgmtService.regDisPrmtInfoExcel(vo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", resultCnt+"건 등록되었습니다");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		}
		model.addAttribute("result", resultMap);
		return "jsonView"; 
	}

	/**
	 * @Description : 평생할인 정책 목록 콤보 조회
	 * @Param : void
	 * @Return : String
	 * @Author : 김동혁
	 */
	@RequestMapping(value = "/org/prmtmgmt/getDisPrmtListCombo.json")
	public String getDisPrmtListCombo(@ModelAttribute("searchVO") DisPrmtMgmtVO searchVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("평생할인 정책 목록 콤보 조회 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			List<EgovMap> prmtList = disPrmtMgmtService.getDisPrmtListCombo(searchVO);

			if (prmtList == null || prmtList.size() == 0) {
				if ((!"".equals(searchVO.getModelId()) || searchVO.getModelId() != null)
						&& "MM".equals(searchVO.getReqBuyType())) {
					String modelId = searchVO.getModelId();
					searchVO.setModelId(null);
					prmtList = disPrmtMgmtService.getDisPrmtListCombo(searchVO);
					searchVO.setModelId(modelId);
				}
			}
			Map<String, String> prmtMap = new HashMap<String, String>();

			if (KtisUtil.isNotEmpty(prmtList)) {
				prmtMap.put("prmtId", (String) prmtList.get(0).get("prmtId"));
				prmtMap.put("prmtNm", (String) prmtList.get(0).get("prmtNm"));
				prmtMap.put("disCnt", (String) prmtList.get(0).get("disCnt"));
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("prmtMap", prmtMap);

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", e.getMessage());
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : List 프로모션 엑셀 다운로드
	 */
	@RequestMapping("/org/prmtmgmt/getDisChoChrgPrmtListExcelDown.json")
   	public String getDisChoChrgPrmtListExcelDown(@ModelAttribute("searchVO") DisPrmtMgmtVO searchVO,
			@RequestBody String data,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> paramMap) {
    	
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
						
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String prmtIdListParam = request.getParameter("prmtIdList");
			
			if (KtisUtil.isNotEmpty(prmtIdListParam)) {
			    List<String> prmtIdList = Arrays.asList(prmtIdListParam.split(","));
			    searchVO.setPrmtIdList(prmtIdList);
			}
			
			List<DisPrmtMgmtVO> resultList = disPrmtMgmtService.getDisChoChrgPrmtListExcelDown(searchVO);
			
			for(int i = 0; i < resultList.size(); i++){
				resultList.get(i).parseEngg();
			}

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "프로모션정책_";//파일명
			String strSheetname = "프로모션정책";//시트명

			String [] strHead = { 
								"순번",					"프로모션명",			"시작일자",				"시작시간",				"종료일자",
								"종료시간",				"채널유형",				"업무구분",				"가입유형",				"개통단말기",
								"대표모델ID",				"약정기간_무약정",		"약정기간_12개월",		"약정기간_18개월",		"약정기간_24개월",
								"약정기간_30개월",		"약정기간_36개월",		"대상조직",				"대상요금제",			"대상부가서비스"
								};

			String [] strValue = { 
								"rnum",			"prmtNm",		"strtDt",			"strtTm",			"endDt",			
								"endTm",			"chnlTp",			"evntCd",			"slsTp",			"intmYn",
								"modelId",		"enggCnt0",		"enggCnt12",	"enggCnt18",	"enggCnt24",
								"enggCnt30",	"enggCnt36",	"orgnId",			"rateCd",			"soc"
								};

			//엑셀 컬럼 사이즈
			int[] intWidth = {
							1500, 13000, 3000, 3000, 3000,
							3000, 3000, 3000, 3000, 5000, 
							5000, 5000, 5000, 5000, 5000,
							5000, 3000, 4000, 3000, 5000
							};
			int[] intLen = {
							0, 0, 0, 0, 0,
							0, 0, 0, 0, 0,
							0, 0, 0, 0, 0,
							0, 0, 0, 0, 0
							};

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
