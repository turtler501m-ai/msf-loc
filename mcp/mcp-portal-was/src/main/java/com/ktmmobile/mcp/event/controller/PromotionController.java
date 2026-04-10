package com.ktmmobile.mcp.event.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.event.dto.AgreeTermsEventDto;
import com.ktmmobile.mcp.event.dto.PromotionDto;
import com.ktmmobile.mcp.event.service.AgreeTermsEventService;
import com.ktmmobile.mcp.event.service.PromotionSvc;

@Controller
public class PromotionController {

    private static Logger logger = LoggerFactory.getLogger(PromotionController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private PromotionSvc promotionSvc;

    @Autowired
    private AgreeTermsEventService agreeTermsEventService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    SmsSvc smsSvc ;

    public static final String COMM_AUTH_SMS_INFO = "COMM_AUTH_SMS_INFO";

    @RequestMapping(value = {"/event/promotionPop.do" , "/m/event/promotionPop.do"})
    public String promotionPop(HttpServletRequest request, @ModelAttribute PromotionDto promotionDto, Model model ) {

        String eventCode = StringUtil.NVL(promotionDto.getCode(),"");
        if("".equals(eventCode)){
            logger.info("error");
        }

        String rtnPageNm = "/portal/event/promotionPop";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/event/promotionPop";
        }

        /*// 유의사항 1
        FormDtlDTO formDtlDTO1 = new FormDtlDTO();
        String cdGroupId1 = "FormEtcCla";
        String cdGroupId2 = "Promotion3";
        formDtlDTO1.setCdGroupId1(cdGroupId1);
        formDtlDTO1.setCdGroupId2(cdGroupId2);
        FormDtlDTO formDtlRtn1 = appformSvc.getFormDesc(formDtlDTO1);// docver
        String docVer1 = "";
        if(formDtlRtn1 !=null){
            docVer1 = formDtlRtn1.getDocVer();
        }
        FormDtlDTO oFormDtlDTO1 = new FormDtlDTO();
        oFormDtlDTO1.setCdGroupId1(cdGroupId1);
        oFormDtlDTO1.setCdGroupId2(cdGroupId2);
        oFormDtlDTO1.setDocVer(docVer1);
        FormDtlDTO rtnObj1 = formDtlSvc.FormDtlView(oFormDtlDTO1); // 내용가져오기
        String docContent1 = "";
        if (rtnObj1 != null) {
            docContent1 = StringEscapeUtils.unescapeHtml(rtnObj1.getDocContent());
        }

        // 유의사항 2
        FormDtlDTO formDtlDTO2 = new FormDtlDTO();
        cdGroupId2 = "Promotion4";
        formDtlDTO2.setCdGroupId1(cdGroupId1);
        formDtlDTO2.setCdGroupId2(cdGroupId2);
        FormDtlDTO formDtlRtn2 = appformSvc.getFormDesc(formDtlDTO2);// docver
        String docVer2 = "";
        if(formDtlRtn2 !=null){
            docVer2 = formDtlRtn2.getDocVer();
        }
        FormDtlDTO oFormDtlDTO2 = new FormDtlDTO();
        oFormDtlDTO2.setCdGroupId1(cdGroupId1);
        oFormDtlDTO2.setCdGroupId2(cdGroupId2);
        oFormDtlDTO2.setDocVer(docVer2);
        FormDtlDTO rtnObj2 = formDtlSvc.FormDtlView(oFormDtlDTO2); // 내용가져오기
        String docContent2 = "";
        if (rtnObj2 != null) {
            docContent2 = StringEscapeUtils.unescapeHtml(rtnObj2.getDocContent());
        }*/
        String cdGroupId1 = "FormEtcCla";
        String docContent1 = promotionSvc.getDocumentContent(cdGroupId1, "Promotion1");
        String docContent2 = promotionSvc.getDocumentContent(cdGroupId1, "Promotion2");
        String docContent3 = promotionSvc.getDocumentContent(cdGroupId1, "Promotion3");
        String docContent4 = promotionSvc.getDocumentContent(cdGroupId1, "Promotion4");

        model.addAttribute("eventCode", eventCode);
        model.addAttribute("docContent1", docContent1);
        model.addAttribute("docContent2", docContent2);
        model.addAttribute("docContent3", docContent3);
        model.addAttribute("docContent4", docContent4);

        return rtnPageNm;
    }


    @RequestMapping(value = "/event/promotionCheckAjax.do")
    @ResponseBody
    public Map<String,Object> promotionCheckAjax(@ModelAttribute PromotionDto promotionDto , Model model ) {

        Map<String,Object> hm = new HashMap<String,Object>();
        int cnt = promotionSvc.getDoubleCheckCtn(promotionDto);
        String resultCode = "S";
        if (cnt >= 1) {
            resultCode = "E";
        }
        hm.put("resultCode", resultCode);
        return hm;
    }

    @RequestMapping(value = "/event/promotionRegAjax.do")
    @ResponseBody
    public Map<String,Object> promotionRegAjax(@ModelAttribute PromotionDto promotionDto , Model model ) {

        // TEST
        /*if ("LOCAL".equals(serverName)) {
            promotionDto.setCode("Z04");
            promotionDto.setName("이준후");
            promotionDto.setPhone("01077778888");
            promotionDto.setAgree("Y");
            promotionDto.setBirthDate("20000101");
            promotionDto.setCi("fOOBa8su482jkfsjuidf");
            promotionDto.setDi("89871243jkjkajhsiduawshkm");
            promotionDto.setUseTelCode("KT");
            promotionDto.setUsePayAmt("1~3만원대");
            promotionDto.setSel1("Y");
            promotionDto.setSel2("N");
            promotionDto.setSel3("N");
        }*/

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        Map<String,Object> hm = new HashMap<String,Object>();

        // 본인인증 세션 검사 (간편본인인증 > 휴대폰인증)
        NiceResDto niceResDtoSess = SessionUtils.getNiceResCookieBean();
        if(niceResDtoSess == null
           || StringUtil.isEmpty(niceResDtoSess.getConnInfo())
           || StringUtil.isEmpty(niceResDtoSess.getDupInfo())){

            hm.put("resultCode", "E");
            return hm;
        }

        // 본인인증 정보 추가 검증
        String autBirthDate = StringUtil.NVL(niceResDtoSess.getBirthDate(), "");
        String autName = StringUtil.NVL(niceResDtoSess.getName(), "").replaceAll(" ", ""); // dto에서 upper처리
        String authMobileNo = StringUtil.NVL(niceResDtoSess.getsMobileNo(), "");

        String inpBirthDate = StringUtil.NVL(promotionDto.getBirthDate(), ""); // yyyymmdd
        String inpName = StringUtil.NVL(promotionDto.getName(), "").toUpperCase().replaceAll(" ", "");
        String inpMobileNo = StringUtil.NVL(promotionDto.getPhone(), "");

        if(inpBirthDate.indexOf(autBirthDate) < 0
           || !inpName.equals(autName)
           || !inpMobileNo.equals(authMobileNo)){

            hm.put("resultCode", "E");
            return hm;
        }

        // 화면세팅에서 서버세팅으로 변경
        promotionDto.setCi(niceResDtoSess.getConnInfo());
        promotionDto.setDi(niceResDtoSess.getDupInfo());

        int cnt = this.promotionSvc.promotionReg(promotionDto);

        String resultCode = "S";

        if (cnt < 1) {
            resultCode = "E";
        }else {
            AgreeTermsEventDto agreeTermsEventDto = new AgreeTermsEventDto();
            agreeTermsEventDto.setCi(niceResDtoSess.getConnInfo());
            agreeTermsEventDto.setDi(niceResDtoSess.getDupInfo());
            agreeTermsEventDto.setEventCode("EventPromotion");
            agreeTermsEventDto.setRip(ipstatisticService.getClientIp());
            agreeTermsEventDto.setAgreeArticle1("Y");
            agreeTermsEventDto.setAgreeArticle2("Y");
            agreeTermsEventDto.setAgreeArticle3("Y");
            agreeTermsEventDto.setAgreeArticle4("Y");
            agreeTermsEventDto.setEventKey(promotionDto.getEventPromotionId());

            if(null != userSession && !StringUtils.isEmpty(userSession.getUserId())) {
                agreeTermsEventDto.setUserId(userSession.getUserId());
                agreeTermsEventDto.setCretId(userSession.getUserId());
                agreeTermsEventDto.setAmdId(userSession.getUserId());
            }

            agreeTermsEventService.agreeJoinInsert(agreeTermsEventDto);
        }

        hm.put("resultCode", resultCode);
        return hm;
    }

}
