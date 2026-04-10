package com.ktmmobile.mcp.coupon.controller;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.*;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.coupon.dto.CouponCategoryDto;
import com.ktmmobile.mcp.coupon.dto.CouponOutsideDto;
import com.ktmmobile.mcp.coupon.dto.McpUserCertDto;
import com.ktmmobile.mcp.coupon.service.CouponService;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.phone.dto.CommonSearchDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;


@Controller
public class CouponController {

    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private CouponService couponService;

    @Autowired
    private SmsSvc smsSvc;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private FormDtlSvc formDtlSvc;

    @Autowired
    private MypageService mypageService;

    /**
     * 쿠폰 메인페이지
     * @param request
     * @param model
     * @return String
     * @author wooki
     * @Date : 2023.03.20
     */
    @RequestMapping(value = {"/coupon/couponMain.do", "/m/coupon/couponMain.do"})
    public String couponMain(HttpServletRequest request, Model model, @ModelAttribute CommonSearchDto commonSearchDto) {

        String returnUrl = "/portal/coupon/couponMain";
        String userRtnUrl = "/coupon/couponMain.do";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/coupon/couponMain";
            userRtnUrl = "/m/coupon/couponMain.do";
        }

        CouponCategoryDto ctgDto = new CouponCategoryDto();
        List<CouponCategoryDto> mbList = null;
        //로그인여부 판단
        boolean isLogin = false;
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if (userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
            ctgDto.setUserId(userSession.getUserId());
            ctgDto.setUserDivision(userSession.getUserDivision());
            isLogin = true;
        }

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(userRtnUrl);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto, 2)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("CommonSearchDto", commonSearchDto);
            //model.addAttribute("ListOrderEnum", listOrderEnum);
            return "/common/successRedirect";
        }

        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.COUPON_CATEGORY);
        List<NmcpCdDtlDto> couponCategoryList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

        boolean isData = false;

        try {
            //M쿠폰 리스트 불러오기
            mbList = couponService.getMbershipList(ctgDto);

            if (null != mbList && !mbList.isEmpty()) {
                isData = true;
            }
        } catch (DataAccessException e) {
            logger.debug("DataAccessException 오류");
        } catch (Exception e) {
            logger.debug("DB 오류");
        }

        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("C001")); // bannCtg
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("isData", isData);
        model.addAttribute("mbList", mbList);
        model.addAttribute("couponCategoryList", couponCategoryList);
        model.addAttribute("couponAccessCnt", StringUtil.addComma(fCommonSvc.selectPageViewsCount(request.getRequestURI()))); // m쿠폰 페이지 조회수

        return returnUrl;
    }

    /**
     * 쿠폰 상세 팝업
     * @param request
     * @param model
     * @return String
     * @author wooki
     * @Date : 2023.03.21
     */
    @RequestMapping(value = {"/coupon/couponDetailPop.do", "/m/coupon/couponDetailPop.do"})
    public String couponDetailPop(HttpServletRequest request, ModelMap model, @ModelAttribute CouponCategoryDto ctgDto) {

        String pageUrl = "";
        //중복요청 체크
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/coupon/couponDetailPop";
        } else {
            pageUrl = "/portal/coupon/couponDetailPop";
        }

        CouponCategoryDto mbDtlDto = null;

        try {
            //M쿠폰 조회
            mbDtlDto = couponService.getMbershipDetail(ctgDto);
        } catch (DataAccessException e) {
            logger.debug("DataAccessException 오류");
        } catch (Exception e) {
            logger.debug("DB 오류");
        }

        model.addAttribute("mbDtlDto", mbDtlDto);

        return pageUrl;
    }

    /**
     * 쿠폰 다운로드 팝업
     * @param request
     * @param model
     * @return String
     * @author wooki
     * @Date : 2023.03.21
     */
    @RequestMapping(value = {"/coupon/couponDownPop.do", "/m/coupon/couponDownPop.do"})
    public String couponDownPop(HttpServletRequest request, ModelMap model, @ModelAttribute CouponCategoryDto ctgDto) {

        if (ctgDto.getCoupnCtgCdList().isEmpty()) {
            throw new McpCommonJsonException("xxxx", "비정상적인 접근입니다.");
        }
        String pageUrl = "";
        //중복요청 체크
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/coupon/couponDownPop";
        } else {
            pageUrl = "/portal/coupon/couponDownPop";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (null == userSession || StringUtils.isEmpty(userSession.getUserId())) {
            return "redirect:/loginForm.do";
        }

        List<CouponCategoryDto> CouponCategoryList = new ArrayList<>();
        try {
            //M쿠폰 조회
            for (String coupnCtgCd : ctgDto.getCoupnCtgCdList()) {
                ctgDto.setCoupnCtgCd(coupnCtgCd);
                CouponCategoryDto couponCategoryDto = couponService.getMbershipDetail(ctgDto);
                CouponCategoryList.add(couponCategoryDto);
            }
        } catch (DataAccessException e) {
            logger.debug("DataAccessException 오류");
        } catch (Exception e) {
            logger.debug("DB 오류");
        }
        //약관가져오기1
        FormDtlDTO formDto = new FormDtlDTO();
        formDto.setCdGroupId1("TERMSCOUPON");
        formDto.setCdGroupId2("TERMSCOUPON01");
        FormDtlDTO rtnFormDto = formDtlSvc.FormDtlView(formDto);

        if (null != rtnFormDto) {
            String docContent = StringUtil.NVL(rtnFormDto.getDocContent(), "");
            rtnFormDto.setDocContent(ParseHtmlTagUtil.convertHtmlchars(docContent));
        }

        model.addAttribute("CouponCategoryList", CouponCategoryList);
        model.addAttribute("rtnFormDto", rtnFormDto);

        return pageUrl;
    }

    /**
     * 로그인 유무 검사
     * @return Map
     * @author wooki
     * @Date : 2023.03.21
     */
    @RequestMapping(value = {"/coupon/checkLogin.do", "/m/coupon/checkLogin.do"})
    @ResponseBody
    public Map<String, Object> getCheckLogin() {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        boolean isLogin = false;

        if (userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
            isLogin = true;
        }

        rtnMap.put("isLogin", isLogin);

        return rtnMap;
    }

    /**
     * 쿠폰 다운로드
     * @param request
     * @param model
     * @return Map
     * @author wooki
     * @Date : 2023.03.22
     */
    @RequestMapping(value = {"/coupon/downCoupon.do", "/m/coupon/downCoupon.do"})
    @ResponseBody
    public Map<String, Object> downCoupon(HttpServletRequest request, ModelMap model, @RequestBody CouponCategoryDto ctgDto) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //1.로그인 정보 없으므로 리턴
        if (null == userSession || StringUtils.isEmpty(userSession.getUserId())) {
            rtnMap.put("RESULT_CODE", "FAIL1");
            rtnMap.put("RESULT_MSG", "로그인 정보가 없습니다.<br><br>로그인 후 이용해 주세요.");
            return rtnMap;
        }
        //1-2.정회원이 아닌 경우 리턴
        if (!"01".equals(userSession.getUserDivision())) {
            rtnMap.put("RESULT_CODE", "FAIL1");
            rtnMap.put("RESULT_MSG", "정회원만 이용가능합니다.<br><br>정회원 인증 후 이용해 주세요.");
            return rtnMap;
        }

        String smsRcvNo = ctgDto.getSmsRcvNo(); //입력한 휴대폰번호

        //2.휴대폰정보 없으면 리턴
        if (StringUtils.isEmpty(smsRcvNo)) {
            rtnMap.put("RESULT_CODE", "FAIL2");
            rtnMap.put("RESULT_MSG", "휴대폰 정보가 없습니다.<br><br>다시 시도해 주세요.");
            return rtnMap;
        }

        //3.쿠폰카테고리코드 없으면 리턴
        if (ctgDto.getCoupnCtgCdList().isEmpty()) {
            rtnMap.put("RESULT_CODE", "FAIL3");
            rtnMap.put("RESULT_MSG", "쿠폰 정보가 없습니다.<br><br>다시 시도해 주세요.");
            return rtnMap;
        }

        //4.계약 정보 확인(API호출) - 입력한 휴대폰 번호로 계약정보 있는지 확인
        McpUserCertDto userCertDto = new McpUserCertDto();
        userCertDto.setMobileNo(smsRcvNo);
        //입력받은 mobile_no로 회선정보 조회
        List<McpUserCertDto> cntrInfoAList = couponService.getMcpUserCntrInfoA(userCertDto);

        //4-1.계약정보 없으면 리턴
        if (null == cntrInfoAList) {
            rtnMap.put("RESULT_CODE", "FAIL4");
            rtnMap.put("RESULT_MSG", "입력하신 정보는 kt M모바일에 가입된 정보가<br>아니거나 사용중인 상태가 아닙니다.<br><br>확인 후 다시 입력 해 주세요.");
            return rtnMap;
        }

        //5.정회원 or 준회원(=엠모바일 회선을 사용하나 정회원 인증을 하지 않은 회원 or 홈페이지만 가입자) 구분해서 처리
        boolean isMemOk = false; //명의확인여부
        String rtnContractNum = null; //api에서 조회해온 계약번호
        //5-1.준회원은 4번에서 조회해온 customer_id로 MSP_JUO_SUB_INFO에서 모든 회선 정보를 가져와서 홈페이지 회원 가입시 입력한 휴대폰번호를 비교한다.
        if ("00".equals(userSession.getUserDivision())) {
            //5-1-1.customer_id set
            String rtnCustomerId = cntrInfoAList.get(0).getCustomerId();

            //5-1-2.customer_id가 null이 아니면 다음 실행
            if (StringUtil.isNotBlank(rtnCustomerId)) {
                McpUserCertDto mucDto = new McpUserCertDto();
                mucDto.setCustomerId(rtnCustomerId);
                //5-1-3.customer_id로 모든 회선정보 조회
                List<McpUserCertDto> rtnMucList = couponService.getMcpUserCntrInfoA(mucDto);

                boolean realMember = false;
                if (null != rtnMucList) {
                    //5-1-4.회원가입 시 입력한 휴대폰번호와 5-1-3에서 조회한 회선정보 중 일치하는 휴대폰번호가 있다면 준회원 중에서도 엠모바일 회선 사용자임
                    for (McpUserCertDto rtnDto1 : rtnMucList) {
                        if (userSession.getMobileNo().equals(rtnDto1.getMobileNo())) {
                            realMember = true;
                        }
                    }

                    //5-1-5.엠모바일 회선 사용하는 준회원 이라면, 회선 리스트의 휴대폰 번호와 입력한 휴대폰 번호가 일치하는지 확인
                    if (realMember) {
                        for (McpUserCertDto rtnDto2 : rtnMucList) {
                            if (smsRcvNo.equals(rtnDto2.getMobileNo())) {
                                isMemOk = true; //명의확인여부셋
                                rtnContractNum = rtnDto2.getContractNum(); //계약번호셋
                            }
                        }
                    }
                }
            }
            //5-2.정회원이면 소유한 회선 정보 중 비교해서 같으면 쿠폰다운 가능
        } else {
            McpUserCertDto regularDto = new McpUserCertDto();
            regularDto.setUserId(userSession.getUserId());
            //5-2-1.회원아이디로 모든 회선 조회
            List<McpUserCertDto> rtnRegularList = couponService.getMcpUserCntrInfoAByUserId(regularDto);

            if (null != rtnRegularList) {
                //5-2-2.모든 회선의 휴대폰 번호 중 입력한 휴대폰 번호와 일치하는지 확인
                for (McpUserCertDto rtnRegularDto : rtnRegularList) {
                    if (smsRcvNo.equals(rtnRegularDto.getMobileNo())) {
                        isMemOk = true; //명의확인여부셋
                        rtnContractNum = rtnRegularDto.getContractNum(); //계약번호셋
                    }
                }
            }
        }

        //5-3.명의 불일치면 리턴
        if (!isMemOk) {
            rtnMap.put("RESULT_CODE", "FAIL5");
            rtnMap.put("RESULT_MSG", "회선 정보가 일치하지 않습니다.<br><br>휴대폰 번호 확인 후 다시 시도 바랍니다.");
            return rtnMap;
        }

        //5-4.계약번호 없으면 리턴
        if (null == rtnContractNum) {
            rtnMap.put("RESULT_CODE", "FAIL6");
            rtnMap.put("RESULT_MSG", "계약번호가 없습니다.<br><br>이 메시지가 계속되면 고객센터 문의 바랍니다.");
            return rtnMap;
        }

        //6.쿠폰 발급 이력 조회
        ctgDto.setUserId(userSession.getUserId());
        List<String> coupnCtgCdList = ctgDto.getCoupnCtgCdList();
        List<String> coupnCtgNmList = ctgDto.getCoupnCtgNmList();
        List<String> smsSndPosblYnList = ctgDto.getSmsSndPosblYnList();

        List<Map<String, Object>> couponList = new ArrayList<>();
        for (int i = 0; i < coupnCtgCdList.size(); i++) {
            Map<String, Object> resultMap = new HashMap<String, Object>();

            String coupnCtgCd = coupnCtgCdList.get(i);
            String coupnCtgNm = coupnCtgNmList.get(i);
            String smsSndPosblYn = smsSndPosblYnList.get(i);

            ctgDto.setCoupnCtgCd(coupnCtgCd);
            CouponCategoryDto couponReturnDto = couponService.getCouponDupInfo(ctgDto);

            //NMCP_COUPN_MBERSHIP_BAS, NMCP_COUPN_MBERSHIP_FAIL 에 update 할 dto set
            CouponOutsideDto couponOutsideDto = new CouponOutsideDto();
            couponOutsideDto.setCoupnCtgCd(coupnCtgCd);
            couponOutsideDto.setUserId(userSession.getUserId());
            couponOutsideDto.setSvcCntrNo(rtnContractNum);
            couponOutsideDto.setContractNum(rtnContractNum);
            couponOutsideDto.setSmsRcvNo(smsRcvNo);
            couponOutsideDto.setCretId(userSession.getUserId());
            couponOutsideDto.setCretIp(request.getRemoteAddr());
            couponOutsideDto.setAmdId(userSession.getUserId());
            couponOutsideDto.setAmdIp(request.getRemoteAddr());
            if (null != couponReturnDto) {
                couponOutsideDto.setCoupnNo(couponReturnDto.getCoupnNo());
                String strDown = couponReturnDto.getCoupnGiveDate(); //기발급된 날짜
                String stanMonth = couponReturnDto.getDnStan(); //기준월(1:1개월, 3:3개월)

                //sms직접발송 사용여부 없으면 Y으로 set
                if (StringUtils.isEmpty(smsSndPosblYn)) {
                    smsSndPosblYn = "Y";
                }
                //6-1.기준월이 없으면 리턴(쿠폰발급기준이 없는 경우.. 발급기준이 없는 exception 대비)
                if (StringUtil.isBlank(stanMonth)) {
                    resultMap.put("RESULT_CODE", "FAIL7");
                    //resultMap.put("RESULT_MSG", "쿠폰발급기준이 없습니다.<br><br>이 메시지가 계속되면 고객센터 문의 바랍니다.");
                    resultMap.put("RESULT_MSG", "시스템 오류");
                    resultMap.put("coupnCtgNm", coupnCtgNm);

                    couponOutsideDto.setRsltCd("01"); // 쿠폰발급기준누락
                    couponService.insertCoupnFailHist(couponOutsideDto);
                    couponList.add(resultMap);
                    continue;
                }

                if (StringUtil.isNotBlank(strDown) && StringUtil.isNotBlank(stanMonth)) {

                    //6-2.기발급 날짜와 오늘 날짜 비교
                    strDown = strDown.substring(0, 6); //기발급 년월
                    SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMM");
                    Date todayDate = new Date();
                    String strThisMonth = sFormat.format(todayDate); //현재 년월

                    int intDownYear = Integer.parseInt(strDown.substring(0, 4));
                    int intDownMonth = Integer.parseInt(strDown.substring(4));
                    int intTodayYear = Integer.parseInt(strThisMonth.substring(0, 4));
                    int intTodayMonth = Integer.parseInt(strThisMonth.substring(4));

                    Calendar calDown = new GregorianCalendar(intDownYear, intDownMonth, 1); //기발급 날짜
                    Calendar calToday = new GregorianCalendar(intTodayYear, intTodayMonth, 1); //오늘 날짜

                    int yearDiff = calToday.get(Calendar.YEAR) - calDown.get(Calendar.YEAR); //년 차이
                    int monthDiff = (yearDiff * 12) + calToday.get(Calendar.MONTH) - calDown.get(Calendar.MONTH); //월 차이

                    //6-3.기발급된 날짜와 현재의 월 차이가 쿠폰발급기준(1개월에 1회, 3개월에 1회)에 맞지 않으면 리턴
                    if (monthDiff < Integer.parseInt(stanMonth)) {
                        resultMap.put("RESULT_CODE", "FAIL8");
                        //resultMap.put("RESULT_MSG", "이미 발급 받은 이력이 있어<br>추가 발급이 불가합니다.<br><br>본 무료쿠폰은 동일쿠폰에 대해<br>회선당 " + stanMonth +"개월에 1회에 한해 신청 가능합니다.");
                        resultMap.put("RESULT_MSG", "중복 발행");
                        resultMap.put("coupnCtgNm", coupnCtgNm);

                        couponOutsideDto.setRsltCd("02"); //중복발행
                        couponService.insertCoupnFailHist(couponOutsideDto);
                        couponList.add(resultMap);
                        continue;
                    }
                }
            }

            //7.쿠폰 잔여수량 있는지 확인
            int leftQnty = couponService.getCouponLeftQnty(coupnCtgCd);

            //잔여수량 없으면 리턴
            if (leftQnty == 0) {
                resultMap.put("RESULT_CODE", "FAIL9");
                //resultMap.put("RESULT_MSG", "준비된 쿠폰이 모두 소진되었습니다.");
                resultMap.put("RESULT_MSG", "잔여 수량 부족");
                resultMap.put("coupnCtgNm", coupnCtgNm);

                couponOutsideDto.setRsltCd("03");    //잔여 수량 부족
                couponService.insertCoupnFailHist(couponOutsideDto);
                couponList.add(resultMap);
                continue;
            }

            if ("N".equals(smsSndPosblYn)) {
                couponOutsideDto.setSmsSndYn("Y");
                couponOutsideDto.setSmsSndSuccessYn("Y");
            } else {
                couponOutsideDto.setSmsSndYn("N");
                couponOutsideDto.setSmsSndSuccessYn("N");
            }

            //8.쿠폰발급(NMCP_COUPN_MBERSHIP_BAS에 update)
            int updateResult = couponService.updateCouponMbership(couponOutsideDto);

            //8-1.쿠폰발급(update) 성공이면 문자발송
            if (updateResult != 0) {

                if ("N".equals(smsSndPosblYn)) { //sms직접발송 미사용일 때에만 문자발송(Y:후발송, N:즉시발송)
                    //8-1-1.발급받은 쿠폰 정보 조회
                    CouponCategoryDto forSmsDto = couponService.getCouponInfoForSms(couponOutsideDto);
                    if (null != forSmsDto) {
                        //8-1-2.문자발송
                        int smsReulst = this.sendLmsByTemplate(forSmsDto);
                        if (smsReulst <= 0) {
                            //8-1-3.문자발송 실패면 NMCP_COUPN_MBERSHIP_BAS에 SMS_SND_YN, SMS_SND_SUCCESS_YN N으로 udpate
                            couponOutsideDto.setCoupnNo(forSmsDto.getCoupnNo());
                            couponService.updateMbershipSmsFail(couponOutsideDto);
                        }
                    }
                }

                resultMap.put("SMS_SEND_POSBL_YN", smsSndPosblYn); //sms즉시발송 사용여부
                resultMap.put("RESULT_CODE", "SUCCESS");
                resultMap.put("coupnCtgNm", coupnCtgNm);
                couponList.add(resultMap);
                //8-2.update 실패 (시스템오류)
            } else {
                resultMap.put("RESULT_CODE", "FAIL10");
                //resultMap.put("RESULT_MSG", "쿠폰 다운로드 실패했습니다.<br><br>이 메시지가 계속되면 고객센터 문의 바랍니다.");
                resultMap.put("RESULT_MSG", "시스템 오류");
                resultMap.put("coupnCtgNm", coupnCtgNm);
                couponOutsideDto.setRsltCd("04"); //시스템 오류
                couponService.insertCoupnFailHist(couponOutsideDto);
                couponList.add(resultMap);
            }
        }

        rtnMap.put("RESULT_CODE", "SUCCESS");
        rtnMap.put("COUPON_LIST", couponList);

        return rtnMap;
    }

    /**
     * 설명 : 템플릿을 이용해 고객에게 쿠폰 문자 발송
     * @Author : wooki
     * @Date : 2023.03.06
     * @param CouponOutsideDto
     * @return int
     */
    public int sendLmsByTemplate(CouponCategoryDto ctgDto) {
        //M쿠폰 템플릿 가져오기
        MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(253);

        String endDate = ctgDto.getPstngEndDate();
        if (8 < endDate.length()) {
            endDate = endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-" + endDate.substring(6, 8);
        }
        mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{coupnNm}", ctgDto.getCoupnCtgNm()));
        mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{coupnNo}", ctgDto.getCoupnNo()));
        mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{endDate}", endDate));
        if (null != ctgDto.getLinkMo() && !"".equals(ctgDto.getLinkMo())) {
            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{linkMo}", "- URL : " + ctgDto.getLinkMo()));
        } else {
            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{linkMo}", ""));
        }

        String subject = "[kt M모바일] 쿠폰 지급 "; //MMS 제목
        String callBack = mspSmsTemplateMstDto.getCallback(); //고객센터
        String svcNo = ctgDto.getSmsRcvNo();

        // 즉시 발송 대상
        //int smsResult = smsSvc.sendLms(subject, svcNo, mspSmsTemplateMstDto.getText(), callBack);

        //20230427 sms발송 --> 카카오알림톡 발송으로 수정
        //int smsResult = smsSvc.sendKakaoNoti(subject, svcNo, mspSmsTemplateMstDto.getText(), callBack, mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
        int smsResult = smsSvc.sendKakaoNoti(subject, svcNo, mspSmsTemplateMstDto.getText(), callBack, mspSmsTemplateMstDto.getkTemplateCode(),
                Constants.KAKAO_SENDER_KEY, "253");

        return smsResult;
    }

    @RequestMapping(value = "/coupon/getBenefitCouponListPagedAjax.do")
    @ResponseBody
    public Map<String, Object> getBenefitCouponListPaged(PageInfoBean pageInfoBean) {
        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();
        int maxResult = pageInfoBean.getRecordCount();

        List<CouponCategoryDto> couponList =  null;
        int totalCount = 0;

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        //비로그인
        if (userSessionDto == null || StringUtils.isEmpty(userSessionDto.getUserId())) {
            totalCount = couponService.countMbershipList();
            couponList = couponService.getMbershipListPaged(skipResult, maxResult);
        }else {
            if("00".equals(userSessionDto.getUserDivision())) { //준회원
                totalCount = couponService.countMbershipList();
                couponList = couponService.getMbershipListPaged(skipResult, maxResult);
            }else { 	//정회원
                     List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
                     MyPageSearchDto searchVO = new MyPageSearchDto();
                     boolean chk = mypageService.checkUserType(searchVO, cntrList, userSessionDto);

                     if(chk) {
                         McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());
                             if(mcpUserCntrMngDto !=null && mcpUserCntrMngDto.getSoc() !=null) {
                                 totalCount = couponService.countMbershipLoginList(mcpUserCntrMngDto.getSoc());
                                 couponList = couponService.getMbershipListPaged(skipResult, maxResult,mcpUserCntrMngDto.getSoc());
                             }
                        }

            }
        }

        pageInfoBean.setTotalCount(totalCount);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("couponList", couponList);
        resultMap.put("totalPageCount", pageInfoBean.getTotalPageCount());
        resultMap.put("couponTotalCount", totalCount);
        return resultMap;
    }

    @RequestMapping(value = "/coupon/getBenefitCouponDetailAjax.do")
    @ResponseBody
    public Map<String, Object> getBenefitCouponDetail(CouponCategoryDto ctgDto) {
        Map<String, Object> resultMap = new HashMap<>();
        CouponCategoryDto couponDetail = couponService.getBenefitCouponDetail(ctgDto);
         resultMap.put("couponDetail",couponDetail);
        return resultMap;
    }

    @RequestMapping(value = "/coupon/getMyCouponListAjax.do")
    @ResponseBody
    public Map<String, Object> getMyCouponList(MyPageSearchDto searchVO) {
        Map<String, Object> resultMap = new HashMap<>();

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto == null || StringUtils.isEmpty(userSessionDto.getUserId())) {
            resultMap.put("resultCd", "00001");
            return resultMap;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSessionDto);

        if (!chk) {
            resultMap.put("resultCd", "00002");
            return resultMap;
        }

        CoupInfoDto coupInfoDto = new CoupInfoDto();
        coupInfoDto.setSvcCntrNo(searchVO.getContractNum());
        coupInfoDto.setCoupnDivCd("MB");
        List<CoupInfoDto> myCouponList = couponService.getUseCoupInfoList(coupInfoDto);

        resultMap.put("resultCd", AJAX_SUCCESS);
        resultMap.put("myCouponList", myCouponList);
        return resultMap;
    }


    @RequestMapping(value = "/coupon/benefitCouponCheckAjax.do")
    @ResponseBody
    public Map<String, Object> benefitCouponCheck(CouponCategoryDto ctgDto) {

        Map<String, Object> resultMap = new HashMap<>();
         UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String couponUseYn = "N";

           if (userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
             ctgDto.setUserId(userSession.getUserId());
             ctgDto.setUserDivision(userSession.getUserDivision());

            //쿠폰 사용여부 체크
            CouponCategoryDto ctgDto2 = new CouponCategoryDto();

            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            MyPageSearchDto searchVO = new MyPageSearchDto();
            boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);

            ctgDto2.setCoupnCtgCd(ctgDto.getCoupnCtgCd());
            ctgDto2.setUserId(userSession.getUserId());

            if(chk) {
                ctgDto2.setSmsRcvNo(searchVO.getCtn());
              }else {
                  ctgDto2.setSmsRcvNo(userSession.getMobileNo());
              }

            CouponCategoryDto couponReturnDto = couponService.getCouponDupInfo(ctgDto2);

            if (null != couponReturnDto) {
                String strDown = couponReturnDto.getCoupnGiveDate(); //기발급된 날짜
                String stanMonth = couponReturnDto.getDnStan(); //기준월(1:1개월, 3:3개월)

                    if (StringUtil.isNotBlank(strDown) && StringUtil.isNotBlank(stanMonth)) {

                        //6-2.기발급 날짜와 오늘 날짜 비교
                        strDown = strDown.substring(0, 6); //기발급 년월
                        SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMM");
                        Date todayDate = new Date();
                        String strThisMonth = sFormat.format(todayDate); //현재 년월

                        int intDownYear = Integer.parseInt(strDown.substring(0, 4));
                        int intDownMonth = Integer.parseInt(strDown.substring(4));
                        int intTodayYear = Integer.parseInt(strThisMonth.substring(0, 4));
                        int intTodayMonth = Integer.parseInt(strThisMonth.substring(4));

                        Calendar calDown = new GregorianCalendar(intDownYear, intDownMonth, 1); //기발급 날짜
                        Calendar calToday = new GregorianCalendar(intTodayYear, intTodayMonth, 1); //오늘 날짜

                        int yearDiff = calToday.get(Calendar.YEAR) - calDown.get(Calendar.YEAR); //년 차이
                        int monthDiff = (yearDiff * 12) + calToday.get(Calendar.MONTH) - calDown.get(Calendar.MONTH); //월 차이

                        //6-3.기발급된 날짜와 현재의 월 차이가 쿠폰발급기준(1개월에 1회, 3개월에 1회)에 맞지 않으면 리턴
                        if (monthDiff < Integer.parseInt(stanMonth)) {
                            couponUseYn = "Y";
                        }
                    }
                }
           }

          //쿠폰 잔여수량 있는지 확인
           int leftQnty = couponService.getCouponLeftQnty(ctgDto.getCoupnCtgCd());
           if (leftQnty == 0) {
               couponUseYn = "Y";
           }


         resultMap.put("useYn",couponUseYn);
        return resultMap;
    }

}
