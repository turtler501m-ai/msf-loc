package com.ktmmobile.mcp.ktPolicy.controller;

import static com.ktmmobile.mcp.common.constants.Constants.SITE_CODE_DEFAULT;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ktmmobile.mcp.common.dto.PersonalPolicyDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.cs.service.CsInquirySvc;
import com.ktmmobile.mcp.ktPolicy.dto.PolicyDto;
import com.ktmmobile.mcp.ktPolicy.service.PolicySvc;

@Controller
public class PolicyController {

	private static final Logger logger = LoggerFactory.getLogger(PolicyController.class);

	@Autowired
	private PolicySvc policySvc;

	@Autowired
	private CsInquirySvc csInquirySvc;

	/**
	 * @Description : 이용약관 > 개인정보 처리방침
	 * @param model
	 * @return
	 * @Author : ant
	 * @Create Date : 2016. 2. 19
	 */
	@RequestMapping(value="/m/policy/privacyList.do")
	public String mKtPolicy(@ModelAttribute PageInfoBean pageInfoBean, @ModelAttribute PolicyDto policyDto, Model model) {

		String stpltCtgCd = "06";
		int pageNo = policyDto.getPageNo();
		if(pageNo==0) {
			policyDto.setPageNo(1);
		}
		model.addAttribute("policyDto",policyDto);
		model.addAttribute("stpltCtgCd",stpltCtgCd);
		return "/mobile/policy/mPrivacyList";
	}

	/**
	 * @Description : 이용약관 > 개인정보 처리방침 리스트조회
	 * @param model
	 * @return
	 * @Author : ant
	 * @Create Date : 2016. 2. 19
	 */
	@RequestMapping(value = "/policy/mPrivacyListAjax.do")
	@ResponseBody
	public Map<String, Object> mPrivacyListAjax(@ModelAttribute PolicyDto policyDto,BindingResult bind, @ModelAttribute PageInfoBean pageInfoBean ) {

		if (bind.hasErrors()) {
			throw new McpCommonException(BIDING_EXCEPTION);
		}

		HashMap<String, Object> rtnMap = new HashMap<String, Object>();

		// 현재 페이지 번호 초기화
		if(pageInfoBean.getPageNo() == 0){
			pageInfoBean.setPageNo(1);
		}
		//임시 한페이지당 보여줄 리스트 수
		pageInfoBean.setRecordCount(10);

		int totalCount = 0;
		int listCount = 0;
		List<PolicyDto> policyDtoList = null;
		try {
			policyDto.setSiteCode(SITE_CODE_DEFAULT);
			// 카운트
			totalCount = policySvc.getPriTotalCount(policyDto);
			pageInfoBean.setTotalCount(totalCount);
			int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
			int maxResult   = pageInfoBean.getRecordCount();  // Pagesize
			// 조회
			policyDtoList = policySvc.privacyBoardList(policyDto,skipResult,maxResult);
			listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + policyDtoList.size();

		}catch(DataAccessException e) {
			logger.debug("DataAccessException error");
		} catch(Exception e) {
			logger.debug("error");
		}
		//페이지 카운트

		rtnMap.put("listCount", listCount); //일반게시판
		rtnMap.put("policyDtoList", policyDtoList); //일반게시판
		rtnMap.put("pageInfoBean", pageInfoBean);
		rtnMap.put("pageNo",pageInfoBean.getPageNo());
		rtnMap.put("totalCount", totalCount);
		rtnMap.put("RESULT_CODE", "0001");

		return rtnMap;
	}

	/**
	 * @Description : 이용약관 > 개인정보 처리방침
	 * @param model
	 * @return
	 * @Author : ant
	 * @Create Date : 2016. 2. 19
	 */
	@RequestMapping(value="/policy/privacyList.do")
	public String privacyList(@ModelAttribute PageInfoBean pageInfoBean, @ModelAttribute PolicyDto policyDto, Model model) {

		// 현재 페이지 번호 초기화
		if(pageInfoBean.getPageNo() == 0){
			pageInfoBean.setPageNo(1);
		}
		//임시 한페이지당 보여줄 리스트 수
		pageInfoBean.setRecordCount(10);

		policyDto.setSiteCode(SITE_CODE_DEFAULT);
		// 카운트
		int totalCount = policySvc.getPriTotalCount(policyDto);
		pageInfoBean.setTotalCount(totalCount);
		int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
		int maxResult   = pageInfoBean.getRecordCount();  // Pagesize
		// 조회
		List<PolicyDto> policyDtoList = policySvc.privacyBoardList(policyDto,skipResult,maxResult);

		model.addAttribute("policyDtoList", policyDtoList); //일반게시판
		model.addAttribute("pageInfoBean", pageInfoBean);
		model.addAttribute("policyDto", policyDto);
		model.addAttribute("totalCount", totalCount);


		return "/portal/policy/privacyList";
	}

	/**
	 * @Description : 이용약관 > 개인정보 처리방침 상세보기
	 * @param model
	 * @return
	 * @Author : ant
	 * @Create Date : 2016. 1. 25
	 */
	@RequestMapping(value = {"/m/policy/mPrivacyView.do","/policy/privacyView.do"})
	public String mPrivacyView(@ModelAttribute PolicyDto policyDto,Model model) {

		String rtnPageNm = "";
    	if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/policy/mPrivacyView";
        } else {
        	rtnPageNm = "/portal/policy/privacyView";
        }

    	policyDto.setSiteCode(SITE_CODE_DEFAULT);

        //공지 글 상세
    	PolicyDto viewDto = policySvc.viewPrivacyBoard(policyDto);
    	int stpltSeq = 0;
    	if(viewDto !=null) {
    		String stpltSbst = StringUtil.NVL(viewDto.getStpltSbst(),"");
    		viewDto.setStpltSbst(ParseHtmlTagUtil.convertHtmlchars(stpltSbst));
    		stpltSeq = viewDto.getStpltSeq();
    	}
    	this.histPlusCount(stpltSeq);

        model.addAttribute("viewDto",viewDto);
        model.addAttribute("policyDto",policyDto);

      return rtnPageNm;
   }


	/**
	 * @Description : 이용약관 > 이용약관
	 * @param model
	 * @return
	 * @Author : ant
	 * @Create Date : 2016. 2. 19
	*/
	@RequestMapping(value="/m/policy/ktPolicy.do")
	public String ktPolicyList(@ModelAttribute PageInfoBean pageInfoBean, @ModelAttribute PolicyDto policyDto, Model model,@RequestParam(defaultValue="") String detail ,RedirectAttributes redirect) {

		int paramStpltSeq = policyDto.getStpltSeq();
		// 현재 페이지 번호 초기화
		if(pageInfoBean.getPageNo() == 0){
			pageInfoBean.setPageNo(1);
		}
		//임시 한페이지당 보여줄 리스트 수
		pageInfoBean.setRecordCount(10);

		// 카운트
		int totalCount = policySvc.getTotalCount(policyDto);
		pageInfoBean.setTotalCount(totalCount);
		// 조회
		List<PolicyDto> policyDtoList = policySvc.policyBoardList(policyDto,0,999);

		List<HashedMap> policyCategory = csInquirySvc.selectGroupCode("TERMS");

		model.addAttribute("policyCategory", policyCategory);
		model.addAttribute("policyDtoList", policyDtoList); //일반게시판
		model.addAttribute("pageInfoBean", pageInfoBean);
		model.addAttribute("paramStpltSeq", paramStpltSeq);

		if("footer".equals(detail) && policyDtoList !=null && !policyDtoList.isEmpty()) {

			int stpltSeq = policyDtoList.get(0).getStpltSeq();
			String stpltCtgCd = StringUtil.NVL(policyDtoList.get(0).getStpltCtgCd(),"00");
			redirect.addAttribute("pageNo", 1);
			redirect.addAttribute("stpltSeq", stpltSeq);
			redirect.addAttribute("stpltCtgCd",stpltCtgCd);
			return "redirect:/m/policy/mKtPolicyView.do";
		}
		model.addAttribute("totalCount", totalCount);
		return "/mobile/policy/mKtPolicyList";
	}



	/**
     * @Description : 이용약관 > 이용약관
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 25
     */
    @RequestMapping(value="/policy/ktPolicy.do")
    public String ktPolicy(@ModelAttribute PageInfoBean pageInfoBean, @ModelAttribute PolicyDto policyDto, Model model,@RequestParam(defaultValue="") String detail ,RedirectAttributes redirect) {

    	// 현재 페이지 번호 초기화
    	if(pageInfoBean.getPageNo() == 0){
    		pageInfoBean.setPageNo(1);
    	}
    	//임시 한페이지당 보여줄 리스트 수
    	pageInfoBean.setRecordCount(10);

    	// 카운트
    	int totalCount = policySvc.getTotalCount(policyDto);
    	pageInfoBean.setTotalCount(totalCount);
    	int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    	int maxResult   = pageInfoBean.getRecordCount();  // Pagesize
    	// 조회

    	List<PolicyDto> policyDtoList = policySvc.policyBoardList(policyDto,skipResult,maxResult);

    	List<HashedMap> policyCategory = csInquirySvc.selectGroupCode("TERMS");

    	model.addAttribute("policyCategory", policyCategory);
    	model.addAttribute("policyDtoList", policyDtoList); //일반게시판
    	model.addAttribute("pageInfoBean", pageInfoBean);

    	if("footer".equals(detail) && policyDtoList !=null && !policyDtoList.isEmpty()) {
    		int stpltSeq = policyDtoList.get(0).getStpltSeq();
    		String stpltCtgCd = StringUtil.NVL(policyDtoList.get(0).getStpltCtgCd(),"00");
    		redirect.addAttribute("pageNo", 1);
    		redirect.addAttribute("stpltSeq", stpltSeq);
    		redirect.addAttribute("stpltCtgCd",stpltCtgCd);
            return "redirect:/policy/ktPolicyView.do";
    	}
    	model.addAttribute("totalCount", totalCount);
    	return "/portal/policy/ktPolicy";
    }

	/**
	 * @Description : 이용약관 > 이용약관 > 상세보기
	 * @param model
	 * @return
	 * @Author : ant
	 * @Create Date : 2016. 2. 19
	*/
	@RequestMapping(value= {"/m/policy/mKtPolicyView.do","/policy/ktPolicyView.do"})
	public String mKtPolicyView(@ModelAttribute PageInfoBean pageInfoBean, @ModelAttribute PolicyDto policyDto, Model model) {

		String rtnPageNm = "";
    	if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/policy/mKtPolicyView";
        } else {
        	rtnPageNm = "/portal/policy/ktPolicyView";
        }

		//이용약관 상세 조회
		PolicyDto viewDto = policySvc.viewKtPolicy(policyDto);
		String stpltSbst = "";
		int stpltSeq = 0;
		if(viewDto !=null) {
			stpltSbst = StringUtil.NVL(viewDto.getStpltSbst(),"");
			viewDto.setStpltSbst(ParseHtmlTagUtil.convertHtmlchars(stpltSbst));
			stpltSeq = viewDto.getStpltSeq();
		}
		this.histPlusCount(stpltSeq);
		String paramStpltCtgCd = policyDto.getParamStpltCtgCd();
		model.addAttribute("viewDto",viewDto);
		model.addAttribute("pageInfoBean",pageInfoBean);
		model.addAttribute("paramStpltCtgCd",paramStpltCtgCd);
		return rtnPageNm;
	}


	/**
	 * @Description : 이용약관 > 이용약관 주요설명
	 * @param model
	 * @return
	 * @Author : ant
	 * @Create Date : 2019. 7. 9
	 */
	@RequestMapping(value= {"/m/policy/policyGuideView.do","/policy/policyGuideView.do"})
	public String mPolicyGuideView(@ModelAttribute PolicyDto policyDto, Model model) {

		String rtnPageNm = "";
    	if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/policy/mPolicyGuideView";
        } else {
        	rtnPageNm = "/portal/policy/policyGuideView";
        }

		/*이용약관 주요설명서 가져오기 */
		List<PolicyDto> policyDtoList = policySvc.getPolicyGuide(policyDto);
		if(policyDtoList !=null && !policyDtoList.isEmpty()) {
			String stpltSbst = "";
			int stpltSeq = 0;
			for(PolicyDto dto : policyDtoList) {
				stpltSbst = StringUtil.NVL(dto.getStpltSbst(),"");
				dto.setStpltSbst(ParseHtmlTagUtil.convertHtmlchars(stpltSbst));
				stpltSeq = dto.getStpltSeq();
				this.histPlusCount(stpltSeq);
			}
		}

		model.addAttribute("policyDtoList",policyDtoList);
		return rtnPageNm;
	}

	/**
	 * @Description : 이용약관 > 청소년 보호 정책
	 * @param model
	 * @return
	 * @Author : ant
	 * @Create Date : 2016. 1. 25
	 */
	@RequestMapping(value= {"/m/policy/youngList.do","/policy/youngList.do"})
	public String mYoungList(@ModelAttribute PolicyDto policyDto, Model model) {

		String rtnPageNm = "";
    	if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/policy/mYoungList";
        } else {
        	rtnPageNm = "/portal/policy/youngList";
        }

		/*청소년 보호 정책 가져오기*/
		List<PolicyDto> policyDtoList = policySvc.youngBoardList(policyDto);
		if(policyDtoList !=null && !policyDtoList.isEmpty()) {
			String stpltSbst = "";
			int stpltSeq = 0;
			for(PolicyDto dto : policyDtoList) {
				stpltSbst = StringUtil.NVL(dto.getStpltSbst(),"");
				dto.setStpltSbst(ParseHtmlTagUtil.convertHtmlchars(stpltSbst));
				stpltSeq = dto.getStpltSeq();
				this.histPlusCount(stpltSeq);
			}
		}
		model.addAttribute("policyDtoList",policyDtoList);
		return rtnPageNm;
	}

	private void histPlusCount(int stpltSeq) {

		PersonalPolicyDto personalPolicyDto = new PersonalPolicyDto();
		if(stpltSeq > 0) {
			try {
	    		personalPolicyDto.setStpltSeq(stpltSeq);
	    		policySvc.histPlusCount(personalPolicyDto);
	    	} catch(DataAccessException e) {
				logger.debug("eDataAccessException rror");
			} catch(Exception e) {
	    		logger.debug("error");
	    	}
		}
	}

}