package com.ktis.msp.gift.prmtmgmt.controller;

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

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.gift.prmtmgmt.service.GiftPrmtService;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class GiftPrmtController extends BaseController {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private GiftPrmtService giftPrmtService;

	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	
	/**
	 * 사은품 프로모션 관리 화면호출
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/gift/prmtmgmt/getGiftPrmtMgmtView.do")
	public ModelAndView getGiftPrmtMgmtView(HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@RequestParam Map<String, Object> commandMap,
								ModelMap model) throws EgovBizException {
		
		logger.debug("**********************************************************");
		logger.debug("* 사은품 프로모션 관리 화면 : /gift/prmtmgmt/msp_gift_prmt *");
		logger.debug("**********************************************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			//본사화면일경우
			if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new EgovBizException("권한이 없습니다.");
			}

			model.addAttribute("authYn",giftPrmtService.groupUserAuth((String)pRequest.getSession().getAttribute("SESSION_USER_ID")));
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.setViewName("/gift/prmtmgmt/msp_gift_prmt");
			
			return modelAndView;
			
		} catch (Exception e) {
			//logger.debug(e.getMessage());
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 사은품 프로모션 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/gift/prmtmgmt/getGiftPrmtList.json")
	public String getGiftPrmtList(HttpServletRequest request, HttpServletResponse response
														, @ModelAttribute("searchVO") GiftPrmtMgmtVO giftPrmtMgmtVO
														, ModelMap modelMap
														, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(giftPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(giftPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<GiftPrmtMgmtVO> resultList = giftPrmtService.getGiftPrmtList(giftPrmtMgmtVO);
			
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
	 * 사은품 프로모션 조직 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/gift/prmtmgmt/getGiftPrmtOrgnList.json")
	public String getGiftPrmtOrgnList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") GiftPrmtMgmtSubVO giftPrmtMgmtSubVO
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
			
			List<GiftPrmtMgmtSubVO> resultList = giftPrmtService.getGiftPrmtOrgnList(giftPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 사은품 프로모션 요금제 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/gift/prmtmgmt/getGiftPrmtSocList.json")
	public String getGiftPrmtSocList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") GiftPrmtMgmtSubVO giftPrmtMgmtSubVO
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
			
			List<GiftPrmtMgmtSubVO> resultList = giftPrmtService.getGiftPrmtSocList(giftPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 사은품 프로모션 부가서비스 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/gift/prmtmgmt/getGiftPrmtPrdtList.json")
	public String getGiftPrmtPrdtList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") GiftPrmtMgmtSubVO giftPrmtMgmtSubVO
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
			
			List<GiftPrmtMgmtSubVO> resultList = giftPrmtService.getGiftPrmtPrdtList(giftPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 사은품 프로모션 등록
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/gift/prmtmgmt/regGiftPrmtInfo.json")
	public String regGiftPrmtInfo(@ModelAttribute("giftPrmtMgmtVO") GiftPrmtMgmtVO giftPrmtMgmtVO, 
								@RequestBody String data,
								HttpServletRequest request,
								HttpServletResponse response,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			loginInfo.putSessionToVo(giftPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			if(!"10".equals(giftPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			logger.info(data);
			
			GiftPrmtMgmtVO vo = new ObjectMapper().readValue(data, GiftPrmtMgmtVO.class);
			
			vo.setSessionUserId(giftPrmtMgmtVO.getSessionUserId());
			
			giftPrmtService.regGiftPrmtInfo(vo);
			
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
	 * 사은품 프로모션 등록
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/gift/prmtmgmt/modGiftPrmtInfo.json")
	public String modGiftPrmtInfo(@ModelAttribute("giftPrmtMgmtVO") GiftPrmtMgmtVO giftPrmtMgmtVO, 
								@RequestBody String data,
								HttpServletRequest request,
								HttpServletResponse response,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			loginInfo.putSessionToVo(giftPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			if(!"10".equals(giftPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			logger.info(data);
			
			GiftPrmtMgmtVO vo = new ObjectMapper().readValue(data, GiftPrmtMgmtVO.class);
			
			vo.setSessionUserId(giftPrmtMgmtVO.getSessionUserId());
			
			giftPrmtService.modGiftPrmtInfo(vo);
			
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
	 *사은품 프로모션 변경
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/gift/prmtmgmt/updGiftPrmtByInfo.json")
	public String updGiftPrmtByInfo(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute GiftPrmtMgmtVO giftPrmtMgmtVO,
								ModelMap modelMap,
								@RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(giftPrmtMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(giftPrmtMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			giftPrmtService.updGiftPrmtByInfo(giftPrmtMgmtVO);
			
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
	 * 모집경로 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/gift/prmtmgmt/getGiftPrmtOnoffList.json")
	public String getGiftPrmtOnoffList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") GiftPrmtMgmtSubVO giftPrmtMgmtSubVO
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
			
			List<GiftPrmtMgmtSubVO> resultList = giftPrmtService.getGiftPrmtOnoffList(giftPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 대상제품 목록 조회
	 * @param request
	 * @param response
	 * @param paramVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/gift/prmtmgmt/getGiftPrmtModelList.json")
	public String getGiftPrmtModelList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") GiftPrmtMgmtSubVO giftPrmtMgmtSubVO
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
			
			List<GiftPrmtMgmtSubVO> resultList = giftPrmtService.getGiftPrmtModelList(giftPrmtMgmtSubVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
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
	@RequestMapping(value = "/gift/prmtmgmt/getExcelUpSocList.json")
	public String getExcelUpSocList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") GiftPrmtMgmtSubVO giftPrmtMgmtSubVO
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
			
			String sOpenFileName = baseDir + "/CMN/" + giftPrmtMgmtSubVO.getFileName();
			String[] arrCell = {"soc"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO", sOpenFileName, arrCell);
			
			if(uploadList.size() == 0){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","엑셀파일에 값이 없습니다.");
				modelMap.addAttribute("result", resultMap);
				return "jsonView";
			}
			
			try {
				
				for ( int i = 0 ; i < uploadList.size(); i++)
				{
					GiftPrmtMgmtSubVO vo = (GiftPrmtMgmtSubVO) uploadList.get(i);
					socList.add(vo.getSoc());
					logger.info("soc:" + vo.getSoc());
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Connection Exception occurred");
			}
			
			giftPrmtMgmtSubVO.setSocList(socList);
			
			
			List<GiftPrmtMgmtSubVO> resultList = giftPrmtService.getExcelUpSocList(giftPrmtMgmtSubVO);
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			int succCt = 0;
			int failCt = 0;
			String alertStr;
			
			for(int i = 0; i<resultList.size(); i++){
				GiftPrmtMgmtSubVO resultvo = (GiftPrmtMgmtSubVO) resultList.get(i);
				
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
	@RequestMapping(value = "/gift/prmtmgmt/getExcelUpModelList.json")
	public String getExcelUpModelList(HttpServletRequest request, HttpServletResponse response
								, @ModelAttribute("searchVO") GiftPrmtMgmtSubVO giftPrmtMgmtSubVO
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
			
			List<String> modelCdList = new ArrayList<String>();
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + giftPrmtMgmtSubVO.getFileName();
			String[] arrCell = {"modelCd"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO", sOpenFileName, arrCell);
			
			if(uploadList.size() == 0){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","엑셀파일에 값이 없습니다.");
				modelMap.addAttribute("result", resultMap);
				return "jsonView";
			}
			
			try {
				
				for ( int i = 0 ; i < uploadList.size(); i++)
				{
					GiftPrmtMgmtSubVO vo = (GiftPrmtMgmtSubVO) uploadList.get(i);
					modelCdList.add(vo.getModelCd());
					logger.info("modelCd:" + vo.getModelCd());
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Connection Exception occurred");
			}
			
			giftPrmtMgmtSubVO.setModelCdList(modelCdList);
			
			
			List<GiftPrmtMgmtSubVO> resultList = giftPrmtService.getExcelUpModelList(giftPrmtMgmtSubVO);
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			int succCt = 0;
			int failCt = 0;
			String alertStr;
			
			for(int i = 0; i<resultList.size(); i++){
				GiftPrmtMgmtSubVO resultvo = (GiftPrmtMgmtSubVO) resultList.get(i);
				
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
	/**
	* 2023.05.19 
	* @Description : 사은품프로모션 요금제 등록 엑셀다운로드 양식 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/gift/prmtmgmt/getSocTempExcelDown.json")
	public String getSocTempExcelDown(@ModelAttribute("searchVO") GiftPrmtMgmtSubVO searchVO,
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
			
			List<?> resultList = new ArrayList<RcpListVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "대상요금제엑셀업로드양식_";//파일명
			String strSheetname = "대상요금제엑셀업로드양식";//시트명
			
			String [] strHead = {"요금제 코드"};
			String [] strValue = {"soc"};
//			String [] strValue = {};
			
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
	* 2023.05.19 
	* @Description : 사은품프로모션 제품 등록 엑셀다운로드 양식 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/gift/prmtmgmt/getModelTempExcelDown.json")
	public String getModelTempExcelDown(@ModelAttribute("searchVO") GiftPrmtMgmtSubVO searchVO,
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
			
			List<?> resultList = new ArrayList<RcpListVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "대상제품엑셀업로드양식_";//파일명
			String strSheetname = "대상제품엑셀업로드양식";//시트명
			
			String [] strHead = {"제품코드"};
			String [] strValue = {"modelCd"};
//			String [] strValue = {};
			
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
	 * @Description : GIFT 제품 관리 엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/gift/prdtMng/getGiftPrmtListEx.json")
	public String getGiftPrmtListEx(@ModelAttribute("searchVO") GiftPrmtMgmtVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사은품 프로모션 관리 엑셀다운 START."));
		logger.info(generateLogMsg("================================================================="));

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
			excelMap.put("DUTY_NM","PRMT");
			excelMap.put("MENU_NM","사은품프로모션관리");
			
			String searchVal = "시작기준일:"+searchVO.getSearchBaseDate()+
					"|시작종료일:"+searchVO.getSearchBaseEndDate()+
					"|프로모션명:"+searchVO.getPrmtNm()+
					"|채널유형:"+searchVO.getOrgnType()+
					"|구매유형:"+searchVO.getReqBuyType()+
					"|모집경로:"+searchVO.getOnOffType()+
					"|노출여부:"+searchVO.getShowYn()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
			
			List<?> resultList = giftPrmtService.getGiftPrmtListEx(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "사은품_프로모션관리_";//파일명
			String strSheetname = "프로모션관리";//시트명
			
			String [] strHead = {"프로모션ID","프로모션명","노출여부","노출여부","시작일자"
					,"종료일자","프로모션 유형","채널유형","구매유형","총금액 제한"
					,"신규","번호이동","약정기간_무약정","약정기간_12개월","약정기간_18개월"
					,"약정기간_24개월","약정기간_30개월","약정기간_36개월","노출문구","등록자"
					,"등록일시","수정자","수정일시"}; //23
			String [] strValue = {"prmtId","prmtNm","showYn","showYnNm","strtDt"
					,"endDt","prmtTypeNm","orgnTypeNm","reqBuyTypeNm","amountLimit"
					,"nacYn","mnpYn","enggCnt0","enggCnt12","enggCnt18"
					,"enggCnt24","enggCnt30","enggCnt36","prmtText","regstNm"
					,"regstDttm","rvisnNm","rvisnDttm"}; //23
			int[] intWidth = {7000, 9000, 5000, 6000, 7000, 7000
					,8000, 6000, 7000, 8000, 5000, 5000
					,7000, 7000, 7000, 7000, 9000, 9000
					,9000, 9000, 9000, 9000, 9000, 5000
					,9000, 5000, 9000}; //23	
			int[] intLen = {0, 0, 0, 0, 0
					, 0, 0, 0, 0, 0
					, 0, 0, 0, 0, 0
					, 0, 0, 0, 0, 0
					, 0, 0, 0}; //23 

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);

			logger.info("strFileName : "+strFileName);
			logger.info("#################### 데이터 엑셀로 변환 종료 #####################");
			
			
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
				pRequestParamMap.put("DUTY_NM"   ,"PTNR");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID", exclDnldId);
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		}finally {
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
	
	
	/*
	 * 사은품 프로모션 복사
	@RequestMapping("/gift/prmtmgmt/copyGiftPrmtInfo.json")
	public String copyGiftPrmtInfo(@ModelAttribute GiftPrmtMgmtCopyVO giftPrmtMgmtCopyVO,
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
			loginInfo.putSessionToVo(giftPrmtMgmtCopyVO);
			
			// 본사 화면인 경우
			if(!"10".equals(giftPrmtMgmtCopyVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			giftPrmtService.copyGiftPrmtInfo(giftPrmtMgmtCopyVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
		 */

}
