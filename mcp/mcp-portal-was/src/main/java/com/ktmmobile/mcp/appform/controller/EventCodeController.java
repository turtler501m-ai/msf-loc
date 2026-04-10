package com.ktmmobile.mcp.appform.controller;

import com.ktmmobile.mcp.appform.service.EventCodeSvc;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.etc.dto.GiftPromotionBas;
import com.ktmmobile.mcp.etc.service.GiftSvc;
import com.ktmmobile.mcp.rate.dto.EventCodePrmtXML;
import com.ktmmobile.mcp.rate.dto.RateGiftPrmtListDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;

@Controller
public class EventCodeController {
    private static final Logger logger = LoggerFactory.getLogger(EventCodeController.class);

    @Autowired
    private GiftSvc giftSvc;

    @Autowired
    private EventCodeSvc eventCodeSvc;

    @Autowired
    private FCommonSvc fCommonSvc;


    /** 프로모션 계층으로 조회 처리 new */
    @RequestMapping(value = "/benefit/giftBasListWithEvntCdAjax.do")
    @ResponseBody
    public RateGiftPrmtListDTO giftBasListAjaxNew(HttpServletRequest request, GiftPromotionBas giftPromotionBas) {

        RateGiftPrmtListDTO rateGiftPrmtListDTO = new RateGiftPrmtListDTO();

        if(StringUtils.isBlank(giftPromotionBas.getRateCd())) {
            return rateGiftPrmtListDTO;
        }

        String eventRateYn = "N";
        String referer = StringUtil.NVL(request.getHeader("referer"), "");
        String pageUri = "";

        if(referer.indexOf("/appForm/appFormDesignView.do") > -1) {
            pageUri = "appFormDesignView";
        }else if(referer.indexOf("/product/phone/phoneView.do") > -1) {
            pageUri = "phoneView";
        }else if(referer.indexOf("/appForm/appJehuFormDesignView.do") > -1) {
            pageUri = "appJehuFormDesignView";
        }else if(referer.indexOf("/appForm/appAgentFormDesignView.do") > -1) {
            pageUri = "appAgentFormDesignView";
        }

        NmcpCdDtlDto nmcpCdDtlDto= fCommonSvc.getDtlCodeWithNm("PrmtEvntCdUrl", pageUri);

        if(nmcpCdDtlDto != null){
            List<String> eventCodeList = eventCodeSvc.getEvntCdList(giftPromotionBas.getRateCd());
            eventRateYn = eventCodeList.isEmpty() ? "N" : "Y";
        }

        // m전산 사은품 프로모션 조회
        List<GiftPromotionBas> prmtList = giftSvc.giftBasList(giftPromotionBas);
        if(prmtList == null || prmtList.isEmpty()){
            rateGiftPrmtListDTO.setEvntCdPrmtYn(eventRateYn);
            return rateGiftPrmtListDTO;
        }

        // 사은품 프로모션에 해당하는 요금제 혜택요약 조회
        List<String> prmtIdList = prmtList.stream().map(GiftPromotionBas::getPrmtId).collect(Collectors.toList());
        rateGiftPrmtListDTO = giftSvc.selectRateGiftPrmtByIdList(prmtIdList,pageUri);
        rateGiftPrmtListDTO.setEvntCdPrmtYn(eventRateYn);

        return rateGiftPrmtListDTO;
    }

    /** 이벤트 코드 검증 */
    @RequestMapping(value = "/benefit/checkEvntCdAjax.do")
    @ResponseBody
    public Map<String, Object> checkEvntCdAjax(GiftPromotionBas giftPromotionBas) {

        Map<String, Object> rtnMap = new HashMap<>();

        if(StringUtils.isEmpty(giftPromotionBas.getRateCd()) || StringUtils.isEmpty(giftPromotionBas.getEvntCdPrmt())){
            throw new McpCommonJsonException("0001", BIDING_EXCEPTION);
        }

        List<String> eventCodeList = eventCodeSvc.getEvntCdList(giftPromotionBas.getRateCd());
        if(!eventCodeList.contains(giftPromotionBas.getEvntCdPrmt())){
            throw new McpCommonJsonException("0002", "이벤트 코드 불일치");
        }

       //GiftPromotionBas getEventVal = eventCodeSvc.getEventVal(giftPromotionBas.getEvntCdPrmt());
        GiftPromotionBas getEventVal = eventCodeSvc.getEventVal(giftPromotionBas.getRateCd(),giftPromotionBas.getEvntCdPrmt());

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("getEventVal", getEventVal);
        return rtnMap;
    }

    /** 단말개통 시 요금제 이벤트 코드 존재 여부 확인 */
    @RequestMapping(value = "/benefit/giftBasListYnWithEvntCdAjax.do")
    @ResponseBody
    public Map<String, String> giftBasListYnWithEvntCdAjax(HttpServletRequest request, GiftPromotionBas giftPromotionBas) {

        Map<String, String> rtnMap = new HashMap<>();

        if(StringUtils.isBlank(giftPromotionBas.getRateCd())) {
            rtnMap.put("eventRateYn", "N");
            return rtnMap;
        }

        String eventRateYn = "N";

        NmcpCdDtlDto nmcpCdDtlDto= fCommonSvc.getDtlCodeWithNm("PrmtEvntCdUrl", "phoneView");

        if(nmcpCdDtlDto != null){
            List<String> eventCodeList = eventCodeSvc.getEvntCdList(giftPromotionBas.getRateCd());
            eventRateYn = eventCodeList.isEmpty() ? "N" : "Y";
        }

        rtnMap.put("eventRateYn", eventRateYn);
        return rtnMap;
    }


    /** 확인용  */
    @RequestMapping(value = "/benefit/getEventCodePrmtXML.do")
    @ResponseBody
    public List<EventCodePrmtXML>  getEventCodePrmtXML( @RequestParam(required = false, defaultValue = "eventCodePrmtList") String key) {

        logger.info("key===>" + key);
        return eventCodeSvc.getEventCodePrmtXML(key) ;

    }

}
