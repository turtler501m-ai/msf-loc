package com.ktis.msp.org.orgmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.orgmgmt.service.OrgMgmtService;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtPpsVo;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsCustomerVo;
import com.ktis.msp.util.CommonHttpClient;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : OrgMgmtController
 * @Description : 조직관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.07 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 13.
 */
@Controller
public class OrgMgmtController  extends BaseController {

	/** 조직관리 서비스 */
	@Autowired
	private OrgMgmtService orgMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private MaskingService maskingService;

	@Autowired
	private OrgCommonService orgCommonService;
	
    @Autowired
    private FileDownService  fileDownService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	/** Constructor */
	public OrgMgmtController() {
		setLogPrefix("[OrgMgmtController] ");
	}

	/**
	 * @Description : 조직 관리 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/org/orgmgmt/msp_org_bs_1001.do", method={POST, GET})
	public ModelAndView insertOrgPopup(HttpServletRequest request, HttpServletResponse response, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 관리 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			
			model.addAttribute("startDate",orgCommonService.getToDay());
			
			modelAndView.setViewName("org/orgmgmt/msp_org_bs_1001");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	/**
	 * @Description : 조직팝업 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/msp_org_pu_1001.do", method={POST, GET})
	public String serchPopupOrg(HttpServletRequest request, HttpServletResponse response, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직팝업 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		LoginInfo loginInfo = new LoginInfo(request, response);

		model.addAttribute("orgnLvlCd", loginInfo.getUserId());

		model.addAttribute("pageType", request.getParameter("pageType"));

		return "org/orgmgmt/msp_org_pu_1001";
	}

	/**
	 * @Description : 조직 정보 등록
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/orgmgmt/insertMgmt.json")
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
			
			// 본사 권한
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			orgMgmtVO.setRegId(loginInfo.getUserId());    /** 사용자ID */
			orgMgmtVO.setRvisnId(loginInfo.getUserId());
			
			
			if(!StringUtils.isEmpty(orgMgmtVO.getHghrOrgnCd()))
			{
				OrgMgmtVO hghrOrgMgmtVO = new OrgMgmtVO();
				
				hghrOrgMgmtVO.setOrgnId(orgMgmtVO.getHghrOrgnCd());
				
				OrgMgmtVO returnHghrOrgMgmtVO = orgMgmtService.detailOrgMgmt(hghrOrgMgmtVO);
				
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
			
			//v2018.11 PMD 적용 소스 수정
			//int returnCnt = orgMgmtService.insertOrgMgmt(orgMgmtVO);
			orgMgmtService.insertOrgMgmt(orgMgmtVO);
			
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

	/**
	 * @Description : 조직 정보 수정
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/orgmgmt/updateMgmt.json")
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
			
			// 본사 권한
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			orgMgmtVO.setRegId(loginInfo.getUserId());    /** 사용자ID */
			orgMgmtVO.setRvisnId(loginInfo.getUserId());
			
			OrgMgmtVO hghrOrgMgmtVO = new OrgMgmtVO();
			//현재 조직에 대한 정보
			OrgMgmtVO returnOrgMgmtVO = orgMgmtService.detailOrgMgmt(orgMgmtVO);
	
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
				OrgMgmtVO returnHghrOrgMgmtVO = orgMgmtService.detailOrgMgmt(hghrOrgMgmtVO);
				
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
			
			//v2018.11 PMD 적용 소스 수정
			//int returnCnt = orgMgmtService.updateOrgMgmt(orgMgmtVO);
			orgMgmtService.updateOrgMgmt(orgMgmtVO);
			
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

		int mskingCnt = orgMgmtService.updateOrgMgmtMasking(updateOrgMgmtVO);

		logger.info(generateLogMsg("마스킹 Update ==" + mskingCnt));
	}



	/**
	 * @Description : 조직 리스트 조회
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/orgmgmt/listOrgMgmt.json")
	public String listOrgMgmt(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));


		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			/* 로그인정보체크 */
			loginInfo.putSessionToVo(orgMgmtVO);

			// 본사 권한
			// 2024-03-26 knote 접점은 k-note 팝업에서 조직 검색 가능 하도록 추가
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd()) && !"Y".equals(rcpMgmtService.getKnoteYn(loginInfo.getUserOrgnId()))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = orgMgmtService.listOrgMgmt(orgMgmtVO);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("corpRegNum", "CORPORATE");
			maskFields.put("rprsenNm", "CUST_NAME");
			maskFields.put("rprsenRrnum", "SSN");
			maskFields.put("telnum", "TEL_NO");
			maskFields.put("email", "EMAIL");
			maskFields.put("addr1", "ADDR");
			maskFields.put("addr2", "PASSWORD");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
			{
                throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	* @Description : 조직 트리 리스트
	* @Param  :
	* @Return : String
	* @Author : 고은정
	* @Create Date : 2014. 10. 13.
	 */
	@RequestMapping("/org/orgmgmt/treeListOrgMgmt.json")
	public String treeListOrgMgmt(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 트리 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));


		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			/* 로그인정보체크 */
			loginInfo.putSessionToVo(orgMgmtVO);
			
			// 본사 권한
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = orgMgmtService.treeListOrgMgmt(orgMgmtVO);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("corpRegNum", "CORPORATE");  //법인등록번호
			maskFields.put("rprsenNm", "CUST_NAME");    //대표자명
			maskFields.put("rprsenRrnum", "SSN");       //주민번호
			maskFields.put("telnum", "TEL_NO");         //전화번호
			maskFields.put("email", "EMAIL");           //이메일
			maskFields.put("addr1", "ADDR");            //주소
			maskFields.put("addr2", "PASSWORD");        //주소

			maskFields.put("respnPrsnNm", "CUST_NAME");    //담당자명
			maskFields.put("respnPrsnIdMsk", "SYSTEM_ID");    //담당자아이디
			maskFields.put("respnPrsnMblphn", "MOBILE_PHO");    //담당자휴대폰
			maskFields.put("respnPrsnEmail", "EMAIL");    //담당자이메일
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);

			resultMap =  makeResultTreeRow(pRequestParamMap, resultList, orgMgmtVO.getId());

			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP2000103", "조직관리"))
			{
				throw new MvnoErrorException(e);
			} 
		}

		return "jsonView";
	}

	/**
	 * @Description : 조직 이력 조회
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/orgmgmt/listOrgnHst.do")
	public String listOrgnHst(HttpServletRequest request, HttpServletResponse response, OrgMgmtVO orgMgmtVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 이력 조회 START."));
		logger.info(generateLogMsg("================================================================="));


		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			// 본사 권한
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = orgMgmtService.listOrgnHst(orgMgmtVO);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("corpRegNum", "CORPORATE");
			maskFields.put("rprsenNm", "CUST_NAME");
			maskFields.put("rprsenRrnum", "SSN");
			maskFields.put("telnum", "TEL_NO");
			maskFields.put("email", "EMAIL");
			maskFields.put("addr1", "ADDR");
			maskFields.put("addr2", "PASSWORD");

			maskFields.put("respnPrsnNm", "CUST_NAME"); //담당자명
			maskFields.put("rvisnNm", "CUST_NAME"); //수정자
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 우편번호찾기
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 10. 6.
	 */
	@RequestMapping(value = "/org/orgmgmt/findZipCd.do", method={POST, GET})
	public String findZipCd(){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("우편번호찾기 화면 START."));
		logger.info(generateLogMsg("================================================================="));

		return "org/orgmgmt/road3_popup";
	}


    /**
     * <pre>
     * 설명     : 주소 검색
     * @param locale
     * @param model
     * @return
     * @return: String
     * </pre>
     */
    @RequestMapping(value = "/addrlink/addrLinkApi.do")
    @ResponseBody
    public HttpEntity<byte[]> addrLinkApi(
          //v2018.11 PMD 적용 소스 수정
          /*@RequestParam(value = "currentPage") String currentPage
            ,@RequestParam(value = "countPerPage") String countPerPage
            ,@RequestParam(value = "keyword") String keyword*/
            @RequestParam Map<String, Object> pReqParamMap
            , ModelMap model) {
       
       String currentPage = "";
       String countPerPage = "";
       String keyword = "";
       
       currentPage = (pReqParamMap.get("currentPage") == null ? "" : pReqParamMap.get("currentPage").toString());
       countPerPage = (pReqParamMap.get("countPerPage") == null ? "" : pReqParamMap.get("countPerPage").toString());
       keyword = (pReqParamMap.get("keyword") == null ? "" : pReqParamMap.get("keyword").toString());
       
        try {
            keyword= URLEncoder.encode(keyword, "UTF-8");
            logger.info("keyword1111=====>"+keyword);
        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }

        keyword = keyword.toUpperCase();
        keyword = keyword.replaceAll("OR", "");
        keyword = keyword.replaceAll("SELECT", "");
        keyword = keyword.replaceAll("INSERT", "");
        keyword = keyword.replaceAll("DELETE", "");
        keyword = keyword.replaceAll("UPDATE", "");
        keyword = keyword.replaceAll("CREATE", "");
        keyword = keyword.replaceAll("DROP", "");
        keyword = keyword.replaceAll("EXEC", "");
        keyword = keyword.replaceAll("UNION", "");
        keyword = keyword.replaceAll("FETCH", "");
        keyword = keyword.replaceAll("DECLARE", "");
        keyword = keyword.replaceAll("TRUNCATE", "");

        logger.info("keyword2222=====>"+keyword);


		String jusoKey =  propertyService.getString("juso.key");
		String jusoServer =  propertyService.getString("juso.interface.server");

        String param = "";
        try {
            param = "currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+keyword+"&confmKey="+jusoKey ;
        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }

        String url = jusoServer + "?" + param;
        logger.info("URL : "+url);

        HttpEntity<byte[]> result = null;
        CommonHttpClient client = new CommonHttpClient(url);
        try {
            String xml = client.get();
            logger.debug(xml);
            byte[] documentBody = xml.getBytes("UTF-8");

            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "xml"));
            header.setContentLength(documentBody.length);
            result = new HttpEntity<byte[]>(documentBody, header);

        } catch (Exception e) {
        	throw new MvnoErrorException(e);
        }

        return result;
    }	
	
	
	/**
	 * @Description : 조직레벨 공통 코드 리스트를 조회
	 * @Param  :
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 9. 23.
	 */
	@RequestMapping("/org/orgmgmt/listCmnCdOrgnLvl.json")
	public String listCmnCdOrgnLvl(HttpServletRequest request, HttpServletResponse response, OrgMgmtVO orgMgmtVO, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("레벨 공통코드 리스트 START."));
		logger.info(generateLogMsg("================================================================="));

		try {
			//v2018.11 PMD 적용 소스 수정
		   //LoginInfo loginInfo = new LoginInfo(request, response);
		   new LoginInfo(request, response);
			
			List<?> resultList = orgMgmtService.listCmnCdOrgnLvl(orgMgmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			//logger.info(generateLogMsg(String.format("조직레벨 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
		    throw new MvnoErrorException(e);

		}

		return "jsonView";
	}

	/**
	 * @Description : 조직레벨 공통 코드 리스트를 조회
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 9. 23.
	 */
	@RequestMapping("/org/orgmgmt/listCmnCdOrgnType.json")
	public String listCmnCdOrgnType(HttpServletRequest request, HttpServletResponse response, OrgMgmtVO orgMgmtVO, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유형 공통코드 리스트 START."));
		logger.info(generateLogMsg("================================================================="));
		
		try {
			//v2018.11 PMD 적용 소스 수정
		   //LoginInfo loginInfo = new LoginInfo(request, response);
		   new LoginInfo(request, response);
			
			List<?> resultList = orgMgmtService.listCmnCdOrgnType(orgMgmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			//logger.info(generateLogMsg(String.format("조직유형 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
		    throw new MvnoErrorException(e);
		}

		return "jsonView";
	}

	/**
	* @Description : 조직ID 중복검색
	* @Param  :
	* @Return : String
	* @Author : 고은정
	* @Create Date : 2014. 9. 27.
	 */
	@RequestMapping("/org/orgmgmt/isExistOrgnId.json")
	public String isExistOrgnId(HttpServletRequest request, HttpServletResponse response, OrgMgmtVO orgMgmtVO, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직ID 중복검색 리스트 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//v2018.11 PMD 적용 소스 수정
		   //LoginInfo loginInfo = new LoginInfo(request, response);
			new LoginInfo(request, response);
			
			orgMgmtVO.setOrgnId(orgMgmtVO.getOrgnId().toUpperCase());
			
			int resultCnt = orgMgmtService.isExistOrgnId(orgMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", resultCnt);

			logger.info(generateLogMsg("중복 건수 = " + resultCnt));

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
	 * @Description : 조직 선불 정보 상세
	 * @Param  :
	 * @Return : String
	 * @Author : 김웅
	 * @Create Date : 2014.10. 06.
	  */
	@RequestMapping(value = "/org/orgmgmt/ppsOrgnInfo.json" )
	public String selectOrgMgmtPpsInfo( ModelMap model
										, @ModelAttribute("searchVO")PpsCustomerVo ppsCustomerVo
										, HttpServletRequest request
										, HttpServletResponse response
										, @RequestParam Map<String, Object> pRequestParamMap )
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 선불 상세  START."));
		logger.info(generateLogMsg("================================================================="));

		printRequest(request);
		logger.info("===========================================");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;

		try{
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			OrgMgmtPpsVo orgMgmtPpsVo = orgMgmtService.getPpsOrgnInfoDetail(pRequestParamMap);
			
			resultMap =  makeResultSingleRow(ppsCustomerVo, orgMgmtPpsVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
			
		} catch (Exception e) {
			 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			 resultMap.put("msg", returnMsg);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 조직 선불 정보 등록/변경
	 * @Param  :
	 * @Return : String
	 * @Author : 김웅
	 * @Create Date : 2014.10. 06.
	  */
	@RequestMapping(value = "/org/orgmgmt/ppsOrgnChgProc.json" )
	public String ppsOrgnChgProc( ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap ){
		

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 선불 정보 등록/변경  START."));
		logger.info(generateLogMsg("================================================================="));

//	    logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));

		printRequest(pRequest);
		logger.info("===========================================");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;

		try{
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = orgMgmtService.ppsOrgnChgProc(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @param request
	 * @param response
	 * @param pRequestParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/org/orgmgmt/getAgncyOrgnList.do", method={POST, GET})
	public String getAgncyOrgnList(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직팝업 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		
		logger.info("loginInfo=" + loginInfo);
		return "org/orgmgmt/msp_org_pu_1002";

	}

	/**
	 * M&S 조직목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/org/orgmgmt/getAgncyOrgnList.json", method={POST, GET})
	public String getAgncyOrgnListJson(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, OrgMgmtVO orgMgmtVO, @RequestParam Map<String, Object> pRequestParamMap){


		logger.info("pRequestParamMap=" + pRequestParamMap);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			List<?> resultList = orgMgmtService.getAgncyOrgList(orgMgmtVO);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch(Exception e){
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 조직정보변경
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/org/orgmgmt/setSessOrgnInfo.json", method={POST, GET})
	public String setSessOrgnInfoJson(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info("pRequestParamMap=" + pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			HttpSession session = pRequest.getSession(true);
			
			session.setAttribute("SESSION_USER_ORGN_ID",		pRequestParamMap.get("orgnId"));
			session.setAttribute("SESSION_USER_ORGN_NM",		pRequestParamMap.get("orgnNm"));
			session.setAttribute("SESSION_USER_ORGN_TYPE_CD",	pRequestParamMap.get("typeCd"));
			session.setAttribute("SESSION_USER_ORGN_LVL_CD",	pRequestParamMap.get("orgnLvlCd"));
			session.setAttribute("SESSION_USER_LOGIS_CNTER_YN",	pRequestParamMap.get("logisCnterYn"));
			session.setAttribute("SESSION_TYPE_DTL_CD1",		pRequestParamMap.get("typeDtlCd1"));
			
			int lSessionPeriodSeconds = Integer.parseInt(StringUtils.defaultString((String)propertyService.getString("sessionPeriodSeconds"),"3600")) ;
			session.setMaxInactiveInterval( lSessionPeriodSeconds );
			
			resultMap.put("code",	messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg",	"");
		}catch(Exception e){
			resultMap.put("code",	messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg",	"");
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	/**
	 * @Description : 조직 유형 대분류를 찾아온다.
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2015. 6. 25.
	 */
	@RequestMapping("/org/orgmgmt/selectOrgnType1.json")
	public String selectOrgnType1(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pReqParamMap, OrgMgmtVO orgMgmtVO)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 조직 유형 대분류 리스트 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			// 본사 권한
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = orgMgmtService.selectOrgnType1(orgMgmtVO);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("조직유형 대분류 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	* @Description : 조직 유형을 찾아온다.
	* @Param  :
	* @Return : String
	* @Author : 장익준
	* @Create Date : 2015. 6. 25.
	 */
	@RequestMapping("/org/orgmgmt/selectOrgnTypeDtl.json")
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
			
			// 본사 권한
			if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			if( request.getParameter("orgnType1") != null )
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType1"));
				orgnType = request.getParameter("orgnType1");
				orgnType = orgMgmtService.selectOrgnTypeDtlCd(orgnType);
				resultList = orgMgmtService.selectOrgnTypeDtl(orgnType);
			}else if( request.getParameter("orgnType2") != null )
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType2"));
				orgnType = request.getParameter("orgnType2");
				orgnType = orgMgmtService.selectOrgnTypeDtlCd(orgnType);
				resultList = orgMgmtService.selectOrgnTypeDtl(orgnType);
			}else if( request.getParameter("orgnType3") != null )
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType3"));
				orgnType = request.getParameter("orgnType3");
				orgnType = orgMgmtService.selectOrgnTypeDtlCd(orgnType);
				resultList = orgMgmtService.selectOrgnTypeDtl(orgnType);
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

	/**
	* @Description : 조직 유형이 변경될때 해당되는 유형을 가져온다. 하위 유형
	* @Param  :
	* @Return : String
	* @Author : 장익준
	* @Create Date : 2015. 6. 25.
	 */
	@RequestMapping("/org/orgmgmt/selectOrgnTypeChg.json")
	public String selectOrgnTypeChg(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 하위 유형을 찾아온다. START."));
		logger.info(generateLogMsg("================================================================="));
		
		String orgnType = null;
		List<?> resultList = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 권한
			if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			if( request.getParameter("orgnType1") != null )
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType1"));
				orgnType = request.getParameter("orgnType1");
				orgnType = orgMgmtService.selectOrgnTypeChg(orgnType);
				resultList = orgMgmtService.selectOrgnTypeDtl(orgnType);
			}else if( request.getParameter("orgnType2") != null )
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType2"));
				orgnType = request.getParameter("orgnType2");
				orgnType = orgMgmtService.selectOrgnTypeChg(orgnType);
				resultList = orgMgmtService.selectOrgnTypeDtl(orgnType);
			}else if( request.getParameter("orgnType3") != null )
			{
				logger.debug(">>>>>>>>>>>>>> result:" + request.getParameter("orgnType3"));
				orgnType = request.getParameter("orgnType3");
				orgnType = orgMgmtService.selectOrgnTypeChg(orgnType);
				resultList = orgMgmtService.selectOrgnTypeDtl(orgnType);
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
	
	/**
	 * 조직관리(신규)
	 */
	@RequestMapping(value = "/org/orgmgmt/msp_org_bs_2001.do", method={POST, GET})
	public ModelAndView viewOrgMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직 관리 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("org/orgmgmt/msp_org_bs_2001");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 조직관리 신규
	 * 2018-02-19
	 */
	@RequestMapping("/org/orgmgmt/updateMgmtNew.json")
	public String updateOrgMgmtNew(OrgMgmtVO orgMgmtVO,
								HttpServletRequest request,
								HttpServletResponse response,
								ModelMap model)
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
			
			// 본사 권한
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			orgMgmtVO.setRegId(loginInfo.getUserId());    /** 사용자ID */
			orgMgmtVO.setRvisnId(loginInfo.getUserId());
			
			OrgMgmtVO hghrOrgMgmtVO = new OrgMgmtVO();
			//현재 조직에 대한 정보
			OrgMgmtVO returnOrgMgmtVO = orgMgmtService.detailOrgMgmt(orgMgmtVO);
	
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
				OrgMgmtVO returnHghrOrgMgmtVO = orgMgmtService.detailOrgMgmt(hghrOrgMgmtVO);
				
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
			
			//v2018.11 PMD 적용 소스 수정
			//int returnCnt = orgMgmtService.updateOrgMgmtNew(orgMgmtVO);
			orgMgmtService.updateOrgMgmtNew(orgMgmtVO);
			
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
	
	/**
	 * 조직변경이력관리
	 */
	@RequestMapping(value = "/org/orgmgmt/msp_org_bs_2002.do", method={POST, GET})
	public ModelAndView viewOrgChgHist(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직변경이력관리 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("org/orgmgmt/msp_org_bs_2002");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	/**
	 * @Description : 조직변경이력 조회
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 김민지
	 * @Create Date : 2019. 2. 28.
	 */
	@RequestMapping("/org/orgmgmt/listOrgChgHist.json")
	public String listOrgChgHist(HttpServletRequest request, HttpServletResponse response, OrgMgmtVO orgMgmtVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직변경이력 조회 START."));
		logger.info(generateLogMsg("================================================================="));


		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			// 본사 권한
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = orgMgmtService.listOrgChgHst(orgMgmtVO, pRequestParamMap);
						
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @throws IOException 
	 * @Description : 조직변경이력 엑셀다운
	 * @Param  : OrgMgmtVO
	 * @Return : String
	 * @Author : 김민지
	 * @Create Date : 2019. 2. 28.
	 */
	@RequestMapping("/org/orgmgmt/listOrgChgHistExcel.json")
	public String listOrgChgHistExcel(HttpServletRequest request, HttpServletResponse response, OrgMgmtVO orgMgmtVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) throws IOException{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("조직변경이력 엑셀 다운 START."));
		logger.info(generateLogMsg("Return Vo [orgMgmtVO] = " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));


		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			// 본사 권한
			if(!"10".equals(orgMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> aryRsltList = orgMgmtService.listOrgChgHstExcel(orgMgmtVO, pRequestParamMap);
						
			String serverInfo = "";
			try {
				serverInfo = propertyService.getString("excelPath");
			} catch (Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo + "조직변경이력_";// 파일명
			String strSheetname = "조직변경이력";// 시트명
			
			String[] strHead = {  "변경일자", "조직ID", "조직명", "조직유형", "KT조직ID"
								, "상위조직", "상태", "사용유무", "적용종료일", "지번주소우편번호"
								, "지번주소기본내용", "지번주소상세내용", "지번주소광역명", "지번주소중역명", "지번주소중소역명"
								, "지번주소면동리명", "지번주소번지명", "지번주소동코드", "도로명주소우편번호", "도로명주소기본내용"
								, "도로명주소상세내용", "도로명주소광역명", "도로명주소중역명", "도로명주소중소역명", "도로명주소도로명"
								, "도로명주소건물번호", "주소참고내용", "마케팅매니저ID", "MVNO조직ID" };
			String[] strValue = { "orgChgDate", "orgnId", "orgnNm", "typeNm", "ktOrgId"
								, "hghrOrgnNm", "orgnStatNm", "useYn", "applCmpltDt", "arnoAdrZipcd"
								, "arnoAdrBasSbst", "arnoAdrDtlSbst", "arnoAdrBroadNm", "arnoAdrMdarNm", "arnoAdrBmasNm"
								, "arnoAdrMyunDnglNm", "arnoAdrBjNm", "arnoAdrDongCd", "roadnAdrZipcd", "roadnAdrBasSbst"
								, "roadnAdrDtlSbst", "roadnAdrBroadNm", "roadnAdrMdarNm", "roadnAdrBmasNm", "roadnAdrRoadNm"
								, "roadnAdrBldgNo", "adrRfrnSbst", "admUserId", "mvnoOrgId" };
			int[] intWidth = {    3000, 4000, 7000, 3000, 3000
								, 7000, 2000, 3000, 4000, 5000
								, 8000, 10000, 5000, 5000, 5000
								, 5000, 10000, 5000, 7000, 12000
								, 10000, 5000, 5000, 6000, 8000
								, 7000, 8000, 5000, 4000};
			int[] intLen = {  0, 0, 0, 0, 0
							, 0, 0, 0, 0, 0
							, 0, 0, 0, 0, 0
							, 0, 0, 0, 0, 0
							, 0, 0, 0, 0, 0
							, 0, 0, 0, 0};
			
			// 파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, aryRsltList, strHead, intWidth, strValue, request, response, intLen);

			logger.info("strFileName : " + strFileName);
			logger.info("#################### 데이터 엑셀로 변환 종료 #####################");
			
			file = new File(strFileName);
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}
			
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");
				
				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "ORG"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); // 사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}finally{
			/** 20230518 PMD 조치 */
			if (out != null) out.close();
			if (in != null) in.close();		
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
