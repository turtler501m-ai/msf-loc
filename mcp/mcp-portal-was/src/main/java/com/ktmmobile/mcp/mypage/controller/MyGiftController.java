package com.ktmmobile.mcp.mypage.controller;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.mypage.dto.BenefitSearchDto;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyGiftDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MyBenefitService;
import com.ktmmobile.mcp.mypage.service.MypageService;



@Controller
public class MyGiftController {

    private static Logger logger = LoggerFactory.getLogger(MyGiftController.class);

    @Autowired
    private MyBenefitService myBenefitService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;


    // 화면_My 사은품 (2022-02-14)
    @RequestMapping(value = { "/mypage/myGiftList.do", "/m/mypage/myGiftList.do" }, method = {RequestMethod.GET, RequestMethod.POST})
    public String myGiftList(HttpServletRequest request, @ModelAttribute PageInfoBean pageInfoBean, Model model, MyGiftDto myGiftDto) throws ParseException  {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null ) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();

        if(Optional.ofNullable(cntrList).isEmpty()) {
            responseSuccessDto.setSuccessMsg("회선정보 조회 중 에러가 발생했습니다. 확인후 다시 이용해주세요.");
            responseSuccessDto.setRedirectUrl(request.getHeader("Referer"));
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }else if(Optional.ofNullable(myGiftDto.getContractNum()).isPresent()){
              if( ! this.checkUserIsSame( cntrList, myGiftDto.getContractNum()) ){
                  responseSuccessDto.setSuccessMsg("로그인정보가 일치하지 않습니다.");
                  responseSuccessDto.setRedirectUrl(request.getHeader("Referer"));
                  model.addAttribute("responseSuccessDto", responseSuccessDto);
                  return "/common/successRedirect";
              }
        }

        if(!cntrList.isEmpty()) {
            for(McpUserCntrMngDto boardDto : cntrList) {
                boardDto.setCntrMobileNo(MaskingUtil.getMaskedTelNo(boardDto.getCntrMobileNoMasking()));
            }
        }

        if(cntrList.size() > 0) {
            String contractNum = null;

            if (myGiftDto.getContractNum() == null ) {
                contractNum = cntrList.get(0).getContractNum();
            } else {
                contractNum = myGiftDto.getContractNum();
            }

            myGiftDto.setContractNum(contractNum);

            if (pageInfoBean.getPageNo() == 0) {
                pageInfoBean.setPageNo(1);
            }

            // 한페이지당 보여줄 리스트 수
            pageInfoBean.setRecordCount(10);

            int totalCount = myBenefitService.getCustListCount(myGiftDto);
            pageInfoBean.setTotalCount(totalCount);



            //api 호출
            if (pageInfoBean.getTotalPageCount() < pageInfoBean.getPageNo()) {
                throw new McpCommonJsonException(BIDING_EXCEPTION);
            }

            int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
            int maxResult = pageInfoBean.getRecordCount(); // Pagesize

            List<MyGiftDto> getGiftCustList;

            getGiftCustList = myBenefitService.getGiftCustList(myGiftDto, skipResult, maxResult);

            int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + getGiftCustList.size();

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

            model.addAttribute("contractNum", contractNum);
            model.addAttribute("pageInfoBean", pageInfoBean);
            model.addAttribute("giftCustList", getGiftCustList);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("listCount", listCount);
            model.addAttribute("pageNo", pageInfoBean.getPageNo());

        }


        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        // 포맷 적용
        String formatedNow = now.format(formatter);

        model.addAttribute("formatedNow",formatedNow);
        model.addAttribute("cntrList",cntrList);

        String rtnPageNm;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnPageNm = "/mobile/mypage/myGiftList";
        } else {
            rtnPageNm = "/portal/mypage/myGiftList";
        }
        return rtnPageNm;
    }

    // 조회_사은품 신청 내역 (2022-02-14)
    @RequestMapping( value = { "/mypage/myGiftListAjax.do", "/m/mypage/myGiftListAjax.do" })
    @ResponseBody
    public HashMap<String,Object> myGiftListAjax(HttpServletRequest request, @ModelAttribute PageInfoBean pageInfoBean, Model model, MyGiftDto myGiftDto) throws ParseException  {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null ) { // 취약성 332
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        String cntrNo = myGiftDto.getContractNum();
        if(Optional.ofNullable(cntrList).isEmpty()) {
            rtnMap.put("result", "00001");
            rtnMap.put("message", "회선정보 조회 중 에러가 발생했습니다. 확인후 다시 이용해주세요.");
            rtnMap.put("location", request.getHeader("Referer"));
            return rtnMap;
        }else {
            if( ! this.checkUserIsSame( cntrList, cntrNo) ){
                rtnMap.put("result", "00002");
                rtnMap.put("message", "로그인정보가 일치하지 않습니다.");
                rtnMap.put("location", request.getHeader("Referer"));
                return rtnMap;
            }
        }

        if(!cntrList.isEmpty()) {
            for(McpUserCntrMngDto boardDto : cntrList) {
                boardDto.setCntrMobileNo(MaskingUtil.getMaskedTelNo(boardDto.getCntrMobileNoMasking()));
            }
        }

        if(cntrList.size() > 0) {
            String contractNum = null;

            if (myGiftDto.getContractNum() == null ) {
                contractNum = cntrList.get(0).getContractNum();
            } else {
                contractNum = myGiftDto.getContractNum();
            }

            myGiftDto.setContractNum(contractNum);
            // 한페이지당 보여줄 리스트 수
            pageInfoBean.setRecordCount(10);

            // 페이지 토탈 카운트
            int totalCount = myBenefitService.getCustListCount(myGiftDto);
            pageInfoBean.setTotalCount(totalCount);

            int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
            int maxResult = pageInfoBean.getRecordCount(); // Pagesize

            List<MyGiftDto> getGiftCustList;

            getGiftCustList = myBenefitService.getGiftCustList(myGiftDto, skipResult, maxResult);

            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy.MM.dd");

            for (MyGiftDto giftCustDate : getGiftCustList) {
                Date formatDate = dtFormat.parse(giftCustDate.getRegstDttm());
                // Date타입의 변수를 새롭게 지정한 포맷으로 변환
                String strNewDtFormat = newDtFormat.format(formatDate);
                giftCustDate.setRegstDttm(strNewDtFormat);

            }

            rtnMap.put("giftCustList", getGiftCustList);
            //model.addAttribute("giftCustList", request.getParameter("cntrNo"));


            int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + getGiftCustList.size();

            rtnMap.put("totalCount", totalCount);
            rtnMap.put("pageInfoBean", pageInfoBean);
            rtnMap.put("maxPage", pageInfoBean.getTotalCount());
            rtnMap.put("listCount", listCount);

            //model.addAttribute("totalgiftCustList", giftCustList.size());
        }
        return rtnMap;
    }

    // 조회_사은품 신청 내역 추가 조회 (2022-02-14)
    @RequestMapping( value = { "/mypage/myGiftMoreListAjax.do", "/m/mypage/myGiftMoreListAjax.do" })
    public String myGiftMoreListAjax(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") BenefitSearchDto searchVO)  {

        String rtnPageNm;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnPageNm = "/mobile/mypage/myGiftMoreListAjax";
        } else {
            rtnPageNm = "/portal/mypage/myGiftMoreListAjax";
        }
        return rtnPageNm;
    }

    private boolean checkUserIsSame(List<McpUserCntrMngDto> cntrList, String cntrNo) {
        boolean result = false;
        for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
            if(cntrNo.equals(mcpUserCntrMngDto.getContractNum())) {
                result = true;
                break;
            }
        }
        return result;
    }
}
