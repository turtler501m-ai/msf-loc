package com.ktmmobile.mcp.event.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.BannerDto;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.cprt.dto.AliasMapWrapper;
import com.ktmmobile.mcp.cprt.dto.AlliCardContDto;
import com.ktmmobile.mcp.cprt.dto.AlliCardContDtoXML;
import com.ktmmobile.mcp.cprt.dto.AlliCardDtlContDtoXML;
import com.ktmmobile.mcp.event.service.CprtCardEventService;

@Controller
public class CprtCardEventController {

    private static Logger logger = LoggerFactory.getLogger(CprtCardEventController.class);

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    @Autowired
    CprtCardEventService cprtCardEventService;

    /**
     * 설명 : 제휴카드 페이지 이동
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param alliCardContDto
     * @param model
     * @return
     */
    @RequestMapping(value={"/event/cprtCardList.do","/m/event/cprtCardList.do"})
    public String cprtCardList(HttpServletRequest request, AlliCardContDto alliCardContDto, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/event/cprtCardList";
        }else {
            returnUrl =  "/portal/event/cprtCardList";
        }

        model.addAttribute("alliCardContDto", alliCardContDto);
        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("E006")); // bannCtg

        return returnUrl;
    }

    /**
     * 설명 : 카테고리 xml 파일의 prodList 정보 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/event/cprtCardXmlListAjax.do","/m/event/cprtCardXmlListAjax.do"})
    @ResponseBody
    public AliasMapWrapper cprtCardXmlListAjax(HttpServletRequest request, HttpServletResponse response) {
        AliasMapWrapper aliasMapWrapper = cprtCardEventService.getAliasMapWrapper();

        return aliasMapWrapper;
    }

    /**
     * 설명 : 제휴카드 정보 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param AlliCardContDto
     * @param model
     * @return
     */
    @RequestMapping(value={"/event/cprtCardPanel.do","/m/event/cprtCardPanel.do"})
    public String cprtCardPanel(AlliCardContDto AlliCardContDto, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/event/cprtCardPanel";
        }else {
            returnUrl =  "/portal/event/cprtCardPanel";
        }

        // 기본 제휴카드정보 xml 조회
        AlliCardDtlContDtoXML gdncBas = cprtCardEventService.getCprtCardGdncBasXml(AlliCardContDto);
        // 제휴카드 혜택 상세 xml조회
        List<AlliCardContDtoXML> bnfitList = cprtCardEventService.getCprtCardBnfitGdncDtlXmlList(AlliCardContDto);
        // 제휴카드 상세내역 xml 조회
        List<AlliCardContDtoXML> dtlList = cprtCardEventService.getCprtCardGdncDtlXmlList(AlliCardContDto);
        // 제휴카드 링크상세 xml조회
        List<AlliCardContDtoXML> linkList = cprtCardEventService.getCprtCardLinkXmlList(AlliCardContDto);

        model.addAttribute("cprtCardCtgCd", AlliCardContDto.getCprtCardCtgCd());
        model.addAttribute("gdncBas", gdncBas);
        model.addAttribute("bnfitList", bnfitList);
        model.addAttribute("dtlList", dtlList);
        model.addAttribute("linkList", linkList);
        model.addAttribute("fileuploadBase", fileuploadBase);
        model.addAttribute("fileuploadWeb", fileuploadWeb);

        return returnUrl;
    }

    /**
     * 설명 : 가입가이드 팝업 페이지 이동
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param alliCardContDto
     * @param model
     * @return
     */
    @RequestMapping(value={"/event/cprtCardRegLayer.do","/m/event/cprtCardRegLayer.do"})
    public String cardRegLayer(AlliCardContDto alliCardContDto, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/event/cprtCardRegLayer";
        }else {
            returnUrl =  "/portal/event/cprtCardRegLayer";
        }

        // 제휴카드 상세내역 xml 조회
        List<AlliCardContDtoXML> dtlList = cprtCardEventService.getCprtCardGdncDtlXmlList(alliCardContDto);
        // 제휴카드 링크 xml 조회
        List<AlliCardContDtoXML> linkList = cprtCardEventService.getCprtCardLinkXmlList(alliCardContDto);

        model.addAttribute("dtlList", dtlList);
        model.addAttribute("linkList", linkList);

        return returnUrl;
    }

    /**
     * 설명 : 혜택비교하기 팝업 페이지 이동
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param alliCardContDto
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value={"/event/cprtCardBnfitLayer.do","/m/event/cprtCardBnfitLayer.do"})
    public String bnfitLayer(AlliCardContDto alliCardContDto, Model model) throws ParseException {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/event/cprtCardBnfitLayer";
        }else {
            returnUrl =  "/portal/event/cprtCardBnfitLayer";
        }

        // 제휴카드 안내 목록 조회
        List<AlliCardContDto> prodList = cprtCardEventService.getProdXmlList();

        for(AlliCardContDto prodDto : prodList) {

            // 기본 제휴카드정보 xml 조회
            AlliCardDtlContDtoXML gdncBas = cprtCardEventService.getCprtCardGdncBasXml(prodDto);
            // 제휴카드 혜택 상세 xml조회
            List<AlliCardContDtoXML> bnfitList = cprtCardEventService.getCprtCardBnfitGdncDtlXmlList(prodDto);

            if(gdncBas != null) {
                prodDto.setCprtCardThumbImgNm(gdncBas.getCprtCardThumbImgNm());
            }

            if(bnfitList != null) {
                for(AlliCardContDtoXML bnfitDto : bnfitList) {
                    if(("PCARD104").equals(bnfitDto.getCprtCardBnfitItemCd())) {
                        prodDto.getBnfitLists1().add(bnfitDto);
                    }
                    if(("PCARD106").equals(bnfitDto.getCprtCardBnfitItemCd())) {
                        prodDto.getBnfitLists2().add(bnfitDto);
                    }
                }
            }
        }

        model.addAttribute("prodList", prodList);
        model.addAttribute("fileuploadWeb", fileuploadWeb);

        return returnUrl;
    }

    /**
     * 설명 : 제휴카드 이벤트 팝업 페이지 이동
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param alliCardContDto
     * @param model
     * @return
     */
    @RequestMapping(value={"/event/cprtCardEventLayer.do","/m/event/cprtCardEventLayer.do"})
    public String cprtCardLayer(AlliCardContDto alliCardContDto, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/event/cprtCardEventLayer";
        }else {
            returnUrl =  "/portal/event/cprtCardEventLayer";
        }

        return returnUrl;
    }

    /**
     * 설명 : 배너목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param bannCtg
     * @return
     */
    @RequestMapping(value={"/event/cardBannerListAjax.do","/m/event/cardBannerListAjax.do"})
    @ResponseBody
    public JsonReturnDto bannerListAjax(HttpServletRequest request, @RequestParam("bannCtg") String bannCtg){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        // 배너 목록 조회
        List<BannerDto> bannerList = NmcpServiceUtils.getBannerList(bannCtg);

        if(bannerList != null) {
            logger.debug("bannerList len :{}", bannerList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("bannerList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(bannerList);

        return result;
    }
}
