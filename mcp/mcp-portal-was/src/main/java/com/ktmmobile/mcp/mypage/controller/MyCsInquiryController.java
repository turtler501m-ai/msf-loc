package com.ktmmobile.mcp.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.common.cti.CtiService;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.cs.controller.CsFaqController;
import com.ktmmobile.mcp.cs.dto.InquiryBoardDto;
import com.ktmmobile.mcp.cs.service.CsInquirySvc;
import com.ktmmobile.mcp.mypage.dto.CallASDto;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;

@Controller
public class MyCsInquiryController {
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
    @RequestMapping(value = { "/m/mypage/myCsInquiryList.do" })
    public String myCsInquiryList(HttpServletRequest request, @ModelAttribute InquiryBoardDto inquiryBoardDto, Model model) {

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

        model.addAttribute("authSmsDto",authSmsDto);
        model.addAttribute("inquiryBoardDto",inquiryBoardDto);
        model.addAttribute("userSession",userSession);
        model.addAttribute("isHist",isHist);

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

        String returnUrl;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/myCsInquiryList";
        } else {
            returnUrl = "/portal/mypage/myCsInquiryList";
        }


        return returnUrl;
    }

    @RequestMapping(value = {"/mypage/myCsInquiryList.do"})
    public String pcCsInquiryIntHist(HttpServletRequest request, @ModelAttribute InquiryBoardDto inquiryBoardDto, Model model, @ModelAttribute PageInfoBean pageInfoBean) {

        String menuType = "QNA";
        // 세센 아이디 확인(비회원 , 회원)
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);

        if(userSession==null && authSmsDto==null) {
            return "/portal/mypage/myCsInquiryList";
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
                dto.setQnaContent(qnaContent);

                // 답변 줄바꿈 처리
                ansContent = ansContent.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
                dto.setAnsContent(ParseHtmlTagUtil.convertHtmlchars(ansContent));
            }
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

        model.addAttribute("menuType",menuType);
        model.addAttribute("pageInfoBean",pageInfoBean);
        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("authSmsDto",authSmsDto);
        model.addAttribute("userSession",userSession);
        return "/portal/mypage/myCsInquiryList";
    }

    /**
     *
     *
     */
    @RequestMapping(value = { "/mypage/myCsInquiryListAjax.do", "/m/mypage/myCsInquiryListAjax.do" })
    @ResponseBody
    public Map<String, Object> myCsInquiryListAjax(HttpServletRequest request, @ModelAttribute PageInfoBean pageInfoBean, @ModelAttribute InquiryBoardDto inquiryBoardDto, Model model) {

        String menuType = "mypage";
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);

        String userId = "";
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
            }
        }

        rtnMap.put("listCount", listCount); //일반게시판
//        rtnMap.put("endPage", pageInfoBean.getTotalPageCount());
        rtnMap.put("pageNo",pageInfoBean.getPageNo());
        rtnMap.put("totalCount", totalCount);
        rtnMap.put("inquiryList", inquiryList);

        return rtnMap;
    }



    /**
     * 통화품질 불량접수
     */
    @RequestMapping(value= {"/m/mypage/callASView.do","/mypage/callASView.do"})
    public String callASView(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String pageUrl = "";
        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/mypage/as/callASView";
            checkOverlapDto.setRedirectUrl("/m/mypage/callASView.do");
        } else {
            pageUrl = "/portal/mypage/as/callASView";
            checkOverlapDto.setRedirectUrl("/mypage/callASView.do");
        }

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String ncn = searchVO.getNcn();
        String ctn = searchVO.getCtn();

     // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            model.addAttribute("ctn", StringUtil.getMobileFullNum(StringUtil.NVL(ctn,"")));
            model.addAttribute("userName", userSession.getName());

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름,휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }else {
            model.addAttribute("ctn", StringMakerUtil.getPhoneNum(ctn));
            model.addAttribute("userName", StringMakerUtil.getName(userSession.getName()));
        }

        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("ncn", ncn);

        return pageUrl;
    }


    @RequestMapping(value= {"/mypage/callSaveAjax.do"})
    @ResponseBody
    public Map<String, Object> callSaveAjax(HttpServletRequest request ,@ModelAttribute CallASDto callASDto, Model model) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String returnCode = "00";
        String message = "";
        String ncn = callASDto.getNcn();
        String custId = "";
        String ctn = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String userId = userSession.getUserId();
        String userNm = userSession.getName();

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userId);
        if(cntrList !=null && !cntrList.isEmpty()) {
            for(McpUserCntrMngDto dto : cntrList) {
                String svcCntrNo = StringUtil.NVL(dto.getSvcCntrNo(),"");
                if(svcCntrNo.equals(ncn)) {
                    custId = dto.getCustId();
                    ctn = dto.getCntrMobileNo();
                    break;
                }
            }
        } else {
            rtnMap.put("returnCode", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        if("".equals(custId) || "".equals(ctn)) {
            rtnMap.put("returnCode", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        try {
            callASDto.setUserId(userId);
            callASDto.setContractNum(ncn);
            callASDto.setCstmrName(userNm);
            callASDto.setMobileNo(ctn);
            logger.info("callASDto==>"+ObjectUtils.convertObjectToString(callASDto));
            int cnt =  csInquirySvc.callASListCnt(callASDto);
            if(cnt > 0) {
                returnCode = "88";
                message = "이미 접수 중인 내역이 존재합니다.\r\n 신청하신 내용 확인 후 고객센터에서 빠르게 연락드리도록 하겠습니다.";
            } else {
                int result = csInquirySvc.callASInsert(callASDto);
                if(result < 1) {
                    returnCode = "99";
                    message = "등록에 실패했습니다.";
                }
            }
        }catch(DataAccessException e) {
            returnCode = "99";
            message = "등록에 실패했습니다.";
            logger.info("error:"+e.getMessage());
        } catch(Exception e) {
            returnCode = "99";
            message = "등록에 실패했습니다.";
            logger.info("error:"+e.getMessage());
        }

        rtnMap.put("returnCode",returnCode);
        rtnMap.put("message",message);

        return rtnMap;
    }



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
