package com.ktmmobile.msf.form.comm.controller;


import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.form.newchange.dto.AppformReqDto;
import com.ktmmobile.msf.form.newchange.service.AppformSvc;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.form.common.dto.AuthSmsDto;
import com.ktmmobile.msf.form.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.form.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.msf.form.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.form.common.exception.McpCommonException;
import com.ktmmobile.msf.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.form.common.mspservice.MspService;
import com.ktmmobile.msf.form.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.msf.form.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.form.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.msf.form.common.util.*;
import com.ktmmobile.msf.form.comm.dto.OrderDto;
import com.ktmmobile.msf.form.comm.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.msf.form.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.form.common.exception.msg.ExceptionMsgConstant.*;


/**
 * 주문 조회 Controller
 * @author key
 * @Date 2021.12.30
 */
@Controller
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Value("${sale.orgnId}")
    private String orgnId;
    @Value("${api.interface.server}")
    private String apiInterfaceServer;
    @Autowired
    private OrderService odersvc;

    @Autowired
    AppformSvc appformSvc;

    @Autowired
    MspService mspService;

    @Autowired
    CertService certService;

    public static final String ORDER_AUTH_MENU = "order";

    /**
     * 설명 : 주문조회
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param redirectUrl
     * @param model
     * @return
     * @throws ParseException
    */
    @RequestMapping(value = {"/orderList.do","/order/orderList.do","/m/order/orderList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String orderList(HttpServletRequest request, @RequestParam(defaultValue="",required=false) String redirectUrl, Model model) throws ParseException {

        UserSessionDto usd = SessionUtils.getUserCookieBean();
        UserSessionDto guest = SessionUtils.getOrderSession();

        AuthSmsDto authSmsDto = (AuthSmsDto) request.getSession().getAttribute(SessionUtils.COMM_AUTH_SMS_INFO+"_"+ORDER_AUTH_MENU);

        String SessionYn = "N";
        String loginYn = "N";
        String pageAuthYn = "N";
        if(usd != null || (guest != null && authSmsDto != null)){
               SessionYn = "Y";
        }else {

           String reDirectUrl = "/certSmsNomemberView.do";
           if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
               reDirectUrl = "/m/certSmsNomemberView.do";
           }

           ResponseSuccessDto rsDto = new ResponseSuccessDto();
            rsDto.setRedirectUrl(reDirectUrl);
           model.addAttribute("responseSuccessDto", rsDto);
            return "/common/successRedirect";
        }

        if(usd != null ){
            loginYn = "Y";
        }

        if(authSmsDto != null ){
            pageAuthYn = "Y";
        }

        model.addAttribute("SessionYn", SessionYn);
        model.addAttribute("loginYn", loginYn);
        model.addAttribute("pageAuthYn", pageAuthYn);

        String nowDateString = DateTimeUtil.getFormatString("yyyy.MM.dd");

        model.addAttribute("monthAgoDate",DateTimeUtil.addMonths(nowDateString, -1 ,"yyyy.MM.dd"));
        model.addAttribute("twoMonthAgoDate",DateTimeUtil.addMonths(nowDateString, -2,"yyyy.MM.dd"));
        //model.addAttribute("oneYearAgoDate",DateTimeUtil.addYears(nowDateString, -1,"yyyy.MM.dd"));
        model.addAttribute("oneYearAgoDate",DateTimeUtil.addMonths(nowDateString, -12,"yyyy.MM.dd"));
        model.addAttribute("menuType", ORDER_AUTH_MENU);
        model.addAttribute("orderType","order");

        String returnUrl = "/portal/popup/certSmsNomemberView";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/popup/certSmsNomemberView";
        }

           return returnUrl;
    }

    /**
     * 설명 : 임시저장 조회
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param redirectUrl
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = {"/order/orderTempList.do","/m/order/orderTempList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String orderTempList(HttpServletRequest request, @RequestParam(defaultValue="",required=false) String redirectUrl, Model model) throws ParseException {

        UserSessionDto usd = SessionUtils.getUserCookieBean();
        UserSessionDto guest = SessionUtils.getOrderSession();

        AuthSmsDto authSmsDto = (AuthSmsDto) request.getSession().getAttribute(SessionUtils.COMM_AUTH_SMS_INFO+"_"+ORDER_AUTH_MENU);

        String SessionYn = "N";
        String loginYn = "N";
        String pageAuthYn = "N";
        if(usd != null || (guest != null && authSmsDto != null)){
               SessionYn = "Y";
        }else {

           String reDirectUrl = "/certSmsNomemberView.do";
           if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
               reDirectUrl = "/m/certSmsNomemberView.do";
           }

           ResponseSuccessDto rsDto = new ResponseSuccessDto();
            rsDto.setRedirectUrl(reDirectUrl);
           model.addAttribute("responseSuccessDto", rsDto);
            return "/common/successRedirect";
        }

        if(usd != null ){
            loginYn = "Y";
        }

        if(authSmsDto != null ){
            pageAuthYn = "Y";
        }
        model.addAttribute("SessionYn", SessionYn);
        model.addAttribute("loginYn", loginYn);
        model.addAttribute("pageAuthYn", pageAuthYn);

        String nowDateString = DateTimeUtil.getFormatString("yyyy.MM.dd");

        model.addAttribute("monthAgoDate",DateTimeUtil.addMonths(nowDateString, -1 ,"yyyy.MM.dd"));
        model.addAttribute("twoMonthAgoDate",DateTimeUtil.addMonths(nowDateString, -2,"yyyy.MM.dd"));
        model.addAttribute("oneYearAgoDate",DateTimeUtil.addYears(nowDateString, -1,"yyyy.MM.dd"));
        model.addAttribute("menuType", ORDER_AUTH_MENU);

        String returnUrl = "/portal/popup/certSmsNomemberView";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/popup/certSmsNomemberView";
        }

        model.addAttribute("orderType","temp");

           return returnUrl;
    }

    /**
     * 설명 : 셀프 유심 조회
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param redirectUrl
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = {"/appForm/reqSelfDlvryList.do","/m/appForm/reqSelfDlvryList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String reqSelfDlvryList(HttpServletRequest request, @RequestParam(defaultValue="",required=false) String redirectUrl, Model model) throws ParseException {

        UserSessionDto usd = SessionUtils.getUserCookieBean();
        UserSessionDto guest = SessionUtils.getOrderSession();

        AuthSmsDto authSmsDto = (AuthSmsDto) request.getSession().getAttribute(SessionUtils.COMM_AUTH_SMS_INFO+"_"+ORDER_AUTH_MENU);

        String SessionYn = "N";
        String loginYn = "N";
        String pageAuthYn = "N";
        if(usd != null || (guest != null && authSmsDto != null)){
               SessionYn = "Y";
        }else {

           String reDirectUrl = "/certSmsNomemberView.do";
           if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
               reDirectUrl = "/m/certSmsNomemberView.do";
           }

           ResponseSuccessDto rsDto = new ResponseSuccessDto();
            rsDto.setRedirectUrl(reDirectUrl);
           model.addAttribute("responseSuccessDto", rsDto);
            return "/common/successRedirect";
        }

        if(usd != null ){
            loginYn = "Y";
        }

        if(authSmsDto != null ){
            pageAuthYn = "Y";
        }
        model.addAttribute("SessionYn", SessionYn);
        model.addAttribute("loginYn", loginYn);
        model.addAttribute("pageAuthYn", pageAuthYn);

        String nowDateString = DateTimeUtil.getFormatString("yyyy.MM.dd");

        model.addAttribute("monthAgoDate",DateTimeUtil.addMonths(nowDateString, -1 ,"yyyy.MM.dd"));
        model.addAttribute("twoMonthAgoDate",DateTimeUtil.addMonths(nowDateString, -2,"yyyy.MM.dd"));
        model.addAttribute("oneYearAgoDate",DateTimeUtil.addYears(nowDateString, -1,"yyyy.MM.dd"));
        model.addAttribute("menuType", ORDER_AUTH_MENU);

        String returnUrl = "/portal/popup/certSmsNomemberView";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/popup/certSmsNomemberView";
        }

        model.addAttribute("orderType","self");

           return returnUrl;
    }

    /**
     * 설명 : 비회원 로그인
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param redirectUrl
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = {"/certSmsNomemberView.do","/m/certSmsNomemberView.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String certSmsNomemberView(HttpServletRequest request, @RequestParam(defaultValue="",required=false) String redirectUrl, Model model) throws ParseException {

        UserSessionDto usd = SessionUtils.getUserCookieBean();
        UserSessionDto guest = SessionUtils.getOrderSession();

        AuthSmsDto authSmsDto = (AuthSmsDto) request.getSession().getAttribute(SessionUtils.COMM_AUTH_SMS_INFO+"_"+ORDER_AUTH_MENU);

        String SessionYn = "N";
        String loginYn = "N";
        String pageAuthYn = "N";
        if(usd != null || (guest != null && authSmsDto != null)){
            String reDirectUrl = "/order/orderTempList.do";
           if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                reDirectUrl = "/m/order/orderTempList.do";
            }

            ResponseSuccessDto rsDto = new ResponseSuccessDto();
             rsDto.setRedirectUrl(reDirectUrl);
            model.addAttribute("responseSuccessDto", rsDto);
             return "/common/successRedirect";

        }

        model.addAttribute("SessionYn", SessionYn);
        model.addAttribute("loginYn", loginYn);
        model.addAttribute("pageAuthYn", pageAuthYn);

        String nowDateString = DateTimeUtil.getFormatString("yyyy.MM.dd");

        model.addAttribute("monthAgoDate",DateTimeUtil.addMonths(nowDateString, -1 ,"yyyy.MM.dd"));
        model.addAttribute("twoMonthAgoDate",DateTimeUtil.addMonths(nowDateString, -2,"yyyy.MM.dd"));
        model.addAttribute("oneYearAgoDate",DateTimeUtil.addYears(nowDateString, -1,"yyyy.MM.dd"));
        model.addAttribute("menuType", ORDER_AUTH_MENU);
        String returnUrl = "/portal/popup/certSmsNomemberView";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/popup/certSmsNomemberView";
        }

        model.addAttribute("orderType","noMember");

           return returnUrl;
    }

    /**
     * 설명 : 주문 상세 조회
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param appformReqDto
     * @param model
     * @param dto
     * @return
     * @throws CryptoException
     */
    @RequestMapping (value={"/orderView.do","/m/orderView.do"} , method = RequestMethod.POST)
    public String orderView(
            HttpServletRequest request
            , AppformReqDto appformReqDto
            , ModelMap model
            , OrderDto dto) throws CryptoException{

        //세센 아이디 확인(비회원 , 회원)
        UserSessionDto usd = SessionUtils.getUserCookieBean();
        UserSessionDto guest = SessionUtils.getOrderSession();

        if(usd == null && guest ==null){
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setRedirectUrl("/loginForm.do");
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }
        String returnUrl = "/portal/order/orderView";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
             returnUrl="/mobile/order/orderView";
        }

        //사용자 검증
        if(null!=guest && guest.getUserDivision().equals("02")){//비회원
            appformReqDto.setCstmrName(guest.getName());
            appformReqDto.setCstmrMobileFn(guest.getMobileNo().substring(0,3));
            appformReqDto.setCstmrMobileMn(guest.getMobileNo().substring(3,guest.getMobileNo().length()-4));
            appformReqDto.setCstmrMobileRn(guest.getMobileNo().substring(guest.getMobileNo().length()-4, guest.getMobileNo().length()));
        } else if(usd.getUserDivision() !=null){//일반회원 정회원
            appformReqDto.setCretId(usd.getUserId());
        }


        if (appformSvc.isOwnerCount(appformReqDto) < 1) {
            throw new McpCommonException(OWNER_CHECK_EXCEPTION);
        }


        //상세보기
        dto.setOrgnId(orgnId);
        OrderDto view=odersvc.selectOrderView(dto);

        //가격정보를 ...
        MspSaleSubsdMstDto  mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
        mspSaleSubsdMstDto.setSprtTp(view.getSprtTp());    //지원금유형 단말할인:KD ,요금할인:PM)
        mspSaleSubsdMstDto.setHndstAmt(view.getModelPriceInt() + view.getModelPriceVatInt() ); //단말금액(VAT)포함
        mspSaleSubsdMstDto.setSubsdAmt(view.getModelDiscount2Int());  //공시지원금
        mspSaleSubsdMstDto.setAgncySubsdAmt(view.getModelDiscount3Int());  //대리점 보조금
        mspSaleSubsdMstDto.setAgncySubsdMax(view.getMaxDiscount3Int());  //대리점 보조금(max)
        mspSaleSubsdMstDto.setModelMonthly(view.getModelMonthlyInt());   //단말기 할부 (24,36)
        mspSaleSubsdMstDto.setBaseAmt(view.getBaseAmtInt()) ; //기본요금
        mspSaleSubsdMstDto.setDcAmt(view.getDcAmtInt()) ; //할인금액(약정할인선택시 할인금액)
        //setPromotionDcAmt  //프로모션 할인 설정 시 필요
        mspSaleSubsdMstDto.setAddDcAmt(view.getAddDcAmtInt());  //추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)
        //할부이자율
        CmnGrpCdMst cdInfo = mspService.findCmnGrpCdMst("CMN0051", "20");  //할부수수료 default 값
        BigDecimal instRate = new BigDecimal(cdInfo.getEtc1());

        if (!StringUtils.isBlank(view.getModelSalePolicyCode())) {

            //---- API 호출 S ----//
              RestTemplate restTemplate = new RestTemplate();
              MspSalePlcyMstDto mspSalePlcyMstDto = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSalePlcyMst", view.getModelSalePolicyCode(), MspSalePlcyMstDto.class);
             //---- API 호출 E ----//

            instRate  = mspSalePlcyMstDto.getInstRate();
        }

       //프로모션 금액 조회
        Integer promotionDcAmt = 0;
        String agrmTrim = "";

        if (!StringUtils.isBlank(view.getEnggMnthCnt())) {
            agrmTrim = view.getEnggMnthCnt();
         }

        mspSaleSubsdMstDto.setOrgnId(orgnId);
        mspSaleSubsdMstDto.setRateCd(view.getSocCode());
        mspSaleSubsdMstDto.setOperType(view.getOperType());
        mspSaleSubsdMstDto.setAgrmTrm(agrmTrim);

        String plcySctnCd = "";
          if("MM".equals(view.getReqBuyType())) {
              plcySctnCd = "01";
          }else if("UU".equals(view.getReqBuyType())) {
              plcySctnCd = "02";
          }
        mspSaleSubsdMstDto.setPlcySctnCd(plcySctnCd);
          mspSaleSubsdMstDto.setOnOffType(view.getOnOffType());
          promotionDcAmt = new RestTemplate().postForObject(apiInterfaceServer + "/msp/getromotionDcAmt", mspSaleSubsdMstDto, Integer.class);
        //프로모션 금액 세팅
        mspSaleSubsdMstDto.setPromotionDcAmt(promotionDcAmt == null ? 0 : promotionDcAmt);

        OrderDto apdView = new RestTemplate().postForObject(apiInterfaceServer + "/order/orderApdView", dto, OrderDto.class);

        mspSaleSubsdMstDto.setInstRate(instRate);   //할부 이자율
        model.addAttribute("saleInfo", mspSaleSubsdMstDto);




        List<NmcpCdDtlDto> stateList =NmcpServiceUtils.getCodeList("PSTATE00");//신청진행상태 결제완료
        List<NmcpCdDtlDto> stateList01 =NmcpServiceUtils.getCodeList("PSTATE01");//신청진행상태 결제완료
        List<NmcpCdDtlDto> stateList02 =NmcpServiceUtils.getCodeList("PSTATE02");//신청진행상태   배송대기
        List<NmcpCdDtlDto> stateList03 =NmcpServiceUtils.getCodeList("PSTATE03");//신청진행상태 배송중
        List<NmcpCdDtlDto> stateList04 =NmcpServiceUtils.getCodeList("PSTATE04");//신청진행상태 개통대기
        List<NmcpCdDtlDto> stateList05 =NmcpServiceUtils.getCodeList("PSTATE05");//신청진행상태 개통완료

        // view.setReqAccountNumber(MaskingUtil.getMaskedValue(EncryptUtil.ace256Dec(view.getReqAccountNumber()),6,false));
        // view.setReqCardNo(MaskingUtil.getMaskedValue(EncryptUtil.ace256Dec(view.getReqCardNo()),6,false));

        view.setReqAccountNumber(MaskingUtil.getMaskedByBankAccountNum(EncryptUtil.ace256Dec(view.getReqAccountNumber())));
        view.setReqCardNo(MaskingUtil.getMaskedByCreditCard( EncryptUtil.ace256Dec(view.getReqCardNo())));


        model.addAttribute("apdView", apdView);
        model.addAttribute("view", view);
        model.addAttribute("stateList", stateList);
        model.addAttribute("stateList01", stateList01);
        model.addAttribute("stateList02", stateList02);
        model.addAttribute("stateList03", stateList03);
        model.addAttribute("stateList04", stateList04);
        model.addAttribute("stateList05", stateList05);

        return returnUrl;
    }

    /**
     * 설명 : 유심 주문 상세조회
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param requestSelfDlvry
     * @param model
     * @return
     */
    @RequestMapping(value = {"/order/reqSelfDlvryView.do","/m/order/reqSelfDlvryView.do"}  )
    public String reqSelfDlvryView( HttpServletRequest request, McpRequestSelfDlvryDto requestSelfDlvry
            , Model model )  {

        UserSessionDto usd = SessionUtils.getUserCookieBean();
        UserSessionDto guest = SessionUtils.getOrderSession();

        if(usd == null && guest ==null){
            throw new McpCommonException(NO_FRONT_SESSION_ORDER_EXCEPTION);
        }

        if(null!=guest && guest.getUserDivision().equals("02")){//비회원
              requestSelfDlvry.setCstmrName(guest.getName());//이름
            requestSelfDlvry.setDlvryTelFn(guest.getMobileNo().substring(0,3));//전화번호
            requestSelfDlvry.setDlvryTelMn(guest.getMobileNo().substring(3,guest.getMobileNo().length()-4));//전화번호
            requestSelfDlvry.setDlvryTelRn(guest.getMobileNo().substring(guest.getMobileNo().length()-4, guest.getMobileNo().length()));//전화번호

        }else if(usd.getUserDivision() != null){//일반회원 정회원
            //requestSelfDlvry.setOnlineAuthDi(usd.getPin());
            requestSelfDlvry.setCretId(usd.getUserId());
        }

        if (requestSelfDlvry.getSelfDlvryIdx() < 1) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        List<McpRequestSelfDlvryDto> rtnReqSelfDlvryList = odersvc.selectMcpRequestSelfDlvry(requestSelfDlvry, 0, 1);


        model.addAttribute("selfDlvry", requestSelfDlvry);
        model.addAttribute("selfDlvryData", rtnReqSelfDlvryList.get(0));

        String returnUrl = "/portal/order/reqSelfDlvryView";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl="/mobile/order/reqSelfDlvryView";
        }

        return returnUrl;

    }

    /**
     * <pre>
     * 설명     : 셀프개통 유심 배송 요청
     * @param model
     * @param  :
     * @return
     * @return: String
     * </pre>
     * @throws ParseException
     */
    @RequestMapping(value = {"/order/reqSelfDlvryListPageAjax.do","/m/order/reqSelfDlvryListPageAjax.do"}  )
    @ResponseBody
    public  Map<String, Object> reqSelfDlvryListPageAjax(HttpServletRequest request,
            McpRequestSelfDlvryDto requestSelfDlvry,PageInfoBean pageInfoBean) throws RuntimeException, ParseException {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

          //세센 아이디 확인(비회원 , 회원)
        UserSessionDto usd = (UserSessionDto)SessionUtils.getUserCookieBean();

        UserSessionDto guest = SessionUtils.getOrderSession();

        if(usd == null && guest ==null){
            rtnJson.put("RESULT_CODE", "0");
            return rtnJson;
        }

        if(null!=guest && guest.getUserDivision().equals("02")){//비회원

            try {
                  requestSelfDlvry.setCstmrName(guest.getName());//이름
                requestSelfDlvry.setDlvryTelFn(guest.getMobileNo().substring(0,3));//전화번호
                requestSelfDlvry.setDlvryTelMn(guest.getMobileNo().substring(3,guest.getMobileNo().length()-4));//전화번호
                requestSelfDlvry.setDlvryTelRn(guest.getMobileNo().substring(guest.getMobileNo().length()-4, guest.getMobileNo().length()));//전화번호
            } catch(IndexOutOfBoundsException e) {
                rtnJson.put("RESULT_CODE", "0");
                return rtnJson;
            } catch (Exception e) {
                rtnJson.put("RESULT_CODE", "0");
                return rtnJson;
            }

        }else if(usd.getUserDivision() != null){//일반회원 정회원
            //requestSelfDlvry.setOnlineAuthDi(usd.getPin());
            requestSelfDlvry.setCretId(usd.getUserId());

        }

        String toDay = DateTimeUtil.getFormatString("yyyyMMdd") ;

        if (StringUtils.isBlank(requestSelfDlvry.getSysRdateE())) {
            requestSelfDlvry.setSysRdateE(toDay);
        }

        if (StringUtils.isBlank(requestSelfDlvry.getSysRdateS())) {
            requestSelfDlvry.setSysRdateS(DateTimeUtil.addMonths(toDay,-1));
        } else {
            //1년 이내에서 조회 가능 -웝취약점 보안
            String limitDay = DateTimeUtil.addMonths(toDay,-12);

            if( limitDay.compareTo(requestSelfDlvry.getSysRdateS()) > 0){
                throw new McpCommonJsonException("0001", INVALID_PARAMATER_EXCEPTION);
            }
        }


        /* 현재 페이지 번호 초기화 */
        if (pageInfoBean.getPageNo() == 0) {
            pageInfoBean.setPageNo(1);
        }

        /* 한페이지당 보여줄 리스트 수 */
        pageInfoBean.setRecordCount(10);

        /* 페이지 토탈 카운트 */
        int totalCount = odersvc.selectMcpRequestSelfDlvryCount(requestSelfDlvry);
        pageInfoBean.setTotalCount(totalCount);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();

        if("Y".equals(NmcpServiceUtils.isMobile())){
            skipResult = 0;
            maxResult = totalCount;
        }

        List<McpRequestSelfDlvryDto> resultList = odersvc.selectMcpRequestSelfDlvry(requestSelfDlvry, skipResult, maxResult);
        int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + resultList.size();

        for (McpRequestSelfDlvryDto view : resultList) {


            //웹취약성 조치 -  가입자(본인)의 성함, 가입 전화번호가 제외
            view.setCstmrName("");
            view.setDlvryAddr("");
            view.setDlvryAddrDtl("");
            view.setDlvryJibunAddrDtl("");
            view.setDlvryMemo("");
            view.setDlvryAddrDtl("");
            view.setDlvryName("");
            view.setDlvryNo("");
            view.setDlvryPost("");
            view.setDlvryTel("");
            view.setDlvryTelFn("");
            view.setDlvryTelMn("");
            view.setDlvryTelRn("");
        }

        rtnJson.put("stateCountList",odersvc.selectMcpRequestSelfDlvryStateCount(requestSelfDlvry));
        rtnJson.put("stateNowCountList",odersvc.selectMcpRequestNowDlvryStateCount(requestSelfDlvry));
        rtnJson.put("RESULT_CODE", AJAX_SUCCESS);
        rtnJson.put("total", totalCount);
        rtnJson.put("listCount", listCount);
        rtnJson.put("pageInfoBean", pageInfoBean);
        rtnJson.put("selfDlvry", requestSelfDlvry);
          rtnJson.put("resultList", resultList);

          return rtnJson;
    }

    /**
     * 설명 : 임시 저장 조회 AJAX
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param dto
     * @return
     * @throws RuntimeException
     * @throws ParseException
     */
    @RequestMapping(value = {"/order/orderTempxListAajx.do","/m/order/orderTempxListAajx.do"})
    @ResponseBody
    public Map<String, Object> orderTempxListAajx(
            HttpServletRequest request,
              OrderDto dto) throws RuntimeException, ParseException {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        //세센 아이디 확인(비회원 , 회원)
        UserSessionDto usd = SessionUtils.getUserCookieBean();

        UserSessionDto guest = SessionUtils.getOrderSession();

        //log.debug("=================================================================================================================================================");
        //log.debug("usd==="+usd);
        //log.debug("guest==="+guest);
        //log.debug("=================================================================================================================================================");
        if(usd == null && guest ==null){
            rtnJson.put("RESULT_CODE", "0");
            return rtnJson;

        }

        String nowDateString = DateTimeUtil.getFormatString("yyyyMMdd");
        dto.setSearchStart(nowDateString);
        dto.setSearchEnd(DateTimeUtil.addDays(nowDateString, -7));

        dto.setOrgnId(orgnId);

        //사용자 검증
        if(null!=guest && guest.getUserDivision().equals("02")){//비회원
            dto.setCstmrName(guest.getName());
            dto.setCstmrMobileFn(guest.getMobileNo().substring(0,3));
            dto.setCstmrMobileMn(guest.getMobileNo().substring(3,guest.getMobileNo().length()-4));
            dto.setCstmrMobileRn(guest.getMobileNo().substring(guest.getMobileNo().length()-4, guest.getMobileNo().length()));
        } else if(usd.getUserDivision() !=null){//일반회원 정회원
            dto.setCretId(usd.getUserId());
        }


        int total = odersvc.selectOrderTempGroupListCount(dto);

        List<OrderDto> resultList;

        resultList = odersvc.selectOrderTempGroupList(dto, 0, total);

        rtnJson.put("RESULT_CODE", AJAX_SUCCESS);
        rtnJson.put("resultList", resultList);
          rtnJson.put("totalCount", resultList.size());

        return rtnJson;

    }

    /**
     * 설명 : 임시 저장 조회 AJX
     * @author key
     * @Date 2021.12.30
     * @param dto
     * @param pageInfoBean
     * @param request
     * @return
     * @throws RuntimeException
     * @throws ParseException
     */
    @RequestMapping(value = {"/order/orderTempPageGroupListAjax.do","/m/order/orderTempPageGroupListAjax.do"})
    @ResponseBody
    public Map<String, Object> orderTempPageGroupListAjax(
            OrderDto dto,PageInfoBean pageInfoBean,HttpServletRequest request) throws RuntimeException, ParseException {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        //세센 아이디 확인(비회원 , 회원)
        UserSessionDto usd = SessionUtils.getUserCookieBean();
        UserSessionDto guest = SessionUtils.getOrderSession();

        //log.debug("=================================================================================================================================================");
        //log.debug("usd==="+usd);
        //log.debug("guest==="+guest);
        //log.debug("=================================================================================================================================================");
        if(usd == null && guest ==null){
            rtnJson.put("RESULT_CODE", "0");
            return rtnJson;

        }


        String nowDateString = DateTimeUtil.getFormatString("yyyyMMdd");
        dto.setSearchStart(DateTimeUtil.addMonths(nowDateString, -1));
        dto.setSearchEnd(nowDateString);

        dto.setOrgnId(orgnId);

          if(null!=guest && guest.getUserDivision().equals("02")){//비회원

              try {
                  dto.setCstmrName(guest.getName());//이름
                  dto.setCstmrMobileFn(guest.getMobileNo().substring(0,3));//전화번호
                  dto.setCstmrMobileMn(guest.getMobileNo().substring(3,guest.getMobileNo().length()-4));//전화번호
                  dto.setCstmrMobileRn(guest.getMobileNo().substring(guest.getMobileNo().length()-4, guest.getMobileNo().length()));//전화번호
              } catch(IndexOutOfBoundsException e) {
                  rtnJson.put("RESULT_CODE", "0");
                  return rtnJson;
              } catch (Exception e) {
                  rtnJson.put("RESULT_CODE", "0");
                  return rtnJson;
              }

        }else if(usd.getUserDivision() != null){//일반회원 정회원
            //정회원일때 cret_id 로 찾는다 로그인 상태에서 신청시 cret_id로그인아이디를 넣는다
            dto.setCretId(usd.getUserId());

        }

        /* 현재 페이지 번호 초기화 */
        if (pageInfoBean.getPageNo() == 0) {
            pageInfoBean.setPageNo(1);
        }
        /* 한페이지당 보여줄 리스트 수 */
        pageInfoBean.setRecordCount(10);
        pageInfoBean.setPageSize(10);
        /* 카운터 조회 */
        int total = odersvc.selectOrderTempListCount(dto);
        pageInfoBean.setTotalCount(total);

        // 총페이지와 요청 페이지 비교
        if (pageInfoBean.getTotalPageCount() < pageInfoBean.getPageNo()) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int nextResult = (pageInfoBean.getPageNo() * pageInfoBean.getRecordCount()) < total ? (pageInfoBean.getPageNo() * pageInfoBean.getRecordCount()) : total;
        List<OrderDto> resultList;

        if("Y".equals(NmcpServiceUtils.isMobile())){
            skipResult = 0;
            nextResult = total;
        }

        resultList = odersvc.selectOrderTempList(dto, skipResult, nextResult);
        for (OrderDto view : resultList) {
            //웹취약성 조치 -  가입자(본인)의 성함, 가입 전화번호가 제외
            view.setCstmrMobileFn("");
            view.setCstmrMobileMn("");
            view.setCstmrMobileRn("");
            view.setCstmrName("");
        }
        rtnJson.put("RESULT_CODE", AJAX_SUCCESS);
        rtnJson.put("resultList", resultList);
        rtnJson.put("pageInfoBean", pageInfoBean);

        return rtnJson;

    }

    /**
     * 설명 : 주문 조회 AJAX
     * @author key
     * @Date 2021.12.30
     * @param dto
     * @param pageInfoBean
     * @param request
     * @return
     * @throws RuntimeException
     * @throws ParseException
     */
    @RequestMapping(value = {"/order/orderPageGroupListAjax.do","/m/order/orderPageGroupListAjax.do"})
    @ResponseBody
    public Map<String, Object> orderPageGroupListAjax(
            OrderDto dto,PageInfoBean pageInfoBean,HttpServletRequest request) throws RuntimeException, ParseException {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        //세센 아이디 확인(비회원 , 회원)
        UserSessionDto usd = SessionUtils.getUserCookieBean();

        UserSessionDto guest = SessionUtils.getOrderSession();

        //log.debug("=================================================================================================================================================");
        //log.debug("usd==="+usd);
        //log.debug("guest==="+guest);
        //log.debug("=================================================================================================================================================");
        if(usd == null && guest ==null){
            rtnJson.put("RESULT_CODE", "0");
            return rtnJson;
        }

        String toDay = DateTimeUtil.getFormatString("yyyyMMdd") ;

        if (StringUtils.isBlank(dto.getSearchEnd())) {
            dto.setSearchEnd(toDay);
        }

        if (StringUtils.isBlank(dto.getSearchStart())) {
            dto.setSearchStart(DateTimeUtil.addMonths(toDay,-1));
        } else {
            //1년 이내에서 조회 가능 -웝취약점 보안
            String limitDay = DateTimeUtil.addMonths(toDay,-12);

            if( limitDay.compareTo(dto.getSearchStart()) > 0){
                throw new McpCommonJsonException("0001", INVALID_PARAMATER_EXCEPTION);
            }

        }

        dto.setOrgnId(orgnId);

          if(null!=guest && guest.getUserDivision().equals("02")){//비회원

              try {
                  dto.setCstmrName(guest.getName());//이름
                  dto.setCstmrMobileFn(guest.getMobileNo().substring(0,3));//전화번호
                  dto.setCstmrMobileMn(guest.getMobileNo().substring(3,guest.getMobileNo().length()-4));//전화번호
                  dto.setCstmrMobileRn(guest.getMobileNo().substring(guest.getMobileNo().length()-4, guest.getMobileNo().length()));//전화번호
              } catch(IndexOutOfBoundsException e) {
                  rtnJson.put("RESULT_CODE", "0");
                  return rtnJson;
              } catch (Exception e) {
                  rtnJson.put("RESULT_CODE", "0");
                  return rtnJson;
            }

        }else if(usd.getUserDivision() != null){//일반회원 정회원
            //정회원일때 cret_id 로 찾는다 로그인 상태에서 신청시 cret_id로그인아이디를 넣는다
            dto.setCretId(usd.getUserId());

        }

        /* 현재 페이지 번호 초기화 */
        if (pageInfoBean.getPageNo() == 0) {
            pageInfoBean.setPageNo(1);
        }
        /* 한페이지당 보여줄 리스트 수 */
        pageInfoBean.setRecordCount(10);
        pageInfoBean.setPageSize(10);
        /* 카운터 조회 */
        int total = odersvc.selectOrderGroupListCount(dto);
        pageInfoBean.setTotalCount(total);

        // 총페이지와 요청 페이지 비교
        if (pageInfoBean.getTotalPageCount() < pageInfoBean.getPageNo()) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int nextResult = (pageInfoBean.getPageNo() * pageInfoBean.getRecordCount()) < total ? (pageInfoBean.getPageNo() * pageInfoBean.getRecordCount()) : total;

        if("Y".equals(NmcpServiceUtils.isMobile())){
            skipResult = 0;
            nextResult = total;
        }

        List<OrderDto> resultList;

        resultList = odersvc.selectOrderGroupList(dto, skipResult, nextResult);
        for (OrderDto view : resultList) {

            //가격정보를 ...
            MspSaleSubsdMstDto  mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
            mspSaleSubsdMstDto.setSprtTp(view.getSprtTp());    //지원금유형 단말할인:KD ,요금할인:PM)
            mspSaleSubsdMstDto.setHndstAmt(view.getModelPriceInt() + view.getModelPriceVatInt() ); //단말금액(VAT)포함
            mspSaleSubsdMstDto.setSubsdAmt(view.getModelDiscount2Int());  //공시지원금
            mspSaleSubsdMstDto.setAgncySubsdAmt(view.getModelDiscount3Int());  //대리점 보조금
            mspSaleSubsdMstDto.setAgncySubsdMax(view.getMaxDiscount3Int());  //대리점 보조금(max)
            mspSaleSubsdMstDto.setModelMonthly(view.getModelMonthlyInt());   //단말기 할부 (24,36)
            mspSaleSubsdMstDto.setBaseAmt(view.getBaseAmtInt()) ; //기본요금
            mspSaleSubsdMstDto.setDcAmt(view.getDcAmtInt()) ; //할인금액(약정할인선택시 할인금액)
            //setPromotionDcAmt  //프로모션 할인 설정 시 필요
            mspSaleSubsdMstDto.setAddDcAmt(view.getAddDcAmtInt());  //추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)
            //할부이자율
            CmnGrpCdMst cdInfo = mspService.findCmnGrpCdMst("CMN0051", "20");  //할부수수료 default 값
            BigDecimal instRate = new BigDecimal(cdInfo.getEtc1());

            if (!StringUtils.isBlank(view.getModelSalePolicyCode())) {

                //---- API 호출 S ----//
                  RestTemplate restTemplate = new RestTemplate();
                  MspSalePlcyMstDto mspSalePlcyMstDto = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSalePlcyMst", view.getModelSalePolicyCode(), MspSalePlcyMstDto.class);
                 //---- API 호출 E ----//
                  if(mspSalePlcyMstDto != null && mspSalePlcyMstDto.getInstRate() != null) {
                      instRate  = mspSalePlcyMstDto.getInstRate();
                  }
            }

            //프로모션 금액

            Integer promotionDcAmt = 0;
            mspSaleSubsdMstDto.setOrgnId(orgnId);
            mspSaleSubsdMstDto.setRateCd(view.getSocCode());
            mspSaleSubsdMstDto.setOperType(view.getOperType());
            mspSaleSubsdMstDto.setAgrmTrm("");

            String plcySctnCd = "";
            if("MM".equals(view.getReqBuyType())) {
                plcySctnCd = "01";
            } else if("UU".equals(view.getReqBuyType())) {
                plcySctnCd = "02";
            }

            mspSaleSubsdMstDto.setPlcySctnCd(plcySctnCd);
            mspSaleSubsdMstDto.setOnOffType(view.getOnOffType());
            promotionDcAmt = new RestTemplate().postForObject(apiInterfaceServer + "/msp/getromotionDcAmt", mspSaleSubsdMstDto, Integer.class);
            mspSaleSubsdMstDto.setPromotionDcAmt(promotionDcAmt == null ? 0 : promotionDcAmt);

            mspSaleSubsdMstDto.setInstRate(instRate);   //할부 이자율
            view.setMspSaleSubsdMstDto(mspSaleSubsdMstDto);

            //웹취약성 조치 -  가입자(본인)의 성함, 가입 전화번호가 제외
            view.setCstmrMobileFn("");
            view.setCstmrMobileMn("");
            view.setCstmrMobileRn("");
            view.setCstmrName("");
        }

        List<OrderDto> stateCountList = odersvc.selectRequestOrderStateCount(dto);

        rtnJson.put("stateCountList",stateCountList);
        int PSTATE01 = 0;
        int PSTATE02 = 0;
        int PSTATE03 = 0;
        int PSTATE04 = 0;
        int PSTATE05 = 0;
        List<NmcpCdDtlDto> stateList =NmcpServiceUtils.getCodeList("PSTATE00");//신청진행상태 결제완료
        List<NmcpCdDtlDto> stateList01 =NmcpServiceUtils.getCodeList("PSTATE01");//신청진행상태 결제완료
        List<NmcpCdDtlDto> stateList02 =NmcpServiceUtils.getCodeList("PSTATE02");//신청진행상태   배송대기
        List<NmcpCdDtlDto> stateList03 =NmcpServiceUtils.getCodeList("PSTATE03");//신청진행상태 배송중
        List<NmcpCdDtlDto> stateList04 =NmcpServiceUtils.getCodeList("PSTATE04");//신청진행상태 개통대기
        List<NmcpCdDtlDto> stateList05 =NmcpServiceUtils.getCodeList("PSTATE05");//신청진행상태 개통완료
        for (OrderDto orderDto : stateCountList) {

            if("00".equals(orderDto.getState())) {
                PSTATE01 += orderDto.getStateCount();
            }

            if("01".equals(orderDto.getState()) || "02".equals(orderDto.getState()) || "09".equals(orderDto.getState()) || "07".equals(orderDto.getState()) || "08".equals(orderDto.getState())) {
                PSTATE02 += orderDto.getStateCount();
            }


            if("03".equals(orderDto.getState()) || "04".equals(orderDto.getState()) || "10".equals(orderDto.getState())) {
                PSTATE03 += orderDto.getStateCount();
            }


            if("20".equals(orderDto.getState()) || "11".equals(orderDto.getState()) || "13".equals(orderDto.getState())) {
                PSTATE04 += orderDto.getStateCount();
            }


            if("21".equals(orderDto.getState())) {
                PSTATE05 += orderDto.getStateCount();
            }

        }

        for (OrderDto orderDto : resultList) {
            if (stateList != null) {
                for (NmcpCdDtlDto nmcpCdDtlDto : stateList) {
                    if(orderDto.getRequestStateCode().equals(nmcpCdDtlDto.getDtlCd())) {
                        orderDto.setState("PSTATE00");
                    }
                }
            }
            if ( stateList01 != null) {
                for (NmcpCdDtlDto nmcpCdDtlDto : stateList01) {
                    if(orderDto.getRequestStateCode().equals(nmcpCdDtlDto.getDtlCd())) {
                        orderDto.setState("PSTATE01");
                    }
                }
            }
            if (stateList02 != null) {
                for (NmcpCdDtlDto nmcpCdDtlDto : stateList02) {
                    if(orderDto.getRequestStateCode().equals(nmcpCdDtlDto.getDtlCd())) {
                        orderDto.setState("PSTATE02");
                    }
                }
            }
            if ( stateList03 != null) {
                for (NmcpCdDtlDto nmcpCdDtlDto : stateList03) {
                    if(orderDto.getRequestStateCode().equals(nmcpCdDtlDto.getDtlCd())) {
                        orderDto.setState("PSTATE03");
                    }
                }
            }
            if (stateList04 != null) {
                for (NmcpCdDtlDto nmcpCdDtlDto : stateList04) {
                    if(orderDto.getRequestStateCode().equals(nmcpCdDtlDto.getDtlCd())) {
                        orderDto.setState("PSTATE04");
                    }
                }
            }
            if (stateList05 != null) {
                for (NmcpCdDtlDto nmcpCdDtlDto : stateList05) {
                    if(orderDto.getRequestStateCode().equals(nmcpCdDtlDto.getDtlCd())) {
                        orderDto.setState("PSTATE05");
                    }
                }
            }
        }




        Map<String,Integer> stateMap = new HashMap<String,Integer>();
        stateMap.put("PSTATE01", PSTATE01);
        stateMap.put("PSTATE02", PSTATE02);
        stateMap.put("PSTATE03", PSTATE03);
        stateMap.put("PSTATE04", PSTATE04);
        stateMap.put("PSTATE05", PSTATE05);

        rtnJson.put("RESULT_CODE", AJAX_SUCCESS);
        rtnJson.put("stateMap", stateMap);
        rtnJson.put("resultList", resultList);
        rtnJson.put("totalCount", total);
        rtnJson.put("pageInfoBean", pageInfoBean);

        return rtnJson;

    }

    /**
     * 설명 : 세션 검증
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param dto
     * @return
     * @throws RuntimeException
     * @throws ParseException
     */
    @RequestMapping(value = {"/order/sessionCheckAajx.do","/m/order/sessionCheckAajx.do"})
    @ResponseBody
    public Map<String, Object> sessionCheckAajx(
            HttpServletRequest request,
              OrderDto dto) throws RuntimeException, ParseException {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        //세센 아이디 확인(비회원 , 회원)
        UserSessionDto usd = SessionUtils.getUserCookieBean();
        UserSessionDto guest = SessionUtils.getOrderSession();

        //log.debug("=================================================================================================================================================");
        //log.debug("usd==="+usd);
        //log.debug("guest==="+guest);
        //log.debug("=================================================================================================================================================");
        if(usd == null && guest == null){
            rtnJson.put("RESULT_CODE", "00001");

        }else {
            rtnJson.put("RESULT_CODE", "00000");

        }

        return rtnJson;

    }


}
