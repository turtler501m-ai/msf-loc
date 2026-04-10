package com.ktmmobile.mcp.retention.controller;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.retention.dto.RetentionDto;
import com.ktmmobile.mcp.retention.service.RetentionService;


/**
 * 재약정 신청 Controller
 * @author key
 * @Date 2021.12.30
 */
@Controller
public class RetentionController {
    public static final String COMM_AUTH_SMS_INFO = "COMM_AUTH_SMS_INFO";
    private static final Logger logger = LoggerFactory.getLogger(RetentionController.class);


    @Autowired
    FCommonSvc fCommonSvc;

    @Autowired
    SmsSvc smsSvc;

    @Autowired
    private RetentionService retentionService;


    /**
     * <pre>
     * 설명     : 재약정/기변신청
     * @param :
     * @return: String
     * @see :
     * </pre>
     */
    @RequestMapping(value = {"/retention/retentionInfoView.do", "/m/retention/retentionInfoView.do"})
    public String getRetentionInfoViewAJax(){
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            return "/mobile/retention/retentionInfoView";
        } else {
            return "/portal/retention/retentionInfoView";
        }

    }


    /**
     * 설명 : 약정 만료알림 화면
     * @author key
     * @Date 2021.12.30
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = {"/retention/retentionNoticeView.do", "/m/retention/retentionNoticeView.do"})
    public String retentionNoticeView(Model model) throws ParseException {

        String nowDateString = DateTimeUtil.getFormatString("yyyy.MM.dd");
        model.addAttribute("threeMonthLaterDate",DateTimeUtil.addMonths(nowDateString, 3 ,"yyyy.MM.dd"));

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            return "/mobile/retention/retentionNoticeView";
        } else {
            return "/portal/retention/retentionNoticeView";
        }
    }

    /**
     * 설명 : 약정 만료알림 신청
     * @author key
     * @Date 2021.12.30
     * @param request
     * @param retentionDto
     * @return
     */
    @RequestMapping(value = {"/retention/retentionNoticeAjax.do","/m/retention/retentionNoticeAjax.do"})
    @ResponseBody
    public Map<String, String> retentionNoticeAjax(HttpServletRequest request,@ModelAttribute("retentionDto") RetentionDto retentionDto) {
        Map<String, String> result = new HashMap<String, String>();

        //인증정보 확인
        NiceResDto sessNiceOpenRes =  SessionUtils.getNiceOpenResCookieBean() ;

        if (sessNiceOpenRes == null || !sessNiceOpenRes.getName().equals(retentionDto.getCustomerNm()) || sessNiceOpenRes.getBirthDate().indexOf(retentionDto.getBirthDate()) < 0 ) {
            result.put("resultCd", "0001");
            result.put("msg", "본인 인증한 정보가 틀립니다.");
            return result;
        }

        //기존 신청 정보 조회
        List<RetentionDto> retentioList = retentionService.selectApyNotiTxtList(retentionDto);
        if (retentioList != null && retentioList.size() > 0 ) {
            String expiryDate = retentioList.get(0).getExpiryDate();
            String toExpiryDate  = "";
            if (expiryDate.length() > 8) {
                //20220402000000
                toExpiryDate = expiryDate.substring(0, 4) + "." + expiryDate.substring(4, 6) + "." + expiryDate.substring(6, 8) ;
            } else {
                toExpiryDate = expiryDate;
            }
            result.put("resultCd", "0002");
            result.put("msg", "약정만료일: "+toExpiryDate+" 로<br> 알림받기가 등록되어 있습니다.");


             //- "약정만료일: YYYY.MM.DD 로 알림받기가 등록되어 있습니다." 텍스트 표시
             return result;
        }



        retentionDto.setCstmrCi(sessNiceOpenRes.getConnInfo());
        retentionDto.setAuthInfo("ReqNo:" + sessNiceOpenRes.getReqSeq() + ", ResNo:" + sessNiceOpenRes.getResSeq() );


        String userId = retentionService.selectRetentionUserId(retentionDto);
        String sysRegIp = request.getRemoteAddr();
        retentionDto.setUserId(userId);
        retentionDto.setAmdIp(sysRegIp);
        retentionDto.setCretIp(sysRegIp);

        int chk = retentionService.insertRetentionUserId(retentionDto);

        if(chk < 0) {
            result.put("resultCd", "-2");
            result.put("msg", "약정만료 알림받기 신청처리중 오류가 발생했습니다.");
            return result;
        }

        result.put("resultCd", "00000");
        result.put("msg", "처리완료");
        return result;

    }


}