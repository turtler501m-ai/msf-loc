package com.ktmmobile.mcp.main.controller;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.board.service.BoardCommonSvc;
import com.ktmmobile.mcp.common.dto.BannerDto;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.PopupDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.mspservice.MspService;
import com.ktmmobile.mcp.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrRelDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgRelDTO;
import com.ktmmobile.mcp.main.service.MainService;
import com.ktmmobile.mcp.mypage.service.FarPricePlanService;
import com.ktmmobile.mcp.phone.constant.PhoneConstant;
import com.ktmmobile.mcp.phone.dto.CommonSearchDto;
import com.ktmmobile.mcp.phone.dto.PhoneMspDto;
import com.ktmmobile.mcp.phone.dto.PhoneProdBasDto;
import com.ktmmobile.mcp.phone.dto.PhoneSntyBasDto;
import com.ktmmobile.mcp.phone.service.PhoneService;
import com.ktmmobile.mcp.rate.dto.RateAdsvcBnfitGdncDtlXML;
import com.ktmmobile.mcp.rate.dto.RateAdsvcCtgBasDTO;
import com.ktmmobile.mcp.rate.dto.RateAdsvcGdncBasXML;
import com.ktmmobile.mcp.rate.service.RateAdsvcGdncService;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;
import com.ktmmobile.mcp.requestReview.service.RequestReviewService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.*;

@Controller
public class MainIntroController {

    private static final Logger logger = LoggerFactory.getLogger(MainIntroController.class);

    @Autowired
    MainService mainService;

    @Autowired
    RateAdsvcGdncService rateAdsvcGdncService;

    @Autowired
    RequestReviewService requestReviewService;

    @Autowired
    BoardCommonSvc boardCommonSvc;

    @Autowired
    MspService mspService;

    @Autowired
    PhoneService phoneService;

    @Autowired
    FarPricePlanService farPricePlanService;

    /** 조직 코드 */
    @Value("${sale.orgnId}")
    private String orgnId;

    /**
     * 설명 : 메인 페이지 이동
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = { "/main.do", "/m/main.do" })
    public String mainController(HttpServletRequest request, Model model) {

        String returnUrl = "";

        if ("M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/main/main";
        } else if ("A".equals(NmcpServiceUtils.getPlatFormCd())) {

            HttpSession session = request.getSession(true);
            String isAppIsFirst = StringUtil.NVL((String) session.getAttribute("isAppIsFirst"), "");
            if ("".equals(isAppIsFirst)) { // 처음
                session.setAttribute("isAppIsFirst", "Y");
                UserSessionDto userSession = SessionUtils.getUserCookieBean();
                if (userSession != null) {

                    String userDivision = StringUtil.NVL(userSession.getUserDivision(), "00");
                    if ("01".equals(userDivision)) {
                        return "redirect:/m/mypage/myinfoView.do";
                    } else {
                        return "/mobile/main/main";
                    }
                } else {
//        			return "redirect:/m/loginForm.do?uri=/m/mypage/myinfoView.do";
                    return "redirect:/m/loginForm.do?uri=first";
                }
            } else { // 기존로직
                returnUrl = "/mobile/main/main";
            }
        } else {
            returnUrl = "/portal/main/main";
        }

        model.addAttribute("isMain", "Y");

        return returnUrl;
    }

    /**
     * 설명 : 배너목록 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param bannCtg
     * @return
     */
    @RequestMapping(value = { "/bannerListAjax.do", "/m/bannerListAjax.do" })
    @ResponseBody
    public JsonReturnDto bannerListAjax(HttpServletRequest request, @RequestParam("bannCtg") String bannCtg) {

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        List<BannerDto> bannerList = NmcpServiceUtils.getBannerList(bannCtg);

        if (bannerList != null) {
            logger.debug("bannerList len :{}", bannerList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("bannerList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(bannerList);

        return result;
    }

    /**
     * 설명 : PC 메인 상단 빅배너 목록 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param bannCtg
     * @return
     */
    @RequestMapping(value = { "/bigBannerListAjax.do" })
    @ResponseBody
    public JsonReturnDto bigBannerListAjax(HttpServletRequest request, @RequestParam("bannCtg") String bannCtg) {

        JsonReturnDto result = new JsonReturnDto();
        Map<String, List<BannerDto>> rtnMap = new HashMap<String, List<BannerDto>>();
        List<BannerDto> bannerApdList = new ArrayList<BannerDto>();
        String returnCode = "00";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        List<BannerDto> bannerList = NmcpServiceUtils.getBannerList(bannCtg); // bannCtg

        if (bannerList != null && bannerList.size() > 0) {

            rtnMap.put("bannerList", bannerList);

            for (BannerDto bannerDto : bannerList) {
                List<BannerDto> apdList = NmcpServiceUtils.getBannerApdList(String.valueOf(bannerDto.getBannSeq()));
                if (apdList != null && !apdList.isEmpty()) {
                    for (int i = 0; i < 1; i++) {
                        BannerDto apdDto = apdList.get(i) != null ? apdList.get(i) : null ;
                        bannerApdList.add(apdDto);
                    }
                }
            }
            rtnMap.put("bannerApdList", bannerApdList);

            logger.debug("bannerList len :{}", bannerList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("bannerList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(rtnMap);

        return result;
    }

    /**
     * 설명 : 팝업 목록 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param popupUrl
     * @return
     */
    @RequestMapping(value = { "/m/popupListAjax.do" })
    @ResponseBody
    public JsonReturnDto popupListAjax(HttpServletRequest request, @RequestParam("popupUrl") String popupUrl) {

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        List<PopupDto> popupList = NmcpServiceUtils.getPopupList(popupUrl);

        if (popupList != null) {
            logger.debug("popupList len :{}", popupList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("popupList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(popupList);

        return result;
    }

    /**
     * 설명 : 사용후기 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param pageInfoBean
     * @param requestReviewDto
     * @return
     */
    @RequestMapping(value = { "/m/requestReviewListAjax.do" })
    @ResponseBody
    public JsonReturnDto requestReviewListAjax(HttpServletRequest request, PageInfoBean pageInfoBean,
            RequestReviewDto requestReviewDto) {

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        // 현재 페이지 번호 초기화
        if (pageInfoBean.getPageNo() == 0) {
            pageInfoBean.setPageNo(1);
        }

        // 한페이지당 보여줄 리스트 수
        //pageInfoBean.setRecordCount(3);

        // 카운터 조회
        int total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto);
        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount(); // Pagesize

        List<RequestReviewDto> requestReviewDtoList = requestReviewService.selectRequestReviewList(requestReviewDto,
                skipResult, maxResult);
        if (requestReviewDtoList != null && !requestReviewDtoList.isEmpty()) {
            for (RequestReviewDto dto : requestReviewDtoList) {
                dto.setSysRdateDd(DateTimeUtil.changeFormat(dto.getSysRdate(), "yyyy.MM.dd"));
                dto.setRegNm(MaskingUtil.getMaskedName(dto.getRegNm()));
            }
            requestReviewDtoList = requestReviewDtoList.stream()
                    .sorted(Comparator.comparing(RequestReviewDto::getPrizeSbst, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
                            .thenComparing(RequestReviewDto::getNtfYn).reversed())
                    .limit(3).collect(Collectors.toList());
        }

        if (requestReviewDtoList != null) {
            logger.debug("workNotiList len :{}", requestReviewDtoList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("workNotiList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(requestReviewDtoList);

        return result;
    }

    /**
     * 설명 : 공지사항 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param pageInfoBean
     * @param boardDto
     * @return
     */
    @RequestMapping(value = { "/m/boardListAjax.do" })
    @ResponseBody
    public JsonReturnDto boardListAjax(HttpServletRequest request, @ModelAttribute PageInfoBean pageInfoBean,
            @ModelAttribute BoardDto boardDto) {

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        if (pageInfoBean.getPageNo() == 0) {
            pageInfoBean.setPageNo(1);
        }
        // 임시 한페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(1);

        // 페이지 카운트
        int totalCount = boardCommonSvc.getTotalCount(boardDto);
        pageInfoBean.setTotalCount(totalCount);

        // RowBound 필수값 세팅
        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount(); // Pagesize

        List<BoardDto> boardList = boardCommonSvc.selectBoardList(boardDto, skipResult, maxResult);
        for (BoardDto boardo : boardList) {
            boardo.setBoardContents(ParseHtmlTagUtil.convertHtmlchars(boardo.getBoardContents()));
        }

        if (boardList != null) {
            logger.debug("popupList len :{}", boardList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("popupList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(boardList);

        return result;
    }

    /**
     * 설명 : 유심요금제 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param nmcpRecommendProdCtgBasDTO
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = { "/m/rateScndList.do" })
    @ResponseBody
    public JsonReturnDto rateScndList(HttpServletRequest request, NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO)
            throws ParseException {
        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        List<NmcpRecommendProdCtgBasDTO> recommendProdCtgScndList = mainService.getRecommendProdCtgScndList(nmcpRecommendProdCtgBasDTO);
        // 상품목록 추가
        for (NmcpRecommendProdCtgBasDTO recommendProdCtgScndDTO : recommendProdCtgScndList) {
            List<NmcpRecommendProdCtgRelDTO> rtnList = new ArrayList<NmcpRecommendProdCtgRelDTO>();
            // 추천 상품 카테고리 매핑 관리 목록 조회
            List<NmcpRecommendProdCtgRelDTO> recommendProdCtgRelList = mainService.getRecommendProdCtgRelList(recommendProdCtgScndDTO);
            // RATE_CD 에 해당하는 상품정보를 xml 에서 읽어 dto.getLists() 추가한다.
            for (NmcpRecommendProdCtgRelDTO recommendProdCtgRelDTO : recommendProdCtgRelList) {

                // Param 추가
                RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
                rateAdsvcCtgBasDTO.setRateAdsvcGdncSeq(recommendProdCtgRelDTO.getRecommendProdGdncSeq());
                // 요금제부가서비스안내기본 조회
                RateAdsvcGdncBasXML gdncBasVo = rateAdsvcGdncService.getRateAdsvcGdncBasXml(rateAdsvcCtgBasDTO);

                if (gdncBasVo != null) {
                    if (DateTimeUtil.isMiddleDateTime(gdncBasVo.getPstngStartDate(), gdncBasVo.getPstngEndDate())) {
                        // 요금제부가서비스혜택안내상세 xml 목록 조회
                        List<RateAdsvcBnfitGdncDtlXML> bnfitList = rateAdsvcGdncService
                                .getRateAdsvcBnfitGdncDtlXmlList(rateAdsvcCtgBasDTO);

                        // 요금제부가서비스안내기본 추가
                        recommendProdCtgRelDTO.setRateAdsvcNm(gdncBasVo.getRateAdsvcNm());
                        recommendProdCtgRelDTO.setRateAdsvcBasDesc(gdncBasVo.getRateAdsvcBasDesc());
                        recommendProdCtgRelDTO.setMmBasAmtDesc(gdncBasVo.getMmBasAmtDesc());
                        recommendProdCtgRelDTO.setMmBasAmtVatDesc(gdncBasVo.getMmBasAmtVatDesc());
                        recommendProdCtgRelDTO.setPromotionAmtDesc(gdncBasVo.getPromotionAmtDesc());
                        recommendProdCtgRelDTO.setPromotionAmtVatDesc(gdncBasVo.getPromotionAmtVatDesc());

                        // 혜택정보 추가
                        if (bnfitList != null) {
                            for (RateAdsvcBnfitGdncDtlXML bnfitDTO : bnfitList) {
                                // 데이터
                                if ("RATEBE01".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setBnfitData(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 음성
                                if ("RATEBE02".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setBnfitVoice(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 문자
                                if ("RATEBE03".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setBnfitSms(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // WiFi
                                if ("RATEBE04".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setBnfitWifi(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 프로모션 데이터
                                if ("RATEBE31".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setPromotionBnfitData(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 프로모션 음성
                                if ("RATEBE32".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setPromotionBnfitVoice(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 프로모션 문자
                                if ("RATEBE33".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setPromotionBnfitSms(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 최대 데이터제공량(데이터(노출문구))
                                if ("RATEBE95".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setMaxDataDelivery(bnfitDTO.getRateAdsvcItemDesc());
                                }
                            }
                        }


                        rtnList.add(recommendProdCtgRelDTO);
                    }
                }
            }
            recommendProdCtgScndDTO.getLists().addAll(rtnList);
        }

        if (recommendProdCtgScndList != null) {
            logger.debug("workNotiList len :{}", recommendProdCtgScndList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("workNotiList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(recommendProdCtgScndList);

        return result;
    }

    /**
     * 설명 : 휴대폰요금제 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param nmcpRecommendProdCtgBasDTO
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = { "/m/rateAdsvcScndList.do" })
    @ResponseBody
    public JsonReturnDto rateAdsvcScndList(HttpServletRequest request, NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO) throws ParseException {
        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        // ========================1.정책정보 조회 시작 ===============================
        // 해당 기관정보로 정책정보를 조회 한다. 정책정보는 온라인직영,핸드폰,단말할인,기관코드를 기준으로 가져온다
        MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
        mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP); // 정책타입 (온라인직영)
        mspSalePlcyMstDto.setPlcySctnCd(PHONE_FOR_MSP); // 정책유형 (핸드폰)
        mspSalePlcyMstDto.setSprtTp(PHONE_DISCOUNT_FOR_MSP); // 할인유형 (단말할인)
        mspSalePlcyMstDto.setOrgnId(orgnId); // 기관코드

        List<MspSalePlcyMstDto> listMspSaleDto = mspService.listMspSalePlcyInfoByOnlyOrgn(mspSalePlcyMstDto);

        if (listMspSaleDto == null || listMspSaleDto.size() == 0) {
            throw new McpCommonException(NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }

        // 할부수수료 default 값 조회
        CmnGrpCdMst cdInfo = mspService.findCmnGrpCdMst("CMN0051", "20");
        // ======================== 정책정보 조회 끝 ===============================

        // 휴대폰 요금제 2Depth 목록 조회
        List<NmcpRecommendProdCtgBasDTO> recommendProdCtgScndList = mainService.getRecommendProdCtgScndList(nmcpRecommendProdCtgBasDTO);

        // 플랫폼 환경
        String onOffType = "P".equals(NmcpServiceUtils.getPlatFormCd()) ? "0" : "3"; // PC = 0 / m = 3

        // 상품목록 추가
        for (NmcpRecommendProdCtgBasDTO recommendProdCtgScndDTO : recommendProdCtgScndList) {

            // 추천 상품 카테고리 매핑 관리 목록 조회
            List<NmcpRecommendProdCtgRelDTO> recommendProdCtgRelList = mainService.getRecommendProdCtgRelList(recommendProdCtgScndDTO);

            for (NmcpRecommendProdCtgRelDTO recommendProdCtgRelDTO : recommendProdCtgRelList) {
                // ======================== 상품리스트 조회 시작 ===============================
                CommonSearchDto commonSearchDto = new CommonSearchDto();
                commonSearchDto.setShandYn("N");
                commonSearchDto.setProdId(recommendProdCtgRelDTO.getProdId());

                commonSearchDto.setListMspSaleDto(listMspSaleDto); // 정책정보가 존재하는 상품정보를 읽어오기위해 set
                List<PhoneProdBasDto> lteList = phoneService.listPhoneProdBasForFrontOneQuery(commonSearchDto);
                if (!lteList.isEmpty()) {
                     int idx = 0;
                    // 속성모델
                    PhoneSntyBasDto phoneSntyBasDto = new PhoneSntyBasDto();
                    phoneSntyBasDto.setProdId(commonSearchDto.getProdId());
                    List<PhoneSntyBasDto> sntyProd = this.phoneService.selectProdBasSnty(phoneSntyBasDto);
                    if (!ObjectUtils.isEmpty(sntyProd)) {
                        sntyProd = sntyProd.stream().filter(snty -> !"Y".equals(snty.getSdoutYn())).collect(Collectors.toList());
                        Optional<PhoneSntyBasDto> rprsSnty = sntyProd.stream().filter(snty -> "Y".equals(snty.getRprsPrdtYn())).findFirst();
                        if (rprsSnty.isPresent()) {
                            rprsSnty.ifPresent(snty -> recommendProdCtgRelDTO.setPrdtId(snty.getHndsetModelId()));
                        } else {
                            sntyProd.stream().findFirst().ifPresent(snty -> recommendProdCtgRelDTO.setPrdtId(snty.getHndsetModelId()));
                        }
                    }

                    for (PhoneProdBasDto item : lteList) {
                        MspSalePlcyMstDto paramPlcy = null;
                        // 동일한 LTE 단말기인 경우라도 단말기 마다. 정책이 상이 할수 있다. 각 상품별 정책 정보를 설정 한다.
                        for (MspSalePlcyMstDto param : listMspSaleDto) {
                            if (item.getSalePlcyCd().equals(param.getSalePlcyCd())) {
                                paramPlcy = param;
                                break;
                            }
                        }

                        // 상품 리스트는 단말할인/24개월/번호이동이 기준값이다.
                        MspSaleSubsdMstDto mspSaleSubsdMst = null;

                        // admin에서 약정기간 관리 기능 추가
                        item.setAgrmTrmBase(StringUtils.isEmpty(item.getAgrmTrmBase()) || "0".equals(item.getAgrmTrmBase()) ? "24" : item.getAgrmTrmBase());///<====== 0일 때.. 24개월 약정?????

                        try {
                            mspSaleSubsdMst = mspService.getLowPriceChargeInfoByProdList(item.getRprsPrdtId(),paramPlcy, "N"
                                    , orgnId, OPER_PHONE_NUMBER_TRANS, item.getAgrmTrmBase(), recommendProdCtgRelDTO.getRateCd(), "Y", cdInfo, onOffType);

                            if (ObjectUtils.isEmpty(mspSaleSubsdMst)) {
                               // 추천요금제로 재 조회
                               mspSaleSubsdMst = this.mspService.getLowPriceChargeInfoByProdList(item.getRprsPrdtId(), paramPlcy, "N",
                                       mspSalePlcyMstDto.getOrgnId(), OPER_PHONE_NUMBER_TRANS, "24", null, "Y", cdInfo, onOffType); // <====  default ??????
                            }

                        } catch (McpCommonException mce) {
                            logger.error("정책정보가 없는 상품 입니다.====>[" + item.getProdNm() + "]");

                        } finally {

                            if (!ObjectUtils.isEmpty(mspSaleSubsdMst)) {
                                item.setPayMnthAmt(mspSaleSubsdMst.getPayMnthUnContAmt(item.getAgrmTrmBase()));// 무약정 단말값 계산
                                item.setInstAmt(mspSaleSubsdMst.getInstAmt());
                                item.setRentalBaseAmt(mspSaleSubsdMst.getBaseAmt() - mspSaleSubsdMst.getDcAmt()- mspSaleSubsdMst.getAddDcAmt()); // 기본료-기본할인금액-추가할인금액
                                item.setBaseAmt(mspSaleSubsdMst.getTotalPayAmout());
                                item.setRateCd(mspSaleSubsdMst.getRateCd());
                                item.setRateNm(mspSaleSubsdMst.getRateNm());
                                recommendProdCtgRelDTO.setPhoneProdBasDto(item);
                            }
                        }

                        if (idx == 0) {
                            break;
                        }
                    }
                }

            }

            recommendProdCtgScndDTO.getLists().addAll(recommendProdCtgRelList);
        }

        if (recommendProdCtgScndList != null) {
            logger.debug("workNotiList len :{}", recommendProdCtgScndList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("workNotiList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(recommendProdCtgScndList);

        return result;
    }


    /**
     * 설명 : 휴대폰요금제 조회
     *
     * @Author : 양우철
     * @Date : 2023.05.12
     * @param request
     * @param nmcpRecommendProdCtgBasDTO
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/rateAdsvcScndNeList.do")
    @ResponseBody
    public JsonReturnDto rateAdsvcScndNeList( NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO) throws ParseException {
        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";


        // 휴대폰 요금제 2Depth 목록 조회
        List<NmcpRecommendProdCtgBasDTO> recommendProdCtgScndList = mainService.getRecommendProdCtgScndList(nmcpRecommendProdCtgBasDTO);
        // 플랫폼 환경
        String onOffType = "P".equals(NmcpServiceUtils.getPlatFormCd()) ? "0" : "3"; // PC = 0 / m = 3

        // 상품목록 추가
        for (NmcpRecommendProdCtgBasDTO recommendProdCtgScndDTO : recommendProdCtgScndList) {

            // 추천 상품 카테고리 매핑 관리 목록 조회
            List<NmcpRecommendProdCtgRelDTO> recommendProdCtgRelList = mainService.getRecommendProdCtgRelList(recommendProdCtgScndDTO);

            for (NmcpRecommendProdCtgRelDTO recommendProdCtgRelDTO : recommendProdCtgRelList) {
                //recommendProdCtgRelDTO.getProdId(
                /**
                 * 상품코드로
                 * 1.상품및 단품정보 조회 상품대표이미지,용량색상별 정보 등을 조회한다.
                 * 1. MspMapper.findPhoneMspDto 단품모델ID로 핸드폰정보를 조회한다.
                 * 2. MspMapper.findMspSaleOrgnMst 판매정책정보를 조회한다.
                 * 3. MspMapper.findMspSalePrdMst 판매중인 상품정보를  조회한다.(msp)
                 * 4. 금액 확인..
                 */
                /*
                 * ## 1.상품및 단품정보 조회 상품대표이미지,용량색상별 정보 등을 조회한다.
                 */
                PhoneProdBasDto phoneProdBasDto = new PhoneProdBasDto();
                phoneProdBasDto.setProdId(recommendProdCtgRelDTO.getProdId());
                PhoneProdBasDto phoneProdBas = phoneService.findPhoneProdBasDtoByProdId(phoneProdBasDto);

                if (phoneProdBas == null) {
                    throw new McpCommonJsonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION);
                }

                String prdtSctnCd = "";
                MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
                mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP);
                if (PROD_LTE_CD.equals(phoneProdBas.getProdCtgId())) {
                    mspSalePlcyMstDto.setPrdtSctnCd(LTE_FOR_MSP);
                    prdtSctnCd = PhoneConstant.LTE_FOR_MSP;
                } else if (PROD_3G_CD.equals(phoneProdBas.getProdCtgId())) {
                    mspSalePlcyMstDto.setPrdtSctnCd(THREE_G_FOR_MSP);
                    prdtSctnCd = PhoneConstant.THREE_G_FOR_MSP;
                } else if (PROD_5G_CD.equals(phoneProdBas.getProdCtgId())) {
                    mspSalePlcyMstDto.setPrdtSctnCd(FIVE_G_FOR_MSP);
                    prdtSctnCd = PhoneConstant.FIVE_G_FOR_MSP;
                } else if (PROD_LTE5G_CD.equals(phoneProdBas.getProdCtgId())) {
                    mspSalePlcyMstDto.setPrdtSctnCd(LTEFIVE_G_FOR_MSP);
                    prdtSctnCd = PhoneConstant.LTEFIVE_G_FOR_MSP;
                }
                mspSalePlcyMstDto.setPlcySctnCd(PHONE_FOR_MSP);
                mspSalePlcyMstDto.setOrgnId(orgnId);

                String hndsetModelIdParam = recommendProdCtgRelDTO.getPrdtId();
                for (PhoneSntyBasDto snty : phoneProdBas.getPhoneSntyBasDtosList()) {
                    if ("Y".equals(snty.getRprsPrdtYn())) {
                        hndsetModelIdParam = snty.getHndsetModelId();
                    }
                }

                // 현재 단품 ID 로 해당 대표모델ID를 조회한다.
                PhoneMspDto phoneMspDto = mspService.findMspPhoneInfo(hndsetModelIdParam);
                String rprsPrdtIdCurrent = phoneMspDto.getRprsPrdtId(); // 해당 단품의 대표모델ID

                logger.info("[WOO]대표모델코드rprsPrdtIdCurrent===>" + rprsPrdtIdCurrent);

                // 2. 정책 조회
                MspSaleDto mspSaleDto = null;
                try {
                    mspSaleDto = mspService.getMspSale(rprsPrdtIdCurrent, mspSalePlcyMstDto);
                } catch (McpCommonException mce) {
                    throw new McpCommonJsonException(mce.getErrorMsg());
                }

                //3. 요금제 조회
                //관리자 대표 약정
                String agrmTrmBase = phoneProdBas.getAgrmTrmBase();
                String repRate = phoneProdBas.getRepRate();  //대표 요금제
                //String rateCd = recommendProdCtgRelDTO.getRateCd(); // NMCP_RECOMMEND_PROD_CTG_REL 설정한 요금제
                String modelMonthly = "0";

                if ("0".equals(agrmTrmBase)) {
                    modelMonthly = "24";  //무약정 , 24개달 단말 할부
                } else {
                    modelMonthly = agrmTrmBase;
                }

                mspSaleDto.setForCompareYn("Y");// 비교하기 리스트용 SQL 문 호출을 위해서 Y 일경우 상품상세 성능 개선을 위해서 불필요한 필드 제거를 하지 않는다.
                List<MspSaleSubsdMstDto> chargeList = mspService.listChargeInfo(rprsPrdtIdCurrent, mspSaleDto, "N", orgnId,
                        OPER_PHONE_NUMBER_TRANS, agrmTrmBase, modelMonthly , repRate, "Y",onOffType);

                if (chargeList == null  || chargeList.size() < 1) {
                    logger.error("요금제 정보가 없습니다. ");
                }
                phoneProdBas.setMspSaleSubsdMstListForLowPrice(chargeList);
                recommendProdCtgRelDTO.setPhoneProdBasDto(phoneProdBas) ;

            }

            recommendProdCtgScndDTO.getLists().addAll(recommendProdCtgRelList);

        }



        result.setReturnCode("00");
        result.setMessage("성공");
        result.setResult(recommendProdCtgScndList);

        return result;
    }

    /**
     * 설명 : 요금제 추천 질문 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = { "/m/rateRecommendInqrBasListAjax.do" })
    @ResponseBody
    public JsonReturnDto rateRecommendInqrBasListAjax(HttpServletRequest request) throws ParseException {
        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        List<NmcpRateRecommendInqrBasDTO> iinqrBasList = mainService.selectRateRecommendInqrBasList();

        if (iinqrBasList != null) {
            logger.debug("iinqrBasList len :{}", iinqrBasList.size());
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("inqrBasList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(iinqrBasList);

        return result;
    }

    /**
     * 설명 : 요금제 추천 결과 조회
     *
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param request
     * @param nmcpRateRecommendInqrRelDTO
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = { "/m/rateRecommendInqrRelAjax.do" })
    @ResponseBody
    public JsonReturnDto rateRecommendInqrRelAjax(HttpServletRequest request,
            NmcpRateRecommendInqrRelDTO nmcpRateRecommendInqrRelDTO) throws ParseException {
        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        List<NmcpRecommendProdCtgRelDTO> rtnList = new ArrayList<NmcpRecommendProdCtgRelDTO>();
        List<NmcpRateRecommendInqrRelDTO> inqrRelList = mainService
                .selectRateRecommendInqrRel(nmcpRateRecommendInqrRelDTO);

        if (inqrRelList != null) {
            for (NmcpRateRecommendInqrRelDTO inqrRelDTO : inqrRelList) {
                // Param 추가
                RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
                rateAdsvcCtgBasDTO.setRateAdsvcGdncSeq(inqrRelDTO.getRateAdsvcGdncSeq());

                // 요금제부가서비스안내기본 조회
                RateAdsvcGdncBasXML gdncBasVo = rateAdsvcGdncService.getRateAdsvcGdncBasXml(rateAdsvcCtgBasDTO);
                if (gdncBasVo != null) {
                    if (DateTimeUtil.isMiddleDateTime(gdncBasVo.getPstngStartDate(), gdncBasVo.getPstngEndDate())) {
                        // 요금제부가서비스혜택안내상세 xml 목록 조회
                        List<RateAdsvcBnfitGdncDtlXML> bnfitList = new ArrayList<RateAdsvcBnfitGdncDtlXML>();
                        bnfitList = rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(rateAdsvcCtgBasDTO);

                        NmcpRecommendProdCtgRelDTO recommendProdCtgRelDTO = new NmcpRecommendProdCtgRelDTO();
                        recommendProdCtgRelDTO.setRateAdsvcNm(gdncBasVo.getRateAdsvcNm());
                        recommendProdCtgRelDTO.setRateAdsvcBasDesc(gdncBasVo.getRateAdsvcBasDesc());
                        recommendProdCtgRelDTO.setMmBasAmtDesc(gdncBasVo.getMmBasAmtDesc());
                        recommendProdCtgRelDTO.setMmBasAmtVatDesc(gdncBasVo.getMmBasAmtVatDesc());
                        recommendProdCtgRelDTO.setPromotionAmtDesc(gdncBasVo.getPromotionAmtDesc());
                        recommendProdCtgRelDTO.setPromotionAmtVatDesc(gdncBasVo.getPromotionAmtVatDesc());
                        recommendProdCtgRelDTO.setRateCd(inqrRelDTO.getRateCd());

                        // 혜택정보 추가
                        if (bnfitList != null) {
                            for (RateAdsvcBnfitGdncDtlXML bnfitDTO : bnfitList) {
                                // 데이터
                                if ("RATEBE01".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setBnfitData(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 음성
                                if ("RATEBE02".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setBnfitVoice(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 문자
                                if ("RATEBE03".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setBnfitSms(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // WiFi
                                if ("RATEBE04".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setBnfitWifi(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 프로모션 데이터
                                if ("RATEBE31".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setPromotionBnfitData(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 프로모션 음성
                                if ("RATEBE32".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setPromotionBnfitVoice(bnfitDTO.getRateAdsvcItemDesc());
                                }
                                // 프로모션 문자
                                if ("RATEBE33".equals(bnfitDTO.getRateAdsvcBnfitItemCd())) {
                                    recommendProdCtgRelDTO.setPromotionBnfitSms(bnfitDTO.getRateAdsvcItemDesc());
                                }
                            }
                        }
                        rtnList.add(recommendProdCtgRelDTO);
                    }
                }
            }
        }

        if (rtnList != null) {
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("inqrBasResultList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(rtnList);

        return result;
    }
}
