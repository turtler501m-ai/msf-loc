package com.ktis.msp.org.orgmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.orgmgmt.service.OrgPopupService;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;

@Controller
public class OrgPopupController extends BaseController {
	
	@Autowired
	private OrgPopupService orgPopupService;
	
	@Autowired
	private MaskingService maskingService;
	
	/** Constructor */
    public OrgPopupController() {
        setLogPrefix("[OrgMgmtController] ");
    }

	
	@RequestMapping(value = "/msp_org_pu_1010.do", method={POST, GET})
	public String searchPopupOrg1010(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("본사/대리점 조직팝업 화면 START." + request.getParameter("aaaa")));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		
		model.addAttribute("orgnLvlCd", loginInfo.getUserId());
		model.addAttribute("typeCd", request.getParameter("typeCd"));
		
		return "org/orgmgmt/msp_org_pu_1010";
	}
	
	@RequestMapping(value = "/msp_org_pu_1011.do", method={POST, GET})
	public String searchPopupOrg1011(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("판매점 조직팝업 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		
		model.addAttribute("orgnLvlCd", loginInfo.getUserId());
		
		return "org/orgmgmt/msp_org_pu_1011";
	}
	
	@RequestMapping(value = "/msp_org_pu_1012.do", method={POST, GET})
	public String searchPopupOrg1012(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("판매점 조직팝업 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		//v2018.11 PMD 적용 소스 수정
		//LoginInfo loginInfo = new LoginInfo(request, response);
		new LoginInfo(request, response);

		//202408 M2M 판매점용
		if(!StringUtils.isBlank(request.getParameter("m2mYn")) && "Y".equals(request.getParameter("m2mYn"))) {
			model.addAttribute("m2mYn", request.getParameter("m2mYn"));
		}

		return "org/orgmgmt/msp_org_pu_1012";
	}
	
	@RequestMapping("/org/orgmgmt/getHeadAgencyOrgnList.json")
	public String getHeadAgencyOrgnList(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("본사/대리점 조직 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인정보체크 */
			//LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			List<?> resultList = orgPopupService.getHeadAgencyOrgnList(orgMgmtVO);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("corpRegNum", "CORPORATE");
			maskFields.put("rprsenNm", "CUST_NAME");
			maskFields.put("telnum", "TEL_NO");
			maskFields.put("email", "EMAIL");
			maskFields.put("addr1", "ADDR");
			maskFields.put("addr2", "PASSWORD");
//			maskFields.put("respnPrsnId", "CUST_NAME");
//			maskFields.put("respnPrsnNum", "SYSTEM_ID");
//			maskFields.put("respnPrsnMblphn", "MOBILE_PHO");
//			maskFields.put("respnPrsnEmail", "EMAIL");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		return "jsonView";
	}
	
	
	@RequestMapping("/org/orgmgmt/getShopOrgnList.json")
	public String getShopOrgnList(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("판매점 조직 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인정보체크 */
			//LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			List<?> resultList = orgPopupService.getShopOrgnList(orgMgmtVO);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("corpRegNum", "CORPORATE");
			maskFields.put("rprsenNm", "CUST_NAME");
			maskFields.put("telnum", "TEL_NO");
			maskFields.put("email", "EMAIL");
			maskFields.put("addr1", "ADDR");
			maskFields.put("addr2", "PASSWORD");
//			maskFields.put("respnPrsnId", "CUST_NAME");
//			maskFields.put("respnPrsnNum", "SYSTEM_ID");
//			maskFields.put("respnPrsnMblphn", "MOBILE_PHO");
//			maskFields.put("respnPrsnEmail", "EMAIL");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
			{
                throw new MvnoErrorException(e);
			}
		}
		
		return "jsonView";
	}
	
	@RequestMapping("/org/orgmgmt/getShopOrgnListNew.json")
	public String getShopOrgnListNew(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("판매점 조직 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인정보체크 */
			//LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			List<?> resultList = orgPopupService.getShopOrgnListNew(orgMgmtVO);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("corpRegNum", "CORPORATE");
			maskFields.put("rprsenNm", "CUST_NAME");
			maskFields.put("telnum", "TEL_NO");
			maskFields.put("email", "EMAIL");
			maskFields.put("addr1", "ADDR");
			maskFields.put("addr2", "PASSWORD");
//			maskFields.put("respnPrsnId", "CUST_NAME");
//			maskFields.put("respnPrsnNum", "SYSTEM_ID");
//			maskFields.put("respnPrsnMblphn", "MOBILE_PHO");
//			maskFields.put("respnPrsnEmail", "EMAIL");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
			{
                throw new MvnoErrorException(e);
			}
		}
		
		return "jsonView";
	}
	
   @RequestMapping(value = "/msp_org_pu_1013.do", method={POST, GET})
    public String searchPopupOrg1013(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("대리점/판매점 조직팝업 화면 START."));
        logger.info(generateLogMsg("================================================================="));
        
        return "org/orgmgmt/msp_org_pu_1013";
    }
   
   @RequestMapping("/org/orgmgmt/getSalesOrgnList.json")
   public String getSalesOrgnList(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {
       
       logger.info(generateLogMsg("================================================================="));
       logger.info(generateLogMsg("대리점/판매점 조직 리스트 조회 START."));
       logger.info(generateLogMsg("================================================================="));
       
       LoginInfo loginInfo = new LoginInfo(request, response);
       loginInfo.putSessionToParameterMap(pRequestParamMap);
       
       Map<String, Object> resultMap = new HashMap<String, Object>();
       
       try {
           /* 로그인정보체크 */
           loginInfo.putSessionToVo(orgMgmtVO);
           
           List<?> resultList = orgPopupService.getSalesOrgnList(orgMgmtVO);
           
           resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
           
           model.addAttribute("result", resultMap);

       } catch (Exception e) {
           resultMap.clear();
           if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
           {
               throw new MvnoErrorException(e);
           }
       }
       
       return "jsonView";
   }
   
   @RequestMapping(value = "/msp_org_pu_1014.do", method={POST, GET})
	public String searchPopupOrg1014(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("대리점/판매점 조직팝업 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		
		model.addAttribute("orgnLvlCd", loginInfo.getUserId());
		model.addAttribute("typeCd", request.getParameter("typeCd"));
		
		return "org/orgmgmt/msp_org_pu_1014";
	}
   
   @RequestMapping("/org/orgmgmt/getSalesOrgnNewList.json")
	public String getSalesOrgnNewList(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("본사/대리점 조직 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인정보체크 */
			//LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			List<?> resultList = orgPopupService.getSalesOrgnNewList(orgMgmtVO);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("corpRegNum", "CORPORATE");
			maskFields.put("rprsenNm", "CUST_NAME");
			maskFields.put("telnum", "TEL_NO");
			maskFields.put("email", "EMAIL");
			maskFields.put("addr1", "ADDR");
			maskFields.put("addr2", "PASSWORD");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}		
		return "jsonView";
	}


   /**
    * @Description : 유심접점 찾기 팝업 화면
    * @Param  : void
    * @Return : String
    * @Author : 
    * @Create Date : 2023. 3. 15.
    */
   @RequestMapping(value = "/searchUsimOrgPopup.do", method={POST, GET})
   public String searchUsimOrgPopup(HttpServletRequest request, HttpServletResponse response, ModelMap model){
       
       logger.info(generateLogMsg("================================================================="));
       logger.info(generateLogMsg("유심접점 찾기 팝업 화면 START."));
       logger.info(generateLogMsg("================================================================="));
       
		//model.addAttribute("startDate",orgCommonService.getFirstDay());
		//model.addAttribute("endDate",orgCommonService.getToDay());
		
       return "org/orgmgmt/msp_org_pu_1040";
   }
	

   @RequestMapping("/org/usimMgmt/searchUsimOrgList.json")
	public String searchUsimOrgList(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심접점 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {

			loginInfo.putSessionToVo(orgMgmtVO);
			
			//logger.info(generateLogMsg("orgMgmtVO = " + orgMgmtVO));
			
			List<?> resultList = orgPopupService.getUsimOrgList(orgMgmtVO);
			//maskingService.setMask(resultList, maskFields, pRequestParamMap);
           resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
           model.addAttribute("result", resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		return "jsonView";
	}


	/**
	 * @Description : K-Note 접접 찾기 팝업 화면
	 * @Param  : void
	 * @Return : String
	 * @Author :
	 * @Create Date : 2024. 3. 26.
	 */
	@RequestMapping(value = "/searchKnoteOrgPopup.do", method={POST, GET})
	public String searchKnoteOrgPopup(HttpServletRequest request, HttpServletResponse response, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("K-Note 접점 찾기 팝업 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		return "org/orgmgmt/msp_org_pu_1050";
	}


	@RequestMapping("/org/orgmgmt/searchKnoteOrgList.json")
	public String searchKnoteOrgList(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("K-Note 접점 조회 START."));
		logger.info(generateLogMsg("================================================================="));

		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pRequestParamMap);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			/* 로그인정보체크 */
			loginInfo.putSessionToVo(orgMgmtVO);

			List<?> resultList = orgPopupService.getKnoteOrgList(orgMgmtVO);

			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
			{
				throw new MvnoErrorException(e);
			}
		}

		return "jsonView";
	}
}
