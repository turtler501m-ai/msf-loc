package com.ktis.msp.org.mnfctmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.mnfctmgmt.service.MnfctMgmtService;
import com.ktis.msp.org.mnfctmgmt.vo.MnfctMgmtVo;
import com.ktis.msp.util.StringUtil;



/**
 * @Class Name : MnfctMgmtController
 * @Description : 제조사 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Controller
public class MnfctMgmtController extends BaseController {

	@Autowired
	private MnfctMgmtService mnfctMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;

	public MnfctMgmtController() {
		setLogPrefix("[MnfctMgmtController] ");
	}

	/**
	 * @Description : 제조사 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/org/mnfctmgmt/mnfctMgmt.do")
	public ModelAndView mnfctMgmt(@ModelAttribute("mnfctMgmtVo") MnfctMgmtVo mnfctMgmtVo, HttpServletRequest request, HttpServletResponse response, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제조사 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(mnfctMgmtVo);
			
			// 본사 권한
			if(!"10".equals(mnfctMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("org/mnfctmgmt/msp_org_bs_1019");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}


	/**
	 * @Description : 제조사 찾기 팝업화면
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 27.
	 */
	@RequestMapping(value = "/org/mnfctmgmt/searchMnfctInfo.do", method={POST, GET})
	public String searchMnfctInfo(HttpServletRequest request, HttpServletResponse response){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제조사 찾기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		// 로그인체크만
		//v2018.11 PMD 적용 소스 수정
		//LoginInfo loginInfo = new LoginInfo(request, response);
		new LoginInfo(request, response);
		
		return "org/mnfctmgmt/msp_org_pu_1019";
	}

	/**
	 * @Description : 제조사 리스트 조회
	 * @Param  :
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/mnfctmgmt/mcfctMgmtList.json")
	public String selectMnfctMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("mnfctMgmtVo") MnfctMgmtVo mnfctMgmtVo, ModelMap model, @RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제조사 조회 START."));
		logger.info(generateLogMsg("Return Vo [acntMgmtVO] = " + mnfctMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(mnfctMgmtVo);
			
			// 본사 권한
			if(!"10".equals(mnfctMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = mnfctMgmtService.selectMngctServiceList(mnfctMgmtVo);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP2000029", "업체관리"))
			{
				//logger.info(generateLogMsg(String.format("제조사 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 제조사명리스트를 조회
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 27.
	 */
	@RequestMapping("/org/mnfctmgmt/selectMnfctList.json")
	public String selectMnfctList(HttpServletRequest request, HttpServletResponse response,MnfctMgmtVo mnfctMgmtVo, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제조사명 리스트 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(mnfctMgmtVo);
			
			List<?> resultList = mnfctMgmtService.selectMnfctList(mnfctMgmtVo);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("제조사명 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 제조사/공급사 등록
	 * @Param  : mnfctMgmtVo.class
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/mnfctmgmt/insertMnfctMgmt.json")
	public String insertMnfctMgmt(MnfctMgmtVo mnfctMgmtVo, HttpServletRequest request, HttpServletResponse response, ModelMap model)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제조사/공급사 등록 START."));
		logger.info(generateLogMsg("mnfctMgmtVo= " + mnfctMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(mnfctMgmtVo);
			
			// 본사 권한
			if(!"10".equals(mnfctMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			mnfctMgmtVo.setRegId(loginInfo.getUserId());    /** 사용자ID */
			mnfctMgmtVo.setRvisnId(loginInfo.getUserId());
			
			//----------------------------------------------------------------
            // 업체명, 대표자명, e-mail, 상세주소 특수문자(<, >) 체크
            //----------------------------------------------------------------
			String strMnfctNm = String.valueOf(mnfctMgmtVo.getMnfctNm()); // 업체명
            String strRprsenNm = String.valueOf(mnfctMgmtVo.getRprsenNm()); // 대표자명
            String strEmail = String.valueOf(mnfctMgmtVo.getEmail()); // e-mail
            String strDtlAddr = String.valueOf(mnfctMgmtVo.getDtlAddr()); // 상세주소
            if(StringUtil.chkString(strMnfctNm) || StringUtil.chkString(strRprsenNm) 
                    || StringUtil.chkString(strEmail) || StringUtil.chkString(strDtlAddr) ) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.CHK_STRING", null, Locale.getDefault()));
            }
			
			//전화번호, Fax번호 ,사업자번호 합치기
			if(KtisUtil.isNotEmpty(mnfctMgmtVo.getTelnum2()) && KtisUtil.isNotEmpty(mnfctMgmtVo.getTelnum3())){
				mnfctMgmtVo.setTelnum(mnfctMgmtVo.getTelnum1()+mnfctMgmtVo.getTelnum2()+mnfctMgmtVo.getTelnum3());
			}
			
			if(KtisUtil.isNotEmpty(mnfctMgmtVo.getFax2()) && KtisUtil.isNotEmpty(mnfctMgmtVo.getFax3())){
				mnfctMgmtVo.setFax(mnfctMgmtVo.getFax1()+mnfctMgmtVo.getFax2()+mnfctMgmtVo.getFax3());
			}
			
			if(KtisUtil.isNotEmpty(mnfctMgmtVo.getBizRegNum1()) && KtisUtil.isNotEmpty(mnfctMgmtVo.getBizRegNum2()) && KtisUtil.isNotEmpty(mnfctMgmtVo.getBizRegNum3())){
				mnfctMgmtVo.setBizRegNum(mnfctMgmtVo.getBizRegNum1()+ mnfctMgmtVo.getBizRegNum2() + mnfctMgmtVo.getBizRegNum3());
			}
			
			int returnCnt = mnfctMgmtService.insertMnfctMgmt(mnfctMgmtVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			logger.info(generateLogMsg("등록 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
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

	/**
	* @Description : 제조사/공급사 수정
	* @Param  : mnfctMgmtVo
	* @Return : String
	* @Author : 고은정
	* @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/mnfctmgmt/updateMnfctMgmt.json")
	public String updateMnfctMgmt(MnfctMgmtVo mnfctMgmtVo, HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제조사/공급사 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(mnfctMgmtVo);
			
			// 본사 권한
			if(!"10".equals(mnfctMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			mnfctMgmtVo.setRegId(loginInfo.getUserId());    /** 사용자ID */
			mnfctMgmtVo.setRvisnId(loginInfo.getUserId());
			
			//----------------------------------------------------------------
            // 업체명, 대표자명, e-mail, 상세주소 특수문자(<, >) 체크
            //----------------------------------------------------------------
            String strMnfctNm = String.valueOf(mnfctMgmtVo.getMnfctNm()); // 업체명
            String strRprsenNm = String.valueOf(mnfctMgmtVo.getRprsenNm()); // 대표자명
            String strEmail = String.valueOf(mnfctMgmtVo.getEmail()); // e-mail
            String strDtlAddr = String.valueOf(mnfctMgmtVo.getDtlAddr()); // 상세주소
            if(StringUtil.chkString(strMnfctNm) || StringUtil.chkString(strRprsenNm) 
                    || StringUtil.chkString(strEmail) || StringUtil.chkString(strDtlAddr) ) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.CHK_STRING", null, Locale.getDefault()));
            }
			
			//전화번호, Fax번호 ,사업자번호 합치기
			if(KtisUtil.isNotEmpty(mnfctMgmtVo.getTelnum2()) && KtisUtil.isNotEmpty(mnfctMgmtVo.getTelnum3())){
				mnfctMgmtVo.setTelnum(mnfctMgmtVo.getTelnum1()+mnfctMgmtVo.getTelnum2()+mnfctMgmtVo.getTelnum3());
			}
			
			if(KtisUtil.isNotEmpty(mnfctMgmtVo.getFax2()) && KtisUtil.isNotEmpty(mnfctMgmtVo.getFax3())){
				mnfctMgmtVo.setFax(mnfctMgmtVo.getFax1()+mnfctMgmtVo.getFax2()+mnfctMgmtVo.getFax3());
			}
			
			if(KtisUtil.isNotEmpty(mnfctMgmtVo.getBizRegNum1()) && KtisUtil.isNotEmpty(mnfctMgmtVo.getBizRegNum2()) && KtisUtil.isNotEmpty(mnfctMgmtVo.getBizRegNum3())){
				mnfctMgmtVo.setBizRegNum(mnfctMgmtVo.getBizRegNum1()+ mnfctMgmtVo.getBizRegNum2() + mnfctMgmtVo.getBizRegNum3());
			}
			
			int returnCnt = mnfctMgmtService.updateMnfctMgmt(mnfctMgmtVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			logger.info(generateLogMsg("수정 건수 = " + returnCnt));
			
		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
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

}
