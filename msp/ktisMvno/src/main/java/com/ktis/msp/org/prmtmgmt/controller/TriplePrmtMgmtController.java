package com.ktis.msp.org.prmtmgmt.controller;

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

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.service.TriplePrmtMgmtService;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.TriplePrmtMgmtVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class TriplePrmtMgmtController extends BaseController {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private TriplePrmtMgmtService triplePrmtMgmtService;
	
	@Autowired
	private FileDownService fileDownService;
	
	/**
	 * 트리플혜택관리
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/getTriplePrmtMgmtView.do")
	public ModelAndView getTriplePrmtMgmtView(HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> commandMap,
								ModelMap model) throws EgovBizException {
		
		logger.debug("**********************************************************");
		logger.debug("* 트리플혜택관리 : msp/org/prmtmgmt/msp_org_prmt_1007 *");
		logger.debug("**********************************************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			//본사화면일경우
			if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.setViewName("/org/prmtmgmt/msp_org_prmt_1007");
			
			return modelAndView;
			
		} catch (Exception e) {
			//logger.debug(e.getMessage());
			throw new MvnoRunException(-1, "");
		}
	}
	/**
	 * 트리플할인 프로모션 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getTriplePrmtList.json")
	public String getTriplePrmtList(HttpServletRequest request, HttpServletResponse response
														, @ModelAttribute("searchVO") TriplePrmtMgmtVO triplePrmtMgmtVO
														, ModelMap modelMap
														, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(triplePrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(triplePrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<TriplePrmtMgmtVO> resultList = triplePrmtMgmtService.getTriplePrmtList(triplePrmtMgmtVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, totalCount);
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 트리플할인 프로모션 요금제 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getTriplePrmtSocList.json")
	public String getTriplePrmtSocList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") TriplePrmtMgmtVO triplePrmtMgmtVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<TriplePrmtMgmtVO> resultList = triplePrmtMgmtService.getTriplePrmtSocList(triplePrmtMgmtVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 트리플 할인 부가서비스 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getTriplePrmtAddList.json")
	public String getTriplePrmtAddList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") TriplePrmtMgmtVO triplePrmtMgmtVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<TriplePrmtMgmtVO> resultList = triplePrmtMgmtService.getTriplePrmtAddList(triplePrmtMgmtVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	/**
	 * 트리플할인 등록
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/regTriplePrmtInfo.json")
	public String regTriplePrmtInfo(@ModelAttribute("triplePrmtMgmtVO") TriplePrmtMgmtVO triplePrmtMgmtVO, 
								@RequestBody String data,
								HttpServletRequest request,
								HttpServletResponse response,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			loginInfo.putSessionToVo(triplePrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			if(!"10".equals(triplePrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			logger.info(data);
			
			TriplePrmtMgmtVO vo = new ObjectMapper().readValue(data, TriplePrmtMgmtVO.class);
			
			vo.setSessionUserId(triplePrmtMgmtVO.getSessionUserId());
			
			triplePrmtMgmtService.regTriplePrmtInfo(vo);
			
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
	 *트리플할인 변경
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/org/prmtmgmt/updTriplePrmtByInfo.json")
	public String updTriplePrmtByInfo(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute TriplePrmtMgmtVO triplePrmtMgmtVO,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(triplePrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(triplePrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			triplePrmtMgmtService.updTriplePrmtByInfo(triplePrmtMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		
		} catch( EgovBizException e ) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
		}
		
		modelMap.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	/**
	 * 요금제코드 엑셀 업로드
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/org/prmtmgmt/getExcelUpSocList.json")
	public String getExcelUpSocList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") TriplePrmtMgmtVO triplePrmtMgmtVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<String> socList = new ArrayList<String>();
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + triplePrmtMgmtVO.getFileName();
			String[] arrCell = {"soc"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.org.prmtmgmt.vo.TriplePrmtMgmtVO", sOpenFileName, arrCell);
			
			if(uploadList.size() == 0){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","엑셀파일에 값이 없습니다.");
				modelMap.addAttribute("result", resultMap);
				return "jsonView";
			}
			
			try {
				
				for ( int i = 0 ; i < uploadList.size(); i++)
				{
					TriplePrmtMgmtVO vo = (TriplePrmtMgmtVO) uploadList.get(i);
					socList.add(vo.getSoc());
					logger.info("soc:" + vo.getSoc());
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Connection Exception occurred");
			}
			
			triplePrmtMgmtVO.setSocList(socList);
			
			
			List<TriplePrmtMgmtVO> resultList = triplePrmtMgmtService.getExcelUpSocList(triplePrmtMgmtVO);
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			int succCt = 0;
			int failCt = 0;
			String alertStr;
			
			for(int i = 0; i<resultList.size(); i++){
				TriplePrmtMgmtVO resultvo = (TriplePrmtMgmtVO) resultList.get(i);
				
				if("1".equals(resultvo.getRowCheck())){
					//성공건수
					succCt ++;
				}
			}
			//실패건수
			failCt = uploadList.size() - succCt;
			//건수 메시지
			alertStr = "전체 : " +  uploadList.size() + "건 / " + "성공: " + succCt + "건 / " + "실패: " + failCt + "건";
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", alertStr);
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
			
		}
		
		
		return "jsonView";
	}
}
