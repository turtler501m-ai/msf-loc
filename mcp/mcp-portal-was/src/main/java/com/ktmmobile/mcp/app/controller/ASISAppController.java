package com.ktmmobile.mcp.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.ktmmobile.mcp.app.dto.McpUserAppDto;
import com.ktmmobile.mcp.app.service.AppSvc;
import com.ktmmobile.mcp.common.util.StringUtil;

@Controller
public class ASISAppController {

    private static final Logger logger = LoggerFactory.getLogger(ASISAppController.class);

    @Autowired
    private AppSvc appSvc;


    /**
     * 설명 : VERSION 정보 가지고 오기, as-is 앱용
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param os
     * @param version
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/app/getVersionJson.do")
    @ResponseBody
    public Map<String, Object> getVersionJson(HttpServletRequest request, HttpServletResponse response
            , String os, String version
            ) throws UnsupportedEncodingException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> valueMap = new HashMap<String, Object>();

        if(!"".equals(StringUtil.NVL(os, "")) && !"".equals(StringUtil.NVL(version, ""))) {

            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("os", os);
            paramMap.put("version", version);

            try {
                Map<String, String> appVersion = appSvc.selectAppVersion(os);

                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("VERSION", appVersion.get("VERSION"));
                resultMap.put("DN_URL", appVersion.get("DN_URL"));
                resultMap.put("MUST_UP", appVersion.get("MUST_UP"));

                String versionDB = appVersion.get("VERSION").replace(".", "");
                int intVerDB = Integer.parseInt(versionDB);

                if (intVerDB >= 200) {
                    valueMap.put("RESULT_CD", "0000");
                    valueMap.put("RESULT_DESC", "");
                    valueMap.put("RESULT_DATA", resultMap);
                } else {
                    valueMap.put("RESULT_CD", "4002");
                    valueMap.put("RESULT_DESC", "App 리뉴얼 작업중입니다.\r\n"
                            + "PC/모바일 브라우저에서는 정상이용 가능합니다.");
                }

            } catch(DataAccessException e) {
                valueMap.put("RESULT_CD", "4002");
                valueMap.put("RESULT_DESC", "App 리뉴얼 작업중입니다.\r\n"
                        + "PC/모바일 브라우저에서는 정상이용 가능합니다.");
            } catch(Exception e) {
                valueMap.put("RESULT_CD", "4002");
                valueMap.put("RESULT_DESC", "App 리뉴얼 작업중입니다.\r\n"
                        + "PC/모바일 브라우저에서는 정상이용 가능합니다.");
            }
        } else {
            valueMap.put("RESULT_CD", "4002");
            valueMap.put("RESULT_DESC", "App 리뉴얼 작업중입니다.\r\n"
                    + "PC/모바일 브라우저에서는 정상이용 가능합니다.");
        }
        returnMap.put("CODE", "0000");
        returnMap.put("ERRORDESC", "");
        returnMap.put("VALUE", valueMap);

        return returnMap;
    }


    /**
     * 설명 : PUSH 정보 수정, as-is 앱용
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param uuid
     * @param udid
     * @param os
     * @param version
     * @param sendYn
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/app/setPushSendJson.do")
    @ResponseBody
    public Map<String, Object> setPushSendJson(HttpServletRequest request, HttpServletResponse response
            , String uuid, String udid, String os, String version, String sendYn
            ) throws UnsupportedEncodingException {

        Map<String, Object> valueMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        valueMap.put("RESULT_CD", "0202");
        valueMap.put("RESULT_DESC", "App 리뉴얼 작업중입니다.\r\n"
                + "PC/모바일 브라우저에서는 정상이용 가능합니다.");
        returnMap.put("CODE", "0000");
        returnMap.put("ERRORDESC", "");
        returnMap.put("VALUE", valueMap);

        return returnMap;
    }


    /**
     * 설명 : 위젯정보, as-is 앱용
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param mcpUserAppDto
     * @param sessionStatus
     * @return
     */
    @RequestMapping(value = "/app/getWidgetDataDecJson.do")
    @ResponseBody
    public Map<String, Object> getWidgetDataDecJson( McpUserAppDto mcpUserAppDto, SessionStatus sessionStatus) {
        Map<String, Object> returnMap = new WeakHashMap<String, Object>();

        Map<String, Object> valueMap = new WeakHashMap<String, Object>();
        valueMap.put("RESULT_CD", "0202");
        valueMap.put("RESULT_DESC", "App 리뉴얼 작업중입니다.\r\n"
                + "PC/모바일 브라우저에서는 정상이용 가능합니다.");
        returnMap.put("CODE", "0000");
        returnMap.put("ERRORDESC", "");
        returnMap.put("VALUE", valueMap);

        return returnMap;
    }

    @RequestMapping(value = {"/index.do", "/retention/retentionPopup.do"
            , "/mobile/index.do","/app/redirectDec.do"
            , "/appform/appSimpleform.do","/appform/appSimpleInfo.do","/appform/appformNe.do","/appform/appformRental.do","/appform/orderComplete.do","/appform/orderSlimpleComplete.do","/request/partnerRequestForm.do","/usim/usimRegi.do"
            , "/m/appform/appSimpleform.do","/m/appform/appSimpleInfo.do","/m/appform/appformNe.do","/m/appform/orderComplete.do","/m/appform/orderSlimpleComplete.do","/m/request/partnerRequestForm.do"
            , "/direct/7-11FaqList.do","/direct/7-11Store.do","/direct/cuStore.do","/direct/emart24FaqList.do","/direct/emart24Store.do","/direct/gs25Store.do","/direct/ministopFaqList.do","/direct/ministopStore.do"
            , "/m/direct/7-11FaqList.do","/m/direct/CU.do","/m/direct/cspaceStoreList.do","/m/direct/cuFaqList.do","/m/direct/cuStoreList.do"
            , "/cs/csInquiryList.do","/m/cs/mCsInquiryInt.do"
            , "/cs/csNoticeView.do"
            , "/appform/reqSelfDlvry.do"
            , "/m/appform/reqSelfDlvry.do"
            , "/m/appform/reqSelfDlvryList.do","/m/orderList.do"
            , "/mmobile/mblogDetail.do"
            , "/m/blogDetail.do","/m/m_blogList.do"
            , "/m/content/ktMobileApp.do", "/m/content/ktMMobile.do"
            , "/comunity/lteRateList.do", "/usim/usimPrePaid.do"
            , "/m/content/mKtFeePlanDetail.do","/m/content/mKtFeePlan.do"
            , "/content/additionBasic.do"
            , "/m/content/mAdditionBasic.do","/m/content/mKtInternationalRoaming.do"
            , "/m/cs/mDeviceAsInfo.do"
            , "/m/mmobile/m_newsDataList.do","/m/mmobile/m_newsMDetail.do"
            , "/cs/csInfo.do","/mmobile/ktmMobileGuid.do"
            , "/request/ppsRequest.do","/request/requestComplete.do","/request/requestForm.do"
            , "/dlvryChkAddPopup.do"
            , "/m/usim/usimDetail.do","/usim/usimDetail.do"
            , "/m/mypage/requestView.do"
            , "/mypage/serviceView02.do"
            , "/product/phone/officialNoticeSupportListAjax.do"
            })
    public String redirectUrl(HttpServletRequest request, HttpServletResponse response) {

        String rtnString = "/main.do";

        if(request.getRequestURI().equals("/mobile/index.do") || request.getRequestURI().equals("/app/redirectDec.do")){
            rtnString = "/m/main.do";
        } else if(request.getRequestURI().equals("/appform/appSimpleform.do") || request.getRequestURI().equals("/appform/appSimpleInfo.do") || request.getRequestURI().equals("/appform/appformNe.do") ||
                request.getRequestURI().equals("/appform/appformRental.do") || request.getRequestURI().equals("/appform/orderComplete.do") || request.getRequestURI().equals("/appform/orderSlimpleComplete.do")
                || request.getRequestURI().equals("/request/partnerRequestForm.do") || request.getRequestURI().equals("/usim/usimRegi.do")) {
            rtnString = "/appForm/appSimpleInfo.do";
        } else if(request.getRequestURI().equals("/m/appform/appSimpleform.do") || request.getRequestURI().equals("/m/appform/appSimpleInfo.do") || request.getRequestURI().equals("/m/appform/appformNe.do") ||
                request.getRequestURI().equals("/m/appform/orderComplete.do") || request.getRequestURI().equals("/m/appform/orderSlimpleComplete.do") || request.getRequestURI().equals("/m/request/partnerRequestForm.do")) {
            rtnString = "/m/appForm/appSimpleInfo.do";
        } else if(request.getRequestURI().equals("/direct/7-11FaqList.do") || request.getRequestURI().equals("/direct/7-11Store.do") || request.getRequestURI().equals("/direct/cuStore.do") ||
                request.getRequestURI().equals("/direct/emart24FaqList.do") || request.getRequestURI().equals("/direct/emart24Store.do") || request.getRequestURI().equals("/direct/gs25Store.do") ||
                request.getRequestURI().equals("/direct/ministopFaqList.do") || request.getRequestURI().equals("/direct/ministopStore.do")) {
            rtnString = "/direct/storeInfo.do";
        } else if(request.getRequestURI().equals("/m/direct/7-11FaqList.do") || request.getRequestURI().equals("/m/direct/CU.do") ||
                request.getRequestURI().equals("/m/direct/cspaceStoreList.do") || request.getRequestURI().equals("/m/direct/cuFaqList.do") || request.getRequestURI().equals("/m/direct/cuStoreList.do")){
            rtnString = "/m/direct/storeInfo.do";
        } else if(request.getRequestURI().equals("/cs/csInquiryList.do")){
            rtnString = "/cs/csInquiryInt.do";
        } else if(request.getRequestURI().equals("/m/cs/mCsInquiryInt.do")){
            rtnString = "/m/cs/csInquiryInt.do";
        } else if(request.getRequestURI().equals("/cs/csNoticeView.do")){
            rtnString = "/cs/csNoticeList.do";
        } else if(request.getRequestURI().equals("/appform/reqSelfDlvry.do")){
            rtnString = "/appForm/reqSelfDlvry.do";
        } else if(request.getRequestURI().equals("/m/appform/reqSelfDlvry.do")){
            rtnString = "/m/appForm/reqSelfDlvry.do";
        } else if(request.getRequestURI().equals("/m/appform/reqSelfDlvryList.do") || request.getRequestURI().equals("/m/orderList.do")){
            rtnString = "/m/order/orderList.do";
        } else if(request.getRequestURI().equals("/mmobile/mblogDetail.do")){
            rtnString = "/mstory/snsList.do";
        } else if(request.getRequestURI().equals("/m/blogDetail.do") || request.getRequestURI().equals("/m/m_blogList.do")){
            rtnString = "/m/mstory/snsList.do";
        } else if(request.getRequestURI().equals("/m/content/ktMobileApp.do") || request.getRequestURI().equals("/m/content/ktMMobile.do")){
            rtnString = "/m/cs/ktMobileAppView.do";
        } else if(request.getRequestURI().equals("/comunity/lteRateList.do") || request.getRequestURI().equals("/usim/usimPrePaid.do")){
            rtnString = "/rate/rateList.do";
        } else if(request.getRequestURI().equals("/m/content/mKtFeePlanDetail.do") || request.getRequestURI().equals("/m/content/mKtFeePlan.do")){
            rtnString = "/m/rate/rateList.do";
        } else if(request.getRequestURI().equals("/content/additionBasic.do")){
            rtnString = "/rate/adsvcGdncList.do";
        } else if(request.getRequestURI().equals("/m/content/mAdditionBasic.do") || request.getRequestURI().equals("/m/content/mKtInternationalRoaming.do")){
            rtnString = "/m/rate/adsvcGdncList.do";
        } else if(request.getRequestURI().equals("/m/cs/mDeviceAsInfo.do")){
            rtnString = "/m/cs/mDeviceAsView.do";
        } else if(request.getRequestURI().equals("/m/mmobile/m_newsDataList.do") || request.getRequestURI().equals("/m/mmobile/m_newsMDetail.do")){
            rtnString = "/mmobile/newsDataList.do";
        } else if(request.getRequestURI().equals("/cs/csInfo.do") || request.getRequestURI().equals("/mmobile/ktmMobileGuid.do")){
            rtnString = "/cs/serviceGuide.do";
        } else if(request.getRequestURI().equals("/request/ppsRequest.do") || request.getRequestURI().equals("/request/requestComplete.do") || request.getRequestURI().equals("/request/requestForm.do")){
            rtnString = "/appForm/appCounselorInfo.do";
        } else if(request.getRequestURI().equals("/dlvryChkAddPopup.do")){
            rtnString = "/appForm/reqSelfDlvry.do";
        } else if(request.getRequestURI().equals("/m/usim/usimDetail.do")){
            rtnString = "/m/appForm/reqSelfDlvry.do";
        } else if(request.getRequestURI().equals("/usim/usimDetail.do")){
            rtnString = "/appForm/reqSelfDlvry.do";
        } else if(request.getRequestURI().equals("/m/mypage/requestView.do")){
            rtnString = "/m/mypage/myinfoView.do";
        } else if(request.getRequestURI().equals("/mypage/serviceView02.do")){
            rtnString = "/mypage/regServiceView.do";
        } else if(request.getRequestURI().equals("/product/phone/officialNoticeSupportListAjax.do")){
            rtnString = "/m/product/phone/officialNoticeSupportListAjax.do";
        }

        return "redirect:"+rtnString;
    }
}
