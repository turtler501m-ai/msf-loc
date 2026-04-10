package com.ktmmobile.mcp.cs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.board.service.BoardCommonSvc;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.cs.service.CsFaqSvc;

@Controller
public class CsServiceGuideController {

	private static Logger logger = LoggerFactory.getLogger(CsFaqController.class);

	@Autowired
	private CsFaqSvc csFaqSvc;

	@Autowired
	private BoardCommonSvc boardCommonSvc;

	/**
	 * <pre>
	 * 설명     : 고객센터 > 이용안내 > 고객센터안내
	 * &#64;return
	 * &#64;return: String
	 * </pre>
	 */
	@RequestMapping(value = { "/cs/serviceGuide.do", "/m/cs/serviceGuide.do" })
	public String serviceGuide(Model model) {

		// 로그인한 고객만 보이기 나중에 추가작업 필요
		UserSessionDto userSession = SessionUtils.getUserCookieBean();
		String userDivision = "00";
		if (userSession != null) {
			userDivision = StringUtil.NVL(userSession.getUserDivision(), "00");
		}
		String rtnPageNm = "";
		// 모바일 분기처리
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			rtnPageNm = "/mobile/cs/guide/serviceGuide";
		} else {
			rtnPageNm = "/portal/cs/guide/serviceGuide";
		}

		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			rtnPageNm = "/mobile/cs/guide/serviceGuide";
		} else {
			rtnPageNm = "/portal/cs/guide/serviceGuide";
		}

		int selectCnt = 5; // top 조회할 개수
		BoardDto boardDto = new BoardDto();
		boardDto.setSelectCnt(selectCnt);
		List<BoardDto> faqTopList = csFaqSvc.getTopFormList(boardDto); // top10 리스트

		model.addAttribute("userDivision", userDivision);
		model.addAttribute("faqTopList", faqTopList);

		return rtnPageNm;
	}

	/**
	 * <pre>
	 * 설명     : 고객센터 > 이용안내 > 고객센터안내 > 자주묻는 질문 카운트 증가
	 * </pre>
	 */
	@RequestMapping(value = { "/cs/serviceGuideCntAjax.do" })
	@ResponseBody
	public Map<String, Object> serviceGuideCntAjax(@ModelAttribute BoardDto boardDto) {

		Map<String, Object> rtnJson = new HashMap<String, Object>();

		int boardSeq = boardDto.getBoardSeq();
		int cnt = 0;
		int boardHitCnt = 0;
		try {
			cnt = boardCommonSvc.updateHit(boardSeq);
			if (cnt > 0) {
				BoardDto resDto = csFaqSvc.getBoardHitCnt(boardSeq);
				if (resDto != null) {
					boardHitCnt = resDto.getBoardHitCnt();
				}
			}
		} catch (DataAccessException e) {
			logger.debug("DataAccessException ERROR updateHit");
		} catch (Exception e) {
			logger.debug("ERROR updateHit");
		}
		rtnJson.put("cnt", cnt);
		rtnJson.put("boardHitCnt", boardHitCnt);
		return rtnJson;
	}

	/**
	 * <pre>
	 * 설명     : 고객센터 > 이용안내 > 고객센터안내 > ARS 안내
	 * </pre>
	 */
	@RequestMapping(value = { "/cs/serviceGuideArsHtml.do", "/m/cs/serviceGuideArsHtml.do" })
	public String serviceGuideArsHtml(Model model) {

		String rtnPageNm = "";
		// 모바일 분기처리
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			rtnPageNm = "/mobile/cs/html/serviceGideArsHtml";
		} else {
			rtnPageNm = "/portal/cs/html/serviceGideArsHtml";
		}

		return rtnPageNm;
	}

	/**
	 * <pre>
	 * 설명     : 고객센터 > 이용안내 > 유심및 단말 개통 가이드
	 * </pre>
	 */
	@RequestMapping(value = { "/mmobile/ktmMobileUsimGuid.do", "/m/mmobile/ktmMobileUsimGuidView.do" })
	public String ktmMobileUsimGuidView(Model model,
			@RequestParam(value = "tab", required = false, defaultValue = "1") String tab) {

		String rtnPageNm = "";

		// 모바일 분기처리
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			rtnPageNm = "/mobile/cs/guide/ktmMobileUsimGuidView";
		} else {
			rtnPageNm = "/portal/cs/guide/ktmMobileUsimGuidView";
		}

		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			rtnPageNm = "/mobile/cs/guide/ktmMobileUsimGuidView";
		} else {
			rtnPageNm = "/portal/cs/guide/ktmMobileUsimGuidView";
		}

		logger.debug("url : " + rtnPageNm);
		model.addAttribute("tab", tab);
		return rtnPageNm;
	}

	/**
	 * <pre>
	 * 설명     : 고객센터 > 이용안내 > kt M모바일 앱 안내
	 * </pre>
	 */
	@RequestMapping(value = { "/cs/ktMobileApp.do", "/m/cs/ktMobileAppView.do" })
	public String ktMobileAppView(Model model) {

		String rtnPageNm = "";
		// 모바일 분기처리
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			rtnPageNm = "/mobile/cs/guide/ktMobileAppView";
		} else {
			rtnPageNm = "/portal/cs/guide/ktMobileAppView";
		}

		return rtnPageNm;
	}

	/**
	 * <pre>
	 * 설명     : 고객센터 > 이용안내 > 단말기 AS 안내
	 * </pre>
	 */
	@RequestMapping(value = { "/cs/deviceAsInfo.do", "/m/cs/mDeviceAsView.do" })
	public String mDeviceAsView(Model model) {

		String rtnPageNm = "";
		// 모바일 분기처리
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			rtnPageNm = "/mobile/cs/guide/mDeviceAsView";
		} else {
			rtnPageNm = "/portal/cs/guide/deviceAsInfo";
		}
		logger.debug("url : " + rtnPageNm);
		return rtnPageNm;
	}

	/**
	 * <pre>
	 * 설명     : 고객센터 > 셀프서비스 > 서비스 조회/변경
	 * &#64;Author : 박성훈
	 * &#64;Date : 2022.05.16
	 * </pre>
	 */
	@RequestMapping(value = { "/cs/selfServiceInquiry.do", "/m/cs/selfServiceInquiry.do" })
	public String selfServiceInquiry(Model model) {

		String rtnPageNm = "";
		// 모바일 분기처리
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			rtnPageNm = "/mobile/cs/guide/selfServiceInquiry";
		} else {
			rtnPageNm = "/portal/cs/guide/selfServiceInquiry";
		}

		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			rtnPageNm = "/mobile/cs/guide/selfServiceInquiry";
		} else {
			rtnPageNm = "/portal/cs/guide/selfServiceInquiry";
		}

		return rtnPageNm;
	}

	/**
	 * <pre>
	 * 설명     : 고객센터 > 셀프서비스 > 요금조회 및 납부
	 * &#64;Author : 박성훈
	 * &#64;Date : 2022.05.16
	 * </pre>
	 */
	@RequestMapping(value = { "/cs/selfServiceCharge.do", "/m/cs/selfServiceCharge.do" })
	public String selfServiceCharge(Model model) {

		String rtnPageNm = "";
		// 모바일 분기처리
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			rtnPageNm = "/mobile/cs/guide/selfServiceCharge";
		} else {
			rtnPageNm = "/portal/cs/guide/selfServiceCharge";
		}

		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			rtnPageNm = "/mobile/cs/guide/selfServiceCharge";
		} else {
			rtnPageNm = "/portal/cs/guide/selfServiceCharge";
		}

		return rtnPageNm;
	}



}
