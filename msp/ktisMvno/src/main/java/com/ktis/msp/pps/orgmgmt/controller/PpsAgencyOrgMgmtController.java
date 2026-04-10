package com.ktis.msp.pps.orgmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;
import com.ktis.msp.pps.orgmgmt.service.PpsAgencyOrgMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class PpsAgencyOrgMgmtController extends BaseController {
	@Autowired
	private PpsAgencyOrgMgmtService ppsAgencyOrgMgmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MaskingService maskingService;
		
	@RequestMapping(value = "/pps/agency/orgmgmt/AgentOrgmgmtForm.do", method={POST, GET} )
	public ModelAndView selectAgentOrgmgmtForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		ModelAndView modelAndView = new ModelAndView();
		try {
			//----------------------------------------------------------------
	    	// Login check
	    	//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			OrgMgmtVO searchVO = new OrgMgmtVO();
	    	//loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
			loginInfo.putSessionToVo(searchVO);
			logger.info("PpsAgencyOrgMgmtController.selectAgentOrgmgmtForm : 대리점 판매점 목록조회 페이지 호출");
			
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", searchVO);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("maskingYn", loginService.getUsrMskYn(loginInfo.getUserId()));
			modelAndView.setViewName("pps/agency/orgmgmt/agency_orgmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    	
		
	}
	
	
	@RequestMapping(value = "/pps/agency/orgmgmt/AgencyOrgMgmtList.json" )
	public String selectAgencyOrgMgmtListJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{

		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
	    pRequestParamMap.put("adminId", loginInfo.getUserId());
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}
		
		
		logger.info("PpsAgencyOrgMgmtController.selectAgencyOrgMgmtListJson : 대리점 판매점 목록조회");
		//----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
    		List<?> resultList =  ppsAgencyOrgMgmtService.getAgencyOrgMgmtList(pRequestParamMap);
    		
    		//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
            maskFields.put("corpRegNum", "CORPORATE");
            maskFields.put("rprsenNm", "CUST_NAME");
            maskFields.put("rprsenRrnum", "SSN");
            maskFields.put("telnum", "TEL_NO");
            maskFields.put("fax", "TEL_NO");
            maskFields.put("email", "EMAIL");
            maskFields.put("addr1", "ADDR");
            maskFields.put("addr2", "PASSWORD");
            
            maskingService.setMask(resultList, maskFields, pRequestParamMap);

			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
    		
    	 } catch (Exception e) {
    	   		resultMap.clear();
    			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    				//logger.error(e);
    				throw new MvnoErrorException(e);
    			}
    	     }
		
    	
    	
		model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 대리점 판매점내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/orgmgmt/AgencyOrgMgmtListExcel.json" )
	public String selectOrgMgmtListExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		    
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap); 
		pRequestParamMap.put("adminId", loginInfo.getUserId());
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}
	    
	    logger.info("ppsAgencyOrgMgmtController.selectOrgMgmtListExcelJson :선불 판매점내역 목록조회 엑셀출력");
	    
	    try{
	    	// 대리점 권한
			if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsAgencyOrgMgmtService.getAgencyOrgMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	/**
	 *  대리점 조직아이디 생성
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/orgmgmt/searchOrgnId.json" )
	public String ppsSearchOrgnIdJson( ModelMap model,
			HttpServletRequest pRequest,
			/*@ModelAttribute("ppsPinInfoVo")PpsPinInfoVo searchPinInfoVo,*/
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
		)
{

		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}
    	
		
		logger.info("대리점 조직아이디 생성");
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
			// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}

			resultMap= ppsAgencyOrgMgmtService.getPpsSearchOrgnId(pRequestParamMap);


			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		//logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 조직 정보 등록
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/pps/orgmgmt/insertMgmt.json")
	public String insertOrgMgmt(OrgMgmtVO orgMgmtVO, HttpServletRequest request, HttpServletResponse response, ModelMap model)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 정보 등록 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			// 대리점 권한
			if(!"20".equals(orgMgmtVO.getSessionUserOrgnTypeCd()) || !"10".equals(orgMgmtVO.getSessionUserOrgnLvlCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			orgMgmtVO.setRegId(loginInfo.getUserId());    /** 사용자ID */
			orgMgmtVO.setRvisnId(loginInfo.getUserId());
			
			
			if(!StringUtils.isEmpty(orgMgmtVO.getHghrOrgnCd()))
			{
				OrgMgmtVO hghrOrgMgmtVO = new OrgMgmtVO();
				
				hghrOrgMgmtVO.setOrgnId(orgMgmtVO.getHghrOrgnCd());
				
				OrgMgmtVO returnHghrOrgMgmtVO = ppsAgencyOrgMgmtService.ppsDetailOrgMgmt(hghrOrgMgmtVO);
				
				int lvlCd = 0;
				
				lvlCd = Integer.parseInt(returnHghrOrgMgmtVO.getOrgnLvlCd().trim());
				
				//조직 레벨 자동 셋팅
				if(lvlCd == 10 || lvlCd == 20)
				{
					orgMgmtVO.setOrgnLvlCd(OrgMgmtVO.ORG_LVL_CD_SALE_AGNCY);
					orgMgmtVO.setTypeCd(OrgMgmtVO.ORG_TYPE_CD_SALE_AGNCY);
				}
				else if(lvlCd == 5)
				{
					orgMgmtVO.setOrgnLvlCd(OrgMgmtVO.ORG_LVL_CD_AGNCY);
					orgMgmtVO.setTypeCd(OrgMgmtVO.ORG_TYPE_CD_AGNCY);
				}
				else
				{
					lvlCd = lvlCd + 1;
					orgMgmtVO.setOrgnLvlCd(Integer.toString(lvlCd));
					orgMgmtVO.setTypeCd(OrgMgmtVO.ORG_TYPE_CD_HDOFC);
				}
				
				//MVNO 조직일 경우 Y 셋팅
				orgMgmtVO.setTypeDtlCd("Y");
			}
			
			//법인등록번호, 주민번호, 전화번호, Fax번호 ,사업자번호 합치기
			if(KtisUtil.isNotEmpty(orgMgmtVO.getCorpRegNum1()) && KtisUtil.isNotEmpty(orgMgmtVO.getCorpRegNum2())){
				orgMgmtVO.setCorpRegNum(orgMgmtVO.getCorpRegNum1()+orgMgmtVO.getCorpRegNum2());
			}
			
			if(KtisUtil.isNotEmpty(orgMgmtVO.getRprsenRrnum1()) && KtisUtil.isNotEmpty(orgMgmtVO.getRprsenRrnum2())){
				orgMgmtVO.setRprsenRrnum(orgMgmtVO.getRprsenRrnum1()+orgMgmtVO.getRprsenRrnum2());
			}
			
			if(KtisUtil.isNotEmpty(orgMgmtVO.getTelnum2()) && KtisUtil.isNotEmpty(orgMgmtVO.getTelnum3())){
				orgMgmtVO.setTelnum(orgMgmtVO.getTelnum1()+orgMgmtVO.getTelnum2()+orgMgmtVO.getTelnum3());
			}
			
			if(KtisUtil.isNotEmpty(orgMgmtVO.getFax2()) && KtisUtil.isNotEmpty(orgMgmtVO.getFax3())){
				orgMgmtVO.setFax(orgMgmtVO.getFax1()+orgMgmtVO.getFax2()+orgMgmtVO.getFax3());
			}
			
			if(KtisUtil.isNotEmpty(orgMgmtVO.getBizRegNum1()) && KtisUtil.isNotEmpty(orgMgmtVO.getBizRegNum2()) && KtisUtil.isNotEmpty(orgMgmtVO.getBizRegNum3())){
				orgMgmtVO.setBizRegNum(orgMgmtVO.getBizRegNum1()+ orgMgmtVO.getBizRegNum2() + orgMgmtVO.getBizRegNum3());
			}
			
//			int returnCnt = ppsAgencyOrgMgmtService.ppsInsertOrgMgmt(orgMgmtVO);
			ppsAgencyOrgMgmtService.ppsInsertOrgMgmt(orgMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 조직 정보 수정
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/pps/orgmgmt/updateMgmt.json")
	public String updateOrgMgmt(OrgMgmtVO orgMgmtVO, HttpServletRequest request, HttpServletResponse response, ModelMap model)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 정보 수정 START. VO : "+orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			// 대리점 권한
			if(!"20".equals(orgMgmtVO.getSessionUserOrgnTypeCd()) || !"10".equals(orgMgmtVO.getSessionUserOrgnLvlCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			orgMgmtVO.setRegId(loginInfo.getUserId());    /** 사용자ID */
			orgMgmtVO.setRvisnId(loginInfo.getUserId());
			
			OrgMgmtVO hghrOrgMgmtVO = new OrgMgmtVO();
			//현재 조직에 대한 정보
			OrgMgmtVO returnOrgMgmtVO = ppsAgencyOrgMgmtService.ppsDetailOrgMgmt(orgMgmtVO);
	
			if("1".equals(orgMgmtVO.getOrgnLvlCd())){
				//본사조직이다.
				logger.debug("본사조직");
				
				//마스킹 Update 처리
				updateMsaking(orgMgmtVO, returnOrgMgmtVO);
			}
			else
			{
				hghrOrgMgmtVO.setOrgnId(orgMgmtVO.getHghrOrgnCd());
				
				//상위 조직에 대한 정보
				OrgMgmtVO returnHghrOrgMgmtVO = ppsAgencyOrgMgmtService.ppsDetailOrgMgmt(hghrOrgMgmtVO);
				
				//마스킹 Update 처리
				updateMsaking(orgMgmtVO, orgMgmtVO);
				
				orgMgmtVO.setOldFax(returnOrgMgmtVO.getFax());
				logger.info(generateLogMsg("과거 펙스번호 ==" + returnOrgMgmtVO.getFax()));
				
				if(!StringUtils.isEmpty(orgMgmtVO.getHghrOrgnCd()))
				{
					int lvlCd = 0;
					
					lvlCd = Integer.parseInt(returnHghrOrgMgmtVO.getOrgnLvlCd().trim());
					
					//조직 레벨 자동 셋팅
					if(lvlCd == 10)
					{
						orgMgmtVO.setOrgnLvlCd(OrgMgmtVO.ORG_LVL_CD_SALE_AGNCY);
						orgMgmtVO.setTypeCd(OrgMgmtVO.ORG_TYPE_CD_SALE_AGNCY);
					}
					else if(lvlCd == 5)
					{
						orgMgmtVO.setOrgnLvlCd(OrgMgmtVO.ORG_LVL_CD_AGNCY);
						orgMgmtVO.setTypeCd(OrgMgmtVO.ORG_TYPE_CD_AGNCY);
					}
					else
					{
						lvlCd = lvlCd + 1;
						orgMgmtVO.setOrgnLvlCd(Integer.toString(lvlCd));
						orgMgmtVO.setTypeCd(OrgMgmtVO.ORG_TYPE_CD_HDOFC);
					}
				}
			}
			if(KtisUtil.isNotEmpty(orgMgmtVO.getFax2()) && KtisUtil.isNotEmpty(orgMgmtVO.getFax3())){
				orgMgmtVO.setFax(orgMgmtVO.getFax1()+orgMgmtVO.getFax2()+orgMgmtVO.getFax3());
			}
			
			if(KtisUtil.isNotEmpty(orgMgmtVO.getBizRegNum1()) && KtisUtil.isNotEmpty(orgMgmtVO.getBizRegNum2()) && KtisUtil.isNotEmpty(orgMgmtVO.getBizRegNum3())){
				orgMgmtVO.setBizRegNum(orgMgmtVO.getBizRegNum1()+ orgMgmtVO.getBizRegNum2() + orgMgmtVO.getBizRegNum3());
			}
			
//			int returnCnt = ppsAgencyOrgMgmtService.updateOrgMgmt(orgMgmtVO);
			ppsAgencyOrgMgmtService.updateOrgMgmt(orgMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 마스킹 조직 정보 수정
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */

	private void updateMsaking(OrgMgmtVO orgMgmtVO, OrgMgmtVO returnOrgMgmtVO) {

		logger.info("returnOrgMgmtVO = " + returnOrgMgmtVO.toString());

		//마스킹 대상 UPDATE
		String strCorpRegNum = null;//법인등록번호 뒷자리 5개
		String strRprsenNm = null;//대표자명
		String strRprsenRrnum = null;//대표자주민번호
		String strTelnum = null;//대표전화번호 중간 2자리
//    		String strTelnum2 = null;//대표자전화번호 마지막 1자리
		String strEmail = null;//이메일
		String strAddr1 = null;//주소
		String strAddr2 = null;//상세주소

		strCorpRegNum = orgMgmtVO.getCorpRegNum1()+orgMgmtVO.getCorpRegNum2();
		strRprsenNm = orgMgmtVO.getRprsenNm();
		strRprsenRrnum = orgMgmtVO.getRprsenRrnum1()+orgMgmtVO.getRprsenRrnum2();
		strTelnum = orgMgmtVO.getTelnum1()+orgMgmtVO.getTelnum2()+orgMgmtVO.getTelnum3();
		strEmail = orgMgmtVO.getEmail();
		strAddr1 = orgMgmtVO.getAddr1();
		strAddr2 = orgMgmtVO.getAddr2();

		int intCorpRegNum = 0;
		int intRprsenNm = 0;
		int intRprsenRrnum = 0;
		int intTelnum = 0;
		int intEmail = 0;
		int intAddr1 = 0;
		int intAddr2 = 0;

		if(!StringUtils.isEmpty(strCorpRegNum))
		{
			intCorpRegNum = strCorpRegNum.indexOf("*");
		}

		if(!StringUtils.isEmpty(strRprsenNm))
		{
			intRprsenNm = strRprsenNm.indexOf("*");
		}

		if(!StringUtils.isEmpty(strRprsenRrnum))
		{
			intRprsenRrnum = strRprsenRrnum.indexOf("*");
		}

		if(!StringUtils.isEmpty(strTelnum))
		{
			intTelnum = strTelnum.indexOf("*");
		}

		if(!StringUtils.isEmpty(strEmail))
		{
			intEmail = strEmail.indexOf("*");
		}

		if(!StringUtils.isEmpty(strAddr1))
		{
			intAddr1 = strAddr1.indexOf("*");
		}

		if(!StringUtils.isEmpty(strAddr2))
		{
			intAddr2 = strAddr2.indexOf("*");
		}


		OrgMgmtVO updateOrgMgmtVO = new OrgMgmtVO();

		updateOrgMgmtVO.setOrgnId(orgMgmtVO.getOrgnId());

		if(intCorpRegNum == -1)
		{
			updateOrgMgmtVO.setCorpRegNum(orgMgmtVO.getCorpRegNum1()+orgMgmtVO.getCorpRegNum2());
		}
		else
		{
			updateOrgMgmtVO.setCorpRegNum(null);
		}

		if(intRprsenNm == -1)
		{
			updateOrgMgmtVO.setRprsenNm(orgMgmtVO.getRprsenNm());
		}
		else
		{
			updateOrgMgmtVO.setRprsenNm(null);
		}

		if(intRprsenRrnum == -1)
		{
			updateOrgMgmtVO.setRprsenRrnum(orgMgmtVO.getRprsenRrnum1()+orgMgmtVO.getRprsenRrnum2());
		}
		else
		{
			updateOrgMgmtVO.setRprsenRrnum(null);
		}

		if(intTelnum == -1)
		{
			updateOrgMgmtVO.setTelnum(orgMgmtVO.getTelnum1()+orgMgmtVO.getTelnum2()+orgMgmtVO.getTelnum3());
		}
		else
		{
			updateOrgMgmtVO.setTelnum(null);
		}

		if(intEmail == -1)
		{
			updateOrgMgmtVO.setEmail(orgMgmtVO.getEmail());
		}
		else
		{
			updateOrgMgmtVO.setEmail(null);
		}

		if(intAddr1 == -1)
		{
			updateOrgMgmtVO.setAddr1(orgMgmtVO.getAddr1());
		}
		else
		{
			updateOrgMgmtVO.setAddr1(null);
		}

		if(intAddr2 == -1)
		{
			updateOrgMgmtVO.setAddr2(orgMgmtVO.getAddr2());
		}
		else
		{
			updateOrgMgmtVO.setAddr2(null);
		}

		int mskingCnt = ppsAgencyOrgMgmtService.updateOrgMgmtMasking(updateOrgMgmtVO);

		logger.info(generateLogMsg("마스킹 Update ==" + mskingCnt));
	}
	
	/**
	* @Description : 조직 유형을 찾아온다.
	* @Param  :
	* @Return : String
	* @Author : 장익준
	* @Create Date : 2015. 6. 25.
	 */
	@RequestMapping("/pps/orgmgmt/selectOrgnTypeDtl.json")
	public String selectOrgnTypeDtl(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 유형을 찾아온다. START."));
		logger.info(generateLogMsg("================================================================="));

		String orgnType = null;
		List<?> resultList = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 대리점 권한
    		if(!"20".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pReqParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			if( !StringUtils.isEmpty(request.getParameter("orgnType1")))
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType1"));
				orgnType = request.getParameter("orgnType1");
				orgnType = ppsAgencyOrgMgmtService.selectOrgnTypeDtlCd(orgnType);
				resultList = ppsAgencyOrgMgmtService.selectOrgnTypeDtl(orgnType);
			}else if( !StringUtils.isEmpty(request.getParameter("orgnType2")))
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType2"));
				orgnType = request.getParameter("orgnType2");
				orgnType = ppsAgencyOrgMgmtService.selectOrgnTypeDtlCd(orgnType);
				resultList = ppsAgencyOrgMgmtService.selectOrgnTypeDtl(orgnType);
			}else if( !StringUtils.isEmpty(request.getParameter("orgnType3")))
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType3"));
				orgnType = request.getParameter("orgnType3");
				orgnType = ppsAgencyOrgMgmtService.selectOrgnTypeDtlCd(orgnType);
				resultList = ppsAgencyOrgMgmtService.selectOrgnTypeDtl(orgnType);
			}
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			resultMap.clear();
			//logger.info(generateLogMsg(String.format("조직 유형 조회 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	

}
