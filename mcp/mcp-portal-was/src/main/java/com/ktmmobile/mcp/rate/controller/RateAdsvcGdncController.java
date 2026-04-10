package com.ktmmobile.mcp.rate.controller;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.coupon.dto.McpUserCertDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.*;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.service.PointService;
import com.ktmmobile.mcp.rate.dto.*;
import com.ktmmobile.mcp.rate.service.RateAdsvcGdncService;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;
import com.ktmmobile.mcp.requestReview.service.RequestReviewService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.POINT_RSN_CD_U62;
import static com.ktmmobile.mcp.common.constants.Constants.POINT_TRT_USE;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class RateAdsvcGdncController {

    private static Logger logger = LoggerFactory.getLogger(RateAdsvcGdncController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    RateAdsvcGdncService rateAdsvcGdncService;

    @Autowired
    private RequestReviewService requestReviewService;

    @Autowired
    private FarPricePlanService farPricePlanService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MyBenefitService myBenefitService;

    @Autowired
    private PointService pointService;

    @Autowired
    private RegSvcService regSvcService;

    @Autowired
    private MypageUserService mypageUserService;

    // 파일업로드 경로
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /**
     * 설명 : 요금제 페이지 이동
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/rateBackList.do","/m/rate/rateBackList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String rateBackList(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/rateListBack";
        }else {
            returnUrl =  "/portal/rate/rateListBack";
        }
        model.addAttribute("rateAdsvcCtgBasDTO", rateAdsvcCtgBasDTO);

        return returnUrl;
    }


    /**
     * 설명 : 요금제 페이지 이동
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/rateList.do","/m/rate/rateList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String rateList(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/rateList";
        }else {
            returnUrl =  "/portal/rate/rateList";
        }

        String eventRatePop = request.getParameter("eventRatePop");

        if(StringUtils.isEmpty(eventRatePop)) {
            model.addAttribute("eventRatePop", "N");
       }else {
           model.addAttribute("eventRatePop", "Y");
           model.addAttribute("eventRateSeq", rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq());
           model.addAttribute("eventRateCd", rateAdsvcCtgBasDTO.getRateAdsvcCd());
       }

        model.addAttribute("rateAdsvcCtgBasDTO", rateAdsvcCtgBasDTO);

        return returnUrl;
    }


    /**
     * 설명 : 요금제 비교 페이지
     * @Author : papier
     * @Date : 2025.01.23
     * @return
     */
    @RequestMapping(value = {"/rate/rateComp.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String rateComp() {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/rateComp";
        }else {
            returnUrl =  "/portal/rate/rateComp";
        }

        return returnUrl;
    }

    /**
     * 설명 : 전제 카테고리 xml 정보 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/rate/adsvcGdncListAjax.do","/m/rate/adsvcGdncListAjax.do"})
    @ResponseBody
    public MapWrapper rateAdsvcGdncBasXmlViewAjax(HttpServletRequest request, HttpServletResponse response) {
        MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper();
        return mapWrapper;
    }


    /**
     * 설명 : 전제 카테고리 xml 정보 조회
     * @Author : papier
     * @Date : 2023.07.10
     * @return
     */
    @RequestMapping(value = "/rate/getCtgXmlAllListAjax.do")
    @ResponseBody
    public List<RateAdsvcGdncProdXML> getCtgXmlAllListAjax(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        List<RateAdsvcGdncProdXML> ctgXmlList = rateAdsvcGdncService.getCtgXmlAllList(rateAdsvcCtgBasDTO);
        return ctgXmlList;
    }

    /**
     * 설명 : 2Depth 요금제 카테고리 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/subTabPanel.do","/m/rate/subTabPanel.do"}, method = RequestMethod.GET)
    public String subTabPanel(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/subTabPanel";
        }else {
            returnUrl =  "/portal/rate/subTabPanel";
        }

        List<RateAdsvcCtgBasDTO> buttonXmlList = null;
        if(rateAdsvcGdncService.getProdCount(rateAdsvcCtgBasDTO) == 0) { // 2Depth 카테고리에 상품목록이 없을경우
            rateAdsvcCtgBasDTO.setRateAdsvcDivCd("RATE");
            rateAdsvcCtgBasDTO.setDepthKey(2);
            buttonXmlList = rateAdsvcGdncService.getCtgXmlList(rateAdsvcCtgBasDTO);
        }

        model.addAttribute("upRateAdsvcCtgCd", rateAdsvcCtgBasDTO.getRateAdsvcCtgCd());
        model.addAttribute("buttonXmlList", buttonXmlList);

        return returnUrl;
    }

    /**
     * 설명 : 2Depth 또는 3Depth 요금제 카테고리 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/rateListPanel.do","/m/rate/rateListPanel.do"}, method = RequestMethod.GET)
    public String rateListPanel(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/rateListPanel";
        }else {
            returnUrl =  "/portal/rate/rateListPanel";
        }

        if(rateAdsvcGdncService.getProdCount(rateAdsvcCtgBasDTO) > 0) { // 2Depth 카테고리에 상품목록이 있을경우
            // 2depth 카테고리 목록 조회
            rateAdsvcCtgBasDTO.setRateAdsvcDivCd("RATE");
            rateAdsvcCtgBasDTO.setDepthKey(2);
            List<RateAdsvcCtgBasDTO> ctgXmlList = rateAdsvcGdncService.getCtgXmlList(rateAdsvcCtgBasDTO);

            model.addAttribute("ctgXmlList", ctgXmlList);
        } else {
            // 3depth 카테고리 목록 조회
            rateAdsvcCtgBasDTO.setRateAdsvcDivCd("RATE");
            rateAdsvcCtgBasDTO.setDepthKey(3);
            List<RateAdsvcCtgBasDTO> ctgXmlList = rateAdsvcGdncService.getCtgXmlList(rateAdsvcCtgBasDTO);

            model.addAttribute("ctgXmlList", ctgXmlList);
        }

        return returnUrl;
    }

    /**
     * 설명 : 요금제 카테고리에 해당하는 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/rate/rateContent.do","/m/rate/rateContent.do"}, method = RequestMethod.GET)
    public String rateContent(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) throws ParseException {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/rateContent";
        }else {
            returnUrl =  "/portal/rate/rateContent";
        }

        // 상품목록 xml 조회
        List<RateAdsvcCtgBasDTO> prodXmlList = rateAdsvcGdncService.getProdXmlList(rateAdsvcCtgBasDTO);
        // 요금제부가서비스안내상품관계 xml 조회
        List<RateAdsvcGdncProdRelXML> prodRelXmlList = rateAdsvcGdncService.getRateAdsvcGdncProdRelXml();

        for(RateAdsvcCtgBasDTO prodVo : prodXmlList) {
            // 요금제부가서비스안내기본
            RateAdsvcGdncBasXML gdncBas = rateAdsvcGdncService.getRateAdsvcGdncBasXml(prodVo);

            if(gdncBas != null && DateTimeUtil.isMiddleDateTime(gdncBas.getPstngStartDate(), gdncBas.getPstngEndDate())) {
                // 요금제부가서비스안내기본 추가
                if(gdncBas.getRateAdsvcGdncSeq() == prodVo.getRateAdsvcGdncSeq()) {
                    prodVo.setMmBasAmtDesc(gdncBas.getMmBasAmtDesc());
                    prodVo.setMmBasAmtVatDesc(gdncBas.getMmBasAmtVatDesc());
                    prodVo.setPromotionAmtDesc(gdncBas.getPromotionAmtDesc());
                    prodVo.setPromotionAmtVatDesc(gdncBas.getPromotionAmtVatDesc());
                }

                // 요금제부가서비스혜택안내상세 xml 목록 조회
                List<RateAdsvcBnfitGdncDtlXML> bnfitList = rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(prodVo);

                // 요금제부가서비스혜택안내상세 추가
                if(bnfitList != null) {
                    for(RateAdsvcBnfitGdncDtlXML bnfitVo : bnfitList) {
                        if(("RATEBE01").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 데이터
                            prodVo.setBnfitData(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code1 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code1.getImgNm())) {
                                prodVo.setBnfitDataItemImgNm(code1.getImgNm());
                            } else {
                                prodVo.setBnfitDataItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }
                        }
                        if(("RATEBE02").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 음성
                            prodVo.setBnfitVoice(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code2 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code2.getImgNm())) {
                                prodVo.setBnfitVoiceItemImgNm(code2.getImgNm());
                            } else {
                                prodVo.setBnfitVoiceItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }
                        }
                        if(("RATEBE03").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 문자
                            prodVo.setBnfitSms(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code4 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code4.getImgNm())) {
                                prodVo.setBnfitSmsItemImgNm(code4.getImgNm());
                            } else {
                                prodVo.setBnfitSmsItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }
                        }
                        // 프로모션 데이터
                         if("RATEBE31".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                             prodVo.setPromotionBnfitData(bnfitVo.getRateAdsvcItemDesc());

                             NmcpCdDtlDto code5 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code5.getImgNm())) {
                                prodVo.setBnfitWifiItemImgNm(code5.getImgNm());
                            } else {
                                prodVo.setBnfitWifiItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }
                         }
                         // 프로모션 음성
                         if("RATEBE32".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                             prodVo.setPromotionBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                         }
                         // 프로모션 문자
                         if("RATEBE33".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                             prodVo.setPromotionBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                         }
                        // 제휴혜택 노출문구
                        if(("RATEBE11").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            RateAdsvcBnfitGdncDtlDTO bnfitDto = new RateAdsvcBnfitGdncDtlDTO();
                            bnfitDto.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                            bnfitDto.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                            bnfitDto.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code6 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code6.getImgNm())) {
                                bnfitDto.setRateAdsvcItemImgNm(code6.getImgNm());
                            } else {
                                bnfitDto.setRateAdsvcItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }

                            prodVo.getBnfitList().add(bnfitDto);
                        }
                        // 혜택안내 노출문구
                        if(("RATEBE12").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            RateAdsvcBnfitGdncDtlDTO bnfitDto2 = new RateAdsvcBnfitGdncDtlDTO();
                            bnfitDto2.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());

                            NmcpCdDtlDto code7 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code7.getImgNm())) {
                                bnfitDto2.setRateAdsvcItemImgNm(code7.getImgNm());
                            } else {
                                bnfitDto2.setRateAdsvcItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }

                            if(!StringUtils.isEmpty(bnfitVo.getRateAdsvcItemNm())) {
                                bnfitDto2.setRateAdsvcItemNm(removeHtmlTag(ParseHtmlTagUtil.convertHtmlchars(bnfitVo.getRateAdsvcItemNm())));
                            }
                            if(!StringUtils.isEmpty(bnfitVo.getRateAdsvcItemDesc())) {
                                bnfitDto2.setRateAdsvcItemDesc(removeHtmlTag(ParseHtmlTagUtil.convertHtmlchars(bnfitVo.getRateAdsvcItemDesc())));
                            }
                            prodVo.getBnfitList2().add(bnfitDto2);
                        }

                        //노출순서 : 기본료 -> 약정시 기본료 (RATEBE91) -> 요금할인 시 기본료 (RATEBE92) -> 시니어 할인 기본료 (RATEBE93)
                        if(("RATEBE91").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            prodVo.setContractAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                        }
                        if(("RATEBE92").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            prodVo.setRateDiscntAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                        }
                        if(("RATEBE93").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            prodVo.setSeniorDiscntAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                        }
                    }
                }

                if(prodRelXmlList != null) {
                    for(RateAdsvcGdncProdRelXML prodRelVo : prodRelXmlList) {
                        if(prodRelVo.getRateAdsvcGdncSeq() == prodVo.getRateAdsvcGdncSeq() ) {
                            prodVo.setRateAdsvcCd(prodRelVo.getRateAdsvcCd());
                        }
                    }
                }
            }
        }

        model.addAttribute("subTabId", rateAdsvcCtgBasDTO.getSubTabId());
        model.addAttribute("prodXmlList", prodXmlList);

        return returnUrl;
    }


    @RequestMapping(value = "/rate/rateContentAjax.do")
    @ResponseBody
    public List<RateAdsvcCtgBasDTO> rateContentAjax(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) throws ParseException {

        // 상품목록 xml 조회
        List<RateAdsvcCtgBasDTO> prodXmlList = rateAdsvcGdncService.getProdXmlList(rateAdsvcCtgBasDTO);

        // 요금제 혜택요약 xml 조회
        List<RateGiftPrmtXML> giftPrmtXmlList = rateAdsvcGdncService.getRateGiftPrmtXmlList();
        // 요금제부가서비스안내상품관계 xml 조회
        List<RateAdsvcGdncProdRelXML> prodRelXmlList = rateAdsvcGdncService.getRateAdsvcGdncProdRelXml();

        for(RateAdsvcCtgBasDTO prodVo : prodXmlList) {
            // 요금제부가서비스안내기본
            RateAdsvcGdncBasXML gdncBas = rateAdsvcGdncService.getRateAdsvcGdncBasXml(prodVo);

            if(gdncBas != null && DateTimeUtil.isMiddleDateTime(gdncBas.getPstngStartDate(), gdncBas.getPstngEndDate())) {
                // 요금제부가서비스안내기본 추가
                if(gdncBas.getRateAdsvcGdncSeq() == prodVo.getRateAdsvcGdncSeq()) {
                    prodVo.setMmBasAmtDesc(gdncBas.getMmBasAmtDesc());
                    prodVo.setMmBasAmtVatDesc(gdncBas.getMmBasAmtVatDesc());
                    prodVo.setPromotionAmtDesc(gdncBas.getPromotionAmtDesc());
                    prodVo.setPromotionAmtVatDesc(gdncBas.getPromotionAmtVatDesc());
                    prodVo.setStickerCtg(gdncBas.getStickerCtg());
                }
                // 요금제부가서비스혜택안내상세 xml 목록 조회
                List<RateAdsvcBnfitGdncDtlXML> bnfitList = rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(prodVo);

                // 요금제부가서비스혜택안내상세 추가
                if(bnfitList != null) {
                    for(RateAdsvcBnfitGdncDtlXML bnfitVo : bnfitList) {

                        if(("RATEBE01").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 데이터
                            prodVo.setBnfitData(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code1 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code1.getImgNm())) {
                                prodVo.setBnfitDataItemImgNm(code1.getImgNm());
                            } else {
                                prodVo.setBnfitDataItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }
                        } else if(("RATEBE02").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 음성
                            prodVo.setBnfitVoice(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code2 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code2.getImgNm())) {
                                prodVo.setBnfitVoiceItemImgNm(code2.getImgNm());
                            } else {
                                prodVo.setBnfitVoiceItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }
                        } else if(("RATEBE03").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 문자
                            prodVo.setBnfitSms(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code4 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code4.getImgNm())) {
                                prodVo.setBnfitSmsItemImgNm(code4.getImgNm());
                            } else {
                                prodVo.setBnfitSmsItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }
                        } else if("RATEBE21".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(데이터(KB))
                            bnfitVo.setRateAdsvcItemDesc(StringUtil.NVL(bnfitVo.getRateAdsvcItemDesc(), "0"));
                            bnfitVo.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc().trim().replaceAll(",", ""));

                            /*
                            bnfit.setRateAdsvcItemDesc(StringUtil.NVL(bnfit.getRateAdsvcItemDesc(), "0"));
                            bnfit.setRateAdsvcItemDesc(bnfit.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                            chargeInfo.getMspRateMstDto().setXmlDataCnt((Integer.parseInt(StringUtil.NVL(chargeInfo.getMspRateMstDto().getXmlDataCnt(), "0")) + Integer.parseInt(StringUtil.NVL((String.valueOf(Math.round(Double.parseDouble(bnfit.getRateAdsvcItemDesc())))), "0")))+"");
                            if(Integer.parseInt(chargeInfo.getMspRateMstDto().getXmlDataCnt()) > 999999999) {
                                chargeInfo.getMspRateMstDto().setXmlDataCnt("999999999");
                            }
                            */

                            int xmlDataInt = 0;
                            try {
                                xmlDataInt = Integer.parseInt(bnfitVo.getRateAdsvcItemDesc());
                            } catch (NumberFormatException e) {
                                xmlDataInt = 0;
                            }
                            prodVo.setXmlDataCnt(xmlDataInt);

                        } else if("RATEBE22".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(데이터(KB))
                            bnfitVo.setRateAdsvcItemDesc(StringUtil.NVL(bnfitVo.getRateAdsvcItemDesc(), "0"));
                            bnfitVo.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc().trim().replaceAll(",", ""));

                            /*
                            bnfit.setRateAdsvcItemDesc(StringUtil.NVL(bnfit.getRateAdsvcItemDesc(), "0"));
                                bnfit.setRateAdsvcItemDesc(bnfit.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                                chargeInfo.getMspRateMstDto().setXmlCallCnt((Integer.parseInt(StringUtil.NVL(chargeInfo.getMspRateMstDto().getXmlCallCnt(), "0")) + Integer.parseInt(StringUtil.NVL((String.valueOf(Math.round(Double.parseDouble(bnfit.getRateAdsvcItemDesc())))), "0")))+"");
                                if(Integer.parseInt(chargeInfo.getMspRateMstDto().getXmlCallCnt()) > 999999999) {
                                    chargeInfo.getMspRateMstDto().setXmlCallCnt("999999999");
                                }
                            */

                            int xmlDataInt = 0;
                            try {
                                xmlDataInt = Integer.parseInt(bnfitVo.getRateAdsvcItemDesc());
                            } catch (NumberFormatException e) {
                                xmlDataInt = 0;
                            }
                            prodVo.setXmlCallCnt(xmlDataInt);

                        }  else if("RATEBE25".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(데이터Qos(Kbps))
                            bnfitVo.setRateAdsvcItemDesc(StringUtil.NVL(bnfitVo.getRateAdsvcItemDesc(), "0"));
                            bnfitVo.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                            int xmlQosCntInt = 0;
                            try {
                                xmlQosCntInt = Integer.parseInt(bnfitVo.getRateAdsvcItemDesc());
                            } catch (NumberFormatException e) {
                                xmlQosCntInt = 0;
                            }
                            prodVo.setXmlQosCnt(xmlQosCntInt);

                        }else if("RATEBE31".equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 프로모션 데이터
                            prodVo.setPromotionBnfitData(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code5 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code5.getImgNm())) {
                                prodVo.setBnfitWifiItemImgNm(code5.getImgNm());
                            } else {
                                prodVo.setBnfitWifiItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }
                        } else if("RATEBE32".equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 프로모션 음성
                            prodVo.setPromotionBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                        } else if("RATEBE33".equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 프로모션 문자
                            prodVo.setPromotionBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                        } else if(("RATEBE11").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 제휴혜택 노출문구
                            RateAdsvcBnfitGdncDtlDTO bnfitDto = new RateAdsvcBnfitGdncDtlDTO();
                            bnfitDto.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                            bnfitDto.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                            bnfitDto.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());

                            NmcpCdDtlDto code6 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code6.getImgNm())) {
                                bnfitDto.setRateAdsvcItemImgNm(code6.getImgNm());
                            } else {
                                bnfitDto.setRateAdsvcItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }

                            prodVo.getBnfitList().add(bnfitDto);
                        } else if(("RATEBE12").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { // 혜택안내 노출문구
                            RateAdsvcBnfitGdncDtlDTO bnfitDto2 = new RateAdsvcBnfitGdncDtlDTO();
                            bnfitDto2.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());

                            NmcpCdDtlDto code7 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                            if(!StringUtils.isEmpty(code7.getImgNm())) {
                                bnfitDto2.setRateAdsvcItemImgNm(code7.getImgNm());
                            } else {
                                bnfitDto2.setRateAdsvcItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                            }

                            if(!StringUtils.isEmpty(bnfitVo.getRateAdsvcItemNm())) {
                                bnfitDto2.setRateAdsvcItemNm(removeHtmlTag(ParseHtmlTagUtil.convertHtmlchars(bnfitVo.getRateAdsvcItemNm())));
                            }
                            if(!StringUtils.isEmpty(bnfitVo.getRateAdsvcItemDesc())) {
                                bnfitDto2.setRateAdsvcItemDesc(removeHtmlTag(ParseHtmlTagUtil.convertHtmlchars(bnfitVo.getRateAdsvcItemDesc())));
                            }
                            prodVo.getBnfitList2().add(bnfitDto2);
                        } else if(("RATEBE91").equals(bnfitVo.getRateAdsvcBnfitItemCd())) { //노출순서 : 기본료 -> 약정시 기본료 (RATEBE91) -> 요금할인 시 기본료 (RATEBE92) -> 시니어 할인 기본료 (RATEBE93)
                            prodVo.setContractAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                        } else if(("RATEBE92").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            prodVo.setRateDiscntAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                        } else if(("RATEBE93").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            prodVo.setSeniorDiscntAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                        } else if(("RATEBE95").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            prodVo.setMaxDataDelivery(bnfitVo.getRateAdsvcItemDesc());
                        } else if(("RATEBE25").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(데이터Qos(Kbps))
                            bnfitVo.setRateAdsvcItemDesc(StringUtil.NVL(bnfitVo.getRateAdsvcItemDesc(), "0"));
                            bnfitVo.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                            int bnfitDataInt = 0;
                            try {
                                bnfitDataInt =  Integer.parseInt(bnfitVo.getRateAdsvcItemDesc());
                            } catch(NumberFormatException e) {
                                logger.error(e.getMessage());
                            } catch(Exception e) {
                                logger.error(e.getMessage());
                            }

                            prodVo.setBnfitDataInt(bnfitDataInt);
                        }

                    }
                }

                if(prodRelXmlList != null) {
                    for(RateAdsvcGdncProdRelXML prodRelVo : prodRelXmlList) {
                        if(prodRelVo.getRateAdsvcGdncSeq() == prodVo.getRateAdsvcGdncSeq() ) {
                            prodVo.setRateAdsvcCd(prodRelVo.getRateAdsvcCd());

                            // === START 요금제 혜택요약 ===
                            if(!StringUtil.isEmpty(prodRelVo.getGiftPrmtSeqs())){
                                RateGiftPrmtListDTO rateGiftPrmtListDTO = rateAdsvcGdncService.initRateGiftPrmtInfo(giftPrmtXmlList, prodRelVo.getGiftPrmtSeqs(), "");
                                prodVo.setRateGiftPrmtListDTO(rateGiftPrmtListDTO);
                            }
                            // === END 요금제 혜택요약 ===
                        }
                    }
                }
            }
        }

        return prodXmlList;
    }

    /**
     * 설명 : 요금제 상세화면 팝업
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param pageInfoBean
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/rateLayer.do","/m/rate/rateLayer.do"}, method = RequestMethod.GET)
    public String rateLayer(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, PageInfoBean pageInfoBean, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/rateLayer";
        }else {
            returnUrl =  "/portal/rate/rateLayer";
        }

        // 요금제부가서비스안내기본
        RateAdsvcGdncBasXML gdncBasVo = rateAdsvcGdncService.getRateAdsvcGdncBasXml(rateAdsvcCtgBasDTO);

        //할인 금액 조회
        MspSaleSubsdMstDto rtnRateInfo = farPricePlanService.getRateInfo(rateAdsvcCtgBasDTO.getRateAdsvcCd());


//        if (rtnRateInfo != null) {
//            promotionDcAmt = rtnRateInfo.getPromotionDcAmt();
//        }

        if (gdncBasVo == null) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }
        int promotionDcAmt = ObjectUtils.isNotEmpty(rtnRateInfo) ? rtnRateInfo.getPromotionDcAmt() : 0;
        if (promotionDcAmt > 0) {
            int payMnthChargeAmt = rtnRateInfo.getPayMnthChargeAmt();  //getPayMnthChargeAmt
            DecimalFormat decFormat = new DecimalFormat("###,###");
            String strPayMnthChargeAmt = decFormat.format(payMnthChargeAmt);
            gdncBasVo.setPromotionAmtVatDesc(strPayMnthChargeAmt);  //프로모션 요금
        } else {
            gdncBasVo.setPromotionAmtVatDesc("");
        }

        // 요금제부가서비스혜택안내상세 xml 목록 조회
        List<RateAdsvcBnfitGdncDtlXML> bnfitList = rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(rateAdsvcCtgBasDTO);
        // 요금제부가서비스안내상세 xml 목록 조회
        List<RateAdsvcGdncDtlXML> gdncDtlList = rateAdsvcGdncService.getRateAdsvcGdncDtlXmlList(rateAdsvcCtgBasDTO);
        // 요금제부가서비스안내링크상세 xml 목록 조회
        List<RateAdsvcGdncLinkDtlXML> gdncLinkDtlList = rateAdsvcGdncService.getRateAdsvcGdncLinkDtlXmlList(rateAdsvcCtgBasDTO);
        // 상품목록 xml 조회
        List<RateAdsvcCtgBasDTO> prodXmlList = rateAdsvcGdncService.getProdXmlList(rateAdsvcCtgBasDTO);

        RateAdsvcCtgBasDTO rateAdsvcVo = new RateAdsvcCtgBasDTO();
        // 상위 카테고리명 셋팅
        if(prodXmlList != null) {
            for(RateAdsvcCtgBasDTO prodVo : prodXmlList) {
                if((rateAdsvcCtgBasDTO.getRateAdsvcCtgCd()).equals(prodVo.getRateAdsvcCtgCd())
                        && rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq() == prodVo.getRateAdsvcGdncSeq()) {
                    rateAdsvcVo.setRateAdsvcCtgNm(prodVo.getRateAdsvcCtgNm());
                }
            }
        }

        // 요금제부가서비스혜택안내상세
        RateAdsvcGdncBasDTO rateAdsvcGdncBasDTO = new RateAdsvcGdncBasDTO();
        if(bnfitList != null) {
            for(RateAdsvcBnfitGdncDtlXML bnfitVo : bnfitList) {
                // 데이터
                if("RATEBE01".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setBnfitData(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code1 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code1.getImgNm())) {
                        rateAdsvcVo.setBnfitDataItemImgNm(code1.getImgNm());
                    } else {
                        rateAdsvcVo.setBnfitDataItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }
                }
                // 음성
                if("RATEBE02".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setBnfitVoice(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code2 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code2.getImgNm())) {
                        rateAdsvcVo.setBnfitVoiceItemImgNm(code2.getImgNm());
                    } else {
                        rateAdsvcVo.setBnfitVoiceItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }
                }
                // 문자
                if("RATEBE03".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setBnfitSms(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code3 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code3.getImgNm())) {
                        rateAdsvcVo.setBnfitSmsItemImgNm(code3.getImgNm());
                    } else {
                        rateAdsvcVo.setBnfitSmsItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }
                }
                // WiFi
                if("RATEBE04".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setBnfitWifi(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code4 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code4.getImgNm())) {
                        rateAdsvcVo.setBnfitWifiItemImgNm(code4.getImgNm());
                    } else {
                        rateAdsvcVo.setBnfitWifiItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }
                }
                // 프로모션 데이터
                if("RATEBE31".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setPromotionBnfitData(bnfitVo.getRateAdsvcItemDesc());
                }
                // 프로모션 음성
                if("RATEBE32".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setPromotionBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                }
                // 프로모션 문자
                if("RATEBE33".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setPromotionBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                }

                // 최대 데이터제공량(데이터(노출문구))
                if("RATEBE95".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setMaxDataDelivery(bnfitVo.getRateAdsvcItemDesc());
                }
                // 제휴혜택 노출문구
                if(("RATEBE11").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    RateAdsvcBnfitGdncDtlDTO bnfitDto = new RateAdsvcBnfitGdncDtlDTO();
                    bnfitDto.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                    bnfitDto.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                    bnfitDto.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code5 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code5.getImgNm())) {
                        bnfitDto.setRateAdsvcItemImgNm(code5.getImgNm());
                    } else {
                        bnfitDto.setRateAdsvcItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }

                    rateAdsvcVo.getBnfitList().add(bnfitDto);
                }
                // 혜택안내 노출문구
                if(("RATEBE12").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    RateAdsvcBnfitGdncDtlDTO bnfitDto2 = new RateAdsvcBnfitGdncDtlDTO();
                    bnfitDto2.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                    bnfitDto2.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                    bnfitDto2.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code6 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code6.getImgNm())) {
                        bnfitDto2.setRateAdsvcItemImgNm(code6.getImgNm());
                    } else {
                        bnfitDto2.setRateAdsvcItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }

                    rateAdsvcVo.getBnfitList2().add(bnfitDto2);
                }

                //노출순서 : 기본료 -> 약정시 기본료 (RATEBE91) -> 요금할인 시 기본료 (RATEBE92) -> 시니어 할인 기본료 (RATEBE93)
                if(("RATEBE91").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcGdncBasDTO.setContractAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                }
                if(("RATEBE92").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcGdncBasDTO.setRateDiscntAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                }
                if(("RATEBE93").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcGdncBasDTO.setSeniorDiscntAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                }
            }
        }

        // 요금제부가서비스안내상세
        if(gdncDtlList != null) {
            for(RateAdsvcGdncDtlXML gdncDtlVo : gdncDtlList) {

                RateAdsvcGdncDtlDTO rateAdsvcGdncDtlDTO = new RateAdsvcGdncDtlDTO();

                rateAdsvcGdncDtlDTO.setFontStyleCd(gdncDtlVo.getFontStyleCd());
                rateAdsvcGdncDtlDTO.setRateAdsvcItemNm(gdncDtlVo.getRateAdsvcItemNm());
                rateAdsvcGdncDtlDTO.setRateAdsvcItemSbst(gdncDtlVo.getRateAdsvcItemSbst());

                if("RATE101".equals(gdncDtlVo.getRateAdsvcItemCd())) {
                    rateAdsvcVo.getGdncDtlBasList().add(rateAdsvcGdncDtlDTO);
                } else {
                    rateAdsvcVo.getGdncDtlList().add(rateAdsvcGdncDtlDTO);
                }
            }
        }

        // 요금제부가서비스안내링크상세
        if(gdncLinkDtlList != null) {
            for(RateAdsvcGdncLinkDtlXML gdncLinDtlkVo : gdncLinkDtlList) {
                RateAdsvcGdncLinkDtlDTO linkDtlDto = new RateAdsvcGdncLinkDtlDTO();
                linkDtlDto.setLinkNm(gdncLinDtlkVo.getLinkNm());
                linkDtlDto.setLinkUrl(gdncLinDtlkVo.getLinkUrl());

                rateAdsvcVo.getGdncLinkDtlList().add(linkDtlDto);
            }
        }

        // 사용후기 조회
        RequestReviewDto requestReviewDto = new RequestReviewDto();
        requestReviewDto.setRateCd(rateAdsvcCtgBasDTO.getRateAdsvcCd());

        pageInfoBean.setPageNo(1); // 현재 페이지 번호 초기화
        pageInfoBean.setRecordCount(10); // 한페이지당 보여줄 리스트 수

        int total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto); // 카운터 조회
        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        List<RequestReviewDto> requestReviewList = requestReviewService.selectRequestReviewList(requestReviewDto, skipResult, maxResult);
        if(requestReviewList !=null && !requestReviewList.isEmpty()){
            for(RequestReviewDto dto : requestReviewList){
                dto.setSysRdateDd(DateTimeUtil.changeFormat(dto.getSysRdate(),"yyyy.MM.dd"));
                dto.setMkRegNm(dto.getRegNm());
                dto.setRegNm("");
            }
        }

        if (requestReviewList == null) {
            model.addAttribute("requestReviewList", null);
            model.addAttribute("listCount", 0);
        } else {
            model.addAttribute("requestReviewList", requestReviewList);
            model.addAttribute("listCount", requestReviewList.size());
        }

        model.addAttribute("gdncBasVo", gdncBasVo);
        model.addAttribute("rateAdsvcVo", rateAdsvcVo);
        model.addAttribute("requestReviewDto", requestReviewDto);
        model.addAttribute("maxPage", pageInfoBean.getTotalPageCount());

        model.addAttribute("total", total);
        model.addAttribute("fileuploadBase", fileuploadBase);
        model.addAttribute("rateAdsvcCtgBasDTO", rateAdsvcCtgBasDTO);
        model.addAttribute("rateAdsvcGdncBasDTO", rateAdsvcGdncBasDTO);

        return returnUrl;
    }

    /**
     * 설명 : 요금제 상세화면(AI용)
     * 기존 rateLayer과 동일하나 레이어팝업이 아닌 전체화면
     */
    @RequestMapping(value = {"/rate/rateDetail.do","/m/rate/rateDetail.do"}, method = RequestMethod.GET)
    public String rateDetail(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, PageInfoBean pageInfoBean, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/rateDetail";
        }else {
            returnUrl =  "/portal/rate/rateDetail";
        }

        // 요금제부가서비스안내기본
        RateAdsvcGdncBasXML gdncBasVo = rateAdsvcGdncService.getRateAdsvcGdncBasXml(rateAdsvcCtgBasDTO);

        //할인 금액 조회
        MspSaleSubsdMstDto rtnRateInfo = farPricePlanService.getRateInfo(rateAdsvcCtgBasDTO.getRateAdsvcCd());

        if (gdncBasVo == null) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }
        int promotionDcAmt = ObjectUtils.isNotEmpty(rtnRateInfo) ? rtnRateInfo.getPromotionDcAmt() : 0;
        if (promotionDcAmt > 0) {
            int payMnthChargeAmt = rtnRateInfo.getPayMnthChargeAmt();  //getPayMnthChargeAmt
            DecimalFormat decFormat = new DecimalFormat("###,###");
            String strPayMnthChargeAmt = decFormat.format(payMnthChargeAmt);
            gdncBasVo.setPromotionAmtVatDesc(strPayMnthChargeAmt);  //프로모션 요금
        } else {
            gdncBasVo.setPromotionAmtVatDesc("");
        }

        // 요금제부가서비스혜택안내상세 xml 목록 조회
        List<RateAdsvcBnfitGdncDtlXML> bnfitList = rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(rateAdsvcCtgBasDTO);
        // 요금제부가서비스안내상세 xml 목록 조회
        List<RateAdsvcGdncDtlXML> gdncDtlList = rateAdsvcGdncService.getRateAdsvcGdncDtlXmlList(rateAdsvcCtgBasDTO);
        // 요금제부가서비스안내링크상세 xml 목록 조회
        List<RateAdsvcGdncLinkDtlXML> gdncLinkDtlList = rateAdsvcGdncService.getRateAdsvcGdncLinkDtlXmlList(rateAdsvcCtgBasDTO);
        // 상품목록 xml 조회
        List<RateAdsvcCtgBasDTO> prodXmlList = rateAdsvcGdncService.getProdXmlList(rateAdsvcCtgBasDTO);

        RateAdsvcCtgBasDTO rateAdsvcVo = new RateAdsvcCtgBasDTO();
        // 상위 카테고리명 셋팅
        if(prodXmlList != null) {
            for(RateAdsvcCtgBasDTO prodVo : prodXmlList) {
                if((rateAdsvcCtgBasDTO.getRateAdsvcCtgCd()).equals(prodVo.getRateAdsvcCtgCd())
                        && rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq() == prodVo.getRateAdsvcGdncSeq()) {
                    rateAdsvcVo.setRateAdsvcCtgNm(prodVo.getRateAdsvcCtgNm());
                }
            }
        }

        // 요금제부가서비스혜택안내상세
        RateAdsvcGdncBasDTO rateAdsvcGdncBasDTO = new RateAdsvcGdncBasDTO();
        if(bnfitList != null) {
            for(RateAdsvcBnfitGdncDtlXML bnfitVo : bnfitList) {
                // 데이터
                if("RATEBE01".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setBnfitData(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code1 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code1.getImgNm())) {
                        rateAdsvcVo.setBnfitDataItemImgNm(code1.getImgNm());
                    } else {
                        rateAdsvcVo.setBnfitDataItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }
                }
                // 음성
                if("RATEBE02".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setBnfitVoice(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code2 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code2.getImgNm())) {
                        rateAdsvcVo.setBnfitVoiceItemImgNm(code2.getImgNm());
                    } else {
                        rateAdsvcVo.setBnfitVoiceItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }
                }
                // 문자
                if("RATEBE03".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setBnfitSms(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code3 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code3.getImgNm())) {
                        rateAdsvcVo.setBnfitSmsItemImgNm(code3.getImgNm());
                    } else {
                        rateAdsvcVo.setBnfitSmsItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }
                }
                // WiFi
                if("RATEBE04".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setBnfitWifi(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code4 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code4.getImgNm())) {
                        rateAdsvcVo.setBnfitWifiItemImgNm(code4.getImgNm());
                    } else {
                        rateAdsvcVo.setBnfitWifiItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }
                }
                // 프로모션 데이터
                if("RATEBE31".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setPromotionBnfitData(bnfitVo.getRateAdsvcItemDesc());
                }
                // 프로모션 음성
                if("RATEBE32".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setPromotionBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                }
                // 프로모션 문자
                if("RATEBE33".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setPromotionBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                }

                // 최대 데이터제공량(데이터(노출문구))
                if("RATEBE95".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcVo.setMaxDataDelivery(bnfitVo.getRateAdsvcItemDesc());
                }
                // 제휴혜택 노출문구
                if(("RATEBE11").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    RateAdsvcBnfitGdncDtlDTO bnfitDto = new RateAdsvcBnfitGdncDtlDTO();
                    bnfitDto.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                    bnfitDto.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                    bnfitDto.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code5 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code5.getImgNm())) {
                        bnfitDto.setRateAdsvcItemImgNm(code5.getImgNm());
                    } else {
                        bnfitDto.setRateAdsvcItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }

                    rateAdsvcVo.getBnfitList().add(bnfitDto);
                }
                // 혜택안내 노출문구
                if(("RATEBE12").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    RateAdsvcBnfitGdncDtlDTO bnfitDto2 = new RateAdsvcBnfitGdncDtlDTO();
                    bnfitDto2.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                    bnfitDto2.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                    bnfitDto2.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());

                    NmcpCdDtlDto code6 = rateAdsvcGdncService.getDtlCode("CO0012", bnfitVo.getRateAdsvcItemImgCd());
                    if(!StringUtils.isEmpty(code6.getImgNm())) {
                        bnfitDto2.setRateAdsvcItemImgNm(code6.getImgNm());
                    } else {
                        bnfitDto2.setRateAdsvcItemImgNm(bnfitVo.getRateAdsvcItemImgNm());
                    }

                    rateAdsvcVo.getBnfitList2().add(bnfitDto2);
                }

                //노출순서 : 기본료 -> 약정시 기본료 (RATEBE91) -> 요금할인 시 기본료 (RATEBE92) -> 시니어 할인 기본료 (RATEBE93)
                if(("RATEBE91").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcGdncBasDTO.setContractAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                }
                if(("RATEBE92").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcGdncBasDTO.setRateDiscntAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                }
                if(("RATEBE93").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    rateAdsvcGdncBasDTO.setSeniorDiscntAmtVatDesc(bnfitVo.getRateAdsvcItemDesc());
                }
            }
        }

        // 요금제부가서비스안내상세
        if(gdncDtlList != null) {
            for(RateAdsvcGdncDtlXML gdncDtlVo : gdncDtlList) {

                RateAdsvcGdncDtlDTO rateAdsvcGdncDtlDTO = new RateAdsvcGdncDtlDTO();

                rateAdsvcGdncDtlDTO.setFontStyleCd(gdncDtlVo.getFontStyleCd());
                rateAdsvcGdncDtlDTO.setRateAdsvcItemNm(gdncDtlVo.getRateAdsvcItemNm());
                rateAdsvcGdncDtlDTO.setRateAdsvcItemSbst(gdncDtlVo.getRateAdsvcItemSbst());

                if("RATE101".equals(gdncDtlVo.getRateAdsvcItemCd())) {
                    rateAdsvcVo.getGdncDtlBasList().add(rateAdsvcGdncDtlDTO);
                } else {
                    rateAdsvcVo.getGdncDtlList().add(rateAdsvcGdncDtlDTO);
                }
            }
        }

        // 요금제부가서비스안내링크상세
        if(gdncLinkDtlList != null) {
            for(RateAdsvcGdncLinkDtlXML gdncLinDtlkVo : gdncLinkDtlList) {
                RateAdsvcGdncLinkDtlDTO linkDtlDto = new RateAdsvcGdncLinkDtlDTO();
                linkDtlDto.setLinkNm(gdncLinDtlkVo.getLinkNm());
                linkDtlDto.setLinkUrl(gdncLinDtlkVo.getLinkUrl());

                rateAdsvcVo.getGdncLinkDtlList().add(linkDtlDto);
            }
        }

        // 사용후기 조회
        RequestReviewDto requestReviewDto = new RequestReviewDto();
        requestReviewDto.setRateCd(rateAdsvcCtgBasDTO.getRateAdsvcCd());

        pageInfoBean.setPageNo(1); // 현재 페이지 번호 초기화
        pageInfoBean.setRecordCount(10); // 한페이지당 보여줄 리스트 수

        int total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto); // 카운터 조회
        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        List<RequestReviewDto> requestReviewList = requestReviewService.selectRequestReviewList(requestReviewDto, skipResult, maxResult);
        if(requestReviewList !=null && !requestReviewList.isEmpty()){
            for(RequestReviewDto dto : requestReviewList){
                dto.setSysRdateDd(DateTimeUtil.changeFormat(dto.getSysRdate(),"yyyy.MM.dd"));
                dto.setMkRegNm(dto.getRegNm());
                dto.setRegNm("");
            }
        }

        if (requestReviewList == null) {
            model.addAttribute("requestReviewList", null);
            model.addAttribute("listCount", 0);
        } else {
            model.addAttribute("requestReviewList", requestReviewList);
            model.addAttribute("listCount", requestReviewList.size());
        }

        model.addAttribute("gdncBasVo", gdncBasVo);
        model.addAttribute("rateAdsvcVo", rateAdsvcVo);
        model.addAttribute("requestReviewDto", requestReviewDto);
        model.addAttribute("maxPage", pageInfoBean.getTotalPageCount());

        model.addAttribute("total", total);
        model.addAttribute("fileuploadBase", fileuploadBase);
        model.addAttribute("rateAdsvcCtgBasDTO", rateAdsvcCtgBasDTO);
        model.addAttribute("rateAdsvcGdncBasDTO", rateAdsvcGdncBasDTO);

        return returnUrl;
    }

     /**
      * 설명 : 약정할인 프로그램 및 할인반환금 안내 팝업
      * @Author : 강채신
      * @Date : 2021.12.30
      * @param request
      * @param response
      * @param model
      * @return
      */
     @RequestMapping(value = {"/rate/rateAgreeView.do","/m/rate/rateAgreeView.do"}, method = RequestMethod.GET)
     public String rateAgreeView(HttpServletRequest request, HttpServletResponse response, Model model) {

         String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/rateAgreeView";
        }else {
            returnUrl =  "/portal/rate/rateAgreeView";
        }

         RateAgreeDTO rateAgreeDto = rateAdsvcGdncService.getRateAgreeView();
         rateAgreeDto.setDocContent(ParseHtmlTagUtil.convertHtmlchars(rateAgreeDto.getDocContent()));

         model.addAttribute("rateAgreeDto", rateAgreeDto);

         return returnUrl;
     }

    /**
     * 설명 : 부가서비스 페이지 이동
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/adsvcGdncList.do","/m/rate/adsvcGdncList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String adsvcGdncList(HttpServletRequest request, HttpServletResponse response, Model model,@ModelAttribute RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/adsvcGdncList";
        }else {
            returnUrl =  "/portal/rate/adsvcGdncList";
        }

        //로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String userDivision = "00";
        if(userSession !=null) {
            userDivision = StringUtil.NVL(userSession.getUserDivision(),"00");
        }

        String rateAdsvcCtgCd = rateAdsvcCtgBasDTO.getRateAdsvcCtgCd();
        int rateAdsvcGdncSeq = rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq();
        model.addAttribute("paramRateAdsvcCtgCd", rateAdsvcCtgCd);
        model.addAttribute("paramRateAdsvcGdncSeq", rateAdsvcGdncSeq);
        model.addAttribute("userDivision", userDivision);

        return returnUrl;
    }

    /**
     * 설명 : 2Depth 부가서비스 카테고리 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/adsvcSubTabPanel.do","/m/rate/adsvcSubTabPanel.do"}, method = RequestMethod.GET)
    public String adsvcGdncSubTabPanel(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/adsvcSubTabPanel";
        }else {
            returnUrl =  "/portal/rate/adsvcSubTabPanel";
        }

        // 2depth 카테고리 목록 조회
        rateAdsvcCtgBasDTO.setRateAdsvcDivCd("ADDSV");
        rateAdsvcCtgBasDTO.setDepthKey(2);
        List<RateAdsvcCtgBasDTO> buttonXmlList = rateAdsvcGdncService.getCtgXmlList(rateAdsvcCtgBasDTO);

        model.addAttribute("buttonRateAdsvcCtgCd", rateAdsvcCtgBasDTO.getRateAdsvcCtgCd());
        model.addAttribute("buttonXmlList", buttonXmlList);

        return returnUrl;
    }

    /**
     * 설명 : 부가서비스 전체 상품목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/adsvcAllListPanel.do","/m/rate/adsvcAllListPanel.do"}, method = RequestMethod.GET)
    public String adsvcAllListPanel(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/adsvcListPanel";
        }else {
            returnUrl =  "/portal/rate/adsvcListPanel";
        }

        String rateAdsvcCtgCd = "";

        MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper();

        Map<String, ListXmlWrapper> ctgMapXml = mapWrapper.getItem();
        ListXmlWrapper ctgListXml = ctgMapXml.get("ctgList");
        List<RateAdsvcGdncProdXML> ctgList = ctgListXml.getItem();

        for(RateAdsvcGdncProdXML item : ctgList) {
            try {
                if(item.getDepthKey() == 1 && "Y".equals(item.getUseYn())
                   && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                   && (!"137".equals(item.getRateAdsvcCtgCd()))
                   && "ADDSV".equals(item.getRateAdsvcDivCd())
                ) {
                    rateAdsvcCtgCd = item.getRateAdsvcCtgCd();
                }
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        List<RateAdsvcCtgBasDTO> dupProdXmlList = new ArrayList<RateAdsvcCtgBasDTO>();

        rateAdsvcCtgBasDTO.setRateAdsvcCtgCd(rateAdsvcCtgCd);
        rateAdsvcCtgBasDTO.setRateAdsvcDivCd("ADDSV");
        rateAdsvcCtgBasDTO.setDepthKey(2);
        List<RateAdsvcCtgBasDTO> buttonXmlList = rateAdsvcGdncService.getCtgXmlList(rateAdsvcCtgBasDTO, mapWrapper);

        for(RateAdsvcCtgBasDTO rateAdsvcCtgBas : buttonXmlList) {
            rateAdsvcCtgBas.setFreeYn(rateAdsvcCtgBasDTO.getFreeYn());
            rateAdsvcCtgBas.setSelfYn(rateAdsvcCtgBasDTO.getSelfYn());
            List<RateAdsvcCtgBasDTO> tempProdXmlList = rateAdsvcGdncService.getProdXmlList(rateAdsvcCtgBas, mapWrapper);
            dupProdXmlList.addAll(tempProdXmlList);
        }

        List<RateAdsvcCtgBasDTO> prodXmlList = rateAdsvcGdncService.deduplicateProdList(dupProdXmlList);

        model.addAttribute("subTabId", rateAdsvcCtgBasDTO.getSubTabId());
        model.addAttribute("prodXmlList", prodXmlList);

        return returnUrl;
    }

    /**
     * 설명 : 부가서비스 상품목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/adsvcListPanel.do","/m/rate/adsvcListPanel.do"}, method = RequestMethod.GET)
    public String adsvcGdncListPanel(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/adsvcListPanel";
        }else {
            returnUrl =  "/portal/rate/adsvcListPanel";
        }

        List<String> ctgCdList = new ArrayList<String>();
        ctgCdList = rateAdsvcCtgBasDTO.getCtgCdList();

        List<RateAdsvcCtgBasDTO> dupProdXmlList = new ArrayList<RateAdsvcCtgBasDTO>();

        for(String ctgCd : ctgCdList) {
            RateAdsvcCtgBasDTO tmpRateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
            tmpRateAdsvcCtgBasDTO.setRateAdsvcCtgCd(ctgCd);
            tmpRateAdsvcCtgBasDTO.setFreeYn(rateAdsvcCtgBasDTO.getFreeYn());
            tmpRateAdsvcCtgBasDTO.setSelfYn(rateAdsvcCtgBasDTO.getSelfYn());
            List<RateAdsvcCtgBasDTO> tmpProdXmlList = new ArrayList<RateAdsvcCtgBasDTO>();

            tmpProdXmlList = rateAdsvcGdncService.getProdXmlList(tmpRateAdsvcCtgBasDTO);
            dupProdXmlList.addAll(tmpProdXmlList);
        }

        List<RateAdsvcCtgBasDTO> prodXmlList = rateAdsvcGdncService.deduplicateProdList(dupProdXmlList); // 중복 상품 제거

        model.addAttribute("subTabId", rateAdsvcCtgBasDTO.getSubTabId());
        model.addAttribute("prodXmlList", prodXmlList);

        return returnUrl;
    }

    /**
     * 설명 : 부가서비스 상세 xml 정보 조회(btnYn : Y = 소개페이지용 / N = 개통페이지용)
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/adsvcContent","/m/rate/adsvcContent"})
    public String adsvcGdncViewAjax(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/adsvcContent";
        }else {
            returnUrl =  "/portal/rate/adsvcContent";
        }

        List<RateAdsvcGdncDtlXML> contentXmlList = rateAdsvcGdncService.getRateAdsvcGdncDtlXmlList(rateAdsvcCtgBasDTO);
         //2023.08.09-wooki 추가 - 해지안내 null일때 안나오도록
        if(null != contentXmlList) {
            for(int i=0; i<contentXmlList.size(); i++) {
                if(StringUtil.isNotBlank((contentXmlList.get(i).getRateAdsvcItemCd()))) {
                    if("ADDSV109".equals(contentXmlList.get(i).getRateAdsvcItemCd()) && StringUtil.isBlank(contentXmlList.get(i).getRateAdsvcItemSbst())) {
                        contentXmlList.remove(i);
                    }
                }
            }
        }
        RateAdsvcCtgBasDTO prodInfo = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasDTO);
        RateAdsvcGdncProdRelXML prodRel = rateAdsvcGdncService.getRateAdsvcGdncProdRel(rateAdsvcCtgBasDTO);

        String btnYn = StringUtil.NVL(rateAdsvcCtgBasDTO.getBtnYn(), "N");

        model.addAttribute("contentXmlList", contentXmlList);
        model.addAttribute("prodInfo", prodInfo);
        model.addAttribute("prodRel", prodRel);
        model.addAttribute("btnYn", btnYn);

        return returnUrl;
    }

    /**
     * 부가서비스 신청 전 정회원 체크
     * @author : 김동혁
     * @Date : 2023.06.16
     * @return Map
     */
    @RequestMapping(value = {"/rate/checkUser.do","/m/rate/checkUser.do"})
    @ResponseBody
    public Map<String, Object> getCheckUser(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        boolean isLogin = false;
        String userDivision = "00";

        //로그인 체크
        if(userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
            isLogin = true;
        }

        //정회원 체크
        if(userSession != null) {
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
            if(chk){
                userDivision = StringUtil.NVL(userSession.getUserDivision(),"00");
            }

            //미성년자 체크
            int userAge= 0;
            boolean underAge = false;
            if(userSession.getBirthday() != null){
                userAge= NmcpServiceUtils.getBirthDateToAmericanAge(userSession.getBirthday(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
                if(userAge < 19) {
                    underAge = true;
                }
            }
            rtnMap.put("underAge", underAge);
        }

        rtnMap.put("isLogin", isLogin);
        rtnMap.put("userDivision", userDivision);

        return rtnMap;
    }

    /**
     * 부가서비스 신청 POPUP
     * @author : 김동혁
     * @Date : 2023.06.14
     * @param request
     * @param model
     * @param searchVO
     * @param rateAdsvcCd
     * @param baseVatAmt
     * @param rateAdsvcNm
     * @param flag
     * @return
     * @throws ParseException
     */

    @RequestMapping(value = { "/rate/adsvcJoinPop.do", "/m/rate/adsvcJoinPop.do" })
    public String adsvcJoinPop(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            @RequestParam(value = "rateAdsvcGdncSeq", required = true) int rateAdsvcGdncSeq) throws ParseException {

        String returnUrl = "";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

        String rateAdsvcCd = "";
        String rateAdsvcNm = "";
        String baseVatAmt = "";

        RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
        rateAdsvcCtgBasDTO.setRateAdsvcGdncSeq(rateAdsvcGdncSeq);

        RateAdsvcGdncProdRelXML prodRel = rateAdsvcGdncService.getRateAdsvcGdncProdRel(rateAdsvcCtgBasDTO);
        RateAdsvcCtgBasDTO prodInfo = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasDTO);

        rateAdsvcCd = prodRel.getRateAdsvcCd();
        rateAdsvcNm = prodRel.getRateAdsvcNm();
        baseVatAmt = prodInfo.getMmBasAmtVatDesc();

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/rate/adsvcJoinPop";
            if (Constants.REG_SVC_CD_1.equals(rateAdsvcCd)) { // 불법 TM수신차단
                returnUrl = "/mobile/rate/adsvcJoinTmPop";
            } else if (Constants.REG_SVC_CD_2.equals(rateAdsvcCd)) { // 번호도용차단서비스
                returnUrl = "/mobile/rate/adsvcJoinStealNumberPop";
            } else if (Constants.REG_SVC_CD_3.equals(rateAdsvcCd)) { // 로밍 하루종일 ON 부가서비스
                returnUrl = "/mobile/mypage/addSvcRoamingPop";
            } else if (Constants.REG_SVC_CD_4.equals(rateAdsvcCd)) { // 포인트할인
                returnUrl = "/mobile/mypage/pointAddSvcPop";
            }
        } else {
            returnUrl = "/portal/rate/adsvcJoinPop";
            if (Constants.REG_SVC_CD_1.equals(rateAdsvcCd)) { // 불법 TM수신차단
                returnUrl = "/portal/rate/adsvcJoinTmPop";
            } else if (Constants.REG_SVC_CD_2.equals(rateAdsvcCd)) { // 번호도용차단서비스
                returnUrl = "/portal/rate/adsvcJoinStealNumberPop";
            } else if (Constants.REG_SVC_CD_3.equals(rateAdsvcCd)) { // 로밍 하루종일 ON 부가서비스
                returnUrl = "/portal/mypage/addSvcRoamingPop";
            } else if (Constants.REG_SVC_CD_4.equals(rateAdsvcCd)) { // 포인트할인
                returnUrl = "/portal/mypage/pointAddSvcPop";
            }
        }

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String stDt = DateTimeUtil.addMonths(DateTimeUtil.getShortDateString(),2,"yyyyMMdd");
        String etDt = DateTimeUtil.addMonths(DateTimeUtil.getShortDateString(),6,"yyyyMMdd");
        String nowData = DateTimeUtil.getDateString().replaceAll("-", "");

        CustPointDto custPoint = new CustPointDto();

        // 포인트조회
        if (Constants.REG_SVC_CD_4.equals(rateAdsvcCd)) {
            custPoint = myBenefitService.selectCustPoint(searchVO.getNcn());
        }

        model.addAttribute("stDt", stDt);
        model.addAttribute("etDt", etDt);
        model.addAttribute("nowData", nowData);
        model.addAttribute("custPoint", custPoint);
        model.addAttribute("rateAdsvcCd", rateAdsvcCd);
        model.addAttribute("rateAdsvcNm", rateAdsvcNm);
        model.addAttribute("baseVatAmt", baseVatAmt);

        return returnUrl;
    }

    /**
     * 회선번호로 계약번호 조회
     * @param
     * @return Map<String, String>
     */
    @RequestMapping(value = { "/rate/getNcnbyCtnAjax.do", "/m/rate/getNcnbyCtnAjax.do" } )
    @ResponseBody
    public Map<String, String> getNcnbyCtnAjax(@RequestParam HashMap<String, String> paraMap) {

        Map<String, String> rtnMap = new HashMap<String, String>();
        boolean chkCntr = false;
        String contractNum = "";
        String ctn = StringUtil.NVL(paraMap.get("ctn"), "");
        String lineType = StringUtil.NVL(paraMap.get("lineType"), "");
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        try {
            //로그인 체크
            if(null == userSession || StringUtils.isEmpty(userSession.getUserId())) {
                rtnMap.put("rtnCd", "0010");
                rtnMap.put("rtnMsg", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
                return rtnMap;
            }

            //회원의 회선 목록 조회
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            if(cntrList == null || cntrList.isEmpty()) {
                rtnMap.put("rtnCd", "0011");
                rtnMap.put("rtnMsg", "회원 정보가 없습니다.<br>정회원 인증 후 이용해 주세요.");
                return rtnMap;
            }

            //가입할 회선과 조회한 회선 일치하는지 체크
            for(McpUserCntrMngDto cntr : cntrList) {
                if(ctn.equals(cntr.getCntrMobileNo())) {
                    chkCntr = true;
                }
            }
            if(!chkCntr) {
                switch(lineType) {
                    case "G":
                        rtnMap.put("rtnCd", "0012");
                        rtnMap.put("rtnMsg", "신청 회선 정보가 일치하지 않습니다.<br>휴대폰 번호 확인 후 다시 시도 바랍니다.");
                        return rtnMap;
                    case "M":
                        rtnMap.put("rtnCd", "0013");
                        rtnMap.put("rtnMsg", "대표 회선 정보가 일치하지 않습니다.<br>휴대폰 번호 확인 후 다시 시도 바랍니다.");
                        return rtnMap;
                    case "S":
                        rtnMap.put("rtnCd", "0014");
                        rtnMap.put("rtnMsg", "서브 회선 정보가 일치하지 않습니다.<br>휴대폰 번호 확인 후 다시 시도 바랍니다.");
                        return rtnMap;
                    default:
                        rtnMap.put("rtnCd", "0015");
                        rtnMap.put("rtnMsg", "회선 정보가 일치하지 않습니다.<br>휴대폰 번호 확인 후 다시 시도 바랍니다.");
                        return rtnMap;
                }

            }

            //가입할 회선의 계약번호 조회
            McpUserCertDto userCertDto = new McpUserCertDto();
            userCertDto.setMobileNo(ctn);
            List<McpUserCertDto> cntrInfoAList = rateAdsvcGdncService.getMcpUserCntrInfoA(userCertDto) ;

            if(cntrInfoAList == null || cntrInfoAList.isEmpty()) {
                rtnMap.put("rtnCd", "0016");
                rtnMap.put("rtnMsg", "회원 정보를 조회할 수 없습니다.<br>이 메시지가 계속되면 고객센터 문의 바랍니다.");
                return rtnMap;
            }

            if("".equals(cntrInfoAList.get(0).getContractNum()) || cntrInfoAList.get(0).getContractNum() == null ) {
                rtnMap.put("rtnCd", "0017");
                rtnMap.put("rtnMsg", "회원 정보를 조회할 수 없습니다.<br>이 메시지가 계속되면 고객센터 문의 바랍니다.");
                return rtnMap;
            }

            contractNum = cntrInfoAList.get(0).getContractNum();

        } catch(RestClientException e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        rtnMap.put("rtnCd", "0000");
        rtnMap.put("rtnMsg", "");
        rtnMap.put("contractNum", contractNum);

        return rtnMap;
    }

    /**
     * 부가서비스 신청 AJAX
     * @author 김동혁
     * @Date : 2023.06.15
     * @param request
     * @param model
     * @param searchVO
     * @param soc
     * @param ftrNewParam
     * @param ctnVal
     * @param couoponPrice
     * @param flag
     * @return
     * @throws SocketTimeoutException
     */

    @RequestMapping(value = { "/rate/adsvcJoinAjax.do", "/m/rate/adsvcJoinAjax.do" })
    @ResponseBody
    public JsonReturnDto  regSvcChgAjax(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO, String soc, String ftrNewParam, String ctnVal
            ,String couoponPrice
            ,String flag) throws SocketTimeoutException {

        String resultCode = "99";
        String message = "";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }

        String ip = request.getRemoteAddr();

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
             throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION,url);
        }

        JsonReturnDto jsonReturnDto = new JsonReturnDto();

        try {
            //부가서비스 신청
            regSvcService.regSvcChg(searchVO.getNcn(),searchVO.getCtn(), searchVO.getCustId(),soc, ftrNewParam);
            resultCode = "00";
            message = "";

            if (Constants.REG_SVC_CD_4.equals(soc)) {
                if(couoponPrice != null && !"".equals(couoponPrice)) {
                    CustPointDto custPoint = myBenefitService.selectCustPoint(searchVO.getNcn());
                    if(custPoint != null) {
                        CustPointTxnDto custPointTxnDto = new CustPointTxnDto();
                        int strCouoponPrice = Integer.parseInt(couoponPrice);
                        custPointTxnDto.setSvcCntrNo(searchVO.getNcn()); //cntrNo
                        custPointTxnDto.setPoint(strCouoponPrice); // 사용포인트
                        custPointTxnDto.setPointTrtCd(POINT_TRT_USE); // 사용사유코드
                        custPointTxnDto.setPointTxnDtlRsnDesc("부가서비스 포인트 사용(요금할인)");		// 사용 설명
                        custPointTxnDto.setPointTxnRsnCd(POINT_RSN_CD_U62); // 사용사유
                        custPointTxnDto.setRemainPoint(custPoint.getTotRemainPoint() - strCouoponPrice); // 잔액
                        custPointTxnDto.setPointDivCd("MP"); // 포인트종류
                        custPointTxnDto.setCretId(userSession.getUserId());
                        custPointTxnDto.setCretIp(ip);
                        custPointTxnDto.setPointCustSeq(custPoint.getPointCustSeq());
                        pointService.editPoint(custPointTxnDto);
                        resultCode = "00";
                        message = "";
                    }
                }

            }
        } catch (SelfServiceException e) {
             resultCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
             message = getErrMsg(e.getMessage());
        }  catch (SocketTimeoutException e){
            resultCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
        }

        jsonReturnDto.setReturnCode(resultCode);
        jsonReturnDto.setMessage(message);
        return jsonReturnDto;

    }

    /**
     * 설명 : 사용후기 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param pageInfoBean
     * @param requestReviewDto
     * @return
     */
    @RequestMapping(value = {"/rate/requestReviewGetPagingAjax.do","/m/rate/requestReviewGetPagingAjax.do"})
    @ResponseBody
    public Map<String, Object> requestReviewGetPagingAjax(HttpServletRequest request, HttpServletResponse response
            , PageInfoBean pageInfoBean
            ,RequestReviewDto requestReviewDto) {

        HashMap<String,Object> map = new HashMap<String, Object>();

        //현재 페이지 번호 초기화
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        //한페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(10);

        //카운터 조회
        int total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto);
        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        List<RequestReviewDto> requestReviewDtoList = requestReviewService.selectUsimRequestReviewList(requestReviewDto,skipResult,maxResult);
        if(requestReviewDtoList !=null && !requestReviewDtoList.isEmpty()){
            for(RequestReviewDto dto : requestReviewDtoList){
                dto.setSysRdateDd(DateTimeUtil.changeFormat(dto.getSysRdate(),"yyyy.MM.dd"));
                dto.setMkRegNm(dto.getRegNm());
                dto.setRegNm("");
            }
        }

        map.put("listCount", requestReviewDtoList.size());
        map.put("requestReviewDtoList", requestReviewDtoList);

        return map;
    }



    /**
     * 설명 : 알면유용한 부가서비스
     * @Author : 김일환
     * @Date : 2022.11.02
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/adsvcUsefulInfo.do","/m/rate/adsvcUsefulInfo.do"})
    public String usAdsvcGdncList(Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/adsvcUsefulInfo";
        }else {
            returnUrl =  "/portal/rate/adsvcUsefulInfo";
        }

        return returnUrl;
    }


    /**
     * 설명 : html 태그 삭제처리
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param html
     * @return
     */
    public String removeHtmlTag(String html) {
        return html.replaceAll("<[^>]*>", " ");
    }


    /**
     * 설명 : 해외로밍 페이지 이동
     * @Date : 2023.06.07
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/roamingList.do","/m/rate/roamingList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String roaming(HttpServletRequest request, HttpServletResponse response, Model model,@ModelAttribute RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/roamingList";
        }else {
            returnUrl =  "/portal/rate/roamingList";
        }

        String rateAdsvcCtgCd = rateAdsvcCtgBasDTO.getRateAdsvcCtgCd();
        int rateAdsvcGdncSeq = rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq();

        model.addAttribute("paramRateAdsvcGdncSeq", rateAdsvcGdncSeq);
        model.addAttribute("paramRateAdsvcCtgCd", rateAdsvcCtgCd);
        return returnUrl;
    }


    /**
     * @Description : 해외로밍 서비스 신청 팝업
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/rate/roamingPop.do", "/m/rate/roamingPop.do"})
    public String joinPop(HttpServletRequest request, Model model,@ModelAttribute RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {

        //모바일 여부 확인
        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/roamingPop";
        }else {
            returnUrl =  "/portal/rate/roamingPop";
        }

        //팝업에 띄울 해외로밍 상품 정보 조회
        RateAdsvcCtgBasDTO joinPop = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasDTO);
        RateAdsvcGdncProdRelXML prodRel = rateAdsvcGdncService.getRateAdsvcGdncProdRel(rateAdsvcCtgBasDTO);

        model.addAttribute("joinPop",joinPop);
        model.addAttribute("prodRel", prodRel);

        return returnUrl;
    }


    /**
     * 설명 : 해외로밍 xml 정보 조회
     * @Date : 2023.06.08
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/rate/roamingListAjax.do","/m/rate/roamingListAjax.do"})
    @ResponseBody
    public MapWrapper roamingListAjax(HttpServletRequest request, HttpServletResponse response) {
        MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper();
        return mapWrapper;
    }




    /**
     * 설명 : 해외로밍 전체 상품목록 조회
     * @Date : 2023.06.09
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/roamingAllListPanel.do","/m/rate/roamingAllListPanel.do"}, method = RequestMethod.GET)
    public String roamingAllListPanel(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/roamingListPanel";
        }else {
            returnUrl =  "/portal/rate/roamingListPanel";
        }

        String rateAdsvcCtgCd = "";

        MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper();

        Map<String, ListXmlWrapper> ctgMapXml = mapWrapper.getItem();
        ListXmlWrapper ctgListXml = ctgMapXml.get("ctgList");
        List<RateAdsvcGdncProdXML> ctgList = ctgListXml.getItem();

        for(RateAdsvcGdncProdXML item : ctgList) {
            try {
                if(item.getDepthKey() == 1 && "Y".equals(item.getUseYn())
                        && "137".equals(item.getRateAdsvcCtgCd())
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                ) {
                    rateAdsvcCtgCd = item.getRateAdsvcCtgCd();
                }
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        List<RateAdsvcCtgBasDTO> dupProdXmlList = new ArrayList<RateAdsvcCtgBasDTO>();

        rateAdsvcCtgBasDTO.setRateAdsvcCtgCd(rateAdsvcCtgCd);
        rateAdsvcCtgBasDTO.setRateAdsvcDivCd("ADDSV");
        rateAdsvcCtgBasDTO.setDepthKey(2);
        rateAdsvcCtgBasDTO.setUpRateAdsvcCtgCd("137");


        List<RateAdsvcCtgBasDTO> buttonXmlList = rateAdsvcGdncService.getCtgXmlList(rateAdsvcCtgBasDTO, mapWrapper);

        for(RateAdsvcCtgBasDTO rateAdsvcCtgBas : buttonXmlList) {
            rateAdsvcCtgBas.setSelfYn(rateAdsvcCtgBasDTO.getSelfYn());
            List<RateAdsvcCtgBasDTO> tempProdXmlList = rateAdsvcGdncService.getProdXmlList(rateAdsvcCtgBas, mapWrapper);
            dupProdXmlList.addAll(tempProdXmlList);
        }


        List<RateAdsvcCtgBasDTO> prodXmlList = new ArrayList<RateAdsvcCtgBasDTO>();


        prodXmlList = rateAdsvcGdncService.deduplicateProdList(dupProdXmlList);

        model.addAttribute("subTabId", rateAdsvcCtgBasDTO.getSubTabId());
        model.addAttribute("prodXmlList", prodXmlList);

        return returnUrl;
    }




    /**
     * 설명 : 해외로밍 상품목록 조회
     * @Date : 2023.06.09
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/roamingListPanel.do","/m/rate/roamingListPanel.do"}, method = RequestMethod.GET)
    public String roamingListPanel(HttpServletRequest request, HttpServletResponse response, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/roamingListPanel";
        }else {
            returnUrl = "/portal/rate/roamingListPanel";
        }

        //값이 두개일 경우가 있으니까 받는 것을 dto인 list형태의 roamingList에다가 받아주고 for문으로 돌려서
        List<String> ctgCdList = new ArrayList<String>();
        ctgCdList = rateAdsvcCtgBasDTO.getCtgCdList();

        List<RateAdsvcCtgBasDTO> dupProdXmlList = new ArrayList<RateAdsvcCtgBasDTO>();
        MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper();

        for(String ctgCd : ctgCdList) {
            RateAdsvcCtgBasDTO tmpRateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
            tmpRateAdsvcCtgBasDTO.setRateAdsvcCtgCd(ctgCd);
            tmpRateAdsvcCtgBasDTO.setSelfYn(rateAdsvcCtgBasDTO.getSelfYn());
            List<RateAdsvcCtgBasDTO> tmpProdXmlList = new ArrayList<RateAdsvcCtgBasDTO>();

            tmpProdXmlList = rateAdsvcGdncService.getProdXmlList(tmpRateAdsvcCtgBasDTO, mapWrapper);
            dupProdXmlList.addAll(tmpProdXmlList);
        }

        List<RateAdsvcCtgBasDTO> prodXmlList = rateAdsvcGdncService.deduplicateProdList(dupProdXmlList);

        model.addAttribute("prodXmlList", prodXmlList);

        return returnUrl;
    }


    /**
     * 설명 : 해외로밍 상세 xml 정보 조회
     * @Date : 2023.06.09
     * @param rateAdsvcCtgBasDTO
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rate/roamingContent.do","/m/rate/roamingContent.do"})
    public String roamingViewAjax(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, Model model) {

        String returnUrl = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/rate/roamingContent";
        }else {
            returnUrl =  "/portal/rate/roamingContent";
        }

        List<RateAdsvcGdncDtlXML> contentXmlList = rateAdsvcGdncService.getRateAdsvcGdncDtlXmlList(rateAdsvcCtgBasDTO);
        //2023.08.09-wooki 추가 - 해지안내 null일때 안나오도록
        if(null != contentXmlList) {
            for(int i=0; i<contentXmlList.size(); i++) {
                if(StringUtil.isNotBlank((contentXmlList.get(i).getRateAdsvcItemCd()))) {
                    if("ADDSV109".equals(contentXmlList.get(i).getRateAdsvcItemCd()) && StringUtil.isBlank(contentXmlList.get(i).getRateAdsvcItemSbst())) {
                        contentXmlList.remove(i);
                    }
                }
            }
        }
        RateAdsvcCtgBasDTO prodInfo = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasDTO);
        RateAdsvcGdncProdRelXML prodRel = rateAdsvcGdncService.getRateAdsvcGdncProdRel(rateAdsvcCtgBasDTO);

        model.addAttribute("contentXmlList", contentXmlList);
        model.addAttribute("prodInfo", prodInfo);
        model.addAttribute("prodRel", prodRel);

        return returnUrl;
    }

    private ResponseSuccessDto getMessageBox() {
        ResponseSuccessDto mbox = new ResponseSuccessDto();

        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }
        mbox.setRedirectUrl(url);
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
        return mbox;
    }

    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
        return result;
    }

    private String getErrMsg(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        if(arg.length > 1) {
            result = arg[1];
        } else {
            result = arg[0];
        }
        return result;
    }


    /**
     * 해외로밍 신청 AJAX
     * @Date : 2023.06.19
     * @param request
     * @param model
     * @param searchVO
     * @param soc
     * @return
     * @throws SocketTimeoutException
     */

     @RequestMapping(value = { "/rate/roamingJoinAjax.do", "/m/rate/roamingJoinAjax.do" })
     @ResponseBody
     public JsonReturnDto roamingJoinAjax(HttpServletRequest request, ModelMap model,
                                         @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                         @ModelAttribute("rateAdsvcCtgBasDTO") RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO,
                                         String soc, String strtDt , String strtTm, String endDt,String[] addPhone
                                        , String flag, String prodHstSeq) throws SocketTimeoutException, ParseException {

         JsonReturnDto jsonReturnDto = new JsonReturnDto();
         Map<String, Object> rtnMap = new HashMap<String, Object>();

          String resultCode = "99";
          String message = "";

         String url = "/mypage/updateForm.do";
         if ("Y".equals(NmcpServiceUtils.isMobile())) {
             url = "/m/mypage/updateForm.do";
         }

         // 1. 로그인 체크
         UserSessionDto userSession = SessionUtils.getUserCookieBean();
         if (userSession == null || StringUtil.isEmpty(userSession.getUserId())) {
             throw new McpCommonJsonException("01", NO_FRONT_SESSION_EXCEPTION);
         }

         // 2. 정회원 체크
         List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
         boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
         if (!chk) {
             throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION, url);
         }

         // 3. 계약번호 존재 여부
         String ncn = searchVO.getNcn();
         Map<String, String> ncnObj = mypageUserService.selectContractObj("", "", ncn);
         if (MapUtils.isEmpty(ncnObj)) {
             throw new McpCommonJsonException("03", "회원 정보를 조회할 수 없습니다.<br><br>이 메시지가 계속되면 고객센터 문의 바랍니다.");
         }

         //시퀀스값으로 해당 상품 조회
         RateAdsvcCtgBasDTO prod = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasDTO);

         // 4. 로밍 가입 신청 validation check
         jsonReturnDto = rateAdsvcGdncService.regRoamValidationChk(prod, soc, strtDt, strtTm, endDt, addPhone);
         if(!"00".equals(jsonReturnDto.getReturnCode())) {
             return jsonReturnDto;
         }

         // 5. 서브 상품 가입시
         // 5-1. 대표상품 유효한지 check
         // 5-2. 대표상품 일련번호 get
         String mtProdHstSeq = "";
         if("S".equals(prod.getLineType())) {
             String mtCd = prod.getMtCd();
             mtProdHstSeq = rateAdsvcGdncService.getMtProdHstSeq(mtCd, strtDt, endDt, addPhone[0], ncn);

             if("".equals(mtProdHstSeq) || mtProdHstSeq == null) {
                 throw new McpCommonJsonException("04", "입력한 번호로 정보를 조회할 수 없습니다.<br><br>대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.");
             }
         }

         // 6. 로밍 가입에 필요한 param 생성
         String ftrNewParam = rateAdsvcGdncService.getRoamParam(prod, soc, strtDt, strtTm, endDt, addPhone, mtProdHstSeq);

         try {
             if("Y".equals(flag)) { //변경인 경우
                 //로밍 해지
                 rtnMap = regSvcService.moscRegSvcCanChgSeq(searchVO, soc, prodHstSeq);

                 if("S".equals(rtnMap.get("RESULT_CODE"))) {
                     //해외 로밍 신청
                     regSvcService.regSvcChg(searchVO.getNcn(),searchVO.getCtn(), searchVO.getCustId(),soc, ftrNewParam);
                     resultCode = "00";
                     message = "";
                 } else {
                     resultCode = (String) rtnMap.get("RESULT_CODE");
                     message = (String) rtnMap.get("message");
                 }
             } else {
                 //해외로밍 신청
                 regSvcService.regSvcChg(searchVO.getNcn(),searchVO.getCtn(), searchVO.getCustId(),soc, ftrNewParam);
                 resultCode = "00";
                 message = "";
             }

         } catch (SelfServiceException e) {
             resultCode = getErrCd(e.getResultCode());//N:성공, S:시스템에서, E:Biz 에러
             message = getErrMsg(e.getMessage());
         }  catch (SocketTimeoutException e){
             resultCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
             message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
         }

         jsonReturnDto.setReturnCode(resultCode);
         jsonReturnDto.setMessage(message);
         return jsonReturnDto;

     }


    /**
     * 설명 : 전제 카테고리 xml 정보 조회
     * @Author : papier
     * @Date : 2025.01.10
     */
    @RequestMapping(value = "/rate/getRateDtlInfoAjax.do")
    @ResponseBody
    public RateDtlInfo getRateDtlInfo(@RequestParam(value = "rateAdsvcCd", required = true) String rateAdsvcCd
                                     ,@RequestParam(value = "adminEventDate", required = false, defaultValue = "") String adminEventDate) {

        RateDtlInfo rateDtlInfo = new RateDtlInfo();

        // adminEventDate 값이 있으면 해당 날짜 기준으로 요금제 정보가 동적 생성되어야 함 (관리자만 진입 가능)
        if(!StringUtil.isEmpty(adminEventDate)){

          UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
          String userId = (userSessionDto == null) ? "" : userSessionDto.getUserId();

          NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("EventPageTestUsers", userId);
          if(nmcpCdDtlDto == null) {
            return rateDtlInfo; // 빈 객체 리턴
          }

          rateDtlInfo = rateAdsvcGdncService.getRateDtlInfoWithDate(rateAdsvcCd, adminEventDate);

        }else{
          rateDtlInfo = rateAdsvcGdncService.getRateDtlInfo(rateAdsvcCd);
        }

        return rateDtlInfo;
    }


    /**
     * 설명 : 전제 카테고리 xml 정보 조회
     * @Author : papier
     * @Date : 2025.01.23
     * @return
     */
    @RequestMapping(value = "/rate/getCtgRateAllListAjax.do")
    @ResponseBody
    public List<RateAdsvcGdncProdXML> getCtgRateAllList() {
        //공통 코드 조회
        List<NmcpCdDtlDto> rateCompCodeList = Optional.ofNullable(NmcpServiceUtils.getCodeList("rateCompCodeList"))
                .orElse(Collections.emptyList());
        Map<String, NmcpCdDtlDto> rateCompCodeListMap = rateCompCodeList.stream()
                .collect(Collectors.toMap(
                        NmcpCdDtlDto::getDtlCd,
                        Function.identity(),
                        (existing, replacement) -> replacement // 기존 값 덮어쓰기
                ));

        //전체 요금제 CTG 정보
        MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper();
        List<RateAdsvcGdncProdXML> ctgXmlList = Optional.ofNullable(rateAdsvcGdncService.getCtgRateAllList(mapWrapper))
                .orElse(Collections.emptyList());

        //공통 코드에 매칭되는 상위 부모 CTG에 대해 추출
        List<RateGiftPrmtXML> rateGiftPrmtXmlList = rateAdsvcGdncService.getRateGiftPrmtXmlList();
        List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelXml = rateAdsvcGdncService.getRateAdsvcGdncProdRelXml();
        List<RateAdsvcGdncProdXML> rtnCtgList = ctgXmlList.stream()
                .filter(item -> Objects.nonNull(rateCompCodeListMap.get(item.getUpRateAdsvcCtgCd())))
                .peek(rateCtgInfo -> {
                    //logger.info("getRateDtlInfoList - rateDtlList.size(): {}", rateDtlList.size());
                    Optional.ofNullable(rateAdsvcGdncService.getRateDtlInfoList(rateCtgInfo.getRateAdsvcCtgCd(), mapWrapper, rateGiftPrmtXmlList, rateAdsvcGdncProdRelXml))
                            .filter(list -> !list.isEmpty()) // 빈 리스트 방지
                            .ifPresent(rateCtgInfo::setRateDtlList);
                }).sorted(Comparator.comparing(ctgObj -> {
                    try {
                        return Integer.parseInt(ctgObj.getSortOdrg());
                    } catch (NumberFormatException e) {
                        return Integer.MAX_VALUE; // 숫자가 아닌 값은 가장 마지막으로 정렬
                    }
                })).collect(Collectors.toList());

        //people.sort(Comparator.comparing(person -> person.age));
        // 공통 코드에 매칭되는 CTG 정보 추출
        return rtnCtgList;
    }


    /**
     * 설명 : 전제 카테고리 xml 정보 조회
     * @Author : papier
     * @Date : 2025.11.25
     * @return
     */
    @RequestMapping(value = "/rate/getCtgRateAllListDataAjax.do")
    @ResponseBody
    public List<RateAdsvcGdncProdXML> getCtgRateAllListData() {
        //공통 코드 조회
        List<NmcpCdDtlDto> rateCompCodeList = Optional.ofNullable(NmcpServiceUtils.getCodeList("rateCompCodeList"))
                .orElse(new ArrayList<>());  // 빈 리스트면 수정 가능한 리스트로 반환

        NmcpCdDtlDto newCode = new NmcpCdDtlDto();
        newCode.setDtlCd("6");
        newCode.setDtlCdNm("LTE 휴대폰 요금제");
        rateCompCodeList.add(newCode);
        NmcpCdDtlDto newCode2 = new NmcpCdDtlDto();
        newCode2.setDtlCd("8");
        newCode2.setDtlCdNm("5G 휴대폰 요금제");
        rateCompCodeList.add(newCode2);

        Map<String, NmcpCdDtlDto> rateCompCodeListMap = rateCompCodeList.stream()
                .collect(Collectors.toMap(
                        NmcpCdDtlDto::getDtlCd,
                        Function.identity(),
                        (existing, replacement) -> replacement // 기존 값 덮어쓰기
                ));

        //전체 요금제 CTG 정보
        MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper();
        List<RateAdsvcGdncProdXML> ctgXmlList = Optional.ofNullable(rateAdsvcGdncService.getCtgRateAllList(mapWrapper))
                .orElse(Collections.emptyList());

        //공통 코드에 매칭되는 상위 부모 CTG에 대해 추출
        List<RateGiftPrmtXML> rateGiftPrmtXmlList = rateAdsvcGdncService.getRateGiftPrmtXmlList();
        List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelXml = rateAdsvcGdncService.getRateAdsvcGdncProdRelXml();
        List<RateAdsvcGdncProdXML> rtnCtgList = ctgXmlList.stream()
                .filter(item -> Objects.nonNull(rateCompCodeListMap.get(item.getUpRateAdsvcCtgCd())))
                .peek(rateCtgInfo -> {
                    //logger.info("getRateDtlInfoList - rateDtlList.size(): {}", rateDtlList.size());
                    Optional.ofNullable(rateAdsvcGdncService.getRateDtlInfoList(rateCtgInfo.getRateAdsvcCtgCd(), mapWrapper, rateGiftPrmtXmlList, rateAdsvcGdncProdRelXml, true))
                            .filter(list -> !list.isEmpty()) // 빈 리스트 방지
                            .ifPresent(rateCtgInfo::setRateDtlList);
                }).sorted(Comparator.comparing(ctgObj -> {
                    try {
                        return Integer.parseInt(ctgObj.getSortOdrg());
                    } catch (NumberFormatException e) {
                        return Integer.MAX_VALUE; // 숫자가 아닌 값은 가장 마지막으로 정렬
                    }
                })).collect(Collectors.toList());

        //people.sort(Comparator.comparing(person -> person.age));
        // 공통 코드에 매칭되는 CTG 정보 추출
        return rtnCtgList;
    }

    @RequestMapping(value = "/rate/getRateAmountListAjax.do")
    @ResponseBody
    public List<RateAdsvcCtgBasDTO> getRateAmountList() {
        return rateAdsvcGdncService.getRateAmountList();
    }
}
