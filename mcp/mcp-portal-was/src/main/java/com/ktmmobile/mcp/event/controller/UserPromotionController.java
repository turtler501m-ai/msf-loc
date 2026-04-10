package com.ktmmobile.mcp.event.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.NiceLogDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.NiceLogSvc;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.event.dto.UserPromotionDto;
import com.ktmmobile.mcp.event.service.UserPromotionSvc;
import com.ktmmobile.mcp.join.service.JoinSvc;


@Controller
public class UserPromotionController {

    private static Logger logger = LoggerFactory.getLogger(UserPromotionController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private FormDtlSvc formDtlSvc;

    @Autowired
    JoinSvc joinSvc;

    @Autowired
    NiceLogSvc nicelog ;

    @Autowired
    UserPromotionSvc userPromotionSvc;

    /**
     * 설명 : 회원가입 프로모션 화면 로딩
     */
    @RequestMapping(value = { "/event/userPromotion.do", "/m/event/userPromotion.do" })
    public String requestReView(@ModelAttribute UserPromotionDto userPromotionDto, Model model, HttpServletRequest request) {

        String returnUrl = "";
        FormDtlDTO promoImg = null;

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/event/userPromotion";

            FormDtlDTO formDtlDTO = new FormDtlDTO();
            formDtlDTO.setCdGroupId1("INFOPRMT");
            formDtlDTO.setCdGroupId2("USERPROMO002");
            promoImg = formDtlSvc.FormDtlView(formDtlDTO);
        }else {
            returnUrl = "portal/event/userPromotion";

            FormDtlDTO formDtlDTO = new FormDtlDTO();
            formDtlDTO.setCdGroupId1("INFOPRMT");
            formDtlDTO.setCdGroupId2("USERPROMO001");
            promoImg = formDtlSvc.FormDtlView(formDtlDTO);
        }

     // 이벤트 코드
        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.USER_PROMOTION);
        List<NmcpCdDtlDto> nmcpMainCdDtlDtoList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

        String codeEndDd = "";

        if(nmcpMainCdDtlDtoList !=null && !nmcpMainCdDtlDtoList.isEmpty()){
            codeEndDd = "Y";
        }else {
            codeEndDd = "N";
        }

        model.addAttribute("promoImg", promoImg);
        model.addAttribute("codeEndDd",codeEndDd);

        //회원가입 프로모션으로 인증 받아 가입 한 경우
        String promoFinish = request.getParameter("promoFinish");
        if(promoFinish != null) {
            model.addAttribute("promoFinish","promoFinish");
        }


        return returnUrl;
    }

    /**
     * 설명 : 회원가입 프로모션 팝업 호출 Ajax
     */
    @RequestMapping(value = {"/event/userPromotionPop.do", "/m/event/userPromotionPop.do"})
    public String userPromotionPop(HttpServletRequest request,HttpSession session, Model model) {

        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);

        String returnUrl = "portal/event/userPromotionPop";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/event/userPromotionPop";
        }

        NiceLogDto niceLogDto= new NiceLogDto();
        niceLogDto.setReqSeq(niceResDto.getReqSeq());
        niceLogDto.setResSeq(niceResDto.getResSeq());

        NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);

        SessionUtils.saveUserPromotionRes(niceResDto.getResSeq());


      //회원가입 중복체크
        int duplicateCount = joinSvc.dupleChk(niceResDto.getDupInfo());
        int dormancyCount = joinSvc.dormancyDupleChk(niceResDto.getDupInfo());

        model.addAttribute("snsAddYn", "N");

        if (duplicateCount > 0 || dormancyCount > 0) {
            model.addAttribute("diVal", EncryptUtil.ace256Enc(niceResDto.getDupInfo()));
            model.addAttribute("snsAddYn", "Y");
            session.setAttribute("joinVal", "Y");
        }


        String mobileNo = "";
        int niceHistSeq = 0;

        if(niceLogRtn != null){
            mobileNo = niceResDto.getsMobileNo();
            niceHistSeq = (int)niceLogRtn.getNiceHistSeq();
        }

        model.addAttribute("mobileNo", mobileNo);
        model.addAttribute("niceHistSeq", niceHistSeq);
        model.addAttribute("getInfo", niceResDto);

        model.addAttribute("checkKid", "N");

        if(niceResDto.getBirthDate() != null && !"".equals(niceResDto.getBirthDate())){
            try {
                //만 나이 확인
                String birthDate = niceResDto.getBirthDate().length() > 8 ? EncryptUtil.ace256Dec(niceResDto.getBirthDate()) : niceResDto.getBirthDate();
                niceResDto.setBirthDate(birthDate);
                int age = NmcpServiceUtils.getBirthDateToAge(birthDate, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
                //회원가입 시, 만 14세 미만 이용자 법적대리인 동의 절차 적용 
                if (Constants.AGREE_AUT_AGE > age) {
                    model.addAttribute("checkKid", "Y");
                }
            } catch (CryptoException e) {
                logger.error("Exception e : {}", e.getMessage());
            }

        }


        return returnUrl;
    }

    /**
     * 설명 : 회원가입 프로모션 참여결과 리스트 (나의혜택보기 >이벤트 참여 결과)
     */
    @RequestMapping(value = {"/mypage/myPromotionList.do", "/m/mypage/myPromotionList.do"})
    public String myPromotionList(@ModelAttribute UserPromotionDto userPromotionDto, Model model) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";

        String returnUrl = "portal/mypage/myPromotionList";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/mypage/myPromotionList";
        }

        userPromotionDto.setUserId(userSession.getUserId());
        List<UserPromotionDto> myPromotionList = userPromotionSvc.selectUserPromotionList(userPromotionDto);

        model.addAttribute("myPromotionList",myPromotionList);

        return returnUrl;
    }

}
