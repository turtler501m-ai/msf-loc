package com.ktmmobile.mcp.cs.controller;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.cs.dto.BookingDateDto;
import com.ktmmobile.mcp.cs.dto.BookingUserDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
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

import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.common.cti.CtiService;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.xss.RequestWrapper;
import com.ktmmobile.mcp.cs.dto.InquiryBoardDto;
import com.ktmmobile.mcp.cs.service.CsInquirySvc;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class CsInquiryController {

    private final Logger logger = LoggerFactory.getLogger(CsFaqController.class);

    @Autowired
    CsInquirySvc csInquirySvc;

    @Autowired
    FormDtlSvc formDtlSvc;

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    MypageUserService mypageUserService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private CtiService ctiService;

    @Autowired
    private MaskingSvc maskingSvc;

    /**
     * @Description : 1대1문의 하기 진입페이지
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 25
     */
    @RequestMapping(value = { "/cs/csInquiryInt.do", "/m/cs/csInquiryInt.do" })
    public String csInquiryInt(HttpServletRequest request, @ModelAttribute PageInfoBean pageInfoBean,	@ModelAttribute InquiryBoardDto inquiryBoardDto, Model model) {
        String rtnPageNm;

        String menuType = "QNA";

        // 세센 아이디 확인(비회원 , 회원)
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);

        List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();
        McpUserCntrMngDto mcpUserCntrMngDto = new McpUserCntrMngDto();
        String userDivision = "";
        String qnaCtg = "";
        String cntrMobileNo = "";
        String maskCntrMobileNo = "";
        // 로그인
        if (userSession != null) { // 정회원
            userDivision = userSession.getUserDivision();

            if("01".equals(userDivision)) {
                cntrList = mypageService.selectCntrList(userSession.getUserId());
                qnaCtg = "UI0010";
            } else { // 준회원
                cntrMobileNo = StringUtil.NVL(userSession.getMobileNo(),"");
                maskCntrMobileNo = MaskingUtil.getMaskedTelNo(cntrMobileNo);
                mcpUserCntrMngDto.setCntrMobileNo(maskCntrMobileNo);
                cntrList.add(mcpUserCntrMngDto);
                qnaCtg = "UI0020";
            }
        } else {

            if( authSmsDto !=null ){ // 인증받고 진입
                String phoneNum = authSmsDto.getPhoneNum();
                mcpUserCntrMngDto.setCntrMobileNo(phoneNum);
                cntrList.add(mcpUserCntrMngDto);
                userDivision = "02";
                qnaCtg = "UI0020";
            }
        }

        if(cntrList!=null && !cntrList.isEmpty()) {
            for(McpUserCntrMngDto dto : cntrList) {
                String maskCtn = StringUtil.NVL(dto.getCntrMobileNoMasking(),"");
                dto.setMkMobileNo(maskCtn);
            }
        }

        List<NmcpCdDtlDto> inquiryCategoryList = new ArrayList<NmcpCdDtlDto>();

        // 문의 유형 가져오기
        inquiryCategoryList = NmcpServiceUtils.getCodeList(qnaCtg); // 카테고리 유형

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnPageNm = "/mobile/cs/inquiry/inquiryInt";
        } else {
            rtnPageNm = "/portal/cs/inquiry/inquiryInt";
        }

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        }

        model.addAttribute("menuType",menuType);
        model.addAttribute("authSmsDto",authSmsDto);
        model.addAttribute("userSession",userSession);
        model.addAttribute("cntrList",cntrList);
        model.addAttribute("userDivision",userDivision);
        model.addAttribute("inquiryCategoryList",inquiryCategoryList);
        model.addAttribute("cntrMobileNo",cntrMobileNo);


        return rtnPageNm;
    }

    /**
     * @Description : 1대1문의 하기 진입페이지
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 25
     */
    @RequestMapping(value = {"/m/cs/csInquiryIntHist.do"})
    public String csInquiryIntHist(HttpServletRequest request, @ModelAttribute InquiryBoardDto inquiryBoardDto, Model model) {

        String menuType = "QNA";
        // 세센 아이디 확인(비회원 , 회원)
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);

        String isHist = "N";
        if(userSession !=null || authSmsDto!=null) {
            isHist = "Y";
        }

        int pageNo = inquiryBoardDto.getPageNo();
        if(pageNo==0) {
            inquiryBoardDto.setPageNo(1);
        }

        String userId = "000000000000000000000000000";
        // 로그인
        if (userSession != null) { // 정회원
            userId = userSession.getUserId();
        } else {
            if( authSmsDto !=null ){ // 인증받고 진입
                userId = authSmsDto.getPhoneNum();
            }
        }
        inquiryBoardDto.setQnaWriterID(userId);

        List<InquiryBoardDto> inquiryList = csInquirySvc.inquiryBoardList(inquiryBoardDto,0,10);
        if(inquiryList !=null && !inquiryList.isEmpty()) {
             // 마스킹해제
            if(SessionUtils.getMaskingSession() > 0 ) {
                model.addAttribute("maskingSession", "Y");
                MaskingDto maskingDto = new MaskingDto();

                long maskingRelSeq = SessionUtils.getMaskingSession();
                maskingDto.setMaskingReleaseSeq(maskingRelSeq);
                maskingDto.setUnmaskingInfo("휴대폰번호");
                maskingDto.setAccessIp(ipstatisticService.getClientIp());
                maskingDto.setAccessUrl(request.getRequestURI());
                maskingDto.setUserId(userSession.getUserId());
                maskingDto.setCretId(userSession.getUserId());
                maskingDto.setAmdId(userSession.getUserId());
                maskingSvc.insertMaskingReleaseHist(maskingDto);
            }
        }

        model.addAttribute("authSmsDto",authSmsDto);
        model.addAttribute("inquiryBoardDto",inquiryBoardDto);
        model.addAttribute("userSession",userSession);
        model.addAttribute("isHist",isHist);
        model.addAttribute("menuType",menuType);
        return "/mobile/cs/inquiry/inquiryIntHist";
    }

    /**
     * @Description : 1대1문의 하기 subcategory 조회
     * @param model
     * @return
     * @Author : ant08
     * @Create Date : 2021. 11 .24
     */

    @RequestMapping(value = {"/m/cs/selectQnaListAjax.do"})
    @ResponseBody
    public Map<String, Object> selectQnaListAjax(HttpServletRequest request, @ModelAttribute PageInfoBean pageInfoBean, @ModelAttribute InquiryBoardDto inquiryBoardDto, Model model) {

        String menuType = "QNA";
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);

        String userId = "000000000000000000000000000";
        // 로그인
        if (userSession != null) { // 정회원
            userId = userSession.getUserId();
        } else {
            if( authSmsDto !=null ){ // 인증받고 진입
                userId = authSmsDto.getPhoneNum();
            }
        }
        inquiryBoardDto.setQnaWriterID(userId);

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*임시 한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(10);

        /*페이지 카운트*/
        int totalCount = csInquirySvc.getTotalCount(inquiryBoardDto);
        pageInfoBean.setTotalCount(totalCount);

        /*RowBound 필수값 세팅*/
        int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult   = pageInfoBean.getRecordCount(); // Pagesize

        /*1:1문의 게시판 리스트*/
        List<InquiryBoardDto> inquiryList = csInquirySvc.inquiryBoardList(inquiryBoardDto,skipResult,maxResult);
        int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + inquiryList.size();
        if(inquiryList !=null && !inquiryList.isEmpty()) {
            for(InquiryBoardDto dto : inquiryList) {
                String mobileNo = "";
                // 마스킹해제
                if(SessionUtils.getMaskingSession() > 0 ) {
                    mobileNo = StringUtil.getMobileFullNum(StringUtil.NVL(dto.getMobileNo(),""));
                }else {
                    mobileNo = MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(StringUtil.NVL(dto.getMobileNo(),"")));
                }
                dto.setMobileNo(mobileNo);
                String qnaCtg = dto.getQnaCtg();
                String qnaSubCtgCd = dto.getQnaSubCtgCd();
                String qnaSubCtgNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(qnaCtg,qnaSubCtgCd),"");
                dto.setQnaSubCtgNm(qnaSubCtgNm);
                String qnaContent = StringUtil.NVL(dto.getQnaContent(),"");
                String ansContent = StringUtil.NVL(dto.getAnsContent(),"");
                //dto.setQnaContent(ParseHtmlTagUtil.convertHtmlchars(qnaContent));
                dto.setQnaContent(qnaContent);

                // 답변 줄바꿈 처리
                ansContent = ansContent.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                dto.setAnsContent(ParseHtmlTagUtil.convertHtmlchars(ansContent));
            }
        }

        rtnMap.put("listCount", listCount); //일반게시판
        rtnMap.put("endPage", pageInfoBean.getTotalPageCount());
        rtnMap.put("pageNo",pageInfoBean.getPageNo());
        rtnMap.put("totalCount", totalCount);
        rtnMap.put("inquiryList", inquiryList);

        return rtnMap;
    }

    /**
     * @Description : 1대1문의 등록
     * @param model
     * @return
     * @Author : ant08
     * @Create Date : 2021. 11 .24
     */
    @RequestMapping(value="/cs/insertInquiryWriteFormAjax.do")
    @ResponseBody
    public Map<String, Object> insertCsInquiryWriteForm(HttpServletRequest request,@ModelAttribute InquiryBoardDto inquiryBoardDto, Model model, BindingResult bind) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String menuType = "QNA";
        // 세센 아이디 확인(비회원 , 회원)
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);

        List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();
        McpUserCntrMngDto mcpUserCntrMngDto = new McpUserCntrMngDto();
        String userDivision = "03";
        String userId = "";
        String userNm ="";

        // DB INSERT 시 사용자명 존재여부로 회원과 비회원을 구분하고 있음.
        // ARS연동시에는 회원과 비회원 구분없이 이름이 필요하므로 변수 하나 생성해서 사용
        String userNmAll = "";

        // 로그인
        if (userSession != null) {
            userDivision = userSession.getUserDivision();
            userId = userSession.getUserId();
            userNm = userSession.getName();
            userNmAll = userSession.getName();

            // 정회원일시 계약번호로 전화번호를 가져온다.
            if ("01".equals(userDivision)) {
                McpUserCntrMngDto cntrMngDto = mypageService.getCntrInfoByContNum(inquiryBoardDto.getSvcCntrNo());
                if (cntrMngDto != null) {
                    inquiryBoardDto.setMobileNo(cntrMngDto.getCntrMobileNo());
                } else {
                    rtnMap.put("inquiryInt", -1);
                    return rtnMap;
                }
            } else { // 준회원
                // 최초 셋팅된 전화번호(010-****-1234)이면 세션에 있는 전화번호로 셋팅
                // 전화번호 입력시(01012341234)에는 입력한 전화번호 그대로 진행
                if (inquiryBoardDto.getMobileNo().equals(MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(userSession.getMobileNo())))) {
                    inquiryBoardDto.setMobileNo(userSession.getMobileNo());
                }
            }
        } else {
            if (authSmsDto == null) {
                rtnMap.put("inquiryInt", -1);
                return rtnMap;
            } else { // 비회원 인증받고 진입
//                String reqMobileNo= inquiryBoardDto.getMobileNo();
//                if (StringUtils.isEmpty(reqMobileNo) || !reqMobileNo.equals(authSmsDto.getPhoneNum())) {
//                    rtnMap.put("inquiryInt", -2);
//                    return rtnMap;
//                }
                // 인증했던 전화번호를 가져온다.
                inquiryBoardDto.setMobileNo(authSmsDto.getPhoneNum());
                userDivision = "02";
                userId = authSmsDto.getPhoneNum();
                userNmAll = authSmsDto.getAuthNum();  // authNum 필드에 이름이 저장되어있음(이유는 모름)
            }
        }
        inquiryBoardDto.setQnaWriterID(userId);
        inquiryBoardDto.setQnaNM(userNm);

        String qnaSubject = StringUtil.NVL(inquiryBoardDto.getQnaSubject(),"");
        qnaSubject = StringEscapeUtils.unescapeXml(qnaSubject);
        qnaSubject = RequestWrapper.cleanXSS(qnaSubject);

        inquiryBoardDto.setQnaSubject(qnaSubject);


        String qnaContent = StringUtil.NVL(inquiryBoardDto.getQnaContent(),"");
        qnaContent = StringEscapeUtils.unescapeXml(qnaContent);
        qnaContent = RequestWrapper.cleanXSS(qnaContent);

        inquiryBoardDto.setQnaContent(qnaContent);

        String smsSendYN = inquiryBoardDto.getSmsSendYN();
        inquiryBoardDto.setQnaSmsSendYn(smsSendYN);

        // 선택된 회선 찾기 끝

        inquiryBoardDto.setUserNmAll(userNmAll);
        inquiryBoardDto.setUserDivision(userDivision);

        /*1:1문의 insert하기*/
        int inquiryInt = 0;
        try {
            csInquirySvc.inquiryInsert(inquiryBoardDto);
            inquiryInt = 1;
        } catch (McpCommonJsonException e) {
            inquiryInt = 0;
        } catch (Exception e) {
            inquiryInt = 0;
        }

        rtnMap.put("inquiryInt", inquiryInt);
        return rtnMap;
    }

    /**
     * @Description : 1대1문의 하기 진입페이지
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 25
     */
    @RequestMapping(value = {"/cs/csInquiryIntHist.do"})
    public String pcCsInquiryIntHist(HttpServletRequest request, @ModelAttribute InquiryBoardDto inquiryBoardDto, Model model, @ModelAttribute PageInfoBean pageInfoBean) {

        String menuType = "QNA";
        // 세센 아이디 확인(비회원 , 회원)
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);

        if(userSession==null && authSmsDto==null) {
            model.addAttribute("menuType",menuType);
            return "/portal/cs/inquiry/inquiryIntHist";
        }

        String userId = "000000000000000000000000000";
        // 로그인
        if (userSession != null) { // 정회원
            userId = userSession.getUserId();
        } else {
            if( authSmsDto !=null ){ // 인증받고 진입
                userId = authSmsDto.getPhoneNum();
            }
        }
        inquiryBoardDto.setQnaWriterID(userId);

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*임시 한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(10);

        /*페이지 카운트*/
        int totalCount = csInquirySvc.getTotalCount(inquiryBoardDto);
        pageInfoBean.setTotalCount(totalCount);

        /*RowBound 필수값 세팅*/
        int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult   = pageInfoBean.getRecordCount(); // Pagesize

        /*1:1문의 게시판 리스트*/
        List<InquiryBoardDto> inquiryList = csInquirySvc.inquiryBoardList(inquiryBoardDto,skipResult,maxResult);
        if(inquiryList !=null && !inquiryList.isEmpty()) {
            for(InquiryBoardDto dto : inquiryList) {
                 String mobileNo = "";
                 // 마스킹해제
                 if(SessionUtils.getMaskingSession() > 0 ) {
                     mobileNo = StringUtil.getMobileFullNum(StringUtil.NVL(dto.getMobileNo(),""));
                 }else {
                     mobileNo = MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(StringUtil.NVL(dto.getMobileNo(),"")));
                 }
                dto.setMobileNo(mobileNo);
                String qnaCtg = dto.getQnaCtg();
                String qnaSubCtgCd = dto.getQnaSubCtgCd();
                String qnaSubCtgNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(qnaCtg,qnaSubCtgCd),"");
                dto.setQnaSubCtgNm(qnaSubCtgNm);
                String qnaContent = StringUtil.NVL(dto.getQnaContent(),"");
                String ansContent = StringUtil.NVL(dto.getAnsContent(),"");
                //dto.setQnaContent(ParseHtmlTagUtil.convertHtmlchars(qnaContent));
                dto.setQnaContent(qnaContent);

                // 답변 줄바꿈 처리
                ansContent = ansContent.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                dto.setAnsContent(ParseHtmlTagUtil.convertHtmlchars(ansContent));
            }

            if(SessionUtils.getMaskingSession() > 0 ) {
                MaskingDto maskingDto = new MaskingDto();
                model.addAttribute("maskingSession", "Y");
                long maskingRelSeq = SessionUtils.getMaskingSession();
                maskingDto.setMaskingReleaseSeq(maskingRelSeq);
                maskingDto.setUnmaskingInfo("휴대폰번호");
                maskingDto.setAccessIp(ipstatisticService.getClientIp());
                maskingDto.setAccessUrl(request.getRequestURI());
                maskingDto.setUserId(userSession.getUserId());
                maskingDto.setCretId(userSession.getUserId());
                maskingDto.setAmdId(userSession.getUserId());
                maskingSvc.insertMaskingReleaseHist(maskingDto);
            }

        }
        model.addAttribute("menuType",menuType);
        model.addAttribute("pageInfoBean",pageInfoBean);
        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("authSmsDto",authSmsDto);
        model.addAttribute("userSession",userSession);
        model.addAttribute("totalCount",totalCount);
        return "/portal/cs/inquiry/inquiryIntHist";
    }


    /**
     * @Description : 상담 예약 페이지 진입
     * @param request
     * @param bookingUserDto
     * @param model
     * @param searchVO
     * @return String
     */
    @RequestMapping(value = { "/cs/csInquiryBooking.do", "/m/cs/csInquiryBooking.do" })
    public String csInquiryBooking(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @ModelAttribute BookingUserDto bookingUserDto) {

        // 1. 리턴페이지 설정
        String jspPageName = "/portal/cs/inquiryBooking/inquiryBooking";
        String thisPageName ="/cs/csInquiryBooking.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/mobile/cs/inquiryBooking/inquiryBooking";
            thisPageName ="/m/cs/csInquiryBooking.do";
        }

        // 2. 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(thisPageName);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        // 3. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";

        // 4. 정회원 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        // 5. 후불고객 체크
        List<McpUserCntrMngDto> poList = mypageService.selectPoList(cntrList);
        if (poList == null || poList.isEmpty()) { // 정회원이면서 소유한 회선이 모두 선불인 경우
            if("Y".equals(NmcpServiceUtils.isMobile())){
                throw new McpCommonException("선불요금제는 이용할 수 없습니다", "/m/main.do");
            }else{
                throw new McpCommonException("선불요금제는 이용할 수 없습니다", "/main.do");
            }
        }

        for(McpUserCntrMngDto dto : poList) { // 휴대폰 번호 마스킹처리
            String maskCtn = StringUtil.NVL(dto.getCntrMobileNoMasking(),"");
            dto.setMkMobileNo(maskCtn);
        }

        // 6. 문의 유형 소분류 조회 (api)
        // 공통코드 그룹명 > VOC0027
        List<CmnGrpCdMst> inquiryCategoryList = null;
        CmnGrpCdMst cmnGrpCdMst = new CmnGrpCdMst();
        cmnGrpCdMst.setGrpId("VOC0027");

        inquiryCategoryList = csInquirySvc.selectBookingCd(cmnGrpCdMst); // 카테고리 유형

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }

        model.addAttribute("poList",poList);
        model.addAttribute("inquiryCategoryList",inquiryCategoryList);

        return jspPageName;
    }

    /**
     * @Description : 예약상담제 공통코드 조회
     * @param cmnGrpCdMst
     * @return Map<String, Object>
     */
    @RequestMapping("/cs/getBookingCdAjax.do")
    @ResponseBody
    public Map<String, Object> getBookingCdAjax(@ModelAttribute CmnGrpCdMst cmnGrpCdMst){

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        List<CmnGrpCdMst> rtnList= csInquirySvc.selectBookingCd(cmnGrpCdMst);
        rtnMap.put("rtnList", rtnList);
        return rtnMap;
    }

    /**
     * @Description : 특정 예약 일시에 예약가능 인원 수 존재여부 확인
     * @param bookingDateDto
     * @return Map<String,String>
     */
    @RequestMapping(value={"/cs/checkBookingAvailableByDt.do", "/m/cs/checkBookingAvailableByDt.do"})
    @ResponseBody
    public Map<String,String> checkBookingAvailableByDt(@ModelAttribute("bookingDateDto") BookingDateDto bookingDateDto) {

        Map<String,String> rtnMap= new HashMap<>();
        rtnMap.put("remainYn", "F"); // default 세팅

        // 예약날짜가 익월을 넘거갈 경우 N리턴
        if(StringUtil.isNotNull(bookingDateDto.getCsResDt())){

            boolean possibleDate= checkResPossibleDate(bookingDateDto.getCsResDt()); // 사용자가 선택한 예약일시 유효성 확인
            if(!possibleDate){
                rtnMap.put("remainYn", "F");
            }else{
                String remainYn= csInquirySvc.checkBookingAvailableByDt(bookingDateDto);
                rtnMap.put("remainYn", remainYn);
            }
        }

        return rtnMap;
    }

    /**
     * @Description : 예약상담 시간 선택 팝업
     * @param request
     * @param model
     * @param bookingDateDto
     * @return Map<String,String>
     */
    @RequestMapping(value={"/bookingTimePop.do", "/m/bookingTimePop.do"})
    public String bookingTime(HttpServletRequest request, Model model, @ModelAttribute("bookingDateDto") BookingDateDto bookingDateDto) {

        //모바일 여부 확인
        String mobileYN = "N";
        if ("Y".equals(NmcpServiceUtils.isMobile())) mobileYN = "Y";

        List<BookingDateDto> timeList = null;
        if(StringUtil.isNotNull(bookingDateDto.getCsResDt())){

            boolean possibleDate= checkResPossibleDate(bookingDateDto.getCsResDt()); // 사용자가 선택한 예약일시 유효성 확인
            if(possibleDate){
                timeList= csInquirySvc.getBookingTimeList(bookingDateDto); // 특정 예약 일시에 해당하는 예약시간대 조회
            }
        }

        model.addAttribute("timeList", timeList);
        model.addAttribute("mobileYN", mobileYN);

        return "/portal/cs/inquiryBooking/bookingTimePop";
    }

    /**
     * @Description : 예약상담 접수
     * @param request
     * @param model
     * @param bookingDateDto
     * @return Map<String,String>
     */
    @RequestMapping(value={"/cs/insertInquiryBooking.do", "/m/cs/insertInquiryBooking.do"})
    @ResponseBody
    public Map<String, Object> insertInquiryBooking(HttpServletRequest request, @ModelAttribute BookingUserDto bookingUserDto, @ModelAttribute BookingDateDto bookingDateDto, Model model, BindingResult bind, @ModelAttribute("searchVO") MyPageSearchDto searchVO) throws ParseException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 1. 중복 요청 체크 (중복 접수 방지)
        String thisPageName ="/cs/insertInquiryBooking.do";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            thisPageName ="/m/cs/insertInquiryBooking.do";
        }

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(thisPageName);
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)){
            // 처리중인 업무가 있습니다. 잠시 후 다시 시도해 주세요.
            throw new McpCommonJsonException("0001",TIME_OVERLAP_EXCEPTION);
        }

        // 2. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtil.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("0002", NO_FRONT_SESSION_EXCEPTION);
        }

        // 3. 정회원 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("0003", NOT_FULL_MEMBER_EXCEPTION);
        }

        // 4. 계약번호가 로그인한 고객과 일치하는지 체크 / 후불체크
        boolean customerCntrChek = false;
        String usrNm= null;     // 사용자명
        String unSvcNo = null;  // 사용자 전화번호
        String contractNum = StringUtil.NVL(bookingUserDto.getContractNum(), "");

        List<McpUserCntrMngDto> poList = mypageService.selectPoList(cntrList); // 후불 리스트
        if (Optional.ofNullable(poList).isPresent()) {
            for (McpUserCntrMngDto dto : poList) {
                if (contractNum.equals(dto.getContractNum())) {
                    customerCntrChek = true;
                    usrNm = dto.getUserName();
                    unSvcNo = dto.getUnSvcNo();
                    break;
                }
            }
        }

        if (!customerCntrChek) {
            throw new McpCommonJsonException("0004", "로그인 정보가 다릅니다.");
        }

        // 5. 데이터 유효성 검사
        String contractNum1 = bookingUserDto.getContractNum();  // 계약번호
        String csResDt1 = bookingUserDto.getCsResDt();  		// 예약일시
        String csResTm1 = bookingUserDto.getCsResTm();  		// 예약시간대
        String vocDtl1 = bookingUserDto.getVocDtl();  			// 유형상세코드
        String vocThi1 = bookingUserDto.getVocThi();  			// 유형소코드
        String csSubject1 = bookingUserDto.getCsSubject(); 		// 문의제목
        String csContent1 = bookingUserDto.getCsContent(); 		// 문의내용

        if ("".equals(StringUtil.NVL(contractNum1, "")) ||
                "".equals(StringUtil.NVL(csResDt1, "")) ||
                "".equals(StringUtil.NVL(csResTm1, "")) ||
                "".equals(StringUtil.NVL(vocDtl1, "")) ||
                "".equals(StringUtil.NVL(vocThi1, "")) ||
                "".equals(StringUtil.NVL(csSubject1, "")) ||
                "".equals(StringUtil.NVL(csContent1, ""))) {
            throw new McpCommonJsonException("0005", "필수 입력값이 없습니다.");
        }

        // 6. 예약상담 등록 가능여부 체크 (예약일시 + 시간대가 현재보다 이후만 가능 / 예약일시는 계약번호당 하나)
        boolean possibleDate= checkResPossibleDate(bookingUserDto.getCsResDt()); // 사용자가 선택한 예약일시 유효성 확인
        if(!possibleDate){
            throw new McpCommonJsonException("0006", "당일 포함 이전 날짜는 예약이 불가하며,</br>익월까지만 예약 가능합니다.");
        }

        Map<String, Object> result = csInquirySvc.overNum(bookingUserDto); // 예약가능여부 체크

        if(result == null || result.size() == 0){
            throw new McpCommonJsonException("0008", "올바른 정보를 입력해주세요.");
        }

        String possible = (String) result.get("POSSIBLE");
        int cnt = 0;
        try{
            cnt= (int) result.get("CNT");
        }catch(NumberFormatException e){
            cnt= 1; // 예약불가처리
            logger.error("CsInquiryController.insertInquiryBooking NumberFormatException occured");
        }

        if(cnt >= 1) { // 예약 건수 초과
            throw new McpCommonJsonException("0009", "회선 기준 예약일자 당 한 건만 예약가능합니다");
        }else if("N".equals(possible)){ // 이전시간대
            throw new McpCommonJsonException("0010", "당일 포함 이전 날짜는 예약 불가합니다.");
        }

        // 7. 예약상담 INSERT 데이터 세팅
        // 7-1. 로그인 아이디
        bookingUserDto.setUsrId(userSession.getUserId());

        // 7-2. 문의제목
        String csSubject = StringEscapeUtils.unescapeXml(csSubject1);
        csSubject = RequestWrapper.cleanXSS(csSubject);
        bookingUserDto.setCsSubject(csSubject);

        // 7-3. 문의내용
        String csContent = StringEscapeUtils.unescapeXml(csContent1);
        csContent = RequestWrapper.cleanXSS(csContent);
        bookingUserDto.setCsContent(csContent);

        // 7-4. VOC(대)
        bookingUserDto.setVocFir("RES");

        // 7-5. VOC(중)
        if ("APP".equals(NmcpServiceUtils.getDeviceType())) {
            bookingUserDto.setVocSec("APP");
        } else {
            bookingUserDto.setVocSec("WEB");
        }

        // 8. 현재를 기준으로 예약가능인원이 남아있는지 체크
        List<BookingDateDto> timeList = csInquirySvc.getBookingTimeList(bookingDateDto);
        String remainYn= (timeList== null || timeList.size() == 0) ? "N" : timeList.get(0).getRemainYn();

        if (!"Y".equals(remainYn)) {
            throw new McpCommonJsonException("0011", "선택하신 시간대의 예약이 모두 완료되었습니다.다른 날짜로 선택 부탁드립니다.");
        }

        String userDivision= StringUtil.NVL(userSession.getUserDivision(), "01");
        bookingUserDto.setUsrNm(usrNm); 						   // 사용자명
        bookingUserDto.setUnSvcNo(unSvcNo);  				   // 전화번호
        bookingUserDto.setUserDivision(userDivision);  // 정회원구분

        // 9. 상담 예약 등록
        try {
            csInquirySvc.insertBooking(bookingUserDto);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "");
        } catch(McpCommonJsonException e) {
            throw new McpCommonJsonException("0012", "등록에 실패하였습니다.\n잠시 후 이용바랍니다.");
        } catch(Exception e){
            throw new McpCommonJsonException("0012", "등록에 실패하였습니다.\n잠시 후 이용바랍니다.");
        }

        return rtnMap;
    }


    //문의 예약 결과보기
    @RequestMapping(value = {"/cs/csInquiryBookingHist.do", "/m/cs/csInquiryBookingHist.do"})
    public String pcCsInquiryBookingHist(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @ModelAttribute BookingUserDto bookingUserDto, Model model, @ModelAttribute PageInfoBean pageInfoBean) {

        String jspPageName = "/portal/cs/inquiryBooking/inquiryBookingHist";  // jsp
        String thisPageName = "/cs/csInquiryBookingHist.do";  // url
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            jspPageName = "/mobile/cs/inquiryBooking/inquiryBookingHist";
            thisPageName = "/m/cs/csInquiryBookingHist.do";
        }

        // 1. 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(thisPageName);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        // 2. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";

        // 3. 정회원 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect"; //정회원이 아니면 여기로 리턴
        }

        // 3. 후불고객 체크
        List<McpUserCntrMngDto> poList = mypageService.selectPoList(cntrList);
        if (poList == null || poList.isEmpty()) { // 정회원이면서 소유한 회선이 모두 선불인 경우
            if("Y".equals(NmcpServiceUtils.isMobile())){
                throw new McpCommonException("선불요금제는 이용할 수 없습니다", "/m/main.do");
            }else{
                throw new McpCommonException("선불요금제는 이용할 수 없습니다", "/main.do");
            }
        }

        // 4. 예약현황 조회 파라미터 세팅
        bookingUserDto.setUsrId(userSession.getUserId());

        // 총 개수
        int totalCount = csInquirySvc.bookingTotalCount(bookingUserDto);

        if(totalCount > 0) {
            if(SessionUtils.getMaskingSession() > 0 ) {
                model.addAttribute("maskingSession", "Y");
                MaskingDto maskingDto = new MaskingDto();

                long maskingRelSeq = SessionUtils.getMaskingSession();
                maskingDto.setMaskingReleaseSeq(maskingRelSeq);
                maskingDto.setUnmaskingInfo("휴대폰번호");
                maskingDto.setAccessIp(ipstatisticService.getClientIp());
                maskingDto.setAccessUrl(request.getRequestURI());
                maskingDto.setUserId(userSession.getUserId());
                maskingDto.setCretId(userSession.getUserId());
                maskingDto.setAmdId(userSession.getUserId());
                maskingSvc.insertMaskingReleaseHist(maskingDto);
            }
        }

        pageInfoBean.setPageSize(10);
        pageInfoBean.setTotalCount(totalCount);

        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("userSession", userSession);
        model.addAttribute("totalCount", totalCount);




        return jspPageName;
    }

    @RequestMapping(value = {"/m/cs/bookingDataAjax.do", "/cs/bookingDataAjax.do"})
    @ResponseBody
    public Map<String,Object> bookingDataAjax(@ModelAttribute BookingUserDto bookingUserDto, PageInfoBean pageInfoBean, Model model, HttpServletRequest request) {

        HashMap<String,Object> map = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        // 로그인 여부 (userId 필수)
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0002", NO_FRONT_SESSION_EXCEPTION);
        }

        // 리스트 조회 파라미터 세팅
        bookingUserDto.setUsrId(userSession.getUserId());

        /*현재 페이지 번호 초기화*/
        if (pageInfoBean.getPageNo() <= 0) {
            pageInfoBean.setPageNo(1);
        }

        /*임시 한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(10);

        /*페이지 카운트*/
        int totalCount = csInquirySvc.bookingTotalCount(bookingUserDto);
        pageInfoBean.setTotalCount(totalCount);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        bookingUserDto.setSkipResult(skipResult);
        bookingUserDto.setMaxResult(maxResult);

        /* 문의 예약 상담 게시판 리스트*/
        List<BookingUserDto> bookingList = csInquirySvc.bookingList(bookingUserDto);

        int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + bookingList.size();
        if (bookingList != null && !bookingList.isEmpty()) {
            for (BookingUserDto dto : bookingList) {
                if(SessionUtils.getMaskingSession() > 0 ) {
                    String unSvcNo = StringUtil.getMobileFullNum(StringUtil.NVL(dto.getUnSvcNo(), ""));
                    dto.setUnSvcNo(unSvcNo);
                }else {
                    //번호 가리기
                    String unSvcNo = MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(StringUtil.NVL(dto.getUnSvcNo(), "")));
                    dto.setUnSvcNo(unSvcNo);
                }
            }
        }

        map.put("RESULT_CODE", AJAX_SUCCESS);
        map.put("listCount", listCount);
        map.put("pageInfoBean", pageInfoBean);
        map.put("totalCount", totalCount);
        map.put("bookingList", bookingList);
        return map;
    }

    /**
     * @Description: 예약상담 접수가능 마지막일자 체크
     * @param csResDt
     * @return boolean
     */
    private boolean checkResPossibleDate(String csResDt){

        boolean possibleFlag= true;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
            Date currentTime = new Date();

            // 오늘날짜
            String toDay = formatter.format(currentTime);

            //오늘을 기준으로 다음달 마지막일 출력
            String nxtDate = DateTimeUtil.addMonths(toDay, +1);
            String ntxEndDate = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(nxtDate), "yyyyMMdd", "yyyyMMdd");

            Date todayDt= formatter.parse(toDay);      // 오늘날짜
            Date bookingDt = formatter.parse(csResDt); // 사용자가 지정한 예약일시
            Date maxBookingDt = formatter.parse(ntxEndDate); // 예약 마감일 (다음달 마지막일)

            if(bookingDt.before(todayDt) || bookingDt.equals(todayDt)){ // 오늘 포함한 이전 날짜 예약 불가
                possibleFlag= false;
            }else if(bookingDt.after(maxBookingDt)){
                possibleFlag= false;
            }
        }catch(ParseException e) {
            logger.error("올바르지 않은 날짜 포맷");
            possibleFlag= false;
        }

        return possibleFlag;
    }

    //정회원 메세지 출력
    private ResponseSuccessDto getMessageBox(){
        ResponseSuccessDto mbox = new ResponseSuccessDto();
        mbox.setRedirectUrl("/mypage/updateForm.do");
        if("Y".equals(NmcpServiceUtils.isMobile())){
            mbox.setRedirectUrl("/m/mypage/updateForm.do");
        }
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
        return mbox;
    }








}

