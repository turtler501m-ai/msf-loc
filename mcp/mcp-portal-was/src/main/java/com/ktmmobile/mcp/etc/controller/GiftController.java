package com.ktmmobile.mcp.etc.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.etc.dto.ConsentGiftDto;
import com.ktmmobile.mcp.etc.dto.GiftPromotionDto;
import com.ktmmobile.mcp.etc.service.GiftSvc;

@Controller
public class GiftController {

    private static Logger logger = LoggerFactory.getLogger(GiftController.class);

    public static final String COMM_AUTH_SMS_INFO = "COMM_AUTH_SMS_INFO";

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private SmsSvc smsSvc;


    @Autowired
    private GiftSvc giftSvc;

    @Autowired
    private CertService certService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @RequestMapping("/m/gift/giftPromotion.do")
    public String promotionGift(ModelMap model, HttpServletRequest request , @ModelAttribute GiftPromotionDto giftPromotionDto){

        String menuType = "gift";
        GiftPromotionDto promotionDto = null;
        List<GiftPromotionDto> giftDtoList = null;
        // prmtId 없으면 끝
        String prmtId = StringUtil.NVL(giftPromotionDto.getPrmtId(), "");
        if(!"".equals(prmtId)){
            // 사은품 리미트 가격 조회
            promotionDto = giftSvc.getPrmtChk(giftPromotionDto);

            // 사은품 리스트 조회
            giftDtoList = giftSvc.getGiftList(giftPromotionDto);
        }

        model.addAttribute("promotionDto", promotionDto);
        model.addAttribute("giftDtoList", giftDtoList);
        model.addAttribute("prmtId", prmtId);
        model.addAttribute("menuType", menuType);
        return "/mobile/etc/giftPromotion";
    }


    @RequestMapping(value = "/m/gift/userSmsAjax.do")
    @ResponseBody
    public Map<String, Object> userSmsAjax(HttpServletRequest request, @ModelAttribute GiftPromotionDto giftPromotionDto ){

        String resCode = "SUCCESS";
        String name = giftPromotionDto.getName();
        String phone = giftPromotionDto.getPhone();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if ( StringUtils.isBlank(name) || StringUtils.isBlank(phone) ) {
            resCode = "BLANK";
            rtnMap.put("resCode", resCode);
            return rtnMap;
        }

        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("gift");
        if (authSmsDto==null) {
            rtnMap.put("resCode", "CERT");
            return rtnMap;
        }

        //==========STEP START==========
        // 이름, 핸드폰번호
        String[] certKey = {"urlType", "name", "mobileNo"};
        String[] certValue = {"chkGiftList", name, phone};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            rtnMap.put("resCode", "STEP01");
            return rtnMap;
        }
        //==========STEP END==========

        /////////////// 신청 가능여부 체크  S ///////////////

        Map<String,Object> map = new HashMap<String,Object>();
        map = this.preChkFn(giftPromotionDto);
        resCode = (String)map.get("resCode");



        if(!"SUCCESS".equals(resCode)){
            rtnMap.put("resCode", resCode);
            return rtnMap;
        }

        /////////////// 신청 가능여부 체크 E ///////////////

        rtnMap.put("resCode", resCode);
        return rtnMap;
    }


    @RequestMapping(value="/m/gift/giftSaveAjax.do")
    @ResponseBody
    public Map<String, Object> giftSaveAjax(@ModelAttribute GiftPromotionDto giftPromotionDto,HttpServletRequest request) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String resCode = "APPSUCC";

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/m/gift/giftSaveAjax.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            rtnMap.put("resCode", "CLICK");
            return rtnMap;
        }

        // 인증 받았는지 확인
        HttpSession session = request.getSession();
        StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
        atr.append("_").append("gift");

        AuthSmsDto sessionAuthSmsDto = (AuthSmsDto)session.getAttribute(atr.toString());
        if( sessionAuthSmsDto == null ) {
            rtnMap.put("resCode", "CERT");
            return rtnMap;
        }

        // ============ STEP START ============
        // 1. 최소 스텝 수 체크
        if(certService.getStepCnt() < 3 ){
            rtnMap.put("resCode", "STEP02");
            return rtnMap;
        }

        // 2. 최종 데이터 체크: 스텝종료여부, 이름, 핸드폰번호
        String[] certKey = {"urlType", "stepEndYn", "name", "mobileNo"};
        String[] certValue = {"regGift", "Y", giftPromotionDto.getName(), giftPromotionDto.getPhone()};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            rtnMap.put("resCode", "STEP01");
            return rtnMap;
        }
        // ============ STEP END ============

        try {

            /////////////// 신청 가능여부 체크  S ///////////////
            String contractNum = "";
            Map<String,Object> map = new HashMap<String,Object>();
            map = this.preChkFn(giftPromotionDto);
            resCode = (String)map.get("resCode");

            GiftPromotionDto resDto = new GiftPromotionDto();
            if(!"SUCCESS".equals(resCode)){
                rtnMap.put("resCode", resCode);
                return rtnMap;
            } else {
                resDto = (GiftPromotionDto) map.get("mspJuoSubInfoData");
                contractNum = resDto.getContractNum();
                giftPromotionDto.setContractNum(contractNum);
                resCode = "APPSUCC";
            }
            /////////////// 신청 가능여부 체크 E ///////////////

            // 1. 고객정보 업데이트
            boolean result = giftSvc.updateMspGiftPrmtCustomer(giftPromotionDto);
            boolean complCtn = false;
            if(result){
                // 2. 사은품신청정보 등록
                complCtn = giftSvc.insertMspGiftPrmtResult(giftPromotionDto);
            }

            // 실패
            if(!complCtn){
                rtnMap.put("resCode", "APPFAIL");
            } else {
                try{
                    // 문자 발송
                    MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
                    // 고객연락처
                    StringBuffer str1 = new StringBuffer();
                    str1 = str1.append(giftPromotionDto.getTelFn1()).append(giftPromotionDto.getTelMn1()).append(giftPromotionDto.getTelRn1());
                    String cntrMobileNo = str1.toString();

                    mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(Constants.SMS_GIFT_PROMOTION_TEMPLATE_ID);
                    if (mspSmsTemplateMstDto != null) {
                        // 신청 날짜
                        Date today = new Date();
                        String strToday = DateTimeUtil.changeFormat(today,"yy년 MM월 dd일");

                        // 신청 사은품 이름
                        StringBuffer str = new StringBuffer();
                        String prdtNmList = "";
                        List<GiftPromotionDto> giftPromotionDtoList = giftSvc.getSaveGiftList(giftPromotionDto);
                        if(giftPromotionDtoList !=null && !giftPromotionDtoList.isEmpty()){
                            for(GiftPromotionDto dto : giftPromotionDtoList){
                                String prdtNm = dto.getPrdtNm();
                                str.append(prdtNm).append(System.getProperty("line.separator"));
                            }
                        }
                        prdtNmList = str.toString();

                        String smsMsg = mspSmsTemplateMstDto.getText();
                        smsMsg = smsMsg.replace("#{date}", strToday).replace("#{gift}", prdtNmList);
                        mspSmsTemplateMstDto.setText(smsMsg);

                        //smsSvc.sendLms( mspSmsTemplateMstDto.getSubject(), cntrMobileNo, smsMsg , mspSmsTemplateMstDto.getCallback() );
                        smsSvc.sendLms( mspSmsTemplateMstDto.getSubject(), cntrMobileNo, smsMsg ,
                                mspSmsTemplateMstDto.getCallback(),String.valueOf(Constants.SMS_GIFT_PROMOTION_TEMPLATE_ID),"SYSTEM");
                    }
                }  catch (RestClientException e) {
                    logger.debug(e.getMessage());
                }   catch(Exception e){
                    logger.debug("SMS-ERROR");
                }
            }
            rtnMap.put("resCode", resCode);
            return rtnMap;
        } catch(DataAccessException e) {
            rtnMap.put("resCode", "APPFAIL");
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("resCode", "APPFAIL");
            return rtnMap;
        }
    }

    @RequestMapping("/m/gift/giftPromotionComplete.do")
    public String giftPromotionComplete(ModelMap model, HttpServletRequest request , @ModelAttribute GiftPromotionDto giftPromotionDto){
        String prmtId = StringUtil.NVL(giftPromotionDto.getPrmtId(), "");
        model.addAttribute("prmtId", prmtId);
        return "/mobile/etc/giftPromotionComplete";
    }

    private Map<String,Object> preChkFn(GiftPromotionDto giftPromotionDto){
        Map<String,Object> map = new HashMap<String,Object>();

        String resCode = "SUCCESS";
        GiftPromotionDto resDto = null;
        try{

            GiftPromotionDto promotionDto = giftSvc.getPrmtChk(giftPromotionDto);
            if(promotionDto==null){
                resCode = "END"; // 종료된 프로모션
                map.put("resCode", resCode);
                return map;
            }

            // 0. 대상자 인지 확인 MSP_GIFT_PRMT_CUSTOMER@DL_MSP
            String smsSendDate = "";
            GiftPromotionDto giftPrmtCustomer = giftSvc.getMspGiftPrmtCustomer(giftPromotionDto);
            if(giftPrmtCustomer ==null){
                resCode = "NPROM"; // 프로모션 대상자아님
                map.put("resCode", resCode);
                return map;
            } else {
                smsSendDate = giftPrmtCustomer.getSmsSendDate();
            }

            // 1.본인인증 해지/정지 상태확인 msp_juo_sub_info@DL_MSP 조회 인증받은 핸드폰 번호로


            resDto = giftSvc.getMspJuoSubInfoData(giftPromotionDto);
            if(resDto == null){
                resCode = "NUSE"; // 해지 또는 정지
                map.put("resCode", resCode);
                map.put("mspJuoSubInfoData", resDto);
                return map;
            }

            Date today = new Date();
            String sysdate = DateTimeUtil.changeFormat(today,"yyyyMMdd");
            int between = 100;
            try{
                between = DateTimeUtil.daysBetween(smsSendDate,sysdate);
            } catch(ParseException e) {
                logger.debug("ParseException error");
            } catch(Exception e){
                logger.debug("between error");
            }
            if(between > 6) {
                // 2.sms 발송 포함 6일 초과 여부 체크
                resCode = "OVER"; // 개통일 포함 9일 초과 여부 체크
                map.put("resCode", resCode);
                map.put("mspJuoSubInfoData", resDto);
                return map;
            }

            String contractNum = resDto.getContractNum();
            // 3.신청이력이 있는경우 확인 MSP_GIFT_PRMT_RESULT@DL_MSP 프로모션 아이디와 인증받은 핸드폰 번호로 조회..
            List<GiftPromotionDto> giftPromotionDtoList = null;
            giftPromotionDto.setContractNum(contractNum);
            giftPromotionDtoList = giftSvc.getChkMspGiftPrmt(giftPromotionDto);
            if(giftPromotionDtoList != null && !giftPromotionDtoList.isEmpty()){
                resCode = "DOUBL"; // 이미 신청한 고객
                map.put("resCode", resCode);
                map.put("mspJuoSubInfoData", resDto);
                return map;
            }
        } catch(DataAccessException e) {
            resCode = "ERROR"; // DB에러
            map.put("resCode", resCode);
            map.put("mspJuoSubInfoData", resDto);
            return map;
        } catch(Exception e){
            resCode = "ERROR"; // DB에러
            map.put("resCode", resCode);
            map.put("mspJuoSubInfoData", resDto);
            return map;
        }

        map.put("resCode", resCode);
        map.put("mspJuoSubInfoData", resDto);
        return map;

    }

    @RequestMapping("/m/gift/consentGift.do")
    public String consentGift(ModelMap model, HttpServletRequest request , @ModelAttribute ConsentGiftDto consentGiftDto){

        String menuType = "consentGift";
        ConsentGiftDto consentDto = null;

         // 개인정보제공 URL 파라미터 수집
        String url = request.getQueryString();
         Map<String, String> params = new HashMap<>();

            String[] keyValuePairs = url.split("&");
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split("=");
                String key = keyValue[0];
                String value = keyValue[1];

                params.put(key, value);
            }

        // MMS 식별 값
        String taxNo = params.get("taxNo");

        if(StringUtils.isNotBlank(taxNo)) {

            consentGiftDto.setTaxNo(Integer.parseInt(taxNo));
            consentDto = giftSvc.addYnChk(consentGiftDto);

            //문자 발송 시간
            String regstTime = consentDto.getRequestTime();
            //현재 시간
            LocalDate currentDate = LocalDate.now();

            //REGST_TIME을 LocalDateTime으로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime regstDateTime = LocalDateTime.parse(regstTime, formatter);

            LocalDate regstDate = regstDateTime.toLocalDate();

            //시간 차이 계산
            long daysBetween = ChronoUnit.DAYS.between(regstDate, currentDate);

            //5일이 지났는지 확인(날짜로만 체크 하도록 수정 요청)
            if (daysBetween >= 5) {
                model.addAttribute("diffInDays", "Y");
            }

        }

        model.addAttribute("popType", "consentGift");
        model.addAttribute("consentDto", consentDto);
        model.addAttribute("menuType", menuType);

        return "/mobile/etc/consentGift";
    }

    @RequestMapping(value = "/m/gift/consentSmsAjax.do")
    @ResponseBody
    public Map<String, Object> consentSmsAjax(HttpServletRequest request, @ModelAttribute ConsentGiftDto consentGiftDto ){

        String resCode = "SUCCESS";
        String name = consentGiftDto.getUserNm();
        String phone = consentGiftDto.getTelNO();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if ( StringUtils.isBlank(name) || StringUtils.isBlank(phone) ) {
            resCode = "BLANK";
            rtnMap.put("resCode", resCode);
            return rtnMap;
        }

        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("consentGift");
        if (authSmsDto==null) {
            rtnMap.put("resCode", "CERT");
            return rtnMap;
        }

        //==========STEP START==========
        // 이름, 핸드폰번호
        String[] certKey = {"urlType", "name", "mobileNo"};
        String[] certValue = {"chkConsent", name, phone};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            rtnMap.put("resCode", "STEP01");
            return rtnMap;
        }
        //==========STEP END==========

        /////////////// 신청 가능여부 체크  S ///////////////

        Map<String,Object> map = new HashMap<String,Object>();
        map = this.consentChkFn(consentGiftDto);
        resCode = (String)map.get("resCode");



        if(!"SUCCESS".equals(resCode)){
            rtnMap.put("resCode", resCode);
            return rtnMap;
        }

        /////////////// 신청 가능여부 체크 E ///////////////

        rtnMap.put("resCode", resCode);
        return rtnMap;
    }

    private Map<String,Object> consentChkFn(ConsentGiftDto consentGiftDto){
        Map<String,Object> map = new HashMap<String,Object>();

        String resCode = "SUCCESS";

        try{
            // 0. 대상자 인지 확인 MSP_TAX_SMS_SEND_LIST@DL_MSP
            ConsentGiftDto giftPrmtCustomer = giftSvc.answerYnChk(consentGiftDto);
            if(giftPrmtCustomer ==null){
                resCode = "NPROM"; // 사은품 수령 대상자가 아님
                map.put("resCode", resCode);
                return map;
            }

            // 1.본인인증 해지/정지 상태확인 msp_juo_sub_info@DL_MSP 조회 인증받은 핸드폰 번호로
            ConsentGiftDto resDto = giftSvc.getConMspJuoSubInfoData(consentGiftDto);
            if(resDto == null){
                resCode = "NUSE"; // 해지 또는 정지
                map.put("resCode", resCode);
                return map;
            }

            // 2.신청이력이 있는경우 확인 MSP_TAX_SMS_SEND_LIST@DL_MSP
            ConsentGiftDto GiftConDto = giftSvc.answerYnChk(consentGiftDto);
            if(GiftConDto != null && GiftConDto.getTaxRecvTime() != null){
                resCode = "DOUBL"; // 이미 신청한 고객
                map.put("resCode", resCode);
                return map;
            }
        } catch(DataAccessException e) {
            resCode = "ERROR"; // DB에러
            map.put("resCode", resCode);
            return map;
        } catch(Exception e){
            resCode = "ERROR"; // DB에러
            map.put("resCode", resCode);
            return map;
        }

        map.put("resCode", resCode);
        map.put("taxNo", consentGiftDto.getTaxNo());
        return map;

    }

    @RequestMapping(value="/m/gift/consentSaveAjax.do")
    @ResponseBody
    public Map<String, Object> consentSaveAjax(@ModelAttribute ConsentGiftDto consentGiftDto,HttpServletRequest request) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String resCode = "APPSUCC";

        // 인증 받았는지 확인
        HttpSession session = request.getSession();
        StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
        atr.append("_").append("consentGift");

        AuthSmsDto sessionAuthSmsDto = (AuthSmsDto)session.getAttribute(atr.toString());
        if( sessionAuthSmsDto == null ) {
            rtnMap.put("resCode", "CERT");
            return rtnMap;
        }

        try {

            //==========STEP START==========
            // 1. 최소 스텝개수 확인
            if(certService.getStepCnt() < 3 ){
                rtnMap.put("resCode", "STEP02");
                return rtnMap;
            }

            // 2. 최종 데이터 확인: step 종료 여부, 이름, 핸드폰번호
            String[] certKey = {"urlType", "stepEndYn", "name", "mobileNo"};
            String[] certValue = {"saveConsent", "Y", consentGiftDto.getUserNm(), consentGiftDto.getTelNO()};
            Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
            if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                rtnMap.put("resCode", "STEP01");
                return rtnMap;
            }
            //==========STEP END==========

            /////////////// 신청 가능여부 체크  S ///////////////

            Map<String,Object> map = new HashMap<String,Object>();
            map = this.consentChkFn(consentGiftDto);
            resCode = (String)map.get("resCode");

            if(!"SUCCESS".equals(resCode)){
                rtnMap.put("resCode", resCode);
                return rtnMap;
            }
            /////////////// 신청 가능여부 체크 E ///////////////

            // 1. 회신일자 및 (주소가 있는 경우 주소 포함)업데이트.
            boolean result = giftSvc.updateReplyDate(consentGiftDto);

            // 업데이트 성공
            if(result){
                rtnMap.put("resCode", "APPSUCC");
            }else {
                rtnMap.put("resCode", "APPFAIL");
            }

        } catch(DataAccessException e) {
            rtnMap.put("resCode", "APPFAIL");
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("resCode", "APPFAIL");
            return rtnMap;
        }
        return rtnMap;
    }
}
